package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.CaixaDao;
import br.com.controlpro.entity.consultas.Caixa;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class CaixaBO {

	public static CaixaBO getInstance() {
			return new CaixaBO();
	}

	public Caixa find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Caixa.class, id);
	}

	public LazyEntityDataModel<Caixa> caixasLazy(Caixa Caixa) {
		return CaixaDao.getInstance().caixasLazy(Caixa);
	}

	public List<Caixa> caixasComplete(String CaixaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CaixaDao.getInstance().caixas(CaixaName);
	}


	public List<Caixa> list(Caixa caixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CaixaDao.getInstance().caixas(caixa);
	}
	
	public List<Caixa> listDinheiro(Caixa caixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CaixaDao.getInstance().caixasDinheiro(caixa);
	}

	public StringBuilder dadosFiltro() {
		return CaixaDao.getInstance().getDadosFiltro();
	}

	public void validation(Caixa caixas) {

	}

}