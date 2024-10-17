package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.ResumoCartaoBO;
import br.com.controlpro.entity.consultas.ResumoCartao;
import br.com.controlpro.entity.consultas.ResumoCartaoTotal;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class ResumoCartaoMB implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;


	private ResumoCartao resumoCartao;
	private List<ResumoCartao> resumoCartaoList;
	private List<ResumoCartaoTotal> resumoCartaoTotalList;

	@PostConstruct
	public void init() {
		resumoCartao = new ResumoCartao();
		resumoCartaoList = new ArrayList<>();
		resumoCartaoTotalList = new ArrayList<>();
	}

	public String list() {
		try {
			resumoCartaoList = ResumoCartaoBO.getInstance().resumoCartao(resumoCartao);
			resumoCartaoTotalList = ResumoCartaoBO.getInstance().resumoCartaoTotal(resumoCartao);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		list();
		mapa.put("resumo-cartao-total", resumoCartaoTotalList);

		resumoCartao = new ResumoCartao();
		GenericReport
				.gerarPdfComJRBeanCollectionDataSource(
						mapa,
						resumoCartaoList,
						"resumo-cartao",
						"Relatório de Resumo de Cartão - Gerado em: "
								+ DataUtil.formatarData(new Date()), true);
	}
	
//	public Integer getTotalQuantidade() {
//		Integer valor = 0;
//		for (ResumoCartao item : resumoCartaoList) {
//			valor = valor + item.getQuantidade();
//		}
//		return valor;
//	}

	public ResumoCartao getResumoCartao() {
		return resumoCartao;
	}

	public void setResumoCartao(ResumoCartao resumoCartao) {
		this.resumoCartao = resumoCartao;
	}
	
	public List<ResumoCartao> getResumoCartaoList() {
		return resumoCartaoList;
	}

	public void setResumoCartaoList(List<ResumoCartao> resumoCartaoList) {
		this.resumoCartaoList = resumoCartaoList;
	}

	public List<ResumoCartaoTotal> getResumoCartaoTotalList() {
		return resumoCartaoTotalList;
	}

	public void setResumoCartaoTotalList(List<ResumoCartaoTotal> resumoCartaoTotalList) {
		this.resumoCartaoTotalList = resumoCartaoTotalList;
	}
	
}