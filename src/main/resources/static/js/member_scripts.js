/*ID 중복 체크*/
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

/*jQuery 사용하여 실시간 비밀번호 확인 로직*/
function pwCheck(){
    if($('#pw1').val() === $('#pw2').val()){
        $('#pwConfirm').text('비밀번호 일치').css('color', 'green')
    }else{
        $('#pwConfirm').text('비밀번호 불일치').css('color', 'red')
    }
}

/*회원정보 수정, 삭제 확인 로직*/
function memberSubmitForm(actionType) {
    if (actionType === 'delete') {
        // confirm 창을 통해 사용자에게 삭제 의사를 확인
        alert("question", "확인", "정말로 회원 탈퇴를 하시겠습니까?", function (){
            document.getElementById('actionField').value = 'delete';
            document.getElementById('modifyForm').submit();
        }, function (){});
    } else if (actionType === 'update') {
        alert("question", "확인", "정말로 회원정보를 수정 하시겠습니까?", function (){
            document.getElementById('actionField').value = 'update';
            document.getElementById('modifyForm').submit();
        }, function () {});
    }
}