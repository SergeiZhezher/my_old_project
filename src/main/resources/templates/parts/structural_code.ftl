<#macro structural_code>
    <div id="upPanel"
         style="z-index: 30; background: #292930; position: fixed;  margin-top: -3.5%; margin-left: -1%; width: 101%; height: 6%; opacity: 0.8"
         xmlns="http://www.w3.org/1999/html">
        <input id="searchField" type="text" onfocus="this.removeAttribute('readonly');" readonly="readonly" autocomplete="off" placeholder="Search" style="margin-top: 0.5%; margin-left: 9%; width: 34%; height: 65%; background: #7DA2A9; color: white; padding-left: 2%"/>

        <div class="social-buttons">
            <a href="#"><i class="fab fa-facebook-f"></i></a>
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-instagram"></i></a>
            <a href="#"><i class="fab fa-youtube"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
        </div>

        <div id="Auth" style="color: white; cursor: pointer; font-family: Georgia; margin-left: 92%; margin-top: -2.9%; font-size: 20px; user-select: none">Reg/Log</div>
        <div id="hint"  style=" position: absolute; background: black; opacity: 0.9; margin-left: 9%; margin-top: 0.93%; width: 34%;" ></div>

    </div>

    <section class="regular slider">
        <#list sliderFilm as sl>
            <div>

                <a href="/film/${sl.getId()}">
                    <img src="/img/${sl.getFilmName()}.jpg"/> </a>
                    <div style="position: relative; text-align: center; pointer-events: none; bottom: 145px; height: 110px; opacity: 0.7; background: black">
                    <span style="color: white; position: relative; top: 8%; font-family: Georgia; font-weight: 500" >${sl.getFilmName()}</span> <br/>
                    <span style="color: white; position: relative; top: 16%; font-family: Georgia; font-weight: 300" >Жанр - ${sl.getGenre()}</span> <br/>
                    <span style="color: white; position: relative; top: 25%; font-family: Georgia; font-weight: 300" >Год - ${sl.getYears()}</span> <br/>
                    </div>
        </div>
        </#list>
    </section>

    <div class="menu-tab">
        <div id="one"></div>
        <div id="two"></div>
        <div id="three"></div>
    </div>

    <div class="menu-hide">
        <span style="color: #F7F7F7;  margin-left: 5%; font-family: Georgia; font-size: 25px;">Жанры:</span>
        <nav>
            <ul>

                <li><a href="/Боевик">Боевик</a></li>
                <li><a href="/Военное">Военное</a></li>
                <li><a href="/Драма">Драма</a></li>
                <li><a href="/Документальный">Документальный</a></li>
                <li><a href="/Комедия">Комедия</a></li>
                <li><a href="/Мультфильм">Мультфильм</a></li>
                <li><a href="/Приключения">Приключения</a></li>
                <li><a href="/Семейный">Семейный</a></li>
                <li><a href="/Трилеры">Трилеры</a></li>
                <li><a href="/Трейлеры">Трейлеры</a></li>
                <li><a href="/Ужасы">Ужасы</a></li>
                <li><a href="/Фантастика">Фантастика</a></li>

            </ul>
        </nav>
    </div>

    <div class="login-wrap">
        <div class="login-html">
            <input id="tab-1" type="radio" name="tab" class="sign-in" checked="checked"/><label for="tab-1" class="tab">Sign In</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up"/><label for="tab-2" class="tab">Sign Up</label>
            <div class="login-form">
                <div class="sign-in-htm">
                    <form method="post" action="/login">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <div class="group">
                        <label for="user" class="label">Username</label>
                        <input id="user" type="text" name="username" class="input"/>
                    </div>
                    <div class="group">
                        <label for="pass" class="label">Password</label>
                        <input id="pass" type="password" name="password" class="input" data-type="password"/>
                    </div>
                    <div class="group">
                        <input id="check" type="checkbox" class="check" checked="checked"/>
                        <label for="check"><span class="icon"></span> Keep me Signed in</label>
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign In"/>
                    </div>
                    </form>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <a href="#forgot" style="color:#6a6f8c;">Forgot Password?</a>
                    </div>
                </div>
                <div class="sign-up-htm">
                    <form method="post" action="/registration">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <div class="group">
                        <label for="user" class="label">Username</label>
                        <input id="user" type="text" name="username" class="input ${(usernameError??)?string('is-invalid', '')}" value="<#if user??>${user.username}</#if>"/>
                        <#if usernameError??>
                                <script> alert("${usernameError}"); </script>
                        </#if>
                    </div>
                    <div class="group">
                        <label for="pass" class="label">Password</label>
                        <input id="pass" type="password" name="password" class="input ${(passwordError??)?string('is-invalid', '')}" data-type="password"/>
                        <#if passwordError??>
                            <script> alert("${passwordError}"); </script>
                        </#if>
                    </div>
                    <div class="group">
                        <label for="pass" class="label">Email Address</label>
                        <input id="pass" placeholder="email@gmail.com" type="email" name="email" class="input ${(emailError??)?string('is-invalid', '')}"/>
                        <#if emailError??>
                            <script> alert("${emailError}"); </script>
                        </#if>
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign Up"/>
                    </div>
                        <div style="transform:scale(0.70);transform-origin:0 0; display: inline-block; margin-right: 30px;">
                            <div class="g-recaptcha" data-sitekey="6Le9u9sUAAAAAP4qoFkqsz8XcicStY0QYnGrxTLJ"></div>
                            <#if captchaError??>
                                <script>alert("${captchaError}")</script>
                            </#if>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </div>

    <div class="navbar">

        <div class="dropdown">
            <button class="dropbtn">Cтрана
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="dropdown-content">
                <a href="#">Скоро...</a>
            </div>
        </div>

        <div class="dropdown">
            <button class="dropbtn">Год выпуска
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="dropdown-content">
                <a href="#">Скоро...</a>
            </div>
        </div>

        <div class="dropdown">
            <button class="dropbtn">Рейтинг
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="dropdown-content">
                <a href="#">Скоро...</a>
            </div>
        </div>

        <div class="dropdown">
            <button class="dropbtn">Качество
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="dropdown-content">
                <a href="#">Скоро...</a>
            </div>
        </div>
    </div>
</#macro>