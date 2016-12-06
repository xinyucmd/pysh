<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%
 String returnUrl = (String)request.getSession().getAttribute("returnUrl");
         if(returnUrl !=null){
             request.getSession().removeAttribute("returnUrl");
             response.sendRedirect(returnUrl);
         }
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" /> 
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
        <jsp:include page="/include/left.jsp" ></jsp:include>
            
        <div class="lne_centr">
        <!--top-->
        	<div class="lne-centr-top">
            	<ul class="ul-one ul-line">
                	<li><i class="p-arrow icon1"></i>可用余额：
                	    <em>
                	      <s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if><s:else>0</s:else>
                	    </em>元
                	</li>
                	
                    <li><i class="p-arrow icon2"></i>冻结金额：
                      <em>
                        <s:if test="#request.accmountStatisMap.freezeAmount !=''">${accmountStatisMap.freezeAmount}</s:if><s:else>0</s:else>
                      </em>元
                   </li>
                </ul>
                <ul class="ul-one">
                	<li>
                	   <i class="p-arrow icon3">
                	     </i>已收收益：
                	     <em>
                	     <s:if test="#request.accmountStatisMap.hasPayInterest !=''">
                	        <fmt:formatNumber value="${accmountStatisMap.hasPayInterest}" type="number" pattern="0.00" />
                	     </s:if>
                	     </em>元
                	</li>
                	
                    <li>
                      <i class="p-arrow icon4"></i>待收收益：
                       <em>
                         <s:if test="#request.accmountStatisMap.forPayInterest !=''">
                	        <fmt:formatNumber value="${accmountStatisMap.forPayInterest}" type="number" pattern="0.00" />
                	     </s:if>
                       </em>元
                    </li>
                </ul>
                <ul class="ul-two">
                	<li><a href="rechargeInit.do" class="blue-btn">充值</a></li>
                    <li><a href="withdrawLoad.do" class="blue-btn">提现</a></li>
                </ul>
            </div>
           <!--还款情况--> 
            <div class="lne-centr-con">
            	<p class="lne-centr-con-title">
                	<span class="fl"><i class="p-arrow1 icon5"></i>未来30天的还款情况</span>
                    <span class="fr"><a href="homeBorrowRecycleList.do?nav=homeBorrowInvestList">查看更多项目</a></span>
                </p>
                <div class="lne-centr-con-content">
                	<table border="0" cellspacing="0" cellpadding="0" class="table-con">
                    	<tr>
                    		<th class="p-w10">计息日期</th>
                            <th class="p-w7">期数</th>
                            <th class="p-w12">本期还款金额</th>
                            <th class="p-w12">本期还款时间</th>
                            <th class="p-w18">项目名称</th>
                        </tr>
                        <s:if test="pageBean.page!=null || pageBean.page.size>0">
                        <s:iterator value="pageBean.page" var="bean">
                        <tr>
                        	<td>${bean.auditTime}</td>
                            <td>${bean.repayPeriod}</td>
                            <td>${bean.repayAmount}</td>
                            <td>${bean.repayDate}</td>
                            <td>${bean.borrowTitle}</td>
                        </tr>
                        </s:iterator>
                        </s:if>
                         
                    </table>
         <!--分页  --> 
         <s:if test="pageBean.page!=null || pageBean.page.size>0">
		    <div class="s_foot-page">
	            <p>
	               <shove:page url="home.do" curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
				   </shove:page>
	            </p>
		   </div>    
		</s:if>
                </div>
                
                <p class="lne-centr-con-title mt18">
                	<span class="fl"><i class="p-arrow1 icon6"></i>个人理财统计</span>
                </p>
                <div class="lne-centr-con-content1">
                	<table border="0" cellspacing="0" cellpadding="0" class="table-con">
                    	<tr>
                    		<th>总借出金额</th>
                            <th>总借出笔数</th>
                            <th>已回收本息</th>
                            <th>已回收笔数</th>
                            <th>待回收本息</th>
                            <th>待回收笔数</th>
                        </tr>
                        <tr>
	                    	<td align="center">￥<s:if test="#request.financeStatisMap.investAmount !=''">${financeStatisMap.investAmount}</s:if><s:else>0</s:else></td>
						    <td align="center"><s:if test="#request.financeStatisMap.investCount !=''">${financeStatisMap.investCount}</s:if><s:else>0</s:else></td>
						    <td align="center">￥<s:if test="#request.financeStatisMap.hasPI !=''"><fmt:formatNumber value="${financeStatisMap.hasPI}" type="number" pattern="0.00" /></s:if><s:else>0</s:else></td>
						    <td align="center"><s:if test="#request.financeStatisMap.hasCount !=''">${financeStatisMap.hasCount}</s:if><s:else>0</s:else></td>
						    <td align="center">￥<s:if test="#request.financeStatisMap.forPI !=''"><fmt:formatNumber value="${financeStatisMap.forPI}" type="number" pattern="0.00" /></s:if><s:else>0</s:else></td>
						    <td align="center"><s:if test="#request.financeStatisMap.forCount !=''">${financeStatisMap.forCount}</s:if><s:else>0</s:else></td>
                    </tr>
                    </table>
                </div>
                <p class="lne-centr-con-title mt18">
                	<span class="fl"><i class="p-arrow1 icon7"></i>回报统计</span>
                </p>
                <div class="lne-centr-con-content1">
                	<table border="0" cellspacing="0" cellpadding="0" class="table-con">
                    	<tr>
                    		<th>已赚利息</th>
                            <th>奖励收入总额</th>
                            <th>已赚逾期罚息</th>
                        </tr>
                        <tr>
                        	 <td align="center">￥<s:if test="#request.financeStatisMap.earnInterest !=''"> <fmt:formatNumber value="${financeStatisMap.earnInterest}" type="number" pattern="0.00" /></s:if><s:else>0</s:else></td>
    					     <td align="center">￥<s:if test="#request.financeStatisMap.rewardIncome !=''">${financeStatisMap.rewardIncome}</s:if><s:else>0</s:else></td>
    					     <td align="center">￥<s:if test="#request.financeStatisMap.hasFI !=''">${financeStatisMap.hasFI}</s:if><s:else>0</s:else></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script >
//查询vip积分
function qvip(){
window.location.href='userrrjc.do?flag=2';
}
//查询信用积分
function qCridit(){
window.location.href='userrrjc.do?flag=1';
}
var result =0 ;
$(function(){
	var param = {};
	$.shovePost('recycleInfo.do',param,function(data){
		$('#recycleInfo').html(data);
	});
    //样式选中
	 $('#li_1').addClass('on');
     dqzt(4);
	 $("#btn_img").click(function(){
	 	param['paramMap.userName']=$("#u").val();
	 	$.post("queryHeadImg.do",param,function(data){
	 		if (data == '1'){
	 			 alert("请在个人设置中填写完基本信息");
				 return false;
	 		}
	 		if(data == '2'){
	 			var dir = getDirNum();
				var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':1.0,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
				json = encodeURIComponent(json);
				window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
				var headImgPath = $("#img").attr("src");
				if(headImgPath ==""){
					alert("上传失败！");	
				}	
				  window.location.reload();
		 		}
	 	});
	 		
 			
	  });
});		     
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "upload/"+basepath+"/"+fileName;
	//	$("#img").attr("src",path);
	//	param["paramMap.imgPath"]=path;
		$.get("updatePersonImg.do",{"imgPath":path},initCallBack);
	}
}
function initCallBack(data){
	alert(data.msg);
}
function getDirNum(){
	var date = new Date();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m<10){
		m = "0"+m;
    }
	if(d<10){
	   d = "0"+d;
	}
	var dirName = date.getFullYear()+""+m+""+d;
	return dirName; 
}
    function pastpictur(){
       jBox.open("post:pastpicture.do", "上传头像", 500,150,{ buttons: { "关闭":true} });
    }
    function ffff(){
      ClosePop();
    }
$("#jumpPage").click(function(){
    var curPage = $("#curPageText").val();
   
	 if(isNaN(curPage)){
		alert("输入格式不正确!");
		return;
	 }
	 $("#pageNum").val(curPage);
   
   window.location.href = "home.do?curPage="+curPage;
});

function jumpUrl(obj){
    window.location.href=obj;
}
</script>
</body>
</html>
