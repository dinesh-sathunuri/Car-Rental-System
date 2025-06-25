import Navbar from "../common/Navbar";
import "../../styles/Login.css";
import Car from '../../assets/login-car.png';
import { loginUser } from "../../services/authService";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
function Login() {
  const [formData,setFormatData]=useState({
    email:"",
    password:""
  });
  const navigate = useNavigate();
  const handleChange=(e)=>{
    setFormatData({...formData,
        [e.target.name]:e.target.value
    });
  }
  const handleSubmit=async(e)=>{
    e.preventDefault();
    try{ 
      await loginUser(formData);
      alert("login Successful!");
      localStorage.setItem("userEmail",formData.email);
      setTimeout(()=>{
        navigate("/")
    },4000);

    }catch(error){
      alert(error.response?.data?.message || "Login failed");

    }

  }
  return (
    <div>
      <Navbar showRegisterButton={true} />
      <div className="login-page">
        <div className="login-wrapper">
          <div className="login-container">
            <h2 className="login-heading">Login</h2>
            <div className="login-details">
              <label>Email ID</label>
              <input name="email" value={formData.email} onChange={(e)=>handleChange(e)} placeholder="Enter your email" />
              <label>Password</label>
              <input name="password" value={formData.password} onChange={(e)=>handleChange(e)} type="password" placeholder="Enter your password" />
              <button onClick={handleSubmit} className="login-button">Login</button>
            </div>
          </div>
          <div className="login-image-container">
            <img src={Car} alt="Car" className="login-image" />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
