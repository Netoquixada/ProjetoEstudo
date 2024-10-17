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
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.ValeBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.constants.StatusVale;
import br.com.controlpro.entity.Vale;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@RequestScoped
public class ValePagamentosBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Vale vale;
	private Vale valeFilter;
	private List<Vale> vales;

	@PostConstruct
	public void init() {
		vale = new Vale();
		valeFilter = new Vale();
		vales = new ArrayList<Vale>();
	}

	public void gerarPDF(ActionEvent ev) {
			Map<String, Object> mapa = new HashMap<String, Object>();

			valeFilter = new Vale();
			mapa.put("filtro", ValeBO.getInstance().dadosFiltro());
			mapa.put("valorTotal", getTotal());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							vales,
							"vales",
							"Relatório de vales - "
									+ DataUtil.formatarData(new Date()), true);
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (Vale item : vales) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public String list() {
		try {
			vales = ValeBO.getInstance().valeListReportPagamentos(valeFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OpcoesPagamento[] getOpcoesPagamento(){
		return OpcoesPagamento.values();
	}

	public Estados[] getEstados() {
		return Estados.values();
	}

	public StatusVale[] getStatusValeList() {
		return StatusVale.values();
	}
	public Loja[] getLojas() {
		return Loja.values();
	}

	public Vale getVale() {
		return vale;
	}

	public void setVale(Vale vale) {
		this.vale = vale;
	}

	public Vale getValeFilter() {
		return valeFilter;
	}

	public void setValeFilter(Vale valeFilter) {
		this.valeFilter = valeFilter;
	}

	public List<Vale> getVales() {
		return vales;
	}

	public void setVales(List<Vale> vales) {
		this.vales = vales;
	}
}
