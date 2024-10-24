<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코마 : 글쓰기</title>

<!-- Fonts and icons -->
<script src="assets/js/plugin/webfont/webfont.min.js"></script>
<script src="https://kit.fontawesome.com/7f7b0ec58f.js" crossorigin="anonymous"></script>
<!-- CSS Files -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/plugins.min.css" />
<link rel="stylesheet" href="assets/css/kaiadmin.css" />

<!-- CKEditor cdn -->
<script src="https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.js"></script>
<!-- CKEditor css -->
<link rel="stylesheet" href="https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.css">
<!-- CKEditor Js Import -->
<script type="importmap">
{
	"imports": {
		"CKEditor": "https://cdn.ckeditor.com/ckeditor5/43.1.0/ckeditor5.js",
		"CKEditor/": "https://cdn.ckeditor.com/ckeditor5/43.1.0/",
		"editorConfig": "./js/CKEditorConfig.js"
	}
}
</script>
<script type="module" src="./js/CKEditor.js"></script>

<style type="text/css">
.ck.ck-editor{
	width: 100%;
	max-width: 1500px;
}
.ck-editor__editable {
	min-height: 350px;
}
.ck-content{
	height: 350px !important;
	
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
                    <h1 class="text-center">글쓰기</h1>
                </div>
            </div>
            <!-- Form for Posting -->
            <form action="BOARDINSERTACTION.do" method="POST" name="editing">
                <div class="row">
                    <div class="col-md-1 d-flex align-items-center justify-content-center justify-content-md-end">
                        <h3>제목</h3>
                    </div>
                    <div class="col-md-11">
                        <div class="form-group">
                        <!-- 제목 바이트 제한 : 100자 -->
                            <input type="text" class="form-control" id="title" name="VIEW_TITLE" required placeholder="글의 제목을 입력해주세요 ( 제한 : 100자 )" maxlength="100"/>
                            <!-- 바이트 제한을 넘어서면 작게 안내문구 보이도록 설정 -->
                            <div id="titleError" class="byte-error">제목은 100자를 넘을 수 없습니다.</div>
                        </div>
                    </div>
                </div>
                <div class="row">
					<div class="col-md-1 d-flex justify-content-center justify-content-md-end">
								<h3>지역</h3>
					</div>
					<div class="col-md-11">
						<div class="form-group">
							<select class="form-control form-select" id="country" name="board_location">
								<option value="" class="text-muted">지역</option>
								<option value="SEOUL">서울특별시</option>
								<option value="SEJONG">세종특별자치도</option>
								<option value="BUSAN">부산광역시</option>
								<option value="DAEGU">대구광역시</option>
								<option value="DAEJEON">대전광역시</option>
								<option value="INCHEON" >인천광역시</option>
								<option value="GWANGJU">광주광역시</option>
								<option value="ULSAN">울산광역시</option>
								<option value="GYEONGGI">경기도</option>
								<option value="CHUNGCHEONGNAMDO">충청남도</option>
								<option value="CHUNGCHEONGBUKDO">충청북도</option>
								<option value="JEONLANAMDO">전라남도</option>
								<option value="JEONLABUKDO">전라북도</option>
								<option value="GYEONGSANGNAMDO">경상남도</option>
								<option value="GYEONGSANGBUKDO">경상북도</option>
								<option value="GANGWONDO">강원도</option>
							</select>
						</div>
					</div>
				</div>
                <div class="row">
                    <div class="col-md-1 d-flex justify-content-center justify-content-md-end">
                        <h3>내용</h3>
                    </div>
                    <div class="col-md-11">
                        <div class="form-group">
                            <div class="input-group">
                                <textarea id="content" class="form-control" name="VIEW_CONTENT">
                                	<!-- 수정할 게시글을 불러올 영역 -->
                                </textarea>
                            </div>
                            <hr>
                            <div class="text-group" style="text-align:right; height: 40px !important;">
	                           현재 이미지 개수 : <span class="img-length">0장</span> <br>
	                           현재 글자 수 : <span class="text-length">0자</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row pt-5">
                    <div class="col-12 d-flex justify-content-center">
                        <button type="button" class="btn btn-black me-4" onclick="cancelEditing()">취소</button>
                        <button type="submit" class="btn btn-primary" onclick="return validateForm()">작성</button>
                    </div>
                </div>
                <!-- End of Form -->
            </form>
        </div>
    </div>

    <script type="text/javascript">

    // 바이트 계산 함수
    function getByteLength(str) {
       // TextEncoder를 사용하여 문자열의 바이트 길이 계산
        return new TextEncoder().encode(str).length; // 바이트 길이 반환
    }

    // 제목과 내용의 입력 체크 함수
    function updateByteCount() {
       // 제목과 내용 입력 필드 값 가져오기
        const title = document.getElementById('title').value;

        // 가져온 입력 값들의 바이트 길이 계산
        const titleByteCount = getByteLength(title);

        // 오류 메시지를 표시할 요소를 가져옴
        const titleError = document.getElementById('titleError');

        // 제목 바이트가 100을 넘었다면
        if (titleByteCount > 100) {
           // 바이트 초과 오류 메시지 표시
            titleError.style.display = 'block';
           // 초과된 문자를 제거하여 제목의 길이를 100 이하로 강제로 설정
            document.getElementById('title').value = title.slice(0, 100); 
        } else {
           // 기본값 : none, 오류메시지 숨김
            titleError.style.display = 'none';
        }
    }

 // 제목 입력 필드와 내용 입력 필드에 대한 'input' 이벤트 리스너
    // 실시간 바이트 체크
    document.getElementById('title').addEventListener('input', updateByteCount);

    // 폼 검증 함수
    function validateForm() {
       // 폼 제출 전에 바이트 수를 업데이트하여 현재 상태를 반영
        updateByteCount(); 
     // 제목과 내용의 오류 메시지 표시 상태를 확인하여 폼 제출을 허용할지 결정
        const titleError = document.getElementById('titleError').style.display === 'block';
     
        if(titleError){ // 제목과 길이 제한을 초과한 채로 제출한다면
           alert('제목과 내용의 길이 제한을 다시 확인해주세요!'); // 경고창 띄우기
        }
        return !titleError; // 에러가 없으면 폼 제출
    }

    function cancelEditing() {
        window.location.href = 'MainCommunityPage.do';
    }

    // 페이지 로드 시 초기 바이트 수 업데이트
    window.onload = updateByteCount;
    </script>

    <!-- Core JS Files -->
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>

</body>
</html>