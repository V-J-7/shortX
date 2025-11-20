import { useContext, useState } from "react";
import { EmailContext } from "../EmailContext.js";
import {link} from "../BackendLink.jsx"

function ShortenURLs({ onShorten }) {
    const { email } = useContext(EmailContext);
    const [original, setOriginal] = useState('');
    const [shortURL, setShortURL] = useState('');
    const [urlName, setUrlName] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    async function sendURL(e) {
        e.preventDefault();
        setIsLoading(true);
        try {
            const res = await fetch(`${link}/shorten`, {
                method: "POST",
                headers: {
                    "Content-type": "application/json"
                },
                body: JSON.stringify({ email, original, urlName })
            });
            setShortURL(await res.text());
            if (onShorten) {
                onShorten();
            }
            setOriginal("");
            setUrlName('')
        }
        finally {
            setIsLoading(false);
        }
    }
    return (
        <form onSubmit={sendURL} className="shorten-form">
            <div className="form-group">
                <label htmlFor="original-url">Original URL</label>
                <input
                    id="original-url"
                    className="input-field"
                    type="url"
                    placeholder="https://example.com/very/long/url"
                    value={original}
                    onChange={(e) => setOriginal(e.target.value)}
                    required
                />
            </div>

            <div className="form-group">
                <label htmlFor="url-name">URL Name (Label)</label>
                <input
                    id="url-name"
                    className="input-field"
                    type="text"
                    placeholder="My awesome link"
                    value={urlName}
                    onChange={(e) => setUrlName(e.target.value)}
                />
            </div>

            {shortURL && shortURL !== "Invalid URL" && (
                <div className="form-group">
                    <label>Your Short URL</label>
                    <div style={{
                        background: 'rgba(0, 217, 255, 0.1)',
                        border: '1px solid rgba(0, 217, 255, 0.5)',
                        padding: '1rem',
                        borderRadius: '4px',
                        marginTop: '0.5rem'
                    }}>
                        <a href={`${link}/${shortURL}`} target="_blank" rel="noopener noreferrer">{link}/{shortURL}</a>
                    </div>
                </div>
            )}

            {shortURL === "Invalid URL" && (
                <p className="invalid-url">❌ Invalid URL</p>
            )}

            <button type="submit" className="shorten-btn" disabled={isLoading}>
                {isLoading ? "SHORTENING..." : "SHORTEN URL"}
            </button>
        </form>
    );
}

export default ShortenURLs;
