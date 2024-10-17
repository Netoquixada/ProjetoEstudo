package br.com.controlpro.constants;

public enum Tipo {

	DEPOSITO("Deposito"), TRANSFERENCIA("Transferência");

	Tipo(String descricao) {
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
