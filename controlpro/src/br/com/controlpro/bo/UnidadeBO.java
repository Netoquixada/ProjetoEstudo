package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.UnidadeDao;
import br.com.controlpro.entity.Unidade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class UnidadeBO {

	public static UnidadeBO getInstance() {
			return new UnidadeBO();
	}

	public void save(Unidade Unidade) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Unidade);
		GenericDao.save(Unidade);
	}

	public void update(Unidade Unidade) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Unidade);
	}

	public Unidade find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Unidade.class, id);
	}

	public LazyEntityDataModel<Unidade> unidadesLazy(Unidade Unidade) {
		return UnidadeDao.getInstance().unidadesLazy(Unidade);
	}

	public List<Unidade> unidadesComplete(String UnidadeName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UnidadeDao.getInstance().unidades(UnidadeName);
	}

	public List<Unidade> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UnidadeDao.getInstance().unidades();
	}

	public StringBuilder dadosFiltro() {
		return UnidadeDao.getInstance().getDadosFiltro();
	}

	public List<Unidade> unidadeListReport(Unidade unidade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UnidadeDao.getInstance().unidadeListReport(unidade);
	}

	public void validation(Unidade unidade) {

	}

}