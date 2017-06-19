<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/member/member.js"/>" ></script>
<c:if test="${info == 'admin'}">
<section>
	<div class="container">
		<form role="form"></form>
		<table class="table table-striped">
		<tr>
			<td>
				<label for="user_id">아이디 : </label>
				${member.user_id}
			</td>
		</tr>
		<tr>
			<td><label for="user_birth">생일 : </label>
				${member.user_birth}
			</td>
		</tr>
		<tr>
			<td>
				<label for="user_gender">성별 : </label>
				<c:set var="gender" value="${member.user_gender}" />
	
				<c:if test="${gender eq 0}">
					남자
				</c:if>
				<c:if test="${gender eq 1}">
					여자
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<label for="user_phone">전화번호 : </label>
				${member.user_phone}
			</td>
		</tr>
		<tr>
			<td>
				<label for="user_email">이메일 </label>
				${member.user_email}
			</td>
		</tr>
		<tr>
			<td>
				<label for="user_date">가입날짜 : </label>
				<fmt:formatDate pattern="yyyy-MM-dd" value="${member.user_date}" />
			</td>
		</tr>
		</table>
		<div>
			<button type="submit" class="btn btn-warning">Modify</button>
			<button type="submit" class="btn btn-danger">Remove</button>
			<button type="submit" class="btn btn-primary">List</button>
		</div>
		<div class="pagelist">
			<input type="hidden" id="page" name="page" value="${pagination.page}">
			<input type="hidden" id="recordPage" name="recordPage" value="${pagination.recordPage}">
			<input type="hidden" id="check" name="check" value="${member.user_id}">
		</div>
	</div>
</section>
</c:if>
<c:if test="${info == null || info == 'user'}">
잘못된접근입니다.
</c:if>