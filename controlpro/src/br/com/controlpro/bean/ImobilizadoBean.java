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

import br.com.controlpro.bo.ImobilizadoBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.TipoImobilizado;
import br.com.controlpro.entity.Imobilizado;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class ImobilizadoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Imobilizado imobilizado;
	private Imobilizado imobilizadoFilter;
	private List<Imobilizado> imobilizados;

	@PostConstruct
	public void init() {
		imobilizado = new Imobilizado();
		imobilizadoFilter = new Imobilizado();
		imobilizados = new ArrayList<Imobilizado>();
	}

	public void save() {
		try {
			ImobilizadoBO.getInstance().save(imobilizado);
			addInfoMessage("Cadastrado com sucesso!", "Imobilizado " + imobilizado.getNome());
			imobilizado = new Imobilizado();
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

			List<Imobilizado> imobilizados = ImobilizadoBO.getInstance().imobilizadoListReport(imobilizadoFilter);

			imobilizadoFilter = new Imobilizado();
			mapa.put("valorTotal", getTotal());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							imobilizados,
							"imobilizados",
							"Relatório de imobilizados - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (Imobilizado item : imobilizados) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public TipoImobilizado[] getTipoImobilizado() {
		return TipoImobilizado.values();
	}
	
	public Loja[] getLoja() {
		return Loja.values();
	}
	
	public String update() {
		try {
			ImobilizadoBO.getInstance().update(imobilizado);
			addInfoMessage("Editado com sucesso!", "Imobilizado " + imobilizado.getNome());
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
		imobilizado = new Imobilizado();
		AfterRedirect.manterMensagem();
		return "/private/control/controlImobilizado.xhtml?faces-redirect=true";
	}

	public String list() {
		try {
			imobilizados = ImobilizadoBO.getInstance().imobilizadoListReport(imobilizadoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String updateStatus() {
		try {
			if (imobilizado.getStatus()) {
				imobilizado.setStatus(false);
				ImobilizadoBO.getInstance().update(imobilizado);
			} else {
				imobilizado.setStatus(true);
				ImobilizadoBO.getInstance().update(imobilizado);
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
		String status = imobilizado.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O imobilizado est� " + status);
		return null;
	}

	public Imobilizado getImobilizado() {
		return imobilizado;
	}

	public void setImobilizado(Imobilizado imobilizado) {
		this.imobilizado = imobilizado;
	}

	public Imobilizado getImobilizadoFilter() {
		return imobilizadoFilter;
	}

	public void setImobilizadoFilter(Imobilizado imobilizadoFilter) {
		this.imobilizadoFilter = imobilizadoFilter;
	}


	public List<Imobilizado> getImobilizados() {
		return imobilizados;
	}

	public void setImobilizados(List<Imobilizado> imobilizados) {
		this.imobilizados = imobilizados;
	}
}
