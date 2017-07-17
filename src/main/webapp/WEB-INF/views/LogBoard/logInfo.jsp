<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="./resources/js/logs/loginfo.js"/></script>
<!-- 
	PAGE HEADER 
	
	CLASSES:
		.page-header-xs	= 20px margins
		.page-header-md	= 50px margins
		.page-header-lg	= 80px margins
		.page-header-xlg= 130px margins
		.dark			= dark page header
	
		.shadow-before-1 	= shadow 1 header top
		.shadow-after-1 	= shadow 1 header bottom
		.shadow-before-2 	= shadow 2 header top
		.shadow-after-2 	= shadow 2 header bottom
		.shadow-before-3 	= shadow 3 header top
		.shadow-after-3 	= shadow 3 header bottom
-->
<section class="page-header page-header-xs shadow-before-1">
	<div class="container">

		<h1>Log</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li class="active">Log</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<div class="logbar hidden-xs">
			<div class="bars currentive" data-onlog="0">모든로그</div>
			<div class="bars" data-onlog="1">일상로그</div>
			<div class="bars" data-onlog="3">발자국로그</div>
			<div class="bars" data-onlog="2">여행기로그</div>
		</div>
		
		<!-- first cell -->
		<div class="columnA t-cellgrid col-md-4">

		</div>
			
		<!-- second cell -->
		<div class="columnB t-cellgrid col-md-4">
			
		</div>
			
		<!-- third cell -->
		<div class="columnC t-cellgrid col-md-4">
				
		</div>
		
	</div>
	<div style="height:100px;"></div>
</section>