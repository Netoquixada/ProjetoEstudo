package br.com.controlpro.constants;

public enum RelatoControleInterno {

	DESISTENCIA("Desistência"),
	DEFEITO("Defeito")
	;
	
	RelatoControleInterno(String descricao){
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
