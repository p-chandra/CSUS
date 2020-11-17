<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<center>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Random Key</title>
</head>

<body background="C:\Users\p-chandra\Documents\Workspace\QuickServletApp\WebContent\temp.jpg">>
<font size="14" color="white">

<%Object randomKey = request.getAttribute("randomKey");%>
<%=randomKey%>

<input type="hidden" name="FormName" value="RandKey" />
</font>
<br><br/>

<a href="https://docs.google.com/spreadsheets/d/1HEkPX-wEowUAOSH3rAzwLOndnAMZ_WsCkxR_aonbyu8/edit#gid=0">Manual Entry</a>

<br><br/>
<br><br/>
<font size="5" color="white">
<body>
    <div><span id="time">15:00</span></div>
</body>

<script>
function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10)
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = duration;
        }
    }, 1000);
}

window.onload = function () {
    var fifteenMinutes = 60 * 15,
        display = document.querySelector('#time');
    startTimer(fifteenMinutes, display);
};

</script>
</font>
</center>
</body>
</html>