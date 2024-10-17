package br.com.controlpro.conveter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.Funcionario;


@FacesConverter(forClass=Funcionario.class, value="funcionarioConverter")
public class FuncionarioConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.isEmpty())
			return null;
		return arg1.getAttributes().get(string);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		Funcionario funcionario = (Funcionario) object;
		if (funcionario == null || funcionario.getNome() == null)
			return null;
		arg1.getAttributes().put(String.valueOf(funcionario.getNome()), funcionario);
		return String.valueOf(funcionario.getNome());
	}
	
}


















