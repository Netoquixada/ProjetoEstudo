package br.com.controlpro.constants;

public enum SituacaoConta {

	QUITADA("Quitada"),
	NAO_QUITADA("Não Quitada"),
	;
	
	SituacaoConta(String descricao){
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
