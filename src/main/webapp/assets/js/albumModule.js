
export function openCreateAlbumPopup() {
    const albumPopup = document.getElementById('albumPopup');
    albumPopup.style.display = 'flex';
}

export function createAlbum() {

}

export function closePopupCreateAlbum() {
    const albumPopup = document.getElementById('albumPopup');
    albumPopup.style.display = 'none';
}

export function fetchDataAlbum(){
    return  fetch('http://localhost:8080/SoundSephere/User/getListAlbumJson')
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

export function renderAlbumList(albumList, containerId) {
    const container = document.getElementById(containerId);
    if (!container) return;

    container.innerHTML = '';

    albumList.forEach(function(album) {
        const albumCard = createAlbumCard(album);
        container.appendChild(albumCard);
    });
}

export function createAlbumCard(album) {
    const li = document.createElement('li');
    li.className = 'pb-2';

    const cardDiv = document.createElement('div');
    cardDiv.className = 'card';

    const cardBodyDiv = document.createElement('div');
    cardBodyDiv.className = 'card-body';

    const rowDiv = document.createElement('div');
    rowDiv.className = 'row align-items-center';

    const badgeColDiv = document.createElement('div');
    badgeColDiv.className = 'col-auto';
    const badge = document.createElement('div');
    badge.className = 'badge badge-primary rounded-circle';
    badge.style.backgroundColor = '#3E862C';
    badge.style.height = '50px';
    badge.style.width = '50px';
    badgeColDiv.appendChild(badge);

    const infoColDiv = document.createElement('div');
    infoColDiv.className = 'col';
    const titleH6 = document.createElement('h6');
    titleH6.className = 'card-title';
    titleH6.textContent = album.name;
    const descriptionP = document.createElement('p');
    descriptionP.className = 'card-text';
    descriptionP.textContent = album.status;
    infoColDiv.appendChild(titleH6);
    infoColDiv.appendChild(descriptionP);

    const buttonColDiv = document.createElement('div');
    buttonColDiv.className = 'col-auto';
    const playButton = document.createElement('button');
    playButton.className = 'btn btn-primary';
    playButton.textContent = 'Play';
    buttonColDiv.appendChild(playButton);

    rowDiv.appendChild(badgeColDiv);
    rowDiv.appendChild(infoColDiv);
    rowDiv.appendChild(buttonColDiv);

    cardBodyDiv.appendChild(rowDiv);
    cardDiv.appendChild(cardBodyDiv);

    li.appendChild(cardDiv);

    return li;
}
