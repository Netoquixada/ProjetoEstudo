package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.ClienteBO;
import br.com.controlpro.bo.ResgateBO;
import br.com.controlpro.bo.VendasBO;
import br.com.controlpro.entity.Resgate;
import br.com.controlpro.entity.consultas.Cliente;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.ValorResgateExcedidoException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class ResgateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Integer codigo;
	private Resgate resgate;
	private Cliente cliente;
	private List<Resgate> resgates;

	@PostConstruct
	public void init() {
		resgate = new Resgate();
		cliente = new Cliente();
		resgates = new ArrayList<Resgate>();
	}

	public void consultarPorCodigo() {
		
		try {
			cliente = ClienteBO.getInstance().find(codigo);
			
			resgate.setTotalVendas(VendasBO.getInstance().totalVendasPorCodigoCliente(cliente.getId()));
			resgate.setTotalResgate(ResgateBO.getInstance().totalResgatePorCodigoCliente(cliente.getId()));
			resgate.setSaldo(resgate.getTotalVendas().subtract(resgate.getTotalResgate()));
			
			resgates = ResgateBO.getInstance().resgatePorCliente(cliente);
			
			addInfoMessage("Cliente encontrado!","");
			
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addInfoMessage("Erro! " + e.getMessage(),"");
			limparObjetos();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro! " + e.getMessage(),"");
			limparObjetos();
		} catch (NullPointerException e){
			addErrorMessage("Cliente não encontrado!","");
			limparObjetos();
		}
	}
	
	public void save(){
		
		try {
			resgate.setCliente(cliente);
			ResgateBO.getInstance().save(resgate);
			addInfoMessage("Resgate salvo com sucesso!","");
			limparObjetos();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		} catch (ValorResgateExcedidoException e) {
			addErrorMessage(e.getMessage(),"");
		}
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Resgate getResgate() {
		return resgate;
	}

	public void setResgate(Resgate resgate) {
		this.resgate = resgate;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Resgate> getResgates() {
		return resgates;
	}
	
	public void setResgates(List<Resgate> resgates) {
		this.resgates = resgates;
	}
	
	private void limparObjetos(){
		codigo = null;
		cliente = new Cliente();
		resgate = new Resgate();
		resgates = new ArrayList<Resgate>();
	}
	
	
}
