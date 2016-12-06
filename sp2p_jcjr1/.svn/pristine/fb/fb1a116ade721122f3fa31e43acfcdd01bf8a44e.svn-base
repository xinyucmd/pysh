<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>媒体报道-添加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>

		
	</head>
	<body>
	
	<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="main_alll_h2">
								<a href="persioninfo.do?id=${userId}">基本信息</a>
							</td>
						
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
							<s:if test="#request.uId==-1">
							   上传资料
							</s:if>
							<s:else>
							  <a href="persionData.do?id=${userId}">上传资料</a>
							</s:else>
								
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" height="28" class="xxk_all_a">
							
							<a href="personpublishBorrowInit.do?id=${userId}">发布借款</a>
<%--							<s:if test="#request.userId==-1">--%>
<%--							  发布借款--%>
<%--							</s:if>--%>
<%--							<s:else>--%>
<%--							  <a href="publishBorrowInit.do?paramMap.userId=${userId}&paramMap.applyId=${applyId }&paramMap.type=1">发布借款</a>--%>
<%--							</s:else>--%>
							</td>
							<td width="2">
								&nbsp;
							</td>
						</tr>
					</table>
					<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						
						<span id="dataInfo">
						
						<div class="boxmain2">
	<p class="tips">
		<span style="color: #DB7017">*</span> <span style="font-size:12px;">为必填项，所有资料均会严格保密。</span><span style="color: #DB7017">*</span> &nbsp;<span style="font-size:12px;">成为借款人必填项。</span>
	</p>
	<div class="biaoge2">
		<form id="BaseDataform">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style=" font-size:12px;">
				<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>真实姓名：
					</td>
					<td>
							<input type="text" class="inp188" name="paramMap.realName"
								id="realName" value="${map.realName}"
								 />
							<span style="color: red; float: none;" id="u_realName"
								class="formtips"></span>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>身份证号：
					</td>
					<td>
							<input type="text" class="inp188" name="paramMap.idNo" id="idNo" value="${map.idNo}"/>
							<span style="color: red; float: none;" id="u_idNo"
								class="formtips"></span>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">
						<span style="color: red; float: none;" class="formtips">*</span>手机号码：
					</td>
					<td>
									<input type="text" class="inp188" name="paramMap.cellPhone"
										id="cellPhone" value="${map.cellPhone}"/>
							
									<span style="color: red; float: none;" id="u_cellPhone"
										class="formtips"></span>
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				<tr>
					<td align="right">&nbsp;
						<input type="hidden" value="${userId}" id="userId"/>
					</td>
					<td style="padding-top: 20px;">
							<input id="jc_btn" value="修改并保存" id="bcbtn" style="background: #666666;color: white;width: auto;" type="button"  />
					</td>
				</tr>
				<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
					<tr height="8px;">
						<td></td>
						<td></td>
					</tr>
				</table>
			</form>
	
		</div>
	</div>
						 </span>
					</div>
				</div>
			</div>
			</div>
	
	
	
		
		<script type="text/javascript" src="script/nav-zh.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script>
  $(function(){
    if('${map.auditStatus}'=='3'){
       $("#province").attr("disabled","true");
       $("#registedPlacePro").attr("disabled","true");
       $("#city").attr("disabled","true");
       $("#registedPlaceCity").attr("disabled","true");
       $("#clickCode").hide();
    }

    //省份改变
	$("#workPro").change(function(){
     var provinceId = $("#workPro").val();
     citySelectInit(provinceId, 2);
    	//$("#area").html("<option value='-1'>--请选择--</option>");
     });

	//城市跟随改变
	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegionAdmin.do",param,function(data){
		for(var i = 0; i < data.length; i ++){
			_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
		}
		$("#workCity").html(_array.join(""));
	});
}
    
  });





</script>
<script>
$(function(){
  var t = '${map.idNo}'==''?'':'${map.idNo}';
  var len = t.length;
  
  if(len==15){
  var tmplen = t.substr(0,2);
  
  $('#idNo1').html(tmplen+'***** **** ****');  
  }else if(len==18){
  var tttttt = t.substr(0,2);
  $('#idNo1').html(tttttt+'**** **** **** ****');  
  }
  
  //alert(t.substr);
  
  var tt = '${map.cellPhone}'==''?'':'${map.cellPhone}';
  var len1 = tt.length;
  var tmplen1;
  var tmplen2;
  if(len1!=0){
  tmplen1 = tt.substr(0,3);
  tmplen2 = tt.substr(7,11);
  $('#cellPhone1').html(tmplen1+' **** '+tmplen2); 
  }

<%--  $("#grtx").hide(); --%>
  
});
</script>
<script type="text/javascript">
//提交基本资料 start-->
$(document).ready(function(){
    $("#BaseDataform :input").blur(function(){
    //验证手机号码
      if($(this).attr("id")=="cellPhone"){
      if( $(this).val() ==""){
         $("#u_cellPhone").attr("class", "formtips onError");
		 $("#u_cellPhone").html("请填写手机号码！");
      }else if((!/^1[3,5,8]\d{9}$/.test($("#cellPhone").val()))){ 
       $("#u_cellPhone").attr("class", "formtips onError");
	     $("#u_cellPhone").html("请正确填写手机号码！");
      }else{
           $("#u_cellPhone").attr("class", "formtips");
	       $("#u_cellPhone").html(""); 
      }
  }
  //真实姓名
  		if($(this).attr("id")=="realName"){  
  		        var isName = new Object();
  		        isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
				if($(this).val() ==""){
			      	$("#u_realName").attr("class", "formtips onError");
			      	$("#u_realName").html("请填写真实姓名！");
			    }else if($(this).val().length <2 || $(this).val().length> 20){   
	            	$("#u_realName").attr("class", "formtips onError");
	                $("#u_realName").html("名字长度为2-20个字符"); 
	            }else if(!isName.test($(this).val())){
	                  $("#u_realName").html("真实姓名输入有误"); 
	            }
	            else{   
	            	$("#u_realName").attr("class", "formtips");
	                $("#u_realName").html(""); 
	            } 
          }
  //========
  //身份号码
  if($(this).attr("id")=="idNo"){
     var isIDCard1 = new Object();
     var isIDCard2 = new Object();
     //身份证正则表达式(15位) 
     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
     //身份证正则表达式(18位) 
     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
    if($(this).val() ==""){
       //$("#u_idNo").attr("class", "formtips onError");
      $("#u_idNo").html("请填写身份证号码");
    }else if(isIDCard1.test($(this).val())||isIDCard2.test($(this).val())){
           //验证身份证号码的有效性
      var parama = {};
      parama["paramMap.idNo"] = $(this).val();
      $.post("isIDNOAdmin.do",parama,function(data){
       if(data.putStr=="0"){
        $("#u_idNo").html("请填写身份证号码");
      }else if(data.putStr=="1"){
           $("#u_idNo").html("身份证号码不合法!");
           $(this).val("");
      }else if(data.putStr=="2"){
    	  $("#u_idNo").html("身份证已存在!");
      }
      else{
        $("#u_idNo").html("");
      }
    });
    }else {
        $("#u_idNo").html("身份证号码不正确");
    }
  }
  //========== 验证出生年月
      if($(this).attr("id")=="birthday"){
    if($(this).val() !=""){
       $("#u_birthday").html("");
    }
  }
 //================居住电话
        if($(this).attr("id")=="telephone"){
        var mdd =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
    if($(this).val() ==""){
 //   $("#u_telephone").attr("class", "formtips onError");
 //   $("#u_telephone").html("请填写居住电话");
 }else if(!mdd.test($(this).val())){
       $("#u_telephone").attr("class", "formtips onError");
       $("#u_telephone").html("请填写正确的居住电话");
    }else{
    $("#u_telephone").attr("class", "formtips");
       $("#u_telephone").html("");
    }
  }
 //============
     });
     		$("#province").change(function(){
			var provinceId = $("#province").val();
			citySelectInit(provinceId, 2);
		});
			 $("#registedPlacePro").change(function(){
			var provinceId = $("#registedPlacePro").val();
			registedPlaceCity(provinceId, 2);
		});

});
	function registedPlaceCity(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#registedPlaceCity").html(_array.join(""));
		});
	}


	function jump(url) {
		
		alert("请先回答密保问题！");
		window.location.href = url;
}

	function citySelectInit(parentId, regionType){
		var _array = [];
		_array.push("<option value='-1'>--请选择--</option>");
		var param = {};
		param["regionType"] = regionType;
		param["parentId"] = parentId;
		$.post("ajaxqueryRegion.do",param,function(data){
			for(var i = 0; i < data.length; i ++){
				_array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
			}
			$("#city").html(_array.join(""));
		});
	}
	
	
	//提交基础资料
	  $("#jc_btn").click(function(){
	     var tsex = '${map.sex}'==''?'':'${map.sex}';
	     if($("#realName").val()==""){
	    // $("#u_realName").html("请填写真实姓名！");
	     alert("请填写真实姓名！");
	     return false 
	     }else if($("#u_realName").html()!=""&&$("#u_realName").html()!=null){
				alert($("#u_realName").html());
				return false;
			  }
	     if($("#idNo").val()==""){
	    // $("#u_idNo").html("请填写身份号码！");
	     alert("请填写身份号码！");
	     return false
	      }else if($("#u_idNo").html()!=""&&$("#u_idNo").html()!=null){
				alert($("#u_idNo").html());
				return false;
			  }
	     if($("#cellPhone").val()==""){
	     //$("#u_cellPhone").html("请填写手机号码！");
	      alert("请填写手机号码！");
	     return false 
	     }
	    
		  
	   $("#jc_btn").attr("disabled",true);
	   var param = {};
	    var userId = $("#userId").val();
	    param["paramMap.id"] = userId;
	    param["paramMap.realName"]=$("#realName").val();
	    param["paramMap.idNo"]=$("#idNo").val();
	    param["paramMap.cellPhone"]=$("#cellPhone").val();
	    
	    param["paramMap.num"]="1";
	    
	    $.post("updatepersoninfo.do",param,function(data){
	       if(data.msg=="保存成功"){
	            alert("保存成功"); 
	            window.location.href='persionData.do?id='+userId;
	       }else{
	    	   if(data.msg=="保存成功2"){
	    		   alert("保存成功"); 
	    		   //window.clearInterval(tipId);
	    		   //$("#clickCode5").html("点击获取验证码");
	    		  // window.location.reload();
	    		   window.location.href='persionData.do?id='+userId;
		   }
		     $("#jc_btn").attr("disabled",false);
	         //alert("请正确填写基本资料");
	         if(data.msg=="请正确填写真实名字"){
	         //$("#u_realName").html("请填写真实姓名！")
	         alert("请填写真实姓名！");
	       }
	            if(data.msg=="真实姓名的长度为不小于2和大于20"){
	            //$("#u_realName").html("真实姓名的长度为不小于2和大于20！")
	             alert("真实姓名的长度为不小于2和大于20！");
	       }
	       if(data.msg=="请正确身份证号码"){
	        // $("#u_idNo").html("请正确身份证号码！")
	         alert("请正确输入你的身份证号码");
	       }
	        if(data.msg=="身份证已注册")
	         {
	            	alert("身份证已注册!");
	        }
	        if(data.msg=="请正确填写手机号码"){
	           // $("#u_cellPhone").html("请填写手机号码！");
	              alert("请填写手机号码!");
	           
	       }
	       if(data.msg=="手机号码长度不对"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码长度不对！");
	
	       }
	       
	       if(data.msg=="手机已存在"){
	       
	        alert("该手机号码已经被注册过了！");
	        
	       }
	      
	      }
		      
	       if(data.msg=="请正确填写手机号码"){
	         alert("手机验证码填写错误！");
	       }
	        
	       if(data.msg=="身份证不合法"){
	        alert("你输入的身份证号码不合法,请重新输入");
	        $("#u_idNo").html("请输入正确身份证号码！");
	    
	       }
	       if(data.msg=="身份证已被注册"){
	        alert("你输入的身份证号码身份证已被注册,请重新输入");
	        $("#u_idNo").html("请重新输入身份证号码！");
	      
	       }
	    });
	    
	});
</script>
	</body>
</html>
