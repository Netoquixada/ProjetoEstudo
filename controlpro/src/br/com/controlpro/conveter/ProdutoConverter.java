package br.com.controlpro.conveter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.Produto;


@FacesConverter(forClass=Produto.class, value="produtoConverter")
public class ProdutoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.isEmpty())
			return null;
		return arg1.getAttributes().get(string);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		Produto produto = (Produto) object;
		if (produto == null || produto.getNome() == null)
			return null;
		arg1.getAttributes().put(String.valueOf(produto.getNome()), produto);
		return String.valueOf(produto.getNome());
	}
	
}


















