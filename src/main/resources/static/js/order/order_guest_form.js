document.addEventListener('DOMContentLoaded', () => {

    /** 로컬 시간대 기준, 날짜를 YYYY-MM-DD 형식으로 변환하는 헬퍼 함수 */
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

        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);

        const tomorrowString = toLocalISOString(tomorrow);

        deliveryInput.min = tomorrowString;
        if (!deliveryInput.value) {
            deliveryInput.value = tomorrowString;
        }
    })();


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


    /* ----- 주문 폼 필수 입력 검증 ----- */
    const form = document.querySelector('.order-form');
    form.addEventListener('submit', function (event) {
        // 배송지 정보
        const postCode = form.querySelector('input[name="postCode"]').value.trim();
        const address = form.querySelector('input[name="address"]').value.trim();
        const detailAddress = form.querySelector('input[name="detailAddress"]').value.trim();
        const recipientName = form.querySelector('#recipientName').value.trim();
        const recipientContact = form.querySelector('#recipientContact').value.trim();

        // 주문자 정보
        const guestName = form.querySelector('#orderName').value.trim();
        const orderPassword = form.querySelector('#orderPassword').value.trim();
        const guestEmail = form.querySelector('#email').value.trim();
        const guestContact = form.querySelector('#contact').value.trim();

        // 배송 날짜가 비어 있으면 자동으로 내일 날짜로 채움
        const deliveryInput = document.getElementById('deliveryDate');
        if (deliveryInput && !deliveryInput.value) {
            const tomorrow = new Date();
            tomorrow.setDate(tomorrow.getDate() + 1);
            deliveryInput.value = toLocalISOString(tomorrow);
        }

        // 필수 입력값 검증
        if (!guestName || !orderPassword || !guestEmail || !guestContact) {
            alert("주문자 정보를 모두 입력해주세요! 🙏");
            event.preventDefault();
            return;
        }
        if (!postCode || !address || !detailAddress || !recipientName || !recipientContact) {
            alert("배송지 정보를 모두 입력해주세요! 🙏");
            event.preventDefault();
            return;
        }
    });

    /* ---------- (수정) 주문 금액 계산 함수 ---------- */
    function calculateOrder() {
        // 가격 정보들을 간결하고 안전하게 숫자로 변환
        const unitPrice = Number(document.getElementById('unitPrice')?.value || 0);
        const qty = Number(document.getElementById('quantityInput')?.value || 1);

        const pkgSelect = document.getElementById('packagingSelect');
        const pkgPrice = pkgSelect ? Number(pkgSelect.selectedOptions[0]?.dataset.price || 0) : 0;

        // 금액 계산
        const subtotal = (unitPrice + pkgPrice) * qty;
        const shippingFee = subtotal >= 50000 ? 0 : 3000;
        const total = subtotal + shippingFee;

        // 화면 표시
        const estEl = document.getElementById('estimatedTotal');
        if (estEl) {
            estEl.textContent = new Intl.NumberFormat('ko-KR').format(total) + '원';
        }
    }

    // 이벤트 리스너 바인딩
    document.getElementById('quantityInput')?.addEventListener('input', calculateOrder);
    document.getElementById('packagingSelect')?.addEventListener('change', calculateOrder);

    // 페이지 로드 시 초기 계산 실행
    calculateOrder();
});