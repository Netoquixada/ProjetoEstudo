package br.com.controlpro.constants;

public enum OpcoesPagamento {

	CARTAO("Cartão"),
	CHEQUE("Cheque"),
	DEPOSITO("Deposito"),
	TRANSFERENCIA("Transferência"),
	DINHEIRO("Dinheiro"),
	;
	
	OpcoesPagamento(String descricao){
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
