function WarnFunction() {
    $("#commentField").attr("placeholder", "Вы не авторизованы поэтому ваш коментарий не будет опубликован").css('color', 'red');
    $("#commentField").css('height', '50px');
    $("#buttonComment").css('pointer-events', 'none');

}