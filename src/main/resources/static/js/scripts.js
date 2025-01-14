/*검색 기능 로직*/
function clearSearchField() {
    document.getElementById('search-field').value = '';
    document.getElementById('search-field').focus(); // 입력 필드에 다시 초점을 맞춥니다.
}

app.controller('MemberController', ['$scope', function ($scope) {
    $scope.showSignIn = !signUpFailed;
}]);

/*장바구니 전체선택 체크박스로 상품 전체 선택 로직*/
function toggleSelectAll(source) {
    const checkboxes = document.querySelectorAll('.product-checkbox-item');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = source.checked; // 모든 체크박스의 상태를 전체 선택 체크박스와 동일하게 설정
    });
    calculateTotal(); // 전체 가격 다시 계산
}

/*아이템 하나 선택시 전체 선택 체크/해제*/
function toggleSelectItem() {
    const allCheckboxes = document.querySelector('.product-checkbox');
    const checkboxes = document.querySelectorAll('.product-checkbox-item');
    const checked = document.querySelectorAll('.product-checkbox-item:checked');
    if(checkboxes.length === checked.length) {
        allCheckboxes.checked = true;
    } else {
        allCheckboxes.checked = false;
    }
    calculateTotal(); // 전체 가격 다시 계산
}

/*장바구니 상품가격 계산 로직*/
function calculateTotal() {
    let totalProductPrice = 0; // 총 가격 초기화
    let totalPrice = 0;
    document.querySelectorAll('.product-checkbox-item').forEach((checkbox) => {
        if (checkbox.checked) { // 체크박스가 체크되어 있는 경우
            const row = checkbox.closest('.row'); // 해당 체크박스가 속한 row 요소 찾기
            const quantityInput = row.querySelector('.product-quantity'); // 해당 row에서 수량 입력 필드 찾기
            const price = parseInt(checkbox.dataset.price) || 0; // 상품 가격 가져오기, NaN 방지
            const quantity = parseInt(quantityInput.value) || 0; // 상품 수량 가져오기, NaN 방지

            totalProductPrice += price * quantity; // 총 가격 업데이트
        }
    });
    const shippingFee = parseInt(document.getElementById("shippingFee").value);
    totalPrice = totalProductPrice + shippingFee;
    document.getElementById('total-price').innerText = totalProductPrice + '원'; // 총 가격 표시 업데이트
    document.getElementById('total-price1').innerText = totalPrice + '원'; // 총 가격 표시 업데이트
    return totalPrice;
}

/*결제취소 페이지에서 취소금액 계산 로직*/
function calculateTotal1() {
    let totalProductPrice = 0; // 총 가격 초기화
    let totalPrice = 0;
    let quantity = 1;
    document.querySelectorAll('.product-checkbox').forEach((checkbox) => {
        if (checkbox.checked) { // 체크박스가 체크되어 있는 경우
            const row = checkbox.closest('.row'); // 해당 체크박스가 속한 row 요소 찾기
            const price = parseInt(checkbox.dataset.price) || 0; // 상품 가격 가져오기, NaN 방지

            totalProductPrice += price * quantity; // 총 가격 업데이트
        }
    });
    document.getElementById('total-price').innerText = totalProductPrice + '원'; // 총 가격 표시 업데이트
    document.getElementById('total-price1').innerText = totalProductPrice + '원'; // 총 가격 표시 업데이트
}

/*상품 수정, 삭제 확인 로직*/
function productSubmitForm(actionType) {
    if (actionType === 'delete') {
        // confirm 창을 통해 사용자에게 삭제 의사를 확인
        alert("question", "확인", "정말로 상품을 삭제하시겠습니까?", function (){
            document.getElementById('actionField').value = 'delete';
            document.getElementById('productForm').submit();
        }, function (){});
    } else if (actionType === 'update') {
        alert("question", "확인", "정말로 상품을 수정하시겠습니까?", function (){
            document.getElementById('actionField').value = 'update';
            document.getElementById('productForm').submit();
        }, function () {});
    }
}

/*문의글 수정, 삭제 확인 로직*/
function boardSubmitForm(actionType) {
    if (actionType === 'delete') {
        alert("question", "확인", "정말로 게시글을 삭제하시겠습니까?", function (){
            document.getElementById('actionField').value = 'delete';
            document.getElementById('boardForm').submit();
        }, function (){});
    } else if (actionType === 'update') {
        alert("question", "확인", "정말로 게시글을 수정하시겠습니까?", function (){
            document.getElementById('actionField').value = 'update';
            document.getElementById('boardForm').submit();
        }, function (){});
    }
}

/*포트원 결제API로 결제진행 로직*/
function requestPay() {
    const productName = document.getElementById("productName").value;
    const memberId = document.getElementById("memberId").value;
    const totalPrice = calculateTotal();
    console.log("Total Price in requestPay:", totalPrice); // 로그로 확인
    const buyerName = document.getElementById("buyerName").value;
    const buyerAddr = document.getElementById("buyerAddr").value;
    const buyerEmail = document.getElementById("buyerEmail").value;
    const buyerTel = document.getElementById("buyerTel").value;


    const cartItems = document.querySelectorAll(".row.mb-4");
    const products = [];

    cartItems.forEach(item => {
        // 체크박스가 선택되었는지 확인
        const isChecked = item.querySelector(".product-checkbox").checked;
        if (isChecked) { // 체크박스가 선택된 상품만 처리
            const productId = item.querySelector("#serialNumber").value;
            const productName = item.querySelector("#productName").value;
            const productPrice = parseFloat(item.querySelector("#productPrice").value);
            const productSize = item.querySelector("#productSize").value;
            const productDescription = item.querySelector("#productDescription").value;

            products.push({
                serialNumber: productId,
                productName: productName,
                productPrice: productPrice,
                productSize: productSize,
                productDescription: productDescription,
            });
        }
    });
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
                alert('success', '완료', '결제가 완료되었습니다.');

                const paymentData = {
                    paymentId: rsp.merchant_uid,
                    impUid: rsp.imp_uid,
                    amount: totalPrice,
                    paymentDate: new Date().toISOString(), // 현재 시간을 ISO 형식으로 설정
                    memberId: memberId,
                    products: products,
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
                            products.forEach(product => {
                                const productElement = document.querySelector(`div[data-product-id="${product.serialNumber}"]`);
                                if (productElement) {
                                    productElement.closest('.row').remove();
                                }
                            });
                        }
                    })
                    .catch((error) => {
                        console.error('결제 정보를 전송하는 중 오류가 발생했습니다: ', error.message);
                    });

                console.log(rsp.data);
            } else {
                alert('결제에 실패하였습니다. 에러내용: ' + rsp.error_msg);
                console.log(rsp);
            }
        }
    );
}

/*포트원 결제API 결제 취소 기능 로직(구현X)*/
function cancelPayment() {
    const impUid = document.getElementById("impUid").value; // 결제 ID를 가져옴
    const cancelReason = document.getElementById("cancelReason").value; // 취소 사유를 가져옴
    const cancelAmount = parseInt(document.getElementById("cancelAmount").value); // 취소 금액을 가져옴
    console.log(impUid + ", " + cancelReason);
    if (!impUid || !cancelReason) {
        alert("결제 ID와 취소 사유를 입력해주세요.");
        return;
    }

    const cancelData = {
        impUid: impUid,
        reason: cancelReason,
        cancel_request_amount: cancelAmount
    };

    fetch('http://localhost:8090/api/payments/cancel', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(cancelData),
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                alert(data.error); // 서버에서 반환한 에러 메시지를 사용자에게 알림
            } else {
                alert('결제가 취소되었습니다.');
                console.log('결제 취소 성공:', data);
            }
        })
        .catch((error) => {
            console.error('결제 취소 요청 중 오류가 발생했습니다: ', error.message);
        });
}

/**
 * 알림창 sweetalert2
 * parameter: (필수) icon			=> 'info', 'success', 'error', 'warning', 'question'.
 * 			  (필수) title			=> 알림 내용.
 * 			  (선택) text			=> 알림 세부 내용. (없는 경우 비우거나 undefined)
 * 			  (선택) confirmCallback	=> 확인 이후 진행할 function. (없는 경우 비우거나 undefined)
 * 			  (선택) cancelCallback	=> 취소 이후 진행할 function. (없는 경우 비우거나 undefined)
 * */
function alert(icon, title, text, confirmCallback, cancelCallback) {
    Swal.fire({
        icon: icon,
        title: title,
        html: text.replace(/\n/g, '<br>'),
        showConfirmButton: true,
        confirmButtonColor: "#3085D6",
        confirmButtonText: "확인",
        showCancelButton: icon === 'question',
        cancelButtonColor: "#DD3333",
        cancelButtonText: "취소",
        allowOutsideClick: false,
        allowEscapeKey: false,
        customClass: {
            container: 'custom-alert-container' // 변경된 클래스
        }
    }).then(function (result) {
        if (result.isConfirmed) { // 확인 시
            if (confirmCallback !== undefined) confirmCallback();
            return true;
        } else if (result.isDenied) { // 취소 시
            if (cancelCallback !== undefined) cancelCallback();
            return false;
        }
    });
}

