import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router';
import styles from "./App.module.css"
import Header from "./core/module/organisms/header/Header.tsx";
import Footer from "./core/module/organisms/footer/Footer.tsx";
import Home from "./core/module/pages/Home.tsx";
import LogoutPage from "./core/module/pages/LogoutPage.tsx";
import ProtectedRoute from "./keycloak/ProtectedRoute.tsx";
import {AuthProvider} from "./keycloak/AuthContext.tsx";


const App = () =>  {
  return (
      <AuthProvider>
      <Router>
          <div className={styles.wrapper}>
              <Header />
              <main className={styles.main}>
                  <Routes>
                      <Route path="/" element={<Navigate to="/home" />} />
                      <Route path="/home" element={<Home />} />
                      <Route path="/logout" element={<LogoutPage />} />
                      <Route element={<ProtectedRoute />}>
                          <Route path="/tasks" element={<div>Защищенный слой</div>} />
                      </Route>
                  </Routes>
              </main>
              <Footer />
          </div>
      </Router>
      </AuthProvider>
  )
}

export default App
