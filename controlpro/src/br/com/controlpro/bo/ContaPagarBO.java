package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ContaPagarDao;
import br.com.controlpro.entity.ContaPagar;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ContaPagarBO {

	public static ContaPagarBO getInstance() {
		return new ContaPagarBO();
	}

	public void save(ContaPagar contaPagar)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(contaPagar);
		GenericDao.save(contaPagar);
	}

	public void update(ContaPagar contaPagar)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(contaPagar);
	}

	public ContaPagar find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ContaPagar.class, id);
	}

	public LazyEntityDataModel<ContaPagar> contaPagarLazy(
			ContaPagar contaPagar) {
		return ContaPagarDao.getInstance().contaPagarsLazy(contaPagar);
	}

	public List<ContaPagar> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ContaPagarDao.getInstance().contaPagars();
	}

	public StringBuilder dadosFiltro() {
		return ContaPagarDao.getInstance().getDadosFiltro();
	}

	public List<ContaPagar> contaPagarListReport(ContaPagar contaPagar)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ContaPagarDao.getInstance().contaPagarListReport(
				contaPagar);
	}

	public void validation(ContaPagar ordemCorte) {

	}

}