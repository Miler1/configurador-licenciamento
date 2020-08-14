package com.configuradorlicenciamento.configuracao.services;

import br.ufla.lemaf.beans.pessoa.Perfil;
import br.ufla.lemaf.beans.pessoa.Usuario;
import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.exceptions.UnauthenticatedException;
import com.configuradorlicenciamento.configuracao.interfaces.IDefaultService;
import com.configuradorlicenciamento.entradaUnica.services.EntradaUnicaWS;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Service
public class DefaultService implements IDefaultService {


    @Override
    public Boolean verificaPermissao(HttpServletRequest request, Acao... acoes) {

        boolean permitido = false;

        Object sessionKey = request.getSession().getAttribute("sessionKeyEntradaUnica");

        Usuario usuarioSessao = null;

        try {
            usuarioSessao = EntradaUnicaWS.ws.searchBySessionKey(sessionKey.toString());
        } catch (Exception e) {
            throw new UnauthenticatedException("Opa! Sua seção expirou. Faça login novamente!");
        }

        if(usuarioSessao == null) {
            throw new UnauthenticatedException("Opa! Sua seção expirou. Faça login novamente!");
        }

        Principal principal = request.getUserPrincipal();

        Collection<GrantedAuthority> perfilSelecionado = ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

        Perfil perfil = usuarioSessao.perfis.stream().filter(p -> perfilSelecionado.contains(new SimpleGrantedAuthority(p.codigo))).findFirst().get();

        for (Acao acao : acoes)
            permitido = permitido || (usuarioSessao != null && hasPermissao(perfil, acao.codigo));

        return permitido;
    }

    private boolean hasPermissao(Perfil perfil, String codigoAcao) {

        return perfil.permissoes.stream().filter(permissao -> permissao.codigo.equals(codigoAcao)).count() > 0;

    }
}
