package com.api3Dsm.domain.modelo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@javax.persistence.Entity
public class Usuario {

		@Id
		@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
		@EqualsAndHashCode.Include
		private Long id;
		
		@Column
		private String email;
		
		@Column
		private String senha;
		
		@Column
		private String cargo;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSenha() {
			return senha;
		}
		public void setSenha(String senha) {
			this.senha = senha;
		}
		public String getCargo() {
			return cargo;
		}
		public void setCargo(String cargo) {
			this.cargo = cargo;
		}

}
