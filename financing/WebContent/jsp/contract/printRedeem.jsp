<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

 <%  
    String swfFilePath=session.getAttribute("swfpath").toString();  
%>  
<s:action name="select" id="select" namespace="/jsp/select"></s:action>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
  href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/print/jquery.js"></script>
<script type="text/javascript" src="../../js/print/flexpaper_flash.js"></script>
<script type="text/javascript" src="../../js/print/flexpaper_flash_debug.js"></script>

<script type="text/javascript">

	function cancel(){
		openPage('/contract/listPrintRedeemPage.action');
	}
	
	function download(){
		var id='<s:property value="id"/>';
		openPage('/contract/downloadRedeem.action?id='+id);
	}
	
</script>
</head>

<body>
  <div id="page-wrap">
    
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
    
      <div id="shortcuts" class="headerList">
		      
	<!-- saveClose -->	       
		   
	<!-- saveClose -->	       
		   	   
    <!-- cancel -->
	           <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" plain="true">
			           		<s:text name="button.cancel" />
			           </a>
	          </span>
	          
	          <span style="white-space: nowrap;"> 
			           <a id="cancel_btn" href="#" class="easyui-linkbutton" iconCls="icon-export" onclick="download()" plain="true">
			           		<s:text name="下载" />
			           </a>
	          </span>
      </div>
<!---->

<div id="feature-content">
			<h2>
		    	<s:text name="查看合同" />
		    </h2>
		</div> 
		
<!-- 表单 -->			
	<div id="feature-content">  
            <a id="viewerPlaceHolder" style="width:1100px;height:500px;display:block"></a>  
            <script type="text/javascript">   
                var fp = new FlexPaperViewer(     
                         '../../flash/FlexPaperViewer',  
                         'viewerPlaceHolder', { config : {  
                         SwfFile : escape('../../<%=swfFilePath%>'),  
                         Scale : 0.6,   
                         ZoomTransition : 'easeOut',  
                         ZoomTime : 0.5,  
                         ZoomInterval : 0.2,  
                         FitPageOnLoad : false,  
                         FitWidthOnLoad : true,  
                         FullScreenAsMaxWindow : false,  
                         ProgressiveLoading : false,  
                         MinZoomSize : 0.2,  
                         MaxZoomSize : 5,  
                         SearchMatchAll : false,  
                         InitViewMode : 'Portrait',  
                    	 PrintPaperAsBitmap : false,
                         ViewModeToolsVisible : true,  
                         ZoomToolsVisible : true,  
                         NavToolsVisible : true,  
                         CursorToolsVisible : true,  
                         SearchToolsVisible : true,  
                          
                         localeChain: 'zh_CN'  
                         }});  
            </script>              
        </div> 
    
    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



