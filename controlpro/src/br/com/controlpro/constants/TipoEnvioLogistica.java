package br.com.controlpro.constants;

public enum TipoEnvioLogistica {

	RETIRADA("Retirada"),
	CORREIOS("Correios"),
	EXCURSAO("Excursão"),
	DELIVERY("Delivery")
	;
	
	TipoEnvioLogistica(String descricao){
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
