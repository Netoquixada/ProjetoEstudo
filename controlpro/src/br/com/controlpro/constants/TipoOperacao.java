package br.com.controlpro.constants;

public enum TipoOperacao {

	IND("IND"),
	VND("VND"),
	MA("MA"),
	INI("INI"),
	MM("MM");
	
	private String descricao;
	
	TipoOperacao(String descricao){
		this.descricao = descricao;
	}

	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
