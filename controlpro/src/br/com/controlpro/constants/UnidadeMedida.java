package br.com.controlpro.constants;

public enum UnidadeMedida {

	Kg("Quilograma(s)"),
	M("Metro(s)"),
	Un("Unidade")
	;
	
	UnidadeMedida(String descricao){
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
