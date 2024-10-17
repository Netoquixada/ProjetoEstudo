package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.controlpro.constants.Loja;

@Entity
@Table(name = "CONTROL_META")
public class Meta extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Enumerated(EnumType.STRING)
	private Loja loja;
	
	private String competencia;
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;

	
	@OneToMany(mappedBy = "meta",fetch=FetchType.LAZY)
	private List<ItemMeta> itensMeta;;
	
	@Transient
	private Date dataCadastroInicio;

	@Transient
	private Date dataCadastroFim;
	
	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCadastroInicio() {
		return dataCadastroInicio;
	}

	public void setDataCadastroInicio(Date dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}

	public Date getDataCadastroFim() {
		return dataCadastroFim;
	}

	public void setDataCadastroFim(Date dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}

	public BigDecimal getValor() {
		if(valor == null) {
			valor = new BigDecimal(0);
		}
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<ItemMeta> getItensMeta() {
		return itensMeta;
	}

	public void setItensMeta(List<ItemMeta> itensMeta) {
		this.itensMeta = itensMeta;
	}
	
}
