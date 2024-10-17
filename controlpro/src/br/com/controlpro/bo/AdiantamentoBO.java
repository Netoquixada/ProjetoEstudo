package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.AdiantamentoDao;
import br.com.controlpro.entity.Adiantamento;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class AdiantamentoBO {

	public static AdiantamentoBO getInstance() {
			return new AdiantamentoBO();
	}

	public void save(Adiantamento Adiantamento) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Adiantamento);
		GenericDao.save(Adiantamento);
	}
	
	public void delete(Adiantamento adiantamento) throws IntegridadeObjetoHibernateException, ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
			GenericDao.delete(adiantamento);
	}

	public void update(Adiantamento Adiantamento) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Adiantamento);
	}

	public Adiantamento find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Adiantamento.class, id);
	}


	public List<Adiantamento> adiantamentosComplete(String AdiantamentoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AdiantamentoDao.getInstance().adiantamentos(AdiantamentoName);
	}

	public List<Adiantamento> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AdiantamentoDao.getInstance().adiantamentos();
	}
	
	public List<Adiantamento> adiantamentosPendentesPorFaccao(Faccao faccao) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return AdiantamentoDao.getInstance().adiantamentosPendentesPorFaccao(faccao);
	}

	public StringBuilder dadosFiltro() {
		return AdiantamentoDao.getInstance().getDadosFiltro();
	}

	public List<Adiantamento> adiantamentoListReport(Adiantamento adiantamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AdiantamentoDao.getInstance().adiantamentoListReport(adiantamento);
	}

	public List<Adiantamento> adiantamentoListReportPagamentos(Adiantamento adiantamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AdiantamentoDao.getInstance().adiantamentoListReportPagamentos(adiantamento);
	}

	public void validation(Adiantamento adiantamento) {

	}

}