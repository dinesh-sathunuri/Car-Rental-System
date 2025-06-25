import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../common/Navbar";
import "../../styles/CustomerDetails.css";
import { fetchCarById, fetchCustomerById} from "../../services/authService";
import PaymentSection from "../common/PaymentSection"; 

function CarDetail() {
  const { carId } = useParams();
  const [car, setCar] = useState(null);
  const [owner, setOwner] = useState(null);
  const [loading, setLoading] = useState(true);

  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const loggedInEmail = localStorage.getItem("userEmail");

  useEffect(() => {
    async function loadCarAndOwner() {
      if (!carId) return;
      try {
        const carData = await fetchCarById(carId);
        setCar(carData);

        if (carData.customerId) {
          const ownerData = await fetchCustomerById(carData.customerId);
          setOwner(ownerData);
        }
      } catch (error) {
        console.error("Failed to load car or owner data", error);
      } finally {
        setLoading(false);
      }
    }

    loadCarAndOwner();
  }, [carId]);

  if (loading) return <div>Loading...</div>;
  if (!car) return <div>Car not found.</div>;

  const fullName = owner ? `${owner.firstName} ${owner.lastName}` : "";
  const pickupAddress = owner?.addresses?.[0];

  const isOwner = owner?.email === loggedInEmail;

  return (
    <div className="car-detail-container">
      <Navbar />

      <div className="car-detail-card">
        <h2>{car.make} {car.model} ({car.year})</h2>
        <p><strong>License Plate:</strong> {car.licensePlate}</p>
        <p><strong>Description:</strong> {car.description}</p>
        <p className="price"><strong>Rent per Day:</strong> ${car.rentPerDay}</p>

        {owner && pickupAddress ? (
          <div className="owner-info">
            <h3>Owner Information</h3>
            <p><strong>Name:</strong> {fullName}</p>
            <p>
              <strong>Pick-up Address:</strong><br />
              {pickupAddress.street}, {pickupAddress.city}, {pickupAddress.state} {pickupAddress.zipCode}, {pickupAddress.country}
            </p>
          </div>
        ) : (
          <p>Owner information not available</p>
        )}

        <p><strong>Please Select the dates</strong></p>
        <div className="date-selection">
          <label>Start Date: <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} /></label>
          <label>End Date: <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} /></label>
        </div>

        {isOwner ? (
          <p style={{ color: "red", fontWeight: "bold", marginTop: 20 }}>
            🚫 You cannot rent your own car.
          </p>
        ) : (
          startDate && endDate && (
            <PaymentSection
              car={car}
              startDate={startDate}
              endDate={endDate}
            />
          )
        )}
      </div>
    </div>
  );
}

export default CarDetail;
