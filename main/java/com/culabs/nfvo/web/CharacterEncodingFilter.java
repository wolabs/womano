package com.culabs.nfvo.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.culabs.nfvo.util.SysConst;

/**
 * 
 * @ClassName: CharacterEncodingFilter 
 * @Description: TODO
 * @author
 * @date 2015年4月23日 下午3:50:25 
 * @version 1.0
 */
public class CharacterEncodingFilter implements Filter
{
    protected String encoding = null;

    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        if (null != this.encoding)
        {
            request.setCharacterEncoding(this.encoding);
            response.setCharacterEncoding(this.encoding);
            response.setContentType(SysConst.MEDIA_TEXT_HTML_UTF_8);
            chain.doFilter(request, response);
        }
    }

    public void destroy()
    {
        this.encoding = null;
    }
}
