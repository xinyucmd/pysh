 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<input type="hidden" value="${paramMap.borrowId}" id="borrowIdval"/>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
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

    <div class="biaoge">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
  <tr>
    <th>序号</th>
    <th>计划还款日期</th>
    <th>计划还款本息</th>
    <th>实还日期</th>
    <th>逾期天数</th>
    <th>实还本息</th>
    <th>逾期罚息</th>
    <th>总还款金额</th>
    <th>状态</th>
    <th>操作</th>
    
    </tr>
    
    <s:if test="pageBean.page==null || pageBean.page.size<=0">
    <tr><td align="center" colspan="11">暂无记录</td></tr>
  </s:if>
  
  <s:else>
	    <s:iterator value="pageBean.page"  var="bean" status="sta"> 
		    <tr>
			    <td align="center"> <s:property value='#sta.index+1'/>  </td>
			    <td align="center">${bean.repayDate}</td>
			    <td align="center">￥${bean.repayTotal}</td>
			    <td align="center">${bean.realRepayDate}</td>
			    <td align="center">${bean.lateDay }天</td>
			    <td align="center">￥${bean.hasPI }</td>
			    <td align="center">￥${bean.lateTotal }</td>
			    <td align="center">￥${bean.total }</td>
			    <s:if test="#bean.repayStatus=='已偿还'">
			       <td align="center">${bean.repayStatus }</td>
			       <td align="center">-</td>
			    </s:if>
			    <s:else>
			       <td align="center">${bean.repayStatus }</td>
			       <td align="center"><a href="javascript:myPay(${bean.id},'${bean.repayDate}');">还款</a></td>
			    </s:else>
		    </tr>
	    </s:iterator>
    </s:else>
          </table>
          <input type="hidden" value="${bean.borrowId}" id="borrow_id"/>
<shove:page curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" 
theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>	
          </div>    
   <span id="my_pay"></span>       
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
 <script>	
    function myPay(id,repayDate){//还款
      
    	var currDate = '${currDate}';
    	if(repayDate>currDate){
    		if(confirm("您确定要提前还款吗？")){
    			var borrowId = $("#borrow_id").val();
    	    	var url = "queryMyPayData.do?payId="+id+"&borrowId="+borrowId;
    	    	jQuery.jBox.open("post:"+url, "还款", 450,450,{ buttons: { } });
    		}
    		return;
    	}
    	
    	var borrowId = $("#borrow_id").val();
    	var url = "queryMyPayData.do?payId="+id+"&borrowId="+borrowId;
    	jQuery.jBox.open("post:"+url, "还款", 450,450,{ buttons: { } });
    	
    }
    
    function initListInfo(param){
    	param["paramMap.borrowId"] = $("#borrow_id").val();
    	param["paramMap.status"] = "1";
    	        $.shovePost("queryPayingDetails.do",param,function(data){
    	          //alert(data);
    	       $("#borrow_details").html(data);
    	       //弹出框
    	    });
    }
 </script>