import axios, { Axios } from "axios";

const BASE_URL = "http://localhost:2424/api/customer"; 

export const registerUser = async (userData) => {
  try {
    const response = await axios.post(`${BASE_URL}/register`, userData);
    return response.data;
  } catch (error) {
    throw error.response?.data || "Registration failed";
  }
};

export const loginUser = async (credentials) => {
  try {
    const response = await axios.post(`${BASE_URL}/login`, credentials, {
      withCredentials: true, 
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || "Login failed";
  }
};

export const fetchCarImage = async (carName) => {
  const accessKey = "qdjou6Oq4Sog9ua_GyIbHlhcZG1xzvTje8us_1YZay4"; 
  const url = `https://api.unsplash.com/search/photos?query=${encodeURIComponent(carName)}&client_id=${accessKey}&per_page=1`;

  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`Unsplash API error: ${response.status}`);
    }

    const data = await response.json();
    const imageUrl = data.results[0]?.urls?.regular;

    return imageUrl || "https://via.placeholder.com/300x150.png?text=No+Image";
  } catch (error) {
    console.error("Error fetching car image:", error);
    return "https://via.placeholder.com/300x150.png?text=No+Image";
  }
};

export const fetchAllCars= async()=>{
  try{
    const response = await axios.get("http://localhost:2424/api/home/carlist",{ withCredentials: true })
    return response.data

  }catch(error){
    console.error("Error fetching cars:", error);
    return null;
  }
}
export const fetchCarById = async(id)=>{
  try{
    const response = await axios.get(`http://localhost:2424/api/home/carDetails/${id}`,{ withCredentials: true })
    return response.data

  }catch(error){
    console.error("Error fetching cars:", error);
    return null;
  }
}
export const fetchCustomerById = async (id) => {
  try {
    const response = await axios.get(`http://localhost:2424/api/customer/customerDetails/${id}`,{ withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("Error fetching customer:", error);
    return null;
  }
};
export const fetchAddressById= async(id)=>{
  try{
    const response= await axios.get(`http://localhost:2424/api/address/details/${id}`);
    return response.data;
  }catch(error){
    console.error("Error fetching Address:", error);
    return null;
  }
};

const PAYMENT_BASE_URL = "http://localhost:2424/api/bookings";

export const makePayment = async (paymentData) => {
  try {
    const response = await axios.post(PAYMENT_BASE_URL, paymentData, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("Payment failed:", error);
    throw error.response?.data || "Payment failed";
  }
};

export const fetchCustomerByEmail = async (email) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/by-email`,
      { email }, 
      { withCredentials: true }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching customer by email:", error);
    throw error;
  }
};

export const addNewCar = async (carData) => {
  try {
    const response = await axios.post("http://localhost:2424/api/rental-cars", carData, {
      withCredentials: true,
    });
    return response.data;  // typically returns created car info
  } catch (error) {
    console.error("Failed to add new car:", error);
    throw error.response?.data || "Failed to add new car";
  }
};
export const fetchRentalsByCustomer =async(id)=>{
  try{
    const response = await axios.post(`http://localhost:2424/api/home/customercars`,{id},{ withCredentials: true })
    return response.data

  }catch(error){
    console.error("Error fetching cars:", error);
    return null;
  }
}

export const addNewCarWithImage = async (formData) => {
  try {
    const response = await axios.post(`http://localhost:2424/api/home/add`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    console.error("Image upload failed:", error);
    throw error;
  }
};
export async function fetchRentalRequestsByCarId(carId) {
  try{
  const response = await axios.get(`http://localhost:2424/api/home/requests/car/${carId}`);
  return response.data;
  }catch(error)
  {
    console.error(error);
    return null
  }
  
};
export async function updateRentalStatus(rentalId, newStatus) {
  try{
  const response = await axios.put(`http://localhost:2424/api/home/rentals/${rentalId}/status`, 
    { status: newStatus }
  );
  return response.data;
  }catch(error){
    console.error(error);
    return null;
  }
}
export async function fetchRentalsByCustomerId(customerId) {
  try{
  const response = await axios.get(`http://localhost:2424/api/home/rentals/customer/${customerId}`);
  return response.data;
  }catch(error){
    console.error(error);
    return null;
  }
}
export async function updateCustomerInfo(customer){
  try{
    const response= await axios.put(`http://localhost:2424/api/customer/updates`,{
      customer
    });
    return response.data;
  }catch(error){
    console.error(error);
    return null;
  }
}