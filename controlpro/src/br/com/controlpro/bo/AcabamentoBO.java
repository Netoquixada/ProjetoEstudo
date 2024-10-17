package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.AcabamentoDao;
import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class AcabamentoBO {

	public static AcabamentoBO getInstance() {
		return new AcabamentoBO();
	}

	public void save(Acabamento acabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(acabamento);
		GenericDao.save(acabamento);
	}

	public void update(Acabamento acabamento)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(acabamento);
	}

	public Acabamento find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Acabamento.class, id);
	}

	public List<Acabamento> acabamentoComplete(String acabamentoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AcabamentoDao.getInstance().acabamentos(acabamentoName);
	}

	public List<Acabamento> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AcabamentoDao.getInstance().acabamentos();
	}

	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return AcabamentoDao.getInstance().geradorDeProtocolo();
	}

	public StringBuilder dadosFiltro() {
		return AcabamentoDao.getInstance().getDadosFiltro();
	}

	public List<Acabamento> acabamentoListReport(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AcabamentoDao.getInstance().acabamentoListReport(
				acabamento);
	}

	public void validation(Acabamento ordemCorte) {

	}
	

}