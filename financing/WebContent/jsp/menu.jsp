
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.Map"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java"
	import="com.jiangchuanbanking.system.domain.User"%>
<%
	User user = (User) session.getAttribute("loginUser");
%>
<%
	request.setAttribute("user", user);
%>
<div
	style="background: #fafafa; padding: 3px; width: 99.4%; border: 1px solid #ccc">

	<s:if
		test="#session.loginUser.role == 'R06'||#session.loginUser.role == 'R02'||#session.loginUser.role == 'R03'||#session.loginUser.role == 'R05'">
		<a href="javascript:void(0)" id="mb1" class="easyui-menubutton"
			data-options="menu:'#mm1',iconCls:'icon-market'"> <s:text
				name='客户管理' />
		</a>
	</s:if>

	<s:if
		test="#session.loginUser.role != 'R01'&&#session.loginUser.role != 'R06'">
		<a href="javascript:void(0)" id="mb2" class="easyui-menubutton"
			data-options="menu:'#mm2',iconCls:'icon-activity'"> <s:text
				name='menu.moneyman.title' />
		</a>
	</s:if>

	<s:if test="#session.loginUser.role == 'R04'">
		<a href="javascript:void(0)" id="mb3" class="easyui-menubutton"
			data-options="menu:'#mm3',iconCls:'icon-copy'"> <s:text
				name='menu.moneyaccount.title' />
		</a>
	</s:if>

	<s:if
		test="#session.loginUser.role == 'R06'||#session.loginUser.role == 'R05'">
		<a href="javascript:void(0)" id="mb4" class="easyui-menubutton"
			data-options="menu:'#mm4',iconCls:'icon-bar'"> <s:text
				name='menu.statana.title' />
		</a>
	</s:if>


	<a href="javascript:void(0)" id="mb7" class="easyui-menubutton"
		data-options="menu:'#mm7',iconCls:'icon-system'"> <s:text
			name='menu.system.title' />
	</a>

	<!-- 
		<a href="javascript:void(0)" id="mb8" class="easyui-menubutton" data-options="menu:'#mm8',iconCls:'icon-help'">
			<s:text name='menu.help.title' />
		</a>
		 -->
</div>

<div id="mm1" style="width: 150px; display: none">

	<!-- 客户 
  <s:if test="#session.loginUser.view_account == 1">
   	 <div onClick="openPage('/investor/listInvestorPage.action')">
    	  <s:text name='menu.accounts.title' />
     </div>
  </s:if>   
-->
	<!-- 客户管理 -->
<s:if test="#session.loginUser.role == 'R02'||#session.loginUser.role == 'R06'">
	<div onClick="openPage('/investor/listCustomerPage.action')">
		<s:text name='menu.customer.title' />
	</div>
	</s:if> 
<!-- 银行账户 -->
  <s:if test="#session.loginUser.role == 'R02'">
    <div onClick="openPage('/financing/listBankAccountPage.action')">
      <s:text name='menu.bankAccount.title' />
    </div>
  </s:if> 

	<!-- 合同管理 -->
<s:if test="#session.loginUser.role == 'R02'||#session.loginUser.role == 'R06'">
	<div onClick="openPage('/subscription/listPactInfoPage.action')">
		<s:text name='menu.contract.title' />
	</div>
 </s:if>
	<!-- 合同生成 -->
	<s:if test="#session.loginUser.role == 'R03'">
		<div onClick="openPage('/contract/listContractGenPage.action')">
			<s:text name='合同生成' />
		</div>
	<!-- 合同打印-->
    <div onClick="openPage('/contract/listPrintPactPage.action')">
      <s:text name='contract.print' />
      </div>
     </s:if> 
     <!-- 生成回购申请 -->
	<s:if test="#session.loginUser.role == 'R03'">
		<div onClick="openPage('/contract/listRedeemGenPage.action')">
			<s:text name='生成回购申请' />
		</div>
	</s:if>
	<!-- 打印回购申请 -->
	<s:if test="#session.loginUser.role == 'R03'">
		<div onClick="openPage('/contract/listPrintRedeemPage.action')">
			<s:text name='打印回购申请' />
		</div>
	</s:if>
     <!-- 收据打印-->
     <s:if test="#session.loginUser.role == 'R05'">  
     	<div onClick="openPage('/contract/listReceiptPage.action')">
      		<s:text name='收据打印' />
        </div>
      </s:if> 
 </div>
  
  
  
</div>

<div id="mm2" style="width: 150px; display: none">


	<!-- 认购 -->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/subscription/listsubscripPage.action')">
			<s:text name='认购' />
		</div>
	</s:if>

	<!-- 续购 -->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/subscription/listContinuePage.action')">
			<s:text name='renewals' />
		</div>
	</s:if>
	<!-- 债券匹配 -->
	<s:if test="#session.loginUser.role == 'R03'">
		<div onClick="openPage('/subscription/listClaimsPage.action')">
			<s:text name='claims.match' />
		</div>
	</s:if>
	
	<!-- 合同生成
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/contract/listContractGenPage.action')">
			<s:text name='合同生成' />
		</div>
	</s:if> -->
	
	<!-- 到账确认 -->
	<s:if test="#session.loginUser.role == 'R04'">
		<div onClick="openPage('/subscription/listAccConfirmPage.action')">
			<s:text name='arrival.confirm' />
		</div>
	</s:if>
	<!-- 已到账确认列表 -->
	<s:if test="#session.loginUser.role == 'R04'">
		<div onClick="openPage('/subscription/listAccConfirmedPage.action')">
			<s:text name='arrival.confirmed' />
		</div>
	</s:if>
	<!-- 到账复核 -->
	<s:if test="#session.loginUser.role == 'R05'">
		<div onClick="openPage('/subscription/listAccCheckPage.action')">
			<s:text name='arrival.review' />
		</div>
	</s:if>
	<!-- 已到账复核列表 -->
	<s:if test="#session.loginUser.role == 'R05'">
		<div onClick="openPage('/subscription/listAccCheckedPage.action')">
			<s:text name='arrival.reviewed' />
		</div>
	</s:if>
	<!-- 赎回支付-->
	<s:if test="#session.loginUser.role == 'R04'">
		<div onClick="openPage('/redeem/listRedemPayPage.action')">
			<s:text name='redemption.payments' />
		</div>
	</s:if>
	<!-- 到期续购确认-->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/subscription/listRenewPage.action')">
			<s:text name='renew.confirm' />
		</div>
	</s:if>
	<!-- 提前赎回-->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/subscription/listAdvanceRedemPage.action')">
			<s:text name='early.redemption' />
		</div>
	</s:if>
	<!-- 撤销提前赎回-->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/redeem/listCancelAdvanceRedemPage.action')">
			<s:text name='cancel.early.redemption' />
		</div>
	</s:if>
	<!-- 赎回确认-->
	<s:if test="#session.loginUser.role == 'R05'">
		<div onClick="openPage('/redeem/listRedemConfirmPage.action')">
			<s:text name='redemption.confirm' />
		</div>
	</s:if>
	<!-- 撤销理财-->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/subscription/listUndoPage.action')">
			<s:text name='undo.wealth' />
		</div>
	</s:if>
	<!-- 撤销理财确认-->
	<s:if test="#session.loginUser.role == 'R04'">
		<div onClick="openPage('/redeem/listUndoApplyPage.action')">
			<s:text name='undo.wealth.confirm' />
		</div>
	</s:if>
	<!-- 取消撤销理财申请-->
	<s:if test="#session.loginUser.role == 'R02'">
		<div onClick="openPage('/redeem/listCancelUndoPage.action')">
			<s:text name='取消撤销理财' />
		</div>
	</s:if>
</div>
<!--  -->
<div id="mm3" style="width: 150px; display: none">
	<!-- 理财账户-->
	<s:if test="#session.loginUser.role == 'R04'">
		<div
			onClick="openPage('/mainAccount/listMainAccountPage.action?account_no=1')">
			<s:text name='wealth.account' />
		</div>
	</s:if>
	<!-- 
    <div onClick="openPage('/investor/listInvestorPage.action')">
      <s:text name='menu.accounts.title' />
    </div>
  
  <s:if test="#session.loginUser.view_contact == 1">
    <div onClick="openPage('/financing/listContactPage.action')">
      <s:text name='menu.contacts.title' />
    </div>
  </s:if>
  <s:if test="#session.loginUser.view_opportunity == 1">
    <div onClick="openPage('/financing/listOpportunityPage.action')">
      <s:text name='menu.opportunities.title' />
    </div>
  </s:if>
  <s:if test="#session.loginUser.view_lead == 1">
    <div onClick="openPage('/financing/listLeadPage.action')">
      <s:text name='menu.leads.title' />
    </div>
  </s:if>  
  
  <s:if test="#session.loginUser.view_lead == 1">
    <div onClick="openPage('/financing/listBankAccountPage.action')">
      <s:text name='menu.bankAccount.title' />
    </div>
  </s:if> 
   -->

</div>

<!-- 报表 -->
<div id="mm4" style="width: 150px; display: none">

	<div onClick="openPage('/statistics/monthPaySum.action')">
		<s:text name='menu.monthPaySum.title' />
	</div>
	<div onClick="openPage('/statistics/liabilitySum.action')">
		<s:text name='menu.liabilitySum.title' />
	</div>
	<div onClick="openPage('/statistics/percentCalcul.action')">
		<s:text name='menu.percentCalcul.title' />
	</div>
</div>
<!-- 系统管理 -->
<div id="mm7" style="width: 150px; display: none">
	<!-- 首页   -->
	<div onClick="openPage('/home/homePage.action')">
		<s:text name='menu.home.title' />
	</div>

	<!-- 横分线 -->
	<div class="menu-sep"></div>

	<s:if test="#session.loginUser.role == 'R01'">
		<div onClick="openPage('/prod/listProdAppPage.action')">
			<s:text name='product.approval' />
		</div>
	</s:if>
	<!--  
  <s:if test="#session.loginUser.view_system == 1">
    <div>
      <span><s:text name='menu.dropdown.title' /></span>
      <div style="width: 170px;">
        <div onClick="openPage('/system/listAccountLevelPage.action')">
          <s:text name='menu.accountLevel.title' />
        </div> 
        <div onClick="openPage('/system/listAccountNaturePage.action')">
          <s:text name='menu.accountNature.title' />
        </div>               
        <div onClick="openPage('/system/listAccountTypePage.action')">
          <s:text name='menu.accountType.title' />
        </div>
        <div onClick="openPage('/system/listAnnualRevenuePage.action')">
          <s:text name='menu.annualRevenue.title' />
        </div>
        <div onClick="openPage('/system/listCapitalPage.action')">
          <s:text name='menu.capital.title' />
        </div>  
        <div onClick="openPage('/system/listCompanySizePage.action')">
          <s:text name='menu.companySize.title' />
        </div>
        <div onClick="openPage('/system/listIndustryPage.action')">
          <s:text name='menu.industry.title' />
        </div>
        <div onClick="openPage('/system/listLeadSourcePage.action')">
          <s:text name='menu.leadSource.title' />
        </div>
        <div onClick="openPage('/system/listLeadStatusPage.action')">
          <s:text name='menu.leadStatus.title' />
        </div>
        <div onClick="openPage('/system/listUserStatusPage.action')">
          <s:text name='menu.userStatus.title' />
        </div>
        <div onClick="openPage('/system/listReligiousPage.action')">
          <s:text name='menu.religious.title' />
        </div>        
        <div onClick="openPage('/system/listSalesStagePage.action')">
          <s:text name='menu.salesStage.title' />
        </div>
        <div onClick="openPage('/system/listSalutationPage.action')">
          <s:text name='menu.salutation.title' />
        </div>
      </div>
    </div>
    
    <div class="menu-sep"></div>
     -->
	<!-- 产品配置 listProductConfigurationPage-->
	<s:if test="#session.loginUser.role == 'R01'">
		<div onClick="openPage('/prod/listProdPage.action')">
			<s:text name='menu.ProductConfiguration' />
		</div>
	</s:if>


<s:if test="#session.loginUser.role == 'R01'">
	<div onClick="openPage('/contract/listTemplatePackage.action')">
		<s:text name='合同模板' />
	</div>
</s:if>
	<!-- 数据字典 -->
	<s:if test="#session.loginUser.role == 'R01'">
		<div onClick="openPage('/system/listWealthParnDicPage.action')">
			<s:text name='dictionary' />
		</div>
	</s:if>

	<s:if test="#session.loginUser.role == 'R01'">
		<div onClick="openPage('/system/listUserPage.action')">
			<s:text name='menu.user.title' />
		</div>
	</s:if>
	<!--   
    <div onClick="openPage('/system/listRolePage.action')">
      <s:text name='menu.role.title' />
    </div>
      -->
	<div class="menu-sep"></div>
	<!--   
    <div onClick="openPage('/system/listChangeLogPage.action')">
      <s:text name='menu.changeLog.title' />
    </div>
  </s:if>
   -->
	<div onClick="openPage('/system/changePasswordPage.action')">
		<s:text name='menu.changePassword.title' />
	</div>
</div>

<div id="mm8" style="width: 100px; display: none">

	<div onClick="openPage('/system/aboutPage.action')">
		<s:text name='menu.help.title' />
	</div>

	<div onClick="openPage('/system/aboutPage.action')">
		<s:text name='menu.about.title' />
	</div>

</div>


