import {getImageFromFirebase, uploadFileToFirebase} from './firebaseModule.js';
import {fetchDataAlbum} from "./albumModule.js";
import {loadSong} from "./artist_main.js";

export function openAddSongPopup() {
    const addSongPopup = document.getElementById('addSongPopup');
    addSongPopup.style.display = 'flex';
}

export function addNewSong() {
    const songNameInput = document.getElementById('songName').value;
    const descriptionSongInput = document.getElementById('descriptionSong').value;
    const writtenByInput = document.getElementById('writtenBy').value;
    const producedByInput = document.getElementById('producedBy').value;
    const imageInput = document.getElementById('imageInput').files[0];
    const mp3FileInput = document.getElementById('mp3File').files[0];
    const lyricFileInput = document.getElementById('lyricFile').files[0];
    const timePlayInput = document.getElementById('timePlay').value;

    const songData = {
        songName: songNameInput,
        description: descriptionSongInput,
        writtenBy: writtenByInput,
        producedBy: producedByInput,
        timePlay: timePlayInput,
        imageFileName: imageInput ? imageInput.name : null,
        mp3FileName: mp3FileInput ? mp3FileInput.name : null,
        lyricFileName: lyricFileInput ? lyricFileInput.name : null
    };
    console.log(JSON.stringify(songData));
    fetch('http://localhost:8080/SoundSephere/SongPlay/addNewSong', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(songData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.result) {
                if (imageInput) {
                    uploadFileToFirebase(imageInput, `myimages/${imageInput.name}`);
                }
                if (mp3FileInput) {
                    uploadFileToFirebase(mp3FileInput, `audioSongs/${mp3FileInput.name}`);
                }
                if (lyricFileInput) {
                    uploadFileToFirebase(lyricFileInput, `lyricsSongs/${lyricFileInput.name}`);
                }
                window.alert('Song created successfully');
                closeSongPopup();
                loadSong();
            } else {
                window.alert('Failed to create song');
            }
        })
        .catch(error => {
            console.error('Error creating song:', error);
        });
}

export function closeSongPopup() {
    const addSongPopup = document.getElementById('addSongPopup');
    addSongPopup.style.display = 'none';
}

export function openAddSongToAlbumPopup(songId) {
    const addSongToAlbumPopUp = document.getElementById('addSongToAlbumPopUp');
    addSongToAlbumPopUp.style.display = 'flex';

    const btnCloseAlbumPopup = document.getElementById('btnCloseAlbumPopup');
    const btnCloseAlbumPopupTop = document.getElementById('btnCloseAlbumPopupTop');
    btnCloseAlbumPopup.addEventListener('click', function() {
        closeAddSongToAlbumPopup();
    });
    btnCloseAlbumPopupTop.addEventListener('click', function() {
        closeAddSongToAlbumPopup();
    });
    const albumItems = document.getElementById('albumItems');
    fetchDataAlbum()
        .then(data=>{
            albumItems.innerHTML = '';
            data.forEach(album => {
                const albumItem = document.createElement('li');
                albumItem.className = 'list-group-item d-flex justify-content-between align-items-center';
                albumItem.textContent = album.name;

                const addButton = document.createElement('button');
                addButton.className = 'btn btn-primary';
                addButton.textContent = '+';
                addButton.addEventListener('click', function() {
                    addSongToAlbum(songId, album.id);
                });

                albumItem.appendChild(addButton);
                albumItems.appendChild(albumItem);
            });
        })
        .catch(error => {
            console.error('Lỗi trong quá trình lấy dữ liệu:', error);
        });

}

export function addSongToAlbum(songId, albumId){
    console.log("Add song " + songId + " to album " + albumId);
    const payload = {
        songId: songId,
        playlistId: albumId
    };

    fetch('http://localhost:8080/SoundSephere/Playlist/addSongToPlaylist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.result) {
                window.alert('Add song to album successfully');
                closeAddSongToAlbumPopup();
            } else {
                window.alert('Failed to add song to album because song already in album');
            }
        })
        .catch(error => {
            console.error('Error add song to album', error);
        });
}

export function closeAddSongToAlbumPopup() {
    const addSongToAlbumPopUp = document.getElementById('addSongToAlbumPopUp');
    addSongToAlbumPopUp.style.display = 'none';
}

export function createSongCard(song){
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

    const dropdownDiv = document.createElement('div');
    dropdownDiv.className = 'col-auto';
    const dropdown = document.createElement('div');
    dropdown.className = 'dropdown';
    const button = document.createElement('button');
    button.className = 'btn btn-primary dropdown-toggle dropdownMenuButton';
    button.type = 'button';
    button.setAttribute('data-toggle', 'dropdown');
    button.setAttribute('aria-haspopup', 'true');
    button.setAttribute('aria-expanded', 'false');
    button.textContent = '...';
    const dropdownMenuDiv = document.createElement('div');
    dropdownMenuDiv.className = 'dropdown-menu';
    dropdownMenuDiv.style.position = 'absolute';
    dropdownMenuDiv.style.right = '0';
    dropdownMenuDiv.style.transform = 'translateX(-100%)';

    button.addEventListener('click', function() {
        if (dropdownMenuDiv.style.display === 'block') {
            dropdownMenuDiv.style.display = 'none';
        } else {
            dropdownMenuDiv.style.display = 'block';
        }
    });

    const playSongA = document.createElement('a');
    playSongA.className = 'dropdown-item playSong';
    playSongA.href = '/SoundSephere/SongPlay/getSong?idSong=' + song.id;
    playSongA.textContent = 'Play';

    const addToPlaylistA = document.createElement('a');
    addToPlaylistA.className = 'dropdown-item addToAlbum';
    addToPlaylistA.href = '#';
    addToPlaylistA.textContent = 'Add to Album';
    addToPlaylistA.addEventListener('click', function() {
        openAddSongToAlbumPopup(song.id);
    });



    dropdownMenuDiv.appendChild(playSongA);
    dropdownMenuDiv.appendChild(addToPlaylistA);
    dropdown.appendChild(button);
    dropdown.appendChild(dropdownMenuDiv);
    dropdownDiv.appendChild(dropdown);

    contentDiv.appendChild(badgeDiv);
    contentDiv.appendChild(songInfoDiv);
    contentDiv.appendChild(dropdownDiv);

    li.appendChild(contentDiv);

    return li;
}

export function renderSongList(songList, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;

    container.innerHTML = '';

    songList.forEach(function(song) {
        const songCard = createSongCard(song);
        container.appendChild(songCard);
    });
}

export function fetchDataSong(){
    return  fetch('http://localhost:8080/SoundSephere/User/getListSongJson')
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