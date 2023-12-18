document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchQuery');
    const searchTypeSelect = document.getElementById('searchType');
    const tableRows = document.querySelectorAll('table tr');
    const originalTable = document.getElementById('originalTableHTML');
    const originalTableHTML = originalTable.innerHTML;
    function addSpaceToCurrencyValue(formattedValue) {
        return formattedValue.replace(/(\D)(\d)/, '$1 $2');
    }
    function formatTotalAmount() {
        const totalAmountElements = document.querySelectorAll("#total_amount");
        totalAmountElements.forEach(function (totalAmountElement) {
            const unformattedValue = totalAmountElement.textContent;
            const numericValue = parseFloat(unformattedValue);
            //const formatter = new Intl.NumberFormat('ru-RU', {style: 'currency', currency: 'RUB'});
            //const formatter = new Intl.NumberFormat('uk-UA', { style: 'currency', currency: 'UAH' });
            //const formatter = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' });
            //const formatter = new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' });
            const formatter = new Intl.NumberFormat('zh-CN', { style: 'currency', currency: 'CNY' });
            const formattedValue = formatter.format(numericValue);
            const formattedWithSpace = addSpaceToCurrencyValue(formattedValue);
            totalAmountElement.textContent = formattedWithSpace;
        });
    }
    function resetTableOrder() {
        const table = document.querySelector('table');
        table.innerHTML = originalTableHTML;
        const currentURL = window.location.href;
        formatTotalAmount();
    }
    function applyFilters() {
        const statusFilters = Array.from(document.querySelectorAll('input[name="statusFilter"]:checked')).map(input => input.value);
        const paymentFilters = Array.from(document.querySelectorAll('input[name="paymentFilter"]:checked')).map(input => input.value);
        tableRows.forEach(function (row, index) {
            if (index === 0) return;
            const statusCellValue = row.cells[3].textContent.trim();
            const paymentCellValue = row.cells[4].textContent.trim();
            const matcheStatus = statusFilters.length === 0 || statusFilters.includes(statusCellValue);
            const matchesPayment = paymentFilters.length === 0 || paymentFilters.includes(paymentCellValue);
            row.style.display = matcheStatus && matchesPayment ? '' : 'none';
        });
    }
    function clearFilters() {
        resetTableOrder();
        applyFilters();
    }

    formatTotalAmount();

    const applyFiltersButton = document.querySelector('input[type="button"][value="Apply Filters"]');
    applyFiltersButton.addEventListener('click', function () {
        applyFilters();
    });

    const clearFiltersButton = document.querySelector('input[type="reset"][value="Clear Filters"]');
    clearFiltersButton.addEventListener('click', function () {
        clearFilters();
    });
    const  resetButtons  = document.querySelector('input[type="button"][value="Reset Table"]');
    resetButtons.addEventListener('click', function ()  {
        resetTableOrder();
    });
});
