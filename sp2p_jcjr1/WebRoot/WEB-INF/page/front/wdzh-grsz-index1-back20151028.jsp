<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${sitemap.siteName}</title>
		<jsp:include page="/include/head.jsp"></jsp:include>
        <jsp:include page="/include/common.jsp"></jsp:include> 
		<link href="css/inside.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
		<script language="javascript" type="text/javascript"
			src="My97DatePicker/WdatePicker.js"></script>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
<div class="ne_wdzh"></div>
<div class="lne_cent">
           <!-- 引用我的帐号主页左边栏 -->
         <jsp:include page="/include/left.jsp" ></jsp:include>
		<div class="lne_centr s_centr">
				<div class="myhome_tit tab_meun">
		         	<ul>
		              <li class="on">
								基本信息
							</li>
							
		            </ul>
		         </div>
				

 <div class="myhome_content tab_content clearfix">

	<div >
		<form id="BaseDataform"> 
			<table class="s_basedata">
                    	<tr>
                          
                        <s:if test='#request.flag=="2"'>
                        <th width="120"></th>
                        <td width="320">
					    </td>
					    </s:if>
						<s:else>
						<th width="120">真实姓名：</th>
                        <td width="320">
						  ${map.realName}
						  <input type="hidden" class="test" name="paramMap.realName" id="realName" value="${map.realName}"/>
						</td>
						</s:else>
						
						<s:if test='#request.map.realName==""'>
							<td width="300"></td>
						</s:if>
						<s:else>
							<td width="300"><span class="font-gray">完成</span></td>
						</s:else>
                        </tr>
                        <tr>
                            <s:if test='#request.map.idNo==""'>
                            <th width="120">实名认证：</th>
                            <td width="320">
                              <span class="un-set">未认证</span>
							</td>
							</s:if>
							<s:else>
							<th width="120">身份证号码：</th>
                            <td width="320">
							  <a id='idNo1'></a>
							</td>
							</s:else>
							
                            
                            <td>
                             <s:if test='#request.map.lian_state=="0"'>
                               <span id="rzPerson">
                                  <a href="updateUserBaseDataJbxxrz.do" onclick="re_person()">认证</a>
                               </span>
                               <span id="subPerson" style="display: none">认证</span>
                             </s:if>
                             <s:else>
                               <span class="font-gray">完成</span>
                             </s:else>
                            </td>
                        </tr>
                        <tr>
                        	<th>手机绑定：</th>
                            <td>
                            <s:if test="#request.map.cellPhone!=null && #request.map.cellPhone!= ''">
								<a id="cellPhone1"></a>
									<input type="hidden" class="test" name="paramMap.cellPhone"
										id="cellPhone" value="${map.cellPhone}" />
							</s:if>
							<s:else>
									<s:if test='#request.flag=="2"'>
										<input type="text" class="test" name="paramMap.cellPhone"
											id="cellPhone" />
								
										<span style="color: red; float: none; font-size:12px;" id="u_cellPhone"
											class="formtips"></span>
									</s:if>
									<s:else>
										<a id="cellPhone1"></a>
										<input type="hidden" class="test" name="paramMap.cellPhone"
											id="cellPhone" value="${map.cellPhone}" />
									</s:else>
							</s:else>
							</td>
                            <td><a href="boundcellphone.do">修改</a></td>
                        </tr>
                        <tr>
                        	<th>邮箱绑定：</th>
                            <td>
                            	<span class="un-set">
                            	<s:if test="#request.email==null or #request.email==''">未设置</s:if>
                            	<s:else>${email }</s:else></span>
                            </td>
                            <td><a href="emailManagerInit.do">验证</a></td>
                        </tr>
                        <tr>
                        	<th>登录密码：</th>
                            <td>*********</td>
                            <td><a href="setPwd.do?flag=0">修改</a></td>
                        </tr>
                        <tr>
                        	<th>交易密码：</th>
                            <td>*********</td>
                            <td><a href="setPwd.do">修改</a></td>
                        </tr>
                    </table>
			</form>
	
		</div>
	</div>
					</div>
				</div>
			
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
		<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
		<script type="text/javascript" src="script/nav-zh.js"></script>
		<script type="text/javascript" src="common/date/calendar.js"></script>
		<script type="text/javascript" src="css/popom.js"></script>
		<script type="text/javascript">
            if('${el}'==1){

            alert('实名认证已经通过！');   
            }
        </script>
		
		
		<script>
  $(function(){
	  $(".lne_l3 > ul >li").removeClass("clicked");
		 $('#li_9').addClass('clicked');
    if('${map.auditStatus}'=='3'){
       $("#province").attr("disabled","true");
       $("#registedPlacePro").attr("disabled","true");
       $("#city").attr("disabled","true");
       $("#registedPlaceCity").attr("disabled","true");
       $("#clickCode").hide();
    }
  });



</script>
<script>
$(function(){
	dqzt(4);
	$('#li_5').addClass('on');
  var t = '${map.idNo}'==''?'':'${map.idNo}';
  var len = t.length;
  
  if(len==15){
  var tmplen = t.substr(0,4);
  
  $('#idNo1').html(tmplen+' ******* '+t.substr(t.length-4));  
  }else if(len==18){
  var tttttt = t.substr(0,4);
  $('#idNo1').html(tttttt+' ***** ***** '+t.substr(t.length-4));  
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
		  var timers=180;
		  var tipId;
		  var flags= false;
		   $(function(){
		       //当用户再次输入手机号码时候 add by lw
		       	$("#cellPhone").change( function() {
		       var phone=$("#cellPhone").val();
		        if($("#cellPhone").val()==""){
		        $("#u_cellPhone").attr("class", "formtips onError");
		        $("#u_cellPhone").html("请填写手机号码！");
		        }else if((!/^1[3,4,5,7,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                }else{
                  	  var cellPhone=$("#cellPhone").val();
                   		$.post("cellphoneOnly.do","cellphone="+cellPhone,function(data){
                   			if(data.msg=="手机已存在"){
								 $("#u_cellPhone").attr("class", "formtips onError");
	                        	 $("#u_cellPhone").html("手机号码已存在！");
	                    	}else{
                      			$("#u_cellPhone").attr("class", "formtips");
	                 			$("#u_cellPhone").html(""); 
	                   			if($("#clickCode5").html()!="重新发送验证码"||$("#clickCode").html()!="点击获取验证码") { 
	                     			//当处在发送中的状态时候
	                    			window.clearInterval(tipId);
	                    			flags = false;
	                   				$("#clickCode5").html("点击获取验证码");
		                			$.post("removeCode.do","",function(data){});//删除手机验证码
		                 		}
	         	  			}
	                    });
                     
                   }
        		 });
		  
		       $("#clickCode5").click(function(){
		           //验证手机号码
		            var phone=$("#cellPhone").val();
		           if($("#cellPhone").val()==""){
                      $("#u_cellPhone").attr("class", "formtips onError");
		              $("#u_cellPhone").html("请填写手机号码！");
                   }else if((!/^1[3,4,5,7,8]\d{9}$/.test($("#cellPhone").val()))){ 
                      $("#u_cellPhone").attr("class", "formtips onError");
	                  $("#u_cellPhone").html("请正确填写手机号码！");
                   }else{
                  	  var cellPhone=$("#cellPhone").val();
                   		$.post("cellphoneOnly.do","cellphone="+cellPhone,function(data){
                   			if(data.msg=="手机已存在"){
								 $("#u_cellPhone").attr("class", "formtips onError");
	                        	 $("#u_cellPhone").html("手机号码已存在！");
	                    	}else{
	                    		 $("#u_cellPhone").attr("class", "formtips");
	                  			 $("#u_cellPhone").html(""); 
	                 			if($("#clickCode5").html()=="重新发送验证码"||$("#clickCode5").html()=="点击获取验证码") { 
		                           if(flags) return ;
		                           
		                           var phone = $("#cellPhone").val();
		                           $.post("phoneCheck.do","phone="+phone,function(datas){

		                               if(datas.ret != 1){

		                                   alert(datas.msg);
		                                   return;

		                               }
		                               phone = datas.phone;
		                               var test={};
		                               test["paramMap.phone"] = phone;
		                               $.post("sendSMS.do",test,function(data){

		                                   if(data==1){

		                                       timers=180;
		                                       flags = true;
		                                       tipId=window.setInterval("timer_()",1000);

		                                   }else{
		                                   alert("手机验证码发送失败");
		                                   }

		                               });

		                           });
	                               
	                               
	                        }
	                       }
	                    });
                     
                   }
		       });
		   });
		   //定时
		   function timer_(){
		    
		       if(timers>=0){
		         $("#clickCode5").html("验证码获取："+timers+"秒");
		         timers--;
		       }else{
		          window.clearInterval(tipId);
		           flags = false;
		           $("#clickCode5").html("重新发送验证码");
		           $.post("removeCode.do","",function(data){});
		           
		       }
		   }

		   function shezhi(item){
			   if(item=='realName'){
				     var realname = $('#realName').val();
				     param["paramMap.realName"] = realname;
				     param["paramMap.columName"] = "realName";
		  		        var isName = new Object();
		  		        isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
						if(realname ==""){
							alert("请填写真实姓名");
					    }else if(realname.length <2 || realname.length> 20){   
					    	alert("名字长度为2-20个字符");
			            }else if(!isName.test(realname)){
			            	alert("真实姓名输入有误");
			            }else{
			            	$.post("updateUserBaseDataOne.do",param,function(data){
								if(data.msg == "设置成功"){
									alert(data.msg);
									window.location.href="owerInformationInit1.do";
								}else {
									alert(data.msg);
								}
				            });
				        }
				   //owerInformationInit1.do
				}else if(item=='idNo'){
					var idNo = $('#idNo').val();
				     var isIDCard1 = new Object();
				     var isIDCard2 = new Object();
				     //身份证正则表达式(15位) 
				     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
				     //身份证正则表达式(18位) 
				     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
				    if(idNo ==""){
						alert("身份证不能为空");
				    }else if(isIDCard1.test(idNo)||isIDCard2.test(idNo)){
				           //验证身份证号码的有效性
				     var parama = {};
				     parama["paramMap.idNo"] = $('#idNo').val();
					$.post("isIDNO.do",parama,function(data){
					       if(data.putStr=="0"){
					        alert("请填写身份证号码");
					        return;
					      }else if(data.putStr=="1"){
					    	  alert("身份证号码不合法!");
					    	  return;
					      }else if(data.putStr=="2"){
					    	  alert("身份证号码已存在!");
					    	  return;
					      }else{
					    	  parama = {};
						      parama["paramMap.idNo"] = idNo;
							  parama["paramMap.columName"] = "idNo";
						      $.post("updateUserBaseDataOne.do",parama,function(data){
						    	  if(data.msg == "设置成功"){
										alert(data.msg);
										window.location.href="owerInformationInit1.do";
									}else {
										alert(data.msg);
									}
						    });
						  }
					    });
				    }else{
						alert("身份证信息不合法");
					}
				}
			}
//======
$(document).ready(function(){
    $("#BaseDataform :input").blur(function(){
    //验证手机号码
      if($(this).attr("id")=="cellPhone"){
      if( $(this).val() ==""){
         $("#u_cellPhone").attr("class", "formtips onError");
		 $("#u_cellPhone").html("请填写手机号码！");
      }else if((!/^1[3,4,5,7,8]\d{9}$/.test($("#cellPhone").val()))){ 
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
  if($(this).attr("id")=="idNo111"){
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
      $.post("isIDNO.do",parama,function(data){
       if(data.putStr=="0"){
        $("#u_idNo").html("请填写身份证号码");
      }else if(data.putStr=="1"){
           $("#u_idNo").html("身份证号码不合法!");
      }else if(data.putStr=="2"){
           $("#u_idNo").html("身份证号码已存在!");
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
 //==手机验证码
       if($(this).attr("id")=="vilidataNum"){
    if($(this).val() ==""){
    $("#u_vilidataNum").attr("class", "formtips onError");
    $("#u_vilidataNum").html("请填写手机验证码");
    }else{
    $("#u_vilidataNum").attr("class", "formtips");
       $("#u_vilidataNum").html("");
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
	     }
	     if($("#idNo").val()==""){
	    // $("#u_idNo").html("请填写身份号码！");
	     alert("请填写身份号码！");
	     return false
	      }
	     if($("#cellPhone").val()==""){
	     //$("#u_cellPhone").html("请填写手机号码！");
	      alert("请填写手机号码！");
	     return false 
	     }
	    
		     if($("#vilidataNum").val()==""){ 
		    // $("#u_vilidataNum").html("请填写手机验证码");
		      alert("请填写手机验证码！");
		      return false
		      }
	     
		    
	   if($("#img").attr("src")==""){
	       alert("请上传你的个人头像");
	       return false;
	   }
	   $("#jc_btn").attr("disabled",true);
	   var param = {};
	    param["paramMap.realName"]=$("#realName").val();
	    param["paramMap.idNo"]=$("#idNo").val();
	    param["paramMap.cellPhone"]=$("#cellPhone").val();
	    param["paramMap.vilidataNum"]=$("#vilidataNum").val();
	    
	    
	    //if(tsex==''){
	    //param["paramMap.sex"]=$("input[name='paramMap_sex']:checked").val();
	    //}else{
	    // param["paramMap.sex"]=("input[name='paramMap_sex']:checked").val();
	    //}
	    
	    param["paramMap.sex"]=$("input[name='paramMap_sex']:checked").val();
	    
	    param["paramMap.birthday"]=$("#birthday").val();
	    param["paramMap.highestEdu"]=$("#highestEdu").val();
	    param["paramMap.eduStartDay"]=$("#eduStartDay").val();
	    param["paramMap.school"]=$("#school").val();
	    param["paramMap.maritalStatus"]=$("input[name='paramMap_maritalStatus']:checked").val();
	    param["paramMap.hasChild"]=$("input[name='paramMap_hasChild']:checked").val();
	    param["paramMap.hasHourse"]=$("input[name='paramMap_hasHourse']:checked").val();
	    param["paramMap.hasHousrseLoan"]=$("input[name='paramMap_hasHousrseLoan']:checked").val();
	    param["paramMap.hasCar"]=$("input[name='paramMap_hasCar']:checked").val();
	    param["paramMap.hasCarLoan"]=$("input[name='paramMap_hasCarLoan']:checked").val();
	    param["paramMap.nativePlacePro"]=$("#province").val();
	    param["paramMap.nativePlaceCity"]=$("#city").val();
	   
	    param["paramMap.registedPlacePro"]=$("#registedPlacePro").val();
	    param["paramMap.registedPlaceCity"]=$("#registedPlaceCity").val();
	    
	    param["paramMap.address"]=$("#address").val();
	    param["paramMap.telephone"]=$("#telephone").val();
	    //用户头像路径
	    param["paramMap.personalHead"]=$("#img").attr("src");
	    param["paramMap.iscellPhone"]=$("#iscellPhone").val();
	    param["paramMap.num"]="1";
	    $.post("updateBasedate.do",param,function(data){
	       if(data.msg=="保存成功"){
	            alert("保存成功"); 
	            window.clearInterval(tipId);
	            $("#clickCode5").html("点击获取验证码");
	            window.location.reload();
	       }else{
	    	   if(data.msg=="保存成功2"){
	    		   alert("保存成功"); 
	    		   window.clearInterval(tipId);
	    		   $("#clickCode5").html("点击获取验证码");
	    		   window.location.reload();
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
	       //手机验证码
	       if(data.msg=="与获取验证码手机号不一致"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机号码与获取验证码手机号不一致！");
	       }
	       if(data.msg=="请填写验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请填写验证码");
	       }
	       if(data.msg=="请重新发送验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请重新发送验证码");
	       }
	        if(data.msg=="请输入正确的验证码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请输入正确的验证码");
	       }
	       }
	       if(data.msg=="请正确填写手机号码"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("手机验证码填写错误！");
	       }
	      if(data.msg=="请正确填写性别"){
	        // $("#u_cellPhone").html("手机号码长度不对！")
	         alert("请勾选填写性别！");
	       }
	       if(data.msg=="请正确填写出生日期"){
	         //$("#u_birthday").html("请正确填写出生日期！")
	         alert("请正确填写出生日期！");
	       }
	       if(data.msg=="请正确填写入学年份"){
	        // $("#u_eduStartDay").html("请正确填写入学年份！")
	         alert("请正确填写入学年份！");
	       }
	             if(data.msg=="请正确填写入毕业院校"){
	        // $("#u_school").html("请正确填写入毕业院校！")
	          alert("请正确填写入毕业院校！");
	       }
	             if(data.msg=="请正确填写入学年份"){
	       //  $("#u_eduStartDay").html("请正确填写入学年份！")
	        alert("请正确填写入学年份！");
	       }
	        if(data.msg=="请正确填写最高学历"){
	         //$("#u_highestEdu").html("请正确填写最高学历！")
	          alert("请正确填写最高学历！");
	       }
	         if(data.msg=="请正确填写入籍贯省份"){
	        // $("#u_nativePlacePro").html("请正确填写入籍贯省份！")
	         alert("请正确填写入籍贯省份！");

	       }
	       if(data.msg=="请正确填写入籍贯城市"){
	         //$("#u_nativePlaceCity").html("请正确填写入籍贯城市！")
	            alert("请正确填写入籍贯城市！");

	       }
             if(data.msg=="请正确填写入户口所在地省份"){
	       //  $("#u_registedPlacePro").html("请正确填写入户口所在地省份！")
	          alert("请正确填写入户口所在地省份!");
	       }
	                 if(data.msg=="请正确填写入户口所在地城市"){
	        // $("#u_registedPlaceCity").html("请正确填写入户口所在地城市！")
	           alert("请正确填写入户口所在地城市!");
	       }
	        if(data.msg=="请正确填写入你的家庭电话"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	        alert("请正确填写入你的家庭电话！");
	       }
	       if(data.msg=="出生日期不能大于当前日期"){
		        alert("出生日期不能大于当前日期");
		    }  
	       if(data.msg=="入学年份不能大于当前日期"){
		        alert("入学年份不能大于当前日期");
		    }
	       if(data.msg=="出生日期不能大于入学日期"){
		        alert("出生日期不能大于入学日期");
		    }
	      if(data.msg=="你的家庭电话输入不正确"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	         alert("请正确填写入你的居住电话");
	         $(this).show();
	       }
	      
	      if(data.msg=="你的居住电话输入长度不对"){
	         //$("#u_telephone").html("请正确填写入你的家庭电话！")
	         alert("你的居住电话输入长度不对");
	       
	       }
	       
	    
	           if(data.msg=="请正确上传你的个人头像"){
	         //$("#u_img").html("个人头像不能为空！")
	          alert("个人头像不能为空！");
	        
	       }
	       if(data.msg=="超时请重新登录"){
	          window.location.href='login.do';
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


	
			$(function(){
				//上传图片
				$("#btn_personalHead").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					 window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
					var headImgPath = $("#img").attr("src");
					if(headImgPath!=""){
						//window.location.href="updateHeadImg.do?userHeadImg="+headImgPath;	
						 $("#u_img").html("");
					}else{ 
						alert("上传失败！");	
					}
				});
				
			});
			
			function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
					$("#img").attr("src",path);
					$("#setImg").attr("src",path);
		  		}
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
			//======================工作认证
			
			//===============|~

			


//end
</script>
		
		<script>
		function jumpUrl(obj) {
		window.location.href = obj;
	}
		//工作认证
	function loadWorkInit(url){
		var bb = '${person}';//填写工作信息必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href="owerInformationInit.do";
		} else {
			window.location.href=url;
		}
	}
		//加载该用户提现银行卡信息
	function loadBankInfo(url) {
		var bb = '${person}';//设置提现银行卡必须先填写个人资料
		if (bb == "0") {
			alert("请先完善个人信息");
			window.location.href="owerInformationInit.do";
		} else {
			window.location.href=url;
		}

	}
	
	function bindingPhoneLoad(url) {
			   window.location.href=url;
	}

	function updatePwd(url) {
		window.location.href=url;
		/*var bb = '${isApplyPro}';
		if (bb == 1) { 
			alert("请先设置安全问题");
			window.location.href="queryQuestion.do";
		}else if(bb == 2){
			alert("请先回答安全问题");
			window.location.href="getQuestion.do";
		}*/
	}
</script>

		<script>
	function changeBankInfos(id) {
		var url = "queryOneBankInfo.do?bankId=" + id;
		jQuery.jBox.open("post:" + url, "银行卡变更", 600, 400, {
			buttons : {}
		});
	}
	
	function re_person(){
		$('#rzPerson').hide();
		$('#subPerson').show();
	}
</script>
	</body>
</html>
