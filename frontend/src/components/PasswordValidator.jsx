import '../styles/PasswordValidator.css';

function PasswordValidator({ password }) {
    const checks = {
        length: password.length >= 8,
        lowercase: /[a-z]/.test(password),
        uppercase: /[A-Z]/.test(password),
        digit: /\d/.test(password),
    };

    const allValid = Object.values(checks).every(v => v === true);

    return (
        <div className="password-validator">
            <div className={`validator-item ${checks.length ? 'valid' : 'invalid'}`}>
                <span className="check-icon">{checks.length ? '✓' : '✗'}</span>
                <span>At least 8 characters</span>
            </div>
            <div className={`validator-item ${checks.lowercase ? 'valid' : 'invalid'}`}>
                <span className="check-icon">{checks.lowercase ? '✓' : '✗'}</span>
                <span>One lowercase letter (a-z)</span>
            </div>
            <div className={`validator-item ${checks.uppercase ? 'valid' : 'invalid'}`}>
                <span className="check-icon">{checks.uppercase ? '✓' : '✗'}</span>
                <span>One uppercase letter (A-Z)</span>
            </div>
            <div className={`validator-item ${checks.digit ? 'valid' : 'invalid'}`}>
                <span className="check-icon">{checks.digit ? '✓' : '✗'}</span>
                <span>One number (0-9)</span>
            </div>
            {password && (
                <div className={`strength-bar ${allValid ? 'strong' : 'weak'}`}>
                    <div className={`strength-fill ${allValid ? 'strong' : 'weak'}`}></div>
                </div>
            )}
        </div>
    );
}

export default PasswordValidator;
