<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/layers/layersup.js"/>" ></script>


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

		<h1>LayersUp</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="">LayersUp</a></li>
			<li class="active">Book</li>
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
						<h4>CATEGORY</h4>
					</div>
					<ul class="list-group list-group-bordered list-group-noicon uppercase">
						<c:if test="${info == 'user'}">
							<li class="list-group-item">
								<!-- <a href="javascript:bookMakeList();"><span class="size-11 text-muted pull-right"></span>Book</a>-->
							</li>
							<li class="list-group-item">
								<a href="javascript:gifMakeList();"><span class="size-11 text-muted pull-right"></span>Gif</a>
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
			<div id="Category_select">	
				
			</div>
		</div>
	</div>
</section>
</c:if>
<c:if test="${info == null}">
잘못된접근입니다.
</c:if>