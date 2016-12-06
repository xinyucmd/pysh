<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  <!-- 合同补录表单 -->
      <%@ include file="../../common.jsp"%>
      <title><s:text name="pactAdditTitle"></s:text></title>
      <script>
      function savebl(){
      
      }
      </script>
  </head>
  <body>
	<table>
		<tr>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="cifNo"></s:text>
				</s:i18n>		
			</td>
			<td>
				<s:property value="#request.pactBean.cifNo"/>
			</td>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="cifName"></s:text>
				</s:i18n>	
			</td>
			<td>
				<s:property value="#request.cifName"/>
			</td>
		</tr>
		<tr>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="pactAmt"></s:text>
				</s:i18n>	
			</td>
			<td>
				<s:property value="#request.pactBean.pactAmt"/>
			</td>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="lnRate"></s:text>
				</s:i18n>	
			</td>
			<td>
				<s:property value="#request.pactBean.lnRate"/>
			</td>
		</tr>
		<tr>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="returnMethod"></s:text>
				</s:i18n>	
			</td>
			<td>
				<s:property value="#request.pactBean.returnMethod"/>
			</td>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="rateWay"></s:text>
				</s:i18n>	
			</td>
			<td>
				<s:property value="#request.pactBean.rateWay"/>
			</td>
		</tr>
		
		<tr>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="begDate"></s:text>
				</s:i18n>	
			</td>
			<td>
				<input class="Wdate" type="text" id="begDate" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
			</td>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="signDate"></s:text>
				</s:i18n>	
			</td>
			<td>
				<input class="Wdate" type="text" id="signDate" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
			</td>
		</tr>
		
		<tr>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="endDate"></s:text>
				</s:i18n>	
			</td>
			<td>
				<input class="Wdate" type="text" id="endDate" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
			</td>
			<td>
				<s:i18n name="com.dx.loan.putout.putout">
					<s:text name="tel"></s:text>
				</s:i18n>	
			</td>
			<td>
				<s:property value="#request.pactBean.telNo"/>	
			</td>
		</tr>
	</table>   
	<input type="button"  value="保存"  onclick="savebl()"/>
	 
  </body>
</html>
