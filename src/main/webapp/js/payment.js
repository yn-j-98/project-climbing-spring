






function requestPay() {

    var IMP = window.IMP; // imp 객체를 가져온다.
    IMP.init(""); // 고객사 식별 코드

    // let uuid = self.crypto.randomUUID(); // 랜덤 uuid생성 // 기존 배포전 uuid 생성 방법
    let uuid = generateUUID(); // 랜덤 uuid생성 // 기존 배포전 uuid 생성 방법
    const cleanUuid = uuid.replace(/-/g, ""); // 불필요한 특수기호 삭제
    const currentDate = new Date().toISOString().slice(0, 10).replace(/-/g, '');

    // const memberUuid = member_id.split('@')[0];// 사용자 이름에서 @ 앞에 아이디만 추출
    const merchant_uid = cleanUuid+currentDate; // 예약자id+주문 번호 날짜+랜덤값
    console.log("merchant_uid : "+merchant_uid);

    let reservation_price = $('#member_money').val(); // 가격
    console.log("가격 : "+reservation_price);
    let gym_num = $('#gymNum').val(); // 암벽장 번호
    console.log("암벽장 번호 : "+gym_num);
    let reservationDate = $('#member_date').val(); // 예약 날짜
    console.log("예약일 : "+reservationDate);
    const gym_name = $('#gymName').val(); // 암벽장 이름
    console.log("암벽장 이름 : "+gym_name);
    const member_id = $('#member_id').val();
    console.log("멤버 아이디 : "+member_id);
    const member_point = $('#member_point').val(); // 사용자 사용한 포인트
    console.log("멤버 사용포인트 : "+member_point);
    const member_name = $('#member_name').val(); // 사용자 이름
    console.log("멤버 이름 : "+member_name);

    // 사전 검증 등록
    $.ajax({
        url: "paymentPrepare.do",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            merchant_uid: merchant_uid,
            amount: reservation_price,
            reservation_date: reservationDate,
            reservation_gym_num: gym_num,
            reservation_member_id: member_id
        }) // UUID, 가격 보내서 사전 검증 Controller로 전달
    }).done(function(data) {
        console.log("첫 번째 응답: " + data);
        if (data === "true") {  // true라면
            console.log("사전 검증 등록 완료"); // 사전검증 등록 성공

            // 사전 검증 등록 확인
            $.ajax({
                url: "paymentPrepared.do",
                method: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    merchant_uid: merchant_uid, // 해당 uuid 보냄
                })

            }).done(function(data) {
                console.log("두 번째 응답:", data); // 응답 전체 확인
                console.log(typeof data);
                console.log("두 번째 응답:", data.amountRes); // 금액 확인

                if (!isNaN(data.amountRes) && data.amountRes > 0) { // 결제한 금액이 있다면
                    console.log("사전 검증 등록 조회 성공");
                    amount = data.amount_result;
                    console.log(amount);
                } else {
                    console.log("오류 발생: 반환된 값이 유효하지 않음");
                }
            }).fail(function(error) {
                console.log("사전 검증 조회 AJAX 오류:", error);
            });

        } else {
            console.log("사전 검증 등록 실패");
            location.href = "paymentFailed.do"
        }
    }).fail(function(error) {
        console.log("사전 검증 등록 AJAX 오류:", error);
    });

    IMP.request_pay({
        pg: 'kakaopay',
        pay_method: 'card',
        amount: reservation_price,
        name: gym_name,
        merchant_uid: merchant_uid,
        buyer_email: member_id
    },
        function (rsp) {
            if (rsp.success) {
                // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
                // jQuery로 HTTP 요청
                jQuery.ajax({
                    url: "paymentValidate.do",
                    method: "POST",
                    // JSON 형식으로 전송
                    data: {
                        imp_uid: rsp.imp_uid,  // 포트원 결제 고유번호
                        gym_num: gym_num,
                        reservation_date : reservationDate,
                        member_point : member_point,
                        merchant_uid: rsp.merchant_uid  // 주문번호
                    }
                }).done(function (data) {
                    if (rsp.paid_amount == data) {
                        // 결제 API 성공시 로직
                            location.href = "paymentSuccess.do"
                    } else {
                        // 결제 취소
                        location.href = "paymentFailed.do"
                    }
                }).fail(function (ajaxError) {
                    console.error('AJAX 요청 오류:', ajaxError);
                    console.error('에러 상태 코드:', ajaxError.status);
                    console.error('에러 응답 텍스트:', ajaxError.responseText);
                });
            } else {
                console.error('결제 실패:', rsp.error_msg);
            }
        });
};

// 배포에서 사용할 uuid 생성 함수
function generateUUID() {
    // 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'라는 문자열을 템플릿으로 사용하여 UUID를 생성합니다.
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        // 0부터 15까지의 무작위 정수를 생성합니다.
        // Math.random()은 0 이상 1 미만의 부동 소수점 난수를 반환합니다.
        // 여기에 16을 곱해서 0에서 16 사이의 숫자를 만든 뒤, | 0 연산자로 정수 부분만 취합니다.
        var r = Math.random() * 16 | 0,
            // 'x'인 경우에는 r 값을 그대로 사용하고, 'y'인 경우에는 특정 비트 조작을 가합니다.
            // r & 0x3는 하위 2비트만 남기고, 0x8 (8진수 10)과 OR 연산을 수행합니다.
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        // 최종 계산된 값을 16진수 문자열로 변환합니다.
        return v.toString(16);
    });
}

document.addEventListener('DOMContentLoaded', function () {
    // 버튼 클릭 시 requestPay 함수 호출
    document.getElementById('reservationbutton').addEventListener('click', requestPay);
});

