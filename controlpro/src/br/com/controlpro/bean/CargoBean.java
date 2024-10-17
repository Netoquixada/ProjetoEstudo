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

import br.com.controlpro.bo.CargoBO;
import br.com.controlpro.entity.Cargo;
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
public class CargoBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Cargo cargo;
	private Cargo cargoFilter;
	private LazyEntityDataModel<Cargo> lazy;
	private List<Cargo> cargos;

	@PostConstruct
	public void init() {
		cargo = new Cargo();
		cargoFilter = new Cargo();
		lazy = new LazyEntityDataModel<Cargo>(Cargo.class);
		cargos = new ArrayList<Cargo>();
	}

	public void save() {
		try {
			CargoBO.getInstance().save(cargo);
			addInfoMessage("Cadastrado com sucesso!",
					"Cargo " + cargo.getNome());
			cargo = new Cargo();
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
			CargoBO.getInstance().update(cargo);
			addInfoMessage("Editado com sucesso!", "Cargo " + cargo.getNome());
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
		cargo = new Cargo();
		AfterRedirect.manterMensagem();
		return "/private/control/controlCargo.xhtml?faces-redirect=true";
	}

	public String list() {
		lazy = CargoBO.getInstance().cargosLazy(cargoFilter);
		return null;
	}

	public String updateStatus() {
		try {
			if (cargo.getStatus()) {
				cargo.setStatus(false);
				CargoBO.getInstance().update(cargo);
			} else {
				cargo.setStatus(true);
				CargoBO.getInstance().update(cargo);
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
		String status = cargo.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O cargo está " + status);
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		try {
			Map<String, Object> mapa = new HashMap<String, Object>();

			List<Cargo> cargos = CargoBO.getInstance().cargoListReport(cargoFilter);

			cargoFilter = new Cargo();
			mapa.put("logo", BuscaNoWebContent
					.buscaArquivo("/resources/imagens/logo-diva.jpg"));
			mapa.put("filtro", CargoBO.getInstance().dadosFiltro());

			GenericReport
					.gerarPdfComJRBeanCollectionDataSource(
							mapa,
							cargos,
							"cargos",
							"Relatório de cargos - "
									+ DataUtil.formatarData(new Date()), true);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Cargo getCargoFilter() {
		return cargoFilter;
	}

	public void setCargoFilter(Cargo cargoFilter) {
		this.cargoFilter = cargoFilter;
	}

	public LazyEntityDataModel<Cargo> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Cargo> lazy) {
		this.lazy = lazy;
	}

	public List<Cargo> getCargos() {
		try {
			cargos = CargoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}
}
