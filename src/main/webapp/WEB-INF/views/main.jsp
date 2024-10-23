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

<style>
.banner {
	position: relative;
	width: 100%;
	height: 420px;
	border-radius: 32px;
	margin-left: auto;
	margin-right: auto;
}

.banner .swiper-slide {
	text-align: center;
	font-size: 18px;
	background: #fff;
	display: flex;
	justify-content: center;
	align-items: center;
}

.banner .swiper-slide img {
	display: block;
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.mySwiper2, .mySwiper3 {
	width: 100%;
	height: 400px;
	position: relative;
}

.mySwiper2 .swiper-slide,
.mySwiper3 .swiper-slide {
	text-align: center;
	font-size: 18px;
	border-radius: 32px;
	background: #fff;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction:column;
} 
.menu-box{
	width:120px;
	height:120px;
}

@media (max-width:768px){
	.menu-box{
		width:60px;
		height:60px;
	}
	.menu-box + a > h4 {
		font-size:1rem;
	}
}
</style>
</head>
<body>
	<!-- GNB 커스텀 태그 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>

	<!-- container start -->
	<div class="container">
		<div class="page-inner">
			<div class="row pt-3 pb-5">
				<div class="col-12">
					<div class="swiper mySwiper banner">
						<div class="swiper-wrapper">
							<div class="swiper-slide"><img src="images/banner2.jpg" alt="banner2"></div>
							<div class="swiper-slide"><img src="images/banner1.png" alt="banner1"></div>
						</div>
						<div class="swiper-button-next"></div>
						<div class="swiper-button-prev"></div>
						<div class="swiper-pagination"></div>
					</div>
				</div>
			</div>
			<div class="row py-5">
				<div
					class="col-2 d-flex justify-content-center align-items-center flex-column">
					<div
						class="rounded-circle bg-white d-block d-flex justify-content-center align-items-center menu-box border border-light-subtle">
						<a href="StorePage.do" class="d-block">
							<button type="button" class="btn text-dark p-3 text-center">
								<span class="display-6 text-primary"> <i
									class="fa-solid fa-store"></i>
								</span>
							</button>
						</a>
					</div>
					<a href="StorePage.do" class="mt-2">
						<h4 class="text-center text-primary">암벽화 상점</h4>
					</a>
				</div>
				<div
					class="col-2 d-flex justify-content-center align-items-center flex-column">
					<div
						class="rounded-circle bg-white d-block d-flex justify-content-center align-items-center menu-box border border-light-subtle">
						<a href="GymMainPage.do" class="d-block">
							<button type="button" class="btn text-dark p-3 text-center">
								<span class="display-6 text-primary"> <i
									class="fa-solid fa-chess"></i>
								</span>
							</button>
						</a>
					</div>
					<a href="GymMainPage.do" class="mt-2">
						<h4 class="text-center text-primary">암벽장 투어</h4>
					</a>
				</div>
				<div
					class="col-2 d-flex justify-content-center align-items-center flex-column">
					<div
						class="rounded-circle bg-white d-block d-flex justify-content-center align-items-center menu-box border border-light-subtle">
						<a href="CrewListPage.do" class="d-block">
							<button type="button" class="btn text-dark p-3 text-center">
								<span class="display-6 text-primary"> <i
									class="fa-solid fa-crown"></i>
								</span>
							</button>
						</a>
					</div>
					<a href="CrewListPage.do" class="mt-2">
						<h4 class="text-center text-primary">크루 가입</h4>
					</a>
				</div>
				<div
					class="col-2 d-flex justify-content-center align-items-center flex-column">
					<div
						class="rounded-circle bg-white d-block d-flex justify-content-center align-items-center menu-box border border-light-subtle">
						<a href="LocationPage.do" class="d-block">
							<button type="button" class="btn text-dark p-3 text-center">
								<span class="display-6 text-primary"> <i
									class="fa-solid fa-comments"></i>
								</span>
							</button>
						</a>
					</div>
					<a href="LocationPage.do" class="mt-2">
						<h4 class="text-center text-primary">지역별 커뮤니티</h4>
					</a>
				</div>
				<div
					class="col-2 d-flex justify-content-center align-items-center flex-column">
					<div
						class="rounded-circle bg-white d-block d-flex justify-content-center align-items-center menu-box border border-light-subtle">
						<a href="CrewBattlePage.do" class="d-block">
							<button type="button" class="btn text-dark p-3 text-center">
								<span class="display-6 text-primary"> <i
									class="fa-solid fa-fire"></i>
								</span>
							</button>
						</a>
					</div>
					<a href="CrewBattlePage.do" class="mt-2">
						<h4 class="text-center text-primary">크루전</h4>
					</a>
				</div>
				<div
					class="col-2 d-flex justify-content-center align-items-center flex-column">
					<div
						class="rounded-circle bg-white d-block d-flex justify-content-center align-items-center menu-box border border-light-subtle">
						<a href="RankingPage.do" class="d-block">
							<button type="button" class="btn text-dark p-3 text-center">
								<span class="display-6 text-primary"> <i
									class="fa-solid fa-ranking-star"></i>
								</span>
							</button>
						</a>
					</div>
					<a href="CrewRankingPage.do" class="mt-2">
						<h4 class="text-center text-primary">전체 랭킹</h4>
					</a>
				</div>
			</div>
			<div class="row py-5">
				<div class="col-12">
					<div class="card card-stats rounded-5 border border-light-subtle">
						<div class="row">
							<div class="col-md-3 position-relative">
								<div class="p-5">
									<h1 class="ms-5">크루</h1>
									<h1 class="ms-5">명예의 전당</h1>
								</div>
								<div class="w-50 h-50 position-absolute top-50 start-50 translate-middle">
									<div class="swiper-button-next btn1"></div>
									<div class="swiper-button-prev btn1"></div>
								</div>
							</div>
							<div class="col-md-9">
								<div class="swiper mySwiper2">
									<div class="swiper-wrapper">
										<c:forEach var="crew" items="${model_crew_rank_datas}" varStatus="status">
										    <c:if test="${status.index + 1 >= 1 && status.index + 1 <= model_crew_rank_datas.size()}">
										        <div class="swiper-slide">
										            <div class="d-flex ">
										                <h1>${status.index + 1}</h1>
										                <img class="w-100 h-100" src="${crew.model_member_crew_profile}" alt="crew logo"/>
										            </div>
										            <h3 class="pt-5">${crew.model_member_crew_name}</h3>
										        </div>
										    </c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row py-5">
				<div class="col-12">
					<div class="card card-stats rounded-5 border border-light-subtle">
						<div class="row">
							<div class="col-md-9">
								<div class="swiper mySwiper3">
									<div class="swiper-wrapper">
										<c:forEach var="member" items="${model_member_rank_datas}" varStatus="status">
										    <c:if test="${status.index + 1 >= 1 && status.index + 1 <= model_member_rank_datas.size()}">
										        <div class="swiper-slide">
										            <div class="d-flex ">
										                <h1>${status.index + 1}</h1>
										                <img class="w-100 h-100" src="${member.model_member_profile}" alt="profile"/>
										            </div>
										            <h3 class="pt-5">${member.model_member_name}</h3>
										        </div>
										    </c:if>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="col-md-3 position-relative">
								<div class="p-5 text-end">
									<h1 class="me-5">개인</h1>
									<h1 class="me-5">명예의 전당</h1>
								</div>
								<div class="w-50 h-50 position-absolute top-50 start-50 translate-middle">
									<div class="swiper-button-next btn2"></div>
									<div class="swiper-button-prev btn2"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row pt-5 pb-3">
					<div class="col-12">
						<h1 class="text-center">커뮤니티</h1>
					</div>
				</div>
				<div class="row pb-5 mb-5">
					<c:forEach var="board" items="${model_board_datas}">					
						<div class="col-6 col-lg-2 pt-5 pt-lg-0 mt-5 mt-lg-0" style="height:260px;">
							<div class="card card-stats w-100 h-100 rounded-5 p-3 overflow-hidden border border-light-subtle">
								<h4 class="text-center">${board.model_board_title}</h4>	
								<a href="BOARDONEPAGEACTION.do?model_board_num=${board.model_board_num}" class="text-dark link-primary">
									${board.model_board_content}
								</a>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="assets/js/core/jquery.cookie.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>

	<script>
		var swiper = new Swiper(".mySwiper", {
			slidesPerView : 1,
			spaceBetween : 30,
	      autoplay: {
	          delay: 2500,
	          disableOnInteraction: false,
	        },
			loop : true,
			pagination : {
				el : ".swiper-pagination",
				clickable : true,
			},
			navigation : {
				nextEl : ".swiper-button-next",
				prevEl : ".swiper-button-prev",
			},
		});
	    
	    var swiper = new Swiper('.mySwiper2', {
	        slidesPerView: 4,
	        direction: getDirection(),
	        navigation: {
	          nextEl: '.swiper-button-next.btn1',
	          prevEl: '.swiper-button-prev.btn1',
	        },
	        on: {
	          resize: function () {
	            swiper.changeDirection(getDirection());
	          },
	        },
	      });

	      function getDirection() {
	        var windowWidth = window.innerWidth;
	        var direction = window.innerWidth <= 760 ? 'vertical' : 'horizontal';

	        return direction;
	      } 
	    
		    var swiper = new Swiper('.mySwiper3', {
		        slidesPerView: 4,
		        direction: getDirection2(),
		        navigation: {
		          nextEl: '.swiper-button-next.btn2',
		          prevEl: '.swiper-button-prev.btn2',
		        },
		        on: {
		          resize: function () {
		            swiper.changeDirection(getDirection2());
		          },
		        },
		      });

		      function getDirection2() {
		        var windowWidth = window.innerWidth;
		        var direction = window.innerWidth <= 760 ? 'vertical' : 'horizontal';

		        return direction;
		      } 
	    
	    
	</script>


</body>

</html>