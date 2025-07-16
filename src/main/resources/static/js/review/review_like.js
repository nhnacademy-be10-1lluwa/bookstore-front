document.addEventListener('click', function (e) {
    if (e.target.classList.contains('like-btn') || e.target.closest('.like-btn')) {
        const button = e.target.closest('.like-btn');
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

                if (data.likedByMe) {
                    button.classList.add('liked');
                    likeIcon.textContent = '❤️';
                } else {
                    button.classList.remove('liked');
                    likeIcon.textContent = '🤍';
                }

                likeCountSpan.textContent = data.likeCount;
            })
            .catch(error => console.error('Error toggling like:', error));
    }
});