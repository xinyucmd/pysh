<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>小贷公司列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>
<link href="../../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<link href="../script/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../script/jquery.pagination.js"></script>
<script type="text/javascript" language="javascript">
//初始化
$(function(){
	
	     if($('#loan_logo_path').val() == ''){
		     $("#loan_logo_path_img").attr("src","../images/NoImg.GIF");
	     }else{
		     $('#loan_logo_path_img').attr('src',"../"+$('#loan_logo_path').val());
	     }
	     
	    		  
	    if($('#service_letter_path').val() == ''){
			$("#service_letter_path_img").attr("src","../images/NoImg.GIF");
		}else{
			$('#service_letter_path_img').attr('src',"../"+$('#service_letter_path').val());
		}
				  
		if($('#bonding_letter_path').val() == ''){
			$("#bonding_letter_path_img").attr("src","../images/NoImg.GIF");
		}else{
			$('#bonding_letter_path_img').attr('src',"../"+$('#bonding_letter_path').val());
		}
});


//上传图片日期路径 例如：20141212
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

//修改小贷公司logo
function loan_logo_path_update_a(){
    var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'loanLogo/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadLoanLogoCallUpdate','cp':'loan_logo_path_update_img'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#loan_logo_path_update_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//修改小贷公司logo回调    	
function uploadLoanLogoCallUpdate(basepath,fileName,cp){
	if(cp == "loan_logo_path_update_img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#loan_logo_path_update_img").attr("src","../"+path);
		$("#loan_logo_path_update").val(path);
	}
}




//小贷公司logo
function loan_logo_path_a(){
    var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'loanLogo/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadLoanLogoCall','cp':'loan_logo_path_img'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#loan_logo_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//小贷公司logo回调    	
function uploadLoanLogoCall(basepath,fileName,cp){
	if(cp == "loan_logo_path_img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#loan_logo_path_img").attr("src","../"+path);
		$("#loan_logo_path").val(path);
	}
}


//小贷公司 授信函图片
function service_letter_path_a(){
    var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'loanServiceLetter/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadLoanServiceLetterCall','cp':'service_letter_path_img'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#service_letter_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//小贷公司授信函图片图片路径回调	    	
function uploadLoanServiceLetterCall(basepath,fileName,cp){
	if(cp == "service_letter_path_img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#service_letter_path_img").attr("src","../"+path);
		$("#service_letter_path").val(path);
	}
}

//    修改小贷公司 授信函图片
function service_letter_path_a_u(){
    var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'loanServiceLetter/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadLoanServiceLetterCall_u','cp':'service_letter_path_img_u'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#service_letter_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//    修改小贷公司授信函图片图片路径回调	    	
function uploadLoanServiceLetterCall_u(basepath,fileName,cp){
	if(cp == "service_letter_path_img_u"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#service_letter_path_img_u").attr("src","../"+path);
		$("#service_letter_path_u").val(path);
	}
}

//担保机构 担保函图片
function bonding_letter_path_a(){
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'bondingLetter/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadBondingLetterCall','cp':'bonding_letter_path_img'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#bonding_letter_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//担保机构担保函图片路径回调	    	
function uploadBondingLetterCall(basepath,fileName,cp){
	if(cp == "bonding_letter_path_img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#bonding_letter_path_img").attr("src","../"+path);
		$("#bonding_letter_path").val(path);
	}
}

//     修改担保机构 担保函图片
function bonding_letter_path_a_db(){
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'bondingLetter/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadBondingLetterCall_db','cp':'bonding_letter_path_img_db'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#bonding_letter_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//      修改担保机构担保函图片路径回调	    	
function uploadBondingLetterCall_db(basepath,fileName,cp){
	if(cp == "bonding_letter_path_img_db"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#bonding_letter_path_img_db").attr("src","../"+path);
		$("#bonding_letter_path_db").val(path);
	}
}



jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#div_add_company_loan').slideUp(200);
	})

})
jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#div_add_company_bonding').slideUp(200);
	})

})
jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#div_add_company_loan_bonding').slideUp(200);
	})

})
jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#loanCompanyAdmin').slideUp(200);
	})

})
jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#theme-popover3').slideUp(200);
	})

})
jQuery(document).ready(function($) {
	$('.close_btn1').click(function(){
		$('#theme-popover4').slideUp(200);
	})

})
jQuery(document).ready(function($) {
	$('.close_btn').click(function(){
		$('#adminUserDiv').slideUp(200);
	})

})
</script>
<script>
jQuery(document).ready(function($) {
	$('.theme-login').click(function(){
		$('#theme-popover').slideDown(200);
	}) 
	$('.theme-login1').click(function(){
		$('#theme-popover2').slideDown(200);
	}) 
})
</script>

<style>
ul li{ list-style:none;}
.close_btn{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; right:10px; cursor:pointer;}
.close_btn1{ width:27px; height:27px; background:url("/images/recommend/close.jpg") no-repeat; position:absolute; right:10px; cursor:pointer;}
.con1{ width:600px; overflow:hidden;}
.con1 ul li{ line-height:50px;display:inline-block; overflow:hidden; width:600px;}
.con1 ul li label{ width:120px; text-align:right; display:inline-block; font-size:12px; margin-right:10px; float:left;}
.input_a{ width:160px; height:26px;border:none; border:solid 1px #c7c7c7; float:left; }
.input_b{ padding:0 10px; background:#e5f3fa; border:none; line-height:20px; border-radius:3px; border:solid 1px #c7c7c7;; margin-left:10px;}
.zh_table td{ border:solid 1px #c7c7c7; background:#fff;}
.f99 {color: #666;font-family: "tahoma";font-size: 12px;line-height:36px;}
.user_table tr{ height:40px;}
</style>
</head>
	 <body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						  <td width="100" height="28"  class="main_alll_h2">
								<a href="javascript:void(0);">小贷公司列表</a>
								<div id="testDiv"></div>
							</td>
							<td width="2">&nbsp;
								
							</td>
							<td>&nbsp;
								
							</td>
						</tr>
					</table>
				</div>
				<div style="padding:15px; width:1200px;  overflow:hidden;  background:#f1f1f1; margin-top:10px;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tbody>
							<tr>
								<td>
								<input type="button" onclick="addCompanyLoan();" value="添加合作机构"       style=" padding:0 10px; height:30px; background:#e5f3fa; border-radius:5px; text-align:center; cursor:pointer;"/>
								<input  type="button" onclick="addLoanCompanyAdmin()"  value="添加管理员 " style=" padding:0 10px; height:30px; background:#e5f3fa; border-radius:5px; text-align:center; cursor:pointer;"/>
								<input type="button" id="btn_bonding_add" value="添加担保机构" onclick="addBondingCompanyBtn()" style=" padding:0 10px; height:30px; background:#e5f3fa; border-radius:5px; text-align:center; cursor:pointer;"/>
								</td>
							</tr>
						
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									合作机构名称：
									<input id="loanCompanyName" value="" class="inp80" />&nbsp;&nbsp;
									  
									<input  id="search" type="button" value="查找" name="search" onclick="searchLoanData()"/>
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList" style="overflow:hidden;">
                     <div>
		             <table id="tab_content" style=" color: #333333;width:100%; border-collapse: collapse;" cellspacing="0" cellpadding="0" bgcolor="#fff" border="0" class="zh_table">
			    
				     </table>
						<!--<table  id="" style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" >
						</table>-->
                        </div>
				        <div id="Pagination" class="paging" style="margin-top:20px; position:relative;"></div>
                       <!--  
                       <div style="margin-top:20px;"><a style="position:relative; left:550px; cursor:pointer; padding:6px 10px; font-weight:bold; font-size:12px; color:#333; background:#e5f3fa; border-radius:5px; border:solid 1px #c7c7c7; font-size:12px;" class="theme-login">下一步</a></div>
		               -->
		               </span>
				</div> 
	          <div id="div_add_company_loan" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">
              <div class="close_btn"></div>
			   <form>
				<div class="con1">
                <ul>
                <li><label>合作机构名称:</label><input id="loan_name" value="" class="input_a"/>
                <span  style="color: red" id="loan_company_name" class="formtips"></span>
                </li>
                <li>
                <label>信用评级:</label>
                <select id="level">
                    <option value="1">一级</option>
                    <option value="2">二级</option>
                    <option value="3">三级</option>
                    <option value="4">四级</option>
                    <option value="5">五级</option>
                </select>
                </li>
                <li><label>合作机构Logo:</label>
				 <input type="hidden" id="loan_logo_path" value="" />
				 <img id="loan_logo_path_img" src=""  width="62" height="62" style="position:relative; top:15px;"/> 
				 <a href="###" id="" class="scbtn"  onclick="loan_logo_path_a()" style="font-size:12px;">上传图片</a>
				 <span  style="color: red" id="loan_company_logo_path" class="formtips"></span>
				</li>
                
                <li>
                <label>授信总额:</label>
                <input id="total_amount" value="" class="input_a" onblur="setValueAvailableTotalAmount()"/>
                <span  style="color: red;font-size:12px; " id="total_amount_span" class="formtips"></span>   
                </li>
                
				<li>
				<label>授信余额:</label>
				<input id="available_total_amount" value="" class="input_a" readonly="readonly"/>
				<span  style="color: red; " id="available_total_amount_span" class="formtips"></span> 
				</li>
				
                <li>
                <label>偿债准备金比例:</label>
                <input id="insolvency_reserves_scale" value="" class="input_a"/>%
                <span  style="color: red" id="insolvency_reserves_scale_span" class="formtips"></span> 
                </li>
                
                <li>
                <label>渠道费比例:</label>
                <input id="channel_cost" value="" class="input_a"/>%
                <span  style="color: red" id="channel_cost_span" class="formtips"></span> 
                </li>
                
				<li>
				<label style="width:130px;">与微信贷合作开始日期:</label>
				<input id="start_time" class="in_text_250 input_a" type="text"  name="start_time" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" />
				 <span  style="color: red" id="start_time_span" class="formtips"></span> 
				</li>
				
				<li>
				<label style="width:130px;">与微信贷合作结束日期:</label>
				<input id="end_time" class="in_text_250 input_a" type="text"  name="start_time"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
				<span  style="color: red" id="end_time_span" class="formtips"></span> 
				</li>
				
				<!-- whb 添加资料  -->
				<li><label>上传基本资料:</label>
				 <a href="###" id="data_add" class="scbtn"  onclick="data()" style="font-size:12px;">添加</a>
				 <span  style="color: red" id="data_add_span" class="formtips"></span> 
				</li>
				<li id="li_data_content"></li>
				
				<li><label>授信函:</label>
				 <input type="hidden" id="service_letter_path"  value="" />
				 <img id="service_letter_path_img" src=""  width="62" height="62" style="position:relative; top:15px;"/> 
				 <a href="###" id="" class="scbtn"  onclick="service_letter_path_a()" style="font-size:12px;">上传图片</a>
				</li>
				
				<li>
				<label>是否必选第三方担保:</label>
				<select id="bonding_required"  onchange="changeBonding(this.value)" >
					<option value="1" selected="selected">是</option>
					<option value="0">否</option>
				</select>
				</li>
				
				<li id="li_mortg_title" style="display: none">
				<label>上传抵押物:</label>
				    <a href="###" id="data_mortg_add" class="scbtn"  onclick="data_mortg()" style="font-size:12px;">添加</a>
				     <span  style="color: red" id="mortg_path_span" class="formtips"></span>
				</li>
				<li id="li_mortg_content"></li>
				<li>
				<label>合作机构简介:</label>
				<textarea id="loanDesc" rows="5" cols="15" style=" height:70px; width:260px;"></textarea><br />
				<span  style="color: red;padding-left: 250px" id="loanDesc_span" class="formtips"></span> 
				</li>
				
				<li style="margin-left:130px;"><input type="button" value="提交" id="btn_submit" class="input_b"/>
				<input type="reset" value="重置" class="input_b"/>
				<input type="button" id="btn_cancle" value="取消" class="input_b"/></li>
                </ul>
                </div>
			  </form>
	        </div>
	        
	        <!-- 修改 -------------小贷公司弹出层-------------------------------------------------------------------------------------------- -->
	        <div id="theme-popover3" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999;">
	            <input type="hidden" id="loan_id_u" value="" class="input_a"/>
                <div class="close_btn"></div>
	            <form>
                <div class="con1">
                <ul>
                <li><label>合作机构名称:</label>
                <input id="loan_name_u" value="" class="input_a"/>
                <span  style="color: red" id="loan_name_u_span" class="formtips"></span> 
                </li>
                <li><label>信用评级:</label>
                    <select id="level_u">
                        <option value="1">一级</option>
                         <option value="2" >二级</option>
                          <option value="3">三级</option>
                           <option value="4">四级</option>
                            <option value="5">五级</option>
                    </select>
                </li>
                
                <li><label>合作机构Logo:</label>
				 <input type="hidden" id="loan_logo_path_update"  value="" />
				  <img id="loan_logo_path_update_img" src=""  width="62" height="62" style="position:relative; top:15px;"/> 
				 <a href="###" id="" class="scbtn"  onclick="loan_logo_path_update_a()" style="font-size:12px;">修改图标</a>
				  <span  style="color: red" id="loan_logo_path_update_span" class="formtips"></span> 
				</li>
                
                <li><label>授信总额:</label><input id="total_amount_u" value="" class="input_a" onchange="hasTotalAmountSum()"/>
                 <span  style="color: red; font-size:12px;" id="total_amount_u_span" class="formtips"></span> 
                </li>
				<li><label>授信余额:</label><input id="available_total_amount_u" value="" class="input_a" disabled="disabled"/>
				  <input type="hidden" id="has_total_amount_u" value="" class="input_a"/>
				  <span  style="color: red" id="available_total_amount_u_span" class="formtips"></span> 
				</li>
                <li><label>偿债准备金比例:</label><input id="insolvency_reserves_scale_u" value="" class="input_a"/>%
                <span  style="color: red" id="insolvency_reserves_scale_u_span" class="formtips"></span> 
                </li>
                <li><label>渠道费比例:</label><input id="channel_cost_u" value="" class="input_a"/>%
                 <span  style="color: red" id="channel_cost_u_span" class="formtips"></span> 
                </li>
				<li><label style="width:130px;">与微信贷合作开始日期:</label><input id="start_time_u" class="in_text_250 input_a" type="text"  name="start_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" />
								    <span  style="color: red" id="start_time_u_span" class="formtips"></span> 
								    </li>
				<li><label style="width:130px;">与微信贷合作结束日期:</label><input id="end_time_u" class="in_text_250 input_a" type="text"  name="start_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								     <span  style="color: red" id="end_time_u_span" class="formtips"></span> 
								    </li>
				
				<!-- whb  修改资料  -->
				<li><label>上传资料:</label>
				 <a href="###" id="data_update" class="scbtn"  onclick="data()" style="font-size:12px;">添加</a>
				</li>
				<li id="li_data_content_update"></li>
				
				<li><label>授信函:</label>
				 <input type="hidden" id="service_letter_path_u"  value="" />
				 <img id="service_letter_path_img_u" src="../images/NoImg.GIF" width="62" height="62" style="position:relative; top:15px;"/> 
				 <a href="###" id="" class="scbtn"  onclick="service_letter_path_a_u()" style="font-size:12px;">上传图片</a></li>
				
				<li>
				<label>是否必选第三方担保:</label>
				<select id="bonding_required_u" onchange="changeBondingUpdate(this.value)">
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
				</li>
				
				<li id="li_mortg_title_update">
				<label>上传抵押物:</label>
				 	<a href="###" id="data_mortg_update" class="scbtn"  onclick="data_mortg()" style="font-size:12px;">添加</a>
				</li>
				<li id="li_mortg_content_update"></li>
				<!--   
				<li>
				<label>状态:</label>
				<select id="loanState_u">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
				</li>
				 -->
				<li>
				<label>合作机构简介:</label>
				<textarea id="loanDesc_u" rows="5" cols="15" style=" height:70px; width:260px;"></textarea><br />
				<span  style="color: red;padding-left: 250px" id="loanDesc_u_span" class="formtips"></span> 
				</li>
				
				<li style="margin-left:130px;"><input type="button" value="提交"  class="input_b" onclick="doEditeLoanCompany()"/>
				<input type="button" value="取消" class="input_b" onclick="themepopover3Cancel()"/> 
				<input type="button" value="添加担保机构" class="input_b" onclick="addLoanCompanyBtn()"/>
				<input type="button" value="添加管理员" class="input_b" onclick="addLoanCompanyAdminBtn()"/>
				</li>
                </ul>
                </div>
                </form>
                <table id="loan_bonding_company_tab" style=" color: #333333;width:100%; border-collapse: collapse;" cellspacing="0" cellpadding="0" bgcolor="#fff" border="0" class="zh_table"></table>
              </div>
              
              <!-- 修改-----担保机构弹出层 ----------------------------------------------------------------------------------------------------------->
              <div id="theme-popover4" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">
                 <input type="hidden"  id="bonding_id_db" value="" class="input_a"/>
                 <input type="hidden" id="loan_bonding_id_db" value=""/>
                 <div class="close_btn1"></div>
                 <form>
                 <div class="con1">
                 <ul>
                    <li>
					<label>担保机构名称：</label>
					<input id="bonding_name_db" value="" class="input_a"/>
					<span  style="color: red;padding-left: 250px" id="bonding_name_db_span" class="formtips"></span> 
					</li>
					
					<li>
					<label>担保机构评级：</label>
					   <select id="bonding_level_db">
					       <option value="1">一级</option>
					       <option value="2">二级</option>
					       <option value="3">三级</option>
					       <option value="4">四级</option>
					       <option value="5">五级</option>
					   </select>
				 
					</li>
                    <li>
					<label>准入时间：</label>
					<input id="in_time_db" class="in_text_250 input_a" type="text"  name="create_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								    	<span  style="color: red;padding-left: 250px" id="in_time_db_span" class="formtips"></span>
					</li>
                    <li>
					<label>状态:</label>
					<select id="status_bonding_db">
					<option value="1">启用</option>
					<option value="0">禁用</option>
					</select>
					 </li>
                     <li>
					<label>担保机构简介:</label>
					<textarea rows="5" cols="15" id="desc_bonding_db" style=" height:100px; width:250px; float:left;"></textarea>
					<span  style="color: red;padding-left: 250px" id="desc_bonding_db_span" class="formtips"></span>
					</li>
					 
					<li><label style="width:130px;">担保机构与小贷公司合作开始时间:</label><input id="loan_bonding_start_time_db" class="in_text_250 input_a" type="text"  name ="loan_bonding_end_time" 
					                onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
					                <span  style="color: red;padding-left: 250px" id="loan_bonding_start_time_db_span" class="formtips"></span>
					                </li>
					<li><label style="width:130px;">担保机构与小贷公司合作结束时间:</label><input id="loan_bonding_end_time_db" class="in_text_250 input_a" type="text"  name="loan_bonding_end_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								    <span  style="color: red;padding-left: 250px" id="loan_bonding_end_time_db_span" class="formtips"></span>
								    </li>
					<li><label style="width:130px;">担保机构对小贷公司授信额度:</label><input id="loan_bonding_credit_limit_db" name="loan_bonding_credit_limit" class="input_a"/>
					 <span  style="color: red;padding-left: 250px" id="loan_bonding_credit_limit_db_span" class="formtips"></span>
					 </li>
					<li><label>合作模式:</label>
					<select id="loan_bonding_model_type_db" name="loan_bonding_model_type">
					<option value="1">整体授信</option>
					<option value="2">逐笔审核</option>
					</select>
					</li>
					<li><label>合作关系描述:</label><textarea rows="5" cols="15" id="loan_bonding_desc_db" style=" height:100px; width:250px;"></textarea></li>
					<li><label>担保函:</label>
					 <input type="hidden" id="bonding_letter_path_db"  value=""  class="input_a"/>
				     <img id="bonding_letter_path_img_db" src="../images/NoImg.GIF" width="62" height="62"/>
				     <a href="###" id="" class="scbtn"  onclick="bonding_letter_path_a_db()">上传图片</a>
				      <span  style="color: red;padding-left: 250px" id="bonding_letter_path_db_span" class="formtips"></span>
				     </li>
					
					<li style="margin-left:130px;">
						<input type="button" value="提交" id="btn_bonding_submit_db" onclick="doEditeLoanBondingCompany()" class="input_b"/>
						<input type="button" id="btn_bonding_cancle_db" value="取消" class="input_b" onclick="themepopover4Cancel()"/>
					</li>
                    </ul>
                    </div>
                    </form>
                  </div>
			</div>
               
             <!-- 查询管理员详情 --> 	
             <div id="adminUserDiv" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;">
             	<div class="close_btn"></div>
             	<form>                                   
                <div class="con1">
                <ul>
                   <li>
                       <label>用户名:</label><span id="userNameL"></span>
                   </li>  
                   <li>
                       <label>公司电话:</label><span id="phoneL"></span>
                     </li>
                      
                     <li>
                       <label>公司邮箱:</label><span id="emailL"></span>
                     </li>
                     <li>
                       <label>用户状态:</label><span id="enableL"></span>
                     </li>
                     
                </ul>
                </div>
                </form>
                
             </div>
	        	
			<!-- 担保机构信息录入 -->
			 <input type="hidden" id="loan_bonding_loanid" value=""/>
			 <input type="hidden" id="loan_bonding_bondingid" value=""/>
			 <input type="hidden" id="loan_bonding_id" value=""/>
			<div id="theme-popover" style="padding:15px; width:1200px;  overflow:hidden;  background:#f1f1f1; margin-top:10px;">
				 <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0"  >
				    <tr>
				       <td></td>
				    </tr>
				    <tr>
				       <td class="f66" align="left" width="50%" height="36px">
				                          担保机构名称：<input type="text" id="bondingCompanyName" value=""/>
				                       <input type="button" value="查找" onclick="searchBondindBtnData()"/>
				      </td>
				    </tr>		
				</table>
                <table id="tab_bonding_content"   style=" color: #333333;width:100%; border-collapse: collapse;" cellspacing="0" cellpadding="0" bgcolor="#fff" border="0" class="zh_table">
				</table>
				<!--<table id="tab_bonding_content" style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" >
				</table>-->
				<div id="PaginationBoading" class="paging" style="margin-top:20px; position:relative;  overflow:hidden;"></div>
                <!-- <a style="position:relative; left:600px; cursor:pointer; padding:6px 10px; font-weight:bold; font-size:14px; color:#333; background:#e5f3fa; border-radius:5px; border:solid 1px #c7c7c7; font-size:12px;" class="theme-login1">下一步</a> -->
			</div>
			<br /><br />
			<div id="div_add_company_bonding" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">	
       
              <div class="close_btn"></div>
				<select id="sel_bonding_list" onchange="ajaxQueryCompanyBondingByIdData()">
					<option value="0">请选择担保机构</option>
					<c:forEach items="${companyBondingList }" var="list">
					<option value="${list.id}">${list.name}</option>
					</c:forEach>
				</select>
				
				<p>基本信息</p>
				<form>
                <div class="con1">
                <ul>
                    <li>
					  <label>担保机构名称：</label>
					  <input id="bonding_name" value="" class="input_a"/>
					  <span  style="color: red" id="bonding_name_span" class="formtips"></span> 
					</li>
					<li>
					<label>担保机构评级：</label>
					   <select id="bonding_level">
					       <option value="1">一级</option>
					       <option value="2">二级</option>
					       <option value="3">三级</option>
					       <option value="4">四级</option>
					       <option value="5">五级</option>
					   </select>
				 
					</li>
                    <li>
					<label>准入时间：</label>
					<input id="create_time" class="in_text_250 input_a" type="text"  name="create_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								     <span  style="color: red" id="create_time_span" class="formtips"></span> 
								    </li>
                    <li>
					<label>状态:</label>
					<select id="status_bonding">
					<option value="1">启用</option>
					<option value="0">禁用</option>
					</select>
					 </li>
                     <li>
					<label> 担保机构简介:</label>
					<textarea rows="30" cols="60" id="desc_bonding" style=" height:100px; width:250px;"></textarea><br />
					<span  style="color: red;padding-left: 250px" id="desc_bonding_span" class="formtips"></span> 
					</li>
					
					<li><label style="width:130px;">担保机构与小贷公司合作开始时间:</label><input id="loan_bonding_start_time" class="in_text_250 input_a" type="text"  name ="loan_bonding_end_time" 
					                onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
					                <span  style="color: red" id="loan_bonding_start_time_span" class="formtips"></span> 
					                </li>
					<li><label style="width:130px;">担保机构与小贷公司合作结束时间:</label><input id="loan_bonding_end_time" class="in_text_250 input_a" type="text"  name="loan_bonding_end_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
								    <span  style="color: red" id="loan_bonding_end_time_span" class="formtips"></span> 
								    </li>
					<li><label style="width:130px;">担保机构对小贷公司授信额度:</label><input id="loan_bonding_credit_limit" name="loan_bonding_credit_limit" class="input_a"/> 
					<span  style="color: red" id="loan_bonding_credit_limit_span" class="formtips"></span> 
					</li>
					<li><label>合作模式:</label><select id="loan_bonding_model_type" name="loan_bonding_model_type">
					<option value="1">整体授信</option>
					<option value="2">逐笔审核</option>
					</select>
					</li>
					<li><label>合作关系描述:</label>
					<textarea rows="30" cols="60" id="loan_bonding_desc" style=" height:100px; width:250px;"></textarea>
					</li>
					<li><label>担保函:</label>
					 <input type="hidden" id="bonding_letter_path"  value=""  class="input_a"/>
				     <img id="bonding_letter_path_img" src="" width="62" height="62"/>
				     <a href="###" id="" class="scbtn"  onclick="bonding_letter_path_a()">上传图片</a>
				     <span  style="color: red" id="bonding_letter_path_span" class="formtips"></span> 
				     </li>
					
					<li style="margin-left:130px;">
					<input type="button" value="提交" id="btn_bonding_submit" class="input_b"/>
					<input type="reset" value="重置" class="input_b"/ >
					<input type="button" id="btn_bonding_cancle" value="取消" class="input_b"/>
					</li>
                    </ul>
                    </div>
				</form>
				</div>
                
				<div id="theme-popover1" style=" width: 1200px; padding:10px; overflow:hidden; background:#f1f1f1;display:none;">
				
				 <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" >
				    <tr>
				      <td><input type="button" id="btn_loan_bonding_add" onclick="addLoanBondingBtn()" value="添加小贷公司和担保机构关系信息" style=" padding:0 10px; height:30px; background:#e5f3fa; border-radius:5px; text-align:center; cursor:pointer;"/></td>
				    </tr>
				    <tr>
				      <td class="f66" align="left" width="50%" height="36px">
				               小贷公司名称：<input type="text" id="loanCompanyNames" value=""/>
				               担保机构名称：<input type="text" id="bondingCompanyNames" value=""/>
				                   <input type="button" value="查找" onclick="searchloanBondingCompanyBtnData()"/>
				      </td>
				    </tr>
				</table>
                <table  style=" color: #333333;width:100%; border-collapse: collapse;" cellspacing="0" cellpadding="0" bgcolor="#fff" border="0" class="zh_table">
			<tbody>
				<tr class="gvHeader">
					<th scope="col">
						编号
					</th>
					<th scope="col">
						小贷公司
					</th>
					<th scope="col">
						担保机构
					</th>
					
					<th scope="col">
						开始时间
					</th>
					<th scope="col">
						结束时间
					</th>
					<th scope="col">
						合作模式
					</th>
					<th scope="col">
						创建时间
					</th>
					<th scope="col">
						描述
					</th>
					</tr>
				</table>
				<!--<table id="tab_loan_bonding_content" style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0" ></table>-->
				<div id="PaginationLoanBonding" class="paging" style="margin-top:20px; overflow:hidden; position:relative;"></div>
                <!--  <a style="position:relative; left:600px; cursor:pointer; padding:6px 10px; font-weight:bold; font-size:14px; color:#333; background:#e5f3fa; border-radius:5px; border:solid 1px #c7c7c7; font-size:12px;" class="theme-login2">下一步</a>-->
				</div>
			 
				<div id="div_add_company_loan_bonding" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;">
				<p>基本信息</p>
                <div class="close_btn"></div>
				<form>
                <div class="con1">
                	<ul>
				
					<li><label>开始时间:</label><input id="loan_bonding_start_time" class="in_text_250 input_a" type="text"  name ="loan_bonding_end_time" 
					                onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/></li>
					<li><label>结束时间:</label><input id="loan_bonding_end_time" class="in_text_250 input_a" type="text"  name="loan_bonding_end_time" 
								    onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/></li>
					<li><label>额度:</label><input id="loan_bonding_credit_limit" name="loan_bonding_credit_limit" class="input_a"/> </li>
					<li><label>合作模式:</label><select id="loan_bonding_model_type" name="loan_bonding_model_type">
					<option value="1">整体授信</option>
					<option value="2">逐笔审核</option>
					</select>
					</li>
					<li><label style=" position:relative;">描述:</label><textarea rows="5" cols="15" id="loan_bonding_desc" style=" height:70px; width:260px;"></textarea></li>
					<li><label>担保函:</label>
					 <input type="text" id="bonding_letter_path"  value=""  class="input_a"/>
				     <img id="bonding_letter_path_img" src="" width="62" height="62"/>
				     <a href="###" id="" class="scbtn"  onclick="bonding_letter_path_a()">上传图片</a></li>
					<li style="margin-left:130px;"><input type="button" value="提交" id="btn_loan_bonding_submit" class="input_b"/>
					<input type="button" value="重置" class="input_b"/>
					<input type="button" id="btn_loan_bonding_cancle" value="取消" class="input_b"/></li>
                    </ul>
                    </div>
				</form>
			</div>
			
			<!-- 添加小贷公司超级管理员 -->
			<div id="loanCompanyAdmin" style="display:none; position:absolute; background:#fff; border:solid 3px #c7c7c7; border-radius:10px; width:600px; height:auto; padding:20px; left:50px; top:50px;z-index: 9999">
            <div class="close_btn"></div>
				<div style="padding: 15px 10px 0px 10px;">
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table style="color:#333; width:100%; order-collapse: collapse; background:#fff;" cellspacing="0" cellpadding="0" bgcolor="#fff" border="0" class="user_table">
							 
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									用户名：
								</td>
								<td align="left" class="f66">
									<s:textfield id="userNames" name="paramMap.userNames" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
										 <span  style="color: red" id="userNames_span" class="formtips"></span> 
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									密码：
								</td>
								<td align="left" class="f66">
									<s:password id="password" name="paramMap.password" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" />
										 <span  style="color: red" id="password_span" class="formtips"></span>
								</td>
							</tr>
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									确认密码：
								</td>
								<td align="left" class="f66">
									<s:password id="confirmPassword" name="paramMap.confirmPassword" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" />
										 <span  style="color: red" id="confirmPassword_span" class="formtips"></span>
								</td>
							</tr>
							 
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									公司电话：
								</td>
								<td align="left" class="f66">
									<s:textfield id="telphone" name="paramMap.telphone" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
										 <span  style="color: red" id="telphone_span" class="formtips"></span>
								</td>
							</tr>
							 
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									公司邮箱：
								</td>
								<td align="left" class="f66">
									<s:textfield id="email" name="paramMap.email" theme="simple"
										cssClass="in_text_2" cssStyle="width: 150px" ></s:textfield>
										<span  style="color: red" id="email_span" class="formtips"></span>
								</td>
							</tr>
							 
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									状态：
								</td>
								<td align="left" class="f66">
									<s:radio id="enable" name="paramMap.enable"  theme="simple"
										list="#{1:'启用',2:'禁用'}"/>
								</td>

							</tr>
							<tr>
								<td height="25">
								</td>
								<td align="left" class="f66" style="color: Red;">
									<span  style="color: red" id="loanCompanyCheck" class="formtips"></span>
								</td>
							</tr>
							<tr>
								<td>
                                <button onclick="btn_saveLoadUser()" style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px" /> &nbsp;
                                <button type="reset" style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px; position:relative; bottom:25px; left:100px;"/>&nbsp;
                                 
                                </td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		 
	</div>
 </div>
	
</body>
	
	
</html>

<script type="text/javascript">

//whb
//图片id
var m = 0; 
var n = 1;  
//修改图片id
var up = 0; 
//修改时图片反显的id范围
var min = 0;
var max = 0;



//初始化分页变量
var pageNumLoan = 0; 
var pageSize = 10; 
var totalNum = 0;

//翻页调用  - 小贷公司
function PageCallbackLoan(pageNumLoan, jq) {             
	searchData(pageNumLoan);
}

//翻页调用  - 担保机构
function PageCallbackBonding(pageNumLoan, jq) {             
	searchBondingData(pageNumLoan);
}

//翻页调用  - 小贷担保机构关系
function PageCallbackLoanBonding(pageNumLoan, jq) {             
	searchLoanBondingData(pageNumLoan);
}

var param={};

searchData(pageNumLoan);
searchBondingData(pageNumLoan);
searchLoanBondingData(pageNumLoan);

$("#btn_cancle").on("click",function(){
	$("#div_add_company_loan").hide();
});
$("#btn_submit").on("click",addCompayLoan);

$("#btn_bonding_cancle").on("click",function(){
	$("#div_add_company_bonding").hide();
});
$("#btn_bonding_submit").on("click",addCompayBonding);


//反选小贷公司
function editeLoanCompany(id,name,total_amount,available_total_amount,has_total_amount,insolvency_reserves_scale,start_time_f,end_time_f,bonding_required,status,service_letter_path,desc,level,channel_cost,loan_logo_path){
	$('#loan_bonding_loanid').val(id);
	
	$("#loan_id_u").val(id);
	$("#loan_name_u").val(name);
	$("#level_u").val(level);
	$("#total_amount_u").val(total_amount);
	$("#available_total_amount_u").val(available_total_amount);
	$("#has_total_amount_u").val(has_total_amount);
	$("#insolvency_reserves_scale_u").val(insolvency_reserves_scale);
	$("#channel_cost_u").val(channel_cost);
	
	 var obj1=document.getElementById("level_u");
	    for(i=0;i<=obj1.options.length;i++){  
	     if((obj1.options[i].value)==parseInt(level)){      
	  	   obj1.options[i].selected=true;   
		        break;
	     }
	    }
	
	$("#start_time_u").val(start_time_f);
	$("#end_time_u").val(end_time_f);
	if(service_letter_path==''){
		$("#service_letter_path_img_u").attr("src","../images/NoImg.GIF");
	}else{
		$("#service_letter_path_img_u").attr('src',"../"+service_letter_path);
	}
	 
	if(loan_logo_path=='' || loan_logo_path=='null'){
		$("#loan_logo_path_update_img").attr("src","../images/NoImg.GIF");
	}else{
		$("#loan_logo_path_update_img").attr('src',"../"+loan_logo_path);
	}
	
	$("#service_letter_path_u").val(service_letter_path);
	$("#loan_logo_path_update").val(loan_logo_path);
	$("#loanDesc_u").val(desc);
	//是否必须an
	var obj1=document.getElementById("bonding_required_u");
    for(i=0;i<=obj1.options.length;i++){  
     if((obj1.options[i].value)==parseInt(bonding_required)){      
  	   obj1.options[i].selected=true;   
	        break;
     }
    }
	//0:需上传抵押物
    if(1 == $("#bonding_required_u").val()){
		$("#li_mortg_title_update").hide();
		$("#li_mortg_content_update").hide();		
	}
    else if(0 == $("#bonding_required_u").val()){
		$("#li_mortg_title_update").show();
		$("#li_mortg_content_update").show();
	}
	
    //状态
	//var obj1=document.getElementById("loanState_u");
    // for(i=0;i<=obj1.options.length;i++){  
     // if((obj1.options[i].value)==parseInt(status)){      
   	  // obj1.options[i].selected=true;   
	  //      break;
     // }
    // }
	$('#theme-popover3').show();
	var param = {}
	param["id"] = $("#loan_id_u").val();
	$.ajax({
	    type: "post",
	    url: "findAllBondingCompanyByLoadId.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	
	    	var tr_list_o = $("#loan_bonding_company_tab");
	    	tr_list_o.empty();
	    	
	    	var strHtml = "";
	    	strHtml +="<tr class='gvHeader'>";
    		strHtml +="<th scope='col'>"+'编号'+"</th>";
    		strHtml +="<th scope='col'>"+'担保机构名称'+"</th>";
    		strHtml +="<th scope='col'>"+'准入时间'+"</th>";
    		strHtml +="<th scope='col'>"+'状态'+"</th>";
    		strHtml +="<th scope='col'>"+'操作'+"</th>";
    		strHtml +="</tr>";
    		
	    	 if(data.result != null && data.result != undefined && data.result != ''){
		    		for(var i=0;i<data.result.length;i++){
			    		var o = data.result[i];
			    		strHtml +="<tr>";
			    		strHtml +="<td class='f99' align='center'>"+(i+1)+"</td>";
			    		strHtml +="<td class='f99' align='center'>"+o.name+"</td>";
			    		strHtml +="<td class='f99' align='center'>"+o.in_time_f+"</td>";
			    		if(o.status==1){
			    		   strHtml +="<td class='f99' align='center'>"+'启用'+"</td>";
			    		}
			    		if(o.status==0){
				    		   strHtml +="<td class='f99' align='center'>"+'禁用'+"</td>";
				    	}
			    		strHtml +="<td class='f99' align='center'>"+'<a href="###" onclick="editeLoanBondingCompany('+"'"+o.id+"'"+','+"'"+o.name+"'"+','+"'"+o.in_time_f+"'"+','+"'"+o.status+"'"+','+"'"+o.desc+"'"+','+"'"+o.create_time+"'"+','+"'"+o.loan_bonding_id+"'"+','+"'"+o.start_time+"'"+','+"'"+o.end_time+"'"+','+"'"+o.credit_limit+"'"+','+"'"+o.model_type+"'"+','+"'"+o.bonding_letter_paths+"'"+','+"'"+o.loan_bonding_desc+"'"+','+"'"+o.bonding_level+"'"+')">编辑</a>'+"</td>";
						strHtml +="</tr>";
			    	}
		    		tr_list_o.append(strHtml);
		    	}
	   
	    
	    	 //修改页面时n值为1000
	    	 n = -1000;
	    	//反显基本资料图片 whb update
	    		$.ajax({
	    		    type: "post",
	    		    url: "loadMortgPic.do",
	    		    dataType: "json",
	    		    data:param,
	    		    success: function (data) {
	    		    	
	    		    	 if(data.state==1){
	    		    		 //反显图片资料
	    		    		 var dataContentUpdate = $("#li_data_content_update");
	    		    		 dataContentUpdate.empty();
	    		    		 var strHtml = "";
	    		    		 //反显抵押物资料
	    		    		 var mortgContentUpdate = $("#li_mortg_content_update");
	    		    		 mortgContentUpdate.empty();
	    		    		 var mortgHtml = "";
	    		    		 if(data.result != null && data.result != undefined && data.result != ''){
		    		    		 for(var i=0;i<data.result.length;i++){
			    			    		var o = data.result[i];
			    			    		//id范围 whb
			    		    			 if(i==0){
			    		    				min = o.id;
			    		    			 }else if(i==data.result.length-1){
			    		    				max = o.id;
			    		    			 }
			    			    		if(o.type == 2){
			    			    			//alert(o.data_path + "--------" + o.data_desc);
			    			    			strHtml += "<div id = 'div_update_" + o.id + "'>";
				    		    		 	strHtml += "<input type='hidden' id='upload_data_path_update_" + o.id + "' value='" + o.data_path + "' />";
				    			    		strHtml += "<label id='img_data_update_" + o.id + "'>图片:</label>";
				    		    		  	strHtml += "<img id='upload_data_path_update_img_" + o.id + "' src='../" + o.data_path + "' width='62' height='62' style='position:relative; top:15px;'/>&nbsp;";
				    		    			strHtml +="<a href='###' id='upload_data_update_" + o.id + "' class='scbtn'  onclick='upload_data_path_update_a(" + o.id + ")' style='font-size:12px;'>修改图片</a>&nbsp;";
				    		    			strHtml +="<a href='###' id='delete_data_update_" + o.id + "' class='scbtn'  onclick='delete_data_update(" + o.id + ")' style='font-size:12px;'>删除</a><br/>";
				    		    			strHtml +="<label id='img_data_desc_update_" + o.id + "' style='position:relative;'>图片描述:</label>";
				    		    			strHtml +="<textarea id='data_desc_update_" + o.id + "' rows='5' cols='15' style=' height:70px; width:260px;'>" + o.data_desc + "</textarea><br/>";
				    		    			strHtml +="</div>";
			    			    		}
			    			    		else if(o.type == 1){
			    			    			 mortgHtml += "<div id = 'mortg_div_update_" + o.id + "'>";
			    		    		    	 mortgHtml += "<input type='hidden' id='upload_mortg_path_update_" + o.id + "' value='" + o.data_path + "' />";
			    		    		    	 mortgHtml += "<label id='mortg_img_data_update_" + o.id + "'>抵押物图片:</label>";
			    		    		    	 mortgHtml += "<img id='upload_mortg_path_update_img_" + o.id + "' src='../" + o.data_path + "' width='62' height='62' style='position:relative; top:15px;'/>&nbsp;";
			    		    		    	 mortgHtml +="<a href='###' id='upload_mortg_update_" + o.id + "' class='scbtn'  onclick='upload_mortg_path_update_a(" + o.id + ")' style='font-size:12px;'>修改图片</a>&nbsp;";
			    		    		    	 mortgHtml +="<a href='###' id='delete_mortg_update_" + o.id + "' class='scbtn'  onclick='delete_data_update(" + o.id + ")' style='font-size:12px;'>删除</a><br/>";
			    		    		    	 mortgHtml +="<label id='img_mortg_desc_update_" + o.id + "' style='position:relative; '>抵押物描述:</label>";
			    		    		    	 mortgHtml +="<textarea id='mortg_desc_update_" + o.id + "' rows='5' cols='15' style=' height:70px; width:260px;'>" + o.data_desc + "</textarea><br/>";
			    		    		    	 mortgHtml +="</div>";
			    			    		}
			    			     }
		    		    		 mortgContentUpdate.append(mortgHtml);
		    		    		 dataContentUpdate.append(strHtml);
	    		    		 }
	    		    	 }
	    		    },
	    		    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    				alert(errorThrown);
	    		    }
	    		});
	    
	    
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
	    }
	});
	
	 
	
}

//反选担保机构详细信息
function editeLoanBondingCompany(id,name,in_time_f,status,desc,create_time,loan_bonding_id,start_time,end_time,credit_limit,model_type,bonding_letter_paths,loan_bonding_desc, bonding_level){
	
	$("#bonding_id_db").val(id);//担保机构id
	$("#bonding_name_db").val(name);
	//$("#bonding_level_db").val(bonding_level);
	$("#in_time_db").val(in_time_f);
	$("#status_bonding_db").val(status);
	$("#desc_bonding_db").val(desc);
	var obj1=document.getElementById("status_bonding_db");
    for(i=0;i<=obj1.options.length;i++){  
     if((obj1.options[i].value)==parseInt(status)){      
  	   obj1.options[i].selected=true;   
	        break;
     }
    }
    
    var obj1=document.getElementById("bonding_level_db");
    for(i=0;i<=obj1.options.length;i++){  
     if((obj1.options[i].value)==parseInt(bonding_level)){      
  	   obj1.options[i].selected=true;   
	        break;
     }
    }
	
	$("#loan_bonding_id_db").val(loan_bonding_id);//中间关系表id
	$("#loan_bonding_end_time_db").val(end_time);
	$("#loan_bonding_start_time_db").val(start_time);
	$("#loan_bonding_credit_limit_db").val(credit_limit);
	$("#loan_bonding_model_type_db").val(model_type);
	$("#loan_bonding_desc_db").val(loan_bonding_desc);
	$("#bonding_letter_path_db").val(bonding_letter_paths);//
	if(bonding_letter_paths==''){
		$("#bonding_letter_path_img_db").attr("src","../images/NoImg.GIF");
	}else{
		$("#bonding_letter_path_img_db").attr('src',"../"+bonding_letter_paths);
	}
	
	var obj1=document.getElementById("loan_bonding_model_type_db");
    for(i=0;i<=obj1.options.length;i++){  
     if((obj1.options[i].value)==parseInt(model_type)){      
  	   obj1.options[i].selected=true;   
	        break;
     }
    }
    $('#theme-popover4').show();
}

//编辑小贷公司
function doEditeLoanCompany(){
	var param = {}
	param["id"] = $("#loan_id_u").val();
	param["loan_name"] = $("#loan_name_u").val();
	param["total_amount"] = $("#total_amount_u").val();
	param["available_total_amount"] = $("#available_total_amount_u").val();
	param["has_total_amount"] = $("#has_total_amount_u").val();
	param["insolvency_reserves_scale"] = $("#insolvency_reserves_scale_u").val();
	param["start_time"] = $("#start_time_u").val();
	param["end_time"] = $("#end_time_u").val();
	param["service_letter_path"] = $("#service_letter_path_u").val();
	param["bonding_required"] = $("#bonding_required_u").val();
	//param["status"] = $("#loanState_u").val();
	param["desc"] = $("#loanDesc_u").val();
	param["loan_logo_path_update"] = $("#loan_logo_path_update").val();
	param["level"] = $("#level_u").val();
	param["channel_cost"] = $("#channel_cost_u").val();
	
	 
	if(n > -1000){
		// 添加图片资料
		var data_path = "";
		var data_desc = "";
		// 添加抵押物资料
		var mortg_path = "";
		var mortg_desc = "";
		for(var i=-1000;i<n;i++){
			// 添加图片资料
			if($("#upload_data_path_"+i+"").val() != "" && $("#upload_data_path_"+i+"").val() != undefined){
				data_path += $("#upload_data_path_"+i+"").val() + ",";
				if($("#data_desc_"+i+"").val() == ""){
					data_desc += "无" + ",";
				}else{
					data_desc += $("#data_desc_"+i+"").val() + ",";
				}
			}
			// 添加抵押物资料
			if($("#upload_mortg_path_"+i+"").val() != "" && $("#upload_mortg_path_"+i+"").val() != undefined){
				mortg_path += $("#upload_mortg_path_"+i+"").val() + ",";
				if($("#mortg_desc_"+i+"").val() == ""){
					mortg_desc += "无" + ",";
				}else{
					mortg_desc += $("#mortg_desc_"+i+"").val() + ",";
				}
			}
		}
		param["num"] = 1;
		param["data_path"] = data_path;
		param["data_desc"] = data_desc;
		param["mortg_path"] = mortg_path;
		param["mortg_desc"] = mortg_desc;
	}
	//修改基本资料图片
	if(min > max){
		var tmp = 0;
		tmp = min;
		min = max;
		max = tmp;
	}
	//修改图片
	var data_update_path = "";
	var data_update_desc = "";
	//修改抵押物
	var mortg_update_path = "";
	var mortg_update_desc = "";
	var upda = 0;
	var ids = "";//修改图片id
	var idsMortg = "";//修改抵押物id
	for(var j=min;j<=max;j++){
		//修改图片
		if(($("#upload_data_path_update_"+j+"").val() != "" || $("#data_desc_update_"+j+"").val() != "") && $("#upload_data_path_update_"+j+"").val() != undefined){
			data_update_path += $("#upload_data_path_update_"+j+"").val() + ",";
			if($("#data_desc_update_"+j+"").val() == ""){
				data_update_desc += "无" + ",";
			}else{
				data_update_desc += $("#data_desc_update_"+j+"").val() + ",";
			}
			upda = 1;
			ids += j + ",";
		}
		//修改抵押物
		if(($("#upload_mortg_path_update_"+j+"").val() != "" || $("#mortg_desc_update_"+j+"").val() != "") && $("#upload_mortg_path_update_"+j+"").val() != undefined){
			mortg_update_path += $("#upload_mortg_path_update_"+j+"").val() + ",";
			if($("#mortg_desc_update_"+j+"").val() == ""){
				mortg_update_desc += "无" + ",";
			}else{
				mortg_update_desc += $("#mortg_desc_update_"+j+"").val() + ",";
			}
			upda = 1;
			idsMortg += j + ",";
		}
	}
	param["up"] = upda;
	param["data_update_path"] = data_update_path;
	param["data_update_desc"] = data_update_desc;
	param["mortg_update_path"] = mortg_update_path;
	param["mortg_update_desc"] = mortg_update_desc;
	param["ids"] = ids;
	param["idsMortg"] = idsMortg;
	
	//alert("111111111"+data_path+"--------"+data_desc+"+++++++++"+mortg_path+"*********"+mortg_desc);
	//alert("222222222"+data_update_path+"--------"+data_update_desc+"+++++++++"+mortg_update_path+"*********"+mortg_update_desc);
	//return;
	//校验
	if($("#loan_name_u").val()==''){
		$("#loan_name_u_span").attr("class", "formtips onError");
		$("#loan_name_u_span").html("请填写合作机构名称"); 
		return;
	}else{
		$("#loan_name_u_span").attr("class", "formtips");
		$("#loan_name_u_span").html(""); 
	}
	//机构logo
	if($("#loan_logo_path_update").val()=='null'){
		$("#loan_logo_path_update_span").attr("class", "formtips onError");
		$("#loan_logo_path_update_span").html("请上传合作机构logo");
		return
	}else{
		$("#loan_logo_path_update_span").attr("class", "formtips");
		$("#loan_logo_path_update_span").html(""); 
	}
	
	//授信总额
	if($("#total_amount_u").val()==''){
		$("#total_amount_u_span").attr("class", "formtips onError");
		$("#total_amount_u_span").html("请填写授信总金额");
		return
	}else{
		
		var reg = /^[1-9]+\d*(\.[0-9]{2})?$/;
	    var obj = document.getElementById("total_amount_u");  
	    if(!reg.test(obj.value)){  
	    	$("#total_amount_u_span").attr("class", "formtips onError");
	    	$("#total_amount_u_span").html("输入不正确！"); 
	    	return
	    }else{
		   $("#total_amount_u_span").attr("class", "formtips");
		   $("#total_amount_u_span").html(""); 
	    }
	}
	
	if($("#available_total_amount_u").val()<0){
		$("#available_total_amount_u_span").attr("class", "formtips onError");
		$("#available_total_amount_u_span").html("授信余额必须大于零元");
		return
	}else{
		$("#available_total_amount_u_span").attr("class", "formtips");
		$("#available_total_amount_u_span").html("");
	}
	
	//偿债准备金比例
	if($("#insolvency_reserves_scale_u").val()==''){
		$("#insolvency_reserves_scale_u_span").attr("class", "formtips onError");
		$("#insolvency_reserves_scale_u_span").html("请填写偿债准备金比例");
		return
	}else{
		var reg2 = /^[0-9]{1}(\.[0-9]{1,2})?$/;
        var obj = document.getElementById("insolvency_reserves_scale_u").value;  
	    if(!reg2.test(obj)){
	    	$("#insolvency_reserves_scale_u_span").attr("class", "formtips onError");
			$("#insolvency_reserves_scale_u_span").html("输入不正确");
			return
	    }else{
	    	$("#insolvency_reserves_scale_u_span").attr("class", "formtips");
			$("#insolvency_reserves_scale_u_span").html("");
	    }
	}
	//渠道费比例
	if($("#channel_cost_u").val()==''){
		$("#channel_cost_u_span").attr("class", "formtips onError");
		$("#channel_cost_u_span").html("请填写渠道费比例");
		return
	}else{
		var reg3 = /^[0-9]{1}(\.[0-9]{1,2})?$/;
		var obj = document.getElementById("channel_cost_u").value;  
		if(!reg3.test(obj)){
			$("#channel_cost_u_span").attr("class", "formtips onError");
			$("#channel_cost_u_span").html("输入不正确");
			return
	    }else{
	    	$("#channel_cost_u_span").attr("class", "formtips");
			$("#channel_cost_u_span").html(""); 
	    }
		
	}
	 
	//开始时间
	if($("#start_time_u").val()==''){
		$("#start_time_u_span").attr("class", "formtips onError");
		$("#start_time_u_span").html("请填写合作开始时间");
		return
	}else{
		$("#start_time_u_span").attr("class", "formtips");
		$("#start_time_u_span").html(""); 
	}
	
	//结束时间
	if($("#end_time_u").val()==''){
		$("#end_time_u_span").attr("class", "formtips onError");
		$("#end_time_u_span").html("请填写合作结束时间");
		return
	}else if($("#end_time_u").val()<$("#start_time_u").val()){
		$("#end_time_u_span").attr("class", "formtips onError");
		$("#end_time_u_span").html("合作结束时间必须大于开始时间");
		return
	}else{
		$("#end_time_u_span").attr("class", "formtips");
		$("#end_time_u_span").html(""); 
	}
	 
	//合作机构简介
	if($("#loanDesc_u").val()==''){
		$("#loanDesc_u_span").attr("class", "formtips onError");
		$("#loanDesc_u_span").html("请填写合作机构简介信息");
		return
	}else{
		$("#loanDesc_u_span").attr("class", "formtips");
		$("#loanDesc_u_span").html("");
	}
	 
	$.ajax({
	    type: "post",
	    url: "updateCompnayLoanData.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	 if(data.state==1){
	    		 alert('小贷公司信息修改成功！');
	    		 $('#theme-popover3').hide();
	    		 searchData(pageNumLoan);
	    	 }else{
	    		 alert('小贷公司信息修改失败！');
	    	 };
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
	    }
	});
}

//修改合作机构信息及其中间表
function doEditeLoanBondingCompany(){
	 var param={};
	 param["id"] = $("#bonding_id_db").val();//担保机构id
	 param["bonding_name"] = $("#bonding_name_db").val();
	 param["in_time"] = $("#in_time_db").val();
	 param["status_bonding"] = $("#status_bonding_db").val();
	 param["desc_bonding"] = $("#desc_bonding_db").val();
	 param["bonding_level"] = $("#bonding_level_db").val();
	
	 param["loan_bonding_id"] = $("#loan_bonding_id_db").val();//中间关系表id
	 param["end_time"] = $("#loan_bonding_end_time_db").val();
	 param["start_time"] = $("#loan_bonding_start_time_db").val();
	 param["credit_limit"] = $("#loan_bonding_credit_limit_db").val();
	 param["model_type"] = $("#loan_bonding_model_type_db").val();
	 param["desc"] = $("#loan_bonding_desc_db").val();
	 param["bonding_letter_path"] = $("#bonding_letter_path_db").val();
	 
	 //校验
	 if($("#bonding_name_db").val()==''){
			$("#bonding_name_db_span").attr("class", "formtips onError");
			$("#bonding_name_db_span").html("请填写担保机构名称"); 
			return;
		}else{
			$("#bonding_name_db_span").attr("class", "formtips");
			$("#bonding_name_db_span").html(""); 
		}
		if($("#in_time_db").val()==''){
			$("#in_time_db_span").attr("class", "formtips onError");
			$("#in_time_db_span").html("准入时间不能为空"); 
			return;
		}else{
			$("#in_time_db_span").attr("class", "formtips");
			$("#in_time_db_span").html(""); 
		}
		
		if($("#desc_bonding_db").val()==''){
			$("#desc_bonding_db_span").attr("class", "formtips onError");
			$("#desc_bonding_db_span").html("请填写担保机构简介"); 
			return;
		}else{
			$("#desc_bonding_db_span").attr("class", "formtips");
			$("#desc_bonding_db_span").html("");
		}
		
		//开始时间
		if($("#loan_bonding_start_time_db").val()==''){
			$("#loan_bonding_start_time_db_span").attr("class", "formtips onError");
			$("#loan_bonding_start_time_db_span").html("请填写合作开始时间");
			return
		}else{
			$("#loan_bonding_start_time_db_span").attr("class", "formtips");
			$("#loan_bonding_start_time_db_span").html("");
		}
		
		//结束时间
		if($("#loan_bonding_end_time_db").val()==''){
			$("#loan_bonding_end_time_db_span").attr("class", "formtips onError");
			$("#loan_bonding_end_time_db_span").html("请填写合作结束时间");
			return
		}else if($("#loan_bonding_end_time_db").val()<$("#loan_bonding_start_time_db").val()){
			$("#loan_bonding_end_time_db_span").attr("class", "formtips onError");
			$("#loan_bonding_end_time_db_span").html("合作结束时间必须大于开始时间");
			return
		}else{
			$("#loan_bonding_end_time_db_span").attr("class", "formtips");
			$("#loan_bonding_end_time_db_span").html(""); 
		}
		
		//担保授信总额
		if($("#loan_bonding_credit_limit_db").val()==''){
			$("#loan_bonding_credit_limit_db_span").attr("class", "formtips onError");
			$("#loan_bonding_credit_limit_db_span").html("请填写授信总金额");
			return
		}else{
			
			var reg = /^[1-9]+\d*(\.[0-9]{2})?$/;
		    var obj = document.getElementById("loan_bonding_credit_limit_db");  
		    if(!reg.test(obj.value)){  
		    	$("#loan_bonding_credit_limit_db_span").attr("class", "formtips onError");
		    	$("#loan_bonding_credit_limit_db_span").html("输入不正确！"); 
		    	return
		    }else{
			   $("#loan_bonding_credit_limit_db_span").attr("class", "formtips");
			   $("#loan_bonding_credit_limit_db_span").html(""); 
		    }
		}
		
		//担保函校验
		if($("#loan_bonding_model_type_db").val()==1){//整体授信
			if($("#bonding_letter_path_db").val()==''){
				$("#bonding_letter_path_db_span").attr("class", "formtips onError");
				$("#bonding_letter_path_db_span").html("请上传担保函");
				return
			}else{
				$("#bonding_letter_path_db_span").attr("class", "formtips");
				$("#bonding_letter_path_db_span").html(""); 
			}
		}else{
			$("#bonding_letter_path_db_span").attr("class", "formtips");
			$("#bonding_letter_path_db_span").html(""); 
		}
	  
	 $.ajax({
		    type: "post",
		    url: "updateBondingCompanyData.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		    	 if(data.state==1){
		    		 alert('担保机构信息修改成功！');
		    		 $('#theme-popover4').hide();
		    		 searchBondingData(pageNumLoan);
		    	 }else{
		    		 alert('担保机构信息修改失败！');
		    	 };
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
		    }
		});
}

//添加担保机构
function addCompayBonding(){
	param={};
	param["bonding_name"] = $("#bonding_name").val();
	param["create_time"] = $("#create_time").val();
	param["status_bonding"] = $("#status_bonding").val();
	param["desc_bonding"] = $("#desc_bonding").val();
	param["bonding_level"] = $("#bonding_level").val();
	
	param["loan_comp_id"] = $("#loan_bonding_loanid").val();
	param["end_time"] = $("#loan_bonding_end_time").val();
	param["start_time"] = $("#loan_bonding_start_time").val();
	param["credit_limit"] = $("#loan_bonding_credit_limit").val();
	param["model_type"] = $("#loan_bonding_model_type").val();
	param["desc"] = $("#loan_bonding_desc").val();
	param["bonding_letter_path"] = $("#bonding_letter_path").val();
	param["loan_bonding_bondingid"] = $("#loan_bonding_bondingid").val();
	
	//校验担保机构
	if($("#loan_bonding_loanid").val()==''){
		alert('您尚未添加小贷公司！');
		return;
	}
	
	if($("#bonding_name").val()==''){
		$("#bonding_name_span").attr("class", "formtips onError");
		$("#bonding_name_span").html("请填写担保机构名称"); 
		return;
	}else{
		$("#bonding_name_span").attr("class", "formtips");
		$("#bonding_name_span").html(""); 
	}
	if($("#create_time").val()==''){
		$("#create_time_span").attr("class", "formtips onError");
		$("#create_time_span").html("准入时间不能为空"); 
		return;
	}else{
		$("#create_time_span").attr("class", "formtips");
		$("#create_time_span").html(""); 
	}
	
	if($("#desc_bonding").val()==''){
		$("#desc_bonding_span").attr("class", "formtips onError");
		$("#desc_bonding_span").html("请填写担保机构简介"); 
		return;
	}else{
		$("#desc_bonding_span").attr("class", "formtips");
		$("#desc_bonding_span").html(""); 
	}
	
	//开始时间
	if($("#loan_bonding_start_time").val()==''){
		$("#loan_bonding_start_time_span").attr("class", "formtips onError");
		$("#loan_bonding_start_time_span").html("请填写合作开始时间");
		return
	}else{
		$("#loan_bonding_start_time_span").attr("class", "formtips");
		$("#loan_bonding_start_time_span").html(""); 
	}
	
	//结束时间
	if($("#loan_bonding_end_time").val()==''){
		$("#loan_bonding_end_time_span").attr("class", "formtips onError");
		$("#loan_bonding_end_time_span").html("请填写合作结束时间");
		return
	}else if($("#loan_bonding_end_time").val()<$("#loan_bonding_start_time").val()){
		$("#loan_bonding_end_time_span").attr("class", "formtips onError");
		$("#loan_bonding_end_time_span").html("合作结束时间必须大于开始时间");
		return
	}else{
		$("#loan_bonding_end_time_span").attr("class", "formtips");
		$("#loan_bonding_end_time_span").html(""); 
	}
	
	//担保授信总额
	if($("#loan_bonding_credit_limit").val()==''){
		$("#loan_bonding_credit_limit_span").attr("class", "formtips onError");
		$("#loan_bonding_credit_limit_span").html("请填写授信总金额");
		return
	}else{
		
		var reg = /^[1-9]+\d*(\.[0-9]{2})?$/;
	    var obj = document.getElementById("loan_bonding_credit_limit");  
	    if(!reg.test(obj.value)){  
	    	$("#loan_bonding_credit_limit_span").attr("class", "formtips onError");
	    	$("#loan_bonding_credit_limit_span").html("输入不正确！"); 
	    	return
	    }else{
		   $("#loan_bonding_credit_limit_span").attr("class", "formtips");
		   $("#loan_bonding_credit_limit_span").html(""); 
	    }
	}
	
	//担保函校验
	if($("#loan_bonding_model_type").val()==1){//整体授信
		if($("#bonding_letter_path").val()==''){
			$("#bonding_letter_path_span").attr("class", "formtips onError");
			$("#bonding_letter_path_span").html("请上传担保函");
			return
		}else{
			$("#bonding_letter_path_span").attr("class", "formtips");
			$("#bonding_letter_path_span").html(""); 
		}
	}else{
		$("#bonding_letter_path_span").attr("class", "formtips");
		$("#bonding_letter_path_span").html(""); 
	}
	
	 
	$("#btn_bonding_submit").attr("disabled","disabled");
	var url = "addCompnayBonding.do";
	$.ajax({
	    type: "post",
	    url: url,
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	$("#btn_bonding_submit").removeAttr("disabled");
	    	if(data.status ==1){
	    		$('#loan_bonding_bondingid').val(data.id);
	    		alert("添加成功");
	    		$('#div_add_company_bonding').hide();
	    		themepopover3Cancel();//隐藏
	    		themepopover4Cancel();//隐藏
	    		searchBondingData(pageNumLoan);
	    	}else{
	    		alert(data.desc);
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	$("#btn_bonding_submit").removeAttr("disabled");
			alert(errorThrown);
	    }
	});
}

//添加小贷公司
function addCompayLoan(){
	//合作机构
	 
	if($("#loan_name").val()==''){
		$("#loan_company_name").attr("class", "formtips onError");
		$("#loan_company_name").html("请填写合作机构名称"); 
		return;
	}else{
		$("#loan_company_name").attr("class", "formtips");
		$("#loan_company_name").html(""); 
	}
	//机构logo
	if($("#loan_logo_path").val()==''){
		$("#loan_company_logo_path").attr("class", "formtips onError");
		$("#loan_company_logo_path").html("请上传合作机构logo");
		return
	}else{
		$("#loan_company_logo_path").attr("class", "formtips");
		$("#loan_company_logo_path").html(""); 
	}
	
	//授信总额
	if($("#total_amount").val()==''){
		$("#total_amount_span").attr("class", "formtips onError");
		$("#total_amount_span").html("请填写授信总金额");
		return
	}else{
		
		var reg = /^[1-9]+\d*(\.[0-9]{2})?$/;
	    var obj = document.getElementById("total_amount");  
	    if(!reg.test(obj.value)){  
	    	$("#total_amount_span").attr("class", "formtips onError");
	    	$("#total_amount_span").html("输入不正确！"); 
	    	return
	    }else{
		   $("#total_amount_span").attr("class", "formtips");
		   $("#total_amount_span").html(""); 
	    }
	}
	
	//偿债准备金比例
	if($("#insolvency_reserves_scale").val()==''){
		$("#insolvency_reserves_scale_span").attr("class", "formtips onError");
		$("#insolvency_reserves_scale_span").html("请填写偿债准备金比例");
		return
	}else{
		var reg2 = /^[0-9]{1}(\.[0-9]{1,2})?$/;
        var obj = document.getElementById("insolvency_reserves_scale").value;  
	    if(!reg2.test(obj)){
	    	$("#insolvency_reserves_scale_span").attr("class", "formtips onError");
			$("#insolvency_reserves_scale_span").html("输入不正确");
			return
	    }else{
	    	$("#insolvency_reserves_scale_span").attr("class", "formtips");
			$("#insolvency_reserves_scale_span").html("");
	    }
	}
	//渠道费比例
	if($("#channel_cost").val()==''){
		$("#channel_cost_span").attr("class", "formtips onError");
		$("#channel_cost_span").html("请填写渠道费比例");
		return
	}else{
		var reg3 = /^[0-9]{1}(\.[0-9]{1,2})?$/;
		var obj = document.getElementById("channel_cost").value;  
		if(!reg3.test(obj)){
			$("#channel_cost_span").attr("class", "formtips onError");
			$("#channel_cost_span").html("输入不正确");
			return
	    }else{
	    	$("#channel_cost_span").attr("class", "formtips");
			$("#channel_cost_span").html(""); 
	    }
		
	}
	
	//开始时间
	if($("#start_time").val()==''){
		$("#start_time_span").attr("class", "formtips onError");
		$("#start_time_span").html("请填写合作开始时间");
		return
	}else{
		$("#start_time_span").attr("class", "formtips");
		$("#start_time_span").html(""); 
	}
	
	//结束时间
	if($("#end_time").val()==''){
		$("#end_time_span").attr("class", "formtips onError");
		$("#end_time_span").html("请填写合作结束时间");
		return
	}else if($("#end_time").val()<$("#start_time").val()){
		$("#end_time_span").attr("class", "formtips onError");
		$("#end_time_span").html("合作结束时间必须大于开始时间");
		return
	}else{
		$("#end_time_span").attr("class", "formtips");
		$("#end_time_span").html(""); 
	}
	
	
	 
	param["loan_name"] = $("#loan_name").val();
	param["total_amount"] = $("#total_amount").val();
	param["available_total_amount"] = $("#available_total_amount").val();
	param["has_total_amount"] = $("#has_total_amount").val();
	param["insolvency_reserves_scale"] = $("#insolvency_reserves_scale").val();
	param["start_time"] = $("#start_time").val();
	param["end_time"] = $("#end_time").val();
	param["service_letter_path"] = $("#service_letter_path").val();
	param["bonding_required"] = $("#bonding_required").val();
	param["status"] = $("#loanState").val();
	param["desc"] = $("#loanDesc").val();
	param["loan_logo_path"] = $("#loan_logo_path").val();
	param["level"] = $("#level").val();
	param["channel_cost"] = $("#channel_cost").val();
	
	param["mortg_path"] = $("#upload_mortg_path").val();
	param["mortg_desc"] = $("#mortg_desc").val();
	param["num"] = m;
	// 添加图片资料
	var data_path = "";
	var data_desc = "";
	// 添加抵押物资料
	var mortg_path = "";
	var mortg_desc = "";
	if(n > 1){
		
		for(var i=1;i<n;i++){
			// 添加图片资料
			if($("#upload_data_path_"+i+"").val() != "" && $("#upload_data_path_"+i+"").val() != undefined){
				data_path += $("#upload_data_path_"+i+"").val() + ",";
				if($("#data_desc_"+i+"").val() == ""){
					data_desc += "无" + ",";
				}else{
					data_desc += $("#data_desc_"+i+"").val() + ",";
				}
			}
			// 添加抵押物资料
			if($("#upload_mortg_path_"+i+"").val() != "" && $("#upload_mortg_path_"+i+"").val() != undefined){
				mortg_path += $("#upload_mortg_path_"+i+"").val() + ",";
				if($("#mortg_desc_"+i+"").val() == ""){
					mortg_desc += "无" + ",";
				}else{
					mortg_desc += $("#mortg_desc_"+i+"").val() + ",";
				}
			}
		}
		param["data_path"] = data_path;
		param["data_desc"] = data_desc;
		param["mortg_path"] = mortg_path;
		param["mortg_desc"] = mortg_desc;
	}
	
	
	//基本资料校验
	if(data_path==''){
		$("#data_add_span").attr("class", "formtips onError");
		$("#data_add_span").html("请上传基本资料");
		return
	}else{
		$("#data_add_span").attr("class", "formtips");
		$("#data_add_span").html(""); 
	}
	
	//抵押物校验
	if($("#bonding_required").val()=='0'){//不需要担保机构--进行校验抵押物
	if(mortg_path==''){
	 
		$("#mortg_path_span").attr("class", "formtips onError");
		$("#mortg_path_span").html("请上传抵押物");
		return 
	}else{
		$("#mortg_path_span").attr("class", "formtips");
		$("#mortg_path_span").html(""); 
	}
	} 
	
	//合作机构简介
	if($("#loanDesc").val()==''){
		$("#loanDesc_span").attr("class", "formtips onError");
		$("#loanDesc_span").html("请填写合作机构简介信息");
		return
	}else{
		$("#loanDesc_span").attr("class", "formtips");
		$("#loanDesc_span").html("");
	}
	 
	//alert(data_path+"--------"+data_desc+"+++++++++"+mortg_path+"*********"+mortg_desc);
	 
	$("#btn_submit").attr("disabled","disabled");
	var url = "addCompnayLoan.do";
	$.ajax({
	    type: "post",
	    url: url,
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	$("#btn_submit").removeAttr("disabled");
	    	if(data.status ==1){
	    		alert("添加成功");
	    		$('#loan_bonding_loanid').val(data.id);
	    		$('#div_add_company_loan').hide();
	    		themepopover3Cancel();//隐藏
	    		themepopover4Cancel();//隐藏
	    		searchData(pageNumLoan);
	    	}else{
	    		alert(data.desc);
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	$("#btn_submit").removeAttr("disabled");
			alert(errorThrown);
	    }
	});
}

//添加小贷公司和担保机构关系
$('#btn_loan_bonding_submit').on('click',function(){
	param["loan_comp_id"] = $("#loan_bonding_loanid").val();
	param["bonding_comp_id"] = $("#loan_bonding_bondingid").val();
	param["end_time"] = $("#loan_bonding_end_time").val();
	param["start_time"] = $("#loan_bonding_start_time").val();
	param["end_time"] = $("#loan_bonding_end_time").val();
	param["credit_limit"] = $("#loan_bonding_credit_limit").val();
	param["model_type"] = $("#loan_bonding_model_type").val();
	param["desc"] = $("#loan_bonding_desc").val();
	param["bonding_letter_path"] = $("#bonding_letter_path").val();
	
	if($("#loan_bonding_loanid").val()==''){
		alert('您尚未添加小贷公司！');
		return;
	}
    if($("#loan_bonding_bondingid").val()==''){
    	alert('您尚未添加或选择担保机构！');
    	return;
	}
	
	$("#btn_loan_bonding_submit").attr("disabled","disabled");
	var url = "addCompnayLoanBonding.do";
	$.ajax({
	    type: "post",
	    url: url,
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	$("#btn_loan_bonding_submit").removeAttr("disabled");
	    	if(data.status ==1){
	    		$('#loan_bonding_id').val(data.id);
	    		searchLoanBondingData(pageNumLoan);
	    		alert("添加成功");
	    	}else{
	    		alert(data.desc);
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	$("#btn_submit").removeAttr("disabled");
			alert(errorThrown);
	    }
	}); 
});

//添加小贷公司按钮
function addCompanyLoan(){
	if($("#div_add_company_loan").is(":hidden")){
		$("#div_add_company_loan").show();
	}else{
		$("#div_add_company_loan").hide();
	}
}

//添加担保机构按钮
function addBondingCompanyBtn(){
	if($("#div_add_company_bonding").is(":hidden")){
		$("#div_add_company_bonding").show();
	}else{
		$("#div_add_company_bonding").hide();
	}
}

//添加小贷公司和担保机构关系按钮
function addLoanBondingBtn(){
	if($("#div_add_company_loan_bonding").is(":hidden")){
		$("#div_add_company_loan_bonding").show();
	}else{
		$("#div_add_company_loan_bonding").hide();
	}
}

//添加小贷公司管理员按钮
function addLoanCompanyAdmin(){
	if($("#loanCompanyAdmin").is(":hidden")){
		$("#loanCompanyAdmin").show();
	}else{
		$("#loanCompanyAdmin").hide();
	}
}

function searchLoanData(){
	searchData(pageNumLoan);
}

function searchBondindBtnData(){
	searchBondingData(pageNumLoan);
}

function searchloanBondingCompanyBtnData(){
	searchLoanBondingData(pageNumLoan);
}

//查询小贷公司
function searchData(pageNumLoan){
	var param = {};
	param['loanCompanyName']=  $('#loanCompanyName').val();
	param["pageNum"] = pageNumLoan+1;
	param["pageSize"] = pageSize;
     
	$.ajax({
	    type: "post",
	    url: "queryAllCompanyLoan.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	$("#loading").hide();
	    	
	    	var tr_list_o = $("#tab_content");
	    	tr_list_o.empty();
	    	
	    	var strHtml = "";
	    	strHtml +="<tr class='gvHeader'>";
    		strHtml +="<th scope='col'>"+'编号'+"</th>";
    		strHtml +="<th scope='col'>"+'名称'+"</th>";
    		strHtml +="<th scope='col'>"+'授信总额'+"</th>";
    		strHtml +="<th scope='col'>"+'授信余额'+"</th>";
    		strHtml +="<th scope='col'>"+'授信占用额'+"</th>";
    		strHtml +="<th scope='col'>"+'偿还准备金比例'+"</th>";
    		strHtml +="<th scope='col'>"+'渠道费比例'+"</th>";
    		strHtml +="<th scope='col'>"+'合作开始日期'+"</th>";
    		strHtml +="<th scope='col'>"+'合作结束日期'+"</th>";
    		strHtml +="<th scope='col'>"+'是否必须按担保机构'+"</th>";
    		strHtml +="<th scope='col'>"+'状态'+"</th>";
    		strHtml +="<th scope='col'>"+'操作'+"</th>";
    		strHtml +="</tr>";
    		
    		
    		totalNum = data.totalNum;
	    	$("#Pagination").pagination(totalNum, {  
	    	    callback: PageCallbackLoan, 
	    	    maxentries:4,
	    	    prev_text: '上一页',  
	    	    next_text: '下一页',  
	    	    items_per_page: pageSize,  //每页显示条目数
	    	    num_display_entries: 6,//连续分页主体部分分页条目数  
	    	    current_page: pageNumLoan,//当前页索引  
	    	    num_edge_entries: 2//两侧首尾分页条目数  
	    	}); 
	    	
	    	if(data.result != null && data.result != undefined && data.result != ''){
	    		for(var i=0;i<data.result.length;i++){
		    		var o = data.result[i];
		    		strHtml +="<tr>";
		    		strHtml +="<td class='f99' align='center'>"+(i+1)+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.name+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.total_amount+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.available_total_amount+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.has_total_amount+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.insolvency_reserves_scale+"%</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.channel_cost+"%</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.start_time_f+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.end_time_f+"</td>";
		    		if(o.bonding_required==1){
		    			strHtml +="<td class='f99' align='center'>"+'必选'+"</td>";
		    		}
		    		if(o.bonding_required==0){
		    			strHtml +="<td class='f99' align='center'>"+'可选'+"</td>";
		    		}
		    		if(o.status==1){
		    		  strHtml +="<td class='f99' align='center'>"+'启用'+"</td>";
		    		  strHtml +="<td class='f99' align='center'>"+'<a href="###" onclick="editeLoanCompany('+"'"+o.id+"'"+','+"'"+o.name+"'"+','+"'"+o.total_amount+"'"+','+"'"+o.available_total_amount+"'"+','+"'"+o.has_total_amount+"'"+','+"'"+o.insolvency_reserves_scale+"'"+','+"'"+o.start_time_f+"'"+','+"'"+o.end_time_f+"'"+','+"'"+o.bonding_required+"'"+','+"'"+o.status+"'"+','+"'"+o.service_letter_path+"'"+','+"'"+o.desc+"'"+','+"'"+o.level+"'"+','+"'"+o.channel_cost+"'"+','+"'"+o.loan_logo_path+"'"+')">编辑</a>,<a href="###" onclick="deleteCompanyLoanData('+o.id+',0)">禁用</a>,<a href="###" onclick="findLoanCompanyAdmin('+o.user_id+')">管理员</a>'+"</td>";
						
		    		}
		    		if(o.status==0){
			    		  strHtml +="<td class='f99' align='center'>"+'禁用'+"</td>";
			    		  strHtml +="<td class='f99' align='center'>"+'<a href="###" onclick="editeLoanCompany('+"'"+o.id+"'"+','+"'"+o.name+"'"+','+"'"+o.total_amount+"'"+','+"'"+o.available_total_amount+"'"+','+"'"+o.has_total_amount+"'"+','+"'"+o.insolvency_reserves_scale+"'"+','+"'"+o.start_time_f+"'"+','+"'"+o.end_time_f+"'"+','+"'"+o.bonding_required+"'"+','+"'"+o.status+"'"+','+"'"+o.service_letter_path+"'"+','+"'"+o.desc+"'"+','+"'"+o.level+"'"+','+"'"+o.channel_cost+"'"+','+"'"+o.loan_logo_path+"'"+')">编辑</a>,<a href="###" onclick="deleteCompanyLoanData('+o.id+',1)">启用</a>,<a href="###" onclick="findLoanCompanyAdmin('+o.user_id+')">管理员</a>'+"</td>";
							
			    	}
		    		 
		    		strHtml +="</tr>";
		    	}
	    		tr_list_o.append(strHtml);
	    	}else{
	    		if(data.status=='0'){
	    			alert(data.desc);
	    		}else{
	    			//alert('没有数据！');
	    		}
	    	
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	       alert(errorThrown);
	    }
	});
	
}

//查询担保机构
function searchBondingData(pageNumLoan){
	var param = {};
	param['bondingCompanyName']=  $('#bondingCompanyName').val();
	param["pageNum"] = pageNumLoan+1;
	param["pageSize"] = pageSize;
	
	$.ajax({
	    type: "post",
	    url: "queryAllCompanyBonding.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	 
	    	var tr_list_o = $("#tab_bonding_content");
	    	tr_list_o.empty();
	    	
	    	var strHtml = "";
	    	strHtml +="<tr class='gvHeader'>";
    		strHtml +="<th scope='col'>"+'编号'+"</th>";
    		strHtml +="<th scope='col'>"+'担保机构'+"</th>";
    		strHtml +="<th scope='col'>"+'准入时间'+"</th>";
    		strHtml +="<th scope='col'>"+'状态'+"</th>";
    		strHtml +="</tr>";
	    	
	    	totalNum = data.totalNum;
	    	$("#PaginationBoading").pagination(totalNum, {  
	    	    callback: PageCallbackBonding, 
	    	    maxentries:4,
	    	    prev_text: '上一页',  
	    	    next_text: '下一页',  
	    	    items_per_page: pageSize,  //每页显示条目数
	    	    num_display_entries: 6,//连续分页主体部分分页条目数  
	    	    current_page: pageNumLoan,//当前页索引  
	    	    num_edge_entries: 2//两侧首尾分页条目数  
	    	}); 
	    	
	    	if(data.result != null && data.result != undefined && data.result != ''){
	    		for(var i=0;i<data.result.length;i++){
		    		var o = data.result[i];
		    		strHtml +="<tr>";
		    		strHtml +="<td class='f99' align='center'>"+(i+1)+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.name+"</td>";
		    		strHtml +="<td class='f99' align='center'>"+o.in_time_f+"</td>";
		    		 
		    		if(o.status==1){
			    		  strHtml +="<td class='f99' align='center'>"+'启用'+"</td>";
			    	}
			    	if(o.status==0){
				    	  strHtml +="<td class='f99' align='center'>"+'禁用'+"</td>";
				    }
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

//查询小贷公司 和 担保机构关系
function searchLoanBondingData(pageNumLoan){
	var param = {};
	param['loanCompanyNames']=  $('#loanCompanyNames').val();
	param['bondingCompanyNames']=  $('#bondingCompanyNames').val();
	param["pageNum"] = pageNumLoan+1;
	param["pageSize"] = pageSize;
	
	$.ajax({
	    type: "post",
	    url: "queryCompnayLoanBondingPage.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	    	 
	    	var tr_list_o = $("#tab_loan_bonding_content");
	    	tr_list_o.empty();
	    	
	    	totalNum = data.totalNum;
	    	$("#PaginationLoanBonding").pagination(totalNum, {  
	    	    callback: PageCallbackLoanBonding, 
	    	    maxentries:4,
	    	    prev_text: '上一页',  
	    	    next_text: '下一页',  
	    	    items_per_page: pageSize,  //每页显示条目数
	    	    num_display_entries: 6,//连续分页主体部分分页条目数  
	    	    current_page: pageNumLoan,//当前页索引  
	    	    num_edge_entries: 2//两侧首尾分页条目数  
	    	}); 
	    	
	    	if(data.result != null && data.result != undefined && data.result != ''){
		    	var strHtml = "";
	    		for(var i=0;i<data.result.length;i++){
		    		var o = data.result[i];
		    		strHtml +="<tr>";
		    		strHtml +="<td class='f99' align='center' >"+(i+1)+"</td>";
		    		strHtml +="<td class='f99' align='center' >"+o.loan_name+"</td>";
		    		strHtml +="<td class='f99' align='center' >"+o.bonding_name+"</td>";
		    		strHtml +="<td class='f99' align='center' >"+o.start_time+"</td>";
		    		strHtml +="<td class='f99' align='center' >"+o.end_time+"</td>";
		    		strHtml +="<td class='f99' align='center' >"+o.model_type+"</td>";
		    		strHtml +="<td class='f99' align='center' >"+o.desc+"</td>";
		    		strHtml +="<td class='f99' align='center' >编辑</td>";
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

//根据选中下拉框中的担保机构进行自动填充数据

function ajaxQueryCompanyBondingByIdData(){
	var value=document.getElementById("sel_bonding_list").value;
	if(value=='0'){
		  $('#loan_bonding_bondingid').val('');
	      $('#bonding_name').val('');
	      $('#create_time').val('');
	      $('#desc_bonding').val('');
	      
	      $('#bonding_name').attr("disabled",false);
	      $('#create_time').attr("disabled",false);
	      $('#desc_bonding').attr("disabled",false);
	      $('#status_bonding').attr("disabled",false);
	      $('#bonding_level').attr("disabled",false);
	      
	      return;
	}
	var param = {};
	param["bondingId"] = value;
	
	$.ajax({
	    type: "post",
	    url: "ajaxQueryCompanyBondingById.do",
	    dataType: "json",
	    data:param,
	    success: function (data) {
	      $('#loan_bonding_bondingid').val(data.result.id);
	      $('#bonding_name').val(data.result.name);
	      $('#create_time').val(data.result.create_time_f);
	      $('#desc_bonding').val(data.result.desc);
	      
	      //状态  0禁用 1启用
	      var obj1=document.getElementById("status_bonding");
	      for(i=0;i<=obj1.options.length;i++){  
	       if((obj1.options[i].value)==parseInt(data.result.status)){      
	    	   obj1.options[i].selected=true;   
		        break;
	       }
	      }
	      
	      //担保机构评级 
	      //alert(data.result.bonding_level);
	      var obj1=document.getElementById("bonding_level");
	      for(i=0;i<=obj1.options.length;i++){  
	       if((obj1.options[i].value)==parseInt(data.result.bonding_level)){      
	    	   obj1.options[i].selected=true;   
		        break;
	       }
	      }
	      
	      
	      
	      $('#bonding_name').attr("disabled",true);
	      $('#create_time').attr("disabled",true);
	      $('#desc_bonding').attr("disabled",true);
	      $('#status_bonding').attr("disabled",true);
	      $('#bonding_level').attr("disabled",true);
	      
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	       alert(errorThrown);
	    }
	});
	
}
 
//添加小额公司超级管理员TODO
function btn_saveLoadUser(){
	  
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		var obj = document.getElementsByName("paramMap.enable");
		    for(var i=0; i<obj.length; i ++){
		        if(obj[i].checked){
		        	param["enable"] = obj[i].value;
		        }
		    }
		param["userName"] = $("#userNames").val();
		param["password"] = $("#password").val();
		param["confirmPassword"] = $("#confirmPassword").val();
		param["email"] = $("#email").val();
		param["telphone"] = $("#telphone").val();
		param["loan_bonding_loanid"] = $("#loan_bonding_loanid").val();
		
		//校验
		 
		if($("#loan_bonding_loanid").val()==''){
			$("#loanCompanyCheck").attr("class", "formtips onError");
			$("#loanCompanyCheck").html("您尚未添加合作机构");
			return;
		}else{
			$("#loanCompanyCheck").attr("class", "formtips");
			$("#loanCompanyCheck").html(""); 
		}
		 
		if($("#userNames").val()=='' || $("#userNames").val().length>16 || $("#userNames").val().length<1){
			$("#userNames_span").attr("class", "formtips onError");
			$("#userNames_span").html("请填写用户名,长度1~16位");
			return;
		}else{
			$("#userNames_span").attr("class", "formtips");
			$("#userNames_span").html(""); 
		}
		 
		if($("#password").val()==''){
			$("#password_span").attr("class", "formtips onError");
			$("#password_span").html("密码不能为空");
			return;
		}else{
			$("#password_span").attr("class", "formtips");
			$("#password_span").html(""); 
		}
		
		if($("#confirmPassword").val()==''){
			$("#confirmPassword_span").attr("class", "formtips onError");
			$("#confirmPassword_span").html("确认密码不能为空");
			return;
		}else{
			if($("#confirmPassword").val()!=$("#password").val()){
				$("#confirmPassword_span").attr("class", "formtips onError");
				$("#confirmPassword_span").html("密码输入不一致");
				return;
			}else{
				$("#confirmPassword_span").attr("class", "formtips");
				$("#confirmPassword_span").html(""); 
			}
		}
		 
		var reg = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		if(!reg.test($("#telphone").val())){  
	    	$("#telphone_span").attr("class", "formtips onError");
	    	$("#telphone_span").html("电话号码格式不正确！"); 
	    	return;
	    }else{
		   $("#telphone_span").attr("class", "formtips");
		   $("#telphone_span").html(""); 
	    }
		
		var reg = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		if(!reg.test($("#telphone").val())){  
	    	$("#telphone_span").attr("class", "formtips onError");
	    	$("#telphone_span").html("电话号码格式不正确！"); 
	    	return;
	    }else{
		   $("#telphone_span").attr("class", "formtips");
		   $("#telphone_span").html(""); 
	    }
		
		var emailREG = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
		if(!emailREG.test($("#email").val())){
			$("#email_span").attr("class", "formtips onError");
	    	$("#email_span").html("邮箱格式不正确！"); 
	    	return;
		}else{
		    $("#email_span").attr("class", "formtips");
			$("#email_span").html(""); 
		}
		 
		//判断唯一性 
		$.ajax({
			    type: "post",
			    url: "findLoanCompanyAdminUserByLoanId.do",
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	if(data.state ==1){
			    	  alert('该小贷公司已经存在超级管理员');
			    	} 
			    	if(data.state ==0){
			    		$.ajax({//添加
			    		    type: "post",
			    		    url: "addLoanAdmin.do",
			    		    dataType: "json",
			    		    data:param,
			    		    success: function (data) {
			    		    	if(data.status ==1){
			    		    		alert("超级用户添加成功");
			    		    		$('#loanCompanyAdmin').hide();
			    		    		searchData(pageNumLoan);
			    		    	}else if(data.status ==0){
			    		    		alert("用户名已经存在");
			    		    	}
			    		    },
			    		    error: function (XMLHttpRequest, textStatus, errorThrown) {
			    				alert(errorThrown);
			    		    }
			    		}); 
			    	} 
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
			    }
		}); 
		 
}



//取消编辑小贷公司
function themepopover3Cancel(){
	 $('#theme-popover3').hide();
}

//取消编辑担保机构
function themepopover4Cancel(){
	 $('#theme-popover4').hide();
}

//在编辑小贷公司中添加担保机构
function addLoanCompanyBtn(){
	addBondingCompanyBtn();
	 $('#theme-popover3').hide();
}

//在编辑小贷公司中添加管理员
function addLoanCompanyAdminBtn(){
	addLoanCompanyAdmin();
	 $('#theme-popover3').hide();
}

//反选授信余额
function setValueAvailableTotalAmount(){
	$("#available_total_amount").val($("#total_amount").val());
}

//查询小贷公司管理员信息
function findLoanCompanyAdmin(userId){
     if(userId==null || userId==''){
    	 $('#adminUserDiv').hide();
    	 alert('该小贷公司尚未开通管理员用户,请在编辑中开通！');
    	 return;
     }
     var param = {};
     param["userId"] = userId;
     $.ajax({
		    type: "post",
		    url: "findLoanCompanyAdminUserById.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		        
		        $('#userNameL').html(data.paramMap.userName);
		        $('#phoneL').html(data.paramMap.telphone);
		         
		        $('#emailL').html(data.paramMap.email);
		        if(data.paramMap.enable==1){
		        	 $('#enableL').html('启用');
		        }
		        if(data.paramMap.enable==2){
		        	 $('#enableL').html('禁用');
		        }
		       
		        
		    	$('#adminUserDiv').show();
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
		    }
		});
}

//添加时是否必选三方担保whb
function changeBonding(bondingValue){
	//0:需上传抵押物
	if(0 == bondingValue){
		$("#li_mortg_title").show();
		$("#li_mortg_content").show();
	}else if(1 == bondingValue){
		$("#li_mortg_title").hide();
		$("#li_mortg_content").hide();
	}
}
//修改时是否必选三方担保whb
function changeBondingUpdate(bondingValue){
	//0:需上传抵押物
	if(0 == bondingValue){
		$("#li_mortg_title_update").show();
		$("#li_mortg_content_update").show();
	}else if(1 == bondingValue){
		$("#li_mortg_title_update").hide();
		$("#li_mortg_content_update").hide();
	}
}
//添加抵押物资料 whb
function data_mortg(){
	
	var dataContent = $("#li_mortg_content");
	//判断是否修改
	if(n < 0){
	   	dataContent = $("#li_mortg_content_update");
	}
	var strHtml = "<div id = 'mortg_div_" + n + "'>";
  	strHtml += "<input type='hidden' id='upload_mortg_path_" + n + "' value='' />";
  	strHtml += "<label id='mortg_img_data_" + n + "'>抵押物图片:</label>";
  	strHtml += "<img id='upload_mortg_path_img_" + n + "' src='' width='62' height='62' style='position:relative; top:15px;'/>&nbsp;";
	strHtml +="<a href='###' id='upload_mortg_" + n + "' class='scbtn'  onclick='upload_mortg_path_a(" + n + ")' style='font-size:12px;'>上传图片</a>&nbsp;";
	strHtml +="<a href='###' id='delete_mortg_" + n + "' class='scbtn'  onclick='delete_mortg(" + n + ")' style='font-size:12px;'>删除</a><br/>";
	strHtml +="<label id='img_mortg_desc_" + n + "' style='position:relative;'>抵押物描述:</label>";
	strHtml +="<textarea id='mortg_desc_" + n + "' rows='5' cols='15' style=' height:70px; width:260px;'></textarea><br/>";
	strHtml += "</div>";
	dataContent.append(strHtml);
	n++;
}

//添加抵押物删除图片
function delete_mortg(id){
	$("#mortg_div_"+id+"").remove();
	/* $("#upload_mortg_path_"+id+"").remove();
	$("#mortg_img_data_"+id+"").remove();
	$("#upload_mortg_path_img_"+id+"").remove();
	$("#upload_mortg_"+id+"").remove();
	$("#delete_mortg_"+id+"").remove();
	$("#img_mortg_desc_"+id+"").remove();
	$("#mortg_desc_"+id+"").remove(); */
}

//上传抵押物图片
function upload_mortg_path_a(n){
	m = n;
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'mortgPath/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadMortgPathCall','cp':'upload_mortg_path_img_"+n+"'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#upload_mortg_path_img_"+n+"").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	} 
}

//上传抵押物图片路径回调	    	
function uploadMortgPathCall(basepath,fileName,cp){
	if(cp == "upload_mortg_path_img_"+m+""){
		var path = "upload/"+basepath+"/"+fileName;
		$("#upload_mortg_path_img_"+m+"").attr("src","../"+path);
		$("#upload_mortg_path_"+m+"").val(path);
	}
}

//  修改 上传抵押物图片
function upload_mortg_path_update_a(id){
	up = id;
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'mortgPath/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadMortgPathUpdateCall','cp':'upload_mortg_path_update_img_" + id + "'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#upload_mortg_path_update_img_" + id + "").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	} 
}

// 修改上传抵押物图片路径回调	    	
function uploadMortgPathUpdateCall(basepath,fileName,cp){
	if(cp == "upload_mortg_path_update_img_" + up + ""){
		var path = "upload/"+basepath+"/"+fileName;
		$("#upload_mortg_path_update_img_" + up + "").attr("src","../"+path);
		$("#upload_mortg_path_update_" + up + "").val(path);
	}
}

//添加上传资料 whb
function data(){
	
	var dataContent = $("#li_data_content");
	//判断是否修改
	if(n < 0){
	   	dataContent = $("#li_data_content_update");
	}
   	//dataContent.empty();
  	var strHtml = "<div id = 'div_" + n + "'>";
  	strHtml += "<input type='hidden' id='upload_data_path_" + n + "' value='' />";
  	strHtml += "<label id='img_data_" + n + "'>图片:</label>";
  	strHtml += "<img id='upload_data_path_img_" + n + "' src='' width='62' height='62' style='position:relative; top:15px;'/>&nbsp;";
	strHtml +="<a href='###' id='upload_data_" + n + "' class='scbtn'  onclick='upload_data_path_a(" + n + ")' style='font-size:12px;'>上传图片</a>&nbsp;";
	strHtml +="<a href='###' id='delete_data_" + n + "' class='scbtn'  onclick='delete_data(" + n + ")' style='font-size:12px;'>删除</a><br/>";
	strHtml +="<label id='img_data_desc_" + n + "' style='position:relative;'>图片描述:</label>";
	strHtml +="<textarea id='data_desc_" + n + "' rows='5' cols='15' style=' height:70px; width:260px;'></textarea><br/>";
	strHtml += "</div>";
	dataContent.append(strHtml);
	n++;
}

//添加方法删除图片
function delete_data(id){
	$("#div_"+id+"").remove();
	/* $("#upload_data_path_"+id+"").remove();
	$("#img_data_"+id+"").remove();
	$("#upload_data_path_img_"+id+"").remove();
	$("#upload_data_"+id+"").remove();
	$("#delete_data_"+id+"").remove();
	$("#img_data_desc_"+id+"").remove();
	$("#data_desc_"+id+"").remove(); */
}

//修改方法删除图片
function delete_data_update(id){
	 
	if(confirm("确定要删除吗?")){
		var param = {}
		param["id"] = id;
	    $.ajax({
			    type: "post",
			    url: "deleteCompnayImgData.do",
			    dataType: "json",
			    data:param,
			    success: function (data) {
			    	if(data.state == 1){
			    		
			    		//基本资料图片
				    	$("#div_update_"+id+"").remove();
				    	/* $("#upload_data_path_update_"+id+"").remove();
				    	$("#img_data_update_"+id+"").remove();
				    	$("#upload_data_path_update_img_"+id+"").remove();
				    	$("#upload_data_update_"+id+"").remove();
				    	$("#delete_data_update_"+id+"").remove();
				    	$("#img_data_desc_update_"+id+"").remove();
				    	$("#data_desc_update_"+id+"").remove(); */
				    	//抵押物图片
				    	$("#mortg_div_update_"+id+"").remove();
				    	/* $("#upload_mortg_path_update_"+id+"").remove();
				    	$("#mortg_img_data_update_"+id+"").remove();
				    	$("#upload_mortg_path_update_img_"+id+"").remove();
				    	$("#upload_mortg_update_"+id+"").remove();
				    	$("#delete_mortg_update_"+id+"").remove();
				    	$("#img_mortg_desc_update_"+id+"").remove();
				    	$("#mortg_desc_update_"+id+"").remove(); */
				    	
			    	}else if(data.state == 0){
			    		alert("删除失败!");
			    	}
			    },
			    error: function (XMLHttpRequest, textStatus, errorThrown) {
					alert(errorThrown);
			    }
		});
    }
}

//上传图片
function upload_data_path_a(n){
	m = n;
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'data_img/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadDataPathCall','cp':'upload_data_path_img_" + n + "'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#upload_data_path_img_" + n + "").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}

//上传图片路径回调	    	
function uploadDataPathCall(basepath,fileName,cp){
	if(cp == "upload_data_path_img_"+m+""){
		var path = "upload/"+basepath+"/"+fileName;
		//alert("000"+m);
		$("#upload_data_path_img_"+m+"").attr("src","../"+path);
		$("#upload_data_path_"+m+"").val(path);
	}
}

//  修改 上传图片
function upload_data_path_update_a(id){
	up = id;
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'data_img/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadDataPathUpdateCall','cp':'upload_data_path_update_img_" + id + "'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#upload_data_path_update_img_" + id + "").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	} 
}

// 修改上传图片路径回调	    	
function uploadDataPathUpdateCall(basepath,fileName,cp){
	if(cp == "upload_data_path_update_img_" + up + ""){
		var path = "upload/"+basepath+"/"+fileName;
		$("#upload_data_path_update_img_" + up + "").attr("src","../"+path);
		$("#upload_data_path_update_" + up + "").val(path);
	}
}

//删除小贷公司 
function  deleteCompanyLoanData(id,m){
	 var srr = '';
	 if(m==0){
		 str = '确定禁用该合作机构吗？禁用后该机构下所有用户都将不可登陆系统！';
	 }
	 if(m==1){
		 str = '确定重新启用该合作机构吗？启用后该机构下所有用户都将可重新登陆系统！';
	 }
	 var param = {};
	 param["id"] = id;
	 param["temp"] = m;
	 if(confirm(str)){
		
	   
	    $.ajax({
		    type: "post",
		    url: "deleteCompanyLoan.do",
		    dataType: "json",
		    data:param,
		    success: function (data) {
		        if(data.state==1){
		        	if(m==0){
		        	  alert('禁用成功！');
		        	}
		        	if(m==1){
			          alert('启用成功！');
			        }
		        	searchData(pageNumLoan);
		        }else{
		        	if(m==0){
			          alert('禁用失败！');
			        }
			        if(m==1){
				       alert('启用失败！');
				    }
		        };
		        
		    },
		    error: function (XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
		    }
		});
	 }
}

//自动算出小贷公司授信余额
function hasTotalAmountSum(){
	var values =  $('#total_amount_u').val()-$('#has_total_amount_u').val();
	
	if($('#total_amount_u').val()-$('#has_total_amount_u').val()<0){
		$("#total_amount_u_span").attr("class", "formtips onError");
		$("#total_amount_u_span").html("授信总金额必须大于或者等于授信占用额"+$('#has_total_amount_u').val()+"元");
	}else{
		  $("#total_amount_u_span").attr("class", "formtips");
		  $("#total_amount_u_span").html(""); 
		  $("#available_total_amount_u_span").attr("class", "formtips");
		  $("#available_total_amount_u_span").html(""); 
		  $('#available_total_amount_u').val(values);
	} 
	
}

</script>
