package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.SubGrupoDao;
import br.com.controlpro.entity.SubGrupo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class SubGrupoBO {

	public static SubGrupoBO getInstance() {
			return new SubGrupoBO();
	}

	public void save(SubGrupo SubGrupo) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(SubGrupo);
		GenericDao.save(SubGrupo);
	}

	public void update(SubGrupo SubGrupo) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(SubGrupo);
	}

	public SubGrupo find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(SubGrupo.class, id);
	}

	public LazyEntityDataModel<SubGrupo> subGruposLazy(SubGrupo SubGrupo) {
		return SubGrupoDao.getInstance().subGruposLazy(SubGrupo);
	}

	public List<SubGrupo> subGruposComplete(String SubGrupoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SubGrupoDao.getInstance().subGrupos(SubGrupoName);
	}

	public List<SubGrupo> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SubGrupoDao.getInstance().subGrupos();
	}

	public StringBuilder dadosFiltro() {
		return SubGrupoDao.getInstance().getDadosFiltro();
	}

	public List<SubGrupo> subGrupoListReport(SubGrupo subGrupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SubGrupoDao.getInstance().subGrupoListReport(subGrupo);
	}

	public void validation(SubGrupo subGrupo) {

	}

}