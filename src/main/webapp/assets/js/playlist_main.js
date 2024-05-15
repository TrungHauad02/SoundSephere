import {
    fetchDataPlaylist,
    renderPlaylistList,
    openCreatePlaylistPopup,
    closeCreatePlaylistPopup,
    createNewPlaylist
} from "./playlistModule.js";

document.addEventListener('DOMContentLoaded', function() {
    const btnOpenPopupCreatePlaylist = document.getElementById('btnOpenPopupCreatePlaylist');
    btnOpenPopupCreatePlaylist.addEventListener('click', openCreatePlaylistPopup);

    const btnClosePopupCreatePlaylist = document.getElementById('btnClosePopupCreatePlaylist');
    btnClosePopupCreatePlaylist.addEventListener('click', closeCreatePlaylistPopup);

    const btnCreatePlaylist = document.getElementById('btnCreatePlaylist');
    btnCreatePlaylist.addEventListener('click', createNewPlaylist);

    loadPlaylist();
});

export function loadPlaylist(){
    fetchDataPlaylist()
        .then(data=>{
            renderPlaylistList(data,'playlistContainer');
        })
        .catch(error => {
            console.error('Lỗi trong quá trình lấy dữ liệu:', error);
        });
}