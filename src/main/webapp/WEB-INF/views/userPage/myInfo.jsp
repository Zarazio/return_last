<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/member/mydel.js"/>" ></script>
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

<c:if test="${info == 'user' || info == 'admin'}">
<section class="page-header dark page-header-xs shadow-before-1">
	<div class="container">

		<h1>마이페이지</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li class="active">마이페이지</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->


<section>
	<div class="container">

		<div class="row">

			<!-- LEFT -->
			<div class="col-md-3 col-sm-3">

				<!-- side navigation -->
				<div class="side-nav margin-bottom-200 margin-top-40">
					<div class="side-nav-head">
						<h4>MY PAGE</h4>
					</div>
					<ul class="list-group list-group-bordered list-group-noicon uppercase">
						<c:if test="${info == 'user'}">
							<li class="list-group-item">
								<a href="myModify"><span class="size-11 text-muted pull-right"></span>회원정보수정</a>
							</li>
							<li class="list-group-item">
								<a class="memDel"><span class="size-11 text-muted pull-right"></span>회원탈퇴</a>
							</li>
						</c:if>
						<c:if test="${info == 'admin'}">
							<li class="list-group-item mypageMove">
								<a href="myModify"><span class="size-11 text-muted pull-right"></span>관리자정보수정</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			<div id="user-travel-log">	
				<c:if test="${info == 'user'}">
					<div><h2>Log</h2></div>
					<div><h2>Travel-Plan</h2></div>
				</c:if>
			</div>
		</div>
	</div>
</section>
</c:if>
<c:if test="${info == null}">
잘못된접근입니다.
</c:if>