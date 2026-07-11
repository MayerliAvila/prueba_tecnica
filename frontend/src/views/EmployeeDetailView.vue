<template>
  <!--
    Vista de detalle de un empleado.
    Muestra la información general, ubicación, beneficios
    y permite editar los datos del empleado.
  -->
  <div class="employee-detail-view">
    <!-- Enlace para regresar al listado de empleados -->
    <RouterLink to="/" class="back-link"> ← Volver a empleados </RouterLink>

    <!-- Estado de carga -->
    <div v-if="loading" class="loading">Cargando empleado...</div>

    <!-- Estado de error -->
    <div v-else-if="error" class="error">
      {{ error }}
    </div>

    <!-- Información del empleado -->
    <template v-else-if="employee">
      <!-- Encabezado -->
      <EmployeeHeaderCard :employee="employee" @edit="openEditModal" />

      <!-- Información principal -->
      <div class="detail-grid">
        <EmployeeLocationCard :employee="employee" />

        <EmployeeBenefitsCard :employee-id="employee.id" />
      </div>

      <!-- Información adicional -->
      <EmployeeInfoFooter :employee="employee" />
    </template>

    <!-- Modal para editar el empleado -->
    <EmployeeFormModal
      v-if="showModal"
      :employee="employee"
      @save="handleSave"
      @close="closeModal"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import employeeService from "@/services/employeeService";
import { useToast } from "@/composables/useToast";
import { extractErrorMessage } from "@/utils/extractErrorMessage";
import EmployeeHeaderCard from "@/components/employees/EmployeeHeaderCard.vue";
import EmployeeLocationCard from "@/components/employees/EmployeeLocationCard.vue";
import EmployeeBenefitsCard from "@/components/employees/EmployeeBenefitsCard.vue";
import EmployeeInfoFooter from "@/components/employees/EmployeeInfoFooter.vue";
import EmployeeFormModal from "@/components/employees/EmployeeFormModal.vue";

/**
 * Obtiene los parámetros de la ruta actual.
 * Se utiliza para recuperar el identificador del empleado.
 */
const route = useRoute();

/**
 * Composable encargado de mostrar notificaciones.
 */
const toast = useToast();

/**
 * Información del empleado.
 */
const employee = ref(null);

/**
 * Estado de carga de la consulta.
 */
const loading = ref(false);

/**
 * Mensaje de error en caso de fallo.
 */
const error = ref(null);

/**
 * Controla la visualización del modal de edición.
 */
const showModal = ref(false);

/**
 * Indica si la actualización del empleado está en proceso.
 */
const saving = ref(false);

/**
 * Obtiene la información del empleado desde el backend.
 */
const fetchEmployee = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await employeeService.getEmployeeById(route.params.id);

    employee.value = response.data;
  } catch (err) {
    error.value = "Error al cargar el empleado";

    toast.error(extractErrorMessage(err, "No se pudo cargar el empleado"));

    console.error(err);
  } finally {
    loading.value = false;
  }
};

/**
 * Abre el formulario de edición.
 */
const openEditModal = () => {
  showModal.value = true;
};

/**
 * Cierra el formulario de edición.
 */
const closeModal = () => {
  showModal.value = false;
};

/**
 * Actualiza la información del empleado.
 *
 * Si la operación es exitosa:
 * - Muestra una notificación.
 * - Cierra el modal.
 * - Recarga la información actualizada.
 *
 * @param {Object} formData Información enviada desde el formulario.
 */
const handleSave = async (formData) => {
  saving.value = true;

  try {
    await employeeService.updateEmployee(route.params.id, formData);

    toast.success("Empleado actualizado correctamente");

    closeModal();

    await fetchEmployee();
  } catch (err) {
    toast.error(extractErrorMessage(err, "No se pudo actualizar el empleado"));

    console.error(err);
  } finally {
    saving.value = false;
  }
};

/**
 * Carga la información del empleado
 * cuando la vista es montada.
 */
onMounted(fetchEmployee);
</script>

<style scoped>
/* Contenedor principal de la vista */
.employee-detail-view {
  padding: 32px;
  max-width: 1100px;
  margin: 0 auto;
}

/* Enlace para volver al listado */
.back-link {
  display: inline-block;
  color: #6b7280;
  font-size: 14px;
  text-decoration: none;
  margin-bottom: 16px;
}

.back-link:hover {
  color: #374151;
}

/* Distribución de las tarjetas principales */
.detail-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-top: 20px;
}

/* Estados de carga y error */
.loading,
.error {
  text-align: center;
  padding: 60px 0;
  color: #6b7280;
}

/* Mensaje de error */
.error {
  color: #dc2626;
}

/* Diseño responsivo */
@media (max-width: 768px) {
  .detail-grid {
    grid-template-columns: 1fr;
  }
}
</style>
