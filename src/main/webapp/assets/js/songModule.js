import {getImageFromFirebase} from './firebaseModule.js';

export function openAddSongPopup() {
    const addSongPopup = document.getElementById('addSongPopup');
    addSongPopup.style.display = 'flex';
}

export function addNewSong() {
    console.log('Add new song');
}

export function closeSongPopup() {
    const addSongPopup = document.getElementById('addSongPopup');
    addSongPopup.style.display = 'none';
}

export function openAddSongToPlaylistPopup(song) {
    const addSongToPlaylistPopUp = document.getElementById('addSongToPlaylistPopUp');
    addSongToPlaylistPopUp.style.display = 'flex';

    const btnClosePlaylistPopup = document.getElementById('btnClosePlaylistPopup');
    const btnClosePlaylistPopupTop = document.getElementById('btnClosePlaylistPopupTop');
    btnClosePlaylistPopup.addEventListener('click', function() {
        closeAddSongToPlaylistPopup();
    });
    btnClosePlaylistPopupTop.addEventListener('click', function() {
        closeAddSongToPlaylistPopup();
    });
}

export function closeAddSongToPlaylistPopup() {
    const addSongToPlaylistPopUp = document.getElementById('addSongToPlaylistPopUp');
    addSongToPlaylistPopUp.style.display = 'none';
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
    playSongA.href = '#';
    playSongA.textContent = 'Play';

    const addToPlaylistA = document.createElement('a');
    addToPlaylistA.className = 'dropdown-item addToPlaylist';
    addToPlaylistA.href = '#';
    addToPlaylistA.textContent = 'Add to Playlist';
    addToPlaylistA.addEventListener('click', function() {
        openAddSongToPlaylistPopup(song);
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