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

import br.com.controlpro.bo.CaixaBO;
import br.com.controlpro.bo.ConferenciaBO;
import br.com.controlpro.constants.TipoOperacao;
import br.com.controlpro.entity.Conferencia;
import br.com.controlpro.entity.consultas.Caixa;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class CaixaDetalhadaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508477123324402389L;

	private Caixa caixas;
	private Caixa caixasFilter;
	private List<Caixa> listaCaixas;
	private BigDecimal entrada;
	private BigDecimal saida;
	private BigDecimal saldo;

	@PostConstruct
	public void init() {
		caixas = new Caixa();
		caixasFilter = new Caixa();
		listaCaixas = new ArrayList<Caixa>();
		entrada = new BigDecimal(0);
		saida = new BigDecimal(0);
		saldo = new BigDecimal(0);
	}

	public String list() {
		try {
			listaCaixas = CaixaBO.getInstance().listDinheiro(caixasFilter);
			entrada = new BigDecimal(0);
			saida = new BigDecimal(0);
			saldo = new BigDecimal(0);
			for (int i = 0; i < listaCaixas.size(); i++) {
				if (listaCaixas.get(i).getDinheiro() > 0.0) {
					entrada = entrada.add(new BigDecimal(listaCaixas.get(i).getDinheiro()));
				} else if (listaCaixas.get(i).getDinheiro() < 0.0) {
					saida = saida.add(new BigDecimal(listaCaixas.get(i).getDinheiro()));
				}
			}
			saldo = entrada.subtract(new BigDecimal(Math.abs(saida.doubleValue())));
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();

		// List<Caixa> caixas =
		// CaixaBO.getInstance().conferenciaListReport(conferenciaFilter);

		// conferenciaFilter = new Conferencia();
		mapa.put("filtro", ConferenciaBO.getInstance().dadosFiltro());
		mapa.put("entrada", entrada);
		mapa.put("saida", saida);
		mapa.put("saldo", saldo);

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, listaCaixas, "caixa-detalhado",
				"Relatório de Caixa Detalhado - " + DataUtil.formatarData(new Date()), true);
	}

	public Caixa getCaixa() {
		return caixas;
	}

	public TipoOperacao[] getTipoOperacoes() {
		return TipoOperacao.values();
	}

	public void setCaixa(Caixa caixas) {
		this.caixas = caixas;
	}

	public List<Caixa> getListaCaixas() {
		return listaCaixas;
	}

	public void setListaCaixas(List<Caixa> listaCaixa) {
		this.listaCaixas = listaCaixa;
	}

	public Caixa getCaixaFilter() {
		return caixasFilter;
	}

	public void setCaixaFilter(Caixa caixasFilter) {
		this.caixasFilter = caixasFilter;
	}

	public Caixa getCaixas() {
		return caixas;
	}

	public void setCaixas(Caixa caixas) {
		this.caixas = caixas;
	}

	public Caixa getCaixasFilter() {
		return caixasFilter;
	}

	public void setCaixasFilter(Caixa caixasFilter) {
		this.caixasFilter = caixasFilter;
	}

	public BigDecimal getEntrada() {
		return entrada;
	}

	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getSaida() {
		return saida;
	}

	public void setSaida(BigDecimal saida) {
		this.saida = saida;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}
