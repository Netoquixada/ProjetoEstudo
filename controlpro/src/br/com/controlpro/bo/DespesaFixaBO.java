package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.DespesaFixaDao;
import br.com.controlpro.entity.DespesaFixa;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class DespesaFixaBO {

	public static DespesaFixaBO getInstance() {
		return new DespesaFixaBO();
	}

	public void save(DespesaFixa despesaFixa)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(despesaFixa);
		GenericDao.save(despesaFixa);
	}

	public void update(DespesaFixa despesaFixa)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(despesaFixa);
	}

	public DespesaFixa find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(DespesaFixa.class, id);
	}

	public LazyEntityDataModel<DespesaFixa> despesaFixaLazy(
			DespesaFixa despesaFixa) {
		return DespesaFixaDao.getInstance().despesaFixasLazy(despesaFixa);
	}

	public List<DespesaFixa> despesaFixaComplete(String despesaFixaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return DespesaFixaDao.getInstance().despesaFixas(despesaFixaName);
	}

	public List<DespesaFixa> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return DespesaFixaDao.getInstance().despesaFixas();
	}

	public StringBuilder dadosFiltro() {
		return DespesaFixaDao.getInstance().getDadosFiltro();
	}

	public List<DespesaFixa> despesaFixaListReport(DespesaFixa despesaFixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return DespesaFixaDao.getInstance().despesaFixaListReport(
				despesaFixa);
	}

	public void validation(DespesaFixa ordemCorte) {

	}
	
	public void validarQuantidadeEnvio()
			throws QuantidadeEnvioInvalidaException {

	}
}