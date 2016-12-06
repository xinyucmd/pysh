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
                                     用户名： <input type="text" name="parma.username" id="usernameFind" class="input_a"/>
                                     手机号：<input type="text" name="parma.cellphone" id="cellphoneFind" class="input_a"/>
                                     真实姓名：<input type="text" name="parma.realName" id="realNameFind" class="input_a"/>
                               <input type="button" id="recomendRelation" value="查找"/>
        	      </li>
        	      <li>
                   <div class="table_top">
                   		<ul>
                        	<li style="width:50px;">编号</li>
                            <li>用户名</li>
                            <li>手机号</li>
                            <li>真实姓名</li>
                            <li>推荐总人数</li>
                            <li>推荐认证人数</li>
                            <li>推荐投资人数</li>
                            <li>推荐投资总额/元</li>
                            <li>操作</li>
                        </ul>
                   </div>
                   <div id="tab_admin_user_content" class="table_top_a">
                   		
                   </div>
        	       </li>
        	       <li>
        	           <div id="Pagination" class="paging" style="margin:20px 0 0 500px; overflow:hidden;"></div> <br />
        	       </li>
        	      </ul>
            </div> 
            
            <!-- 查看详情 -->
            <div>
        	     <ul>
        	       <li>
        	          <div style="height: "></div>          
        	      </li>
        	      <li>
        	        <input type="hidden" id="hiddenId"/>
        	                     用户名： <input type="text" name="" id="usernameFindDetail" class="input_a"/>
        	                     手机号：<input type="text" name="" id="cellphoneFindDetail" class="input_a"/>
        	                     真实姓名：<input type="text" name="" id="realNameFindDetail" class="input_a"/>
        	                     好友来源：<select id="srcFindDetail">
        	                    <option value="">请选择</option>
        	                    <option value="1">邀请链接</option> 
        	                    <option value="2">二维码扫码</option> 
        	                    <option value="3">填写推荐人</option>     
        	                 </select>
        	                     注册时间：<input type="text" name="" class="input_a" id="time1FindDetail"
        	                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        	                     至<input type="text" name="" class="input_a" id="time2FindDetail"
        	                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
        	                <input type="button" value="查找" onclick="recomendRelatioDetail()"/>
        	      </li>
        	      <li>
                   <div class="table_top">
                   		<ul>
                        	<li style="width:50px;">编号</li>
                            <li>用户名</li>
                            <li>手机号</li>
                            <li style="width:200px;">注册时间</li>
                            <li>真实姓名</li>
                            <li style="width:160px;">身份是否认证</li>
                            <li>好友来源</li>
                            <li>是否投资</li>
                        </ul>
                   </div>
                   <div id="tab_content_detail" class="table_top_a">
                   		
                   </div>
        	       </li>
        	       <li>
        	           <div id="PaginationDetail" class="paging" style="margin:20px 0 0 500px; overflow:hidden;"></div> <br />
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
   $('#recomendRelation').click(function(){
	   $('PaginationDetail').hide();
	   queryRecomendRelation(pageNum);
   });
   
   //翻页调用  
   function PageCallback(pageNum, jq) {             
	   queryRecomendRelation(pageNum);  
   }
 //翻页调用  
   function PageCallbackDetail(pageNum, jq) { 
	   
	   var id = $('#hiddenId').val();
	   var m=1;
	   findDetail(id,pageNum,m);
   }
   
   queryRecomendRelation(pageNum);
   function queryRecomendRelation(pageNum){
	$("#tab_content_detail").hide();
	var param={};
	    param["username"] = $('#usernameFind').val();
	    param["mobilePhone"] = $('#cellphoneFind').val();
	    param["reallyName"] = $('#realNameFind').val();
	   
	    param["pageNum"] = pageNum+1;
		param["pageSize"] = pageSize;
	    $.ajax({
		    type: "post",
		    url: "queryRecomendRelationPage.do",
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
			    		strHtml +="<li align='center' style=' width:50px;'>"+(i+1)+"</li>";
	                    strHtml +="<li align='center'>"+o.username+"</li>";
	                    strHtml +="<li align='center'>"+o.mobilePhone+"</li>";
	                    strHtml +="<li align='center' >"+o.realName+"</li>";
	                    strHtml +="<li align='center'>"+o.recomendSum+"</li>";
	                    strHtml +="<li align='center'>"+o.recomendRzSum+"</li>";
	                    strHtml +="<li align='center'>"+o.recomendTzSum+"</li>";
	                    strHtml +="<li align='center'>"+o.recomendInvestAmount+".00</li>";
	                    strHtml +="<li class='f66' align='center' height='36px'>"+'<a href="###" onclick="findDetail('+"'"+o.id+"'"+',0,0);">查看详情</a>'+"</li>";
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
   
   //查看详情
   
   function findDetail(id,pageNum,m){
	   $('#hiddenId').val(id);
	   $("#tab_content_detail").show();
	    if(m==0){
	    	$('#usernameFindDetail').val('');
		    $('#cellphoneFindDetail').val('');
		    $('#realNameFindDetail').val('');
		    $('#srcFindDetail').val('');
		    $('#time1FindDetail').val('');
		    $('#time2FindDetail').val('');
	    }
	    
		var param={};
		    param["username"] = $('#usernameFindDetail').val();
		    param["mobilePhone"] = $('#cellphoneFindDetail').val();
		    param["reallyName"] = $('#realNameFindDetail').val();
		    param["src"] = $('#srcFindDetail').val();
		    param["time1"] = $('#time1FindDetail').val();
		    param["time2"] = $('#time2FindDetail').val();
		    
		    param["pageNum"] = pageNum+1;
			param["pageSize"] = pageSize;
		    $.ajax({
			    type: "post",
			    url: "queryRecomendRationDetail.do?id="+id,
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	 
			    	var tr_list_o = $("#tab_content_detail");
			    	tr_list_o.empty();
			    	
			    	totalNum = data.totalNum;
			    	$("#PaginationDetail").pagination(totalNum, {  
			    	    callback: PageCallbackDetail, 
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
				    		strHtml +="<li align='center' style=' width:50px;'>"+(i+1)+"</li>";
		                    strHtml +="<li align='center'>"+o.username+"</li>";
		                    strHtml +="<li align='center'>"+o.mobilePhone+"</li>";
		                    strHtml +="<li align='center' style=' width:200px;'>"+o.createTime+"</li>";
		                   
							if(o.realName==null){
		                    	strHtml +="<li align='center'>未认证</li>";
		                    }else{
		                        strHtml +="<li align='center' >"+o.realName+"</li>";
		                    }
		                    if(o.idNo==null){
		                    	
		                    	strHtml +="<li align='center' style='width:160px;'>未认证</li>";
		                    }else{
		                    	strHtml +="<li align='center' style='width:160px;'>"+o.idNo+"</li>";
		                    }
							if(o.src=='1'){
		                    	strHtml +="<li align='center'>邀请链接</li>";
		                    }else if(o.src=='2'){
		                    	strHtml +="<li align='center'>二维码扫码</li>";
		                    }else if(o.src=='3'){
		                    	strHtml +="<li align='center'>填写推荐人</li>";
		                    }else{
		                    	strHtml +="<li align='center'>未知</li>";
		                    }
							
							if(o.investor>0){
								strHtml +="<li align='center'>已投资</li>";
							}else{
								strHtml +="<li align='center'>未投资</li>";
							}
		                    //strHtml +="<li align='center'>"+o.investor+"</li>";
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
   
   function recomendRelatioDetail(){
	   m = 1;
	   var id = $('#hiddenId').val();
	   if(id==''){
		   alert('非法操作');
		   return;
	   }
	  
	   findDetail(id,pageNum,m);
   }
</script>
</body>
</html>
