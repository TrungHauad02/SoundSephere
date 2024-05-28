const lyricLabel = document.querySelector('label[for="lyric-outlined"]');
const playlistLabel = document.querySelector('label[for="playlist-outlined"]');
const windowLyrics = document.querySelector('#windowLyric')
const windowPlaylist = document.querySelector('#windowPlaylist')
console.log(windowLyrics, windowPlaylist)

document.addEventListener('DOMContentLoaded', function() {
    lyricLabel.addEventListener('click', function() {
        // Thực hiện hành động khi bấm vào "Lyrics"
        windowPlaylist.classList.add('window-modal-hidden')
        windowLyrics.classList.remove('window-modal-hidden')
        console.log("Bạn đã chọn Lyrics");
    });

    playlistLabel.addEventListener('click', function() {
        // Thực hiện hành động khi bấm vào "Playlist"
        windowPlaylist.classList.remove('window-modal-hidden')
        windowLyrics.classList.add('window-modal-hidden')
        console.log("Bạn đã chọn Playlist");
    });
});
