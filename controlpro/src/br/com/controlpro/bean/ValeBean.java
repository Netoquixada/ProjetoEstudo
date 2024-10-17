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

import br.com.controlpro.bo.ValeBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.StatusVale;
import br.com.controlpro.entity.Vale;
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
public class ValeBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Vale vale;
	private Vale valeFilter;
//	private LazyEntityDataModel<Vale> lazy;
	private List<Vale> vales;

	@PostConstruct
	public void init() {
		vale = new Vale();
		valeFilter = new Vale();
//		lazy = new LazyEntityDataModel<Vale>(Vale.class);
		vales = new ArrayList<Vale>();
	}

	public void save() {
		try {
			vale.setPago(false);
			vale.setStatusVale(StatusVale.ABERTO);
			ValeBO.getInstance().save(vale);
			addInfoMessage("Cadastrado com sucesso!", "Vale " + vale.getFuncionario().getNome());
			vale = new Vale();
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

			List<Vale> vales = ValeBO.getInstance().valeListReport(valeFilter);

			valeFilter = new Vale();
			mapa.put("filtro", ValeBO.getInstance().dadosFiltro());
			mapa.put("valorTotal", getTotal());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							vales,
							"vales",
							"Relatório de vales - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void deletar(Vale vale) {
		try {
			ValeBO.getInstance().delete(vale);
			addInfoMessage("Excluído com sucesso!", "Vale " + vale.getFuncionario().getNome());
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
		for (Vale item : vales) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public String update() {
		try {
			ValeBO.getInstance().update(vale);
			addInfoMessage("Editado com sucesso!", "Vale " + vale.getFuncionario().getNome());
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
		vale = new Vale();
		AfterRedirect.manterMensagem();
		return "/private/control/controlVale.xhtml?faces-redirect=true";
	}

	public String list() {
		try {
			vales = ValeBO.getInstance().valeListReport(valeFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OpcoesPagamento[] getOpcoesPagamento(){
		return OpcoesPagamento.values();
	}
	
	public Loja[] getLojas() {
		return Loja.values();
	}

	public String updateStatus() {
		try {
			if (vale.getStatus()) {
				vale.setStatus(false);
				ValeBO.getInstance().update(vale);
			} else {
				vale.setStatus(true);
				ValeBO.getInstance().update(vale);
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
		String status = vale.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O vale est� " + status);
		return null;
	}

	public String updatePagamento() {
		try {
			if (vale.getPago()) {
				vale.setPago(false);
				vale.setStatusVale(StatusVale.ABERTO);
				ValeBO.getInstance().update(vale);
			} else {
				vale.setPago(true);
				vale.setStatusVale(StatusVale.PAGO);
				ValeBO.getInstance().update(vale);
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
		String status = vale.getPago() ? "Pago" : "Não pago";
		addInfoMessage("Pagamento realizado com sucesso!", "O vale está " + status);
		return null;
	}

	public Estados[] getEstados() {
		return Estados.values();
	}

	public StatusVale[] getStatusValeList() {
		return StatusVale.values();
	}

	public Vale getVale() {
		return vale;
	}

	public void setVale(Vale vale) {
		this.vale = vale;
	}

	public Vale getValeFilter() {
		return valeFilter;
	}

	public void setValeFilter(Vale valeFilter) {
		this.valeFilter = valeFilter;
	}

//	public LazyEntityDataModel<Vale> getLazy() {
//		return lazy;
//	}
//
//	public void setLazy(LazyEntityDataModel<Vale> lazy) {
//		this.lazy = lazy;
//	}

	public List<Vale> getVales() {
		return vales;
	}

	public void setVales(List<Vale> vales) {
		this.vales = vales;
	}
}
