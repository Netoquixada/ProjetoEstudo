package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ConferenciaDao;
import br.com.controlpro.entity.Conferencia;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ConferenciaBO {

	public static ConferenciaBO getInstance() {
			return new ConferenciaBO();
	}

	public void save(Conferencia Conferencia) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Conferencia);
		GenericDao.save(Conferencia);
	}

	public void update(Conferencia Conferencia) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Conferencia);
	}

	public Conferencia find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Conferencia.class, id);
	}

	public LazyEntityDataModel<Conferencia> conferenciasLazy(Conferencia Conferencia) {
		return ConferenciaDao.getInstance().conferenciasLazy(Conferencia);
	}

	public List<Conferencia> conferenciasComplete(String ConferenciaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ConferenciaDao.getInstance().conferencias(ConferenciaName);
	}

	public List<Conferencia> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ConferenciaDao.getInstance().conferencias();
	}

	public StringBuilder dadosFiltro() {
		return ConferenciaDao.getInstance().getDadosFiltro();
	}

	public List<Conferencia> conferenciaListReport(Conferencia conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ConferenciaDao.getInstance().conferenciaListReport(conferencia);
	}

	public void validation(Conferencia conferencia) {

	}
	
	public byte[] buscarArquivo(Integer id) throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ConferenciaDao.getInstance().buscarArquivo(id);
	}

}