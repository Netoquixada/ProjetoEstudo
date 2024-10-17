package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.ItemMetaBO;
import br.com.controlpro.bo.MetaBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.ItemMeta;
import br.com.controlpro.entity.Meta;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.ManagedBeanHelper;
import br.com.controlpro.util.RecuperarObjetoViaRequisicao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@ViewScoped
public class MetaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarMeta.xhtml";
	private final String LIST = "/private/lista/listarMeta.xhtml?faces-redirect=true";

	private Usuario usuarioLogado;

	private Meta meta;
	private Meta metaFilter;
	private List<Meta> metas = new ArrayList<Meta>();

	private ItemMeta itemMeta;
	private List<ItemMeta> itemMetaList;

	private int percetualMeta = 0;
	
	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		meta = new Meta();
		metaFilter = new Meta();

		itemMeta = new ItemMeta();
		itemMetaList = new ArrayList<ItemMeta>();

		Meta aux = RecuperarObjetoViaRequisicao.getObjeto(Meta.class, "id");
		meta = aux != null ? aux : new Meta();

		// CASO SEJA EDI��O DE DADOS
		if (meta.getId() != null) {
			try {
				itemMetaList = ItemMetaBO.getInstance().itemMetaPorMeta(meta);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}

	}

	public String save() {
		try {
			MetaBO.getInstance().save(meta);

			addInfoMessage("Cadastrado com sucesso!", "");
			meta = new Meta();
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
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {
			MetaBO.getInstance().update(meta);
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
		}
		meta = new Meta();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public void clearItemMeta() {
		itemMeta = new ItemMeta();
	}

	public Loja[] getLojas() {
		return Loja.values();
	}
	
	

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (ItemMeta item : itemMetaList) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}
	
	public Integer getTotalItens() {
		Integer acumulador = 0;
		for (ItemMeta item : itemMetaList) {
			acumulador = acumulador + item.getQtdItens();
		}
		return acumulador;
	}

	public String list() {
		try {
			metas = MetaBO.getInstance().metaListReport(metaFilter);
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
		meta = new Meta();
		return CAD_EDIT;
	}

//	public void calcValorTotal() {
//		itemMeta
//				.setValorTotal(itemMeta.getValor().multiply(new BigDecimal(itemMeta.getQuantidade())));
//	}

	public void calPercentual() {
		if (meta.getValor().doubleValue() == 0) {
			percetualMeta = 0;
		} else {
			percetualMeta = (getTotal().multiply(new BigDecimal(100))).divide(this.meta.getValor(), BigDecimal.ROUND_UP).intValue();
		}
	}
	
	public Long setarMaximoDashboard(){
		if (meta.getValor().compareTo(getTotal()) == 1){
			return 100L;
		} else {
			return this.percetualMeta + 10L;
		}
		
	}
	

	public void saveItem() {
		try {
			itemMeta.setUsuario(usuarioLogado);
			itemMeta.setMeta(meta);
			ItemMetaBO.getInstance().save(itemMeta);

			carregarListaItemMeta();
			addInfoMessage("Cadastrado com sucesso!", "");
			itemMeta = new ItemMeta();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
	}

	public void carregarListaItemMeta() {
		try {
			itemMetaList = ItemMetaBO.getInstance().itemMetaPorMeta(meta);
			calPercentual();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Meta getMetaFilter() {
		return metaFilter;
	}

	public void setMetaFilter(Meta metaFilter) {
		this.metaFilter = metaFilter;
	}

	public List<Meta> getMetas() {
		return metas;
	}

	public void setMetas(List<Meta> metas) {
		this.metas = metas;
	}

	public ItemMeta getItemMeta() {
		return itemMeta;
	}

	public void setItemMeta(ItemMeta itemMeta) {
		this.itemMeta = itemMeta;
	}

	public List<ItemMeta> getItemMetaList() {
		return itemMetaList;
	}

	public void setItemMetaList(List<ItemMeta> itemMetaList) {
		this.itemMetaList = itemMetaList;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public int getPercetualMeta() {
		return percetualMeta;
	}
	
	public void setPercetualMeta(int percetualMeta) {
		this.percetualMeta = percetualMeta;
	}

}
