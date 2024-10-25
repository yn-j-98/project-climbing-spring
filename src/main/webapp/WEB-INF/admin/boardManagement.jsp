<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="wf-publicsans-n7-active wf-publicsans-n5-active wf-publicsans-n6-active wf-publicsans-n3-active wf-publicsans-n4-active sidebar-color wf-fontawesome5solid-n4-active wf-fontawesome5regular-n4-active wf-fontawesome5brands-n4-active wf-simplelineicons-n4-active wf-active nav_open">
<head>
    <meta charset="UTF-8">
    <title>게시판 관리</title>
    <!--   Core JS Files   -->
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>

    <!-- Bootstrap JavaScript with Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>

    <!--페이지네이션 외부 스크립트-->
    <script src="js/pagenation.js"></script>
    <!-- sweetAlert JS FILE -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
    <script src="js/sweetAlert_modal.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/kaiadmin.css"/>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <!-- Fonts and icons -->
    <script src="assets/js/plugin/webfont/webfont.min.js"></script>
    <script src="https://kit.fontawesome.com/7f7b0ec58f.js" crossorigin="anonymous"></script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/kaiadmin.css"/>


    <!-- template Js File -->
    <script src="assets/js/kaiadmin.min.js"></script>
    <style>

    </style>
</head>
<body>
<mytag:admin_gnb member_id="Controller 데이터 입력할 예정"></mytag:admin_gnb>
<div class="main-panel flex-grow-1 p-4">
    <div class="nav-toggle position-absolute top-0 start-0">
        <button class="sidenav-toggler btn btn-icon btn-round btn-white h-100 w-25">
            <i class="fa fa-align-left"></i>
        </button>
    </div>
    <h1 class="text-center">게시판 관리</h1>
    <div class="container">
        <div class="row pb-5 justify-content-start d-flex">
            <div class="col-1"></div>
            <div class="col-2 ">
                <div class="row mb-4 ">
                    <div class="col">
                        <button type="button" id="deleteChecked" class="btn btn-danger">삭제</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-10">
                <div class="card card-stats card-round">
                    <div class="row p-3">
                        <div class="col-md-3 ">
                            <div class="form-floating">
                                <%--TODO 컨디션 안쓴다면 ID 변경--%>
                                <select class="form-select" id="search_keyword">
                                    <option ${search_keywoard == "BOARD_NUM" ? "selected":''} value="BOARD_NUM">게시글 번호</option>
                                    <option ${search_keywoard == "BOARD_WRITER_ID" ? "selected":''} value="BOARD_WRITER_ID">아이디</option>
                                    <option ${search_keywoard == "BOARD_TITLE" ? "selected":''} value="BOARD_TITLE">제목</option>
                                </select>
                                <label for="search_keyword">검색할 목록</label>
                            </div>
                        </div>
                        <div class="col-md-9">
                            <div class="form-floating">
                                <div class="input-group mb-3">
                                    <input type="text" class="form-control" aria-label="검색어 입력" placeholder="검색어 입력"
                                           aria-describedby="search" id="search_content">
                                    <%--TODO 키워드 안쓴다면 ID 변경--%>
                                    <button class="btn btn-outline-secondary" type="button" id="search">검색
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-10">
                <div class="card card-stats card-round">
                    <!-- 게시글+체크박스-->
                    <div class="row">
                        <div class="table-responsive">
                            <table class="table align-middle text-center table-hover">
                                <thead>
                                <tr>
                                    <th scope="col">
                                        <!--전체 선택 버튼-->
                                        <div class="form-check text-start">
                                            <label for="allCheck">모두 선택</label>
                                            <input class="form-Allcheck-input" id="allCheck" type="checkbox">
                                        </div>
                                    </th>
                                    <th scope="col" class="w-25">제목</th>
                                    <th scope="col" class="w-25">이름</th>
                                    <th scope="col" class="w-50">내용</th>
                                </tr>
                                </thead>
                                <!--데이터 목록-->
                                <tbody id="boardList">
                                <c:forEach var="board" items="${datas}">
                                    <tr class="align-middle" data-board-num="1">
                                        <td>
                                            <div class="form-check text-start">
                                                <input class="form-check-input" type="checkbox" value="">
                                            </div>
                                        </td>
                                        <td>${board.board_title}</td>
                                        <td>${board.member_name}</td>
                                        <td class="overflow-hidden">
                                            ${board.board_writer_id}
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 페이지네이션 -->
    <div id="pageNation" class="row justify-content-center">
        <div class="row pt-5">
            <div class="col-md-12 d-flex justify-content-center ">
                <nav aria-label="Page navigation" data-search-contents="${search_content}">
                    <input type="hidden" id="totalCount" value="${total}"> <%--FIXME--%>
                    <input type="hidden" id="currentPage" value="${page}">
                    <ul id="pagination" class="pagination justify-content-center align-items-center">
                        <!--페이지네이션 삽입-->
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {

        //모두선택 체크시 실행
        $('#allCheck').change(function () {
            //form-check-input인 모든 체크박스의 checked 속성을 #allCheck 체크박스의 checked 값과 동일하게 설정
            $('.form-check-input').prop('checked', $(this).prop('checked'));
        });

        //삭제버튼 누를시 실행
        $('#deleteChecked').click(function () {
            var checkedItems = $('.form-check-input:checked');
            if (checkedItems.length === 0) {
                sweetAlert_error('삭제할 항목이 없습니다', '');
                return;
            }
            sweetAlert_confirm_warning('글 삭제', '정말로 삭제하시겠습니까?', '삭제', '취소')
                .then(function (boardDelete) {
                    if (boardDelete) {
                        // 폼 생성
                        var form = $('<form/>', {
                            action: '컨트롤러가 지정해준 값.do',
                            method: 'POST',
                            style: 'display: none;'
                        });

                        // 체크된 모든 항목의 boardNum을 추가
                        checkedItems.each(function () {
                            var boardNum = $(this).data("boardNum");
                            console.log("boardNum =[" + boardNum + "]");
                            //TODO 배열 요청 자체적으로 배열로 만들어서 넘긴다 = 같은이름이기 때문에
                            form.append($('<input/>', {type: 'hidden', name: 'gym_num_list', value: boardNum}));
                        });

                        console.log("폼 HTML = [" + form.html() + "]"); // 폼 내부의 HTML 내용 출력
                        form.appendTo('body').submit(); // 폼을 body에 추가하여 제출
                        // sweetAlert_success('삭제가 완료되었습니다', ' ');
                        //TODO 컨트롤러가 인포페이지로 이동해줄것
                    }
                });
        });

        //검색 동기
        $('#search').click(function () {
            var board_search_content = $('#board_search_content').val();
            var board_search_keyword = $('#board_search_keyword').val();//문자열 값으로 변환
            console.log("board_search_content = [" + board_search_content + "]");
            console.log("board_search_keyword = [" + board_search_keyword + "]");

            var form = $('<form/>', {
                //TODO .do 입력
                action: '컨트롤러가 지정해준 값.do',
                method: 'POST',
                style: 'display: none;'
            });
            form.append($('<input/>', {type: 'hidden', name: 'search_keyword', value: search_keyword}));
            form.append($('<input/>', {type: 'hidden', name: 'search_content', value: search_content}));
            // 문서에 form 추가
            $('body').append(form);
            form.submit();
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>