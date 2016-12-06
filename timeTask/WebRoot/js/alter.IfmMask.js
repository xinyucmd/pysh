/* 
* IfmMask 1.1
* Copyright (c) 2010 Alterhz http://www.alterteam.cn
* Date: 2010-5-3
* 运用iframe元素添加的遮罩层，可以遮挡flash。
* 参数列表：
* zId：遮罩层iframe的Id号
* v：遮罩层是否可见
* l：遮罩层左边距
* t：遮罩层右边距
* w：遮罩层宽度
* h：遮罩层高度
* index：遮罩层深度
* o：遮罩层透明度
* color：遮罩层的颜色
* 函数列表：
* IfmMask.IfmMask(zId, v, l, t, w, h, index, o, color) //构造函数
* IfmMask.getVisible() //获取遮罩层的显示和隐藏状态
* IfmMask.show() //显示遮罩层
* IfmMask.hide() //隐藏遮罩层
* IfmMask.fadeIn() //缓慢淡入遮罩层
* IfmMask.fadeOut() //缓慢淡出遮罩层
* IfmMask.changeSize(w, h) //改变遮罩层的尺寸
* IfmMask.changeZoom(index) //改变遮罩层的深度
* IfmMask.changeAlpha(o) //改变遮罩层的透明度
* IfmMask.changePosition(l, t) //改变遮罩层的位置
* IfmMask.getDOM() //兼容IE、Firefox的遮罩层iframe DOM获取函数
* 使用方法，例如：
* (1) var z1 = new IfmMask('z1', true, "30px", "30px", "800px", "600px", 2, 0.5, "#f00");
* (2) var z2 = new IfmMask('z2'); z2.changePosition("200px", "200px");
* (3) $(z1.getDOM()).bind("click", function() { //事件处理函数	});  //给遮罩层添加事件处理函数
*/
//遮罩层的颜色
var maskColor;

//遮罩层构造函数
function IfmMask(zId, v, l, t, w, h, index, o, color) {
	//遮罩层的Id号
	this.id = zId;
	//判断参数是否存在
	if("undefined" == typeof(color)) {
		maskColor = "#000000";
	}
	else {
		maskColor = color;
	}
	//遮罩层iframe的html创建代码
	var ifmStr = "<iframe id='" + zId + "' frameborder='0' src='javascript:parent.getPage()'></iframe>";
	//将iframe添加到body中
	$("body").append(ifmStr);
	
	//判断参数是否存在
	if("undefined" == typeof(l)) {
		l = "0px";
	}
	if("undefined" == typeof(t)) {
		t = "0px";
	}
	if("undefined" == typeof(w)) {
		//获取屏幕宽度
		w = $("body").width();
	}
	if("undefined" == typeof(h)) {
		//获取屏幕高度。
		h = $("body").height();
	}
	if("undefined" == typeof(index)) {
		index = 2010;
	}
	//改变遮罩层的css。
	$("#" + this.id).css({"position": "absolute", "left": l,"top": t , "width": w, "height": h, "z-index": index});
	//判断参数是否存在
	if("undefined" == typeof(o)) {
		o = 0.5;
	}
	//改变遮罩层的透明度。
	$("#" + this.id).fadeTo("fast", o);
	
	//判断参数是否存在。
	if("undefined" == typeof(v)) {
		v = true;
	}
	
	//遮罩层是否可见
	if(!v) {
		$("#" + this.id).hide();
	}
};
//改变遮罩层的深度
IfmMask.prototype.changeZoom = function(index) {
	$("#" + this.id).css({"z-index": index});
}
//获取遮罩层的显示和隐藏状态
IfmMask.prototype.getVisible = function() {
	if("none" == $("#" + this.id).css("display")) {
		return false;
	}
	else {
		return true;
	}
}
//显示遮罩层
IfmMask.prototype.show = function() {
	$("#" + this.id).show();
}
//隐藏遮罩层
IfmMask.prototype.hide = function() {
	$("#" + this.id).hide();
}
//缓慢淡入遮罩层
IfmMask.prototype.fadeIn = function() {
	$("#" + this.id).fadeIn();
}
//缓慢淡出遮罩层
IfmMask.prototype.fadeOut = function() {
	$("#" + this.id).fadeOut();
}
//改变遮罩层的尺寸
IfmMask.prototype.changeSize = function(w, h) {
	$("#" + this.id).css({"width": w, "height": h});
}
//改变遮罩层的位置
IfmMask.prototype.changePosition = function(l, t) {
	$("#" + this.id).css({"left": l, "top": t});
}
//改变遮罩层的透明度
IfmMask.prototype.changeAlpha = function(o) {
	$("#" + this.id).fadeTo("fast", o);
};
//兼容IE、Firefox的遮罩层iframe DOM获取函数
IfmMask.prototype.getDOM = function() {
	return document.getElementById(this.id).contentDocument || document.frames[this.id].document;
};
//iframe元素的子页面
function getPage() {
    var code = ["<html>"];
    code.push("<body style='background:" + maskColor + "'>");
    code.push("</body>");
    code.push("</html>");
    return code.join("");
}