<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	    <jsp:include page="/include/head.jsp"></jsp:include>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	    <jsp:include page="/include/common.jsp"></jsp:include>
	    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" /> 
	    <style type="text/css">
		p{
		 
		padding:0px;
	}
.form_s{
 	width:226px;
 	height:30px;
 	 padding:0 10px;
 	 border:1px solid #96ccf4;
 	border-radius:3px;
 	 
}
.form_s:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
}
 .form-controls{
 	height:30px;
 	 padding:0 10px;
 	 border:1px solid #96ccf4;
 	border-radius:3px;
} 
.form-controls:focus{
	border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
}
</style>
	</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	








<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
	<jsp:include page="/include/left.jsp"></jsp:include>
	
 <div class="lne_centr">
           <!--还款情况--> 
            <div class="lne-centr-con">
                <div class="lne-centr-con-content">
                	<div style="padding:0 20px;">
                    	<p class="blue-title"><i>微信贷温馨提示：</i>我们会保管您的资料安全不被泄露，请你放心填写。</p>
                        	<div class="tb_list clearfix">
              	 <div class="form-group">
                  	 <label>自动投标状态：</label> <s:if test="%{automaticBidMap.bidStatus ==2}">开启状态</s:if><s:else>关闭状态</s:else>
                  </div>  
                  <div class="form-group">
                     <label>您的账户余额：</label> ${automaticBidMap.usableSum}元<input type="hidden" value="${automaticBidMap.usableSum}" id="usableSum" name="usableSum" />
                  </div> 
                    <div class="form-group">
                     <label>每次投标金额：</label>
                     	<input type="text" class="form_s" id="bidAmount" maxlength="20"  value="${automaticBidMap.bidAmount}"/> 元
                    </div>
                      <div class="form-group">
                     <label>利率范围：</label>
                     	<input type="text" class="form_s" style="width:80px;" id="rateStart" maxlength="20"  value="${automaticBidMap.rateStart}"/> % -- <input type="text" class="form_s" style="width:80px;" id="rateEnd" maxlength="20"  value="${automaticBidMap.rateEnd}"/> %
                    </div>
                    <div class="form-group">
                     <label>借款期限：</label>
                     	<s:select list="#{-1:'1天',-15:'15天',-25:'25天',1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}" id="deadlineStart" cssClass="form-controls"  listKey="key" listValue="value" name="automaticBidMap.deadlineStart"></s:select>      
                     	 -- 
                     	<s:select list="#{-1:'1天',-15:'15天',-25:'25天',1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}" id="deadlineEnd"  cssClass="form-controls" listKey="key" listValue="value" name="automaticBidMap.deadlineEnd"></s:select>                       
                     </div>
                     <div class="form-group">
                     <label>账户保留金额：</label>
                     	<input type="text" class="form_s" id="remandAmount" maxlength="20"  value="${automaticBidMap.remandAmount}"/> 元
                    </div>
                    <div class="form-group">
                     <label>借款类型：</label>
                     	<s:checkboxlist name="checkList.id" cssClass="checkeTypes" value="checkList.{#this.id}" list="#{3:'优选宝',4:'定息宝'}"  listKey="key" listValue="value"></s:checkboxlist>
                    </div> 
                    
                     	<s:if test="%{automaticBidMap.bidStatus ==2}">
                     	   <input type="button"  id="setbtn" class="btn btn-warning btn-lg" style="margin-left:80px;" value="关闭自动投标" />
                     	</s:if>
                        <s:else>
                           <input type="button"  id="setbtn" class="btn btn-warning btn-lg" style="margin-left:80px;" value="开启自动投标" />
                        </s:else>
                           <input type="button" class="btn btn-warning btn-lg" style="margin-left:10px;" id="savebtn" value="保存设置" />
               </div>
               </div>
               </div>
               <div class="personal-rule">
                   <p>自动投标工具说明</p>
                    <ul>
                        	<li>1. 投资项目的发布十五分钟后，才会启动自动投标。</li>
                            <li>2. 投标进度达到95%时停止自动投标。若投标后投标进度超过95%，则按照投标进度达到95%的金额<br/>向下取100的倍数金额值投标。</li>
                            <li>3. 单笔投标金额若超过该借款标的总额的20%，则按照20%的比例的金额向下取100的倍数金额值投标。</li>
                            <li>4. 满足自动投标规则的金额小于设定的每次投标金额，也会进行自动投标。</li>
                            <li>5. 借款用户在获得借款时会自动关闭自动投标，以避免借款被用作自动投标资金。</li>
                            <li>6. 投标排序规则如下：<br/>
                            	a) 投标序列按照用户开启自动投标的时间先后进行排序。<br/>
                                b) 每个用户每个标仅自动投标一次，投标后，排到队尾。<br/>
                                c)轮到用户投标时没有符合用户条件的标，也视为投标一次，重新排队。 
                            </li>
                        </ul>
              </div>  
         </div>
    </div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<input type="hidden" id="s" value="${automaticBidMap.bidStatus}"/>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script>
$(function(){
    //样式选中
    //$("#zh_hover").attr('class','nav_first');
	 //$("#zh_hover div").removeClass('none');		
     //$('#li_13').addClass('on');
     $(".lne_l3 > ul >li").removeClass("clicked");
		 $('#li_7').addClass('clicked');
	 $('#savebtn').click(function(){
	     var chk_value =[];  
         $('input[name="checkList.id"]:checked').each(function(){  
             chk_value.push($(this).val());  
         });
         $("#savebtn").attr("disabled",true);
         param['paramMap.usableSum'] = $('#usableSum').val();
	     param['paramMap.bidAmount'] = $('#bidAmount').val();
	     param['paramMap.rateStart'] = $('#rateStart').val();
	     param['paramMap.rateEnd'] = $("#rateEnd").val();
	     param['paramMap.deadlineStart'] = $('#deadlineStart').val();
	     param['paramMap.deadlineEnd'] = $('#deadlineEnd').val();
	     param['paramMap.creditStart'] = $('#creditStart').val();
	     param['paramMap.creditEnd'] = $('#creditEnd').val();
	     param['paramMap.remandAmount'] = $('#remandAmount').val();
	     param['paramMap.borrowWay']=''+chk_value;
	     $.shovePost('automaticBidModify.do',param,function(data){
	             $("#savebtn").attr("disabled",false);
		   alert(data.msg);
		 });
	 });
	 $('#setbtn').click(function(){
	     var str = $('#s').val();
	     param['paramMap.s']=str;
	     $.shovePost('automaticBidSet.do',param,function(data){
		   if(data.msg == 1){
		      alert('操作成功');
		      window.location.href='automaticBidInit.do';
		      return false;
		   }
		   alert(data.msg);
		 });
	 });
});	     
</script>
</body>
</html>