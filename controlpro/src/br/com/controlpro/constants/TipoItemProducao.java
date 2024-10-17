package br.com.controlpro.constants;

public enum TipoItemProducao {

	SHORT("Short"),
	SAIA("Saia"),
	TOP("Top"),
	BLUSA("Blusa"),
	KIMONO("Kimono"),
	VESTIDO("Vestido"),
	CROPPED("Cropped"),
	CALCA("Calça"),
	OUTRO("Outro"),
	BODY("Body"),
	BLAZER("Blazer"),
	MACAQUINHAO("Macaquinho")
	;
	
	TipoItemProducao(String descricao){
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
