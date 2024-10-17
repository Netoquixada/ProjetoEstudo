package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.DespesaFixaBO;
import br.com.controlpro.bo.ItemDespesaFixaBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.constants.StatusDespesaFixa;
import br.com.controlpro.entity.DespesaFixa;
import br.com.controlpro.entity.ItemDespesaFixa;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@ViewScoped
public class DespesaFixaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarDespesaFixa.xhtml";
	private final String LIST = "/private/lista/listarDespesaFixa.xhtml?faces-redirect=true";

	private DespesaFixa despesaFixa;
	private DespesaFixa despesaFixaFilter;
	private LazyEntityDataModel<DespesaFixa> lazy = new LazyEntityDataModel<DespesaFixa>(DespesaFixa.class);
	private List<DespesaFixa> despesaFixas;

	private Usuario usuarioLogado;

	private ItemDespesaFixa itemDespesaFixa;
	private List<ItemDespesaFixa> itemDespesaFixaList;
	private List<ItemDespesaFixa> itemDespesaFixaListAux;
	
	private BigDecimal meta = new BigDecimal(0);
	BigDecimal acumulador = new BigDecimal(0.0);

	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		despesaFixa = new DespesaFixa();
		despesaFixaFilter = new DespesaFixa();
		lazy = new LazyEntityDataModel<DespesaFixa>(DespesaFixa.class);
		despesaFixas = new ArrayList<DespesaFixa>();

		itemDespesaFixa = new ItemDespesaFixa();
		itemDespesaFixaList = new ArrayList<ItemDespesaFixa>();
		itemDespesaFixaListAux = new ArrayList<ItemDespesaFixa>();
		DespesaFixa aux = RecuperarObjetoViaRequisicao.getObjeto(DespesaFixa.class, "id");
		despesaFixa = aux != null ? aux : new DespesaFixa();

		// CASO SEJA EDI��O DE DADOS
		if (despesaFixa.getId() != null) {
			try {
				itemDespesaFixaList = ItemDespesaFixaBO.getInstance().itemDespesaFixaPorDespesaFixa(despesaFixa);
				getRefreshValores();
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}

	}

	public String save() {
		try {

			despesaFixa.setItensDespesaFixa(itemDespesaFixaList);
			DespesaFixaBO.getInstance().save(despesaFixa);

			for (int i = 0; i < itemDespesaFixaList.size(); i++) {
				itemDespesaFixaList.get(i).setDespesaFixa(despesaFixa);
			}

			if (!itemDespesaFixaList.isEmpty()) {
				ItemDespesaFixaBO.getInstance().mergeAll(itemDespesaFixaList);
			}
			addInfoMessage("Cadastrado com sucesso!", "");
			despesaFixa = new DespesaFixa();
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
			despesaFixa.setItensDespesaFixa(itemDespesaFixaList);
			DespesaFixaBO.getInstance().update(despesaFixa);

			for (int i = 0; i < itemDespesaFixaList.size(); i++) {
				itemDespesaFixaList.get(i).setDespesaFixa(despesaFixa);
			}

			if (!itemDespesaFixaList.isEmpty()) {
				ItemDespesaFixaBO.getInstance().mergeAll(itemDespesaFixaList);
			}

			if (!itemDespesaFixaListAux.isEmpty()) {
				ItemDespesaFixaBO.getInstance().deleteAll(itemDespesaFixaListAux);
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
		despesaFixa = new DespesaFixa();
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	public String gerarPDFDespesaFixa() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<DespesaFixa> despesasFixas = new ArrayList<DespesaFixa>();

		despesasFixas.add(despesaFixa);

		carregarListaItemDespesaFixa();
		getRefreshValores();
		mapa.put("despesa-fixa-itens", itemDespesaFixaList);
		mapa.put("meta", meta);
		mapa.put("acumulador", acumulador);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, despesasFixas,
				"despesa-fixa", "Despesas da Loja: - " + despesaFixa.getLoja().getDescricao(),
				true);
		return null;
	}
	
	

	public void finalizarDespesa() {
		try {
			itemDespesaFixa.setStatusDespesaFixa(StatusDespesaFixa.FINALIZADO);
			itemDespesaFixa.setDataConfirmacao(new Date());
			ItemDespesaFixaBO.getInstance().update(itemDespesaFixa);
			getRefreshValores();
			addInfoMessage("Processo de finalização realizado com sucesso!", "");
		} catch (ViolacaoChaveHibernateException | ObjetoNaoEncontradoHibernateException | ValidacaoHibernateException
				| SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

	}
	
	public void getRefreshValores() {
		acumulador = new BigDecimal(0);
		for (ItemDespesaFixa item : itemDespesaFixaList) {
			if (item.getStatusDespesaFixa().equals(StatusDespesaFixa.ATIVO)) {
				acumulador = acumulador.add(item.getValor());
			}
		}
		meta = new BigDecimal(0);
		meta = acumulador.divide(new BigDecimal(despesaFixa.getQuantidadeProducao()),2,RoundingMode.UP);
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public void clearItemDespesaFixa() {
		itemDespesaFixa = new ItemDespesaFixa();
	}

	public String list() {
		lazy = DespesaFixaBO.getInstance().despesaFixaLazy(despesaFixaFilter);
		return null;
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		despesaFixa = new DespesaFixa();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (despesaFixa.getStatus()) {
				despesaFixa.setStatus(false);
				DespesaFixaBO.getInstance().update(despesaFixa);
			} else {
				despesaFixa.setStatus(true);
				DespesaFixaBO.getInstance().update(despesaFixa);
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
		String status = despesaFixa.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O despesaFixa est� " + status);
		return null;
	}

	public String removerItemDespesaFixa() {
		try {
			for (int i = 0; i < itemDespesaFixaList.size(); i++) {
				if (itemDespesaFixaList.get(i) == itemDespesaFixa) {
					itemDespesaFixaListAux.add(itemDespesaFixaList.get(i));
					itemDespesaFixaList.remove(i);
					addWarnMessage("Grade removida com sucesso!", "");
					break;
				}
			}
			getRefreshValores();
			itemDespesaFixa = new ItemDespesaFixa();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemDespesaFixa() {
		try {
			itemDespesaFixa.setStatusDespesaFixa(StatusDespesaFixa.ATIVO);
			itemDespesaFixaList.add(itemDespesaFixa);
			getRefreshValores();
			addInfoMessage("Item adicionada com sucesso!", "");
			itemDespesaFixa = new ItemDespesaFixa();
		} catch (Exception e) {
			addWarnMessage("", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemDespesaFixa() {
		try {
			for (int i = 0; i < itemDespesaFixaList.size(); i++) {
				if (itemDespesaFixaList.get(i) == itemDespesaFixa) {
					itemDespesaFixaList.set(i, itemDespesaFixa);
					addInfoMessage("Item atualizada com sucesso!", "");
					break;
				}
			}
			getRefreshValores();
			itemDespesaFixa = new ItemDespesaFixa();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public SituacaoOrdem[] getSituacoes() {
		return SituacaoOrdem.values();
	}
	
	public void carregarListaItemDespesaFixa() {
		try {
			itemDespesaFixaList = ItemDespesaFixaBO.getInstance().itemDespesaFixaPorDespesaFixa(despesaFixa);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public DespesaFixa getDespesaFixa() {
		return despesaFixa;
	}

	public void setDespesaFixa(DespesaFixa despesaFixa) {
		this.despesaFixa = despesaFixa;
	}

	public DespesaFixa getDespesaFixaFilter() {
		return despesaFixaFilter;
	}

	public void setDespesaFixaFilter(DespesaFixa despesaFixaFilter) {
		this.despesaFixaFilter = despesaFixaFilter;
	}

	public LazyEntityDataModel<DespesaFixa> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<DespesaFixa> lazy) {
		this.lazy = lazy;
	}

	public List<DespesaFixa> getDespesaFixas() {
		return despesaFixas;
	}

	public void setDespesaFixas(List<DespesaFixa> despesaFixas) {
		this.despesaFixas = despesaFixas;
	}

	public ItemDespesaFixa getItemDespesaFixa() {
		return itemDespesaFixa;
	}

	public void setItemDespesaFixa(ItemDespesaFixa itemDespesaFixa) {
		this.itemDespesaFixa = itemDespesaFixa;
	}

	public List<ItemDespesaFixa> getItemDespesaFixaList() {
		return itemDespesaFixaList;
	}

	public void setItemDespesaFixaList(List<ItemDespesaFixa> itemDespesaFixaList) {
		this.itemDespesaFixaList = itemDespesaFixaList;
	}

	public List<ItemDespesaFixa> getItemDespesaFixaListAux() {
		return itemDespesaFixaListAux;
	}

	public void setItemDespesaFixaListAux(List<ItemDespesaFixa> itemDespesaFixaListAux) {
		this.itemDespesaFixaListAux = itemDespesaFixaListAux;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public BigDecimal getMeta() {
		return meta;
	}
	
	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}

	public BigDecimal getAcumulador() {
		return acumulador;
	}

	public void setAcumulador(BigDecimal acumulador) {
		this.acumulador = acumulador;
	}
	
	
}
