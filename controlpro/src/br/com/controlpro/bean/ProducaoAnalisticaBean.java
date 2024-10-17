package br.com.controlpro.bean;

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

import br.com.controlpro.bo.FaccaoBO;
import br.com.controlpro.bo.ProducaoAnalisticaBO;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.ItemProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.BuscaNoWebContent;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class ProducaoAnalisticaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private List<ItemProducao> itemProducaoList = new ArrayList<ItemProducao>();
	private List<HistoricoProducao> historicoProducaoList = new ArrayList<HistoricoProducao>();
	private List<HistoricoConta> pagamentosProducaoList = new ArrayList<HistoricoConta>();
	
	private Faccao faccaoFilter = new Faccao();
	private String protocoloFilter = null;
	
	// TOTAIS ITENS PRODUÇÃO
	private Integer qtdEnviado;
	private BigDecimal totalItens;
	
	// TOTAIS RECEBIMENTOS PRODUÇÃO
	private Integer qtdRecebida;
	private Integer qtdDefeito;

	// TOTAIS PAGAMENTOS PRODUÇÃO
	private BigDecimal totalConta;
	private BigDecimal totalDesconto;
	private BigDecimal totalBonus;
	private BigDecimal totalContaTotal;
	private BigDecimal totalPago;
	
	@PostConstruct
	public void init() {

	}

	public String list() {
		try {
			itemProducaoList = ProducaoAnalisticaBO.getInstance().itens(protocoloFilter, faccaoFilter);
			historicoProducaoList = ProducaoAnalisticaBO.getInstance().historico(protocoloFilter, faccaoFilter);
			pagamentosProducaoList = ProducaoAnalisticaBO.getInstance().pagamentos(protocoloFilter, faccaoFilter);
			
			atualizandoValores();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String gerarPDFProducaoAnalistica() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<String> producaoAnalistica = new ArrayList<String>();
		producaoAnalistica.add("");

		mapa.put("itens-producao-analistica", itemProducaoList);
		mapa.put("recebimentos-producao-analistica", historicoProducaoList);
		mapa.put("pagamentos-producao-analistica", pagamentosProducaoList);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, producaoAnalistica, "producao-analistica-geral",
				"Produção Analistica - " + new Date(), true);
		return null;
	}

	
	public void atualizandoValores() {
		
		// TOTAIS ITENS PRODUÇÃO
		qtdEnviado = 0;
		totalItens = new BigDecimal(0);
		
		for (ItemProducao item : itemProducaoList) {
			qtdEnviado = qtdEnviado + item.getQuantidade();
			totalItens = totalItens.add(item.getValorTotal());
		}
		
		// TOTAIS RECEBIMENTOS PRODUÇÃO
		qtdRecebida = 0;
		qtdDefeito = 0;

		for (HistoricoProducao recebimento : historicoProducaoList) {
			qtdRecebida = qtdRecebida + recebimento.getQtdRecebido();
			qtdDefeito = qtdDefeito + recebimento.getQtdDefeito();
		}
		
		// TOTAIS PAGAMENTOS PRODUÇÃO
		totalConta = new BigDecimal(0);
		totalDesconto = new BigDecimal(0);
		totalBonus = new BigDecimal(0);
		totalContaTotal = new BigDecimal(0);
		totalPago = new BigDecimal(0);
		
		for (HistoricoConta conta : pagamentosProducaoList) {
			totalConta = totalConta.add(conta.getContaPagar().getValor().subtract(conta.getValorJuros()));
			totalDesconto = totalDesconto.add(conta.getValorDesconto());
			totalBonus = totalBonus.add(conta.getValorJuros());
			totalContaTotal = totalContaTotal.add(conta.getContaPagar().getValor().subtract(conta.getValorDesconto()));
			totalPago = totalPago.add(conta.getValorPago());
			
		}
	}
	
	public List<Faccao> getFaccaos() {

		try {
			return FaccaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ItemProducao> getItemProducaoList() {
		return itemProducaoList;
	}

	public void setItemProducaoList(List<ItemProducao> itemProducaoList) {
		this.itemProducaoList = itemProducaoList;
	}

	public Faccao getFaccaoFilter() {
		return faccaoFilter;
	}

	public void setFaccaoFilter(Faccao faccaoFilter) {
		this.faccaoFilter = faccaoFilter;
	}
	
	public String getProtocoloFilter() {
		return protocoloFilter;
	}
	
	public void setProtocoloFilter(String protocoloFilter) {
		this.protocoloFilter = protocoloFilter;
	}
	
	public List<HistoricoProducao> getHistoricoProducaoList() {
		return historicoProducaoList;
	}
	
	public void setHistoricoProducaoList(List<HistoricoProducao> historicoProducaoList) {
		this.historicoProducaoList = historicoProducaoList;
	}
	
	public List<HistoricoConta> getPagamentosProducaoList() {
		return pagamentosProducaoList;
	}
	
	public void setPagamentosProducaoList(List<HistoricoConta> pagamentosProducaoList) {
		this.pagamentosProducaoList = pagamentosProducaoList;
	}

	public Integer getQtdEnviado() {
		return qtdEnviado;
	}

	public void setQtdEnviado(Integer qtdEnviado) {
		this.qtdEnviado = qtdEnviado;
	}

	public BigDecimal getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(BigDecimal totalItens) {
		this.totalItens = totalItens;
	}

	public Integer getQtdRecebida() {
		return qtdRecebida;
	}

	public void setQtdRecebida(Integer qtdRecebida) {
		this.qtdRecebida = qtdRecebida;
	}

	public Integer getQtdDefeito() {
		return qtdDefeito;
	}

	public void setQtdDefeito(Integer qtdDefeito) {
		this.qtdDefeito = qtdDefeito;
	}

	public BigDecimal getTotalConta() {
		return totalConta;
	}

	public void setTotalConta(BigDecimal totalConta) {
		this.totalConta = totalConta;
	}

	public BigDecimal getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(BigDecimal totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	public BigDecimal getTotalBonus() {
		return totalBonus;
	}

	public void setTotalBonus(BigDecimal totalBonus) {
		this.totalBonus = totalBonus;
	}

	public BigDecimal getTotalContaTotal() {
		return totalContaTotal;
	}

	public void setTotalContaTotal(BigDecimal totalContaTotal) {
		this.totalContaTotal = totalContaTotal;
	}

	public BigDecimal getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
	}
	
}