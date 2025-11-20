import { useContext, useEffect, useState } from "react";
import { EmailContext } from '../EmailContext.js'
import '../styles/Dashboard.css'
import Header from "./Header.jsx";
import ShortenURLs from "./ShortenURLs.jsx";
import QRCode from 'react-qr-code'
import User from "./User.jsx"

function Dashboard() {
    const [dashboardMessage, setDashboardMessage] = useState('');
    const [searchTerm, setSearchTerm] = useState('')
    const [shorteners, setShorteners] = useState([]);
    const [originalURL, setOriginalURL] = useState('');
    const [showQR, setShowQR] = useState(false);
    const { email } = useContext(EmailContext)
    const fetchData = async () => {
        const res = await fetch("http://localhost:8080/dashboard", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify({email})
        })
        if (res.status === 204) {
            setShorteners([]);
            setDashboardMessage("No URLs created.");
        } else {
            setDashboardMessage('');
            setShorteners(await res.json());
        }
    }
    const removeURL=async(shortURL,email)=>{
        const res=await fetch("http://localhost:8080/delete",{
            method:"POST",
            headers:{
                "Content-type":"application/json",
            },
            body:JSON.stringify({email,shortURL})
        })
        await fetchData();
    }
    useEffect(() => {
        fetchData()
    }, [email])
    const filteredShorteners = shorteners.filter(s => s.originalURL.toLowerCase().includes(searchTerm.toLowerCase()) || s.urlName.toLowerCase().includes(searchTerm.toLowerCase()));
    const host = "http://localhost:8080"
    return (
        <>
            <Header />
            <div className="dashboard-container">
                <User />
                {showQR && (
                    <div className="qr-overlay" onClick={() => setShowQR(false)}>
                        <div className="qr-popup">
                            <QRCode value={originalURL} size={256} />
                            <h1 className="qr-text" style = {{"color": "var(--primary)"}}>Click to close</h1>
                        </div>
                    </div>
                )}

                <h1>CREATE SHORT URL</h1>
                <ShortenURLs onShorten={fetchData} />

                <h1>YOUR SHORTENED URLS</h1>
                {shorteners.length > 0 ? (
                    <>
                        <label>Search by Name or URL:</label>
                        <input type="search" placeholder="Search URLs..." value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)} className="input-field" />
                        <table className="url-table">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Original URL</th>
                                    <th>Short URL</th>
                                    <th>QR Code</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredShorteners.map((s, index) => (
                                    <tr key={index}>
                                        <td>{s.urlName}</td>
                                        <td><a href={s.originalURL} target="_blank" rel="noopener noreferrer">{s.originalURL.length > 40 ? s.originalURL.slice(0, 40) + "..." : s.originalURL}</a></td>
                                        <td><a href={s.originalURL} target="_blank" rel="noopener noreferrer">{host}/{s.shortURL}</a></td>
                                        <td>
                                            <button onClick={() => {
                                                setOriginalURL(s.originalURL);
                                                setShowQR(true)
                                            }}>QR Code</button>
                                        </td>
                                        <td><button className="delete-btn" onClick={() => removeURL(s.shortURL, email)}>Delete</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </>
                ) : <p>{dashboardMessage}</p>}
            </div>
        </>
    )
}
export default Dashboard