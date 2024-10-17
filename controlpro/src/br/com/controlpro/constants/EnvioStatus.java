package br.com.controlpro.constants;

public enum EnvioStatus {
	EMPACOTAMENTO("Empacotamento"),
	ENVIADO("Enviado"),
	ESTRAVIADO("Estraviado"),
	ENTREGUE("Entregue"),
	;
	
	EnvioStatus(String descricao){
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
