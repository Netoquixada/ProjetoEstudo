package br.com.controlpro.constants;

public enum Loja {

	LOLLI("Lolli"),
	LOLLABEL("Lollabel"),
	LOLLI_II("Lolli II"),
	FORTELEZA("Fortaleza"),
	LOLLINHA("Lollinha"), 
	OUTRA("Outras"),
	GLAMIX("Glamix"),
	FABRICA("Fabrica");

	Loja(String descricao) {
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
