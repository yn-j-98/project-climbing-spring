<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 크루전 개최</title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />

	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<!-- pagination Js -->
	<script src="../../js/pagination.js"></script>
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
						<a href="crew.do"
						   class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">내크루</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="crewCommunity.do"
						   class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">커뮤니티</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="crewBattle.do"
						   class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">크루전 개최</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="crewList.do"
						   class="text-dark text-decoration-underline link-primary">
							<h3 class="m-0">
								<b>크루 가입</b>
							</h3>
						</a>
					</div>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-10 pt-4 px-4 pb-4">
					<div class="row justify-content-center">
						<div class="col-md-5">크루명</div>
						<div class="col-md-4">크루장명</div>
						<div class="col-md-3">인원수</div>
					</div>
				</div>
			</div>
			<div class="row justify-content-center">
				<c:if test="${empty crew_datas}">
					<div class="alert alert-danger" role="alert">
						크루가 없습니다
					</div>
				</c:if>
				<c:if test="${not empty crew_datas}">
					<c:forEach var="crew_data" items="${crew_datas}">
						<c:choose>
							<c:when test="${crew_data != null}">
								<div class="col-md-10">
									<div class="card card-stats card-round pt-4 px-4 pb-4">
										<div class="row justify-content-center">
								
											<div class="col-md-5">
												<!-- 아래 링크를 클릭하면 선택한 크루의 설명창이 뜨게 됨 -->
												<a href="crewInfo.do?crew_num=${crew_data.crew_num}"><b>${crew_data.crew_name}</b></a>
											</div>
											<div class="col-md-4">${crew_data.crew_leader}</div>
											<div class="col-md-3">최대 크루원 수:
												${crew_data.crew_max_member_size}</div>
											</div>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<p>크루 목록 데이터가 없습니다 ...</p>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</div>
			<div id="pageNation" class="row justify-content-center">
				<div class="row pt-5">
					<div class="col-md-12 d-flex justify-content-center ">
						<nav aria-label="Page navigation">
							<input type="hidden" id="totalCount" value="${total}">
							<input type="hidden" id="currentPage" value="${page}">
							<ul id="pagination" class="pagination justify-content-center align-items-center">

							</ul>
						</nav>
					</div>
				</div>
			</div>



		</div>
		<!-- container end -->
	</div>







</body>

</html>