package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "Caixas_Dia")
public class Caixa implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Caixa")
	private Integer caixa;
	
	@Column(name = "Filial")
	private Integer filial;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Column(name = "Cartoes_Prazo")
	private Float cartoesCredito = 0.0f;	

	@Column(name = "Cartoes_Vista")
	private Float cartoesDebito = 0.0f;	

	@Column(name = "Dinheiro")
	private Float dinheiro = 0.0f;	
	
	@Column(name = "Outros")
	private Float outros = 0.0f;	

	@Column(name = "Tipo")
	private String tipoMov;
	
	@Column(name = "Descricao")
	private String descricao;	
	
	@Transient
	private Float total = 0.0f;	
	
	@Transient
	private Date dataVendaInicio;

	@Transient
	private Date dataVendaFim;
	
	
	
	public Float getOutros() {
		return outros;
	}
	
	public void setOutros(Float outros) {
		this.outros = outros;
	}
	
		
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(String tipoMov) {
		this.tipoMov = tipoMov;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCaixa() {
		return caixa;
	}

	public void setCaixa(Integer caixa) {
		this.caixa = caixa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public Float getTotal() {
		total = this.cartoesCredito + this.cartoesDebito + this.dinheiro + this.outros;
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
	
		
	public Integer getFilial() {
		return filial;
	}

	public void setFilial(Integer filial) {
		this.filial = filial;
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
		Caixa other = (Caixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
