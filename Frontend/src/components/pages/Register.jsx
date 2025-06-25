import Navbar from "../common/Navbar";
import { useState } from "react";
import "../../styles/Register.css";
import { registerUser } from "../../services/authService";
import { useNavigate } from "react-router-dom";
function Register() {
  const navigate = useNavigate();
  const [formData,setFormData]=useState({
    firstName:"",
    lastName:"",
    email:"",
    password:"",
    phoneNumber:"",
    licenseNumber:""
  });
  const handleChange=(e)=>{
    setFormData({...formData,
      [e.target.name]:e.target.value});
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await registerUser(formData);
      alert("Register Successful!");
      setTimeout(() => {
          navigate("/login")
      }, 2000)
    } catch (error) {
      alert(error.response?.data?.message || "Register Failed");
    }
    
  }
  return (
    <div className="register-page">
      <Navbar showLoginButton={true} />
      <div className="Register-container">
        <div className="Register-box">
          <h2 className="Register-Heading">Register</h2>
          <div className="Register-details">
            <label>First Name</label>
            <input name="firstName" value={formData.firstName} onChange={(e)=>handleChange(e)} placeholder="Enter your first name" />
            <label>Last Name</label>
            <input name="lastName" value={formData.lastName} onChange={(e)=>handleChange(e)} placeholder="Enter your last name" />
            <label>Email</label>
            <input name="email" value={formData.email} onChange={(e)=>handleChange(e)} placeholder="Enter your email" />
            <label>Password</label>
            <input name="password" value={formData.password} onChange={(e)=>handleChange(e)} type="password" placeholder="Enter your password" />
            <label>Phone Number</label>
            <input name="phoneNumber" value={formData.phoneNumber} onChange={(e)=>handleChange(e)}  placeholder="Enter your phone number" />
            <label>License Number</label>
            <input name="licenseNumber" value={formData.licenseNumber} onChange={(e)=>handleChange(e)} placeholder="Enter your license number" />
            <button onClick={handleSubmit} className="signup-button">signup</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register;
