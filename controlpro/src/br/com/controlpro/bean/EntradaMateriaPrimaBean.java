package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.ItemEntradaMateriaPrimaBO;
import br.com.controlpro.bo.MateriaPrimaBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.bo.EntradaMateriaPrimaBO;
import br.com.controlpro.entity.ItemEntradaMateriaPrima;
import br.com.controlpro.entity.EntradaMateriaPrima;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class EntradaMateriaPrimaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarEntradaMateriaPrima.xhtml";
	private final String LIST = "/private/lista/listarEntradaMateriaPrima.xhtml?faces-redirect=true";

	private EntradaMateriaPrima entradaMateriaPrima;
	private EntradaMateriaPrima entradaMateriaPrimaFilter;
	private List<EntradaMateriaPrima> entradaMateriaPrimas;

	private ItemEntradaMateriaPrima itemEntradaMateriaPrima;
	ItemEntradaMateriaPrima itemEntradaMateriaPrimaTemp;
	private List<ItemEntradaMateriaPrima> itemEntradaMateriaPrimaList;
	private List<ItemEntradaMateriaPrima> itemEntradaMateriaPrimaListAux;
	BigDecimal acumuladorTotal = new BigDecimal(0.0);

	@PostConstruct
	public void init() {

		entradaMateriaPrima = new EntradaMateriaPrima();
		entradaMateriaPrimaFilter = new EntradaMateriaPrima();
//		try {
//			entradaMateriaPrimas = EntradaMateriaPrimaBO.getInstance().list();
//		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		itemEntradaMateriaPrima = new ItemEntradaMateriaPrima();
		itemEntradaMateriaPrimaTemp = new ItemEntradaMateriaPrima();
		itemEntradaMateriaPrimaList = new ArrayList<ItemEntradaMateriaPrima>();
		itemEntradaMateriaPrimaListAux = new ArrayList<ItemEntradaMateriaPrima>();

		EntradaMateriaPrima aux = RecuperarObjetoViaRequisicao.getObjeto(EntradaMateriaPrima.class, "id");
		entradaMateriaPrima = aux != null ? aux : new EntradaMateriaPrima();

		// CASO SEJA EDIÇÃO DE DADOS
		if (entradaMateriaPrima.getId() != null) {
			try {
				itemEntradaMateriaPrimaList = ItemEntradaMateriaPrimaBO.getInstance()
						.itemPorEntradaMateriaPrima(entradaMateriaPrima);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}
	}

	public String save() {
		try {
			entradaMateriaPrima.setItensEntradaMateriaPrima(itemEntradaMateriaPrimaList);
			EntradaMateriaPrimaBO.getInstance().save(entradaMateriaPrima);

			for (int i = 0; i < itemEntradaMateriaPrimaList.size(); i++) {
				itemEntradaMateriaPrimaList.get(i).getMateriaPrima()
						.setSaldo(itemEntradaMateriaPrimaList.get(i).getMateriaPrima().getSaldo()
								.add(new BigDecimal(itemEntradaMateriaPrimaList.get(i).getQuantidade())));

				itemEntradaMateriaPrimaList.get(i).setEntradaMateriaPrima(entradaMateriaPrima);

				MateriaPrimaBO.getInstance().update(itemEntradaMateriaPrimaList.get(i).getMateriaPrima());
			}

			if (!itemEntradaMateriaPrimaList.isEmpty()) {
				ItemEntradaMateriaPrimaBO.getInstance().mergeAll(itemEntradaMateriaPrimaList);
			}
			addInfoMessage("Cadastrado com sucesso!", "");
			entradaMateriaPrima = new EntradaMateriaPrima();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {
			entradaMateriaPrima.setItensEntradaMateriaPrima(itemEntradaMateriaPrimaList);
			EntradaMateriaPrimaBO.getInstance().update(entradaMateriaPrima);

			for (int i = 0; i < itemEntradaMateriaPrimaList.size(); i++) {
				itemEntradaMateriaPrimaList.get(i).setEntradaMateriaPrima(entradaMateriaPrima);
			}

			if (!itemEntradaMateriaPrimaList.isEmpty()) {
				ItemEntradaMateriaPrimaBO.getInstance().mergeAll(itemEntradaMateriaPrimaList);
			}

			// Atualizando o Saldo (DECREMENTO)
			for (int i = 0; i < itemEntradaMateriaPrimaListAux.size(); i++) {
				itemEntradaMateriaPrimaListAux.get(i).getMateriaPrima()
						.setSaldo(itemEntradaMateriaPrimaListAux.get(i).getMateriaPrima().getSaldo()
								.subtract(new BigDecimal(itemEntradaMateriaPrimaListAux.get(i).getQuantidade())));
				MateriaPrimaBO.getInstance().update(itemEntradaMateriaPrimaListAux.get(i).getMateriaPrima());
			}

			if (!itemEntradaMateriaPrimaListAux.isEmpty()) {
				ItemEntradaMateriaPrimaBO.getInstance().deleteAll(itemEntradaMateriaPrimaListAux);
			}
			addInfoMessage("Editado com sucesso!", "");
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ListaVaziaException e) {
			e.printStackTrace();
		} catch (IntegridadeObjetoHibernateException e) {
			e.printStackTrace();
		}
		entradaMateriaPrima = new EntradaMateriaPrima();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (ItemEntradaMateriaPrima item : itemEntradaMateriaPrimaList) {
			acumulador = acumulador.add(item.getTotal());
		}
		return acumulador;
	}

	public BigDecimal getTotalPorEntrada(EntradaMateriaPrima entrada) {
		BigDecimal acumulador = new BigDecimal(0.0);
		try {
			itemEntradaMateriaPrimaList = ItemEntradaMateriaPrimaBO.getInstance().itemPorEntradaMateriaPrima(entrada);
			for (ItemEntradaMateriaPrima item : itemEntradaMateriaPrimaList) {
				acumulador = acumulador.add(item.getTotal());
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

		return acumulador;
	}

	public void clearItemEntradaMateriaPrima() {
		itemEntradaMateriaPrima = new ItemEntradaMateriaPrima();
	}

	public String list() {
		try {
			acumuladorTotal = new BigDecimal(0.0);
			entradaMateriaPrimas = EntradaMateriaPrimaBO.getInstance()
					.entradaMateriaPrimasListReport(entradaMateriaPrimaFilter);

			for (int i = 0; i < entradaMateriaPrimas.size(); i++) {

				BigDecimal acumulador = new BigDecimal(0.0);
				itemEntradaMateriaPrimaList = ItemEntradaMateriaPrimaBO.getInstance()
						.itemPorEntradaMateriaPrima(entradaMateriaPrimas.get(i));
				for (ItemEntradaMateriaPrima item : itemEntradaMateriaPrimaList) {
					acumulador = acumulador.add(item.getTotal());
				}
				entradaMateriaPrimas.get(i).setValorTotal(acumulador);
				acumuladorTotal = acumuladorTotal.add(entradaMateriaPrimas.get(i).getValorTotal());
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();
		mapa.put("valorTotal", getTotal());
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, entradaMateriaPrimas, "entrada-materia-prima",
				"Relatório de Entrada de Materia Prima - " + DataUtil.formatarData(new Date()), true);
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		entradaMateriaPrima = new EntradaMateriaPrima();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (entradaMateriaPrima.getStatus()) {
				entradaMateriaPrima.setStatus(false);
				EntradaMateriaPrimaBO.getInstance().update(entradaMateriaPrima);
			} else {
				entradaMateriaPrima.setStatus(true);
				EntradaMateriaPrimaBO.getInstance().update(entradaMateriaPrima);
			}
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}
		String status = entradaMateriaPrima.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O entradaMateriaPrima está " + status);
		return null;
	}

	public String removerItemEntradaMateriaPrima() {
		try {
			for (int i = 0; i < itemEntradaMateriaPrimaList.size(); i++) {
				if (itemEntradaMateriaPrimaList.get(i) == itemEntradaMateriaPrima) {
					itemEntradaMateriaPrimaListAux.add(itemEntradaMateriaPrimaList.get(i));
					itemEntradaMateriaPrimaList.remove(i);
					addWarnMessage("Item removido com sucesso!", "");
					break;
				}
			}
			itemEntradaMateriaPrima = new ItemEntradaMateriaPrima();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemEntradaMateriaPrima() {
		try {
			itemEntradaMateriaPrimaList.add(itemEntradaMateriaPrima);
			addInfoMessage("Item adicionado com sucesso!", "");
			itemEntradaMateriaPrima = new ItemEntradaMateriaPrima();
		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemEntradaMateriaPrima() {
		try {
			for (int i = 0; i < itemEntradaMateriaPrimaList.size(); i++) {
				if (itemEntradaMateriaPrimaList.get(i) == itemEntradaMateriaPrima) {
					itemEntradaMateriaPrimaList.set(i, itemEntradaMateriaPrima);
					addInfoMessage("Item atualizado com sucesso!", "");
					break;
				}
			}
			itemEntradaMateriaPrima = new ItemEntradaMateriaPrima();
		} catch (Exception e) {
			addErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public void carregarListaItemEntradaMateriaPrima() {
		try {
			itemEntradaMateriaPrimaList = ItemEntradaMateriaPrimaBO.getInstance()
					.itemPorEntradaMateriaPrima(entradaMateriaPrima);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public Integer quantidadeItensPorEntrada(EntradaMateriaPrima entrada) {
		try {
			return ItemEntradaMateriaPrimaBO.getInstance().itemPorEntradaMateriaPrima(entrada).size();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public EntradaMateriaPrima getEntradaMateriaPrima() {
		return entradaMateriaPrima;
	}

	public void setEntradaMateriaPrima(EntradaMateriaPrima entradaMateriaPrima) {
		this.entradaMateriaPrima = entradaMateriaPrima;
	}

	public EntradaMateriaPrima getEntradaMateriaPrimaFilter() {
		return entradaMateriaPrimaFilter;
	}

	public void setEntradaMateriaPrimaFilter(EntradaMateriaPrima entradaMateriaPrimaFilter) {
		this.entradaMateriaPrimaFilter = entradaMateriaPrimaFilter;
	}

	public List<EntradaMateriaPrima> getEntradaMateriaPrimas() {
		return entradaMateriaPrimas;
	}

	public void setEntradaMateriaPrimas(List<EntradaMateriaPrima> entradaMateriaPrimas) {
		this.entradaMateriaPrimas = entradaMateriaPrimas;
	}

	public ItemEntradaMateriaPrima getItemEntradaMateriaPrima() {
		return itemEntradaMateriaPrima;
	}

	public void setItemEntradaMateriaPrima(ItemEntradaMateriaPrima itemEntradaMateriaPrima) {
		this.itemEntradaMateriaPrima = itemEntradaMateriaPrima;
	}

	public List<ItemEntradaMateriaPrima> getItemEntradaMateriaPrimaList() {
		return itemEntradaMateriaPrimaList;
	}

	public void setItemEntradaMateriaPrimaList(List<ItemEntradaMateriaPrima> itemEntradaMateriaPrimaList) {
		this.itemEntradaMateriaPrimaList = itemEntradaMateriaPrimaList;
	}

	public List<ItemEntradaMateriaPrima> getItemEntradaMateriaPrimaListAux() {
		return itemEntradaMateriaPrimaListAux;
	}

	public void setItemEntradaMateriaPrimaListAux(List<ItemEntradaMateriaPrima> itemEntradaMateriaPrimaListAux) {
		this.itemEntradaMateriaPrimaListAux = itemEntradaMateriaPrimaListAux;
	}

	public BigDecimal getAcumuladorTotal() {
		return acumuladorTotal;
	}

	public void setAcumuladorTotal(BigDecimal acumuladorTotal) {
		this.acumuladorTotal = acumuladorTotal;
	}

}
