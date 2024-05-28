import { initializeFirebase } from './firebaseModule.js';
import {
    openCreateAlbumPopup,
    createAlbum,
    closePopupCreateAlbum,
    fetchDataAlbum,
    renderAlbumList
} from './albumModule.js';
import {fetchDataSong, renderSongList, openAddSongPopup, closeSongPopup, addNewSong} from './songModule.js';

document.addEventListener('DOMContentLoaded', function() {
    initializeFirebase();

    // Handle Create Album Popup
    const openCreateAlbumPopupButton = document.getElementById('openCreateAlbumPopup');
    openCreateAlbumPopupButton.addEventListener('click', openCreateAlbumPopup);

    const btnCreateAlbum = document.getElementById('btnCreateAlbum');
    btnCreateAlbum.addEventListener('click', createAlbum);

    const btnCloseCreateAlbumPopup = document.getElementById('btnCloseCreateAlbumPopup');
    btnCloseCreateAlbumPopup.addEventListener('click', closePopupCreateAlbum);

    // Handle Add Song Popup
    const btnOpenSongPopup = document.getElementById('btnOpenSongPopup');
    btnOpenSongPopup.addEventListener('click', openAddSongPopup);

    const closeSongPopupButton = document.getElementById('btnCloseSongPopup');
    closeSongPopupButton.addEventListener('click', closeSongPopup);

    const btnAddSong = document.getElementById('btnAddSong');
    btnAddSong.addEventListener('click', addNewSong);

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

    loadSong();
    loadAlbum();
});

export function loadAlbum(){
    fetchDataAlbum()
        .then(data=>{
            renderAlbumList(data,'albumContainer');
        })
        .catch(error => {
            console.error('Lỗi trong quá trình lấy dữ liệu:', error);
        });
}

export function loadSong(){
    fetchDataSong()
        .then(data => {
            renderSongList(data, 'songListContainer');
        })
        .catch(error => {
            console.error('Lỗi trong quá trình lấy dữ liệu:', error);
        });
}