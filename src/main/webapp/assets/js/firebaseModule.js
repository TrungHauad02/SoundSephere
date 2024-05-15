import {initializeApp} from "https://www.gstatic.com/firebasejs/10.11.0/firebase-app.js";
import {getDownloadURL, getStorage, ref, uploadBytes } from "https://www.gstatic.com/firebasejs/10.11.0/firebase-storage.js";


const firebaseConfig = {
    apiKey: "AIzaSyCfY68Y0ovhi1TN-GDNgjSg2fTKKX050TA",
    authDomain: "soundsphere-16b0b.firebaseapp.com",
    projectId: "soundsphere-16b0b",
    storageBucket: "soundsphere-16b0b.appspot.com",
    messagingSenderId: "10122020658",
    appId: "1:10122020658:web:da439c45bc0312f12cf92b"
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