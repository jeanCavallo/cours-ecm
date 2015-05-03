
// BUG-9 : auto complétion
$('input[data-role="tags"]').tokenfield();

$('button[data-role="addIngredient"]').click(function() {
    // BUG-8 : delete then add, index is wrong
    var index = $('#ingredients > div').length;

    $('#ingredients').append($('<div>').load('/admin/recettes/ingredientFormRow?ingredientIndex=' + index));
});

$(document).on('click', 'button[data-role="removeIngredient"]', function() {
    $(this).parents('.row')[0].remove();
});