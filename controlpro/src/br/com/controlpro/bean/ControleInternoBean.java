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

import br.com.controlpro.bo.ControleInternoBO;
import br.com.controlpro.constants.LocalControleInterno;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.RelatoControleInterno;
import br.com.controlpro.constants.TipoControleInterno;
import br.com.controlpro.entity.ControleInterno;
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
public class ControleInternoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private ControleInterno controleInterno;
	private ControleInterno controleInternoFilter;
	private List<ControleInterno> controleInternos;

	@PostConstruct
	public void init() {
		controleInterno = new ControleInterno();
		controleInternoFilter = new ControleInterno();
		controleInternos = new ArrayList<ControleInterno>();
	}

	public void save() {
		try {
			ControleInternoBO.getInstance().save(controleInterno);
			addInfoMessage("Cadastrado com sucesso!", "ControleInterno " + controleInterno.getRelatoControleInterno().getDescricao());
			controleInterno = new ControleInterno();
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

			List<ControleInterno> controleInternos = ControleInternoBO.getInstance().controleInternoListReport(controleInternoFilter);

			controleInternoFilter = new ControleInterno();
			mapa.put("filtro", ControleInternoBO.getInstance().dadosFiltro());
			mapa.put("valorTotal", getTotal());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							controleInternos,
							"controle-interno",
							"Relatório de Controle Interno - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void deletar(ControleInterno controleInterno) {
		try {
			ControleInternoBO.getInstance().delete(controleInterno);
			addInfoMessage("Excluído com sucesso!", "ControleInterno " + controleInterno.getRelatoControleInterno().getDescricao());
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
		for (ControleInterno item : controleInternos) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public String update() {
		try {
			ControleInternoBO.getInstance().update(controleInterno);
			addInfoMessage("Editado com sucesso!", "ControleInterno " + controleInterno.getRelatoControleInterno().getDescricao());
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
		controleInterno = new ControleInterno();
		AfterRedirect.manterMensagem();
		return "/private/control/controlControleInterno.xhtml?faces-redirect=true";
	}

	public String list() {
		try {
			controleInternos = ControleInternoBO.getInstance().controleInternoListReport(controleInternoFilter);
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
			if (controleInterno.getStatus()) {
				controleInterno.setStatus(false);
				ControleInternoBO.getInstance().update(controleInterno);
			} else {
				controleInterno.setStatus(true);
				ControleInternoBO.getInstance().update(controleInterno);
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
		String status = controleInterno.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O controleInterno est� " + status);
		return null;
	}

	public TipoControleInterno[] getTiposControleInterno() {
		return TipoControleInterno.values();
	}
	
	public RelatoControleInterno[] getRelatosControleInterno() {
		return RelatoControleInterno.values();
	}
	
	public LocalControleInterno[] getLocaisControleInterno() {
		return LocalControleInterno.values();
	}

	public ControleInterno getControleInterno() {
		return controleInterno;
	}

	public void setControleInterno(ControleInterno controleInterno) {
		this.controleInterno = controleInterno;
	}

	public ControleInterno getControleInternoFilter() {
		return controleInternoFilter;
	}

	public void setControleInternoFilter(ControleInterno controleInternoFilter) {
		this.controleInternoFilter = controleInternoFilter;
	}

	public List<ControleInterno> getControleInternos() {
		return controleInternos;
	}

	public void setControleInternos(List<ControleInterno> controleInternos) {
		this.controleInternos = controleInternos;
	}
}
