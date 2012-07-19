package br.com.silvanopessoa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.dto.UsuarioSistemaDTO;
import br.com.silvanopessoa.model.entity.Funcionario;
import br.com.silvanopessoa.service.IFuncionarioService;
import br.com.silvanopessoa.service.ILoginService;
import br.com.silvanopessoa.service.ISessionService;
import br.com.silvanopessoa.support.ServiceSupport;

@Service
public class LoginService extends ServiceSupport implements ILoginService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1171910738549409621L;
	
	@Resource
	ISessionService sessionService;
	
	@Resource
	IFuncionarioService funcionarioService;
	
	
	/* (non-Javadoc)
	 * @see br.com.silvanopessoa.service.ILoginService#requestAutenticarUsuario(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public UsuarioSistemaDTO requestAutenticarUsuario(String usuario, String senha) throws BusinessMessageException, ApplicationMessageException{
		UsuarioSistemaDTO dto = new UsuarioSistemaDTO();
		try{
			//RN01 - 
			ApplicationContext appContext = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			//RN02 - Obtem o Authentication Manager definido no security-config.xml
			AuthenticationManager manager = (AuthenticationManager) appContext.getBean("_authenticationManager");
			//RN03 - Substitui os ? nos sql do Authentication Manager e defini no token
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuario, senha );
			//RN04 - Realiza a autenticacao, ou seja, valida senha e login conforme com token. Se for inválida retorna o erro.
			Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			//RN05 - Autorizacoes
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			//RN06 - Resposta
			dto.setUsuario(usuario);
			List<String> permissoes = new ArrayList<String>();
			for (GrantedAuthority grantedAuthority : authorities) {
				permissoes.add(grantedAuthority.getAuthority());
			}
			dto.setPermissoes(permissoes);
			Funcionario funcionario = funcionarioService.getByUser(usuario);
			sessionService.initSession(funcionario);
		}catch (BadCredentialsException e) {
			throw new BusinessMessageException(this.getMessage("exceptions.usuario.senha.invalido"));
		}catch (Exception e) {
			throw new ApplicationMessageException(this.getMessage("app.error.unknown"));
		}
		return dto;
	}
	
}
