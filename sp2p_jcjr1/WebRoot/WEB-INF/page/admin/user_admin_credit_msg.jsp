<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${sitemap.siteName}</title>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<script src="../script/jquery-1.7.1.min.js" type="text/javascript"></script>
<script>
</script>
</head>
<body>
<div class="">
<div class="nymain">
  <div class="wdzh">
    <div class="r_main">
      <div class="box">
       <h2 class="otherh2x">${sitemap.siteName}认证</h2>
      <div class="boxmain2">
       <div class="biaoge" style="margin-top:0px;">

          </div>
          <div class="biaoge" >
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
    <th colspan="3">${map.companyName} &nbsp;&nbsp;&nbsp;&nbsp;认证信息</th>
    </tr>
  
  <tr>
    <td align="center">&nbsp;</td>
    <td align="center">项目</td>
    <td align="center">状态</td>
    </tr>
  <tr>
    <td align="center">基本信息</td>
    <td align="center">企业基本信息</td>
    <td align="center">
     	<s:if test="#request.map.companyName!=null">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>
    </tr>
  <tr>
    <td rowspan="5" align="center">必要企业认证</td>
    <td align="center">法人身份认证</td>
    <td align="center">
         <s:if test="#request.map.auditStatus1==3">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>
    </tr>
  <tr>
    <td align="center">营业执照正副本认证</td>
    <td align="center">
         <s:if test="#request.map.auditStatus2==3">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>

    </tr>
  <tr>
    <td align="center">组织机构代码认证</td>
    <td align="center">
         <s:if test="#request.map.auditStatus3==3">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>
    </tr>
  <tr>
    <td align="center">税务登记证认证</td>
    <td align="center">
         <s:if test="#request.map.auditStatus4==3">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>

    </tr>
  <tr>
    <td align="center">企业银行账号认证</td>
    <td align="center">
         <s:if test="#request.map.auditStatus5==3">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>

    </tr>
  <tr>
    <td rowspan="1" align="center">可选认证</td>
    <td align="center">其他资料认证</td>
    <td align="center">
         <s:if test="#request.map.auditStatus6==3">
         	通过
      	</s:if>
      <s:else>待上传</s:else>
    </td>
  </tr>


    <!-- 
  <tr>
    <td align="center">视频</td>
    <td align="center">
     
      <s:if test="#request.map.tmshipingauditStatus==3">
         通过
      </s:if>
      <s:elseif test="#request.map.tmshipingauditStatus==2">不通过</s:elseif>
      <s:elseif test="#request.map.tmshipingauditStatus==1">待审核</s:elseif>
      <s:else>待上传</s:else>
     

    </td>
    <td align="center">
    
 ${map.tmshipingcriditing }
    
    </td>
    </tr>
     -->
  
 <!--  
  <tr>
    <td rowspan="8" align="center">基本认证</td>
    <th align="center">项目</th>
    <th align="center">次数</th>
    <th align="center">分数</th>
  </tr>
  <tr>
    <td align="center">提前还款</td>
    <td align="center">2</td>
    <td align="center">20分</td>
  </tr>
  <tr>
    <td align="center">按时还款</td>
    <td align="center">2</td>
    <td align="center">20分</td>
    </tr>
  <tr>
    <td align="center">迟还款（逾期一天以上至10天以内的还款）</td>
    <td align="center">2</td>
    <td align="center">20分</td>
    </tr>
  <tr>
    <td align="center">逾期还款（11-30天）</td>
    <td align="center">2</td>
    <td align="center">20分</td>
    </tr>
  <tr>
    <td align="center">逾期还款（逾期31天以上至90天以内）</td>
    <td align="center">2</td>
    <td align="center">20分</td>
    </tr>
  <tr>
    <td align="center">逾期还款（逾期90天以上的还款）</td>
    <td align="center">2</td>
    <td align="center">20分</td>
    </tr>
    -->
          </table>

          </div>
       </div>
</div>
    </div>
  </div>
</div>
<div class="footer02">
  <p><img src="../images/maitl_bottom1.jpg" />	版权所有 © 2013 ${sitemap.companyName} ${sitemap.regionName }  备案号：<span>${sitemap.certificate }</span><br/>
  客服热线（9：00-18：00）: ${sitemap.servicePhone }
    <div style=" clear:both;"></div>
</div>
</body>
</html>
