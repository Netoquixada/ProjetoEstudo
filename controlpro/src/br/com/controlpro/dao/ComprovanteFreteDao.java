package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ComprovanteFrete;
import br.com.controlpro.entity.ControlePedido;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ComprovanteFreteDao {

	private ComprovanteFreteDao() {
	}
	
	public ComprovanteFrete findById(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ComprovanteFrete.class, id);
	}

	public static ComprovanteFreteDao getInstance() {
		return new ComprovanteFreteDao();
	}

	public List<ComprovanteFrete> comprovanteFretePorControlePedido(ControlePedido controle)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (controle != null) {
			restrictions.add(Restrictions.eq("controlePedido", controle));
		}

		return GenericDao.findAllByAttributesRestrictions(ComprovanteFrete.class,
				null, true, restrictions);
	}
	
	public byte[] buscarArquivo(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		byte[] arquivoData = null;
		arquivoData = findById(id).getArquivo();
		return arquivoData;
	}
	
	public List<ComprovanteFrete> comprovantes() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ComprovanteFrete.class, null, true, restricions);
	}
}
