import React, { useEffect, useState } from "react";
import Navbar from "../common/Navbar";
import { fetchRentalsByCustomerId, fetchCarById, fetchCustomerByEmail } from "../../services/authService";
import "../../styles/RentalStatusPage.css";

function RentalStatusPage() {
  const [rentals, setRentals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [customerId, setCustomerId] = useState(null);

  useEffect(() => {
    async function loadRentals() {
      try {
        const email = localStorage.getItem("userEmail");
        if (!email) throw new Error("User not logged in");

        const customer = await fetchCustomerByEmail(email);
        setCustomerId(customer.id);

        const data = await fetchRentalsByCustomerId(customer.id);
        if (!Array.isArray(data)) {
          console.warn("API returned non-array data:", data);
          setRentals([]);
          setError("Unexpected data format from server");
          return;
        }

        // Add car info to each rental
        const rentalsWithCarInfo = await Promise.all(
          data.map(async (rental) => {
            let carInfo = "";
            try {
              const car = await fetchCarById(rental.rentalCarId);
              carInfo = `${car.make} ${car.model} (${car.year})`;
            } catch (err) {
              console.warn("Car fetch failed", err);
            }
            return { ...rental, carInfo };
          })
        );

        setRentals(rentalsWithCarInfo);
      } catch (err) {
        console.error(err);
        setError(err.message || "Failed to load rentals.");
      } finally {
        setLoading(false);
      }
    }

    loadRentals();
  }, []);

  return (
    <div>
      <Navbar />
      <div className="rental-status-container">
        <h2>Your Rental Requests</h2>
        {loading ? (
          <p>Loading...</p>
        ) : error ? (
          <p className="error">{error}</p>
        ) : rentals.length === 0 ? (
          <p>No rental requests made yet.</p>
        ) : (
          <ul className="rental-status-list">
            {rentals.map((rental) => (
              <li key={rental.id} className="rental-status-item">
                <p><strong>Car:</strong> {rental.carInfo}</p>
                <p><strong>From:</strong> {new Date(rental.startDate).toLocaleDateString()}</p>
                <p><strong>To:</strong> {new Date(rental.endDate).toLocaleDateString()}</p>
                <p><strong>Status:</strong> {rental.status}</p>
                <p><strong>Total:</strong> ${rental.totalAmount}</p>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}

export default RentalStatusPage;
