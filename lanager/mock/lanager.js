#!/usr/bin/env node
var http = require("http");

function success (response, message)
{
    response.statusCode = 200;
    response.write('<div class="alert alert-success">' + message + '</div>\n');
}
function error (response, message)
{
    response.statusCode = 200;
    response.write('<div class="alert alert-danger">' + message + '</div>\n');
}

var server = http.createServer(
    function (request, response)
    {
        if (request.url == "/dup")
        {
            error(response, "The item has already been added to this playlist");
        }
        else if (request.url == "/toolong")
        {
            error(response, "The item's duration must not exceed 1337 minutes");
        }
        else
        {
            success(response, "successfully created playlist item");
        }
        response.end();
    }
);

server.listen(54321);
