var arr;
$(document).ready(function () {
	//alert($("#temp").val());
	var temp=$("#temp").val();
	arr=temp.split('|');
	for(var no in arr){
		arr[no]=arr[no].split('@');
	}
	//alert(arr);
	selectChange();
});
function cancel() {
	window.location.href("login.html");
}
function save() {
//alert(arr);
}
function selectChange() {
	//alert($("#stepSelect").val());
	changeSelect($("#stepSelect").val());
}

function changeSelect(step){
	for(var no in arr){
		if(step==arr[no][0]){
			//alert(arr[no]+":::"+arr[no].length);
			var len=arr[no].length;
			var tmp="";
			for(i=2;i<len;i++){
				tmp=tmp+arr[no][i]+"@";
			}
			tmp=tmp.substr(0,tmp.length-1);
			$('#tels').val(tmp);
		}
	}
}

