package br.com.controlpro.constants;

public enum LocalControleInterno {

	SITE("Site"),
	WHATSAPP("Whatsapp")
	;
	
	LocalControleInterno(String descricao){
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
