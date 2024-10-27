<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>예약 관리</title>
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

    <!--페이지네이션 외부 스크립트-->
    <script src="../../js/pagination.js"></script>

    <!-- Fonts and icons -->
    <script src="assets/js/plugin/webfont/webfont.min.js"></script>
    <script src="https://kit.fontawesome.com/7f7b0ec58f.js" crossorigin="anonymous"></script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/kaiadmin.css"/>


    <!-- template Js File -->
    <script src="assets/js/kaiadmin.min.js"></script>

</head>
<body>
<mytag:admin_gnb member_id="${MEMBER_ID}"></mytag:admin_gnb>
<div class="main-panel flex-grow-1 p-4">
    <div class="nav-toggle position-absolute top-0 start-0">
        <button class="sidenav-toggler btn btn-icon btn-round btn-white h-100 w-25">
            <i class="fa fa-align-left"></i>
        </button>
    </div>
    <h1 class="text-center">예약자 관리</h1>
    <div class="container">
        <main class="container-fluid">
            <!-- Chart Section -->
            <div class="row">
                <div class="chart-container">
                    <div class="row">
                        <%--TODO chart데이터 들어갈 곳--%>
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
                                        <option ${reservation_search_keyword == "RESERVATION_MEMBER_ID" ? "selected":''} value="RESERVATION_MEMBER_ID">예약자</option>
                                        <option ${reservation_search_keyword == "RESERVATION_GYM_NAME" ? "selected":''} value="RESERVATION_GYM_NAME">암벽장</option>
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
            <!-- 예약자 리스트 -->
            <div class="row">
                <c:forEach var="reservation" items="${datas}">
                    <div class="col-md-3">
                        <div class="card h-90">
                            <div class="row g-0 d-flex flex-column">
                                <div class="col">
                                    <div class="card-body">
                                        <h5 class="card-title border-bottom border-dark-subtle">예약 정보</h5>
                                        <div class="mt-3">
                                            <p class="card-text">
                                                <strong>암벽장 위치:</strong> ${reservation.gym_location}
                                            </p>
                                            <p class="card-text">
                                                <strong>암벽장 가격:</strong> ${reservation.gym_price}원
                                            </p>
                                            <p class="card-text">
                                                <strong>예약자명:</strong> ${reservation.reservation_member_name}
                                            </p>
                                            <p class="card-text">
                                                <strong>실제 결제 금액:</strong> ${reservation.reservation_price}원
                                            </p>
                                            <div class="text-end">
                                                <button type="button" class="btn btn-danger"
                                                        data-reservation-num="${reservation.reservation_num}">예약 취소
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- 페이지네이션 -->
            <row>
                <div id="pageNation" class="row justify-content-center">
                    <div class="row pt-5">
                        <div class="col-md-12 d-flex justify-content-center ">
                            <nav aria-label="Page navigation">
                                <input type="hidden" id="totalCount" value="${total}"
                                       data-search-contents= ${reservation_search_content}><%--FIXME--%>
                                <input type="hidden" id="currentPage" value="${page}">
                                <ul id="pagination" class="pagination justify-content-center align-items-center">

                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </row>
        </main>
    </div>
</div>
<!-- Bootstrap JS and Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    $(document).ready(function () {
        $(".btn-danger").click(function () {
            //TODO pk받아오기
            // .data() 메서드를 사용할 때 camelCase로 변환하여 접근
            let reservationNum = $(this).data('reservationNum');
            console.log("reservationNum =[" + reservationNum + "]");
            sweetAlert_confirm_question('예약 삭제', '정말로 삭제하시겠습니까?', '삭제', '취소')
                .then(function (reservationDelete) {
                    if (reservationDelete) {//확인을 누른다며
                        <!-- TODO 성공시 동기 로직 -->
                        var form = $('<form/>', {
                            action: '컨트롤러가 지정해준 값.do',
                            method: 'POST',
                            style: 'display: none;'
                        });

                        form.append($('<input/>', {type: 'hidden', name: 'reservation_num', value: reservationNum}));
                        console.log("폼 HTML = [" + form.html() + "]"); // 폼 내부의 HTML 내용 출력
                        form.submit();
                        // sweetAlert_success('삭제가 완료되었습니다', ' ');
                        //TODO 컨트롤러가 인포페이지로 이동해줄것
                    }
                });
        });

        //검색 동기
        $('#search').click(function () {
            var search_content = $('#search_content').val();
            var search_keyword = $('#search_keyword').val();//문자열 값으로 변환
            console.log("search_content = [" + search_content + "]");
            console.log("search_keyword = [" + search_keyword + "]");

            var form = $('<form/>', {
                //TODO .do 입력
                action: '컨트롤러가 지정해준 값',
                method: 'POST',
                style: 'display: none;'
            });
            form.append($('<input/>', {type: 'hidden', name: 'reservation_search_keyword', value: search_keyword}));
            form.append($('<input/>', {type: 'hidden', name: 'reservation_search_content', value: search_content}));
            // 문서에 form 추가
            $('body').append(form);
            form.submit();
            //TODO 컨트롤러가 인포페이지로 이동해줄것
        });
    });
</script>
</body>
</html>