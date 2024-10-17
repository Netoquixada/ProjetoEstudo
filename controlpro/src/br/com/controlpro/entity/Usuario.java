package br.com.controlpro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.PerfilUser;


@NamedQueries({
		@NamedQuery(name = "findByLoginAndSenha", query = "select u from Usuario u where u.login = :login and u.senha = :senha"),
})

@Entity
@Table(name = "CONTROL_USUARIO")
public class Usuario extends EntidadeGenerica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9214896121606081420L;

	private String nome;

	private String email;

	private String login;

	private String senha;

	private String cpf;

	private String celular;
	
	private String telefone;

	@Type(type = "true_false")
	private Boolean primeiroAcesso = true;
	
	@Transient
	private Boolean atualizarSenha = false;

	@Enumerated(EnumType.STRING)
	@Column(name = "perfil_user")
	private PerfilUser perfilUser;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "loja")
	private Loja loja = Loja.FABRICA;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

	public PerfilUser getPerfilUser() {
		return perfilUser;
	}

	public void setPerfilUser(PerfilUser perfilUser) {
		this.perfilUser = perfilUser;
	}
	
	public Boolean getAtualizarSenha() {
		return atualizarSenha;
	}
	
	public void setAtualizarSenha(Boolean atualizarSenha) {
		this.atualizarSenha = atualizarSenha;
	}
	
	public Loja getLoja() {
		return loja;
	}
	
	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", email=" + email + ", login=" + login + ", senha=" + senha + ", cpf=" + cpf
				+ ", celular=" + celular + ", telefone=" + telefone + ", primeiroAcesso=" + primeiroAcesso
				+ ", atualizarSenha=" + atualizarSenha + ", perfilUser=" + perfilUser + ", loja=" + loja + "]";
	}
	
}
