const cartButtons = document.querySelectorAll('.add-to-cart-btn');

cartButtons.forEach(button => {
    button.addEventListener('click', function() {
        const title = this.dataset.title;
        const author = this.dataset.author;
    });
});

// 도서 상세페이지 (인기 도서)
document.querySelectorAll('.book-item').forEach(item => {
    item.addEventListener('click', () => {
        const bookIsbn = item.getAttribute('data-id');
        if (bookIsbn) {
            window.location.href = `/books/isbn/${bookIsbn}`;
        }
    });
});