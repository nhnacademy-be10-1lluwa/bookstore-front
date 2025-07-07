const memberId = 1; // 실제 로그인된 회원ID로 교체

async function loadCart() {
    try {
        const res = await fetch(`/cart`, {
            credentials: 'same-origin'
        });
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const cart = await res.json();
        renderCart(cart);
    } catch (err) {
        alert(`장바구니를 불러오는 중 오류: ${err.message}`);
    }
}

function renderCart(cart) {
    const tbody = document.querySelector('#cart-table tbody');
    tbody.innerHTML = '';
    let total = 0;

    // ★ DTO에서 바로 salePrice, imgUrl 사용
    cart.books.forEach(book => {
        const sub = book.salePrice * book.amount;
        total += sub;
        const tr = document.createElement('tr');
        tr.innerHTML = `
                <td><img src="${book.imgUrl}" alt="${book.title}" width="60"></td>
                <td>${book.title}</td>
                <td>${book.salePrice.toLocaleString()}원</td>
                <td>${book.amount}</td>
                <td>${sub.toLocaleString()}원</td>
            `;
        tbody.appendChild(tr);
    });

    document.getElementById('total').textContent =
        `총합: ${total.toLocaleString()}원`;
}

async function clearCart() {
    if (!confirm('장바구니를 비우시겠습니까?')) return;
    try {
        const res = await fetch(`/cart`, {
            method: 'DELETE',
            credentials: 'same-origin'
        });
        if (!res.ok) throw new Error(await res.text());
        loadCart();
    } catch (err) {
        alert(`비우기 실패: ${err.message}`);
    }
}

window.addEventListener('DOMContentLoaded', loadCart);