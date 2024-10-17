package br.com.controlpro.constants;

public enum GrupoItem {
	TECIDO("Tecido"),
	AVIAMENTO("Aviamento"),
	OUTROS("Outros"),
	MALHA("Malha")
	;
	
	GrupoItem(String descricao){
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
