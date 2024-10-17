package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.constants.GrupoItem;
import br.com.controlpro.dao.MateriaPrimaDao;
import br.com.controlpro.entity.MateriaPrima;
import br.com.controlpro.entity.SubGrupo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class MateriaPrimaBO {

	public static MateriaPrimaBO getInstance() {
		return new MateriaPrimaBO();
	}

	public void save(MateriaPrima MateriaPrima)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(MateriaPrima);
		GenericDao.save(MateriaPrima);
	}

	public void update(MateriaPrima MateriaPrima)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(MateriaPrima);
	}

	public MateriaPrima find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(MateriaPrima.class, id);
	}

	public LazyEntityDataModel<MateriaPrima> materiaPrimasLazy(MateriaPrima MateriaPrima) {
		return MateriaPrimaDao.getInstance().materiaPrimasLazy(MateriaPrima);
	}

	public List<MateriaPrima> materiaPrimasComplete(String MateriaPrimaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MateriaPrimaDao.getInstance().materiaPrimas(MateriaPrimaName);
	}

	public List<MateriaPrima> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MateriaPrimaDao.getInstance().materiaPrimas();
	}
	
	public List<MateriaPrima> materiaPrimaPorGrupo(GrupoItem grupo, SubGrupo subGrupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MateriaPrimaDao.getInstance().materiaPrimasPorGrupo(grupo,subGrupo);
	}
	
	public List<MateriaPrima> listAviamento()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MateriaPrimaDao.getInstance().materiaPrimasAviamento();
	}

	public List<MateriaPrima> listMalha()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MateriaPrimaDao.getInstance().materiaPrimasMalha();
	}

	public StringBuilder dadosFiltro() {
		return MateriaPrimaDao.getInstance().getDadosFiltro();
	}

	public List<MateriaPrima> materiaPrimaListReport(MateriaPrima materiaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MateriaPrimaDao.getInstance().materiaPrimaListReport(materiaPrima);
	}

	public void validation(MateriaPrima materiaPrima) {

	}

}