<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="member_id" %>

<style>
	.navbar .navbar-nav .nav-item .nav-link{
		color: #212223 !important;
		padding: 8px 30px !important;
		font-size: 16px !important;
	}
</style>

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
				<li class="nav-item p-3"><a class="nav-link " href="grigri.do">GRIGRI</a></li>
				<li class="nav-item p-3"><a class="nav-link" href="store.do">상점</a></li>
				<li class="nav-item p-3"><a class="nav-link" href="gymMain.do">암벽장</a></li>
				<li class="nav-item p-3"><a class="nav-link" href="crewList.do">크루
					<span class="badge text-bg-danger">HOT</span>
				</a></li>
				<li class="nav-item p-3"><a class="nav-link" href="crewRank.do">랭킹</a></li>
				<li class="nav-item p-3"><a class="nav-link" href="community.do">커뮤니티</a></li>
			</ul>
			<ul class="navbar-nav text-center">
				<c:if test="${empty member_id}">
					<li class="nav-item p-3"><a class="nav-link" href="login.do">로그인</a></li>
				</c:if>
				<c:if test="${not empty member_id}">
					<li class="nav-item p-3"><a class="nav-link" href="myPage.do">마이페이지</a></li>
					<li class="nav-item p-3"><a class="nav-link" href="logout.do">로그아웃</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
</div>

<div class="main-header">
	<div class="main-header-logo">
		<!-- Logo Header -->
		<div class="logo-header" data-background-color="dark">
			<a href="main.do" class="logo"> <img
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
				<a class="navbar-brand" href="main.do">
					<img src="./images/logo.png" alt="navbar brand" class="navbar-brand" height="80" />
				</a>
			</nav>
			<nav class="navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="grigri.do">GRIGRI</a></li>
					<li class="nav-item"><a class="nav-link" href="store.do">상점</a></li>
					<li class="nav-item"><a class="nav-link" href="gymMain.do">암벽장</a></li>
					<li class="nav-item"><a class="nav-link position-relative" href="crewList.do">
						<span class="badge rounded-pill text-bg-danger me-1">HOT</span>크루
					</a></li>
					<li class="nav-item"><a class="nav-link" href="crewRank.do">랭킹</a></li>
					<li class="nav-item"><a class="nav-link" href="community.do">커뮤니티</a></li>
				</ul>
			</nav>
			<ul class="navbar-nav topbar-nav align-items-center">
				<c:if test="${empty member_id}">
					<li class="nav-item">
						<a class="nav-link" href="login.do"> login </a>
					</li>
				</c:if>
				<c:if test="${not empty member_id}">
					<li class="nav-item">
						<a class="nav-link" href="myPage.do"> 마이페이지 </a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="logout.do"> logout </a>
					</li>
				</c:if>

			</ul>
		</div>
	</nav>
	<!-- End Navbar -->
</div>