<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 게시글확인 </title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js"
	crossorigin="anonymous"></script>

<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />

<style type="text/css">

figure.image_resized img {
  width: 100%; /* figure의 크기를 유지 */
  height: auto; /* 이미지 비율 유지하기 위함 */
}
</style>
</head>
<body>
	<!-- GNB 커스텀 태그 -->
	<mytag:gnb member_id="${MEMBER_ID}" ></mytag:gnb>
	
	<!-- container start -->
	<div class="container">
		<div class="page-inner">
			<div class="row py-3">
				<div class="col-12">
					<h1 class="text-center">${BOARD.model_board_title}</h1>
				</div>
			</div>
			<div class="row border-bottom border-dark pb-3">
				<div class="col-md-1 d-flex justify-content-end align-items-center">
					<div class="avatar avatar-sm">
						<img src="${member_profile}" alt="profile"
							class="avatar-img rounded-circle" />
					</div>
				</div>
				<div class="col-md-11 d-flex align-items-center">
					<p class="mb-0">작성자: ${BOARD.model_board_writer_id}</p>
				</div>
			</div>
			<div class="row py-5">
				<div class="col-12 d-flex justify-content-center">
					<div class="w-75">
						<p class="text-start">${BOARD.model_board_content}</p>
					</div>
				</div>
			</div>
			<div class="row border-top border-dark py-3">
				<form action="REPLYACTION.do" method="POST">
					<input type="hidden" name="board_id" value="${BOARD.model_board_num}" />
					<div class="row">
						<div class="col-11">
							<div class="form-group">
								<input name="reply_content" type="text" class="form-control"
									id="comment" placeholder="댓글를 입력해주세요" />
							</div>
						</div>
						<div class="col-1 d-flex align-items-center">
							<button type="submit" class="btn btn-secondary">댓글</button>
						</div>
					</div>
				</form>
			</div>
			<c:forEach var="reply" items="${REPLY}">
				<c:choose>
					<c:when test="${not empty REPLY}">
						<div class="row border-top border-bottom py-3 px-5 comment-item">
			               <div class="col-md-2">
			                  <p>작성자: ${reply.model_reply_writer_id}</p>
			               </div>
			               <div class="col-md-9">
			                  <form action="REPLYUPDATEACTION.do" method="POST">
			                     <input type="hidden" name="board_id" value="${BOARD.model_board_num}" />
			                     <input type="hidden" name="reply_id" value="${reply.model_reply_num}" />
			                     <p class="comment-text">${reply.model_reply_content}</p>
			                     <div class="edit-form d-none">
			                        <div class="row">
			                           <div class="col-8">
			                              <input type="text" class="form-control comment-edit" name="reply_content"/>
			                           </div>
			                           <div class="col-4">
			                              <a href="REPLYUPDATE.do">
			                                 <button type="submit" class="btn btn-primary save-edit">변경완료</button>
			                              </a>
			                           </div>
			                        </div>
			                     </div>
			                  </form>
			               </div>
			               <div class="col-1">
			                  <c:if test="${MEMBER_ID eq reply.model_reply_writer_id}">
			                     <div class="dropdown">
			                        <button class="btn btn-icon btn-clean me-0" type="button"
			                           id="dropdownMenuButton" data-bs-toggle="dropdown"
			                           aria-haspopup="true" aria-expanded="false">
			                           <i class="fas fa-ellipsis-h"></i>
			                        </button>
			                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
			                           <a class="dropdown-item" id="edit-comment">수정</a>
			                           <a class="dropdown-item" href="REPLYDELETEACTION.do?replyId=${reply.model_reply_num}&board_num=${BOARD.model_board_num}">삭제</a>
			                        </div>
			                     </div>
			                  </c:if>
			               </div>
			            </div>
					</c:when>
				</c:choose>
			</c:forEach>

	<!--   Core JS Files   -->
	<script src="assets/js/core/jquery-3.7.1.min.js"></script>
	<script src="assets/js/core/popper.min.js"></script>
	<script src="assets/js/core/bootstrap.min.js"></script>
	
	<script>
	// DOMContentLoaded 이벤트가 발생하면 콜백 함수를 실행
	// 즉, DOM이 완전히 로드된 후에 이 코드가 실행
	document.addEventListener('DOMContentLoaded', () => {

	    // .comment-item 클래스를 가진 모든 요소를 선택하여 comments라는 변수에 저장
	    const comments = document.querySelectorAll('.comment-item');

	    // 각 댓글(comment-item)에 대해 반복 작업을 수행합니다.
	    comments.forEach(comment => {
	    	try {
	        // 현재 댓글의 편집 버튼(.edit-comment)을 선택하여 editButton 변수에 저장
	        const editButton = comment.querySelector('#edit-comment');
	        
	        // 현재 댓글의 편집 입력란(.comment-edit)을 선택하여 commentEdit 변수에 저장
	        const commentEdit = comment.querySelector('.comment-edit');

	        // 현재 댓글의 텍스트 영역(.comment-text)을 선택하여 commentText 변수에 저장
	        const commentText = comment.querySelector('.comment-text');
            
	        // 현재 댓글의 저장 버튼(.save-edit)을 선택하여 saveEditButton 변수에 저장
	        const saveEditButton = comment.querySelector('.save-edit');

	        // editButton 클릭 시 실행될 이벤트 리스너를 추가
	        editButton.addEventListener('click', (event) => {
	            // 기본 클릭 동작을 막습니다
	            event.preventDefault();

	            // commentText 요소에 'd-none' 클래스를 추가하여 댓글 텍스트를 숨기기
	            commentText.classList.add('d-none');

	            // 클릭된 버튼의 부모 요소(comment)를 기준으로 하위 요소들에 접근
	            const currentComment = event.currentTarget.closest('.comment-item');

	            // currentComment 내의 commentText에 'd-none' 클래스를 추가하여 댓글 텍스트를 숨기기
	            currentComment.querySelector('.comment-text').classList.add('d-none');

	            // currentComment 내의 editForm에서 'd-none' 클래스를 제거하여 편집 폼을 표시
	            currentComment.querySelector('.edit-form').classList.remove('d-none');


	            // commentEdit 입력란에 현재 댓글의 텍스트 내용을 설정
	            commentEdit.value = commentText.textContent.trim();
	        });

	        // saveEditButton 클릭 시 실행될 이벤트 리스너를 추가
	        saveEditButton.addEventListener('click', () => {
	            // commentEdit 입력란의 값을 commentText 요소에 설정하여 변경된 댓글을 표시
	            commentText.textContent = commentEdit.value;

	            // editForm 요소에 'd-none' 클래스를 추가하여 편집 폼을 숨기기
	            editForm.classList.add('d-none');

	            // commentText 요소에서 'd-none' 클래스를 제거하여 댓글 텍스트를 다시 표시
	            commentText.classList.remove('d-none');
	        });
	    	} catch (error) {
	    	        
	    	}
	    });
	});
	</script>
</body>
</html>