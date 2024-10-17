package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Conferencia;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ConferenciaDao {

	public static ConferenciaDao getInstance() {
		return new ConferenciaDao();
	}
	
	public Conferencia findById(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Conferencia.class, id);
	}

	public LazyEntityDataModel<Conferencia> conferenciasLazy(Conferencia conferencia) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (conferencia.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", conferencia.getLoja()));

		}
		if (conferencia.getStatusConferencia() != null) {
			restrictions.add(Restrictions.eq("statusConferencia", conferencia.getStatusConferencia()));

		}
		if (conferencia.getVendedor() != null) {
			restrictions.add(Restrictions.eq("vendedor", conferencia.getVendedor()));
		}
		if (conferencia.getDataInicio() != null && conferencia.getDataFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() == null) {
			conferencia.setDataFim(conferencia.getDataInicio());
			restrictions.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() == null && conferencia.getDataFim() != null) {
			conferencia.setDataInicio(conferencia.getDataFim());
			restrictions.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}
		return new LazyEntityDataModel<Conferencia>(Conferencia.class, null, null,
				restrictions, null);
	}

	public List<Conferencia> conferencias(String conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
			
		return GenericDao.findAllByAttributesRestrictions(Conferencia.class,
				"dataCadastro", true, restrictions);
	}

	public List<Conferencia> conferencias()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Conferencia.class,
				"dataCadastro", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Conferencia> conferenciaListReport(Conferencia conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (conferencia.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", conferencia.getLoja()));
//			dadosFiltro.append("NOME: " + conferencia.getLoja());
		}
		if (conferencia.getLoja() != null) {
			restrictions.add(Restrictions.eq("statusConferencia", conferencia.getStatusConferencia()));

		}
		if (conferencia.getVendedor() != null) {
			restrictions.add(Restrictions.eq("vendedor", conferencia.getVendedor()));
		}
		if (conferencia.getDataInicio() != null && conferencia.getDataFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() == null) {
			conferencia.setDataFim(conferencia.getDataInicio());
			restrictions.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() == null && conferencia.getDataFim() != null) {
			conferencia.setDataInicio(conferencia.getDataFim());
			restrictions.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(Conferencia.class,
				"dataCadastro", true, restrictions);
	}
	
	public byte[] buscarArquivo(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		byte[] arquivoData = null;
		arquivoData = findById(id).getArquivo();
		return arquivoData;
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
