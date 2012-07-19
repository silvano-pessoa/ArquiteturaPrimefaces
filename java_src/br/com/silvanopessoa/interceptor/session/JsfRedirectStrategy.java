package br.com.silvanopessoa.interceptor.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.UrlUtils;

public class JsfRedirectStrategy implements InvalidSessionStrategy {

    private boolean contextRelative;

	@Override
	public void onInvalidSessionDetected(HttpServletRequest arg0,HttpServletResponse arg1) throws IOException, ServletException {
		arg0.getSession();
		this.sendRedirect(arg0, arg1, "/index.jsf");
	}

	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        boolean ajaxRedirect = request.getHeader("faces-request") != null && request.getHeader("faces-request").toLowerCase().indexOf("ajax") > -1;

        if(ajaxRedirect) {
            String ajaxRedirectXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<partial-response><redirect url=\""+redirectUrl+"\"></redirect></partial-response>";
            response.setContentType("text/xml");
            response.getWriter().write(ajaxRedirectXml);
        } else {
            response.sendRedirect(redirectUrl);
        }
    }

    private String calculateRedirectUrl(String contextPath, String url) {
        if (!UrlUtils.isAbsoluteUrl(url)) {
            if (contextRelative) {
                return url;
            } else {
                return contextPath + url;
            }
        }
        if (!contextRelative) {
            return url;
        }
        url = url.substring(url.indexOf("://") + 3);
        url = url.substring(url.indexOf(contextPath) + contextPath.length());
        if (url.length() > 1 && url.charAt(0) == '/') {
            url = url.substring(1);
        }
        return url;
    }

    public void setContextRelative(boolean useRelativeContext) {
        this.contextRelative = useRelativeContext;
    }

}
