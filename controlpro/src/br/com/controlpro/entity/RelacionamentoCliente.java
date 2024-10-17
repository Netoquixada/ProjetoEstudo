package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RelacionamentoCliente extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;
	
	public RelacionamentoCliente() {
		
	}
	
	public RelacionamentoCliente(Integer codigo, String nome, String cidade, String bairro, String endereco, String telefone,
			BigDecimal valor, BigDecimal resgatado,BigDecimal saldo, Date ultimaCompra) {
		this.codigo = codigo;
		this.nome = nome;
		this.cidade = cidade;
		this.bairro = bairro;
		this.endereco = endereco;
		this.telefone = telefone;
		this.valor = valor;
		this.resgatado = resgatado;
		this.saldo = saldo;
		this.ultimaCompra = ultimaCompra;
		
	}
	
	private Integer codigo;
	private String nome;
	private String cidade;
	private String bairro;
	private String endereco;
	private String telefone;
	private BigDecimal valor;
	private BigDecimal resgatado;
	private BigDecimal saldo;
	private Date ultimaCompra;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getResgatado() {
		return resgatado;
	}

	public void setResgatado(BigDecimal resgatado) {
		this.resgatado = resgatado;
	}

	public Date getUltimaCompra() {
		return ultimaCompra;
	}

	public void setUltimaCompra(Date ultimaCompra) {
		this.ultimaCompra = ultimaCompra;
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}
	
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}