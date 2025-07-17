document.addEventListener('click', function (e) {
    const button = e.target.closest('.like-btn');
    if (!button) {
        return;
    }

    const bookId = button.getAttribute('data-book-id');
    const reviewId = button.getAttribute('data-review-id');

    fetch(`/books/${bookId}/reviews/${reviewId}/likes`, {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(data => {
            const likeIcon = button.querySelector('.like-icon');
            const likeCountSpan = document.getElementById(`like-count-${reviewId}`);

            if (likeIcon) {
                likeIcon.textContent = data.likedByMe ? '❤️' : '🤍';
            }

            if (likeCountSpan) {
                likeCountSpan.textContent = data.likeCount;
            }

            button.classList.toggle('liked', data.likedByMe);
        })
        .catch(error => console.error('Error toggling like:', error));
});
