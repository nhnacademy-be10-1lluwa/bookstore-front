let addressCount = /*[[${addressCount}]]*/ 0;

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

document.addEventListener("DOMContentLoaded", function () {
    const newAddressBtn = document.getElementById("newAddressBtn");
    newAddressBtn.addEventListener("click", function (e) {
        if (addressCount >= 10) {
            e.preventDefault();
            alert("주소는 최대 10개까지 등록 가능합니다! 😯");
            return;
        }

        window.location.href = "/addresses/new";
    });
});

