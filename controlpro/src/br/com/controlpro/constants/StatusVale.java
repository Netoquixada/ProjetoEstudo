package br.com.controlpro.constants;

public enum StatusVale {

	PAGO("Pago"), ABERTO("Aberto");

	StatusVale(String descricao) {
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
