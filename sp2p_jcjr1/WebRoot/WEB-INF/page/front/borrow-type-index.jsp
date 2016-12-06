<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<jsp:include page="/include/common.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="s_jkmain clearfix">
     <div class="jk_main clearfix">
     	 
         <!--申请额度 开始-->
         <div class="jk_title">
         	 	<img src="images/pic_3.png" />
         </div>
         <!--申请额度 结束-->
         <!--发布表列表 开始-->
         <div class="jk_list clearfix">
         	  <ul>
         	  	<s:if test="#request.worth==1">
              	  <li class="clearfix">
                    
                    <div class="jk-cnet">
                        <h4 class="jk_t1">净值借款</h4>
                        <p>借款人可以发布的不超过其平台账号净值额度的借款金额，但借款人提现将受到限制。这是一种安系数相对较高的借款标，因此利率方面可能比较低。净值标通常用于临时周转，使资金利用率最大化。</p>
                        <a class="jk_btn jk_btn1" href="addBorrowInit.do?t=1">净值借款</a>
                    </div>
                 </li>
                 </s:if>
                 <s:if test="#request.seconds==1">
                 <li class="clearfix">
                    
                    <div class="jk-cnet">
                        <h4 class="jk_t2">秒还借款</h4>
                        <p>借款人标满瞬间送出利息，免手续费、自动审核、自动还款，不定期送出秒还标。</p>
                        <a class="jk_btn jk_btn2" href="addBorrowInit.do?t=2">秒还借款</a>
                    </div>
                </li>
                </s:if>
                <s:if test="#request.ordinary==1">
                <li class="clearfix">
                     
                    <div class="jk-cnet">
                        <h4 class="jk_t3">优选宝</h4>
                        <p>信用借款标是一种将个人的信用运用于借款之中，免抵押、免担保的小额个人信用贷款标，微信贷通过严格审核，对借款人给予信用评级，授予信用额度，允许其在平台发布贷款信息。</p>
                   	    <a class="jk_btn jk_btn3" href="addBorrowInit.do?t=3">信用借款</a>
                    </div>
                </li>
                </s:if>
                <s:if test="#request.field==1">
                <li class="clearfix">
                    
                    <div class="jk-cnet">
                        <h4 class="jk_t4">定息宝</h4>
                        <p>小微企业现场考察审批借款；汽车、房产、货物等借款；逾期24小时内赔付。</p>
                    	<a class="jk_btn jk_btn4" href="addBorrowInit.do?t=4">实地考察</a>
                    </div>
                </li>
                </s:if>
                <s:if test="#request.institutions==1">
                <li class="clearfix">
                     
                    <div class="jk-cnet">
                        <h4 class="jk_t5">担保借款</h4>
                        <p>是指微信贷的合作伙伴为相应的借款提供连带保证，并负有连带保证责任的借款。 （机构担保标需要通过机构担保认证）</p>
                    	<a class="jk_btn jk_btn5" href="addBorrowInit.do?t=5">担保借款</a>
                    </div>
                </li>
                </s:if>
                <s:if test="#request.flow==1">
                 <li class="clearfix">
                     
                    <div class="jk-cnet">
                        <h4 class="jk_t6">活利宝</h4>
                        <p>借款人可以发布的不超过其平台账号净值额度的借款金额，但借款人提现将受到限制。这是一种安全系数相对较高的借款标，因此利率方面可能比较低。净值标通常用于临时周转，使资金利用率最大化。</p>
                    	<a class="jk_btn jk_btn6" href="addBorrowInit.do?t=6">流转标</a>
                    </div>
                </li>
                </s:if>
              </ul>	
         </div>
         <!--发布表列表 结束-->
     </div>  
</div>
<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>
$(function(){
    //样式选中
dqzt(2);
});		     
</script>
</body>
</html>
