<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  <div class="lne_centl">
    	<div class="lne_l1">
        	<ul>
            	<li class="lne_l1la"></li>
            	<li class="lne_l1lb">
                	<h2>微信贷</h2>
                    <p>
                    	<s:if test="#session.user.authStep>=2">
                    		<img src="images/zhengy.png" title="个人信息已认证"  />
                    	</s:if>
                    	<s:else>
                    		<img src="images/zheng.png" title="个人信息未认证" />
                    	</s:else>
                    	<s:if test="#session.user.email!=null && #session.user.email!=''">
                        	<img src="images/xiny.png" title="邮箱已认证"  />	
                    	</s:if>
                    	<s:else>
                        	<img src="images/xin.png" title="邮箱未认证"  />
                    	</s:else>
                    	<s:if test="#session.user.phoneStatus==3">
                        	<img src="images/phoney.png" title="手机号码已认证"  />
                    	</s:if>
                    	<s:else>
                    		<img src="images/phoney.png" title="手机号码已认证"  />
                       		<%--<img src="images/phone.png" title="手机号码未认证"  />
                    	--%></s:else>
                    </p>
                </li>
            </ul>
        </div>
        <div class="lne_l2">
        	<table width="240" border="0" cellspacing="0" cellpadding="0">
                <tr height="33" valign="bottom">
                    <td colspan="2"><span>可用余额</span></td>
                </tr>
                <tr height="5"></tr>
                <tr height="30">
                    <td><b>${sessionScope.user.usableSum }元</b></td>
                    <td align="right"><input onclick="javascript:window.location.href='rechargeInit.do'" type="button" value="充值" /></td>
                </tr>
            </table>
        </div>
        <div class="lne_l3">
        <ul>
        <li>
			<h2><a href="home.do" style="background:#fff; color:#ff6960; font-size:16px; font-weight:bold;">账户总览</a></h2>
          </li>
          <li>
        	<h2>资金管理</h2>
        	<ul class="level2">
            	<li id="li_1"><a href="rechargeInit.do">充值</a></li>
            	<li id="li_2"><a href="withdrawLoad.do">提现</a></li>
            	<li id="li_3"><a href="queryFundrecordInit.do">资金记录</a></li>
            </ul>
            </li>
            <li>
        	<h2>理财管理</h2>
        	<ul class="level2">
            	<li id="li_4"><a href="homeBorrowInvestList.do">我的投资</a></li>
            	<li id="li_5"><a href="queryCanAssignmentDebt.do">债权转让</a></li>
            	<li id="li_6"><a href="queryBuyingDebt.do">债权拍拍</a></li>
            	<!-- <li id="li_7"><a href="automaticBidInit.do">自动投标</a></li>  -->
            	
            	<li id="li_8"><a href="financeStatisInit.do">理财统计</a></li>
            </ul>
            </li>
            <li>
        	<h2>账户管理</h2>
        	<ul class="level2">
            	<li id="li_9"><a href="owerInformationInit1.do">基本信息</a></li>
            	<li id="li_10"><a href="yhbound.do">银行卡管理</a></li>
            	<li id="li_11"><a href="mailNoticeInit.do">系统消息</a></li>
            </ul>
            </li>
            <li>
        	<h2>借款管理</h2>
        	<ul class="level2">
            	<li id="li_12"><a href="owerInformationInit.do">认证信息</a></li>
            	<li id="li_13"><a href="queryMySuccessBorrowList.do">还款管理</a></li>
            	<li id="li_14"><a href="homeBorrowAuditList.do">申请中的借款</a></li>
            	<li id="li_15"><a href="loanStatisInit.do">贷款统计</a></li>
            	<li id="li_16"><a href="borrowType.do">我要借款</a></li>
            </ul>
            </li>
           </ul>
        </div>
    </div>
