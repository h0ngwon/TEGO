import { Navigate } from "react-router-dom";
import { tokenStore } from "../util/token";

function NoRequireAuth({ children }) {
  const { isLoggedIn } = tokenStore;

  if (isLoggedIn) {
    return <Navigate to="/" replace />;
  }

  return children;
}

export default NoRequireAuth;
