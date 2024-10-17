package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import groovy.util.ObjectGraphBuilder.DefaultNewInstanceResolver;

@Entity
@Table(name = "CONTROL_ITEM_ENFESTO")
public class ItemEnfestoCorte extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "materia_prima")
	private MateriaPrima materiaPrima;

	@Column(name = "comp", scale = 2, precision = 9)
	private BigDecimal comprimento = new BigDecimal(0);

	@Column(name = "lar", scale = 2, precision = 9)
	private BigDecimal largura = new BigDecimal(0);

	@Column(name = "folha", scale = 2, precision = 9)
	private Integer folha = 0;
	
	@Column(name = "qtd_por_folha", scale = 2, precision = 9)
	private Integer qtdPorFolha = 0;


	@Column(name = "metro_folha", scale = 2, precision = 9)
	private BigDecimal metroFolha = new BigDecimal(0);

	@Column(name = "total", scale = 2, precision = 9)
	private BigDecimal total = new BigDecimal(0);
	
	@Column(name = "usado_por_item", scale = 2, precision = 9)
	private BigDecimal usadoPorItem = new BigDecimal(0);
	
	@Column(name = "peca_cortada", scale = 2, precision = 9)
	private BigDecimal pecaCortada = new BigDecimal(0);
	
	@Transient
	private Boolean debitarSaldo = false;

	@ManyToOne
	@JoinColumn(name = "ordem_corte_id")
	private OrdemCorte ordemCorte;

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public BigDecimal getComprimento() {
		return comprimento;
	}

	public void setComprimento(BigDecimal comprimento) {
		this.comprimento = comprimento;
	}

	public BigDecimal getLargura() {
		return largura;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public Integer getFolha() {
		return folha;
	}

	public void setFolha(Integer folha) {
		this.folha = folha;
	}

	public BigDecimal getTotal() {
		total = getPecaCortada().multiply(getUsadoPorItem());
		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getUsadoPorItem() {
		if(usadoPorItem == null) {
			usadoPorItem = new BigDecimal(0);
		}
		usadoPorItem = getComprimento().divide(new BigDecimal(getQtdPorFolha()), 2, RoundingMode.HALF_UP);
		usadoPorItem = usadoPorItem.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return usadoPorItem;
	}

	public BigDecimal getPecaCortada() {
		if(pecaCortada == null) {
			pecaCortada = new BigDecimal(0);
		}
		pecaCortada = new BigDecimal(getFolha() * getQtdPorFolha());
		pecaCortada = pecaCortada.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return pecaCortada;
	}

	public void setPecaCortada(BigDecimal pecaCortada) {
		this.pecaCortada = pecaCortada;
	}

	public void setUsadoPorItem(BigDecimal usadoPorItem) {
		this.usadoPorItem = usadoPorItem;
	}

	public OrdemCorte getOrdemCorte() {
		return ordemCorte;
	}

	public void setOrdemCorte(OrdemCorte ordemCorte) {
		this.ordemCorte = ordemCorte;
	}

	public BigDecimal getMetroFolha() {
		metroFolha = comprimento.multiply(largura);
		metroFolha = metroFolha.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return metroFolha;
	}

	public void setMetroFolha(BigDecimal metroFolha) {
		this.metroFolha = metroFolha;
	}
	
	public Integer getQtdPorFolha() {
		if(qtdPorFolha == null) {
			qtdPorFolha = 0;
		}
		return qtdPorFolha;
	}
	
	public void setQtdPorFolha(Integer qtdPorFolha) {
		this.qtdPorFolha = qtdPorFolha;
	}
	
	public Boolean getDebitarSaldo() {
		if(debitarSaldo == null) {
			debitarSaldo = false;
		}
		return debitarSaldo;
	}
	
	public void setDebitarSaldo(Boolean debitarSaldo) {
		this.debitarSaldo = debitarSaldo;
	}

}
