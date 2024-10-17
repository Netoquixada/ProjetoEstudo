package br.com.controlpro.constants;

public enum TipoMateriaPrima {

	MALHA("Malha"),
	PLANO("Plano"),
	FORRO("Forro"),
	OUTRO("Outro")
	;
	
	TipoMateriaPrima(String descricao){
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
