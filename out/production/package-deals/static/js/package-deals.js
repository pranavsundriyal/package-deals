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
            },
            success: function (data) {
                $("#status").empty();
                $("#results").empty();

                for(var i=0; i<data.length; i++) {
                    $("#results").append(i + ') ' +data[i].packageNetPrice + ' | '+data[i].origin+' | '+data[i].destination+' | '
                         +data[i].flightNo +' | '+data[i].outboundDate+' | '+data[i].inboundDate+'<br>');
                }
            },
            error: function(data) {
                //Do Something to handle error
            }
        });
    });
});
