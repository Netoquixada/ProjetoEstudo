package br.com.controlpro.constants;

public enum TipoMovimentacao {

	ENTRADA("Entrada"),
	SAIDA("Saída"),
	;
	
	TipoMovimentacao(String descricao){
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
