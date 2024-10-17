package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.MetaDao;
import br.com.controlpro.entity.Meta;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class MetaBO {

	public static MetaBO getInstance() {
		return new MetaBO();
	}

	public void save(Meta meta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(meta);
		GenericDao.save(meta);
	}

	public void update(Meta meta)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(meta);
	}

	public Meta find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Meta.class, id);
	}

	public List<Meta> metaComplete(String metaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MetaDao.getInstance().metas(metaName);
	}

	public List<Meta> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MetaDao.getInstance().metas();
	}

	public StringBuilder dadosFiltro() {
		return MetaDao.getInstance().getDadosFiltro();
	}

	public List<Meta> metaListReport(Meta meta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MetaDao.getInstance().metaListReport(
				meta);
	}

	public void validation(Meta ordemCorte) {

	}
	

}