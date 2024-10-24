<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 마이페이지</title>

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
			<div class="row justify-content-center">
				<div class="col-md-5">
					<div class="card card-stats card-round p-5 ">
						<div class="row">
							<div class="col-12 d-flex justify-content-center">
								<div class="avatar avatar-xxl">
									<img src="${MEMBERDATA.model_member_profile}"
										class="avatar-img rounded-circle" alt="profile image" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="info-user pt-4 pb-4">
									<h5>이름 : ${MEMBERDATA.model_member_name}</h5>
									<h5>전화번호 : ${MEMBERDATA.model_member_phone}</h5>
									<h5>이메일 : ${MEMBERDATA.model_member_id}</h5>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 text-center">
								<a href="CHANGEMEMBERPAGEACTION.do">
									<button class="btn btn-secondary">회원정보 변경</button>
								</a>
							</div>
							<div class="col-md-6 text-center">
								<button class="btn btn-danger" onclick="confirmDeleteMember()">회원
									탈퇴</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row justify-content-center ">
				<div class="col-md-12">
					<div class="card card-stats card-round pt-3 px-5 pb-5">
						<ul class="nav nav-tabs nav-line nav-color-secondary"
							id="line-tab" role="tablist">
							<li class="nav-item"><a class="nav-link active"
								id="line-post-tab" data-bs-toggle="pill" href="#line-post"
								role="tab" aria-controls="pills-post" aria-selected="true">
									내가 작성한 글 관리 </a></li>
							<c:if test="${MEMBERDATA.model_member_role == 'T'}">
								<li class="nav-item"><a class="nav-link" id="line-crew-tab"
									data-bs-toggle="pill" href="#line-crew" role="tab"
									aria-controls="pills-crew" aria-selected="false"> 신규 회원 관리
								</a></li>
							</c:if>
							<li class="nav-item"><a class="nav-link"
								id="line-reservation-tab" data-bs-toggle="pill"
								href="#line-reservation" role="tab"
								aria-controls="pills-reservation" aria-selected="true"> 예약
									관리 </a></li>
						</ul>
						<div class="tab-content mt-3 mb-3" id="line-tabContent">
							<div class="tab-pane fade show active" id="line-post"
								role="tabpanel" aria-labelledby="line-post-tab">
								<table class="w-100">
									<tbody>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<c:forEach var="board" items="${BOARD}">
											<tr>
												<td colspan=5><a
													href="BOARDONEPAGEACTION.do?model_board_num=${board.model_board_num}"
													class="text-muted"> ${board.model_board_title} </a></td>
												<td align="right">
													<button class="btn btn-primary me-3"
														onclick="location.href='BOARDUPDATEPAGEACTION.do?model_board_num=${board.model_board_num}'">수정</button>
													<button class="btn btn-danger"
														onclick="deleteBoard(${board.model_board_num})">삭제</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="tab-pane fade" id="line-crew" role="tabpanel"
								aria-labelledby="line-crew-tab">
								<div class="row">
									<div class="col-12 text-center">
										<h3>신규 회원 목록</h3>
										<c:forEach var="newmember" items="${MEMBER_LIST}">
											<p>${newmember.model_member_name}</p>
										</c:forEach>
									</div>
								</div>
							</div>
							<!-- 예약 관리 탭 -->
							<div class="tab-pane fade" id="line-reservation" role="tabpanel"
								aria-labelledby="line-reservation-tab">
								<table class="w-100">
									<tbody>
										<tr>
											<td>날짜</td>
											<td>암벽장 이름</td>
											<td align="left">가격</td>
											<td></td>
											<td></td>
										</tr>
										<c:forEach var="model_reservation_data"
											items="${model_reservation_datas}">
											<c:choose>
												<c:when test="${model_reservation_data.model_reservation_num >0}">
													<tr>
														<td>${model_reservation_data.model_reservation_date}</td>
														<td>
															${model_reservation_data.model_reservation_gym_name}</td>
														<td>${model_reservation_data.model_reservation_price}</td>
														<td></td>
														<td align="right">
															<button class="btn btn-danger"
																onclick="deleteReservation(${model_reservation_data.model_reservation_num})">예약
																취소</button>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr> <!-- 테이블 구조 보존 -->
														<td colspan="5" class="text-center"><h3>예약 목록이 없습니다...</h3></td>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tbody>
								</table>

							</div>
						</div>
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
		function confirmDeleteMember() {
	        if (confirm('정말 회원 탈퇴를 하시겠습니까?')) {
	            location.href = 'DELETEMEMBERACTION.do';
	        }
	        	    }
	
		function deleteBoard(boardNum){
			if(confirm('정말 삭제하시겠습니까?')){
				location.href='BOARDDELETEACTION.do?model_board_num='+boardNum;
			}
		}
		function deleteReservation(reservationNum) {
			if(confirm('정말 삭제하시겠습니까?')){
				location.href='DeleteReservation.do?model_reservation_num='+reservationNum;
			}
		}
	
	</script>
</body>

</html>