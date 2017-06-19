<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="<c:url value="./resources/js/login/loginConfirm.js"/>" ></script>
<section>
	<div class="container">
		<form id="test_submit" onsubmit="return false;">
			<ul>
				<li>
					<div class="form-group">
						<label>아이디</label>
						<input id="user_id" name="user_id" type="text" size="20"
							maxlength="40" placeholder="아이디 입력" class="form-control">
					</div>
				<li>
					<div class="form-group">
						<label>패스워드</label>
						<input id="user_pass" name="user_pass" type="password" size="20"
							maxlength="20" placeholder="패스워드 입력" class="form-control">
					</div>			
				<li>
					<div class="form-group">
						<input type="submit" id="loginTest" value="로그인" class="btn btn-info">
						<div id="id_confirm" style="display:none"></div>
						<div id="pass_confirm" style="display:none"></div>
					</div>
			</ul>
		</form>
	</div>
</section>