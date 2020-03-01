<#macro page status, Pname>
<!DOCTYPE html>
<html>
<head>
    <title>Slick Playground</title>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/slider.css">
    <link rel="stylesheet" type="text/css" href="/static/css/slider-theme.css">
    <link rel="stylesheet" type="text/css" href="/static/css/All.css">
    <link rel="stylesheet" type="text/css" href="/static/css/filterLists.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
    <script src="/static/js/all.js" type="text/javascript" charset="utf-8"></script>
    <script src="/static/js/slider.js" type="text/javascript" charset="utf-8"></script>
    <script src="/static/js/jquery.star-rating-svg.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/star-rating-svg.css">
    <link rel="stylesheet" type="text/css" href="/static/css/plaginator.css">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>

<body bgcolor="#181818">

    <#nested>


    <form style="position: absolute; top: 400%" action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" value="Sign Out"/>
    </form>

<div class="container">
    <a href="http://localhost:8080/"><h1>LIB FILM</h1></a>
</div>

    <style> ::placeholder {color: white}    </style>


<#--    CONFIGS SLIDER-->
    <script>

        $(document).on('ready', function() {
        $(".vertical-center-4").slick({
            dots: false,
            vertical: true,
            centerMode: true,
            slidesToShow: 4,
            slidesToScroll: 2
        });

        $(".regular").slick({
            dots: false,
            infinite: true,
            slidesToShow: 7,
            slidesToScroll: 7
        });

        $(".lazy").slick({
            lazyLoad: 'ondemand', // ondemand progressive anticipated
            infinite: true
        });
    });
</script>

<script>
    //MENU GENGRE
    let b = false;
    $(document).ready(function(){
        $('.menu-tab').click(function(){
            $('.menu-hide').toggleClass('show');
            $('.menu-tab').toggleClass('active');
        });
        $('a').click(function(){
            $('.menu-hide').removeClass('show');
            $('.menu-tab').removeClass('active');
        });
    });
    //LOGIN MENU
    $(document).ready(function() {
        $('#Auth').click(function () {
            if (b == false) {  $('.login-wrap').show(250); b = true}
            else {$('.login-wrap').hide(250); b = false}
        });
    });
</script>

<#--SEARCH HINT CONFIGS-->
<script>
    let count = 0;

    $('#searchField').keyup(function(){
        var Value = $('#searchField').val();
        if(Value.length >= 3) {
            $.get(
                "/data",
                {
                    hint: Value,
                },
                onAjaxSuccess
            );
            function onAjaxSuccess(data)
            {
                $('#hint div').remove();
                for (let entry of data) {
                    id2 = "/film/" + Number(entry.replace(/\D+/g,""));
                    console.log(id2);
                    $('#hint').append('<a href=""><div> ' + entry + ' </div></a>');
                    $("div#hint a:eq(" + count + ")").attr('href', id2);
                    count++;

                }
                count = 0;
            }
        }
        else {
            $('#hint a').remove();
        }
    });
</script>

</body>
</html>
</#macro>