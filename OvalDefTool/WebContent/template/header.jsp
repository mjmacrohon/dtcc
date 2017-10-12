<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 
	The heander.jsp purpose contains the html tags related to header.
	And the inclusion of css files.
 -->
<!DOCTYPE html>
<html >
	<head>

		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="IE=Edge">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="-1">
 

		<title><spring:eval expression="@appProp.getProperty('app.name')" /> | <spring:eval expression="@appProp.getProperty('app.company')" /> </title>
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" href="resources/css/images/favicon.ico" type="image/x-icon">
        <link rel="icon" href="resources/css/images/favicon.ico" type="image/x-icon">
        
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="../resources/css/bootstrap.css">
		<!--font Awesome CSS-->
		<link rel="stylesheet" href="../resources/css/font-awesome.css">
		<!-- bootstrap select -->
		<link rel="stylesheet" href="../resources/css/bootstrap-select.css">
		
		<!-- jquery treetable  -->
		<link rel="stylesheet" href="../resources/css/jquery.treetable.css">
		<link rel="stylesheet" href="../resources/css/jquery.treetable.theme.default.css">
		
		<!-- JQUERY UI -->
		<link rel="stylesheet" href="../resources/css/jquery-ui.css">
		<!-- DATATable	-->		
		<link rel="stylesheet" href="../resources/css/jquery.dataTables.min.css">
			
		 
		<!-- DATATable bootstrap style				
		<link rel="stylesheet" href="../resources/css/dataTables.bootstrap.min.css">
		 -->
		 
		<!-- bootstrap-datetimepicker -->
		<link rel="stylesheet" href="../resources/css/bootstrap-datetimepicker.css">
		
		<!-- CHOOSEN -->
		<link rel="stylesheet" href="../resources/css/chosen.min.css">
		<link rel="stylesheet" href="../resources/css/bootstrap-chosen.css">
		<!--CUSTOM GENERAL CSS-->
		<link rel="stylesheet" href="../resources/css/qtip.css">
		<!--CUSTOM GENERAL CSS-->
		<link rel="stylesheet" href="../resources/css/general.css">
		<link rel="stylesheet" href="../resources/css/table.css">