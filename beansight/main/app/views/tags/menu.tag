<!-- MENU NAV -->
<h2 class="azurme"><span class="cuf-grb">&{'menutitle'}</span></h2>
<ul class="menuleft">
    <li><a href="@{Application.insights()}" class="cuf-grb #{currentMenu 'menuInsights' /}">&{'menuinsights'}</a></li>
#{isConnected}    
    <li><a href="@{Application.profile()}#userinsights" class="cuf-grb #{currentMenu 'menuProfile' /}">&{'menuprofile'}</a></li>
#{/isConnected}
    <li><a href="@{Application.experts()}" class="cuf-grb #{currentMenu 'menuExperts' /}">&{'menuexperts'}</a></li>
#{isNotConnected}
    <li><a href="@{Register.register()}" class="cuf-grb #{currentMenu 'menuSignUp' /}">&{'signuplink'}</a></li>
#{/isNotConnected}
</ul>