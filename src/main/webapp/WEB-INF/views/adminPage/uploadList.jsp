<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="<c:url value="./resources/js/upload/uploadList.js"/>" ></script>
<c:if test="${info == 'admin'}">
<section>
	<div class="container">
		<form id="pageForm" class="form-inline">
			<input type="hidden" name="page" value="${pagination.page}">
			<input type="hidden" name="recordPage" value="${pagination.recordPage}">
		</form>
		<table class="table table-striped">
			<tr>
				<th>글 번호</th>
				<th>주소</th>
				<th>장소명</th>
				<th>위도</th>
				<th>경도</th>
				<th>장소타입</th>
				<th>장소 활성화</th>
				<th>등록일자</th>
			</tr>
			<c:forEach items="${list}" var="place">
				<tr>
					<td>${place.place_code}</td>
					<td>${place.place_address}</td>
					<td>
						<a href="${place.place_code}" class="place_info">
							${place.place_name}
						</a>
					</td>
					<td>${place.place_lat}</td>
					<td>${place.place_lng}</td>
					<td>${place.place_type}</td>
					<td>${place.place_on}</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${place.add_date}" />
					</td>
				</tr>
			</c:forEach>	
		</table>
		<div class="text-center">
			<ul class="pagination">
				<c:if test="${pagination.prev}">
					<li>
						<a href="${pagination.startPage-1}">&laquo;</a>
					</li>
				</c:if>
				<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
					<li <c:out value="${idx==pagination.page?'class=active':''}" /> ><!-- li class="active" -->	
						<a href="${idx}">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${pagination.next}">
					<li>
						<a href="${pagination.endPage+1}">&raquo;</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</section>
</c:if>
<c:if test="${info == null || info == 'user'}">
잘못된접근입니다.
</c:if>