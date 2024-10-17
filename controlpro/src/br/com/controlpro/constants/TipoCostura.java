package br.com.controlpro.constants;

public enum TipoCostura {

	COSTURA("Costura"),
	CASEAR("Casear"),
	APLIQUE("Aplique"),
	BOTAO("Botao"),
	OUTROS("Outros"),
	;
	
	TipoCostura(String descricao){
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
