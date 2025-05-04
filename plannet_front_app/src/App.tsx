import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router';
import styles from "./App.module.css"
import Header from "./core/module/organisms/header/Header.tsx";
import Footer from "./core/module/organisms/footer/Footer.tsx";
import Home from "./core/module/pages/Home.tsx";


const App = () =>  {
  return (
      <Router>
          <div className={styles.wrapper}>
              <Header />
              <main className={styles.main}>
                  <Routes>
                      <Route path="/" element={<Navigate to="/home" />} />
                      <Route path="/home" element={<Home />} />
                  </Routes>
              </main>
              <Footer />
          </div>
      </Router>
  )
}

export default App
