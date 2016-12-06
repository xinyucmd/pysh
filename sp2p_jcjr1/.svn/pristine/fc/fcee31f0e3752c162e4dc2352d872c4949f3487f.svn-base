<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${sitemap.siteName}</title>
   <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include> 
</head>

<body>
<!-- 引用头部公共部分 -->




<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="ne_wdzh"></div>
<div class="lne_cent">

	<jsp:include page="/include/left.jsp"></jsp:include>


	<div class="lne_centr s_centr">
    	 <!--标题-->
      	 <div class="myhome_tit tab_meun">
         	<ul>
                <li class="on">资金记录</li>
            </ul>
         </div>
         <!--内容-->
         <div class="myhome_content tab_content clearfix">
             <!--资金记录-->
             <div>
           	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zh_table s_zhtab" style="margin-top:10px;">
					<tr>
						<td>账户总额：￥<fmt:formatNumber value="${accmountStatisMap.accountSum}" type="number" pattern="0.00" /></td>
						<td>可用余额：￥${usableSum }</td>
						<td>冻结金额：￥${freezeSum }</td>
					</tr>
					<tr>
						<td>总收益：￥<s:if test="#request.accmountStatisMap.earnSum !=''"><fmt:formatNumber value="${accmountStatisMap.earnSum}" type="number" pattern="0.00" /></s:if><s:else>0.00</s:else></td>
						<td>利息收益：￥<s:if test="#request.accmountStatisMap.rateEarnAmount !=''"><fmt:formatNumber value="${accmountStatisMap.rateEarnAmount}" type="number" pattern="0.00" /></s:if><s:else>0.00</s:else></td>
						<td>其他收益：￥<s:if test="#request.accmountStatisMap.otherEarnAmount !=''">${accmountStatisMap.otherEarnAmount}</s:if><s:else>0.00</s:else></td>
					</tr>
				</table>
                 <div class="myhome_srh">
                   	 <ul class="clearfix">
                        <li>起始时间：<input id="startTime" type="text" class="inp140 test2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/></li>
                        <li>结束时间：<input id="endTime" type="text" class="inp140 test2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/></li>
                        <li>类型：<s:select list="potypeList" id="momeyType" name="momeyType" cssClass="sel1" listKey="operateType" listValue="fundMode"   headerKey="" headerValue="--请选择--"></s:select></li>
                        <li><input type="button" class="myhome_btn" value="查 询" onclick="fundRecordList();"/></li>
                     </ul>
                 </div>
                 
                 <div id="fundRecord"></div>
                
             </div>	
         </div>
    </div>
</div>

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
