package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.OrdemCorteDao;
import br.com.controlpro.entity.OrdemCorte;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class OrdemCorteBO {

	public static OrdemCorteBO getInstance() {
		return new OrdemCorteBO();
	}

	public void save(OrdemCorte OrdemCorte)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(OrdemCorte);
		GenericDao.save(OrdemCorte);
	}

	public void update(OrdemCorte OrdemCorte)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(OrdemCorte);
	}

	public OrdemCorte find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(OrdemCorte.class, id);
	}

	public LazyEntityDataModel<OrdemCorte> ordemCortesLazy(OrdemCorte OrdemCorte) {
		return OrdemCorteDao.getInstance().ordemCortesLazy(OrdemCorte);
	}

	public List<OrdemCorte> ordemCortesComplete(String OrdemCorteName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OrdemCorteDao.getInstance().ordemCortes(OrdemCorteName);
	}

	public List<OrdemCorte> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OrdemCorteDao.getInstance().ordemCortes();
	}

	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return OrdemCorteDao.getInstance().geradorDeProtocolo();
	}

	public StringBuilder dadosFiltro() {
		return OrdemCorteDao.getInstance().getDadosFiltro();
	}

	public List<OrdemCorte> ordemCorteListReport(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OrdemCorteDao.getInstance().ordemCorteListReport(ordemCorte);
	}

	public void validation(OrdemCorte ordemCorte) {

	}

}