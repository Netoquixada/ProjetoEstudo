package br.com.controlpro.conveter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.Banco;


@FacesConverter(forClass=Banco.class, value="bancoConverter")
public class BancoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.isEmpty())
			return null;
		return arg1.getAttributes().get(string);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		Banco banco = (Banco) object;
		if (banco == null || banco.getNome() == null)
			return null;
		arg1.getAttributes().put(String.valueOf(banco.getNome()), banco);
		return String.valueOf(banco.getNome());
	}
	
}


















