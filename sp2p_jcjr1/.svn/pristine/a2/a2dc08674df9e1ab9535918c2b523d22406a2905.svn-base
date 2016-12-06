<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
	<div>
		<table id="gvNews" style="width: 100%; color: #333333;"	cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
			
				<tr >
					<td  colspan="2"><strong>合计项</strong></td>
					<td colspan="3" align="right" >债权转让金额合计:</td>
					<td  align="center" >￥<span id="debtPriceSum"></span></td>	
					<td colspan="3" align="center" >所有失败债权转让金额合计:
					</td>		
				</tr>
				
				<tr class=gvHeader>
					<th scope="col">
						标题
					</th>
					<th scope="col">
						转让人
					</th>
					<th scope="col">
						转让金额
					</th>
					<th scope="col">
						原始年利率
					</th>
					<th scope="col">
						转让利率
					</th>
					<th scope="col">
						债权价值
					</th>
					<th scope="col">
						债权价格
					</th>
					<th scope="col">
						剩余天数
					</th>
					<th scope="col">
						剩余期数
					</th>
					
					<th scope="col">
						到期日
					</th>
					<th scope="col">
						<input id="checkBoxAll" type="checkbox" onclick="checkAllDebt()"/>
					</th>
				</tr>
				
				<s:iterator value="#request.debtList" var="debtMap">
					<tr class="gvItem">
						<td align="center">
							<a href="../financeDetail.do?id=${debtMap.borrowId }" target="_blank">${debtMap.borrowTitle}</a>
						</td>
						<td align="center">
							${debtMap.investorName}
						</td>
						<td align="center">
							${debtMap.investAmount}
						</td>
						<td align="center">
							${debtMap.annualRate}
						</td>
						<td align="center">
							${debtMap.annualRateDebtBDDouble}
						</td>
						<td align="center">
							${debtMap.debtValue}
						</td>
						<td align="center">
							${debtMap.debtPrice}
						</td>
						<td align="center">
							${debtMap.remainingDays}
						</td>
						<td align="center">
							${debtMap.periodsBanlanceCount}
						</td>
						<td align="center">
							${debtMap.lastRepayment}
						</td>
						<td>
							<input id="${debtMap.investId}" type="checkbox" onclick="checkDebt()"/>
							<input id="${debtMap.investId}_investId" value="${debtMap.investId}" type="hidden">
							<input id="${debtMap.investId}_debtPrice" value="${debtMap.debtPrice}" type="hidden">
							<input id="${debtMap.investId}_debtValue" value="${debtMap.debtValue}" type="hidden">
							<input id="${debtMap.investId}_borrowId" value="${debtMap.borrowId}" type="hidden">
							<input id="${debtMap.investId}_investor" value="${debtMap.investor}" type="hidden">
							<input id="${debtMap.investId}_investorName" value="${debtMap.investorName}" type="hidden">
							<input id="${debtMap.investId}_annualRateDebtBDDouble" value="${debtMap.annualRateDebtBDDouble}" type="hidden">
							<input id="${debtMap.investId}_lastRepayment" value="${debtMap.lastRepayment}" type="hidden">
						</td>
					</tr>
				</s:iterator>
				
				
			</tbody>
		</table>
	</div>