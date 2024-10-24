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
						<a href="CrewBattlePage.do"
						   class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">
								<b>크루전 개최</b>
						</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="crewList.do"
						   class="text-dark text-decoration-underline  link-primary">
							<h3 class="m-0">크루 가입</h3>
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
			<div class="row pt-5">
				<div class="col-md-10 d-flex justify-content-center">
					<nav aria-label="Page navigation">
						<input type="hidden" id="totalCount" value="${total}">
						<input type="hidden" id="currentPage" value="${page}">
						<ul id="pagination" class="pagination justify-content-center">

						</ul>
					</nav>
				</div>
			</div>



		</div>
		<!-- container end -->
	</div>


<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	
	<script type="text/javascript">

		// 페이지네이션 생성

		// 4가지 값
		// 화면에 보여질 페이지 그룹
		// 화면에 보여질 첫번째 페이지 = 화면에 그려질 마지막 페이지 - (한 화면에 나타낼 페이지 - 1)
		// 화면에 보여질 마지막 페이지 = 화면에 보여질 페이지 그룹 * 한 화면에 나타낼 페이지
		// 총 페이지 수 = Math.ceil(전체 개수 / 한 페이지에 나타낼 데이터 수)

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
			var pageCount = 5; // 보여주고 싶은 페이지 개수 입력
			// 현재 게시물의 전체 개수가 10개 이하이면 pagination을 숨깁니다.
			if (_totalCount <= pageCount) return;

			// 총 페이지 수 계산 (전체 게시물 수를 한 페이지에 보여줄 게시물 수로 나눈 값의 올림)
			const totalPage = Math.ceil(_totalCount / pageCount);
			console.log("pagenation.js totalPage =["+totalPage+"]");

			// 현재 페이지 그룹 계산 (현재 페이지를 10으로 나눈 값의 올림)
			const pageGroup = Math.ceil(currentPage / pageCount);
			console.log("pagenation.js pageGroup =["+pageGroup+"]");

			// 현재 페이지 그룹에서의 마지막 페이지 계산
			let last = pageGroup * pageCount;
			console.log("pagenation.js last =["+last+"]");

			// 마지막 페이지가 총 페이지 수를 초과하지 않도록 조정
			if (last > totalPage) last = totalPage;

			// 현재 페이지 그룹에서의 첫 번째 페이지 계산
			const first = last - (pageCount - 1) <= 0 ? 1 : last - (pageCount - 1);
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