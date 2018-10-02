$(document).ready(function () {

    $("#btnSubmit").click(function (event) {
        event.preventDefault();
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $("#btnSubmit").prop("disabled", true);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/upload/multi",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000
        }).done(function (data) {
            $("#result").text(JSON.stringify(data));
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);
        }).fail(function (e) {
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);
        });

    });

    $('#downloadBtn').click(function (event) {
        var tokenKey = $('#tokenKey').val();
        event.preventDefault();
        window.location.replace("http://localhost:8080/api/download/" + tokenKey);
    });
});