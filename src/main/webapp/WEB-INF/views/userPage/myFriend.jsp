<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="./resources/js/friend/friend.js"/></script>
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
	
			<h1>친구관리</h1>
	
			<!-- breadcrumbs -->
			<ol class="breadcrumb">
				<li><a href="main">메인</a></li>
				<li><a href="myinfo">마이페이지</a></li>
				<li class="active">친구관리</li>
			</ol><!-- /breadcrumbs -->
	
		</div>
	</section>
	<!-- /PAGE HEADER -->
	
	<section>
		<div class="container">
			<div class="col-md-3 col-sm-3">
				<div class="row padding-10">
					<h4>내상태</h4>
					<div class="myinfo text-center">
						<img src="displayProfile?fileName=${vo.user_profile}" style="width:250px; height:250px">
						<div class="myViewfont margin-top-10"><b>아이디 : ${vo.user_id}</b></div>
					</div>
				</div>
			</div>
			
			<div class="col-md-4 col-sm-4">
				<div class="row padding-10">
					<h4>친구상태 리스트</h4>
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th width="6%" class="text-center"></th>
									<th width="35%" class="text-center">회원아이디</th>
									<th class="text-center">상태</th>
									<th class="text-center"></th>
								</tr>
							</thead>
							<tbody class="friendList" data-my="${mem}">
							<!--
								<tr>
									<td><img src="" style="width:45px; height:45px;"></td>
									<td class="verticals">dariane</td>
									<td class="verticals">친구대기</td>
									<td class="verticals"><button class="btn btn-blue btn-3d btn-xs">요청취소</button></td>
								</tr>
								
								<tr>
									<td><img src="" style="width:45px; height:45px;"></td>
									<td class="verticals">kakao</td>
									<td class="verticals">친구</td>
									<td class="verticals"><button class="btn btn-red btn-3d btn-xs">친구삭제</button></td>
								</tr>
							
								<tr>
									<td><img src="" style="width:45px; height:45px;"></td>
									<td class="verticals">kakao</td>
									<td class="verticals">친구요청</td>
									<td class="verticals"><button class="btn btn-green btn-3d btn-xs">친구요청</button></td>
								</tr>
							-->
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="col-md-4 col-sm-4">
				<div class="row padding-10">
					<h4>친구검색</h4>
					<div class="search margin-bottom-20">
						<div class="input-group">
							<input type="text" id="keyons" class="form-control" placeholder="친구를 검색해 보세요." autocomplete="off">
							<span class="input-group-btn">
								<button class="btn btn-info btn-3d searchon">친구검색</button>
							</span>
						</div>
					</div>
					<div class="lists">
						<div class="table-responsive">
							<table class="table table-bordered table-striped">
								<tbody class="searchlist">
									<!-- 
									<tr>
										<td width="6%" class="text-center" ><img src="" style="width:45px; height:45px;"></td>
										<td width="40%" class="verticals">dariane</td>
										<td class="verticals"><button class="btn btn-blue btn-3d btn-xs">친구요청</button></td>
									</tr>
									 -->
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			
			
		</div>
	</section>
</c:if>
<c:if test="${info == null || info == 'admin'}">
잘못된접근입니다.
</c:if>