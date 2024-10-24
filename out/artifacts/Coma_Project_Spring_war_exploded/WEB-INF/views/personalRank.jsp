<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마에 오신 것을 환영합니다</title>
<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />

</head>
<body>
	<!-- GNB 커스텀 태그 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>

	<!-- container start -->
	<div class="container">
		<div class="page-inner">
			<div class="row pt-5 pb-2">
				<div class="col-12">
					<h1 class="text-center">랭킹</h1>
				</div>
			</div>
			<div class="row pt-2 pb-5">
				<div class="col-12">
					<div class="d-flex justify-content-center align-items-center">
						<a href="CrewRankingPage.do" class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">크루 랭킹</h3>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="RankingPage.do" class="text-dark text-decoration-underline link-primary">
							<h3 class="m-0"><b>개인 랭킹</b></h3>
						</a>
					</div>
				</div>
			</div>
			<c:forEach var="member" items="${member_datas}" varStatus="status">
   				 <c:if test="${status.index + 1 >= 1 && status.index + 1 <= member_datas.size()}"> 
					<div class="row py-1 d-flex align-items-center">
						<div class="col-1">
							<h2>${status.index + 1}</h2>
						</div>
						<div class="col-11">
							<div class="card card-stats card-round">
								<div class="row">
									<div class="col-2 d-flex flex-column align-items-center justify-content-center">
										<p class="m-0">크루 등급</p>
										<img class="w-25" src="${member.member_grade_profile}" alt="계급">
										<p class="m-0">${member.member_grade_name}</p>
									</div>
									<div class="col-7 d-flex align-items-center">
										<h4>${member.member_name}</h4>
									</div>
									<div class="col-3 pe-5 d-flex align-items-end justify-content-center flex-column">
										<h4>개인 점수: ${member.member_total_point}</h4>
									</div>
								</div>
							</div>
						</div>
					</div>
	 			</c:if>
			</c:forEach>
		</div>
	</div>	
	

		<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/core/jquery.cookie.js"></script>
</body>
</html>