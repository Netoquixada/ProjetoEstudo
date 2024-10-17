package br.com.controlpro.conveter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.controlpro.entity.consultas.Cliente;




@FacesConverter(forClass=Cliente.class, value="fornecedorConverter")
public class FornecedorConverter implements Converter{


	@Override
	public Object getAsObject(FacesContext ctx, UIComponent componente,
			String value) {
		if (value != null) {
			return componente.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent componente,
			Object value) {

		if (value != null) {
			Cliente entity = (Cliente) value;

			if (entity.getId() != null) {
				this.addAttribute(componente, entity);

				if (entity.getId() != null) {
					return String.valueOf(entity.getId());
				}
				return (String) value;
			}
		}
		return "";
	}

	private void addAttribute(UIComponent componente, Cliente o) {
		this.getAttributesFrom(componente).put(o.getId().toString(), o);
	}

	private Map<String, Object> getAttributesFrom(UIComponent componente) {
		return componente.getAttributes();
	}
}
