<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="disableCaching.jsp" %>
<%@ page errorPage="anerrorpage.jsp" %>
<%@ include file="language.jsp" %>

<%@ page import="com.agrologic.app.model.Language" %>
<%@ page import="com.agrologic.app.model.Program" %>
<%@ page import="com.agrologic.app.model.Screen" %>
<%@ page import="com.agrologic.app.model.User" %>

<% User user = (User) request.getSession().getAttribute("user");

    if (user == null) {
        response.sendRedirect("./index.htm");
        return;
    }

    Program program = (Program) request.getAttribute("program");
    Collection<Screen> screens = program.getScreens();
    Collection<Language> languages = (Collection<Language>) request.getAttribute("languages");

    String ptl = request.getParameter("translateLang");
    if (ptl == null) {
        ptl = "1";
    }
    Long translateLang = Long.parseLong(ptl);
%>

<!DOCTYPE html>
<html dir="<%=session.getAttribute("dir")%>">
<head>
    <title>Screen Manager - Screens</title>
    <link rel="StyleSheet" type="text/css" href="resources/custom/style/admincontent.css"/>
    <link rel="StyleSheet" type="text/css" href="resources/custom/style/menubar.css"/>
    <script type="text/javascript" src="resources/custom/javascript/general.js">;</script>
    <script type="text/javascript" src="resources/custom/javascript/util.js">;</script>

    <script language="Javascript">
        /**
         * Add new screen .
         */
        function addScreen() {
            redirect("./addscreen.html");
            return false;
        }
        /**
         * Remove screen .
         */
        function removeScreen(programId, screenId) {
            if (confirm("This action will remove screen from database.\nDo you want to continue?") == true) {
                redirect("./removescreen.html?programId=" + programId + "&screenId=" + screenId);
            }
        }

        function confirmRemove() {
            return confirm("This action will remove screen from database.\nDo you want to continue?");
        }
        /**
         */
        function filterProgramsScreens() {
            var programId = document.formFilterPrograms.Program_Filter.value;
            redirect("./all-screens.html?programId=" + programId);
            return false;
        }
        /**
         * Save changes for all screens position and display chackboxes
         */
        function save(programId) {
            if (!accept()) {
                return;
            }
            var showScreenMap = new Hashtable();
            var posScreenMap = new Hashtable();

            var chb = document.formScreen.list;
            var poss = document.formScreen.position;

            // if only one check table-list
            if (chb.tagName == "INPUT") {
                var screenId = chb.value;
                if (chb.checked) {
                    showScreenMap.put(screenId, "yes");
                } else {
                    showScreenMap.put(screenId, "no");
                }
                var poss = poss.value;
                posScreenMap.put(screenId, poss);
                // if two or more check table-list
            } else {
                for (var i = 0; i < chb.length; i++) {
                    var screenId = chb[i].value;
                    if (chb[i].checked) {
                        showScreenMap.put(screenId, "yes");
                    } else {
                        showScreenMap.put(screenId, "no");
                    }
                    var pos = poss[i].value;
                    posScreenMap.put(screenId, pos);
                }
            }
            redirect("./save-screens.html?programId=" + programId + "&showScreenMap=" + showScreenMap + "&posScreenMap=" + posScreenMap);
        }

        function uncheckUnusedData(programId) {
            if (!accept()) {
                return;
            }
            var cid = controllerId();
            redirect("./uncheck-unused-data.html?programId="
                    + programId + "&controllerId=" + cid);
        }

        function accept() {
            var fname = window.prompt("Please enter your password:", "Your password")
            if (fname == '<%=user.getPassword()%>') {
                return true;
            }
            return false;
        }
        function controllerId() {
            var controllerId = window.prompt("Please enter controller id :", "")
            return controllerId;
        }
        /**
         * Set display checkbox .
         */
        function check(index) {
            var form = document.getElementById('formScreen');
            var checkall = document.getElementById('listall');

            for (var i = 0; i < form.elements.length; i++) {
                if (form.elements[i].value == index && form.elements[i].checked == false) {
                    checkall.checked = false;
                }
            }
        }
        /**
         * Check all display chakboxes.
         */
        function checkedAll() {
            var form = document.getElementById('formScreen');
            var checkall = document.getElementById('listall');

            for (var i = 0; i < form.elements.length; i++) {
                if (checkall.checked == true) {
                    form.elements[i].checked = true;
                } else {
                    form.elements[i].checked = false;
                }
            }
        }
        /**
         *
         */
        function filterLanguages(programId) {
            var langId = document.formScreen.Lang_Filter.value;
            redirect("./all-screens.html?programId=" + programId + "&translateLang=" + langId);
            return false;
        }
        /**
         *
         */
        function setReadonly(screenid) {
            alert(screenid)
            document.getElementById("txt" + screenid).setAttribute("readonly", false);
            document.getElementById("txt" + screenid).focus();
        }
    </script>
</head>
<body>
<div id="header">
    <%@include file="usermenuontop.jsp" %>
</div>
<div id="main-shell">
    <table border="0" cellPadding=1 cellSpacing=1>
        <tr>
            <td>
                <h1 style=""><%=session.getAttribute("screens.page.header")%>
                </h1>

                <h2><%=session.getAttribute("screens.page.sub.header")%>
                </h2>

                <h3>Program - <font color="teal"><%=program.getName()%>
                </font></h3>
            </td>
            <td align="center">
                <jsp:include page="messages.jsp"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float:left"
                        onclick='redirect("./all-programs.html");'><%=session.getAttribute("button.back")%>
                </button>
                <button onclick='return save(<%=program.getId()%>);'><%=session.getAttribute("button.save")%>
                </button>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button id="Preview" name="Preview" type="button"
                        onclick="redirect('./screen-preview.html?programId=<%=program.getId()%>');">
                    <img src="resources/custom/images/preview.png" style="cursor: pointer" hspace="5"
                         border="0"/><%=session.getAttribute("button.preview")%>
                </button>
                <button id="Add" name="Add" type="button"
                        onclick="redirect('./add-screen.html?programId=<%=program.getId()%>');">
                    <img src="resources/custom/images/screen.gif" style="cursor: pointer" hspace="5"
                         border="0"/><%=session.getAttribute("button.add.screen")%>
                </button>
                <button id="Relays" name="Relays" type="button"
                        onclick="redirect('./program-relays.html?programId=<%=program.getId()%>&translateLang=<%=translateLang%>');">
                    <img src="resources/custom/images/relay.png" style="cursor: pointer" hspace="5" border="0"/>Relays
                </button>
                <button id="Alarms" name="Alarms" type="button"
                        onclick="redirect('./program-alarms.html?programId=<%=program.getId()%>');">
                    <img src="resources/custom/images/alarm1.gif" style="cursor: pointer" hspace="5" border="0"/>Alarms
                </button>
                <button id="SystemStates" name="System States" type="button"
                        onclick="redirect('./program-systemstates.html?programId=<%=program.getId()%>');">
                    <img src="resources/custom/images/system.gif" style="cursor: pointer" hspace="5" border="0"/>System States
                </button>
                <button id="btnUnchickUnusedData" name="btnUnchickUnusedData"
                        onclick="return uncheckUnusedData(<%=program.getId()%>);">
                    <img src="resources/custom/images/check.png" style="cursor: pointer" hspace="5" border="0"/>Clear Unused Data
                </button>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <%=screens.size()%>&nbsp;</b><%=session.getAttribute("label.records")%>
                <form id="formScreen" name="formScreen">
                    <table class="table-list" border=1 >
                        <thead>
                        <tr>
                            <th class="centerHeader" width="150px">Title</th>
                            <th class="centerHeader" width="180px">Text
                                <select id="Lang_Filter" name="Lang_Filter"
                                        onchange="return filterLanguages(<%=program.getId()%>);">
                                    <%for (Language l : languages) {%>
                                    <option value="<%=l.getId()%>"><%=l.getLanguage()%>
                                    </option>
                                    <%}%>
                                </select>
                            </th>
                            <th class="centerHeader" width="150px">Description</th>
                            <th class="centerHeader">Show All &nbsp;&nbsp;&nbsp;
                                <input type="checkbox" id="listall" name="listall" title="Show"
                                       onclick="checkedAll();"/></th>
                            <th class="centerHeader">Position</th>
                            <th class="centerHeader" width="250px" colspan="2">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% int cnt = 0;%>
                        <%for (Screen screen : screens) {%>
                        <% if ((cnt % 2) == 0) {%>
                        <tr class="odd" onMouseOver="changeOdd(this);" onmouseout="changeOdd(this)">
                                <%} else {%>
                        <tr class="even" onMouseOver="changeEven(this);" onmouseout="changeEven(this)">
                            <%}%>
                            <td style="padding-left:25px">
                                <a href="./all-tables.html?programId=<%=screen.getProgramId()%>&screenId=<%=screen.getId()%>&translateLang=<%=translateLang%>"><%=screen.getTitle()%>
                                </a></td>
                            <td class="leftCell"
                                ondblclick="window.open('add-screentranslate.jsp?screenId=<%=screen.getId()%>&langId=<%=translateLang%>&screenName=<%=screen.getTitle()%>','mywindow','status=yes,width=300,height=250,left=350,top=400,screenX=100,screenY=100');"><%=screen.getUnicodeTitle()%>
                            </td>
                            <td class="leftCell"><%=screen.getDescript()%>
                            </td>
                            <td class="centerCell">
                                <input type="checkbox" name="list" <%=screen.isChecked()%> value="<%=screen.getId()%>"
                                       onclick="check(<%=screen.getId()%>);"/></td>
                            <td class="centerCell">
                                <input type="text" name="position" value="<%=screen.getPosition()%>" size="5"/>
                            </td>
                            <td class="centerCell">
                                <img src="resources/custom/images/edit.gif" style="cursor: pointer" hspace="5" border="0" title='Edit'/>
                                <a href="./editscreenrequest.html?programId=<%=program.getId()%>&screenId=<%=screen.getId()%>">
                                    <%=session.getAttribute("button.edit")%>
                                </a>
                            </td>
                            <td class="centerCell">
                                <img src="resources/custom/images/close.png" title="Remove" hspace="5" style="cursor: pointer" border="0"/>
                                <a href="javascript:removeScreen(<%=screen.getProgramId()%>,<%=screen.getId()%>);">
                                    <%=session.getAttribute("button.delete")%>
                                </a>
                            </td>
                        </tr>
                        <%cnt++;%>
                        <%}%>
                        </tbody>
                    </table>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <button style="float:left" id="btnBack" name="btnBack"
                        onclick='return back("./all-programs.html");'><%=session.getAttribute("button.back")%>
                </button>
                <button id="btnSave" name="btnSave"
                        onclick='return save(<%=program.getId()%>);'><%=session.getAttribute("button.save")%>
                </button>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        var length = document.formScreen.Lang_Filter.options.length;
        for (var i = 0; i < length; i++) {
            var translateLang = parseInt(<%=request.getParameter("translateLang")%>);
            if (document.formScreen.Lang_Filter.options[i].value == translateLang) {
                document.formScreen.Lang_Filter.selectedIndex = i;
                break;
            }
        }
    </script>
</div>

</body>
</html>