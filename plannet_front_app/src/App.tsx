import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router';
import styles from "./App.module.css"
import Header from "./core/module/organisms/header/Header.tsx";
import Footer from "./core/module/organisms/footer/Footer.tsx";
import Home from "./core/module/pages/Home.tsx";
import LogoutPage from "./core/module/pages/LogoutPage.tsx";
import ProtectedRoute from "./keycloak/ProtectedRoute.tsx";
import {AuthProvider} from "./keycloak/AuthContext.tsx";
import Profile from "./core/module/pages/Profile.tsx";
import "primereact/resources/primereact.min.css";
import "primereact/resources/themes/lara-light-indigo/theme.css";
import GroupManager from "./core/module/organisms/GroupManager.tsx";
import 'primeicons/primeicons.css';
import JoinGroup from "./core/module/organisms/join_group/JoinGroup.tsx";
import TaskPage from "./core/module/pages/task/TaskPage.tsx";
import NewTask from "./core/module/organisms/new_task/NewTask.tsx";

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
                          <Route path="/tasks" element={<TaskPage />} />
                          <Route path="/tasks/new" element={<NewTask />} />
                          <Route path="/profile" element={<Profile />}>
                              <Route path="group" element={<GroupManager />} />
                              <Route path="group/join/:code" element={<JoinGroup />} />
                          </Route>
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
