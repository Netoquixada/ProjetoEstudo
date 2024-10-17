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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;

import br.com.controlpro.bo.MateriaPrimaBO;
import br.com.controlpro.bo.SubGrupoBO;
import br.com.controlpro.constants.GrupoItem;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.constants.TipoMateriaPrima;
import br.com.controlpro.constants.UnidadeMedida;
import br.com.controlpro.entity.MateriaPrima;
import br.com.controlpro.entity.SubGrupo;
import br.com.controlpro.entity.consultas.Cliente;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class MateriaPrimaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarMateriaPrima.xhtml";
	private final String LIST = "/private/lista/listarMateriaPrima.xhtml?faces-redirect=true";

	private MateriaPrima materiaPrima;
	private MateriaPrima materiaPrimaFilter;
	private List<MateriaPrima> materiaPrimas;

	private Cliente cliente;
	Cliente clienteTemp;
	private List<Cliente> clienteList;
	private GrupoItem grupoItem;
	private SubGrupo subGrupoItem;
	List<SubGrupo> subSubGrupos = new ArrayList<>();

	@PostConstruct
	public void init() {
		materiaPrima = new MateriaPrima();
		materiaPrimaFilter = new MateriaPrima();
		try {
			materiaPrimas = MateriaPrimaBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cliente = new Cliente();
		clienteList = new ArrayList<Cliente>();

		MateriaPrima aux = RecuperarObjetoViaRequisicao.getObjeto(MateriaPrima.class, "id");
		materiaPrima = aux != null ? aux : new MateriaPrima();

		// CASO SEJA EDIÇÃO DE DADOS
		if (materiaPrima.getId() != null) {
			clienteList = materiaPrima.getFornecedores();
		}
	}

	public String save() {
		try {
			materiaPrima.setUltimaAtualizacao(new Date());
			MateriaPrimaBO.getInstance().save(materiaPrima);
			addInfoMessage("Cadastrado com sucesso!", "");
			materiaPrima = new MateriaPrima();
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

	public UnidadeMedida[] getUnidades() {
		return UnidadeMedida.values();
	}

	public GrupoItem[] getGrupoItens() {
		return GrupoItem.values();
	}

	public TipoMateriaPrima[] getTipos() {
		return TipoMateriaPrima.values();
	}

	public String update() {
		try {
			materiaPrima.setUltimaAtualizacao(new Date());
			if(materiaPrima.getGrupoItem().equals(GrupoItem.AVIAMENTO)) {
				materiaPrima.setTipoMateriaPrima(null);
			}
			MateriaPrimaBO.getInstance().update(materiaPrima);
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
		materiaPrima = new MateriaPrima();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public SituacaoOrdem[] getSituacoes() {
		return SituacaoOrdem.values();
	}
	
	public void clearCliente() {
		cliente = new Cliente();
	}
	
	// Upload do arquivo
		public void uploadFile(FileUploadEvent event) {
			try {
				this.materiaPrima.setExtensao(getExtensao(event.getFile().getFileName()));
				this.materiaPrima.setArquivo(event.getFile().getContents());
				FacesMessage message = new FacesMessage("Enviado com sucesso!", event.getFile().getFileName());
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (Exception e) {
				e.printStackTrace();
				addWarnMessage("Erro: " + e.getMessage(), "");
			}
		}

		public String getExtensao(String nomeArquivo) {
			if (nomeArquivo.isEmpty() || nomeArquivo == null)
				return null;
			return nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length()).toLowerCase();
		}

	public List<MateriaPrima> getMaterias() {
		List<MateriaPrima> lista = new ArrayList<>();
		try {
			lista = MateriaPrimaBO.getInstance().materiaPrimaPorGrupo(grupoItem,subGrupoItem);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return lista;
	}
	public List<MateriaPrima> getMateriasAviamento() {
		List<MateriaPrima> lista = new ArrayList<>();
		try {
			lista = MateriaPrimaBO.getInstance().listAviamento();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return lista;
	}
	public List<MateriaPrima> getMateriasMalha() {
		List<MateriaPrima> lista = new ArrayList<>();
		try {
			lista = MateriaPrimaBO.getInstance().listMalha();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<MateriaPrima> getMateriaPrimaAutoComplete(String nome) {

		List<MateriaPrima> materiaComplete = new ArrayList<MateriaPrima>();

		try {
			materiaComplete = MateriaPrimaBO.getInstance().materiaPrimasComplete(nome);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return materiaComplete;
	}
	
	public String list() {
		try {
			materiaPrimas = MateriaPrimaBO.getInstance().materiaPrimaListReport(materiaPrimaFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, materiaPrimas, "materia-prima",
				"Relatório de Materia Prima - " + DataUtil.formatarData(new Date()), true);
	}


	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		materiaPrima = new MateriaPrima();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (materiaPrima.getStatus()) {
				materiaPrima.setStatus(false);
				MateriaPrimaBO.getInstance().update(materiaPrima);
			} else {
				materiaPrima.setStatus(true);
				MateriaPrimaBO.getInstance().update(materiaPrima);
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
		String status = materiaPrima.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O materiaPrima está " + status);
		return null;
	}

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public MateriaPrima getMateriaPrimaFilter() {
		return materiaPrimaFilter;
	}

	public void setMateriaPrimaFilter(MateriaPrima materiaPrimaFilter) {
		this.materiaPrimaFilter = materiaPrimaFilter;
	}

	public List<MateriaPrima> getMateriaPrimas() {
		return materiaPrimas;
	}

	public void setMateriaPrimas(List<MateriaPrima> materiaPrimas) {
		this.materiaPrimas = materiaPrimas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClienteList() {
		if (clienteList == null) {
			clienteList = new ArrayList<Cliente>();
		}
		return clienteList;
	}

	public void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
	}
	
	public GrupoItem getGrupoItem() {
		return grupoItem;
	}
	
	public void setGrupoItem(GrupoItem grupoItem) {
		this.grupoItem = grupoItem;
	}
	
	public SubGrupo getSubGrupoItem() {
		return subGrupoItem;
	}
	
	public void setSubGrupoItem(SubGrupo subGrupoItem) {
		this.subGrupoItem = subGrupoItem;
	}
	
	public List<SubGrupo> getSubGrupos() {
		try {
			subSubGrupos = SubGrupoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return subSubGrupos;
	}

}
