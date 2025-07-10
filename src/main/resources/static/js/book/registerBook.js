// bookAdmin.js
document.addEventListener('DOMContentLoaded', function() {
    // 검색 버튼 이벤트 핸들러
    document.getElementById('search-button').addEventListener('click', async () => {
        const title = document.getElementById('search-input').value;
        if (!title) {
            alert('검색어를 입력해주세요.');
            return;
        }

        const resultsContainer = document.getElementById('results-container');
        resultsContainer.innerHTML = '<p>검색 중...</p>';

        try {
            const response = await fetch(`/admin/book/search/aladin?title=${encodeURIComponent(title)}`);
            if (!response.ok) throw new Error('검색에 실패했습니다.');
            const books = await response.json();
            displayResults(books);
        } catch (error) {
            resultsContainer.innerHTML = `<p class="text-danger">${error.message}</p>`;
        }
    });

    // 검색 결과 표시 함수
    function displayResults(books) {
        const resultsContainer = document.getElementById('results-container');
        resultsContainer.innerHTML = '';

        if (!books || books.length === 0) {
            resultsContainer.innerHTML = '<p>검색 결과가 없습니다.</p>';
            return;
        }

        books.forEach(book => {
            const card = document.createElement('div');
            card.className = 'col-md-6 col-lg-4';
            card.innerHTML = `
                <div class="card h-100 book-card">
                    <div class="card-body">
                        <img src="${book.cover}" alt="책 표지" class="img-fluid mb-3" style="max-width: 150px; border-radius: 8px;">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text">저자: ${book.author}</p>
                        <p class="card-text">출판사: ${book.publisher}</p>
                        <p class="card-text">ISBN: ${book.isbn}</p>
                    </div>
                </div>
            `;
            card.addEventListener('click', () => registerWithAladin(book));
            resultsContainer.appendChild(card);
        });
    }

    // 도서 등록 함수
    async function registerWithAladin(book) {
        if (!confirm(`[${book.title}]\n이 도서를 등록하시겠습니까?`)) return;

        try {
            const response = await fetch('/admin/book/book_register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(book)
            });

            if (response.ok) {
                const result = await response.json();
                window.location.href = result.redirectUrl;
            } else {
                const contentType = response.headers.get('Content-Type');
                if (contentType?.includes('application/json')) {
                    const error = await response.json();
                    alert(`등록 실패: ${error.message || '알 수 없는 오류'}`);
                } else {
                    const errorText = await response.text();
                    console.error('HTML Error:', errorText);
                    alert('서버 오류가 발생했습니다.');
                }
            }
        } catch (error) {
            alert(`오류 발생: ${error.message}`);
        }
    }
});