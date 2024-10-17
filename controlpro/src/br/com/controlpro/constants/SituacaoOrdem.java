package br.com.controlpro.constants;

public enum SituacaoOrdem {

	ANDAMENTO("Andamento"), FINALIZADA("Finalizada"), CANCELADA("Cancelada");

	SituacaoOrdem(String descricao) {
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
