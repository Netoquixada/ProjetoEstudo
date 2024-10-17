package br.com.controlpro.constants;

public enum StatusAcabamento {

	PAGO("Pago"), ABERTO("Aberto");

	StatusAcabamento(String descricao) {
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
