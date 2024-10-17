package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.consultas.Cliente;

@Entity
@Table(name = "CONTROL_ENTRADA_MATERIA_PRIMA")
public class EntradaMateriaPrima extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();
	
	private String nota;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Cliente fornecedor;
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	@OneToMany(mappedBy = "entradaMateriaPrima",fetch=FetchType.LAZY)
	private List<ItemEntradaMateriaPrima> itensEntradaMateriaPrima;
	
	@Transient
	private BigDecimal valorTotal = new BigDecimal(0);
	
	@Transient
	private Date dataInicio;

	@Transient
	private Date dataFim;
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Cliente getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Cliente fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<ItemEntradaMateriaPrima> getItensEntradaMateriaPrima() {
		return itensEntradaMateriaPrima;
	}

	public void setItensEntradaMateriaPrima(List<ItemEntradaMateriaPrima> itensEntradaMateriaPrima) {
		this.itensEntradaMateriaPrima = itensEntradaMateriaPrima;
	}
	
	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
