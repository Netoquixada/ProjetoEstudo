package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.ControlePedido;
import br.com.controlpro.entity.HistoricoControlePedido;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControlePedidoDao {

	public static ControlePedidoDao getInstance() {
		return new ControlePedidoDao();
	}

	public ControlePedido findById(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ControlePedido.class, id);
	}

	public LazyEntityDataModel<ControlePedido> controlePedidoLazy(ControlePedido conferencia) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (conferencia.getDespachante() != null) {
			restrictions.add(Restrictions.eq("despachante", conferencia.getDespachante()));
		}

		if (conferencia.getRastreio() != null && !conferencia.getRastreio().isEmpty()) {
			restrictions.add(Restrictions.eq("rastreio", conferencia.getRastreio()));

		}

		if (conferencia.getEnvioStatus() != null && !conferencia.getEnvioStatus().isEmpty()) {
			restrictions.add(Restrictions.eq("envioStatus", conferencia.getEnvioStatus()));

		}

		if (conferencia.getEnvio() != null && !conferencia.getEnvio().isEmpty()) {
			restrictions.add(Restrictions.eq("envio", conferencia.getEnvio()));

		}
		// if (conferencia.getVendedor() != null) {
		// restrictions.add(Restrictions.eq("vendedor",
		// conferencia.getVendedor()));
		// }
		if (conferencia.getDataInicio() != null && conferencia.getDataFim() != null) {
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() == null) {
			conferencia.setDataFim(conferencia.getDataInicio());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() == null && conferencia.getDataFim() != null) {
			conferencia.setDataInicio(conferencia.getDataFim());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}
		return new LazyEntityDataModel<ControlePedido>(ControlePedido.class, null, null, restrictions, null);
	}

	public List<ControlePedido> controlesPedidos(String conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(ControlePedido.class, "dataCadastro", true, restrictions);
	}

	public List<ControlePedido> controlesPedidos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(ControlePedido.class, "dataCadastro", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ControlePedido> controlePedidoListReport(ControlePedido conferencia,boolean glamix)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (conferencia.getDespachante() != null) {
			restrictions.add(Restrictions.eq("despachante", conferencia.getDespachante()));
		}

		if (conferencia.getRastreio() != null && !conferencia.getRastreio().isEmpty()) {
			restrictions.add(Restrictions.eq("rastreio", conferencia.getRastreio()));
		}
		if (conferencia.getNumeroPedido() != null && !conferencia.getNumeroPedido().isEmpty()) {
			restrictions.add(Restrictions.eq("numeroPedido", conferencia.getNumeroPedido()));
		}
		
		if (conferencia.getSequenciaVenda() != null && !conferencia.getSequenciaVenda().isEmpty()) {
			restrictions.add(Restrictions.like("sequenciaVenda", conferencia.getSequenciaVenda(), MatchMode.ANYWHERE));
		}else if(!glamix) {
			restrictions.add(Restrictions.ne("sequenciaVenda",""));
		}else {
			restrictions.add(Restrictions.eq("sequenciaVenda",""));
		}

		if (conferencia.getEnvioStatus() != null && !conferencia.getEnvioStatus().isEmpty()) {
			restrictions.add(Restrictions.eq("envioStatus", conferencia.getEnvioStatus()));
		}
		
		

		if (conferencia.getEnvio() != null && !conferencia.getEnvio().isEmpty()) {
			restrictions.add(Restrictions.eq("envio", conferencia.getEnvio()));

		}
		
		if(conferencia.getStatusOcorrencia() != 0) {
			restrictions.add(Restrictions.eq("statusOcorrencia", conferencia.getStatusOcorrencia()));
		}
		// if (conferencia.getVendedor() != null) {
		// restrictions.add(Restrictions.eq("vendedor",
		// conferencia.getVendedor()));
		// }
		if (conferencia.getDataInicio() != null && conferencia.getDataFim() != null) {
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() == null) {
			conferencia.setDataFim(conferencia.getDataInicio());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() == null && conferencia.getDataFim() != null) {
			conferencia.setDataInicio(conferencia.getDataFim());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(ControlePedido.class, "dataCadastro", false, restrictions);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<HistoricoControlePedido> list = GenericDao.getListaByDemanda(HistoricoControlePedido.class,
				0, 1, null, null, "id", SortOrder.DESCENDING, null, null);
		Integer num = null;
		if (list != null && !list.isEmpty()) {
			num = Integer.valueOf(list.get(0).getId() + 1);
			return num.toString();
		}
		return "1";
	}
	public String geradorDeProtocoloGlamix()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		
		List<ControlePedido> list = GenericDao.getListaByDemanda(ControlePedido.class,
				0, 1, null, null, "id", SortOrder.DESCENDING, null, null);
		Integer num = null;
		if (list != null && !list.isEmpty()) {
			num = Integer.valueOf(list.get(0).getId() + 1);
			return num.toString();
		}
		return "1";
	}
	
	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
