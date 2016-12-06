<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${sitemap.siteName}</title>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    

</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap" style="padding-top:10px;">
<div class="lne_cent">
        <%@include file="/include/left.jsp" %>
		<div class="lne_centr">
		<div class="lne-centr-top">
            	<ul class="ul-one ul-line">
                	<li>已还总额：<em><s:if test="#request.accmountStatisMap.hasRePaySum !=''">${accmountStatisMap.hasRePaySum}</s:if><s:else>0</s:else></em>元</li>
                    <li>待还总额：<em><s:if test="#request.accmountStatisMap.forRePaySum !=''">${accmountStatisMap.forRePaySum}</s:if><s:else>0</s:else></em>元</li>
                </ul>
                <ul class="ul-one ul-line">
                	<li>已还本金：<em><s:if test="#request.accmountStatisMap.hasRePayPrincipal !=''">${accmountStatisMap.hasRePayPrincipal}</s:if><s:else>0</s:else></em>元</li>
                    <li>待还本金：<em><s:if test="#request.accmountStatisMap.forRePayPrincipal !=''">${accmountStatisMap.forRePayPrincipal}</s:if><s:else>0</s:else></em>元</li>
                </ul>
                 <ul class="ul-one">
                	<li>已还利息：<em><s:if test="#request.accmountStatisMap.hasRePayInterest !=''">${accmountStatisMap.hasRePayInterest}</s:if><s:else>0</s:else></em>元</li>
                    <li>待还利息：<em><s:if test="#request.accmountStatisMap.forRePayInterest !=''">${accmountStatisMap.forRePayInterest}</s:if><s:else>0</s:else></em>元</li>
                </ul>
            </div>
		   <div class="lne-centr-con" style="margin-top:10px;">
            	<div style="background:#fff;padding-bottom:5px; margin-bottom:10px;">
                     <div class="myhome_tit tab_meun">
                        <ul>
                            <li style="width:154px; *width:152px;" onclick="jumpUrl('queryMySuccessBorrowList.do');">成功借款</li>
                            <li class="on" style="width:154px; *width:152px;" onclick="jumpUrl('queryMyPayingBorrowList.do');">正在还款的借款</li>
                            <li style="width:154px; *width:152px;" onclick="jumpUrl('queryAllDetails.do');">还款明细账</li>
                            <li style="width:154px; *width:152px;" onclick="jumpUrl('queryBorrowInvestorInfo.do');">借款明细账</li>
                            <li style="border-right:none; width:154px; *width:152px;" onclick="jumpUrl('queryPayoffList.do');">已还完的借款</li>
                        </ul>
                    </div> 	  
        <div class="mg-height">
        <div id="srh_detail">
       <form action="queryMyPayingBorrowList.do" id="searchForm" method="post" class="form-inline">
          <div class="form-group" style="width:200px\9;">
         <label >发布时间：</label><input type="text" id="startTime" name="startTime" value="${paramMap.startTime }"  class="form-control Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        </div> 
        <div class="form-group" style="width:150px\9;">   
        <label>到 </label>    
        <input type="text" id="endTime" value="${paramMap.endTime }" name="endTime"  class="form-control Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        </div>
         <div class="form-group" style="width:178px\9;"> 
          <label>关键字：</label>
            <input type="text"  name="title" value="${paramMap.title }" id="title" class="form-control" style="height:28px;" />
         </div>
        <input type="button"  id="btn_search" value="搜 索"  class="btn btn-primary" style=" position:relative; right:0;" />
        <input type="button"  value="导出excel表" onclick="excel2()" class="btn btn-primary" style=" position:relative; right:0;"/>
          <input type="hidden" name="curPage" id="pageNum" />
         </form>         
          </div>
        </div>
        <div class="tab_content" id="biaoge_details">
             <div class="lne-centr-con-content">
            <!-- <table border="0" cellspacing="0" cellpadding="0"  class="table-con"> -->
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;"> 
  <tr>
    <th>标题</th>
    <th>借款协议</th>
    <th>借款类型</th>
    <th>借款金额</th>
    <th>年利率</th>
    <th>还款期限</th>
    <th>借款时间</th>
    <th>偿还本息</th>
    <th>已还本息</th>
    <th>未还本息</th>
    <th>操作</th>
    </tr>
  <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
  
  <input id="borrow_des" name="borrow_des" value="" type="hidden"/>
    <s:iterator value="pageBean.page"  var="bean" status="s"> 
       <tr>
	    <td align="center"><a href="financeDetail.do?id=${bean.borrowId}"  target="_blank" >${bean.borrowTitle }</a></td>
	    <td align="center"><%--<a href="javascript:showAgree('${bean.borrowId}');">查看协议</a>--%>
<%--	    <a href="protocol.do?typeId=15&&borrowId=${bean.borrowId}" target="_blank">查看协议</a>--%>
 		<form action="protocol.do" id="form<s:property value='#s.count'/>" method="post" target="_blank">
	      
	          <input type="hidden" name="typeId" value="${bean.encTypeId }"/>
	          <input type="hidden" name = "borrowId" value="${bean.encBorrowId }"/>
	          <a href="javascript:void(0);" onclick="$('#form<s:property value='#s.count'/>').submit();">查看协议</a>
	      </form>
	    </td>
	    <td align="center">${bean.borrowWay }</td>
	    <td align="center">${bean.borrowAmount }元</td>
	    <td align="center">${bean.annualRate }%</td>
	    <td align="center">${bean.deadline }
	    <s:if test="#bean.isDayThe == 1">
	      个月
	    </s:if>
	    <s:else>
	     天
	    </s:else>
	    </td>
	    <td align="center">${bean.publishTime}</td>
	    <td align="center">￥${bean.stillTotalSum }</td>
	    <td align="center">￥${bean.hasPI }</td>
	    <td align="center">￥${bean.hasSum }</td>
	    <td align="center"><a href="javascript:payingDetails(${bean.borrowId });" >还款明细</a></td>
	  </tr>
    </s:iterator>
  </s:else>
          </table>
          <!--  <div  class="page" > -->
           <div class="s_foot-page">
    <p>
	     <shove:page url="queryMyPayingBorrowList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="startTime">${paramMap.startTime}</s:param>
	    	<s:param name="endTime">${paramMap.endTime}</s:param>
	    	<s:param name="title">${paramMap.title}</s:param>
	     </shove:page>
	     </p>
	     </div>
         </div>
    </div>
</div>
  <div id="borrow_details"></div> 
    </div>
  </div></div></div>
  
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    $("#zh_hover").attr('class','nav_first');
	$('#li_7').addClass('on');
	$('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
     }); 
      $("#btn_search").click(function(){
		 	if($("#startTime").val() >$("#endTime").val()){
		      	alert("开始日期不能大于结束日期");
		      	return;
		  	}
			$("#pageNum").val(1);
		 	$("#searchForm").submit();
		 });
	 
	  	$("#jumpPage").click(function(){
		  	if($("#startTime").val() >$("#endTime").val()){
		      	alert("开始日期不能大于结束日期");
		      	return;
		  	}
		 	var curPage = $("#curPageText").val();
		 	if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			}
		 	$("#pageNum").val(curPage);
		 	$("#searchForm").submit();
		 });
});
	

	
	function showAgree(borrowId){
    var url = "protocol.do?typeId=15&&borrowId="+borrowId;
	     jQuery.jBox.open("post:"+url, "查看协议书", 650,400,{ buttons: { } });
	     
    }
    
    function jumpUrl(obj){
          window.location.href=obj;
    }
       
    function excel2(){
       	window.location.href=encodeURI(encodeURI("exportSuccessBorrow.do?status=4"+"&&startTime="+$("#startTime").val()+"&&endTime="+$("#endTime").val()+"&&title="+$("#title").val()));
    }
    
     function payingDetails(id){
        /*var url = "queryPayingDetails.do?borrowId="+id+"&status=1";
        window.location.href=url;*/
		 $("#borrow_des").attr("value",id);
		 $("#srh_detail").hide();
		 $("#biaoge_details").hide();
		 var param = {};
		 param["paramMap.borrowId"] = id;
		 param["pageBean.pageNum"]=1;
		 param["paramMap.status"] = "1";
        $.shovePost("queryPayingDetails.do",param,function(data){
          //alert(data);
	       $("#borrow_details").html(data);
	       //弹出框
	    });
     }
     function  initListInfo(param){
     	param["paramMap.borrowId"] = $('#borrowIdval').val();
     	param["paramMap.status"] = "1";
     	$.shovePost("queryPayingDetails.do",param,function(data){
	       $("#borrow_details").html(data);
	       //弹出框
	    });
     }
</script>

</body>
</html>
