<%--
    Document   : edit-alarm
    Created on : Oct 24, 2011, 3:16:59 PM
    Author     : Administrator
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page errorPage="anerrorpage.jsp" %>
<%@ page import="com.agrologic.app.model.User" %>

<% User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        response.sendRedirect("./index.htm");
        return;
    }

    Long alarmId = Long.parseLong(request.getParameter("alarmId"));
    String alarmName = request.getParameter("alarmName");
    Long translateLang = Long.parseLong(request.getParameter("translateLang"));
%>

<!DOCTYPE html>
<html dir="<%=session.getAttribute("dir")%>">
<head>

    <link rel="StyleSheet" type="text/css" href="resources/style/admincontent.css">
    <script language="Javascript">
        function reset() {
            document.getElementById("msgText").innerHTML = "";
        }
        function validate() {
            var valid = true;
            reset();
            if (document.addForm.Ntext.value == "") {
                document.getElementById("msgText").innerHTML = "&nbsp;Text can't be empty";
                document.getElementById("msgText").style.color = "RED";
                document.addForm.Ntranslate.focus();
                valid = false;
            }
            if (!valid) {
                return false;
            }
        }
        function closeWindow() {
            self.close();
            window.opener.location.replace("./all-alarms.html?translateLang=<%=translateLang%>");
        }
    </script>
    <title>Edit Alarm</title>
</head>
<body onunload="closeWindow();">
<table class="main" align="center" cellpadding="0" cellspacing="0" border="0" width="100%" style="padding:10px">
    <tr>
        <td>
            <h1>Edit alarm</h1>

            <p>

            <h2>edit alarm </h2>

            <form id="addForm" name="addForm" action="./edit-alarm.html" method="post" onsubmit="return validate();">
                <table width="100%" align="left" border="0">
                    <input type="hidden" id="translateLang" name="translateLang" value="<%=translateLang%>">
                    <input type="hidden" id="NalarmId" name="NalarmId" value="<%=alarmId%>">
                    <tr>
                        <td align="left">Insert &nbsp;<%=alarmName%>
                    </tr>
                    <tr>
                        <td align="left"><input id="Ntext" type="text" name="Ntext" value="<%=alarmName%>">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" id="msgText" align="left"></td>
                    </tr>
                    <tr>
                        <td>
                            <button id="btnUpdate" name="btnUpdate" type="submit"><img src="resources/images/edit.gif"
                                                                                       hspace="5"><%=session.getAttribute("button.update") %>
                            </button>
                            <button type="button" onclick='self.close();'><img src="resources/images/close.png"
                                                                               hspace="5"><%=session.getAttribute("button.cancel") %>
                            </button>
                        </td>
                    </tr>
                </table>
            </form>
</table>
</body>
</html>