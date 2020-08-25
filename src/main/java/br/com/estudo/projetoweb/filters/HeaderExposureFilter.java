package br.com.estudo.projetoweb.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

	/*
	 * Ele vai interceptar todas as requisições, ele vai expor todos os headers
	 * location nas respostas e encaminha a resposta. Isso é feito pois os headers
	 * precisam ficar expostos de uma maneira clara para que o Angular possa pegar
	 * esse header
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.addHeader("access-control-expose-headers", "Authorization");
		chain.doFilter(request, httpServletResponse);
	}

}
