// (Optional) CSS tweak for selected address preview
const style = document.createElement('style');
style.textContent = '.selected-address-preview{margin-bottom:6px;display:block;}';
document.head.appendChild(style);

document.addEventListener('DOMContentLoaded', () => {

    /**
     * (수정됨) 로컬 시간대 기준으로 날짜를 YYYY-MM-DD 형식으로 변환하는 헬퍼 함수
     * @param {Date} date - 변환할 Date 객체
     * @returns {string} YYYY-MM-DD 형식의 문자열
     */
    function toLocalISOString(date) {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    /* ----- 배송 날짜 초기값 (내일) ----- */
    (function setDefaultDeliveryDate() {
        const deliveryInput = document.getElementById('deliveryDate');
        if (!deliveryInput) return;

        const today = new Date();
        const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);

        // (수정됨) toLocalISOString 헬퍼 함수 사용
        const tomorrowString = toLocalISOString(tomorrow);

        // min, value 기본 세팅 (HTML쪽에서 이미 처리됐더라도 JS에서 한 번 더 보호)
        deliveryInput.min = tomorrowString;
        if (!deliveryInput.value) {
            deliveryInput.value = tomorrowString;
        }
    })();

    /* ----- 사용 포인트 기본값(0) 표시 ----- */
    (function setDefaultUsedPoint() {
        const usedInput = document.querySelector('input[name="usedPoint"]');
        if (usedInput && usedInput.value.trim() === '') {
            usedInput.value = 0;
        }
    })();

    const modal = document.getElementById('addressModal');
    const openBtn = document.getElementById('openAddressModal');
    const closeBtn = document.getElementById('closeAddressModal');
    const newAddressBtn = document.getElementById('newAddressBtn');
    const selectedArea = document.getElementById('selectedAddressArea');

    const addressCount = window.orderMemberInit?.addressCount ?? 0;
    const pointBalance = Number(
        document.querySelector('.point-use strong')?.getAttribute('data-balance') || 0
    );

    /* ----- 다음 우편번호 API 함수 (전역) ----- */
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

    /** 모달 열기 */
    if (openBtn) {
        openBtn.addEventListener('click', () => {
            modal.style.display = 'block';
        });
    }

    /* ----- (수정됨) Delegated handler: 주소 선택 후 모달 닫기 ----- */
    modal.addEventListener('click', (e) => {
        const btn = e.target.closest('.select-address');
        if (!btn) return;

        const card = btn.closest('.address-card');
        const id = card.dataset.id;
        const labelHtml = card.querySelector('.address-info').innerHTML;

        selectedArea.innerHTML = `
      <input type="hidden" name="memberAddressId" value="${id}">
      <div class="selected-address-preview">${labelHtml}</div>
    `;

        // 폼 필드 채우기
        document.getElementById('postcode').value = card.dataset.postcode || '';
        document.getElementById('address').value = card.dataset.address || '';
        document.getElementById('detailAddress').value = card.dataset.detailAddress || '';
        document.getElementById('recipientName').value = card.dataset.recipientName || '';
        document.getElementById('recipientContact').value = card.dataset.recipientContact || '';

        // 모달 닫기
        modal.style.display = 'none';
    });

    /** 모달 닫기 이벤트들 */
    if (closeBtn) closeBtn.addEventListener('click', () => modal.style.display = 'none');
    window.addEventListener('click', e => {
        if (e.target === modal) modal.style.display = 'none';
    });

    /** 새 주소 등록 버튼 */
    if (newAddressBtn) {
        newAddressBtn.addEventListener('click', () => {
            if (addressCount >= 10) {
                alert('주소는 최대 10개까지 등록 가능합니다! 😯');
                return;
            }
            // 새 주소 등록 페이지로 이동하는 로직은 그대로 둡니다.
            // 실제 프로젝트에 맞게 주소 확인 필요
            // window.location.href = '/addresses/new';
            alert("'/addresses/new' 페이지로 이동합니다. (데모)");
        });
    }

    /* ----- 주문 폼 필수 입력 검증 ----- */
    const form = document.querySelector('.order-form');
    form.addEventListener('submit', function (event) {
        const postCode = form.querySelector('input[name="postCode"]').value.trim();
        const address = form.querySelector('input[name="address"]').value.trim();
        const detailAddress = form.querySelector('input[name="detailAddress"]').value.trim();
        const recipientName = form.querySelector('#recipientName').value.trim();
        const recipientContact = form.querySelector('#recipientContact').value.trim();

        const usedPointInput = form.querySelector('input[name="usedPoint"]');
        if (usedPointInput && usedPointInput.value.trim() === '') {
            usedInput.value = 0; // Default to 0 if empty
        }

        // (수정됨) 배송 날짜가 비어있을 때 toLocalISOString 사용
        const deliveryInput = document.getElementById('deliveryDate');
        if (deliveryInput && !deliveryInput.value) {
            const today = new Date();
            const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
            deliveryInput.value = toLocalISOString(tomorrow);
        }

        if (!postCode || !address || !detailAddress || !recipientName || !recipientContact) {
            alert("필수 입력란을 모두 채워주세요! 🙏");
            event.preventDefault();
        }
    });

    /* ---------- 주문 금액 계산 (장바구니형으로 변경됨) ---------- */
    function calculateOrder() {
        let subtotal = 0;
        let totalDiscount = 0;

        // Iterate through each item in the cart
        document.querySelectorAll('.item-summary > div').forEach((itemDiv, index) => {
            const quantityInput = itemDiv.querySelector(`input[name="items[${index}].quantity"]`);
            const packagingSelect = itemDiv.querySelector(`select[name="items[${index}].packagingId"]`);
            const priceSpan = itemDiv.querySelector('p span'); // Assuming salePrice is here

            const quantity = Number(quantityInput?.value || 1);
            const unitPriceText = priceSpan.textContent.replace(/[^0-9]/g, ''); // Remove non-numeric characters
            const unitPrice = Number(unitPriceText || 0);

            const pkgPrice = packagingSelect ? Number(packagingSelect.selectedOptions[0].dataset.price || 0) : 0;

            // Calculate item total for this specific item
            const itemSubtotal = (unitPrice * quantity) + pkgPrice;
            subtotal += itemSubtotal;
        });

        // Apply coupon discount to the total subtotal
        const couponSel = document.querySelector('select[name="memberCouponId"]');
        if (couponSel && couponSel.value) {
            const opt = couponSel.selectedOptions[0];
            const rate = Number(opt.dataset.rate || 0);
            const amt = Number(opt.dataset.amount || 0);
            totalDiscount = rate > 0 ? subtotal * (rate / 100) : amt;
        }

        const usedPointEl = form.querySelector('input[name="usedPoint"]');
        const usedPoint = Number(usedPointEl?.value || 0);

        const afterDiscount = Math.max(subtotal - totalDiscount, 0);
        const afterPoint = Math.max(afterDiscount - usedPoint, 0);
        const shippingFee = afterPoint >= 50000 ? 0 : 3000;
        const total = afterPoint + shippingFee;

        const estEl = document.getElementById('estimatedTotal');
        if (estEl) {
            estEl.textContent = new Intl.NumberFormat('ko-KR').format(Math.round(total)) + '원';
        }
    }

    // Add event listeners for each quantity and packaging input
    document.querySelectorAll('input[name$=".quantity"]').forEach(input => {
        input.addEventListener('input', calculateOrder);
    });

    document.querySelectorAll('select[name$=".packagingId"]').forEach(select => {
        select.addEventListener('change', calculateOrder);
    });

    // Existing event listeners (no change needed here as they already target elements outside specific items)
    document.querySelector('select[name="memberCouponId"]')?.addEventListener('change', calculateOrder);
    form.querySelector('input[name="usedPoint"]')?.addEventListener('input', calculateOrder);

    document.getElementById('applyPointBtn')?.addEventListener('click', () => {
        const usedInput = form.querySelector('input[name="usedPoint"]');
        const used = Number(usedInput.value || 0);
        if (used > pointBalance) {
            alert('보유 포인트를 초과했습니다! 😅');
            usedInput.value = pointBalance; // 초과 시 최댓값으로 설정
        }
        calculateOrder();
    });

    // Initial calculation when the page loads
    calculateOrder();
});