package br.com.controlpro.constants;

public enum TipoControleInterno {

	ESTORNO("Estorno"),
	CUPOM("Cupon");
	
	TipoControleInterno(String descricao){
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
