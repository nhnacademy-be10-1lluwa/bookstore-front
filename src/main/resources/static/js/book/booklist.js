const resultsContainer = document.getElementById('book-list');
const memberId = 1; // 테스트용 회원 ID

function displayResults(books) {
    resultsContainer.innerHTML = '';

    if (books.length === 0) {
        resultsContainer.innerHTML = '<p>등록된 도서가 없습니다.</p>';
        return;
    }

    books.forEach(book => {
        const price = book.salePrice
            ? book.salePrice.toLocaleString() + '원'
            : '가격 정보 없음';

        resultsContainer.innerHTML += `
        <div class="book-card" data-id="${book.id}">
          <img src="${book.imgUrl}" alt="${book.title} 표지"
               onerror="this.onerror=null;this.src='https://via.placeholder.com/240x336?text=No+Image';">
          <div class="book-info">
            <h2>${book.title}</h2>
            <p><strong>저자:</strong> ${book.author}</p>
            <p><strong>출판사:</strong> ${book.publisher}</p>
            <p class="book-price">${price}</p>
          </div>
          <div class="action">
            <input type="number" id="qty-${book.id}" value="1" min="1">
            <button onclick="addToCart(${book.id})">장바구니 담기</button>
          </div>
        </div>`;
    });
}

function addToCart(bookId) {
    const qty = parseInt(document.getElementById(`qty-${bookId}`).value, 10);
    fetch(`/cart`, {
        method: 'POST',
        credentials: 'same-origin',
        body: JSON.stringify({ bookId, amount: qty })
    })
        .then(res => {
            if (res.ok) alert('장바구니에 추가되었습니다.');
            else return res.text().then(text => { throw new Error(text); });
        })
        .catch(err => alert(`추가 실패: ${err.message}`));
}

// 페이지 로드시 전체 도서 불러오기
fetch('/books')
    .then(res => res.json())
    .then(data => displayResults(data))
    .catch(err => {
        resultsContainer.innerHTML = '<p>도서 목록을 불러오지 못했습니다.</p>';
        console.error(err);
    });