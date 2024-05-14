import { initializeApp } from 'firebase/app';
import { getStorage, ref } from 'firebase/storage';


const firebaseConfig = {
    apiKey: "AIzaSyCfY68Y0ovhi1TN-GDNgjSg2fTKKX050TA",
    authDomain: "soundsphere-16b0b.firebaseapp.com",
    projectId: "soundsphere-16b0b",
    storageBucket: "soundsphere-16b0b.appspot.com",
    messagingSenderId: "10122020658",
    appId: "1:10122020658:web:da439c45bc0312f12cf92b"
};

export function initializeFirebase() {
    const app = initializeApp(firebaseConfig);
}

