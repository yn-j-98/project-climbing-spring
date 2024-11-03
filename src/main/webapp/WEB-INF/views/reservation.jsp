<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<html>
<head>

<title>예약 상세 페이지</title>

<!-- 결제 포트원 추가 -->
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>

<!-- 결제 외부 JS 추가 -->
<script src="../../js/payment.js"></script>

<!-- Core JS Files -->
<script src="../../assets/js/core/jquery-3.7.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script src="../../assets/js/core/popper.min.js"></script>
<script src="../../assets/js/core/bootstrap.min.js"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="../../assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="../../assets/css/plugins.min.css" />
<link rel="stylesheet" href="../../assets/css/kaiadmin.css" />



<!-- Fonts and icons -->
<script src="../../assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>


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
					<div class="card-body">
						<input type="hidden" id="gymNum" name="reservation_gym_num" value="${gym_num}">
						<input type="hidden" id="gymName" name="reservation_gym_name" value="${gym_name}">
						<div class="row">
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">아이디</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_id" value="${MEMBER_ID}" name="reservation_member_id" readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">이름</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_name" value="${member_name}" name="member_name" readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">신청날짜</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_date" value="${reservation_date}" name="reservation_date" readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">가능인원</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_cnt" value="${reservation_cnt}명" name="reservation_cnt" readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">사용포인트</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_point" value="${reservation_use_point}" name="reservation_use_point" readonly/>
								</div>
							</div>
							<div class="col-md-2 d-flex align-items-center">
								<p class="mb-0">결제 금액</p>
							</div>
							<div class="col-md-10">
								<div class="form-group">
									<input type="text" class="form-control" id="member_money" value="${reservation_price}" name="reservation_price" readonly/>
								</div>
							</div>
						</div>
					</div>
					<div class="card-action text-center">
						<button type="button"
							class="btn btn-black px-5 mb-3 mb-sm-0 me-0 me-sm-4"
							onclick="window.location.href='main.do';">취소</button>
						<button type="button" class="btn btn-primary px-5" id="reservationbutton">예약</button>
					</div>

				</div>
			</div>
		</div>
	</div>

</body>
</html>