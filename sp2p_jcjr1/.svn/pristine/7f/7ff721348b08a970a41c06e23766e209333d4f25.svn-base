<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script>
$(function(){
	$(".s_rili-tab,.clearfix > li").removeClass("today");
	if('${recycleMap.day}'==''){
		var a = parseInt('${nowday}');
	}else{
		var a = parseInt('${recycleMap.day}');
	}
	
	$(".s_rili-tab,.clearfix > li").eq(a).addClass("today");
});
</script>

<div class="s_rili-left">
				<h3 class="s_rili-title">
					<span class="next-month"></span>
					<span class="prev-month"></span> 
						<s:if test="#request.recycleMap==null || #request.recycleMap.size==0">${request.nowmonth}月</s:if>
					<s:else>${recycleMap.month}月</s:else>
				</h3>
				<ul class="s_rili-tab clearfix">
					<li>1</li>
					<li>2</li>
					<li>3</li>
					<li>4</li>
					<li>5</li>
					<li>6</li>
					<li>7</li>
					<li>8</li>
					<li>9</li>
					<li>10</li>
					<li>11</li>
					<li>12</li>
					<li>13</li>
					<li>14</li>
					<li>15</li>
					<li>16</li>
					<li>17</li>
					<li class="today">18</li>
					<li>19</li>
					<li>20</li>
					<li>21</li>
					<li>22</li>
					<li>23</li>
					<li>24</li>
					<li>25</li>
					<li>26</li>
					<li>27</li>
					<li>28</li>
						<s:if test="#request.recycleMap.maxDay==28 || #request.maxDay1==28">
							<li class="nextmonth">1</li>
							<li class="nextmonth">2</li>
							<li class="nextmonth">3</li>
							<li class="nextmonth">4</li>
							<li class="nextmonth">5</li>
							<li class="nextmonth">6</li>
							<li class="nextmonth">7</li>
						</s:if>
						<s:elseif test="#request.recycleMap.maxDay==31 || #request.maxDay1==31">
							<li>29</li>
							<li>30</li>
							<li>31</li>
							<li class="nextmonth">1</li>
							<li class="nextmonth">2</li>
							<li class="nextmonth">3</li>
							<li class="nextmonth">4</li>
						</s:elseif><s:else>
							<li>29</li>
							<li>30</li>
							<li class="nextmonth">1</li>
							<li class="nextmonth">2</li>
							<li class="nextmonth">3</li>
							<li class="nextmonth">4</li>
							<li class="nextmonth">5</li>
						</s:else>

				</ul>
			</div>
			<div class="s_rili-right">
				<h3 class="s_rili-title clearfix"><span>下次回款日期</span><a href="homeBorrowInvestList.do">查看所有投资记录</a></h3>
				<div class="s_rili-today-get clearfix">
					<span class="next-month"></span>
					<span class="prev-month"></span>
					<dl class="today-date">
						<dt><s:if test="#request.recycleMap==null || #request.recycleMap.size==0">${request.nowmonth}月</s:if>
					<s:else>${recycleMap.month}月</s:else></dt>
						<dd><s:if test="#request.recycleMap==null || #request.recycleMap.size==0">${request.nowday}月</s:if>
					<s:else>${recycleMap.day}</s:else></dd>
					</dl>
					<ul class="today-get">
						<li>回款日期 <span><s:if test="request.recycleMap==null || request.recycleMap.size==0">无</s:if><s:else>${recycleMap.repayDate}</s:else></span></li>
						<li>收益 <span>${recycleMap.earn},</span>  回收本金 <span>${recycleMap.forpayPrincipal}</span></li>
						<li>投资项目 <span class="pronums">1</span> 个</li>
					</ul>
				</div>
			</div>