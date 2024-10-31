<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>코마 : 크루 커뮤니티</title>

	<!--jquery cdn-->
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	<!--sockjs라이브러리 cdn-->
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<!--stomp프로토콜 cdn-->
	<script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@6.1.1/umd/stomp.min.js"></script>

	<!-- Fonts and icons -->
	<script src="assets/js/plugin/webfont/webfont.min.js"></script>
	<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
			crossorigin="anonymous"></script>

	<!-- CSS Files -->
	<link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="assets/css/plugins.min.css"/>
	<link rel="stylesheet" href="assets/css/kaiadmin.css"/>

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>

	<style>
		.post-container {
			width: 800px;
			margin: 0 auto;
			background-color: #a5a5a5;
			border-radius: 20px;
			padding: 15px;
			/*overflow-y: auto; 자동스크롤 기능*/
		}

		.post-item {
			margin-bottom: 0.5rem;
		}

		.post-row {
			display: flex;
			align-items: center;
		}

		.profile-image-col {
			position: relative;
		}

		.avatar-img {
			width: 50px;
			height: 50px;
		}

		.text-overlay {
			position: absolute;
			top: 5px;
			left: 0;
			width: 100%;
			text-align: center;
			color: white;
			font-size: 12px;
			text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
		}

		.message-content {
			background-color: #ffffff;
			border-radius: 10px;
			padding-top: 0.5rem;
			padding-bottom: 0.5rem;
			padding-left: 1rem;
			padding-right: 1rem;
			width: auto;
		}

		.card-text {
			white-space: pre-wrap;
			word-wrap: break-word;
		}
	</style>
</head>

<body style="background: #F5F7FD !important;">
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
						<p class="fs-4 m-0">내 크루</p>
					</a>
					<h3 class="px-5 m-0">/</h3>
					<a href="crewCommunity.do"
					   class="text-dark text-decoration-underline  link-primary">
						<h3 class="m-0">
							<b>커뮤니티</b>
						</h3>
					</a>
					<h3 class="px-5 m-0">/</h3>
					<a href="crewBattle.do"
					   class="text-dark text-decoration-none link-primary">
						<p class="fs-4 m-0">크루전 개최</p>
					</a>
					<h3 class="px-5 m-0">/</h3>
					<a href="crewList.do"
					   class="text-dark text-decoration-none link-primary">
						<p class="fs-4 m-0">크루 가입</p>
					</a>
				</div>
			</div>
		</div>
		<%--추가될 데이터 시작--%>
		<div class="row">
			<div class="col post-list" id="postList">
				<c:forEach var="data" items="${datas}">
					<c:choose>
						<c:when test="${data.crew_board_writer_id == sessionScope.MEMBER_ID}">
							<div class="row justify-content-end">
								<div class="col-8 mb-2 post-item">
									<div class="row no-gutters align-items-center post-row">
										<!-- 메시지 내용 -->
										<div class="col width-350">
											<div class="card-body message-content" style="background: yellow">
													<%--채팅 내용--%>
												<span class="w-100" style="word-wrap: break-word;">
														${data.crew_board_content}
												</span>
											</div>
										</div>
										<!-- 사용자 프로필 이미지 -->
										<div class="coltext-center p-3 position-relative profile-image-col">
											<div class="avatar avatar-xl">
												<img src="${data.crew_board_writer_profile}"
													 class="avatar-img rounded-circle" alt="작성자 사진">
											</div>
											<div class="text-overlay">
													${data.crew_board_writer_name}
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="row justify-content-start">
								<div class="col-8 mb-2 post-item">
									<div class="row no-gutters align-items-center post-row">
										<!-- 사용자 프로필 이미지 -->
										<div class="col-auto text-center p-3 position-relative profile-image-col">
											<div class="avatar avatar-xl">
												<img src="${data.crew_board_writer_profile}"
													 class="avatar-img rounded-circle" alt="작성자 사진">
											</div>
											<div class="text-overlay">
													${data.crew_board_writer_name}
											</div>
										</div>
										<!-- 메시지 내용 -->
										<div class="col width-350">
											<div class="card-body message-content">
													<%--채팅 내용--%>
												<span class="w-100" style="word-wrap: break-word;">
														${data.crew_board_content}
												</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
		<%--끝--%>
		<div class="row justify-content-center mt-5">
			<div class="col-md-10">
				<div class="card card-stats card-round p-5">
					<!-- 글 작성 form -->
					<div class="row">
						<div class="col-md-8 text-left">
							<input type="text" class="form-control" id="text" name="text"
								   required placeholder="메세지 입력" maxlength="20">
						</div>
						<div class="col-md-2">
							<!-- 크루 커뮤니티 글 작성 submit -->
							<button class="btn btn-primary me-3 w-100" type="button" id="sendBtn">글 작성</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- container end -->
	</div>
</div>
<script>
	$(document).ready(function () {
		let socket = new SockJS('http://localhost:8089/chat');
		let stompClient = Stomp.over(socket);

		let crewNum = "<%= session.getAttribute("CREW_CHECK") %>" || "default";
		let memberId = "<%= session.getAttribute("MEMBER_ID") %>";

		stompClient.connect({}, function (frame) {
			console.log('stomp 연결= ['+frame+']');
			stompClient.subscribe('/topic/crew/' + crewNum, function (messageOutput) {
				let message = JSON.parse(messageOutput.body);
				displayMessage(message);
			});
		});

		// 다른 .do 요청으로 이동 시 연결 해제
		$(window).on('beforeunload', function () {
			if (stompClient !== null) {
				stompClient.disconnect();
				console.log("웹소켓 연결 해제");
			}
		});

		$("#sendBtn").click(function () {
			let content = $("#text").val();
			if (content === '') {
				alert("메세지를 입력해주세요!");
				return;
			}

			let chatMessage = {
				crew_board_writer_id: memberId,
				crew_board_content: content
			};

			stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
			$("#text").val("");
		});

		$("#text").keypress(function (e) {
			if (e.which === 13) {
				$("#sendBtn").click();
				return false;
			}
		});

		function displayMessage(message) {
			let newMessage = $("<div></div>", {
				"class": "row " + (message.crew_board_writer_id === memberId ? "justify-content-end" : "justify-content-start")
			}).append(
					$("<div></div>", {
						"class": "col-8 mb-2 post-item"
					}).append(
							$("<div></div>", {
								"class": "row no-gutters align-items-center post-row"
							}).append(
									...(message.crew_board_writer_id !== memberId ? [
										$("<div></div>", {
											"class": "col-auto text-center p-3 position-relative profile-image-col"
										}).append(
												$("<div></div>", {
													"class": "avatar avatar-xl"
												}).append(
														$("<img>", {
															"src": message.crew_board_writer_profile,
															"class": "avatar-img rounded-circle",
															"alt": "작성자 사진"
														})
												),
												$("<div></div>", {
													"class": "text-overlay"
												}).text(message.crew_board_writer_name)
										)
									] : []),
									$("<div></div>", {
										"class": "col width-350"
									}).append(
											$("<div></div>", {
												"class": "card-body message-content",
												"style": "background: " + (message.crew_board_writer_id === memberId ? "yellow" : "white")
											}).append(
													$("<span></span>", {
														"class": "w-100",
														"style": "word-wrap: break-word;"
													}).text(message.crew_board_content)
											)
									),
									...(message.crew_board_writer_id === memberId ? [
										$("<div></div>", {
											"class": "col-auto text-center p-3 position-relative profile-image-col"
										}).append(
												$("<div></div>", {
													"class": "avatar avatar-xl"
												}).append(
														$("<img>", {
															"src": message.crew_board_writer_profile,
															"class": "avatar-img rounded-circle",
															"alt": "작성자 사진"
														})
												),
												$("<div></div>", {
													"class": "text-overlay"
												}).text(message.crew_board_writer_name)
										)
									] : [])
							)
					)
			);

			$("#postList").append(newMessage);
			$("#postList").scrollTop($("#postList")[0].scrollHeight);
		}
	});
</script>

</body>

</html>