<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="../../common.jsp"%>
    <!-- 还款计划生成页面 -->
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script>
	function changePlan(){
		var fixDate,dueNo;
	    fixDate = $('#fixDate').val(); 
	    dueNo = $('#dueNo').val();
		window.location.href="plan_creatPlan.action?fixDate="+fixDate+"&dueNo="+dueNo;
	}
	
	function savePlan(){
		window.location.href="plan_savePlan.action";
		
		window.location.href="LnPayChkAction_findByPagePlan.action";
	}
	
	// 从只读状态转换到编辑状态
	function edite(obj){
		var readId ,editeId;
		readId = obj.id;//  只读td的id
		editeId = readId.replace('_r','_w');  // 可编辑td的id
		// 隐藏只读td
		$('#'+readId).hide();
		// 显示编辑td
		$('#'+editeId).show();
		// input 获得焦点
		$('#'+editeId+' input' ).select();
	}
	
	// 编辑后保存
	function  save(parId,obj,adjustTermNo,adjustType){
		var readId ,editeId,newValue,dueNo,cifNo,pactNo,strJson,planPram,k;
		editeId = parId;  // 可编辑td的id
		readId = editeId.replace('_w','_r');// 只读td的id
		newValue = obj.value;// 修改后的新值
		// 显示只读td
		$('#'+readId).show().text(newValue);
		// 隐藏编辑td
		$('#'+editeId).hide();
		// 
		dueNo = $('#dueNo').val();
		cifNo = $('#cifNo').val();
		pactNo = $('#pactNo').val();
		
		
		// [{"begDate":"2012-09-10","accFee":"100"},{"begDate":"2012-12-10","accFee":"200"}]
		/*********************开始构造json格式的还款计划列表*********************/
		strJson="[";// 还款计划json格式
		$.each($('#plan tr[id]'), function(i, val) {
	        var $row,index;
         	$row =  $(val); // tr对象(jquery)
         	index = i+1;
         	strJson = strJson+"{"
         	// 还款计划期号 
		 	termNo = $row.children('td[id="termNo"]').text().trim();
		 	strJson = strJson+"\"termNo\""+":"+"\""+termNo+"\""+",";
		 	// 还款本金 returnCapital
		 	returnCapital = $row.children('#returnCapital'+index+'_r').text().trim();
		 	strJson = strJson+"\"returnCapital\""+":"+"\""+returnCapital+"\""+",";
		 	// 还款利息
		 	returnInterest = $row.children('#returnInterest'+index).text().trim();
		 	strJson = strJson+"\"returnInterest\""+":"+"\""+returnInterest+"\""+",";
		 	// 计划开始日期
		 	begDate = $row.children('#begDate'+index).text().trim();
		 	strJson = strJson+"\"begDate\""+":"+"\""+begDate+"\""+",";
		 	// 计划结束日期
		 	endDate = $row.children('#endDate'+index+'_r').text().trim();
		 	strJson = strJson+"\"endDate\""+":"+"\""+endDate+"\""+",";
		 	// 账户管理费
		 	accFee = $row.children('#accFee'+index).text().trim();
		 	strJson = strJson+"\"accFee\""+":"+"\""+accFee+"\""+",";
		 	// 履约保证金额
		 	perfAmount = $row.children('#perfAmount'+index).text().trim();
		 	strJson = strJson+"\"perfAmount\""+":"+"\""+perfAmount+"\""+",";
		 	strJson = strJson+"\"pactNo\""+":"+"\""+pactNo+"\""+",";
		 	strJson = strJson+"\"dueNo\""+":"+"\""+dueNo+"\""+",";
		 	strJson = strJson+"\"cifNo\""+":"+"\""+cifNo+"\""+"},";
		});
		strJson = strJson+"]";
		strJson = strJson.replace(",]","]");
		/*********************结束构造json格式的还款计划列表*********************/
    	// 设置生成还款计划的参数
    	planPram="{";
    	// 设置是从第几期划款计划开始调整的
    	planPram = planPram+"\"termNo\""+":"+"\""+adjustTermNo+"\""+",";
    	// 调整方式 0 调整本金  1 调整还款计划结束日期
    	planPram = planPram+"\"adjustType\""+":"+"\""+adjustType+"\"";
    	planPram = planPram+"}";
    	$.ajax({
			url:"plan_editePlan.action",
			data:{"json":strJson,"planPram":planPram},
			type:"post",
			dataType:"json",
			success:function(data){
				k = 1;
				for(i=0;i<data.length;++i){
					$('#returnCapital'+k+'_r').text(data[i].returnCapital);
					$('#returnCapital'+k+'_w'+' input').val(data[i].returnCapital);
					$('#returnInterest'+k).text(data[i].returnInterest);
					$('#endDate$'+k+'_r').text(data[i].endDate);
					$('#endDate$'+k+'_w'+' input').val(data[i].endDate);
					$('#total'+k).text(data[i].total);
				    k = k+1;
				}
     		}
		})
	}
	
	</script>
  </head>
  
  <body>
  <!-- 借据号 -->
  <input type="hidden" id="dueNo"  name="dueNo"  value="${dueNo}"/>
  <!-- 合同号 -->
  <input type="hidden" id="pactNo"  name="pactNo"  value="${pactNo}"/>
  <!-- 客户号 -->
  <input type="hidden" id="cifNo"  name="cifNo"  value="${cifNo}"/>

  	<table id="plan" >
  		<tr>
  			<th>期数</th>
  			<th>应还本金</th>
  			<th>应还利息</th>
  			<th>还款总额</th>
  			<th>开始日期</th>
  			<th>结束日期</th>
  			<s:if test="#account_fee==1">
  			<th>账户管理费</th>
  			</s:if>
  			<th>履约保证金</th>
  		</tr>
  		
  	<c:forEach var="planBean" items="${planBeans}" varStatus="status">   
  		<tr id='tr_${status.index+1}'>
  			<td id="termNo">
  				${planBean.termNo}
  		 	</td>
  		 	<!-- 本金只读 -->
  		 	<c:if test="${isPlan==true}">
	  		 	<td  id="returnCapital${status.index+1}_r"  onclick="edite(this)">
	  		 		 ${planBean.returnCapital}
	  		 	</td>
  		 	</c:if>
  		 	
  		 	<c:if test="${isPlan==false}">
	  		 	<td  id="returnCapital${status.index+1}_r" >
	  		 		 ${planBean.returnCapital}
	  		 	</td>
  		 	</c:if>
  		 	
  		 	
  		 	
  		 	<!-- 本金编辑 -->
			<td  id="returnCapital${status.index+1}_w"  style="display:none">
  		 		 <input  value="${planBean.returnCapital}"  onBlur="save('returnCapital${status.index+1}_w',this,'${status.index+1}','0')"/>
  		 	</td>
  		 	
  		 	
  		 	<!-- 利息只读 -->
  		 	<td  id="returnInterest${status.index+1}">
  		 		 ${planBean.returnInterest}
  		 	</td>
  		 	
  		 	<td id="total${status.index+1}">
  		 		${planBean.total}
  		 	</td>
  		 	
  		 	<td id="begDate${status.index+1}">
  		 		 ${planBean.begDate}
  		 	</td>
  		 	
  		 	<!-- 结束日期只读 -->
  		 	<td id="endDate${status.index+1}_r">
  		 		 ${planBean.endDate}
  		 	</td>
  		 	
  		 	<!-- 结束日期编辑 -->
  		 	<td id="endDate${status.index+1}_w"  style="display:none">
  		 		  <input  value="${planBean.endDate}" onBlur="save('endDate${status.index+1}_w',this,'${status.index+1}','1')"/>
  		 	</td>
  		 	
  		 	<s:if test="#account_fee==1">
  		 		<td id="accFee${status.index+1}">
  		 		 	${planBean.accFee}
  		 		</td>
  		 	</s:if>
  		 	
  		 	<td id="perfAmount${status.index+1}">
  		 	${planBean.perfAmount}
  		 	
  		 	</td>
  		</tr>    
  	</c:forEach>
  	</table>

	<!-- 判断是否显示固定日还款选项 -->  	
  	<s:if test="#isFixDay==1 && #fixDayType==1">
  		<!-- 固定还款日期 -->
  		<s:select list="#request.fixdate"  label="固定还款日期"  value="#request.defaultkey" name="fixDate" ></s:select>
	</s:if>
	
	<s:if test="#isFixDay==1 && #fixDayType==0">
  		<!-- 固定还款日期 -->
  		固定还款日期:
  		<table>
  			<tr>
  				<td>
					<input type="text" class="Wdate"   id="fixDate" name="fixDate"  onchange="changePlan()" onclick="WdatePicker({alwaysUseStartDate:true,qsEnabled:false,isShowToday:false,isShowClear:false,readOnly:true,startDate:'2012-04-01',dateFmt:'d'})" />
  				</td> 
  			</tr>
  		</table>
	</s:if>
	
	<input type="button" value="保存" onclick="savePlan()" />
	
  </body>
</html>
