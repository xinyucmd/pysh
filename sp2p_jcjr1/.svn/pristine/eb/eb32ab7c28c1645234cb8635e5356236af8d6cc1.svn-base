<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${sitemap.siteName}</title>
	<jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include> 
 	<style>
	ul li{ list-style:none;}
/*	.lne_cent{width:800px; height:auto; display:block; position:absolute; left:15%; top:10%;font-size: 14px; color: #666; font-family: "微软雅黑";}*/
 /*	.weixin{ background:#c7c7c7; width:32px; height:26px; border-radius:2px; float:left; margin-left:10px;}
	.weixin img{ position:relative; top:3px; left:5px;}*/
	.p_text{  line-height:26px; margin-top:20px; font-size:12px;}
	.p_con{ overflow:hidden; width:650px; margin-top:10px;}
/*	.p_con ul{ background:#f1f1f1; padding:12px 0; overflow:hidden; margin:5px 0;}*/
	.p_con .item1 li{ float:left; width:130px; text-align:center;}
	.p_con .item2 li{ float:left; width:160px; text-align:center;}
	.p_con h5{ font-size:14px;}
	.p_con h4{ font-size:14px; color:#ff6960; margin-top:8px; text-align:center;}
	.p_po{width:690px;margin:20px auto; }
	.p_list{ width:650px;  margin:20px auto;}
	table { border-collapse: collapse; }
	.zh_table{margin-top:10px;line-height:30px;	}	
	.zh_table th{border:1px solid #ddd; background-color:#f9f9f9;font-weight:normal;text-align:center; width:108px;}
	.zh_table td{border:1px solid #ddd;text-align:center; width:108px;}
	.p_con_left{ float:left; width:440px;}
	.p_con_left ul{ width:420px; padding:0; overflow:hidden; font-weight:bold; font-size:14px; line-height:40px;}
	.p_con_left ul li{ float:left; width:140px;}
	.p_con_left ul li a{ color:#03F; font-size:14px; margin-left:10px;}
	.p_con_right{ margin-left:25px; width:150px; float:left;}
	.lianjie{ margin-top:20px;}
	.theme-popover{ width:300px; height:300px; position:fixed; display:none; z-index:9999; top:0; background:#fbfbfb; border-radius:8px; left:40%; border:solid 2px #dbdada; font-size:14px;}
	.theme-poptit .close{ background:url(/images/recommend/close.jpg) no-repeat; top:5px;right:5px;  position:absolute; z-index:10001; height:27px; width:27px;}
	.theme-popover .tibo{ height:30px;  padding:0 20px;}
	.jp_content{ width:500px; height:500px; position:fixed; display:none; z-index:9999; top:10%; background:#fbfbfb; border-radius:8px; left:35%; border:solid 5px #dbdada; font-size:14px; padding:20px;}
	.jp_content p{ line-height:26px;}
	.jp_content1{ width:500px; height:300px; position:fixed; display:none; z-index:9999; top:10%; background:#fbfbfb; border-radius:8px; left:35%; border:solid 5px #dbdada; font-size:14px; padding:20px;}
	.jp_content1 p{ line-height:26px;}
	.jp_btn{ width:35px; height:35px; position:absolute; top:3px; right:1px; z-index:100002; }
	.jp_btn .close3{ background:url(/images/recommend/close.jpg) no-repeat; left:0;position:absolute; z-index:10001; height:27px; width:27px; display:block;}
	.jp_btn .close4{ background:url(/images/recommend/close.jpg) no-repeat; left:0;position:absolute; z-index:10001; height:27px; width:27px; display:block;}
	.load_con{ width:100%; height:200%; position:absolute; background:#fff; filter:alpha(opacity=30);  -moz-opacity:0.3;  -khtml-opacity: 0.3;  opacity: 0.3; top:0; }
</style>

</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="ne_wdzh"></div>
<div class="lne_cent">
	<!-- 引用我的帐号主页左边栏 -->
    <%@include file="/include/left.jsp" %>
	<div class="lne_centr" style="background:#fff; padding:20px  0 50px 30px; width:710px; position:relative;">
     <div class="p_con">
      		<div class="p_con_left">
            	<ul>
                	<li>我的好友：<font id="beRecommendCount"></font>人</li><li>剩余邀请券：<font id="availableRecommendCount"></font>张</li>
                	<!-- <li><a href="finance.do?m=1&type=4">获得更多</a></li> -->
                </ul>
                <ul>
                	<li>有效认证：<font id="beRecommendIdCount"></font>人</li><li>邀请奖励：<font id="cashAmountTotal"></font>元</li>
                	<!-- <li><a href="javascript:;" class="jp">奖励规则</a></li> -->
                </ul>
                <ul>
                	<li>已投资：<font id="beRecommendInvstCount"></font>人</li><li>投资佣金：<font id="invstBrokerageTotal"></font>元</li><li>
                	<a href="javascript:;" class="jp1">佣金规则</a></li>
                </ul>
            </div>
            <div class="p_con_right">
            	<!-- 
            	<div class="weixin"><a href="javascript:;"  onclick="qrcodeClick();"><img src="images/recommend/wx.png" width="176" height="49" /></a></div> 
            	 -->
                <div class="lianjie"><a href="#"><img id="img_cp_link" src="images/recommend/lianjie.png" width="176" height="49" data-clipboard-target="reconmmend_link"/></a></div> 
        	</div>
        </div>
        <div class="jp_content">
            <div>
                    <h4 style=" font-size:16px; font-weight:bold;">奖励规则</h4>
                    <p>1.	微信贷平台用户，累计最低投资金额1000元，即可获10张邀请奖励券，按照100元/张的比例递增，单个账号最高获得100张邀请奖励券。</p>
                    <p>2.	用户每邀请一位好友成功注册微信贷即消耗一张邀请奖励券，好友实名认证后，可获得50元现金奖励。</p>
                    <p>3.	推荐人的邀请奖励券用完后，可再次在微信贷投资1000元获得邀请奖励券，账户最高额度为100张，累计参与次数不限。</p>
                    <p>4.	被推荐用户和手机号必须真实有效，平台客服也会对活动期间新增注册用户的电话进行核实，如发现恶意刷奖的用户，微信贷有权取消其活动资格，所得奖励不予承兑。</p>
                    <p>5.	每个手机号码、身份证仅限参加一次，刷奖、冒用他人身份证、银行卡者一经核实，取消活动资格，所得奖励不予承兑。</p>
                    <p>6.	新用户在PC端登录网站注册时，需填写推荐人的微信贷登陆名（用户名ID），漏填或错填无奖励，也不接受人工补录申请。</p>
                    <p>7.	活动期间，没有推荐人的新注册用户不享受注册优惠奖励。</p>
                    <p>8.	活动期结束后如邀请奖励券未使用自动作废，系统清零。</p>
                    <p>9.	活动期间产生的任何提现费用及由用户自理。</p>
                    <p>10.	本活动最终解释权归微信贷所有。</p>
            </div>
        	<div class="jp_btn"><a id="aclose3" title="关闭" class="close3"></a></div>
        </div>
        <div class="jp_content1">
            <div>
                    <h4 style=" font-size:16px; font-weight:bold;">投资奖励规则</h4>
                    <p>推荐人享受被推荐人活动期间投资本金1%的年化收益返利，奖励按期发放，期限与投资人的投资期限相同。</p>
                    <p>例如：<br/>
如被推荐人投资100万的1年期15%的定息宝，推荐人每月可获得833元，一共能获得10000元投资返利，如投资额人在投资周期内，将债权转出，则推荐人将不再收到投资奖励。
</p>
            </div>
        	<div class="jp_btn"><a id="aclose4" title="关闭" class="close4"></a></div>
        </div>
        <div class="theme-popover">
        <div>
       	  	<p class="tibo" style="border-bottom:solid 1px #c9c9c9; padding-top:20px;" >分享到微信朋友圈</p>
            <div id="codes" style="margin: 20px auto;left:50px;width:135px;height:135x">
            </div>
            <p class="tibo" style="border-top:solid 1px #c9c9c9; padding-top:10px;">打开微信使用"扫一扫"即可将网页分享到我的微信朋友圈。</p>
        </div>	
        <div class="theme-poptit"><a href="javascript:close();" title="关闭" class="close"></a></div>
    </div>
     <div class="p_list">
     	<div class="p_list_title">
         	<span style="font-weight:bold; font-size:16px;">我的好友</span>
         </div>
         <div>
         	<table id="tab_list" width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table" style="margin-top:10px;">
                     <tr>
                         <th>用户名</th>
                         <th>注册时间</th>
                         <th>身份认证</th>
                         <th>是否投资</th>
                        <!--  <th>邀请奖励</th> -->
                          <!-- <th>累计投资佣金</th>   -->
                         <th>是否有效</th> 
                     </tr>
                  <table id="tab_list" style="" cellspacing="0" cellpadding="0"
						width="100%" border="0" class="zh_table">
                 </table>  
           </table> 
         </div>
     </div>	
     </div>
     </div>
	  <div class="load_con" id="loading"><span><img src="../images/admin/load.gif" class="load" alt="加载中..."  style="position:absolute; top:25%; left:50%;"/></span></div>
	  
<input type="hidden" id="reconmmend_link" value=""/>
<input type="hidden" id="reconmmend_url_code" value=""/>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script>
	var param={};
	$('.jp').click(function(){
		$('.jp_content').slideDown(200);
	})
	$('#aclose3').click(function(){
		$('.jp_content').slideUp(200);
	})
	
	$('.jp1').click(function(){
		$('.jp_content1').slideDown(200);
	})
	$('#aclose4').click(function(){
		$('.jp_content1').slideUp(200);
	})

	initPage();
	function initPage(){
		var url = "queryReconmmendInfo.do";
		$("#loading").show();
		$.ajax({
		    type: "post",
		    url: url,
		    dataType: "json",
		    // data:param,
		    success: function (data) {
		    	$("#reconmmend_link").val(data.reconmmend_url);
		    	$("#reconmmend_url_code").val(data.reconmmend_url_code);
		    	var rsb = data.recommendBase;
		    	var rs = data.recommnedSummary;
		    	settingSummary(rsb,rs);
		    	
		    	var tr_list_o = $("#tab_list");
		    	var strHtml = "";
		    	for(var i=0;i<data.result.length;i++){
		    		var o = data.result[i];
		    		strHtml +="<tr>";
					strHtml +="<td>"+o.username+"</td>";
					strHtml +="<td>"+o.createTime+"</td>";
					if(o.authentication==1){
						strHtml +="<td>是</td>";
					}else{
						strHtml +="<td>否</td>";
					}
					if(o.investAmountTotal>0){
						strHtml +="<td>是</td>";
					}else{
						strHtml +="<td>否</td>";
					}
					//strHtml +="<td>"+o.cashAmountTotal+"</td>";
					//strHtml +="<td>"+o.investAmountBrokerageTotal+"</td>";
					strHtml +="<td>"+o.type_lable+"</td>";
					strHtml +="</tr>";
		    	}
	    		tr_list_o.append(strHtml);
	    		
	    		$("#loading").hide();
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		    	$("#loading").hide();
		       alert(errorThrown);
		    }
		});
		
	}
	
	function settingSummary(rsb,rs){
		// 设置汇总数据
		if(rsb!=null && rsb!= 'undefined' && rsb!=undefined){
			$("#availableRecommendCount").append(rsb.availableRecommendCount);
		}else{
			$("#availableRecommendCount").append("0");
		}
		
		if(rs!=null && rs!= 'undefined' && rs!=undefined){
	    	$("#start_time").append(rs.start_time);
	    	$("#end_time").append(rs.end_time);
	    	$("#beRecommendCount").append(rs.beRecommendCount);
	    	$("#beRecommendInvstCount").append(rs.beRecommendInvstCount);
	    	$("#beRecomendInvstAmountTotal").append(rs.beRecomendInvstAmountTotal);
	    	$("#cashAmountTotal").append(rs.cashAmountTotal);
	    	$("#invstBrokerageTotal").append(rs.invstBrokerageTotal);
	    	$("#brokerageAmountTotal").append(rs.brokerageAmountTotal);
	    	$("#beRecommendIdCount").append(rs.beRecommendIdCount);
		}else{
			$("#start_time").append("0");
	    	$("#end_time").append("0");
	    	$("#beRecommendCount").append("0");
	    	$("#beRecommendInvstCount").append("0");
	    	$("#beRecomendInvstAmountTotal").append("0");
	    	$("#cashAmountTotal").append("0");
	    	$("#invstBrokerageTotal").append("0");
	    	$("#brokerageAmountTotal").append("0");
	    	$("#beRecommendIdCount").append("0");
		}
		
	}
</script>
<script type="text/javascript" src="js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="js/ZeroClipboard.js"></script>

<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>

<script type="text/javascript">
	var user = new MUser();
	var temp=0;
	function qrcodeClick(){
		$('.theme-popover').slideDown(200);
		var conCent= $("#reconmmend_url_code").val();
		if(temp ==0){
			user.genQCode("codes",conCent);
		}
		
		/*if(temp==0){
			$("#codes").qrcode({ 
			    render: "table", //table方式 
			    width: 200, //宽度 
			    height:200, //高度 
			    typeNumber  : -1, //计算模式  
			    correctLevel:0,
			    text: conCent //任意内容 
			});
		}*/
		temp = 1;
	}
	function close(){
		$('.theme-popover').css("display","none");
	}
</script>
<script type="text/javascript">

// 定义一个新的复制对象
var clip = new ZeroClipboard($("#img_cp_link"), {
  moviePath: "js/ZeroClipboard.swf"
} );

// 复制内容到剪贴板成功后的操作
clip.on( 'complete', function(client, args) {
   //alert("复制成功，复制内容为："+ args.text);
	alert("复制成功,复制内容可直接粘贴!");
} );

</script>
</body>
</html>