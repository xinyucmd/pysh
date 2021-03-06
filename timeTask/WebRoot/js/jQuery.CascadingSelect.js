/*
* jQuery CascadingSelect AddOption
* 草川禾 20120511 开始
*/


jQuery.fn.CascadingSelect = function(target,url,options,endfn){
    $.ajaxSetup({async:false});        
	if(target[0].tagName != "SELECT") throw "target must be SELECT";
    if(url.length == 0) throw "request is required";            
    if(options.parameter == undefined) throw "parameter is required";
	
    this.change(function(){
		var newurl = "";
		urlstr = url.split("?");
		newurl = urlstr[0] + "?" + options.parameter + "=" + $(this).val() + "&" +urlstr[1];
        target.FillOptions(newurl,options);
        if(typeof endfn =="function") endfn();
    });
}

jQuery.fn.AddOption = function(text,value,selected,index){
    option = new Option(text,value);            
	this[0].options.add(option,index);
	this[0].options[index].selected = selected;
}

