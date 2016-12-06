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
                   <div class="table_top" style="margin-left:150px;">
                   		<ul>
                            <li>奖励总额</li>
                            <li>注册奖励</li>
                            <li>投资奖励</li>
                            
                            <li>推荐注册奖励</li>
                            <li>累计推荐注册奖励</li>
                            <li>推荐投资奖励</li>
                            <li>累计推荐投资奖励</li>
                            
                        </ul>
                        <ul>
                            <li><fmt:formatNumber value="${all}" pattern="#0.00"></fmt:formatNumber></li>
                            <li><fmt:formatNumber value="${money1}" pattern="#0.00"></fmt:formatNumber></li>
                            <li><fmt:formatNumber value="${money2}" pattern="#0.00"></fmt:formatNumber></li>
                            <li><fmt:formatNumber value="${money3}" pattern="#0.00"></fmt:formatNumber></li>
                            <li><fmt:formatNumber value="${money4}" pattern="#0.00"></fmt:formatNumber></li>
                            <li><fmt:formatNumber value="${money5}" pattern="#0.00"></fmt:formatNumber></li>
                            <li><fmt:formatNumber value="${money6}" pattern="#0.00"></fmt:formatNumber></li>
                            
                        </ul>
                   </div>
        	       </li>
        	       <li>
        	            
        	       </li>
        	      </ul>
        	 
        	      <ul>
        	       <li>
        	          <div style="height: "></div>          
        	      </li>
                      <li style="margin:20px 0 5px 0;">
                                     用户名： <input type="text" name="parma.username" id="usernameFind" class="input_a"/>
                                     真实姓名：<input type="text" name="parma.realName" id="realNameFind" class="input_a"/>
                                     奖励类型：<select id="typeFind">
        	                    <option value="">请选择</option>
        	                    <option value="1">注册奖励</option> 
        	                    <option value="2">投资奖励</option> 
        	                    <option value="3">推荐注册奖励</option>  
        	                     <option value="4">累计推荐注册奖励</option>     
        	                     <option value="5">推荐投资奖励</option>     
        	                     <option value="6">累计推荐投资奖励</option>     
        	                 </select>
        	      奖励时间：<input type="text" name="" class="input_a" id="time1Find"
        	                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        	                     至<input type="text" name="" class="input_a" id="time2Find"
        	                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
                      <input type="button" id="recomendRelation" value="查找"/>
        	      </li>
        	      <li style="margin-left:200px;">
                   <div class="table_top">
                   		<ul>
                        	<li style="width:50px;">编号</li>
                            <li>用户名</li>
                            <li>真实姓名</li>
                            <li>奖励金额</li>
                            <li>奖励类型</li>
                            <li style="width:200px;">奖励时间</li>
                        </ul>
                   </div>
                   <div id="tab_admin_user_content" class="table_top_a">
                   </div>
        	       </li>
        	       <li style="margin-left:100px;">
        	           <div id="Pagination" class="paging" style="margin:20px 0 0 300px; overflow:hidden;"></div> <br />
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
   $('#recomendRelation').click(function(){
	    var t1 = $('#time1Find').val();
		var t2 = $('#time2Find').val();
		if(t1!='' && t2!=''){
			if(t1>=t2){
				alert('开始时间不能大于或等于结束时间');
				return;
			} 
		}
		
	   queryMoneySend(pageNum);
   });
   
   //翻页调用  
   function PageCallback(pageNum, jq) {             
	   queryMoneySend(pageNum);  
   }
   
   queryMoneySend(pageNum);
   function queryMoneySend(pageNum){
	var param={};
	    param["username"] = $('#usernameFind').val();
	    param["reallyName"] = $('#realNameFind').val();
	    param["type"] = $('#typeFind').val();
	    param["time1"] = $('#time1Find').val();
	    param["time2"] = $('#time2Find').val();
	   
	    param["pageNum"] = pageNum+1;
		param["pageSize"] = pageSize;
	    $.ajax({
		    type: "post",
		    url: "queryMoneySendPage.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	 
		    	var tr_list_o = $("#tab_admin_user_content");
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
			    		strHtml +="<ul>";
			    		strHtml +="<li align='center' style='width:50px;'>"+(i+1)+"</li>";
	                    strHtml +="<li align='center'>"+o.username+"</li>";
	                    //strHtml +="<li align='center'>"+o.realName+"</li>";
	                    if(o.realName==null){
	                    	 strHtml +="<li align='center'>未知</li>";
	                    }
	                    if(o.realName!=null){
	                    	strHtml +="<li align='center'>"+o.realName+"</li>";
	                    }
	                    strHtml +="<li align='center' >"+o.reward_amount+"</li>";
	                    if(o.reward_type==1){
	                    	 strHtml +="<li align='center'>注册奖励</li>";
	                    }
	                    if(o.reward_type==2){
	                    	 strHtml +="<li align='center'>投资奖励</li>";
	                    }
	                    if(o.reward_type==3){
	                    	 strHtml +="<li align='center'>推荐注册奖励</li>";
	                    }
	                    if(o.reward_type==4){
	                    	 strHtml +="<li align='center'>累计推荐注册奖励</li>";
	                    }
	                    if(o.reward_type==5){
	                    	 strHtml +="<li align='center'>推荐投资奖励</li>";
	                    }
	                    if(o.reward_type==6){
	                    	 strHtml +="<li align='center'>累计推荐投资奖励</li>";
	                    }
	                   
	                    strHtml +="<li align='center' style='width:200px;'>"+o.reward_times+"</li>";
						strHtml +="</ul>";
			    	}
		    		tr_list_o.append(strHtml);
		    	} 
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		       alert(111);
		    }
		}); 
   }
  
</script>
</body>
</html>
