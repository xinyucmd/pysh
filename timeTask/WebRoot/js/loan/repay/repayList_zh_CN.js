/* 草川禾 20120511 开始*/
$(document).ready(function(){
	// 当前窗口对象
	var $window = $(window);
	var maiheight ,
		mainwidth,
		cellWidth,
		gapTop,
		otherpm,
		w;
	
	 w = $("#ptable").width() - 3;
	 gapTop =  0;
	 otherpm = 110; //GridHead，toolbar，footer,gridmargin
	
	maiheight = $window.height();
	maiwidth = $window.width();
	cellWidth = (maiwidth-118)/8;
	cellWidth=parseInt(cellWidth);
	var h = maiheight - gapTop - otherpm;
	var grid=$("#flexTable").flexigrid({
		width: w,
		height: h,
		url: 'repay_repayList.action',
		dataType: 'json',
		colModel : [
		//{display: '编号', name : 'id', width : 50, sortable : true, align: 'center',hide: true,toggle : false,process:formatMoney},
			{display: '借据号', name : 'due_No', width : cellWidth, sortable : true, align: 'center'},
			{display: '客户名称', name : 'cif_Name', width : cellWidth, sortable : true, align: 'center'},
			{display: '借据金额', name : 'due_Amt', width : cellWidth, sortable : true, align: 'center'},
			{display: '应还本金', name : 'return_Capital', width : cellWidth, sortable : true, align: 'center'},
			{display: '应还利息', name : 'return_Interest', width : cellWidth, sortable : true, align: 'center'},																
			{display: '开始日期', name : 'beg_Date', width : cellWidth, sortable : true, align: 'center'},																
			{display: '结束日期', name : 'end_Date', width : cellWidth, sortable : true, align: 'center'},															
			{display: '客户号', name : 'cif_No', width : cellWidth, hide: true, align: 'center'},															
			{display: '期号', name : 'term_No', width : cellWidth, hide: true, align: 'center'},															
			{display: '账户管理费', name : 'acc_Fee', width : cellWidth,hide: true,  align: 'center'},															
			{display: '操作', name : 'op', width : cellWidth, link: true, align: 'center',process:formatMoney1}														
																	
			],
		buttons : [
			{ name: 'add', displayname: '新增', bclass: 'add', onpress: toolbarItem },
            { name: 'modify', displayname: '修改', bclass: 'modify', onpress: toolbarItem },
            { name: 'delete', displayname: '删除', bclass: 'delete', onpress: toolbarItem },
			{separator: true}
			],
		searchitems : [
			{display: '借据号', name : 'due_No'},
			{display: '客户号', name : 'cif_Name'},
			{display: '借据金额', name : 'due_Amt'},
			{display: '应还本金', name : 'return_Capital'},
			{display: '应还利息', name : 'return_Interest'},																
			{display: '开始日期', name : 'beg_Date'},																
			{display: '结束日期', name : 'end_Date'}	
			],
		errormsg: '发生异常',
		sortname: "due_No",
		sortorder: "desc",
		//qop: "LIKE",//搜索的操作符
		usepager: true,
		title: '还款列表',
		pagestat: '显示记录从{from}到{to}，总数 {total} 条',
		useRp: true,
		rp: 5,
		rpOptions: [5, 10, 15, 20, 40, 100], //可选择设定的每页结果数
		nomsg: '没有符合条件的记录存在',
		minColToggle: 1, //允许显示的最小列数 

		showTableToggleBtn: true,
		autoload: true, //自动加载，即第一次发起ajax请求
		resizable: false, //table是否可伸缩 indicator_waitanim.gif

		procmsg: '加载中, 请稍等 ...',
		hideOnSubmit: true, //是否在回调时显示遮盖
		blockOpacity: 0.5,//透明度设置

		showcheckbox: true,//是否显示第一列的checkbox（用于全选）
		gridClass: "bbit-grid",//样式
        rowhandler: contextmenu,//是否启用行的扩展事情功能,在生成行时绑定事件，如双击，右键菜单等

		rowbinddata: true,//配合上一个操作，如在双击事件中获取该行的数据
		onrowchecked: callme//在每一行的的checkbox选中状态发生变化时触发某个事件
		//onrowchecked: false//在每一行的的checkbox选中状态发生变化时触发某个事件
	});
	function callme(){
		//TODO
	}
	function contextmenu(row) {
        var menu = { width: 150, items: [
			             { text: "查看", icon: "images/icons/search.png", alias: "contextmenu-view", action: contextMenuItem },
			             { text: "编辑", icon: "images/icons/modify.png", alias: "contextmenu-modify", action: contextMenuItem },
			             { text: "删除", icon: "images/icons/close.png", alias: "contextmenu-delete", action: contextMenuItem }
			        	]
    				};
		function contextMenuItem(target) {
            var id = $(target).attr("id").substr(3);
            var cmd = this.data.alias;//别称
            var ch = $.browser.msie ? target.ch : target.getAttribute("ch");
            var cell = ch.split("_FG$SP_");
            if (cmd == "contextmenu-view") {
                //TODO
            }
            else if (cmd == "contextmenu-modify") {
            	//TODO
            }
            else if (cmd == "contextmenu-delete") {
                var name = cell[1];
                //TODO
                if (confirm("你确认要删除操作员 [" + name + "] 吗？")) {
                    $.ajax({
						type: "POST",
						url: "flexGridServlet.do?action=delete",
						data: "id="+id,
						dataType:"text",
						success: function(msg){
							if(msg=="success"){
								$("#flexTable").flexReload();
							}else{
								alert("有错误发生,msg="+msg);
							}
						},
						error: function(msg){
							alert("msg="+msg);
						}
					});
                }
            }
            else {
                $("#flexTable").flexReload();
            }
        }
        $(row).contextmenu(menu);
    }
	
	function toolbarItem(com, grid) {
       if (com=='delete'){
			deleteMe();
		}else if (com=='add'){
			openDialogAdd();
		}else if (com=='modify'){
			openDialogModify();
		}
    }
    //操作函数
	function formatMoney(value, pid) {
         return "￥" + parseFloat(value).toFixed(2);
    }
	function formatMoney1(value, pid,row) {
		//alert(row.cell)
		//alert(row.cell[1])
		//alert(row.cell[2])
		//alert(row.cell[3])
		//alert(row.cell[4])
       return "<a href='#'>连接</a>";  // return "￥" + parseFloat(value).toFixed(2);
    }
    function openDialogAdd(){
		$('#dialogAdd').dialog({
			closed:false
		});
	}
    function openDialogModify(){
    	if($(".trSelected",grid).length==1){
			$('#dialogModify').dialog({
				closed:false
			});
		}else if($(".trSelected",grid).length>1){
			alert("请选择一个修改,不能同时修改多个记录!");
		}else if($(".trSelected",grid).length==0){
			alert("请选择一个您要修改的记录!")
		}
	}
    
    function deleteMe(){
		if($('.trSelected',grid).length==0){
			alert("请选择要删除的数据!");
		}else{
			if(confirm('是否删除这 ' + $('.trSelected',grid).length + ' 条记录吗?')){
				 var  ids ="";
			      $('.trSelected td:nth-child(2)',grid).each(function(){
			      	ids+=","+$(this).text();
			      });
			      ids=ids.substring(1);
			      //TODO
			      $.ajax({
						type: "POST",
						url: "flexGridServlet.do?action=delete",
						data: "id="+ids,
						dataType:"text",
						success: function(msg){
							if(msg=="success"){
								$("#flexTable").flexReload();
							}else{
								alert("有错误发生,msg="+msg);
							}
						},
						error: function(msg){
							alert("msg="+msg);
						}
					});
		      	}
		   }
	}
    //dialog start （add）

    $('#dialogAdd').dialog({
    		href:'addmodify.jsp',
    		onClose:function(){
    			$('#dialogAdd #form1')[0].reset();
    			//document.forms[0].reset();
				//$('#dialogAdd #form1 :input').val("");//清空表单值

				//$('#dialogAdd #form1 #language')[0].selectedIndex = 0;//恢复select的默认值

    		},
   			closed:true,
   			width:320,
   			height:400,
   			showType:'fade',//'slide','fade','show'
   			showSpeed:400,
			iconCls: 'icon-save',
			buttons:{
				'\u65B0\u589E':function(){//新增
					var params=$("#dialogAdd #form1").serialize();
					$.ajax({
							type: "POST",
							url: "flexGridServlet.do?action=add",
							data: encodeURI(params),
							dataType:"text",
							success: function(msg){
								if(msg=="success"){
									$('#flexTable').flexReload();
									$('#dialogAdd').dialog({closed:true});
								}else{
									$('#dialogAdd').dialog({closed:true});
									alert("有错误发生,msg="+msg);
								}
							},
							error: function(msg){
								alert("msg="+msg);
							}
					});
				},
				'\u53D6\u6D88':function(){//取消
					$('#dialogAdd').dialog({closed:true});
				}
			}
	})
	
	 //dialog start （Modify）

    $('#dialogModify').dialog({
    		href:'addmodify.jsp',
    		onOpen:function(){
    			var id=$('.trSelected td:nth-child(2)',grid).text();
    			log.info('id='+id);
    			//ps：这样获取是不妥的，为了简单先不改。

    			$('#dialogModify #form1 #name').val($('.trSelected td:nth-child(3)',grid).text());
    			$('#dialogModify #form1 #address').val($('.trSelected td:nth-child(4)',grid).text());
    			$('#dialogModify #form1 #salary').val($('.trSelected td:nth-child(5)',grid).text().substring(1));
    			$('#dialogModify #form1 #date').val($('.trSelected td:nth-child(6)',grid).text());
    			$('#dialogModify #form1 #language').val($('.trSelected td:nth-child(7)',grid).text());
    		},
    		onClose:function(){
    			$('#dialogModify #form1')[0].reset();
				//$('#dialogModify #form1 :input').val("");//清空表单值

				//$('#dialogModify #form1 #language')[0].selectedIndex = 0;//恢复select的默认值

    		},
   			closed:true,
   			width:320,
   			height:400,
   			showType:'fade',//'slide','fade','show'
   			showSpeed:400,
			iconCls: 'icon-modify',
			buttons:{
				'\u4FEE\u6539':function(){//修改
					var params=$("#dialogModify #form1").serialize();//此处必须加上#dialogModify
					var id=$('.trSelected td:nth-child(2)',grid).text();
					//TODO
					$.ajax({
							type: "POST",
							url: "flexGridServlet.do?action=modify&id="+id,
							data: encodeURI(params),
							dataType:"text",
							success: function(msg){
								if(msg=="success"){
									$("#flexTable").flexReload();
									$('#dialogModify').dialog({closed:true});
								}else{
									$('#dialogModify').dialog({closed:true});
									alert("有错误发生,msg="+msg);
								}
							},
							error: function(msg){
								alert("msg="+msg);
							}
					});
				},
				'\u53D6\u6D88':function(){//取消
					$('#dialogModify').dialog({closed:true});
				}
			}
	});
	
	//context menu start
	 $('span.context').contextMenu('contextMenu', {
	      bindings: {
				        'add': function(t) {
				          openDialogAdd();
				        },
				        'modify': function(t) {
				        	openDialogModify();
				          //alert('Trigger was '+t.id+'\nAction was Save');
				        },
				        'delete': function(t) {
				          deleteMe();
				        }
				    },
			/*
	      menuStyle:{
				      border : "2px solid green"
				    },
	      itemStyle:{
				        fontFamily : 'verdana',
				        backgroundColor : '#666',
				        color: 'white',
				        border: 'none',
				        padding: '1px'
				    },
	
	      itemHoverStyle:{
					        color: '#fff',
					        backgroundColor: '#0f0',
					        border: 'none'
					      },
					      */
	      shadow:true,
	      eventPosX:0,
	      eventPosY:0
	      //onContextMenu:function(e){if ($(e.target).attr('id') == 'dontShow') return false;else return true; },
	      //onShowMenu:function(e, menu) {if ($(e.target).attr('id') == 'showOne') { $('#item_2, #item_3', menu).remove();} return menu;}
    });
    
    //联动select option start
    //TODO
     $("#province").FillOptions(
     		"flexGridServlet.do?action=getProvince",
     		{
      		datatype:"json",
      		textfield:"province",
      		valuefiled:"provinceID",
      		//selectedindex:0,//填充并选中第1项

      		keepold:true//填充并且保留原有项

     		}
     );
	 $("#province").AddOption("请选择省份：","-1",true,0);//最上端插入一个文本为“请选择省份“，值为”-1“的列表项，并且是选中状态

     $("#city").AddOption("请选择城市：","-1",true,0);
      $("#province").CascadingSelect(
           $("#city"),//需要联动的下拉列表框，必须
           "flexGridServlet.do?action=getCity",
           {datatype:"json",textfield:"city",valuefiled:"cityID",parameter:"p"},//通过设置parameter:”p”这个参数会生成一个"handler1.ashx?p=xxx”这样的地址来做ajax请求
           function(){//完成联动后执行

           		//log.info("测试");
           }
      );
	
});
// 跳转到还款详情页面
function myclick(parm){
var	mydata =  parm.split('#');
	 alert(mydata[9]);
    var name=uniencode(mydata[1]);
	window.location.href="repay_repayDetial.action?repayBean.dueNo="+mydata[0]+"&repayBean.begDate="+mydata[5]+"&repayBean.endDate="+mydata[6]+"&repayBean.cifNo="+mydata[7]+"&repayBean.dueAmt="+mydata[2]+"&repayBean.termNo="+mydata[8]+"&repayBean.cifName="+name+"&repayBean.otherFee="+mydata[9];
}

function uniencode(text) 
{ 
    text = escape(text.toString()).replace(/\+/g, "%2B"); 
           

    var matches = text.match(/(%([0-9A-F]{2}))/gi); 
    if (matches) 
    { 
        for (var matchid = 0; matchid < matches.length; matchid++) 
        { 
            var code = matches[matchid].substring(1,3); 
            if (parseInt(code, 16) >= 128) 
            { 
                text = text.replace(matches[matchid], '%u00' + code); 
            } 
        } 
    } 
    text = text.replace(/%/g,'\\');
    return text; 
} 






/* 草川禾 20120511 结束*/