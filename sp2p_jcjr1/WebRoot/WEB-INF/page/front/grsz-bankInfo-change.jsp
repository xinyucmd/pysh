<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${sitemap.siteName}</title>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/css.css" />
    <style type="text/css">
    .form_s{
 	width:226px;
 	height:30px;
 	 padding:0 10px;
 	 border:1px solid #96ccf4;
 	border-radius:3px;
}
td{
padding:10px 0;
}
.form_s:focus {
  border-color: #66afe9;
  outline: 0;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
          box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
}
    </style>
<script type="text/javascript">
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


function bank_card_path_a_u(){
    var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'bankCardIdentify/"+dir+"','fileLimitSize':1,'title':'上传图片','cfn':'uploadBank_card_u','cp':'bank_card_path_img_u'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#bank_card_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}
  	
function uploadBank_card_u(basepath,fileName,cp){
	if(cp == "bank_card_path_img_u"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#bank_card_path_img_u").attr("src","../"+path);
		$("#bank_card_path_u").val(path);
	}
}

function identify_path_a_u(){
    var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'bankCardIdentify/"+dir+"','fileLimitSize':1,'title':'上传图片','cfn':'identify_call_u','cp':'identify_path_img_u'}";
	json = encodeURIComponent(json);
	window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
	var headImgPath = $("#identify_path_img").attr("src");
	if(headImgPath ==""){
		alert("上传失败！");	
	}
}
  	
function identify_call_u(basepath,fileName,cp){
	if(cp == "identify_path_img_u"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#identify_path_img_u").attr("src","../"+path);
		$("#identify_path_u").val(path);
	}
}

</script>
</head>


<body>

<div class="nymain" style="width: 600px;">
  <div class="wdzh" style="width: 600px;">
    <div class="r_main" style="width: 600px;">
<div class="box" style="width: 600px;" >
        <div class="boxmain2" style="padding-top:10px; width: 600px;" >
         <div class="biaoge2" style="margin-top:0px; width: 600px;" >
    <table  width="600px;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="center" style="padding:0px 0px 10px 10px;font-size:16px;"> 变更银行卡信息</th>
    </tr>
    <tr >
    <td width="27%" align="right" style="font-size:14px;">真实姓名：</td>
    <td width="83%">${realName }<span class="txt"></span></td>
  </tr>
    <tr>
    <td width="18%" align="right" style="font-size:14px;">原银行卡号：</td>
    <td width="83%">${bankCard }<span class="txt"></span></td>
  </tr>
  
  <tr>
    <td align="right" style="font-size:14px;">开户行名称：</td>
    <td><input type="text" class="form_s" placeholder="XX银行" id="bankName_" />
      <span class="txt" style="color: red;"></span></td>
  </tr>
  <tr>
    <td align="right" style="font-size:14px;">支行名称：</td>
    <td><input type="text" class="form_s" placeholder="XX支行" id="subBankName_" />
      <span class="txt" style="color: red;"></span></td>
  </tr>
  <tr>
    <td align="right" style="font-size:14px;">银行卡所属省：</td>
    <td><select id="province_name" style="width: 100px;height:30px;border:1px solid #96ccf4;border-radius:3px;">
                     				<option>请选择</option>
                				</select>
      <span class="txt" style="color: red;"></span></td>
  </tr>
  <tr>
    <td align="right" style="font-size:14px;">银行卡所属市：</td>
    <td><select id="city_name" style="width: 100px;height:30px;border:1px solid #96ccf4;border-radius:3px;">
                    			 <option>请选择</option>
               			 </select>
      <span class="txt" style="color: red;"></span></td>
  </tr>
  <tr>
    <td align="right" style="font-size:14px;">已绑定手机：</td>
    <td><a id="cellPhone1"></a>
      <!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:parent.location.href='boundcellphone.do'" style="color:red;">修改</a> -->
    </td>
  </tr>
  <tr>
  <td align="right" style="font-size:14px;">手机验证码：</td>
  <td>
      <input type="text" style="width:110px;" class="form_s" placeholder="请输入验证码"  id="randomCode"/>
      <a href="###" id="" class="scbtn" onclick="sendphoneCode()" style="font-size:14px;">获取验证码</a>
     
    </td>
    </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-bottom: 10px;">
	    <a href="javascript:update_BankBaseInfo();" class="bcbtn">修改</a><br />
	    <input type="checkbox" id="chkAll" onclick="showOrHideDIV()"/><span style=" color: red;font-size:14px;">修改银行卡号</span> 
    </td>
  </tr>
  </table>
 <div id = "bankDIV" style="display: none;">
	  <table width="100%;" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td align="right" style="font-size:14px;">新的卡号：</td>
	    <td><input type="text" class="form_s" id="bankCard_" />
	      <span class="txt" style="color: red;"></span></td>
	  </tr>
	  
	  <tr>
	      <td align="right" style="font-size:14px;">银行卡变更证明：</td>
	      <td>
	        <input type="hidden" id="bank_card_path_u"  value="" />
			<img id="bank_card_path_img_u" src="../images/NoImg.GIF" width="62" height="62" style="position:relative; top:15px;"/> 
			<a href="###" id="" class="scbtn"  onclick="bank_card_path_a_u()" style="font-size:12px;">上传</a>
			<span style=" color: red;font-size:14px;">请上传新银行卡开户证明</span> 
	      </td>
	  </tr> 
	  
	  <tr>
	      <td align="right" style="font-size:14px;">本人身份证：</td>
	      <td style="height:100px;">
	        <input type="hidden" id="identify_path_u"  value="" />
			<img id="identify_path_img_u" src="../images/NoImg.GIF" width="62" height="62" style="position:relative; top:15px;"/> 
			<a href="###" id="" class="scbtn"  onclick="identify_path_a_u()" style="font-size:12px;">上传</a>
	      </td>
	  </tr>
	  
	  <tr>
	    <td align="right" style="font-size:14px;">交易密码：</td>
	    <td><input type="password" class="form_s" id="dealpwd_" />
	      <span class="txt" style="color: red;"></span></td>
	  </tr>
	  <tr>
	    <td align="right">&nbsp;</td>
	    <td style="padding-top:10px;">
		    <a href="javascript:changeBankInfo();" class="bcbtn" style="font-size:14px;">提交变更</a>
	    </td>
	  </tr>
	   <tr>
	  		<td colspan="2"><span style="color: red; float: none;" id="bk1_tip_" class="formtips"></span></td>
	  </tr>
	</table>
	</div>

    </div>
    </div>
</div>
    </div>
  </div>
</div>

<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jquery.shove-1.0.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
<script>
$(function() {
    //加载省份列表信息
    $.ajax({
        url: "/script/Area.xml",
        dataType: "xml",
        success: function (xml) {
            $(xml).find("province").each(function () { //找到(province)省份节点;
                $("<option></option>").html($(this).attr("name")).appendTo("#province_name");//加载(province)省份信息到列表中
            })
       }
   })
   //省份列表信息更改时，加载城市列表信息
   $("#province_name").change(function () {
       var value = $("#province_name").val();//省份值;
       if (value != "请选择") {
    	   $("#city_name").find("option").remove();//显示城市下拉列表框删除城市下拉列表中的数据;
      	 	$("#city_name").html("<option>请选择</option>");   
           $.ajax({
               url: "/script/Area.xml",
               dataType: "xml",
               success: function (xml) {
                   $(xml).find("[name='" + value + "']").find("city").each(function () {//根据省份name属性得到子节点City节点name属性;
                       $("<option></option>").html($(this).attr("name")).appendTo("#city_name");//加载City(城市)信息到下拉列表中;
                   })
               }
           })
       }
   })
   var tt = '${map1.cellPhone}'==''?'':'${map1.cellPhone}';
    var len1 = tt.length;
    var tmplen1;
    var tmplen2;
    if(len1!=0){
    tmplen1 = tt.substr(0,3);
    tmplen2 = tt.substr(7,11);
    $('#cellPhone1').html(tmplen1+' **** '+tmplen2); 
    }
})
  function update_BankBaseInfo(){
	  var param = {};
	  param["paramMap.bankId"] = ${bankId};
	  param["paramMap.mBankName"] = $("#bankName_").val();
	  param["paramMap.mSubBankName"] = $("#subBankName_").val();
	  param["paramMap.random_Code"] = $("#randomCode").val();
	  param["paramMap.province_name"] = $("#province_name").val();
	  param["paramMap.city_name"] = $("#city_name").val();
	  
	  $.shovePost("updateBankBaseInfo.do",param,function(data){
	         
		  if(data == 1){
	        	window.parent.tipjBox("未输入开户行名称");        
	            return;
	        }else if(data == 2){
	        	window.parent.tipjBox("开户行长度4~20字符");        
	            return;
	        }else if(data == 3){
	        	window.parent.tipjBox("未输入支行名称");        
	            return;
	        }else if(data == 4){
	        	window.parent.tipjBox("支行长度4~20字符");        
	            return;
	        }else if(data == 5){
	        	window.parent.tipjBox("请输入验证码");        
	            return;
	        }else if(data == 6){
	        	window.parent.tipjBox("验证码输入错误");        
	            return;
	        }else if(data == 11){
	        	window.parent.tipjBox("所属省不能为空");        
	            return;
	        }else if(data == 12){
	        	window.parent.tipjBox("所属省至少两个字符");        
	            return;
	        }else if(data == 13){
	        	window.parent.tipjBox("系统未检测到您输入的省份");        
	            return;
	        }else if(data == 22){
	        	window.parent.tipjBox("所属市不能为空");        
	            return;
	        }else if(data == 23){
	        	window.parent.tipjBox("所属市至少两个字符");        
	            return;
	        }else if(data == 24){
	        	window.parent.tipjBox("系统未检测到您输入的市");        
	            return;
	        }else if(data == 7){
	        	 alert("修改成功"); 
	            
	        }else if(data == 8){
	        	window.parent.tipjBox("修改失败");        
	            return;
	        } 
	        window.parent.history.go(0);
	        window.parent.window.jBox.close() ;
	        
	     });
	  
	  
  }



 
   function changeBankInfo(){
    
     if(isNaN($("#bankCard_").val())){
    		window.parent.tipjBox("银行卡号不合法");
    		return;
     }      
     param["paramMap.bankId"] = ${bankId};
     param["paramMap.mBankName"] = $("#bankName_").val();
     param["paramMap.mSubBankName"] = $("#subBankName_").val();
     param["paramMap.mBankCard"] = $("#bankCard_").val();
     param["paramMap.dealpwd"] = $("#dealpwd_").val();
     param["paramMap.random_Code"] = $("#randomCode").val();
     param["paramMap.identify_path_u"] = $("#identify_path_u").val();
     param["paramMap.bank_card_path_u"] = $("#bank_card_path_u").val();
     
     $.shovePost("updateBankInfo.do",param,function(data){
        
        if(data == 1){
        	window.parent.tipjBox("该卡号在系统中已存在");        
            return;
        }else if(data == 2){
        	window.parent.tipjBox("请输入验证码");        
            return;
        }else if(data == 3){
        	window.parent.tipjBox("手机验证码错误");        
            return;
        }else if(data == 4){
        	window.parent.tipjBox("未上传银行卡变更证明");        
            return;
        }else if(data == 5){
        	window.parent.tipjBox("未上传身份证件");        
            return;
        }else if(data == 6){
        	window.parent.tipjBox("交易密码错误");        
            return;
        }else if(data == 7){
        	window.parent.tipjBox("请输入交易密码");        
            return;
        }else if(data == 8){
        	window.parent.tipjBox("未输入开户行名称");        
            return;
        }else if(data == 9){
        	window.parent.tipjBox("未输入支行名称");        
            return;
        }else if(data == 10){
        	window.parent.tipjBox("未输入银行卡号码");        
            return;
        }
        else if(data == 11){
        	window.parent.tipjBox("开户行长度4~20字符");        
            return;
        }
        else if(data == 12){
        	window.parent.tipjBox("支行长度4~20字符");        
            return;
        }
        else if(data == 13){
        	window.parent.tipjBox("银行卡号长度10~30位数字");        
            return;
        }else if(data == 15){  
           alert("申请变更成功");          
        }else if(data == 14){
          	window.parent.tipjBox("申请变更失败");        
            return;
        }
        window.parent.history.go(0);
        window.parent.window.jBox.close() ;
        
     });
     
   }
   
   function sendphoneCode(){
	   var param = {};
	   $.shovePost("tosendPhoneCode.do",param,function(data){
	        if(data == 1){ 
	        	alert('手机验证码已经发送到您的手机中...');
	        } 
	     });
   }
   
   function showOrHideDIV(){
	   var chk = document.getElementById("chkAll");
	 
	   if(chk.checked){ 
		   $('#bankDIV').show();
	   }else{
		   $('#bankDIV').hide();
	   }
	  
   }
</script>
</body>
</html>
