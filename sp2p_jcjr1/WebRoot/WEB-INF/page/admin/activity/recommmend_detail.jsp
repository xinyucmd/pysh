<%@ page language="java" pageEncoding="UTF-8"%>
<style>
	ul li{ list-style:none;}
	.lne_cent{width:800px; height:auto; display:none; position:absolute; left:15%; top:10%;font-size: 14px; color: #666; font-family: "å¾®è½¯éé»";background:#e6f3fa;z-index: 9999;}
 	.weixin{ background:#c7c7c7; width:32px; height:26px; border-radius:2px; float:left; margin-left:10px;}
	.weixin img{ position:relative; top:3px; left:5px;}
	.p_text{  line-height:26px; margin-top:20px; font-size:12px;}
	.p_con{ overflow:hidden; width:650px; margin-top:10px;}
	.p_con ul{ background:#f1f1f1; padding:12px 0; overflow:hidden; margin:5px 0;}
	.p_con .item1 li{ float:left; width:130px; text-align:center;}
	.p_con .item2 li{ float:left; width:160px; text-align:center;}
	.p_con h5{ font-size:14px;}
	.p_con h4{ font-size:14px; color:#ff6960; margin-top:8px; text-align:center;}

	.p_po{width:690px;margin:20px auto; }
	.p_list{ width:650px;  margin:20px auto;}
	.closebtn{ background:url(/images/close.gif) no-repeat; top:20px;right:60px;  position:absolute; z-index:10001; height:25px; width:25px;}
	.theme-popover-mask{display:none;z-index: 9998;position:fixed;top:0;left:0;width:100%;height:100%;background:#000;opacity:0.4;filter:alpha(opacity=30);}
	.tab_con{ overflow:hidden;}
	.tab_con ul{ margin:0; padding:0; border:solid 1px #c7c7c7; overflow:hidden; border-left:none;}
	.tab_con ul li{ float:left; width:105px; text-align:center; line-height:30px; border-left:solid 1px #c7c7c7;}
</style>

 
<script>
jQuery(document).ready(function($) {
	$('.p_po .closebtn').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.lne_cent').slideUp(200);
	})

})
</script>
<div class="ne_wdzh"></div>
<div class="lne_cent">
	<div class="p_po">
	<a href="javascript:;"class="closebtn"></a>
	<div class="lne_centr" style="background:#fff; padding:20px 20px 50px 20px; width:650px;">
     <div class="p_con">
        	<ul class="item1">
            	<li><h5>推荐人数</h5><h4 id="beRecommendCount"></h4></li>
                <li><h5>实名认证人数</h5><h4 id="beRecommendIdCount"></h4></li>
                <li><h5>剩余推荐人数</h5><h4 id="availableRecommendCount"></h4></li>
                <li><h5>已投资用户</h5><h4 id="beRecommendInvstCount"></h4></li>
            </ul>
            <ul class="item2">
            	<li><h5>推荐人投资总额</h5><h4 id="beRecomendInvstAmountTotal"></h4></li>
                <li><h5>返现金额</h5><h4 id="cashAmountTotal"></h4></li>
                <li><h5>推荐人的投资佣金</h5><h4 id="invstBrokerageTotal"></h4></li>
                <li><h5>推荐人获取所有佣金</h5><h4 id="brokerageAmountTotal"></h4></li>
            </ul>
        </div>
     <div class="p_list">
     	<div class="p_list_title">
         	<span style="font-weight:bold; font-size:16px;">邀请列表</span>
         </div>
         <div class="tab_con" style="margin-top:10px;">
           <ul>
           		<li>好友TOP10榜单</li>
                <li>注册时间</li>
                <li>用户名</li>
                <li>来源</li>
                <li>投资</li>
                <li>有效推荐</li>
           </ul>
         </div>
         <div class="tab_con" id="tab_content1">
         </div>
     </div>	
	  <span id="loading" style="display:none"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
</div>
</div>
</div>
<div class="theme-popover-mask"></div>

<script>
	
	function showLayer(){
		
		$('.theme-popover-mask').fadeIn(100);
		$('.lne_cent').slideDown(200);
		
	}
	
	function queryRecommendInfo(recommendUserId){
		showLayer();
		empty();
		var param={};
		param["recommend_id"] = recommendUserId;
		var url = "../queryReconmmendInfoForAdmin.do";
		$("#loading").show();
		$.ajax({
		    type: "post",
		    url: url,
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	$("#loading").hide();
		    	$("#reconmmend_link").val(data.reconmmend_url);
		    	var rsb = data.recommendBase;
		    	var rs = data.recommnedSummary;
		    	settingSummary(rsb,rs);
		    	
		    	var tr_list_o = $("#tab_content1");
		    	var strHtml = "";
		    	for(var i=0;i<data.result.length;i++){
		    		var o = data.result[i];
		    		strHtml +="<ul>";
		    		strHtml +="<li>"+(i+1)+"</li>";
					strHtml +="<li>"+o.createTime+"</li>";
					strHtml +="<li>"+o.username+"</li>";
					strHtml +="<li>"+o.activity+"</li>";
					strHtml +="<li>"+o.investAmountTotal+"</li>";
					strHtml +="<li>"+o.type_lable+"</li>";
					strHtml +="</ul>";
		    	}
	    		tr_list_o.append(strHtml);
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
	    	$("#beRecommendCount").append(rs.beRecommendCount);
	    	$("#beRecommendInvstCount").append(rs.beRecommendInvstCount);
	    	$("#beRecomendInvstAmountTotal").append(rs.beRecomendInvstAmountTotal);
	    	$("#cashAmountTotal").append(rs.cashAmountTotal);
	    	$("#invstBrokerageTotal").append(rs.invstBrokerageTotal);
	    	$("#brokerageAmountTotal").append(rs.brokerageAmountTotal);
	    	$("#beRecommendIdCount").append(rs.beRecommendIdCount);
		}else{
	    	$("#beRecommendCount").append("0");
	    	$("#beRecommendInvstCount").append("0");
	    	$("#beRecomendInvstAmountTotal").append("0");
	    	$("#cashAmountTotal").append("0");
	    	$("#invstBrokerageTotal").append("0");
	    	$("#brokerageAmountTotal").append("0");
	    	$("#beRecommendIdCount").append("0");
		}
	}
	
	function empty(){
		var tr_list_o = $("#tab_content1");
		tr_list_o.empty();
		$("#availableRecommendCount").empty();
		$("#beRecommendCount").empty();
    	$("#beRecommendInvstCount").empty();
    	$("#beRecomendInvstAmountTotal").empty();
    	$("#cashAmountTotal").empty();
    	$("#invstBrokerageTotal").empty();
    	$("#brokerageAmountTotal").empty();
    	$("#beRecommendIdCount").empty();
	}
	
</script>