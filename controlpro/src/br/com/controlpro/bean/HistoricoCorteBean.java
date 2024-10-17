package br.com.controlpro.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.HistoricoCorteBO;
import br.com.controlpro.bo.OrdemCorteBO;
import br.com.controlpro.entity.HistoricoCorte;
import br.com.controlpro.entity.OrdemCorte;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.BuscaNoWebContent;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class HistoricoCorteBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private HistoricoCorte historicoFilter;
	private List<HistoricoCorte> historicoCorteList;
	private Long acumulador;
	private BigDecimal valorUnitario = new BigDecimal(0.0);
	private BigDecimal totalPagar = new BigDecimal(0.0);
	
	@PostConstruct
	public void init() {
		historicoFilter = new HistoricoCorte();
		historicoCorteList = new ArrayList<>();
	}
	
	public void list(){
		try {
			historicoCorteList = HistoricoCorteBO.getInstance().listFiltro(historicoFilter);
			getRefreshValores();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public void getRefreshValores() {
		acumulador = 0L;
		for (HistoricoCorte item : historicoCorteList) {
//			if (item.getStatusDespesaFixa().equals(StatusDespesaFixa.ATIVO)) {
				acumulador = acumulador + item.getQtdRecebido();
//			}
		}
		totalPagar = new BigDecimal(0);
		totalPagar = valorUnitario.multiply(new BigDecimal(acumulador));
	}
	
	public void gerarPDF(ActionEvent ev) {
			Map<String, Object> mapa = new HashMap<String, Object>();

			list();
			getRefreshValores();

			historicoFilter = new HistoricoCorte();
			mapa.put("filtro", OrdemCorteBO.getInstance().dadosFiltro());
			mapa.put("valorTotal", totalPagar);
			mapa.put("enviados", acumulador);
			mapa.put("valorUnitario", valorUnitario);

			GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, historicoCorteList, "ordem-corte-recebimento", "Arquivo", true);
	}

	public List<HistoricoCorte> getHistoricoCorteList() {
		return historicoCorteList;
	}
	
	public void setHistoricoCorteList(List<HistoricoCorte> historicoCorteList) {
		this.historicoCorteList = historicoCorteList;
	}
	
	public HistoricoCorte getHistoricoFilter() {
		return historicoFilter;
	}
	
	public void setHistoricoFilter(HistoricoCorte historicoFilter) {
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
	
}
