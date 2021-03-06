<%@ page import="com.agrologic.app.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="language.jsp" %>
<%@ page errorPage="anerrorpage.jsp" %>

<% User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        response.sendRedirect("./index.htm");
        return;
    }

%>
<!DOCTYPE html>
<html dir="<%=session.getAttribute("dir")%>">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Change password</title>

    <link rel="StyleSheet" type="text/css" href="resources/style/admincontent.css">
    <link rel="StyleSheet" type="text/css" href="resources/style/menubar.css">
    <script type="text/javascript" src="resources/javascript/general.js">;</script>
    <script type="text/javascript" language="javascript">

        function validate() {
            if (document.editForm.Opassword.value == "") {
                alert('You must enter your current password');
                document.editForm.Opassword.focus();
                return false;
            }
            if (document.editForm.Npassword.value.length < 5) {
                alert('Password must be at least 5 characters');
                document.editForm.Npassword.focus();
                return false;
            }
            if (document.editForm.NNpassword.value != document.editForm.Npassword.value) {
                alert("The new password and the confirmation password don't match. Please type the same password in both boxes.");
                return false;
            }
            document.editForm.submit();
        }
    </script>
</head>
<body>
<div id="header">
    <%@include file="usermenuontop.jsp" %>
</div>
<div id="main-shell">
    <table border="0" cellPadding=1 cellSpacing=1 width="100%">
        <tr>
            <td valign="top" height="648px">
                <table border=0 cellPadding=1 cellSpacing=1 width="600">
                    <tr>
                        <td style="font-size:x-small;font-weight:normal;" nowrap>
                            <a href="main.jsp">Home</a>
                        </td>
                    </tr>
                    <tr>
                        <td width="400" nowrap>
                            <p>

                            <h1><%=session.getAttribute("user.password.title") %>
                            </h1></p>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" colspan="3">
                            <jsp:include page="messages.jsp"/>
                        </td>
                    </tr>
                    <tr>
                        <td colSpan=2 width="600px">
                            <p>

                            <h2><%=session.getAttribute("user.password.subtitle") %>
                            </h2>

                            <div><p><%=session.getAttribute("user.password.descript") %>
                            </p></div>
                            <p>

                            <form action="change-password.jsp?userId=<%=user.getId()%>" method="post" id="editForm"
                                  name="editForm">
                                <table borderColor=gray cellSpacing=1 cellPadding=1 width="auto" border=0>
                                    <tr>
                                        <td><%=session.getAttribute("user.password.current") %>
                                        </td>
                                        <td><input type="password" name="Opassword" value=""></td>
                                    </tr>
                                    <tr>
                                        <td><%=session.getAttribute("user.password.new") %>
                                        </td>
                                        <td><input type="password" name="Npassword" value=""></td>
                                    </tr>
                                    <tr>
                                        <td><%=session.getAttribute("user.password.retype") %>
                                        </td>
                                        <td><input type="password" name="NNpassword" value=""></td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <button id="btnBack" name="btnBack" onclick='return back("./main.jsp");'>
                                                &nbsp;Back&nbsp;</button>
                                            <button id="btnUpdate" name="btnUpdate" onclick='return validate();'><img
                                                    src="resources/images/save.gif"> <%=session.getAttribute("button.save") %>
                                            </button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>

</body>
</html>