// ProfileDetailsPage.jsx
import React, { useEffect, useState } from "react";
import Navbar from "../common/Navbar";
import { fetchCustomerByEmail, updateCustomerInfo } from "../../services/authService";
import "../../styles/ProfileDetailsPage.css";

function ProfileDetailsPage() {
  const [customer, setCustomer] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [form, setForm] = useState({});
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState(null);

  useEffect(() => {
    async function loadCustomer() {
      const email = localStorage.getItem("userEmail");
      if (!email) return;

      try {
        const data = await fetchCustomerByEmail(email);

        // Prepare form data and flatten addresses
        const addresses = data.addresses || [];
        const formData = {
          ...data,
          addresses: addresses.map((addr) => ({ ...addr }))
        };

        setCustomer(formData);
        setForm(formData);
      } catch (err) {
        console.error("Failed to fetch profile", err);
        setMessage("Failed to load profile.");
      } finally {
        setLoading(false);
      }
    }
    loadCustomer();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddressChange = (index, field, value) => {
    setForm((prev) => {
      const updatedAddresses = [...prev.addresses];
      updatedAddresses[index] = { ...updatedAddresses[index], [field]: value };
      return { ...prev, addresses: updatedAddresses };
    });
  };

  const handleSave = async () => {
    try {
      const updated = await updateCustomerInfo(form);
      setCustomer(updated);
      setIsEditing(false);
      setMessage("Profile updated successfully.");
    } catch (err) {
      console.error("Update failed", err);
      setMessage("Failed to update profile.");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="profile-container">
        <h2>Profile Details</h2>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <div className="profile-form">
            {message && <p className="message">{message}</p>}
            <label>First Name:
              <input
                name="firstName"
                value={form.firstName || ""}
                onChange={handleChange}
                disabled={!isEditing}
              />
            </label>
            <label>Last Name:
              <input
                name="lastName"
                value={form.lastName || ""}
                onChange={handleChange}
                disabled={!isEditing}
              />
            </label>
            <label>Email:
              <input
                name="email"
                value={form.email || ""}
                disabled
              />
            </label>
            <label>Phone:
              <input
                name="phoneNumber"
                value={form.phoneNumber || ""}
                onChange={handleChange}
                disabled={!isEditing}
              />
            </label>

            <h3>Addresses</h3>
            {form.addresses && form.addresses.length > 0 ? (
              form.addresses.map((address, idx) => (
                <div className="address-block" key={idx}>
                  <label>Street:
                    <input
                      value={address.street || ""}
                      onChange={(e) => handleAddressChange(idx, "street", e.target.value)}
                      disabled={!isEditing}
                    />
                  </label>
                  <label>City:
                    <input
                      value={address.city || ""}
                      onChange={(e) => handleAddressChange(idx, "city", e.target.value)}
                      disabled={!isEditing}
                    />
                  </label>
                  <label>State:
                    <input
                      value={address.state || ""}
                      onChange={(e) => handleAddressChange(idx, "state", e.target.value)}
                      disabled={!isEditing}
                    />
                  </label>
                  <label>Zip:
                    <input
                      value={address.zipCode || ""}
                      onChange={(e) => handleAddressChange(idx, "zipCode", e.target.value)}
                      disabled={!isEditing}
                    />
                  </label>
                </div>
              ))
            ) : (
              <p>No address info found.</p>
            )}

            <div className="profile-buttons">
              {isEditing ? (
                <>
                  <button onClick={handleSave}>Save</button>
                  <button onClick={() => { setForm(customer); setIsEditing(false); }}>Cancel</button>
                </>
              ) : (
                <button onClick={() => setIsEditing(true)}>Edit Profile</button>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default ProfileDetailsPage;
