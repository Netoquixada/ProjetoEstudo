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

import br.com.controlpro.bo.CidadeBO;
import br.com.controlpro.bo.RelacionamentoClienteBO;
import br.com.controlpro.entity.RelacionamentoCliente;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class RelacionamentoClienteMB implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private RelacionamentoCliente relacionamentoCliente;
	private List<RelacionamentoCliente> relacionamentoClienteList;
	private List<RelacionamentoCliente> relacionamentoClienteAux;
	private List<String> cidades;

	@PostConstruct
	public void init() {
		relacionamentoCliente = new RelacionamentoCliente();
		relacionamentoClienteList = new ArrayList<>();
		relacionamentoClienteAux = new ArrayList<>();
		cidades = new ArrayList<>();
	}

	public String list() {
		try {
			relacionamentoClienteList = new ArrayList<>();
			relacionamentoClienteAux = new ArrayList<>();
			relacionamentoClienteAux = RelacionamentoClienteBO.getInstance().relatorioRelacionamentoCLiente();

			for (RelacionamentoCliente relacionamentoCliente : relacionamentoClienteAux) {
				if (this.relacionamentoCliente.getCidade().isEmpty()) {
					if (relacionamentoCliente.getSaldo().compareTo(this.relacionamentoCliente.getSaldo()) == 1
							&& relacionamentoCliente.getUltimaCompra()
									.before(this.relacionamentoCliente.getUltimaCompra())) {
						relacionamentoClienteList.add(relacionamentoCliente);
					}
				}else {
					if (relacionamentoCliente.getSaldo().compareTo(this.relacionamentoCliente.getSaldo()) == 1
							&& relacionamentoCliente.getUltimaCompra()
							.before(this.relacionamentoCliente.getUltimaCompra())
							&& relacionamentoCliente.getCidade().equals(this.relacionamentoCliente.getCidade())) {
						relacionamentoClienteList.add(relacionamentoCliente);
					}
				}
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();

		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, relacionamentoClienteList, "relacionamento-cliente",
				"Listagem de cliente pra prospecção - " + DataUtil.formatarData(new Date()), true);
	}

	public RelacionamentoCliente getRelacionamentoCliente() {
		return relacionamentoCliente;
	}

	public void setRelacionamentoCliente(RelacionamentoCliente relacionamentoCliente) {
		this.relacionamentoCliente = relacionamentoCliente;
	}

	public List<RelacionamentoCliente> getRelacionamentoClienteList() {
		return relacionamentoClienteList;
	}

	public void setRelacionamentoClienteList(List<RelacionamentoCliente> relacionamentoClienteList) {
		this.relacionamentoClienteList = relacionamentoClienteList;
	}

	public List<RelacionamentoCliente> getRelacionamentoClienteAux() {
		return relacionamentoClienteAux;
	}

	public void setRelacionamentoClienteAux(List<RelacionamentoCliente> relacionamentoClienteAux) {
		this.relacionamentoClienteAux = relacionamentoClienteAux;
	}

	public List<String> getCidades() {
		cidades = CidadeBO.getInstance().cidadesVenda();
		return cidades;
	}

	public void setCidades(List<String> cidades) {
		this.cidades = cidades;
	}

//	public void gerarPDF(ActionEvent ev) {
//		try {
//			Map<String, Object> mapa = new HashMap<String, Object>();
//			List<RelacionamentoCliente> relatorioLista = RelacionamentoClienteBO.getInstance().relacionamentoCliente(relacionamentoCliente);
//
//			relacionamentoCliente = new RelacionamentoCliente();
//			GenericReport
//					.gerarPdfComJRBeanCollectionDataSource(
//							mapa,
//							relatorioLista,
//							"relatorio-sia",
//							"Relatório de produção ambulatorial - Gerado em: "
//									+ DataUtil.formatarData(new Date()), true);
//
//		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
//			e.printStackTrace();
//		}
//	}

}