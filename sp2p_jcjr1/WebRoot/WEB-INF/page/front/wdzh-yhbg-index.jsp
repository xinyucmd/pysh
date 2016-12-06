
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
<script language="javascript" type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
 #bankcard_Floatbox {
    background: #fff9eb none repeat scroll 0 0;
    border: 1px solid #ffd19b;
    border-radius: 3px;
    color: #fe8e01;
    display: none;
    font: 18px/37px "Microsoft Yahei";
    height: 37px;
    left: 665px;
    padding: 0 14px;
    position: absolute;
    top: 380px;
    white-space: nowrap;
    width: 270px;
    z-index: 10;
} 
.form_s{
 	width:226px;
 	height:30px;
 	 padding:0 10px;
 	 border:1px solid #96ccf4;
 	border-radius:3px;
 	 
}
.form-group{
	margin:30px 0;
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
<div class="wrap" style="padding-top:10px;">
    <div class="lne_cent">
		<%@include file="/include/left.jsp"%>
		<div class="lne_centr">
		<div class="lne-centr-con">
            <div class="lne-centr-con-content">
			<div style="padding:0 20px;">
			<p class="blue-title"><i>微信贷温馨提示：</i>我们会保管您的资料安全不被泄露，请你放心填写。</p>
				<div class="tb_list clearfix" id="is_show_add_banker_card"
					style="display: none">
					 
					 <div class="form-group">
					   <label >真实姓名</label>
					     <span class="txt" id="cardUserName1">
								<s:if test="%{#request.realName==''}">
									<s:property value="#request.realName" default="---"></s:property>
								</s:if>
								<s:else>
									<shove:sub value="#request.realName" size="1" suffix="***" />*
							    </s:else>
						</span>
						
						</div>
						<div class="form-group">
						<label ><span style="color:red;">*</span>开户行</label>
						<input type="text"  placeholder="请输入开户行" id="bankName1"  class="form_s"/>
							<span
							style="color: red; float: right; width: 280px; text-align: left;"
							id="bk1_tip" class="formtips"></span>
					    </div>
					    <div class="form-group">
							<label><span style="color:red;">*</span>支行：</label>
							<input type="text"   placeholder="XX支行" class="form_s"
								id="subBankName1"/><span id = "banktail"></span><span
								style="color: red; float: right; width: 280px; text-align: left;"
								id="bk1_tip1" class="formtips"></span>
					    </div>
					    <div class="form-group">
							<label><span style="color:red;">*</span>卡号：</label>
							  <input type="text"  placeholder="请输入银行卡号" maxlength="21" class="form_s"
								id="bankCard1" /><span
								style="color: red; float: right; width: 280px; text-align: left;"
								id="bk1_tip2" class="formtips"></span>
								<div id="bankcard_Floatbox" style="display: none;"></div>
					     </div>
					     <div class="form-group"> 
							 <label><span style="color:red;">*</span>银行卡所属省：</label>
							<select id="province_name" style="width: 100px;height:30px;border:1px solid #96ccf4;border-radius:3px;">
                     				<option>请选择</option>
                				</select>
							 <span
								style="color: red; float: right; width: 280px; text-align: left;"
								id="bk1_tip6" class="formtips"></span>  
								
                
					    </div> 
					     
					      <div class="form-group">
							 <label><span style="color:red;">*</span>银行卡所属市：</label>
								<select id="city_name" style="width: 100px;height:30px;border:1px solid #96ccf4;border-radius:3px;">
                    			 <option>请选择</option>
               			 </select> <span
								style="color: red; float: right; width: 280px; text-align: left;"
								id="bk1_tip7" class="formtips"></span>
								</div>
					     <div class="form-group">
							<label><span style="color:red;">*</span>交易密码：</label><input type="password" placeholder="请输入交易密码" class="form_s"
								id="dealpwd" style="width:226px;height:30;padding:0 10px;"/><span
								style="color: red; float: right; width: 280px; text-align: left;"
								id="bk1_tip3" class="formtips"></span>
					     </div>
						<input type="button" value="确定提交" class="btn btn-warning btn-lg" style="margin-left:190px;" id="addbank" />
					     <span
							style="color: red; float: right; width: 280px; text-align: left;"
							id="bk1_tip4" class="formtips"></span>
				</div>


				<span id="bankInfo"></span>
			</div>
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
		$(function() {
			//$('#li_5').addClass('on');
			//$("#jk_hover div").removeClass('none');
			$(".lne_l3 > ul >li").removeClass("clicked");
			$('#li_10').addClass('clicked');
		});

		function jumpUrl(obj) {
			window.location.href = obj;
		}
	</script>
	<script>
		$(function() {
			dqzt(4);
			$('#li_5').addClass('on');
			var bb = '${person}';//设置提现银行卡必须先填写个人资料
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href = "owerInformationInit.do";
			}
		});
	</script>
	<script>
		$(function() {
			      //加载省份列表信息
			      $.ajax({
			          url: "/script/Area.xml",
			          dataType: "xml",
			          success: function (xml) {
			              $(xml).find("province").each(function () {//找到(province)省份节点;
			                  $("<option></option>").html($(this).attr("name")).appendTo("#province_name");//加载(province)省份信息到列表中
			              })
			         }
			     })
			     //省份列表信息更改时，加载城市列表信息
			     $("#province_name").change(function () {
			         var value = $("#province_name").val();//省份值;
			         if (value != "请选择") {
			        	 $("#city_name").find("option").remove();//显示城市下拉列表框删除城市下拉列表中的数据;
			        	 $("#city_name").html("<option>请选择</option>");//加载城市列表中的请选择;
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
			$.shovePost("queryBankInfoInit.do", null, function(data) {
				$("#bankInfo").html(data);
				var is_state = $('#is_state').val();
				if (is_state == '0') {

					$('#is_show_add_banker_card').show();
				}
				if (is_state == '1') {
					$('#is_show_add_banker_card').hide();
				}
			});
			//提现银行卡设置1
			$("#bankName1").blur(function() {
				if ($("#bankName1").val() == "") {
					$("#bk1_tip").html("开户银行名称不能为空");
					$("#bankName1").css('border','1px solid #f00');
				} else {
					$("#bk1_tip").html("");
					$("#bk1_tip4").html("");
					$("#bankName1").css('border','1px solid #96ccf4');
				}
			});
			$("#dealpwd").blur(function() {
				if ($("#dealpwd").val() == "") {
					$("#bk1_tip3").html("交易密码不能为空");
					$("#dealpwd").css('border','1px solid #f00');
				} else {
					$("#bk1_tip3").html("");
					$("#bk1_tip4").html("");
					$("#dealpwd").css('border','1px solid #96ccf4');
				}
			});
			$("#subBankName1").blur(function() {
				if ($("#subBankName1").val() == "") {
					$("#bk1_tip1").html("开户支行不能为空");
					$("#banktail").hide();
					$("#subBankName1").css('border','1px solid #f00');
				} else {
					$("#bk1_tip1").html("");
					$("#bk1_tip4").html("");
					$("#subBankName1").css('border','1px solid #96ccf4');
				}
			});
			$("#province_name").blur(function() {
				if ($("#province_name").val() == "") {
					$("#bk1_tip6").html("所属省不能为空");
					$("#province_name").css('border','1px solid #f00');
				} else {
					$("#bk1_tip6").html("");
					$("#bk1_tip7").html("");
					$("#province_name").css('border','1px solid #96ccf4');
				}
			});
			$("#city_name").blur(function() {
				if ($("#city_name").val() == "") {
					$("#bk1_tip7").html("所属市不能为空");
					$("#city_name").css('border','1px solid #f00');
				} else {
					$("#bk1_tip6").html("");
					$("#bk1_tip7").html("");
					$("#city_name").css('border','1px solid #96ccf4');

				}
			});
			//银行卡号验证新
			$("#bankCard2").blur(function() {
			   var b_value = $("#bankCard2").val();
		        if (""==b_value||isNaN(b_value)) {
		        	$("#bk1_tip2").html("银行卡号不能为空");
		        	$("#bankCard2").css('border','1px solid #f00');
		        } else if (!(/^\d{16,21}$/g).test(b_value)) {
		        	$("#bk1_tip2").html("请填写正确的银行卡号");
		        	$("#bankCard2").css('border','1px solid #f00');
		        } else {
		        	$("#bk1_tip2").html("");
					$("#bk1_tip4").html("");
					$("#bankcard_Floatbox").hide().text("");
					$("#bankCard2").css('border','1px solid #96ccf4');
		        }
			}).
			/*银行卡输入提示*/
		    keyup(function () {
        var _this = $(this), value = $(this).val();
        value = value.replace(/[^\d]/g, "");
        _this.val(value);
        if (!value || !(/^\d{1,21}$/g).test(value) || value == _this.attr("placeholder")) {
            $("#bankcard_Floatbox").hide().text("");
        } else {
            var re = /(\d{4})/g;
            if (value.length >= 4) {
                value = value.replace(re, function ($0) {
                    return $0 + ' ';
                })
            }
            $("#bankcard_Floatbox").show().text(value);
        }
    }).
		    /*只允许输入数字*/
		    keydown(function (e) {
		        if (e.shiftKey)
		            return false;
		        var key = [8, 37, 39, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 
		104, 105, 110, 190];
		        if ($.inArray(e.which, key) < 0) {
		            return false;
		        }
		    });
		   
			//提现银行卡设置2
			$("#bankName2").blur(function() {
				if ($("#bankName2").val() == "") {
					$("#bk2_tip").html("开户银行名称不能为空");
				} else {
					$("#bk2_tip").html("");
					$("#bk1_tip4").html("");
				}
			});

			$("#subBankName2").blur(function() {
				if ($("#subBankName2").val() == "") {
					$("#bk2_tip1").html("开户支行不能为空");
				} else {
					$("#bk2_tip1").html("");
					$("#bk1_tip4").html("");
				}
			});

			//银行卡号验证新
			$("#bankCard1").blur(function() {
			   var b_value = $("#bankCard1").val();
		        if (""==b_value||isNaN(b_value)) {
		        	$("#bk1_tip2").html("银行卡号不能为空");
		        	$("#bankCard1").css('border','1px solid #f00');
		        } else if (!(/^\d{16,21}$/g).test(b_value)) {
		        	$("#bk1_tip2").html("请填写正确的银行卡号");
		        	$("#bankCard1").css('border','1px solid #f00');
		        } else {
		        	$("#bk1_tip2").html("");
					$("#bk1_tip4").html("");
					$("#bankcard_Floatbox").hide().text("");
					$("#bankCard1").css('border','1px solid #96ccf4');
		        }
			}).
			/*银行卡输入提示*/
		    keyup(function () {
        var _this = $(this), value = $(this).val();
        value = value.replace(/[^\d]/g, "");
        _this.val(value);
        if (!value || !(/^\d{1,21}$/g).test(value) || value == _this.attr("placeholder")) {
            $("#bankcard_Floatbox").hide().text("");
        } else {
            var re = /(\d{4})/g;
            if (value.length >= 4) {
                value = value.replace(re, function ($0) {
                    return $0 + ' ';
                })
            }
            $("#bankcard_Floatbox").show().text(value);
        }
    }).
		    /*只允许输入数字*/
		    keydown(function (e) {
		        if (e.shiftKey)
		            return false;
		        var key = [8, 37, 39, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 
		104, 105, 110, 190];
		        if ($.inArray(e.which, key) < 0) {
		            return false;
		        }
		    });
		   
		});

		$("#addbank").click(function() {
			addBankInfo();

		});
		//添加提现银行信息
		function addBankInfo() {
			if ($.trim($("#bankName1").val()) == ""
					|| $.trim($("#subBankName1").val()) == ""
					|| $.trim($("#bankCard1").val()) == ""
					|| $.trim($("#dealpwd").val()) == "") {
				$("#bk1_tip4").html("请完整填写信息");
				return;
			} else if (isNaN($("#bankCard1").val())) {
				$("#bk1_tip4").html("");
				$("#bk1_tip").html("请输入正确的银行卡号");
				return;
			}

			param["paramMap.bankName"] = $("#bankName1").val();
			param["paramMap.subBankName"] = $("#subBankName1").val();
			param["paramMap.bankCard"] = $("#bankCard1").val();
			param["paramMap.cardUserName"] = $("#cardUserName1").text();
			param["paramMap.dealpwd"] = $("#dealpwd").val();
			
		    param["paramMap.province_name"] = $("#province_name").val();
			param["paramMap.city_name"] = $("#city_name").val();

			if (!window.confirm("确定要添加银行卡绑定吗?")) {
				return;
			} else {
				$("#addbank").attr("disabled", true);
			}

			$.shovePost("addBankInfo.do", param, function(data) {
				if (data == 1) {
					$("#bk1_tip").html("请输h入有效的银行卡信息");
					$("#bankCard1").attr("value", "");//银行卡信息错误，只清空银行卡信息
					$("#addbank").attr("disabled", false);
					return;
				} else if (data == 2) {
					alert("你已经添加了两张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
				} else if (data == 3) {
					alert("请正确填写参数");
					$("#addbank").attr("disabled", false);
					return;
				} else if (data == 5) {
					window.parent.tipjBox("交易密码错误");   
					$("#addbank").attr("disabled", false);
					return;
				} else if (data == 11) {
					alert("该卡号系统中已经存在，请重新填写");
					$("#addbank").attr("disabled", false);
					return;
				} else if(data == 11){
		        	window.parent.tipjBox("所属省不能为空");   
		        	$("#addbank").attr("disabled", false);
		            return;
		        }else if(data == 12){
		        	window.parent.tipjBox("所属省至少两个字符"); 
		        	$("#addbank").attr("disabled", false);
		            return;
		        }else if(data == 13){
		        	window.parent.tipjBox("系统未检测到您输入的省份");    
		        	$("#addbank").attr("disabled", false);
		            return;
		        }else if(data == 22){
		        	window.parent.tipjBox("所属市不能为空");      
		        	$("#addbank").attr("disabled", false);
		            return;
		        }else if(data == 23){
		        	window.parent.tipjBox("所属市至少两个字符"); 
		        	$("#addbank").attr("disabled", false);
		            return;
		        }else if(data == 24){
		        	window.parent.tipjBox("系统未检测到您输入的市");    
		        	$("#addbank").attr("disabled", false);
		            return;
		        }else {
					$('#is_show_add_banker_card').hide();
					$("#bankInfo").html(data);
				}

				$("#bankName1").attr("value", "");
				$("#subBankName1").attr("value", "");
				$("#bankCard1").attr("value", "");
				$("#dealpwd").attr("value", "");
				$("#addbank").attr("disabled", false);
			});
		}
		function jump(url) {

			alert("请先回答密保问题！");
			window.location.href = url;
		}
		function addBankInfo2() {
			if ($("#bankName2").val() == "" || $("#subBankName2").val() == ""
					|| $("#bankCard2").val() == "") {
				$("#bk2_tip4").html("请完整填写信息");
				return;
			} else if (isNaN($("#bankCard2").val())) {
				$("#bk2_tip").html("请输入正确的银行卡号");
				return;
			}
			param["paramMap.bankName"] = $("#bankName2").val();
			param["paramMap.subBankName"] = $("#subBankName2").val();
			param["paramMap.bankCard"] = $("#bankCard2").val();
			param["paramMap.cardUserName"] = $("#cardUserName2").text();

			if (!window.confirm("确定要添加银行卡绑定吗?")) {
				return;
			}

			$.shovePost("addBankInfo.do", param, function(data) {
				if (data == 1) {
					$("#bk2_tip").html("请输入有效的银行卡信息");
					$("#bankCard2").attr("value", "");//银行卡信息错误，只清空银行卡信息
					return;
				} else if (data == 2) {
					alert("你已经添加了两张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
				} else if (data == 3) {
					alert("请正确填写参数");
				} else {
					$("#bankInfo").html(data);
				}
				$("#bankName2").attr("value", "");
				$("#subBankName2").attr("value", "");
				$("#bankCard2").attr("value", "");
			});
		}
		function tipjBox(content) {
			$.jBox.tip(content);
		}

		function closejBox() {
			tipjBox("变更成功，请等待审核");
			history.go(0);
		}
		//删除添加的银行卡信息
		function del(id) {
			if (!window.confirm("确定要删除吗?")) {
				return;
			}
			$.shovePost("deleteBankInfo.do", {
				bankId : id
			}, function(data) {
				$("#bankInfo").html(data);
			});
		}

		function changeBankCancel(id) {
			if (!window.confirm("确定要取消变更吗?")) {
				return;
			}
			$.shovePost("bankChangeCancel.do", {
				bankId : id
			}, function(data) {
				if (data == 1) {
					alert("取消失败，请重新取消");
					return;
				}
				$("#bankInfo").html(data);
			});
		}
	</script>
	// JavaScript Document
	<script>
		//工作认证
		function loadWorkInit(url) {
			var bb = '${person}';//填写工作信息必须先填写个人资料
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href = "owerInformationInit.do";
			} else {
				window.location.href = url;
			}
		}
		function bindingPhoneLoad(url) {
			/**	var bb = '${person}';//申请手机绑定必须先填写个人资料
				var cc = '${pass}';
				if (bb == "0") {
					alert("请先完善个人信息");
					window.location.href='owerInformationInit.do';
				} else if (cc != 3) {
					alert("请等待个人信息审核通过");
					return ;
				} else {*/
			window.location.href = url;
			//		}
		}

		//加载该用户提现银行卡信息
		function loadBankInfo(url) {
			var bb = '${person}';//设置提现银行卡必须先填写个人资料
			if (bb == "0") {
				alert("请先完善个人信息");
				window.location.href = "owerInformationInit.do";
			} else {
				window.location.href = url;
			}

		}
		function updatePwd(url) {
			window.location.href = url;
			/*var bb = '${isApplyPro}';
			if (bb == 1) { 
				alert("请先设置安全问题");
				window.location.href="queryQuestion.do";
			}else if(bb == 2){
				alert("请先回答安全问题");
				window.location.href="getQuestion.do";
			}*/
		}
		function changeBankInfos(id) {
			var url = "queryOneBankInfo.do?bankId=" + id;
			$.jBox("iframe:" + url, {
				title : "银行卡变更",
				width : 750,
				height : 600,
				buttons : {}
			});
		}
		function deleteBankCard(id) {
			if (!window.confirm("确定要删除吗?")) {
				return;
			}
			$.shovePost("deleteBankCard.do", {
				bankId : id
			}, function(data) {
				window.location.href = "yhbound.do";
			});
		}
	</script>
</body>
</html>