package br.com.controlpro.acesso;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.controlpro.bo.LoginBO;
import br.com.controlpro.bo.UsuarioBO;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.util.CriptografiaMD5;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.RecuperaMensagemComFlash;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -8351262285681938218L;

	private String login;
	private String senha;

	/**
	 * M�todo usado para validar o usu�rio no momento do login
	 * 
	 * @return String
	 */
	public String loginSistema() {

		try {
			invalidaSessaoAtiva();

			if(UsuarioBO.getInstance().list().isEmpty()){
				UsuarioBO.getInstance().criarPrimeiroUsuario();	
			}
			
			Usuario usuario = LoginBO.getInstance().login(getLogin(),
					CriptografiaMD5.cifrar(getSenha()));

			if (usuario == null) {
				addErrorMessage("Erro!", "Usuário não encontrado!");
				return "/public/login";
			}
			if (usuario.getStatus() == false) {
				addErrorMessage("Erro!",
						"Usuário inativo, contate o administrador do sistema!");
				return "/public/login";
			}

			ManagedBeanHelper.recuperaBean("escopoSessaoBean",
					EscopoSessaoBean.class).setUsuarioLogado(usuario);
			addInfoMessage("Bem vindo! " + usuario.getNome(), "");
			RecuperaMensagemComFlash.flashScope().setKeepMessages(true);
			if (usuario.getPrimeiroAcesso() == true) {
				return "/private/mudarSenha.xhtml?faces-redirect=true";
			}
			// Após o login é redirecionado para a pagina configurada aqui a baixp
			return "/private/index.xhtml?faces-redirect=true";

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro", e.getMessage());
			return "/public/login.xhtml";
		} catch (NumeroAtributosDiferenteNumeroValoresException e) {
			addErrorMessage("Erro", e.getMessage());
			e.printStackTrace();
			return "/public/login.xhtml";
		} catch (NoSuchAlgorithmException e) {
			addErrorMessage("Erro", e.getMessage());
			e.printStackTrace();
			return "/public/login.xhtml";
		} catch (UnsupportedEncodingException e) {
			addErrorMessage("Erro", e.getMessage());
			e.printStackTrace();
			return "/public/login.xhtml";
		}
	}

	public String logout() {

		ManagedBeanHelper.destroiBean("escopoSessaoBean",
				EscopoSessaoBean.class);

		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false)).invalidate();

		RecuperaMensagemComFlash.flashScope().setKeepMessages(true);
		addInfoMessage("Você acabou de sair do Sistema!", "");
		return "/public/login.xhtml?faces-redirect=true";
	}

	private void invalidaSessaoAtiva() {
		if (((HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false)) != null) {
			((HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false)).invalidate();
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}