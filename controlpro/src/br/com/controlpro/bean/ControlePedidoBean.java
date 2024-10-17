package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.ComprovanteFreteBO;
import br.com.controlpro.bo.ControlePedidoBO;
import br.com.controlpro.bo.HistoricoControlePedidoBO;
import br.com.controlpro.bo.MovimentacaoProdutoBO;
import br.com.controlpro.bo.VendasBO;
import br.com.controlpro.constants.Envio;
import br.com.controlpro.constants.EnvioStatus;
import br.com.controlpro.entity.ComprovanteFrete;
import br.com.controlpro.entity.ControlePedido;
import br.com.controlpro.entity.HistoricoControlePedido;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.entity.consultas.MovimentacaoProduto;
import br.com.controlpro.entity.consultas.Vendas;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.SaidaNaoEncontradaException;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DownloadArquivoUtil;
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
public class ControlePedidoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarControlePedido.xhtml";
	private final String LIST = "/private/lista/listarControlePedido.xhtml?faces-redirect=true";
	private final String CAD_EDIT_GLAMIX = "/private/cadastro/cadastrarControlePedidoGlamix.xhtml";
	private final String LIST_GLAMIX = "/private/lista/listarControlePedidoGlamix.xhtml?faces-redirect=true";

	private ControlePedido controlePedido;
	private ControlePedido controlePedidoFilter;
	private List<ControlePedido> controlePedidos;

	private ComprovanteFrete comprovante;
	private List<ComprovanteFrete> comprovanteList;
	private List<ComprovanteFrete> comprovanteListAux;

	private Vendas venda;
	private StreamedContent arquivo;
	private List<MovimentacaoProduto> movimentacoes;
	private BigDecimal valorFrete = new BigDecimal(0);
	private BigDecimal valorFretePago = new BigDecimal(0);
	private BigDecimal valorPedido = new BigDecimal(0);

	private HistoricoControlePedido historicoControlePedido;
	private List<HistoricoControlePedido> historicoControlePedidoList;

	private Usuario usuarioLogado;
	
	@PostConstruct
	public void init() {
		controlePedido = new ControlePedido();
		controlePedidoFilter = new ControlePedido();
		controlePedidos = new ArrayList<ControlePedido>();
		movimentacoes = new ArrayList<MovimentacaoProduto>();
		venda = new Vendas();
		comprovante = new ComprovanteFrete();
		comprovanteList = new ArrayList<>();
		comprovanteListAux = new ArrayList<>();

		historicoControlePedido = new HistoricoControlePedido();
		historicoControlePedidoList = new ArrayList<>();

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		ControlePedido aux = RecuperarObjetoViaRequisicao.getObjeto(ControlePedido.class, "id");
		controlePedido = aux != null ? aux : new ControlePedido();

		// CASO SEJA EDI��O DE DADOS
		if (controlePedido.getId() != null) {
			try {
				comprovanteList = ComprovanteFreteBO.getInstance().comprovanteFretePorControlePedido(controlePedido);
				if(!controlePedido.getSequenciaVenda().isEmpty()) {
					movimentacoesProduto();
				}
			} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
				e.printStackTrace();
			}
		}
	}

	public String save() {
		try {
			ControlePedidoBO.getInstance().save(controlePedido);

			for (int i = 0; i < comprovanteList.size(); i++) {
				comprovanteList.get(i).setControlePedido(controlePedido);
			}

			if (!comprovanteList.isEmpty()) {
				ComprovanteFreteBO.getInstance().mergeAll(comprovanteList);
			}

			addInfoMessage("Cadastrado com sucesso!", "");
			controlePedido = new ControlePedido();
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
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {
			ControlePedidoBO.getInstance().update(controlePedido);

			for (int i = 0; i < comprovanteList.size(); i++) {
				comprovanteList.get(i).setControlePedido(controlePedido);
			}

			if (!comprovanteList.isEmpty()) {
				ComprovanteFreteBO.getInstance().mergeAll(comprovanteList);
			}

			if (!comprovanteListAux.isEmpty()) {
				ComprovanteFreteBO.getInstance().deleteAll(comprovanteListAux);
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
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (IntegridadeObjetoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		controlePedido = new ControlePedido();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public BigDecimal getTotalFrete() {
		BigDecimal valor = new BigDecimal(0);
		for (ControlePedido controlePedido : controlePedidos) {
			valor = valor.add(controlePedido.getValorFrete());
		}
		return valor;
	}

	public String list() {
		try {
			controlePedidos = ControlePedidoBO.getInstance().controlePedidoListReport(controlePedidoFilter, false);
			atualizandoValores();

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String listGlamix() {
		try {
			controlePedidos = ControlePedidoBO.getInstance().controlePedidoListReport(controlePedidoFilter, true);
			atualizandoValores();

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void atualizandoValores() {
		valorFrete = new BigDecimal(0);
		valorFretePago = new BigDecimal(0);
		valorPedido = new BigDecimal(0);
		for (ControlePedido controlePedido : controlePedidos) {
			valorFrete = valorFrete.add(controlePedido.getValorFrete());
			valorFretePago = valorFretePago.add(controlePedido.getValorFretePago());
			valorPedido = valorPedido.add(controlePedido.getValorPedidoGlamix());
		}
	}

	public String movimentacoesProduto() {
		try {
			venda = VendasBO.getInstance().vendaPorSequencia(controlePedido.getSequenciaVenda()).get(0);
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

	public String removerComprovante() {
		try {
			for (int i = 0; i < comprovanteList.size(); i++) {
				if (comprovanteList.get(i) == comprovante) {
					comprovanteListAux.add(comprovanteList.get(i));
					comprovanteList.remove(i);
					addWarnMessage("Comprovante removido com sucesso!", "");
					break;
				}
			}
			comprovante = new ComprovanteFrete();
		} catch (Exception e) {
			addErrorMessage("", e.getMessage());
		}
		return null;
	}

	// Upload do arquivo
	public void uploadFile(FileUploadEvent event) {
		try {
			this.comprovante.setExtensao(getExtensao(event.getFile().getFileName()));
			this.comprovante.setArquivo(event.getFile().getContents());
			this.comprovante.setNome(event.getFile().getFileName());
			comprovanteList.add(comprovante);
			FacesMessage message = new FacesMessage("Enviado com sucesso!", event.getFile().getFileName());
			FacesContext.getCurrentInstance().addMessage(null, message);
			comprovante = new ComprovanteFrete();
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

	public StreamedContent downloadArquivo() {
		try {
			arquivo = DownloadArquivoUtil.getInstance().downloadArquivo(
					ComprovanteFreteBO.getInstance().buscarArquivo(comprovante.getId()), arquivo, "Comprovante",
					comprovante.getExtensao());
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
		}
		return arquivo;
	}

	public Envio[] getEnvios() {
		return Envio.values();
	}

	public EnvioStatus[] getEnvioStatus() {
		return EnvioStatus.values();
	}

	public Envio[] getEnvio() {
		return Envio.values();
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		controlePedido = new ControlePedido();
		return CAD_EDIT;
	}

	public String goToListGlamix() {
		return LIST_GLAMIX;
	}

	public String prepareUpdateGlamix() {
		return CAD_EDIT_GLAMIX;
	}

	public String prepareSaveGlamix() {
		controlePedido = new ControlePedido();
		return CAD_EDIT_GLAMIX;
	}

	public String updateStatus() {
		try {
			if (controlePedido.getStatus()) {
				controlePedido.setStatus(false);
				ControlePedidoBO.getInstance().update(controlePedido);
			} else {
				controlePedido.setStatus(true);
				ControlePedidoBO.getInstance().update(controlePedido);
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
		String status = controlePedido.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O controlePedido est� " + status);
		return null;
	}

	public void carregarLista() {
		try {
			historicoControlePedidoList = HistoricoControlePedidoBO.getInstance()
					.historicoPorControlePedido(controlePedido);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public String saveHistoricoControlePedido() {
		try {
			String protocolo = formataProtocolo(historicoControlePedido);
			historicoControlePedido.setProtocolo(protocolo);
			historicoControlePedido.setUsuario(usuarioLogado);
			historicoControlePedido.setControlePedido(controlePedido);

			HistoricoControlePedidoBO.getInstance().save(historicoControlePedido);

			controlePedido.setStatusOcorrencia(1);
			ControlePedidoBO.getInstance().update(controlePedido);

			addInfoMessage("Cadastrado com sucesso!", "");
			historicoControlePedido = new HistoricoControlePedido();

			carregarLista();
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
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String finalizarOcorrencia() {
		try {

			String protocolo = formataProtocolo(historicoControlePedido);
			historicoControlePedido.setProtocolo(protocolo);
			historicoControlePedido.setUsuario(usuarioLogado);
			historicoControlePedido.setControlePedido(controlePedido);
			historicoControlePedido.setDescricao("Finalizando ocorrência!");
			HistoricoControlePedidoBO.getInstance().save(historicoControlePedido);

			controlePedido.setStatusOcorrencia(2);
			ControlePedidoBO.getInstance().update(controlePedido);
			addInfoMessage("Ocorrencia finalizada com sucesso!", "");
			historicoControlePedido = new HistoricoControlePedido();
			carregarLista();
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

	public String saveGlamix() {
		try {
			String protocolo = formataProtocoloGlamix(controlePedido);
			controlePedido.setProtocolo(protocolo);
			controlePedido.setSequenciaVenda("");
			ControlePedidoBO.getInstance().save(controlePedido);

			for (int i = 0; i < comprovanteList.size(); i++) {
				comprovanteList.get(i).setControlePedido(controlePedido);
			}

			if (!comprovanteList.isEmpty()) {
				ComprovanteFreteBO.getInstance().mergeAll(comprovanteList);
			}

			addInfoMessage("Cadastrado com sucesso!", "");
			controlePedido = new ControlePedido();
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
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT_GLAMIX;
	}

	public String updateGlamix() {
		try {
			ControlePedidoBO.getInstance().update(controlePedido);

			for (int i = 0; i < comprovanteList.size(); i++) {
				comprovanteList.get(i).setControlePedido(controlePedido);
			}

			if (!comprovanteList.isEmpty()) {
				ComprovanteFreteBO.getInstance().mergeAll(comprovanteList);
			}

			if (!comprovanteListAux.isEmpty()) {
				ComprovanteFreteBO.getInstance().deleteAll(comprovanteListAux);
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
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (IntegridadeObjetoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		controlePedido = new ControlePedido();
		AfterRedirect.manterMensagem();
		return LIST_GLAMIX;
	}

	public String formataProtocolo(HistoricoControlePedido historico)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "CP" + ControlePedidoBO.getInstance().geradorDeProtocolo() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(historico.getDataCadastro());
		return res;
	}

	public String formataProtocoloGlamix(ControlePedido controle)
			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		String res = "GM" + ControlePedidoBO.getInstance().geradorDeProtocoloGlamix() + "-"
				+ new SimpleDateFormat("MM/yyyy").format(controle.getDataCadastro());
		return res;
	}

	public ControlePedido getControlePedido() {
		return controlePedido;
	}

	public void setControlePedido(ControlePedido controlePedido) {
		this.controlePedido = controlePedido;
	}

	public ControlePedido getControlePedidoFilter() {
		return controlePedidoFilter;
	}

	public void setControlePedidoFilter(ControlePedido controlePedidoFilter) {
		this.controlePedidoFilter = controlePedidoFilter;
	}

	public List<ControlePedido> getControlePedidos() {
		return controlePedidos;
	}

	public void setControlePedidos(List<ControlePedido> controlePedidos) {
		this.controlePedidos = controlePedidos;
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

	public ComprovanteFrete getComprovante() {
		return comprovante;
	}

	public void setComprovante(ComprovanteFrete comprovante) {
		this.comprovante = comprovante;
	}

	public List<ComprovanteFrete> getComprovanteList() {
		return comprovanteList;
	}

	public void setComprovanteList(List<ComprovanteFrete> comprovanteList) {
		this.comprovanteList = comprovanteList;
	}

	public List<ComprovanteFrete> getComprovanteListAux() {
		return comprovanteListAux;
	}

	public void setComprovanteListAux(List<ComprovanteFrete> comprovanteListAux) {
		this.comprovanteListAux = comprovanteListAux;
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

	public BigDecimal getValorPedido() {
		return valorPedido;
	}

	public void setValorPedido(BigDecimal valorPedido) {
		this.valorPedido = valorPedido;
	}

	public HistoricoControlePedido getHistoricoControlePedido() {
		return historicoControlePedido;
	}

	public void setHistoricoControlePedido(HistoricoControlePedido historicoControlePedido) {
		this.historicoControlePedido = historicoControlePedido;
	}

	public List<HistoricoControlePedido> getHistoricoControlePedidoList() {
		return historicoControlePedidoList;
	}

	public void setHistoricoControlePedidoList(List<HistoricoControlePedido> historicoControlePedidoList) {
		this.historicoControlePedidoList = historicoControlePedidoList;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
