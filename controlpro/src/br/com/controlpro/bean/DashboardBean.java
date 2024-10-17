package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.GradeOrdemBO;
import br.com.controlpro.bo.HistoricoCorteBO;
import br.com.controlpro.bo.ItemProducaoBO;
import br.com.controlpro.entity.GradeOrdem;
import br.com.controlpro.entity.HistoricoCorte;
import br.com.controlpro.entity.ItemProducao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class DashboardBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	
	private Long cortadas = 0L;
	private Long produzido = 0L;
	private Long enviado = 0L;
	private Long aguardando = 0L;
	private Long percentualCorteProducao;

	private Long producaoEnviado = 0L;
	private Long producaoPronto = 0L;
	private Long producaoSaldo = 0L;
	private Long producaoPercentualPronto = 0L;
	private List<GradeOrdem> listaCorte;
	private List<ItemProducao> listaProducao;
	private List<HistoricoCorte> listaHistoricoCorte;
	
	
	
	
	@PostConstruct
	public void init() {
		listaCorte = new ArrayList<>();
		listaProducao = new ArrayList<>();
		listaHistoricoCorte = new ArrayList<>();
		
		atualizaValores();
	}

	
	public void atualizaValores(){
		try {
			listaCorte = GradeOrdemBO.getInstance().grades();
			

			listaHistoricoCorte = HistoricoCorteBO.getInstance().list();
			for (HistoricoCorte historico : listaHistoricoCorte) {
				cortadas = cortadas + historico.getQtdRecebido();
			}
			
			listaProducao = ItemProducaoBO.getInstance().itens();
			
			for (ItemProducao item : listaProducao) {
				produzido = produzido + item.getProntas();
				enviado = enviado + item.getQuantidade();
			}
			
			aguardando = cortadas - enviado;
			percentualCorteProducao = (produzido * 100) / cortadas;
			
			listaProducao = ItemProducaoBO.getInstance().itens();
			
			for (ItemProducao item : listaProducao) {
				producaoEnviado = producaoEnviado + item.getQuantidade();
				producaoPronto = producaoPronto + item.getProntas();
			}
			producaoSaldo = producaoEnviado - producaoPronto;
			producaoPercentualPronto = (producaoPronto * 100) / producaoEnviado;
			
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public Long getProducaoEnviado() {
		return producaoEnviado;
	}


	public void setProducaoEnviado(Long producaoEnviado) {
		this.producaoEnviado = producaoEnviado;
	}


	public Long getProducaoPronto() {
		return producaoPronto;
	}


	public void setProducaoPronto(Long producaoPronto) {
		this.producaoPronto = producaoPronto;
	}


	public Long getProducaoSaldo() {
		return producaoSaldo;
	}


	public void setProducaoSaldo(Long producaoSaldo) {
		this.producaoSaldo = producaoSaldo;
	}


	public Long getProducaoPercentualPronto() {
		return producaoPercentualPronto;
	}


	public void setProducaoPercentualPronto(Long producaoPercentualPronto) {
		this.producaoPercentualPronto = producaoPercentualPronto;
	}


	public List<GradeOrdem> getListaCorte() {
		return listaCorte;
	}


	public void setListaCorte(List<GradeOrdem> listaCorte) {
		this.listaCorte = listaCorte;
	}


	public List<ItemProducao> getListaProducao() {
		return listaProducao;
	}


	public void setListaProducao(List<ItemProducao> listaProducao) {
		this.listaProducao = listaProducao;
	}


	public Long getCortadas() {
		return cortadas;
	}


	public void setCortadas(Long cortadas) {
		this.cortadas = cortadas;
	}


	public Long getProduzido() {
		return produzido;
	}


	public void setProduzido(Long produzido) {
		this.produzido = produzido;
	}


	public Long getEnviado() {
		return enviado;
	}


	public void setEnviado(Long enviado) {
		this.enviado = enviado;
	}


	public Long getAguardando() {
		return aguardando;
	}


	public void setAguardando(Long aguardando) {
		this.aguardando = aguardando;
	}


	public List<HistoricoCorte> getListaHistoricoCorte() {
		return listaHistoricoCorte;
	}


	public void setListaHistoricoCorte(List<HistoricoCorte> listaHistoricoCorte) {
		this.listaHistoricoCorte = listaHistoricoCorte;
	}
	
	public Long getPercentualCorteProducao() {
		return percentualCorteProducao;
	}
	
	public void setPercentualCorteProducao(Long percentualCorteProducao) {
		this.percentualCorteProducao = percentualCorteProducao;
	}
	

}
