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

            console.log(`Checkbox checked: ${checkbox.checked}`);
            console.log(`Price: ${price}, Quantity: ${quantity}`);
            totalPrice += price * quantity; // 총 가격 업데이트
        }
    });
    console.log(`Total Price: ${totalPrice}`);
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
    const productSize = document.getElementById("productSize").value;
    const productDescription = document.getElementById("productDescription").value;
    const totalPrice = calculateTotal();
    console.log("Total Price in requestPay:", totalPrice); // 로그로 확인
    const buyerName = document.getElementById("buyerName").value;
    const buyerAddr = document.getElementById("buyerAddr").value;
    const buyerEmail = document.getElementById("buyerEmail").value;
    const buyerTel = document.getElementById("buyerTel").value;

    const productId = document.getElementById("productSerialNumber").value;

    IMP.init("imp30825140"); // 가맹점 식별코드
    IMP.request_pay(
        {
            pg: "html5_inicis.INIpayTest",
            pay_method: "card",
            merchant_uid: "order_no_" + new Date().getTime(),
            name: productName,
            amount: totalPrice,
            buyer_email: buyerEmail,
            buyer_name: buyerName,
            buyer_tel: buyerTel,
            buyer_addr: buyerAddr,
            escrow: true,
            vbank_due: new Date().getTime() + 30,
            bypass: {
                acceptmethod: "noeasypay",
                P_RESERVED: "noeasypay=Y",
            },
            period: {
                from: "20240101",
                to: "20241231",
            },
        },
        function (rsp) {
            if (rsp.success) {
                alert('결제가 완료되었습니다. 결제 ID: ' + rsp.imp_uid);

                const paymentData = {
                    paymentId: rsp.merchant_uid,
                    amount: totalPrice,
                    paymentDate: new Date().toISOString(), // 현재 시간을 ISO 형식으로 설정
                    products: [
                        {
                            productSerialNumber: productId,
                            productName: productName,
                            productPrice: totalPrice,
                            productSize: productSize,
                            productDescription: productDescription,
                        }
                    ],
                    buyerName: buyerName,
                    buyerEmail: buyerEmail,
                    buyerTel: buyerTel,
                    buyerAddr: buyerAddr
                };

                // 서버에 결제 정보 전송
                fetch('http://localhost:8090/api/payments/complete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(paymentData),
                })
                    .then(response => response.json())
                    .then(Data => {
                        if (Data.error) {
                            alert(Data.error); // 서버에서 반환한 에러 메시지를 사용자에게 알림
                        } else {
                            console.log('결제 정보 전송 성공:', Data);
                        }
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });

                console.log(rsp.data);
            } else {
                alert('결제에 실패하였습니다. 에러내용: ' + rsp.error_msg);
                console.log(rsp);
            }
        }
    );
}
