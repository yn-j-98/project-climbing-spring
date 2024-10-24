<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 크루 커뮤니티</title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!--   Core JS Files   -->
		<script src="assets/js/core/jquery-3.7.1.min.js"></script>
		<script src="assets/js/core/popper.min.js"></script>
		<script src="assets/js/core/bootstrap.min.js"></script>
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
							<p class="fs-4 m-0">내크루</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="crewCommunity.do"
						   class="text-dark text-decoration-underline link-primary">
							<h3 class="m-0">커뮤니티</h3>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="CrewBattlePage.do"
						   class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">
								<b>크루전 개최</b>
							</p>
						</a>
						<h3 class="px-5 m-0">/</h3>
						<a href="crewList.do"
						   class="text-dark text-decoration-none link-primary">
							<p class="fs-4 m-0">크루 가입</p>
						</a>
					</div>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-10">
					<div class="card card-stats card-round p-5">
						<!-- 글 작성 form -->
						<form id="postForm" method="POST">
					        <div class="row">
					            <div class="col-md-2 d-flex align-items-center justify-content-center justify-content-md-start">
					                <h6>글 제목</h6>
					            </div>
					            <div class="col-md-8 text-left">
					                <input type="text" class="form-control" id="title" name="crew_board_title"
					                    required placeholder="글의 제목을 입력해주세요 ( 제한 : 100자 )" maxlength="100">
					            </div>
					            <div class="col-md-2">
					                <!-- 크루 커뮤니티 글 작성 submit -->
					                <button class="btn btn-primary me-3 w-100" type="submit">글 작성</button>
					            </div>
					        </div>
					        <br> <br>
					        <div class="row">
					            <div class="col-md-2 d-flex justify-content-center justify-content-md-start">
					                <h6>글 내용</h6>
					            </div>
					            <div class="col-md-10">
					                <div class="form-group p-0 mb-0">
					                    <div class="input-group">
					                        <!-- 내용 바이트 제한 : 1000자 -->
					                        <textarea id="content" class="form-control" name="crew_board_content"
					                            required style="height: 200px !important;" maxlength="60"
					                            placeholder="글의 내용을 입력해주세요 ( 제한 : 60자 )"></textarea>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </form>
				    </div>
					<!-- 글 목록을 표시할 곳 -->
					<% int cnt = 1; %>
					<div id="postList" class="row justify-content-center">
					    <c:forEach var="crew_board_data" items="${crew_board_datas}">
					        <script>
					            // JSON 형식 로그
					            console.log("110 crew_board_data", {
					                index: <%=cnt%>,  // 현재 인덱스
					                writer: "${crew_board_data.crew_board_writer_id}",
					                title: "${crew_board_data.crew_board_title}",
					                content: "${crew_board_data.crew_board_content}"
					            });
					        </script>
					        <div class="col-12 mb-2">
					            <div class="card card-stats card-round pt-3 px-5 pb-5">
					                <div class="row">
					                    <div class="col-md-3 text-center">
					                       	<div class="avatar avatar-xl">
					                       		<img src="${crew_board_data.crew_board_member_profile}" class="avatar-img rounded-circle form-group" alt="작성자 사진">					                       		
					                       	</div>
					                       	<p>${crew_board_data.crew_board_writer_id}</p>
					                  	</div>
					                  	<div class="col-md-9">
					                  		<div class="row">
							                  	<div class="col-md-2 d-flex align-items-center">
							                        <h6>글 제목</h6>
							                    </div>
							                    <div class="col-md-9 d-flex align-items-center">
							                        <b>${crew_board_data.crew_board_title}</b>
							                    </div>
					                  		</div>
					                  		<div class="row">
							                  	<div class="col-md-2 d-flex align-items-center">
							                        <h6>글 내용</h6>
							                    </div>
							                    <div class="col-md-9 d-flex align-items-center">${crew_board_data.crew_board_content}</div>
					                  		</div>
					                  	</div>
					                </div>
					            </div>
					        </div>
					        <% cnt++; %>
					    </c:forEach>
					</div>
				</div>
			</div>
			<!-- 페이지네이션 -->
			<div id="pageNation" class="row justify-content-center">
				<div class="row pt-5">
					<div class="col-md-10 d-flex justify-content-center">
						<nav aria-label="Page navigation">
							<input type="hidden" id="totalCount" value="${total}">
							<input type="hidden" id="currentPage" value="${page}">
							<ul id="pagination" class="pagination justify-content-center">
							
							</ul>
						</nav>
					</div>
				</div>
			</div>
			<!-- container end -->
		</div>
		<script type="text/javascript">
		// 크루 게시판 작성부분(비동기 처리)
	    $('#postForm').on('submit', function(event) {
	        event.preventDefault(); // 기본 폼 제출 동작 방지
	        
		 	// textarea의 내용을 가져와 줄바꿈을 <br>로 변환
		    const content = $('#content').val().replace(/\n/g, '<br>');
	
		    // 변환된 내용을 다시 textarea에 설정
		    $('#content').val(content);

	        // 비동기 요청
	        $.ajax({
	            url: 'crewBoardInsert', // 게시글 추가 및 목록 가져오기 위한 URL
	            type: 'POST', // HTTP 요청 방식: POST
	            // JSON 문자열로 변환하여 전송
	            data: $(this).serialize(), // 기본 폼 데이터 전송
	            success: function(insertResponse) {
	            	console.log("165 insertResponse ",insertResponse)
	                // 요청 성공 시 처리
	                if (insertResponse.status === 'success') {
	                    // 성공 메시지 표시
	                    alert(insertResponse.message); 
	                    
	        	        console.log("비우기 전 게시글 리스트 내용 = ", $('#postList').html());
	        	        // 게시글 리스트 비우기
	        	        $('#postList').empty(); 
	        	        console.log("비우기 후 게시글 리스트 내용 = ", $('#postList').html());

	                    // 게시글 목록을 업데이트
	                    updatePostList(insertResponse.crew_board_datas); // 서버에서 받은 게시글 목록 사용
	                } else {
	                    alert(insertResponse.message); // 실패 메시지 표시
	                }
	            },
	            error: function(xhr, status, error) {
	            	console.log("에러 = ",error)
	                alert('에러 ' + error); // 에러 메시지 표시
	            }
	        });
	    });

	    // 게시글 목록을 업데이트하는 함수
	    function updatePostList(crew_board_datas) {
	        var postsHtml = ''; // 게시글 목록을 담을 변수 초기화
	        console.log("게시글 목록 = ", crew_board_datas);
			
	        // 응답에서 게시글 데이터를 처리
	        $.each(crew_board_datas, function(index, post) {
	        	//crew_board_datas 배열의 반복문
	        	//function(index, post)
	        	//index : 0부터 시작하여 배열의 길이-1까지 증가
	        	//post : 현재 반복에서 처리할 데이터 변수명
	        	//foreach문과 같은 구조 + 기본 for문의 index처리 가능
	            var profile = post.crew_board_member_profile; // 프로필 URL
	            console.log("216 updatePostList profile",profile);
	            
	            // 작성자 ID, HTML로 변환
	            var writer = $('<div>').text(post.crew_board_writer_id).html(); 
	            console.log("220 updatePostList writer",writer);
	            
	            // 제목, HTML로 변환
	            var title = $('<div>').text(post.crew_board_title).html(); 
	            console.log("224 updatePostList title",title);
	            
	            // 내용, HTML로 변환
	            var content = post.crew_board_content.replace(/&lt;br&gt;/g, '<br>').replace(/\n/g, '<br>');
	            console.log("228 변환된 updatePostList content",content);
	            
	            // 각 게시글의 HTML을 동적으로 생성하여 `postsHtml`에 추가
	            postsHtml += '<div class="col-12 mb-2">' +
	            '<div class="card card-stats card-round pt-3 px-5 pb-5">' +
	                '<div class="row">' +
	                    '<div class="col-md-3 text-center">' +
	                        '<div class="avatar avatar-xl">' +
	                            '<img src="' + profile + '" class="avatar-img rounded-circle form-group" alt="작성자 사진">' +
	                        '</div>' +
	                        '<p>' + writer + '</p>' +
	                    '</div>' +
	                    '<div class="col-md-9">' +
	                        '<div class="row">' +
	                            '<div class="col-md-2 d-flex align-items-center">' +
	                                '<h6>글 제목</h6>' +
	                            '</div>' +
	                            '<div class="col-md-9 d-flex align-items-center">' +
	                                '<b>' + title + '</b>' +
	                            '</div>' +
	                        '</div>' +
	                        '<div class="row">' +
	                            '<div class="col-md-2 d-flex align-items-center">' +
	                                '<h6>글 내용</h6>' +
	                            '</div>' +
	                            '<div class="col-md-9 d-flex align-items-center">' +
	                                content +
	                            '</div>' +
	                        '</div>' +
	                    '</div>' +
	                '</div>' +
	            '</div>' +
	        '</div>';
	        });
	        // 동적으로 생성된 게시글 목록을 HTML에 삽입
	        $('#postList').html(postsHtml);
	    }

		$(document).ready(function () {
			renderpagination();
		})
		// 페이지네이션 생성 함수
		function renderpagination() {
			var url = window.location.pathname;
			console.log("pagenation.js url :"+url)
			var _totalCount = parseInt($("#totalCount").val());  // 서버에서 전달된 전체 게시물 개수
			//NaN / null 유효성 검사
			if (isNaN(_totalCount) || _totalCount === 0 || _totalCount === null) {
				_totalCount = 0;
			}

			var currentPage = parseInt($("#currentPage").val()); // 서버에서 전달된 현재 페이지 번호
			//NaN / null 유효성 검사
			if (isNaN(currentPage) || currentPage === 0 || currentPage === null) {
				currentPage = 1;
			}

			console.log("pagenation.js _totalCount =["+_totalCount+"]");
			console.log("pagenation.js currentPage =["+currentPage+"]");

			$("#pagination").empty();  // 기존 페이지네이션을 지우기

			// 현재 게시물의 전체 개수가 10개 이하이면 pagination을 숨깁니다.
			if (_totalCount <= 10) return;

			// 총 페이지 수 계산 (전체 게시물 수를 한 페이지에 보여줄 게시물 수로 나눈 값의 올림)
			const totalPage = Math.ceil(_totalCount / 10);
			console.log("pagenation.js totalPage =["+totalPage+"]");

			// 현재 페이지 그룹 계산 (현재 페이지를 10으로 나눈 값의 올림)
			const pageGroup = Math.ceil(currentPage / 10);
			console.log("pagenation.js pageGroup =["+pageGroup+"]");

			// 현재 페이지 그룹에서의 마지막 페이지 계산
			let last = pageGroup * 10;
			console.log("pagenation.js last =["+last+"]");

			// 마지막 페이지가 총 페이지 수를 초과하지 않도록 조정
			if (last > totalPage) last = totalPage;

			// 현재 페이지 그룹에서의 첫 번째 페이지 계산
			const first = last - (10 - 1) <= 0 ? 1 : last - (10 - 1);
			console.log("pagenation.js first =["+first+"]");

			// 다음 그룹의 첫 페이지 계산
			const next = last + 1;
			console.log("pagenation.js next =["+next+"]");

			// 이전 그룹의 마지막 페이지 계산
			const prev = first - 1;
			console.log("pagenation.js prev =["+prev+"]");

			// 페이지네이션 버튼을 담을 비어있는 DocumentFragment 객체 생성
			const fragmentPage = document.createDocumentFragment();

			// 이전 그룹으로 이동하는 버튼 생성 (prev가 0보다 크다면 생성)
			if (prev > 0) {
				const preli = document.createElement('li');
				preli.className = 'page-item';

				// 쿼리 파라미터 없이 링크 생성
				preli.insertAdjacentHTML("beforeend",
						"<a class='page-link' href='"+url+"?page="+prev+"' aria-label='Previous' data-page='" + prev + "'>" +
						"<span aria-hidden='true'>&laquo;</span>" +
						"</a>"
				);

				fragmentPage.appendChild(preli); // fragment에 추가
			}

			// 현재 페이지 그룹의 페이지 번호 버튼 생성
			for (let i = first; i <= last; i++) {
				const li = document.createElement("li");
				li.className = 'page-item';

				let linkHTML = "<a class='page-link m-2' href='"+url+"?page="+i+"' data-page='" + i + "'>" + i + "</a>";
				if (i === currentPage) {
					linkHTML = "<a class='page-link m-2 active' href='"+url+"?page="+i+"' data-page='" + i + "'>" + i + "</a>";
				}

				li.insertAdjacentHTML("beforeend", linkHTML);
				fragmentPage.appendChild(li); // fragment에 추가
			}

			// 다음 그룹으로 이동하는 버튼 생성 (last가 totalPage보다 작다면 생성)
			if (last < totalPage) {
				const endli = document.createElement('li');
				endli.className = 'page-item';

				// 쿼리 파라미터 없이 링크 생성
				endli.insertAdjacentHTML("beforeend",
						"<a class='page-link' href='"+url+"?page="+next+"' aria-label='Next' data-page='" + next + "'>" +
						"<span aria-hidden='true'>&raquo;</span>" +
						"</a>"
				);

				fragmentPage.appendChild(endli); // fragment에 추가
			}

			// 생성된 페이지네이션 버튼들을 화면에 추가
			document.getElementById('pagination').appendChild(fragmentPage);
		}

		// DOM이 완전히 로드된 후 페이지네이션을 생성
		//     renderpagination(currentPage, _totalCount);

		// 페이지 버튼 클릭 이벤트 처리
		$("#pagination").on("click", "a", function (e) {
			// 기본 동작(페이지 이동) 방지
			e.preventDefault();
			// 클릭된 페이지 링크 요소를 jQuery 객체로 저장
			const $item = $(this);
			// 클릭된 링크의 'data-page' 값(페이지 번호)을 가져옴
			const selectedPage = parseInt($item.attr("data-page"));

			// 페이지네이션 재생성 및 해당 페이지 데이터 로드
			if (selectedPage && selectedPage !== currentPage) {
				currentPage = selectedPage;
				renderpagination(currentPage, _totalCount);
				// 페이지 변경 시 필요한 추가 데이터 로드 로직이 있다면 여기에 추가
			}
		});
	</script>
</body>
</html>