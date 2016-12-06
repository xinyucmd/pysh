<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${sitemap.siteName}</title>
   <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include>
	    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" /> 
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="ne_wdzh"></div>
<div class="lne_cent">

	<jsp:include page="/include/left.jsp"></jsp:include>
	
	<div class="lne_centr s_centr">
    	 <!--标题-->
      	 <div class="myhome_tit tab_meun">
         	<ul>
            	<li class="on" onclick="goUrl('queryBuyingDebt.do')">参与购买的债权</li>
                <li onclick="goUrl('querySucessBuyedDebt.do')">已成功购买的债权</li>
            </ul>
         </div>
         <!--内容-->
         <div class="myhome_content tab_content clearfix">
              <!--参与购买的债权-->
              <div>
              	  <div class="myhome_srh">
              	  
              	  	<form id="searchForm" action="queryBuyingDebt.do" method="post">
				          <input type="hidden" name="curPage" id="pageNum"/>
				          
				          <ul class="clearfix">
	                        <li>发布时间：
	                        	<input type="text" name="startTime" value="${paramMap.startTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"  class="inp90 test2" />
	                        	&nbsp;&nbsp;到&nbsp;&nbsp;
	                        	<input type="text"  name="endTime" value="${paramMap.endTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'});"  class="inp90 test2" />
	                        </li>
	                        <li>标题：<input type="text" name="borrowTitle" value="${paramMap.borrowTitle }" class="inp90 test2" /></li>
	                        <li><input type="button" class="myhome_btn" value="搜 索" id="btn_search"/></li>
	                     </ul>
			        </form>
			        
                  </div>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
                        <tr>
                            <th>标题</th>
                            <th>期限</th>
                            <th>年利率</th>
                            <th>债权期限</th>
                            <th>债权金额</th>
                            <th>最高价</th>
                            <th>我的竞价</th>
                            <th>剩余时间</th>
                            <th>状态</th>
                            <th>操作</th>	
                        </tr>
                        <s:if test="pageBean.page==null || pageBean.page.size() == 0">
					    <tr>
					      <td colspan="10" align="center">暂无记录</td>
					      </tr>
					    </s:if>
					    <s:else>
						    <s:iterator value="pageBean.page" var="bean">
                        <tr>
                            <td><a href="financeDetail.do?id=${borrowId}"  target="_blank">}${borrowTitle }</a></td>
                            <td>${deadline }个月</td>
                            <td>${annualRate }%</td>
						    <td>${debtLimit }个月</td>
						    <td>${debtSum }</td>
						    <td>${auctionHighPrice }</td>
						    <td>${auctionPrice }</td>
                            <td>
                            	<s:if test="#bean.debtStatus == 2">${remainDays }  </s:if>
	    						<s:else>--</s:else>
							</td>
                            <td>
                            	<s:if test="#bean.debtStatus == 2">
						    		竞拍中
						    	</s:if>
						    	<s:elseif test="#bean.debtStatus == 3">
						    		竞拍成功
						    	</s:elseif>
						    	<s:elseif test="#bean.debtStatus == 4">
						    		竞拍失败
						    	</s:elseif>
						    	<s:elseif test="#bean.debtStatus == 5">
						    		撤销
						    	</s:elseif>
						    	<s:elseif test="#bean.debtStatus == 6">
						    		审核失败
						    	</s:elseif>
						    	<s:elseif test="#bean.debtStatus == 7">
						    		提前还款
						    	</s:elseif>		
	    					</td>
                            <td>
                            	<s:if test="#bean.auctionCount<2">
	    							<s:if test="#bean.debtStatus == 2">
	    								<!-- <a href="auctingDebtInit.do?debtId=${debtId }">出价</a> -->
	    							</s:if>
	    						</s:if>
	    						<a href="queryDebtDetail.do?id=${debtId }" target="_blank"> 查看</a>
	    					</td>
                        </tr>
                        </s:iterator>
                        </s:else>
                    </table> 
                     <!--分页器 开始-->
                     <div class="wrap" style="margin:20px 0 10px 0">
                        <div class="inwrap">
                           <div class="page"> 
						    	<shove:page url="queryBuyingDebt.do" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
									<s:param name="startTime" >${paramMap.startTime }</s:param>
									<s:param name="endTime" >${paramMap.endTime }</s:param>
									<s:param name="borrowTitle" >${paramMap.borrowTitle }</s:param>
								</shove:page>
						    </div>           	
                        </div>
                    </div>
                    <!--分页器 结束-->
              </div>	
         </div>
    </div>
</div>









<div class="box" style="display:none;">
        
</div>
 
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

<script>
   $(function(){
     //$("#zh_hover").attr('class','nav_first');
	 //$("#zh_hover div").removeClass('none');
	 //$('#li_15').addClass('on');
	 $(".lne_l3 > ul >li").removeClass("clicked");
		 $('#li_6').addClass('clicked');
   	$("#btn_search").click(function(){
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
	
	
	function goUrl(url){
   	   window.location.href = url;
   }	
	
  
   
   
</script>
</body>
</html>
