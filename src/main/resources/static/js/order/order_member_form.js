// (Optional) CSS tweak for selected address preview
const style = document.createElement('style');
style.textContent = '.selected-address-preview{margin-bottom:6px;display:block;}';
document.head.appendChild(style);

document.addEventListener('DOMContentLoaded', () => {
  /* ----- 배송 날짜 초기값 (내일) ----- */
  (function setDefaultDeliveryDate() {
    const deliveryInput = document.getElementById('deliveryDate');
    if (!deliveryInput) return;

    const today  = new Date();
    const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
    const iso = tomorrow.toISOString().split('T')[0];

    // min, value 기본 세팅 (HTML쪽에서 이미 처리됐더라도 JS에서 한 번 더 보호)
    deliveryInput.min   = iso;
    if (!deliveryInput.value) {
      deliveryInput.value = iso;
    }
  })();

  /* ----- 사용 포인트 기본값(0) 표시 ----- */
  (function setDefaultUsedPoint() {
    const usedInput = document.querySelector('input[name="usedPoint"]');
    if (usedInput && usedInput.value.trim() === '') {
      usedInput.value = 0;
    }
  })();
  const modal        = document.getElementById('addressModal');
  const openBtn      = document.getElementById('openAddressModal');
  const closeBtn     = document.getElementById('closeAddressModal');
  const newAddressBtn= document.getElementById('newAddressBtn');
  const selectedArea = document.getElementById('selectedAddressArea');

  // 서버가 주입한 주소 개수 (없으면 0)
  const addressCount = window.orderMemberInit?.addressCount ?? 0;
  const pointBalance = Number(
    document.querySelector('.point-use strong')?.getAttribute('data-balance') || 0
  );

  /* ----- 다음 우편번호 API 함수 (전역) ----- */
  window.openPostcode = function () {
    new daum.Postcode({
      oncomplete: function (data) {
        // 도로명 주소만 허용
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

        document.getElementById('postcode').value      = data.zonecode;
        document.getElementById('address').value       = fullAddr;
        document.getElementById('detailAddress').focus();
      }
    }).open();
  };

  /** 주소 카드에 클릭 리스너를 붙인다(중복 방지) */
  function attachSelectHandlers() {
    modal.querySelectorAll('.address-card').forEach(card => {
      const selectBtn = card.querySelector('.select-address');
      if (!selectBtn || selectBtn.dataset.listenerAttached) return;

      selectBtn.addEventListener('click', () => {
        const id        = card.dataset.id;
        const labelHtml = card.querySelector('.address-info').innerHTML;
        // memberAddressId를 히든 필드로 넣고, 화면에는 주소만 간단히 보여준다
        selectedArea.innerHTML = `
          <input type="hidden" name="memberAddressId" value="${id}">
          <div class="selected-address-preview">${labelHtml}</div>
        `;
        // 폼 입력란에 선택한 주소 값 채우기
        document.getElementById('postcode').value          = card.dataset.postcode          || '';
        document.getElementById('address').value           = card.dataset.address           || '';
        document.getElementById('detailAddress').value     = card.dataset.detailAddress     || '';
        document.getElementById('addressName').value       = card.dataset.addressName       || '';
        document.getElementById('recipientName').value     = card.dataset.recipientName     || '';
        document.getElementById('recipientContact').value  = card.dataset.recipientContact  || '';
        modal.style.display = 'none';
      });
      selectBtn.dataset.listenerAttached = 'true';
    });
  }

  /** 모달 열기 */
  if (openBtn) {
    openBtn.addEventListener('click', () => {
      modal.style.display = 'block';
      // attachSelectHandlers();   // 주소 카드 갱신 시마다 다시 호출 가능 (이제 필요 없음)
    });
  }

  /* ----- Delegated handler: 주소 선택 후 모달 닫기 ----- */
  modal.addEventListener('click', (e) => {
    const btn = e.target.closest('.select-address');
    if (!btn) return;

    const card       = btn.closest('.address-card');
    const id         = card.dataset.id;
    const labelHtml  = card.querySelector('.address-info').innerHTML;

    // 선택한 주소 미리보기 + hidden field
    selectedArea.innerHTML = `
      <input type="hidden" name="memberAddressId" value="${id}">
      <div class="selected-address-preview">${labelHtml}</div>
    `;

    // 폼 필드 채우기
    document.getElementById('postcode').value          = card.dataset.postcode         || '';
    document.getElementById('address').value           = card.dataset.address          || '';
    document.getElementById('detailAddress').value     = card.dataset.detailAddress    || '';
    document.getElementById('addressName').value       = card.dataset.addressName      || '';
    document.getElementById('recipientName').value     = card.dataset.recipientName    || '';
    document.getElementById('recipientContact').value  = card.dataset.recipientContact || '';

    // 모달 닫기
    modal.style.display = 'none';
  });

  /** 모달 닫기 */
  if (closeBtn) closeBtn.addEventListener('click', () => modal.style.display = 'none');
  window.addEventListener('click', e => { if (e.target === modal) modal.style.display = 'none'; });

  /** 새 주소 등록 버튼 */
  if (newAddressBtn) {
    newAddressBtn.addEventListener('click', () => {
      if (addressCount >= 10) {
        alert('주소는 최대 10개까지 등록 가능합니다! 😯');
        return;
      }
      window.location.href = '/addresses/new';
    });
  }

  /* ----- 주문 폼 필수 입력 검증 & 기본배송지 체크 ----- */
  const form = document.querySelector('.order-form');
  form.addEventListener('submit', function (event) {
    const postCode         = form.querySelector('input[name="postCode"]').value.trim();
    const address          = form.querySelector('input[name="address"]').value.trim();
    const detailAddress    = form.querySelector('input[name="detailAddress"]').value.trim();
    const recipientName    = form.querySelector('#recipientName').value.trim();
    const recipientContact = form.querySelector('#recipientContact').value.trim();

    // usedPoint가 비어 있으면 0으로 설정 (Apply 버튼 안 눌러도 NullPointer 방지)
    const usedPointInput = form.querySelector('input[name="usedPoint"]');
    if (usedPointInput && usedPointInput.value.trim() === '') {
      usedPointInput.value = 0;
    }

    // 배송 날짜가 비어 있으면 자동으로 내일 날짜로 채움
    const deliveryInput = document.getElementById('deliveryDate');
    if (deliveryInput && !deliveryInput.value) {
      const today  = new Date();
      const tomorrow = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 1);
      deliveryInput.value = tomorrow.toISOString().split('T')[0];
    }

    // 기본 배송지 hidden 필드 값 세팅
    const isDefaultChecked = document.getElementById("isDefaultCheckbox").checked;
    document.getElementById("defaultAddressInput").value = isDefaultChecked ? "true" : "false";

    // 주소 개수 제한 (mode=new 일 때만)
    const mode = form.dataset.mode;
    if (mode === 'new' && typeof addressCount !== 'undefined' && addressCount >= 10) {
      alert("주소는 최대 10개까지 등록할 수 있어요! 😅");
      event.preventDefault();
      return;
    }

    // 필수 입력값 검증
    if (!postCode || !address || !detailAddress || !recipientName || !recipientContact) {
      alert("필수 입력란을 모두 채워주세요! 🙏");
      event.preventDefault();
    }
  });

  /* ---------- 주문 금액 계산 ---------- */
  function calculateOrder() {
    const unitPrice = Number(document.getElementById('unitPrice')?.value || 0);
    const qty       = Number(document.getElementById('quantityInput')?.value || 1);

    const pkgSelect  = document.getElementById('packagingSelect');
    const pkgPrice   = pkgSelect ? Number(pkgSelect.selectedOptions[0].dataset.price || 0) : 0;

    // 쿠폰 할인
    const couponSel = document.getElementById('couponSelect');
    let discount = 0;
    if (couponSel && couponSel.value) {
      const opt   = couponSel.selectedOptions[0];
      const rate  = Number(opt.dataset.rate   || 0);
      const amt   = Number(opt.dataset.amount || 0);
      discount    = rate > 0 ? (unitPrice + pkgPrice) * qty * (rate / 100) : amt;
    }

    const usedPointEl = form.querySelector('input[name="usedPoint"]');
    const usedPoint   = Number(usedPointEl?.value || 0);

    const subtotal      = (unitPrice + pkgPrice) * qty;
    const afterDiscount = Math.max(subtotal - discount, 0);
    const afterPoint    = Math.max(afterDiscount - usedPoint, 0);

    const shippingFee   = afterPoint >= 50000 ? 0 : 3000;
    const total         = afterPoint + shippingFee;

    const estEl = document.getElementById('estimatedTotal');
    if (estEl) {
      estEl.textContent = new Intl.NumberFormat('ko-KR').format(total) + '원';
    }
  }

  // 이벤트 바인딩
  document.getElementById('quantityInput')?.addEventListener('input', calculateOrder);
  // document.querySelectorAll('input[name="item.packagingId"]').forEach(r =>
  //   r.addEventListener('change', calculateOrder));
  document.getElementById('packagingSelect')?.addEventListener('change', calculateOrder);
  document.getElementById('couponSelect')?.addEventListener('change', calculateOrder);
  // form.querySelector('input[name="usedPoint"]')?.addEventListener('input', calculateOrder);

  document.getElementById('applyPointBtn')?.addEventListener('click', () => {
    const used = Number(form.querySelector('input[name="usedPoint"]').value || 0);
    if (used > pointBalance) {
      alert('보유 포인트를 초과했습니다! 😅');
      return;
    }
    calculateOrder();
  });

  // 초기 계산
  calculateOrder();
});