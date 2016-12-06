<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="common.jsp"%>
<script  src="<%=path%>/js/main.js"></script>
<title><s:text name="mainTitle"></s:text></title>
</head>
<body>
<div id="ptable">
	<table id="flexTable" style="display:none"></table>
</div>  
<div id="dialogAdd" class="mydialog" title="添加" ></div>
<div id="dialogModify" class="mydialog" title="修改" ></div>
<span class="context" style="display:none;">右键显示菜单</span>
<div class="contextMenu" id="contextMenu" style="display:none;">
      <ul>
        <li id="add"><img src="<%=path%>/images/icons/add.png" /> Add</li>
        <li id="modify"><img src="<%=path%>/images/icons/modify.png" /> Modify</li>
        <li id="delete"><img src="<%=path%>/images/icons/search.png" /> Delete</li>
      </ul>
</div>
<select id="province"></select>
<select id="city"></select>
</body>
</html>