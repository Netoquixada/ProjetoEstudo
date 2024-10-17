package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ValeDao;
import br.com.controlpro.entity.Vale;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ValeBO {

	public static ValeBO getInstance() {
			return new ValeBO();
	}

	public void save(Vale Vale) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Vale);
		GenericDao.save(Vale);
	}
	
	public void delete(Vale vale) throws IntegridadeObjetoHibernateException, ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
			GenericDao.delete(vale);
	}

	public void update(Vale Vale) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Vale);
	}

	public Vale find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Vale.class, id);
	}

	public LazyEntityDataModel<Vale> valesLazy(Vale Vale) {
		return ValeDao.getInstance().valesLazy(Vale);
	}

	public List<Vale> valesComplete(String ValeName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ValeDao.getInstance().vales(ValeName);
	}

	public List<Vale> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ValeDao.getInstance().vales();
	}

	public StringBuilder dadosFiltro() {
		return ValeDao.getInstance().getDadosFiltro();
	}

	public List<Vale> valeListReport(Vale vale)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ValeDao.getInstance().valeListReport(vale);
	}

	public List<Vale> valeListReportPagamentos(Vale vale)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ValeDao.getInstance().valeListReportPagamentos(vale);
	}

	public void validation(Vale vale) {

	}

}