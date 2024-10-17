package br.com.controlpro.conveter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.AdmCartao;


@FacesConverter(forClass=AdmCartao.class, value="admCartaoConverter")
public class AdmCartaoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.isEmpty())
			return null;
		return arg1.getAttributes().get(string);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		AdmCartao admCartao = (AdmCartao) object;
		if (admCartao == null || admCartao.getNome() == null)
			return null;
		arg1.getAttributes().put(String.valueOf(admCartao.getNome()), admCartao);
		return String.valueOf(admCartao.getNome());
	}
	
}


















