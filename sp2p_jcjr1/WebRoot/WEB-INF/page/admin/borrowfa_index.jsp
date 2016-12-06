
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>资料下载-内容维护</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
			   		param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
					
			});
			
			function initListInfo(praData){
			    param["paramMap.state"] = '${paramMap.state}';
				param["paramMap.tname"] = $("#tname").val();
			    param["paramMap.telphone"] = $("#telphone").val();
		 		$.post("queryborrowfabiao.do",praData,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
					//分页
   		function pageInfo(pageId){
   			param["pageBean.pageNum"] = pageId;
   			initListInfo(param);
   		}
   		//删除单个
   		
	  function deletes(id){
   	   if(confirm("确定要删除吗?")){
    	window.location.href='deleteBorrowfaId.do?paramMap.id='+id
	 }

   }
		 		//判断是否有选中项
   		function checked(str){
   			var c = 0;
   			$(".biaoid").each( function(i, n){
				if(n.checked){
					c = 1;
				}
			});
			if(c==0){
				alert("请先选中您要"+str+"的项！");
				return false;
			}
			return true;
   		}
   		//删除多个选中项
   		function deleteBorrowfabiao(){
   			if(!checked("删除")){
   				return;
   			}
	 		if(!window.confirm("确定要删除所有选中记录?")){
	 			return;
	 		}
	 		var ids = "";
			$(".biaoid").each( function(i, n){
				if(n.checked){
					ids += n.value+",";
				}
			});
			ids = ids.substring(0,ids.lastIndexOf(","));
		 	window.location.href = "deleteBorrowfabiao.do?id="+ids;
   		}

   		//全选
   		function checkAll(e){
	   		if(e.checked){
	   			$(".biaoid").attr("checked","checked");
	   		}else{
	   			$(".biaoid").removeAttr("checked");
	   		}
   		}
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
						
						<s:if test="paramMap.state==null || paramMap.state==-1 ">
							<td width="100" height="28"  class="main_alll_h2">
							</s:if>
								<s:else>
							<td width="100" height="28"  class="xxk_all_a">
					               </s:else>
									<a href="borrowfabiao.do?paramMap.state=-1">申请列表</a>
							<td width="2">
									&nbsp;
								</td>
								<s:if test="paramMap.state==0">
							<td width="100" height="28"  class="main_alll_h2">
							    </s:if>
							      <s:else>
							<td width="100" height="28"  class="xxk_all_a">
							        </s:else>
									<a href="borrowfabiao.do?paramMap.state=0">未处理列表</a>
								</td>
							<td width="2">
									&nbsp;
								</td>
								<s:if test="paramMap.state==1">
							<td width="100" height="28"  class="main_alll_h2">
							       </s:if>
							        <s:else>
							<td width="100" height="28"  class="xxk_all_a">
							          </s:else>
									<a href="borrowfabiao.do?paramMap.state=1">已处理列表</a>
								</td>
							<td>
								&nbsp;<input type="hidden" value="${state}" name="states" id="states">
							    </td>
						</tr>
					</table>
					</div>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										联系人：
										<input id="tname" name="paramMap.tname" type="text"/>
										联系电话：
										<input id="telphone" name="paramMap.telphone" type="text"/>
										<input id="bt_search" type="button" value="查找"  />&nbsp;&nbsp;
										<input id="excel" type="button" value="导出Excel" name="excel" />
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
	</body>
</html>
