import { useState } from "react";
import LoginForm from "./LoginForm.jsx";
import SignupForm from "./SignupForm.jsx";
import Header from "./Header.jsx";
import '../styles/Authentication.css';

function AuthPage() {
    const [isLogin, setIsLogin] = useState(true);

    return (
        <>
            <Header />
            <div className="auth-container">
                <div className="auth-wrapper">
                    {isLogin ? (
                        <LoginForm onToggle={() => setIsLogin(false)} />
                    ) : (
                        <SignupForm onToggle={() => setIsLogin(true)} />
                    )}
                </div>
            </div>
        </>
    );
}

export default AuthPage;