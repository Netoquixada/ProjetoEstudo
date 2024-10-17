package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.OrdemProducaoBO;
import br.com.controlpro.bo.SangriaBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.Sangria;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.ManagedBeanHelper;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class SangriaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Sangria sangria;
	private Sangria sangriaFilter;
	private List<Sangria> sangrias;

	private Usuario usuarioLogado;

	@PostConstruct
	public void init() {
		sangria = new Sangria();
		sangriaFilter = new Sangria();
		sangrias = new ArrayList<Sangria>();

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		try {
			sangrias = SangriaBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {

			String protocolo = formataProtocolo(sangria);
			sangria.setProtocolo(protocolo);
			sangria.setResponsavel(usuarioLogado);
			SangriaBO.getInstance().save(sangria);
			addInfoMessage("Cadastrado com sucesso!", "Sangria " + sangria.getDescricao());
			sangria = new Sangria();
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

	public String update() {
		try {
			SangriaBO.getInstance().update(sangria);
			addInfoMessage("Editado com sucesso!", "Sangria " + sangria.getDescricao());
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
		sangria = new Sangria();
		AfterRedirect.manterMensagem();
		return "/private/control/controlSangria.xhtml?faces-redirect=true";
	}

	public String formataProtocolo(Sangria sangria)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = SangriaBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(sangria.getDataSangria());
		return res;
	}

	public String list() {
		try {
			sangrias = SangriaBO.getInstance().sangriaListReport(sangriaFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("valorTotal", getTotal());
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, sangrias, "sangrias",
				"Relatório de sangrias - " + DataUtil.formatarData(new Date()), true);
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (Sangria item : sangrias) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public String updateStatus() {
		try {
			if (sangria.getStatus()) {
				sangria.setStatus(false);
				SangriaBO.getInstance().update(sangria);
			} else {
				sangria.setStatus(true);
				SangriaBO.getInstance().update(sangria);
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
		String status = sangria.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O sangria est� " + status);
		return null;
	}

	public Estados[] getEstados() {
		return Estados.values();
	}

	public Sangria getSangria() {
		return sangria;
	}

	public void setSangria(Sangria sangria) {
		this.sangria = sangria;
	}

	public Sangria getSangriaFilter() {
		return sangriaFilter;
	}

	public void setSangriaFilter(Sangria sangriaFilter) {
		this.sangriaFilter = sangriaFilter;
	}

	public List<Sangria> getSangrias() {
		return sangrias;
	}

	public void setSangrias(List<Sangria> sangrias) {
		this.sangrias = sangrias;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
