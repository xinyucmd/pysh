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
	        .p_con{ overflow:hidden; width:650px; margin-top:10px; margin-left:30px;}
	    /*	.p_con ul{ background:#f1f1f1; padding:12px 0; overflow:hidden; margin:5px 0;}*/
	        .p_con .item1 li{ float:left; width:130px; text-align:center;}
	        .p_con .item2 li{ float:left; width:160px; text-align:center;}
	        .p_con h5{ font-size:14px;}
	        .p_con h4{ font-size:14px; color:#ff6960; margin-top:8px; text-align:center;}
	        .p_po{width:690px;margin:20px auto; }
	        .p_list{ width:650px;  margin:20px auto;}
	        table { border-collapse: collapse; }
	        .zh_table{line-height:30px;	}	
	        .zh_table th{border:1px solid #ddd; background-color:#f9f9f9;font-weight:normal;text-align:center; color:#666; height:40px; width:92px; }
	        .zh_table td{border:1px solid #ddd;text-align:center; color:#666; font-size:14px; width:92px; }
	        .p_con_left{ float:left; width:500px;}
	        .p_con_left ul{ width:500px; padding:0; overflow:hidden;}
	        .p_con_left ul li{ float:left; width:230px; font-weight:bold; color:#666; font-family:"微软雅黑";}
	        .p_con_left ul li a{ color:#03F; font-size:12px;}
	        .p_con_right{  width:500px; overflow:hidden;}
			.page{ text-align:center; margin-top:25px;}
			.page .disabled {border-color: #eee;border-style: solid;border-width: 1px; color: #ddd;margin: 2px;padding: 2px 5px;}
			.page .current {background-color: #000000;border-color: #000000;border-right: 1px solid #000000;border-style: solid;border-width: 1px; color: #fff;
    font-weight: bold;margin: 2px;padding: 2px 5px;}
			.page a {border-color: #000000; border-right: 1px solid #000000;border-style: solid;border-width: 1px;color: #000000;margin: 2px;padding: 2px 5px;
    text-decoration: none;}
	    </style>	    
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="wrap" style="padding-top:10px;">
<div class="lne_cent">
<%@include file="/include/left.jsp" %>

  <div class="lne_centr">    
            <div class="lne-centr-con" style="margin-top:10px;">
            	<div style="background:#fff;padding-bottom:5px; margin-bottom:10px;">
                	<div class="myhome_tit tab_meun">
                        <ul>
                            <li onclick="jumpUrl('toRewardInfo.do');" style="width:120px; width:115px\9;">现金奖励</li>
                            <s:if test="#request.flag == 1">
					        <li  class="on" onclick="jumpUrl('myBonus.do');" style="width:120px; width:115px\9;">红包奖励</li>
				        <li onclick="jumpUrl('myTyj.do');" style="width:120px; width:115px\9;">我的体验金</li>
					        <li onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px; width:115px\9;">奖励规则</li>
					        <li onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
					        </s:if>
					        <s:elseif test="#request.flag == 2">
					        <li   onclick="jumpUrl('myBonus.do');" style="width:120px; width:115px\9;">红包奖励</li>
					        <li onclick="jumpUrl('myTyj.do');" style="width:120px; width:115px\9;">我的体验金</li>
					        <li onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px; width:115px\9;">奖励规则</li>
					        <li onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
					        </s:elseif>
					        <s:elseif test="#request.flag == 3">
					        <li onclick="jumpUrl('myBonus.do');" style="width:120px; width:115px\9;">红包奖励</li>
					        <li class="on" onclick="jumpUrl('myTyj.do');" style="width:120px; width:115px\9;">我的体验金</li>
					        <li onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px; width:115px\9;">奖励规则</li>
					        <li onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
					        </s:elseif>
					         <s:elseif test="#request.flag == 4">
					        <li onclick="jumpUrl('myBonus.do');" style="width:120px; width:115px\9;">红包奖励</li>
					        <li onclick="jumpUrl('myTyj.do');" style="width:120px; width:115px\9;">我的体验金</li>
					        <li onclick="jumpUrl('rewardRules.do');" style="border-right:none; width:120px; width:115px\9;">奖励规则</li>
					        <li class="on" onclick="jumpUrl('activityCi.do');" style="border-right:none; width:120px; width:115px\9;">复投奖励</li>
					        </s:elseif>
					        
                        </ul>
                    </div> 
              
                   <div class="lne-centr-top" style="border-bottom:solid 10px #f6f6f6 ;">
                            <ul class="ul-one ul-line">
                                <li>红包总额：<em><s:if test="#request.bonusRewardMap.bonus_money > 0">${bonusRewardMap.bonus_money}</s:if><s:else>0.00</s:else></em>元</li>
                                <li>可用红包：<em><s:if test="#request.bonusRewardMap.bonus_avaliable > 0">${bonusRewardMap.bonus_avaliable}</s:if><s:else>0.00</s:else></em>元</li>
                            </ul>
                            <ul class="ul-one ul-line">
                                <li>已用红包：<em><s:if test="#request.bonusRewardMap.bonus_used > 0">${bonusRewardMap.bonus_used}</s:if><s:else>0.00</s:else></em>元</li>
                                <li>已过期红包：<em><s:if test="#request.bonusRewardMap.bonus_expired > 0">${bonusRewardMap.bonus_expired}</s:if><s:else>0.00</s:else></em>元</li>
                            </ul>
                        </div>
               
		</div>
		<table  id="tab_list" border="0" cellspacing="0" cellpadding="0" class="table-con"></table>
        <div class="s_foot-page">
          <p>
         	<a id="backPage">上一页</a>
            <span class="current" id="currentPage">1</span>
             /
            <span class="current" id="totalPage">1</span>
            <a id = "nextPage">下一页</a>
            </p>
         </div>
     </div>	  
</div>
</div>
  </div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/script/m/jquery.rye-core-1.0.js"></script>
<script src="/script/m/rye.jc-core-1.0.js"></script>
<script src="/script/m/rye.user-1.0.js"></script>
<script src="/script/recommend/rye-jc-recommend-bonus.js"></script>
<script >
	var param = {};
	var recommend = new Recommend(param);
	var pageNum = 1;
	var totalPageNum = 1;
	var flag = '${flag}';
	
	recommend.list(pageNum, 10, flag);
	
	function flushPage(){
		$("#currentPage").empty();
		$("#currentPage").append(pageNum);
	}
	
	$("#backPage").click(function(){
		if(pageNum == 1){
			alert('已经是第一页了');
			$("#backPage").attr("disabled","disabled");
		}else{
			pageNum -=1;
			recommend.list(pageNum, 10, flag);
		}
		
		flushPage();
	});
	
	$("#nextPage").click(function(){
		if(pageNum == totalPageNum){
			alert('没有更多数据！');
		}else{
			pageNum +=1;
			recommend.list(pageNum, 10);
		}
		
		flushPage();
		
	});
	
	function jumpUrl(obj){
	    window.location.href=obj;
	}
	
</script>
</body>
</html>