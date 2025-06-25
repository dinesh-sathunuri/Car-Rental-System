import React, { useEffect, useState } from "react";
import Navbar from "../common/Navbar";
import "../../styles/CustomerHome.css";
import { fetchAllCars, fetchCarImage } from "../../services/authService";
import { Link } from "react-router-dom";

function CustomerHome() {
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);

  const [selectedMake, setSelectedMake] = useState("");
  const [selectedPriceRange, setSelectedPriceRange] = useState("");
  const [selectedAvailability, setSelectedAvailability] = useState("");
  const [currentPage, setCurrentPage] = useState(1);

  const carsPerPage = 6;

  // Fetch cars and images together
  const getCarsWithImages = async () => {
    try {
      const data = await fetchAllCars();
      if (!data) return;

      const carsWithImages = await Promise.all(
        data.map(async (car) => {
          if (!car.images || car.images.length === 0) {
            const carName = `${car.make} ${car.model}`;
            const imageUrl = await fetchCarImage(carName);
            return {
              ...car,
              images: [{ imageUrl }],
            };
          }
          return car;
        })
      );

      setCars(carsWithImages);
    } catch (err) {
      console.error("Failed to fetch cars or images:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getCarsWithImages();
  }, []);

  // Unique makes for filter dropdown
  const uniqueMakes = [...new Set(cars.map((car) => car.make))];

  // Filter cars based on Make, Price, and Availability
  const filteredCars = cars.filter((car) => {
    const matchesMake = selectedMake === "" || car.make === selectedMake;

    let matchesPrice = true;
    if (selectedPriceRange === "below60") {
      matchesPrice = car.rentPerDay < 60;
    } else if (selectedPriceRange === "60to80") {
      matchesPrice = car.rentPerDay >= 60 && car.rentPerDay <= 80;
    } else if (selectedPriceRange === "above80") {
      matchesPrice = car.rentPerDay > 80;
    }

    let matchesAvailability = true;
    if (selectedAvailability === "available") {
      // Assuming a car is available if it has no active rentals
      matchesAvailability = !car.rentals || car.rentals.length === 0;
    } else if (selectedAvailability === "notAvailable") {
      matchesAvailability = car.rentals && car.rentals.length > 0;
    }

    return matchesMake && matchesPrice && matchesAvailability;
  });

  // Pagination calculation
  const totalPages = Math.ceil(filteredCars.length / carsPerPage);
  const startIndex = (currentPage - 1) * carsPerPage;
  const currentCars = filteredCars.slice(startIndex, startIndex + carsPerPage);

  // Page change handler
  const goToPage = (page) => {
    if (page < 1 || page > totalPages) return;
    setCurrentPage(page);
  };

  return (
    <div className="container">
      <Navbar showLoginButton={true} showRegisterButton={true} />

      <div className="main-content">
        <aside className="filters">
          <h3>Filters</h3>

          <label>
            Make
            <select
              value={selectedMake}
              onChange={(e) => {
                setSelectedMake(e.target.value);
                setCurrentPage(1);
              }}
            >
              <option value="">All</option>
              {uniqueMakes.map((make) => (
                <option key={make} value={make}>
                  {make}
                </option>
              ))}
            </select>
          </label>

          <label>
            Price Range
            <select
              value={selectedPriceRange}
              onChange={(e) => {
                setSelectedPriceRange(e.target.value);
                setCurrentPage(1);
              }}
            >
              <option value="">All</option>
              <option value="below60">Below $60</option>
              <option value="60to80">$60 - $80</option>
              <option value="above80">Above $80</option>
            </select>
          </label>

          <label>
            Availability
            <select
              value={selectedAvailability}
              onChange={(e) => {
                setSelectedAvailability(e.target.value);
                setCurrentPage(1);
              }}
            >
              <option value="">All</option>
              <option value="available">Available</option>
              <option value="notAvailable">Not Available</option>
            </select>
          </label>
        </aside>

        <section className="car-list">
          <h2>Available Cars</h2>

          {loading ? (
            <p>Loading...</p>
          ) : currentCars.length === 0 ? (
            <p>No cars match your filters.</p>
          ) : (
            <div className="cards">
              {currentCars.map((car) => (
                <Link key={car.id} to={`/car/${car.id}`} className="car-card-link">
                <div  className="car-card" >
                  <img
                    src={
                      car.images && car.images.length > 0
                        ? car.images[0].base64ImageData
                          ? `data:image/jpeg;base64,${car.images[0].base64ImageData}`
                          : car.images[0].imageUrl || "https://via.placeholder.com/300x150.png?text=No+Image"
                        : "https://via.placeholder.com/300x150.png?text=No+Image"
                    }
                    alt={car.model}
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = "https://via.placeholder.com/300x150.png?text=No+Image";
                    }}
                  />
                  <div className="car-info">
                    <h4>
                      {car.year} {car.model}
                    </h4>
                    <p>{car.make}</p>
                    <p className="price">${car.rentPerDay || 0}/day</p>
                  </div>
                </div>
                </Link>
              ))}
            </div>
          )}

          {totalPages > 1 && (
            <div className="pagination">
              <button onClick={() => goToPage(currentPage - 1)} disabled={currentPage === 1}>
                {"<"}
              </button>
              {[...Array(totalPages)].map((_, i) => {
                const pageNum = i + 1;
                return (
                  <button
                    key={pageNum}
                    className={pageNum === currentPage ? "active" : ""}
                    onClick={() => goToPage(pageNum)}
                  >
                    {pageNum}
                  </button>
                );
              })}
              <button onClick={() => goToPage(currentPage + 1)} disabled={currentPage === totalPages}>
                {">"}
              </button>
            </div>
          )}
        </section>
      </div>
    </div>
  );
}

export default CustomerHome;
