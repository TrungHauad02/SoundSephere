import {loadPlaylist} from "./playlist_main.js";
import {goToPlaylistDetail} from "./playlistDetail.js";

export function fetchDataPlaylist(){
    return fetch('http://localhost:8080/SoundSephere/Playlist/getListPlaylistJson')
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

export function renderPlaylistList(playlistList, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;

    container.innerHTML = '';

    playlistList.forEach(function(playlist) {
        const playlistCard = createPlaylistCard(playlist);
        container.appendChild(playlistCard);
    });
}

export function createPlaylistCard(playlist){
    const li = document.createElement('li');
    li.className = 'list-group-item';
    li.style.background = '#00000000';

    const cardDiv = document.createElement('div');
    cardDiv.className = 'card';
    cardDiv.style.backgroundColor = '#202020';

    const cardBodyDiv = document.createElement('div');
    cardBodyDiv.className = 'card-body';
    cardBodyDiv.style.color = 'white';

    const rowDiv = document.createElement('div');
    rowDiv.className = 'row align-items-center';

    const badgeColDiv = document.createElement('div');
    badgeColDiv.className = 'col-auto';

    const badgeDiv = document.createElement('div');
    badgeDiv.className = 'badge badge-primary rounded-circle';
    badgeDiv.style.backgroundColor = '#3E862C';
    badgeDiv.style.height = '50px';
    badgeDiv.style.width = '50px';
    badgeColDiv.appendChild(badgeDiv);

    const infoColDiv = document.createElement('div');
    infoColDiv.className = 'col';
    const titleH6 = document.createElement('h6');
    titleH6.className = 'card-title';
    titleH6.textContent = playlist.name;
    titleH6.style.color = '#ffffff';
    const descriptionP = document.createElement('p');
    descriptionP.className = 'card-text';
    descriptionP.textContent = `Number of songs: ${playlist.number_of_songs}`;
    descriptionP.style.color = '#ffffff';
    infoColDiv.appendChild(titleH6);
    infoColDiv.appendChild(descriptionP);

    const dropdownColDiv = document.createElement('div');
    dropdownColDiv.className = 'col-auto';
    const dropdownDiv = document.createElement('div');
    dropdownDiv.className = 'dropdown';
    const dropdownButton = document.createElement('button');
    dropdownButton.className = 'btn btn-primary dropdown-toggle dropdownMenuButton';
    dropdownButton.type = 'button';
    dropdownButton.setAttribute('data-toggle', 'dropdown');
    dropdownButton.setAttribute('aria-haspopup', 'true');
    dropdownButton.setAttribute('aria-expanded', 'false');
    dropdownButton.textContent = '...';
    const dropdownMenuDiv = document.createElement('div');
    dropdownMenuDiv.className = 'dropdown-menu';
    const deletePlaylistA = document.createElement('a');
    deletePlaylistA.className = 'dropdown-item playlist';
    deletePlaylistA.href = '#';
    deletePlaylistA.textContent = 'Delete playlist';

    deletePlaylistA.addEventListener('click', () => {
        const playlistId = playlist.id;
        deletePlaylist(playlistId);
    });

    const showDetailA = document.createElement('a');
    showDetailA.className = 'dropdown-item addToPlaylist';
    showDetailA.href = '#';
    showDetailA.textContent = 'Show detail';

    showDetailA.addEventListener('click', ()=>{
        goToPlaylistDetail(playlist.id);
    });

    dropdownMenuDiv.appendChild(deletePlaylistA);
    dropdownMenuDiv.appendChild(showDetailA);
    dropdownDiv.appendChild(dropdownButton);
    dropdownDiv.appendChild(dropdownMenuDiv);
    dropdownColDiv.appendChild(dropdownDiv);

    dropdownButton.addEventListener('click', function (){
        if (dropdownMenuDiv.style.display === 'block') {
            dropdownMenuDiv.style.display = 'none';
        } else {
            dropdownMenuDiv.style.display = 'block';
        }
    });

    const playButtonColDiv = document.createElement('div');
    playButtonColDiv.className = 'col-auto';
    const playButton = document.createElement('button');
    playButton.className = 'btn btn-primary';
    playButton.textContent = 'Play';
    playButtonColDiv.appendChild(playButton);

    rowDiv.appendChild(badgeColDiv);
    rowDiv.appendChild(infoColDiv);
    rowDiv.appendChild(dropdownColDiv);
    rowDiv.appendChild(playButtonColDiv);
    cardBodyDiv.appendChild(rowDiv);
    cardDiv.appendChild(cardBodyDiv);
    li.appendChild(cardDiv);

    return li;
}

export function openCreatePlaylistPopup(){
    const playlistPopup = document.getElementById('popupCreatePlaylist');
    playlistPopup.style.display = 'flex';
}

export function closeCreatePlaylistPopup(){
    const playlistPopup = document.getElementById('popupCreatePlaylist');
    playlistPopup.style.display = 'none';
}

export function createNewPlaylist() {
    const playlistNameInput = document.getElementById('playlistName');

    const playlistName = playlistNameInput.value;
    console.log(playlistName);

    const payload = {
        playlistName: playlistName
    };

    fetch('http://localhost:8080/SoundSephere/Playlist/addNewPlaylist', {
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
                window.alert('Playlist created successfully');
                closeCreatePlaylistPopup();
                loadPlaylist();
            } else {
                window.alert('Failed to create playlist');
            }
        })
        .catch(error => {
            console.error('Error creating playlist:', error);
        });
}

export function deletePlaylist(playlistId){
    const payload = {
        playlistId: playlistId
    };

    fetch('http://localhost:8080/SoundSephere/Playlist/deletePlaylist', {
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
                window.alert('Playlist delete successfully');
                loadPlaylist();
            } else {
                window.alert('Failed to delete playlist');
            }
        })
        .catch(error => {
            console.error('Error delete playlist:', error);
        });
}