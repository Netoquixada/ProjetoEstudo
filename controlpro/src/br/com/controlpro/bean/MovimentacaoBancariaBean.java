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

import br.com.controlpro.bo.MovimentacaoBancariaBO;
import br.com.controlpro.entity.consultas.MovimentacaoBancaria;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class MovimentacaoBancariaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;


	private MovimentacaoBancaria movimentacaoBancaria;
	private List<MovimentacaoBancaria> movimentacaoBancariaList;

	private BigDecimal credito;
	private BigDecimal debito;
	
	
	@PostConstruct
	public void init() {
		movimentacaoBancaria = new MovimentacaoBancaria();
		movimentacaoBancariaList = new ArrayList<>();
	}

	public String list() {
		try {
			movimentacaoBancariaList = MovimentacaoBancariaBO.getInstance().movimentacaoBancaria(movimentacaoBancaria);
			
			credito = new BigDecimal(0);
			debito = new BigDecimal(0);
			
			for (int i = 0; i < movimentacaoBancariaList.size(); i++) {
				credito = credito.add(movimentacaoBancariaList.get(i).getCredito());
				debito = debito.add(movimentacaoBancariaList.get(i).getDebito());
			}
			
			
			
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, movimentacaoBancariaList, "movimentacoes_bancarias",
				"Relatório de movimentações bancarias - " + DataUtil.formatarData(new Date()), true);
	}

	public MovimentacaoBancaria getMovimentacaoBancaria() {
		return movimentacaoBancaria;
	}

	public void setMovimentacaoBancaria(MovimentacaoBancaria movimentacaoBancaria) {
		this.movimentacaoBancaria = movimentacaoBancaria;
	}
	
	public List<MovimentacaoBancaria> getMovimentacaoBancariaList() {
		return movimentacaoBancariaList;
	}

	public void setMovimentacaoBancariaList(List<MovimentacaoBancaria> movimentacaoBancariaList) {
		this.movimentacaoBancariaList = movimentacaoBancariaList;
	}

	public BigDecimal getCredito() {
		return credito;
	}

	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}

	public BigDecimal getDebito() {
		return debito;
	}

	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}

}