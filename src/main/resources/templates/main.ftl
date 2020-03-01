<#import "parts/structural_code.ftl" as c>
<#import "parts/additiomaly_code.ftl" as a>

<@a.page 'false', ''>

    <@c.structural_code/>



<div id="MainContent" style="border-top: 3px solid #ccc; border-bottom: 3px solid #ccc;  background-color: #150734; width: 103%; height: 1630px; margin-left: -1%; padding-top: 1%">

    <#if filmList??>
<#list filmList.content as film>
    <div style=" padding-left: 1%; border-right: 2px solid #ccc; background: #150734;  color:white; width: 50%; height: 400px; float: left; padding-right: 10px">
        <div class="my-rating-6" style="position: absolute; margin-left: 34%; margin-top: 250px;"></div>
        <a href="/film/${film.getId()}"><img src="/img/${film.filmName!}.jpg"></a>
        <div id="infoFilm" style="position: absolute; background: black; opacity: 0.7; width: 230px; margin-top: 13%; height: 120px;">
            <p>${film.filmName}</p>
            <p>Жанр - ${film.genre}</p>
            <p>Год - ${film.years}</p>
            <p class="raitHide" style="display: none">${film.rait}</p>
            <p class="votesHide" style="display: none">${film.votes}</p>
            <script>
                test = $('.raitHide').text().trim().replace(/,/, '.').replace(/\s+/g,'');
                test2 = $('.votesHide').text().trim().replace(/,/, '.').replace(/\s+/g,'');
                test3 = test / test2 / 2;
                test3 = test3.toFixed(1);
                if (!isNaN(test3)) {
                    filmRating = test3;

                }
                else filmRating = 0;

                $(".my-rating-6").starRating({
                    totalStars: 5,
                    emptyColor: 'lightgray',
                    hoverColor: 'slategray',
                    activeColor: 'cornflowerblue',
                    initialRating: filmRating,
                    strokeWidth: 0,
                    useGradient: false,
                    minRating: 1,
                    readOnly: true, //read

                });

                test = $('.raitHide').empty();
                test = $('.votesHide').empty();
            </script>
        </div>

        <p style=" font-family: Georgia; width: 63%; height: 51%; font-size: 15px; overflow: hidden">${film.description!}</p>
        <a target="_blank" href="${film.linkPoster}"><div class="buttonS">Смотреть full</div></a>
        <div class="buttonS" style="margin-left: 25%;">Trailer</div>
    </div>
    </#list>
    </#if>

    <#if genreFilm??>
        <#list genreFilm as film>
            <div style=" padding-left: 1%; border-right: 2px solid #ccc; background: #150734;  color:white; width: 50%; height: 400px; float: left; padding-right: 10px">
                <div class="my-rating-6" style="position: absolute; margin-left: 34%; margin-top: 250px;"></div>
                <a href="/film/${film.getId()}"><img src="/img/${film.filmName!}.jpg"></a>
                <div id="infoFilm" style="position: absolute; background: black; opacity: 0.7; width: 230px; margin-top: 13%; height: 120px;">
                    <p>${film.filmName}</p>
                    <p>Жанр - ${film.genre}</p>
                    <p>Год - ${film.years}</p>
                    <p class="raitHide" style="display: none">${film.rait}</p>
                    <p class="votesHide" style="display: none">${film.votes}</p>
                    <script>
                        test = $('.raitHide').text().trim().replace(/,/, '.').replace(/\s+/g,'');
                        test2 = $('.votesHide').text().trim().replace(/,/, '.').replace(/\s+/g,'');
                        test3 = test / test2 / 2;
                        test3 = test3.toFixed(1);
                        if (!isNaN(test3)) {
                            filmRating = test3;

                        }
                        else filmRating = 0;

                        $(".my-rating-6").starRating({
                            totalStars: 5,
                            emptyColor: 'lightgray',
                            hoverColor: 'slategray',
                            activeColor: 'cornflowerblue',
                            initialRating: filmRating,
                            strokeWidth: 0,
                            useGradient: false,
                            minRating: 1,
                            readOnly: true, //read

                        });

                        test = $('.raitHide').empty();
                        test = $('.votesHide').empty();
                    </script>
                </div>

                <p style=" font-family: Georgia; width: 63%; height: 51%; font-size: 15px; overflow: hidden">${film.description!}</p>
                <a target="_blank" href="${film.linkPoster}"><div class="buttonS">Смотреть full</div></a>
                <div class="buttonS" style="margin-left: 25%;">Trailer</div>
            </div>
        </#list>
    </#if>
</div>
    <#if filmList??>
    <div class="content_detail__pagination cdp" actpage="1">
        <a href="/?page=${filmList.getNumber() - 1}" id="afterContent" class="cdp_i">prev</a>

        <a href="/?page=${filmList.getNumber() + 1}" class="cdp_i" id="nextContent">next</a>
    </div>

    <script>
        for (let i = ${filmList.getTotalPages()}; i > 0; i--) {
            $('#afterContent').after('<a href="/?page=' + (i - 1) + '" class="cdp_i" >' + i + '</a>');
        }

        if (${filmList.getNumber()} == 0) {
             $('#afterContent').css({"display": "none"});
        } else {$('#afterContent').css({"display": "inline-block"});}
        if (${filmList.getNumber()} == (${filmList.getTotalPages()} - 1)) {
            $('#nextContent').css({"display": "none"});
        } else {
            $('#nextContent').css({"display": "inline-block"});
        }
    </script>
    </#if>
</@a.page>