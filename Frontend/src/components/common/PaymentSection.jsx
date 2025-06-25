import React, { useState } from "react";
import { makePayment,fetchCustomerByEmail } from "../../services/authService";
import "../../styles/PaymentSection.css";

function PaymentSection({ car, startDate, endDate }) {
  const [paymentMethod, setPaymentMethod] = useState("Credit Card");
  const [paymentStatus, setPaymentStatus] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const calculateTotalAmount = () => {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const days = Math.ceil((end - start) / (1000 * 60 * 60 * 24));
    return days > 0 ? days * car.rentPerDay : 0;
  };

  const handlePayment = async () => {
    const customerDetails = await fetchCustomerByEmail(localStorage.getItem("userEmail"));
    const totalAmount = calculateTotalAmount();

    console.log(customerDetails);
    if (totalAmount <= 0) {
      setPaymentStatus("❌ Invalid date range.");
      return;
    }

    try {
      setSubmitting(true);
      const formattedStartDate = startDate.length === 10 ? `${startDate}T00:00:00` : startDate;
      const formattedEndDate = endDate.length === 10 ? `${endDate}T00:00:00` : endDate;
      const paymentData = {
        rentalCarId: car.id,
        customerId: customerDetails.id,
        startDate:formattedStartDate,
        endDate:formattedEndDate,
        totalAmount: totalAmount,
        method: paymentMethod,
      };

      await makePayment(paymentData); // ✅ use the service function

      setPaymentStatus("✅ Payment successful! Your booking is confirmed.");
    } catch (error) {
      setPaymentStatus("❌ Payment failed. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  const total = calculateTotalAmount();

  return (
    <div className="payment-section">
      <h3>Payment Details</h3>
      <div className="payment-info">
        <p><strong>From:</strong> {new Date(startDate).toLocaleDateString()}</p>
        <p><strong>To:</strong> {new Date(endDate).toLocaleDateString()}</p>
        <p><strong>Total Days:</strong> {Math.ceil((new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24))}</p>
        <p><strong>Total Amount:</strong> ${total}</p>
      </div>

      <div className="payment-method">
        <label htmlFor="method">Payment Method:</label>
        <select id="method" value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
          <option value="Credit Card">Credit Card</option>
          <option value="UPI">UPI</option>
          <option value="Cash">Cash</option>
        </select>
      </div>

      <button className="pay-button" onClick={handlePayment} disabled={submitting}>
        {submitting ? "Processing..." : "Pay Now"}
      </button>

      {paymentStatus && (
        <p className={`payment-status ${paymentStatus.startsWith("✅") ? "success" : "error"}`}>
          {paymentStatus}
        </p>
      )}
    </div>
  );
}

export default PaymentSection;
