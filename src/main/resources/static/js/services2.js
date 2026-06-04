$(document).ready(function () {
    const modals = {}; // modals 변수 정의 및 초기화
    const buttons = {}; // buttons 변수 정의 및 초기화
    const modalIds = [
        "myModal22", "myModal23", "myModal24", "myModal25",
        "myModal26", "myModal28", "myModal29", "myModal30",
        "myModal31", "myModal32", "myModal33", "myModal34",
        "myModal35", "myModal36"
    ];
    const buttonIds = [
        "openModalBtn-01", "openModalBtn-02", "openModalBtn-03", "openModalBtn-04",
        "openModalBtn-05", "openModalBtn-07", "openModalBtn-08", "openModalBtn-09",
        "openModalBtn-10", "openModalBtn-11", "openModalBtn-12", "openModalBtn-13",
        "openModalBtn-14", "openModalBtn-15"
    ];

    // Initialize modals and buttons
    modalIds.forEach(id => modals[id] = document.getElementById(id));
    buttonIds.forEach((id, index) => buttons[id] = document.getElementById(id));

    let currentPoints = parseInt($("#loginMPoint").text(), 10);
    let PExplain = "UI소스코드";
    let PName = "UI소스";
    let PPrice = 200;
    let categoryName = "UI";
    let email = localStorage.getItem('loginMEmail');
    let productId = localStorage.getItem('PId');
    if (productId === null || productId === '') {
        productId = 0;
    } else {
        productId = parseInt(productId, 10); // 정수로 변환
    }
    let memberId = localStorage.getItem('loginMId');

    function handleButtonClick(event, modalId) {
        const confirmResult = confirm("200Point를 사용해 결제하시겠습니까?");

        if (!confirmResult) {
            event.preventDefault(); // 확인이 아닌 경우 기본 동작(페이지 이동) 중지
            return;
        }

        if (currentPoints < PPrice) {
            alert("포인트가 부족합니다.");
            return; // 포인트가 부족하면 함수 종료
        }

        // 포인트 차감 AJAX 호출
        $.ajax({
            url: "/deductPoints",
            method: 'POST',
            data: { MPoint: PPrice, MId: memberId }, // MId 추가
            success: function (response) {
                console.log(response);

                if (typeof updatePoint === "function") {
                    updatePoint();
                } else {
                    console.error("updatePoint 함수가 정의되어 있지 않습니다.");
                }

                if (modals[modalId]) {
                    modals[modalId].style.display = "block";
                } else {
                    console.error(modalId + " 요소를 찾을 수 없습니다.");
                }

                // 제품 정보 전송 AJAX 호출을 포인트 차감 성공 후 실행
                $.ajax({
                    url: "/getproduct",
                    method: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify({
                        email: email,
                        order: {
                            PName: PName,
                            PPrice: PPrice,
                            PCategory: categoryName,
                            PExplain: PExplain,
                            PId: productId
                        }
                    }),
                    success: function (response) {
                        console.log(response);
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                    }
                });

                // 사용자 인터페이스 기록 AJAX 호출 (새로운 히스토리 생성)
                $.ajax({
                    url: "/addHistory",
                    method: 'POST',
                    data: { pids: [productId] },
                    success: function(response) {
                        console.log("Success response:", response);
                    },
                    error: function(xhr, status, error) {
                        console.error("Error: " + error);
                        console.error("Status: " + status);
                        console.error("XHR: ", xhr);
                    }
                });
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    }

    // Attach event listeners to buttons
    buttonIds.forEach((id, index) => {
        buttons[id].onclick = function (event) {
            handleButtonClick(event, modalIds[index]);
        };
    });

    // 모달 닫기 버튼
    const closeModalButtons = document.getElementsByClassName("close");
    Array.from(closeModalButtons).forEach((button, index) => {
        button.onclick = function () {
            modals[modalIds[index]].style.display = "none";
        };
    });

    // 모달 외부를 클릭하여 닫기
    window.onclick = function (event) {
        modalIds.forEach(id => {
            if (event.target === modals[id]) {
                modals[id].style.display = "none";
            }
        });
    };
});

// 내용 보여주기 함수
function showContent(infoId) {
    document.querySelectorAll('.content').forEach(content => content.classList.remove('active'));
    const selectedContent = document.getElementById(infoId);
    if (selectedContent) {
        selectedContent.classList.add('active');
    }
}

// 코드 복사 함수
function copyCode(elementId) {
    const codeElement = document.getElementById(elementId);
    const codeText = codeElement.textContent;

    const tempTextArea = document.createElement("textarea");
    tempTextArea.value = codeText;
    document.body.appendChild(tempTextArea);
    tempTextArea.select();
    document.execCommand("copy");
    document.body.removeChild(tempTextArea);

    alert("코드 복사 완료♥");
}

function updatePoint() {
    // 예시로, 사용자의 포인트를 얻어오는 Ajax 요청을 보낼 수 있습니다.
    $.ajax({
        url: "/getPoints",
        type: "GET",
        success: function (response) {
            console.log(response);
            $("#loginMPoint").text(response);
        },
        error: function (xhr, status, error) {
            console.error("Failed to fetch user points:", status, error);
        }
    });
}

$('#services').click(() => {
    location.href = "/services";
});