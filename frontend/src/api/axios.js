import axios from "axios";

/**
 * Instancia preconfigurada de Axios para comunicarse con el backend.
 *
 * La URL base se obtiene desde las variables de entorno de Vite.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
