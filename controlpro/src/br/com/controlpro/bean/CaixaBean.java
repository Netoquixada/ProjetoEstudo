package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.CaixaBO;
import br.com.controlpro.constants.TipoOperacao;
import br.com.controlpro.entity.consultas.Caixa;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class CaixaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Caixa caixas;
	private Caixa caixasFilter;
	private Caixa caixasTotais;
	private List<Caixa> listaCaixas;
//	private LazyEntityDataModel<Caixa> lazy;

	@PostConstruct
	public void init() {
		caixas = new Caixa();
		caixasFilter = new Caixa();
		caixasTotais = new Caixa();
		listaCaixas = new ArrayList<Caixa>();
//		lazy = new LazyEntityDataModel<Caixa>(Caixa.class);
	}

//	public String list() {
//		lazy = CaixaBO.getInstance().caixasLazy(caixasFilter);
//		return null;
//	}
	public String list() {
		try {
			listaCaixas = CaixaBO.getInstance().list(caixasFilter);
			caixasTotais = new Caixa();
			for (int i = 0; i < listaCaixas.size(); i++) {
				caixasTotais.setCartoesCredito(caixasTotais.getCartoesCredito() + listaCaixas.get(i).getCartoesCredito());
				caixasTotais.setCartoesDebito(caixasTotais.getCartoesDebito() + listaCaixas.get(i).getCartoesDebito());
				caixasTotais.setDinheiro(caixasTotais.getDinheiro() + listaCaixas.get(i).getDinheiro());
				caixasTotais.setOutros(caixasTotais.getOutros() + listaCaixas.get(i).getOutros());
				caixasTotais.setTotal(caixasTotais.getTotal() + listaCaixas.get(i).getTotal());
			}
			
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Caixa getCaixa() {
		return caixas;
	}
	
	public TipoOperacao[] getTipoOperacoes(){
		return TipoOperacao.values();
	}

	public void setCaixa(Caixa caixas) {
		this.caixas = caixas;
	}

	public List<Caixa> getListaCaixas() {
		return listaCaixas;
	}


	public void setListaCaixas(List<Caixa> listaCaixa) {
		this.listaCaixas = listaCaixa;
	}

	public Caixa getCaixaFilter() {
		return caixasFilter;
	}

	public void setCaixaFilter(Caixa caixasFilter) {
		this.caixasFilter = caixasFilter;
	}

//	public LazyEntityDataModel<Caixa> getLazy() {
//		return lazy;
//	}
//
//	public void setLazy(LazyEntityDataModel<Caixa> lazy) {
//		this.lazy = lazy;
//	}
	
	public Caixa getCaixasTotais() {
		return caixasTotais;
	}
	
	public void setCaixasTotais(Caixa caixasTotais) {
		this.caixasTotais = caixasTotais;
	}

}
