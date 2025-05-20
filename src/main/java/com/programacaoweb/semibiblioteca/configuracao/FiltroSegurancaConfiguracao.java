package com.programacaoweb.semibiblioteca.configuracao;

import com.programacaoweb.semibiblioteca.exception.RespostaException;
import com.programacaoweb.semibiblioteca.service.UsuarioService;
import com.programacaoweb.semibiblioteca.service.autenticacao.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FiltroSegurancaConfiguracao extends OncePerRequestFilter {

    final TokenService tokenService;
    final UsuarioService usuarioService;

    public FiltroSegurancaConfiguracao(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String token = this.recoverToken(request);
            Set<String> permissionsRoutes = Set.of("/", "/autenticacao");
            String uri = request.getRequestURI();
            if (token == null && permissionsRoutes.stream().noneMatch(uri::contains)) {
                responseMap.put("error", "Falha na autenticação");
                responseMap.put("message", "Token ausente");
                RespostaException.handlerGlobalExepction(false, responseMap, response);
                return;
            }
            if (token != null) {
                var login = tokenService.validateToken(token);
                UserDetails user = usuarioService.findByLogin(login);
                if (user != null) {
                    authenticateUser(user);
                } else {
                    responseMap.put("error", "Falha na autenticação");
                    responseMap.put("message", "Token Invalido");
                    RespostaException.handlerGlobalExepction(false, responseMap, response);
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            responseMap.put("error", "Internal Server Error");
            responseMap.put("message", e.getMessage());
            RespostaException.handlerGlobalExepction(false, responseMap, response);
        }

    }

    private void authenticateUser(UserDetails user) {
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
