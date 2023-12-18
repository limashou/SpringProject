document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('searchQuery');
    const searchTypeSelect = document.getElementById('searchType');
    const tableRows = document.querySelectorAll('table tr');
    const originalTable = document.getElementById('originalTable');
    const originalTableHTML = originalTable.innerHTML;

    searchInput.addEventListener('input', function () {
        const searchValue = searchInput.value.toLowerCase();
        const searchType = searchTypeSelect.value;

        tableRows.forEach(function (row, index) {
            if (index === 0) return;
            const column = row.querySelector(`td:nth-child(${searchType === 'name' ? '1' : '2'})`);
            if (column) {
                const cellValue = column.textContent.toLowerCase();
                row.style.display = cellValue.includes(searchValue) ? '' : 'none';
            }
        });
    });

    let isSortAscending = true;

    function sortTableByName() {
        const table = document.querySelector('table');
        const rows = Array.from(table.querySelectorAll('tr'));
        const header = rows.shift();
        rows.sort((a, b) => {
            const nameA = a.cells[0].textContent.trim().toLowerCase();
            const nameB = b.cells[0].textContent.trim().toLowerCase();
            return nameA.localeCompare(nameB);
        });

        if (!isSortAscending) {
            rows.reverse();
        }

        table.innerHTML = '';
        table.appendChild(header);
        rows.forEach(row => table.appendChild(row));
        isSortAscending = !isSortAscending;
    }

    function resetTable() {
        const table = document.querySelector('table');
        table.innerHTML = originalTableHTML;
        isSortAscending = true;
        const currentURL = window.location.href;

        if (currentURL.includes('sort=by_rate')) {
            const baseURL = currentURL.split('?')[0];
            window.location.href = baseURL;
        }
    }

    function confirmDelete() {
        return confirm("Are you sure you want to delete this coffeeshop?");
    }

    function applyFilters() {
        const rateFilters = Array.from(document.querySelectorAll('input[name="rateFilter"]:checked')).map(input => input.value);
        const workingHoursFilters = Array.from(document.querySelectorAll('input[name="workingHoursFilter"]:checked')).map(input => input.value);
        const tableRows = document.querySelectorAll('table tr');
        tableRows.forEach(function (row, index) {
            if (index === 0) return;
            const rateCellValue = row.cells[2].textContent.trim();
            const workingHoursCellValue = row.cells[3].textContent.trim();
            console.log(workingHoursCellValue);
            const matchesRate = rateFilters.length === 0 || rateFilters.includes(rateCellValue);
            const matchesWorkingHours = workingHoursFilters.length === 0 || workingHoursFilters.includes(workingHoursCellValue);
            row.style.display = matchesRate && matchesWorkingHours ? '' : 'none';
        });
    }

    function clearFilters() {
        applyFilters();
        resetTable();
    }

    const deleteButtons = document.querySelectorAll('form.rate-button-form input[type="submit"][value="Delete"]');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function () {
            return confirmDelete();
        });
    });

    const sortButton = document.querySelector('input[type="submit"][value="Name"]');
    sortButton.addEventListener('click', function (){
        sortTableByName();
    });

    const  resetButtons  = document.querySelector('input[type="button"][value="Reset Table"]');
    resetButtons.addEventListener('click', function ()  {
        resetTable();
    });

    const applyFiltersButton = document.querySelector('input[type="button"][value="Apply Filters"]');
    applyFiltersButton.addEventListener('click', function () {
        applyFilters();
    });

    const clearFiltersButton = document.querySelector('input[type="reset"][value="Clear Filters"]');
    clearFiltersButton.addEventListener('click', function () {
        clearFilters();
    });
});
