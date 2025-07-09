function addToCart(bookId) {
    const qtyInput = document.getElementById(`qty-${bookId}`);
    const amount = parseInt(qtyInput.value, 10);

    fetch('/cart', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'same-origin',
        body: JSON.stringify({ bookId, amount })
    })
        .then(res => {
            if (res.ok) {
                alert('장바구니에 추가되었습니다.');
            } else {
                return res.text().then(text => { throw new Error(text); });
            }
        })
        .catch(err => alert(`추가 실패: ${err.message}`));
}
