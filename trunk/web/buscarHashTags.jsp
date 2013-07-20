<%-- 
    Document   : buscarHashTags
    Created on : Jun 28, 2013, 11:29:05 PM
    Author     : Luis
--%>

<jsp:include page="./layout/head.jsp"></jsp:include>
<jsp:include page="./layout/topNav.jsp"></jsp:include>
<jsp:include page="./layout/menuNavMob.jsp"></jsp:include>
<jsp:include page="./layout/sideBar.jsp"></jsp:include>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    
    <script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#examplee').dataTable();
			} );
    </script>
    <div class="wrapper">
        
        <c:set var="listaHashtags" scope="request" value="${requestScope.listaHashtags}" />
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="examplee" width="100%">
            <thead>
                    <tr>
                            <th>Hashtag</th>
                            <th>Publicacion</th>
                            <th>Publicado por</th>
                            <th>Fecha publicacion</th>
                    </tr>
            </thead>
            <tbody>
                <c:forEach var="hh" items="${listaHashtags}">
                    <c:forEach var="pu" items="${hh.getPublicaciones()}">
                    <tr class="odd gradeA">
                        <td>
                            ${hh.getNombre()}
                        </td>
                        <td>
                            ${pu.getDescripcion()}
                        </td>
                        <td>
                            ${pu.getPublicadoPor().getNombre()}
                        </td>
                        <td>
                            ${pu.getPublicadoPor().getFechaCreacion()}
                        </td>
                    </tr>
                    </c:forEach>
                    
                </c:forEach>
                    
            </tbody>
    </table>
    
 
            
    </div>

        
        
    <jsp:include page="./layout/footer.jsp"></jsp:include>