package br.com.controlpro.constants;

public enum StatusConferencia {

	PENDENTE("Pendente"),CANCELADO("Cancelado"),CONFIRMADO("Confirmado");

	StatusConferencia(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
