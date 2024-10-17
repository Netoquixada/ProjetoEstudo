package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.CargoDao;
import br.com.controlpro.entity.Cargo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class CargoBO {

	public static CargoBO getInstance() {
			return new CargoBO();
	}

	public void save(Cargo Cargo) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Cargo);
		GenericDao.save(Cargo);
	}

	public void update(Cargo Cargo) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Cargo);
	}

	public Cargo find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Cargo.class, id);
	}

	public LazyEntityDataModel<Cargo> cargosLazy(Cargo Cargo) {
		return CargoDao.getInstance().cargosLazy(Cargo);
	}

	public List<Cargo> cargosComplete(String CargoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CargoDao.getInstance().cargos(CargoName);
	}

	public List<Cargo> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CargoDao.getInstance().cargos();
	}

	public StringBuilder dadosFiltro() {
		return CargoDao.getInstance().getDadosFiltro();
	}

	public List<Cargo> cargoListReport(Cargo cargo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CargoDao.getInstance().cargoListReport(cargo);
	}

	public void validation(Cargo cargo) {

	}

}