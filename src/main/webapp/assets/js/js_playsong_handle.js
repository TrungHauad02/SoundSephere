import { initializeApp } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-analytics.js";
import {
    getStorage,
    ref,
    getDownloadURL,
} from "https://www.gstatic.com/firebasejs/10.11.0/firebase-storage.js";

import {openAddSongToPlaylistPopup} from "./songModule.js"

window.openAddSongToPlaylistPopup = openAddSongToPlaylistPopup;

const firebaseConfig = {
    apiKey: "AIzaSyAujB-WRTuVQ3abGStWa7oz0RyxN7vmmBQ",
    authDomain: "image-mp3.firebaseapp.com",
    projectId: "image-mp3",
    storageBucket: "image-mp3.appspot.com",
    messagingSenderId: "218542874710",
    appId: "1:218542874710:web:8911549200e5b75fbd5bf7",
    measurementId: "G-R3V5FVGKCC",
};

const appMusic = initializeApp(firebaseConfig);
const analytics = getAnalytics(appMusic);

const storage = getStorage();

const player = document.querySelector(`.player`);
const cdThumb = document.querySelector(".cd-thumb");
const nameSong = document.querySelector("header h2");
const nameAuthor = document.querySelector("header h3");
const audio = document.querySelector("#audio");
const playBtn = document.querySelector(".btn-toggle-play");
const progress = document.getElementById("progress");
const prevBtn = document.querySelector(".btn-prev");
const nextBtn = document.querySelector(".btn-next");
const autoSwitchCheckbox = document.getElementById("customSwitch");
const playList = document.querySelector(".playlist");
const timeDuration = document.getElementById('timeDuration');
let autoSwitchMode = false;

function getImageUrl(imagePath) {
    try {
        let refImg = ref(storage, imagePath);
        let urlImg = getDownloadURL(refImg);
        console.log(urlImg);
        return urlImg;
    } catch (error) {
        // Xử lý lỗi nếu có
        console.log("Loi lay url image");
        console.error(error);
        return null; // Trả về null nếu có lỗi
    }
}

const app = {
    currentIndex: 0,
    isPlaying: false,
    timeout: 1200,
    songs: [],

    loadAllSong: function (){
        const playlist = document.querySelector('.playlist');
        const songs = playlist.querySelectorAll('.song');
        songs.forEach(function(songElement) {
            const thumbElement = songElement.querySelector('.thumb');
            const titleElement = songElement.querySelector('.title');
            const authorElement = songElement.querySelector('.author');
            const pathElement = songElement.querySelector('.sourceSong');
            const idSongElement = songElement.querySelector('.songID');
            const lyricElement = songElement.querySelector('.sourceLyric');

            const name = titleElement.textContent.trim();
            const singer = authorElement.textContent.trim();
            const path = pathElement.textContent.trim();
            const image = thumbElement.textContent.trim();
            const idSong = idSongElement.textContent.trim();
            const lyricSong = lyricElement.textContent.trim();

            // Thêm bài hát vào danh sách songs
            app.songs.push({
                name: name,
                singer: singer,
                path: path,
                image: image,
                id: idSong,
                lyric : lyricSong
            });
        });
        },

    loadCurrentSong: async function () {
        nameSong.textContent = this.currentSong.name;
        try {
            let urlCurrentImg = await getImageUrl(this.currentSong.image);
            console.log(urlCurrentImg);
            cdThumb.style.backgroundImage = `url('${urlCurrentImg}')`;
        } catch (error) {
            console.error("Lỗi khi lấy URL hình ảnh:", error);
        }
        nameAuthor.textContent = this.currentSong.singer;
        this.loadSongFromFirebase();

        timeDuration.textContent = this.formatTime(audio.duration);
    },

    loadSongFromFirebase: async function () {
        try {
            const mp3Ref = ref(storage, this.currentSong.path);
            let url = await getDownloadURL(mp3Ref);
            audio.src = url;

            // Tải văn bản từ Firebase Storage
            const lyricsRef = ref(storage, this.currentSong.lyric);
            let response = await getDownloadURL(lyricsRef);
            let lyricText = await fetch(response);
            lyricText = await lyricText.text();

            const windowLyric = document.getElementById('windowLyric');
            if (lyricText.trim() !== "") {
                windowLyric.innerHTML = '<pre>' + lyricText.replace(/\n/g, '<br>') + '</pre>';
            } else {
                windowLyric.innerHTML = '<h1> Không tìm thấy lời bài hát  </h1>';
            }

        } catch (error) {

        }
    },

    formatTime: function (seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = Math.floor(seconds % 60);
        return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
    },

    defineProperties: function () {
        Object.defineProperty(this, "currentSong", {
            get: function () {
                return this.songs[this.currentIndex];
            },
        });
    },

    handleEvent: function () {
        const _this = this;

        const cdThumbAnimate = cdThumb.animate([{ transform: "rotate(360deg)" }], {
            duration: 10000,
            iterations: Infinity,
        });
        cdThumbAnimate.pause();

        //Playbuton
        playBtn.onclick = function () {
            if (_this.isPlaying) {
                audio.pause();
            } else {
                setTimeout(() => {
                    audio.play();
                }, _this.timeout);
            }
        };

        //Khi được play
        audio.onplay = function () {
            _this.isPlaying = true;
            player.classList.add("playing");
            cdThumbAnimate.play();
        };

        //Khi được pause
        audio.onpause = function () {
            _this.isPlaying = false;
            player.classList.remove("playing");
            cdThumbAnimate.pause();
        };

        // Xử lý khi người dùng nhấn xuống thanh progress
        progress.onmousedown = function (event) {
            const seekTime = (event.target.value * audio.duration) / 100;
            audio.currentTime = seekTime;
            progress.value = event.target.value;
            if (!audio.paused) {
                audio.pause();
            }
        };

        // Xử lý khi người dùng thả ra thanh progress
        progress.onmouseup = function () {
            if (audio.paused) {
                audio.play();
            }
        };

        // Xử lý khi tua song
        progress.onchange = function (e) {
            const seekTime = (e.target.value * audio.duration) / 100;
            audio.currentTime = seekTime;
        };

        //Khi play prgress được chạy theo
        audio.ontimeupdate = function () {
            if (audio.duration) {
                const progressPercent = Math.floor((audio.currentTime / audio.duration) * 100);
                progress.value = progressPercent;

                // Update timeNow
                const timeNow = document.getElementById('timeNow');
                timeNow.textContent = _this.formatTime(audio.currentTime);
                timeDuration.textContent = this.formatTime(audio.duration);
            }
        };

        //Khi next song
        nextBtn.onclick = function () {
            _this.nextSong();
            _this.render();
            setTimeout(() => {
                audio.play();
            }, _this.timeout);
        };

        //Khi prev song
        prevBtn.onclick = function () {
            _this.prevSong();
            _this.render();
            setTimeout(() => {
                audio.play();
            }, _this.timeout);
        };

        //Xử lý khi hết bài hát
        audio.onended = function () {
            if (autoSwitchMode) {
                _this.nextSong();
                setTimeout(() => {
                    audio.play();
                }, _this.timeout);
            }
        };

        playList.onclick = function (e) {
            const songNode = e.target.closest(`.song:not(.active)`);
            const optionNode = e.target.closest(".option");
            if (songNode && optionNode==null) {
                //Xử lý khi click vào song
                if (songNode) {
                    audio.pause();
                    _this.currentIndex = Number(songNode.dataset.index);
                    _this.render();
                    _this.loadCurrentSong();
                    setTimeout(() => {
                        audio.play();
                    }, _this.timeout);
                }
            }
            if(optionNode)
            {
                console.log("Ban vừa nhấn vào");
            }
        };
    },

    render: async function () {
        const htmls = await Promise.all(
            this.songs.map(async (song, index) => {
                try {
                    let urlImg = await getImageUrl(song.image);

                    return `<div class="song ${
                        index === this.currentIndex ? "active" : ""
                    }" data-index="${index}">
        <div class="thumb" style="background-image: url('${urlImg}')" >
        </div>
        <div class="body">
          <h3 class="title">${song.name}</h3>
          <p class="author">${song.singer}</p>
        </div>
        <button class="option btn-outline-success" onclick="openAddSongToPlaylistPopup(${song.id})">Add to playlist</button>
      </div>`;
                } catch (error) {
                    // Xử lý lỗi nếu có
                    console.error(error);
                    return ""; // Trả về chuỗi rỗng nếu có lỗi
                }
            })
        );

        playList.innerHTML = htmls.join(" ");
    },

    nextSong: function () {
        this.currentIndex++;
        if (this.currentIndex >= this.songs.length) {
            this.currentIndex = 0;
        }
        this.loadCurrentSong();
    },

    prevSong: function () {
        this.currentIndex--;
        if (this.currentIndex < 0) {
            this.currentIndex = this.songs.length - 1;
        }
        this.loadCurrentSong();
    },

    start: function () {
        this.loadAllSong();
        //Định nghĩa các thuộc tính object
        this.defineProperties();
        //Lắng nghe và xử lý các sự kiện (DOM events)
        this.handleEvent();
        //Render các danh sách bài hát
        this.render();
        //Tai thong tin
        this.loadCurrentSong();
        playBtn.click();
        timeDuration.textContent = this.formatTime(audio.duration);
    },
};

app.start();

// Xử lý sự kiện khi thay đổi trạng thái của checkbox
autoSwitchCheckbox.addEventListener("change", function () {
    if (this.checked) {
        autoSwitchMode = true;
    } else {
        autoSwitchMode = false;
    }
});

//Xử lý khi nhấn vote
const btnRate = document.getElementById("btnRate");
    btnRate.addEventListener("click", function () {
        $.ajax({
            url: "/SoundSephere/Rate",
            type: "get",
            data: { action: "getRateData", id_song: app.currentSong.id }, // Truyền dữ liệu "action" với giá trị "getImage"
            success: function(data) {
                console.log("Success");
                console.log(app.currentSong.id);
                //Đã từng đánh giá
                if (data!="")
                {
                    var layoutRating = document.getElementById("ratingLayout");
                    layoutRating.innerHTML = data +  "<h3 style=\"color: black;\">Bạn đã đánh giá bài hát này !</h3>";
                    var buttonVote  = document.getElementById("buttonVote");
                    buttonVote.disabled = true;
                }
                else
                {
                    console.log("Chua vote lần nào")
                    var layoutRating = document.getElementById("ratingLayout");
                    var buttonVote  = document.getElementById("buttonVote");
                    layoutRating.innerHTML = "<div class=\"rating\">\n" +
                        "    <input type=\"radio\" id=\"star5\" name=\"rating\" value=\"5\"/><label for=\"star5\"><i class=\"fas fa-star\"></i></label>\n" +
                        "    <input type=\"radio\" id=\"star4\" name=\"rating\" value=\"4\"/><label for=\"star4\"><i class=\"fas fa-star\"></i></label>\n" +
                        "    <input type=\"radio\" id=\"star3\" name=\"rating\" value=\"3\"/><label for=\"star3\"><i class=\"fas fa-star\"></i></label>\n" +
                        "    <input type=\"radio\" id=\"star2\" name=\"rating\" value=\"2\"/><label for=\"star2\"><i class=\"fas fa-star\"></i></label>\n" +
                        "    <input type=\"radio\" id=\"star1\" name=\"rating\" value=\"1\"/><label for=\"star1\"><i class=\"fas fa-star\"></i></label>\n" +
                        "</div>\n";
                    buttonVote.disabled = false;
                }
            },
            error: function(xhr) {
                console.error("Error:", xhr.responseText);
            }
        });
        return null;
    });


var buttonVote  = document.getElementById("buttonVote");
buttonVote.addEventListener("click", function () {
    var selectedRating = document.querySelector('input[name="rating"]:checked');
    var point = selectedRating ? selectedRating.value : null;
    var layoutRating = document.getElementById("ratingLayout");

    console.log(point);
    if (point !== null) {
        $.ajax({
            url: "/SoundSephere/Rate",
            type: "post",
            data: { action: "submitRateData", id_song: app.currentSong.id, point: point },

            success: function(data) {
                layoutRating.innerHTML += "<h3 style=\"color: black;\">Đánh giá bài hát thành công!</h3>";
            },
            error: function(xhr) {
                console.error("Error:", xhr.responseText);
            }
        });
    } else {
        console.error("Please select a rating.");
    }
});


var buttonDetailSong  = document.getElementById("btnSongDetail");
buttonDetailSong.addEventListener("click", function () {
    var layoutInfoDetailSong = document.getElementById("infoDetail");
     $.ajax({
            url: "/SoundSephere/SongPlay/",
            type: "get",
            data: { action: "getDetailSong", id_song: app.currentSong.id},

            success: function(data) {
                layoutInfoDetailSong.innerHTML = data;
            },

            error: function(xhr) {
                console.error("Error:", xhr.responseText);
            }
        });
});

