import { initializeApp } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-analytics.js";
import {
    getStorage,
    ref,
    getDownloadURL,
} from "https://www.gstatic.com/firebasejs/10.11.0/firebase-storage.js";

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
const storage = getStorage(appMusic);
export async function getImage(imagePath) {
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