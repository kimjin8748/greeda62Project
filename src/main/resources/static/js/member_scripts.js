function checkId() {
    var userId = document.getElementById("id").value; // 사용자가 입력한 ID 가져오기
    fetch("/check-id", {
        method: "POST", // 요청 메소드를 POST로 설정
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: "id=" + userId // 요청 본문에 id 파라미터 추가
    })
        .then(response => response.json())
        .then(data => {
            // 서버로부터의 응답에 따라 메시지를 변경
            var resultText = data.isDuplicated ? "이미 사용중인 아이디입니다." : "사용 가능한 아이디입니다.";
            document.getElementById("idCheckResult").innerText = resultText; // 결과 표시
        })
        .catch(error => {
            console.error('Error:', error);
            // 에러 발생 시 사용자에게 알림
            document.getElementById("idCheckResult").innerText = "아이디 중복 확인 중 오류가 발생했습니다.";
        });
}

//jQuery 사용하여 실시간 비밀번호 확인
function pwCheck(){
    if($('#pw1').val() === $('#pw2').val()){
        $('#pwConfirm').text('비밀번호 일치').css('color', 'green')
    }else{
        $('#pwConfirm').text('비밀번호 불일치').css('color', 'red')
    }
}