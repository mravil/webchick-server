<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html dir="<%=session.getAttribute("dir")%>">
<head>
    <script language="JavaScript">
        function redirect() {
            window.location = "login.jsp";
        }
    </script>
</head>
<body onload="redirect()">
</body>
</html>