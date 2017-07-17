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
<section class="page-header page-header-xs shadow-before-1">
	<div class="container">

		<h1>장소정보</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="placeInfo">장소</a></li>
			<li class="active">장소정보</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<div class="row">

			<!-- LEFT -->
			<div class="col-md-9 col-sm-9">

				<!-- POST ITEM -->
				<div class="nomargin sky-form boxed">
					<header class="size-18" style="background:rgba(199, 199, 199, 0.1) !important;">
						<b>${place.place_name}</b>
						<div class="pull-right">
							<i class="fa fa-eye"></i>&nbsp; 
							<span class="font-lato">${place.view}</span>&nbsp;&nbsp; 
							<i class="fa fa-clock-o"></i>&nbsp; 
							<span class="font-lato">
								<fmt:formatDate pattern="yyyy-MM-dd" value="${place.add_date}" />
							</span>
						</div>
					</header>
					
					<fieldset class="nomargin">
					
						
						<div class="tab-content">
							
							<!-- OWL SLIDER -->
							<div id="tab_a" class="tab-pane active">
								<c:choose>
			      					<c:when test="${fn:length(list) == 1}">
						            	<div class="owl-carousel buttons-autohide controlls-over" style="border:1px solid rgb(221, 221, 221); height:400px; margin-bottom:0px;" data-plugin-options='{"items": 1, "autoHeight": false, "navigation": true, "pagination": true, "transitionStyle":"fadeUp", "progressBar":"false"}'>
											<c:forEach items="${list}" var="pimg">
												<div>
													<img class="img-responsive" src="displayFile?fileName=${pimg.place_img}" alt="" style="height:400px;">
												</div>
											</c:forEach>
										</div>
						           	</c:when>
						           	<c:otherwise>
						            	<div class="owl-carousel buttons-autohide controlls-over" style="border:1px solid rgb(221, 221, 221); height:400px; margin-bottom:0px;" data-plugin-options='{"items": 1, "autoPlay": 3000, "autoHeight": false, "navigation": true, "pagination": true, "transitionStyle":"fadeUp", "progressBar":"false"}'>
											<c:forEach items="${list}" var="pimg">
												<div>
													<img class="img-responsive" src="displayFile?fileName=${pimg.place_img}" alt="" style="height:400px;">
												</div>
											</c:forEach>
										</div>
						           	</c:otherwise> 
						        </c:choose>
							<!-- /OWL SLIDER -->
							</div>
							
							<!-- MAP -->
							<div id="tab_b" class="tab-pane">
								<div id="google_map" style="width:100%; height:400px; border:#ddd 2px solid;"></div>
								<div id="latLng" style="display:none;" data-lat="${place.place_lat}" data-lng="${place.place_lng}"></div>
							</div>
							<!-- /MAP -->
							
							<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ"></script>
							<script>
								var map;
								// 지도 중앙 위치
								
								$(document).ready(function(){
									
									$("#targetMapA").on("click",function(){
										$("#targetMapB").parent().removeClass("active");
										$("#tab_b").removeClass("active");
										$("#tab_a").addClass("active");
										$(this).parent().addClass("active");
									});
									
									$("#targetMapB").on("click",function(){
										$("#targetMapA").parent().removeClass("active");
										$("#tab_a").removeClass("active");
										$("#tab_b").addClass("active");
										$(this).parent().addClass("active");
										initialize();
									});
									
									function initialize() {
										  
										  var lat = $("#latLng").attr("data-lat")
										  var lng = $("#latLng").attr("data-lng")
										
										  var latLng = new google.maps.LatLng(lat, lng); 
												
										  var mapOptions = { //구글 맵 옵션 설정
										      zoom : 16, //기본 확대율
										      center : latLng, // 지도 중앙 위치
										      scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
										      mapTypeControl : true //맵 타입 컨트롤 사용 여부
										  };
										
										  map = new google.maps.Map(document.getElementById('google_map'), mapOptions); //구글 맵을 사용할 타겟
										  
										  var image = './resources/img/placeholder.png'; //마커 이미지 설정
										
										  var marker = new google.maps.Marker({ //마커 설정
										      map : map,
										      position : map.getCenter(), //마커 위치
										      icon : image //마커 이미지
										  });
										
										  google.maps.event.trigger(map, "resize");
										
									}
								});
							</script>
			
							
						</div>
						
						<!-- 이미지 , 지도  -->
						<div class="row margin-top-10 margin-bottom-20">
							<div class="col-md-6 col-sm-6">
								<ul id="myTab" class="nav nav-tabs nav-bottom-border nav-justified">
									<li class="active">
										<a id="targetMapA">이미지</a>
									</li>
									<li>
										<a id="targetMapB">지도</a>
									</li>
									<li>
										<a class="btn btn-reveal btn-default" style="width:120px; height:43px; border-bottom: 3px solid #89b4c7 !important;">
											<i class="fa fa-plus"></i>
											<span>북마크</span>
										</a>
									</li>	
								</ul>
							</div>
						</div>
						
						<!-- <ul class="blog-post-info left-line"></ul> -->
						
						<div>
							<div class="size-15" style="font-weight:600;">
								<p class="contentLine-two">${place.place_content}</p>
							</div>
						</div>
						
					 	<ul class="blog-post-info left-line content-line"></ul>
						
						<!-- Form -->
						<form action="#" method="post">
						
						<div class="form-group">
							<div class="">
								<label class="size-20">COMMENT</label>
								<textarea required="required" maxlength="5000" rows="5" class="form-control" name="comment" id="comment"></textarea>
							</div>
						</div>
					
						<div class="">
							<button class="btn btn-3d btn-lg btn-reveal btn-black  pull-right">
								<i class="fa fa-check"></i>
								<span>SUBMIT MESSAGE</span>
							</button>
						</div>
					

						</form>
						<!-- /Form -->
						
					</fieldset>
				
					
				</div>
				
				<!-- /POST ITEM -->

			</div>


			<!-- RIGHT -->
			<div class="col-md-3 col-sm-3">

				<!-- side navigation -->
				<div class="side-nav margin-bottom-60">
					<div class="nomargin sky-form boxed">
						<header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important;">
							<img src="displayProfile?fileName=${map.user_profile}" style="width:45px; height:45px; border:1px solid rgb(221, 221, 221);">
							<b class="margin-left-10">${map.user_id}</b>
						</header>
						<fieldset class="nomargin">
							
							<div style="font-weight:600;">
								<div><b class="sideFont">주소</b></div>
								<div>${place.place_address}<br><br></div>
								<div><b class="sideFont">위도</b></div>
								<div>${place.place_lat}<br><br></div>
								<div><b class="sideFont">경도</b></div>
								<div>${place.place_lng}<br><br></div>
								<div><b class="sideFont">카테고리</b></div>
								<div>${place.place_type}<br><br></div>
							</div>
							
							<!-- /side navigation -->
						</fieldset>
					</div>
				</div>


				<!-- JUSTIFIED TAB -->
				<div class="tabs nomargin-top hidden-xs margin-bottom-60">

					<!-- tabs -->
					<ul class="nav nav-tabs nav-bottom-border nav-justified">
						<li class="active">
							<a href="#tab_1" data-toggle="tab">
								Popular
							</a>
						</li>
						<li>
							<a href="#tab_2" data-toggle="tab">
								Recent
							</a>
						</li>
					</ul>

					<!-- tabs content -->
					<div class="tab-content margin-bottom-60 margin-top-30">

						<!-- POPULAR -->
						<div id="tab_1" class="tab-pane active">

							<div class="row tab-post"><!-- post -->
								<div class="col-md-3 col-sm-3 col-xs-3">
									<a href="#">
										<img src="http://placehold.it/300x300" width="50" alt="" />
									</a>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-9">
									<a href="#" class="tab-post-link">Maecenas metus nulla</a>
									<small>June 29 2014</small>
								</div>
							</div><!-- /post -->

							<div class="row tab-post"><!-- post -->
								<div class="col-md-3 col-sm-3 col-xs-3">
									<a href="#">
										<img src="http://placehold.it/300x300" width="50" alt="" />
									</a>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-9">
									<a href="#" class="tab-post-link">Curabitur pellentesque neque eget diam</a>
									<small>June 29 2014</small>
								</div>
							</div><!-- /post -->

							<div class="row tab-post"><!-- post -->
								<div class="col-md-3 col-sm-3 col-xs-3">
									<a href="#">
										<img src="http://placehold.it/300x300" width="50" alt="" />
									</a>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-9">
									<a href="#" class="tab-post-link">Nam et lacus neque. Ut enim massa, sodales</a>
									<small>June 29 2014</small>
								</div>
							</div><!-- /post -->

						</div>
						<!-- /POPULAR -->


						<!-- RECENT -->
						<div id="tab_2" class="tab-pane">

							<div class="row tab-post"><!-- post -->
								<div class="col-md-3 col-sm-3 col-xs-3">
									<a href="#">
										<img src="http://placehold.it/300x300" width="50" alt="" />
									</a>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-9">
									<a href="#" class="tab-post-link">Curabitur pellentesque neque eget diam</a>
									<small>June 29 2014</small>
								</div>
							</div><!-- /post -->

							<div class="row tab-post"><!-- post -->
								<div class="col-md-3 col-sm-3 col-xs-3">
									<a href="#">
										<img src="http://placehold.it/300x300" width="50" alt="" />
									</a>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-9">
									<a href="blog-sidebar-left.html" class="tab-post-link">Maecenas metus nulla</a>
									<small>June 29 2014</small>
								</div>
							</div><!-- /post -->

							<div class="row tab-post"><!-- post -->
								<div class="col-md-3 col-sm-3 col-xs-3">
									<a href="#">
										<img src="http://placehold.it/300x300" width="50" alt="" />
									</a>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-9">
									<a href="#" class="tab-post-link">Quisque ut nulla at nunc</a>
									<small>June 29 2014</small>
								</div>
							</div><!-- /post -->
						</div>
						<!-- /RECENT -->

					</div>

				</div>
				<!-- JUSTIFIED TAB -->


				<!-- TAGS -->
				<h3 class="hidden-xs size-16 margin-bottom-20">TAGS</h3>
				<div class="hidden-xs margin-bottom-60">

					<a class="tag" href="#">
						<span class="txt">RESPONSIVE</span>
						<span class="num">12</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">CSS</span>
						<span class="num">3</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">HTML</span>
						<span class="num">1</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">JAVASCRIPT</span>
						<span class="num">28</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">DESIGN</span>
						<span class="num">6</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">DEVELOPMENT</span>
						<span class="num">3</span>
					</a>
				</div>

			</div>

		</div>
	</div>
</section>