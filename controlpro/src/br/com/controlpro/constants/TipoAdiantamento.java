package br.com.controlpro.constants;

public enum TipoAdiantamento {

	PECAS("Peças"), SERVICOS("Serviços"), OUTRO("Outro"), DEFEITO("Defeito");

	
	TipoAdiantamento(String descricao) {
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
