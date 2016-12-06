<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" /> 
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/topss.jsp"></jsp:include>	
<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
     
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
     
    </div>
    <div class="r_main" style=" border:none; margin:0; width:761px">
      <div class="box box2" style="border-bottom:none;">
      <h2 class="grisnd">我的个人信息</h2>
      <div class="box-main">
      <div style="overflow:hidden; height:100%;">
        <div class="pic_info">
          <div class="pic">
            <shove:img defaulImg="images/default-img.jpg" src="${homeMap.personalHead}"  width="128" height="128"></shove:img>
            </div>
           <p>
            <!-- <a href="javascript:void(0);" id="btn_img">更换头像</a> -->
            <a href="javascript:void(0);"  onclick="pastpictur()">更换头像</a>
           </p>
        
        </div>
        <div class="xx_info">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="right">用户名： </th>
    <td><span id="u">${homeMap.username}</span>
    <s:if test="#request.homeMap.vipStatus ==2"></s:if>
    </td>
  </tr>
  <tr>
    <th align="right">注册时间： </th>
    <td>${homeMap.createTime} </td>
  </tr>
  <tr>
    <th align="right">会员积分：</th>
    <td><img src="images/ico_r_${homeMap.rating}.gif" onclick="qvip()" style="cursor: pointer;"/></td>
  </tr>
 
  <tr>
    <th align="right">信用额度：</th>
    <td>${homeMap.creditLimit}</td>
  </tr>
  <tr>
    <th align="right">个人统计：</th>
    <td>
    <s:if test="#request.homeMap.borrowNum !=''">${homeMap.borrowNum}</s:if><s:else>0</s:else>
          条借款记录，<s:if test="#request.homeMap.investNum !=''">${homeMap.investNum}</s:if><s:else>0</s:else>
          条投标记录</td>
  </tr>
</table>

        </div>
        <div class="hy_info">
        <s:if test="#request.homeMap.vipStatus ==3">
           <a href="renewalVIPInit.do" class="vipbtn" target="_self">会员续费</a>
        </s:if>
        <s:elseif test="#request.homeMap.vipStatus ==1">
           <a href="bevip.do" class="vipbtn"  target="_self">成为会员</a>
        </s:elseif>
        <s:else>
        <p style="padding-left: 0px">信用积分： <img src="images/ico_${homeMap.credit}.jpg"  title="${homeMap.crediting}分" width="33" height="22" onclick="qCridit()" style="cursor: pointer;"/></p>
          <p style="padding-left: 0px">会员到期：<span>${homeMap.vipCreateTime}</span></p>
           <p style="padding-left: 0px">最后登录IP：${homeMap.lastIP}</p>
        </s:else>
        </div>
      </div>
        <div class="tips" style="width:90%; margin:0 auto">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th><img src="images/022713_28.jpg" width="12" height="12" /> 温馨提示：</th>
    <td>未读站内信<a href="mailNoticeInit.do" target="_self">（<s:if test="#request.homeMap.unReadCount !=''">${homeMap.unReadCount}</s:if><s:else>0</s:else>）</a>封</td>
    <td>等待审核借款<a href="homeBorrowAuditList.do" target="_self">（<s:if test="#request.homeMap.waitBorrowCount !=''">${homeMap.waitBorrowCount}</s:if><s:else>0</s:else>）</a>个</td>
    <td>本月待还款<a href="queryMyPayingBorrowList.do" target="_self">（<s:if test="#request.homeMap.repayCount !=''">${homeMap.repayCount}</s:if><s:else>0</s:else>）</a>个</td>
    <td>本月待收款<a href="homeBorrowRecycleList.do" target="_self">（<s:if test="#request.homeMap.forpayCount !=''">${homeMap.forpayCount}</s:if><s:else>0</s:else>）</a>个</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>逾期待还款<a href="queryMyPayingBorrowList.do" target="_self">（<s:if test="#request.homeMap.lateRepayCount !=''">${homeMap.lateRepayCount}</s:if><s:else>0</s:else>）</a>个</td>
    <td>上传资料<a href="userPassData.do" target="_self">（<s:if test="#request.homeMap.materis !=''">${homeMap.materis}</s:if><s:else>0</s:else>）</a></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

        </div>
      </div>
      </div>
      <div class="box" style="border-bottom:none;">
        <div class="box-main" style="padding-left:70px;">
		<div class="mid" >
        <ul><li>
        <a href="rechargeInit.do" target="_self"><img src="images/022713_32.jpg" width="32" height="35" /></a><br/>
        <a href="rechargeInit.do" target="_self">我要充值</a>
        </li>
        <li><a href="finance.do" target="_self"><img src="images/022713_34.jpg" width="32" height="35" /></a><br/>
        <a href="finance.do" target="_self">我要贷出</a>
        </li>
        <li><a href="queryCanAssignmentDebt.do"><img src="images/022713_36.jpg" width="32" height="35" /></a><br/>
        <a href="queryCanAssignmentDebt.do">债权转让</a>
        </li>
        <li><a href="queryMyPayingBorrowList.do"><img src="images/022713_38.jpg" width="32" height="35" /></a><br/>
        <a href="queryMyPayingBorrowList.do">我要还款</a>
        </li>
         <li><a href="homeBorrowRecycleList.do" target="_self"><img src="images/022713_40.jpg" width="32" height="35" /></a><br/>
        <a href="homeBorrowRecycleList.do" target="_self">待收款</a>
        </li>
        <li><a href="queryFundrecordInit.do"><img src="images/022713_42.jpg" width="32" height="35" /></a><br/>
        <a href="queryFundrecordInit.do">资金流水</a>
        </li>
        <li><a href="borrow.do" target="_self"><img src="images/022713_44.jpg" width="32" height="35" /></a><br/>
        <a href="borrow.do" target="_self">我要借款</a>
        </li>
        </ul>
        </div>
      </div>
      </div>
      <div class="box">
      <h2 class="otherh2">账户详情：</h2>
      <div class="box-main">
		<table  border="0" cellpadding="0" cellspacing="0" class="zhtab">
  <tr>
    <th colspan="3" align="left">账户总汇：</th>
    </tr>
  <tr>
    <td>账户总额：￥<s:if test="#request.accmountStatisMap.accountSum !=''">${accmountStatisMap.accountSum}</s:if><s:else>0</s:else></td>
    <td>可用余额：￥<s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if><s:else>0</s:else> </td>
    <td>冻结金额：￥<s:if test="#request.accmountStatisMap.freezeAmount !=''">${accmountStatisMap.freezeAmount}</s:if><s:else>0</s:else></td>
  </tr>
  <tr>
    <td>总收益：￥<s:if test="#request.accmountStatisMap.earnSum !=''">${accmountStatisMap.earnSum}</s:if><s:else>0</s:else></td>
    <td>利息收益：￥<s:if test="#request.accmountStatisMap.rateEarnAmount !=''">${accmountStatisMap.rateEarnAmount}</s:if><s:else>0</s:else></td>
    <td>其他收益：￥<s:if test="#request.accmountStatisMap.otherEarnAmount !=''">${accmountStatisMap.otherEarnAmount}</s:if><s:else>0</s:else></td>
  </tr>
  <tr>
    <th colspan="3" align="left">投资总汇：</th>
    </tr>
  <tr>
    <td>已收总额：￥<s:if test="#request.accmountStatisMap.hasPaySum !=''">${accmountStatisMap.hasPaySum}</s:if><s:else>0</s:else></td>
    <td>已收本金：￥<s:if test="#request.accmountStatisMap.hasPayPrincipal !=''">${accmountStatisMap.hasPayPrincipal}</s:if><s:else>0</s:else></td>
    <td>已收利息：￥<s:if test="#request.accmountStatisMap.hasPayInterest !=''">${accmountStatisMap.hasPayInterest}</s:if><s:else>0</s:else></td>
  </tr>
  <tr>
    <td>待收总额：￥<s:if test="#request.accmountStatisMap.forPaySum !=''">${accmountStatisMap.forPaySum}</s:if><s:else>0</s:else></td>
    <td>待收本金：￥<s:if test="#request.accmountStatisMap.forPayPrincipal !=''">${accmountStatisMap.forPayPrincipal}</s:if><s:else>0</s:else></td>
    <td>待收利息：￥<s:if test="#request.accmountStatisMap.forPayInterest !=''">${accmountStatisMap.forPayInterest}</s:if><s:else>0</s:else></td>
  </tr>
  <tr>
    <th colspan="3" align="left">借款总汇：</th>
    </tr>
  <tr>
    <td>已还总额：￥<s:if test="#request.accmountStatisMap.hasRePaySum !=''">${accmountStatisMap.hasRePaySum}</s:if><s:else>0</s:else></td>
    <td>已还本金：￥<s:if test="#request.accmountStatisMap.hasRePayPrincipal !=''">${accmountStatisMap.hasRePayPrincipal}</s:if><s:else>0</s:else></td>
    <td>已还利息：￥<s:if test="#request.accmountStatisMap.hasRePayInterest !=''">${accmountStatisMap.hasRePayInterest}</s:if><s:else>0</s:else></td>
  </tr>
  <tr>
    <td>待还总额：￥<s:if test="#request.accmountStatisMap.forRePaySum !=''">${accmountStatisMap.forRePaySum}</s:if><s:else>0</s:else></td>
    <td>待还本金：￥<s:if test="#request.accmountStatisMap.forRePayPrincipal !=''">${accmountStatisMap.forRePayPrincipal}</s:if><s:else>0</s:else></td>
    <td>待还利息：￥<s:if test="#request.accmountStatisMap.forRePayInterest !=''">${accmountStatisMap.forRePayInterest}</s:if><s:else>0</s:else></td>
  </tr>
  <tr>
    <th colspan="3" align="left">信用额度总汇：</th>
    </tr>
  <tr>
    <td>信用借款总额度：￥<s:if test="#request.accmountStatisMap.creditLimit !=''">${accmountStatisMap.creditLimit}</s:if><s:else>0</s:else> </td>
    <td>信用可用额度：￥<s:if test="#request.accmountStatisMap.usableCreditLimit !=''">${accmountStatisMap.usableCreditLimit}</s:if><s:else>0</s:else></td>
    <td>&nbsp;</td>
  </tr>
   <tr>
    <th colspan="3" align="left">最近待收还款：</th>
    </tr>
  <tr>
    <td>最近待收还款日：<s:if test="#request.repayMap.minRepayDate == ''">无</s:if>${repayMap.minRepayDate}</td>
    <td>最近待收还款金额：<s:if test="#request.repayMap.totalSum != ''"><fmt:formatNumber value="${repayMap.totalSum}" type="number" pattern="￥0.00" /></s:if><s:else>0.00</s:else></td>
    <td>&nbsp;</td>
     
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

</script>
<script>
var result =0 ;
$(function(){
    //样式选中
    var param = {};
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
</script>
<script>
    function pastpictur(){
       jBox.open("post:pastpicture.do", "上传头像", 500,150,{ buttons: { "关闭":true} });
    }
    function ffff(){
      ClosePop();
    }
</script>
</body>
</html>
