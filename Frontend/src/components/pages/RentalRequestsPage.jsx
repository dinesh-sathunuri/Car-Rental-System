import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../common/Navbar";
import {
  fetchRentalRequestsByCarId,
  fetchCustomerById,
  fetchCarById,
  updateRentalStatus, // You need to create this API
} from "../../services/authService";
import "../../styles/RentalRequestsPage.css";

function RentalRequestsPage() {
  const { carId } = useParams();
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function loadRequests() {
      setLoading(true);
      setError(null);

      try {
        const data = await fetchRentalRequestsByCarId(carId);
        if (!Array.isArray(data)) {
          setError("Unexpected data format received from server.");
          return;
        }

        const enriched = await Promise.all(
          data.map(async (req) => {
            let customerName = "Unknown";
            let carInfo = "";
            let customerEmail="";
            let customerPhone="";
            try {
              const customer = await fetchCustomerById(req.customerId);
              customerName = customer?.firstName || "Unknown";
              customerEmail =customer?.email||" ";
              customerPhone = customer?.phoneNumber||" ";
            } catch(error) {
              console.log(error);
            }

            try {
              const car = await fetchCarById(req.rentalCarId);
              carInfo = `${car?.year || ""} ${car?.make || ""} ${car?.model || ""}`;
            } catch(error) {
              console.log(error);
            }
            return { ...req, customerName,customerEmail,customerPhone, carInfo };
          })
        );

        setRequests(enriched);
      } catch (err) {
        setError("Failed to load rental requests.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    loadRequests();
  }, [carId]);

  const handleStatusUpdate = async (rentalId, newStatus) => {
    const confirm = window.confirm(`Are you sure you want to ${newStatus.toLowerCase()} this request?`);
    if (!confirm) return;

    try {
      await updateRentalStatus(rentalId, newStatus); // Backend call
      setRequests((prev) =>
        prev.map((r) =>
          r.id === rentalId ? { ...r, status: newStatus } : r
        )
      );
    } catch (err) {
      alert("Failed to update status");
      console.error("Status update failed", err);
    }
  };

  return (
    <div>
      <Navbar />
      <div className="rental-requests-container">
        <h2>Rental Requests</h2>
        {loading ? (
          <p>Loading...</p>
        ) : error ? (
          <p className="error-message">{error}</p>
        ) : requests.length === 0 ? (
          <p>No rental requests for this car.</p>
        ) : (
          <ul className="request-list">
            {requests.map((req) => (
              <li key={req.id} className="request-item">
                <p><strong>Car:</strong> {req.carInfo}</p>
                <p><strong>Customer:</strong> {req.customerName}</p>
                <p><strong>Customer Email:</strong> {req.customerEmail}</p>
                <p><strong>Customer phoneNumber:</strong> {req.customerPhone}</p>
                <p><strong>From:</strong> {new Date(req.startDate).toLocaleDateString()}</p>
                <p><strong>To:</strong> {new Date(req.endDate).toLocaleDateString()}</p>
                <p><strong>Status:</strong> {req.status}</p>
                <p><strong>Total:</strong> ${req.totalAmount}</p>

                {req.status === "PENDING" && (
                  <div className="action-buttons">
                    <button
                      className="approve-button"
                      onClick={() => handleStatusUpdate(req.id, "CONFIRMED")}
                    >
                      Approve
                    </button>
                    <button
                      className="cancel-button"
                      onClick={() => handleStatusUpdate(req.id, "CANCELLED")}
                    >
                      Cancel
                    </button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}

export default RentalRequestsPage;
