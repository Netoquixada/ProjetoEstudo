package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.controlpro.bo.VendasBO;
import br.com.controlpro.entity.consultas.Funcionario;
import br.com.controlpro.entity.consultas.Vendas;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

@Entity
@Table(name = "CONTROL_CONTROLE_PEDIDO")
public class ControlePedido extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Column(name = "sequencia_venda")
	private String sequenciaVenda;

	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	private Funcionario despachante;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_envio")
	private Date dataEnvio;
	
	@ManyToOne
	@JoinColumn(name = "id_responsavel_envio")
	private Funcionario responsavelEnvio;

	@Type(type = "true_false")
	private Boolean nfe;

	@Column(name = "status_ocorrencia")
	private Integer statusOcorrencia = 0;
	
	@Column(name = "numero_nfe")
	private String numeroNfe;
	
	private String envio;

	@Column(name = "envio_status")
	private String envioStatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega")
	private Date dataEntraga;

	@Type(type = "true_false")
	@Column(name = "pos_venda")
	private Boolean posVenda = false;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_venda")
	private Date dataPosVenda;

	private String rastreio;
	
	@Column(name = "valor_frete", scale = 2, precision = 9)
	private BigDecimal valorFrete = new BigDecimal(0);

	@Column(name = "valor_frete_pago", scale = 2, precision = 9)
	private BigDecimal valorFretePago = new BigDecimal(0);
	
	@Column(name = "valor_pedido_glamix", scale = 2, precision = 9)
	private BigDecimal valorPedidoGlamix = new BigDecimal(0);
	
	@Column(name = "numero_pedido")
	private String numeroPedido;
	
	@ManyToOne
	@JoinColumn(name = "cliente_glamix")
	private ClienteGlamix clienteGlamix;
	
	@ManyToOne
	@JoinColumn(name = "motoboy")
	private Motoboy motoboy;
	
	@Transient
	private BigDecimal diferencaFrete = new BigDecimal(0);

	@OneToMany(mappedBy = "controlePedido",fetch=FetchType.LAZY)
	private List<ComprovanteFrete> comprovantes;
	
	@Column(columnDefinition = "text")
	private String observacao;

	private String protocolo;
	
	@Transient
	private Date dataInicio;

	@Transient
	private Date dataFim;
	
	@Transient
	private Vendas venda;
	
	public String getSequenciaVenda() {
		return sequenciaVenda;
	}

	public void setSequenciaVenda(String sequenciaVenda) {
		this.sequenciaVenda = sequenciaVenda;
	}

	public String getRastreio() {
		return rastreio;
	}

	public void setRastreio(String rastreio) {
		this.rastreio = rastreio;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getNfe() {
		return nfe;
	}

	public void setNfe(Boolean nfe) {
		this.nfe = nfe;
	}

	public String getEnvio() {
		return envio;
	}

	public void setEnvio(String envio) {
		this.envio = envio;
	}

	public String getEnvioStatus() {
		return envioStatus;
	}

	public void setEnvioStatus(String envioStatus) {
		this.envioStatus = envioStatus;
	}

	public Date getDataEntraga() {
		return dataEntraga;
	}

	public void setDataEntraga(Date dataEntraga) {
		this.dataEntraga = dataEntraga;
	}

	public Boolean getPosVenda() {
		return posVenda;
	}

	public void setPosVenda(Boolean posVenda) {
		this.posVenda = posVenda;
	}

	public Date getDataPosVenda() {
		return dataPosVenda;
	}

	public void setDataPosVenda(Date dataPosVenda) {
		this.dataPosVenda = dataPosVenda;
	}

	public Funcionario getDespachante() {
		return despachante;
	}

	public void setDespachante(Funcionario despachante) {
		this.despachante = despachante;
	}
	
	public BigDecimal getValorFrete() {
		return valorFrete;
	}
	
	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorFretePago() {
		if(valorFretePago == null){
			valorFretePago = new BigDecimal(0);
		}
		return valorFretePago;
	}

	public void setValorFretePago(BigDecimal valorFretePago) {
		this.valorFretePago = valorFretePago;
	}

	public List<ComprovanteFrete> getComprovantes() {
		return comprovantes;
	}

	public void setComprovantes(List<ComprovanteFrete> comprovantes) {
		this.comprovantes = comprovantes;
	}
	
	public BigDecimal getDiferencaFrete() {
		diferencaFrete = valorFrete.subtract(valorFretePago);
		return diferencaFrete;
	}
	
	public void setDiferencaFrete(BigDecimal diferencaFrete) {
		this.diferencaFrete = diferencaFrete;
	}
	
	public Date getDataEnvio() {
		return dataEnvio;
	}
	
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	public Funcionario getResponsavelEnvio() {
		return responsavelEnvio;
	}
	
	public void setResponsavelEnvio(Funcionario responsavelEnvio) {
		this.responsavelEnvio = responsavelEnvio;
	}
	
	public String getNumeroNfe() {
		return numeroNfe;
	}
	
	public void setNumeroNfe(String numeroNfe) {
		this.numeroNfe = numeroNfe;
	}

	public Integer getStatusOcorrencia() {
		return statusOcorrencia;
	}
	
	public void setStatusOcorrencia(Integer statusOcorrencia) {
		this.statusOcorrencia = statusOcorrencia;
	}
	
	public String getProtocolo() {
		return protocolo;
	}
	
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	
	public BigDecimal getValorPedidoGlamix() {
		return valorPedidoGlamix;
	}

	public void setValorPedidoGlamix(BigDecimal valorPedidoGlamix) {
		this.valorPedidoGlamix = valorPedidoGlamix;
	}

	public ClienteGlamix getClienteGlamix() {
		return clienteGlamix;
	}

	public void setClienteGlamix(ClienteGlamix clienteGlamix) {
		this.clienteGlamix = clienteGlamix;
	}

	public Vendas getVenda() {
		
		try {
			List<Vendas> lista = VendasBO.getInstance().vendaPorSequencia(this.sequenciaVenda);
			if(lista.size() == 0) {
				venda = new Vendas();
			}else {
				venda = lista.get(0);
			}
			
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		
		return venda;
	}
	
	public void setVenda(Vendas venda) {
		this.venda = venda;
	}
	
	public Motoboy getMotoboy() {
		return motoboy;
	}
	
	public void setMotoboy(Motoboy motoboy) {
		this.motoboy = motoboy;
	}
	
	public String getNumeroPedido() {
		return numeroPedido;
	}
	
	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

}