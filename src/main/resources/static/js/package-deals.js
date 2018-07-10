/**
 * Created by psundriyal on 6/1/16.
 */
$(document).ready(function() {
    var totalDeals;
    var dealNum = 0;

    $("#search").click(function () {
        $("#status").append("Searching for Results ........");
        $("#results").empty();
        $.ajax({
            type: "GET",
            url: "getCachedDeals",
            data: {
                origin: $("#origin").val(),
                dest: $("#dest").val()
            },
            success: function (data) {
                $("#status").empty();
                $("#results").empty();

                for(var i=0; i<data.length; i++) {
                    totalDeals = data.length;
                    $("#results").append('<div class="col-md-4" id="deal-card-' + i + '"><a target="_blank" rel="noopener noreferrer" style="text-decoration : none" href="' + data[i].url + '"><div class="package-price"><h3>Package Price: $'
                        + data[i].packageNetPrice + '</h3></div><div class="savings"><h4><b>Savings ' + twoDigitPrices(data[i].savings) +'%<b></h4></div><div><h5>Origin: ' + data[i].origin+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Destination: ' + data[i].destination + '</h5></div>'
                        + '<div><h5>Outbound Date & Time: ' + data[i].outboundDate + '</h5></div><div><h5>Inbound Date & Time: '
                        + data[i].inboundDate + '</h5></div>'+ '<div><h5>Flight No: ' + data[i].flightNo +'</h5></div></a><br></div>');
                }
            },
            error: function(data) {
                //Do Something to handle error
            }
        });
    });

    function twoDigitPrices(savings) {
        return savings.toFixed(1);
    }

    $("#search").click(function () {
        var timeInSecs;
        var ticker;

        function startTimer(secs){
            timeInSecs = parseInt(secs)-1;
            ticker = setInterval(tick ,1000);
        }

        function tick() {
            var secs = timeInSecs;
            $("#search-again").empty();
            if (secs > 0 && dealNum < totalDeals) {
                timeInSecs--;
            }
            else {
                clearInterval(ticker);
                if (dealNum < totalDeals){
                    for(var i = 0; i < 8; i++) {
                        $("#deal-card-" + dealNum).addClass("hide-deal-card");
                        dealNum++
                    }
                    startTimer(31);
                }
                else {
                    $("#timer").css("display", "none");
                    document.getElementById("search-again").innerHTML = 'All deals have been shown, please search again.';
                }
            }
            document.getElementById("timer").innerHTML = secs + ' seconds until new deals will be revealed';
        }
        startTimer(31);
    });
});
