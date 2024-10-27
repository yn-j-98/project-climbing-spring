// 동적으로 스크립트 태그 생성
var script = document.createElement("script");
script.src = "https://cdn.iamport.kr/v1/iamport.js";
script.onload = function() {
    // 포트원이 로드된 후 실행할 코드
    $(document).ready(function() {
        console.log("포트원 로드 성공");
    });
};

// <head> 태그 내에 스크립트 추가
document.head.appendChild(script);

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
    document.getElementById('reservationbtn').addEventListener('click', requestPay);
});