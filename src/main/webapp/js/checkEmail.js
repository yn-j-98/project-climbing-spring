$(document).ready(function() {
    var num = '';

    // 아이디 존재 여부 확인
    $('#username').on('input', function() {
        var username = document.getElementById('username').value;
        var usernameMsg = document.getElementById("usernameMsg");

        $.ajax({
            type: "POST",
            url: "checkEmail.do",
            contentType: "application/json",
            data: JSON.stringify({ member_id: username }), // member_id에 username 할당
            success: function(data) {
                if (data === "true") {
                    usernameMsg.textContent = "존재하는 아이디입니다.";
                    usernameMsg.style.color = "green";
                    document.getElementById('sendPasswordBtn').disabled = false;
                } else {
                    usernameMsg.textContent = "존재하지 않는 아이디입니다.";
                    usernameMsg.style.color = "red";
                    document.getElementById('sendPasswordBtn').disabled = true;
                }
            },
            error: function(error) {
                console.log("error : " + error);
            }
        });
    });

    // 비밀번호 전송
    $("#sendPasswordBtn").on('click', function() {
        var username = document.getElementById('username').value; // 공백 제거

        $.ajax({
            url: "sendMail.do",
            type: "POST",
            dataType: "text",
            contentType: "application/json",
            data: JSON.stringify({ member_id: username, member_password: num }), // 변수명 수정
            success: function() {
                alert("임시 비밀번호가 이메일로 전송되었습니다.");
            },
            error: function() {
                alert("비밀번호 전송에 실패했습니다.");
            }
        });
    });
});
