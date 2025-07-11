async function clearCart() {
    if (!confirm('장바구니를 비우시겠습니까?')) return;
    try {
        const res = await fetch(`/cart`, {
            method: 'DELETE',
            credentials: 'same-origin'
        });
        if (!res.ok) {
            const errorMessage = await res.text();
            throw new Error(errorMessage);
        }
        // 장바구니 비우기 성공 후 페이지 새로고침
        // 서버에서 비워진 장바구니 상태를 포함하는 새 HTML을 보내줄 것입니다.
        window.location.reload();
    } catch (err) {
        alert(`장바구니 비우기 실패: ${err.message}`);
    }
}


// ★★★ removeBookFromCart 함수 (JS -> Front Controller로 AJAX 요청) ★★★
async function removeBookFromCart(bookId) {
    if (!confirm('선택하신 도서를 장바구니에서 삭제하시겠습니까?')) {
        return;
    }

    try {
        // DELETE 요청을 bookstore-front의 컨트롤러로 보냅니다. (게이트웨이를 거침)
        const res = await fetch(`/cart/book`, { // ★ bookstore-front 컨트롤러의 새 경로
            method: 'DELETE',
            credentials: 'same-origin', // 인증 쿠키 포함
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({bookId: bookId})
        });

        if (res.ok) { // HTTP 응답 상태 코드가 2xx (200 OK, 204 No Content 등) 이면 성공
            alert('장바구니에서 도서가 삭제되었습니다.');
            window.location.reload(); // 페이지 새로고침하여 변경된 장바구니 상태 반영
        } else {
            // 서버에서 보낸 오류 메시지 (텍스트)를 받아서 사용자에게 알림
            const errorMessage = await res.text();
            throw new Error(errorMessage);
        }
    } catch (err) {
        alert(`도서 삭제 실패: ${err.message}`);
        console.error('장바구니 도서 삭제 오류:', err); // 개발자 콘솔에 상세 오류 로깅
    }
}