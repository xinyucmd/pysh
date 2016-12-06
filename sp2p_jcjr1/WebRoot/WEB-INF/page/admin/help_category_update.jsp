<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>帮助中心-栏目管理-修改</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#btnSave").click(function(){
			    $(this).hide();
				$("#updateHelpCategory").submit();
			});
		});
	</script>
</head>
<body>
    <s:form id="updateHelpCategory" action="updateHelpCategory.do" method="post" theme="simple">
    	<s:hidden id="typeId" name="paramMap.typeId" />
        <div id="right">
            <div style="padding: 15px 10px 0px 10px;">
                <div>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        	<td width="100" height="28"  class="xxk_all_a">
								<a href="queryHelpCategoryListPage.do">帮助中心列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">修改帮助类型</a>
							</td>
                            <td>
                               &nbsp;
                            </td>
                        </tr>
                    </table>
                </div>
                 
                <div style="width: auto; background-color: #FFF; padding: 10px;">
                    <table width="100%" border="0" cellspacing="1" cellpadding="3">
                        <tr>
                            <td style="width: 100px; height: 25px;" align="right" class="blue12">
                               帮助类型序号：</td>
                            <td align="left" class="f66">
                            	<s:textfield name="paramMap.id" readonly="true" cssClass="in_text_2" size="25" value="%{paramMap.id}"  />
                                <span class="require-field">*<s:fielderror fieldName="paramMap['id']"></s:fielderror></span>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 100px; height: 25px;" align="right" class="blue12">
                               帮助类型名称：</td>
                            <td align="left" class="f66">
                            	<s:textfield name="paramMap.helpDescribe" cssClass="in_text_2" size="25" value="%{paramMap.helpDescribe}"/>
                                <span class="require-field">*<s:fielderror fieldName="paramMap['helpDescribe']"></s:fielderror></span>
                            </td>
                        </tr>
                        <tr>
                            <td height="36" align="right" class="blue12">
                                &nbsp;</td>
                            <td>
                                    <button id="btnSave" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px" onclick="return false;"></button>
                                    &nbsp;<button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"></button>
                            </td>
                        </tr>
                    </table>
                    <br />
                </div>
            </div>
        </div>
    </s:form>
</body>
</html>