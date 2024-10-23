<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 크루전 개최 내용</title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />

</head>
<body>
	<!-- GNB 커스텀 태그 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>

	<!-- container start -->
	<div class="container">

		<div class="page-inner">
			<div class="col-12 d-flex justify-content-center pb-3">
				<h2>
					<b>${model_battle_record.model_battle_record_gym_name}</b>
				</h2>
			</div>
			<div class="col-12 d-flex justify-content-center">

				<p>크루전 개최 날짜: ${model_battle_record.model_battle_record_game_date}</p>
			</div>
			<div class="row border-top border-dark pb-3"></div>
			<div class="row">
				<div class="col-md-7 d-flex align-items-center">
					<p class="mb-0">위치 : ${model_battle_record.model_battle_record_gym_location}</p>
				</div>
				<div class="col-md-5 d-flex align-items-center justify-content-end">
					<p class="mb-0">참여 크루 수 : ${model_battle_total.model_battle_total}</p>
				</div>
			</div>
			<div class="row border-bottom border-dark pb-3"></div>
			<div class="row py-5">
				<div class="col-12 d-flex justify-content-center">
					<c:choose>
					    <c:when test="${not empty model_battle_record_datas}">
					        <c:forEach var="model_battle_crew" items="${model_battle_record_datas}">
					            <div class="battle-info">
					            	<p>크루전 결과:
					            		<c:if test="${model_battle_flag}">
					            		<!-- is_winner가 있다면 -->
										    <c:choose>
										        <c:when test="${model_battle_crew.model_battle_record_is_winner == 'T'}">
										            승리
										        </c:when>
										        <c:otherwise>
										            패배
										        </c:otherwise>
										    </c:choose>
									    </c:if>
									    <c:if test="${!model_battle_flag}">
									    	경기 시작전
									    </c:if>
									</p>
					                <p>크루전 MVP: ${model_battle_crew.model_battle_record_mvp_id}</p>
					                <p>크루명: ${model_battle_crew.model_battle_record_crew_name}</p>
					                <p>해당 크루의 크루장: ${model_battle_crew.model_battle_record_crew_leader}</p>
					            </div>
					        </c:forEach>
					    </c:when>
					    <c:otherwise>
					        <p>데이터가 없습니다...</p>
					    </c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="row border-bottom border-dark pb-3"></div>
		</div>
	</div>
	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>


</body>
</html>