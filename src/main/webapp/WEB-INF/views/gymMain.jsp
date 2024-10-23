<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>암벽장 페이지</title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />



<style>
/* 기본 스타일 */
.card.card-stats {
	cursor: pointer;
	transition: background-color 0.3s ease; /* 배경색 변화에 애니메이션 추가 */
}

.active {
	background-color: silver; /* 클릭 시 배경색을 회색으로 변경 */
}
</style>

</head>
<body>

	<!-- 상단 네비게이션 바 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>

	<!-- 메인 컨테이너: 지도와 사이드바를 포함 -->
	<div class="container pt-3">

		<div class="page-inner">
			<div class="col-md-12">
				<div class="card card-stats card-round p-3">
					<div class="card-header">
						<div class="card-title">암벽장 지도</div>
					</div>
					<div class="card-body">
						<div class="row">

							<!-- 지도 영역 -->
							<div class="col-md-8">
								<c:import url="kakaoMap.jsp"></c:import>
							</div>
							<!-- 사이드바 영역 -->
							<div class="col-md-4">
								<div class="form-group">

									<!-- 암벽장 리스트 -->
									<c:forEach var="data" items="${model_gym_datas}">
										<form action="GymInformationPage.do" id="gym_list">
											<input type="hidden" id="gym_name_input" name="gym_name">
											<input type="hidden" id="gym_location_input"
												name="gym_location"> <input type="hidden"
												name="VIEW_GYM_NUM" value="${data.model_gym_num}">
											<div class="card card-stats card-round p-3 mt-3"
												onclick="handleClick(this)" id="${data.model_gym_name}">

												<div class="responsive d-flex align-items-center">
													<i class="fa-solid fa-location-dot fs-2"></i>
													<div class="row">
														<div class="d-flex align-items-center">
															<small class="mb-0" id="gym_name">${data.model_gym_name}</small>
														</div>
														<div class="d-flex align-items-center">
															<small class="mb-0" id="gym_location">${data.model_gym_location}</small>
														</div>
													</div>
													<div class="col-md-3 d-none">
														<button type="submit" class="btn btn-primary">이동</button>
													</div>
												</div>
											</div>
										</form>
									</c:forEach>



									<!-- 페이지 네비게이션 -->
									<div class="row pt-2">
										<div class="col-md-10 d-flex justify-content-center">
											<nav aria-label="Page navigation">
												<ul id="pagination"
													class="pagination justify-content-center">

												</ul>
											</nav>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Core JS Files -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

	<!-- 스크립트 -->
	<script>
		function handleClick(element) {
			// 배경색을 회색으로 변경
			element.classList.toggle('active');

			// 이동 버튼의 visibility를 토글
			const button = element.querySelector('.col-md-3');
			if (button) {
				button.classList.toggle('d-none');
			}

			var gymName = cardElement.querySelector('#gym_name').textContent;
			var gymLocation = cardElement.querySelector('#gym_location').textContent;

			document.getElementById('gym_name_input').value = gymName;
			document.getElementById('gym_location_input').value = gymLocation;
		}

		// 페이지네이션 생성

		// 4가지 값
		// 화면에 보여질 페이지 그룹
		// 화면에 보여질 첫번째 페이지 = 화면에 그려질 마지막 페이지 - (한 화면에 나타낼 페이지 - 1)
		// 화면에 보여질 마지막 페이지 = 화면에 보여질 페이지 그룹 * 한 화면에 나타낼 페이지
		// 총 페이지 수 = Math.ceil(전체 개수 / 한 페이지에 나타낼 데이터 수)

		// 페이지네이션 생성 함수
		function renderpagination(currentPage, _totalCount) {
			// 현재 게시물의 전체 개수가 6개 이하면 pagination을 숨깁니다.
			if (_totalCount <= 5)
				return;

			// 총 페이지 수 계산 (전체 게시물 수를 한 페이지에 보여줄 게시물 수로 나눈 값의 올림)
			const totalPage = Math.ceil(_totalCount / 5);

			// 현재 페이지 그룹 계산 (현재 페이지를 6으로 나눈 값의 올림)
			const pageGroup = Math.ceil(currentPage / 5);

			// 현재 페이지 그룹에서의 마지막 페이지 계산
			let last = pageGroup * 5;

			// 마지막 페이지가 총 페이지 수를 초과하지 않도록 조정
			if (last > totalPage)
				last = totalPage;

			// 현재 페이지 그룹에서의 첫 번째 페이지 계산
			const first = last - (5 - 1) <= 0 ? 1 : last - (5 - 1);

			// 다음 그룹의 첫 페이지 계산
			const next = last + 1;

			// 이전 그룹의 마지막 페이지 계산
			const prev = first - 1;

			// 페이지네이션 버튼을 담을 비어있는 DocumentFragment 객체 생성
			const fragmentPage = document.createDocumentFragment();

			// 이전 그룹으로 이동하는 버튼 생성 (prev가 0보다 크다면 생성)
			if (prev > 0) {
				const preli = document.createElement('li');
				preli.id = 'prev-btn';
				preli.className = 'page-item';

				preli
						.insertAdjacentHTML(
								"beforeend",
								"<a id='allprev' class='page-link' href='GymMainPage.do?page="
										+ prev
										+ "' aria-label='Previous'>"
										+ "<span aria-hidden='true'>&laquo;</span> </a>");

				fragmentPage.appendChild(preli);
			}

			// 현재 페이지 그룹의 페이지 번호 버튼 생성
			for (let i = first; i <= last; i++) {
				const li = document.createElement("li");
				li.className = 'page-item';

				li.insertAdjacentHTML("beforeend",
						"<a class='page-link m-2' href='GymMainPage.do?page="
								+ i + "' id='page-" + i + "' data-num='" + i
								+ "'>" + i + "</a>");

				fragmentPage.appendChild(li);
			}

			// 다음 그룹으로 이동하는 버튼 생성 (last가 totalPage보다 작다면 생성)
			if (last < totalPage) {
				const endli = document.createElement('li');
				endli.id = 'next-btn';
				endli.className = 'page-item';

				endli
						.insertAdjacentHTML(
								"beforeend",
								"<a class='page-link' href='GymMainPage.do?page="
										+ next
										+ "' id='allnext' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a>");

				fragmentPage.appendChild(endli);
			}

			// 생성된 페이지네이션 버튼들을 화면에 추가 
			document.getElementById('pagination').appendChild(fragmentPage);

		};

		// 페이지 버튼 클릭 이벤트 처리
		$("#pagination a").click(function(e) {
			// 기본 동작(페이지 이동) 방지
			e.preventDefault();
			// 클릭된 페이지 링크 요소를 jQuery 객체로 저장
			const $item = $(this);
			// 클릭된 페이지 링크의 텍스트(페이지 번호)를 가져와 selectedPage 변수에 저장
			let selectedPage = $item.text();

			// 각 버튼의 ID에 따라 선택된 페이지 설정
			if ($item.attr("id") === "next")
				selectedPage = next;
			if ($item.attr("id") === "prev")
				selectedPage = prev;
			if ($item.attr("id") === "allprev")
				selectedPage = 1;
			if ($item.attr("id") === "allnext")
				selectedPage = totalPage;

			// 페이지네이션 재생성 및 해당 페이지 데이터 로드
			renderpagination(selectedPage, _totalCount);
			list.search(selectedPage); // 이 함수가 제대로 정의되어 있는지 확인
		});

		// DOM이 완전히 로드된 후 페이지네이션을 생성
		document.addEventListener("DOMContentLoaded", function() {
			const _totalCount = ${model_gym_total}
			; // 서버에서 전달된 전체 게시물 개수
			const currentPage = ${page_num}
			; // 서버에서 전달된 현재 페이지 번호

			renderpagination(currentPage, _totalCount); // 페이지네이션 생성 함수 호출

			// 현재 페이지를 표시하기 위해 active 클래스 추가
			$("#pagination a").removeClass("active text-white");
			$("#pagination a#page-" + currentPage)
					.addClass("active text-white");
			console.log(currentPage);
		});
	</script>



</body>
</html>
