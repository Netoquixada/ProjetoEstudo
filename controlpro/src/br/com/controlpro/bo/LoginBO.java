package br.com.controlpro.bo;

import br.com.controlpro.entity.Usuario;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class LoginBO {


	/**
	 * M�todo que implementa o padr�o de projeto 'singleton', este � respons�vel
	 * por facilitar o acesso a uma �nica inst�ncia da classe LoginBO, este
	 * m�todo garante tamb�m um ponto global de acesso � uma inst�ncia dessa
	 * classe, que ser� obtida atrav�s da chamada desse m�todo.
	 * 
	 * @return AtendiomentoBO
	 */
	public static LoginBO getInstance() {
		return new LoginBO();
	}

	/**
	 * Construtor padr�o com modificador de acesso 'private', para que seja n�o
	 * permitida a cria��o de uma inst�ncia dessa classe.
	 * */
	private LoginBO() {

	}

	public Usuario login(String login, String senha)
			throws NumeroAtributosDiferenteNumeroValoresException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return (Usuario) GenericDao.findByNamedQueryWithParameters(
				Usuario.class, "findByLoginAndSenha", "login", login, "senha",
				senha);

	}
}
