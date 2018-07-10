/**
 * Created by psundriyal on 6/1/16.
 */
$(document).ready(function() {
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
                    $("#results").append('<a target="_blank" rel="noopener noreferrer" href="' +data[i].url+'"> Package Price :'
                        + data[i].packageNetPrice + '<br>Origin : '+data[i].origin+'&nbsp;&nbsp;&nbsp;&nbsp;Destination: '+data[i].destination+'<br>'
                        +'Outbound DateTime: '+data[i].outboundDate+'&nbsp;&nbsp;&nbsp;&nbsp;Inbound DateTime: '
                        +data[i].inboundDate+'<br>'+ 'Flight No: ' + data[i].flightNo +'<br><b>Savings ' + data[i].savings+'%<b></a><br><br><br>');
                }
            },
            error: function(data) {
                //Do Something to handle error
            }
        });
    });
});
