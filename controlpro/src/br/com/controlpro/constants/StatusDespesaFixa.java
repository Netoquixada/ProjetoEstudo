package br.com.controlpro.constants;

public enum StatusDespesaFixa {

	ATIVO("Ativo"),FINALIZADO("Finalizado");

	StatusDespesaFixa(String descricao) {
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
