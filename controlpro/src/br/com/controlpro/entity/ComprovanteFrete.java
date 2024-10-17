package br.com.controlpro.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTROL_COMPROVANTE_FRETE")
public class ComprovanteFrete extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte [] arquivo;
	
	private String nome;

	private String extensao;
	
	@ManyToOne
	@JoinColumn(name = "controle_pedido")
	private ControlePedido controlePedido;

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

	public ControlePedido getControlePedido() {
		return controlePedido;
	}

	public void setControlePedido(ControlePedido controlePedido) {
		this.controlePedido = controlePedido;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

}
