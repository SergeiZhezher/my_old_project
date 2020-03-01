<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>admin panel</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" type="text/css" href="/static/css/admin.css">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js"></script>
  </head>
  <body>

    <div class="contact-section">
      <div class="inner-width">
        <h1>Меню добавления фильмов</h1>
          <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" class="name" name="filmName" placeholder="Film name ">
        <input type="email" class="email" placeholder="Raiting">

            <select class="genre" name="genre" id="sel">
              <option>Биография</option>
              <option>Боевик</option>
              <option>Вестерн</option>
              <option>Военное</option>
              <option>Детектив</option>
              <option>Драма</option>
              <option>Документальный</option>
              <option>Исторический</option>
              <option>Комедия</option>
              <option>Криминал</option>
              <option>Мелодрама</option>
              <option>Мультфильм</option>
              <option>Мюзикл</option>
              <option>Приключения</option>
              <option>Семейный</option>
              <option>Спортивный</option>
              <option>Трилеры</option>
              <option>Ужасы</option>
              <option>Фантастика</option>
            </select>

        <textarea rows="1" placeholder="Описания" class="message"></textarea>
        <input class="load" type="file" name="file" value="load video">
        <input class="load" type="submit" value="Опубликовать ">
          </form>
      </div>
    </div>
    <img src="/img/${text!}" height="250px" width="150px">
  </body>
</html>
