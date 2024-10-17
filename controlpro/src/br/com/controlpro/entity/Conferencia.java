package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.StatusConferencia;
import br.com.controlpro.constants.Tipo;
import br.com.controlpro.entity.consultas.Banco;
import br.com.controlpro.entity.consultas.Funcionario;


@Entity
@Table(name = "CONTROL_CONFERENCIA")
public class Conferencia extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	@ManyToOne
	private Banco banco;
	
	@ManyToOne
	private Funcionario vendedor;
	
	private String pedido;
	
	private String cliente;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor = new BigDecimal(0);
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_cadastro")
	private Date dataCadastro = new Date();
	
	@Enumerated(EnumType.STRING)
	private Tipo tipo;

	@Enumerated(EnumType.STRING)
	private StatusConferencia statusConferencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_status")
	private Date dataStatus;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte [] arquivo;
	
	private String extensao;
	
	@Column(name="observacao", columnDefinition="text")
	private String observacao;
	
	@Transient
	private Date dataInicio;

	@Transient
	private Date dataFim;
	
	
	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Date getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Date dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	public StatusConferencia getStatusConferencia() {
		return statusConferencia;
	}
	
	public void setStatusConferencia(StatusConferencia statusConferencia) {
		this.statusConferencia = statusConferencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public byte[] getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}