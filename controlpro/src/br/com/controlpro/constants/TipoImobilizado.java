package br.com.controlpro.constants;

public enum TipoImobilizado {

	MAQUINA("Maquina"), 
	FERRAMENTA("Ferramenta"),
	VEICULO("Veiculo"),
	INFORMATICA("Informática"),
	SEGURANCA("Segurança"),
	OUTROS("Outros");

	TipoImobilizado(String descricao) {
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
