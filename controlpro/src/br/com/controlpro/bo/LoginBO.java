package br.com.controlpro.bo;

import br.com.controlpro.entity.Usuario;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.NumeroAtributosDiferenteNumeroValoresException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class LoginBO {


	/**
	 * Método que implementa o padrão de projeto 'singleton', este é responsável
	 * por facilitar o acesso a uma única instância da classe LoginBO, este
	 * método garante também um ponto global de acesso à uma instância dessa
	 * classe, que será obtida através da chamada desse método.
	 * 
	 * @return AtendiomentoBO
	 */
	public static LoginBO getInstance() {
		return new LoginBO();
	}

	/**
	 * Construtor padrão com modificador de acesso 'private', para que seja não
	 * permitida a criação de uma instância dessa classe.
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
