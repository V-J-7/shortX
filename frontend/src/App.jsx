import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css'
import AuthPage from "./components/AuthPage.jsx";
import Dashboard from "./components/Dashboard.jsx";
import {EmailContext} from "./EmailContext.js";
import {useState} from "react";
import ShortenURLs from "./components/ShortenURLs.jsx";

function App() {
    const [email,setEmail]=useState(localStorage.getItem("email") || "");
    return(
        <EmailContext.Provider value={{email,setEmailContext:setEmail}}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<AuthPage/>}/>
                    <Route path="/dashboard" element={<Dashboard/>}/>
                    <Route path="/shorten" element={<ShortenURLs/>}/>
                </Routes>
            </BrowserRouter>
        </EmailContext.Provider>
    )
}

export default App
