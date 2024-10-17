package br.com.controlpro.bean;

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
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.AdiantamentoBO;
import br.com.controlpro.bo.ContaPagarAcabamentoBO;
import br.com.controlpro.bo.ContaPagarBO;
import br.com.controlpro.bo.HistoricoContaAcabamentoBO;
import br.com.controlpro.bo.HistoricoContaBO;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.SituacaoConta;
import br.com.controlpro.entity.Adiantamento;
import br.com.controlpro.entity.ContaPagar;
import br.com.controlpro.entity.ContaPagarAcabamento;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoContaAcabamento;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.ManagedBeanHelper;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class ContaPagarBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Usuario usuarioLogado;

	// CONTA PAGAR ORDEM DE PRODUÇÃO
	private ContaPagar contaPagar;
	private ContaPagar contaPagarFilter;
	private List<ContaPagar> contaPagars;
	private HistoricoConta historicoConta;
	private List<HistoricoConta> historicoContaList;

	// CONTA PAGAR ORDEM DE PRODUÇÃO
	private ContaPagarAcabamento contaPagarAcabamento;
	private ContaPagarAcabamento contaPagarAcabamentoFilter;
	private List<ContaPagarAcabamento> contaPagarsAcabamento;
	private HistoricoContaAcabamento historicoContaAcabamento;
	private List<HistoricoContaAcabamento> historicoContaAcabamentoList;

	private final String LIST = "/private/lista/listarContaPagar.xhtml?faces-redirect=true";

	@PostConstruct
	public void init() {

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		contaPagar = new ContaPagar();
		contaPagarFilter = new ContaPagar();
		contaPagars = new ArrayList<ContaPagar>();
		historicoConta = new HistoricoConta();
		historicoContaList = new ArrayList<HistoricoConta>();

		contaPagarAcabamento = new ContaPagarAcabamento();
		contaPagarAcabamentoFilter = new ContaPagarAcabamento();
		contaPagarsAcabamento = new ArrayList<ContaPagarAcabamento>();
		historicoContaAcabamento = new HistoricoContaAcabamento();
		historicoContaAcabamentoList = new ArrayList<HistoricoContaAcabamento>();
	}

	public String list() {
		try {
			contaPagars = ContaPagarBO.getInstance().contaPagarListReport(contaPagarFilter);

			contaPagarAcabamentoFilter = new ContaPagarAcabamento(contaPagarFilter.getSituacao(),
					contaPagarFilter.getItemProducao().getFaccao(), contaPagarFilter.getDataInicio(),
					contaPagarFilter.getDataFim());
			contaPagarsAcabamento = ContaPagarAcabamentoBO.getInstance()
					.contaPagarAcabamentoListReport(contaPagarAcabamentoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public OpcoesPagamento[] getOpcoesPagamento() {
		return OpcoesPagamento.values();
	}

	public String saveHistorico() {
		historicoConta.setContaPagar(contaPagar);
		historicoConta.setUsuario(usuarioLogado);
		historicoConta.setFaccao(contaPagar.getItemProducao().getOrdemProducao().getFaccao());
		try {

			HistoricoContaBO.getInstance().validarQuantidadeValorPago(getSaldoDevedorCalculado(),
					historicoConta.getValorPago());

			refreshValores();

			HistoricoContaBO.getInstance().save(historicoConta);
			ContaPagarBO.getInstance().update(contaPagar);

			addInfoMessage("Conta paga com sucesso!", "");
			clearCarregaHistoricoConta();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (QuantidadeEnvioInvalidaException e) {
			addWarnMessage(e.getMessage(), "");
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return LIST;
	}
	
	
	public void refreshValores() {
//		contaPagar.setValorDevedor(contaPagar.getValorDevedor().add(historicoConta.getValorJuros()));
		contaPagar.setValorDevedor(contaPagar.getValorDevedor().subtract(historicoConta.getValorDesconto()));

		contaPagar.setValorDevedor(contaPagar.getValorDevedor().subtract(historicoConta.getValorPago()));
		contaPagar.setValorPago(contaPagar.getValorPago().add(historicoConta.getValorPago()));

		if (contaPagar.getValorDevedor().doubleValue() == 0.0) {
			contaPagar.setSituacao(SituacaoConta.QUITADA);
		}
	}
	
	
	public String saveHistoricoAcabamento() {
		historicoContaAcabamento.setContaPagarAcabamento(contaPagarAcabamento);
		historicoContaAcabamento.setUsuario(usuarioLogado);
		historicoContaAcabamento.setFaccao(contaPagarAcabamento.getItemAcabamento().getAcabamento().getFaccao());
		try {
			
			HistoricoContaAcabamentoBO.getInstance().validarQuantidadeValorPago(getSaldoDevedorCalculadoAcabamento(),
					historicoContaAcabamento.getValorPago());
			
			refreshValoresAcabamento();
			
			HistoricoContaAcabamentoBO.getInstance().save(historicoContaAcabamento);
			ContaPagarAcabamentoBO.getInstance().update(contaPagarAcabamento);
			
			addInfoMessage("Conta Acabamento paga com sucesso!", "");
			clearCarregaHistoricoContaAcabamento();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
		} catch (QuantidadeEnvioInvalidaException e) {
			addWarnMessage(e.getMessage(), "");
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		List<String> listaAuxiliar = new ArrayList<>();
		listaAuxiliar.add("");
		
		mapa.put("valorTotal", getTotal());
		mapa.put("conta-pagar", contaPagars);
		mapa.put("conta-pagar-acabamento", contaPagarsAcabamento);
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, listaAuxiliar, "contas-geral",
				"Relatório de Contas a pagar - " + DataUtil.formatarData(new Date()), true);
	}
	
	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (ContaPagar item : contaPagars) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}
	public BigDecimal getTotalAcabamento() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (ContaPagarAcabamento item : contaPagarsAcabamento) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}
	
	public BigDecimal getAdiantamentosPendentes(Faccao faccao) {
		try {
			BigDecimal acumulador = new BigDecimal(0.0);
			List<Adiantamento> lista = AdiantamentoBO.getInstance().adiantamentosPendentesPorFaccao(faccao);
			
			for (Adiantamento item : lista) {
				acumulador = acumulador.add(item.getValor());
			}
			return acumulador;
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	public SituacaoConta[] getSituacoes() {
		return SituacaoConta.values();
	}

	
	public void refreshValoresAcabamento() {
		contaPagarAcabamento.setValorDevedor(contaPagarAcabamento.getValorDevedor().add(historicoContaAcabamento.getValorJuros()));
		contaPagarAcabamento.setValorDevedor(contaPagarAcabamento.getValorDevedor().subtract(historicoContaAcabamento.getValorDesconto()));
		
		contaPagarAcabamento.setValorDevedor(contaPagarAcabamento.getValorDevedor().subtract(historicoContaAcabamento.getValorPago()));
		contaPagarAcabamento.setValorPago(contaPagarAcabamento.getValorPago().add(historicoContaAcabamento.getValorPago()));
		
		if (contaPagarAcabamento.getValorDevedor().doubleValue() == 0.0) {
			contaPagarAcabamento.setSituacao(SituacaoConta.QUITADA);
		}
	}

	public BigDecimal getSaldoDevedorCalculado() {
		BigDecimal valor = new BigDecimal(0);
		BigDecimal valorAux = new BigDecimal(0);
		valorAux = contaPagar.getValorDevedor();

		valor = valorAux.add(historicoConta.getValorJuros()).subtract(historicoConta.getValorDesconto());

		return valor;

	}
	
	public BigDecimal getSaldoDevedorCalculadoAcabamento() {
		BigDecimal valor = new BigDecimal(0);
		BigDecimal valorAux = new BigDecimal(0);
		valorAux = contaPagarAcabamento.getValorDevedor();
		valor = valorAux.add(historicoContaAcabamento.getValorJuros()).subtract(historicoContaAcabamento.getValorDesconto());
		return valor;
	}

	public void clearCarregaHistoricoConta() {
		try {
			historicoConta = new HistoricoConta();
			historicoContaList = HistoricoContaBO.getInstance().historicoConta(contaPagar);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	public void clearCarregaHistoricoContaAcabamento() {
		try {
			historicoContaAcabamento = new HistoricoContaAcabamento();
			historicoContaAcabamentoList = HistoricoContaAcabamentoBO.getInstance().historicoContaAcabamento(contaPagarAcabamento);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public ContaPagar getContaPagar() {
		return contaPagar;
	}

	public void setContaPagar(ContaPagar contaPagar) {
		this.contaPagar = contaPagar;
	}

	public ContaPagar getContaPagarFilter() {
		return contaPagarFilter;
	}

	public void setContaPagarFilter(ContaPagar contaPagarFilter) {
		this.contaPagarFilter = contaPagarFilter;
	}

	public HistoricoConta getHistoricoConta() {
		return historicoConta;
	}

	public void setHistoricoConta(HistoricoConta historicoConta) {
		this.historicoConta = historicoConta;
	}

	public List<HistoricoConta> getHistoricoContaList() {
		return historicoContaList;
	}

	public void setHistoricoContaList(List<HistoricoConta> historicoContaList) {
		this.historicoContaList = historicoContaList;
	}

	public List<ContaPagar> getContaPagars() {
		return contaPagars;
	}

	public void setContaPagars(List<ContaPagar> contaPagars) {
		this.contaPagars = contaPagars;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ContaPagarAcabamento getContaPagarAcabamento() {
		return contaPagarAcabamento;
	}

	public void setContaPagarAcabamento(ContaPagarAcabamento contaPagarAcabamento) {
		this.contaPagarAcabamento = contaPagarAcabamento;
	}

	public ContaPagarAcabamento getContaPagarAcabamentoFilter() {
		return contaPagarAcabamentoFilter;
	}

	public void setContaPagarAcabamentoFilter(ContaPagarAcabamento contaPagarAcabamentoFilter) {
		this.contaPagarAcabamentoFilter = contaPagarAcabamentoFilter;
	}

	public List<ContaPagarAcabamento> getContaPagarsAcabamento() {
		return contaPagarsAcabamento;
	}

	public void setContaPagarsAcabamento(List<ContaPagarAcabamento> contaPagarsAcabamento) {
		this.contaPagarsAcabamento = contaPagarsAcabamento;
	}

	public HistoricoContaAcabamento getHistoricoContaAcabamento() {
		return historicoContaAcabamento;
	}

	public void setHistoricoContaAcabamento(HistoricoContaAcabamento historicoContaAcabamento) {
		this.historicoContaAcabamento = historicoContaAcabamento;
	}

	public List<HistoricoContaAcabamento> getHistoricoContaAcabamentoList() {
		return historicoContaAcabamentoList;
	}

	public void setHistoricoContaAcabamentoList(List<HistoricoContaAcabamento> historicoContaAcabamentoList) {
		this.historicoContaAcabamentoList = historicoContaAcabamentoList;
	}

}
