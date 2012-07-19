package br.com.silvanopessoa.interceptor.auditor;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.silvanopessoa.model.entity.CustomRevisionEntity;

public class CustomRevisionInterceptor implements RevisionListener{

	@Override
	public void newRevision(Object arg0) {
		 CustomRevisionEntity revision = (CustomRevisionEntity) arg0;
	     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     revision.setUsername(userDetails.getUsername());
	}

}
