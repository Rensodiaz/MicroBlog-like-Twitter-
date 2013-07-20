<%-- 
    Document   : profileUsuario
    Created on : Jun 27, 2013, 10:14:04 AM
    Author     : Luis
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="./layout/head.jsp"></jsp:include>
<jsp:include page="./layout/topNav.jsp"></jsp:include>
<jsp:include page="./layout/menuNavMob.jsp"></jsp:include>
<jsp:include page="./layout/sideBar.jsp"></jsp:include>

    <!-- Secondary nav -->
    <div class="secNav">
        <div class="secWrapper">
            <div class="secTop">
                <b><h3>FAKE TWITTER.DO</h3><b/>

            </div>
            
            <!-- Tabs container -->
            <div id="tab-container" class="tab-container">
                <ul class="iconsLine ic3 etabs">
                    <li><a href="#general" title=""><span class="icos-fullscreen"></span></a></li>
                    <li><a href="#alt1" title=""><span class="icos-user"></span></a></li>
                    <li><a href="#alt2" title=""><span class="icos-archive"></span></a></li>
                </ul>
                
                <div class="divider"><span></span></div>
                
                <div id="general">

                </div>
                
                <div id="alt1">
                    
                </div>
                
                
                <div id="alt2">
                
      
                </div>
            </div>
            
            <div class="divider"><span></span></div>
            
            
    
            
       </div> 
       <div class="clear"></div>
   </div>
</div>
<!-- Sidebar ends -->




<!-- Contenido de aqui en adelante
    
    
<!-- Content begins -->
<div id="content">
    <div class="contentTop">
        <span class="pageTitle"><span class="icon-screen"></span>Dashboard</span>
        <ul class="quickStats">
            <li>
                <a href="default.htm" class="blueImg"><img src="images/icons/quickstats/plus.png" alt="" /></a>
                <div class="floatR"><strong class="blue">5489</strong><span>visits</span></div>
            </li>
            <li>
                <a href="default.htm" class="redImg"><img src="images/icons/quickstats/user.png" alt="" /></a>
                <div class="floatR"><strong class="blue">4658</strong><span>users</span></div>
            </li>
            <li>
                <a href="default.htm" class="greenImg"><img src="images/icons/quickstats/money.png" alt="" /></a>
                <div class="floatR"><strong class="blue">1289</strong><span>orders</span></div>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
    
    <!-- Breadcrumbs line -->
    <div class="breadLine">
        
    </div>
    
    <!-- Main content -->
    <div class="wrapper">
   <%--<jsp:include page="./layout/shortcuts.jsp"></jsp:include>--%>
    <c:set value="${sessionScope.usuarioSesion}" scope="session" var="p"></c:set>
    CONTEXT HERE!!!
    <form align="center" action="subirImagenController" enctype="multipart/form-data" method="POST">
        <fieldset style="alignment-adjust: central">
            <label>Cambiar Imagen:</label>
            <div>
                
                
                <c:if test="${param.url == null}" >
                    
                    <img  src="${p.getUrlImagenPerfil()}" alt="Picture">
                </c:if>
                <c:if test="${param.url != null}" >
                    
                   <img  src="${param.url}" alt="Picture">
                </c:if>
                 <br>
                <div>
                <input type="hidden" name="accion1" value="profileImagen" />
                <input id="images" type="file" name="SeleccionarImagen"> 
                <input type="button" id="clickImage" value="Add Image" class="buttonS bGreen"/><br>
                <input id="upload" type="submit" value="Subir Foto" >
                <a id="clickImage2" href="#" class="buttonM bDefault"><span class="icos-copypaste"></span><span>Subir Foto</span></a>
                <!--<input type="button" id="clickImage2" value="Subir Foto" class="buttonM bGreyish""/><br>-->
                <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" charset="utf-8"></script>

                <script>
                $('#images').hide();        
                $('#clickImage').click( function() {    
                    $('#images').trigger('click');  
                });
                $('#upload').hide();
                 $('#clickImage2').click( function() {    
                    $('#upload').trigger('click');  
                });
                </script>
                </div>
            </div>
         </fieldset>
     </form> 
    <form align="Right" action="updateUsuarioController"  method="POST">
            <fieldset style="alignment-adjust: central">
                
                <div class="formRow" style="alignment-adjust: central;">
  
                 <label>Nombre Usuario:</label> <br>
                  
                 <input type="text" name="nombreUsuario" value="${p.getNombre()}"><br> 
                  
                 <label>Apellido Usuario:</label> <br>
                 <input type="text" name="apellidoUsuario" value="${p.getApellidos()}"> <br>
            
                 <label>E-mail:</label> <br>
                 <input type="text" name="emailUsuario" value="${p.getEmail()}"><br> 

                 
                 <label>Nuevo Password:</label><br>
                 <input type="password" name="passwordUsuario" ><br>
                 
                 <c:if test="${param.url == null}" >
                    <input type="hidden" name="url" value="${p.getUrlImagenPerfil()}">
                </c:if>
                <c:if test="${param.url != null}" >
                   <input type="hidden" name="url" value="${param.url}">
                  
                </c:if>
                    <br>
                <c:if test="${param.vacio eq '1'}">
                    <p style="color: red; font-family: Helvetica">No pueden ver campos vacios</p>
                </c:if>
                </div>
                <br>
                <div>
                    <button  type="submit" >Salvar cambios</button>
                </div>
         </fieldset>
     </form> 
   </div>

        
    </div>
    <jsp:include page="./layout/footer.jsp"></jsp:include>