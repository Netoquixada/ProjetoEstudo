package br.com.controlpro.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Vendas;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class VendasDao {

	public static VendasDao getInstance() {
		return new VendasDao();
	}

	public LazyEntityDataModel<Vendas> vendasLazy(Vendas vendas) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (vendas.getVendedor() != null) {
			restrictions.add(Restrictions.eq("vendedor", vendas.getVendedor()));
		}
		if (vendas.getCliente() != null) {
			restrictions.add(Restrictions.eq("cliente", vendas.getCliente()));
		}
		if (vendas.getDataVendaInicio() != null && vendas.getDataVendaFim() != null) {
			restrictions.add(Restrictions.between("dataConfirmacao", vendas.getDataVendaInicio(), vendas.getDataVendaFim()));
		}

		if (vendas.getDataVendaInicio() != null && vendas.getDataVendaFim() == null) {
			vendas.setDataVendaFim(vendas.getDataVendaInicio());
			restrictions.add(Restrictions.between("dataConfirmacao", vendas.getDataVendaInicio(), vendas.getDataVendaFim()));
		}

		if (vendas.getDataVendaInicio() == null && vendas.getDataVendaFim() != null) {
			vendas.setDataVendaInicio(vendas.getDataVendaFim());
			restrictions.add(Restrictions.between("dataConfirmacao", vendas.getDataVendaInicio(), vendas.getDataVendaFim()));
		}
		return new LazyEntityDataModel<Vendas>(Vendas.class, null, null, restrictions, null);

	}

	public List<Vendas> vendas(Vendas vendas) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (vendas.getDataVendaInicio() != null
				&& vendas.getDataVendaFim() != null) {
			restrictions.add(Restrictions.between("dataConfirmacao",
					vendas.getDataVendaInicio(),
					vendas.getDataVendaFim()));
		}

		if (vendas.getDataVendaInicio() != null
				&& vendas.getDataVendaFim() == null) {
			vendas.setDataVendaFim(vendas.getDataVendaInicio());
			restrictions.add(Restrictions.between("dataConfirmacao",
					vendas.getDataVendaInicio(),
					vendas.getDataVendaFim()));
		}

		if (vendas.getDataVendaInicio() == null
				&& vendas.getDataVendaFim() != null) {
			vendas.setDataVendaInicio(vendas.getDataVendaFim());
			restrictions.add(Restrictions.between("dataConfirmacao",
					vendas.getDataVendaInicio(),
					vendas.getDataVendaFim()));
		}
		
		if (vendas.getCodOperacao() != 0L) {
			restrictions.add(Restrictions.eq("codOperacao", vendas.getCodOperacao()));
		}
		
		if (vendas.getSequencia() != null && !vendas.getSequencia().isEmpty()) {
			restrictions.add(Restrictions.like("sequencia", vendas.getSequencia(), MatchMode.ANYWHERE));
		}
		
		if (vendas.getVendedor() != null) {
			restrictions.add(Restrictions.eq("vendedor", vendas.getVendedor()));
		}
		if (vendas.getCliente() != null) {
			restrictions.add(Restrictions.eq("cliente", vendas.getCliente()));
		}
		return GenericDao.findAllByAttributesRestrictions(Vendas.class, "dataConfirmacao", true, restrictions);
	}
	
	
	public List<Vendas> vendas(String vendas) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Vendas.class, null, true, restrictions);
	}

	public List<Vendas> vendas() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Vendas.class, "dataConfirmacao", true, restricions);
	}

	public List<Vendas> vendasPorCodigoCliente(Integer id) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("cliente.id", id));
		return GenericDao.findAllByAttributesRestrictions(Vendas.class, "dataConfirmacao", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}
	
	public List<Vendas> vendasPorSequencia(String sequencia) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("sequencia", sequencia));
		return GenericDao.findAllByAttributesRestrictions(Vendas.class, null, true, restricions);
	}

	public BigDecimal totalVendasPorCliente(Integer codigo) {
		BigDecimal result = new BigDecimal(0.0);
		try {
			// System.out.println("select sum(Total) from Saidas where Cliente =
			// " + codigo);
			// System.out.println("select sum(Recebe_Dinheiro +
			// Recebe_Cartao_Credito + Recebe_Cartao_Debito +
			// Recebe_Banco_Valor) from Saidas where Cliente" + codigo);
			result = GenericDao.findEntityNotMappedBySQLQuery(BigDecimal.class, Vendas.class,
					"select sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) from Saidas where Cliente = "
							+ codigo);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return new BigDecimal(0.0);
		return result;
	}

}
