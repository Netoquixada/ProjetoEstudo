package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ItemProducao;
import br.com.controlpro.entity.ItemProducaoAud;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.util.AliasUece;

public class ItemProducaoDao {

	private ItemProducaoDao() {
	}

	public static ItemProducaoDao getInstance() {
		return new ItemProducaoDao();
	}

	public List<ItemProducao> itemProducaoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemProducao != null) {
			restrictions.add(Restrictions.eq("ordemProducao", ordemProducao));
		}
		restrictions.add(Restrictions.eq("status", true));
		restrictions.add(Restrictions.eq("itemReenviado", false));
		return GenericDao.findAllByAttributesRestrictions(ItemProducao.class,
				null, true, restrictions);
	}
	
	public List<ItemProducao> itens() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", false));
		return GenericDao.findAllByAttributesRestrictions(ItemProducao.class, null, true, restricions);
	}
	
	public List<ItemProducao> itensFilter(ItemProducao item)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		List<AliasUece> aliases = new ArrayList<AliasUece>();

		aliases.add(new AliasUece("ordemProducao", "ordemProducao"));
//		aliases.add(new AliasUece("ordemProducao.faccao", "faccao"));
		
		
		if(item.getProduto().getNome() != null && !item.getProduto().getNome().isEmpty()){
			restricions.add(Restrictions.eq("produto", item.getProduto()));
		}
		if(item.getOrdemProducao().getFaccao() != null){
			restricions.add(Restrictions.eq("ordemProducao.faccao", item.getOrdemProducao().getFaccao()));
		}
		if(item.getOrdemProducao().getProtocolo() != null && !item.getOrdemProducao().getProtocolo().isEmpty()){
			restricions.add(Restrictions.eq("ordemProducao.protocolo", item.getOrdemProducao().getProtocolo()));
		}
		
		if (item.getOrdemProducao().getDataCadastroInicio() != null && item.getOrdemProducao().getDataCadastroFim() != null) {
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}

		if (item.getOrdemProducao().getDataCadastroInicio() != null && item.getOrdemProducao().getDataCadastroFim() == null) {
			item.getOrdemProducao().setDataCadastroFim(item.getOrdemProducao().getDataCadastroInicio());
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}

		if (item.getOrdemProducao().getDataCadastroInicio() == null && item.getOrdemProducao().getDataCadastroFim() != null) {
			item.getOrdemProducao().setDataCadastroInicio(item.getOrdemProducao().getDataCadastroFim());
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}
		
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", false));
		return GenericDao.findAllByAttributesRestrictions(ItemProducao.class,
				null, false, restricions,aliases);
	}
	
	public List<ItemProducao> itemReenviadoProducaoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemProducao != null) {
			restrictions.add(Restrictions.eq("ordemProducao", ordemProducao));
		}
		restrictions.add(Restrictions.eq("itemReenviado", true));
		return GenericDao.findAllByAttributesRestrictions(ItemProducao.class,
				null, true, restrictions);
	}
	
	public List<ItemProducao> itensReenviado() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", true));
		return GenericDao.findAllByAttributesRestrictions(ItemProducao.class, null, true, restricions);
	}
	
	public List<ItemProducao> itensReenviadoFilter(ItemProducao item)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		List<AliasUece> aliases = new ArrayList<AliasUece>();

		aliases.add(new AliasUece("ordemProducao", "ordemProducao"));
//		aliases.add(new AliasUece("ordemProducao.faccao", "faccao"));
		
		
		if(item.getProduto().getNome() != null && !item.getProduto().getNome().isEmpty()){
			restricions.add(Restrictions.eq("produto", item.getProduto()));
		}
		if(item.getOrdemProducao().getFaccao() != null){
			restricions.add(Restrictions.eq("ordemProducao.faccao", item.getOrdemProducao().getFaccao()));
		}
		if(item.getOrdemProducao().getProtocolo() != null && !item.getOrdemProducao().getProtocolo().isEmpty()){
			restricions.add(Restrictions.eq("ordemProducao.protocolo", item.getOrdemProducao().getProtocolo()));
		}
		
		if (item.getOrdemProducao().getDataCadastroInicio() != null && item.getOrdemProducao().getDataCadastroFim() != null) {
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}

		if (item.getOrdemProducao().getDataCadastroInicio() != null && item.getOrdemProducao().getDataCadastroFim() == null) {
			item.getOrdemProducao().setDataCadastroFim(item.getOrdemProducao().getDataCadastroInicio());
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}

		if (item.getOrdemProducao().getDataCadastroInicio() == null && item.getOrdemProducao().getDataCadastroFim() != null) {
			item.getOrdemProducao().setDataCadastroInicio(item.getOrdemProducao().getDataCadastroFim());
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}
		
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", true));
		return GenericDao.findAllByAttributesRestrictions(ItemProducao.class,
				null, false, restricions,aliases);
	}
	
	
	public List<ItemProducaoAud> itensAudFilter(ItemProducaoAud item)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		List<AliasUece> aliases = new ArrayList<AliasUece>();

		aliases.add(new AliasUece("ordemProducao", "ordemProducao"));
//		aliases.add(new AliasUece("ordemProducao.faccao", "faccao"));
		
		
		if(item.getProduto().getNome() != null && !item.getProduto().getNome().isEmpty()){
			restricions.add(Restrictions.eq("produto", item.getProduto()));
		}
		if(item.getOrdemProducao().getFaccao() != null){
			restricions.add(Restrictions.eq("ordemProducao.faccao", item.getOrdemProducao().getFaccao()));
		}
		if(item.getOrdemProducao().getProtocolo() != null && !item.getOrdemProducao().getProtocolo().isEmpty()){
			restricions.add(Restrictions.eq("ordemProducao.protocolo", item.getOrdemProducao().getProtocolo()));
		}
		
		if (item.getOrdemProducao().getDataCadastroInicio() != null && item.getOrdemProducao().getDataCadastroFim() != null) {
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}

		if (item.getOrdemProducao().getDataCadastroInicio() != null && item.getOrdemProducao().getDataCadastroFim() == null) {
			item.getOrdemProducao().setDataCadastroFim(item.getOrdemProducao().getDataCadastroInicio());
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}

		if (item.getOrdemProducao().getDataCadastroInicio() == null && item.getOrdemProducao().getDataCadastroFim() != null) {
			item.getOrdemProducao().setDataCadastroInicio(item.getOrdemProducao().getDataCadastroFim());
			restricions.add(Restrictions.between("ordemProducao.dataCadastro", item.getOrdemProducao().getDataCadastroInicio(), item.getOrdemProducao().getDataCadastroFim()));
		}
		
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ItemProducaoAud.class,
				"dataRegistro", true, restricions,aliases);
	}
	
	
	
	
	
	
}
