<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="anerrorpage.jsp" %>

<%@ include file="language.jsp" %>

<%@ page import="com.agrologic.app.model.Controller" %>
<%@ page import="com.agrologic.app.model.Program" %>
<%@ page import="com.agrologic.app.model.User" %>


<% User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        response.sendRedirect("./index.htm");
        return;
    }
    Long userId = Long.parseLong(request.getParameter("userId"));
    Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));
    Controller editController = (Controller) request.getAttribute("editcontroller");
    Collection<Program> programs = (Collection<Program>) request.getAttribute("programs");
    Collection<String> controllernames = (Collection<String>) request.getAttribute("controllernames");
%>

<!DOCTYPE html>
<html dir="<%=session.getAttribute("dir")%>">
<head>
    <title>Edit Controller</title>


    <link rel="StyleSheet" type="text/css" href="resources/custom/style/menubar.css"/>
    <link rel="StyleSheet" type="text/css" href="resources/custom/style/admincontent.css"/>
    <script type="text/javascript" src="resources/custom/javascript/general.js">;</script>
    <script type="text/javascript" language="javascript">

        function validate() {

            if (document.editForm.Ncontrollername.value == "None") {
                alert("You must select controller name.");
                event.returnValue = false;
                editForm.Ncontrollername.focus();
            }
            if (document.editForm.NprogramId.value == "None") {
                alert("You must select program name.");
                event.returnValue = false;
                editForm.NprogramId.focus();
            }
            if (document.editForm.Ntitle.value == "") {
                alert("You must enter a valid title field");
                event.returnValue = false;
                editForm.Ntitle.focus();
            } else {
                document.editForm.Ntitle.value = encode(document.editForm.Ntitle);
            }
            if (document.editForm.Nnetname.value == "") {
                alert("You must enter a valid net name field");
                event.returnValue = false;
                editForm.Nnetname.focus();
            }
        }
        function encode(txtObj) {
            var source = txtObj.value;
            var result = '';
            for (i = 0; i < source.length; i++)
                result += '&#' + source.charCodeAt(i) + ';';
            return result;
        }
        function showNewName() {
            var checked = editForm.newControllerName.checked;
            if (checked == true) {
                document.getElementById('existingNameDiv').style.display = "none";
                document.getElementById('newNameDiv').style.display = "inline";
            } else {
                document.getElementById('existingNameDiv').style.display = "inline";
                document.getElementById('newNameDiv').style.display = "none";
            }
        }
    </script>
</head>
<body>
<div id="header">
    <%@include file="usermenuontop.jsp" %>
</div>
<div id="main-shell">
    <form id="editForm" name="editForm"
          action="editcontroller.html?userId=<%=userId%>&cellinkId=<%=cellinkId %>&controllerId=<%=editController.getId() %>"
          method="post" onsubmit="return validate();">
        <table border="0" cellPadding=1 cellSpacing=1 width="100%">
            <tr>
                <td valign="top" style="padding-top:0px">
                    <h1>Edit Controller</h1>

                    <p>

                    <h2>edit controller <%=editController.getId() %>
                    </h2>

                    <div><p style="color:red">Boxes with an asterisk next to them are required</p></div>
                    <p>

                    <table>
                        <tr>
                            <td class="rightCell">Title *</td>
                            <td><input type="text" name="Ntitle" style="width:100px"
                                       value="<%=editController.getTitle()%>"></td>
                        </tr>
                        <tr>
                            <td class="rightCell">Net Name *</td>
                            <td><input type="text" name="Nnetname" style="width:100px"
                                       value="<%=editController.getNetName()%>"></td>
                        </tr>
                        <tr>
                            <td class="rightCell"> Program Version *</td>
                            <td align="left">
                                <select id="NprogramId" name="NprogramId" style="width:120px">
                                    <option value="None" selected>Select
                                            <% for(Program p:programs) {%>
                                    <option value="<%=p.getId() %>"><%=p.getName() %>
                                            <%}%>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="rightCell">Name&nbsp;</td>
                            <td>
                                <div id="existingNameDiv" name="existingNameDiv" style="display:block;">
                                    <select id="Ncontrollernamelist" name="Ncontrollernamelist" class="dropDownList"
                                            style="width:130px">
                                        <option value=""></option>
                                        <% for (String n : controllernames) {%>
                                        <option value="<%=n%>"><%=n%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                                <div id="newNameDiv" name="newNameDiv" style="display:none;">
                                    <input type="text" name="Ncontrollername" onfocus="this.style.background='orange'"
                                           onblur="this.style.background='white'"/>
                                </div>
                            </td>
                            <td><input type="checkbox" id="newControllerName" name="newControllerName"
                                       onclick="showNewName()">
                                Add Name</input></td>
                        </tr>
                        <tr>
                            <td class="rightCell">Status</td>
                            <td>
                                <%if (editController.isActive()) {%>
                                <input type="checkbox" name="Nactive" id="Nactive" checked>
                                <%} else {%>
                                <input type="checkbox" name="Nactive" id="Nactive">
                                <%}%>
                                Active</input>
                            </td>
                            <td class="rightCell">&nbsp;</td>
                        </tr>

                    </table>

                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <%if (user.getRole() == UserRole.ADMIN) {%>
                    <button name="btnCancel"
                            onclick='return back("./cellink-setting.html?userId=<%=userId %>&cellinkId=<%=cellinkId%>");'>
                        <%=session.getAttribute("button.cancel") %>
                    </button>
                    <%} else {%>
                    <button name="btnCancel"
                            onclick='return back("./cellink-setting.html?userId=<%=userId %>&cellinkId=<%=cellinkId%>");'>
                        <%=session.getAttribute("button.cancel") %>
                    </button>
                    <%}%>
                    <button name="btnOk" type="submit">
                        <%=session.getAttribute("button.ok") %>
                    </button>
                </td>
            </tr>
        </table>
    </form>
</div>

<script language="Javascript">
    var length = document.editForm.NprogramId.options.length;
    var programId =
    <%= editController.getProgramId() %>
    for (var i = 0; i < length; i++) {
        if (document.editForm.NprogramId.options[i].value == programId) {
            document.editForm.NprogramId.selectedIndex = i;
            break;
        }
    }

    var length = document.editForm.Ncontrollernamelist.options.length;
    var ctrlname = '<%= editController.getName() %>'
    for (var i = 0; i < length; i++) {
        if (document.editForm.Ncontrollernamelist.options[i].value == ctrlname) {
            document.editForm.Ncontrollernamelist.selectedIndex = i;
            break;
        }
    }
</script>
</body>
</html>