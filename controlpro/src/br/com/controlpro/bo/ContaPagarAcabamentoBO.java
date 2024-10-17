package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ContaPagarAcabamentoDao;
import br.com.controlpro.entity.ContaPagarAcabamento;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ContaPagarAcabamentoBO {

	public static ContaPagarAcabamentoBO getInstance() {
		return new ContaPagarAcabamentoBO();
	}

	public void save(ContaPagarAcabamento contaPagar)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(contaPagar);
		GenericDao.save(contaPagar);
	}

	public void update(ContaPagarAcabamento contaPagar)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(contaPagar);
	}

	public ContaPagarAcabamento find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ContaPagarAcabamento.class, id);
	}

	public List<ContaPagarAcabamento> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ContaPagarAcabamentoDao.getInstance().contaPagarAcabamentoList();
	}

	public StringBuilder dadosFiltro() {
		return ContaPagarAcabamentoDao.getInstance().getDadosFiltro();
	}

	public List<ContaPagarAcabamento> contaPagarAcabamentoListReport(ContaPagarAcabamento contaPagar)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ContaPagarAcabamentoDao.getInstance().contaPagarAcabamentoListReport(
				contaPagar);
	}

	public void validation(ContaPagarAcabamento ordemCorte) {

	}

}