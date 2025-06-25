import React, { useEffect, useState } from "react";
import Navbar from "../common/Navbar";
import {
  fetchRentalsByCustomer,
  addNewCarWithImage,
  fetchCustomerByEmail,
} from "../../services/authService";
import "../../styles/RentACarPage.css";
import { Link } from "react-router-dom";

function RentACarPage() {
  const [customerId, setCustomerId] = useState(null);
  const [rentals, setRentals] = useState([]);
  const [loadingRentals, setLoadingRentals] = useState(true);

  const [newCar, setNewCar] = useState({
    make: "",
    model: "",
    year: "",
    licensePlate: "",
    description: "",
    rentPerDay: "",
    customerId: null, // will set after fetching customer
  });

  const [carImage, setCarImage] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    async function fetchCustomerAndRentals() {
      try {
        const email = localStorage.getItem("userEmail");
        if (!email) {
          setMessage("User not logged in");
          return;
        }

        const customer = await fetchCustomerByEmail(email);
        const id = customer.id;
        setCustomerId(id);

        setNewCar(prev => ({ ...prev, customerId: id }));

        const data = await fetchRentalsByCustomer(id);
        setRentals(data);
      } catch (error) {
        console.error("Failed to fetch customer or rentals", error);
        setMessage("Error loading your cars.");
      } finally {
        setLoadingRentals(false);
      }
    }
    fetchCustomerAndRentals();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewCar((prev) => ({ ...prev, [name]: value }));
  };

  const handleImageChange = (e) => {
    setCarImage(e.target.files[0]);
  };

  const handleAddCar = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setMessage("");

    try {
      const formData = new FormData();
      // Serialize car data JSON as Blob and append with key 'car'
      const carBlob = new Blob([JSON.stringify({
        ...newCar,
        year: parseInt(newCar.year),
        rentPerDay: parseFloat(newCar.rentPerDay),
      })], { type: "application/json" });
      formData.append("car", carBlob);

      // Append image file under key 'images' (backend expects this key)
      if (carImage) {
        formData.append("images", carImage);
      }

      await addNewCarWithImage(formData);

      setMessage("✅ Car added successfully!");
      setNewCar({
        make: "",
        model: "",
        year: "",
        licensePlate: "",
        description: "",
        rentPerDay: "",
        customerId: customerId,
      });
      setCarImage(null);

      const updatedRentals = await fetchRentalsByCustomer(customerId);
      setRentals(updatedRentals);
    } catch (error) {
      console.error(error);
      setMessage("❌ Failed to add car. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <Navbar />
      <div className="rent-car-container">
        <h2>🚗 Rent Out Your Car</h2>

        <section>
          <h3>Your Listed Cars</h3>
          {loadingRentals ? (
            <p>Loading...</p>
          ) : rentals.length === 0 ? (
            <p>You haven't listed any cars yet.</p>
          ) : (
            <ul className="rented-car-list">
              {rentals.map((rental) => (
                <Link key={rental.id} to={`/car/${rental.id}/requests`} className="car-card-link">
                <li className="rented-car-item" key={rental.id}>
                  <p>
                    <strong>
                      {rental.make} {rental.model} ({rental.year})
                    </strong>
                  </p>
                  <p>License Plate: {rental.licensePlate}</p>
                  <p>Description: {rental.description}</p>
                  <p>Rent Per Day: ${rental.rentPerDay}</p>
                </li>
                </Link>
              ))}
            </ul>
          )}
        </section>

        <section>
          <h3>List a New Car</h3>
          <form onSubmit={handleAddCar} className="new-car-form" encType="multipart/form-data">
            <div>
              <label>Make:</label>
              <input name="make" value={newCar.make} onChange={handleInputChange} required />
            </div>
            <div>
              <label>Model:</label>
              <input name="model" value={newCar.model} onChange={handleInputChange} required />
            </div>
            <div>
              <label>Year:</label>
              <input name="year" type="number" value={newCar.year} onChange={handleInputChange} required />
            </div>
            <div>
              <label>License Plate:</label>
              <input name="licensePlate" value={newCar.licensePlate} onChange={handleInputChange} required />
            </div>
            <div>
              <label>Description:</label>
              <textarea name="description" value={newCar.description} onChange={handleInputChange} />
            </div>
            <div>
              <label>Rent Per Day ($):</label>
              <input name="rentPerDay" type="number" step="0.01" value={newCar.rentPerDay} onChange={handleInputChange} required />
            </div>
            <div>
              <label>Upload Car Image:</label>
              <input type="file" accept="image/*" onChange={handleImageChange} />
            </div>
            <button type="submit" disabled={submitting}>
              {submitting ? "Adding..." : "Add Car"}
            </button>
          </form>
          {message && <p className="feedback-message">{message}</p>}
        </section>
      </div>
    </div>
  );
}

export default RentACarPage;
