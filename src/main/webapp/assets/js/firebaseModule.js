import {initializeApp} from "https://www.gstatic.com/firebasejs/10.11.0/firebase-app.js";
import {getDownloadURL, getStorage, ref, uploadBytes } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-storage.js";


const firebaseConfig = {
    apiKey: "AIzaSyAujB-WRTuVQ3abGStWa7oz0RyxN7vmmBQ",
    authDomain: "image-mp3.firebaseapp.com",
    projectId: "image-mp3",
    storageBucket: "image-mp3.appspot.com",
    messagingSenderId: "218542874710",
    appId: "1:218542874710:web:8911549200e5b75fbd5bf7",
    measurementId: "G-R3V5FVGKCC"
};
let appInitialized = false;
export function initializeFirebase() {
    if (!appInitialized) {
        initializeApp(firebaseConfig);
        appInitialized = true;
    }
}

export async function getImageFromFirebase(imagePath) {
    try {
        initializeFirebase();
        const storage = getStorage();
        const imageRef = ref(storage, imagePath);
        return await getDownloadURL(imageRef);
    } catch (error) {
        console.error("Error fetching image from Firebase:", error);
        throw error;
    }
}

export async function uploadFileToFirebase(file, fileName, fileType) {
    try {
        initializeFirebase();
        const storage = getStorage();
        const fileRef = ref(storage, fileName);
        await uploadBytes(fileRef, file);
        return true;
    } catch (error) {
        console.error("Error uploading file to Firebase:", error);
        throw error;
    }
}