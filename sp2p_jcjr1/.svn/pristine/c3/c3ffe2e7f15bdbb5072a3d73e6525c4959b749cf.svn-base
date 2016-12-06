<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>总注册来源</title>
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
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));
 // 显示标题，图例和空的坐标轴
  myChart.setOption({
	  title: {
         text: '总注册来源'
     },
	 tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        data:['PC','安卓','苹果','其他']
    },
    series: [
        {
            name:'总注册来源',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[]
        }
    ]
    
 });

 myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
 
 $.ajax({
 type : "post",
 async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
 url : "regSourceCharts.do",    //请求发送到TestServlet处
 data : {},
 dataType : "json", 
 //返回数据形式为json
 success : function(result) {
     //请求成功时执行该函数内容，result即为服务器返回的json对象
     if (result) {
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption({        //加载数据图表
                series: [{
                	 type:'pie',
                	 radius: ['50%', '70%'],
                     avoidLabelOverlap: false,
                     
                     label: {
                         normal: {
                             show: false,
                             position: 'center'
                         },
                         emphasis: {
                             show: true,
                             textStyle: {
                                 fontSize: '30',
                                 fontWeight: 'bold'
                             }
                         }
                     },
                     labelLine: {
                         normal: {
                             show: false
                         }
                     },
                	 data: (function(){
                         var res = [];
                         var len = 0;
                         for(var i=0,size=result.length;i<size;i++) {
                        	 if (result[i].regSrc==0)
                        	 {
                        		 result[i].regSrc="PC";
                        	 }
                        	 if (result[i].regSrc==1)
                        	 {
                        		 result[i].regSrc="安卓";
                        	 }
                        	  if (result[i].regSrc==2)
                        	 {
                        		 result[i].regSrc="苹果";
                        	 }
                        	  if (result[i].regSrc==4)
                        	 {
                        		 result[i].regSrc="其他";
                        	 }
                         res.push({
                         name: result[i].regSrc,
                         value: result[i].alluser
                         });
                         }
                         return res;
                         })()
                         
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
