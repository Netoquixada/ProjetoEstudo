package br.com.controlpro.conveter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.Cliente;


@FacesConverter(forClass=Cliente.class, value="clienteConverter")
public class ClienteConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.isEmpty())
			return null;
		return arg1.getAttributes().get(string);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		Cliente Cliente = (Cliente) object;
		if (Cliente == null || Cliente.getNome() == null)
			return null;
		arg1.getAttributes().put(String.valueOf(Cliente.getNome()), Cliente);
		return String.valueOf(Cliente.getNome());
	}
	
}


















