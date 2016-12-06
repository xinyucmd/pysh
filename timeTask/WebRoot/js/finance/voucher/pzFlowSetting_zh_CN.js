
$(document).ready(function () {
	//alert($("#flowno").val());
	var flowno=$("#flowno").val()
	alert(flowno);
	var flownoArray= flowno.split("@");
	for(var no in flownoArray){
	//alert(flownoArray[no]);
		if('2'==flownoArray[no]){
			$('#c1').attr("checked","true");
		}
		if('3'==flownoArray[no]){
			$('#c2').attr("checked","true");
		}
		if('4'==flownoArray[no]){
			$('#c3').attr("checked","true");
		}
	}
});
function cancel() {
	window.location.href("login.html");
}
function save() {
	var flowno="";
	if($('#c1').attr("checked")){
		flowno=flowno+"2@";
	}
	
	if($('#c2').attr("checked")){
		flowno=flowno+"3@";
	}
	
	if($('#c3').attr("checked")){
		flowno=flowno+"4@";
	}
	flowno=flowno.substr(0,flowno.length-1);
	$("#flowno").val(flowno);
	//alert($("#flowno").val());
	form01.submit();
}

