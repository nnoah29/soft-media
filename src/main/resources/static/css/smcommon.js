var v = document.getElementsByTagName("video")[0];
// v.volume = 0.2;
var array_videos = []; //Array with video names
var index = 0; //Index of video
var video = document.getElementById("video"); //Html5 video element
// var banner = document.getElementById("banner"); //Html5 video element
/*$(document).ready(function() {

});*/

function readInfo() {
    $.ajax({
        url: "/media/list"
    }).then(function (data) {
        console.log('data ');
        console.log(data);
        array_videos = data;
        if (array_videos.length !== 0 || array_videos != null) {

            video.src = '/media/video?videoName=' + array_videos[0];
        }
        // console.log($('#video').parents())
        // console.log("parent body")
        // console.log($('body').parents())
        // console.log("parent document")
        // console.log($('#document').parents())
        //console.log(v.parent());
    });


    // banner.innerText="Test de loin";
}


function nextVideo() {

    index++;
    if (index === array_videos.length) {
        readInfo();
        index = 0;

    } else if (array_videos.length === 0) {
        console.log('pas de video');
    } else {
        console.log("array index " + index + array_videos[index]);
        video.src = '/media/video?videoName=' + array_videos[index];
    }
}

function load() {
    readInfo();
    getFluxRss();
    video.addEventListener("ended", nextVideo, false);
    video.addEventListener("error", nextVideo, true);
    //loadBanner();
    setInterval(getFluxRss(), 60 * 1000)
}

window.onload = load;