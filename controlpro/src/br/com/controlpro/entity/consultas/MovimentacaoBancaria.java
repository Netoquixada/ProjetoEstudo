package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MovimentacaoBancaria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public MovimentacaoBancaria() {
		
	}
	
	public MovimentacaoBancaria(Date data, String descricao, String docTipo, String origem, BigDecimal debito
			, BigDecimal credito, Boolean consolidado, String gerouConta) {
		this.data = data;
		this.descricao = descricao;
		this.docTipo = docTipo;
		this.origem = origem;
		this.debito = debito;
		this.credito = credito;
		this.consolidado = consolidado;
		this.gerouConta = gerouConta;
	}

	private Date data;
	private String descricao;
	private String docTipo;
	private String origem;
	private BigDecimal debito;
	private BigDecimal credito;
	private Boolean consolidado;
	private String gerouConta;
	
	private Date dataInicioFilter = new Date();
	private Date dataFimFilter = new Date();

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDocTipo() {
		return docTipo;
	}

	public void setDocTipo(String docTipo) {
		this.docTipo = docTipo;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public BigDecimal getDebito() {
		return debito;
	}

	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}

	public BigDecimal getCredito() {
		return credito;
	}

	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}

	public Date getDataInicioFilter() {
		return dataInicioFilter;
	}

	public void setDataInicioFilter(Date dataInicioFilter) {
		this.dataInicioFilter = dataInicioFilter;
	}

	public Date getDataFimFilter() {
		return dataFimFilter;
	}

	public void setDataFimFilter(Date dataFimFilter) {
		this.dataFimFilter = dataFimFilter;
	}

	public Boolean getConsolidado() {
		return consolidado;
	}

	public void setConsolidado(Boolean consolidado) {
		this.consolidado = consolidado;
	}

	public String getGerouConta() {
		return gerouConta;
	}

	public void setGerouConta(String gerouConta) {
		this.gerouConta = gerouConta;
	}
	
}