package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.ControleManifestacaoBO;
import br.com.controlpro.bo.OcorrenciaControleManifestacaoBO;
import br.com.controlpro.constants.Envio;
import br.com.controlpro.constants.LocalControleInterno;
import br.com.controlpro.entity.ControleManifestacao;
import br.com.controlpro.entity.OcorrenciaControleManifestacao;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class ControleManifestacaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarControleManifestacao.xhtml";
	private final String LIST = "/private/lista/listarControleManifestacao.xhtml?faces-redirect=true";

	private ControleManifestacao controleManifestacao;
	private ControleManifestacao controleManifestacaoFilter;
	private List<ControleManifestacao> controleManifestacaos = new ArrayList<ControleManifestacao>();

	private Usuario usuarioLogado;

	private OcorrenciaControleManifestacao ocorrencia;
	private List<OcorrenciaControleManifestacao> ocorrenciaList;

	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		controleManifestacao = new ControleManifestacao();
		controleManifestacaoFilter = new ControleManifestacao();
		try {
			controleManifestacaos = ControleManifestacaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			e1.printStackTrace();
		}

		ocorrencia = new OcorrenciaControleManifestacao();
		ocorrenciaList = new ArrayList<>();

		ControleManifestacao aux = RecuperarObjetoViaRequisicao.getObjeto(ControleManifestacao.class, "id");
		controleManifestacao = aux != null ? aux : new ControleManifestacao();

		// CASO SEJA EDI��O DE DADOS
		if (controleManifestacao.getId() != null) {
			try {
				ocorrenciaList = OcorrenciaControleManifestacaoBO.getInstance().historicoPorControlePedido(controleManifestacao);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}

	}

	public String save() {
		try {
			String protocolo = formataProtocolo(controleManifestacao);
			controleManifestacao.setProtocolo(protocolo);

			ControleManifestacaoBO.getInstance().save(controleManifestacao);

			addInfoMessage("Cadastrado com sucesso!", "Protocolo: "+controleManifestacao.getProtocolo() + " - " +
					controleManifestacao.getCodigoRastreio() + " - " + DataUtil.formatarData(controleManifestacao.getDataCadastro()));
			
			controleManifestacao = new ControleManifestacao();
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
		return CAD_EDIT;
	}


	public String update() {
		try {
			ControleManifestacaoBO.getInstance().update(controleManifestacao);
			addInfoMessage("Editado com sucesso!", "");
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
		controleManifestacao = new ControleManifestacao();
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	public String gerarPDFControleManifestacao() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<ControleManifestacao> controle = new ArrayList<ControleManifestacao>();

		controle.add(controleManifestacao);

		carregarListaItemProducao();
		mapa.put("controle-manifestacao-ocorrencias", ocorrenciaList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, controle, "controle-manifestacao",
				"Controle de Manifestação - " + controleManifestacao.getProtocolo(), true);
		return null;
	}


	public String list() {
		try {
			controleManifestacaos = ControleManifestacaoBO.getInstance().controleManifestacaoListReport(controleManifestacaoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		controleManifestacao = new ControleManifestacao();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (controleManifestacao.getStatus()) {
				controleManifestacao.setStatus(false);
				ControleManifestacaoBO.getInstance().update(controleManifestacao);
			} else {
				controleManifestacao.setStatus(true);
				ControleManifestacaoBO.getInstance().update(controleManifestacao);
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
		String status = controleManifestacao.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O controleManifestacao est� " + status);
		return null;
	}

	public Envio[] getEnvios() {
		return Envio.values();
	}

	public LocalControleInterno[] getLocais() {
		return LocalControleInterno.values();
	}


	public String formataProtocolo(ControleManifestacao controleManifestacao)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "CM" + ControleManifestacaoBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(controleManifestacao.getDataCadastro());
		return res;
	}

	public void carregarListaItemProducao() {
		try {
			ocorrenciaList = OcorrenciaControleManifestacaoBO.getInstance().historicoPorControlePedido(controleManifestacao);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public List<OcorrenciaControleManifestacao> carregarOcorrencias(ControleManifestacao ordem) {
		try {
			return OcorrenciaControleManifestacaoBO.getInstance().historicoPorControlePedido(ordem);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveOcorrencia() {
		try {
			String protocolo = formataProtocoloOcorrencia();
			ocorrencia.setProtocolo(protocolo);
			ocorrencia.setUsuario(usuarioLogado);
			ocorrencia.setControleManifestacao(controleManifestacao);

			OcorrenciaControleManifestacaoBO.getInstance().save(ocorrencia);

			controleManifestacao.setStatusOcorrencia(1);
			ControleManifestacaoBO.getInstance().update(controleManifestacao);

			addInfoMessage("Cadastrado com sucesso!", "");
			ocorrencia = new OcorrenciaControleManifestacao();

			carregarListaItemProducao();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
	}

	public String finalizarOcorrencia() {
		try {

			String protocolo = formataProtocoloOcorrencia();
			ocorrencia.setProtocolo(protocolo);
			ocorrencia.setUsuario(usuarioLogado);
			ocorrencia.setControleManifestacao(controleManifestacao);
			ocorrencia.setDescricao("Finalizando ocorrência!");
			OcorrenciaControleManifestacaoBO.getInstance().save(ocorrencia);

			controleManifestacao.setStatusOcorrencia(2);
			ControleManifestacaoBO.getInstance().update(controleManifestacao);
			addInfoMessage("Ocorrencia finalizada com sucesso!", "");
			ocorrencia = new OcorrenciaControleManifestacao();
			carregarListaItemProducao();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String formataProtocoloOcorrencia()
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "OCM" + ControleManifestacaoBO.getInstance().geradorDeProtocoloOcorrencia() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(ocorrencia.getDataCadastro());
		return res;
	}

	public ControleManifestacao getControleManifestacao() {
		return controleManifestacao;
	}

	public void setControleManifestacao(ControleManifestacao controleManifestacao) {
		this.controleManifestacao = controleManifestacao;
	}

	public ControleManifestacao getControleManifestacaoFilter() {
		return controleManifestacaoFilter;
	}

	public void setControleManifestacaoFilter(ControleManifestacao controleManifestacaoFilter) {
		this.controleManifestacaoFilter = controleManifestacaoFilter;
	}

	public List<ControleManifestacao> getControleManifestacaos() {
		return controleManifestacaos;
	}

	public void setControleManifestacaos(List<ControleManifestacao> controleManifestacaos) {
		this.controleManifestacaos = controleManifestacaos;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public OcorrenciaControleManifestacao getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(OcorrenciaControleManifestacao ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public List<OcorrenciaControleManifestacao> getOcorrenciaList() {
		return ocorrenciaList;
	}

	public void setOcorrenciaList(List<OcorrenciaControleManifestacao> ocorrenciaList) {
		this.ocorrenciaList = ocorrenciaList;
	}

}
