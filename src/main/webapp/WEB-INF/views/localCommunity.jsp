<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>지역 커뮤니티</title>
	<!-- Fonts and icons -->
	<script src="assets/js/plugin/webfont/webfont.min.js"></script>
	<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
			crossorigin="anonymous"></script>

	<!-- CSS Files -->
	<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="assets/css/plugins.min.css" />
	<link rel="stylesheet" href="assets/css/kaiadmin.css" />

	<!--   Core JS Files   -->
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/core/jquery.cookie.js"></script>

	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>

	<%-- pagination Js File --%>
	<script src="/js/pagination.js"></script>
</head>
<body>
<!-- GNB 커스텀 태그 -->
<mytag:gnb member_id="${MEMBER_ID}" ></mytag:gnb>

<!-- container start -->
<div class="container">
	<div class="page-inner">
		<div class="row pt-5 pb-2">
			<div class="col-12">
				<h1 class="text-center">커뮤니티</h1>
			</div>
		</div>
		<div class="row pt-2 pb-5">
			<div class="col-12">
				<div class="d-flex justify-content-center align-items-center">
					<a href="community.do" class="text-dark text-decoration-none link-primary">
						<p class="fs-4 m-0">전체</p>
					</a>
					<h3 class="px-5 m-0">/</h3>
					<a href="location.do" class="text-dark text-decoration-underline link-primary">
						<h3 class="m-0"><b>지역</b></h3>
					</a>
				</div>
			</div>
		</div>
		<div class="row pt-3">
			<div class="col-12 p-0">
				<form action="location.do" method="GET">
					<div class="row">
						<div class="col-md-3 col-lg-3">
							<div class="form-group">
								<select name="search_keyword"
										class="form-select form-control-lg">
									<option value="SEOUL">서울</option>
									<option value="GYEONGGI">경기</option>
									<option value="INCHEON">인천</option>
									<option value="CHUNGNAM">충남</option>
								</select>
							</div>

						</div>
						<div class="col-md-6 col-lg-7">
							<div class="form-group">
								<div class="input-icon">
									<input name="search_content" type="text" class="form-control"
										   placeholder="검색어를 입력해주세요" /> <span class="input-icon-addon">
											<button type="submit" class="btn">
												<i class="fa fa-search"></i>
											</button>
										</span>
								</div>
							</div>
						</div>
						<div
								class="col-md-3 col-lg-2 d-flex align-items-center justify-content-end">
							<a href="boardInsert.do" class="d-block btn">
								<button type="button"
										class="btn btn-primary btn-round px-5 py-3">글 작성</button>
							</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<c:forEach var="board" items="${BOARD}">
			<c:choose>
				<c:when test="${board.board_num> 0}">
					<div class="row pt-5">
						<div class="col-12">
							<div class="card card-stats card-round mb-0">
								<div class="card-body p-5 d-flex justify-content-between">
									<h3 class="card-title">
										<a href="content.do?board_num=${board.board_num}"
										   class="link-dark"> ${board.board_title}</a>
									</h3>
									<div class="info-user">
										<div class="username">작성자 : ${board.board_writer_id}</div>
										<div class="status">글 조회수 : ${board.board_cnt}</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h1>최신 글 목록이 없습니다...</h1>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<!-- FIXME 페이지네이션 코드 시작 -->
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
		<!-- 페이지네이션 리스트 코드 종료 -->
	</div>
</div>
</body>

</html>