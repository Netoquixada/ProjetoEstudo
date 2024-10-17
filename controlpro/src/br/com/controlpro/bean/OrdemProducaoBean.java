package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.AquisicaoSaidaBO;
import br.com.controlpro.bo.ContaPagarBO;
import br.com.controlpro.bo.HistoricoProducaoBO;
import br.com.controlpro.bo.ItemAviamentoProducaoBO;
import br.com.controlpro.bo.ItemProducaoBO;
import br.com.controlpro.bo.MateriaPrimaBO;
import br.com.controlpro.bo.OcorrenciaOrdemProducaoBO;
import br.com.controlpro.bo.OrdemProducaoBO;
import br.com.controlpro.bo.QueryUtilBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OrigemOcorrencia;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.constants.TipoCostura;
import br.com.controlpro.constants.TipoItemProducao;
import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.ContaPagar;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.ItemAviamentoProducao;
import br.com.controlpro.entity.ItemProducao;
import br.com.controlpro.entity.ItemProducaoAud;
import br.com.controlpro.entity.OcorrenciaOrdemProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.BuscaNoWebContent;
import br.com.controlpro.util.CalcDias;
import br.com.controlpro.util.DataUtil;
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
public class OrdemProducaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarOrdemProducao.xhtml";
	private final String LIST = "/private/lista/listarOrdemProducao.xhtml?faces-redirect=true";

	private OrdemProducao ordemProducao;
	private OrdemProducao ordemProducaoFilter;
	private List<OrdemProducao> ordemProducaos = new ArrayList<OrdemProducao>();

	private Usuario usuarioLogado;
	private ContaPagar contaPagar;

	private ItemProducao itemProducaoReenviar;

	private ItemProducao itemProducao;
	ItemProducao itemProducaoTemp;

	private List<ItemProducao> itemReenviarProducaoList;
	private List<HistoricoProducao> historicoReenviarProducaoList;

	private List<ItemProducao> itemProducaoList;
	private List<ItemProducao> itemProducaoListAux;
	private List<HistoricoProducao> historicoProducaoList;

	private ItemAviamentoProducao itemAviamentoProducao;
	private List<ItemAviamentoProducao> itemAviamentoList;
	private List<ItemAviamentoProducao> itemAviamentoListAux;

	private AquisicaoSaida aquisicaoSaida;
	private List<AquisicaoSaida> aquisicaoSaidaList;

	private Integer cortadas = 0;
	private Integer enviadas = 0;
	private Integer recebidas = 0;
	private Integer aguardandoEnvio = 0;
	private Integer aguardandoRec = 0;

	private OcorrenciaOrdemProducao ocorrencia;
	private List<OcorrenciaOrdemProducao> ocorrenciaList;

	private List<ItemProducaoAud> itemAudProducaoList;

	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		ordemProducao = new OrdemProducao();
		ordemProducaoFilter = new OrdemProducao();
		try {
			ordemProducaos = OrdemProducaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			e1.printStackTrace();
		}

		contaPagar = new ContaPagar();

		itemProducaoReenviar = new ItemProducao();
		itemProducao = new ItemProducao();
		itemProducaoTemp = new ItemProducao();
		itemProducaoList = new ArrayList<ItemProducao>();
		itemProducaoListAux = new ArrayList<ItemProducao>();
		historicoProducaoList = new ArrayList<HistoricoProducao>();

		itemReenviarProducaoList = new ArrayList<ItemProducao>();
		historicoReenviarProducaoList = new ArrayList<HistoricoProducao>();

		itemAudProducaoList = new ArrayList<>();

		itemAviamentoProducao = new ItemAviamentoProducao();
		itemAviamentoList = new ArrayList<>();
		itemAviamentoListAux = new ArrayList<>();

		aquisicaoSaida = new AquisicaoSaida();
		aquisicaoSaidaList = new ArrayList<>();

		ocorrencia = new OcorrenciaOrdemProducao();
		ocorrenciaList = new ArrayList<>();

		OrdemProducao aux = RecuperarObjetoViaRequisicao.getObjeto(OrdemProducao.class, "id");
		ordemProducao = aux != null ? aux : new OrdemProducao();

		// CASO SEJA EDI��O DE DADOS
		if (ordemProducao.getId() != null) {
			try {
				itemProducaoList = ItemProducaoBO.getInstance().itemProducaoPorOrdemProducao(ordemProducao);
				historicoProducaoList = HistoricoProducaoBO.getInstance().historicoProducao(ordemProducao);
				itemAviamentoList = ItemAviamentoProducaoBO.getInstance()
						.itemAviamentoProducaoPorOrdemCorte(ordemProducao);
				ocorrenciaList = OcorrenciaOrdemProducaoBO.getInstance().historicoPorControlePedido(ordemProducao);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}

	}

	public String save() {
		try {
			String protocolo = formataProtocolo(ordemProducao);
			ordemProducao.setProtocolo(protocolo);
			ordemProducao.setSituacao(SituacaoOrdem.ANDAMENTO);

			ordemProducao.setItensProducao(itemProducaoList);
			OrdemProducaoBO.getInstance().save(ordemProducao);

			for (int i = 0; i < itemProducaoList.size(); i++) {
				itemProducaoList.get(i).setOrdemProducao(ordemProducao);
			}

			if (!itemProducaoList.isEmpty()) {
				ItemProducaoBO.getInstance().mergeAll(itemProducaoList);
			}

			for (int i = 0; i < itemAviamentoList.size(); i++) {
				itemAviamentoList.get(i).setOrdemProducao(ordemProducao);
			}

			if (!itemAviamentoList.isEmpty()) {
				ItemAviamentoProducaoBO.getInstance().mergeAll(itemAviamentoList);
			}
			addInfoMessage("Cadastrado com sucesso!", "Protocolo: "+ordemProducao.getProtocolo() + " - " +
					ordemProducao.getSituacao() + " - " + DataUtil.formatarData(ordemProducao.getDataCadastro()));
			
			ordemProducao = new OrdemProducao();
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

	public void finalizarOrdem() {

		try {
			ordemProducao.setSituacao(SituacaoOrdem.FINALIZADA);
			OrdemProducaoBO.getInstance().update(ordemProducao);
			addInfoMessage("Finalizada com sucesso!", "");
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
	}

	public String update() {
		try {
			ordemProducao.setItensProducao(itemProducaoList);
			OrdemProducaoBO.getInstance().update(ordemProducao);

			for (int i = 0; i < itemProducaoList.size(); i++) {
				itemProducaoList.get(i).setOrdemProducao(ordemProducao);
			}

			if (!itemProducaoList.isEmpty()) {
				ItemProducaoBO.getInstance().mergeAll(itemProducaoList);
			}

			if (!itemProducaoListAux.isEmpty()) {
				ItemProducaoBO.getInstance().deleteAll(itemProducaoListAux);
			}

			for (int i = 0; i < itemAviamentoList.size(); i++) {
				itemAviamentoList.get(i).setOrdemProducao(ordemProducao);
			}

			if (!itemAviamentoList.isEmpty()) {
				ItemAviamentoProducaoBO.getInstance().mergeAll(itemAviamentoList);
			}

			if (!itemAviamentoListAux.isEmpty()) {
				ItemAviamentoProducaoBO.getInstance().deleteAll(itemAviamentoListAux);
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
		ordemProducao = new OrdemProducao();
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	public OrigemOcorrencia[] getOrigens() {
		return OrigemOcorrencia.values();
	}

	public TipoItemProducao[] getTipoItensProducao() {
		return TipoItemProducao.values();
	}

	public String gerarPDFOrdemProducao() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<OrdemProducao> ordensProducao = new ArrayList<OrdemProducao>();
		
		ordensProducao.add(ordemProducao);

		carregarListaItemProducao();

		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logoglamix.jpg"));
		mapa.put("ordem-producao-itens", itemProducaoList);
		mapa.put("ordem-producao-historico", historicoProducaoList);
		mapa.put("ordem-producao-aviamento", itemAviamentoList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordensProducao, "ordem-producao",
				"Ordem de producao - " + ordemProducao.getProtocolo(), true);
		return null;
	}
	
	public String gerarPDFOrdemProducaoRemessaRetorno() {
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		List<OrdemProducao> ordensProducao = new ArrayList<OrdemProducao>();
		
		ordensProducao.add(ordemProducao);
		
		carregarListaItemProducao();
		
		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logoglamix.jpg"));
		mapa.put("ordem-producao-itens", itemProducaoList);
		mapa.put("ordem-producao-historico", historicoProducaoList);
		mapa.put("ordem-producao-aviamento", itemAviamentoList);
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordensProducao, "ordem-producao-remessa-retorno",
				"Ordem de producao - " + ordemProducao.getProtocolo(), true);
		return null;
	}

	public String gerarPDFOrdemProducaoResumido() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<OrdemProducao> ordensProducao = new ArrayList<OrdemProducao>();

		ordensProducao.add(ordemProducao);

		carregarListaItemProducao();

		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
		mapa.put("ordem-producao-itens", itemProducaoList);
		mapa.put("ordem-producao-historico", historicoProducaoList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordensProducao, "ordem-producao-resumido",
				"Ordem de producao - " + ordemProducao.getProtocolo(), true);
		return null;
	}

	public String gerarPDFComprovanteOrdemProducao() {

		itemProducaoList = new ArrayList<ItemProducao>();

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<OrdemProducao> ordensProducao = new ArrayList<OrdemProducao>();

		ordensProducao.add(ordemProducao);
		itemProducaoList.add(itemProducao);

		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
		mapa.put("ordem-producao-itens", itemProducaoList);
		mapa.put("faccao", ordemProducao.getFaccao().getNome());
		mapa.put("telefone", ordemProducao.getFaccao().getTelefone());

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordensProducao, "ordem-producao-comprovante",
				"Comprovante do item da ordem de producao - " + ordemProducao.getProtocolo(), true);

		return null;
	}

	/**
	 * Metdo de impressao de relatorio em pdf
	 * 
	 * @param ev
	 */
	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<OrdemProducao> ordens = OrdemProducaoBO.getInstance().ordemProducaoListReport(ordemProducaoFilter);
			ordemProducaoFilter = new OrdemProducao();
			mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
			mapa.put("filtro", OrdemProducaoBO.getInstance().dadosFiltro());

			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordens, "ordem-producao-lista", "Arquivo", true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void gerarPDFResumido(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<OrdemProducao> ordens = OrdemProducaoBO.getInstance().ordemProducaoListReport(ordemProducaoFilter);
			ordemProducaoFilter = new OrdemProducao();
			mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logo-diva.jpg"));
			mapa.put("filtro", OrdemProducaoBO.getInstance().dadosFiltro());

			for (int i = 0; i < ordens.size(); i++) {

				BigDecimal acumulador = new BigDecimal(0);
				try {
					itemProducaoListAux = ItemProducaoBO.getInstance().itemProducaoPorOrdemProducao(ordens.get(i));
				} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
					e.printStackTrace();
				}

				for (ItemProducao item : itemProducaoListAux) {
					acumulador = acumulador.add(item.getValorTotal());
				}

				ordens.get(i).setValorTotalOrdem(acumulador);

			}
			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, ordens, "ordem-producao-lista-resumida",
					"Arquivo", true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public boolean isAtrasado(OrdemProducao ordem) {
		if (ordem.getPrevisaoTermino().before(new Date()) && ordem.getSituacao().getDescricao() == "Andamento") {
			return true;
		} else {
			return false;
		}
	}

	public void clearItemProducao() {
		itemProducao = new ItemProducao();
	}

	public void clearItemAviamento() {
		itemAviamentoProducao = new ItemAviamentoProducao();
	}

	public String list() {
		try {
			ordemProducaos = OrdemProducaoBO.getInstance().ordemProducaoListReport(ordemProducaoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BigDecimal getValorTotalOrdem(OrdemProducao ordem) {
		BigDecimal acumulador = new BigDecimal(0);
		try {
			itemProducaoListAux = ItemProducaoBO.getInstance().itemProducaoPorOrdemProducao(ordem);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

		for (ItemProducao item : itemProducaoListAux) {
			acumulador = acumulador.add(item.getValorTotal());
		}

		return acumulador;
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		ordemProducao = new OrdemProducao();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (ordemProducao.getStatus()) {
				ordemProducao.setStatus(false);
				OrdemProducaoBO.getInstance().update(ordemProducao);
			} else {
				ordemProducao.setStatus(true);
				OrdemProducaoBO.getInstance().update(ordemProducao);
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
		String status = ordemProducao.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O ordemProducao est� " + status);
		return null;
	}

	public String removerItemProducao() {
		try {
			for (int i = 0; i < itemProducaoList.size(); i++) {
				if (itemProducaoList.get(i) == itemProducao) {
					itemProducaoListAux.add(itemProducaoList.get(i));
					itemProducaoList.remove(i);
					addWarnMessage("Grade removida com sucesso!", "");
					break;
				}
			}
			itemProducao = new ItemProducao();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemProducao() {
		try {
			OrdemProducaoBO.getInstance().validarQuantidadeEnvio(itemProducao.getQuantidade(), aguardandoEnvio);
			itemProducao.setUsuario(usuarioLogado);
			itemProducaoList.add(itemProducao);
			addInfoMessage("Item adicionada com sucesso!", "");
			itemProducao = new ItemProducao();
			zerarQuantidades();
		} catch (QuantidadeEnvioInvalidaException e) {
			addWarnMessage("", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemProducao() {
		try {
			for (int i = 0; i < itemProducaoList.size(); i++) {
				if (itemProducaoList.get(i) == itemProducao) {
					 OrdemProducaoBO.getInstance().validarQuantidadeEnvio(itemProducao.getQuantidade(),
					 aguardandoEnvio);
					itemProducao.setUsuario(usuarioLogado);
					itemProducaoList.set(i, itemProducao);
					addInfoMessage("Item atualizada com sucesso!", "");
					break;
				}
			}
			itemProducao = new ItemProducao();
			zerarQuantidades();
		} catch (Exception e) {
			addErrorMessage("Atenção", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return null;
	}

	public String addItemProducaoSimplificada() {
		itemProducaoList.add(itemProducao);
		addInfoMessage("Item adicionada com sucesso!", "");
		itemProducao = new ItemProducao();
		return null;
	}

	public String updateItemProducaoSimplificada() {
		for (int i = 0; i < itemProducaoList.size(); i++) {
			if (itemProducaoList.get(i) == itemProducao) {
				itemProducaoList.set(i, itemProducao);
				addInfoMessage("Item atualizada com sucesso!", "");
				break;
			}
		}
		itemProducao = new ItemProducao();
		return null;
	}

	public String removerItemAviamento() {
		try {
			for (int i = 0; i < itemAviamentoList.size(); i++) {
				if (itemAviamentoList.get(i) == itemAviamentoProducao) {
					itemAviamentoListAux.add(itemAviamentoList.get(i));
					itemAviamentoList.remove(i);
					addWarnMessage("Item removido com sucesso!", "");
					break;
				}
			}
			itemAviamentoProducao = new ItemAviamentoProducao();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public Integer quantidadeItens() {
		Integer valor = 0;
		for (int i = 0; i < itemProducaoList.size(); i++) {
			valor = valor + itemProducaoList.get(i).getQuantidade();
		}
		return valor;
	}

	public String addItemAviamento() {
		try {
			itemAviamentoProducao.setValorTotal(
					itemAviamentoProducao.getMateriaPrima().getValor().multiply(itemAviamentoProducao.getQuantidade()));
			itemAviamentoList.add(itemAviamentoProducao);
			addInfoMessage("Item adicionado com sucesso!", "");
			itemAviamentoProducao = new ItemAviamentoProducao();
		} catch (Exception e) {
			addWarnMessage("", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String updateItemAviamento() {
		try {
			for (int i = 0; i < itemAviamentoList.size(); i++) {
				if (itemAviamentoList.get(i) == itemAviamentoProducao) {
					itemAviamentoProducao.setValorTotal(itemAviamentoProducao.getMateriaPrima().getValor()
							.multiply(itemAviamentoProducao.getQuantidade()));
					itemAviamentoList.set(i, itemAviamentoProducao);
					addInfoMessage("Item atualizado com sucesso!", "");
					break;
				}
			}
			itemAviamentoProducao = new ItemAviamentoProducao();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public void saveAquisicaoSaida() {
		try {
			// aquisicaoSaida.setTotal(aquisicaoSaida.getQuantidade() *
			// quantidadeItens());
			aquisicaoSaida.setOrdemProducao(ordemProducao);
			AquisicaoSaidaBO.getInstance().save(aquisicaoSaida);

			aquisicaoSaida.getMateriaPrima().setSaldo(aquisicaoSaida.getMateriaPrima().getSaldo()
					.subtract(new BigDecimal(aquisicaoSaida.getQuantidade())));
			MateriaPrimaBO.getInstance().update(aquisicaoSaida.getMateriaPrima());

			addInfoMessage("Cadastrado com sucesso!", "");
			aquisicaoSaida = new AquisicaoSaida();

			carregarListaItemProducao();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
	}

	public SituacaoOrdem[] getSituacoes() {
		return SituacaoOrdem.values();
	}

	public TipoCostura[] getTiposCostura() {
		return TipoCostura.values();
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public void calcValorTotal() {
		itemProducao.setValorTotal(itemProducao.getValor().multiply(new BigDecimal(itemProducao.getQuantidade())));
	}

	public void atualizarQtdDias() {
		ordemProducao.setQtdDias(
				(int) CalcDias.calDias(ordemProducao.getDataCadastro(), ordemProducao.getPrevisaoTermino()));
	}

	public String formataProtocolo(OrdemProducao ordemProducao)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "OP" + OrdemProducaoBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(ordemProducao.getDataCadastro());
		return res;
	}

	public void carregarListaItemProducao() {
		try {
			itemProducaoList = ItemProducaoBO.getInstance().itemProducaoPorOrdemProducao(ordemProducao);
			itemReenviarProducaoList = ItemProducaoBO.getInstance().itemReenviarProducaoPorOrdemProducao(ordemProducao);
			historicoProducaoList = HistoricoProducaoBO.getInstance().historicoProducao(ordemProducao);
			historicoReenviarProducaoList = HistoricoProducaoBO.getInstance().historicoReenviadoProducao(ordemProducao);
			aquisicaoSaidaList = AquisicaoSaidaBO.getInstance().aquisicaoSaidaPorOrdemCorte(ordemProducao);
			ocorrenciaList = OcorrenciaOrdemProducaoBO.getInstance().historicoPorControlePedido(ordemProducao);
			itemAviamentoList = ItemAviamentoProducaoBO.getInstance().itemAviamentoProducaoPorOrdemCorte(ordemProducao);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public List<OcorrenciaOrdemProducao> carregarOcorrencias(OrdemProducao ordem) {
		try {
			return OcorrenciaOrdemProducaoBO.getInstance().historicoPorControlePedido(ordem);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveOcorrencia() {
		try {
			String protocolo = formataProtocoloOcorrencia();
			ocorrencia.setProtocolo(protocolo);
			ocorrencia.setUsuario(usuarioLogado);
			ocorrencia.setOrdemProducao(ordemProducao);

			OcorrenciaOrdemProducaoBO.getInstance().save(ocorrencia);

			ordemProducao.setStatusOcorrencia(1);
			OrdemProducaoBO.getInstance().update(ordemProducao);

			addInfoMessage("Cadastrado com sucesso!", "");
			ocorrencia = new OcorrenciaOrdemProducao();

			carregarListaItemProducao();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
	}

	public String finalizarOcorrencia() {
		try {

			String protocolo = formataProtocoloOcorrencia();
			ocorrencia.setProtocolo(protocolo);
			ocorrencia.setUsuario(usuarioLogado);
			ocorrencia.setOrdemProducao(ordemProducao);
			ocorrencia.setDescricao("Finalizando ocorrência!");
			OcorrenciaOrdemProducaoBO.getInstance().save(ocorrencia);

			ordemProducao.setStatusOcorrencia(2);
			OrdemProducaoBO.getInstance().update(ordemProducao);
			addInfoMessage("Ocorrencia finalizada com sucesso!", "");
			ocorrencia = new OcorrenciaOrdemProducao();
			carregarListaItemProducao();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String formataProtocoloOcorrencia()
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "OC" + OrdemProducaoBO.getInstance().geradorDeProtocoloOcorrencia() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(ocorrencia.getDataCadastro());
		return res;
	}

	public String receberItemEnviado(RowEditEvent event) {

		String enderecoRetorno = "";
		boolean finalizarOrdem = true;
		contaPagar = new ContaPagar();

		itemProducaoTemp = ((ItemProducao) event.getObject());
		try {
			if ((itemProducaoTemp.getProntasAux() + itemProducaoTemp.getDefeitoAux()) > itemProducaoTemp
					.getFaltaAux()) {
				addWarnMessage("Atenção!",
						"Falta apenas: " + itemProducaoTemp.getFaltaAux() + " peças a serem cortadas.");

			} else if (itemProducaoTemp.getProntasAux() == 0 && itemProducaoTemp.getDefeitoAux() == 0) {
//				addWarnMessage("Atenção!", "Quantidade de recebimento não pode ser zerado.");
				addWarnMessage("Atenção!", "Por favor, informe a quantidade de recebimentos ou de defeito.");
			} else {
				itemProducaoTemp.setProntas(itemProducaoTemp.getProntas() + itemProducaoTemp.getProntasAux());
				itemProducaoTemp.setDefeito(itemProducaoTemp.getDefeito() + itemProducaoTemp.getDefeitoAux());
				ItemProducaoBO.getInstance().update(itemProducaoTemp);
				addInfoMessage("Sucesso!", "");

				contaPagar.setItemProducao(itemProducaoTemp);
				contaPagar.setUsuario(usuarioLogado);
				contaPagar.setQtdRecebido(itemProducaoTemp.getProntasAux());
				contaPagar.setValor(new BigDecimal(itemProducaoTemp.getProntasAux())
						.multiply(itemProducaoTemp.getValor().add(itemProducaoTemp.getBonusAux())));

				contaPagar.setValorDevedor(new BigDecimal(itemProducaoTemp.getProntasAux())
						.multiply(itemProducaoTemp.getValor().add(itemProducaoTemp.getBonusAux())));

				contaPagar.setValorBonus(itemProducaoTemp.getBonusAux());

				ContaPagarBO.getInstance().save(contaPagar);

				carregarListaItemProducao();

				for (ItemProducao itemProducao : itemProducaoList) {
					if (!itemProducao.getQuantidade().equals(itemProducao.getProntas())) {
						finalizarOrdem = false;
						enderecoRetorno = "/private/lista/listarOrdemProducao.xhtml=faces-redirect=true";
					}
				}
				insertHistoricoProducao(itemProducaoTemp);

				if (finalizarOrdem) {
					ordemProducao.setSituacao(SituacaoOrdem.FINALIZADA);
					OrdemProducaoBO.getInstance().update(ordemProducao);
				}
			}

		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
		itemProducaoTemp = new ItemProducao();
		AfterRedirect.manterMensagem();
		return enderecoRetorno;
	}

	public String receberItemReenviado(RowEditEvent event) {

		String enderecoRetorno = "";

		itemProducaoTemp = ((ItemProducao) event.getObject());
		try {
			if ((itemProducaoTemp.getProntasAux() + itemProducaoTemp.getDefeitoAux()) > itemProducaoTemp
					.getFaltaAux()) {
				addWarnMessage("Atenção!",
						"Falta apenas: " + itemProducaoTemp.getFaltaAux() + " peças a serem cortadas.");

			} else if (itemProducaoTemp.getProntasAux() == 0) {
				addWarnMessage("Atenção!", "Quantidade de recebimento não pode ser zerado.");
			} else {
				itemProducaoTemp.setProntas(itemProducaoTemp.getProntas() + itemProducaoTemp.getProntasAux());
				itemProducaoTemp.setDefeito(itemProducaoTemp.getDefeito() + itemProducaoTemp.getDefeitoAux());
				ItemProducaoBO.getInstance().update(itemProducaoTemp);
				addInfoMessage("Sucesso!", "");

				carregarListaItemProducao();

				insertHistoricoProducao(itemProducaoTemp);

			}

		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
		itemProducaoTemp = new ItemProducao();
		AfterRedirect.manterMensagem();
		return enderecoRetorno;
	}

	public String onRowEditReenviar(RowEditEvent event) {

		itemProducaoTemp = ((ItemProducao) event.getObject());
		try {
			if (itemProducaoTemp.getProntas() < itemProducaoTemp.getQtdEnviarAux()) {
				addWarnMessage("Atenção!",
						"Quantidade informada para Reenviar é superior a quantidade de peças prontas.");

			} else if (itemProducaoTemp.getQtdEnviarAux() == 0) {
				addWarnMessage("Atenção!",
						"Quantidade informada para Reenviar não pode ser 0(Zero).");
			} else {

				itemProducaoReenviar.setOrdemProducao(ordemProducao);
				itemProducaoReenviar.setItemReenviado(true);
				itemProducaoReenviar.setQuantidade(itemProducaoTemp.getQtdEnviarAux());
				itemProducaoReenviar.setProduto(itemProducaoTemp.getProduto());
				itemProducaoReenviar.setJustificativaAux(itemProducaoTemp.getJustificativaAux());
				ItemProducaoBO.getInstance().save(itemProducaoReenviar);

				addInfoMessage("Item Reenviado com sucesso!", "");
				itemProducaoReenviar = new ItemProducao();
			}
		} catch (

		ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

		AfterRedirect.manterMensagem();

		return LIST;
	}

	public String fowEditSimplificada(RowEditEvent event) {

		String enderecoRetorno = "";
		boolean finalizarOrdem = true;

		itemProducaoTemp = ((ItemProducao) event.getObject());
		try {
			if (itemProducaoTemp.getProntasAux() > itemProducaoTemp.getFaltaAux()) {
				addWarnMessage("Atenção!",
						"Falta apenas: " + itemProducaoTemp.getFaltaAux() + " peças a serem cortadas.");
			} else {
				itemProducaoTemp.setProntas(itemProducaoTemp.getProntas() + itemProducaoTemp.getProntasAux());
				ItemProducaoBO.getInstance().update(itemProducaoTemp);
				addInfoMessage("Sucesso!", "");
			}

			carregarListaItemProducao();

			for (ItemProducao itemProducao : itemProducaoList) {
				if (!itemProducao.getQuantidade().equals(itemProducao.getProntas())) {
					finalizarOrdem = false;
					enderecoRetorno = "/private/lista/listarOrdemProducaoSimplificada.xhtml=faces-redirect=true";
				}
			}
			insertHistoricoProducao(itemProducaoTemp);

			if (finalizarOrdem) {
				ordemProducao.setSituacao(SituacaoOrdem.FINALIZADA);
				OrdemProducaoBO.getInstance().update(ordemProducao);
			}

		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		}
		itemProducaoTemp = new ItemProducao();
		AfterRedirect.manterMensagem();
		return enderecoRetorno;
	}

	private void insertHistoricoProducao(ItemProducao itemAux) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {

		HistoricoProducao historico = new HistoricoProducao();
		historico.setUsuario(usuarioLogado);
		// historico.setFaccao(itemAux.getFaccao());
		historico.setFaccao(ordemProducao.getFaccao());
		historico.setProduto(itemAux.getProduto());
		historico.setQtdRecebido(itemAux.getProntasAux());
		historico.setQtdDefeito(itemAux.getDefeitoAux());
		historico.setValorBonus(itemAux.getBonusAux());
		historico.setOrdemProducao(ordemProducao);
		historico.setItemReenviado(itemAux.getItemReenviado());
//		historico.setJustificativa(itemAux.getJustificativaAux());

		HistoricoProducaoBO.getInstance().save(historico);
	}

	public void updateQuantidades() {
		cortadas = QueryUtilBO.getInstance().quantidadeCortadasPorProduto(itemProducao.getProduto()).intValue();
		enviadas = QueryUtilBO.getInstance().quantidadeEnviadasPorProduto(itemProducao.getProduto());
		recebidas = QueryUtilBO.getInstance().quantidadeRecebidasPorProduto(itemProducao.getProduto());

		aguardandoEnvio = cortadas - enviadas;
		aguardandoRec = enviadas - recebidas;
	}

	public void zerarQuantidades() {
		cortadas = 0;
		enviadas = 0;
		recebidas = 0;
		aguardandoEnvio = 0;
		aguardandoRec = 0;
	}

	public OrdemProducao getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public OrdemProducao getOrdemProducaoFilter() {
		return ordemProducaoFilter;
	}

	public void setOrdemProducaoFilter(OrdemProducao ordemProducaoFilter) {
		this.ordemProducaoFilter = ordemProducaoFilter;
	}

	public List<OrdemProducao> getOrdemProducaos() {
		return ordemProducaos;
	}

	public void setOrdemProducaos(List<OrdemProducao> ordemProducaos) {
		this.ordemProducaos = ordemProducaos;
	}

	public ItemProducao getItemProducao() {
		return itemProducao;
	}

	public void setItemProducao(ItemProducao itemProducao) {
		this.itemProducao = itemProducao;
	}

	public List<ItemProducao> getItemProducaoList() {
		return itemProducaoList;
	}

	public void setItemProducaoList(List<ItemProducao> itemProducaoList) {
		this.itemProducaoList = itemProducaoList;
	}

	public List<ItemProducao> getItemProducaoListAux() {
		return itemProducaoListAux;
	}

	public void setItemProducaoListAux(List<ItemProducao> itemProducaoListAux) {
		this.itemProducaoListAux = itemProducaoListAux;
	}

	public Integer getCortadas() {
		return cortadas;
	}

	public void setCortadas(Integer cortadas) {
		this.cortadas = cortadas;
	}

	public Integer getEnviadas() {
		return enviadas;
	}

	public void setEnviadas(Integer enviadas) {
		this.enviadas = enviadas;
	}

	public Integer getRecebidas() {
		return recebidas;
	}

	public void setRecebidas(Integer recebidas) {
		this.recebidas = recebidas;
	}

	public Integer getAguardandoEnvio() {
		return aguardandoEnvio;
	}

	public void setAguardandoEnvio(Integer aguardandoEnvio) {
		this.aguardandoEnvio = aguardandoEnvio;
	}

	public Integer getAguardandoRec() {
		return aguardandoRec;
	}

	public void setAguardandoRec(Integer aguardandoRec) {
		this.aguardandoRec = aguardandoRec;
	}

	public ContaPagar getContaPagar() {
		return contaPagar;
	}

	public void setContaPagar(ContaPagar contaPagar) {
		this.contaPagar = contaPagar;
	}

	public List<HistoricoProducao> getHistoricoProducaoList() {
		return historicoProducaoList;
	}

	public void setHistoricoProducaoList(List<HistoricoProducao> historicoProducaoList) {
		this.historicoProducaoList = historicoProducaoList;
	}

	public ItemAviamentoProducao getItemAviamentoProducao() {
		return itemAviamentoProducao;
	}

	public void setItemAviamentoProducao(ItemAviamentoProducao itemAviamentoProducao) {
		this.itemAviamentoProducao = itemAviamentoProducao;
	}

	public List<ItemAviamentoProducao> getItemAviamentoList() {
		return itemAviamentoList;
	}

	public void setItemAviamentoList(List<ItemAviamentoProducao> itemAviamentoList) {
		this.itemAviamentoList = itemAviamentoList;
	}

	public List<ItemAviamentoProducao> getItemAviamentoListAux() {
		return itemAviamentoListAux;
	}

	public void setItemAviamentoListAux(List<ItemAviamentoProducao> itemAviamentoListAux) {
		this.itemAviamentoListAux = itemAviamentoListAux;
	}

	public AquisicaoSaida getAquisicaoSaida() {
		return aquisicaoSaida;
	}

	public void setAquisicaoSaida(AquisicaoSaida aquisicaoSaida) {
		this.aquisicaoSaida = aquisicaoSaida;
	}

	public List<AquisicaoSaida> getAquisicaoSaidaList() {
		return aquisicaoSaidaList;
	}

	public void setAquisicaoSaidaList(List<AquisicaoSaida> aquisicaoSaidaList) {
		this.aquisicaoSaidaList = aquisicaoSaidaList;
	}

	public OcorrenciaOrdemProducao getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(OcorrenciaOrdemProducao ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public List<OcorrenciaOrdemProducao> getOcorrenciaList() {
		return ocorrenciaList;
	}

	public void setOcorrenciaList(List<OcorrenciaOrdemProducao> ocorrenciaList) {
		this.ocorrenciaList = ocorrenciaList;
	}

	public ItemProducao getItemProducaoReenviar() {
		return itemProducaoReenviar;
	}

	public void setItemProducaoReenviar(ItemProducao itemProducaoReenviar) {
		this.itemProducaoReenviar = itemProducaoReenviar;
	}

	public List<ItemProducao> getItemReenviarProducaoList() {
		return itemReenviarProducaoList;
	}

	public void setItemReenviarProducaoList(List<ItemProducao> itemReenviarProducaoList) {
		this.itemReenviarProducaoList = itemReenviarProducaoList;
	}

	public List<HistoricoProducao> getHistoricoReenviarProducaoList() {
		return historicoReenviarProducaoList;
	}

	public void setHistoricoReenviarProducaoList(List<HistoricoProducao> historicoReenviarProducaoList) {
		this.historicoReenviarProducaoList = historicoReenviarProducaoList;
	}

	public List<ItemProducaoAud> getItemAudProducaoList() {
		return itemAudProducaoList;
	}

	public void setItemAudProducaoList(List<ItemProducaoAud> itemAudProducaoList) {
		this.itemAudProducaoList = itemAudProducaoList;
	}

}
