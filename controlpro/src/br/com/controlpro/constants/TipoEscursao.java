package br.com.controlpro.constants;

public enum TipoEscursao {

	EXCURSAO("Excursão"),
	DELIVERY("Delivery"),
	TRANSPORTADORA("Transportadora"),
	;
	
	TipoEscursao(String descricao){
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
