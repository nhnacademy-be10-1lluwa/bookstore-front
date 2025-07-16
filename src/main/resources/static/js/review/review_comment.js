// 댓글 토글 버튼 클릭 시 댓글 불러오기/숨기기
function toggleComments(button, bookId) {
    const reviewId = button.getAttribute('data-review-id');
    const commentBox = document.getElementById(`comment-box-${reviewId}`);
    const commentForm = document.getElementById(`comment-form-${reviewId}`);
    const isVisible = commentBox.style.display !== 'none';

    if (isVisible) {
        // 댓글 숨기기
        commentBox.innerHTML = '';
        commentBox.style.display = 'none';
        commentForm.style.display = 'none';
        button.innerText = '댓글 보이기';
    } else {
        // 댓글 불러오기
        loadComments(reviewId, bookId)
            .then(() => {
                commentBox.style.display = 'block';
                commentForm.style.display = 'block';
                button.innerText = '댓글 접기';
            })
            .catch(error => console.error('Error loading comments:', error));
    }
}

// 댓글 불러오기 함수 (토글과 등록 후 재갱신에서 사용)
function loadComments(reviewId, bookId) {
    const commentBox = document.getElementById(`comment-box-${reviewId}`);
    return fetch(`/books/${bookId}/reviews/${reviewId}/comments`)
        .then(res => {
            if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
            return res.json();
        })
        .then(data => {
            commentBox.innerHTML = data.map(comment => `
                <div class="comment">
                    <strong>${comment.memberId}</strong>
                    <span>${comment.commentDate}</span>
                    <p>${comment.commentContents}</p>
                </div>
            `).join('');
        });
}

// 댓글 등록
function submitComment(reviewId, bookId) {
    const textarea = document.getElementById(`comment-input-${reviewId}`);
    const content = textarea.value.trim();

    if (!content || content.length > 500) {
        alert('댓글은 1자 이상 500자 이하로 입력해주세요.');
        return;
    }

    fetch(`/books/${bookId}/reviews/${reviewId}/comments`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ commentContents: content })
    })
        .then(() => {
            textarea.value = '';
            loadComments(reviewId, bookId).then(() => {
                document.getElementById(`comment-box-${reviewId}`).style.display = 'block';
                document.getElementById(`comment-form-${reviewId}`).style.display = 'block';
                const toggleButton = document.querySelector(`[data-review-id="${reviewId}"]`);
                if (toggleButton) toggleButton.innerText = '댓글 접기';
            });
        })
}