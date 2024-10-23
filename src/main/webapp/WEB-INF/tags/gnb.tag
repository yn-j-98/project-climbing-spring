<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="member_id" %>


<div class="offcanvas-lg offcanvas-start d-lg-none" tabindex="-1"
		id="offcanvasResponsive" aria-labelledby="offcanvasResponsiveLabel">
		<div class="offcanvas-header">
			<h5 class="offcanvas-title" id="offcanvasResponsiveLabel"></h5>
			<button type="button" class="btn-close" data-bs-dismiss="offcanvas"
				data-bs-target="#offcanvasResponsive" aria-label="Close"></button>
		</div>
		<div class=" offcanvas-body py-3">
			<nav class="navbar h-100 flex-column justify-content-between">
				<ul class="navbar-nav text-center">
					<li class="nav-item p-3"><a class="nav-link" href="StorePage.do">상점</a></li>
					<li class="nav-item p-3"><a class="nav-link" href="GymMainPage.do">암벽장</a></li>
					<li class="nav-item p-3"><a class="nav-link" href="CrewListPage.do">크루</a></li>
					<li class="nav-item p-3"><a class="nav-link" href="CrewRankingPage.do">랭킹</a></li>
					<li class="nav-item p-3"><a class="nav-link" href="MainCommunityPage.do">커뮤니티</a></li>
				</ul>
				<ul class="navbar-nav text-center">
					<c:if test="${empty member_id}">
						<li class="nav-item p-3"><a class="nav-link" href="LOGINPAGEACTION.do">로그인</a></li>					
					</c:if>
					<c:if test="${not empty member_id}">
						<li class="nav-item p-3"><a class="nav-link" href="MYPAGEPAGEACTION.do">마이페이지</a></li>
						<li class="nav-item p-3"><a class="nav-link" href="LOGOUTPAGEACTION.do">로그아웃</a></li>					
					</c:if>
				</ul>
			</nav>
		</div>
	</div>

	<div class="main-header">
		<div class="main-header-logo">
			<!-- Logo Header -->
			<div class="logo-header" data-background-color="dark">
				<a href="MAINPAGEACTION.do" class="logo"> <img
					src="images/logo.png" alt="navbar brand"
					class="navbar-brand" height="50" />
				</a>
				<div class="nav-toggle" data-bs-toggle="offcanvas"
					data-bs-target="#offcanvasResponsive"
					aria-controls="offcanvasResponsive">
					<button class="btn btn-toggle toggle-sidebar">
						<i class="gg-menu-right"></i>
					</button>
					<button class="btn btn-toggle sidenav-toggler">
						<i class="gg-menu-left"></i>
					</button>
				</div>
			</div>
			<!-- End Logo Header -->
		</div>
		<!-- Navbar Header -->
		<nav
			class="navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom">
			<div class="container-fluid justify-content-between">
				<nav
					class="navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex">
					<a class="navbar-brand" href="MAINPAGEACTION.do"> 
						<img src="./images/logo.png" alt="navbar brand" class="navbar-brand" height="80" />
					</a>
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="StorePage.do">상점</a></li>
						<li class="nav-item"><a class="nav-link" href="GymMainPage.do">암벽장</a></li>
						<li class="nav-item"><a class="nav-link" href="CrewListPage.do">크루</a></li>
						<li class="nav-item"><a class="nav-link" href="CrewRankingPage.do">랭킹</a></li>
						<li class="nav-item"><a class="nav-link" href="MainCommunityPage.do">커뮤니티</a></li>
					</ul>
				</nav>
				<ul class="navbar-nav topbar-nav ms-md-auto align-items-center">
					<c:if test="${empty member_id}">
						<li class="nav-item">
							<a class="nav-link" href="LOGINPAGEACTION.do"> login </a>
						</li>
					</c:if>
					<c:if test="${not empty member_id}">
						<li class="nav-item">
							<a class="nav-link" href="MYPAGEPAGEACTION.do"> 마이페이지 </a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="LOGOUTPAGEACTION.do"> logout </a>
						</li>
					</c:if>

				</ul>
			</div>
		</nav>
		<!-- End Navbar -->
	</div>