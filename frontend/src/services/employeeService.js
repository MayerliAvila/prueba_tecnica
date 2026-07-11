import api from "../api/axios";

/**
 * Servicio de acceso a la API REST para la gestión de empleados.
 * Encapsula todas las peticiones HTTP relacionadas con el recurso `/employees`.
 */
export default {
  /**
   * Obtiene la lista completa de empleados.
   * @returns {Promise} respuesta de Axios con `data` como arreglo de empleados
   */
  getEmployees() {
    return api.get("/employees");
  },

  /**
   * Obtiene los datos de un empleado específico.
   * @param {number|string} id - Identificador del empleado
   * @returns {Promise} respuesta de Axios con `data` como objeto empleado
   */
  getEmployeeById(id) {
    return api.get(`/employees/${id}`);
  },

  /**
   * Crea un nuevo empleado.
   * @param {Object} employee - Datos del empleado según el esquema del backend
   * @returns {Promise} respuesta de Axios con el empleado creado en `data`
   */
  createEmployee(employee) {
    return api.post("/employees", employee);
  },

  /**
   * Actualiza un empleado existente.
   * @param {number|string} id - Identificador del empleado a actualizar
   * @param {Object} employee - Nuevos datos del empleado
   * @returns {Promise} respuesta de Axios con el empleado actualizado en `data`
   */
  updateEmployee(id, employee) {
    return api.put(`/employees/${id}`, employee);
  },

  /**
   * Elimina un empleado por su ID.
   * @param {number|string} id - Identificador del empleado a eliminar
   * @returns {Promise} respuesta de Axios (204 No Content)
   */
  deleteEmployee(id) {
    return api.delete(`/employees/${id}`);
  },
};
