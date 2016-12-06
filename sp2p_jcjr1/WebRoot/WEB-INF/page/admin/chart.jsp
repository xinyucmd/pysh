<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>标的组成图表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/echarts.min.js"></script>
<link href="/../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/script/jquery.shove-1.0.js"></script>
<script language="javascript" type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
<link href="/script/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/script/jquery.pagination.js"></script>
<style>
	body { font-size: 14px;  font-family: "微软雅黑";}
	* { margin: 0; padding: 0; }
	ul, ol { list-style: none; }
	em, i { font-style: normal; }
	img { border: none; vertical-align: middle; }
	.clear { clear: both; }
	.clearfix { zoom: 1; }
	.clearfix:after { display: block; visibility: hidden; content: '.'; height: 0; clear: both; }
	a { font-family: "微软雅黑"; text-decoration: none; color: #666; cursor: pointer; }
	#right{ overflow:hidden; width:auto; margin-top:10px;}
	.right_title{}
	.right_title .cen_input{border: 1px solid #d9d9d9; height: 22px; line-height: 22px;}
	.right_title .cen_btn{border: 1px solid #d9d9d9; height: 24px; line-height: 22px; width:60px; margin:0 10px; cursor:pointer;}
	.right_title span{ margin-left:10px;}
   .zh_table td {border: 1px solid #ddd;text-align: center; width:12.5%; height:22px; line-height:22px;}
   .zh_table1_1 td {border: 1px solid #ddd;text-align: center; width:10%; height:40px; line-height:40px;}
   .zh_table1_1 th {background-color: #e8ecef;border: 1px solid #ddd;font-weight: normal;height: 40px;text-align: center; width:10%;}
</style>
</head>
<body>
	<div id="right">
	 <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1200px;height:400px;padding: 10px;"></div>
    <script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));
 // 显示标题，图例和空的坐标轴
 myChart.setOption({
     title: {
         text: '总投资图表'
     },
     tooltip: {},
     toolbox: {
         show : true,
         feature : {
             dataView : {show: true, readOnly: false},
             magicType : {show: true, type: ['line', 'bar']},
             restore : {show: true},
             saveAsImage : {show: true}
         }
     },
     calculable : true,
     legend: {
         data:['总投资额','年化投资额']
     },
     xAxis: {
         data: []
     },
     yAxis: {
         axisLabel : {
             formatter: '{value}元'
         }
     },
     series: [{
         name: '总投资额',
         type: 'bar',
         markPoint : {
             data : [
                 {type : 'max', name: '最大值'},
                 {type : 'min', name: '最小值'}
             ]
         },
         markLine : {
             data : [
                 {type : 'average', name: '平均值'}
             ]
         },
         data: []
     },{
    	 name: '年化投资额',
         type: 'bar',
         markPoint : {
             data : [
                 {type : 'max', name: '最大值'},
                 {type : 'min', name: '最小值'}
             ]
         },
         markLine : {
             data : [
                 {type : 'average', name: '平均值'}
             ]
         },
         data: []
     }]
 });

 myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
 
 var cmonths=[];    //类别数组（实际用来盛放X轴坐标值）
 var allinvests=[];    //销量数组（实际用来盛放Y坐标值）
 var yearinvests=[];    //销量数组（实际用来盛放Y坐标值）
 $.ajax({
 type : "post",
 async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
 url : "createCharts.do",    //请求发送到TestServlet处
 data : {},
 dataType : "json", 
 //返回数据形式为json
 success : function(result) {
     //请求成功时执行该函数内容，result即为服务器返回的json对象
     if (result) {
            for(var i=0;i<result.length;i++){       
               cmonths.push(result[i].cmonth);    //挨个取出类别并填入类别数组
             }
            for(var i=0;i<result.length;i++){       
            	allinvests.push(result[i].allinvest);    //挨个取出销量并填入销量数组
              }
            for(var i=0;i<result.length;i++){       
            	yearinvests.push(result[i].yearinvest);    //挨个取出销量并填入销量数组
              }
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption({        //加载数据图表
            	xAxis : [
                         {
                       axisLabel: {
                    	   interval:0,
                          rotate: 45
                      },
                      data:cmonths }
                       ],
                formatDate:function(cmonths){
			    	if(cmonths.length!=8) return;
			    	return cmonths.substring(2,4)+'/'+cmonths.substring(4,6)+'/'+cmonths.substring(6,8);
			    	},
                series: [{
                    // 根据名字对应到相应的系列
                    name: '总投资额',
                    data: allinvests
                },{
                	// 根据名字对应到相应的系列
                    name: '年化投资额',
                    data: yearinvests
                }]
            });
            
     }
 
},
 error : function(errorMsg) {
     //请求失败时执行该函数
 alert("图表请求数据失败!");
 myChart.hideLoading();
 }
})
    </script>
    </div>
</body>
</html>
