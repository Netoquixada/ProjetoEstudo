package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.controlpro.util.CalcDias;

@Entity
@Table(name = "Saidas")
public class Vendas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;

	@Id
	@GeneratedValue
	@Column(name = "Ordem")
	private Integer id;

	@Column(name = "Sequencia")
	private String sequencia;

	@Column(name = "Tabela")
	private String tabela;

	@Column(name = "Produtos")
	private Float valorVenda;

	@Column(name = "Recebe_Cartao_Credito")
	private Float cartoesCredito = 0.0f;

	@Column(name = "Recebe_Banco_Valor")
	private Float bancoValor = 0.0f;

	@Column(name = "Recebe_Cartao_Debito")
	private Float cartoesDebito = 0.0f;

	@Column(name = "Recebe_Dinheiro")
	private Float dinheiro = 0.0f;

	@Column(name = "Recebe_Troco")
	private Float troco = 0.0f;

	@Column(name = "Recebe_Credito_Cliente")
	private Float recebeCreditoCliente = 0.0f;

	@Column(name = "Total")
	private Float totalVenda = 0.0f;

	@Transient
	private Float total = 0.0f;

	@Transient
	private Float totalDashboard = 0.0f;
//	---------------Outros-----------------

	@Column(name = "Desconto_Produtos")
	private Float destontoProdutos = 0.0f;

//	Total dos produtos com desconto
	@Column(name = "Base_Icms")
	private Float totalProduto = 0.0f;

//	Total pago para envio do pedido
	@Column(name = "Despesa_Acessoria")
	private Float despesaEnvio = 0.0f;

//	---------------Outros-----------------

	@Column(name = "Efetivada_Financ")
	private int efetivadaFinanceiro;
	
	@Column(name = "Desfeita_Financ")
	private int desfeitaFinanceiro;

	@Column(name = "Data_Efetiva_Financ")
	private Date dataConfirmacao;

	@Column(name = "Data")
	private Date data;

	@Transient
	private Date dataVendaInicio;

	@Transient
	private Date dataVendaFim;

	@ManyToOne
	@JoinColumn(name = "Vendedor1")
	private Funcionario vendedor;

	@ManyToOne
	@JoinColumn(name = "Cliente")
	private Cliente cliente;

	@Column(name = "Cod_Operacao")
	private Long codOperacao = 0L;

	@Transient
	private Long diferencaDias;

	public Long getDiferencaDias() {
		if (efetivadaFinanceiro == 1) {
			diferencaDias = CalcDias.calDias(data, dataConfirmacao);
		}
		return diferencaDias;
	}

	public void setDiferencaDias(Long diferencaDias) {
		this.diferencaDias = diferencaDias;
	}
	
	public Float getTotalDashboard() {
		total = this.cartoesCredito + this.cartoesDebito + this.dinheiro + this.bancoValor;
			return totalDashboard;
	}
	
	public void setTotalDashboard(Float totalDashboard) {
		this.totalDashboard = totalDashboard;
	}

	public Float getTotal() {
		total = this.cartoesCredito + this.cartoesDebito + this.dinheiro + this.bancoValor + this.recebeCreditoCliente;
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Float getCartoesCredito() {
		return cartoesCredito;
	}

	public void setCartoesCredito(Float cartoesCredito) {
		this.cartoesCredito = cartoesCredito;
	}

	public Float getCartoesDebito() {
		return cartoesDebito;
	}

	public void setCartoesDebito(Float cartoesDebito) {
		this.cartoesDebito = cartoesDebito;
	}

	public Float getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(Float dinheiro) {
		this.dinheiro = dinheiro;
	}

	public Date getDataConfirmacao() {
		return dataConfirmacao;
	}

	public void setDataConfirmacao(Date dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public Date getDataVendaInicio() {
		return dataVendaInicio;
	}

	public void setDataVendaInicio(Date dataVendaInicio) {
		this.dataVendaInicio = dataVendaInicio;
	}

	public Date getDataVendaFim() {
		return dataVendaFim;
	}

	public void setDataVendaFim(Date dataVendaFim) {
		this.dataVendaFim = dataVendaFim;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public Float getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Float valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Float getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}

	public int getEfetivadaFinanceiro() {
		return efetivadaFinanceiro;
	}

	public void setEfetivadaFinanceiro(int efetivadaFinanceiro) {
		this.efetivadaFinanceiro = efetivadaFinanceiro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Float getBancoValor() {
		return bancoValor;
	}

	public void setBancoValor(Float bancoValor) {
		this.bancoValor = bancoValor;
	}

	public Float getDestontoProdutos() {
		return destontoProdutos;
	}

	public void setDestontoProdutos(Float destontoProdutos) {
		this.destontoProdutos = destontoProdutos;
	}

	public Float getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(Float totalProduto) {
		this.totalProduto = totalProduto;
	}

	public Float getDespesaEnvio() {
		return despesaEnvio;
	}

	public void setDespesaEnvio(Float despesaEnvio) {
		this.despesaEnvio = despesaEnvio;
	}

	public Float getRecebeCreditoCliente() {
		return recebeCreditoCliente;
	}

	public void setRecebeCreditoCliente(Float recebeCreditoCliente) {
		this.recebeCreditoCliente = recebeCreditoCliente;
	}

	public Float getTroco() {
		return troco;
	}

	public void setTroco(Float troco) {
		this.troco = troco;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getCodOperacao() {
		return codOperacao;
	}

	public void setCodOperacao(Long codOperacao) {
		this.codOperacao = codOperacao;
	}

	public int getDesfeitaFinanceiro() {
		return desfeitaFinanceiro;
	}

	public void setDesfeitaFinanceiro(int desfeitaFinanceiro) {
		this.desfeitaFinanceiro = desfeitaFinanceiro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendas other = (Vendas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
