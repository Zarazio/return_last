<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
<section class="page-header dark page-header-xs shadow-before-1">
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
				<h1 class="blog-post-title">${vo.board_title}</h1>
				<ul class="blog-post-info list-inline">
					<li class="pull-right">
						<a href="#">
							<i class="fa fa-eye"></i> 
							<span class="font-lato">${vo.viewCount}</span>
						</a>
					</li>
					<li class="pull-right">
						<a href="#">
							<i class="fa fa-clock-o"></i> 
							<span class="font-lato"><fmt:formatDate pattern="yyyy-MM-dd" value="${vo.board_date}" /></span>
						</a>
					</li>
				</ul>

				<div><p class="content">${vo.board_content}</p></div>

				<div class="divider"><!-- divider --></div>




				<!-- SHARE POST -->
				<div class="clearfix margin-top-30">

					<span class="pull-left margin-top-6 bold hidden-xs">
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
					
					<a href="comuList" class="btn btn-3d btn-teal pull-right" style="margin:0px 0px 0px 5px;">목록</a>
		
					<c:if test="${vo.user_id eq mem}">
						<a href="#" class="btn btn-3d btn-amber pull-right" style="margin:0px 0px 0px 5px;">글삭제</a>
						<a href="comuSet?page=${vo.board_code}" class="btn btn-3d btn-aqua pull-right" style="margin:0px 0px 0px 5px;">글수정</a>
					</c:if>
					<a href="#" class="pull-right fontMargin"><img src="displayProfile?fileName=${vo.user_profile}" style="width:40px; height:40px; border:1px solid gray;">
							&nbsp;<span class="font-lato fontRead">${vo.user_id}</span>
					</a>
				

				</div>
				<!-- /SHARE POST -->

				<div class="divider"><!-- divider --></div>




			 
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