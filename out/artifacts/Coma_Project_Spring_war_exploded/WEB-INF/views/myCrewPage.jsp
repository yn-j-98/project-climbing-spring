<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 내 크루 페이지</title>

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

	<!-- container start -->
	<div class="container">
		<div class="page-inner">
			<div class="row pt-5 pb-2">
				<div class="col-12">
					<h1 class="text-center">크루</h1>
				</div>
			</div>
			<div class="row pt-2 pb-5">
				<div class="col-12">
					<div class="d-flex justify-content-center align-items-center">
						<a href="CrewPage.do"
							class="text-dark text-decoration-underline link-primary">
							<h3 class="m-0">
								<b>내 크루</b>
							</h3>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="CrewCommunityPage.do"
							class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">커뮤니티</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="CrewBattlePage.do"
							class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">크루전 개최</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="CrewListPage.do"
							class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">크루 가입</p>
						</a>
					</div>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-10">
					<!-- 내가 가입한 크루의 크루명 DB에서 가져오기 -->
					<h4>크루명 ${CREW.model_crew_name}</h4>
					<div class="card card-stats card-round pt-3 px-5 pb-5">
						<!-- 크루 사진 이미지 -->
						<img src="${CREW.model_crew_profile }" class="crew-image "
							alt="내가 가입한 크루 사진" />
					</div>
				</div>
			</div>
			<div class="row justify-content-center ">
				<div class="col-md-10">
					<div class="card card-stats card-round pt-3 px-5 pb-5">
						<div class="tab-content mt-3 mb-3" id="line-tabContent">
							<div class="tab-pane fade show active" id="line-post"
								role="tabpanel" aria-labelledby="line-post-tab">
								<div class="row">
									<div class="col-md-3">
										<h5>크루설명</h5>
									</div>
									<div class="col-md-7">
										<!-- DB에 저장된 (내)크루에 대한 설명 가져오기 -->
										<h5>${CREW.model_crew_description}</h5>
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-3">
										<h5>크루장명</h5>
									</div>
									<div class="col-md-7">
										<!-- DB에 저장된 (내)크루의 크루장 명 -->
										<h5>${CREW.model_crew_leader}</h5>
									</div>
								</div>
								<br>
								<div class="row">
									<div class="col-md-3">
										<h5>크루원명</h5>
									</div>
									<div class="col-md-7">
										<!-- DB에 저장된 (내)크루의 크루원 목록 -->
										<c:forEach var="crew" items="${model_member_crew_datas}">
											<h5>${crew.model_member_name}</h5>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-10">
					<div class="card card-stats card-round pt-3 px-5 pb-5">
						<div class="tab-content mt-3 mb-3" id="line-tabContent">
							<div class="tab-pane fade show active" id="line-post"
								role="tabpanel" aria-labelledby="line-post-tab">
								<h4 align="center">크루전 승리목록</h4>
								<br> <br>
								<div class="row">
									<!-- 크루전 승리목록 가져오기 -->
									<c:forEach var="model_battle_record_data" items="${model_battle_record_datas}">

										<c:choose>
											<c:when test="${model_battle_record_data.model_battle_record_crew_num > 0}">
												<!-- 크루전 했던 암벽장 이름 -->
												<div class="col-md-3 text-center">
													<h5>${model_battle_record_data.model_battle_record_gym_name}</h5>
												</div>
												<!-- 암벽장 장소 -->
												<div class="col-md-3 text-center">
													<h5>장소 : ${model_battle_record_data.model_battle_record_gym_location}</h5>
												</div>
												<!-- 크루전 MVP -->
												<div class="col-md-3 text-center">
													<h5>크루전 MVP : ${model_battle_record_data.model_battle_record_mvp_id}</h5>
												</div>
												<!-- 시행일자 -->
												<div class="col-md-3 text-center">
													<h5>시행일자 : ${model_battle_record_data.model_battle_record_game_date}</h5>
												</div>
											</c:when>
											<c:otherwise>
												<h3>크루전 승리목록이 없습니다..</h3>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- container end -->
	</div>
	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
</body>

</html>