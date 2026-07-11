import api from "../api/axios";

/**
 * Servicio de acceso a la API REST para la gestión de beneficios laborales.
 * Encapsula todas las peticiones HTTP relacionadas con el recurso `/benefits`.
 */
export default {
  /**
   * Obtiene todos los beneficios de un empleado específico.
   * @param {number|string} idEmployee - Identificador del empleado
   * @returns {Promise} respuesta de Axios con `data` como arreglo de beneficios
   */
  getBenefitsByEmployee(idEmployee) {
    return api.get(`/benefits/employee/${idEmployee}`);
  },

  /**
   * Crea un nuevo beneficio.
   * @param {Object} benefit - Datos del beneficio (benefitName, amount, employeeId)
   * @returns {Promise} respuesta de Axios con el beneficio creado en `data`
   */
  createBenefit(benefit) {
    return api.post("/benefits", benefit);
  },

  /**
   * Actualiza un beneficio existente.
   * @param {number|string} id - Identificador del beneficio a actualizar
   * @param {Object} benefit - Nuevos datos del beneficio
   * @returns {Promise} respuesta de Axios con el beneficio actualizado en `data`
   */
  updateBenefit(id, benefit) {
    return api.put(`/benefits/${id}`, benefit);
  },

  /**
   * Elimina un beneficio por su ID.
   * @param {number|string} id - Identificador del beneficio a eliminar
   * @returns {Promise} respuesta de Axios (204 No Content)
   */
  deleteBenefit(id) {
    return api.delete(`/benefits/${id}`);
  },
};
