package com.configuradorlicenciamento.seguranca.components;

import com.configuradorlicenciamento.seguranca.enuns.PerfilEnum;
import lombok.Getter;
import br.ufla.lemaf.beans.pessoa.Perfil;
import br.ufla.lemaf.beans.pessoa.Permissao;
import br.ufla.lemaf.beans.pessoa.Usuario;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@SuppressWarnings({"squid:S2160"})
public class Autenticacao extends AbstractAuthenticationToken {

	private String token;
	private Usuario usuario;

	public Autenticacao(Usuario usuarioEntradaUnica) {

		super(getPermissoes(usuarioEntradaUnica));

		this.token = usuarioEntradaUnica.sessionKeyEntradaUnica;
		this.usuario = usuarioEntradaUnica;

		setAuthenticated(true);

	}

	private static List<GrantedAuthority> getPermissoes(Usuario usuarioEntradaUnica) {

		if(usuarioEntradaUnica.perfilSelecionado == null) {
			return autorizaAutenticacaoAplicativo(usuarioEntradaUnica);
		} else {
			return autorizaAutenticacao(usuarioEntradaUnica);
		}

	}

	private static List<GrantedAuthority> autorizaAutenticacao(Usuario usuarioEntradaUnica) {

		Optional<Perfil> perfil = usuarioEntradaUnica.perfis.stream()
				.filter(p -> p.nome.equals(usuarioEntradaUnica.perfilSelecionado.nome))
				.findFirst();

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (perfil.isPresent() && (perfil.get().permissoes != null) && !perfil.get().permissoes.isEmpty()) {

			for (Permissao permissao : perfil.get().permissoes) {

				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permissao.codigo);

				grantedAuthorities.add(grantedAuthority);

			}

		}

		autenticaUsuario(usuarioEntradaUnica, grantedAuthorities);

		return grantedAuthorities;
	}

	private static List<GrantedAuthority> autorizaAutenticacaoAplicativo(Usuario usuarioEntradaUnica) {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(PerfilEnum.ADMINISTRADOR.getCodigo());

		grantedAuthorities.add(grantedAuthority);

		autenticaUsuario(usuarioEntradaUnica, grantedAuthorities);

		return grantedAuthorities;

	}

	private static void autenticaUsuario(Usuario usuarioEntradaUnica, List<GrantedAuthority> grantedAuthorities) {

		User user = new User(usuarioEntradaUnica.nome, usuarioEntradaUnica.sessionKeyEntradaUnica, true, true, true, true, grantedAuthorities);
		Authentication newAuth = new UsernamePasswordAuthenticationToken(user, usuarioEntradaUnica.sessionKeyEntradaUnica, grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);

	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}
}
