<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
    <title>${sitemap.siteName}-手机注册</title>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="css/css.css" rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
    <link href="../script/pagination.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
   <link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
</head>
<style>
.close_btn{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; left:600px; cursor:pointer; top:10px; border:none;}
.s_login_txt{ width:150px; text-align:right; display:inline-block; font-size:12px; margin-right:10px;}
.input_a{ width:160px; height:26px;border:none; border:solid 1px #c7c7c7; }
.input_b{ padding:0 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 1px #c7c7c7;; margin-left:10px;}
.input_c{ padding:2px 10px; background:#e5f3fa; border:none; line-height:26px; border-radius:3px; border:solid 2px #c7c7c7;; margin-left:10px; cursor:pointer;}
.input_d{ padding:0 10px; background:#c7c7c7; border:none; line-height:26px; border-radius:3px;  margin-left:5px; cursor:pointer;}
.zh_table th {background-color: #f9f9f9;border: 1px solid #ddd;font-weight: normal;text-align: center; width:14%;}
.zh_table td {border: 1px solid #ddd;text-align: center; width:14%;}
#ul_input li{ line-height:40px;}
.theme-popover{ width:400px; height:340px; position:fixed; display:none; z-index:9999; top:0; background:#fbfbfb; border-radius:8px; left:35%; border:solid 2px #dbdada; font-size:14px;}
.close{ background:url(../images/admin/close.jpg) no-repeat; top:5px;right:5px;  position:absolute; z-index:10001; height:27px; width:27px;}
input{ border:none;}
	.theme-popover ul { margin:30px 40px;}
	.theme-popover ul li{ line-height:46px;}
	.theme-popover ul li label{}
	.btn-big{ width:160px; height:26px; border:solid 1px #CCC; color:#000; padding-left:10px;}
	.menu{ overflow:hidden;}
	.menu ul li{ float:left; margin:0 10px;}
	ul li{ list-style:none;}
	a{ text-decoration:none;}
</style>
<body>
<!-- 引用头部公共部分 -->


<!--内页主体 开始-->
<form>
<div style="padding: 15px 10px 0px 10px;">
	<div>
	  <div>
	      <input type="button" value="导出" onclick="exports()" id="excel"/>
	  </div>
      <div style=" background-color: #fff;">
      		<table width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-top:10px;" class="zh_table">
					 <tr >
						<th>编号</th>
						<th>用户名</th>
						<th style="width:19%;">手机号码</th>
						<th>真实姓名</th>
						<th>体验金份数</th>
						<th>生成时间</th>
						<th>操作</th>
					</tr>	
			</table>
			<table id="tab_admin_publish_content" width="100%" cellspacing="0" cellpadding="0" border="0" class="zh_table" style="margin:0;"></table>
      </div>
    </div>
    <div id="Pagination" class="paging" style="margin:20px 0 0 500px; overflow:hidden;"></div>
</div>
<div class="theme-popover" >
        <div>
       	  <ul>
            	<li>
                	<label>用户名：</label>
                    <span id="username"></span>
                </li>
                
                <li>
                	<label>手机号：</label>
                   <span id="cell"></span>
                </li>
                 <li>
                	<label>真实姓名：</label>
                     <span id="reallyName"></span>
                </li>
                <li>
                	<label>体验金份数：</label>
                    <span>
                       <input id="amount" style="width: 80px;"/>份
                       <input id="id" type="hidden" value=""/>
                       <input id="userId" type="hidden" value=""/>
                    </span>
                </li>
                <li>
                	<label>生成时间：</label>
                    <span id="createTime"></span>
                </li>
                
            </ul>
            <p style="text-align:center;"><button onclick="updateEmployeeConfigs()" type="button" value="确定提交"  style="width:80px; height:30px; background:#1367b6; color:#fff; border:none; line-height:30px; text-align:center;">确认提交</button></p>
        </div>	
        <div class="theme-poptit"><a href="javascript:closeCode();" title="关闭" class="close"></a></div>
    </div>
  
</form>
<!--内页主体 结束-->

<!-- 引用底部公共部分 -->     
  <script src="/script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="/script/jbox/jquery.jBox-2.3.min.js"></script>
  <script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
  <script type="text/javascript" src="../script/jquery.pagination.js"></script>
	 <script>
    jQuery(document).ready(function($) {
        $('.theme-login').click(function(){
            $('.theme-popover').slideDown(200);
        })
        $('.theme-poptit .close').click(function(){
            $('.theme-popover').slideUp(200);
        })
    
    })
    </script>
  <script>
  
   //初始化分页变量
   var pageNum = 0; 
   var pageSize = 10; 
   var totalNum = 0;
   
   $('#queryAminUserBtn').click(function(){
	   queryAdminUser(pageNum);
   });
   
   //翻页调用  
   function PageCallback(pageNum, jq) {             
	   queryAdminUser(pageNum);  
   }
   
   queryAdminUser(pageNum);
   function queryAdminUser(pageNum){
	var param={};
	  
	    param["pageNum"] = pageNum+1;
		param["pageSize"] = pageSize;
	    $.ajax({
		    type: "post",
		    url: "queryEmployeeConfigList.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	 
		    	var tr_list_o = $("#tab_admin_publish_content");
		    	tr_list_o.empty();
		    	
		    	totalNum = data.totalNum;
		    	$("#Pagination").pagination(totalNum, {  
		    	    callback: PageCallback, 
		    	    maxentries:4,
		    	    prev_text: '上一页',  
		    	    next_text: '下一页',  
		    	    items_per_page: pageSize,  //每页显示条目数
		    	    num_display_entries: 6,//连续分页主体部分分页条目数  
		    	    current_page: pageNum,//当前页索引  
		    	    num_edge_entries: 2//两侧首尾分页条目数  
		    	}); 
		    	
		    	if(data.result != null && data.result != undefined && data.result != ''){
			    	var strHtml = "";
		    		for(var i=0;i<data.result.length;i++){
			    		var o = data.result[i];
			    		strHtml +="<tr>";
			    		strHtml +="<td align='center'>"+(i+1)+"</td>";
	                    strHtml +="<td align='center' >"+o.username+"</td>";
	                    strHtml +="<td align='center' style='width:19%;'>"+o.mobilePhone+"</td>";
	                    strHtml +="<td align='center' >"+o.realName+"</td>";
	                    if(o.amount==null){
	                    	 strHtml +="<td align='center'>"+0+"</td>";
	                    }else{
	                    	 strHtml +="<td align='center'>"+o.amount+"</td>";
	                    }
	                    if(o.createTime==null){
	                    	 var time = '';
	                    	 strHtml +="<td align='center'>"+time+"</td>";
	                    }else{
	                    	 strHtml +="<td align='center'>"+o.createTime+"</td>";
	                    }
	                   
	                    strHtml +="<td align='center'>"+'<a href="###" onclick="editeEmployeeConfig('+"'"+o.id+"'"+','+"'"+o.username+"'"+','+"'"+o.mobilePhone+"'"+','+"'"+o.realName+"'"+','+"'"+o.amount+"'"+','+"'"+o.createTime+"'"+','+"'"+o.userId+"'"+');">编辑</a>'+"</td>";
						strHtml +="</tr>";
			    	}
		    		tr_list_o.append(strHtml);
		    	} 
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		       alert(errorThrown);
		    }
		}); 
   }
   
   
   function updateEmployeeConfigs(){
	   var param = {}
	   var amount = $("#amount").val();
	   param["id"] = $("#id").val();
	   param["amount"] =  amount;
	   param["userId"] =  $("#userId").val();
	    
	   $.ajax({
		    type: "post",
		    url: "updateEmployeeConfig.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		        if(data.msg=='1'){
		        	  alert('设置成功');
		        	  history.go(0);
		        }else if(data.msg=='-1'){
		        	  alert('设置失败');
		        }else{
		        	 alert('异常信息');
		        }
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
		    }
		});
   }
   
   
   function  editeEmployeeConfig(id,username,mobilePhone,realName,amount,createTime,userId){
	   $('#id').val(id);
	   $('#userId').val(userId);
	   $('#username').text(username);
	   $('#cell').text(mobilePhone);
	   $('#reallyName').text(realName);
	   if(amount=='null'){
		   $('#amount').val(0);
	   }else{
		   $('#amount').val(amount);
	   }
	   if(createTime=='null'){
		   $('#createTime').text('');
	   }else{
		   $('#createTime').text(createTime);
	   }

	   $('.theme-popover').slideDown(200);
   }
   
   
   function  exports(){
	   
		  
				  $("#excel").attr("disabled",true);
				   window.location.href=encodeURI(encodeURI("exportEmployeeConfigListExcel.do"));
   }
    
</script>
</body>
</html>
