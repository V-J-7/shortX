import { useState } from 'react'
import { Eye, EyeOff } from 'lucide-react'
import PasswordValidator from './PasswordValidator.jsx'
import '../styles/Authentication.css'
import {link} from "../BackendLink.jsx"

function validEmail(email) {
    const regex = /^[a-zA-Z0-9_%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
}


const SignupForm = ({ onToggle }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [emailMessage, setEmailMessage] = useState('');
    const [signupMessage, setSignupMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const handleSignup = async (e) => {
        e.preventDefault()
        setIsLoading(true);

        if (!validEmail(email)) {
            setEmailMessage("Invalid Email");
            setIsLoading(false);
            return;
        }
        else {
            setEmailMessage('');
        }

        try {
            const res = await fetch(`${link}/auth/signup`, {
                method: "POST",
                headers: {
                    "Content-type": "application/json"
                },
                body: JSON.stringify({ email, password })
            });
            const message = await res.text()
            setSignupMessage(message);
            setPassword('')
            setEmail('')
        }
        catch (err) {
            console.log(err)
            setSignupMessage("Connection error. Please try again.");
        }
        finally {
            setIsLoading(false);
        }
    }

    return (
        <form onSubmit={handleSignup} className="auth-form">
            <h1>Sign Up</h1>
            <label>Email</label>
            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="your@email.com"
                required
            />
            {emailMessage && <p className="error">{emailMessage}</p>}
            
            <label>Password</label>
            <div className="password-input-wrapper">
                <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="••••••••"
                    autoComplete="new-password"
                    required
                />
                {password && (
                    <button
                        type="button"
                        className="password-toggle"
                        onClick={() => setShowPassword(!showPassword)}
                        aria-label="Toggle password visibility"
                    >
                        {showPassword ? <Eye size={20} /> : <EyeOff size={20} />}
                    </button>
                )}
            </div>
            
            {password && <PasswordValidator password={password} />}
            
            <button type="submit" disabled={isLoading}>
                {isLoading ? "Creating Account..." : "Sign Up"}
            </button>
            <p className="status-msg">{signupMessage}</p>

            <div className="toggle-link">
                Already have an account? <a onClick={onToggle}>Log in</a>
            </div>
        </form>
    )
}

export default SignupForm