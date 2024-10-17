package br.com.controlpro.bean;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

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

import br.com.controlpro.acesso.EscopoSessaoBean;
import br.com.controlpro.bo.AdmCartaoBO;
import br.com.controlpro.bo.CaixaDiarioBO;
import br.com.controlpro.bo.ContaBancariaBO;
import br.com.controlpro.constants.Estados;
import br.com.controlpro.constants.OpcoesPagamento;
import br.com.controlpro.entity.CaixaDiario;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.entity.consultas.AdmCartao;
import br.com.controlpro.entity.consultas.ContaBancaria;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.com.controlpro.report.GenericReport;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.DataUtil;
import br.com.controlpro.util.ManagedBeanHelper;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

@ManagedBean
@RequestScoped
public class CaixaDiarioBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private CaixaDiario caixaDiario;
	private CaixaDiario caixaDiarioFilter;
	private List<CaixaDiario> caixaDiarios;

	private Usuario usuarioLogado;

	@PostConstruct
	public void init() {
		caixaDiario = new CaixaDiario();
		caixaDiarioFilter = new CaixaDiario();
		caixaDiarios = new ArrayList<CaixaDiario>();

		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();

		try {
			caixaDiarios = CaixaDiarioBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			if (this.caixaDiario.getNumeroPedido() != null && !this.caixaDiario.getNumeroPedido().isEmpty()) {
				CaixaDiarioBO.getInstance().validarNumeroPedido(this.caixaDiario.getNumeroPedido());
			}
			CaixaDiarioBO.getInstance().save(caixaDiario);
			addInfoMessage("Cadastrado com sucesso!", "CaixaDiario " + caixaDiario.getDescricao());
			caixaDiario = new CaixaDiario();
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
		} catch (QuantidadeEnvioInvalidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		AfterRedirect.manterMensagem();
	}

	public void update() {
		try {
			if (this.caixaDiario.getNumeroPedido() != null && !this.caixaDiario.getNumeroPedido().isEmpty()) {
				CaixaDiarioBO.getInstance().validarNumeroPedido(this.caixaDiario.getNumeroPedido());
			}
			CaixaDiarioBO.getInstance().update(caixaDiario);
			addInfoMessage("Editado com sucesso!", "CaixaDiario " + caixaDiario.getDescricao());
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
		} catch (QuantidadeEnvioInvalidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		caixaDiario = new CaixaDiario();
		AfterRedirect.manterMensagem();
//		return "/private/control/controlCaixaDiario.xhtml?faces-redirect=true";
	}

	public OpcoesPagamento[] getOpcoesPagamento() {
		return OpcoesPagamento.values();
	}
//	public String formataProtocolo(CaixaDiario caixaDiario)
//			throws NumberFormatException, SessaoNaoEncontradaParaEntidadeFornecidaException {
//		String res = CaixaDiarioBO.getInstance().geradorDeProtocolo() + "-"
//				+ new SimpleDateFormat("MM/yyyy").format(caixaDiario.getDataCaixaDiario());
//		return res;
//	}

	public List<ContaBancaria> contasBancarias() {
		try {
			return ContaBancariaBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<AdmCartao> admCartao() {
		try {
			return AdmCartaoBO.getInstance().list();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String list() {
		try {
			caixaDiarios = CaixaDiarioBO.getInstance().caixaDiarioListReport(caixaDiarioFilter);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("valorTotal", getTotal());
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, caixaDiarios, "caixaDiarios",
				"Relatório de caixaDiarios - " + DataUtil.formatarData(new Date()), true);
	}

	public BigDecimal getTotal() {
		BigDecimal acumulador = new BigDecimal(0.0);
		for (CaixaDiario item : caixaDiarios) {
			acumulador = acumulador.add(item.getValor());
		}
		return acumulador;
	}

	public String updateStatus() {
		try {
			if (caixaDiario.getStatus()) {
				caixaDiario.setStatus(false);
				CaixaDiarioBO.getInstance().update(caixaDiario);
			} else {
				caixaDiario.setStatus(true);
				CaixaDiarioBO.getInstance().update(caixaDiario);
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
		String status = caixaDiario.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O caixaDiario est� " + status);
		return null;
	}

	public Estados[] getEstados() {
		return Estados.values();
	}

	public CaixaDiario getCaixaDiario() {
		return caixaDiario;
	}

	public void setCaixaDiario(CaixaDiario caixaDiario) {
		this.caixaDiario = caixaDiario;
	}

	public CaixaDiario getCaixaDiarioFilter() {
		return caixaDiarioFilter;
	}

	public void setCaixaDiarioFilter(CaixaDiario caixaDiarioFilter) {
		this.caixaDiarioFilter = caixaDiarioFilter;
	}

	public List<CaixaDiario> getCaixaDiarios() {
		return caixaDiarios;
	}

	public void setCaixaDiarios(List<CaixaDiario> caixaDiarios) {
		this.caixaDiarios = caixaDiarios;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
