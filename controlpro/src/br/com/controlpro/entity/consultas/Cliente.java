package br.com.controlpro.entity.consultas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import br.com.controlpro.constants.TipoEscursao;
import br.com.controlpro.entity.Excursao;

@Entity
@Table(name = "Cli_For")
public class Cliente implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 303600955063832761L;

	@Id
	@GeneratedValue
	@Column(name = "Codigo")
	private Integer id;
	
	@Column(name = "Nome")
	private String nome;

	@Column(name = "Fantasia")
	private String fantasia;
	
	@Column(name = "Fone_1")
	private String fone;
	
	@Column(name = "Estado")
	private String estado;
	
	@Column(name = "Cidade")
	private String cidade;
	
	@Column(name = "Tipo")
	private String tipo;
	
	@ManyToOne
	private Excursao excursao;
	
	@Transient
	private String codigoCartao;
	
	
	/**
	 * Campos criados e utilizados na Etiqueta do Cliente
	 * @return
	 */
	
	@Transient
	private String pedido;
	
	@Transient
	private String localExcursao;
	
	@Transient
	private String nomeExcursao;
	
	@Transient
	private String destino;
	
	@Transient
	private Date dataEnvio;
	
	@Transient
	private String observacao;
	
	@Transient
	private TipoEscursao tipoEscursao = TipoEscursao.EXCURSAO;
	
	@Transient
	private String enderecoExcursao;
	
	@Transient
	private String bairroExcursao;
	
	@Transient
	private String cidadeExcursao;
	
	public String getLocalExcursao() {
		return localExcursao;
	}

	public void setLocalExcursao(String localExcursao) {
		this.localExcursao = localExcursao;
	}

	public String getNomeExcursao() {
		return nomeExcursao;
	}

	public void setNomeExcursao(String nomeExcursao) {
		this.nomeExcursao = nomeExcursao;
	}

	public String getDestino() {
		destino = cidade + " - " + estado;
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	public Excursao getExcursao() {
		return excursao;
	}

	public void setExcursao(Excursao excursao) {
		this.excursao = excursao;
	}

	public String getCodigoCartao() {
		codigoCartao = "2341  "+StringUtils.rightPad(String.valueOf(id), 4, "0")+"  7843  " + StringUtils.leftPad(String.valueOf(id), 4, "0");
		return codigoCartao;
	}
	
	public void setCodigoCartao(String codigoCartao) {
		this.codigoCartao = codigoCartao;
	}
	
	public String getPedido() {
		return pedido;
	}
	
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	

	public TipoEscursao getTipoEscursao() {
		return tipoEscursao;
	}

	public void setTipoEscursao(TipoEscursao tipoEscursao) {
		this.tipoEscursao = tipoEscursao;
	}

	public String getEnderecoExcursao() {
		return enderecoExcursao;
	}

	public void setEnderecoExcursao(String enderecoExcursao) {
		this.enderecoExcursao = enderecoExcursao;
	}

	public String getBairroExcursao() {
		return bairroExcursao;
	}

	public void setBairroExcursao(String bairroExcursao) {
		this.bairroExcursao = bairroExcursao;
	}

	public String getCidadeExcursao() {
		return cidadeExcursao;
	}

	public void setCidadeExcursao(String cidadeExcursao) {
		this.cidadeExcursao = cidadeExcursao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fantasia == null) ? 0 : fantasia.hashCode());
		result = prime * result + ((fone == null) ? 0 : fone.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cliente other = (Cliente) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fantasia == null) {
			if (other.fantasia != null)
				return false;
		} else if (!fantasia.equals(other.fantasia))
			return false;
		if (fone == null) {
			if (other.fone != null)
				return false;
		} else if (!fone.equals(other.fone))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", fantasia=" + fantasia + ", fone=" + fone + ", estado="
				+ estado + "]";
	}
	
	
}
