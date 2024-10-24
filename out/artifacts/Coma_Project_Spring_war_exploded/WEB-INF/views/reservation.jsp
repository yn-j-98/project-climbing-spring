<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 상세 페이지</title>

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

<!-- 상단 네비게이션 바 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>

	<!-- 메인 컨테이너: 지도와 사이드바를 포함 -->
	<div class="container pt-3">

		<div class="page-inner">
			<div class="col-md-12">
				<div class="card card-stats card-round p-3">
					<div class="card-header d-flex justify-content-center">
						<div class="card-title">예   약</div>
					</div>
					<form action="GymReservation.do">
					<div class="card-body">
						<input type="hidden" id="gymNum" name="VIEW_RESERVATION_GYM_NUM" value="${model_gym_num}">
						<div class="row">
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">아이디</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_id"
										 value="${MEMBER_ID}" name="VIEW_MEMBER_ID"
										readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">이름</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_name"
										 value="${MEMBER_NAME}" name="VIEW_MEMBER_NAME"
										readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">신청날짜</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_date"
										 value="${reservation_date}" name="VIEW_RESERVATION_DATE"
										readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">가능인원</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_cnt"
										 value="${reservation_cnt}명" name="VIEW_MAX_CNT"
										readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">사용포인트</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_point"
										 value="${use_point}" name="VIEW_USE_POINT"
										readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">결제 금액</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_money"
										 value="${reservation_price}" name="VIEW_RESERVATION_PRICE"
										readonly/>
								</div>
							</div>
						</div>
					</div>
					<div class="card-action text-center">
						<button type="button"
							class="btn btn-black px-5 mb-3 mb-sm-0 me-0 me-sm-4"
							onclick="window.location.href='MAINPAGEACTION.do';">취소</button>
						<button type="submit" class="btn btn-primary px-5" id="reservationbtn">예약</button>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Core JS Files -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

</body>
</html>