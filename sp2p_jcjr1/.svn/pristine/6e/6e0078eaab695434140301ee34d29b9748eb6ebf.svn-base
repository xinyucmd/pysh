<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>${sitemap.siteName}-手机注册</title>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="css/css.css" rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
    <link href="../script/pagination.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <jsp:include page="/include/common.jsp"></jsp:include>
   
</head>
<style>
.close_btn{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; left:600px; cursor:pointer; top:10px; border:none;}
.close_btn1{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; left:600px; cursor:pointer; top:10px; border:none;}
.s_login_txt{ width:150px; text-align:right; display:inline-block; font-size:12px; margin-right:10px;}
.input_a{ width:160px; height:26px;border:none; border:solid 1px #c7c7c7; }
.input_b{ padding:0 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 1px #c7c7c7;; margin-left:10px;}
.input_c{ padding:2px 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 2px #c7c7c7;; margin-left:10px; cursor:pointer;}
.input_d{ padding:0 10px; background:#c7c7c7; border:none; line-height:26px; border-radius:3px;  margin-left:5px; cursor:pointer;}
.zh_table th {background-color: #f9f9f9;border: 1px solid #ddd;font-weight: normal;text-align: center; width:14%;}
.zh_table td {border: 1px solid #ddd;text-align: center; width:14%;}
#ul_input li{ line-height:40px;}
#ul_div_check li{ line-height:30px;}
</style>
<body>
<!-- 引用头部公共部分 -->


<!--内页主体 开始-->
<form>
<div style="padding:15px;">
	<div>
        <div>
        	 <div>
        	     <ul>
        	       <li>
        	          <div style="height: "></div>          
        	      </li>
        	      <li>
        	                     用户名： <input type="text" name="parma.username" id="usernameFind" class="input_a"/>
        	                     手机号：<input type="text" name="parma.cellphone" id="cellphoneFind" class="input_a"/>
        	               <input type="button" id="queryAminUserBtn" value="搜索" class="input_c"/>
                           <input type="button" value="注册" class="theme-login" style="padding:2px 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 2px #c7c7c7;; margin-left:10px; cursor:pointer;"/>
        	      </li>
        	      <li>
        	       <table width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-top:10px;" class="zh_table">
					 <tr >
						<th>编号</th>
						<th>手机号</th>
						<th style="width:19%;">用户名称</th>
						<th>最后登陆时间</th>
						<th>创建时间</th>
						<th>操作</th>
						<!-- <th>操作</th>  -->
					 </tr>	
				   </table>
				   <table id="tab_admin_user_content" width="100%" cellspacing="0" cellpadding="0" border="0" class="zh_table" style="margin:0;"></table>
        	       </li>
        	       <li>
        	           <div id="Pagination" class="paging" style="margin:20px 0 0 500px; overflow:hidden;"></div> <br />
        	       </li>
        	      </ul>
             	  <ul id="ul_input" style="position:absolute; display:none; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:20%; top:50px;">
                 	
                 	<li>
                    <span class="s_login_txt" style="color: red">*</span>
                    <span  style="color: red;font-size: 12px"class="formtips">注册营销账户</span>
                    </li>
                 	
                    <li>
                    <span class="s_login_txt">用户名：</span>
                    <input name="paramMap.userName"  id="userName" type="text" class="input_a"/>
                    <span  style="color: red" id="s_userName" class="formtips"></span>
                    </li>
                    
                    <li>
                    <span class="s_login_txt">密码：</span>
                    <input name="paramMap.password" id="password" type="password" class="input_a" />
                    <span  style="color: red" id="s_password" class="formtips"></span>
                    </li>
                    
                    <li>
                       <span class="s_login_txt">确认密码：</span>
                       <input name="paramMap.confirmPassword" id="confirmPassword" type="password"  class="input_a"/>
                       <span  style="color: red;" id="s_confirmPassword" class="formtips"></span>
                    </li>
                    
                    <li>
                 	    <span class="s_login_txt">手机号：</span>
                 	    <input type="text" name="paramMap.cellphone" id="cellphone" class="input_a"/>
                 	    <span style="color: red" id="s_cellphone" class="formtips"></span>
                 	</li>
                 	 
                    <li>
                       <span class="s_login_txt">验证码：</span>
                       <input type="text" name="paramMap.code" id="code" class="input_a"/> 
                       <img src="${sitemap.adminUrl}/imageCode.do?pageId=cellphone" title="点击更换验证码" 
                       style="cursor: pointer;" id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
					   <span  style="color: red" id="pic_code" class="formtips"></span>
					</li>
                    <!--  
                    <li>
                       <span class="s_login_txt">用户类型：</span>
                       <select id="isFlag">
                          <option value="0">普通用户</option>
                          <option value="1">高级用户</option>
                       </select>
                       <span style="color: red;font-size: 12px" id="" class="formtips onError">标注：普通用户只能发布单个债权，高级用户可以发布债权包</span>
                    </li>
                 	  
                 	<li>
                 	   <span class="s_login_txt">短信校验码：</span>
                 	   <input name="paramMap.cellcode" id="cellcode" type="text" class="input_a"/>
                 	   <input type="button" value="点击发送手机校验码"  onclick="sendPhoneMsg()" class="input_d"/>
                 	   <span style="color: red" id="s_cellcode" class="formtips"></span>
                 	</li>
                 	-->
                     
                	<li>
                	<span class="s_login_txt">&nbsp;</span>
                	<input id="btn_cellregs" type="button" value="   注      册    "  onclick="regUerYx_Buton()" class="input_c"/>
                	</li>
                    <input type="button" class="close_btn" value="" />
                 </ul>
            </div>
            <div id="ul_div_check" style="position:absolute; display:none; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:20%; top:50px;"> 
                 <ul>
                 
                    <li>
                    <span class="s_login_txt" style="color: red">*</span>
                    <span  style="color: red;font-size: 12px"class="formtips">更改营销账户手机号</span>
                    </li>
                    <li>
                    <span class="s_login_txt">手机号：</span>
	                    <input id="hiddenTellId" type="hidden" class="input_a"/>
	                    <input id="hiddenUserId" type="hidden" class="input_a"/>
	                    <input id="tell" type="text" class="input_a"/>
	                    <input type="button" value="提 交 "  onclick="chanageTell_Buton()" class="input_c"/>
                    </li>
                	<li>
                	<span class="s_login_txt">&nbsp;</span>
                	  <span  style="color: red" id="tell_check" class="formtips"></span>
                	</li>
                 </ul>
                  <input type="button" class="close_btn1" value="" />
               </div>  
        </div>
    </div>
</div>
</form>
<!--内页主体 结束-->

<!-- 引用底部公共部分 -->     
  <script src="/script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="script/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="/script/jbox/jquery.jBox-2.3.min.js"></script>
  <script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
  <script type="text/javascript" src="../script/jquery.pagination.js"></script>
 <script>
jQuery(document).ready(function($) {
	$('.theme-login').click(function(){
		$('#ul_input').slideDown(200);
	}) 
	$('.close_btn').click(function(){
		$('#ul_input').slideUp(200);
	})
})
</script>
 <script>
jQuery(document).ready(function($) {
	$('.theme-login1').click(function(){
		$('#ul_div_check').slideDown(200);
	}) 
	$('.close_btn1').click(function(){
		$('#ul_div_check').slideUp(200);
	})
})
</script>
  <script>
  //弹出隐私条款和使用条款
 function fff(){
		   jQuery.jBox.open("post:querytips.do", "使用条款", 600,400,{ buttons: { } });
 }
 function ffff(){
	ClosePop();
 }
</script>
<script>
 function ffftip(){
	 jQuery.jBox.open("post:querytip.do", "隐私条款", 600,400,{ buttons: { } });
		    }
</script>

<script>
 $(function(){
   $("#ul_input").find("input").blur(function(){ 
   //手机号码
   if($(this).attr("id")=="cellphone"){
   if((!/^1[3,5,8]\d{9}$/.test($("#cellphone").val()))){
         $("#s_cellphone").attr("class", "formtips onError");
		 $("#s_cellphone").html("手机号码格式错误"); 
		 $(this).val("");
   }else{
         $("#s_cellphone").attr("class", "formtips");
		 $("#s_cellphone").html(""); 
   }
  }
   
   //图片验证码
   if($(this).attr("id")=="code"){
   if($(this).val()==""){
     	 $("#pic_code").attr("class", "formtips onError");
		 $("#pic_code").html("请填写图片验证码"); 
   }else{
        $("#pic_code").attr("class", "formtips");
		$("#pic_code").html(""); 
   }
   }
   
   //用户名
   if($(this).attr("id")=="userName"){
   if($(this).val()==""){
     	 $("#s_userName").attr("class", "formtips onError");
		 $("#s_userName").html("请填写用户名"); 
   }else{
        $("#s_userName").attr("class", "formtips");
		$("#s_userName").html(""); 
   }
   }
   
 //密码
   if($(this).attr("id")=="password"){
   if($(this).val()==""){
     	 $("#s_password").attr("class", "formtips onError");
		 $("#s_password").html("请输入密码"); 
   }else{
        $("#s_password").attr("class", "formtips");
		$("#s_password").html(""); 
   }
   }
   //确认密码
   if($(this).attr("id")=="confirmPassword"){
   if($(this).val()==""){
     	 $("#s_confirmPassword").attr("class", "formtips onError");
		 $("#s_confirmPassword").html("请输入确认密码"); 
   }else{
        $("#s_confirmPassword").attr("class", "formtips");
		$("#s_confirmPassword").html(""); 
   }
   }
 
 });  
});
  </script>
  <script>
  		//初始化验证码
		function switchCode(){
			var timenow = new Date();
			$("#codeNum").attr("src","${sitemap.adminUrl}/imageCode.do?pageId=cellphone&d="+timenow);
		}
  </script>
  <script>
  var falg = true;
 
  function sendPhoneMsg(){
   var errornum=$("form .onError").length;
              if(falg){
              falg = false;
               if($("#cellphone").val()==""){
                 $("#s_cellphone").html("手机号码格式错误");
                 falg = true;
                 return;
               }
              }
              var param = {};
              param["paramMap.cellphone"]= $("#cellphone").val();
              param["paramMap.pageId"] = "cellphone";
              param["paramMap.code"]= $("#code").val();
              $.post("adminUsercellPhoneregsinit.do",param,function(da){//校验
                  if(da==1){
   	                    var phone = $("#cellphone").val();
                        var code = $("#code").val();// 
                        $.post("adminUderphoneCheck.do","phone="+phone+"&code="+code,function(datas){//加密
                       	    if(datas.ret != 1){
                       	        return;
                       	    }
                       	    phone = datas.phone;
                       	    var test={};
                       	    test["paramMap.phone"] = phone;
                       	    test["paramMap.pageId"] = "cellphone";//
                       	            $.post("adminUsersendSMSNew.do",test,function(data){//发短信
                       	                if(data==1){
                       	                	 alert('已将手机验证码发送到您的手机...');
                       	                }else{
                       	                   alert("手机验证码发送失败");
                       	                }

                       	            });

                       	});
                  }else if(da==0){
               	       $("#s_cellphone").html("手机号已存在!");
                  }
                  });
           
  }
  </script>
  <script>
  //注册按钮;
   function regUerYx_Buton(){
	   var states = "1";
	   if($("#cellphone").val()==""){
	         $("#s_cellphone").attr("class", "formtips onError");
			 $("#s_cellphone").html("请输入手机号码"); 
			 states = "0";
	   }else{
	         $("#s_cellphone").attr("class", "formtips");
			 $("#s_cellphone").html(""); 
	   }
	   if($("#code").val()==""){
	     	 $("#pic_code").attr("class", "formtips onError");
			 $("#pic_code").html("请填写图片验证码"); 
			 states = "0";
	   }else{
	        $("#pic_code").attr("class", "formtips");
			$("#pic_code").html(""); 
	   }
	   if($("#userName").val()==""){
	     	 $("#s_userName").attr("class", "formtips onError");
			 $("#s_userName").html("请填写用户名"); 
			 states = "0";
	   }else{
	        $("#s_pic_code").attr("class", "formtips");
			$("#s_pic_code").html(""); 
	   }
	   if($("#password").val()==""){
	     	 $("#s_password").attr("class", "formtips onError");
			 $("#s_password").html("请输入密码"); 
			 states = "0";
	   }else if($("#password")[0].value.length<6 || $("#password")[0].value.length>20){
		     $("#s_password").attr("class", "formtips onError");
			 $("#s_password").html("密码长度6到20个字符"); 
			 states = "0";
	   }else{
		    $("#s_password").attr("class", "formtips");
			$("#s_password").html(""); 
	   }
	   if( $("#confirmPassword").val()==""){
	     	 $("#s_confirmPassword").attr("class", "formtips onError");
			 $("#s_confirmPassword").html("请输入确认密码"); 
			 states = "0";
	   }else{
	        $("#s_confirmPassword").attr("class", "formtips");
			$("#s_confirmPassword").html(""); 
	   }
	    
	   if( states == "0"){
		   return;
	   }
	   
	   var param = {};
       param["paramMap.cellphone"]= $("#cellphone").val();//手机号码
       param["paramMap.code"] = $("#code").val();//图片验证码
       param["paramMap.userName"] = $("#userName").val();//用户名
       param["paramMap.password"] = $("#password").val();//密码
       param["paramMap.confirmPassword"] = $("#confirmPassword").val();//确认密码
       param["isFlag"] = $("#isFlag").val();//是否为超级用户
      
       $.post("regYxUser.do",param,function(data){
         if(data.mailAddress=='1'){
         
            $("#s_confirmPassword").html("两次输入的密码不一致"); 
          
         }
         
         else if(data.mailAddress=='2'){
        	 
			 $("#s_userName").html("用户名已存在"); 
             
          }
         
         else if(data.mailAddress=='14'){
          
            $("#s_password").html("请设置您的密码"); 
            
         }
         else if(data.mailAddress=='15'){
            
           $("#s_confirmPassword").html("请再次输入密码确认"); 
         
         }
         else if(data.mailAddress=='18'){
           
          $("#s_userName").html("用户名长度为2-20个字符"); 
          
         }
         else if(data.mailAddress=='20'){
         $("#s_userName").html("用户名不能含有特殊字符"); 
           
         }
          else if(data.mailAddress=='21'){
          $("#s_userName").html("用户名第一个字符不能是下划线"); 
         }else if(data.mailAddress=='手机已存在'){
           $("#s_cellphone").html("该手机号码已经注册了"); 
           $.post("removecellCode.do","",function(data){});//删除手机验证码  
         }
         else if(data.mailAddress=='请填写手机校验码'){
           $("#s_cellcode").html("请填写手机校验码");
            
         }
         else if(data.mailAddress=='请发送手机校验码'){
             $("#s_cellcode").html("请发送手机校验码");
              
           }
         else if(data.mailAddress=='请输入正确的手机校验码'){
             $("#s_cellcode").html("请输入正确的手机校验码");
              
           }
         else if(data.mailAddress=='与获取验证码手机号不一致'){
             $("#s_cellcode").html("与获取验证码手机号不一致");
              
           }
         else if(data.mailAddress=='图片验证码输入错误'){
             $("#pic_code").html("图片验证码输入错误");
          }
         else if(data.states=='1'){
        	 alert("您好,已经存在高级用户！");
          }
         else if(data.mailAddress=='16'){
             alert("注册失败");
            }
         else if(data.mailAddress=='注册成功'){
          $.post("adminUserRemoveSessionCode.do","",function(data){
        	  alert("恭喜你!注册成功");
        	  queryAdminUser(pageNum);
        	  $('#ul_input').hide();
          });//删除手机验证码
         }
       });
	  
   }
   //初始化分页变量
   var pageNum = 0; 
   var pageSize = 10; 
   var totalNum = 0;
   
   $('#queryAminUserBtn').click(function(){
	   queryAdminUser(pageNum);
   });
   
   //翻页调用  
   function PageCallback(pageNum, jq) {             
	   queryAdminUser(pageNum);  
   }
   
   queryAdminUser(pageNum);
   function queryAdminUser(pageNum){
	var param={};
	    param["username"] = $('#usernameFind').val();
	    param["cellphone"] = $('#cellphoneFind').val();
	   
	    param["pageNum"] = pageNum+1;
		param["pageSize"] = pageSize;
	    $.ajax({
		    type: "post",
		    url: "queryUserYxPage.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	 
		    	var tr_list_o = $("#tab_admin_user_content");
		    	tr_list_o.empty();
		    	
		    	totalNum = data.totalNum;
		    	$("#Pagination").pagination(totalNum, {  
		    	    callback: PageCallback, 
		    	    maxentries:4,
		    	    prev_text: '上一页',  
		    	    next_text: '下一页',  
		    	    items_per_page: pageSize,  //每页显示条目数
		    	    num_display_entries: 6,//连续分页主体部分分页条目数  
		    	    current_page: pageNum,//当前页索引  
		    	    num_edge_entries: 2//两侧首尾分页条目数  
		    	}); 
		    	
		    	if(data.result != null && data.result != undefined && data.result != ''){
			    	var strHtml = "";
		    		for(var i=0;i<data.result.length;i++){
			    		var o = data.result[i];
			    		strHtml +="<tr>";
			    		strHtml +="<td align='center'>"+(i+1)+"</td>";
	                    strHtml +="<td align='center' >"+o.tell+"</td>";
	                    strHtml +="<td align='center' style='width:19%;'>"+o.username+"</td>";
	                    
	                    if(o.lastDate==null){
	                    	 strHtml +="<td align='center' >"+''+"</td>";
	                    }else{
	                    	strHtml +="<td align='center' >"+o.lastDate+"</td>";
	                    }
	                    
	                    strHtml +="<td align='center'>"+o.createTime+"</td>";
	                     
	                    strHtml +="<td class='f66' align='center' height='36px'>"+'<a href="###" onclick="editeUserTell('+"'"+o.id+"'"+','+"'"+o.bid+"'"+','+"'"+o.tell+"'"+');">编辑</a>'+"</td>";
						strHtml +="</tr>";
			    	}
		    		tr_list_o.append(strHtml);
		    	} 
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
		       alert(errorThrown);
		    }
		}); 
   }
   
   function editeUserTell(userId,bid,tell){
	   $('#hiddenUserId').val(userId);
	   $('#hiddenTellId').val(bid);
	   $('#tell').val(tell)
	   $('#ul_div_check').slideDown(200);
   }
   
   function chanageTell_Buton(){
	   if((!/^1[3,5,8]\d{9}$/.test($("#tell").val()))){
			 $("#tell_check").html("*手机号码格式不正确！"); 
			 return;
	   }else{
		   $("#tell_check").html(""); 
	   }
	   
	   var param = {};
	   param['userId'] = $('#hiddenUserId').val();
	   param['id'] = $('#hiddenTellId').val();
	   param['tell'] = $('#tell').val();
	      $.ajax({
			    type: "post",
			    url: "updateUserYxTell.do",
			    dataType: "json",
			    data:param,
			    success: function (data) {
			       if(data.status==0){
				    	   alert('修改数据异常！');
				   }
			       if(data.status==1){
			    	  alert('修改成功！');
			    	  $('#ul_div_check').slideUp(200);
			    	  queryAdminUser(pageNum);
			       }
			       if(data.status==2){
			    	   $("#tell_check").html("*该手机号码已存在！"); 
			       }
			       if(data.status==3){
			    	   alert('该用户已经是小贷公司用户');
			       }
			       if(data.status==4){
			    	   alert('提交成功');
			    	   queryAdminUser();
			       }
			       if(data.status==0){
			    	   alert('设置失败');
			       }
			       
			       if(data.status==5){
			    	   $("#s_userName_check").html("请填写用户名"); 
			       }else{
			    	   $("#s_userName_check").html(""); 
			       }
			       if(data.status==6){
			    	   $("#s_password_check").html("请填写密码"); 
			       }else{
			    	   $("#s_password_check").html(""); 
			       }
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
			       alert(errorThrown);
			    }
			}); 
		 
	  
   }
   
   

</script>
</body>
</html>
