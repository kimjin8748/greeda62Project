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

app.controller('MemberController', ['$scope', function($scope) {
    $scope.showSignIn = !signUpFailed;
}]);

function calculateTotal() {
    let totalPrice = 0; // 총 가격 초기화
    document.querySelectorAll('.product-checkbox').forEach((checkbox) => {
        if (checkbox.checked) { // 체크박스가 체크되어 있는 경우
            const row = checkbox.closest('.row'); // 해당 체크박스가 속한 row 요소 찾기
            const quantityInput = row.querySelector('.product-quantity'); // 해당 row에서 수량 입력 필드 찾기
            const price = parseInt(checkbox.dataset.price); // 상품 가격 가져오기
            const quantity = parseInt(quantityInput.value); // 상품 수량 가져오기
            totalPrice += price * quantity; // 총 가격 업데이트
        }
    });
    document.getElementById('total-price').innerText = totalPrice + '원'; // 총 가격 표시 업데이트
}
