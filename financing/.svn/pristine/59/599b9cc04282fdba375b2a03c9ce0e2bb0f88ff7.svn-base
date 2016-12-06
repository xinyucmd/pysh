<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<div id="top-bar">
  <div id="left-side">
    <a href="#">
    	<img src="../../images/jclogo.png" alt="JiangChuan Banking Logo" height="28px" width="90px" />
    </a>
    <s:text name="info.about" />
  </div>
  <div id="right-side">
    <s:text name="info.welcome" />
    , <strong> <security:authentication property="principal.username" /></strong>
	<a id="logout_link"  href="<c:url value="/j_spring_security_logout"/>">
		[<s:text name="link.logout" />]
    </a>
  </div>
</div>