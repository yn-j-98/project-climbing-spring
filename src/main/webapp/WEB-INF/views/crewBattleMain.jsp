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

	<style>
		.clickable-card {
			cursor: pointer;
			transition: background-color 0.3s ease;
		}

		.clickable-card:hover {
			background-color: #f0f0f0;
		}
	</style>

</head>

<body>
<!--   Core JS Files   -->
<script src="assets/js/core/jquery-3.7.1.min.js"></script>
<script src="assets/js/core/popper.min.js"></script>
<script src="assets/js/core/bootstrap.min.js"></script>

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
					<a href="CrewBattlePage.do"
					   class="text-dark text-decoration-underline link-primary">
						<h3 class="m-0">
							<b>크루전 개최</b>
						</h3>
					</a>
					<h3 class="px-5 m-0">/</h3>
					<a href="crewList.do"
					   class="text-dark text-decoration-none link-primary">
						<p class="fs-4 m-0">크루 가입</p>
					</a>
				</div>
			</div>
		</div>
		<h4 align="center">우리 크루가 참여하고 있는 크루전</h4>
		<br>
		<!-- 우리가 참여하고 있는 크루전 -->
		<div class="row justify-content-center">
			<div class="col-md-10">
				<div class="card card-stats card-round pt-3 px-5 pb-5 clickable-card">
					<!-- 크루전 번호가 없거나 빈 경우 메시지 표시 -->
					<c:if test="${empty my_battle}">
						<div class="alert alert-danger" role="alert">
							크루전 없습니다.
						</div>
					</c:if>
					<div class="row">
						<!-- 크루전 번호가 있는 경우 -->
						<c:if test="${not empty my_battle}">
							<c:choose>
								<c:when test="${my_battle != null}">
									<div class="col-md-6 mb-4">
										<!-- 암벽장 사진 -->
										<div class="mb-3">
											<h4>암벽장 사진 :</h4>
											<img src="${my_battle.battle_gym_profile}" alt="암벽장 사진" class="img-fluid" style="max-width: 100%; height: auto;" />
										</div>
										<!-- 크루전 개최한 장소명 -->
										<a href="crewBattleDetail.do?battle_num=${my_battle.battle_num}">
											<h2>암벽장 이름 : ${my_battle.battle_gym_name}</h2>
										</a>
									</div>
									<div class="col-md-6 mb-4">
										<!-- 암벽장 위치 -->
										<h4>암벽장 위치 : ${my_battle.battle_gym_location}</h4>
										<!-- 크루전 개최 날짜 -->
										<h5>크루전 개최 날짜: ${my_battle.battle_game_date}</h5>
									</div>
								</c:when>
								<c:otherwise>
									<div class="col-12">
										<p>크루전 개최 목록 데이터가 없습니다 ...</p>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<!-- 우리가 참여하고 있는 크루전 end -->

		<!-- 전체 크루전 목록 -->
		<div class="row border-top border-dark pt-3 px-3 pb-3">
			<h4 align="center">크루전 목록</h4>
			<c:if test="${not empty battle_datas}">
				<c:forEach var="battle" items="${battle_datas}">
					<div class="col-md-4 mb-3">
						<div class="card">
							<img src="${battle.battle_gym_profile}" class="card-img-top" alt="암벽장 사진">
							<div class="card-body clickable-card">
								<a href="crewBattleDetail.do?battle_num=${battle.battle_num}">
									<h5 class="card-title">${battle.battle_gym_name}</h5>
								</a>
								<p class="card-text">위치: ${battle.battle_gym_location}</p>
								<p class="card-text">날짜: ${battle.battle_game_date}</p>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<!-- 전체 크루전 목록 end -->
		<!-- 페이지네이션 -->
		<div id="pageNation" class="row justify-content-center">
			<div class="row pt-5">
				<div class="col-md-10 d-flex justify-content-center">
					<nav aria-label="Page navigation">
						<input type="hidden" id="totalCount" value="${total}">
						<input type="hidden" id="currentPage" value="${Page}">
						<ul id="pagination" class="pagination justify-content-center">

						</ul>
					</nav>
				</div>
			</div>
		</div>
	</div>
	<!-- container end -->
</div>
<script type="text/javascript">
	$(document).ready(function () {
		renderpagination();
	})
	// 페이지네이션 생성 함수
	function renderpagination() {
		var url = window.location.pathname;
		console.log("pagenation.js url :"+url)
		var _totalCount = parseInt($("#totalCount").val());  // 서버에서 전달된 전체 게시물 개수
		//NaN / null 유효성 검사
		if (isNaN(_totalCount) || _totalCount === 0 || _totalCount === null) {
			_totalCount = 0;
		}

		var currentPage = parseInt($("#currentPage").val()); // 서버에서 전달된 현재 페이지 번호
		//NaN / null 유효성 검사
		if (isNaN(currentPage) || currentPage === 0 || currentPage === null) {
			currentPage = 1;
		}

		console.log("pagenation.js _totalCount =["+_totalCount+"]");
		console.log("pagenation.js currentPage =["+currentPage+"]");

		$("#pagination").empty();  // 기존 페이지네이션을 지우기

		// 현재 게시물의 전체 개수가 10개 이하이면 pagination을 숨깁니다.
		if (_totalCount <= 10) return;

		// 총 페이지 수 계산 (전체 게시물 수를 한 페이지에 보여줄 게시물 수로 나눈 값의 올림)
		const totalPage = Math.ceil(_totalCount / 10);
		console.log("pagenation.js totalPage =["+totalPage+"]");

		// 현재 페이지 그룹 계산 (현재 페이지를 10으로 나눈 값의 올림)
		const pageGroup = Math.ceil(currentPage / 10);
		console.log("pagenation.js pageGroup =["+pageGroup+"]");

		// 현재 페이지 그룹에서의 마지막 페이지 계산
		let last = pageGroup * 10;
		console.log("pagenation.js last =["+last+"]");

		// 마지막 페이지가 총 페이지 수를 초과하지 않도록 조정
		if (last > totalPage) last = totalPage;

		// 현재 페이지 그룹에서의 첫 번째 페이지 계산
		const first = last - (10 - 1) <= 0 ? 1 : last - (10 - 1);
		console.log("pagenation.js first =["+first+"]");

		// 다음 그룹의 첫 페이지 계산
		const next = last + 1;
		console.log("pagenation.js next =["+next+"]");

		// 이전 그룹의 마지막 페이지 계산
		const prev = first - 1;
		console.log("pagenation.js prev =["+prev+"]");

		// 페이지네이션 버튼을 담을 비어있는 DocumentFragment 객체 생성
		const fragmentPage = document.createDocumentFragment();

		// 이전 그룹으로 이동하는 버튼 생성 (prev가 0보다 크다면 생성)
		if (prev > 0) {
			const preli = document.createElement('li');
			preli.className = 'page-item';

			// 쿼리 파라미터 없이 링크 생성
			preli.insertAdjacentHTML("beforeend",
					"<a class='page-link' href='"+url+"?page="+prev+"' aria-label='Previous' data-page='" + prev + "'>" +
					"<span aria-hidden='true'>&laquo;</span>" +
					"</a>"
			);

			fragmentPage.appendChild(preli); // fragment에 추가
		}

		// 현재 페이지 그룹의 페이지 번호 버튼 생성
		for (let i = first; i <= last; i++) {
			const li = document.createElement("li");
			li.className = 'page-item';

			let linkHTML = "<a class='page-link m-2' href='"+url+"?page="+i+"' data-page='" + i + "'>" + i + "</a>";
			if (i === currentPage) {
				linkHTML = "<a class='page-link m-2 active' href='"+url+"?page="+i+"' data-page='" + i + "'>" + i + "</a>";
			}

			li.insertAdjacentHTML("beforeend", linkHTML);
			fragmentPage.appendChild(li); // fragment에 추가
		}

		// 다음 그룹으로 이동하는 버튼 생성 (last가 totalPage보다 작다면 생성)
		if (last < totalPage) {
			const endli = document.createElement('li');
			endli.className = 'page-item';

			// 쿼리 파라미터 없이 링크 생성
			endli.insertAdjacentHTML("beforeend",
					"<a class='page-link' href='"+url+"?page="+next+"' aria-label='Next' data-page='" + next + "'>" +
					"<span aria-hidden='true'>&raquo;</span>" +
					"</a>"
			);

			fragmentPage.appendChild(endli); // fragment에 추가
		}

		// 생성된 페이지네이션 버튼들을 화면에 추가
		document.getElementById('pagination').appendChild(fragmentPage);
	}

	// DOM이 완전히 로드된 후 페이지네이션을 생성
	//     renderpagination(currentPage, _totalCount);

	// 페이지 버튼 클릭 이벤트 처리
	$("#pagination").on("click", "a", function (e) {
		// 기본 동작(페이지 이동) 방지
		e.preventDefault();
		// 클릭된 페이지 링크 요소를 jQuery 객체로 저장
		const $item = $(this);
		// 클릭된 링크의 'data-page' 값(페이지 번호)을 가져옴
		const selectedPage = parseInt($item.attr("data-page"));

		// 페이지네이션 재생성 및 해당 페이지 데이터 로드
		if (selectedPage && selectedPage !== currentPage) {
			currentPage = selectedPage;
			renderpagination(currentPage, _totalCount);
			// 페이지 변경 시 필요한 추가 데이터 로드 로직이 있다면 여기에 추가
		}
	});
</script>
</body>

</html>