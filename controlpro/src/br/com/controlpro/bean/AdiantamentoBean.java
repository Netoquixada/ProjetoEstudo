package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.AdiantamentoBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.StatusVale;
import br.com.controlpro.constants.TipoAdiantamento;
import br.com.controlpro.entity.Adiantamento;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class AdiantamentoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Adiantamento adiantamento;
	private Adiantamento adiantamentoFilter;
	private List<Adiantamento> adiantamentos;

	@PostConstruct
	public void init() {
		adiantamento = new Adiantamento();
		adiantamentoFilter = new Adiantamento();
		adiantamentos = new ArrayList<Adiantamento>();
	}

	public void save() {
		try {
			adiantamento.setPago(false);
			adiantamento.setStatusAdiantamento(StatusVale.ABERTO);
			AdiantamentoBO.getInstance().save(adiantamento);
			addInfoMessage("Cadastrado com sucesso!", "Adiantamento " + adiantamento.getFaccao().getNome());
			adiantamento = new Adiantamento();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
	}
	
	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<Adiantamento> adiantamentos = AdiantamentoBO.getInstance().adiantamentoListReport(adiantamentoFilter);

			adiantamentoFilter = new Adiantamento();
			mapa.put("filtro", AdiantamentoBO.getInstance().dadosFiltro());
			mapa.put("valorTotal", getTotal());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							adiantamentos,
							"adiantamentos",
							"Relatório de adiantamentos - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void deletar(Adiantamento adiantamento) {
		try {
			AdiantamentoBO.getInstance().delete(adiantamento);
			addInfoMessage("Excluído com sucesso!", "Adiantamento " + adiantamento.getFaccao().getNome());
		} catch (IntegridadeObjetoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (Adiantamento item : adiantamentos) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public String update() {
		try {
			AdiantamentoBO.getInstance().update(adiantamento);
			addInfoMessage("Editado com sucesso!", "Adiantamento " + adiantamento.getFaccao().getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		adiantamento = new Adiantamento();
		AfterRedirect.manterMensagem();
		return "/private/control/controlAdiantamento.xhtml?faces-redirect=true";
	}

	public String list() {
		try {
			adiantamentos = AdiantamentoBO.getInstance().adiantamentoListReport(adiantamentoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OpcoesPagamento[] getOpcoesPagamento(){
		return OpcoesPagamento.values();
	}
	
	public TipoAdiantamento[] getTiposAdiantamentos() {
		return TipoAdiantamento.values();
	}

	public String updateStatus() {
		try {
			if (adiantamento.getStatus()) {
				adiantamento.setStatus(false);
				AdiantamentoBO.getInstance().update(adiantamento);
			} else {
				adiantamento.setStatus(true);
				AdiantamentoBO.getInstance().update(adiantamento);
			}
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}
		String status = adiantamento.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O adiantamento est� " + status);
		return null;
	}

	public String updatePagamento() {
		try {
			if (adiantamento.getPago()) {
				adiantamento.setPago(false);
				adiantamento.setStatusAdiantamento(StatusVale.ABERTO);
				AdiantamentoBO.getInstance().update(adiantamento);
			} else {
				adiantamento.setPago(true);
				adiantamento.setStatusAdiantamento(StatusVale.PAGO);
				AdiantamentoBO.getInstance().update(adiantamento);
			}
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}
		String status = adiantamento.getPago() ? "Pago" : "Não pago";
		addInfoMessage("Pagamento realizado com sucesso!", "O adiantamento está " + status);
		return null;
	}

	public Estados[] getEstados() {
		return Estados.values();
	}

	public StatusVale[] getStatusAdiantamentoList() {
		return StatusVale.values();
	}

	public Adiantamento getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Adiantamento adiantamento) {
		this.adiantamento = adiantamento;
	}

	public Adiantamento getAdiantamentoFilter() {
		return adiantamentoFilter;
	}

	public void setAdiantamentoFilter(Adiantamento adiantamentoFilter) {
		this.adiantamentoFilter = adiantamentoFilter;
	}

	public List<Adiantamento> getAdiantamentos() {
		return adiantamentos;
	}

	public void setAdiantamentos(List<Adiantamento> adiantamentos) {
		this.adiantamentos = adiantamentos;
	}
}
