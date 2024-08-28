function searchExpenditures() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const companyHeader = document.getElementById('companyHeader').value;
    const unit = document.getElementById('unit').value;

    fetch(`/api/expenditures/searchByMultipleCriteria?startDate=${startDate}&endDate=${endDate}&companyHeader=${companyHeader}&unit=${unit}`)
        .then(response => response.json())
        .then(data => {
            const resultsContainer = document.getElementById('results');
            resultsContainer.innerHTML = ''; // 清除之前的結果
            if (data.length > 0) {
                let table = `
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Date</th>
                                <th>Order Number</th>
                                <th>Unit</th>
                                <th>Company Header</th>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Total</th>
                                <th>Tax</th>
                                <th>Accounting Month</th>
                                <th>Voucher Date</th>
                                <th>Voucher Number</th>
                                <th>Voucher Amount</th>
                                <th>Payable Amount</th>
                                <th>Paid Amount</th>
                                <th>Current Payable Amount</th>
                                <th>Payment Unit</th>
                                <th>Remarks</th>
                                <th>Create Time</th>
                                <th>Update Time</th>
                            </tr>
                        </thead>
                        <tbody>
                `;
                data.forEach(item => {
                    table += `
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.date}</td>
                            <td>${item.orderNumber}</td>
                            <td>${item.unit}</td>
                            <td>${item.companyHeader}</td>
                            <td>${item.productName}</td>
                            <td>${item.quantity}</td>
                            <td>${item.unitPrice}</td>
                            <td>${item.total}</td>
                            <td>${item.tax}</td>
                            <td>${item.accountingMonth}</td>
                            <td>${item.voucherDate}</td>
                            <td>${item.voucherNumber}</td>
                            <td>${item.voucherAmount}</td>
                            <td>${item.payableAmount}</td>
                            <td>${item.paidAmount}</td>
                            <td>${item.currentPayableAmount}</td>
                            <td>${item.paymentUnit}</td>
                            <td>${item.remarks}</td>
                            <td>${item.createTime}</td>
                            <td>${item.updateTime}</td>
                        </tr>
                    `;
                });
                table += `
                        </tbody>
                    </table>
                `;
                resultsContainer.innerHTML = table;
            } else {
                resultsContainer.innerHTML = '<p>No results found</p>';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('results').innerHTML = '<p>An error occurred while fetching data.</p>';
        });
}

// 上傳文件功能
// 獲取CSRF token
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 防止表單的默認提交

    const formData = new FormData();
    const fileUpload = document.getElementById('fileUpload').files[0];
    formData.append("file", fileUpload);

    fetch('/api/expenditures/upload', {
        method: 'POST',
        body: formData,
        headers: {
            [csrfHeader]: csrfToken
        }
    })
    .then(response => {
        if (response.ok) {
            alert("File uploaded successfully");
        } else {
            alert("File upload failed");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("An error occurred during file upload");
    });
});

