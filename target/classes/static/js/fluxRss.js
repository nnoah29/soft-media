function getFluxRss() {
    $.ajax({
        url: "/api/fluxRss"
    }).then(function (data) {
        console.log("Flux Rss")
        console.log(data);
        document.getElementById('title').innerText = data.title;
        document.getElementById('contenu').innerHTML = data.content;
        // array_videos = data;
        // video.src = '/media/video?videoName=' + array_videos[0];
        // console.log($('#video').parents())
        // console.log("parent body")
        // console.log($('body').parents())
        // console.log("parent document")
        // console.log($('#document').parents())
        //console.log(v.parent());
    });
}