<%-- 
    Document   : index
    Created on : Jun 22, 2013, 12:18:42 AM
    Author     : Jonathan
--%>

<jsp:include page="layout/head.jsp"></jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="usuario" value="${requestScope.usuario}" scope="request"></c:set>


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
        <span class="pageTitle"><span></span><h4>Bienvenido ${usuario.getUsername()}!</h4></span>
        <ul class="quickStats">
            <li>
                <a href="default.htm" class="blueImg"><img src="images/icons/quickstats/plus.png" alt="" /></a>
                    <c:if test="${usuario.getPassword()!='invitado'}">
                    <div class="floatR"><strong class="blue">${usuario.getPublicaciones().size()}</strong><span>Mensajes</span></div>
                        </c:if>
            </li>
            <li>                
                <a href="default.htm" class="redImg"><img src="images/icons/quickstats/user.png" alt="" /></a>
                    <c:if test="${usuario.getPassword()!='invitado'}">
                    <div class="floatR"><strong class="blue">${usuario.getSeguidores().size()}</strong><span>Amigos</span></div>
                        </c:if>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
    
<!-- Mensaje de validacion del formulario -->
<c:set var="mensajeValidacionIncorrecto" value="${param.mensajeValidacionIncorrecto}"></c:set>
<c:if test="${not empty mensajeValidacionIncorrecto}">
    <div class="nNote nFailure">
        <p>${mensajeValidacionIncorrecto}</p>
    </div>
</c:if>

<c:set var="mensajeValidacionCorrecto" value="${param.mensajeValidacionCorrecto}"></c:set>
<c:if test="${not empty mensajeValidacionCorrecto}">
    <div class="nNote nSuccess">
        <p>${mensajeValidacionCorrecto}</p>
    </div> 
</c:if>
<!-- Fin de mensaje de validacion del formulario -->



<div class="widget">
    <c:if test="${usuario.getPassword()!='invitado'}">
        <div class="body">

            <div class="messageTo">
                <a href="#" title="" class="uName"><img src="images/icons/color/issue.png" alt="" /></a><span> Escriba su publicación </span>

            </div>
            <form action="./PublicacionController" method="POST">
                <input type="hidden" name="accion" value="publicandoEntrada" />
                <textarea name="publicacion" rows="5" cols="3" style="resize: none;" name="textarea" class="auto" placeholder="Digite el contenido de su publicación de máximo 200 caracteres" maxlength="200"></textarea>
                <div class="formRow">
                    <div class="grid9"> 
                        <input name="privada" type="radio" value="true"/> <- Visible solo para seguidores<br/>
                        <input name="privada" type="radio" value="false"/> <- Visible para todo público.
                    </div>                            
                    <div class="sendBtn sendwidget">
                        <input type="submit" name="sendMessage" class="buttonM bLightBlue" value="Publicar" />
                    </div>
                    <div class="clear"></div>
                </div>
            </form>
            <div>
                <c:set value="${sessionScope.listaImagenes}" scope="session" var="p"></c:set>
                    <form align="center" action="./subirImagenController" enctype="multipart/form-data" method="POST">
                        <input type="hidden" name="accion1" value="publicarImagen" />
                        <input id="images" type="file" name="multipleImagen"> 
                        <input type="button" id="clickImage" value="Add Image" class="buttonS bGreen"/><br>
                        <input id="upload" type="submit" value="Subir Foto" >
                        <a id="clickImage2" href="#" class="buttonM bDefault"><span class="icos-copypaste"></span><span>Subir Foto</span></a>
                        <!--<input type="button" id="clickImage2" value="Subir Foto" class="buttonM bGreyish""/><br>-->
                    </form>
                <c:forEach var="p" items="${p}">
                    <img src="${p}" alt="Picture" style="width: 75px; height: 70px"><a href="./subirImagenController?accion1=${p}"><i>X</i></a>
                </c:forEach>

                <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" charset="utf-8"></script>
                <script>
                    $('#images').hide();
                    $('#clickImage').click(function() {
                        $('#images').trigger('click');
                    });
                    $('#upload').hide();
                    $('#clickImage2').click(function() {
                        $('#upload').trigger('click');
                    });
                </script>

            </div>           
        </div>
    </c:if>
</div>   
<!-- Main content -->
<div class="wrapper">

    <div class="widget">
        <div class="whead">
            <h6>Mensajes Actuales</h6>
            <div class="on_off">
                <span class="icon-reload-CW"></span>
                <input type="checkbox" id="check1" checked="checked" name="chbox" />
            </div>            
            <div class="clear"></div>
        </div>

        <c:set var="p" value="${requestScope.publicarAll}" scope="request"></c:set>
        <c:forEach  var="p" items="${p}">
            <ul class="messagesOne">
                <li class="by_user">
                    <a href="#" title=""><img src="${p.getPublicadoPor().getUrlImagenPerfil()}" alt="" /></a>
                    <div class="messageArea">
                        <span class="aro"></span>
                        <div class="infoRow">
                            <span class="name"><strong>${p.getPublicadoPor().getUsername()}</strong> says:</span>
                            <span class="time">${p.getFechaPublicacion()}</span>
                            <div class="clear"></div>
                        </div>
                            
                            <textarea rows="4" cols="50" style="overflow:auto;resize:none">
                                ${p.getDescripcion()}
                            </textarea>
                            <div>
                                <c:forEach var="foto" items="${p.getUrlImagenes()}">
                                    <img src="${foto.geturlPath()}" alt=""style="max-height:100%; max-width:100%;" />
                                </c:forEach>
                            </div>
                    </div>
                    <div class="clear"></div>
                </li>

                <li class="divider"><span></span></li>    
            </ul>
        </c:forEach>
    </div>
 </div>
 <!-- Main content ends -->
    
</div>
<!-- Content ends -->
<jsp:include page="layout/footer.jsp"></jsp:include>