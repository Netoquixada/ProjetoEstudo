package br.com.controlpro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.controlpro.bo.OcorrenciaOrdemProducaoBO;
import br.com.controlpro.entity.OcorrenciaOrdemProducao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@ManagedBean
@ViewScoped
public class OcorrenciaOrdemProducaoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private OcorrenciaOrdemProducao ocorrenciaOrdemProducaoFilter;
	private List<OcorrenciaOrdemProducao> ocorrenciaOrdemProducaoList;
	
	@PostConstruct
	public void init() {
		ocorrenciaOrdemProducaoFilter = new OcorrenciaOrdemProducao();
		ocorrenciaOrdemProducaoList = new ArrayList<>();
	}
	
	public void list(){
		try {
			ocorrenciaOrdemProducaoList = OcorrenciaOrdemProducaoBO.getInstance().ocorrenciasFilter(ocorrenciaOrdemProducaoFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public OcorrenciaOrdemProducao getOcorrenciaOrdemProducaoFilter() {
		return ocorrenciaOrdemProducaoFilter;
	}

	public void setOcorrenciaOrdemProducaoFilter(OcorrenciaOrdemProducao ocorrenciaOrdemProducaoFilter) {
		this.ocorrenciaOrdemProducaoFilter = ocorrenciaOrdemProducaoFilter;
	}

	public List<OcorrenciaOrdemProducao> getOcorrenciaOrdemProducaoList() {
		return ocorrenciaOrdemProducaoList;
	}

	public void setOcorrenciaOrdemProducaoList(List<OcorrenciaOrdemProducao> ocorrenciaOrdemProducaoList) {
		this.ocorrenciaOrdemProducaoList = ocorrenciaOrdemProducaoList;
	}
	
}
