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

import br.com.controlpro.bo.ReservaBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.TipoMovimentacao;
import br.com.controlpro.entity.Reserva;
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
public class ReservaBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;

	
	private Reserva reserva;
	private Reserva reservaFilter;
	private List<Reserva> reservas;
	
	private BigDecimal credito;
	private BigDecimal debito;
	private BigDecimal saldo;

	@PostConstruct
	public void init() {
		reserva = new Reserva();
		reservaFilter = new Reserva();
		reservas = new ArrayList<Reserva>();
	}

	public void save() {
		try {
			ReservaBO.getInstance().save(reserva);
			list();
			addInfoMessage("Cadastrado com sucesso!", "Reserva " + reserva.getOs());
			reserva = new Reserva();
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
			ReservaBO.getInstance().update(reserva);
			list();
			addInfoMessage("Editado com sucesso!", "Reserva " + reserva.getOs());
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
		reserva = new Reserva();
	}
	
	public void prepareSave(){
		reserva = new Reserva();
	}
	
	public Loja[] getLojas(){
		return Loja.values();
	}

	public String list() {
		try {
			reservas = ReservaBO.getInstance().reservaListReport(reservaFilter);
			
			credito = new BigDecimal(0);
			debito = new BigDecimal(0);
			saldo = new BigDecimal(0);
			for (int i = 0; i < reservas.size(); i++) {
				if (reservas.get(i).getTipo().equals("CREDITO")) {
					credito = credito.add(reservas.get(i).getValorReserva());
				} else if (reservas.get(i).getTipo().equals("DEBITO")) {
					debito = debito.add(reservas.get(i).getValorReserva());
				}
			}
			saldo = credito.subtract(debito);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void gerarPDF(ActionEvent ev) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		list();

		mapa.put("entrada", credito);
		mapa.put("saida", debito);
		mapa.put("saldo", saldo);
		
		GenericReport.gerarPdfComJRBeanCollectionDataSource(mapa, reservas, "reservas",
				"Relatório de reservas - " + DataUtil.formatarData(new Date()), true);
	}
	
	public String updateStatus() {
		try {
			if (reserva.getStatus()) {
				reserva.setStatus(false);
				ReservaBO.getInstance().update(reserva);
			} else {
				reserva.setStatus(true);
				ReservaBO.getInstance().update(reserva);
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
		String status = reserva.getStatus() ? "Ativo" : "Inativo";
		addInfoMessage("Status alterado com sucesso!", "O reserva est� " + status);
		return null;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Reserva getReservaFilter() {
		return reservaFilter;
	}

	public void setReservaFilter(Reserva reservaFilter) {
		this.reservaFilter = reservaFilter;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}
