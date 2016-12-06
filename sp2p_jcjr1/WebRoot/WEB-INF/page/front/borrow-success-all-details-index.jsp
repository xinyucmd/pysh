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
<div class="ne_wdzh"></div>
<div class="lne_cent">
           <!-- 引用我的帐号主页左边栏 -->
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
        <li  onclick="jumpUrl('queryMySuccessBorrowList.do');"  style="width:154px; *width:152px;">成功借款</li>
        <li  onclick="jumpUrl('queryMyPayingBorrowList.do');"  style="width:154px; *width:152px;">正在还款的借款</li>
        <li class="on" onclick="jumpUrl('queryAllDetails.do');"  style="width:154px; *width:152px;">还款明细账</li>
        <li onclick="jumpUrl('queryBorrowInvestorInfo.do');"  style="width:154px; *width:152px;">借款明细账</li>
        <li onclick="jumpUrl('queryPayoffList.do');"  style="width:154px; *width:152px;">已还完的借款</li>
        </ul>
        </div>
       <div class="mg-height">
        <form action="queryAllDetails.do" id="searchForm"  method="post" class="form-inline">
          <div class="form-group" style="width:200px\9;">
               <label >发布时间：</label>
               <input type="text" id="startTime" name="startTime" value="${paramMap.startTime }" class="form-control Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
                           </div>  
           <div class="form-group" style="width:150px;\9;">
               <label > 到 </label>     
        <input type="text" id="endTime" value="${paramMap.endTime }" name="endTime" class="form-control Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/> 
        </div>
         <div class="form-group" style="width:178px\9;">
          <label>关键字：</label>
            <input type="text"  name="title" value="${paramMap.title }" id="title"  class="form-control" style="height:28px;"/>
          </div>
                <input type="button"  id="btn_search" value="搜 索" class="btn btn-primary" style=" position:relative; right:0;" />
        <input type="button"  value="导出excel表" onclick="excels()" class="btn btn-primary" style=" position:relative; right:0;"/>
         <input type="hidden" name="curPage" id="pageNum" />
        </form>
        </div>
           <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
  <tr>
    <th>标题</th>
    <th>第几期</th>
    <th>应还款日期</th>
    <th>实际还款日期</th>
    <th>本期应还本息</th>
    <th>利息</th>
    <th>逾期罚款</th>
    <th>逾期天数</th>
    <th>还款状态</th>
    <th>操作</th>
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
    <s:iterator value="pageBean.page"  var="bean"> 
       <tr>
	    <td align="center"><a href="financeDetail.do?id=${bean.id}"  target="_blank" >${bean.borrowTitle }</a></td>
	    <td align="center">${bean.repayPeriod }</td>
	    <td align="center">${bean.repayDate}</td>
	    <td align="center">${bean.realRepayDate}</td>
	    <td align="center">￥${bean.forPI} </td>
	    <td align="center">￥${bean.stillInterest}</td>
	    <td align="center">￥${bean.lateFI}</td>
	    <td align="center">${bean.lateDay }</td>
	    <td align="center">
	        <s:if test="#bean.repayStatus==1">未偿还</s:if>
	        <s:else>已偿还</s:else>
	    </td>
	    <td align="center">
	    <s:if test="#bean.repayStatus==1"><a href="javascript:myPay(${bean.payId },'${bean.repayDate}');">还款</a></s:if>
	        <s:else>---</s:else>
	    </td>
	  </tr>
    </s:iterator>
    </s:else>
          </table>
          
         <div class="s_foot-page">
    <p>
	     <shove:page url="queryAllDetails.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
	    	<s:param name="startTime">${paramMap.startTime}</s:param>
	    	<s:param name="endTime">${paramMap.endTime}</s:param>
	    	<s:param name="title">${paramMap.title}</s:param>
	     </shove:page>
	     </p>
  </div>
	
          </div>
          
    <span id="my_pay_"></span>  
         
    </div>
</div>

    </div>
  </div>
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
	$("#zh_hover div").removeClass('none');
	$('#li_7').addClass('on');
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
	

	function showAgree(){
		var url = "protocol.do?typeId=1";
		 jQuery.jBox.open("post:"+url, "查看协议书", 600,400,{ buttons: {} });
			
    }
    
    function jumpUrl(obj){
    	window.location.href=obj;
    }
       
    function excels(){
    	window.location.href=encodeURI(encodeURI("exportrepayment.do?startTime="+$("#startTime").val()+"&&endTime="+$("#endTime").val()+"&&title="+$("#title").val()));
    }
    
    function myPay(id,repayDate){//还款
    	
    	var currDate = '${currDate}';
    	if(repayDate>currDate){
    		if(confirm("您确定要提前还款吗？")){
    			 var url = "queryMyPayData.do?payId="+id;
    		     jQuery.jBox.open("post:"+url, "还款", 450,450,{ buttons: { } });
    		}
    		return;
    	}
    	
       var url = "queryMyPayData.do?payId="+id;
       jQuery.jBox.open("post:"+url, "还款", 450,450,{ buttons: { } });
       
    }
</script>

</body>
</html>
