function payment() {
    // 아임포트 결제 객체 초기화
    let IMP = window.IMP;
    IMP.init("imp11615807");

    // 구매자 정보 설정
    let buyer_name = "John Doe"; // 예시로 구매자 이름 설정
    let email = "buyer@example.com"; // 예시로 이메일 설정
    let hp = "010-1234-5678"; // 예시로 전화번호 설정
    let addr = "Seoul, Korea"; // 예시로 주소 설정

    // 선택된 충전 금액 요소 찾기
    let selectedAmountElement = document.querySelector('.amount.selected');
    if (!selectedAmountElement) {
        alert("충전할 포인트 금액을 선택해 주세요.");
        return;
    }


    // 선택된 충전 금액 및 기타 정보 가져오기
    let dataPointsString = selectedAmountElement.getAttribute('data-points');
    let selectedAmount = parseInt(dataPointsString.replace(/,/g, ''), 10); // 포인트 금액
    let amount = parseInt(selectedAmountElement.getAttribute('data-price').replace(/,/g, ''), 10); // 실제 결제 금액
    let PCategory = selectedAmountElement.getAttribute('data-category'); // 카테고리 정보
    let PExplain = "포인트충전"; // 설명

    // 아임포트 결제 요청
    IMP.request_pay({
        pg: 'kakaopay', // 결제 PG사
        pay_method: 'card', // 결제 방법
        merchant_uid: 'merchant_' + new Date().getTime(), // 고유 주문 번호
        name: 'DevHub_' + selectedAmount, // 결제 상품명
        amount: amount, // 결제 금액
        buyer_email: email, // 구매자 이메일
        buyer_name: buyer_name, // 구매자 이름
        buyer_tel: hp, // 구매자 전화번호
        buyer_addr: addr, // 구매자 주소
    }, function (rsp) {
        if (rsp.success) {
            console.log('결제 성공', rsp);
            alert('예약 결제가 완료되었습니다!');

            // 서버에 포인트 충전 요청
            $.ajax({
                url: "/charge",
                type: "POST",
                data: JSON.stringify({
                    MPoint: selectedAmount, // 충전할 포인트

                    email: email, // 이메일
                    order: { // 주문 정보
                        PName: dataPointsString, // 상품명
                        PPrice: amount, // 가격
                        PCategory: PCategory, // 카테고리
                        PExplain: PExplain // 설명
                    }
                }),
                contentType: "application/json", // 명확히 Content-Type을 설정
                dataType: "json", // 기대하는 데이터 형식 설정
                success: function (response) {
                    console.log(response);
                    let redirectUrl = response.redirectUrl; // 변수 선언 및 초기화
                    if (redirectUrl) {
                        console.log('Redirecting to:', redirectUrl);
                        window.location.href = redirectUrl; // 리디렉션 URL로 이동
                    }
                    // 결제 완료 후 포인트 업데이트
                    updatePoint();
                },
                error: function (xhr, status, error) {
                    console.error("Error charging points:", status, error);
                }
            });
            $("#payment").submit();
        } else {
            let msg = '결제에 실패하였습니다.\n';
            msg += rsp.error_msg ? rsp.error_msg : '오류가 발생했습니다.';
            alert(msg);
        }

        // 사용자 포인트 업데이트 함수
        function updatePoint() {
            $.ajax({
                url: "/getPoints", // 포인트 조회 API 호출
                type: "GET",
                success: function (response) {
                    let userPoints = response; // /getPoints는 포인트 숫자를 그대로 반환
                    $("#loginMPoint").text(userPoints);
                },
                error: function (xhr, status, error) {
                    console.error("Failed to fetch user points:", status, error);
                }
            });
        }
    })}
