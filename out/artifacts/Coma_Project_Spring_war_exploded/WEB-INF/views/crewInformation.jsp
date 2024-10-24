<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 크루 정보</title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />
<style>
.crew-image {
	width: 100%;
	height: auto;
	max-height: 400px; /* 원하는 최대 높이 설정 */
	object-fit: cover; /* 이미지 비율 유지하며 잘림 방지 */
}
</style>

</head>
<body>
	<!-- GNB 커스텀 태그 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>
	<div class="row pt-5">
		<div
			class="col-md-12 d-flex align-items-center justify-content-center">
			<img src="${model_crew_profile}" class="crew-image" alt="크루 이미지">
		</div>
	</div>
	<!-- container start -->
	<div class="container">

		<div class="page-inner">
			<c:if test="${empty CREW}">
				<div class="alert alert-danger" role="alert">크루 번호가 누락되었습니다.</div>
			</c:if>
			<c:if test="${not empty CREW}">
				<div class="col-12 d-flex justify-content-center pb-3">
					<h2>
						<!-- 크루명 -->
						<b>${CREW.model_crew_name}</b>
					</h2>
				</div>
				<div class="col-12 d-flex justify-content-end pt-3 px-3 pb-3">

					<button type="button" class="btn btn-info" onclick="window.location.href='CrewJoin.do?view_crew_num=${CREW.model_crew_num}'">가입하기</button>
				</div>
				<div class="row border-top border-dark pb-3"></div>
				<div class="row">
					<div class="col-md-7 d-flex align-items-center">
						<p class="mb-0">크루장 : ${CREW.model_crew_leader}</p>
					</div>
					<div class="col-md-5 d-flex align-items-center justify-content-end">
						<p class="mb-0">크루 인원 : ${CREW.model_crew_current_member_size}</p>
					</div>
				</div>
				<div class="row border-bottom border-dark pb-3"></div>
				<div class="row py-5">
					<div class="col-12 d-flex justify-content-center">
					<!-- 크루에 대한 설명 -->
						<p class="text-start">${CREW.model_crew_description}</p>
					</div>
				</div>
				<div class="row border-bottom border-dark pb-3"></div>
				<div class="row pt-3 px-3 pb-3">
					<div class="col-md-7 d-flex align-items-center pt-3 px-3 pb-3">
						<p class="mb-0">
							<b>크루전 승리 목록</b>
						</p>
					</div>
				</div>
				<c:forEach var="model_battle_record_data" items="${model_battle_record_datas}">
				<div class="row pt-1 px-1 pb-1">
				<!-- 암벽장 이름 -->
					<div
						class="col-md-3 d-flex align-items-center justify-content-center">
						${model_battle_record_data.model_battle_record_gym_name}</div>
					<!-- 암벽장 위치 -->
					<div
						class="col-md-3 d-flex align-items-center justify-content-center">
						${model_battle_record_data.model_battle_record_gym_location}</div>
					<div
						class="col-md-3 d-flex align-items-center justify-content-center">
						크루전 MVP : ${model_battle_record_data.model_battle_record_mvp_id}</div>
					<div
						class="col-md-3 d-flex align-items-center justify-content-center">
						진행 날짜 : ${model_battle_record_data.model_battle_record_game_date}</div>
				</div>
				</c:forEach>
			</c:if>
		</div>
		<!-- page inner end -->
	</div>
	<!-- container end -->

	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>


</body>
</html>