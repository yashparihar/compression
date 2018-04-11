var compressfileUpload = document.getElementById("compressfile_upload");
var encoderfileUpload = document.getElementById("encoderfile_upload");

var compressfileDesc = document.getElementById("compressfile_desc");
var encoderfileDesc = document.getElementById("encoderfile_desc");

var uncompressSubmit = document.getElementById("uncompressbtn"); //FOR COMPRESSION SUBMIT

var uncompressMore = document.getElementById("uncompressMore");

var sessionToken = "uncompressToken";
//var downloadFile = document.get
//var fileup = null;
var compressFileup = null;
var encoderFileup = null;

function prepareUpload(e) {
    var ele = e.target;
    var s = ele.files;

    if (ele.id === "compressfile_upload") {
        compressFileup = s[0];
        compressfileDesc.value = ele.value;
    } else {
        encoderFileup = s[0];
        encoderfileDesc.value = ele.value;
    }
}

uncompressMore.addEventListener("click", function () {
    removeSession(sessionToken)
});

compressfileUpload.addEventListener("change", prepareUpload);
encoderfileUpload.addEventListener("change", prepareUpload);

uncompressSubmit.addEventListener("click", function () {

    console.log("uncompress click");
    var data = new FormData();
    data.append("compressfile_upload", compressFileup);
    data.append("encoderfile_upload", encoderFileup);
    apiCall(data, "/uncompress", sessionToken);


});

window.onload = function () {
    toggleWindow(sessionToken);
}