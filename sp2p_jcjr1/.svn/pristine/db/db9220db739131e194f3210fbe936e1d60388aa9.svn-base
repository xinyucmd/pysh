$(function(){
$(".p_tab").eq(0).css('display','block');
$(".p_tabul").children("li").eq(0).addClass('p_on');
$(".p_tabul").children("li").click(function(){
$(".p_tabul").children("li").removeClass('p_on')
$(this).addClass("p_on")
$(".p_tab").css("display","none");
$(".p_tab").eq($(this).index()).css("display","block");
})
})
/*//banner图焦点居中
$(function(){
setInterval(function(){
var pw=$(".pagination").width();
$(".pagination").css("margin-left",-pw/2+'px')
},10);
})
*/

/* $(document).ready(function(){
$(document).ready(function(){
$(".level1>h2").click(function(){
$(this).addClass("current").next().show().parent().siblings().children("a")
.removeClass("current").next().hide();
})
})
});*/
$(document).ready(function(){
var currMenu = $(".tab_meun ul .on").html();
var currMenu2 = $(".myhome_tit .on").html();
if(currMenu == null){
currMenu = currMenu2;
}
if( typeof(currMenu) != "null" && typeof(currMenu)!="undefined"){
currMenu = $.trim(currMenu);
switch (currMenu) {
case "充 值":
$(".lne_l3 .level1:eq(0) ul").show();
var liLength = $(".lne_l3 .level1:eq(0) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(0) ul").find("li").eq(i).children("a").html() == "充值"){
$(".lne_l3 .level1:eq(0) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "提 现":
$(".lne_l3 .level1:eq(0) ul").show();
var liLength = $(".lne_l3 .level1:eq(0) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(0) ul").find("li").eq(i).children("a").html() == "提现"){
$(".lne_l3 .level1:eq(0) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "资金记录":
$(".lne_l3 .level1:eq(0) ul").show();
var liLength = $(".lne_l3 .level1:eq(0) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(0) ul").find("li").eq(i).children("a").html() == "资金记录"){
$(".lne_l3 .level1:eq(0) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "投资列表":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "我的投资"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "债权转让":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "债权转让"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
/*case "参与购买的债权":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "债权拍拍"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;*/
case "自动投标":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "自动投标"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "预约投标":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "预约投标"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "理财统计":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "理财统计"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;

case "基本信息":
$(".lne_l3 .level1:eq(2) ul").show();
var liLength = $(".lne_l3 .level1:eq(2) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(2) ul").find("li").eq(i).children("a").html() == "基本信息"){
$(".lne_l3 .level1:eq(2) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;

case "银行卡设置":
$(".lne_l3 .level1:eq(2) ul").show();
var liLength = $(".lne_l3 .level1:eq(2) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(2) ul").find("li").eq(i).children("a").html() == "银行卡管理"){
$(".lne_l3 .level1:eq(2) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;

case "系统消息":
$(".lne_l3 .level1:eq(2) ul").show();
var liLength = $(".lne_l3 .level1:eq(2) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(2) ul").find("li").eq(i).children("a").html() == "系统消息"){
$(".lne_l3 .level1:eq(2) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;

case "成功借款":
$(".lne_l3 .level1:eq(3) ul").show();
var liLength = $(".lne_l3 .level1:eq(3) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").html() == "还款管理"){
$(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "详细信息":
$(".lne_l3 .level1:eq(3) ul").show();
var liLength = $(".lne_l3 .level1:eq(3) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").html() == "认证信息"){
$(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "审核中的借款":
$(".lne_l3 .level1:eq(3) ul").show();
var liLength = $(".lne_l3 .level1:eq(3) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").html() == "申请中的借款"){
$(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "贷款统计":
$(".lne_l3 .level1:eq(3) ul").show();
var liLength = $(".lne_l3 .level1:eq(3) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").html() == "贷款统计"){
$(".lne_l3 .level1:eq(3) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
}
} 
$(document).ready(function(){
$(".lne_l3 ul li").click(function()
{$(this).children("ul").slideToggle("slow");
$(this).toggleClass("active");
});
});

$(document).ready(function(){
$(window).scroll(function() {
if($(".p_xq004").length && $(".p_xq022").length()){
var menuOffsetTop = $(".p_xq004").offset().top+$(".p_xq022").height();//.menuTopDiv menu上面层的类名 menu菜单类名
var alreadyScrollTop = $(document).scrollTop();
if(alreadyScrollTop >= menuOffsetTop){
$(".p_xq022").addClass("menuFixed");
}
if(alreadyScrollTop<menuOffsetTop){
$(".p_xq022").removeClass("menuFixed");
}
}

});
});

$(function(){
var
$banner=$(".s_banner"),
$scroll=$banner.children(".banner-scroll"),
$ul=$scroll.children("ul"),
$li=$ul.children("li"),
$prev=$scroll.children(".banner-prev"),
$next=$scroll.children(".banner-next"),
len=$li.length,
liw=$li.eq(0).width(),
id;
//banner图居中
var
winW=$(window).width(),
width=$scroll.width();
$scroll.css("margin-left",(winW-width)/2+"px");
$(window).resize(function(){
var
winW=$(window).width(),
width=$scroll.width();
$scroll.css("margin-left",(winW-width)/2+"px");
});
function slide(){
if(!$ul.is(":animated")){
$ul.animate({marginLeft:-width+"px"},300,function(){
$ul.css("margin-left",0).find("li:first").appendTo($ul);
});
}
}
function slideStart(){
id=setInterval(slide,4000);
}
function slideStop(){
clearInterval(id);
}
$scroll.hover(function(){
slideStop();
$prev.fadeIn(200);
$next.fadeIn(200);
},function(){
slideStart();
$prev.fadeOut(200);
$next.fadeOut(200);
}).trigger("mouseleave");
$next.click(function(){
slideStop();
$ul.animate({marginLeft:-width+"px"},300,function(){
$ul.css("margin-left",0).find("li:first").appendTo($ul);
});
});
$prev.click(function(){
slideStop();
$ul.css("margin-left",-width+"px").find("li").prev().appendTo($ul);
$ul.animate({marginLeft:0},300);
})
});



$(function(i){
var
$banner=$(".s_index-main3"),
$scroll=$banner.children(".main3-cont"),
$ul=$scroll.children("ul"),
$li=$ul.children("li"),
$prev=$scroll.children(".banner-prev"),
$next=$scroll.children(".banner-next"),
len=$li.length,
liw=$li.eq(0).width(),i= 0,
id;
//banner图居中
var
winW=$(window).width(),
width=$scroll.width();
$scroll.css("margin-left",(winW-width)/2+"px");
$(window).resize(function(){
var
winW=$(window).width(),
width=$scroll.width();
$scroll.css("margin-left",(winW-width)/2+"px");
});
function slide(){
if(!$ul.is(":animated")){
$ul.animate({marginLeft:-width+"px"},300,function(){
$(".main3-control li").removeClass("hover");
$(".main3-control").find("li").eq(i).addClass("hover");
if(i<3){
i++;
}
if(i==3){
i=0;
}
$ul.css("margin-left",0).find("li:first").appendTo($ul);
});
}
}
function slideStart(){
id=setInterval(slide,4000);
}
function slideStop(){
clearInterval(id);
}
$scroll.hover(function(){
slideStop();
$prev.fadeIn(200);
$next.fadeIn(200);
},function(){
slideStart();
$prev.fadeOut(200);
$next.fadeOut(200);
}).trigger("mouseleave");
$next.click(function(){
slideStop();
if(i<3){
i++;
}
if(i==3){
i=0;
}
$(".main3-control li").removeClass("hover");
$(".main3-control").find("li").eq(i).addClass("hover");

$ul.animate({marginLeft:-width+"px"},300,function(){

$ul.css("margin-left",0).find("li:first").appendTo($ul);
});
});
$prev.click(function(){
slideStop();
if(i<3){
i--;
}
if(i==-1){
i=2;
}
$(".main3-control li").removeClass("hover");
$(".main3-control").find("li").eq(i).addClass("hover");
$ul.css("margin-left",-width+"px").find("li").prev().appendTo($ul);
$ul.animate({marginLeft:0},300);
})
});



$(function(){
$(".s_protab-option li").click(function(){
$(this).addClass("clicked").siblings().removeClass("clicked");
var index=$(".s_protab-option li").index(this);
$(".s_protabs").hide().eq(index).show();
})
});
//进度条（根据进度数值直接变化）
$(function(){
var
$lis=$(".s_protabs>ul>li");
function jindu(obj){
var
$line=obj.find(".s_jindu").children("span"),
num=obj.find(".s_jindu-num").children("b").text();
n=parseInt(parseInt(num)/10);
for(var i=0;i<n;i++){
$line.eq(i).addClass("done");
}
if(n==10){
obj.addClass("pro-over");
}
}
for(var j=0;j<$lis.length;j++){
jindu($lis.eq(j));
}
});
$(function(){
var
$lis=$(".s_touzi-pros dl");
function jindu(obj){
var
$line=obj.find(".s_jindu").children("span"),
num=obj.find(".s_jindu-num").children("b").text();
n=parseInt(parseInt(num)/10);
for(var i=0;i<n;i++){
$line.eq(i).addClass("done");
}
if(n==10){
obj.addClass("pro-over");
//whb 我的投资中显示满标复审
//obj.find(".join-btn").text("还款中...").css("cursor","default").click(function(e){
//e.preventDefault()
//});
obj.find(".li4").children("img").attr("src","images/s_pic38-on.png");
}
}
for(var j=0;j<$lis.length;j++){
jindu($lis.eq(j));
}
});

$(function(){
var
$control=$(".s_index-main3 .main3-control li"),
$scroll=$(".s_index-main3 .main3-cont ul"),
width=$scroll.find("li:first").width();
$control.mouseenter(function(){
var index=$control.index(this);
$scroll.animate({left:-index*width+"px"},300);
$(this).addClass("hover").siblings().removeClass("hover");
})
});

$(function(){
var fn;
$(window).bind("scroll",fn=function(){
if($(this).scrollTop()>=(0.3*$(this).height())){
$(".s_float-tip4").show();
}else{
$(".s_float-tip4").hide();
}
});
var time;
function goTop(num){
time=setInterval(function(){
num-=30;
$(window).scrollTop(num);
if(num <= 0){clearInterval(time);}
},10);
}
$(".s_index-main4bg .go-top").click(function(){
$(window).scrollTop(0);
})
});

$(function(){
$(".s_touzi-option li").click(function(){
$(this).addClass("hover").siblings().removeClass("hover");
var index=$(".s_touzi-option li").index(this);
$(".s_touzi-pros").hide().eq(index).show();
})
});

$(function(){
var
$page=$(".s_foot-page");
function page(obj){
var
$prev=obj.children(".page-prev"),
$next=obj.children(".page-next"),
$pages=obj.children(".page-list"),
len=$pages.length,
$text=obj.children("span").children("input"),
i=0;
$pages.click(function(){
var index=$pages.index(this);
i=index;
$pages.removeClass("hover").eq(index).addClass("hover");
$text.val(i+1);
})
$next.click(function(){
if(i<len-1){
$pages.eq(i).removeClass("hover");
$pages.eq(i+1).addClass("hover");
$text.val(i+2);
i++;
}else{
$pages.eq(len-1).removeClass("hover");
$pages.eq(0).addClass("hover");
$text.val(1);
i=0;
}
})
$prev.click(function(){
if(i>0){
$pages.eq(i).removeClass("hover");
$pages.eq(i-1).addClass("hover");
$text.val(i);
i--;
}else{
$pages.eq(0).removeClass("hover");
$pages.eq(len-1).addClass("hover");
$text.val(2);
i=len-1;
}
})
}
for(var j=0;j<$page.length;j++){
page($page.eq(j));
}
});
$(function(){
var time;
function timeend(num){
time=setInterval(function(){
num--;
$(".find-endtips dd b").text(num);
if(num==0){
clearInterval(time);
}
},1000);
}
timeend(3);

});
$(function(){
$(".p_xq014").click(function(){
var width=$(".s_diyawu").width(),
height=$(".s_diyawu").height();
$(".s_diyawu").show()
.css({"margin-left":-width/2+"px","margin-top":-height/2+"px"})
.children("img").css({"max-width":$(window).width(),"max-height":$(window).height()});
$(".s_diyawubg").show();
$(window).resize(function(){
var width=$(".s_diyawu").width(),
height=$(".s_diyawu").height();
$(".s_diyawu").css({"margin-left":-width/2+"px","margin-top":-height/2+"px"})
.children("img").css({"max-width":$(window).width(),"max-height":$(window).height()});
})
})
$(".s_diyawubg").click(function(){
$(".s_diyawubg").hide();
$(".s_diyawu").hide();
})
$(".s_diyawu .close-btn").click(function(){
$(".s_diyawubg").hide();
$(".s_diyawu").hide();
})
})

//选项卡
$(function(){
$(".tab_meun ul li").click(function(){
var $index=$(".tab_meun ul li").index(this);
$(this).addClass("on").siblings().removeClass("on");
$(".tab_content > div").eq($index).show().siblings().hide();
})

})

$(function(){
var
$parent=$(".s_csrollnews .scroll-area"),
$ul=$parent.children("ul"),
height=$ul.find("li:first").height(),
id;
$parent.hover(function(){
clearInterval(id);
},function(){
id=setInterval(function(){
if(!$ul.is(":animated")){
$ul.animate({marginTop:-height+"px"},300,function(){
$ul.css("margin-top","0").find("li:first").appendTo($ul);
});
}
},2000);
}).trigger("mouseleave");
});
$(function(){
if( $(".main1-cont2 .pro-tab").length ){
$(".main1-cont2 .pro-tab:eq(0) ul").addClass("ul-hover");
$(".main1-cont2 .pro-tab:eq(0) .join-btn").addClass("join-btn-hover");

$(".main1-cont2 .pro-tab").hover(
function () {
$(".main1-cont2 .pro-tab:eq(0) ul").removeClass("ul-hover");
$(".main1-cont2 .pro-tab:eq(0) .join-btn").removeClass("join-btn-hover");
$(this).children("ul").addClass("ul-hover");
$(this).children(".join-btn").addClass("join-btn-hover");
},
function () {
$(this).children("ul").removeClass("ul-hover");
$(this).children(".join-btn").removeClass("join-btn-hover");
}
);
}
});
//安全保障
$(function(){
		$(".s_prosafe .next-btn").click(function(){
				$(".s_ninepic").show();
				$(window).scroll(function(){
						$(".s_ninepic").hide();
					})
			})
		
	});
//2014-10-24
$(function(){
		$(".s_inputval").focus(function(){
				if($(this).val()=="未设置"){
						$(this).val("");
					}
			}).blur(function(){
					if($(this).val()==""){
							$(this).val("");
						}
				});
		//投标提示框
		$(".s_subbtn").click(function(){
				$(".s_openboxbg").show();
				$(".s_moneybox").show();
			})
		$(".s_closebtn").click(function(){
				$(".s_openboxbg").hide();
				$(".s_moneybox").hide();
			})
		//常见问题faq
		$(".s_helpfaqcont>dt").click(function(){
				if($(this).next("dd").is(":hidden")){
						$(this).parent().parent().siblings().find("dl dd").slideUp();
						$(this).next("dd").slideDown();
					}else{
							$(this).next("dd").slideUp();
						}
			})
	});
	});




















