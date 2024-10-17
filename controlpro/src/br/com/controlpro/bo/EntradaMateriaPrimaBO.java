package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.EntradaMateriaPrimaDao;
import br.com.controlpro.entity.EntradaMateriaPrima;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class EntradaMateriaPrimaBO {

	public static EntradaMateriaPrimaBO getInstance() {
		return new EntradaMateriaPrimaBO();
	}

	public void save(EntradaMateriaPrima EntradaMateriaPrima)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(EntradaMateriaPrima);
		GenericDao.save(EntradaMateriaPrima);
	}

	public void update(EntradaMateriaPrima EntradaMateriaPrima)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(EntradaMateriaPrima);
	}

	public EntradaMateriaPrima find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(EntradaMateriaPrima.class, id);
	}

	public LazyEntityDataModel<EntradaMateriaPrima> entradaMateriaPrimaLazy(EntradaMateriaPrima EntradaMateriaPrima) {
		return EntradaMateriaPrimaDao.getInstance().entradaMateriaPrimasLazy(EntradaMateriaPrima);
	}

	public List<EntradaMateriaPrima> entradaMateriaPrimaComplete(String EntradaMateriaPrimaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return EntradaMateriaPrimaDao.getInstance().entradaMateriaPrimas(EntradaMateriaPrimaName);
	}

	public List<EntradaMateriaPrima> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return EntradaMateriaPrimaDao.getInstance().entradaMateriaPrimas();
	}

	public StringBuilder dadosFiltro() {
		return EntradaMateriaPrimaDao.getInstance().getDadosFiltro();
	}

	public List<EntradaMateriaPrima> entradaMateriaPrimasListReport(EntradaMateriaPrima entradaMateriaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return EntradaMateriaPrimaDao.getInstance().entradaMateriaPrimasListReport(entradaMateriaPrima);
	}

	public void validation(EntradaMateriaPrima ordemCorte) {

	}

}