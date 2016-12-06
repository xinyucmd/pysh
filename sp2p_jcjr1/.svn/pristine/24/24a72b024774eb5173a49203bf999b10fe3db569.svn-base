// JavaScript Document

//表格行颜色交叉的代码
 $(document).ready(function()
 {
	$("#cap_table tr:odd").css("background","#f6f6f6");//单数
	$("#messagel li:odd").css("background","#f6f6f6");//单数
	$(".invstable tr:even").css("background","#FFFFFF");//偶数行，从0开始
	$(".invstable tr:odd").css("background","#f6f6f6");//单数
	$(".info_table tr:even").css("background","#f6f6f6");//偶数行，从0开始
	$(".info_table tr:odd").css("background","#FFFFFF");//单数
	$(".record_table tr:even").css("background","#f6f6f6");//偶数行，从0开始
	$(".record_table tr:odd").css("background","#FFFFFF");//单数
	$(".cap_list tr:even").css("background","#f6f6f6");//偶数行，从0开始
	$(".cap_list tr:odd").css("background","#FFFFFF");//单数
	$(".admin_mes_list tr:even").css("background","#fbfbfb");//偶数行，从0开始
	$(".admin_mes_list tr:odd").css("background","#FFFFFF");//单数
	$(".admin_mes_list tr:even").attr("bg","#fbfbfb");
    $(".admin_mes_list tr:odd").attr("bg","#FFFFFF");
    $(".admin_mes_list tr").mouseover(function(){
        $(this).css("background","#f7f7f7");
        })   
    $(".admin_mes_list tr").mouseout(function(){
        var bgc = $(this).attr("bg");
        $(this).css("background",bgc);
      })
	$(".top_table tr:even").css("background","#f5f5f5");//偶数行，从0开始
	$(".top_table tr:odd").css("background","#FFFFFF");//单数
 }
);

$().ready(function() {
		$('.wx-qr').hover(function(){
	    	$(this).find('div').stop().slideDown("1000");//可以自己修改速度
	     },function(){
		 	$(this).find('div').stop().slideUp("fast");//可以自己修改速度
		 });

	});	

//导航菜单
$(function() {

    var $el, leftPos, newWidth;
    
    /*
        EXAMPLE ONE
    */
    
    /* Add Magic Line markup via JavaScript, because it ain't gonna work without */
    $("#example-one").append("<li id='magic-line'></li>");
    
    /* Cache it */
    var $magicLine = $("#magic-line");
    $(".current_page_item a").css('color','#0094ea');
    $magicLine
        .width($(".current_page_item").width()-10)
        .css("left",$(".current_page_item a").position().left+10)
        .data("origLeft", $magicLine.position().left)
        .data("origWidth", $magicLine.width());
        
    $("#example-one li").find("a").hover(function() {
        $el = $(this);
        leftPos = $el.position().left+10;
        newWidth = $el.parent().width()-10;
		
        $magicLine.stop().animate({
            left: leftPos,
            width: newWidth
			
        });
		$(".current_page_item a").css('color','#333333');
		
    }, function() {
        $magicLine.stop().animate({
            left: $magicLine.data("origLeft"),
            width: $magicLine.data("origWidth")
        });   
		$(".current_page_item a").css('color','#0094ea');

    });	
});

//公告
	var interval=3000;//两次滚动之间的时间间隔  
    var stepsize=30;//滚动一次的长度，必须是行高的倍数,这样滚动的时候才不会断行  
    var objInterval=null;  
       
    $(document).ready( function(){  
        //用上部的内容填充下部  
        $("#bottom").html($("#top").html());  
           
        //给显示的区域绑定鼠标事件  
        $("#content").bind("mouseover",function(){StopScroll();});  
        $("#content").bind("mouseout",function(){StartScroll();});  
           
        //启动定时器  
        StartScroll();  
        });   
       
        //启动定时器，开始滚动  
        function StartScroll(){  
           objInterval=setInterval("verticalloop()",interval);  
        }  
       
        //清除定时器，停止滚动  
        function StopScroll(){  
         window.clearInterval(objInterval);  
        }  
       
        //控制滚动  
        function verticalloop(){  
            //判断是否上部内容全部移出显示区域  
            //如果是，从新开始;否则，继续向上移动  
            if($("#content").scrollTop()>=$("#top").outerHeight()){  
                  $("#content").scrollTop($("#content").scrollTop()-$("#top").outerHeight());  
            }  
            //使用jquery创建滚动时的动画效果  
            $("#content").animate(  
                {"scrollTop" : $("#content").scrollTop()+stepsize +"px"},600,function(){  
             //这里用于显示滚动区域的scrollTop，实际应用中请删除  
            // $("#foot").html("scrollTop:"+$("#content").scrollTop());   
            });  
        }  
//列表页四个选项
$(function(){
$(".s_touzi-option li").click(function(){
$(this).addClass("hover").siblings().removeClass("hover");
var index=$(".s_touzi-option li").index(this);
$(".s_touzi-pros").hide().eq(index).show();
})
});
//个人中心左侧菜单
$(function(){
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
case "可以转让的借款":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "债权转让"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
case "参与购买的债权":
$(".lne_l3 .level1:eq(1) ul").show();
var liLength = $(".lne_l3 .level1:eq(1) ul li").length;
for (var i=0; i<liLength; i++)
{
if($(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").html() == "债权拍拍"){
$(".lne_l3 .level1:eq(1) ul").find("li").eq(i).children("a").addClass("clicked");
}
}
break;
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
//$(document).ready(function(){
//$(".lne_l3 ul li").click(function()
//{$(this).children("ul").slideToggle("slow");
//$(this).toggleClass("active");
//});
//});
})
});
//选项卡
$(function(){
    $(".tab_meun ul li").click(function(){
    var $index=$(".tab_meun ul li").index(this);
    $(this).addClass("on").siblings().removeClass("on");
   	$(".tab_content > div").eq($index).show().siblings().hide();
    })
});

/*投资详情页面选项*/
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




