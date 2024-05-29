/*!
* Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function clearSearchField() {
    document.getElementById('search-field').value = '';
    document.getElementById('search-field').focus(); // 입력 필드에 다시 초점을 맞춥니다.
}

app.controller('MemberController', ['$scope', function ($scope) {
    $scope.showSignIn = !signUpFailed;
}]);

function toggleSelectAll(source) {
    const checkboxes = document.querySelectorAll('.product-checkbox');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = source.checked; // 모든 체크박스의 상태를 전체 선택 체크박스와 동일하게 설정
    });
    calculateTotal(); // 전체 가격 다시 계산
}

function calculateTotal() {
    let totalPrice = 0; // 총 가격 초기화
    document.querySelectorAll('.product-checkbox').forEach((checkbox) => {
        if (checkbox.checked) { // 체크박스가 체크되어 있는 경우
            const row = checkbox.closest('.row'); // 해당 체크박스가 속한 row 요소 찾기
            const quantityInput = row.querySelector('.product-quantity'); // 해당 row에서 수량 입력 필드 찾기
            const price = parseInt(checkbox.dataset.price) || 0; // 상품 가격 가져오기, NaN 방지
            const quantity = parseInt(quantityInput.value) || 0; // 상품 수량 가져오기, NaN 방지
            totalPrice += price * quantity; // 총 가격 업데이트
        }
    });
    document.getElementById('total-price').innerText = totalPrice + '원'; // 총 가격 표시 업데이트
    document.getElementById('total-price1').innerText = totalPrice + '원'; // 총 가격 표시 업데이트
    return totalPrice;
}

function submitForm2(actionType) {
    // 회원 탈퇴인 경우
    if (actionType === 'delete') {
        // confirm 창을 통해 사용자에게 삭제 의사를 확인
        var confirmDelete = confirm("정말로 상품을 삭제하시겠습니까?");
        if (confirmDelete) {
            // 사용자가 '확인'을 누른 경우 폼 제출
            document.getElementById('actionField').value = 'delete';
            document.getElementById('productForm').submit();
        }
    } else if (actionType === 'update') {
        // 회원 정보 수정인 경우 바로 폼 제출
        document.getElementById('actionField').value = 'update';
        document.getElementById('productForm').submit();
    }
}

function submitForm3(actionType) {
    // 회원 탈퇴인 경우
    if (actionType === 'delete') {
        // confirm 창을 통해 사용자에게 삭제 의사를 확인
        var confirmDelete = confirm("정말로 게시글을 삭제하시겠습니까?");
        if (confirmDelete) {
            // 사용자가 '확인'을 누른 경우 폼 제출
            document.getElementById('actionField').value = 'delete';
            document.getElementById('productForm').submit();
        }
    } else if (actionType === 'update') {
        // 회원 정보 수정인 경우 바로 폼 제출
        document.getElementById('actionField').value = 'update';
        document.getElementById('productForm').submit();
    }
}

function requestPay() {

    const productName = document.getElementById("productName").value;
    const totalPrice = calculateTotal();
    const buyerName = document.getElementById("buyerName").value;

    IMP.init("imp30825140"); // 가맹점 식별코드
    IMP.request_pay(
        {
            pg: "html5_inicis.INIpayTest", // 테스트 시 html5_inicis.INIpayTest 사용
            pay_method: "card",
            merchant_uid: "order_no_" + new Date().getTime(), // 상점에서 생성한 고유 주문번호
            name: productName,
            amount: totalPrice,
            buyer_email: "test@portone.io",
            buyer_name: buyerName,
            buyer_tel: "010-1234-5678", // 필수 파라미터 입니다.
            buyer_addr: "서울특별시 강남구 삼성동",
            buyer_postcode: "123-456",
            //m_redirect_url: "https://www.your-redirect-url.com", // 모바일에서 결제 완료 후 리디렉션 될 URL
            escrow: true, // 에스크로 결제인 경우 설정
            vbank_due: new Date().getTime() + 30, // 가상계좌 입금 기한 (YYYYMMDD)
            bypass: {
                // PC 경우
                acceptmethod: "noeasypay", // 간편결제 버튼을 통합결제창에서 제외(PC)
                // acceptmethod: "cardpoint", // 카드포인트 사용시 설정(PC)
                // 모바일 경우
                P_RESERVED: "noeasypay=Y", // 간편결제 버튼을 통합결제창에서 제외(모바일)
                // P_RESERVED: "cp_yn=Y", // 카드포인트 사용시 설정(모바일)
                // P_RESERVED: "twotrs_bank=Y&iosapp=Y&app_scheme=your_app_scheme://", // iOS에서 계좌이체시 결제가 이뤄지던 앱으로 돌아가기
            },
            period: {
                from: "20240101", // YYYYMMDD
                to: "20241231", // YYYYMMDD
            },
        },
        function (rsp) {
            if (rsp.success) {
                // 결제 성공 시 로직
                alert('결제가 완료되었습니다. 결제 ID: ' + rsp.imp_uid);
                console.log(rsp);
            } else {
                // 결제 실패 시 로직
                alert('결제에 실패하였습니다. 에러내용: ' + rsp.error_msg);
                console.log(rsp);
            }
        }
    );
}