<#import "parts/structural_code.ftl" as c>
<#import "parts/additiomaly_code.ftl" as a>

<@a.page 'true', 'film'>
    <@c.structural_code/>

<div id="MainContent" style="border-top: 3px solid #ccc; border-bottom: 2px solid #ccc;  background-color: #150734; width: 101%; height: 450px; margin-left: -1%; padding-top: 1%">
    <#list filmInfo as film>
    <div style="position: absolute; color: white; font-size: 27px;margin-left: 3%; font-family: Georgia">${film.getFilmName()!} / ${film.getYears()!} / ${film.getGenre()!}</div>
    <div class="my-rating-6" style="position: absolute; margin-left: 46%;margin-top: -0.3%"></div>
    <span style="position: absolute; width: 58%; color: white; font-family: Georgia; margin-left: 3%; font-size: 16px; margin-top: 5%">${film.getDescription()!}</span>
    <video width="550" height="350" preload="none" controls poster="https://dailyovation.com/wp-content/uploads/2018/05/Film-in-California-Conference-1.jpg" style="margin-left: 61.5%; margin-top: -1%">
        <source src="/trailer/${film.getTrailerName()!}">
    </video>
    <div class="contact-section">
        <div class="inner-width">
            <h1>Ведите свой коментарий:</h1>
            <form method="post" action="/film/${film.getId()}">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <textarea id="commentField" rows="1" placeholder="Message" name="comments" class="message"></textarea>
            <input id="buttonComment"  type="submit" value="Добавить коментарий">
            </form>
        </div>
    </div>
</div>
    <script>var idFilm = ${film.getId()!}</script>
</#list>
<center><h1 style="color: white">Коментарии:</h1></center>
    <#list cList! as list>
<div style="width: 100%; height: auto; border-top: 1px solid white; border-bottom: 1px solid white; float: left; padding-bottom: 3%">
    <img src="https://www.pavilionweb.com/wp-content/uploads/2017/03/man-300x300.png" height="70px" width="70px" style="border-radius: 100%; margin-top: 1%; float: left">
    <h2 style="color: white;margin-top: 1%; margin-left: 6%; position: absolute">${list.getAuthorName()}</h2>
    <div style="width: 90%; height: auto; color: white; position: absolute; margin-left: 6%; margin-top: 3.2%">
        ${list.comment}
        <br>
    </div>
</div>
    </#list>
    <script>${warning!}</script>

    <script>
                $(".my-rating-6").starRating({
                    totalStars: 5,
                    emptyColor: 'lightgray',
                    hoverColor: 'slategray',
                    activeColor: 'cornflowerblue',
                    initialRating: ${rait},
                    strokeWidth: 0,
                    useGradient: false,
                    minRating: 1,
                    readOnly: false, //read

                    callback: function(currentRating, $el)  {
                        $.ajax({
                            url: '/film/' + idFilm + '',
                            type: 'GET',
                            dataType: 'json',
                            contentType: 'application/json',
                            mimeType: 'application/json',
                            data: ({
                                text: currentRating
                            })
                        })
                    }
                });

    </script>


</@a.page>