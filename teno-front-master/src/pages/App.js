import React from "react";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { ConfigProvider } from "antd";
import locale from "antd/lib/locale/ko_KR";
import AppLayout from "../components/AppLayout";
import NoRequireAuth from "../components/NoRequireAuth";
import RequireAuth from "../components/RequireAuth";
import Home from "./Home";
import CenterDetailPage from "./CenterDetailPage";
import TimeTablePage from "./TimetablePage";
import LoginPage from "./LoginPage";
import SignUp from "./SignUpPage";
import Profile from "./Profile";
import "../style/index.css";

function App() {
  const queryClient = new QueryClient();

  return (
    <ConfigProvider locale={locale}>
      <BrowserRouter>
        <QueryClientProvider client={queryClient}>
          <AppLayout>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route
                path="/centers/:id"
                element={
                  <RequireAuth>
                    <CenterDetailPage />
                  </RequireAuth>
                }
              />
              <Route
                path="/centers/:centerId/timetable"
                element={
                  <RequireAuth>
                    <TimeTablePage />
                  </RequireAuth>
                }
              />
              <Route
                path="login"
                element={
                  <NoRequireAuth>
                    <LoginPage />
                  </NoRequireAuth>
                }
              />
              <Route
                path="signUp"
                element={
                  <NoRequireAuth>
                    <SignUp />
                  </NoRequireAuth>
                }
              />
              <Route
                path="/profile"
                element={
                  <RequireAuth>
                    <Profile />
                  </RequireAuth>
                }
              />
            </Routes>
          </AppLayout>
        </QueryClientProvider>
      </BrowserRouter>
    </ConfigProvider>
  );
}

export default App;
