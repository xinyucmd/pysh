 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<link href="../css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
<div class="biaoge2" id="biaoge2_details">
<input type="hidden" value="${paramMap.borrowId}" id="borrowIdval"/>
    <table style="width: 100%; color: #333333; font-size: 13px;"
			cellspacing="1" cellpadding="3">
  <tr>
    <th colspan="3" align="left" style="padding-top:0px;">还款明细</th>
    </tr>
  <tr>
    <td align="left">标题：${borrowTitle }</td>
    <td align="left">借款金额：￥${borrowAmount } </td>
    <td align="left">&nbsp;</td>
  </tr>
  <tr>
    <td align="left">借款利率：${annualRate }% </td>
    <td align="left">借款期限：${deadline }
    <s:if test="#request.isDayThe == 1">
    	个月
    </s:if>
    <s:else>
   		 天
    </s:else>
     </td>
    <td align="left">&nbsp;</td>
  </tr>
  <tr>
    <td align="left">还款方式：
   <s:if test="#request.paymentMode == 1">
    按月等额本息还款
    </s:if>
 <s:elseif test="#request.paymentMode == 2">
 按先息后本还款
 </s:elseif>
 <s:elseif test="#request.paymentMode == 3">
 秒还
 </s:elseif>
  <s:elseif test="#request.paymentMode == 4">
 一次性还款
 </s:elseif>
   
    </td>
    <td align="left">发布时间：${publishTime }</td>
    <td align="left">借款时间： ${auditTime} </td>
  </tr>
    </table>

    </div>
    <div class="biaoge">
          <table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
  <tr class=gvHeader>
    <th style="width: 100px;" scope="col">序号</th>
    <th style="width: 100px;" scope="col">计划还款日期</th>
    <th style="width: 100px;" scope="col">计划还款本息</th>
    <th style="width: 100px;" scope="col">实还日期</th>
    <th style="width: 100px;" scope="col">逾期天数</th>
    <th style="width: 100px;" scope="col">实还本息</th>
    <th style="width: 100px;" scope="col">逾期罚息</th>
    <th style="width: 100px;" scope="col">总还款金额</th>
    <th style="width: 100px;" scope="col">状态</th>
    <th style="width: 100px;" scope="col">操作</th>
    
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
	    <s:iterator value="pageBean.page"  var="bean" status="sta"> 
		    <tr class="gvItem">
			    <td>${bean.id} </td>
			    <td>${bean.repayDate}</td>
			    <td>￥${bean.repayTotal}</td>
			    <td>${bean.realRepayDate}</td>
			    <td>${bean.lateDay }天</td>
			    <td>￥${bean.hasPI }</td>
			    <td>￥${bean.lateTotal }</td>
			    <td >￥${bean.total }</td>
			    <s:if test="#bean.repayStatus=='已偿还'">
			       <td>${bean.repayStatus }</td>
			       <td>-</td>
			    </s:if>
			    <s:else>
			       <td>${bean.repayStatus }</td>
			       <td><a href="javascript:myPay(${bean.id});">还款</a></td>
			        <input type="hidden" value="${bean.borrowId}" id="borrow_id"/>
			    </s:else>
		    </tr>
	    </s:iterator>
    </s:else>
          </table>
<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" 
theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>	
          </div>    
   <span id="my_pay"></span>       
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
 <script>	
    function myPay(id){//还款
       //$("#id").css('display','block');
       var url = "queryAdminPayData.do?payId="+id;
       jQuery.jBox.open("post:"+url, "还款", 350,350,{ buttons: { } });
       
       //return false;
       /*$.shovePost("queryMyPayData.do",{payId:id},function(data){
	       $("#my_pay").html(data);	       
	    });*/
    }
    function initListInfo(param){
    	param["paramMap.borrowId"] = $("#borrowIdval").val();
    	param["paramMap.status"] = "1";
    	        $.shovePost("queryAdminPayingDetails.do",param,function(data){
    	          //alert(data);
    	       $("#borrow_details").html(data);
    	       //弹出框
    	    });
    }
 </script>