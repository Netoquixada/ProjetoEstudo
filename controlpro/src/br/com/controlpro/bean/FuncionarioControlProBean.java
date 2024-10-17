package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import br.com.controlpro.bo.FuncionarioControlProBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.FuncionarioControlPro;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class FuncionarioControlProBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarFuncionarioControlPro.xhtml";
	private final String LIST = "/private/lista/listarFuncionarioControlPro.xhtml?faces-redirect=true";

	private FuncionarioControlPro funcionarioControlPro;
	private FuncionarioControlPro funcionarioControlProFilter;
	private List<FuncionarioControlPro> funcionarioControlPros;

	@PostConstruct
	public void init() {
		funcionarioControlPro = new FuncionarioControlPro();
		funcionarioControlProFilter = new FuncionarioControlPro();
		funcionarioControlPros = new ArrayList<FuncionarioControlPro>();
	}

	public String save() {
		try {
			funcionarioControlPro.setSalarioTotal(funcionarioControlPro.getSalarioCt().add(funcionarioControlPro.getSalarioPf()));
			funcionarioControlPro.setStatus(true);
			FuncionarioControlProBO.getInstance().save(funcionarioControlPro);
			addInfoMessage("Cadastrado com sucesso!", "FuncionarioControlPro " + funcionarioControlPro.getNome());
			funcionarioControlPro = new FuncionarioControlPro();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {

			funcionarioControlPro.setSalarioTotal(funcionarioControlPro.getSalarioCt().add(funcionarioControlPro.getSalarioPf()));
			FuncionarioControlProBO.getInstance().update(funcionarioControlPro);
			addInfoMessage("Editado com sucesso!", "FuncionarioControlPro " + funcionarioControlPro.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		funcionarioControlPro = new FuncionarioControlPro();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String list() {
		try {
			funcionarioControlPros = FuncionarioControlProBO.getInstance().funcionarioControlProListReport(funcionarioControlProFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, funcionarioControlPros, "faccoes",
				"Relatório de facções - " + DataUtil.formatarData(new Date()), true);
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		funcionarioControlPro = new FuncionarioControlPro();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {
			if (funcionarioControlPro.getStatus()) {
				funcionarioControlPro.setStatus(false);
				FuncionarioControlProBO.getInstance().update(funcionarioControlPro);
			} else {
				funcionarioControlPro.setStatus(true);
				FuncionarioControlProBO.getInstance().update(funcionarioControlPro);
			}
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}
		String status = funcionarioControlPro.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O funcionarioControlPro est� " + status);
		return null;
	}
	
	public Loja[] getLojas() {
		return Loja.values();
	}
	
	public List<FuncionarioControlPro> getFuncionarioControlProAutoComplete(String nomeFuncionarioControlPro) {

		List<FuncionarioControlPro> faccoesComplete = new ArrayList<FuncionarioControlPro>();

		try {
			faccoesComplete = FuncionarioControlProBO.getInstance().funcionarioControlProsComplete(nomeFuncionarioControlPro);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return faccoesComplete;
	}
	
	public List<FuncionarioControlPro> getListaFuncionariosControlProList() {
		try {
			funcionarioControlPros  = FuncionarioControlProBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return funcionarioControlPros;
	}
	
	public FuncionarioControlPro getFuncionarioControlPro() {
		return funcionarioControlPro;
	}

	public void setFuncionarioControlPro(FuncionarioControlPro funcionarioControlPro) {
		this.funcionarioControlPro = funcionarioControlPro;
	}

	public FuncionarioControlPro getFuncionarioControlProFilter() {
		return funcionarioControlProFilter;
	}

	public void setFuncionarioControlProFilter(FuncionarioControlPro funcionarioControlProFilter) {
		this.funcionarioControlProFilter = funcionarioControlProFilter;
	}


	public List<FuncionarioControlPro> getFuncionarioControlPros() {
		return funcionarioControlPros;
	}

	public void setFuncionarioControlPros(List<FuncionarioControlPro> funcionarioControlPros) {
		this.funcionarioControlPros = funcionarioControlPros;
	}
	
}
