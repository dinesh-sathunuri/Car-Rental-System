import { BrowserRouter,Routes,Route } from "react-router-dom"
import Home from './components/pages/Home';
import Login from './components/pages/Login'
import Register from "./components/pages/Register";
import CustomerHome from "./components/pages/CustomerHome";
import CarDetail from "./components/pages/CarDetail";
import ProtectedRoute from "./components/pages/ProtectedRoute";
import RentACarPage from "./components/pages/RentACarPage";
import RentalRequestsPage from "./components/pages/RentalRequestsPage";
import RentalStatusPage from "./components/pages/RentalStatusPage";
import ProfileDetailsPage from "./components/pages/ProfileDetailsPage";
function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/login" element={<Login />}/>
        <Route path="/register" element={<Register/>}/>
        <Route path="/home" element={<CustomerHome/>}/>
        <Route
          path="/car/:carId"
          element={
            <ProtectedRoute>
              <CarDetail />
            </ProtectedRoute>
          }
        />
      <Route
          path="/my-cars"
          element={
            <ProtectedRoute>
              <RentACarPage />
            </ProtectedRoute>
          }
        />
        <Route path="/car/:carId/requests" element={<RentalRequestsPage />} />
        <Route path="/rental-status" element={<RentalStatusPage />} />
        <Route path="/profile" element={<ProfileDetailsPage/>}/>
      </Routes>
      
    </BrowserRouter>
    
  )
}

export default App
