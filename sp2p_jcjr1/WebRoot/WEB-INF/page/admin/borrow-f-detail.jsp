<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款管理-初审</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="main_alll_h2">
									借款审核初审
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" align="center"  class="white12">
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div class="tab">
						<table cellspacing="1" cellpadding="3">
							<tr>
								<td class="blue12 left">
									用户名：${borrowMFADetail.username}
								</td>
								<td class="f66 leftp200">
									真实姓名：${borrowMFADetail.realName}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									标的标题：${borrowMFADetail.borrowTitle}
								</td>
							</tr>
							<tr>
							    <td class="blue12 left">
									借款金额：${borrowMFADetail.borrowAmount}&nbsp;元
								</td>
								<td class="f66 leftp200">
									年利率：${borrowMFADetail.annualRate}%
									
									<s:if test="%{borrowMFADetail.add_interest >0 and borrowMFADetail.add_interest !=''}">
										<font color="red">+${borrowMFADetail.add_interest}</font>
									</s:if>
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									借款期限：${borrowMFADetail.deadline}
									<s:if test="%{borrowMFADetail.isDayThe ==1}">个月</s:if><s:else>天</s:else>
								</td>
								<td class="f66 leftp200">
								           借款目的：${borrowMFADetail.purpose}
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									还款方式：
									<s:if test="%{borrowMFADetail.paymentMode ==1}">
									   按月分期还款
									</s:if>
									<s:elseif test="%{borrowMFADetail.paymentMode ==2}">
									 按月付息,到期还本
									</s:elseif>
									<s:elseif test="%{borrowMFADetail.paymentMode ==3}">
									 秒还
									</s:elseif>　
									<s:elseif test="%{borrowMFADetail.paymentMode ==4}">
									一次性还款
									</s:elseif>　
								</td>
								<td class="f66 leftp200">
								标的类型：
							<s:if test="%{borrowMFADetail.borrowWay==1}">
							净值借款
							</s:if>
							<s:elseif test="%{borrowMFADetail.borrowWay==2}">
							秒还借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==3}">
							信用借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==4}">
							实地考察借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==5}">
							机构担保借款
							</s:elseif>
							<s:elseif test="%{borrowMFADetail.borrowWay==6}">
							流转标借款
							</s:elseif>
								</td>
							</tr>
							<s:if test="#request.subscribes==1">
									<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMFADetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMFADetail.circulationNumber}份
								</td>
								</tr>
							</s:if>
							<s:elseif test="%{borrowMFADetail.borrowShow==2}">
								<tr>
									<td class="blue12 left">
									最小认购金额：${borrowMFADetail.smallestFlowUnit}元
								</td>
								<td class="f66 leftp200">
                                                                                                认购总份数：${borrowMFADetail.circulationNumber}份
								</td>
								</tr>
							</s:elseif>
							<s:else>
									<tr>
									<td class="blue12 left">
										最低投标金额：${borrowMFADetail.minTenderedSum}元
									</td>
									<td class="f66 leftp200">
	                                                                                                最高投标金额：
	                                    <s:if test="%{borrowMFADetail.maxTenderedSum==-1}">
								                           没有限制
								        </s:if>
								        <s:else>
								           ${borrowMFADetail.maxTenderedSum}元                
								        </s:else>                                                            
									</td>
								</tr>
							</s:else>
							<s:if test="%{borrowMFADetail.borrowWay==6}">
							  <tr>
								<td class="blue12 left">单次投标金额：${borrowMFADetail.maxTenderedSum}元</td>
								<td class="f66 leftp200">累计投标金额：${borrowMFADetail.maxMoneyValue}元</td>
							</tr>
							</s:if>
							<tr>
								<td class="blue12 left">
									投标奖励:  
									<s:if test="%{borrowMFADetail.excitationType ==2}">
									  按 固定金额,${borrowMFADetail.excitationSum}元
									</s:if>
									<s:elseif test="%{borrowMFADetail.excitationType ==3}">
									 按借款金额比例,${borrowMFADetail.excitationSum}%
									</s:elseif>
									<s:else>
									   无
									</s:else>
								</td>
								<td align="left" class="f66">
								</td>
							</tr>
							<tr>
								<td class="blue12 left">
									投标密码：
									<s:if test="%{borrowMFADetail.hasPWD ==1}">
									   有
									</s:if>
									<s:else>
									   无
									</s:else>
								</td>
								<td class="f66 leftp200">
								</td>
							</tr>
							<s:if test="%{borrowMFADetail.borrowShow==1}">
								<tr>
								<td colspan="2" class="blue12 left">
									借款详情：${borrowMFADetail.detail}
								</td>
								</tr>
							</s:if>
							<s:else>
									<tr>
								<td colspan="2" class="blue12 left">
									借款方商业概述：${borrowMFADetail.businessDetail}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资产情况：${borrowMFADetail.assets}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款方资金用途：${borrowMFADetail.moneyPurposes}
								</td>
							</tr>
							</s:else>
							<tr>
								<td colspan="2" class="blue12 left">
									添加时间：${borrowMFADetail.publishTime}/IP: ${borrowMFADetail.publishIP}
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									借款人认证审核查看：<a href="queryPerUserCreditAction.do?userId=${borrowMFADetail.new_userId}&userId_admin=${borrowMFADetail.new_userId_admin}&publisWay=${borrowMFADetail.publisherWay}" target="_blank" style="color: blue;"><strong>申请人认证详情查看</strong></a>
								</td>
							</tr>
							 
							<s:if test="%{borrowMFADetail.borrowWay==3}">
							 <s:if test="%{borrowMFADetail.bonding_letter_path!=''}">
							<tr>
								<td colspan="2" class="blue12 left">
									逐笔审批的担保函：<img src="../${borrowMFADetail.bonding_letter_path}" width="100" height="100"/>
								</td>
							</tr>
							  </s:if>
						    </s:if>
							 
							</table>
							<div id="content">
							<table>
							
							<!-- whb 添加是否为专享标 -->
							<tr>
								<td colspan="2" class="blue12 left">
									是否专享标：
									<s:radio name="paramMap.isExclus" list="#{0:'否',1:'是'}" value="%{borrowMFADetail.isExclus}" disabled="true"></s:radio>
								</td>
							</tr>
							<!-- guojingchao 抵押物是否可见 -->
							<s:if test="%{borrowMFADetail.borrowWay==4}">
							<tr>
								<td colspan="2" class="blue12 left">
									抵押物是否可见：
									<s:radio name="paramMap.isShowDyw" list="#{0:'否',1:'是'}" value="%{borrowMFADetail.isShowDyw}" disabled="true"></s:radio>
								</td>
							</tr>
							</s:if>
							<tr>
								<td colspan="2" class="blue12 left">
									审核状态：<span class="require-field">*</span>
									<s:radio name="paramMap.status" list="#{2:'审核通过',6:'审核不通过'}" value="%{borrowMFADetail.borrowStatus}"></s:radio>
								</td>
							</tr>
						
							<!-- whb 添加初审发标时间 -->
							<tr>
								<td colspan="2" class="blue12 left">
									发标时间：<span class="require-field">*</span>
									<input id="applyTime" name="paramMap.applyTime" class="Wdate" style="width: 180px;" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="blue12 left">
									站内信通知：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.msg" value="%{borrowMFADetail.mailContent}"  cols="30" rows="5"></s:textarea>
								</td>
								<td align="center" class="f66">
								</td>
							</tr>
							<tr>
							    <td colspan="2" class="blue12 left">
									风控意见：<span class="require-field">*&nbsp;</span>
									<br/><s:textarea cssStyle="margin-left:80px;" name="paramMap.auditOpinion" value="%{borrowMFADetail.auditOpinion}" cols="30" rows="10"></s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left" style="padding-left: 30px;">
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px" ></button>
								</td>
							</tr>
						</table>
							</div>
						<s:hidden name="id" value="%{borrowMFADetail.id}"></s:hidden>
						<s:hidden name="uid" value="%{borrowMFADetail.userId}"></s:hidden>
						<br />
					</div>
				</div>
			</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script>
var falg = false;
	$(function(){
	  //提交表单
   	  $('#btn_save').click(function(){
   		  //whb 添加初审发标时间
   		  if($('#applyTime').val() == ""){
   			  alert("请添加发标时间");
   			  return;
   		  }
   	   $('#btn_save').attr('style',"background-image: url('../images/admin/btn_chulz.jpg'); width: 70px; border: 0; height: 26px");
   	  $('#btn_save').attr('disabled',true);
	     param['paramMap.id'] = $('#id').val();
	     param['paramMap.reciver'] = $('#uid').val();
	     param['paramMap.status'] = $("input[name='paramMap.status']:checked").val();
	     param['paramMap.msg'] = $('#paramMap_msg').val();
	     param['paramMap.auditOpinion'] = $('#paramMap_auditOpinion').val();
	     param['paramMap.applyTime'] = $('#applyTime').val();
	     if(falg) return ;
	     $.shovePost('updateBorrowF.do',param,function(data){
		   var callBack = data.msg;
		   if(callBack == undefined || callBack == ''){
		       $('#content').html(data);
		       $("#btn_save").attr('disabled',false); 
		       falg = false;
		   }else{
		       if(callBack == 1){
		          alert("操作成功!");
		          window.location.href="borrowf.do";
		          falg = true;
		          return false;
		       }
		       alert(callBack);
		       $('#btn_save').attr('style',"background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px");
		       falg = false;
		   $("#btn_save").attr('disabled',false); 
		   }
		 });
	 });
	});
</script>
	</body>	
</html>