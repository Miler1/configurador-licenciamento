package com.configuradorlicenciamento.configuracao.services;

import com.configuradorlicenciamento.configuracao.enums.Acao;
import com.configuradorlicenciamento.configuracao.exceptions.UnauthenticatedException;
import com.configuradorlicenciamento.configuracao.interfaces.IDefaultService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultService implements IDefaultService {

    @Override
    public Boolean verificaPermissao(HttpServletRequest request, Acao... acoes) {

        boolean permitido = false;

        User usuarioSessao = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(usuarioSessao == null) {
            throw new UnauthenticatedException("Opa! Sua seção expirou. Faça login novamente!");
        }

        Principal principal = request.getUserPrincipal();

        Collection<GrantedAuthority> perfilSelecionado = ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

        List<GrantedAuthority> authoritys = usuarioSessao.getAuthorities().stream().filter(p -> perfilSelecionado.contains(p)).collect(Collectors.toList());

        for (Acao acao : acoes)
            permitido = permitido || (usuarioSessao != null && hasPermissao(authoritys, acao.getCodigo()));

        return permitido;
    }

    private boolean hasPermissao(List<GrantedAuthority> authoritys, String codigoAcao) {

        return authoritys.stream().filter(authority -> authority.getAuthority().equals(codigoAcao)).count() > 0;
    }

}
