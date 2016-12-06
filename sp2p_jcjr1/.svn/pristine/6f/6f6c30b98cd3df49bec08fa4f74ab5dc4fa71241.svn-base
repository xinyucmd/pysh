<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  	<head>
	    <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include>
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
                	<li><i class="p-arrow icon10"></i>待收总额：<em>
                	  <s:if test="#request.accmountStatisMap.forPaySum !=''">
						    <fmt:formatNumber value="${accmountStatisMap.forPaySum}" type="number" pattern="0.00" />
						</s:if><s:else>0.00</s:else>
                	</em>元</li>
                    <li><i class="p-arrow icon12"></i>已收金额：<em>
                    <s:if test="#request.accmountStatisMap.hasPaySum !=''">
						     <fmt:formatNumber value="${accmountStatisMap.hasPaySum}" type="number" pattern="0.00" />
						</s:if>
						<s:else>0.00</s:else>
                    
                    </em>元</li>
                </ul>
                <ul class="ul-one ul-line">
                	<li><i class="p-arrow icon3"></i>待收利息：<em>
                	<s:if test="#request.accmountStatisMap.forPayInterest !=''">
						    <fmt:formatNumber value="${accmountStatisMap.forPayInterest}" type="number" pattern="0.00" />
						</s:if><s:else>0.00</s:else>
                	</em>元</li>
                    <li><i class="p-arrow icon4"></i>已收利息：<em>
                    <s:if test="#request.accmountStatisMap.hasPayInterest !=''">
						     <fmt:formatNumber value="${accmountStatisMap.hasPayInterest}" type="number" pattern="0.00" />					
						</s:if><s:else>0.00</s:else>
                    </em>元</li>
                </ul>
                 <ul class="ul-one">
                	<li><i class="p-arrow icon9"></i>待收本金：<em>
                	<s:if test="#request.accmountStatisMap.forPayPrincipal !=''">
						    <fmt:formatNumber value="${accmountStatisMap.forPayPrincipal}" type="number" pattern="0.00" />
						</s:if><s:else>0.00</s:else>
                	</em>元</li>
                    <li><i class="p-arrow icon8"></i>已收本金：<em>
                    <s:if test="#request.accmountStatisMap.hasPayPrincipal !=''">
						     <fmt:formatNumber value="${accmountStatisMap.hasPayPrincipal}" type="number" pattern="0.00" />
						</s:if><s:else>0.00</s:else>
                    </em>元</li>
                </ul>
			</div>
		 
	 <div class="lne-centr-con" style="margin-top:10px;">
		   <div style="background:#fff;padding-bottom:5px; margin-bottom:10px;">
			 <!--标题-->
			  <div class="myhome_tit tab_meun">
				<ul>
					<li onclick="jumpUrl('homeBorrowInvestList.do?nav=homeBorrowInvestList');">投资列表</li>
					<li class="on" onclick="jumpUrl('homeBorrowTenderInList.do?nav=homeBorrowInvestList');">招标中的借款</li>
					<li onclick="jumpUrl('homeBorrowRecycleList.do?nav=homeBorrowInvestList');">回收中的借款</li>
					<li onclick="jumpUrl('homeBorrowRecycledList.do?nav=homeBorrowInvestList');">已结清的借款</li>
					 
				</ul>
			 </div>
			 <!--内容-->
			<div class="mg-height">
						<form  class="form-inline" action="homeBorrowTenderInList.do?nav=homeBorrowInvestList" id="searchForm" method="post">
							<input type="hidden" name="type" value="${paramMap.type}" />
	          				<input type="hidden" name="curPage" id="pageNum" />
							 <div class="form-group">
								 <label >发布时间：</label>
									<input type="text" id="publishTimeStart"  name="publishTimeStart" value="${paramMap.publishTimeStart }"  class="form-control form-xs Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
							 </div>	
							 <div class="form-group">
								 <label >到</label>
								 <input type="text" id="publishTimeEnd"  name="publishTimeEnd" value="${paramMap.publishTimeEnd }" class="form-control form-xs Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
							 </div>	
							 <div class="form-group">
								 <label >关键字：</label>		
								<input class="form-control form-xs " id="titles" name="title" value="${paramMap.title }" type="text" maxlength="200" class="inp90 test2" />
							</div>	
							<input type="button"  value="搜 索" id="search" class="btn btn-primary btn-lg"/>
						</form>
					</div>
					</div>
				  <div class="tab_content">
                    <div class="lne-centr-con-content">
					  <table border="0" cellspacing="0" cellpadding="0" class="table-con">
							<tr>
								<th>标题</th>
								<th>年利率</th>
								<th>投资金额</th>
								<th>期数</th>
								<th>发布时间</th>
								<th>进度</th>	
								<!-- whb 隐藏剩余时间 -->
								<!--<th>剩余时间</th> -->
							</tr>
							<s:if test="pageBean.page==null || pageBean.page.size==0">
							   <tr align="center">
								  <td colspan="7">暂无数据</td>
							   </tr>
							</s:if>
							<s:else>
								<s:iterator value="pageBean.page" var="bean" status="s">
									<s:if test="%{#bean.borrowWay != 6}">
									<tr>
										<td><a href="financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}</a></td>
										<td>
											${bean.annualRate}%
											<s:if test="%{#bean.add_interest > 0}">
												<font color="#ff0000"> <b>+${bean.add_interest}% </b></font>
											</s:if>
										</td>
										<td>${bean.investAmount}</td>
										<td>${bean.deadline}
				    						<s:if test="%{#bean.isDayThe ==1}">个月</s:if><s:else>天</s:else>
				    					</td>
				    					<td align="center">${bean.publishTime}</td>
				    					<td align="center">${bean.schedules}%</td>
				    					<!-- whb 隐藏剩余时间 -->
				    					<!-- <td align="center">${bean.times}</td>-->
										
									</tr>
									</s:if>
								</s:iterator>
							</s:else>						
						</table> 
						 <!--分页器 开始-->
						  <div class="s_foot-page">
							 <p>
								    <shove:page url="homeBorrowTenderInList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
									   	<s:param name="publishTimeStart">${paramMap.publishTimeStart}</s:param>
									   	<s:param name="publishTimeEnd">${paramMap.publishTimeEnd}</s:param>
									   	<s:param name="title">${paramMap.title}</s:param>
									   	<s:param name="type">${paramMap.type}</s:param>
									   	 <s:param name="nav">homeBorrowInvestList</s:param>
								    </shove:page>
							 </p> 
						</div>
						<!--分页器 结束--> 
				  </div>
			 </div>
		</div>
    </div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
$(function(){
    //样式选中
     $("#zh_hover").attr('class','nav_first');
	 $("#zh_hover div").removeClass('none');
     $("#lab_${type}").attr('class','on');
     //$('#li_10').addClass('on');
     $(".lne_l3 > ul >li").removeClass("clicked");
	 $('#li_4').addClass('clicked');
	 $('#search').click(function(){
	 	if($("#publishTimeStart").val() >$("#publishTimeEnd").val()){
	      	alert("开始日期不能大于结束日期");
	      	return;
		}
		$("#pageNum").val(1);
	 	$("#searchForm").submit();
     });
     
     $("#jumpPage").click(function(){
		if($("#publishTimeStart").val() >$("#publishTimeEnd").val()){
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


function jumpUrl(obj){
    window.location.href=obj;
}
function showAgree(){
     var url = "protocol.do?typeId=1";
     jQuery.jBox.open("post:"+url, "查看协议书", 800,500,{ buttons: { } });
}		     
</script>
</body>
</html>