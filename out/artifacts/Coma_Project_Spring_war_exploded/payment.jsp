<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <title>카카오페이</title>
</head>
<body>
<button id="pay" type="button">결제하기</button>
</body>
<script>
    var IMP = window.IMP; // imp 객체를 가져온다.
    IMP.init("imp87461252"); // 고객사 식별 코드

    function requestPay() {
        IMP.request_pay({
            pg: 'kakaopay',
            pay_method: 'card',
            amount: '1000',
            name: 'G바겐',
            merchant_uid: 'ORD20241018-000002'
        })
    };


    document.addEventListener('DOMContentLoaded', function () {
        // 버튼 클릭 시 requestPay 함수 호출
        document.getElementById('pay').addEventListener('click', requestPay);
    });
</script>

</html>
