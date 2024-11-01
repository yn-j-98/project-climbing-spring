<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>카카오페이</title>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
    <script src="js/payment.js"></script>


</head>
<body>

    <div class="card-body">
        <input type="hidden" id="gymNum" name="reservation_gym_num" value="1">
        <input type="hidden" id="gymName" name="reservation_gym_name" value="고물볼드">
        <div class="row">
            <div class="col-md-2 d-flex align-items-center">
                <p class="mb-0">아이디</p>
            </div>
            <div class="col-md-10">
                <div class="form-group">
                    <input type="text" class="form-control" id="member_id" value="coma2" name="reservation_member_id" readonly/>
                </div>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <p class="mb-0">이름</p>
            </div>
            <div class="col-md-10">
                <div class="form-group">
                    <input type="text" class="form-control" id="member_name" value="코마2" name="member_name" readonly/>
                </div>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <p class="mb-0">신청날짜</p>
            </div>
            <div class="col-md-10">
                <div class="form-group">
                    <input type="text" class="form-control" id="member_date" value="2024-10-29" name="reservation_date" readonly/>
                </div>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <p class="mb-0">가능인원</p>
            </div>
            <div class="col-md-10">
                <div class="form-group">
                    <input type="text" class="form-control" id="member_cnt" value="10명" name="reservation_cnt" readonly/>
                </div>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <p class="mb-0">사용포인트</p>
            </div>
            <div class="col-md-10">
                <div class="form-group">
                    <input type="text" class="form-control" id="member_point" value="5000" name="user_point VIEW_USE_POINT" readonly/>
                </div>
            </div>
            <div class="col-md-2 d-flex align-items-center">
                <p class="mb-0">결제 금액</p>
            </div>
            <div class="col-md-10">
                <div class="form-group">
                    <input type="text" class="form-control" id="member_money" value="15000" name="reservation_price" readonly/>
                </div>
            </div>
        </div>
    </div>
    <div class="card-action text-center">
        <button type="button"
                class="btn btn-black px-5 mb-3 mb-sm-0 me-0 me-sm-4"
                onclick="window.location.href='main.do';">취소</button>
        <button type="button" class="btn btn-primary px-5" id="reservationbtn">예약</button>
    </div>

</body>


</html>
