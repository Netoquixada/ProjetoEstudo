package br.com.controlpro.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CONTROL_OCORRENCIA_CONTROLE_MANIFESTACAO")
public class OcorrenciaControleManifestacao extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289000089333348830L;

	@ManyToOne
	@JoinColumn(name = "controle_mainfestacao_id")
	private ControleManifestacao controleManifestacao;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "data_ocorrencia")
	private Date dataOcorrencia = new Date();

	@Column(columnDefinition = "text")
	private String descricao;

	private String protocolo;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public ControleManifestacao getControleManifestacao() {
		return controleManifestacao;
	}
	
	public void setControleManifestacao(ControleManifestacao controleManifestacao) {
		this.controleManifestacao = controleManifestacao;
	}

}
