package br.com.controlpro.constants;

public enum TipoFolhaPagamento {

	MENSAL("Mensal"),
	QUINZENA1("Quinzena 1"),
	QUINZENA2("Quinzena 2"),
	;
	
	TipoFolhaPagamento(String descricao){
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
