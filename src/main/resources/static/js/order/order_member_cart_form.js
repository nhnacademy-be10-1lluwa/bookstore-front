// (기존 코드와 동일) CSS 및 다음 우편번호 API 관련 코드는 그대로 둡니다.
const style = document.createElement('style');
style.textContent = '.selected-address-preview{margin-bottom:6px;display:block;}';
document.head.appendChild(style);

window.openPostcode = function () {
    new daum.Postcode({
        oncomplete: function (data) {
            if (data.userSelectedType !== 'R') {
                alert("도로명 주소로 선택해주세요! 🛣️");
                return;
            }
            let roadAddr = data.roadAddress;
            let extraAddr = '';
            if (data.bname && /[동|로|가]$/g.test(data.bname)) {
                extraAddr += data.bname;
            }
            if (data.buildingName) {
                extraAddr += (extraAddr ? ', ' + data.buildingName : data.buildingName);
            }
            const fullAddr = extraAddr ? `${roadAddr} (${extraAddr})` : roadAddr;
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('address').value = fullAddr;
            document.getElementById('detailAddress').focus();
        }
    }).open();
};


document.addEventListener('DOMContentLoaded', () => {

    // ===== 유틸리티 및 초기화 함수들 =====
    function toLocalISOString(date) {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    (function setDefaultDeliveryDate() {
        const deliveryInput = document.getElementById('deliveryDate');
        if (!deliveryInput) return;
        const today = new Date();
        const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
        const tomorrowString = toLocalISOString(tomorrow);
        deliveryInput.min = tomorrowString;
        if (!deliveryInput.value) {
            deliveryInput.value = tomorrowString;
        }
    })();

    (function setDefaultUsedPoint() {
        const usedInput = document.querySelector('input[name="usedPoint"]');
        if (usedInput && usedInput.value.trim() === '') {
            usedInput.value = 0;
        }
    })();

    // ===== DOM 요소 캐싱 =====
    const form = document.querySelector('.order-form');
    const itemSummary = document.querySelector('.item-summary');
    const pointBalance = Number(document.querySelector('.point-use strong')?.getAttribute('data-balance') || 0);

    // =============================================
    // ===== ✨ 핵심 로직: 쿠폰 및 금액 계산 ✨ =====
    // =============================================

    function updateCouponAvailability() {
        const usedMemberCouponIds = new Set();
        document.querySelectorAll('select[id^="couponSelect_"]').forEach(select => {
            if (select.value) {
                usedMemberCouponIds.add(select.value);
            }
        });

        document.querySelectorAll('select[id^="couponSelect_"]').forEach(currentSelect => {
            const currentSelectedValue = currentSelect.value;
            currentSelect.querySelectorAll('option').forEach(option => {
                if (!option.value || option.value === currentSelectedValue) {
                    option.style.display = '';
                    return;
                }
                if (usedMemberCouponIds.has(option.value)) {
                    option.style.display = 'none';
                } else {
                    option.style.display = '';
                }
            });
        });
    }

    /**
     * ✅ [수정] 전체 주문 금액을 다시 계산하는 함수
     */
    function calculateOrder() {
        let booksSubtotal = 0;
        let totalPackagingPrice = 0;
        let totalDiscount = 0;

        document.querySelectorAll('.item-summary .item-options-group').forEach((itemGroup, index) => {
            const quantityInput = itemGroup.querySelector(`input[type="number"]`);
            const quantity = Number(quantityInput?.value || 1);

            // [수정 1-1] 가격을 가져오는 선택자 수정 (parentElement를 통해 부모로 올라가서 찾기)
            const priceSpan = itemGroup.parentElement.querySelector('p > span');
            const unitPrice = Number(priceSpan?.textContent.replace(/[^0-9]/g, '') || 0);

            const packagingSelect = itemGroup.querySelector(`select[name="cartItems[${index}].packagingId"]`);
            const pkgPrice = Number(packagingSelect?.selectedOptions[0]?.dataset.price || 0);

            // [수정 1-2] 사용자의 계산 공식 (단가+포장비)*수량 을 반영
            // 책 가격과 포장비 모두 수량을 곱해서 합산합니다.
            booksSubtotal += unitPrice * quantity;
            totalPackagingPrice += pkgPrice * quantity;

            const couponSelect = itemGroup.querySelector(`#couponSelect_${index}`);
            if (couponSelect && couponSelect.value) {
                const selectedOption = couponSelect.selectedOptions[0];
                const amount = Number(selectedOption.dataset.amount || 0);
                const percent = Number(selectedOption.dataset.percent || 0);

                if (percent > 0) {
                    totalDiscount += (unitPrice * quantity) * (percent / 100);
                } else {
                    totalDiscount += amount;
                }
            }
        });

        const subtotal = booksSubtotal + totalPackagingPrice;
        const usedPoint = Number(form.querySelector('input[name="usedPoint"]')?.value || 0);

        const afterDiscount = Math.max(0, subtotal - totalDiscount);
        const afterPoint = Math.max(0, afterDiscount - usedPoint);
        const shippingFee = afterPoint >= 50000 ? 0 : 3000;
        const total = afterPoint + shippingFee;

        const estEl = document.getElementById('estimatedTotal');
        if (estEl) {
            estEl.textContent = new Intl.NumberFormat('ko-KR').format(Math.round(total)) + '원';
        }
    }

    // ===== 이벤트 리스너 설정 =====
    if (itemSummary) {

        // 'input' 이벤트: 수량 입력창에 타이핑할 때마다 실시간으로 반응합니다.
        itemSummary.addEventListener('input', (e) => {
            if (e.target.matches('input[type="number"]')) {
                calculateOrder();
            }
        });

        // 'change' 이벤트: 드롭다운(포장, 쿠폰) 선택을 완료했거나,
        // 수량 입력창에서 포커스가 벗어났을 때(화살표 클릭 포함) 반응합니다.
        itemSummary.addEventListener('change', (e) => {
            // <select> 요소가 변경된 경우
            if (e.target.matches('select')) {
                // 쿠폰 선택이 바뀌었다면 다른 쿠폰 목록을 업데이트합니다.
                if (e.target.matches('select[id^="couponSelect_"]')) {
                    updateCouponAvailability();
                }
                // 그리고 항상 금액을 재계산합니다.
                calculateOrder();
            }
        });
    }

    form.querySelector('input[name="usedPoint"]')?.addEventListener('input', calculateOrder);

    /**
     * ✅ [수정 2] 포인트 적용 버튼 로직 수정
     * '모두 사용'이 아닌, 입력된 값을 검증하고 초과 시 보유 포인트로 값을 변경하는 로직
     */
    document.getElementById('applyPointBtn')?.addEventListener('click', () => {
        const usedInput = form.querySelector('input[name="usedPoint"]');
        if (!usedInput) return;

        const used = Number(usedInput.value || 0);
        if (used > pointBalance) {
            alert('보유 포인트를 초과했습니다! 😅');
            usedInput.value = pointBalance; // 초과 시 보유 포인트 최댓값으로 설정
        }
        calculateOrder(); // 값 검증 및 변경 후 금액 재계산
    });

    form.addEventListener('submit', function (event) {
        const recipientName = document.getElementById('recipientName').value.trim();
        const recipientContact = document.getElementById('recipientContact').value.trim();
        const postCode = document.getElementById('postcode').value.trim();
        const address = document.getElementById('address').value.trim();
        const detailAddress = document.getElementById('detailAddress').value.trim();

        if (!recipientName || !recipientContact || !postCode || !address || !detailAddress) {
            alert("필수 배송 정보를 모두 입력해주세요! 🙏");
            event.preventDefault();
        }
    });

    // ===== 페이지 로드 시 초기 실행 =====
    // ✅ [수정 3] 이 코드가 정상 실행되면 주소 버튼도 함께 해결됩니다.
    calculateOrder();
    updateCouponAvailability();

    // ===== 주소 모달 관련 로직 (기존과 동일) =====
    const modal = document.getElementById('addressModal');
    const openBtn = document.getElementById('openAddressModal');
    const closeBtn = document.getElementById('closeAddressModal');
    const selectedArea = document.getElementById('selectedAddressArea');

    if (openBtn) {
        openBtn.addEventListener('click', () => { modal.style.display = 'block'; });
    }
    if (closeBtn) {
        closeBtn.addEventListener('click', () => { modal.style.display = 'none'; });
    }

    window.addEventListener('click', e => { if (e.target === modal) modal.style.display = 'none'; });

    if (modal) {
        modal.addEventListener('click', (e) => {
            const btn = e.target.closest('.select-address');
            if (!btn) return;

            const card = btn.closest('.address-card');
            const id = card.dataset.id;
            const labelHtml = card.querySelector('.address-info').innerHTML;

            selectedArea.innerHTML = `<input type="hidden" name="memberAddressId" value="${id}"><div class="selected-address-preview">${labelHtml}</div>`;
            document.getElementById('recipientName').value = card.dataset.recipientName || '';
            document.getElementById('recipientContact').value = card.dataset.recipientContact || '';
            document.getElementById('postcode').value = card.dataset.postcode || '';
            document.getElementById('address').value = card.dataset.address || '';
            document.getElementById('detailAddress').value = card.dataset.detailAddress || '';

            modal.style.display = 'none';
        });
    }
});