package br.com.controlpro.constants;

public enum OrigemOcorrencia {

	CORTE("Corte"),
	MODELAGEM("Modelagem"),
	PRODUCAO("Produção"),
	;
	
	OrigemOcorrencia(String descricao){
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
