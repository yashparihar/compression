var fileUpload = document.getElementById("file_upload");

var fileDesc = document.getElementById("file_desc");

//var uploadSec = document.getElementById("upload_section");
//var downloadSec = document.getElementById("download_section");

var encoderDown = document.getElementById("encoderFileDownload");
var compressDown = document.getElementById("compressFileDownload");

var submitbtn = document.getElementById("submitbtn"); //FOR COMPRESSION SUBMIT

var compressMore = document.getElementById("compressMore");
//var downloadFile = document.get
var fileup = null;
var sessionToken = "sessiontoken";

function prepareUpload(e) {
    var ele = e.target;
    fileDesc.value = ele.value;
    console.log(ele);
    //console.log(e.target.files);

    var s = ele.files;
    fileup = s[0];
}

compressMore.addEventListener("click", function(){
    removeSession(sessionToken);
});


//encoderDown.addEventListener("click", downloadToggle);
//compressDown.addEventListener("click", downloadToggle);
fileUpload.addEventListener("change", prepareUpload);

submitbtn.addEventListener("click", function () {

    console.log("submit click");
    var data = new FormData();
    data.append("file_upload", fileup);
    apiCall(data, "/compress", sessionToken);
 
});

window.onload = function () {
    toggleWindow(sessionToken);
}