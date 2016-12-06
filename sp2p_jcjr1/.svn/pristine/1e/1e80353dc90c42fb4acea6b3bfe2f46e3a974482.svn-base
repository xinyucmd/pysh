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
	.totle-cont table th{ background:#e8ecef; height:36px;}
</style>
	</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
	<jsp:include page="/include/left.jsp"></jsp:include>
	
	<div class="lne_centr">
    	  
      	 <div class="lne-centr-con">
                <div class="lne-centr-con-content">
                	<div style="padding:0 20px;">
                    	<p class="blue-title"><i>微信贷温馨提示：</i>我们会保管您的资料安全不被泄露，请你放心填写。</p>
                        	<div class="tb_list clearfix">
                        	 <div class="form-group">
                                  <label>您的可用余额：</label> ${automaticBidMap.usableSum}元<input type="hidden" value="${automaticBidMap.usableSum}" id="usableSum" name="usableSum" />
                             </div>
                              <div class="form-group">
                                  <label>预约产品类型：</label><em>定息宝</em>
                             </div>
                             <div class="form-group">
	                             <label>还款方式：</label>
	                     	     <select class="form-controls" id = "repayMothedId" onchange="repayMothed()">
	                     	       <option value="1">先息后本</option>
	                     	       <option value="2">利随本清</option>
	                     	     </select>
                             </div>
                            <div class="form-group">
			                        <label>预约产品期限：</label>
			                     	<select class="form-controls" id = "limits" onchange="limits()">
			                     	    <option value="2">2个月</option>
			                     	    <option value="3">3个月</option>
			                     	    <option value="4">4个月</option>
			                     	    <option value="6">6个月</option>
			                     	    <option value="12">12个月</option>
			                     	    <option value="18">18个月</option>
			                     	    <option value="24">24个月</option>
			                     	    <option value="36">36个月</option>
			                     	</select>
                     	      </div>
                              <div class="form-group">
                                 <label>年化收益率：</label>
			                        <select   id="rate" onchange="rate()" class="form-controls" disabled>
			                            
			                            <option value="5.85">5.85%</option>
			                            <option value="6">6%</option>
			                            
			                     	    <option value="6.5">6.5%</option>
			                     	    <option value="7">7%</option>
			                     	    
			                     	    <option value="7.5">7.5%</option>
			                     	    <option value="8">8%</option>
			                     	    
			                     	    <option value="7.5">7.5%</option>
			                     	    <option value="8">8%</option>
			                     	    
			                     	    <option value="9">9%</option>
			                     	    <option value="9.5">9.5%</option>
			                     	    
			                     	    <option value="10.5">10.5%</option>
			                     	    <option value="11">11%</option>
			                     	    
			                     	    <option value="11">11%</option>
			                     	    <option value="12">12%</option>
			                     	    
			                     	    <option value="12">12%</option>
			                     	    <option value="14">14%</option>
			                     	    
			                     	</select>
                              </div>
                              <div class="form-group">
                                <label>预约金额：</label>
                     	              <input type="text" id="amount" class="form_s"  onkeyup="getInvestLx()"/> 元
                              </div>
                               <div class="form-group">
                              <label>预计收益：</label>
                     	      <span id="getLx">0.00</span> 元
                               </div>
                     
                         <input id="sub" type="button" class="btn btn-warning btn-lg" style="margin-left:150px;" value="立即预约"  onclick="submitAppointInvest()" /> 
                         <input id="wait" type="button" class="btn btn-warning btn-lg" style="margin-left:150px;display: none;" value="预约中" />
                    
                 		</div>
                      </div>
                  </div>
                <div class="lne-centr-con-content1" style="margin-top:10px;">
				 <table border="0" cellspacing="0" cellpadding="0" class="table-con">
					<thead>
                        <tr>
                            <th>预约产品类型</th>
                            <th>预约产品期限</th>
                            <th>预约产品年化</th>
                            <th>预约金额</th>
                            <th>预约时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                    </thead>
					<tbody>
				<s:iterator value="pageBean.page" var="bean" status="s">
                        <tr >  
                                <s:if test="%{#bean.type == 4}">
	                              <td>定息宝</td>
	                            </s:if>
	                            
	                            <td>${bean.limits}个月</td>
	                            <td>${bean.rate}%</td>
	                            <td>${bean.amount}</td>
	                            <td>${bean.createTime_f}</td>
	                            <s:if test="%{#bean.state == 0}">
	                               <td>未投资</td>
	                            </s:if>
	                            <s:if test="%{#bean.state == 1}">
	                               <td>已投资</td>
	                            </s:if>
	                            
	                            <s:if test="%{#bean.state == 0}">
	                               <td><a href="###" onclick="deleteAppointInvest('${bean.id}')">取消</a></td>
	                            </s:if>
	                            <s:if test="%{#bean.state == 1}">
	                               <td>--</td>
	                            </s:if>
	                            
	                            
                        </tr>
                     </s:iterator>
                    </tbody>
				</table>
			</div>
                    <div class="personal-rule">
              	  	<p>预约投标说明</p>
                    <ul>
                        	<li>1. 预约每次最低1000元最高100000元，当有符合条件的标发布后会由系统即刻进行投标操作。</li>
                            <li>2. 预约后直到未进行投资期间，可以在预约列表中进行取消操作。</li>
                            <li>3. 预约并投标成功之后不可撤销。</li>
                            <li>4. 预约功能无法投资有约标字样的标。</li>
                            <li>5. 预约功能不区分普通标和活动标等特殊标，仅根据预约条件进行区分。</li>
                            <li>6. 投标排序规则如下：<br/>
                            	a) 投标顺序按照用户开启预约的时间先后进行排序。<br/>
                                b) 同一标的仅可以进行一次预约投标操作。
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
     //添加预约投标
     function submitAppointInvest(){
    	 
    	var param = {};
    	param["limits"] = $("#limits").val();
 		param["rate"] = $("#rate").val();
 		param["amount"] = $("#amount").val();
 		param["state"] = 0;
 		param["usableSum"] = $("#usableSum").val();
 		
 		$('#sub').hide();
 		$('#wait').show();
 		
 		$.ajax({
 		    type: "post",
 		    url: "addAppointInvest.do",
 		    dataType: "json",
 		    data:param,
 		    success: function (data) {
 		    	alert(data.msg);
 		    	if(data.m==1){
 		    		location.reload();
 		    	}
 		    	$('#sub').show();
 		 		$('#wait').hide();
 		    }
 		});
     }
     
     //删除预约投标
     function deleteAppointInvest(id){
    	 var param = {};
     	 param["id"] = id;
    	 $.ajax({
  		    type: "post",
  		    url: "removeAppointInvest.do",
  		    dataType: "json",
  		    data:param,
  		    success: function (data) {
  		    	alert(data.msg);
  		    	if(data.m==1){
  		    		location.reload();
  		    	}
  		    }
  		}); 
     }
     
    function  getInvestLx(){
    	var limits = $("#limits").val();
 		var rate  = $("#rate").val();
 		var amount = $("#amount").val();
 		var lx = (amount*rate/100)/12*limits;
 		 
 		$('#getLx').html(lx.toFixed(2));
    }
    
    //期限
    function  limits(){
    	
    	  var limits = $("#limits").val();
	      var obj1=document.getElementById("rate");
	      var repayMothedValue  = $("#repayMothedId").val();
	      if(limits==2){
	    	  if(repayMothedValue==1){
	    		  obj1.options[0].selected=true;  
	    	  }
	    	  if(repayMothedValue==2){
	    		  obj1.options[1].selected=true;  
	    	  }
	    	   
	       }
	       if(limits==3){ 
	    	      if(repayMothedValue==1){
		    		  obj1.options[2].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[3].selected=true;  
		    	  }
	    	   
	       }
	       
	       if(limits==4){ 
	    	      if(repayMothedValue==1){
		    		  obj1.options[4].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[5].selected=true;  
		    	  }
	    	   
	       }
	       
	       if(limits==6){      
	    	     if(repayMothedValue==1){
		    		  obj1.options[6].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[7].selected=true;  
		    	  }
	       }
	       
	       if(limits==12){      
	    	     if(repayMothedValue==1){
		    		  obj1.options[8].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[9].selected=true;  
		    	  }
	       }
	       
	       if(limits==18){      
	    	     if(repayMothedValue==1){
		    		  obj1.options[10].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[11].selected=true;  
		    	  }
	       }
	       
	       if(limits==24){      
	    	     if(repayMothedValue==1){
		    		  obj1.options[12].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[13].selected=true;  
		    	  }
	       }
	       
	       if(limits==36){      
	    	     if(repayMothedValue==1){
		    		  obj1.options[14].selected=true;  
		    	  }
		    	  if(repayMothedValue==2){
		    		  obj1.options[15].selected=true;  
		    	  }
	       }
    	
    	
    
 		var rate  = $("#rate").val();
 		var amount = $("#amount").val();
 		var lx = (amount*rate/100)/12*limits;
 		 
 		$('#getLx').html(lx.toFixed(2));
    }
    
    //利率
    function  rate(){
    	var limits = $("#limits").val();
 		var rate  = $("#rate").val();
 		var amount = $("#amount").val();
 		var lx = (amount*rate/100)/12*limits;
 		 
 		$('#getLx').html(lx.toFixed(2));
    }
    
    
    //还款方式
    function repayMothed(){
        var limitsObj = document.getElementById("limits");
            limitsObj.options[0].selected=true; 
        var rateObj = document.getElementById("rate");
    	var repayMothedValue  = $("#repayMothedId").val();
    	 
    	if(repayMothedValue==1){//按月付息
    		rateObj.options[0].selected=true; 
    	}
    	if(repayMothedValue==2){//按月付息
    		rateObj.options[1].selected=true; 
    	}
    	
    	var limits = $("#limits").val();
    	var rate  = $("#rate").val();
 		var amount = $("#amount").val();
 		var lx = (amount*rate/100)/12*limits;
 		$('#getLx').html(lx.toFixed(2));
    }
     
</script>
</body>
</html>