<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>

<!-------------主体 end----------->
 <!--返回顶部-->
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
    <!--footer-->
  <div class="footer">
 	<div class="footer-con">
    	<div class="footer-con-top">
        	<ul>
            	<li>联系我们</li>
                <li><a href="http://wpa.qq.com/msgrd?v=3&uin=1726129854&site=qq&menu=yes"><i class="footer-arrow icon13" style="width:87px; height:23px;"></i></a></li>
                
                 <li><a href="infomations.do?type=1"><i class="footer-arrow icon14 w21"></i>行业资讯</a></li>
                <li><a href="wzdt.do"><i class="footer-arrow icon15 w21"></i>网站地图</a></li>
                <li><a href="###"><i class="footer-arrow icon16 w21"></i>bd@jiangchuanbanking.com</a></li>
            </ul>
            <ul class="followus">
           	  	<li>关注我们</li>
            	<li style="margin-top:5px;"><a href="http://weibo.com/jcjrwxd"><i class="footer-arrow icon17 w31"></i></a></li>
                <li style="position:relative; margin-top:10px;"><a href="###" onclick="$('.one').show()"><i class="footer-arrow icon18 w31"></i></a></li>
            </ul>
            <ul class="about">
       	  	  <li>关于我们</li>
            	<li><a href="getMessageBytypeId.do?typeId=4">关于微信贷</a></li>
                <li><a href="getMessageBytypeId.do?typeId=35">平台公告</a></li>
                <li><a href="getMessageBytypeId.do?typeId=34">媒体报道</a></li>
            </ul>
            <ul class="about">
            	<li>帮助中心</li>
                 <li><a href="callcenter.do">常见问题</a></li>
                 <li><a href="getMessageBytypeId.do?typeId=4&name=hzhb">合作机构</a></li>
                <li><a href="getMessageBytypeId.do?typeId=4&name=rczp">人才招聘</a></li>
            </ul>
            <ul class="about">
            	 <li>安全中心</li>
                 <li><a href="cellPhonereginit.do">立即注册</a></li>
                 <li><a href="capitalEnsure.do">安全保障</a></li>
            </ul>
        </div>
        <div class="footer-con-cen">
   	    	  <a href='http://www.itrust.org.cn/yz/pjwx.asp?wm=109292797X' target='_blank'><img src='images/cert.jpg'></a></li>
                
                <a id='___szfw_logo___' href='https://search.szfw.org/cert/l/CX20141030005803005866' target='_blank'><img src='images/p_ico034.png'></a> 		               
                 <script type='text/javascript'>(function(){document.getElementById('___szfw_logo___').oncontextmenu = function(){return false;}})();</script>
            
         </div>
        <div class="footer-con-bottom">
        	<i>友情链接：</i>
        	<div id="links" style="position:relative; top:-30px; left:60px;"></div>
        	<!--<s:if test="#session.linksList.size>0">
                <s:iterator var="links" value="#session.linksList">
                	<a href="${companyURL}">${companyName}</a>
                </s:iterator>
            </s:if>
            -->
        </div>
    </div>
 </div>
 <div class="copyright">
 	<div class="copy-con"><span>江川金融信息服务(北京)股份有限公司 版权所有2016copyright reserved 2016</span><span>微信贷&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;京ICP备&nbsp;&nbsp;${sitemap.certificate }</span></div>
 </div>
  <div class="copyright">
  	<div class="copy-con"><span>市场有风险 投资需谨慎</span></div>
 </div>
 <!--footer--> 
 <script type="text/javascript">
	function selectTagw(showContent,selfObj){
		// 操作标签
		var tag = document.getElementById("tag_wind").getElementsByTagName("a");
		var taglength = tag.length;
		for(i=0; i<taglength; i++){
			tag[i].className = "";
		}
		selfObj.className = "select_a";
		// 操作内容
		for(i=0; j=document.getElementById("win_tagContents"+i); i++){
			j.style.display = "none";
		}
		document.getElementById(showContent).style.display = "block";	
	}
</script>
<script> 
	var speed=30 
	var demo=document.getElementById("demo"); 
	var demo2=document.getElementById("demo2"); 
	var demo1=document.getElementById("demo1"); 
	demo2.innerHTML=demo1.innerHTML 
	function Marquee(){ 
	if(demo2.offsetTop-demo.scrollTop<=0) 
	  demo.scrollTop-=demo1.offsetHeight 
	else{ 
	  demo.scrollTop++ 
	} 
	} 
	var MyMar=setInterval(Marquee,speed) 
	demo.onmouseover=function() {clearInterval(MyMar)} 
	demo.onmouseout=function() {MyMar=setInterval(Marquee,speed)} 
</script> 
<!---<script type="text/javascript" src="script/jqueryV172.js"></script>--->
<script type="text/javascript" src="script/xl.js"></script>
<script src="script/slides.min.jquery.js" type="text/javascript"></script>
<script src="script/tab_center.js" type="text/javascript"></script>
<script type="text/javascript" src="/script/m/jquery.rye-core-1.0.js"></script>
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
		
		var _param = {};
		var br = new BaseRye(_param);
		br.httpPost("/links.do", param, function(data){
			$("#links").empty();
			for(var i=0;i<data.length;i++){
				var o = data[i];
				$("#links").append("<a href='"+o.companyURL+"'>"+o.companyName+"</a>");
			}
			
		}, function(data){
			alert(data);
		})
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
