package br.com.controlpro.constants;

public enum PerfilUser {

	ADMINISTRADOR("Administrador"),
	OPERADOR("Operador"),
	CORTADOR("Cortador"), 
	PRODUCAO("Producao"), 
	VENDAS("Vendedor"),
	GERENCIA("Gerencia");

	PerfilUser(String descricao) {
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
