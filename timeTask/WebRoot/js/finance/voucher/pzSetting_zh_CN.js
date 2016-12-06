
var sts;
$(document).ready(function () {
	sts = $("#sts").val();
	if(sts.charAt(0)=='1'){
		$("#a1").attr("checked","true");
	}
	if(sts.charAt(1)=='1'){
		$("#a2").attr("checked","true");
	}
	if(sts.charAt(3)=='1'){
		$("#b1").attr("checked","true");
	}
	if(sts.charAt(4)=='1'){
		$("#b2").attr("checked","true");
	}
});
function save() {
	sts="";
	if ($("#a1").attr("checked")) {
		sts=sts+"1";
	}else{
		sts=sts+"0";
	}
	if ($("#a2").attr("checked")) {
		sts=sts+"1";
	}else{
		sts=sts+"0";
	}
	sts=sts+"@";
	if ($("#b1").attr("checked")) {
		sts=sts+"1";
	}else{
		sts=sts+"0";
	}
	if ($("#b2").attr("checked")) {
		sts=sts+"1";
	}else{
		sts=sts+"0";
	}
	//alert(sts);
	$('#sts').val(sts);
}

