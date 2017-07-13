<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="./resources/js/comunity/comunityCommand.js"/></script>

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

		<h1>커뮤니티</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="comuList">커뮤니티</a></li>
			<li class="active">글정보</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->

<section>
	<div class="container">
		<div class="row">
			<!-- LEFT -->
			<div class="col-md-12 col-sm-12">
				<div class="numberPost" data-src="${vo.board_code}">

				<div class="pull-left">
					<div class="comuTitle">${vo.board_title}</div>
				</div>
				<div class="pull-right hei-line">
					<i class="fa fa-eye size-20"></i> 
					<span class="size-20">${vo.viewCount}</span> <!-- class:font-lato -->
					&nbsp;
					<i class="fa fa-clock-o size-20"></i> 
					<span class="size-20"><fmt:formatDate pattern="yyyy-MM-dd" value="${vo.board_date}" /></span>
				</div>

				<ul class="blog-post-info comuTitleline">
				</ul>

				<div><p class="content">${vo.board_content}</p></div>

				<hr/><!--class:divider -->




				<!-- SHARE POST -->
				<div class="clearfix margin-top-20">

					<span class="pull-left margin-top-10 bold hidden-xs">
						Share Post &nbsp;&nbsp;&nbsp; : &nbsp;&nbsp;
					</span>

					<a href="#" class="social-icon social-icon-sm social-icon-transparent social-facebook pull-left" data-toggle="tooltip" data-placement="top" title="Facebook">
						<i class="icon-facebook"></i>
						<i class="icon-facebook"></i>
					</a>

					<a href="#" class="social-icon social-icon-sm social-icon-transparent social-twitter pull-left" data-toggle="tooltip" data-placement="top" title="Twitter">
						<i class="icon-twitter"></i>
						<i class="icon-twitter"></i>
					</a>

					<a href="#" class="social-icon social-icon-sm social-icon-transparent social-gplus pull-left" data-toggle="tooltip" data-placement="top" title="Google plus">
						<i class="icon-gplus"></i>
						<i class="icon-gplus"></i>
					</a>

					<a href="#" class="social-icon social-icon-sm social-icon-transparent social-pinterest pull-left" data-toggle="tooltip" data-placement="top" title="Pinterest">
						<i class="icon-pinterest"></i>
						<i class="icon-pinterest"></i>
					</a>

					<a href="#" class="social-icon social-icon-sm social-icon-transparent social-call pull-left" data-toggle="tooltip" data-placement="top" title="Email">
						<i class="icon-email3"></i>
						<i class="icon-email3"></i>
					</a>
					
					<a href="comuList?page=${page}" class="btn btn-3d btn-teal pull-right" style="margin:0px 0px 0px 5px;">목록</a>
		
					<c:if test="${vo.user_id eq mem}">
						<a class="btn btn-3d btn-amber pull-right comuDel" style="margin:0px 0px 0px 5px;">글삭제</a>
						<a href="comuSet?page=${page}&post=${vo.board_code}" class="btn btn-3d btn-aqua pull-right" style="margin:0px 0px 0px 5px;">글수정</a>
					</c:if>
					<a href="#" class="pull-right fontMargin"><img src="displayProfile?fileName=${vo.user_profile}" style="width:40px; height:40px; border:1px solid gray;">
							&nbsp;<span class="font-lato fontRead">${vo.user_id}</span>
					</a>
				

				</div>
				<!-- /SHARE POST -->

				<hr/><!--class:divider -->




			 
				<!-- COMMENTS -->
	<!--
				<div id="comments" class="comments">

					<h4 class="page-header margin-bottom-60 size-20">
						<span>3</span> COMMENTS
					</h4>

					<!-- comment item -->
	<!--				<div class="comment-item">

						<!-- user-avatar -->
	<!-- 				<span class="user-avatar">
							<img class="pull-left media-object" src="assets/images/avatar.png" width="64" height="64" alt="">
						</span>

						<div class="media-body">
							<a href="#commentForm" class="scrollTo comment-reply">reply</a>
							<h4 class="media-heading bold">Diana Doe</h4>
							<small class="block">June 29, 2014 - 11:23</small>
							Proin eget tortor risus. Cras ultricies ligula sed magna dictum porta. Pellentesque in ipsum id orci porta dapibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
						</div>
					</div>
	-->
					<!-- comment item -->
	<!--				<div class="comment-item">

						<!-- user-avatar -->
	<!--					<span class="user-avatar">
							<img class="media-object" src="assets/images/avatar.png" width="64" height="64" alt="">
						</span>

						<div class="media-body">
							<a href="#commentForm" class="scrollTo comment-reply">reply</a>
							<h4 class="media-heading bold">Melissa Doe</h4>
							<small class="block">June 29, 2014 - 11:23</small>
							Proin eget tortor risus. Cras ultricies ligula sed magna dictum porta. Pellentesque in ipsum id orci porta dapibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
	-->
							<!-- comment reply -->
	<!-- 						<div class="media">

								<!-- user-avatar -->
	<!--							<span class="user-avatar">
									<img class="media-object" src="assets/images/avatar.png" width="64" height="64" alt="">
								</span>

								<div class="media-body">
									<h4 class="media-heading bold">Peter Doe</h4>
									<small class="block">June 29, 2014 - 11:23</small>
									Proin eget tortor risus. Cras ultricies ligula sed magna dictum porta. Pellentesque in ipsum id orci porta dapibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
								</div>
							</div>

						</div>
					</div>

	-->
	<!-- 			<h4 class="page-header size-20">
						LEAVE A <span>COMMENT</span>
					</h4>
	-->

					<!-- Form -->
					<form action="#" method="post">
						<div class="row">
							<div class="form-group">
								<div class="col-md-12">
									<label class="size-20">COMMENT</label>
									<textarea required="required" maxlength="5000" rows="5" class="form-control" name="comment" id="comment"></textarea>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">

								<button class="btn btn-3d btn-lg btn-reveal btn-black  pull-right">
									<i class="fa fa-check"></i>
									<span>SUBMIT MESSAGE</span>
								</button>

							</div>
						</div>

					</form>
					<!-- /Form -->

				</div>
				<!-- /COMMENTS -->


			</div>
		</div>
	</div>
</section>