package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.ClienteBO;
import br.com.controlpro.constants.TipoEscursao;
import br.com.controlpro.entity.consultas.Cliente;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.BuscaNoWebContent;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Cliente cliente;
	private Cliente clienteFilter;
	private List<Cliente> listaClientes;
	private LazyEntityDataModel<Cliente> lazy;

	@PostConstruct
	public void init() {
		cliente = new Cliente();
		clienteFilter = new Cliente();
		listaClientes = new ArrayList<Cliente>();
		lazy = new LazyEntityDataModel<Cliente>(Cliente.class);
		try {
			listaClientes = ClienteBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String list() {
		lazy = ClienteBO.getInstance().clientesLazy(clienteFilter);
		return null;
	}
	
	public TipoEscursao[] getTiposExcursao() {
		return TipoEscursao.values();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public List<Cliente> getFornecedores() {
		List<Cliente> fornecedores = new ArrayList<>();
		try {
			fornecedores = ClienteBO.getInstance().listFornecedores();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return fornecedores;
	}

	public String gerarPDFCartao() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<Cliente> clientes = new ArrayList<Cliente>();

		clientes.add(cliente);

		mapa.put("FRENTE", BuscaNoWebContent.buscaArquivo("/resources/imagens/fundo.png"));

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, clientes, "parceiro", "Cartão - " + cliente.getNome(),
				true);
		return null;
	}
	
	public String gerarPDFEtiqueta() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<Cliente> clientes = new ArrayList<Cliente>();
		String nomeArquivo = "";
		
		
		clientes.add(cliente);

		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logoglamix.jpg"));
		mapa.put("delivery", BuscaNoWebContent.buscaArquivo("/resources/imagens/delivery.jpg"));

		if(cliente.getTipoEscursao() == TipoEscursao.EXCURSAO) {
			nomeArquivo = "etiqueta-cliente";
		}else if(cliente.getTipoEscursao() == TipoEscursao.DELIVERY || cliente.getTipoEscursao() == TipoEscursao.TRANSPORTADORA) {
			nomeArquivo = "etiqueta-cliente-delivery";
		}
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, clientes, nomeArquivo, "Etiqueta - " + cliente.getNome(),
				true);
		return null;
	}
	
	public String update() {
		try {
			ClienteBO.getInstance().update(cliente);
			addInfoMessage("Editado com sucesso!", "Cliente " + cliente.getNome());
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
		cliente = new Cliente();
		AfterRedirect.manterMensagem();
		return "/private/lista/listarClientes.xhtml?faces-redirect=true";
	}
	
	
	
	

	public List<Cliente> getClienteAutoComplete(String clienteName) {
		List<Cliente> clientesComplete = new ArrayList<Cliente>();
		try {
			clientesComplete = ClienteBO.getInstance().clientesComplete(clienteName);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return clientesComplete;
	}

	public List<Cliente> getFornecedorAutoComplete(String fornecedorName) {

		List<Cliente> clientesComplete = new ArrayList<Cliente>();

		try {
			clientesComplete = ClienteBO.getInstance().fornecedoresComplete(fornecedorName);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return clientesComplete;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Cliente getClienteFilter() {
		return clienteFilter;
	}

	public void setClienteFilter(Cliente clienteFilter) {
		this.clienteFilter = clienteFilter;
	}

	public LazyEntityDataModel<Cliente> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Cliente> lazy) {
		this.lazy = lazy;
	}

}
