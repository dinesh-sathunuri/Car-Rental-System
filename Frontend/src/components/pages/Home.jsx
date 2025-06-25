import Navbar from "../common/Navbar";
import "../../styles/Home.css";
import luxuryImg from "../../assets/home-luxury.png"
import sportsImg from "../../assets/home-top.png";
import luxurySuv from "../../assets/home-luxury-suv.png";
import Footer from '../common/Footer'
import { useNavigate } from "react-router-dom";
function Home(){
  const navigate=useNavigate();
  return(
    <div>
        <Navbar showLoginButton={true} showRegisterButton={true}/>
        <div className="top-screen">
        <div className="top-screen-text">
        <h1>Experience the thrill of driving your dream car</h1>
        <h2>Rent luxury and exotic cars for any occasion. Choose from a wide selection of premium vehicles and enjoy an unforgettable driving experience.</h2>
        </div>
        <button onClick={()=>navigate("/home")} className="top-screen-button"><span>Browse Cars</span></button>
        </div>
{/*
<div className="search-container">
  <FontAwesomeIcon icon={faSearch} className="search-icon" />
  <textarea placeholder="Search for pickup location, date, and car type"></textarea>
</div>
*/}
    <h2 className="card-heading">Featured Vehicles</h2>
    <div className="card-container">
    <div className="card-single">
      <img src={sportsImg} alt="Sports Car" />
      <h2>Sports Car</h2>
      <p>Experience the thrill of driving a high-performance sports car.</p>
    </div>
    <div className="card-single">
      <img src={luxuryImg} alt="Luxury Sedan" />
      <h2>Luxury Sedan</h2>
      <p>Travel in style and comfort with our premium sedan rentals.</p>
    </div>
    <div className="card-single">
      <img src={luxurySuv} alt="Luxury SUV" />
      <h2>card-single</h2>
      <p>Explore any terrain with our versatile and luxurious SUV rentals.</p>
    </div>
    </div>
    <Footer/>
    </div>
    
  );

}
export default Home;