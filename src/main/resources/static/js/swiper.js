new Swiper(".mySwiper", {
  slidesPerView: 3,
  spaceBetween: 30,
  loop: true,
  pagination: {
    el: ".swiper-pagination",
    clickable: true,
  },
  breakpoints: {
    368: {
      slidesPerView: 1
    },
    468: {
      slidesPerView: 1
    },
    568: {
      slidesPerView: 1
    },
    668: {
      slidesPerView: 1
    },
    768: {
      slidesPerView: 1
    },
    992: {
      slidesPerView: 3
    }
  }

});

