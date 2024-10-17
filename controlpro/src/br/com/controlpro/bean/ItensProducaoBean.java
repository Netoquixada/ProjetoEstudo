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
import br.com.controlpro.entity.ItemProducao;
import br.com.controlpro.report.GenericReport;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class ItensProducaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private ItemProducao itemProducaoFilter;
	private ItemProducao itemProducaoTotal;
	private List<ItemProducao> itemProducaoList;
	
	@PostConstruct
	public void init() {
		itemProducaoFilter = new ItemProducao();
		itemProducaoTotal = new ItemProducao();
		itemProducaoList = new ArrayList<>();
	}
	
	public void list(){
		try {
			itemProducaoList = ItemProducaoBO.getInstance().itensFilter(itemProducaoFilter);
			itemProducaoTotal = new ItemProducao();
			for (int i = 0; i < itemProducaoList.size(); i++) {
				itemProducaoTotal.setQuantidade(itemProducaoTotal.getQuantidade() + itemProducaoList.get(i).getQuantidade());
				itemProducaoTotal.setProntas(itemProducaoTotal.getProntas() + itemProducaoList.get(i).getProntas());
				itemProducaoTotal.setDefeito(itemProducaoTotal.getDefeito() + itemProducaoList.get(i).getDefeito());
			}
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();

		list();

		mapa.put("prontas", itemProducaoTotal.getProntas());
		mapa.put("enviados", itemProducaoTotal.getQuantidade());
		mapa.put("defeito", itemProducaoTotal.getDefeito());
		mapa.put("pendente", itemProducaoTotal.getQuantidade() - itemProducaoTotal.getProntas() - itemProducaoTotal.getDefeito());

		itemProducaoFilter = new ItemProducao();
		itemProducaoTotal = new ItemProducao();

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, itemProducaoList, "ordem-producao-enviados",
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

	public ItemProducao getItemProducaoFilter() {
		return itemProducaoFilter;
	}

	public void setItemProducaoFilter(ItemProducao itemProducaoFilter) {
		this.itemProducaoFilter = itemProducaoFilter;
	}

	public List<ItemProducao> getItemProducaoList() {
		return itemProducaoList;
	}

	public void setItemProducaoList(List<ItemProducao> itemProducaoList) {
		this.itemProducaoList = itemProducaoList;
	}
	
	public ItemProducao getItemProducaoTotal() {
		return itemProducaoTotal;
	}
	
	public void setItemProducaoTotal(ItemProducao itemProducaoTotal) {
		this.itemProducaoTotal = itemProducaoTotal;
	}
}
