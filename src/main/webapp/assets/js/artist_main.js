document.addEventListener('DOMContentLoaded', function() {

    // Handle Create Album Popup
    const openCreateAlbumPopupButton = document.getElementById('openCreateAlbumPopup');
    const albumPopup = document.getElementById('albumPopup');

    openCreateAlbumPopupButton.addEventListener('click', function() {
        albumPopup.style.display = 'flex';
    });

    const btnCreateAlbum = document.getElementById('btnCreateAlbum');
    btnCreateAlbum.addEventListener('click', function (){
        createAlbum();
    });

    const btnCloseCreateAlbumPopup = document.getElementById('btnCloseCreateAlbumPopup');
    btnCloseCreateAlbumPopup.addEventListener('click', function() {
        closePopupCreateAlbum();
    });

    function closePopupCreateAlbum() {
        albumPopup.style.display = 'none';
    }

    function createAlbum(){
        console.log("Add new album");
    }

    // Handle Add Song Popup
    const btnOpenSongPopup = document.getElementById('btnOpenSongPopup');
    const addSongPopup = document.getElementById('addSongPopup');

    btnOpenSongPopup.addEventListener('click', function() {
        addSongPopup.style.display = 'flex';
    });

    const closeSongPopupButton = document.getElementById('btnCloseSongPopup');
    closeSongPopupButton.addEventListener('click', function() {
        closeSongPopup();
    });

    const btnAddSong = document.getElementById('btnAddSong');
    btnAddSong.addEventListener('click', function (){
        addNewSong();
    });

    function closeSongPopup() {
        addSongPopup.style.display = 'none';
    }

    function addNewSong(){
        console.log('Add new song');
    }

    const imageInput = document.getElementById('imageInput');
    const imagePreview = document.getElementById('imagePreview');

    imageInput.addEventListener('change', function() {
        const file = this.files[0];

        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imagePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // Handle Drop down menu
    const playlists = [
        { name: "Playlist 1" },
        { name: "Playlist 2" },
        { name: "Playlist 3" }
    ];

    const dropdownButtons = document.querySelectorAll('.dropdownMenuButton');
    dropdownButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault();

            const allDropdownMenus = document.querySelectorAll('.dropdown-menu');
            allDropdownMenus.forEach(function(menu) {
                menu.classList.remove('show');
            });

            const dropdownMenu = button.nextElementSibling;
            if (dropdownMenu && dropdownMenu.classList.contains('dropdown-menu')) {
                if (!dropdownMenu.classList.contains('show')) {
                    dropdownMenu.classList.add('show');
                } else {
                    dropdownMenu.classList.remove('show');
                }
            }
        });
    });

    const dropdownMenus = document.querySelectorAll('.dropdown-menu');

    dropdownMenus.forEach((dropdownMenu) => {
        dropdownMenu.addEventListener('click', function(event) {
            event.preventDefault();
            const target = event.target;

            if (target.classList.contains('addToPlaylist')) {
                openAddSongToPlaylistPopup();
            } else if (target.classList.contains('playSong')) {
                console.log('Play the song');
            }
        });
    });

    const addSongToPlaylistPopUp = document.getElementById('addSongToPlaylistPopUp');
    const playlistItemsContainer = document.getElementById('playlistItems');

    function openAddSongToPlaylistPopup() {
        addSongToPlaylistPopUp.style.display = 'flex';
        playlistItemsContainer.innerHTML = '';

        playlists.forEach(function(playlist) {
            const playlistItem = document.createElement('li');
            playlistItem.className = 'list-group-item d-flex justify-content-between align-items-center';

            const playlistName = document.createTextNode(playlist.name);
            playlistItem.appendChild(playlistName);

            const addButton = document.createElement('button');
            addButton.className = 'btn btn-primary btn-sm';
            addButton.textContent = '+';
            addButton.addEventListener('click', function() {
                console.log('Add song to playlist:', playlist.name);
                closeAddSongToPlaylistPopup();
            });

            playlistItem.appendChild(addButton);
            playlistItem.style.marginBottom = '5px';
            playlistItemsContainer.appendChild(playlistItem);
        });
    }

    const btnClosePlaylistPopup = document.getElementById('btnClosePlaylistPopup');
    btnClosePlaylistPopup.addEventListener('click', function() {
        closeAddSongToPlaylistPopup();
    });

    function closeAddSongToPlaylistPopup() {
        addSongToPlaylistPopUp.style.display = 'none';
    }
});
