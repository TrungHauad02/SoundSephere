import {getImageFromFirebase} from "./firebaseModule.js";


export function goToPlaylistDetail(playlistId) {
    window.location.href = `playlist_detail.jsp?playlistId=${playlistId}`;
}


document.addEventListener('DOMContentLoaded', function() {
    loadPlaylistDetail();
});

export function loadPlaylistDetail(){
    const playlistId = getPlaylistIdFromURL();
    console.log(playlistId);
    fetchDataPlaylistDetail(playlistId)
        .then(data => {
            renderPlaylistDetail(data);
        })
        .catch(error => {
            console.error('Lỗi trong quá trình lấy dữ liệu:', error);
        });
}

export function fetchDataPlaylistDetail(playlistId){
    const payload = {
        playlistId: playlistId
    };
    return fetch('http://localhost:8080/SoundSephere/Playlist/getPlaylistDetail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(response => {
            console.log(response)
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Data:', data);
            return data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

export function renderPlaylistDetail(data){
    console.log('Data:', data);
    const playlistName = document.getElementById('playlistName');
    playlistName.textContent = data.playlist.name;
    console.log(data.playlist.name);
    const numberOfSong = document.getElementById('numberOfSong');
    numberOfSong.textContent = "Number of song: " + data.playlist.number_of_songs;
    console.log(data.playlist.number_of_songs);

    const container = document.getElementById('songsContainer');
    if (!container) return;

    container.innerHTML = '';
    data.songs.forEach(function(song) {
        const songCard = createSongCardForPlaylist(song);
        container.appendChild(songCard);
    });
}

function getPlaylistIdFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('playlistId');
}

export function createSongCardForPlaylist(song){
    const li = document.createElement('li');
    li.className = 'list-group-item d-flex justify-content-between align-items-center';

    const contentDiv = document.createElement('div');
    contentDiv.className = 'row align-items-center w-100';

    const badgeDiv = document.createElement('div');
    badgeDiv.className = 'col-auto';

    const badgeImg = document.createElement('img');
    badgeImg.className = 'rounded-circle';
    badgeImg.style.backgroundColor = '#3E862C';
    badgeImg.style.height = '50px';
    badgeImg.style.width = '50px';
    badgeImg.style.objectFit = 'cover';

    getImageFromFirebase(song.image)
        .then((url) => {
            console.log(url)
            badgeImg.src = url;
            badgeDiv.appendChild(badgeImg);
        })
        .catch((error) => {
            console.error('Error fetching image:', error);
        });

    const songInfoDiv = document.createElement('div');
    songInfoDiv.className = 'col';
    const titleH6 = document.createElement('h6');
    titleH6.className = 'card-title';
    titleH6.textContent = song.title;
    const descriptionP = document.createElement('p');
    descriptionP.className = 'card-text';
    descriptionP.textContent = song.description;
    songInfoDiv.appendChild(titleH6);
    songInfoDiv.appendChild(descriptionP);

    const playSongA = document.createElement('a');
    playSongA.className = 'playSong nav-link';
    playSongA.href = '/SoundSephere/SongPlay/getSong?idSong=' + song.id;
    playSongA.textContent = 'Play';

    contentDiv.appendChild(badgeDiv);
    contentDiv.appendChild(songInfoDiv);
    contentDiv.appendChild(playSongA);

    li.appendChild(contentDiv);

    return li;
}