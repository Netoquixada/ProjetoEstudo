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

import br.com.controlpro.bo.CofreBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.TipoMovimentacao;
import br.com.controlpro.entity.Cofre;
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
public class CofreBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	private Cofre cofre;
	private Cofre cofreFilter;
	private List<Cofre> cofres;

	private BigDecimal entrada;
	private BigDecimal saida;
	private BigDecimal saldo;

	@PostConstruct
	public void init() {
		cofre = new Cofre();
		cofreFilter = new Cofre();
		cofres = new ArrayList<Cofre>();
	}

	public void save() {
		try {
			CofreBO.getInstance().save(cofre);
			list();
			addInfoMessage("Cadastrado com sucesso!", "");
			cofre = new Cofre();
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
	}

	public void update() {
		try {
			CofreBO.getInstance().update(cofre);
			list();
			addInfoMessage("Editado com sucesso!", "");
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
		cofre = new Cofre();
	}

	public void prepareSave() {
		cofre = new Cofre();
	}

	public TipoMovimentacao[] getTipoMovimentacoes() {
		return TipoMovimentacao.values();
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public String list() {
		try {
			cofres = CofreBO.getInstance().cofreListReport(cofreFilter);

			entrada = new BigDecimal(0);
			saida = new BigDecimal(0);
			saldo = new BigDecimal(0);
			for (int i = 0; i < cofres.size(); i++) {
				if (cofres.get(i).getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA)) {
					entrada = entrada.add(cofres.get(i).getValor());
				} else if (cofres.get(i).getTipoMovimentacao().equals(TipoMovimentacao.SAIDA)) {
					saida = saida.add(cofres.get(i).getValor());
				}
			}
//			saldo = entrada.subtract(new BigDecimal(Math.abs(saida.doubleValue())));
			saldo = entrada.subtract(saida);

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();

		mapa.put("entrada", entrada);
		mapa.put("saida", saida);
		mapa.put("saldo", saldo);
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, cofres, "cofres",
				"Relatório de controle de Cofres - " + DataUtil.formatarData(new Date()), true);
	}

	public String updateStatus() {
		try {
			if (cofre.getStatus()) {
				cofre.setStatus(false);
				CofreBO.getInstance().update(cofre);
			} else {
				cofre.setStatus(true);
				CofreBO.getInstance().update(cofre);
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
		String status = cofre.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O cofre est� " + status);
		return null;
	}

	public Cofre getCofre() {
		return cofre;
	}

	public void setCofre(Cofre cofre) {
		this.cofre = cofre;
	}

	public Cofre getCofreFilter() {
		return cofreFilter;
	}

	public void setCofreFilter(Cofre cofreFilter) {
		this.cofreFilter = cofreFilter;
	}

	public List<Cofre> getCofres() {
		return cofres;
	}

	public void setCofres(List<Cofre> cofres) {
		this.cofres = cofres;
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
