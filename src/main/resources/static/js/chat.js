let stompClient = null;

// 페이지 로드 시 WebSocket 연결 및 Enter 키 이벤트 리스너 설정
$(document).ready(function () {
    connect();

    // 로그인하지 않았으면 입력·전송 버튼 비활성화 (#user 는 session.loginId)
    if (!$("#user").text().trim()) {
        $("#message").prop("disabled", true).attr("placeholder", "로그인 후 이용 가능합니다");
        $("#sendBtn").prop("disabled", true);
    }

    $("#message").keypress(function (event) {
        if (event.which === 13) { // Enter 키를 누르면 메시지 전송
            event.preventDefault();
            sendMessage();
        }
    });
});

// WebSocket 연결 함수
function connect() {
    let socket = new SockJS('/ws'); // SockJS를 사용하여 WebSocket 생성
    stompClient = Stomp.over(socket); // STOMP 프로토콜 사용
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // 서버로부터의 메시지를 구독
        stompClient.subscribe('/topic/public', function (messageOutput) {
            let message = JSON.parse(messageOutput.body);
            handleMessage(message);
        });
    }, function (error) {
        console.log('Websocket connection error. Trying to reconnect...');
        setTimeout(connect, 10000); // 10초 후 재연결 시도
    });
}

// 메시지 전송 함수
function sendMessage() {
    let messageContent = $("#message").val().trim(); // 입력된 메시지 내용 가져오기
    let sender = $("#user").text().trim(); // 현재 사용자 이름(로그인 ID) 가져오기
    let profile = $("#profile").attr("src"); // 현재 사용자 프로필 이미지 가져오기

    // 로그인한 사용자만 전송 가능 (Enter·버튼 양쪽 모두 적용)
    if (!sender) {
        alert("로그인 후 이용 가능합니다.");
        return;
    }

    if (messageContent && stompClient) {
        let chatMessage = {
            profile: profile,
            sender: sender,
            content: messageContent,
            type: 'CHAT'
        };
        // 서버로 메시지 전송
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        $("#message").val(""); // 메시지 입력 필드 초기화
    }
}

// 서버로부터의 메시지를 처리하는 함수
function handleMessage(message) {
    if (message.type === 'CHAT') {
        showMessageOutput(message);
    } else if (message.type === 'JOIN') {
        showJoinMessage(message);
    } else if (message.type === 'LEAVE') {
        showLeaveMessage(message);
    }
}

// 채팅 메시지를 화면에 표시하는 함수
function showMessageOutput(messageOutput) {
    let messageContainer = $("<div>").addClass("chat-message-container");
    let messageElement = $("<div>").addClass("chat-message");
    let profileImg = $("<img>").addClass("profile-image").attr("src", messageOutput.profile)
        .attr("onclick", `showProfileModal('${messageOutput.sender}', '${messageOutput.profile}')`);
    let contentElement = $("<div>").addClass("message-content").text(messageOutput.content);
    let timeElement = $("<div>").addClass("time").text(formatMessageTime(new Date()));

    messageElement.append(contentElement).append(timeElement);
    messageContainer.append(profileImg).append(messageElement);

    // 현재 사용자와 메시지 보낸 사람이 동일한 경우
    if (messageOutput.sender === $("#user").text()) {
        messageContainer.addClass("self");
    } else {
        messageContainer.addClass("other");
    }

    $("#messages").append(messageContainer);
    $("#messages").scrollTop($("#messages")[0].scrollHeight); // 스크롤을 최신 메시지로 이동
}

// 사용자가 채팅에 입장했을 때의 메시지를 표시하는 함수
function showJoinMessage(message) {
    let joinElement = $("<div>").addClass("join-message").text(message.sender + " 님이 입장하셨습니다.");
    $("#messages").append(joinElement);
    $("#messages").scrollTop($("#messages")[0].scrollHeight);
}

// 사용자가 채팅에서 퇴장했을 때의 메시지를 표시하는 함수
function showLeaveMessage(message) {
    let leaveElement = $("<div>").addClass("leave-message").text(message.sender + " 님이 퇴장하셨습니다.");
    $("#messages").append(leaveElement);
    $("#messages").scrollTop($("#messages")[0].scrollHeight);
}

// 이모티콘 패널을 토글하는 함수
function toggleEmojiPanel() {
    $("#emojiPopup").toggleClass("active");
}

// 메시지 입력 필드에 이모티콘을 추가하는 함수
function addEmoji(emoji) {
    let currentMessage = $("#message").val();
    $("#message").val(currentMessage + emoji);
    $("#emojiPopup").removeClass("active"); // 이모티콘 패널 닫기
}

// 메시지 시간을 포맷하는 함수
function formatMessageTime(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    return hours + ':' + minutes;
}

// 채팅 창을 토글하는 함수
function toggleChat() {
    var chatContainer = document.getElementById('chatContainer');
    if (chatContainer.style.display === 'none' || chatContainer.style.display === '') {
        chatContainer.style.display = 'flex';
    } else {
        chatContainer.style.display = 'none';
    }
}

// 프로필 모달을 표시하는 함수
function showProfileModal(name, profileImg) {
    $("#modalProfileName").text(name);
    $("#modalProfileImg").attr("src", profileImg);
    $("#profileModal").css("display", "block");
}

// 프로필 모달을 닫는 함수
function closeModal() {
    $("#profileModal").css("display", "none");
}
