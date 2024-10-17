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

import br.com.controlpro.bo.FaccaoBO;
import br.com.controlpro.bo.HistoricoProducaoBO;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.report.GenericReport;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class HistoricoProducaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private HistoricoProducao historicoFilter;
	private List<HistoricoProducao> historicoProducaoList;
	private Long acumulador;
	private BigDecimal valorUnitario = new BigDecimal(0.0);
	private BigDecimal totalPagar = new BigDecimal(0.0);

	@PostConstruct
	public void init() {
		historicoFilter = new HistoricoProducao();
		historicoProducaoList = new ArrayList<>();
	}

	public void list() {
		try {
			historicoProducaoList = HistoricoProducaoBO.getInstance().listFiltro(historicoFilter);
			getRefreshValores();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
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

	public void getRefreshValores() {
		acumulador = 0L;
		for (HistoricoProducao item : historicoProducaoList) {
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

		historicoFilter = new HistoricoProducao();
		mapa.put("valorTotal", totalPagar);
		mapa.put("enviados", acumulador);
		mapa.put("valorUnitario", valorUnitario);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, historicoProducaoList, "ordem-producao-recebimento",
				"Arquivo", true);
	}

	public List<HistoricoProducao> getHistoricoProducaoList() {
		return historicoProducaoList;
	}

	public void setHistoricoProducaoList(List<HistoricoProducao> historicoProducaoList) {
		this.historicoProducaoList = historicoProducaoList;
	}

	public HistoricoProducao getHistoricoFilter() {
		return historicoFilter;
	}

	public void setHistoricoFilter(HistoricoProducao historicoFilter) {
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
