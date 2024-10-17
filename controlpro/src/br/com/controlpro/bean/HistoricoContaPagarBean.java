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
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.HistoricoContaAcabamentoBO;
import br.com.controlpro.bo.HistoricoContaBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoContaAcabamento;
import br.com.controlpro.entity.HistoricoPagamentoResumo;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class HistoricoContaPagarBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private HistoricoConta historicoFilter;
	private HistoricoConta historicoConta;
	private HistoricoContaAcabamento historicoAcabamentoFilter;
	private List<HistoricoConta> historicoContaList;
	private List<HistoricoContaAcabamento> historicoContaAcabamentoList;
	private List<HistoricoPagamentoResumo> historicoContaResumidoList;
	private List<HistoricoPagamentoResumo> historicoContaAcabamentoResumidoList;
	private Long acumulador;
	private BigDecimal valorUnitario = new BigDecimal(0.0);
	private BigDecimal totalPagar = new BigDecimal(0.0);
	
	private Loja loja;

	@PostConstruct
	public void init() {
		historicoFilter = new HistoricoConta();
		historicoConta = new HistoricoConta();
		historicoAcabamentoFilter = new HistoricoContaAcabamento();
		historicoContaList = new ArrayList<>();
		historicoContaAcabamentoList = new ArrayList<>();
		historicoContaResumidoList = new ArrayList<>();
		historicoContaAcabamentoResumidoList = new ArrayList<>();
	}

	public void list() {
		try {
			historicoContaList = HistoricoContaBO.getInstance().listFiltro(historicoFilter, loja);
			historicoContaResumidoList = HistoricoContaBO.getInstance().pagamentosResumidos(historicoFilter);

			historicoAcabamentoFilter = new HistoricoContaAcabamento(historicoFilter.getFaccao(), historicoFilter.getDataPesquisaInicio(), 
					historicoFilter.getDataPesquisaFim());
			
			historicoContaAcabamentoList = HistoricoContaAcabamentoBO.getInstance().listFiltro(historicoAcabamentoFilter);
			historicoContaAcabamentoResumidoList = HistoricoContaAcabamentoBO.getInstance().pagamentosResumidos(historicoAcabamentoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void gerarPdfHistorico(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		List<String> listaAuxiliar = new ArrayList<>();
		listaAuxiliar.add("");
		mapa.put("historico-conta-pagar", historicoContaList);
		mapa.put("historico-conta-pagar-acabamento", historicoContaAcabamentoList);
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, listaAuxiliar, "historico-conta-geral",
				"Relatório de Historico de Pagamentos - " + DataUtil.formatarData(new Date()), true);
	}

	public BigDecimal getTotalPago() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (HistoricoConta item : historicoContaList) {
			acumulador = acumulador.add(item.getValorPago());
		}
		return acumulador;
	}

	public void gerarPdfHistoricoResumido(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		List<String> listaAuxiliar = new ArrayList<>();
		listaAuxiliar.add("");
		mapa.put("historico-conta-pagar-resumido", historicoContaResumidoList);
		mapa.put("historico-conta-pagar-resumido-acabamento", historicoContaAcabamentoResumidoList);
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, listaAuxiliar,
				"historico-conta-resumido-geral",
				"Relatório de Historico de Pagamentos Resumidos - " + DataUtil.formatarData(new Date()), true);
	}
	
	public void gerarPDFRecibo() {

		Map<String, Object> mapa = new HashMap<String, Object>();
		List<HistoricoConta> historicoContaList = new ArrayList<HistoricoConta>();

		historicoContaList.add(historicoConta);
//		historicoContaList.add(historicoConta);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, historicoContaList, "recibo-pagamento",
				"Recibo - " + historicoConta.getCodigoRecibo(), true);
	}

	public BigDecimal getTotalPagoResumido() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (HistoricoPagamentoResumo item : historicoContaResumidoList) {
			acumulador = acumulador.add(item.getTotal());
		}
		return acumulador;
	}
	
	public Loja[] getLojas() {
		return Loja.values();
	}

	public List<HistoricoConta> getHistoricoContaList() {
		return historicoContaList;
	}

	public void setHistoricoContaList(List<HistoricoConta> historicoContaList) {
		this.historicoContaList = historicoContaList;
	}

	public HistoricoConta getHistoricoFilter() {
		return historicoFilter;
	}

	public void setHistoricoFilter(HistoricoConta historicoFilter) {
		this.historicoFilter = historicoFilter;
	}

	public Long getAcumulador() {
		return acumulador;
	}

	public void setAcumulador(Long acumulador) {
		this.acumulador = acumulador;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}

	public List<HistoricoPagamentoResumo> getHistoricoContaResumidoList() {
		return historicoContaResumidoList;
	}

	public void setHistoricoContaResumidoList(List<HistoricoPagamentoResumo> historicoContaResumidoList) {
		this.historicoContaResumidoList = historicoContaResumidoList;
	}
	
	public List<HistoricoContaAcabamento> getHistoricoContaAcabamentoList() {
		return historicoContaAcabamentoList;
	}
	
	public void setHistoricoContaAcabamentoList(List<HistoricoContaAcabamento> historicoContaAcabamentoList) {
		this.historicoContaAcabamentoList = historicoContaAcabamentoList;
	}

	public HistoricoContaAcabamento getHistoricoAcabamentoFilter() {
		return historicoAcabamentoFilter;
	}

	public void setHistoricoAcabamentoFilter(HistoricoContaAcabamento historicoAcabamentoFilter) {
		this.historicoAcabamentoFilter = historicoAcabamentoFilter;
	}
	
	public List<HistoricoPagamentoResumo> getHistoricoContaAcabamentoResumidoList() {
		return historicoContaAcabamentoResumidoList;
	}
	
	public void setHistoricoContaAcabamentoResumidoList(
			List<HistoricoPagamentoResumo> historicoContaAcabamentoResumidoList) {
		this.historicoContaAcabamentoResumidoList = historicoContaAcabamentoResumidoList;
	}
	
	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	
	public HistoricoConta getHistoricoConta() {
		return historicoConta;
	}
	
	public void setHistoricoConta(HistoricoConta historicoConta) {
		this.historicoConta = historicoConta;
	}

}
