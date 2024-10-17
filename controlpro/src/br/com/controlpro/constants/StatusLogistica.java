package br.com.controlpro.constants;

public enum StatusLogistica {

	SEPARADO("Separado"),
	EMBALADO("Embalado"),
	ENVIADO("Enviado"),
	ENTREGUE("Entregue"),
	RETORNO("Retorno"),
	;
	
	StatusLogistica(String descricao){
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
