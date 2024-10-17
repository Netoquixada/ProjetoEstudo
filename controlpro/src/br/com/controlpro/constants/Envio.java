package br.com.controlpro.constants;

public enum Envio {

	CORREIOS("Correios"),
	AVIAO("Avião"),
	TRANSPORTADORA("Transportadora"),
	OUTROS("Outros"),
	EXCURSAO("Excursão"),
	MOTOBOY("Motoboy")
	;
	
	Envio(String descricao){
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
