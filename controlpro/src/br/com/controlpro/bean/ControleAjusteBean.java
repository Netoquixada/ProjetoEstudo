package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.StreamedContent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.ControleAjusteBO;
import br.com.controlpro.bo.ItemControleAjusteBO;
import br.com.controlpro.bo.MovimentacaoProdutoBO;
import br.com.controlpro.bo.VendasBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.ControleAjuste;
import br.com.controlpro.entity.ItemControleAjuste;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.entity.consultas.MovimentacaoProduto;
import br.com.controlpro.entity.consultas.Vendas;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.SaidaNaoEncontradaException;
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

@ManagedBean
@ViewScoped
public class ControleAjusteBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarControleAjuste.xhtml";
	private final String LIST = "/private/lista/listarControleAjuste.xhtml?faces-redirect=true";

	private ControleAjuste controleAjuste;
	private ControleAjuste ControleAjusteFilter;
	private List<ControleAjuste> ControleAjustes;

	private Vendas venda;
	private StreamedContent arquivo;
	private List<MovimentacaoProduto> movimentacoes;
	private BigDecimal valorFrete = new BigDecimal(0);
	private BigDecimal valorFretePago = new BigDecimal(0);
	
	private ItemControleAjuste itemControleAjuste;
	private List<ItemControleAjuste> itemControleAjusteList;
	private List<ItemControleAjuste> itemControleAjusteListAux;
	
	private Usuario usuarioLogado;
	
	
	@PostConstruct
	public void init() {
		controleAjuste = new ControleAjuste();
		ControleAjusteFilter = new ControleAjuste();
		ControleAjustes = new ArrayList<ControleAjuste>();
		movimentacoes = new ArrayList<MovimentacaoProduto>();
		
		itemControleAjuste = new ItemControleAjuste();
		itemControleAjusteList = new ArrayList<>();
		itemControleAjusteListAux = new ArrayList<>();
		venda = new Vendas();
		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		ControleAjuste aux = RecuperarObjetoViaRequisicao.getObjeto(ControleAjuste.class, "id");
		controleAjuste = aux != null ? aux : new ControleAjuste();

		// CASO SEJA EDI��O DE DADOS
				if (controleAjuste.getId() != null) {
						movimentacoesProduto();
						try {
							itemControleAjusteList = ItemControleAjusteBO.getInstance().itemPorControleAjuste(controleAjuste);
						} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
							e.printStackTrace();
						}
				}
				
	}

	public String save() {
		try {
			String protocolo = formataProtocolo(controleAjuste);
			controleAjuste.setProtocolo(protocolo);
			ControleAjusteBO.getInstance().save(controleAjuste);
			
			for (int i = 0; i < itemControleAjusteList.size(); i++) {
				itemControleAjusteList.get(i).setControleAjuste(controleAjuste);
			}

			if (!itemControleAjusteList.isEmpty()) {
				ItemControleAjusteBO.getInstance().mergeAll(itemControleAjusteList);
			}

			addInfoMessage("Cadastrado com sucesso!", "");
			controleAjuste = new ControleAjuste();
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
			ControleAjusteBO.getInstance().update(controleAjuste);

			for (int i = 0; i < itemControleAjusteList.size(); i++) {
				itemControleAjusteList.get(i).setControleAjuste(controleAjuste);
			}

			if (!itemControleAjusteList.isEmpty()) {
				ItemControleAjusteBO.getInstance().mergeAll(itemControleAjusteList);
			}

			if (!itemControleAjusteListAux.isEmpty()) {
				ItemControleAjusteBO.getInstance().deleteAll(itemControleAjusteListAux);
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
		controleAjuste = new ControleAjuste();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String list() {
		try {
			ControleAjustes = ControleAjusteBO.getInstance().ControleAjusteListReport(ControleAjusteFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarPDFControleAjuste() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<ControleAjuste> controle = new ArrayList<ControleAjuste>();

		controle.add(controleAjuste);

		atualizarListas();
		mapa.put("controle-ajuste-movimentacoes", movimentacoes);
		mapa.put("controle-ajuste-itens", itemControleAjusteList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, controle, "controle-ajuste",
				"Controle de Ajuste - " + controleAjuste.getProtocolo(), true);
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
			Map<String, Object> mapa = new HashMap<String, Object>();

			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ControleAjustes, "controle-ajuste-lista", "Arquivo", true);
	}

	public String movimentacoesProduto() {
		try {
			venda = VendasBO.getInstance().vendaPorSequencia(controleAjuste.getSequenciaVenda()).get(0);
			movimentacoes = MovimentacaoProdutoBO.getInstance().movimentacoesProdutoPorSequencia(venda.getSequencia());

			if (movimentacoes.isEmpty()) {
				venda = new Vendas();
				throw new SaidaNaoEncontradaException("Nenhuma saída encontrada para essa sequência!");
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (SaidaNaoEncontradaException e) {
			addWarnMessage("Atenção!", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public void atualizarListas() {
		movimentacoesProduto();
		carregarListaItemControleAjuste();
	}
	
	public Loja[] getLojas() {
		return Loja.values();
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		controleAjuste = new ControleAjuste();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (controleAjuste.getStatus()) {
				controleAjuste.setStatus(false);
				ControleAjusteBO.getInstance().update(controleAjuste);
			} else {
				controleAjuste.setStatus(true);
				ControleAjusteBO.getInstance().update(controleAjuste);
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
		String status = controleAjuste.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O ControleAjuste est� " + status);
		return null;
	}
	
	public String formataProtocolo(ControleAjuste controleAjuste)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "CA" + ControleAjusteBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(controleAjuste.getDataCadastro());
		return res;
	}
	
	public void clearItemControleAjuste() {
		itemControleAjuste = new ItemControleAjuste();
	}
	
	public String removerItemControleAjuste() {
		try {
			for (int i = 0; i < itemControleAjusteList.size(); i++) {
				if (itemControleAjusteList.get(i) == itemControleAjuste) {
					itemControleAjusteListAux.add(itemControleAjusteList.get(i));
					itemControleAjusteList.remove(i);
					addWarnMessage("Item removido com sucesso!", "");
					break;
				}
			}
			itemControleAjuste = new ItemControleAjuste();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemControleAjuste() {
		try {
			itemControleAjusteList.add(itemControleAjuste);
			addInfoMessage("Item adicionado com sucesso!", "");
			itemControleAjuste = new ItemControleAjuste();
		} catch (Exception e) {
			addWarnMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemControleAjuste() {
		try {
			for (int i = 0; i < itemControleAjusteList.size(); i++) {
				if (itemControleAjusteList.get(i) == itemControleAjuste) {
					itemControleAjusteList.set(i, itemControleAjuste);
					addInfoMessage("Item atualizado com sucesso!", "");
					break;
				}
			}
			itemControleAjuste = new ItemControleAjuste();
		} catch (Exception e) {
			addErrorMessage("Erro!", e.getMessage());
		}
		return null;
	}

	public void carregarListaItemControleAjuste() {
		try {
			itemControleAjusteList = ItemControleAjusteBO.getInstance()
					.itemPorControleAjuste(controleAjuste);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public ControleAjuste getControleAjuste() {
		return controleAjuste;
	}

	public void setControleAjuste(ControleAjuste ControleAjuste) {
		this.controleAjuste = ControleAjuste;
	}

	public ControleAjuste getControleAjusteFilter() {
		return ControleAjusteFilter;
	}

	public void setControleAjusteFilter(ControleAjuste ControleAjusteFilter) {
		this.ControleAjusteFilter = ControleAjusteFilter;
	}

	public List<ControleAjuste> getControleAjustes() {
		return ControleAjustes;
	}

	public void setControleAjustes(List<ControleAjuste> ControleAjustes) {
		this.ControleAjustes = ControleAjustes;
	}

	public List<MovimentacaoProduto> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<MovimentacaoProduto> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public StreamedContent getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorFretePago() {
		return valorFretePago;
	}

	public void setValorFretePago(BigDecimal valorFretePago) {
		this.valorFretePago = valorFretePago;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ItemControleAjuste getItemControleAjuste() {
		return itemControleAjuste;
	}

	public void setItemControleAjuste(ItemControleAjuste itemControleAjuste) {
		this.itemControleAjuste = itemControleAjuste;
	}

	public List<ItemControleAjuste> getItemControleAjusteList() {
		return itemControleAjusteList;
	}

	public void setItemControleAjusteList(List<ItemControleAjuste> itemControleAjusteList) {
		this.itemControleAjusteList = itemControleAjusteList;
	}

	public List<ItemControleAjuste> getItemControleAjusteListAux() {
		return itemControleAjusteListAux;
	}

	public void setItemControleAjusteListAux(List<ItemControleAjuste> itemControleAjusteListAux) {
		this.itemControleAjusteListAux = itemControleAjusteListAux;
	}
	
}
