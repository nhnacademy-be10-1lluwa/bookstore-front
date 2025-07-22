// 다음 우편번호 api
function openPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 도로명 주소만 허용
            if (data.userSelectedType !== 'R') {
                alert("도로명 주소로 선택해주세요! 🛣️");
                return;
            }

            let roadAddr = data.roadAddress;
            let extraAddr = '';

            if(data.bname && /[동|로|가]$/g.test(data.bname)){
                extraAddr += data.bname;
            }
            if(data.buildingName){
                extraAddr += (extraAddr ? ', ' + data.buildingName : data.buildingName);
            }

            let fullAddr = extraAddr ? `${roadAddr} (${extraAddr})` : roadAddr;

            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('address').value = fullAddr;
            document.getElementById('detailAddress').focus();
        }
    }).open();
}

const form = document.querySelector('.form-card');

if(form) {
    form.addEventListener('submit', function(event) {
        const postCode = form.querySelector('input[name="postCode"]').value.trim();
        const address = form.querySelector('input[name="address"]').value.trim();
        const detailAddress = form.querySelector('input[name="detailAddress"]').value.trim();
        const recipientName = form.querySelector('input[name="recipientName"]').value.trim();
        const recipientContact = form.querySelector('input[name="recipientContact"]').value.trim();

        const isDefaultChecked = document.getElementById("isDefaultCheckbox").checked;
        document.getElementById("defaultAddressInput").value = isDefaultChecked ? "true" : "false";

        // 주소 개수 제한 검사
        const mode = form.dataset.mode;
        if (mode === 'new' && typeof addressCount !== 'undefined' && addressCount >= 10) {
            alert("주소는 최대 10개까지 등록할 수 있어요! 😅");
            event.preventDefault();
            return;
        }

        // 필수 입력 검증
        if (!postCode || !address || !detailAddress || !recipientName || !recipientContact) {
            alert("필수 입력란을 모두 채워주세요! 🙏");
            event.preventDefault(); // 폼 제출 막기
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const newAddressBtn = document.getElementById("newAddressBtn");

    // "새 주소 등록" 버튼이 현재 페이지에 있을 때만 이벤트 리스너를 추가합니다.
    if (newAddressBtn) {
        // DOM에서 addressCount 값을 가져옵니다.
        // HTML에서 hidden input을 사용한 경우:
        const addressCountElement = document.getElementById('currentAddressCount');
        let addressCount = 0; // 기본값
        if (addressCountElement) {
            addressCount = parseInt(addressCountElement.value) || 0;
        }
        // HTML에서 data-address-count 속성을 사용한 경우:
        // let addressCount = parseInt(newAddressBtn.dataset.addressCount) || 0;


        newAddressBtn.addEventListener("click", function (e) {
            e.preventDefault(); // <a> 태그의 기본 동작을 막습니다.

            if (addressCount >= 10) {
                alert("주소는 최대 10개까지 등록 가능합니다! 😯");
                return; // 함수 실행 중단
            }

            // 조건에 맞으면 페이지 이동
            window.location.href = "/addresses/form";
        });
    } else {
        // 이 메시지는 '나의 주소록' 페이지가 아닐 때 뜰 수 있습니다.
        // 예: 주소 등록 폼 페이지 등.
        console.info("ID가 'newAddressBtn'인 요소를 현재 페이지에서 찾을 수 없습니다.");
    }
});

