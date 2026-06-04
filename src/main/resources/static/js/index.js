/* 슬라이더 무한 반복 js */
var swiper = new Swiper('.mySwiper', {
    slidesPerView: 3, /* 한 번에 보이는 슬라이드 수 */
    spaceBetween: 10, /* 슬라이드 간 공간 */
    loop: true, /* 무한 반복 */
    autoplay: {
        delay: 2500, /* 자동 전환 딜레이 2500ms */
        disableOnInteraction: false, /* 사용자 상호작용 시 자동 재생 유지 */
    },
    pagination: {
        el: '.swiper-pagination', /* 페이징 요소 선택자 */
        clickable: true, /* 클릭 가능 */
    },
    navigation: {
        nextEl: '.swiper-button-next', /* 다음 버튼 선택자 */
        prevEl: '.swiper-button-prev', /* 이전 버튼 선택자 */
    },
    on: {
        init: function () {
            // 초기 로딩 시 슬라이드 애니메이션을 제거합니다.
            this.slides.forEach(function (slide) {
                slide.style.opacity = 1;
                slide.style.transform = 'translateY(0)';
            });
        },
        slideChangeTransitionEnd: function () {
            // 슬라이드 전환 시 애니메이션 적용
            this.slides.forEach(function (slide) {
                slide.style.opacity = 1;
                slide.style.transform = 'translateY(0)';
            });
        }
    }
});


/* 스크롤을 내리면 애니메이션 효과가 나타나도록 하는 js */
document.addEventListener('DOMContentLoaded', function () {
    const elements = document.querySelectorAll('.fade-in');

    function checkVisibility() {
        const windowHeight = window.innerHeight;

        elements.forEach(element => {
            const rect = element.getBoundingClientRect();
            if (rect.top < windowHeight - 50) {
                element.classList.add('visible');
            }
        });
    }

    window.addEventListener('scroll', checkVisibility);
    window.addEventListener('resize', checkVisibility);

    // 초기 로드 시에도 체크
    checkVisibility();
});