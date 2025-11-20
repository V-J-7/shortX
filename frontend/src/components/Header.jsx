import '../styles/Header.css';
import {useNavigate} from "react-router-dom";
function Header() {
    const navigate = useNavigate();
    return (
        <header className="header" onClick={() => navigate("/")}>
            <div className="header-content">
                <div className="header-side-text">
                    Fast & Secure
                </div>
                <div className="logo-container">
                    <div className="logo">
                        <span className="logo-text">short</span>
                        <span className="logo-x">X</span>
                    </div>
                </div>
                <div className="header-side-text">
                    Simple & Smart
                </div>
            </div>
        </header>
    );
}

export default Header;
