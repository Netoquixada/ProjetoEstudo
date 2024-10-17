package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.ResumoContaBO;
import br.com.controlpro.bo.VendasBO;
import br.com.controlpro.entity.consultas.ResumoContaTotal;
import br.com.controlpro.entity.consultas.Vendas;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@ViewScoped
public class VendasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Vendas vendas;
	private Vendas vendasFilter;
	private List<Vendas> listaVendass;
	private Vendas vendasTotais;
	private LazyEntityDataModel<Vendas> lazy;
	private List<ResumoContaTotal> resumoContas;

	@PostConstruct
	public void init() {
		vendas = new Vendas();
		vendasFilter = new Vendas();
		listaVendass = new ArrayList<Vendas>();
		vendasTotais = new Vendas();
		lazy = new LazyEntityDataModel<Vendas>(Vendas.class);
		resumoContas = new ArrayList<>();
	}

	public String list() {

		try {
			listaVendass = VendasBO.getInstance().list(vendasFilter);
			vendasTotais = new Vendas();
			for (int i = 0; i < listaVendass.size(); i++) {
				if (listaVendass.get(i).getEfetivadaFinanceiro() == 1 && listaVendass.get(i).getDesfeitaFinanceiro() != 1) {
					vendasTotais.setCartoesCredito(
							vendasTotais.getCartoesCredito() + listaVendass.get(i).getCartoesCredito());
					vendasTotais
							.setCartoesDebito(vendasTotais.getCartoesDebito() + listaVendass.get(i).getCartoesDebito());
					vendasTotais.setDinheiro(vendasTotais.getDinheiro() + listaVendass.get(i).getDinheiro());
					vendasTotais.setTroco(vendasTotais.getTroco() + listaVendass.get(i).getTroco());
					vendasTotais.setBancoValor(vendasTotais.getBancoValor() + listaVendass.get(i).getBancoValor());
					vendasTotais.setTotal(vendasTotais.getTotal() + listaVendass.get(i).getTotal());
					vendasTotais.setRecebeCreditoCliente(vendasTotais.getRecebeCreditoCliente() + listaVendass.get(i).getRecebeCreditoCliente());
				}
			}
			resumoContas = ResumoContaBO.getInstance().resumoCartaoTotal(vendasFilter);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;

		// lazy = VendasBO.getInstance().vendasLazy(vendasFilter);
		// return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, listaVendass, "vendas",
				"Relatório de vendas - " + DataUtil.formatarData(new Date()), true);
	}
	
	public String listDashboard() {

		try {
			listaVendass = VendasBO.getInstance().list(vendasFilter);
			vendasTotais = new Vendas();
			for (int i = 0; i < listaVendass.size(); i++) {
				if (listaVendass.get(i).getEfetivadaFinanceiro() == 1 && listaVendass.get(i).getDesfeitaFinanceiro() != 1) {
					vendasTotais.setCartoesCredito(
							vendasTotais.getCartoesCredito() + listaVendass.get(i).getCartoesCredito());
					vendasTotais
							.setCartoesDebito(vendasTotais.getCartoesDebito() + listaVendass.get(i).getCartoesDebito());
					vendasTotais.setDinheiro(vendasTotais.getDinheiro() + listaVendass.get(i).getDinheiro());
					vendasTotais.setTroco(vendasTotais.getTroco() + listaVendass.get(i).getTroco());
					vendasTotais.setBancoValor(vendasTotais.getBancoValor() + listaVendass.get(i).getBancoValor());
					vendasTotais.setTotal(vendasTotais.getTotal() + listaVendass.get(i).getTotal());
				}
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;

		// lazy = VendasBO.getInstance().vendasLazy(vendasFilter);
		// return null;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public List<Vendas> getListaVendass() {
		// try {
		// listaVendass = VendasBO.getInstance().list(vendasFilter);
		// } catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
		// e.printStackTrace();
		// }
		return listaVendass;
	}

	public Vendas getVendasTotais() {
		return vendasTotais;
	}

	public void setVendasTotais(Vendas vendasTotais) {
		this.vendasTotais = vendasTotais;
	}

	public void setListaVendass(List<Vendas> listaVendas) {
		this.listaVendass = listaVendas;
	}

	public Vendas getVendasFilter() {
		return vendasFilter;
	}

	public void setVendasFilter(Vendas vendasFilter) {
		this.vendasFilter = vendasFilter;
	}

	public LazyEntityDataModel<Vendas> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Vendas> lazy) {
		this.lazy = lazy;
	}
	
	public List<ResumoContaTotal> getResumoContas() {
		return resumoContas;
	}
	
	public void setResumoContas(List<ResumoContaTotal> resumoContas) {
		this.resumoContas = resumoContas;
	}

}
