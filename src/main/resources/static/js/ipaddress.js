// IP 정보 표시 요소가 있는 페이지에서만 실행 (요소가 없는 페이지에서 null 참조로 스크립트가 중단되지 않도록)
const ipAddressEl = document.getElementById('ip-address');

if (ipAddressEl) {
    fetch('https://ipinfo.io/json?token=d0e51168751210')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            ipAddressEl.textContent = `IP 주소: ${data.ip}`;
            document.getElementById('ip-location').textContent = `위치: ${data.city}, ${data.region}, ${data.country}`;
            document.getElementById('ip-isp').textContent = `ISP: ${data.org}`;
        })
        .catch(error => {
            console.error('Error fetching IP address:', error);
            ipAddressEl.textContent = 'Error fetching IP address: ' + error.message;
        });
}
