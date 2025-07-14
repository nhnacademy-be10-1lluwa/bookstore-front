// coupon.js

// 페이지가 로드될 때 한 번 실행 (현재 쿠폰타입 값 기반)
document.addEventListener("DOMContentLoaded", function() {
    toggleCouponTypeFields(); // 페이지 진입 시에도 반영
});

// 쿠폰 타입 변경 시 동작
function toggleCouponTypeFields() {
    // select 값 가져오기
    const couponType = document.getElementById("couponType").value;

    // 그룹 DOM 가져오기
    const booksIdGroup = document.getElementById("booksIdGroup");
    const categoryIdGroup = document.getElementById("categoryIdGroup");

    // 기본적으로 숨김
    booksIdGroup.classList.add("hide");
    categoryIdGroup.classList.add("hide");

    // 타입에 따라 보임/숨김 결정
    if (couponType === "BOOKS") {
        booksIdGroup.classList.remove("hide");     // 도서ID 입력란 표시
    } else if (couponType === "CATEGORY") {
        categoryIdGroup.classList.remove("hide");  // 카테고리ID 입력란 표시
    }
}

function openBookModal() {
    // 진짜 팝업 or 모달 둘다 가능, 여기선 모달로 예시
    const modal = document.getElementById("bookModal");
    if (modal) {
        modal.classList.remove("hide");
    } else {
        alert("모달 엘리먼트를 찾지 못했습니다. id=bookModal 위치를 확인하세요.");
    }
}

function selectBook(bookId, bookTitle) {
    document.getElementById("booksId").value = bookId;
    // 모달 닫기
    document.getElementById("bookModal").classList.add("hide");
    // 필요하면 선택한 도서 제목도 어디 표시
}

function selectBookWithData(btn) {
    // data-book-id, data-book-title 속성 값 읽기
    var bookId = btn.getAttribute('data-book-id');
    var bookTitle = btn.getAttribute('data-book-title');
    selectBook(bookId, bookTitle); // 기존 selectBook 함수를 그대로 쓴다
}


function filterBookList() {
    const input = document.getElementById("bookSearchInput").value.toLowerCase();
    const bookList = document.getElementById("bookList");
    const items = bookList.getElementsByTagName("li");
    for (let i = 0; i < items.length; i++) {
        const title = items[i].querySelector("span").textContent.toLowerCase();
        items[i].style.display = title.includes(input) ? "" : "none";
    }
}



