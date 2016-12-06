<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

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
<script type="text/javascript" src="../../js/jquery.edatagrid.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>


<script type="text/javascript">

    function save(){
    	$('#tt').edatagrid('saveRow');   	
    	$('#tt').edatagrid('reload');
    }

    function saveSub(){
    	$('#aa').edatagrid('reload'); 
    }
    

	$(function() {
		
		document.getElementById("toolbar2").style.display="none";

		$('#tt').edatagrid({
			url : 'listDictionary.action',
			saveUrl : 'saveDictionary.action',
			updateUrl : 'saveDictionary.action',
			destroyUrl : 'deleteDictionary.action'
			
		});
	    $("#delete").click(function() {	
	    	many_deleterow_easyui('deleteDictionaryAll.action?seleteIDs=');
		});	
	    
	    $("#deleteSub").click(function() {	
	    	   rows = $('#aa').datagrid('getSelections');
	           var num =  rows.length;
	           var url = 'deleteDictionary.action?seleteIDs=';
	           
	           
	    		if (num==0){
	    			$.messager.alert("警告","没有被选中记录.");
	    		}
	    		else {
	    			$.messager.confirm('确认','你确认要删除?',function(r){
	    				if (r){
	    					var ids = null ;
	    					data = rows.concat(); 
	    					for (var i = 0 ;i < num; i++){
	    						if (null == ids || i == 0 ) {                 
	    						    ids =  data[i].id;             
	    						} else  {                 
	    							ids = ids + "," +  data[i].id;             
	    						} 
	    					}	
	    					for (var i = num - 1 ;i >= 0; i--){
	    						index = $('#aa').datagrid('getRowIndex',data[i]);
	    						$('#aa').datagrid('deleteRow',index);
	    					}						
	    				    url = url + ids; 
	    				    $.ajax({
	    					    type: "POST",
	    					    url: url,
	    					    data: "",
	    					    dataType:"text", 
	    					    success: function(json){  
	    					    	
	    					    },
	    					    error: function(json){
	    					      alert("json=" + json);
	    					      return false;
	    					    }
	    					  });
	    				}
	    			});
	    		}
		});	
	    $("#edit").click(function() {	
	    	rows = $('#tt').datagrid('getSelections');
	        var num =  rows.length;
	        var parentId='';
	        $.each(rows, function (i, item) { 	
	        	parentId=item.opCode;		
	    	});
	        if (num!=1){
				window.parent.window.$.messager.alert("警告","请选择一条记录.");
			}else{
				$('#aa').edatagrid({
					url : 'listSubDict.action?parentId='+parentId,
					saveUrl : 'saveSubDict.action?parentId='+parentId,
					updateUrl : 'updateSubDict.action',
					destroyUrl : 'deleteDictionary.action'
				   
			   });	
			}
      
		});	
	    
	    
	});
</script>
</head>
<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="feature-title">
        <h2>
          <s:property value="#request.title" />
        </h2>
      </div>
      <div id="feature-content">
      <!--
        <table style="" cellspacing="10" cellpadding="0" width="100%">
          <s:if test="hasActionErrors()">
            <tr>
              <td align="left" colspan="4"><font color="red">
                <s:actionerror /></font></td>
            </tr>
          </s:if>
        </table>
  -->
        <table id="tt" title='<s:property value="#request.title"/>'
          style="width: 1000px; height: 250px " toolbar="#toolbar" 
          pagination="true" rownumbers="true" fitColumns="true" pageList="[5]"
          singleSelect="false">
          <thead>
            <tr>
              <th data-options="field:'ck',checkbox:true"></th>
              <th field="entity.id" width="1" hidden="true"><s:text
                  name='entity.id.label' /></th>
              <th field="opCode" width="1" hidden="true"><s:text
                  name='entity.id.label' /></th>
              
              <th field="entity.opEnName" width="50"
                editor="{type:'validatebox',options:{required:true}}">
                <s:text name='entity.select.label' /> <s:if
                  test="#request.entityName == 'ReminderOption'">
                  <s:text name='entity.unit.label' />
                </s:if>
              </th>
              
              <th field="entity.opCnName" width="50"
                editor="{type:'validatebox',options:{required:true}}">
                <s:text name='entity.dict.label' /> <s:if
                  test="#request.entityName == 'ReminderOption'">
                  <s:text name='entity.unit.label' />
                </s:if>
              </th>
            
          </thead>
        </table>
        
        <table id="aa" title='<s:text name="title.grid.wealthParnDic.sub" />'
          style=" width: 1000px; height: 250px; visibility:hidden" toolbar="#toolbar2"
          pagination="true" rownumbers="true" fitColumns="true" pageList="[5]"
          singleSelect="false">
          <thead>
            <tr>
              <th data-options="field:'ck',checkbox:true"></th>
              <th field="entity.id" width="1" hidden="true"><s:text
                  name='entity.id.label' /></th>
                     
               <th field="entity.opCode" width="50"  editor="{type:'validatebox',options:{required:true}}" ><s:text
                  name='entity.value.label' /></th>
              <th field="entity.opCnName" width="50"  editor="{type:'validatebox',options:{required:true}}"><s:text
                  name='entity.opCnName.label' /></th>
              <th field="entity.opEnName" width="50" editor="text"><s:text
                  name='entity.opEnName.label' /></th>
             
          </thead>
        </table>
      
        <div id="toolbar" >
        
            <a href="#" class="easyui-linkbutton" iconCls="icon-add"
              plain="true"
              onclick="javascript:$('#tt').edatagrid('addRow')"><s:text
                name='button.create' /></a>
          
          
            <a id="delete" href="#" class="easyui-linkbutton"
              iconCls="icon-remove" plain="true"><s:text
                name='button.delete' /></a>
         
         
            <a href="#" class="easyui-linkbutton" iconCls="icon-save"
              plain="true"
              onclick="save()"><s:text
                name='button.save' /></a>
         
          <a href="#" class="easyui-linkbutton" iconCls="icon-undo"
            plain="true"
            onclick="javascript:$('#tt').edatagrid('cancelRow')"><s:text
              name='button.cancel' /></a>
              
        
            <a id="edit" href="#" class="easyui-linkbutton"
              iconCls="icon-edit" plain="true"><s:text
                name='修改' /></a>
       
        </div>
        
         <div id="toolbar2">
          
            <a href="#" class="easyui-linkbutton" iconCls="icon-add"
              plain="true"
              onclick="javascript:$('#aa').edatagrid('addRow')"><s:text
                name='button.create' /></a>
        
         
            <a id="deleteSub" href="#" class="easyui-linkbutton"
              iconCls="icon-remove" plain="true" ><s:text
                name='button.delete' /></a>
        
          
            <a href="#" class="easyui-linkbutton" iconCls="icon-save"
              plain="true"
              onclick="javascript:$('#aa').edatagrid('saveRow'),saveSub()"><s:text
                name='button.save' /></a>
        
          <a href="#" class="easyui-linkbutton" iconCls="icon-undo"
            plain="true"
            onclick="javascript:$('#aa').edatagrid('cancelRow')"><s:text
              name='button.cancel' /></a>
              
        </div>
      </div>
    </div>
    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



