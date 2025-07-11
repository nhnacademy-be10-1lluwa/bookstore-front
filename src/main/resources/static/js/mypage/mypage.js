const btnEdit = document.getElementById("btnEdit");
const btnCancel = document.getElementById("btnCancel");
const infoView = document.getElementById("infoView");
const infoEditForm = document.getElementById("infoEditForm");
const btnDelete = document.getElementById("btnDelete");

if (btnEdit && infoView && infoEditForm) {
    btnEdit.addEventListener("click", () => {
        infoView.style.display = "none";
        infoEditForm.style.display = "block";
    });
}

if (btnCancel && infoView && infoEditForm) {
    btnCancel.addEventListener("click", () => {
        infoEditForm.style.display = "none";
        infoView.style.display = "block";
    });
}

if (btnDelete) {
    btnDelete.addEventListener("click", () => {
        if (confirm("정말 탈퇴하시겠습니까? 탈퇴하면 같은 이메일로 가입하실 수 없습니다 😢")) {
            fetch('my-info/delete', { method: 'POST' })
                .then(res => {
                    if (res.ok) {
                        alert("탈퇴 처리되었습니다. 이용해주셔서 감사합니다:)");
                        window.location.href = "/logout";
                    } else {
                        alert("탈퇴에 실패했습니다. 다시 시도해주세요.");
                    }
                })
        }
    });
}

function filterType(type) {
    document.querySelectorAll(".point-tab").forEach(btn => btn.classList.remove("active"));
    const activeBtn = Array.from(document.querySelectorAll(".point-tab"))
        .find(btn => btn.textContent.includes(type === 'ALL' ? '전체' : (type === 'EARN' ? '적립' : '차감')));
    activeBtn?.classList.add("active");

    const rows = document.querySelectorAll("#pointHistoryTable tbody tr");
    rows.forEach(row => {
        const rowType = row.getAttribute("data-type");
        row.style.display = (type === "ALL" || rowType === type) ? "" : "none";
    });
}