import { useContext, useState } from 'react'
import { useNavigate } from "react-router-dom";
import { Eye, EyeOff } from 'lucide-react'
import { EmailContext } from "../EmailContext.js";
import '../styles/Authentication.css';

function LoginForm({ onToggle }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loginMessage, setLoginMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate()
    const { setEmailContext } = useContext(EmailContext);

    async function handleLogin(e) {
        e.preventDefault()
        setIsLoading(true);
        try {
            const res = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-type": "application/json"
                },
                body: JSON.stringify({ email, password }),
            })
            const message = await res.text();
            setLoginMessage(message);
            if (message === "Login Successful!") {
                localStorage.setItem("email", email);
                setEmailContext(email)
                navigate("/dashboard")
            }
        }
        catch (err) {
            console.log(err)
            setLoginMessage("Connection error. Please try again.");
        }
        finally {
            setIsLoading(false);
        }
    }

    return (
        <form onSubmit={handleLogin} className="auth-form">
            <h1>LOGIN</h1>
            <label>Email</label>
            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="your@email.com"
                required
            />
            <label>Password</label>
            <div className="password-input-wrapper">
                <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="••••••••"
                    required
                />
                <button
                    type="button"
                    className="password-toggle"
                    onClick={() => setShowPassword(!showPassword)}
                    aria-label="Toggle password visibility"
                >
                    {showPassword ? <Eye size={20} /> : <EyeOff size={20} />}
                </button>
            </div>
            <button type="submit" disabled={isLoading}>
                {isLoading ? "Logging In..." : "Login"}
            </button>
            <p className="status-msg">{loginMessage}</p>
            <div className="toggle-link">
                Don't have an account? <a onClick={onToggle}>Sign up</a>
            </div>
        </form>
    )
}

export default LoginForm