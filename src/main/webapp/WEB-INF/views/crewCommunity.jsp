<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>코마 : 크루 커뮤니티</title>

    <!-- jquery cdn -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <!-- sockjs 라이브러리 cdn -->
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>

    <!-- stomp 프로토콜 cdn -->
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@6.1.1/umd/stomp.min.js"></script>

    <!-- Fonts and icons -->
    <script src="https://kit.fontawesome.com/7f7b0ec58f.js" crossorigin="anonymous"></script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/kaiadmin.css"/>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Core JS Files -->
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>

    <style>
        .post-container {
            width: 800px;
            height: 500px;
            margin: 0 auto;
            background-color: #cccccc;
            border-radius: 20px;
            padding: 15px;
            overflow-y: auto;
            /*자동스크롤 기능*/
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
            bottom:15px;
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
        <div class="row post-container">
            <div class="col post-list" id="postList">
                <!--채팅 내역 동적 추가-->
            </div>
        </div>
        <div class="row justify-content-center mt-5">
            <div class="col-md-10">
                <div class="card card-stats card-round p-5">
                    <!-- 글 작성 form -->
                    <div class="row">
                        <div class="col-md-8 text-left">
                            <input type="text" class="form-control" id="text" name="text"
                                   required placeholder="메세지 입력" maxlength="200">
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
        const memberId = "${MEMBER_ID}"; // 로그인된 사용자 ID
        const memberProfile = "https://comapro.cdn1.cafe24.com${data.member_profile}"; //로그인 사용자 이미지
        const memberName = "${data.member_name}"; // 사용자 이름
        let socket = new WebSocket("ws://localhost:8089/chat/" + memberId);

        socket.onopen = function () {
            console.log("웹소켓 연결 성공");
        }

        socket.onclose = function () {
            console.log("웹소켓 연결 해제");
        }

        socket.onerror = function (error) {
            console.error('에러 내용 :', error);
            console.error('에러 상태 코드 :', error.status);
            console.error('에러 응답 텍스트 :', error.responseText);
        }

        // WebSocket 메시지 수신 핸들러
        socket.onmessage = function (e) {
            console.log("socket.onmessage = [" + e + "]");
            console.log("socket.onmessage type = [" + e.type + "]");
            console.log("socket.onmessage origin = [" + e.origin + "]");
            let data = e.data;
            let jsondata;
            try {
                // 서버로부터 받은 메시지를 콘솔에 출력
                console.log("data = [" + data + "]");
                console.log("data.type = [" + data.type + "]");
                // String으로 받은 값을 JSON 형식으로 파싱
                jsondata = JSON.parse(data);
                console.log("jsondata = [" + jsondata + "]");
                console.log("jsondata.type = [" + jsondata.type + "]");

                // 연결, 연결 해제일 때는 system 메시지, 채팅은 json 메시지
                if (jsondata.type === "system") {
                    displaySystemMessage(jsondata.content);
                } else {
                    // 일반 메시지인 경우 기존 처리 방식 사용
                    addPost(jsondata, jsondata.crew_board_writer_id === memberId);
                }
            } catch (error) {
                console.error("JSON 파싱 오류:", error);
                console.error("에러 데이터:", error.data);
                console.error('에러 상태 코드 :', error.status);
                console.error('에러 응답 텍스트 :', error.responseText);
            }
        }

        // WebSocket 메시지 전송 핸들러
        $("#sendBtn").click(function () {
            let content = $("#text").val();
            if (content) {
                let message = {
                    crew_board_writer_id: memberId, // 작성자 ID
                    crew_board_content: content,
                    crew_board_writer_profile: memberProfile, // 사용자 프로필 URL
                    crew_board_writer_name: memberName // 사용자 이름
                };
                socket.send(JSON.stringify(message));
                $("#text").val(""); // 입력 필드 초기화
            }
        });

        // Enter 키로 메시지 전송
        $("#text").keypress(function (e) {
            // Enter 키가 눌리면 메세지 입력 버튼 클릭
            if (e.which === 13) {
                $("#sendBtn").click();
                return false; // 기본 동작 방지
            }
        });

        // 메시지 동적 추가
        function addPost(data, isMyMessage) {
            let postHtml = "";
            postHtml += "<div class='row justify-content-" + (isMyMessage ? "end" : "start") + "'>";
            postHtml += "<div class='col-8 mb-2 post-item'>";
            postHtml += "<div class='row no-gutters align-items-center post-row'>";

            if (!isMyMessage) {
                postHtml += "<div class='col-auto text-center p-3 position-relative profile-image-col'>";
                postHtml += "<div class='avatar avatar-xl'>";
                postHtml += "<img src='" + data.crew_board_writer_profile + "' class='avatar-img rounded-circle' alt='프로필 사진'>";
                postHtml += "</div>";
                postHtml += "<div class='text-overlay'>" + data.crew_board_writer_name + "</div>";
                postHtml += "</div>";
            }

            postHtml += "<div class='col width-350'>";
            postHtml += "<div class='card-body message-content'" + (isMyMessage ? " style='background: yellow'" : "") + ">";
            postHtml += "<span class='w-100' style='word-wrap: break-word;'>";
            postHtml += data.crew_board_content;
            postHtml += "</span>";
            postHtml += "</div>";
            postHtml += "</div>";

            if (isMyMessage) {
                postHtml += "<div class='col-auto text-center p-3 position-relative profile-image-col'>";
                postHtml += "<div class='avatar avatar-xl'>";
                postHtml += "<img src='" + data.crew_board_writer_profile + "' class='avatar-img rounded-circle' alt='프로필 사진'>";
                postHtml += "</div>";
                postHtml += "<div class='text-overlay'>" + data.crew_board_writer_name + "</div>";
                postHtml += "</div>";
            }

            postHtml += "</div>";
            postHtml += "</div>";
            postHtml += "</div>";

            $("#postList").append(postHtml);
            $(".post-container").scrollTop($(".post-container")[0].scrollHeight);
        }

        function displaySystemMessage(message) {
            let systemMessageHtml = "<div>" + message + "</div>";
            $("#postList").append(systemMessageHtml);
            $(".post-container").scrollTop($(".post-container")[0].scrollHeight);
        }
    });
</script>

</body>
</html>