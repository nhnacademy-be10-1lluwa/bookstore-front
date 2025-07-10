
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
