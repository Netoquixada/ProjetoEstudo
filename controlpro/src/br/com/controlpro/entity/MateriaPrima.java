package br.com.controlpro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.controlpro.constants.GrupoItem;
import br.com.controlpro.constants.TipoMateriaPrima;
import br.com.controlpro.constants.UnidadeMedida;
import br.com.controlpro.entity.consultas.Cliente;

@Entity
@Table(name = "CONTROL_MATERIA_PRIMA")
public class MateriaPrima extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4983331588533261636L;

	@Column(name = "nome")
	private String nome;
	
	@ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "CONTROL_MATERIA_FORNECEDOR", joinColumns = { @JoinColumn(name = "id_materia") }, inverseJoinColumns = { @JoinColumn(name = "id_fornecedor") })
	private List<Cliente> fornecedores = new ArrayList<Cliente>();
	
	@Column(name = "valor", scale = 2, precision = 9)
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "unidade_medida")
	private UnidadeMedida unidadeMedida;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "grupo_item")
	private GrupoItem grupoItem;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_materia_prima")
	private TipoMateriaPrima tipoMateriaPrima;
	
	@Column(name = "saldo", scale = 2, precision = 9)
	private BigDecimal saldo;
		
	@Column(columnDefinition = "text")
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultima_atualizacao")
	private Date ultimaAtualizacao = new Date();
	
	@ManyToOne
	@JoinColumn(name = "sub_grupo")
	private SubGrupo subGrupo;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] arquivo;

	private String extensao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cliente> getFornecedores() {
		if(fornecedores == null){
			fornecedores = new ArrayList<>();
		}
		return fornecedores;
	}

	public void setFornecedores(List<Cliente> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(Date ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	
	public GrupoItem getGrupoItem() {
		return grupoItem;
	}
	
	public void setGrupoItem(GrupoItem grupoItem) {
		this.grupoItem = grupoItem;
	}

	public TipoMateriaPrima getTipoMateriaPrima() {
		return tipoMateriaPrima;
	}

	public void setTipoMateriaPrima(TipoMateriaPrima tipoMateriaPrima) {
		this.tipoMateriaPrima = tipoMateriaPrima;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	
	public SubGrupo getSubGrupo() {
		return subGrupo;
	}
	
	public void setSubGrupo(SubGrupo subGrupo) {
		this.subGrupo = subGrupo;
	}
	
}
