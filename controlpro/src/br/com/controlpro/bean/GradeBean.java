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

import br.com.controlpro.bo.GradeBO;
import br.com.controlpro.entity.Grade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.BuscaNoWebContent;
import br.com.controlpro.util.DataUtil;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class GradeBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Grade grade;
	private Grade gradeFilter;
	private LazyEntityDataModel<Grade> lazy;
	private List<Grade> grades;

	@PostConstruct
	public void init() {
		grade = new Grade();
		gradeFilter = new Grade();
		lazy = new LazyEntityDataModel<Grade>(Grade.class);
		grades = new ArrayList<Grade>();
	}

	public void save() {
		try {
			GradeBO.getInstance().save(grade);
			addInfoMessage("Cadastrado com sucesso!",
					"Grade " + grade.getNome());
			grade = new Grade();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario",
					"erro.salvar.entidade.campo.existente");
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
	}

	public String update() {
		try {
			GradeBO.getInstance().update(grade);
			addInfoMessage("Editado com sucesso!", "Grade " + grade.getNome());
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
		grade = new Grade();
		AfterRedirect.manterMensagem();
		return "/private/control/controlGrade.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = GradeBO.getInstance().gradesLazy(gradeFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (grade.getStatus()) {
				grade.setStatus(false);
				GradeBO.getInstance().update(grade);
			} else {
				grade.setStatus(true);
				GradeBO.getInstance().update(grade);
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
		String status = grade.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O grade está " + status);
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<Grade> grades = GradeBO.getInstance().gradeListReport(gradeFilter);

			gradeFilter = new Grade();
			mapa.put("logo", BuscaNoWebContent
					.buscaArquivo("/resources/imagens/logo-diva.jpg"));
			mapa.put("filtro", GradeBO.getInstance().dadosFiltro());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							grades,
							"grades",
							"Relatório de grades - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Grade getGradeFilter() {
		return gradeFilter;
	}

	public void setGradeFilter(Grade gradeFilter) {
		this.gradeFilter = gradeFilter;
	}

	public LazyEntityDataModel<Grade> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Grade> lazy) {
		this.lazy = lazy;
	}

	public List<Grade> getGrades() {
		try {
			grades = GradeBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
}
