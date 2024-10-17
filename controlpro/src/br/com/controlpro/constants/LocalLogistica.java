package br.com.controlpro.constants;

public enum LocalLogistica {

	SITE("Site"),
	WHATZAPP("Whatzapp"),
	;
	
	LocalLogistica(String descricao){
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
