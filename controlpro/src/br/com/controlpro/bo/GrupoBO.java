package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.GrupoDao;
import br.com.controlpro.entity.Grupo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class GrupoBO {

	public static GrupoBO getInstance() {
			return new GrupoBO();
	}

	public void save(Grupo Grupo) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Grupo);
		GenericDao.save(Grupo);
	}

	public void update(Grupo Grupo) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Grupo);
	}

	public Grupo find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Grupo.class, id);
	}

	public LazyEntityDataModel<Grupo> gruposLazy(Grupo Grupo) {
		return GrupoDao.getInstance().gruposLazy(Grupo);
	}

	public List<Grupo> gruposComplete(String GrupoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GrupoDao.getInstance().grupos(GrupoName);
	}

	public List<Grupo> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GrupoDao.getInstance().grupos();
	}

	public StringBuilder dadosFiltro() {
		return GrupoDao.getInstance().getDadosFiltro();
	}

	public List<Grupo> grupoListReport(Grupo grupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GrupoDao.getInstance().grupoListReport(grupo);
	}

	public void validation(Grupo grupo) {

	}

}