package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.FaccaoBO;
import br.com.controlpro.bo.ItemProducaoBO;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.ItemProducaoAud;
import br.com.controlpro.report.GenericReport;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class ItensAudProducaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private ItemProducaoAud itemAudProducaoFilter;
	private ItemProducaoAud itemAudProducaoTotal;
	private List<ItemProducaoAud> itemAudProducaoListl;
	
	@PostConstruct
	public void init() {
		itemAudProducaoFilter = new ItemProducaoAud();
		itemAudProducaoTotal = new ItemProducaoAud();
		itemAudProducaoListl = new ArrayList<>();
	}
	
	public void list(){
		try {
			itemAudProducaoListl = ItemProducaoBO.getInstance().itensAudFilter(itemAudProducaoFilter);
			itemAudProducaoTotal = new ItemProducaoAud();
			for (int i = 0; i < itemAudProducaoListl.size(); i++) {
				itemAudProducaoTotal.setQuantidade(itemAudProducaoTotal.getQuantidade() + itemAudProducaoListl.get(i).getQuantidade());
				itemAudProducaoTotal.setProntas(itemAudProducaoTotal.getProntas() + itemAudProducaoListl.get(i).getProntas());
				itemAudProducaoTotal.setDefeito(itemAudProducaoTotal.getDefeito() + itemAudProducaoListl.get(i).getDefeito());
			}
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();

		list();

		mapa.put("prontas", itemAudProducaoTotal.getProntas());
		mapa.put("enviados", itemAudProducaoTotal.getQuantidade());
		mapa.put("defeito", itemAudProducaoTotal.getDefeito());
		mapa.put("pendente", itemAudProducaoTotal.getQuantidade() - itemAudProducaoTotal.getProntas() - itemAudProducaoTotal.getDefeito());

		itemAudProducaoFilter = new ItemProducaoAud();
		itemAudProducaoTotal = new ItemProducaoAud();

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, itemAudProducaoListl, "ordem-producao-enviados",
				"Arquivo", true);
	}
	
	public List<Faccao> getFaccaos(){
		
		try {
			return FaccaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ItemProducaoAud getItemAudProducaoFilter() {
		return itemAudProducaoFilter;
	}

	public void setItemAudProducaoFilter(ItemProducaoAud itemAudProducaoFilter) {
		this.itemAudProducaoFilter = itemAudProducaoFilter;
	}

	public ItemProducaoAud getItemAudProducaoTotal() {
		return itemAudProducaoTotal;
	}

	public void setItemAudProducaoTotal(ItemProducaoAud itemAudProducaoTotal) {
		this.itemAudProducaoTotal = itemAudProducaoTotal;
	}

	public List<ItemProducaoAud> getItemAudProducaoListl() {
		return itemAudProducaoListl;
	}

	public void setItemAudProducaoListl(List<ItemProducaoAud> itemAudProducaoListl) {
		this.itemAudProducaoListl = itemAudProducaoListl;
	}

}
