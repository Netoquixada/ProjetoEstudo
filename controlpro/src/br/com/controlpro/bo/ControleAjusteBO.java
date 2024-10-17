package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ControleAjusteDao;
import br.com.controlpro.entity.ControleAjuste;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControleAjusteBO {

	public static ControleAjusteBO getInstance() {
			return new ControleAjusteBO();
	}

	public void save(ControleAjuste ControleAjuste) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(ControleAjuste);
		GenericDao.save(ControleAjuste);
	}

	public void update(ControleAjuste ControleAjuste) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(ControleAjuste);
	}

	public ControleAjuste find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ControleAjuste.class, id);
	}

	public LazyEntityDataModel<ControleAjuste> ControleAjusteLazy(ControleAjuste ControleAjuste) {
		return ControleAjusteDao.getInstance().ControleAjusteLazy(ControleAjuste);
	}

	public List<ControleAjuste> ControleAjusteComplete(String ControleAjusteName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleAjusteDao.getInstance().controlesPedidos(ControleAjusteName);
	}
	
	public List<ControleAjuste> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleAjusteDao.getInstance().controlesPedidos();
	}

	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return ControleAjusteDao.getInstance().geradorDeProtocolo();
	}
	
	public StringBuilder dadosFiltro() {
		return ControleAjusteDao.getInstance().getDadosFiltro();
	}

	public List<ControleAjuste> ControleAjusteListReport(ControleAjuste conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleAjusteDao.getInstance().ControleAjusteListReport(conferencia);
	}

	public void validation(ControleAjuste conferencia) {

	}

}