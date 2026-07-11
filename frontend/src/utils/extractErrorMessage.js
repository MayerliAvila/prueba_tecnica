/**
 * Extrae un mensaje de error legible a partir de un error de Axios.
 *
 * Prioridad de extracción:
 * 1. `err.response.data.message`: mensaje estructurado del backend (formato ApiError)
 * 2. Error de red: cuando hay request pero no response (backend caído, CORS, etc.)
 * 3. `fallback`: mensaje genérico configurable
 *
 * @param {Object} err - Objeto de error capturado en el catch de Axios
 * @param {string} [fallback='Ocurrió un error inesperado'] - Mensaje de respaldo
 * @returns {string} Mensaje de error legible para el usuario
 */
export function extractErrorMessage(err, fallback = "Ocurrió un error inesperado") {
  // Error de respuesta del backend (con el formato ApiError)
  if (err?.response?.data?.message) {
    return err.response.data.message;
  }

  // Error de red (backend caído, sin conexión, CORS, etc.)
  if (err?.request && !err?.response) {
    return "No se pudo conectar con el servidor. Verifica tu conexión.";
  }

  // Fallback genérico
  return fallback;
}
