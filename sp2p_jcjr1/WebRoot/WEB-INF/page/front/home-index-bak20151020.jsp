<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
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
<div class="ne_wdzh"></div>
<div class="lne_cent">
	<jsp:include page="/include/left.jsp" ></jsp:include>
	<div class="lne_centr">
    	<div class="lne_r1">
        	<ul>
            	<li>
                	<h5>账户总资产</h5>
                	<h4>
                	  <s:if test="#request.accmountStatisMap.accountSum !=''">
                	      <fmt:formatNumber value="${accmountStatisMap.accountSum}" type="number" pattern="0.00" />
                	  </s:if>
                	 <s:else>0.00</s:else>元
                	</h4>
                </li>
            	<li>
                	<h5>账户余额</h5>
                	<h4><s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if><s:else>0</s:else>元</h4>
                </li>
            	<li>
                	<h5>累计收益</h5>
                	<h4><s:if test="#request.accmountStatisMap.earnSum !=''">
                	       <fmt:formatNumber value="${accmountStatisMap.earnSum}" type="number" pattern="0.00" />
                	   </s:if>
                	   <s:else>0</s:else>元
                	</h4>
                </li>
            	<li style="border:none;">
                	<h5>下月待收本息</h5>
                	<h4>
                	    <s:if test="#request.repayMap.totalSum != ''">
                	    <fmt:formatNumber value="${repayMap.totalSum}" type="number" pattern="0.00" />
                	    </s:if>
                	    <s:else>0.00</s:else>元
                	</h4>
                </li>
            </ul>
        </div>
        <div class="lne_r2">
        	<div class="lne_r2a">
            	<p><span>待收本息</span>
            	<b>
            	<s:if test="#request.accmountStatisMap.forPaySum !=''">
            	     <fmt:formatNumber value="${accmountStatisMap.forPaySum}" type="number" pattern="0.00" />
            	</s:if>
            	<s:else>0</s:else>元
            	</b>
            	</p>
                <p><a href="homeBorrowInvestList.do">查看我的投资</a></p>
            </div>
        	<div class="lne_r2b">
            	<p><span>可用余额</span><b><s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if><s:else>0</s:else>元</b></p>
                <p style="margin-top:20px;"><a href="finance.do?m=1&type=4"><img src="images/small_red_btn.jpg" /></a>
                
                <!-- <a href="automaticBidInit.do" style="margin-left:5px;"><img src="images/small_blue_btn.jpg" /></a> -->
                
                </p>
            </div>
        	<div class="lne_r2c">
            	<p><span>冻结金额</span><b><s:if test="#request.accmountStatisMap.freezeAmount !=''">${accmountStatisMap.freezeAmount}</s:if><s:else>0</s:else>元</b></p>
                <!--<p><a href="#">查看还款列表</a></p>-->
            </div>
        	<div class="lne_r2d">
            	<p>
            	<strong>
            	  <s:if test="#request.accmountStatisMap.accountSum !=''">
            	      <fmt:formatNumber value="${accmountStatisMap.accountSum}" type="number" pattern="0.00" />
            	  </s:if>
            	  <s:else>0</s:else>元
            	</strong>
            	</p>
                <p><span>总资产</span></p>
                <p><img src="images/l_img2.png" /></p>
                <p><input type="button" onclick="javascript:window.location.href='financeStatisInit.do'" value="理财统计" /></p>
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
