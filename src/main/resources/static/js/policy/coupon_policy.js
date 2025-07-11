// 할인타입에 따라 입력창 표시/숨김 토글
function toggleDiscountInputs() {
    var type = document.getElementById('discountType').value;
    var amountGroup = document.getElementById('discountAmountGroup');
    var percentGroup = document.getElementById('discountPercentGroup');
    if (type === 'AMOUNT') {
        amountGroup.classList.remove('hide');
        percentGroup.classList.add('hide');
    } else if (type === 'PERCENT') {
        percentGroup.classList.remove('hide');
        amountGroup.classList.add('hide');
    } else {
        amountGroup.classList.add('hide');
        percentGroup.classList.add('hide');
    }
}
window.onload = toggleDiscountInputs;

document.addEventListener('DOMContentLoaded', () => {
    const valueInput = document.getElementById('valueInput');

    // 폼 제출 전에 displayView 문자열에서 숫자만 추출해서 변환
    document.querySelector('form').addEventListener('submit', (e) => {
        let val = valueInput.value;

        val = val.replace(/[^\d\.]/g, ''); // 숫자와 점(.)만 남기기

        // 변환된 숫자값으로 바꾸기
        valueInput.value = val;
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const toggle = document.getElementById('statusSwitch');
    const statusValue = document.getElementById('statusValue');
    const statusText = document.getElementById('statusText');

    if (toggle && statusValue && statusText) {
        function syncStatus() {
            if (toggle.checked) {
                statusValue.value = 'ACTIVE';
                statusText.textContent = '활성화됨';
            } else {
                statusValue.value = 'INACTIVE';
                statusText.textContent = '비활성화됨';
            }
        }

        syncStatus(); // 초기 상태 반영
        toggle.addEventListener('change', syncStatus);
    }

    // Placeholder 동적 변경
    const valueTypeSelect = document.getElementById('valueTypeSelect');
    const valueInput = document.getElementById('valueInput');

    if (valueTypeSelect && valueInput) {
        function updatePlaceholder() {
            const selected = valueTypeSelect.value;
            valueInput.placeholder = selected === 'RATE'
                ? '예: 0.5 (0.5%)'
                : '예: 5000 (5,000P)';
        }

        updatePlaceholder();
        valueTypeSelect.addEventListener('change', updatePlaceholder);
    }
});