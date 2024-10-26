//이메일 API 관련
$(document).ready(function() {
    var isVerified = false;
    var num = '';

    //정규식을 이용한 유효성 검사
    function checkEmail(email) {
        var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        console.log("이메일 형식에 대한 유효성 검사 : "+emailRegex.test(email)); //이메일의 형식이 유효한지 확인하는 콘솔
        return emailRegex.test(email); //이메일 형식이 유효한지 boolean 값으로 return
    }

    // email 중복 확인할 때
    $('#email').on('input', function() { //이메일을 입력하는 input태그에서 나가면
        console.log("이메일 중복 검사 시작");
        var email = document.getElementById('email').value;
        var emailMsg = document.getElementById("emailMsg"); //유효성 검사의 성공, 실패를 나타낼 span태그

        // 이메일 형식이 아니라면
        if (checkEmail(email)) {
            console.log("이메일 중복 검사 시작 : 이메일 형식이 아니라면 부분");
            emailMsg.textContent = "";
            emailMsg.style.color = "none";
            //이메일 형식이 아니라면 이메일 발송 버튼 사용 불가
            document.getElementById('sendVeriBtn').disabled = false;
        }
        // 이메일 형식이라면
        else {
            console.log("이메일 중복 검사 시작 : 이메일 형식이라면 부분");
            emailMsg.textContent = "이메일 형식으로 입력해주세요. ";
            emailMsg.style.color = "red";
            //이메일 형식이라면 이메일 발송 버튼 사용 가능
            document.getElementById('sendVeriBtn').disabled = true;
        }

        $.ajax({
            type: "POST",
            url: "checkMemberId",
            data: { member_id : email },
            // dataType 생략시 text가 default
            success: function(data) {
                console.log("중복 이메일이 있는지에 대한 data : "+data);
                // C에서 사용 불가능하면 true, 사용 가능하면 false로 받음
                if (data == 'true') {
                    emailMsg.textContent = "이미 사용 중인 이메일입니다.";
                    emailMsg.style.color = "red";
                    //이미 사용 중인 이메일이라면, 이메일 발송 버튼 사용 불가
                    document.getElementById('sendVeriBtn').disabled = true;
                    return false; // 폼이 제출되지 않도록 함
                }
                else if (data == 'false') {
                    //사용 중인 이메일이 아니라면, 이메일 발송 버튼 사용 가능
                    document.getElementById('sendVeriBtn').disabled = false;
                    return true; // 폼이 정상적으로 제출되도록 함
                }
            },
            error: function(error) {
                console.log("error : " + error);
            }
        })
    })

    // 인증번호 전송 버튼을 누를 때
    $("#sendVeriBtn").on('click', function() {
        var email = document.getElementById('email').value;
        num = parseInt(Math.random() * 100000); //랜덤 정수 생성
        console.log("인증번호 전송 버튼 누를 때 email : "+email);
        console.log("인증번호 : "+num);
        $.ajax({
            url: "sendMail", //전송받을 페이지 경로//서블릿
            type: "POST", //데이터 읽어오는 방식//데이터 전송방식
            dataType: "text", //데이터 방식
            data: { email: email, num: num }, //json 타입으로 전송
            success: function(text) { //성공일 경우
                alert("인증 메일을 확인하세요.");
            },
            error: function() { //실패일 경우
                alert("실패");
            }
        });
    })

    // 인증번호 확인 버튼을 누를 때
    $("#checkVeriBtn").on('click', function() {
        var veriNum = document.getElementById('veriNum').value;
        console.log("사용자가 입력한 인증번호 : "+veriNum);
        // 사용자 입력 번호와 인증 번호가 같으면,
        if (num == veriNum) {
            console.log("인증번호 = 입력번호");
            alert("인증이 완료되었습니다."); // 인증 완료
            // 인증이 완료되면 정보 수정을 막기 위해 input 태그 안에 disabled 넣기
            // disabled는 데이터를 전달하지 못하기 때문에 넘겨줘야하는 값은 readonly
            document.getElementById('email').readOnly = true;
            document.getElementById('sendVeriBtn').disabled = true;
            document.getElementById('veriNum').disabled = true;
            document.getElementById('checkVeriBtn').disabled = true;
            // 인증 완료
            isVerified = true;
        }
        // 사용자 입력 번호와 인증 번화가 다르면
        else { // 회원가입 못함
            console.log("인증번호 != 입력번호");
            alert("인증 번호가 틀렸습니다. 다시 인증해주세요.") // 인증 실패, 다시 인증하세요
        }
    })

    // 회원가입 버튼 누를 때
    $('#submitBtn').on('click', function(event) {
        var email = document.getElementById('email').value;
        // 이메일 형식이 아니면 checkEmail()==false
        if (!checkEmail(email)) {
            alert("이메일 형식으로 입력해주세요. ");
            event.preventDefault(); // 폼 제출 방지
            return;
        }
        // 이메일 검사가 진행되지 않았을 경우
        if (isVerified == false) {
            alert("이메일 인증이 완료되지 않았습니다.");
            event.preventDefault(); // 폼 제출 차단
            return;
        }

        // 인증번호 전송 버튼이 활성화되어 있다면 (인증이 완료되지 않음)
        if (!(document.getElementById('sendVeriBtn').disabled)) {
            alert("인증번호를 전송하고 확인해주세요.");
            event.preventDefault(); // 폼 제출 방지
            return;
        }
    })
})
