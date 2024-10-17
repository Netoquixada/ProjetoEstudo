package br.com.controlpro.acesso;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.controlpro.entity.Usuario;



/**
 * Classe usada para retornar o usuário que está logado no sistema
 * 
 * @author ANDREY GOIS - GETSOFTWARE
 * @version 1.0
 */

@ManagedBean
@SessionScoped
public class EscopoSessaoBean implements Serializable {

	private static final long serialVersionUID = -1361342408456177486L;

	@ManagedProperty(value = "#{usuarioLogado}")
	private Usuario usuarioLogado;

	/**
	 * Retorna um Usuario
	 * 
	 * @return Usuario
	 */
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	/**
	 * Seta um Usuario
	 * 
	 * @param Usuario
	 */
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}