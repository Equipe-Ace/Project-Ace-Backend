package com.api3Dsm.domain.modelo;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.Return;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@javax.persistence.Entity
public class Usuario implements UserDetails{

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
		
		
		public String getCargo() {
			return cargo;
		}
		public void setCargo(String cargo) {
			this.cargo = cargo;
		}
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
	
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return List.of(new SimpleGrantedAuthority(cargo));
		}
		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return senha;
		}
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
			return email;
		}
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
			return true;
		}
		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
			return true;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
			return true;
		}
		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			//throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
			return true;
		}

}
