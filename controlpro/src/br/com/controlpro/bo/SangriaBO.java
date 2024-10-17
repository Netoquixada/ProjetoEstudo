package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.SangriaDao;
import br.com.controlpro.entity.Sangria;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class SangriaBO {

	public static SangriaBO getInstance() {
		return new SangriaBO();
	}

	public void save(Sangria Sangria) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(Sangria);
		GenericDao.save(Sangria);
	}

	public void update(Sangria Sangria) throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(Sangria);
	}

	public Sangria find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Sangria.class, id);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException,
			NumberFormatException {
		return SangriaDao.getInstance().geradorDeProtocolo();
	}

	public List<Sangria> sangriasComplete(String SangriaName) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SangriaDao.getInstance().sangrias(SangriaName);
	}

	public List<Sangria> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SangriaDao.getInstance().sangrias();
	}

	public StringBuilder dadosFiltro() {
		return SangriaDao.getInstance().getDadosFiltro();
	}

	public List<Sangria> sangriaListReport(Sangria sangria) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return SangriaDao.getInstance().sangriaListReport(sangria);
	}

	public void validation(Sangria sangria) {

	}

}