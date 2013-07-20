/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import usuarios.Usuario;

/**
 *
 * @author Luis
 */
@MultipartConfig
public class subirImagenController extends HttpServlet {
    

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String fileName="";
        String Url ="";
        String accion = request.getParameter("accion1");
        System.out.println(accion);
        if(accion != null){
            if(accion.equalsIgnoreCase("profileImagen")){
                    Part parte = request.getPart("SeleccionarImagen");
                    if (parte != null) {
                        if(parte.getSize()>0){

                        InputStream is = parte.getInputStream();
                        int i = is.available();
                        byte[] b  = new byte[i];
                        is.read(b);
                        fileName = getFileName(parte);
                        Url = "images/users/"+fileName+"";
                        FileOutputStream os = new FileOutputStream("C:\\Users\\Renso\\Documents\\NetBeansProjects\\MicroBlogParcial2\\trunk\\web\\images\\users\\"+fileName);
                        os.write(b);
                        is.close();
                        response.sendRedirect("./profileUsuario.jsp?url="+Url+"");
                        
                        }else{
                            response.sendRedirect("./profileUsuario.jsp");
                        }
                    }else{
                    response.sendRedirect("./profileUsuario.jsp");
                    }
            
            }else if(accion.equalsIgnoreCase("publicarImagen")){
                    System.out.println("entro1");
                    List<String> listaimagen = new ArrayList<String>();
                    Part parte = request.getPart("multipleImagen");
                    if (parte != null) {
                        if(parte.getSize()>0){

                        InputStream is = parte.getInputStream();
                        int i = is.available();
                        byte[] b  = new byte[i];
                        is.read(b);
                        fileName = getFileName(parte);
                        Url = "images/publicarImagen/"+fileName+"";
                        FileOutputStream os = new FileOutputStream("C:\\Users\\Renso\\Documents\\NetBeansProjects\\MicroBlogParcial2\\trunk\\web\\images\\publicarImagen\\"+fileName);
                        os.write(b);
                        is.close();
                        
                        
                        
                        Object attribute = session.getAttribute("listaImagenes");
                        if (attribute != null) {
                             if (attribute instanceof List) {
                                 listaimagen = (List<String>) attribute;
                             }
                        }
                        listaimagen.add(Url); 
                        session.setAttribute("listaImagenes", listaimagen);
                        System.out.println(session.getAttribute("listaImagenes"));
                        response.sendRedirect("./MostrarPublicaciones");
                    }else{
                            response.sendRedirect("./MostrarPublicaciones");
                    }
            }
                    
        }else{
                System.out.println("entro2");
                List<String> listaimagen1 = new ArrayList<String>();
                Object attribute = session.getAttribute("listaImagenes");
                int c=0;
                if (attribute != null) {
                    if (attribute instanceof List) {
                        listaimagen1 = (List<String>) attribute;
                    }
                }
                for(String imagen : listaimagen1){
                    if(imagen.equalsIgnoreCase(accion)){
                        break;
                    }
                    c++;
                }
                        listaimagen1.remove(c);
                        session.setAttribute("listaImagenes", listaimagen1);
                        System.out.println(session.getAttribute("listaImagenes"));
                        response.sendRedirect("./MostrarPublicaciones");
            }
        }

    }
          private String getFileName(Part part) {
         for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                 return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
         }
         return null;
      }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
