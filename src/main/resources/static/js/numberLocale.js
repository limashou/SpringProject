document.addEventListener("DOMContentLoaded", function() {
    const totalAmountElements = document.querySelectorAll("#cost");
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
    document.querySelector('input[type="submit"]').disabled = true;
    document.querySelectorAll('input[type="checkbox"]').forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            const quantityInput = checkbox.parentElement.querySelector('.menu-quantity');
            if (checkbox.checked) {
                const quantity = prompt('Enter quantity:', '1');
                if (quantity !== null) {
                    quantityInput.style.display = 'inline-block';
                    quantityInput.value = quantity;
                } else {
                    checkbox.checked = false;
                    quantityInput.style.display = 'none';
                    quantityInput.value = 1;
                }
            } else {
                quantityInput.style.display = 'none';
                quantityInput.value = 1;
            }

            if (checkIfItemIsSelected()) {
                document.querySelector('input[type="submit"]').disabled = false;
            } else {
                document.querySelector('input[type="submit"]').disabled = true;
            }
        });
    });
});

function addSpaceToCurrencyValue(formattedValue) {
    return formattedValue.replace(/(\D)(\d)/, '$1 $2');
}
function checkIfItemIsSelected() {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            return true;
        }
    }
    return false;
}

