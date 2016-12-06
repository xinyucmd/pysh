<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!-------------主体 end----------->
<dl class="s_floattip s_float-tip1">
	<a href="http://weibo.com/jcjrwxd" target="_blank" class="clearfix"><dt></dt><dd>新浪微博</dd></a>
</dl>
<dl class="s_floattip s_float-tip2">
	<a href="#" class="clearfix" onclick="$('.one').show()"><dt></dt><dd>关注我们</dd></a>    
</dl>
 <dl class="one"><a href="#" onclick="$('.one').hide()" class="closebtn"></a></dl>
 <dl class="s_floattip s_float-tip2">
	<a href="#" class="clearfix" onclick="$('.one').show()"><dt></dt><dd>关注我们</dd></a>    
</dl>
<dl class="s_floattip s_float-tip3">
	<a href="http://wpa.qq.com/msgrd?v=3&uin=1726129854&site=qq&menu=yes" class="clearfix"><dt></dt><dd>在线客服</dd></a>
</dl>
<dl class="s_floattip s_float-tip4">
	<a href="#" class="clearfix"><dt></dt><dd>返回顶部</dd></a>
</dl>
<div class="p_footerbox index-main">
    <div class="p_footer">
        <div class="p_footer01">
            <div class="p_ft001"><a href="index.jsp"><img src="images/p_ico030.png" /></a></div>
            <div class="p_ft002">
                <dl>
                    <dt>关于我们</dt>
                    <dd><a href="getMessageBytypeId.do?typeId=4">关于微信贷</a></dd>
                    <dd><a href="getMessageBytypeId.do?typeId=35">平台公告</a></dd>
                    <dd><a href="getMessageBytypeId.do?typeId=34">媒体报道</a></dd>
                </dl>
                <dl>
                    <dt>服务中心</dt>
                    <dd><a href="cellPhonereginit.do">立即注册</a></dd>
                    <!--<dd><a href="getMessageBytypeId.do?typeId=6">资费说明</a></dd>-->
                    <dd><a href="callcenter.do">常见问题</a></dd>
                </dl>
                <dl>
                    <dt>安全中心</dt>
                    <dd><a href="capitalEnsure.do">安全保障</a></dd>
                    <dd><a href="getMessageBytypeId.do?typeId=4&name=hzhb">合作伙伴</a></dd>
                </dl>
                <dl class="p_ft003">
                    <dt>联系我们</dt>
                    <dd><a href="getMessageBytypeId.do?typeId=4&name=lxwm">联系我们</a></dd>
                    <dd><a href="getMessageBytypeId.do?typeId=4&name=rczp">人才招聘</a></dd>
                </dl>
            </div>
            <div class="p_ft004">
               <!-- <p><span>关注我们：</span><span><a href="http://weibo.com/jcjrwxd"><img src="images/p_ico031.png" /></a></span><span><a href="#"><img src="images/p_ico032.png" /></a></span></p>-->
                <p class="p_ft005">客服电话</p>
                <p class="p_ft006"><img src="images/p_ico033.png" /> 400-001-6007</p>
                <p style="margin-left:13px;"><em style="list-style:none; color:#ff6960; font-size:12px;">服务时间：(9:00-21:00)</em></p>
                <!-- <p style="margin:5px 0 0 10px; color:#ff6960; font-size:12px;"><img src="images/qq.png" width="20" height="20" /><em style="list-style:none; position:relative; top:5px; left:4px;">QQ群：372149092</em></p> -->
            </div>
        </div>
        <div class="p_footer02">
            <dl>
                <dt>友情链接：</dt>
                
               <s:if test="#session.linksList.size>0">
                <s:iterator var="links" value="#session.linksList">
                <dd><a href="${companyURL}" target="_blank" >${companyName}</a></dd>
                </s:iterator>
            </s:if>
            </dl>
        </div>
        <div class="p_footer03 clearfix">
            <ul>
                <li><a href='http://www.itrust.org.cn/yz/pjwx.asp?wm=109292797X' target='_blank'><img src='images/cert.jpg'></a></li>
                <li>
                <a id='___szfw_logo___' href='https://search.szfw.org/cert/l/CX20141030005803005866' target='_blank'><img src='images/p_ico034.png'></a> 		                <script type='text/javascript'>(function(){document.getElementById('___szfw_logo___').oncontextmenu = function(){return false;}})();</script>
                </li>
            </ul>
           
            <div class="p_ft007" align="center"><span>${sitemap.companyName}</span> <span>版权所有2015copyright reserved 2015</span><span>微信贷&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;京ICP备&nbsp;&nbsp;${sitemap.certificate }</span></div>
        </div>
    </div>
</div>
<!-------------底部 end----------->
<!---<script type="text/javascript" src="script/jqueryV172.js"></script>--->
<script type="text/javascript" src="script/xl.js"></script>
<script src="script/slides.min.jquery.js" type="text/javascript"></script>
<script src="script/tab_center.js" type="text/javascript"></script>
<script>
		$(function(){
		$(".index_yhm").focus(function(){
			var aaa=$(".index_yhm").val();
			if(aaa =="请输入用户名"){
				$(".index_yhm").val(null);
			}
		})
		$(".index_yhm").blur(function(){
			var aaa=$(".index_yhm").val();
			if(aaa ==""){
				$(".index_yhm").val("请输入用户名");
			}
		})
		$(".pass_oo").focus(function(){
			$("#index_mm").hide();
		})
		$(".pass_oo").blur(function(){
		var bbb=$(".pass_oo").val();
		if(bbb ==""){
			$("#index_mm").show();
			}
		})
		$("#index_mm").click(function(){
			$(".pass_oo").focus();
		})
		});
</script>
<script type="text/javascript">
function addCookie()
{
 if (document.all){
       window.external.addFavorite('<%=application.getAttribute("basePath")%>','${sitemap.siteName}');
    }
    else if (window.sidebar) {
       window.sidebar.addPanel('${sitemap.siteName}', '<%=application.getAttribute("basePath")%>', "");
    }else{
       alert('请手动设为首页');
    }
}

function setHomepage(){
    if (document.all){
        document.body.style.behavior='url(#default#homepage)';
        document.body.setHomePage('<%=application.getAttribute("basePath")%>');
    }else if (window.sidebar){
        if(window.netscape){
         try{  
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
         }  
         catch (e)  
         {  
            alert( "该操作被浏览器拒绝，如果想启用该功能，请在地址栏内输入 about:config,然后将项 signed.applets.codebase_principal_support 值该为true" );  
         }
    }else{
        alert('请手动添加收藏');
    }
    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components. interfaces.nsIPrefBranch);
    prefs.setCharPref('browser.startup.homepage','<%=application.getAttribute("basePath")%>');
 }
}
$(function(){
	$(window).scroll(function(){
		if($(window).scrollTop()>=109){
			$(".nav-zdh").css("position","fixed")
		}
		else{
		$(".nav-zdh").css("position","relative")
		}
	})
})
</script>


<!-- 谷歌 -->
<script> 
 (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){ 
 (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o), 
 m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m) 
 })(window,document,'script','//www.google-analytics.com/analytics.js','ga'); 
 ga('create', 'UA-49927322-1', 'auto'); 
 ga('send', 'pageview'); 
</script>
<!-- Start LK Wise Attribution System Tracker --> 
<script type="text/javascript"> 
//<![CDATA[ 
var lk_cmds =[]; 
lk_cmds.push(['setSiteId', 'MQIGVUTK']); 
lk_cmds.push(['trackPageView']); 
(function() { 
var _lk = document.createElement('script'); _lk.type = 'text/javascript'; 
_lk.async = true; 
   _lk.src = 'http://www.zikker.com/lk_t_compress.js'; 
   var _lk_s = document.getElementsByTagName('script')[0]; 
_lk_s.parentNode.insertBefore(_lk, _lk_s); 
}()); 
//]]> 
</script> 
<!-- End LK Wise Attribution System Code -->

<!-- 百度 --> 
<script type="text/javascript"> 
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://"); 
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F5f4e6cc92182a9237eadbebba00f6e33' type='text/javascript'%3E%3C/script%3E")); 
</script>


<script type="text/javascript">
var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cspan id='cnzz_stat_icon_1254167505'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1254167505' type='text/javascript'%3E%3C/script%3E"));
</script>
