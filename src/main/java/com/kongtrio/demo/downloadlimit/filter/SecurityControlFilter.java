package com.kongtrio.demo.downloadlimit.filter;

import com.kongtrio.demo.downloadlimit.exception.DHException;
import com.kongtrio.demo.downloadlimit.exception.ExceptionCode;
import com.kongtrio.demo.downloadlimit.exception.I18NConstants;
import com.kongtrio.demo.downloadlimit.util.SecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 请求拦截器
 * @author DH
 * @date 2020-03-17 09:24
 */
@Provider
@PreMatching
public class SecurityControlFilter implements ContainerRequestFilter {

	static Logger logger = LoggerFactory.getLogger(SecurityControlFilter.class);


	@Context
	private HttpServletRequest request;

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		holdHttpInfo(containerRequestContext);
		String url = containerRequestContext.getUriInfo().getPath();
		System.out.println(url);
	}

	private void holdHttpInfo(ContainerRequestContext requestContext) throws IOException {
		Charset charset = StringUtils.isBlank(request.getCharacterEncoding()) ? Charset.forName("UTF-8")
				: Charset.forName(request.getCharacterEncoding());

		String method = requestContext.getMethod();
		String url = requestContext.getUriInfo().getRequestUri().toString();
		String contentType = requestContext.getHeaderString("Content-Type");
		if (StringUtils.equalsIgnoreCase("post", method)
				&& StringUtils.startsWith(contentType, MediaType.APPLICATION_JSON)) {
			InputStream inputStream = requestContext.getEntityStream();
			StringBuilder sb = new StringBuilder();
			InputStreamReader input = new InputStreamReader(inputStream, charset);
			BufferedReader reader = new BufferedReader(input);
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			requestContext.setEntityStream(new ByteArrayInputStream(sb.toString().getBytes(charset)));
			if (sb.toString().trim().equals("null")) {
				throw new DHException(ExceptionCode.ERROR_CODE_PARAM_REQUIRE, I18NConstants.PARAM_EXP);
			}
			SecurityContextHolder.addHttpInfo(url, method, sb.toString());
		} else {
			SecurityContextHolder.addHttpInfo(url, method, null);
		}
	}

	private static void thorw401Exceoption() {
		Response response = Response.ok().status(401).type(MediaType.APPLICATION_JSON).build();
		throw new WebApplicationException(response);
	}
}
