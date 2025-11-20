import {useNavigate} from "react-router-dom";
import {useContext} from "react";
import {EmailContext} from "../EmailContext.js";

function User() {
    const navigate = useNavigate();
    const { email } = useContext(EmailContext);
    const username = email.split('@')[0];
    return (
        <div className="user-info">
            <div style={{ marginBottom: '1rem' , color: "var(--primary)"}}>Welcome, <span className="username">{username}</span></div>
            <button className="secondary" onClick={() => {
                localStorage.removeItem("email")
                navigate("/")
            }}>Logout</button>
        </div>
    );
}
export default User;