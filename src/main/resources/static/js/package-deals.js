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
                dest: $("#dest").val(),
                month: $("#month").val(),
                year: $("#year").val(),
                noOfDays: $("#noOfDays").val(),
                path: $('input[name=path]:checked').val()

            },
            success: function (data) {
                $("#status").empty();
                $("#results").empty();

                for(var i=0; i<data.length; i++) {
                    totalDeals = data.length;
                    $("#results").append('<div class="col-md-4" id="deal-card-' + i + '"><a target="_blank" rel="noopener noreferrer" style="text-decoration : none" href="' + data[i].url
                        + '"><div class="package-price"><h5><b>Approx. Package Price: $'
                        + twoDigitPrices(data[i].packageNetPrice) + '</b></h5></div><div><h5>Origin: ' + data[i].origin
                        + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Destination: ' + data[i].destination + '</h5></div>'
                        + '<div><h5>Journey Start Time: ' + data[i].outboundDate + '</h5></div><div><h5>Journey End Time: '
                        + data[i].inboundDate + '</h5></div>'+ '<div><h5>Flight No: ' + data[i].flightNo+ '<b></h4></div></a><br></div>');
                }
            },
            error: function(data) {
                //Do Something to handle error
            }
        });
    });

    $("#status").append("getting summary");
    $("#results").empty();
    $.ajax({
            type: "GET",
            url: "getDealSummary",
            data: {},
            success: function (data) {
                $("#status").empty();
                $("#summary").empty();

                var i = 0;
                $.each(data, function(key, value) {
                    $("#summary").append(key+'&nbsp;-&nbsp;'+value+'$ &nbsp;&nbsp;&nbsp;');
                    i++
                    if(i%10 == 0) {
                        $("#summary").append('<br>');
                    }
                });


            },
            error: function(data) {
                //Do Something to handle error
            }

    });

    function twoDigitPrices(savings) {
        return savings.toFixed(2);
    }

  /*  $("#search").click(function () {
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
   */
});
