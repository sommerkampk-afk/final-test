import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar/NavBar';
import LoginView from './views/LoginView/LoginView';
import LogoutView from './views/LogoutView';
import RegisterView from './views/RegisterView/RegisterView';

export default function App() {

  return (
    <div id="cart-app">
      <BrowserRouter>
        <NavBar />
        <main>
          <Routes>
            <Route path="/login" element={<LoginView />} />
            <Route path="/logout" element={<LogoutView />} />
            <Route path="/register" element={<RegisterView />} />
          </Routes>
        </main>
      </BrowserRouter>
    </div>
  );
}
