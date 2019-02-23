$(function () {

    $("#token").prop("disabled", true);
    $("#token").prop("hidden", true);

    $("#btnSubmit").click(function (event) {
        event.preventDefault();
        let form = $('#fileUploadForm')[0];
        let data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $("#btnSubmit").prop("disabled", true);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/upload",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000
        }).done(function (data) {
            let temp = data;
            $("#result").html(JSON.stringify(data));
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);
            let copyText = temp.token;
            // alert("You key saved in buffer: " + copyText);
            copyText.select();
            document.execCommand("copy");
        }).fail(function (e) {
            $("#result").html(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);
        });
    });

    $('#downloadBtn').click(function (event) {
        let tokenKey = $('#tokenKey').val();
        event.preventDefault();
        window.location.replace(window.location.origin + "/download/" + tokenKey);
    });
});