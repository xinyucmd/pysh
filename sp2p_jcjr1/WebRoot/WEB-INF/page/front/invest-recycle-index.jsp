<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  	<head>
	    <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include>
	    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
        	<script>
		$(document).ready(function(){
$("#d_hover").hover(
function () {
$("#k_box").slideDown(200);
},
function () {
$("#k_box").hide();
}
);
});
	</script> 
	</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div id="k_box" style=" padding:5px; border:solid 1px #719dc6; background:#94b9dc; color:#000; font-size:12px; border-radius:5px; display:none; position:absolute; top:700px; right:300px; z-index:1;">已收期数/购买标的时的剩余未还期数<br/></div>
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
					<li onclick="jumpUrl('homeBorrowTenderInList.do?nav=homeBorrowInvestList');">招标中的借款</li>
					<li class="on"  onclick="jumpUrl('homeBorrowRecycleList.do?nav=homeBorrowInvestList');">回收中的借款</li>
					<li style="border-right:none;" onclick="jumpUrl('homeBorrowRecycledList.do?nav=homeBorrowInvestList');">已结清的借款</li>
				</ul>
			 </div>
			 <!--内容-->
	              <div class="mg-height">
						<form class="form-inline" action="homeBorrowRecycleList.do?nav=homeBorrowInvestList" id="searchForm" method="post">
	          				<input type="hidden" name="curPage" id="pageNum" />
							<div class="form-group">
								 <label >关键字：</label>
								 <input id="titles" name="title" value="${paramMap.title }" type="text" maxlength="200" class="form-control form-xs " />			
							</div>
                             <input type="button" class="btn btn-primary btn-lg" value="搜 索" id="search"/>
						</form>
					</div>
					<div class="tab_content">
                    <div class="lne-centr-con-content">
					  <table  border="0" cellspacing="0" cellpadding="0" class="table-con">
							<tr>
								<th>标题</th>
								<th>年利率</th>
								<th>投资金额</th>
								<!-- <th>投资时间</th> -->
								<th>计息日期</th>
								<th id="d_hover">期数<i style="width:0;height:0;border-width:8px;border-style:solid dashed dashed dashed;border-color:#e66161 transparent transparent transparent; position:relative; top:13px; left:5px;"></i></th>
								<th>已收金额</th>	 
								<th colspan="2">查看</th>
							</tr>
							<s:if test="pageBean.page==null || pageBean.page.size==0">
							   <tr align="center">
								  <td colspan="7">暂无数据</td>
							   </tr>
							</s:if>
							<s:else>
								<s:iterator value="pageBean.page" var="bean" status="s">
							
									<tr>
										<td><a href="financeDetail.do?id=${bean.borrowId}" target="_blank">${bean.borrowTitle}</a></td>
										<td>
											${bean.annualRate}%
											
											<s:if test="%{#bean.add_interest > 0}">
												<font color="#ff0000"> <b>+${bean.add_interest}% </b></font>
											</s:if>
										</td>
										<td>${bean.realAmount}</td>
										<!-- 
										<td><fmt:formatDate value="${bean.investTime}" type="date"/></td>
										 -->
										<td>${bean.auditTime}</td>
										
										<td>
											<s:if test="%{#bean.isDebt ==2 && #bean.debtline > -1}">${bean.hasDebtline}/${bean.debtline}</s:if>
											<s:else>${bean.hasDeadline}/${bean.deadline}</s:else>
				    					</td>
				    					<td align="center">${bean.forTotalSum}</td>
			    						<td align="center">
			    							<a href="javascript:viewDetail('${bean.minId}','${bean.id }','${bean.borrowId}');">还款详情</a>
			    						</td>
			    						<td align="center">
											<form action="protocol.do" id="form<s:property value='#s.count'/>" method="post" target="_blank">
										         <input type="hidden" name="typeId" value="${bean.encTypeId }"/>
										         <input type="hidden" name = "borrowId" value="${bean.encBorrowId }"/>
										         <input type="hidden" name="investId" value="${bean.encInvestId }"/>
										         <s:if test="%{#bean.isDebt ==2}">
										         	<input type="hidden" name = "aid" value="${bean.encDebtId }"/>
										         	<a href="javascript:void(0);" onclick="$('#form<s:property value='#s.count'/>').submit();">转让协议</a>
										         </s:if>
										         <s:else>
										         	<input type="hidden" name="tempState" value="1"/>
										         	<input type="hidden" name="styles" value="${bean.encTypeId }"/>
										         	<a href="javascript:void(0);" onclick="$('#form<s:property value='#s.count'/>').submit();">查看协议</a>
										         </s:else>
								    		 </form>
			    						</td>
									</tr>
								</s:iterator>
							</s:else>						
						</table> 
						 <!--分页器 开始-->
						<%--  <div class="wrap" style="margin:20px 0 10px 0">
							<div class="inwrap">
								<div class="page" >
								     <shove:page url="homeBorrowRecycleList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">	    
								    	<s:param name="title">${paramMap.title}</s:param>
								    	<s:param name="type">${paramMap.type}</s:param>
								     </shove:page>
							  	</div>          	
							</div>
						</div> --%>
						<!--分页器 结束--> 
						<div class="s_foot-page">
						 <p>
						        <shove:page url="homeBorrowRecycleList.do" curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">	    
								    	<s:param name="title">${paramMap.title}</s:param>
								    	<s:param name="type">${paramMap.type}</s:param>
								        <s:param name="nav">homeBorrowInvestList</s:param>
								</shove:page>
								     
					      </p>
					   </div>
				  </div>
			 </div>
		</div>
    </div>
</div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
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
		$("#pageNum").val(1);
		$("#searchForm").submit();
     });
     
     $("#jumpPage").click(function(){
		  
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
function viewDetail(minId,investId,borrowId){
    var url = "homeBorrowForpayDetail.do?minId="+minId+"&investId="+investId+"&borrowId="+borrowId;
    jQuery.jBox.open("post:"+url, "还款详情", 1000,500,{ buttons: { } });
}			     
</script>
</body>
</html>