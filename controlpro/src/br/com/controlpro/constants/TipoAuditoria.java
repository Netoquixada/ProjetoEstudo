package br.com.controlpro.constants;

public enum TipoAuditoria {

	INSERINDO("Inserindo"),
	ATUALIZANDO("Atualizando"),
	DELETANDO("Deletando"),
	;
	
	TipoAuditoria(String descricao){
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
