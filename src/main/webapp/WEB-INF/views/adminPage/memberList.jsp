<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/member/member.js"/>" ></script>
<c:if test="${info == 'admin'}">
<section>
	<div class="container">
		<form id="pageForm" class="form-inline">
			<input type="hidden" name="page" value="${pagination.page}">
			<input type="hidden" name="recordPage" value="${pagination.recordPage}">
		</form>
		<table class="table table-striped">
			<tr>
				<th>회원 아이디</th>
				<th>회원 가입날짜</th>
			</tr>
			<c:forEach items="${member}" var="member">
				<tr>
					<td>
						<a href="${member.user_id}" class="member_info">
							${member.user_id}
						</a>
					</td>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${member.user_date}" />
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