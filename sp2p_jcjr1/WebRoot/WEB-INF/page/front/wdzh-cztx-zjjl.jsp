<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
    <jsp:include page="/include/common.jsp"></jsp:include>
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <meta http-equiv="keywords" content="个人投资理财|互联网理财|P2P理财产品|P2P收益|p2p网贷理财" />
    <meta http-equiv="description" content="投资理财找微信贷，微信贷提供高收益P2P理财产品，为个人和企业提供透明、安全、高效的互联网金融服务。" />
</head>
<body> 
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<!--我的微信贷主要内容-->
<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
    <jsp:include page="/include/left.jsp" ></jsp:include>
        <div class="lne_centr">
        <!--top-->
        	<div class="lne-centr-top">
            	<ul class="ul-one ul-line">
                	<li><i class="p-arrow icon10"></i>账户总额：<em><fmt:formatNumber value="${accmountStatisMap.accountSum}" type="number" pattern="0.00" /></em>元</li>
                    <li><i class="p-arrow icon2"></i>冻结金额：<em>${freezeSum }</em>元</li>
                </ul>
                <ul class="ul-one ul-line">
                	<li><i class="p-arrow icon3"></i>可用余额：<em>${usableSum }</em>元</li>
                    <li><i class="p-arrow icon4"></i>利息收益：<em><s:if test="#request.accmountStatisMap.rateEarnAmount !=''"><fmt:formatNumber value="${accmountStatisMap.rateEarnAmount}" type="number" pattern="0.00" /></s:if><s:else>0.00</s:else></em>元</li>
                </ul>
                 <ul class="ul-one">
                	<li><i class="p-arrow icon8"></i>总收益：<em><s:if test="#request.accmountStatisMap.earnSum !=''"><fmt:formatNumber value="${accmountStatisMap.earnSum}" type="number" pattern="0.00" /></s:if><s:else>0.00</s:else></em>元</li>
                    <li><i class="p-arrow icon9"></i>其他收益：<em><s:if test="#request.accmountStatisMap.otherEarnAmount !=''">${accmountStatisMap.otherEarnAmount}</s:if><s:else>0.00</s:else></em>元</li>
                </ul>
            </div>
           <!--还款情况--> 
            <div class="lne-centr-con">
                <div class="mg-height">
                    <form class="form-inline">
                        <div class="form-group">
                          <label >起始时间</label>
                         <input id="startTime" type="text" class="form-control form-xs" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
                        </div>
                        <div class="form-group">
                          <label >结束时间</label>
                          <input id="endTime" type="text" class="form-control form-xs" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
                        </div>
                        <div class="form-group">
                          <label >类型</label>
                           <s:select list="potypeList" id="momeyType" name="momeyType" class="form-control" style="padding:4px 0; height:30px\9;*height:30px; line-height:30px\9; *line-height:30px; width:160px;" listKey="operateType" listValue="fundMode"   headerKey="" headerValue="--请选择--"></s:select>
                        </div>
                        <input type="button" class="btn btn-primary btn-lg" style="margin-left:5px;" value="查 询" onclick="fundRecordList();"/>
                      </form>
                </div>
                <div class="lne-centr-con-content" id="fundRecord">
                </div>
            </div>
        </div>
    </div>
</div>
<!--我的微信贷主要内容-->
<!--返回顶部-->
 
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script>
    $(function(){
        
	     //$("#zh_hover").attr('class','nav_first');
	      //$('#li_2').addClass('on');
		  //$('.tabmain').find('li').click(function(){
		  //$('.tabmain').find('li').removeClass('on');
		  $(".lne_l3 > ul >li").removeClass("clicked");
		 $('#li_3').addClass('clicked');
        
       //}); 
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.shovePost("queryFundrecordList.do",praData,initCallBack);
	}
	function initCallBack(data){
		$("#fundRecord").html(data);
	}
	
   function fundRecordList(){
      if($("#startTime").val()!="" && $("#endTime").val()!=""){
	        if($("#startTime").val() >$("#endTime").val() ){
	         alert("结束日期要大于开始日期");
	         return;
	      }
      }
      param["pageBean.pageNum"] = 1;
      param["paramMap.startTime"]=$("#startTime").val();
      param["paramMap.endTime"]=$("#endTime").val();
      if($("#momeyType").val()==''){
    	  param["paramMap.momeyType"]='';
      }else{
      	  param["paramMap.momeyType"]=$("#momeyType").find("option:selected").text();
      }
      $.shovePost("queryFundrecordList.do",param,function(data){
         $("#fundRecord").html(data);
      });
   }
   
   function jumpUrl(obj){
          window.location.href=obj;
       }
</script>
</body>
</html>
 