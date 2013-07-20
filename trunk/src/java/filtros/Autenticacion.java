/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Luis
 */
public class Autenticacion implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Iniciando filtro de Autenticacion");
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Aplicando filtro autenticacion");
        
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        HttpSession sesion = req.getSession();
        if(sesion.getAttribute("usuarioSesion") == null){
            res.sendRedirect("./login.jsp");
        } else {
            chain.doFilter(request, response);  
        }
        
        
    }

    @Override
    public void destroy() {
        
        System.out.println("Destruyendo filtro de Autenticacion");
        
    }
    
}
