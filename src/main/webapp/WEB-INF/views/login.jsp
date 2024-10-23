<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 로그인 페이지</title>
<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>
<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />
<!-- Core JS Files -->
<script src="assets/js/core/jquery-3.7.1.min.js"></script>
<script src="assets/js/core/popper.min.js"></script>
<script src="assets/js/core/bootstrap.min.js"></script>
<!-- 카카오 로그인 API 스크립트 -->
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<!-- 네이버 로그인 API 스크립트 -->
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
	charset="utf-8"></script>
<!-- 구글 로그인 API 스크립트 -->
<script src="https://accounts.google.com/gsi/client" async defer></script>
</head>
<body>

	<!-- GNB 커스텀 태그 -->
	<mytag:gnb member_id="${MEMBER_ID}"></mytag:gnb>
	<!-- container start -->
	<div class="container">
		<!-- page-inner start -->
		<div
			class="page-inner w-100 vh-100 d-flex justify-content-center align-items-center">
			<!-- login card start -->
			<div class="card card-stats card-round p-3">
				<div class="card-body p-5">
					<h3 class="text-center">로그인</h3>
					<form id="loginForm" action="LOGINACTION.do" method="POST"
						class="p-3">
						<div class="row">
							<div
								class="col-md-3 d-flex justify-content-end align-items-center p-0">
								<h6 class="m-0">아이디</h6>
							</div>
							<div class="col-md-9 p-0">
								<div class="form-group">
									<input type="email" class="form-control" id="email"
										name="VIEW_EMAIL" required placeholder="아이디를 입력해주세요" />
								</div>
							</div>
						</div>
						<div class="row">
							<div
								class="col-md-3 d-flex justify-content-end align-items-center p-0">
								<h6 class="m-0">비밀번호</h6>
							</div>
							<div class="col-md-9 p-0">
								<div class="form-group">
									<input type="password" class="form-control" id="password"
										name="VIEW_PASSWORD" required placeholder="비밀번호를 입력해주세요" />
								</div>
							</div>
						</div>
						<!-- 자동로그인 input -->
						자동로그인 <input type="checkbox" class="ms-2" name="VIEW_AUTO_LOGIN"
							value="checkLogin">
						<!-- 구글 -->
						<div id="g_id_onload"
							data-client_id="814167133402-goror7cuh15hv87v3f2l0j9t84uckvlk.apps.googleusercontent.com"
							data-context="signup" data-ux_mode="popup"
							data-callback="handleCredentialResponse" data-auto_prompt="false">
						</div>

						<div class="g_id_signin" data-type="standard" data-shape="pill"
							data-theme="outline" data-text="signin_with" data-size="large"
							data-logo_alignment="left"></div>
						<!-- 네이버 로그인 버튼 -->
						<div class="row py-4">
							<div
								class="col-md-6 d-flex justify-content-center align-items-center">
								<!-- 네이버 로그인 버튼 노출 영역 -->
								<!-- 네이버 개발자 문서에서 가져옴 -->
								<div id="naver_id_login"></div>
							</div>
							<div
								class="col-md-6 d-flex justify-content-center align-items-center">
								<!-- 카카오 로그인 버튼 -->
								<!-- https://developers.kakao.com/tool/resource/login 에서 가져온 images 파일 -->
								<a id="kakao-login-btn" href="javascript:loginWithKakao()">
									<img src="images/kakao_login_medium.png" alt="카카오 로그인 버튼" />
								</a>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<button type="button" class="btn btn-secondary w-100"
									id="joinBtn">회원가입</button>
							</div>
						</div>
						<div class="row pt-3">
							<div class="col-12">
								<button type="submit" class="btn btn-primary w-100">로그인</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- login card end -->
		</div>
		<!-- page-inner end -->
	</div>
	<!-- container end -->

	<div id="user-info">
		<p id="user-email"></p>
	</div>



	<script type="text/javascript">
		// 네이버 로그인
		// https://developers.naver.com/docs/login/web/web.md
		// 에서 데이터를 가져왔다고 보면 됨

		var naver_id_login = new naver_id_login( // 네이버 로그인을 위한 객체 생성
		"kQSIom2rw1yt29HcbNc8", // 내 client ID: 네이버 개발자 센터에서 발급받은 클라이언트 ID
		"http://localhost:8088/COMA_PROJECT_CONTROLLER/login.jsp" // 내 callback url: 로그인 후 보여질 URL
		);

		// 네이버 로그인 객체를 생성하고, 고유한 상태 값을 생성
		// 이 값은 로그인 요청과 응답을 연결하기 위해 사용됨
		var state = naver_id_login.getUniqState();

		// 로그인 버튼의 디자인
		// "green"은 버튼 색상, 2는 버튼 모양 (모양은 네이버 개발자 문서 참조), 40은 버튼 크기
		// 네이버 개발자 문서 - https://developers.naver.com/docs/login/bi/bi.md
		naver_id_login.setButton("green", 2, 40);

		// 이 로그인 API를 사용할 페이지의 주소를 설정
		// 이 주소는 네이버 로그인 API에서 검증하는 페이지 주소
		naver_id_login
				.setDomain("http://localhost:8088/COMA_PROJECT_CONTROLLER/login.jsp");

		// 로그인 요청에 사용할 상태 값을 설정
		naver_id_login.setState(state);

		// 로그인 팝업을 사용할 수 있도록 설정
		// 사용자가 로그인 버튼을 클릭했을 때 팝업창이 열리게 됨
		//naver_id_login.setPopup();

		// 네이버 로그인 초기화: 설정한 값을 바탕으로 네이버 로그인 기능을 초기화
		// 이 메소드가 호출되어야 네이버 로그인 버튼이 제대로 작동됨
		naver_id_login.init_naver_id_login();

		//로그인 정보가 있다면 naverLoginCallback() 함수 호출
		naver_id_login.get_naver_userprofile("naverLoginCallback()");

		// 네이버 로그인 버튼 클릭 시 처리 함수
		function naverLoginCallback() {
			// 네이버 사용자 정보 가져오기
			var naverUserInfo = naver_id_login.getProfileData('email');
			if (naverUserInfo) {
				// 이메일 추출
				var email = naverUserInfo;
				// 이메일 정보 C에게 전송
				sendToController({
					email : email
				});
			}
		}

		// 네이버 로그인 상태 확인 및 사용자 정보 처리 함수
		function getNaverUserInfo() {
			// 네이버 로그인 상태라면
			if (naver_id_login.getLoginStatus()) {
				naverLoginCallback(); // 콜백함수 호출
			} else {
				// 로그인 상태가 아닐 경우 경고
				alert("로그인 상태가 아닙니다.");
			}
		}

		// 네이버 로그인 버튼 클릭 시 getNaverUserInfo 호출
		document.getElementById('naver_id_login').addEventListener('click',
				function() {
					getNaverUserInfo();
				});

		// 네이버 로그인 api 끝

		// 카카오 로그인 시작
		// 카카오 developers에서 발급받은 내 client ID
		Kakao.init('f68644c7e9866ef898677d5e1a260265');
		// 로그 (true/false로 콘솔창에 출력됨)
		console.log('Kakao SDK 초기화 여부:', Kakao.isInitialized());

		// 카카오 로그인 버튼 클릭 이벤트
		document.getElementById('kakao-login-btn').onclick = function() {
			Kakao.Auth.login({
				//로그인에 성공했다면
				success : function(authObj) {
					// 카카오 api에게 요청
					Kakao.API.request({
						// 사용자 정보 요청
						// kakao.auth.login(auth)을 request하면 유효시간이 존재하는 토큰을 줌
						// 그 url이 아래의 URL임
						url : '/v2/user/me',
						// 사용자 정보 요청을 성공했다면
						success : function(res) {
							// 사용자 정보에서 이메일을 추출함
							var email = res.kakao_account.email;
							
							Kakao.Auth.logout();
							
							// 추출한 이메일을 C에게 전송
							sendToController({
								email : email
							});
						},
						// 사용자 정보 요청을 실패했다면
						fail : function(error) {
							// 콘솔에 에러 로그 띄움
							console.error('카카오 로그인 사용자 정보 요청 실패:', error);
						}
					});
				},
				// 카카오 로그인에 실패했다면
				fail : function(error) {
					// 콘솔에 로그인 실패 로그 띄움
					console.error('카카오 로그인 실패:', error);
				}
			});
		};// 카카오 로그인 end

		//구글
		function handleCredentialResponse(response) {
			const id_token = response.credential;
			console.log("ID Token: " + id_token);
			const responsePayload = decodeJwtResponse(id_token);
			console.log('Email: ' + responsePayload.email);

			// 화면에 정보 표시
			document.getElementById('user-email').textContent = "Email: "
					+ responsePayload.email;

			sendToController({
				email : responsePayload.email
			});

		}
		//디코딩 함수
		function decodeJwtResponse(id_token) {
			console.log('decodeJwtResponse 호출');
			//받아온 토큰 값을 디코딩하여 정보를 가져옵니다.
			//id_token을 '.'으로 나누어 중간에 있는 payload 부분(base64Url)을 추출합니다.
			const base64Url = id_token.split('.')[1];
			//URL-safe Base64 형식에서 표준 Base64 형식으로 변환합니다. ('-' -> '+', '_' -> '/')
			const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
			//Base64로 인코딩된 문자열을 디코딩하고 각 문자의 유니코드 값을 %인코딩된 형식으로 변환한 후, 이를 다시 문자열로 조합하여 JSON 형식의 payload를 만듭니다.
			const jsonPayload = decodeURIComponent(atob(base64).split('').map(
					function(c) {
						//각 문자의 유니코드 값을 %XX 형식으로 변환합니다.
						return '%'
								+ ('00' + c.charCodeAt(0).toString(16))
										.slice(-2);
					}).join('')); //변환된 값을 하나의 문자열로 조합합니다.
			//최종적으로 JSON 타입으로 변환해 반환합니다.
			return JSON.parse(jsonPayload);
		}

		//구글end

		// C에게 사용자 정보 (이메일)전송하는 함수 (네이버, 카카오)
		function sendToController(userInfo) {
			$.ajax({
				// 서버 API URL
				url : 'loginAPI',
				// 요청방식
				method : 'POST',
				// C에게 전송할 데이터
				data : userInfo,
				// DB에 해당 이메일이 존재한다면
				success : function(response) {
					console.log('서버 응답: ', response);
					if (response == "true") {
						alert('로그인 성공!');
						// 로그인 후 이동하게 될 URL
						window.location.href = 'MAINPAGEACTION.do';
					} else {
						// DB에 해당 이메일이 존재하지 않는다면 회원가입 페이지로 이동
						alert('회원가입 페이지로 이동합니다.')
						window.location.href = 'JOINPAGEACTION.do?model_member_id='
								+ response;
					}

				},
				// 서버 응답 실패시
				error : function(error) {
					console.error('서버 응답: ', error);
					// 로그인 실패 알랏창 띄움
					alert('로그인에 실패했습니다. 다시 시도해 주세요.');
				}
			});
		}

		// 회원가입 버튼 onclick 이벤트
		document.getElementById('joinBtn').onclick = function() {
			// 클릭시에 회원가입 페이지 action 서버로 이동
			alert('회원가입 페이지로 이동합니다.');
			window.location.href = 'JOINPAGEACTION.do';
		};
	</script>

</body>
</html>
