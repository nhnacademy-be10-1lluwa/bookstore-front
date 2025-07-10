


document.addEventListener('DOMContentLoaded', () => {
  const modal        = document.getElementById('addressModal');
  const openBtn      = document.getElementById('openAddressModal');
  const closeBtn     = document.getElementById('closeAddressModal');
  const newAddressBtn= document.getElementById('newAddressBtn');
  const selectedArea = document.getElementById('selectedAddressArea');

  // 서버가 주입한 주소 개수 (없으면 0)
  const addressCount = window.orderMemberInit?.addressCount ?? 0;

  /** 주소 카드에 클릭 리스너를 붙인다(중복 방지) */
  function attachSelectHandlers() {
    modal.querySelectorAll('.address-card').forEach(card => {
      const selectBtn = card.querySelector('.select-address');
      if (!selectBtn || selectBtn.dataset.listenerAttached) return;

      selectBtn.addEventListener('click', () => {
        const id        = card.dataset.id;
        const labelHtml = card.querySelector('.address-info').innerHTML;

        selectedArea.innerHTML = `
          <input type="radio" id="selectedAddr" name="memberAddressId" value="${id}" checked>
          <label for="selectedAddr">${labelHtml}</label>
        `;
        modal.style.display = 'none';
      });
      selectBtn.dataset.listenerAttached = 'true';
    });
  }

  /** 모달 열기 */
  if (openBtn) {
    openBtn.addEventListener('click', () => {
      modal.style.display = 'block';
      attachSelectHandlers();   // 주소 카드 갱신 시마다 다시 호출 가능
    });
  }

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

  /* ---------- 주문 금액 계산 ---------- */
  function calculateOrder() {
    const unitPrice = Number(document.getElementById('unitPrice')?.value || 0);
    const qty       = Number(document.getElementById('quantityInput')?.value || 1);

    const selectedPkg = document.querySelector('input[name="packagingId"]:checked');
    const pkgPrice    = selectedPkg ? Number(selectedPkg.dataset.price || 0) : 0;

    // 쿠폰 할인
    const couponSel = document.getElementById('couponSelect');
    let discount = 0;
    if (couponSel && couponSel.value) {
      const opt   = couponSel.selectedOptions[0];
      const rate  = Number(opt.dataset.rate   || 0);
      const amt   = Number(opt.dataset.amount || 0);
      discount    = rate > 0 ? (unitPrice + pkgPrice) * qty * (rate / 100) : amt;
    }

    const subtotal      = (unitPrice + pkgPrice) * qty;
    const afterDiscount = Math.max(subtotal - discount, 0);
    const shippingFee   = afterDiscount >= 50000 ? 0 : 3000;
    const total         = afterDiscount + shippingFee;

    const estEl = document.getElementById('estimatedTotal');
    if (estEl) {
      estEl.textContent = new Intl.NumberFormat('ko-KR').format(total) + '원';
    }
  }

  // 이벤트 바인딩
  document.getElementById('quantityInput')?.addEventListener('input', calculateOrder);
  document.querySelectorAll('input[name="packagingId"]').forEach(r =>
    r.addEventListener('change', calculateOrder));
  document.getElementById('couponSelect')?.addEventListener('change', calculateOrder);

  // 초기 계산
  calculateOrder();
});