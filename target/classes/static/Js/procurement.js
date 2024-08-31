document.getElementById('uploadProcurementForm').addEventListener('submit', async function (event) {
    event.preventDefault();
    const fileInput = document.getElementById('fileUploadProcurement').files[0];
    if (fileInput) {
        const formData = new FormData();
        formData.append('file', fileInput);
        try {
            const response = await fetch('/api/excel/procurement/upload', {
                method: 'POST',
                body: formData
            });
            const result = await response.text();
            alert(result);
        } catch (error) {
            alert('Error uploading file: ' + error.message);
        }
    }
});

async function searchProcurements() {
    const startDate = document.getElementById('startDateProcurement').value;
    const endDate = document.getElementById('endDateProcurement').value;
    const purchaser = document.getElementById('purchaser').value;
    const store = document.getElementById('store').value;

    // 構建查詢URL
    const url = `/api/procurements/search?startDate=${startDate}&endDate=${endDate}&purchaser=${purchaser}&store=${store}`;

    try {
        const response = await fetch(url);
        const data = await response.json();
        displayProcurements(data);
    } catch (error) {
        alert('Error fetching procurements: ' + error.message);
    }
}

function displayProcurements(data) {
    const resultsContainer = document.getElementById('resultsProcurements');
    resultsContainer.innerHTML = ''; // 清空之前的結果
    data.forEach(item => {
        const div = document.createElement('div');
        div.textContent = `進貨項目: ${item.store}, 採購人: ${item.purchaser}`;
        resultsContainer.appendChild(div);
    });
}

