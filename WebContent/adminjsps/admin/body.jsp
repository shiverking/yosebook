<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>body.jsp</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
        body{
            background: #F5F5F5;
        }
        img{
            width: 800px;
            margin-top: 80px;
        }
        p{
            text-align: center;
            font-size: 38px;
            font-family: "microsoft yahei";
            color: #1E90FF;
        }
    </style>
  </head>
  
  <body style="margin: 0px;">
  	<div style="text-align: center;">
    	<img style="vertical_align:middle;" src="<c:url value='/images/ybig_logo.png'/>" />
    </div>
    <p>欢迎使用集贤书城后台管理系统！</p>
  </body>
</html>
