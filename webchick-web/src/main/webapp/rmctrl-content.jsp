<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ page errorPage="anerrorpage.jsp" %>
<%@ page import="com.agrologic.app.model.Cellink" %>

<jsp:directive.page import="com.agrologic.app.model.User"/>

<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        response.sendRedirect("./index.htm");
        return;
    }
    Cellink cellink = (Cellink) request.getAttribute("cellink");
%>

<!DOCTYPE html>
<html dir="<%=session.getAttribute("dir")%>">
<head>
    <title><%=cellink.getName()%>
    </title>

    <link rel="stylesheet" type="text/css" href="resources/style/rmtstyle.css"/>
</head>
<body>
<br>

<h2>Connected to <%=cellink.getName()%>
</h2>
<hr>
<br>

<h3><a href="rmctrl-main-screen.html?cellinkId=<%=cellink.getId()%>&screenId=1" target="body" class="leftlink"><img
        src="resources/images/ComputerScreen.gif" style="border:0px;height:32px;width:32px;"> Screens </a></h3>
Control your farm as if you were sitting in front of it.
<br>

<h3><a href="rmctrl-underconstruction.jsp" target="body" class="leftlink"><img src="resources/images/configure.png"
                                                                               style="border:0px;height:32px;width:32px;">
    Preferences</a></h3>
<!--a href="rmctrl-preferences.jsp" target="rightPage" class="leftlink"><img src="resources/images/configure.png" style="border:0px;height:32px;width:32px;"> Preferences</a-->
Set preferences to specifically meet your needs.
<br>

<h3><a href="rmctrl-underconstruction.jsp" target="body" class="leftlink"><img src="resources/images/support.png"
                                                                               style="border:0px;height:32px;width:32px;">
    Help</a></h3>
All the resources you need to take full advantage of Remote Cellink.
<hr>
</body>
</html>
