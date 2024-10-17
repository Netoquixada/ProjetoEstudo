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
import br.com.controlpro.bo.AcabamentoBO;
import br.com.controlpro.bo.AquisicaoSaidaAcabamentoBO;
import br.com.controlpro.bo.ContaPagarAcabamentoBO;
import br.com.controlpro.bo.HistoricoAcabamentoBO;
import br.com.controlpro.bo.ItemAcabamentoBO;
import br.com.controlpro.bo.ItemEnvolvidoAcabamentoBO;
import br.com.controlpro.bo.ItemProducaoBO;
import br.com.controlpro.bo.MateriaPrimaBO;
import br.com.controlpro.bo.OrdemProducaoBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.OrigemAcabamento;
import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.constants.StatusAcabamento;
import br.com.controlpro.constants.TipoCostura;
import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.AquisicaoSaidaAcabamento;
import br.com.controlpro.entity.ContaPagarAcabamento;
import br.com.controlpro.entity.HistoricoAcabamento;
import br.com.controlpro.entity.ItemAcabamento;
import br.com.controlpro.entity.ItemEnvolvidoAcabamento;
import br.com.controlpro.entity.ItemProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
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
public class AcabamentoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarAcabamento.xhtml";
	private final String LIST = "/private/lista/listarAcabamento.xhtml?faces-redirect=true";

	private Usuario usuarioLogado;

	private ContaPagarAcabamento contaPagarAcabamento;

	private Acabamento acabamento;
	private Acabamento acabamentoFilter;
	ItemAcabamento itemAcabamentoTemp;
	private List<Acabamento> acabamentos = new ArrayList<Acabamento>();

	private ItemAcabamento itemAcabamento;
	private List<ItemAcabamento> itemAcabamentoList;
	private List<ItemAcabamento> itemAcabamentoListAux;
	
	private ItemEnvolvidoAcabamento itemEnvolvidoAcabamento;
	private List<ItemEnvolvidoAcabamento>itemEnvolvidoAcabamentoList;
	private List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamentoListAux;

	private AquisicaoSaidaAcabamento aquisicaoSaidaAcabamento;
	private List<AquisicaoSaidaAcabamento> aquisicaoSaidaAcabamentoList;

	private List<HistoricoAcabamento> historicoAcabamentoList;

	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		acabamento = new Acabamento();
		acabamentoFilter = new Acabamento();
		try {
			acabamentos = AcabamentoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e1) {
			e1.printStackTrace();
		}

		itemAcabamento = new ItemAcabamento();
		itemAcabamentoTemp = new ItemAcabamento();
		itemAcabamentoList = new ArrayList<ItemAcabamento>();
		itemAcabamentoListAux = new ArrayList<ItemAcabamento>();

		itemEnvolvidoAcabamento = new ItemEnvolvidoAcabamento();
		itemEnvolvidoAcabamentoList = new ArrayList<ItemEnvolvidoAcabamento>();
		itemEnvolvidoAcabamentoListAux = new ArrayList<ItemEnvolvidoAcabamento>();
		
		historicoAcabamentoList = new ArrayList<>();

		aquisicaoSaidaAcabamento = new AquisicaoSaidaAcabamento();
		aquisicaoSaidaAcabamentoList = new ArrayList<>();

		Acabamento aux = RecuperarObjetoViaRequisicao.getObjeto(Acabamento.class, "id");
		acabamento = aux != null ? aux : new Acabamento();

		// CASO SEJA EDI��O DE DADOS
		if (acabamento.getId() != null) {
			try {
				itemAcabamentoList = ItemAcabamentoBO.getInstance().itemAcabamentoPorAcabamento(acabamento);
				itemEnvolvidoAcabamentoList = ItemEnvolvidoAcabamentoBO.getInstance().itemEnvolvidoAcabamentoPorAcabamento(acabamento);
				historicoAcabamentoList = HistoricoAcabamentoBO.getInstance().historicoAcabamento(acabamento);
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}

	}

	public String save() {
		try {
			String protocolo = formataProtocolo(acabamento);
			acabamento.setProtocolo(protocolo);
			acabamento.setSituacao(SituacaoOrdem.ANDAMENTO);
			acabamento.setItensAcabamento(itemAcabamentoList);
			acabamento.setItensEnvolvidosAcabamento(itemEnvolvidoAcabamentoList);
			AcabamentoBO.getInstance().save(acabamento);

			for (int i = 0; i < itemAcabamentoList.size(); i++) {
				itemAcabamentoList.get(i).setAcabamento(acabamento);
			}

			if (!itemAcabamentoList.isEmpty()) {
				ItemAcabamentoBO.getInstance().mergeAll(itemAcabamentoList);
			}

			for (int i = 0; i < itemEnvolvidoAcabamentoList.size(); i++) {
				itemEnvolvidoAcabamentoList.get(i).setAcabamento(acabamento);
			}
			
			if (!itemEnvolvidoAcabamentoList.isEmpty()) {
				ItemEnvolvidoAcabamentoBO.getInstance().mergeAll(itemEnvolvidoAcabamentoList);
			}

			addInfoMessage("Cadastrado com sucesso!", "");
			acabamento = new Acabamento();
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
			acabamento.setItensAcabamento(itemAcabamentoList);
			acabamento.setItensEnvolvidosAcabamento(itemEnvolvidoAcabamentoList);
			AcabamentoBO.getInstance().update(acabamento);

			for (int i = 0; i < itemAcabamentoList.size(); i++) {
				itemAcabamentoList.get(i).setAcabamento(acabamento);
			}

			if (!itemAcabamentoList.isEmpty()) {
				ItemAcabamentoBO.getInstance().mergeAll(itemAcabamentoList);
			}

			if (!itemAcabamentoListAux.isEmpty()) {
				ItemAcabamentoBO.getInstance().deleteAll(itemAcabamentoListAux);
			}
			
			for (int i = 0; i < itemEnvolvidoAcabamentoList.size(); i++) {
				itemEnvolvidoAcabamentoList.get(i).setAcabamento(acabamento);
			}
			
			if (!itemEnvolvidoAcabamentoList.isEmpty()) {
				ItemEnvolvidoAcabamentoBO.getInstance().mergeAll(itemEnvolvidoAcabamentoList);
			}
			
			if (!itemEnvolvidoAcabamentoListAux.isEmpty()) {
				ItemEnvolvidoAcabamentoBO.getInstance().deleteAll(itemEnvolvidoAcabamentoListAux);
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
		acabamento = new Acabamento();
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	public String gerarPDFAcabamento() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<Acabamento> acabamentos = new ArrayList<Acabamento>();
		
		acabamentos.add(acabamento);

		carregarListaItemAcabamento();

		mapa.put("logo", BuscaNoWebContent.buscaArquivo("/resources/imagens/logoglamix.jpg"));
		mapa.put("acabamento-itens", itemAcabamentoList);
		mapa.put("acabamento-historico", historicoAcabamentoList);
		mapa.put("acabamento-itens-envolvidos", itemEnvolvidoAcabamentoList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, acabamentos, "acabamento",
				"Acabamento - " + acabamento.getProtocolo(), true);
		return null;
	}

	public void finalizarOrdem() {

		try {
			acabamento.setSituacao(SituacaoOrdem.FINALIZADA);
			AcabamentoBO.getInstance().update(acabamento);
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

	public void clearItemAcabamento() {
		itemAcabamento = new ItemAcabamento();
	}

	public void clearItemEnvolvidoAcabamento() {
		itemEnvolvidoAcabamento = new ItemEnvolvidoAcabamento();
	}

	public StatusAcabamento[] getStatusAcabamentos() {
		return StatusAcabamento.values();
	}

	public OpcoesPagamento[] getOpcoesPagamento() {
		return OpcoesPagamento.values();
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (ItemAcabamento item : itemAcabamentoList) {
			acumulador = acumulador.add(item.getValorTotal());
		}
		return acumulador;
	}

	public SituacaoOrdem[] getSituacoes() {
		return SituacaoOrdem.values();
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public OrigemAcabamento[] getOrigens() {
		return OrigemAcabamento.values();
	}

	public String list() {
		try {
			acabamentos = AcabamentoBO.getInstance().acabamentoListReport(acabamentoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BigDecimal[] getValoresTotais(Acabamento acabamento) {
		BigDecimal acumulador = new BigDecimal(0);
		BigDecimal quantidade = new BigDecimal(0);
		BigDecimal[] retorno= new BigDecimal[2];
		try {
			itemAcabamentoListAux = ItemAcabamentoBO.getInstance().itemAcabamentoPorAcabamento(acabamento);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}

		for (ItemAcabamento item : itemAcabamentoListAux) {
			acumulador = acumulador.add(item.getValorTotal());
			quantidade = quantidade.add(new BigDecimal(item.getQuantidade()));
		}

		retorno[0] = acumulador;
		retorno[1] = quantidade;
		
		return retorno;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();

		list();

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, acabamentos, "acabamento-lista",
				"Relatório de acabamentos - " + DataUtil.formatarData(new Date()), true);
	}

	public String onRowEdit(RowEditEvent event) {

		String enderecoRetorno = "";
		boolean finalizarOrdem = true;
		contaPagarAcabamento = new ContaPagarAcabamento();

		itemAcabamentoTemp = ((ItemAcabamento) event.getObject());
		try {
			if (itemAcabamentoTemp.getProntasAux() > itemAcabamentoTemp.getFaltaAux()) {
				addWarnMessage("Atenção!",
						"Falta apenas: " + itemAcabamentoTemp.getFaltaAux() + " peças a serem acabadas.");
			} else {
				itemAcabamentoTemp.setProntas(itemAcabamentoTemp.getProntas() + itemAcabamentoTemp.getProntasAux());
				ItemAcabamentoBO.getInstance().update(itemAcabamentoTemp);
				addInfoMessage("Sucesso!", "");

				contaPagarAcabamento.setItemAcabamento(itemAcabamentoTemp);
				contaPagarAcabamento.setUsuario(usuarioLogado);
				contaPagarAcabamento.setQtdRecebido(itemAcabamentoTemp.getProntasAux());
				contaPagarAcabamento.setValor(
						itemAcabamentoTemp.getValor().multiply(new BigDecimal(itemAcabamentoTemp.getProntasAux())));
				contaPagarAcabamento.setValorDevedor(
						itemAcabamentoTemp.getValor().multiply(new BigDecimal(itemAcabamentoTemp.getProntasAux())));
				ContaPagarAcabamentoBO.getInstance().save(contaPagarAcabamento);

				carregarListaItemAcabamento();

				for (ItemAcabamento itemAcabamento : itemAcabamentoList) {
					if (itemAcabamento.getQuantidade() != itemAcabamento.getProntas()) {
						finalizarOrdem = false;
						enderecoRetorno = "/private/lista/listarAcabamento.xhtml=faces-redirect=true";
					}
				}

				insertHistoricoAcabamento(itemAcabamentoTemp);

				if (finalizarOrdem) {
					acabamento.setSituacao(SituacaoOrdem.FINALIZADA);
					AcabamentoBO.getInstance().update(acabamento);
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
		itemAcabamentoTemp = new ItemAcabamento();
		AfterRedirect.manterMensagem();
		return enderecoRetorno;
	}

	public String updatePagamento() {
		try {
			acabamento.setStatusAcabamento(StatusAcabamento.PAGO);
			AcabamentoBO.getInstance().update(acabamento);
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
		addInfoMessage("Pagamento realizado com sucesso!", "");
		return null;
	}

	private void insertHistoricoAcabamento(ItemAcabamento itemAux) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {

		HistoricoAcabamento historico = new HistoricoAcabamento();
		historico.setUsuario(usuarioLogado);
		historico.setFaccao(acabamento.getFaccao());
		historico.setProduto(itemAux.getProduto());
		historico.setQtdRecebido(itemAux.getProntasAux());
		historico.setAcabamento(acabamento);

		HistoricoAcabamentoBO.getInstance().save(historico);
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		acabamento = new Acabamento();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (acabamento.getStatus()) {
				acabamento.setStatus(false);
				AcabamentoBO.getInstance().update(acabamento);
			} else {
				acabamento.setStatus(true);
				AcabamentoBO.getInstance().update(acabamento);
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
		String status = acabamento.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O acabamento est� " + status);
		return null;
	}

	public String removerItemAcabamento() {
		try {
			for (int i = 0; i < itemAcabamentoList.size(); i++) {
				if (itemAcabamentoList.get(i) == itemAcabamento) {
					itemAcabamentoListAux.add(itemAcabamentoList.get(i));
					itemAcabamentoList.remove(i);
					addWarnMessage("Grade removida com sucesso!", "");
					break;
				}
			}
			itemAcabamento = new ItemAcabamento();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	public String addItemAcabamento() {
		itemAcabamentoList.add(itemAcabamento);
		addInfoMessage("Item adicionada com sucesso!", "");
		itemAcabamento = new ItemAcabamento();
		return null;
	}

	public String updateItemAcabamento() {
		for (int i = 0; i < itemAcabamentoList.size(); i++) {
			if (itemAcabamentoList.get(i) == itemAcabamento) {
				itemAcabamentoList.set(i, itemAcabamento);
				addInfoMessage("Item atualizada com sucesso!", "");
				break;
			}
		}
		itemAcabamento = new ItemAcabamento();
		return null;
	}
	
	public String removerItemEnvolvidoAcabamento() {
		try {
			for (int i = 0; i < itemEnvolvidoAcabamentoList.size(); i++) {
				if (itemEnvolvidoAcabamentoList.get(i) == itemEnvolvidoAcabamento) {
					itemEnvolvidoAcabamentoListAux.add(itemEnvolvidoAcabamentoList.get(i));
					itemEnvolvidoAcabamentoList.remove(i);
					addWarnMessage("Item removido com sucesso!", "");
					break;
				}
			}
			itemEnvolvidoAcabamento = new ItemEnvolvidoAcabamento();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}
	
	public String addItemEnvolvidoAcabamento() {
		itemEnvolvidoAcabamentoList.add(itemEnvolvidoAcabamento);
		addInfoMessage("Item adicionada com sucesso!", "");
		itemEnvolvidoAcabamento = new ItemEnvolvidoAcabamento();
		return null;
	}
	
	public String updateItemEnvolvidoAcabamento() {
		for (int i = 0; i < itemEnvolvidoAcabamentoList.size(); i++) {
			if (itemEnvolvidoAcabamentoList.get(i) == itemEnvolvidoAcabamento) {
				itemEnvolvidoAcabamentoList.set(i, itemEnvolvidoAcabamento);
				addInfoMessage("Item atualizado com sucesso!", "");
				break;
			}
		}
		itemEnvolvidoAcabamento = new ItemEnvolvidoAcabamento();
		return null;
	}

	public void calcValorTotal() {
		itemAcabamento
				.setValorTotal(itemAcabamento.getValor().multiply(new BigDecimal(itemAcabamento.getQuantidade())));
	}

	public void atualizarQtdDias() {
		acabamento.setQtdDias((int) CalcDias.calDias(acabamento.getDataCadastro(), acabamento.getPrevisaoTermino()));
	}

	public String formataProtocolo(Acabamento acabamento)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "AC" + AcabamentoBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(acabamento.getDataCadastro());
		return res;
	}

	public void carregarListaItemAcabamento() {
		try {
			itemAcabamentoList = ItemAcabamentoBO.getInstance().itemAcabamentoPorAcabamento(acabamento);
			itemEnvolvidoAcabamentoList = ItemEnvolvidoAcabamentoBO.getInstance().itemEnvolvidoAcabamentoPorAcabamento(acabamento);
			historicoAcabamentoList = HistoricoAcabamentoBO.getInstance().historicoAcabamento(acabamento);
			aquisicaoSaidaAcabamentoList = AquisicaoSaidaAcabamentoBO.getInstance().aquisicaoPorAcabamento(acabamento);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void saveAquisicaoSaidaAcabamento() {
		try {
			aquisicaoSaidaAcabamento.setAcabamento(acabamento);

			AquisicaoSaidaAcabamentoBO.getInstance().save(aquisicaoSaidaAcabamento);

			aquisicaoSaidaAcabamento.getMateriaPrima().setSaldo(aquisicaoSaidaAcabamento.getMateriaPrima().getSaldo()
					.subtract(new BigDecimal(aquisicaoSaidaAcabamento.getQuantidade())));
			MateriaPrimaBO.getInstance().update(aquisicaoSaidaAcabamento.getMateriaPrima());

			addInfoMessage("Cadastrado com sucesso!", "");
			aquisicaoSaidaAcabamento = new AquisicaoSaidaAcabamento();

			carregarListaItemAcabamento();
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

	public Acabamento getAcabamento() {
		return acabamento;
	}

	public void setAcabamento(Acabamento acabamento) {
		this.acabamento = acabamento;
	}

	public Acabamento getAcabamentoFilter() {
		return acabamentoFilter;
	}

	public void setAcabamentoFilter(Acabamento acabamentoFilter) {
		this.acabamentoFilter = acabamentoFilter;
	}

	public List<Acabamento> getAcabamentos() {
		return acabamentos;
	}

	public void setAcabamentos(List<Acabamento> acabamentos) {
		this.acabamentos = acabamentos;
	}

	public ItemAcabamento getItemAcabamento() {
		return itemAcabamento;
	}
	
	public TipoCostura[] getTiposCostura() {
		return TipoCostura.values();
	}

	public void setItemAcabamento(ItemAcabamento itemAcabamento) {
		this.itemAcabamento = itemAcabamento;
	}

	public List<ItemAcabamento> getItemAcabamentoList() {
		return itemAcabamentoList;
	}

	public void setItemAcabamentoList(List<ItemAcabamento> itemAcabamentoList) {
		this.itemAcabamentoList = itemAcabamentoList;
	}

	public List<ItemAcabamento> getItemAcabamentoListAux() {
		return itemAcabamentoListAux;
	}

	public void setItemAcabamentoListAux(List<ItemAcabamento> itemAcabamentoListAux) {
		this.itemAcabamentoListAux = itemAcabamentoListAux;
	}

	public AquisicaoSaidaAcabamento getAquisicaoSaidaAcabamento() {
		return aquisicaoSaidaAcabamento;
	}

	public void setAquisicaoSaidaAcabamento(AquisicaoSaidaAcabamento aquisicaoSaidaAcabamento) {
		this.aquisicaoSaidaAcabamento = aquisicaoSaidaAcabamento;
	}

	public List<AquisicaoSaidaAcabamento> getAquisicaoSaidaAcabamentoList() {
		return aquisicaoSaidaAcabamentoList;
	}

	public void setAquisicaoSaidaAcabamentoList(List<AquisicaoSaidaAcabamento> aquisicaoSaidaAcabamentoList) {
		this.aquisicaoSaidaAcabamentoList = aquisicaoSaidaAcabamentoList;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<HistoricoAcabamento> getHistoricoAcabamentoList() {
		return historicoAcabamentoList;
	}

	public void setHistoricoAcabamentoList(List<HistoricoAcabamento> historicoAcabamentoList) {
		this.historicoAcabamentoList = historicoAcabamentoList;
	}

	public ItemEnvolvidoAcabamento getItemEnvolvidoAcabamento() {
		return itemEnvolvidoAcabamento;
	}

	public void setItemEnvolvidoAcabamento(ItemEnvolvidoAcabamento itemEnvolvidoAcabamento) {
		this.itemEnvolvidoAcabamento = itemEnvolvidoAcabamento;
	}

	public List<ItemEnvolvidoAcabamento> getItemEnvolvidoAcabamentoList() {
		return itemEnvolvidoAcabamentoList;
	}

	public void setItemEnvolvidoAcabamentoList(List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamentoList) {
		this.itemEnvolvidoAcabamentoList = itemEnvolvidoAcabamentoList;
	}

	public List<ItemEnvolvidoAcabamento> getItemEnvolvidoAcabamentoListAux() {
		return itemEnvolvidoAcabamentoListAux;
	}

	public void setItemEnvolvidoAcabamentoListAux(List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamentoListAux) {
		this.itemEnvolvidoAcabamentoListAux = itemEnvolvidoAcabamentoListAux;
	}
	
}
