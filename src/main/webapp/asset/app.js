
var downloadSec = document.getElementsByClassName("download_section");
var uploadSec = document.getElementsByClassName("upload_section");

var compSecLink = document.getElementById("compSecLink");
var decompSecLink = document.getElementById("decompSecLink");

var compSec = document.getElementById("compSec");
var decompSec = document.getElementById("decompSec");

var loader = document.getElementById("loader");

function sectionToggle(e) {
    var ele = e.target.id;

    if (ele == "compSecLink") {
        compSecLink.classList.add("activelink");
        decompSecLink.classList.remove("activelink");

        compSec.classList.add("active");
        decompSec.classList.remove("active");

    } else {
        decompSecLink.classList.add("activelink");
        compSecLink.classList.remove("activelink");
        decompSec.classList.add("active");
        compSec.classList.remove("active");
    }
}


function toggleWindow(token) {

    if (sessionStorage.getItem(token)) {
        //  console.log(sessionStorage.getItem("isAlive"));
        //uploadSec.style.display = "none";
        for(var i=0, len=uploadSec.length; i<len; i++) {
            uploadSec[i].style["display"] = "none";
        }
        for(var i=0, len=downloadSec.length; i<len; i++) {
            downloadSec[i].style["display"] = "block";
        }
        // OPEN DOWNLOAD SECTION
    } else {
        // sessionStorage.setItem("isAlive", "yes");
        for(var i=0, len=uploadSec.length; i<len; i++) {
            uploadSec[i].style["display"] = "block";
        }
        for(var i=0, len=downloadSec.length; i<len; i++) {
            downloadSec[i].style["display"] = "none";
        }
    }
}

/*
// NOT USED
function downloadToggle(e) {
	var ele = e.target.id;
	var url = "/hello?downloadFile=" + ele;
	
	fetch(url)
	.then(function(result) {
		return result.json();
	})
	.then(function(data){
		console.log(data);
	})

};
*/

function removeSession(token) {
    sessionStorage.removeItem(token);
    toggleWindow(token);
}

function apiCall(data, link, token) {
    $.ajax({
        type: "POST",
        url: link,
        dataType: "json",
        data: data,
        // async: false,
        // cache: false,
        processData: false, // Don't process the files
        contentType: false, // Set content type to false as jQuery will
        // tell the server its a query string
        // request

        beforeSend: function() {
        	if (!loader.classList.contains("loader")){
        		loader.classList.add("loader")
        	}
        },
        success: function (response) {
        	 setTimeout(function(){
                 //do what you need here
             result = response;
             console.log(result);

             sessionStorage.setItem(token, result.sessionToken);
             toggleWindow(token);
             }, 4000);
        },
        
        complete: function() {
        	if (loader.classList.contains("loader")){
        		loader.classList.remove("loader")
        	}
        },
    });
}


//  compressMore.addEventListener("click", removeSession);


compSecLink.addEventListener("click", sectionToggle);
decompSecLink.addEventListener("click", sectionToggle);


