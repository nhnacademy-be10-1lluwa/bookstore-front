document.querySelectorAll('.tab-button').forEach(button => {
    button.addEventListener('click', function () {
        const tabName = this.dataset.tab;
        document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
        this.classList.add('active');
        document.getElementById(tabName).classList.add('active');
    });
});

function changeMainImage(thumbnail) {
    const mainImage = document.getElementById('main-image');
    mainImage.src = thumbnail.src;
    document.querySelectorAll('.thumbnail').forEach(t => t.classList.remove('active'));
    thumbnail.classList.add('active');
}

document.addEventListener('DOMContentLoaded', () => {
    const qtyInput = document.querySelector('.cart-quantity');
    const hiddenQtyInputs = document.querySelectorAll('.hidden-quantity');

    if (!qtyInput || hiddenQtyInputs.length === 0) {
        console.warn('수량 입력 또는 hidden input을 찾을 수 없어요');
        return;
    }

    // 수량이 변경될 때마다 모든 hidden input에 값을 복사
    qtyInput.addEventListener('input', () => {
        hiddenQtyInputs.forEach(input => {
            input.value = qtyInput.value;
        });
    });

    // 페이지 로드시 초기 수량도 반영
    hiddenQtyInputs.forEach(input => {
        input.value = qtyInput.value;
    });
});