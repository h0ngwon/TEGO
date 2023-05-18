export const tokenStore = {
  set: token => {
    if (!token) {
      return;
    }
    localStorage.setItem("user", token);
  },
  get: () => {
    return localStorage.getItem("user");
  },
  get isLoggedIn() {
    return !!this.get();
  },
  get getToken() {
    return JSON.parse(localStorage.getItem("user"));
  },
};
