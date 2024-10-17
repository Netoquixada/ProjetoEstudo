package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.EstoqueDao;
import br.com.controlpro.entity.consultas.Estoque;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class EstoqueBO {

	public static EstoqueBO getInstance() {
			return new EstoqueBO();
	}

	public void save(Estoque Estoque) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Estoque);
		GenericDao.save(Estoque);
	}

	public void update(Estoque Estoque) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Estoque);
	}

	public Estoque find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Estoque.class, id);
	}

	public List<Estoque> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return EstoqueDao.getInstance().estoques();
	}

	public StringBuilder dadosFiltro() {
		return EstoqueDao.getInstance().getDadosFiltro();
	}

	public List<Estoque> estoqueListReport(Estoque estoque)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return EstoqueDao.getInstance().estoqueListReport(estoque);
	}

	public void validation(Estoque estoque) {

	}

}