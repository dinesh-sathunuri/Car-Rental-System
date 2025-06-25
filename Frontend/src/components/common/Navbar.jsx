import { useEffect, useState, useRef } from "react";
import "../../styles/Navbar.css"
import { useNavigate } from "react-router-dom";

function Navbar(props) {
  const navigate = useNavigate();
  const [userEmail, setUserEmail] = useState(null);
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const email = localStorage.getItem("userEmail");
    if (email) {
      setUserEmail(email);
    }
  }, []);

  // Close dropdown on outside click
  useEffect(() => {
    function handleClickOutside(event) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    setUserEmail("");
    navigate("/");
  };

  return (
    <div className="navbar-container">
      <div className="navbar-left">
        <h2 className="navbar-heading">WhyBuy</h2>
      </div>

      <div className="navbar-right">
        <div className="navbar-right-pages">
          <a className="navbar-page-link" href="/">Home</a>
          <a className="navbar-page-link" href="/home">Browse Cars</a>
        </div>

        {userEmail ? (
          <div className="navbar-right-buttons" ref={dropdownRef} style={{ position: "relative" }}>
            {/* User email triggers dropdown */}
            <button
              onClick={() => setShowDropdown(!showDropdown)}
              className="navbar-user-email"
            >
              {userEmail} ▼
            </button>

            {showDropdown && (
              <div className="navbar-dropdown-menu">
                <a
                  className="navbar-dropdown-item"
                  href="/profile"
                  onClick={() => setShowDropdown(false)}
                >
                  Profile Details
                </a>
                <a
                  className="navbar-dropdown-item"
                  href="/my-cars"
                  onClick={() => setShowDropdown(false)}
                >
                  Cars to Rent
                </a>
                <a
                  className="navbar-dropdown-item"
                  href="/rental-status"
                  onClick={() => setShowDropdown(false)}
                >
                  Rental Status
                </a>
                <hr />
                <button
                  onClick={handleLogout}
                >
                  Logout
                </button>
              </div>
            )}
          </div>
        ) : (
          <div className="navbar-right-buttons">
            {props.showLoginButton && (
              <button onClick={() => navigate("/login")} className="navbar-login">
                <span>Login</span>
              </button>
            )}
            {props.showRegisterButton && (
              <button onClick={() => navigate("/register")} className="navbar-signup">
                <span>Sign Up</span>
              </button>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default Navbar;
