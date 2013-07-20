<%-- 
    Document   : seguirUsuario
    Created on : Jun 27, 2013, 8:20:21 PM
    Author     : Renso
--%>

<jsp:include page="./layout/head.jsp"></jsp:include>
<jsp:include page="./layout/topNav.jsp"></jsp:include>
<jsp:include page="./layout/menuNavMob.jsp"></jsp:include>
<jsp:include page="./layout/sideBar.jsp"></jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
        <jsp:include page="./layout/shortcuts.jsp"></jsp:include>

            <h4 align="center" >Lista de Usuarios Registrados en el Micro-Blog</h4>
            <!-- Media table -->
            <div class="widget check grid6">
                <div class="whead">
                    <h6>Usuarios</h6><div class="clear"></div>
                </div>                
            </div>        

        <jsp:include page="./layout/footer.jsp"></jsp:include>
