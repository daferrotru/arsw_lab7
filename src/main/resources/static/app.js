var app = (function () {

    var nombreJugador="NN";
    
    var stompClient = null;
    var gameid = 0;
    
    var putPlayerData = function(player) {
        nombreJugador = player.name;
        var content = '<div><img src="' + player.photoUrl + '"/></div>' + "</div>"+
        "<div>" + nombreJugador + "</div>";
        document.getElementById("datosjugador").innerHTML = content;
    };
    
    var updateWord = function(eventBody) {
        document.getElementById("palabra").innerHTML = "<h1>" + eventBody.body + "</h1>";
    };
    
    var putWinner = function(eventBody) {
        var content = "<div>Estado: Ganado!</div>" + "<div>Ganador: " + eventBody.body + "</div>";
        document.getElementById("status").innerHTML = content;
    };

    return {

        loadWord: function () {

            gameid = $("#gameid").val();
            
            $.get("/hangmangames/" + gameid +"/currentword",
                    function (data) {
                        $("#palabra").html("<h1>" + data + "</h1>");
                    }
            ).fail(
                    function (data) {
                        alert(data["responseText"]);
                    }
            );


        }
        ,
        wsconnect: function () {

            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {

                //console.log('Connected: ' + frame);

                //subscriptions
                stompClient.subscribe("/topic/wupdate." + gameid, updateWord);
                stompClient.subscribe("/topic/winner." + gameid, putWinner);
            
            });

        },

        sendLetter: function () {

            var id = gameid;

            var hangmanLetterAttempt = {letter: $("#caracter").val(), username: nombreJugador};

            //console.info("Gameid:"+gameid+",Sending v2:"+JSON.stringify(hangmanLetterAttempt));


            jQuery.ajax({
                url: "/hangmangames/" + id + "/letterattempts",
                type: "POST",
                data: JSON.stringify(hangmanLetterAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    //
                }
            });


        },

        sendWord: function () {
            
            var hangmanWordAttempt = {word: $("#adivina").val(), username: nombreJugador};
            
            //console.info("Gameid:"+gameid+",Sending v2:"+JSON.stringify(hangmanWordAttempt));
            
            var id = gameid;

            jQuery.ajax({
                url: "/hangmangames/" + id + "/wordattempts",
                type: "POST",
                data: JSON.stringify(hangmanWordAttempt),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function () {
                    //
                }
            });

            
        },
        
        getPlayer: function() {
            var idPlayer = $("#playerid").val();
            jQuery.get("/users/" + idPlayer, putPlayerData);
        } 

    };

})();

