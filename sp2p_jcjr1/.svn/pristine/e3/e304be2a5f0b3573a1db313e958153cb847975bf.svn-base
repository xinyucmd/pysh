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
    	<div class="menu">
            <ul>
                <li class="xxk_all_a"><a class="theme-login">发体验标</a></li>
            </ul>
        </div>
      <div style=" background-color: #fff;">
      		<table width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-top:10px;" class="zh_table">
					 <tr >
						<th>编号</th>
						<th>发标标题</th>
						<th>年化收益率</th>
						<th>借款期限</th>
						<th>借款金额</th>
						<th>借款份数</th>
						<th>发标时间</th>
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
                	<label>借款标题</label>
                    <input  id="name" name="" type="text"  class="btn-big"/>
                </li>
                
                <li>
                	<label>借款金额</label>
                    <input name="" type="text" value="10000" disabled="disabled" class="btn-big" />
                </li>
              
                <li>
                	<label>借款份数</label>
                    <input id="parm" name="" type="text" value="100" class="btn-big" />
                </li>
                <li>
                	<label style="position:relative; left:-15px;">年化收益率</label>
                    <select id="rate" style="position:relative; left:-15px;">
                         <option value="12">12%</option>
                    </select>
                </li>
                <li>
                	<label>借款期限</label>
                    <select id = "day">
                         <option value="30">30天</option>
                    </select>
                </li>
                
            </ul>
            <p style="text-align:center;"><button onclick="addEmployeeBorrows()" type="button" value="确定提交"  style="width:80px; height:30px; background:#1367b6; color:#fff; border:none; line-height:30px; text-align:center;">确认提交</button></p>
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
		    url: "queryEmployeeBorrowList.do",
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
	                    strHtml +="<td align='center' >"+o.title_name+"</td>";
	                    strHtml +="<td align='center' >"+o.rate+"%</td>";
	                    strHtml +="<td align='center' >"+o.day+"天</td>";
	                    strHtml +="<td align='center'>"+o.amount_sum/10000+"万元</td>";
	                    strHtml +="<td align='center'>"+o.amount_sum+"份</td>";
	                    strHtml +="<td align='center'>"+o.createTime_f+"</td>";
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
   
   
   function addEmployeeBorrows(){
	   var param = {}
	   param["name"] = $("#name").val();
	   param["parm"] = $("#parm").val();
	   param["rate"] = $("#rate").val();
	   param["day"] = $("#day").val();
	   
	   $.ajax({
		    type: "post",
		    url: "addEmployeeBorrow.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		        if(data.msg=='发标成功'){
		        	  alert('发标成功');
		        	  history.go(0);
		        }else{
		        	alert(data.msg);
		        }
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
		    }
		});
   }
    
</script>
</body>
</html>
