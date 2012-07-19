package br.com.silvanopessoa.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.silvanopessoa.exception.java.ApplicationMessageException;
import br.com.silvanopessoa.exception.java.BusinessMessageException;
import br.com.silvanopessoa.model.dto.UsuarioSistemaDTO;


/**
 * @author silvano.dantas
 *
 */
public interface ILoginService {
	
	/**
	 * @param usuario
	 * @param senha
	 * @return
	 * @throws BusinessMessageException 
	 * @throws ApplicationMessageException 
	 */
	@Transactional(readOnly = true, propagation=Propagation.SUPPORTS, rollbackFor=Exception.class)
	public UsuarioSistemaDTO requestAutenticarUsuario(String usuario, String senha) throws BusinessMessageException, ApplicationMessageException;

}
