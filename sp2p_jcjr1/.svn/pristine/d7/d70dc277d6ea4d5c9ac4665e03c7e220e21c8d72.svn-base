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
<style type="text/css">
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
</style>
</head>
<body> 
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<!--我的微信贷主要内容-->
<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
        <jsp:include page="/include/left.jsp" ></jsp:include>
        <div class="lne_centr">
           <!--还款情况--> 
            <div class="lne-centr-con">
                <div class="lne-centr-con-content">
                	<div style="padding:0 20px;">
                    	<p class="blue-title"><i>微信贷温馨提示：</i>我们会保管您的资料安全不被泄露，请你放心填写。</p>
                        	<div class="tb_list clearfix">
                                <form>
                                    <div class="form-group">
                                      <label >真实姓名</label>
                                      <input type="text" class="form_s"  placeholder="请输入真实姓名" id="realName"/>
                                    </div>
                                     <div class="form-group">
                                      <label >身份证号</label>
                                       <input type="text" class="form_s"  placeholder="请输入身份证号" id="idNo"/>
                                    </div>
                                    <span id = "myhome_btn_id">
                           		       <input type="button"  value="提 交 " class="btn btn-warning btn-lg" style="margin-left:200px;" onclick="shezhi();"/>
                                   </span>
                                   <span id="myhome_btn_wait" style="display: none">
                                      <input type="button" value="请等待..." class="btn btn-warning btn-lg" style="margin-left:200px;"/>
                                   </span> 请仔细核查你信息,提交后不可修改 
                      		</form>
						</div>
                        </div>
                </div>    
            </div>
        </div>
    </div>
</div>
<!--我的微信贷主要内容-->
    <!--返回顶部-->
    <!--footer-->
 				
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
 