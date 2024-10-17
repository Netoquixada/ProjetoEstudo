package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.ItemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.util.AliasUece;

public class ProducaoAnalisticaDao {

	public static ProducaoAnalisticaDao getInstance() {
		return new ProducaoAnalisticaDao();
	}

	public List<ItemProducao> itens(String protocolo, Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
			
			List<AliasUece> aliases = new ArrayList<AliasUece>();

			aliases.add(new AliasUece("ordemProducao", "ordemProducao"));
			
			
			if(faccao != null){
				restricions.add(Restrictions.eq("ordemProducao.faccao", faccao));
			}
			if(protocolo != null && !protocolo.isEmpty()){
				restricions.add(Restrictions.eq("ordemProducao.protocolo", protocolo));
			}
			
			return GenericDao.findAllByAttributesRestrictions(ItemProducao.class,
					null, false, restricions,aliases);
		}
	
	public List<HistoricoProducao> historico(String protocolo, Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		List<AliasUece> aliases = new ArrayList<AliasUece>();
		
		aliases.add(new AliasUece("ordemProducao", "ordemProducao"));
		
		
		if(faccao != null){
			restricions.add(Restrictions.eq("ordemProducao.faccao", faccao));
		}
		if(protocolo != null && !protocolo.isEmpty()){
			restricions.add(Restrictions.eq("ordemProducao.protocolo", protocolo));
		}
		
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				null, false, restricions,aliases);
	}
	
	
	public List<HistoricoConta> pagamentos(String protocolo, Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();

		List<AliasUece> aliases = new ArrayList<AliasUece>();

		aliases.add(new AliasUece("contaPagar", "c"));
		aliases.add(new AliasUece("c.itemProducao", "i"));
		aliases.add(new AliasUece("i.ordemProducao", "p"));
		
		if (protocolo != null && !protocolo.isEmpty()) {
			restricions.add(Restrictions.eq("p.protocolo", protocolo));
		}

		if(faccao != null){
			restricions.add(Restrictions.eq("p.faccao", faccao));
		}

		return GenericDao.findAllByAttributesRestrictions(HistoricoConta.class, null, true, restricions,aliases);
	}

	
	
	

}
