package br.com.controlpro.constants;

public enum OrigemAcabamento {

	EXTERNO("Externo"),
	INTERNO("Interno"),
	;
	
	OrigemAcabamento(String descricao){
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
