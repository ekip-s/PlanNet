import { BrowserRouter as Router } from 'react-router';
import styles from "./App.module.css"
import Header from "./core/module/organisms/header/Header.tsx";


const App = () =>  {
  return (
      <Router>
          <div className={styles.wrapper}>
              <Header />
              <main className={styles.main}>

              </main>
              <footer>подвальчик</footer>
          </div>
      </Router>
  )
}

export default App
