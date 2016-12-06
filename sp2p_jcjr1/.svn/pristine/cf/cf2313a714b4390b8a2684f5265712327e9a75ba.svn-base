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
         <%@include file="/include/left.jsp" %>
		<div class="lne_centr s_centr">
				<div class="myhome_tit tab_meun">
						<ul>
							 
							<li class="on">
								认证信息
							</li>
						</ul>
					</div>
					<div class="myhome_content tab_content clearfix">
					<div class="s_changepw">
				 
              	    <div class="mmxg clearfix">
                   	   <h5>实名认证信息</h5>
                       <ul class="clearfix">
                           <li><span>真实姓名：</span><input type="text" class="test" id="realName"/><strong class="notice">*输入您的真实姓名</strong></li>
                           <li><span>身份证号码：</span><input type="text" class="test" id="idNo"/><strong class="notice">*输入您的身份证号码</strong></li>
                           <li><span>&nbsp;</span>
                           <span id = "myhome_btn_id">
                           		<input type="button"  value="提 交 " class="myhome_btn" onclick="shezhi();"/>
                           </span>
                           <span id="myhome_btn_wait" style="display: none">
                                <input type="button" value="请等待..." class="myhome_btn"/>
                           </span>
                            <strong class="notice">请仔细审核您信息，提交后将不可修改</strong>
                           </li>
                       </ul>
                   </div>
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
		<script>
	 
</script>
<script type="text/javascript">
  function shezhi(){
	                 $('#myhome_btn_id').hide();
	                 $('#myhome_btn_wait').show();
				     var realname = $('#realName').val();
				     var idNo = $('#idNo').val();
				     var isIDCard1 = new Object();
				     var isIDCard2 = new Object();
		  		     var isName = new Object();
		  		        isName = /^[a-zA-Z\u4e00-\u9fa5]+$/;
						if(realname ==""){
							alert("请填写真实姓名");
							$('#myhome_btn_id').show();
			                $('#myhome_btn_wait').hide();
							return;
					    }else if(realname.length <2 || realname.length> 20){   
					    	alert("名字长度为2-20个字符");
					    	$('#myhome_btn_id').show();
			                $('#myhome_btn_wait').hide();
					    	return;
			            }else if(!isName.test(realname)){
			            	alert("真实姓名输入有误");
			            	$('#myhome_btn_id').show();
			                $('#myhome_btn_wait').hide();
			            	return;
			            }
				  
				     //身份证正则表达式(15位) 
				     isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
				     //身份证正则表达式(18位) 
				     isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}(x|X))$/; 
				    if(idNo ==""){
						alert("身份证不能为空");
						$('#myhome_btn_id').show();
		                $('#myhome_btn_wait').hide();
						return;
				    }
				    if(isIDCard1.test(idNo)||isIDCard2.test(idNo)){
				           //验证身份证号码的有效性
				     var parama = {};
				     parama["paramMap.realName"] = realname;
				     parama["paramMap.idNo"] = $('#idNo').val();
					$.post("isIDNO.do",parama,function(data){
					       if(data.putStr=="0"){
					        alert("请填写身份证号码");
					        $('#myhome_btn_id').show();
				            $('#myhome_btn_wait').hide();
					        return;
					      }else if(data.putStr=="1"){
					    	  alert("身份证号码不合法!");
					    	  $('#myhome_btn_id').show();
				              $('#myhome_btn_wait').hide();
					    	  return;
					      }else if(data.putStr=="2"){
					    	  alert("身份证号码已存在!");
					    	  $('#myhome_btn_id').show();
				              $('#myhome_btn_wait').hide();
					    	  return;
					      }else{
						      $.post("updateUserBaseDataOne.do",parama,function(data){
						    	  if(data.msg == "设置成功"){
						    		    $('#myhome_btn_id').show();
						                $('#myhome_btn_wait').hide();
										alert(data.msg);
										window.location.href="owerInformationInit1.do";
									}else if(data.msg == "0" || data.msg == "-1"){
										alert(data.ret_msg);
										$('#myhome_btn_id').show();
							            $('#myhome_btn_wait').hide();
									}else {
										alert(data.msg);
										$('#myhome_btn_id').show();
							            $('#myhome_btn_wait').hide();
									}
						      });
						  }
					    });
				    }else{
						alert("身份证信息不合法");
						$('#myhome_btn_id').show();
		                $('#myhome_btn_wait').hide();
					}
				 
			}
</script>
</body>
</html>
