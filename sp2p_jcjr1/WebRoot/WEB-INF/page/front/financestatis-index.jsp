<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
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
                <li class="on">理财统计</li>
            </ul>
         </div>
         
         <div class="myhome_content tab_content clearfix">
              <!--贷款统计-->
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
                    <tr>
                        <th colspan="3">回报统计</th>
                    </tr>
                    <tr>
                        <td width="33%">已赚利息</td>
 						<td width="34%">奖励收入总额</td>
                        <td width="33%">已赚逾期罚息</td>
                    </tr>
                    <tr>
                        <td align="center">￥<s:if test="#request.financeStatisMap.earnInterest !=''"> <fmt:formatNumber value="${financeStatisMap.earnInterest}" type="number" pattern="0.00" /></s:if><s:else>0</s:else></td>
    					<td align="center">￥<s:if test="#request.financeStatisMap.rewardIncome !=''">${financeStatisMap.rewardIncome}</s:if><s:else>0</s:else></td>
    					<td align="center">￥<s:if test="#request.financeStatisMap.hasFI !=''">${financeStatisMap.hasFI}</s:if><s:else>0</s:else></td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:25px;">
                    <tr>
                        <th colspan="6">个人理财统计</th>
                    </tr>
                    <tr>
                        <td>总借出金额</td>
                        <td>总借出笔数</td>
                        <td>已回收本息</td>
                        <td>已回收笔数</td>
                        <td>待回收本息</td>
                        <td>待回收笔数</td>
                    </tr>
                    <tr>
                    	<td align="center">￥<s:if test="#request.financeStatisMap.investAmount !=''">${financeStatisMap.investAmount}</s:if><s:else>0</s:else></td>
					    <td align="center"><s:if test="#request.financeStatisMap.investCount !=''">${financeStatisMap.investCount}</s:if><s:else>0</s:else></td>
					    <td align="center">￥<s:if test="#request.financeStatisMap.hasPI !=''"><fmt:formatNumber value="${financeStatisMap.hasPI}" type="number" pattern="0.00" /></s:if><s:else>0</s:else></td>
					    <td align="center"><s:if test="#request.financeStatisMap.hasCount !=''">${financeStatisMap.hasCount}</s:if><s:else>0</s:else></td>
					    <td align="center">￥<s:if test="#request.financeStatisMap.forPI !=''"><fmt:formatNumber value="${financeStatisMap.forPI}" type="number" pattern="0.00" /></s:if><s:else>0</s:else></td>
					    <td align="center"><s:if test="#request.financeStatisMap.forCount !=''">${financeStatisMap.forCount}</s:if><s:else>0</s:else></td>
                    </tr>
                </table>  
              
         </div>
         
    </div>
    
    


	
	
	
</div>



<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

  <script>
    $(function(){
         //$("#zh_hover").attr('class','nav_first');
	 	  //$("#zh_hover div").removeClass('none');
    	  //$('#li_12').addClass('on');
    	$(".lne_l3 > ul >li").removeClass("clicked");
		 $('#li_8').addClass('clicked');
        });
  
    </script>
</body>
</html>
