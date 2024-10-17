package br.com.controlpro.conveter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.ContaBancaria;


@FacesConverter(forClass=ContaBancaria.class, value="contaConverter")
public class ContaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.isEmpty())
			return null;
		return arg1.getAttributes().get(string);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		ContaBancaria contaBancaria = (ContaBancaria) object;
		if (contaBancaria == null || contaBancaria.getNome() == null)
			return null;
		arg1.getAttributes().put(String.valueOf(contaBancaria.getNome()), contaBancaria);
		return String.valueOf(contaBancaria.getNome());
	}
	
}


















