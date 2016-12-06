<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!--=======================-->
		<jsp:include page="/include/head.jsp"></jsp:include>
		<jsp:include page="/include/common.jsp"></jsp:include>
		<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		
		<div class="s_jkmain clearfix">
      <!--左导航 开始-->	
      <div class="l_nav clearfix">
         <ul class="clearfix">
				<li class="on">
				<a><span>step1 </span> 基本资料</a>
			</li>
			<li>
			<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
			  <a href="userPassData.do?from=${from }"><span>step2 </span> 上传资料</a>
			</s:if>
			
			<s:else>
				<s:if test="#session.user.authStep>=4">
					<a href="userPassData.do"><span>step2 </span> 上传资料</a>
				</s:if>	
				<s:else>
				     <a><span>step2 </span> 上传资料</a>
				</s:else>
			</s:else>
			</li>
			<li>
			<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
				<s:if test="#session.stepMode ==1">
				     <a href="addBorrowInit.do?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
				  </s:if>
				  <s:elseif test="#session.stepMode ==2">
				     <a href="creditingInit.do"><span>step3 </span>  申请额度</a>
				  </s:elseif>
				  <s:else>
				    <a href="addBorrowInit.do?t=0"><span>step3 </span> 发布贷款</a>	
				  </s:else>
			</s:if>
			<s:else>
				<s:if test="#session.user.authStep>=5">
				  <s:if test="#session.stepMode ==1">
				     <a href="addBorrowInit.do?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
				  </s:if>
				  <s:elseif test="#session.stepMode ==2">
				     <a href="creditingInit.do"><span>step3 </span> 申请额度</a>
				  </s:elseif>
				  <s:else>
				    <a href="addBorrowInit.do?t=0"><span>step3 </span> 发布贷款</a>	
				  </s:else>
				</s:if>	
				<s:else>
				  <s:if test="#session.stepMode ==1">
				       <a><span>step3 </span> 发布贷款</a>							  
				  </s:if>
				  <s:elseif test="#session.stepMode ==2">
				     <a><span>step3 </span> 申请额度</a>
				  </s:elseif>
				</s:else>
			</s:else>
			</li>
		</ul>	
      </div>
      <!--左导航 结束-->
      
      <!--右内容 开始-->	
      <div class="r_main clearfix">
      	<!--标题-->
      	 <div class="jkdet_tit tab_meun">
         	<ul>
								<li  id="li_geren">
									个人详细信息
								</li>
								<li  id="li_work" >
									工作认证信息
								</li>
								<li  id="li_vip" class="on">
									申请vip
								</li>
							</ul>
         </div>
         <!--内容-->
         <div class="jkdet_content tab_content">
             <!--申请VIP-->	
             <div>
             	<p class="tips" style="color:red;">
                    投资者：<br /> 
                    网站合作商提供投资担保，享受100%本金保障。对于担保标、推荐标，还能100%保利息。（普通用户仅保障本金） 有专业客服跟踪服务，体验尊贵感受。 享有尊贵VIP身份标识。<br />
                    借款者：<br />
                    享有借款资格及时缓解资金压力。 参与网站举行的各种活动。
				</p>
                <ul class="jk_grxx clearfix">
                	<li><span>你的状态是：</span><s:if test="#request.vippagemap.vipStatus==2">尊敬的vip会员</s:if>
											<s:else>普通会员</s:else></li>
                    <li><span>用户名：</span>${vippagemap.username }</li>
                    <li><span>姓名：</span>${vippagemap.realName }</li>
                    <li><span>邮箱：</span>${vippagemap.email }</li>
                    <li><span>客服：</span>
						<%-- <a id = "kefuname">${vippagemap.kefuname }</a> --%>
						<a id = "kefuname">小邓</a>
						
												<%-- <input type="hidden" value="${vippagemap.kfid }" name="paramMap.kefu" id="kefu"/> --%>
												
											    <input type="hidden" value="1" name="paramMap.kefu" id="kefu"/>
											    <s:if test="#request.vippagemap.vipStatus==2">&nbsp;</s:if>
											    <s:else>
											    <!-- <input type="button" id="btn_sp" class="scbtn"
									             style="cursor: pointer;" value="选择客服" onclick="fff()"/> -->
												</s:else>
												<span style="color: red; float: none;" id="u_kefu"
														class="formtips"></span>
					</li>
                    <li style="padding-left:180px;"><em class="fred"></em></li>
                    <s:if test="%{#request.isVip eq 'false'}">
                    <li><span>验证码：</span>
						<input type="text" class="inp100x" name="paramMap.code" id="code"/>	
												 <img src="${sitemap.adminUrl}/imageCode.do?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
												<span style="color: red; float: none;" id="u_code"
														class="formtips"></span></li></s:if>
											<s:if test="#request.vippagemap.vipStatus==2">&nbsp;</s:if>
											<s:else>
											<li><span>&nbsp;</span><input  type="button" id="vip_btn"  class="bcbtn" value="我要申请"/></li></s:else>
                </ul>   
             </div>      
         </div>		
      </div>
      <!--右内容 结束-->	 
</div>
		
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
		<script type="text/javascript">
		  $(function(){
		     //样式选中
          dqzt(2);
	  var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
		  });
		</script>
			<script type="text/javascript">
			
		      function switchCode(){
			     var timenow = new Date();
			     $('#codeNum').attr('src','${sitemap.adminUrl}/imageCode.do?pageId=vip&d='+timenow);
		    };
		    
		    
		</script>
		<script type="text/javascript">
		  /*
		   $(function(){
		   $("#btn_sp").click(function(){
		      //window.showModalDialog("uploadFileAction.do?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
		      var   rv   =   showModalDialog("querykefu.do");
               $("#kefuname").html(rv[0]);
               $("#kefu").attr("value",rv[1]);
		   });
		   })
		   */
		   $(function(){
		   
		   //--------------add by houli 判断用户是否已经申请了vip，如果申请了则该页面显示灰色
		      var isVip = '${isVip}';
		      if(isVip == "true"){//页面操作按钮变灰色
		          $("#vip_btn").attr('style','display:none;');
		          $("#btn_sp").attr('style','display:none;');
		          $("#context").attr('disabled','disabled');
		          $("#code").attr('disabled','disabled'); 
		          $("#codeNum").attr('style','display:none;');
		          
		      }
		   
	
		       $('#code').blur(function(){
		        if($('#code').val()==""){
		            $('#u_code').html("验证码不能为空");
		            return ;
		         }else{
		          $('#u_code').html("");
		         }
		         
		      }); 
		      
		      
		      
		      $('#btn_sp').click(function(){
		                   if($("#kefu").val()==""){
		         $("#u_kefu").html("客服不能为空");
		         return ;
		      }else{
		       $("#u_kefu").html("");
		      }
		         
		         });
		      
		   });
		   
		
		</script>
		
		<script type="text/javascript" src="css/popom.js"></script>
		<script type="text/javascript">
			//初始化
		     $(function(){
		          $("#li_geren").click(function(){
		          var from = '${from}';
		            window.location.href='querBaseData.do?from='+from;
		        });
		        
		        
		               $("#li_work").click(function(){
		               var from = '${from}';
		           window.location.href='querWorkData.do?from='+from;
		        });
		        
		               $("#li_vip").click(function(){
		               var from = '${from}';
		       window.location.href='quervipData.do?from='+from;
		    });
		        
		 });
		 
		    
		     $(function(){
			      $("#vip_btn").click(function(){
			      var param= {};
			      param["paramMap.pageId"] = "vip";
			      param["paramMap.code"] = $("#code").val();
			      param["paramMap.content"] = $("#context").val();
			      param["paramMap.kefu"] = $("#kefu").val();
			      //----add by houli
			      param["paramMap.from"] = '${from}';
			      var from = '${from}';
			 //     alert(from);
			      var btype = '${btype}';
			      if(btype == ''){
			         btype = '${session.borrowWay}';
			      }
			      //-----
			      $.post("updataUserVipStatus.do",param,function(data){
			        if(data.msg==1){
			          alert("申请vip成功!");
			         window.location.href="userPassData.do?from="+from+"&btype="+btype;
			        }else if(data.msg==5){
			         $("#u_code").html("验证码错误");
			          switchCode();
			        }else if(data.msg==2){
			          $("#u_kefu").html("跟踪人不能为空");
			           switchCode();
			        }else if(data.msg==13){
			         window.location.href="querBaseData.do";
			        }else if(data.msg==14){
			         window.location.href="querWorkData.do?btype="+btype;
			        }else{
			        	alert(data.msg);
			        	switchCode();
			        }
			      });
			      });
			    });
		    //===
		    function fff(){
		    	alert();
		    	$.jBox("iframe:querykefu.do", {
		    		title: "选择客服",
		    		width: 800,
		    		height: 500,
		    		buttons: {  }
		    		});
		    }
		    
		    function ffff(f,d){
		    
		    	 $("#kefuname").html(f);
               $("#kefu").attr("value",d);
               window.parent.window.jBox.close() ;
		    	if($('#kefu').val() != ''){
		    	   $('#u_kefu').html('');
		    	}
		    }
		    //-===
		
		</script>
		
	</body>
</html>
