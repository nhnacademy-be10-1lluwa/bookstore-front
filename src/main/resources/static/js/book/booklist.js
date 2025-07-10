async function addToCart(bookId) {
    const qtyInput = document.getElementById(`qty-${bookId}`);
    const amount   = parseInt(qtyInput.value, 10);

    console.log('addToCart 호출', { bookId, amount }); // 이 로그가 찍히는지 확인!

    const res = await fetch('/cart/book', {
        method: 'POST',
        credentials: 'same-origin',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ bookId, amount }) // ★★★ 이 부분이 중요! memberId가 없어요!
    });

    console.log('/cart/book 응답 상태:', res.status); // 응답 상태 코드 확인!

    if (res.ok) {
        alert('장바구니에 추가되었습니다.');
        return;
    }
    const err = await res.text(); // ★★★ 에러 메시지 확인 필수!
    alert(`추가 실패: ${err}`);
}