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
    return y;
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
function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
 clock += day + " ";
 /* 
    if(hh < 10)
        clock += "0";
       
    clock += hh + ":";
    if (mm < 10) clock += '0'; 
    clock += mm;  */
    return(clock); 
} 
$(document).ready(function () {
	 $('#nowDate').val(CurentTime()); 
});
	function reportSubmit(){
		var params = "";
		var month = $('#month').datebox('getValue'); 
		var nowDate= $('#nowDate').val();
		if(nowDate!=null&&nowDate!=""){
			params+="year="+nowDate.split("-")[0]+";month="+nowDate.split("-")[1]+";day="+nowDate.split("-")[2];
		} 
		if(month!=null&&month!=""){
			params+=";year0="+month;
		} else{
			params+=";year0="+nowDate.split("-")[0];
		}
 	reporturl="pub/reportpub.jsp?raq=jcwealth/percentCalcul.raq&&params="+params;
	window.showModelessDialog("<%=AppConfig.getReportURL()%>" + reporturl,
				"a", "dialogWidth:1200px;dialogHeight:800px");
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
					<input name="nowDate" id="nowDate"	type="hidden"	>
					<table style="" cellspacing="10" cellpadding="0" width="100%">
						<tr>

							<td class="td-label"><label class="record-label"> <s:text
										name="年度"></s:text>：
							</label></td>
							<td class="td-value"><input name="month" id="month" class="easyui-datebox" 
							data-options="formatter:myformatter,parser:myparser"></td>
							<td class="td-label"></td>
							<td class="td-value"></td>
						</tr>

					</table>
					<div id="shortcuts" class="headerList">
						<b style="white-space: nowrap; color: #444;"><s:text
								name="title.action" />:&nbsp;&nbsp;</b> <span
							style="white-space: nowrap;"> <a id="submit" href="#"
							class="easyui-linkbutton" iconCls="icon-search" plain="true"
							onclick="reportSubmit();"><s:text name='查找' /></a>
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