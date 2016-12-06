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
    <script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
   <style>
     ul li{ list-style:none;}
   	.table_top{ overflow:hidden; width:1090px;}
	.table_top ul{ width:1090px; overflow:hidden;}
	.table_top ul li{ height:40px; background:#e8ecef; border:solid 1px #ddd; width:120px; float:left; line-height:40px; text-align:center;}
	.table_top_a{ overflow:hidden; width:1090px;}
	.table_top_a ul{ width:1090px; overflow:hidden;}
	.table_top_a ul li{ height:36px;  border:solid 1px #ddd; width:120px; float:left; line-height:40px; text-align:center;}
   </style>
</head>
<body>
<form>
<div style="padding:15px;">
	<div>
        <div>
        	 <div>
        	     <ul>
        	       <li>
        	          <div style="height: "></div>          
        	      </li>
        	       <li>
        	      
        	                     体检码面额： <input type="text" name="" id="values" class="input_a"/>
        	                     体检码个数： <input type="text" name="" id="temp" class="input_a"/>
        	                     有效天数： <input type="text" name="" id="day" class="input_a"/>
        	        <input type="button" value="创建" onclick="createActityAnyMsg()"/>
        	        </li>
        	        <li style="margin:10px 0;">
        	                     体检卡号码： <input type="text" name="" id="check_code" class="input_a"/>
        	                     体检卡状态：  <select id="state">
        	                      <option value="">请选择</option> 
        	                      <option value="1">未发送</option> 
        	                      <option value="3">已发送</option>
        	                      <option value="2">已使用</option> 
        	                      <option value="4">已过期</option>
        	                      <option></option> 
        	                      <option></option> 
        	                  </select>
        	                    生成日期：<input type="text" name="" class="input_a" id="start_time"
        	                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        	                    失效日期：<input type="text" name="" class="input_a" id="end_time"
        	                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        	                
        	                <input type="button" id="recomendRelation_any" value="查找"/>
        	                 <input type="button" id="" value="导出" onclick="recomendRelation_excel_any()"/>
        	      </li>
        	      <li>
                   <div class="table_top" style="width:1200px;">
                   		<ul style="width:1200px;">
                        	<li style="width:50px;">编号</li>
                            <li>用户名</li>
                            <li>手机号</li>
                            <li>体检码</li>
                            <li>面值金额</li>
                            <li>有效天数</li>
                            <li>生成时间</li>
                            <li>失效时间</li>
                            <li>状态</li>
                            <li>操作</li>
                        </ul>
                   </div>
                   <div id="tab_queryActivtyAny" class="table_top_a" style="width:1200px;"></div>
        	       </li>
        	       <li>
        	           <div id="Pagination" class="paging" style="margin:20px 0 0 500px; overflow:hidden;"></div> <br />
        	       </li>
        	      </ul>
            </div> 
        </div>
    </div>
</div>
</form>    
  <script src="/script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="/script/jbox/jquery.jBox-2.3.min.js"></script>
  <script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
  <script type="text/javascript" src="../script/jquery.pagination.js"></script>
  <script>
   //初始化分页变量
   var pageNum = 0; 
   var pageSize = 10; 
   var totalNum = 0;
   var m = 0;
   $('#recomendRelation_any').click(function(){
	   queryrecomendRelation_any(pageNum);
   });
   
   //翻页调用  
   function PageCallback(pageNum, jq) {             
	   queryrecomendRelation_any(pageNum);  
   }
  
   
   queryrecomendRelation_any(pageNum);
   function queryrecomendRelation_any(pageNum){
	var param={};
	    param["check_code"] = $('#check_code').val();
	    param["state"] = $('#state').val();
	    param["end_time"] = $('#end_time').val();
	    param["start_time"] = $('#start_time').val();
	    
	    param["pageNum"] = pageNum+1;
		param["pageSize"] = pageSize;
	    $.ajax({
		    type: "post",
		    url: "queryActivtyAny.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	 
		    	var tr_list_o = $("#tab_queryActivtyAny");
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
			    		strHtml +="<ul style='width:1200px;'>";
			    		strHtml +="<li align='center' style=' width:50px;'>"+(i+1)+"</li>";
			    		if(o.username==null){
			    			strHtml +="<li align='center'>-</li>";
	                    }
			    		if(o.username!=null){
			    			strHtml +="<li align='center'>"+o.username+"</li>";
	                    }
			    		if(o.mobilePhone==null){
			    			strHtml +="<li align='center'>-</li>";
	                    }
			    		if(o.mobilePhone!=null){
			    			 strHtml +="<li align='center'>"+o.mobilePhone+"</li>";
	                    }
	                   
	                    strHtml +="<li align='center' >"+o.check_code+"</li>";
	                    strHtml +="<li align='center'>"+o.values+".00元</li>";
	                    strHtml +="<li align='center'>"+o.day+"天</li>";
	                    strHtml +="<li align='center'>"+o.start_time_f+"</li>";
	                    
	                    if(o.end_time_f==null){
	                    	  strHtml +="<li align='center'>-</li>";
	                    }
	                    if(o.end_time_f!=null){
	                    	  strHtml +="<li align='center'>"+o.end_time_f+"</li>";
	                    }
	                  
	                    if(o.state==1){
	                    	 strHtml +="<li align='center'>未发送</li>";
	                    	 strHtml +="<li align='center'>-</li>";
	                    }
	                    if(o.state==2){
	                    	 strHtml +="<li align='center'>已使用</li>";
	                    	 strHtml +="<li align='center'>-</li>";
	                    }
	                    if(o.state==3){
	                    	 strHtml +="<li align='center'>已发送</li>";
	                    	  strHtml +="<li class='f66' align='center' height='36px'>"+'<a href="###" onclick="updateActivityAny('+"'"+o.id+"'"+','+"'"+o.check_code+"'"+');">编辑</a>'+"</li>";
	                    }
	                    if(o.state==4){
	                    	 strHtml +="<li align='center'>已过期</li>";
	                    	 strHtml +="<li align='center'>-</li>";
	                    }
	                   
						strHtml +="</ul>";
			    	}
		    		tr_list_o.append(strHtml);
		    	} 
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		       alert(errorThrown);
		    }
		}); 
   }
   
   function createActityAnyMsg(){
		 
		var param={};
		    param["values"] = $('#values').val();
		    param["temp"] = $('#temp').val();
		    param["day"] = $('#day').val();
		    param["pageNum"] = pageNum+1;
			param["pageSize"] = pageSize;
		    $.ajax({
			    type: "post",
			    url: "createActivtyAny.do",
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	if(data.state==1){
			    		alert("创建体检卡成功！"); 
			    		 queryrecomendRelation_any(pageNum);
			    	}
			    	if(data.state==0){
			    		alert("创建体检卡失败！");
			    	}
			    	if(data.state==-1){
			    		alert("创建体检卡出现异常！");
			    	}
			    	if(data.desc==1){
			    		alert("体检面额必须大于0元！");
			    	}
			    	if(data.desc==2){
			    		alert("生成体检码个数必须大于0！");
			    	}
			    	if(data.desc==3){
			    		alert("体检码有效天数必须大于0！");
			    	}
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
			       alert(errorThrown);
			    }
			}); 
	   }
 
function  updateActivityAny(id,check_code){
	 if(confirm("确定要编辑体检码 "+check_code+" 的使用状态吗？编辑后将不可恢复！")){
		 var param={};
		    param["id"] = id;
		     
		    $.ajax({
			    type: "post",
			    url: "updateActivtyAny.do",
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	if(data.state==1){
			    		alert("编辑成功！");
			    		queryrecomendRelation_any(pageNum);
			    	}
			    	if(data.state==0){
			    		alert("编辑失败！");
			    	}
			    	if(data.state==-1){
			    		alert("编辑出现异常！");
			    	}
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
			       alert(errorThrown);
			    }
			});  
	 }
}

function  recomendRelation_excel_any(){
	 
    var check_code = $('#check_code').val();
    var state  = $('#state').val();
    var end_time = $('#end_time').val();
    var start_time  = $('#start_time').val();
    window.location.href="queryActivtyAnyExcel.do?check_code="+check_code+"&state="+state+"&end_time="+end_time+"&start_time="+start_time; 
	  
}
</script>
</body>
</html>
