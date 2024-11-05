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
						<a href="crewRank.do" class="text-dark text-decoration-underline link-primary">
							<h3 class="m-0"><b>크루 랭킹</b></h3>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="personalRank.do" class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">개인 랭킹</p>
						</a>
					</div>
				</div>
			</div>
			<c:forEach var="member" items="${member_datas}" varStatus="status">
   				 <c:if test="${status.index + 1 >= 1 && status.index + 1 <= 8}">
					<div class="row py-1 d-flex align-items-center">
						<div class="col-1">
							<h2>${status.index + 1}</h2>
						</div>
						<div class="col-11">
							<div class="card card-stats card-round">
								<div class="row">
									<div class="col-2 d-flex flex-column align-items-center justify-content-center">
										<p class="m-0">크루 등급</p>
										<img class="w-25" src="https://comapro.cdn1.cafe24.com${member.member_grade_profile}" alt="계급">
										<p class="m-0">${member.member_grade_name}</p>
									</div>
									<div class="col-5 d-flex align-items-center">
										<h4>${member.member_crew_name}</h4>
									</div>
									<div class="col-2 d-flex align-items-center">
										<h4>크루 점수: ${member.member_total_point}</h4>
									</div>
									<div class="col-3 pe-5 d-flex align-items-end justify-content-center flex-column">
										<p class="m-0">크루장: ${member.member_crew_leader}</p>
										<p class="m-0">크루 인원: ${member.member_crew_current_size}</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
			<div id="member-container"></div>
		</div>
	</div>	
	

		<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/core/jquery.cookie.js"></script>
	<script>
		let member_min_num = 0;
		const size = 8; // 이 값은 페이지당 항목 수로 변경되지 않습니다.
		let currentIndex = 8; // 전역 변수로 인덱스를 추적

		let loading = false;
		let isLastPage = false; // 마지막 페이지 여부를 나타내는 플래그

		// debounce 함수 정의
		function debounce(func, delay) {
			let timeout;
			return function() {
				const context = this, args = arguments;
				clearTimeout(timeout);
				timeout = setTimeout(() => func.apply(context, args), delay);
			};
		}

		function loadMembers() {
			if (loading || isLastPage) return;
			loading = true;

			member_min_num++;

			fetch(`crewRankAsync.do`
					, {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json'
						},
						body: JSON.stringify({
							member_min_num: member_min_num,
						})
					})
					.then(response => {
						if (!response.ok) {
							throw new Error('Network response was not ok ' + response.statusText);
						}
						return response.json();
					}) // 응답을 JSON으로 변환
					.then(data => {

						// 페이지 번호를 증가시킵니다!
						console.log(data);

						const container = document.getElementById('member-container');

						// 새로운 데이터가 추가되기 전의 문서 높이 저장
						const previousHeight = document.body.scrollHeight;

						data.forEach((member,index) => {

							console.log("member" + member);

							const memberHtml =
									'<div class="row py-1 d-flex align-items-center">'
									+ '<div class="col-1">'
									+ '<h2>' + (currentIndex + 1) + '</h2>'
									+ '</div>'
									+ '<div class="col-11">'
									+ '<div class="card card-stats card-round">'
									+ '<div class="row">'
									+ '<div class="col-2 d-flex flex-column align-items-center justify-content-center">'
									+ '<p class="m-0">크루 등급</p>'
									+ '<img class="w-25" src="' + member.member_grade_profile + '" alt="계급">'
									+ '<p class="m-0">' + member.member_grade_name + '</p>'
									+ '</div>'
									+ '<div class="col-5 d-flex align-items-center">'
									+ '<h4>' + member.member_crew_name + '</h4>'
									+ '</div>'
									+ '<div class="col-2 d-flex align-items-center">'
									+ '<h4>크루 점수: ' + member.member_total_point + '</h4>'
									+ '</div>'
									+ '<div class="col-3 pe-5 d-flex align-items-end justify-content-center flex-column">'
									+ '<p class="m-0">크루장: ' + member.member_crew_leader + '</p>'
									+ '<p class="m-0">크루 인원: ' + member.member_crew_current_size + '</p>'
									+ '</div>'
									+ '</div>'
									+ '</div>'
									+ '</div>'
									+ '</div>';
							container.insertAdjacentHTML('beforeend', memberHtml);
							currentIndex++;
						});
						// 새로운 데이터가 추가된 후의 문서 높이 저장
						const newHeight = document.body.scrollHeight;

						// 스크롤을 새로운 데이터가 표시되는 위치로 이동
						window.scrollTo(0, previousHeight);

						// 마지막 페이지인지 체크
						if (data.length === 0) {
							console.log("마지막 페이지 인지 체크")
							isLastPage = true;
							window.removeEventListener('scroll', onScroll); // 스크롤 이벤트 리스너 제거
						}

						loading = false;

					})
					.catch(() => {
						console.error('Fetch error:', error);
						loading = false;
					});



		}

		// debounce 적용된 스크롤 핸들러
		const debouncedLoadMembers = debounce(loadMembers,300);

		function onScroll() {
			if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 50) {
				debouncedLoadMembers();
			}
		}

		window.addEventListener('scroll', onScroll);

	</script>
</body>
</html>