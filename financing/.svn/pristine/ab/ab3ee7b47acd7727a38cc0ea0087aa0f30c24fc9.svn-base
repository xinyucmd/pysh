<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.jiangchuanbanking.util.DateTimeUtil"%>
<%@ page import="com.jiangchuanbanking.system.domain.User"%>
<%@ page language="java" import="com.jiangchuanbanking.util.AppConfig"
	session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../../css/redmond/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../../css/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="../../css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="../../js/datagrid-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="../../js/ui.multiselect.js"></script>
<script type="text/javascript" src="../../js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../js/i18n/grid.locale-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>
	<%
	request.setCharacterEncoding("utf-8");
	%>
<script type="text/javascript">
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m);
}

function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
	function reportSubmit(){
		var params = "";
		var month = $('#month').datebox('getValue'); 
		var pact_no = $("#pact_no").val();
		if (month==null||month=="") {
			alert("请选择查询月度！");
			return false;
		}
		if(pact_no!=null&&pact_no!=""){
			params+=" and pact_no ='"+pact_no+"'";
		}
		if(month!=null&&month!=""){
			params+="  and to_char(t.start_date,'MM') = '"+month.split("-")[1]+"'";
		} 
 reporturl="pub/reportpub.jsp?raq=jcwealth/monthPaySum.raq&&params=year="+month.split("-")[0]+";month="+month.split("-")[1]+";test="+params;
	window.showModelessDialog("<%=AppConfig.getReportURL() %>"+reporturl,"a","dialogWidth:1200px;dialogHeight:800px");
	}
</script>
</head>
<body>
	<div id="page-wrap">
		<s:include value="../header.jsp" />
		<s:include value="../menu.jsp" />
		<div id="feature">
			<s:include value="../navigation.jsp" />
			<div id="feature-title">
				<h2>
					<s:text name="查询条件" />
				</h2>
			</div>

			<div id="feature-content">
				<table style="" cellspacing="10" cellpadding="0" width="100%">
					<s:if test="hasActionErrors()">
						<tr>
							<td align="left" colspan="4"><font color="red"><s:actionerror /></font></td>
						</tr>
					</s:if>
				</table>
				<s:form id="addObjectForm" validate="true"
					namespace="/jsp/statistics" method="post">
					<table style="" cellspacing="10" cellpadding="0" width="100%">
						<tr>
							<td class="td-label"><label class="record-label"> <s:text
										name="合同编号"></s:text>：
							</label></td>
							<td class="td-value"><label class="record-label"><input
									name="pact_no" id="pact_no"></label></td>
							<td class="td-label"><label class="record-label"> <s:text
										name="月度"></s:text><font color="red">*</font>：
							</label></td>
							<td class="td-value"><input name="month" id="month" class="easyui-datebox" 
							data-options="formatter:myformatter,parser:myparser"></td>

						</tr>
							
					</table>
					<div id="shortcuts" class="headerList">
								<b style="white-space: nowrap; color: #444;"><s:text
										name="title.action" />:&nbsp;&nbsp;</b> <span
									style="white-space: nowrap;"> <a id="submit" href="#"
									class="easyui-linkbutton" iconCls="icon-search" plain="true"  onclick="reportSubmit();" ><s:text
											name='查找' /></a>
								</span> </label>
							</div> 
					<%-- <div id="feature-title">
						<h2>
							<s:text name="数据信息" />
						</h2>
					</div> --%>
					
				</s:form>
				<div id="pager" class="scroll"></div>
				<div id="filter" style="margin-left: 30%; display: none">
					<s:text name="title.listAccount" />
				</div>
			</div>
		</div>
		
		<s:include value="../footer.jsp" />
	</div>
</body>
</html>