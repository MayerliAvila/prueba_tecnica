<template>
  <!--
    Vista principal de empleados.
    Permite consultar, buscar, paginar y registrar nuevos empleados.
  -->
  <div class="employee-list-view">
    <!-- Encabezado -->
    <div class="header">
      <div>
        <h1>Empleados</h1>
        <p>Gestiona la información de los empleados de la empresa.</p>
      </div>

      <!-- Abre el formulario para crear un empleado -->
      <button class="new-employee-btn" type="button" @click="openCreateModal">
        + Nuevo Empleado
      </button>
    </div>

    <!-- Barra de búsqueda -->
    <EmployeeSearchBar @search="handleSearch" />

    <!-- Tabla de empleados -->
    <EmployeeTable :employees="paginatedEmployees" @view-employee="goToDetail" />

    <!-- Paginación -->
    <EmployeePagination
      :current-page="currentPage"
      :total-items="filteredEmployees.length"
      :items-per-page="itemsPerPage"
      @update:page="currentPage = $event"
    />

    <!-- Modal para crear un empleado -->
    <EmployeeFormModal
      v-if="showModal"
      :employee="editingEmployee"
      @save="handleSave"
      @close="closeModal"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import employeeService from "@/services/employeeService";
import { useToast } from "@/composables/useToast";
import { extractErrorMessage } from "@/utils/extractErrorMessage";
import EmployeeSearchBar from "@/components/employees/EmployeeSearchBar.vue";
import EmployeeTable from "@/components/employees/EmployeeTable.vue";
import EmployeePagination from "@/components/employees/EmployeePagination.vue";
import EmployeeFormModal from "@/components/employees/EmployeeFormModal.vue";

/**
 * Instancia del enrutador para navegar
 * entre las diferentes vistas.
 */
const router = useRouter();

/**
 * Composable encargado de mostrar notificaciones.
 */
const toast = useToast();

/**
 * Lista completa de empleados.
 */
const employees = ref([]);

/**
 * Texto ingresado en la búsqueda.
 */
const searchText = ref("");

/**
 * Página actualmente seleccionada.
 */
const currentPage = ref(1);

/**
 * Cantidad de registros mostrados por página.
 */
const itemsPerPage = 5;

/**
 * Estado de carga.
 */
const loading = ref(false);

/**
 * Mensaje de error.
 */
const error = ref(null);

/**
 * Controla la visualización del modal.
 */
const showModal = ref(false);

/**
 * Empleado seleccionado para edición.
 *
 * En esta vista permanece en null porque únicamente
 * se realiza el registro de nuevos empleados.
 */
const editingEmployee = ref(null);

/**
 * Indica si la operación de guardado está en proceso.
 */
const saving = ref(false);

/**
 * Obtiene todos los empleados desde el backend.
 */
const fetchEmployees = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await employeeService.getEmployees();

    employees.value = response.data;
  } catch (err) {
    error.value = "Error al cargar los empleados";

    toast.error(extractErrorMessage(err, "No se pudieron cargar los empleados"));

    console.error(err);
  } finally {
    loading.value = false;
  }
};

/**
 * Actualiza el texto de búsqueda
 * y reinicia la paginación.
 *
 * @param {string} text Texto ingresado.
 */
const handleSearch = (text) => {
  searchText.value = text;
  currentPage.value = 1;
};

/**
 * Filtra la lista de empleados
 * por nombre, ciudad, cargo o correo.
 *
 * @returns {Array}
 */
const filteredEmployees = computed(() => {
  if (!searchText.value) return employees.value;

  const term = searchText.value.toLowerCase();

  return employees.value.filter(
    (emp) =>
      emp.name?.toLowerCase().includes(term) ||
      emp.city?.toLowerCase().includes(term) ||
      emp.position?.toLowerCase().includes(term) ||
      emp.email?.toLowerCase().includes(term),
  );
});

/**
 * Obtiene únicamente los registros
 * correspondientes a la página actual.
 *
 * @returns {Array}
 */
const paginatedEmployees = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;

  return filteredEmployees.value.slice(start, end);
});

/**
 * Navega hacia la vista de detalle
 * del empleado seleccionado.
 *
 * @param {number|string} id Identificador del empleado.
 */
const goToDetail = (id) => {
  router.push(`/empleados/${id}`);
};

/**
 * Abre el formulario para crear un nuevo empleado.
 */
const openCreateModal = () => {
  editingEmployee.value = null;
  showModal.value = true;
};

/**
 * Cierra el formulario y limpia el estado.
 */
const closeModal = () => {
  showModal.value = false;
  editingEmployee.value = null;
};

/**
 * Registra un nuevo empleado.
 *
 * Si el registro es exitoso:
 * - Muestra una notificación.
 * - Cierra el formulario.
 * - Recarga la lista de empleados.
 *
 * @param {Object} formData Información del empleado.
 */
const handleSave = async (formData) => {
  saving.value = true;

  try {
    await employeeService.createEmployee(formData);

    toast.success("Empleado creado correctamente");

    closeModal();

    await fetchEmployees();
  } catch (err) {
    toast.error(extractErrorMessage(err, "No se pudo crear el empleado"));

    console.error(err);
  } finally {
    saving.value = false;
  }
};

/**
 * Carga la lista de empleados
 * cuando el componente es montado.
 */
onMounted(fetchEmployees);
</script>

<style scoped>
/* Contenedor principal */
.employee-list-view {
  padding: 32px;
  max-width: 1100px;
  margin: 0 auto;
}

/* Encabezado */
.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
}

/* Título */
.header h1 {
  font-size: 28px;
  font-weight: 700;
  color: #111827;
  margin: 0 0 4px;
}

/* Descripción */
.header p {
  color: #6b7280;
  font-size: 14px;
  margin: 0;
}

/* Botón para crear un empleado */
.new-employee-btn {
  background: #4f46e5;
  color: #fff;
  border: none;
  border-radius: 10px;
  padding: 10px 18px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
}

/* Efecto hover */
.new-employee-btn:hover {
  background: #4338ca;
}
</style>
