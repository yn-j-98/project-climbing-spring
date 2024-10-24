function sweetAlert_success(title, msg) { //성공용 스윗 얼랏
    console.log('sweetAlert_modal.js success start log');
    Swal.fire({ //스윗트 얼랏 기본 제공 함수
        title : title, //스윗트 얼랏에 제목을 지정합니다.
        text : msg, //스윗트 얼랏에 내용을 지정합니다.
        icon : 'success',
    });
    console.log('sweetAlert_modal.js success end log');
}

function sweetAlert_error(title, msg) {//오류/실패용 스윗 얼랏
    console.log('sweetAlert_modal.js error start log');
    Swal.fire({
        title : title,
        text : msg,
        icon : 'error',
    });
    console.log('sweetAlert_modal.js error end log');
}

function sweetAlert_warning(title, msg) {//경고용 스윗 얼랏
    console.log('sweetAlert_modal.js warning start log');
    Swal.fire({
        title : title,
        text : msg,
        icon : 'warning',
    });
    console.log('sweetAlert_modal.js warning end log');
}

function sweetAlert_info(title, msg) {//정보 알림용 스윗 얼랏
    console.log('sweetAlert_modal.js info start log');
    Swal.fire({
        title : title,
        text : msg,
        icon : 'info',
    });
    console.log('sweetAlert_modal.js info end log');
}

function sweetAlert_question(title, msg) {//질문용 스윗 얼랏
    console.log('sweetAlert_modal.js question start log');
    Swal.fire({
        title : title,
        text : msg,
        icon : 'question',
    });
    console.log('sweetAlert_modal.js question end log');
}

function sweetAlert_confirm_success(title, msg, confirm_btn, cancel_btn) {//성공아이콘 취소 등록 용 스윗트 얼랏
    console.log('sweetAlert_modal.js sweetAlert_confirm_success start log');
    return  Swal.fire({
        title: title,
        text: msg,
        icon: 'success',

        showCancelButton: true, // cancel 버튼 생성 / 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confirm 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: confirm_btn, // confirm 버튼 텍스트 지정
        cancelButtonText: cancel_btn, // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 반전

    }).then(function (result){
        console.log('sweetAlert_modal.js return log : ['+result.isConfirmed+']' );
        console.log('sweetAlert_modal.js sweetAlert_confirm_success end log');
        //True False 를 반환
        return result.isConfirmed;
    });
}

function sweetAlert_confirm_error(title, msg, confirm_btn, cancel_btn) {//에러아이콘 취소 등록 용 스윗트 얼랏
    console.log('sweetAlert_modal.js sweetAlert_confirm_error start log');
    return  Swal.fire({
        title: title, //제목 지정
        text: msg, //출력 내용 지정
        icon: 'error',

        showCancelButton: true, // cancel 버튼 생성 / 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confirm 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: confirm_btn, // confirm 버튼 텍스트 지정
        cancelButtonText: cancel_btn, // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 반전

    }).then(function (result){
        console.log('sweetAlert_modal.js return log : ['+result.isConfirmed+']' );
        console.log('sweetAlert_modal.js sweetAlert_confirm_error end log');
        //True False 를 반환
        return result.isConfirmed;
    });
}

function sweetAlert_confirm_warning(title,msg,confirm_btn,cancel_btn) {//경고아이콘 취소 등록 용 스윗트 얼랏
    console.log('sweetAlert_modal.js sweetAlert_confirm_warning start log');
    return  Swal.fire({
        title: title, //제목 지정
        text: msg, //출력 내용 지정
        icon: 'warning',

        showCancelButton: true, // cancel 버튼 생성 / 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confirm 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: confirm_btn, // confirm 버튼 텍스트 지정
        cancelButtonText: cancel_btn, // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 반전

    }).then(function (result){
        console.log('sweetAlert_modal.js return log : ['+result.isConfirmed+']' );
        console.log('sweetAlert_modal.js sweetAlert_confirm_warning end log');
        //True False 를 반환
        return result.isConfirmed;
    });
}

function sweetAlert_confirm_info(title,msg,confirm_btn,cancel_btn) {//내용 전달 아이콘 취소 등록 용 스윗트 얼랏
    console.log('sweetAlert_modal.js sweetAlert_confirm_info start log');
    return  Swal.fire({
        title: title, //제목 지정
        text: msg, //출력 내용 지정
        icon: 'info',

        showCancelButton: true, // cancel 버튼 생성 / 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confirm 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: confirm_btn, // confirm 버튼 텍스트 지정
        cancelButtonText: cancel_btn, // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 반전

    }).then(function (result){
        console.log('sweetAlert_modal.js return log : ['+result.isConfirmed+']' );
        console.log('sweetAlert_modal.js sweetAlert_confirm_info end log');
        //True False 를 반환
        return result.isConfirmed;
    });
}

function sweetAlert_confirm_question(title,msg,confirm_btn,cancel_btn) {//질문아이콘 취소 등록 용 스윗트 얼랏
    console.log('sweetAlert_modal.js sweetAlert_confirm_question start log');
    return  Swal.fire({
        title: title, //제목 지정
        text: msg, //출력 내용 지정
        icon: 'question',

        showCancelButton: true, // cancel 버튼 생성 / 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confirm 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: confirm_btn, // confirm 버튼 텍스트 지정
        cancelButtonText: cancel_btn, // cancel 버튼 텍스트 지정

        reverseButtons: true, // 버튼 순서 반전

    }).then(function (result){
        console.log('sweetAlert_modal.js return log : ['+result.isConfirmed+']' );
        console.log('sweetAlert_modal.js sweetAlert_confirm_question end log');
        //True False 를 반환
        return result.isConfirmed;
    });
}
