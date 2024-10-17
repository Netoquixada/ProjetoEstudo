package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.CidadeBO;
import br.com.controlpro.bo.ClienteGlamixBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.entity.ClienteGlamix;
import br.com.controlpro.entity.consultas.Cidade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class ClienteGlamixBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarClienteGlamix.xhtml";
	private final String LIST = "/private/lista/listarClienteGlamix.xhtml?faces-redirect=true";

	private ClienteGlamix clienteGlamix;
	private ClienteGlamix clienteGlamixFilter;
	private List<ClienteGlamix> clienteGlamixs;
	private List<Cidade> cidades;

	@PostConstruct
	public void init() {
		clienteGlamix = new ClienteGlamix();
		clienteGlamixFilter = new ClienteGlamix();
		clienteGlamixs = new ArrayList<ClienteGlamix>();
		cidades = new ArrayList<Cidade>();
	}

	public String save() {
		try {
			ClienteGlamixBO.getInstance().save(clienteGlamix);
			addInfoMessage("Cadastrado com sucesso!", "ClienteGlamix " + clienteGlamix.getNome());
			clienteGlamix = new ClienteGlamix();
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
			ClienteGlamixBO.getInstance().update(clienteGlamix);
			addInfoMessage("Editado com sucesso!", "ClienteGlamix " + clienteGlamix.getNome());
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
		clienteGlamix = new ClienteGlamix();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String list() {
		try {
			clienteGlamixs = ClienteGlamixBO.getInstance().clienteGlamixListReport(clienteGlamixFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, clienteGlamixs, "faccoes",
				"Relatório de facções - " + DataUtil.formatarData(new Date()), true);
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		clienteGlamix = new ClienteGlamix();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (clienteGlamix.getStatus()) {
				clienteGlamix.setStatus(false);
				ClienteGlamixBO.getInstance().update(clienteGlamix);
			} else {
				clienteGlamix.setStatus(true);
				ClienteGlamixBO.getInstance().update(clienteGlamix);
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
		String status = clienteGlamix.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O clienteGlamix est� " + status);
		return null;
	}
	
	public List<ClienteGlamix> getClienteGlamixAutoComplete(String nomeClienteGlamix) {

		List<ClienteGlamix> faccoesComplete = new ArrayList<ClienteGlamix>();

		try {
			faccoesComplete = ClienteGlamixBO.getInstance().clienteGlamixsComplete(nomeClienteGlamix);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return faccoesComplete;
	}
	
	public Estados[] getEstados(){
		return Estados.values();
	}
	
	public ClienteGlamix getClienteGlamix() {
		return clienteGlamix;
	}

	public void setClienteGlamix(ClienteGlamix clienteGlamix) {
		this.clienteGlamix = clienteGlamix;
	}

	public ClienteGlamix getClienteGlamixFilter() {
		return clienteGlamixFilter;
	}

	public void setClienteGlamixFilter(ClienteGlamix clienteGlamixFilter) {
		this.clienteGlamixFilter = clienteGlamixFilter;
	}


	public List<ClienteGlamix> getClienteGlamixs() {
		try {
			clienteGlamixs = ClienteGlamixBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return clienteGlamixs;
	}

	public void setClienteGlamixs(List<ClienteGlamix> clienteGlamixs) {
		this.clienteGlamixs = clienteGlamixs;
	}
	
	public List<Cidade> getCidades() {
		try {
			cidades = CidadeBO.getInstance().cidadesPorEstado(clienteGlamix.getEstado());
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return cidades;
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
}
