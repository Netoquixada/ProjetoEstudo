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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;

import br.com.controlpro.bo.ItemProdutoConsumoBO;
import br.com.controlpro.bo.ProdutoConsumoBO;
import br.com.controlpro.constants.GrupoItem;
import br.com.controlpro.entity.ItemProdutoConsumo;
import br.com.controlpro.entity.OrdemCorte;
import br.com.controlpro.entity.ProdutoConsumo;
import br.com.controlpro.entity.consultas.Produto;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.BuscaNoWebContent;
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
public class ProdutoConsumoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarProdutoConsumo02.xhtml";
	private final String LIST = "/private/lista/listarProdutoConsumo.xhtml?faces-redirect=true";

	private ProdutoConsumo produtoConsumo;
	private ProdutoConsumo produtoConsumoFilter;
	private List<ProdutoConsumo> produtoConsumos;

	private ItemProdutoConsumo itemProdutoConsumo;
	ItemProdutoConsumo itemProdutoConsumoTemp;
	private List<ItemProdutoConsumo> itemProdutoConsumoList;
	private List<ItemProdutoConsumo> itemProdutoConsumoListAux;

	@PostConstruct
	public void init() {

		produtoConsumo = new ProdutoConsumo();
		produtoConsumoFilter = new ProdutoConsumo();
		try {
			produtoConsumos = ProdutoConsumoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		itemProdutoConsumo = new ItemProdutoConsumo();
		itemProdutoConsumoTemp = new ItemProdutoConsumo();
		itemProdutoConsumoList = new ArrayList<ItemProdutoConsumo>();
		itemProdutoConsumoListAux = new ArrayList<ItemProdutoConsumo>();

		ProdutoConsumo aux = RecuperarObjetoViaRequisicao.getObjeto(ProdutoConsumo.class, "id");
		produtoConsumo = aux != null ? aux : new ProdutoConsumo();

		// CASO SEJA EDIÇÃO DE DADOS
		if (produtoConsumo.getId() != null) {
			try {
				itemProdutoConsumoList = ItemProdutoConsumoBO.getInstance().itemPorProdutoConsumo(produtoConsumo);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}
	}

	public BigDecimal getTotalGeral() {
		return this.getValorCartao().add(this.getValorComissoes()).add(this.produtoConsumo.getCostura())
				.add(this.getValorImpostos()).add(this.produtoConsumo.getLoja()).add(this.produtoConsumo.getOutros())
				.add(getTotal()).add(this.produtoConsumo.getOutrosProducao()).add(this.getValorFinanceiro())
				.add(this.getValorMarketing());
	}
	public BigDecimal getTotalProducao() {
		return this.produtoConsumo.getCostura().add(getTotal()).add(this.produtoConsumo.getOutrosProducao());
	}
	public BigDecimal getTotalOperacional() {
		return this.getValorCartao().add(this.getValorComissoes())
				.add(this.getValorImpostos()).add(this.produtoConsumo.getLoja()).add(this.produtoConsumo.getOutros()).add(this.getValorFinanceiro())
				.add(this.getValorMarketing());
	}

	public BigDecimal getValorCartao() {
		return (this.produtoConsumo.getVendaVarejo().multiply(this.produtoConsumo.getPercentualCartao()))
				.divide(new BigDecimal(100));
	}

	public BigDecimal getValorComissoes() {
		return (this.produtoConsumo.getVendaVarejo().multiply(this.produtoConsumo.getPercentualComissao()))
				.divide(new BigDecimal(100));
	}

	public BigDecimal getValorImpostos() {
		return (this.produtoConsumo.getVendaVarejo().multiply(this.produtoConsumo.getPercentualImpostos()))
				.divide(new BigDecimal(100));
	}
	public BigDecimal getValorFinanceiro() {
		return (this.produtoConsumo.getVendaVarejo().multiply(this.produtoConsumo.getPercentualFinanceiro()))
				.divide(new BigDecimal(100));
	}
	public BigDecimal getValorMarketing() {
		return (this.produtoConsumo.getVendaVarejo().multiply(this.produtoConsumo.getPercentualMarketing()))
				.divide(new BigDecimal(100));
	}

	public BigDecimal getLucroVarejo() {
		return this.produtoConsumo.getVendaVarejo().subtract(getTotalGeral());
	}

	public Double getPercentualLucroVarejo() {
		if (this.produtoConsumo.getVendaVarejo().doubleValue() == 0) {
			return 0.0;
		} else {
			return (getLucroVarejo().doubleValue() / this.produtoConsumo.getVendaVarejo().doubleValue()) * 100;
		}
	}
	
	public String gerarPDFOrdemCorte() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<ProdutoConsumo> produto = new ArrayList<ProdutoConsumo>();

		produto.add(produtoConsumo);

		carregarListaItemProdutoConsumo();

		mapa.put("produto-consumo-itens", itemProdutoConsumoList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, produto, "produto-consumo",
				"Produto Consumo", true);
		return null;
	}

	public BigDecimal getLucroAtacado() {
		return this.produtoConsumo.getVendaAtacado().subtract(getTotalGeral());
	}

	public Double getPercentualLucroAtacado() {
		if (this.produtoConsumo.getVendaAtacado().doubleValue() == 0) {
			return 0.0;
		} else {
			return (getLucroAtacado().doubleValue() / this.produtoConsumo.getVendaAtacado().doubleValue()) * 100;
		}
	}

	public String save() {
		try {
			produtoConsumo.setItensProdutoConsumo(itemProdutoConsumoList);
			ProdutoConsumoBO.getInstance().save(produtoConsumo);

			for (int i = 0; i < itemProdutoConsumoList.size(); i++) {
				itemProdutoConsumoList.get(i).setProdutoConsumo(produtoConsumo);
			}

			if (!itemProdutoConsumoList.isEmpty()) {
				ItemProdutoConsumoBO.getInstance().mergeAll(itemProdutoConsumoList);
			}
			addInfoMessage("Cadastrado com sucesso!", "");
			produtoConsumo = new ProdutoConsumo();
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
			produtoConsumo.setItensProdutoConsumo(itemProdutoConsumoList);
			ProdutoConsumoBO.getInstance().update(produtoConsumo);

			for (int i = 0; i < itemProdutoConsumoList.size(); i++) {
				itemProdutoConsumoList.get(i).setProdutoConsumo(produtoConsumo);
			}

			if (!itemProdutoConsumoList.isEmpty()) {
				ItemProdutoConsumoBO.getInstance().mergeAll(itemProdutoConsumoList);
			}

			if (!itemProdutoConsumoListAux.isEmpty()) {
				ItemProdutoConsumoBO.getInstance().deleteAll(itemProdutoConsumoListAux);
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
		produtoConsumo = new ProdutoConsumo();
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, produtoConsumos, "produto-consumo-lista",
				"Relatório de Produto Consumo - " + DataUtil.formatarData(new Date()), true);
	}

	// Upload do arquivo
	public void uploadFile(FileUploadEvent event) {
		try {
			this.produtoConsumo.setExtensao(getExtensao(event.getFile().getFileName()));
			this.produtoConsumo.setArquivo(event.getFile().getContents());
			FacesMessage message = new FacesMessage("Enviado com sucesso!", event.getFile().getFileName());
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			e.printStackTrace();
			addWarnMessage("Erro: " + e.getMessage(), "");
		}
	}

	public String getExtensao(String nomeArquivo) {
		if (nomeArquivo.isEmpty() || nomeArquivo == null)
			return null;
		return nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length()).toLowerCase();
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (ItemProdutoConsumo item : itemProdutoConsumoList) {
			acumulador = acumulador.add(item.getTotal());
		}
		return acumulador;
	}

//	public BigDecimal totalGeral() {
//		BigDecimal acumulador = new BigDecimal(0.0);
//		acumulador.add(produtoConsumo.getCostura()).add(produtoConsumo.getOutros()).add(produtoConsumo.getOutrosProducao()).add(getTotal());
//		return acumulador;
//	}

	public void clearItemProdutoConsumo() {
		itemProdutoConsumo = new ItemProdutoConsumo();
	}

	public String list() {
		try {
			produtoConsumos = ProdutoConsumoBO.getInstance().produtosConsumoListReport(produtoConsumoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		produtoConsumo = new ProdutoConsumo();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (produtoConsumo.getStatus()) {
				produtoConsumo.setStatus(false);
				ProdutoConsumoBO.getInstance().update(produtoConsumo);
			} else {
				produtoConsumo.setStatus(true);
				ProdutoConsumoBO.getInstance().update(produtoConsumo);
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
		String status = produtoConsumo.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O produtoConsumo está " + status);
		return null;
	}

	public String removerItemProdutoConsumo() {
		try {
			for (int i = 0; i < itemProdutoConsumoList.size(); i++) {
				if (itemProdutoConsumoList.get(i) == itemProdutoConsumo) {
					itemProdutoConsumoListAux.add(itemProdutoConsumoList.get(i));
					itemProdutoConsumoList.remove(i);
					addWarnMessage("Grade removida com sucesso!", "");
					break;
				}
			}
			itemProdutoConsumo = new ItemProdutoConsumo();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemProdutoConsumo() {
		try {
			itemProdutoConsumoList.add(itemProdutoConsumo);
			addInfoMessage("Grade adicionada com sucesso!", "");
			itemProdutoConsumo = new ItemProdutoConsumo();
		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemProdutoConsumo() {
		try {
			for (int i = 0; i < itemProdutoConsumoList.size(); i++) {
				if (itemProdutoConsumoList.get(i) == itemProdutoConsumo) {
					itemProdutoConsumoList.set(i, itemProdutoConsumo);
					addInfoMessage("Grade atualizada com sucesso!", "");
					break;
				}
			}
			itemProdutoConsumo = new ItemProdutoConsumo();
		} catch (Exception e) {
			addErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public void carregarListaItemProdutoConsumo() {
		try {
			itemProdutoConsumoList = ItemProdutoConsumoBO.getInstance().itemPorProdutoConsumo(produtoConsumo);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public ProdutoConsumo getProdutoConsumo() {
		return produtoConsumo;
	}

	public void setProdutoConsumo(ProdutoConsumo produtoConsumo) {
		this.produtoConsumo = produtoConsumo;
	}

	public ProdutoConsumo getProdutoConsumoFilter() {
		return produtoConsumoFilter;
	}

	public void setProdutoConsumoFilter(ProdutoConsumo produtoConsumoFilter) {
		this.produtoConsumoFilter = produtoConsumoFilter;
	}

	public List<ProdutoConsumo> getProdutoConsumos() {
		return produtoConsumos;
	}

	public void setProdutoConsumos(List<ProdutoConsumo> produtoConsumos) {
		this.produtoConsumos = produtoConsumos;
	}

	public ItemProdutoConsumo getItemProdutoConsumo() {
		return itemProdutoConsumo;
	}

	public void setItemProdutoConsumo(ItemProdutoConsumo itemProdutoConsumo) {
		this.itemProdutoConsumo = itemProdutoConsumo;
	}

	public List<ItemProdutoConsumo> getItemProdutoConsumoList() {
		return itemProdutoConsumoList;
	}

	public void setItemProdutoConsumoList(List<ItemProdutoConsumo> itemProdutoConsumoList) {
		this.itemProdutoConsumoList = itemProdutoConsumoList;
	}

	public List<ItemProdutoConsumo> getItemProdutoConsumoListAux() {
		return itemProdutoConsumoListAux;
	}

	public void setItemProdutoConsumoListAux(List<ItemProdutoConsumo> itemProdutoConsumoListAux) {
		this.itemProdutoConsumoListAux = itemProdutoConsumoListAux;
	}

}
