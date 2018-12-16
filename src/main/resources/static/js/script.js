$(function () {
    var droppedFiles = false;
    var fileName = '';
    var $dropzone = $('.dropzone');
    var uploading = false;
    var $syncing = $('.syncing');
    var $done = $('.done');
    var $bar = $('.bar');

    $dropzone.on('drag dragstart dragend dragover dragenter dragleave drop', function (e) {
        e.preventDefault();
        e.stopPropagation();
    })
        .on('dragover dragenter', function () {
            $dropzone.addClass('is-dragover');
        })
        .on('dragleave dragend drop', function () {
            $dropzone.removeClass('is-dragover');
        })
        .on('drop', function (e) {
            droppedFiles = e.originalEvent.dataTransfer.files;
            fileName = droppedFiles[0]['name'];
            $('.filename').html(fileName);
            $('.dropzone .upload').hide();
        });

    $("#uploadFile").bind('click', function () {
        startUpload();
    });

    $("input:file").change(function () {
        fileName = $(this)[0].files[0].name;
        $('.filename').html(fileName);
        $('.dropzone .upload').hide();
    });

    $("#downLoadFile").bind('click', function () {
        Swal({
            title: 'Set key for downloading',
            input: 'text',
            inputAttributes: {
                autocapitalize: 'off'
            },
            showCancelButton: true,
            confirmButtonText: 'Download',
            showLoaderOnConfirm: true,
        }).then((result) => {
            if (result.value) {
                download(result.value);
            }
        })
    });

    $("#sendFile").bind('click', function () {
        sendInstructions();
    });

    function startUpload() {
        if (!uploading && fileName != '') {
            uploading = true;
            $("#uploadFile").html('Uploading...');
            $dropzone.fadeOut();
            $syncing.addClass('active');
            $done.addClass('active');
            $bar.addClass('active');
            upload();
        }
    }

    function upload() {
        event.preventDefault();
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $("#uploadFile").prop("disabled", true);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/transform",
            data: data,
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            timeout: 600000
        }).done(function (data) {
            console.log("SUCCESS : ", data);
            $("#uploadFile").prop("disabled", false);
            showDone();
        }).fail(function (e) {
            console.log("ERROR : ", e);
            $("#uploadFile").prop("disabled", false);
            swal("Oops..", e.responseText, "error");
        });
    }

    function showDone() {
        $("#uploadFile").html('Done');
        $("#sendFile").prop("disabled", false);
        $("#downLoadFile").prop("disabled", false);
    }

    function download(key) {
        window.location.replace("http://localhost:8090/download/" + key);
    }

    function sendInstructions() {
        event.preventDefault();
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);
        data.append("CustomField", "This is some extra data, testing");
        $("#sendFile").prop("disabled", true);
        $.ajax({
            type: "GET",
            url: "/send",
            timeout: 600000
        }).done(function (data) {
            console.log("SUCCESS : ", data);
            $("#sendFile").prop("disabled", false);
            showDone();
        }).fail(function (e) {
            console.log("ERROR : ", e);
            $("#sendFile").prop("disabled", false);
            swal("Oops..", e.responseText, "error");
        });
    }
})