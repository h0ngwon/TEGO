import { Navigate } from "react-router-dom";
import { tokenStore } from "../util/token";

function RequireAuth({ children }) {
  const getToken = tokenStore.isLoggedIn;

  if (!getToken) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default RequireAuth;
