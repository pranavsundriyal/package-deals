/**
 * Created by psundriyal on 6/1/16.
 */
$(document).ready(function() {
    var totalDeals;
    var dealNum = 0;

    $("#search").click(function () {
        $("#status").append("Searching for Results ........");
        $("#results").empty();
        $("#airlinePercentage").empty();
        $.ajax({
            type: "GET",
            url: "getCachedDeals",
            data: {
                origin: $( "#origin" ).selectmenu().val(),
                dest: $("#dest").val(),
                month: $( "#month" ).selectmenu().val(),
                noOfDaysLower: $("#noOfDaysLower").val(),
                noOfDaysHigher: $("#noOfDaysHigher").val(),
                startDayOfWeek: $("#start_day").selectmenu().val(),
                endDayOfWeek: $("#end_day").selectmenu().val(),
                sort: $("#sortBy").selectmenu().val(),
                carrierCode: $("#carrierCode").selectmenu().val()
            },
            success: function (data) {
                $("#status").empty();
                $("#results").empty();

                var adults = $( "#noAdults" ).selectmenu().val();
                var airlineDominanceMap = data.airlineDominanceMap;
                renderAirlinePercentageData(airlineDominanceMap);

                var deals = data.packageDeals;
                for(var i=0; i<deals.length; i++) {
                    totalDeals = deals.length;
                    $("#results").append('<div class="col-md-4" id="deal-card-' + i + '"><a target="_blank" rel="noopener ' +
                        'noreferrer" style="text-decoration : none" href="'
                        + deals[i].url+'&passengers=adults:'+$( "#noAdults" ).selectmenu().val()+'&adults='+adults
                        + '"><div class="package-price"><h5><b>Approx. Per Person Price '+isHotelIncluded(deals[i].package)+' : $'
                        + calculatePrice(deals[i], adults) + '</b></h5></div><div><h5>Origin: ' + deals[i].origin
                        + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Destination: ' + deals[i].destination + '</h5></div>'
                        + '<div><h5>Journey Start Time: ' + deals[i].outboundDate + '</h5></div>'
                        + '<div><h5>Journey End Time: ' + deals[i].inboundDate + '</h5></div>'
                        + '<div><h5>Flight No: ' + deals[i].flightNo+ '</h5></div>'
                        + '<div><h5>No. of Days: ' + deals[i].noOfDays + '</h5></div>'
                        + '<div><h5>'+path(deals[i].package) + '&nbsp;Deal</h5></div></a><br><br></div>');
                }

            },
            error: function(data) {
                //Do Something to handle error
            }
        });
    });

    function renderAirlinePercentageData(data) {
        $("#airlinePercentage").append('<div><b>Carrier Percentage in the search : </b><br></div>');
        var i = 0;
        $.each(data, function(key, value) {
            $("#airlinePercentage").append(key+'&nbsp;-&nbsp;'+value+'% &nbsp;&nbsp;&nbsp;');
            i++
            if(i%10 == 0) {
                $("#airlinePercentage").append('<br>');
            }
        });
    }

    function isHotelIncluded(isPackage) {
        if(path(isPackage) == "Package") {
            return "(Hotel Included)";
        }

        return "";
    }

    function path(isPackage) {
        if(isPackage) {
            return "Package"
        } else {
            return "Flight";
        }
    }

    function getSavings(savings) {
        if(savings != null) {
            return "Package Saving of : " + savings;
        }
        return "";
    }

    function calculatePrice(deal, adults) {
        if (deal.package){
            return twoDigitPrices((adults * deal.packageNetPrice + 100 * deal.noOfDays)/adults)
        }
        return twoDigitPrices(deal.packageNetPrice)
    }



    $("#search").click(function () {
    $("#summary").empty();
    $.ajax({
            type: "GET",
            url: "getDealSummary",
            data: {
                origin: $("#origin").val()
            },
            success: function (data) {
                $("#summary").empty();
                $("#summary").append('<div><b>Top Deals for cities starting from : </b><br></div>');
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
