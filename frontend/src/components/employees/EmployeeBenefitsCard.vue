<template>
  <!--
    Tarjeta que administra los beneficios asociados a un empleado.
    Permite listar, crear, editar y eliminar beneficios.
  -->
  <div class="benefits-card">
    <div class="card-header">
      <h3>📄 Beneficios</h3>

      <!-- Abre el formulario para registrar un nuevo beneficio -->
      <button class="add-btn" type="button" @click="openAddModal">+ Agregar Beneficio</button>
    </div>

    <!-- Estado de carga -->
    <div v-if="loading" class="loading-state">Cargando beneficios...</div>

    <!-- Estado de error -->
    <div v-else-if="error" class="error-state">
      {{ error }}
    </div>

    <!-- Tabla de beneficios -->
    <table v-else class="benefits-table">
      <thead>
        <tr>
          <th>Beneficio</th>
          <th>Monto</th>
          <th class="acciones-col">Acciones</th>
        </tr>
      </thead>

      <tbody>
        <!-- Lista de beneficios -->
        <tr v-for="benefit in benefits" :key="benefit.id">
          <td>{{ benefit.benefitName }}</td>

          <td>${{ benefit.amount?.toLocaleString("es-CO") }}</td>

          <td class="acciones-col">
            <!-- Editar beneficio -->
            <button class="icon-btn edit" type="button" @click="openEditModal(benefit)">✎</button>

            <!-- Eliminar beneficio -->
            <button class="icon-btn delete" type="button" @click="handleDelete(benefit.id)">
              🗑
            </button>
          </td>
        </tr>

        <!-- Estado cuando no existen registros -->
        <tr v-if="benefits.length === 0">
          <td colspan="3" class="empty-state">Sin beneficios registrados</td>
        </tr>
      </tbody>
    </table>

    <!-- Modal para crear o editar beneficios -->
    <BenefitFormModal
      v-if="showModal"
      :benefit="editingBenefit"
      @save="handleSave"
      @close="closeModal"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import benefitService from "@/services/benefitService";
import { useToast } from "@/composables/useToast";
import { extractErrorMessage } from "@/utils/extractErrorMessage";
import BenefitFormModal from "@/components/benefit/BenefitFormModal.vue";

/**
 * Propiedades recibidas desde el componente padre.
 *
 * employeeId:
 * Identificador del empleado cuyos beneficios serán administrados.
 */
const props = defineProps({
  employeeId: {
    type: [Number, String],
    required: true,
  },
});

/**
 * Composable encargado de mostrar notificaciones
 * informativas, de éxito o error.
 */
const toast = useToast();

/**
 * Lista reactiva de beneficios.
 */
const benefits = ref([]);

/**
 * Indica si la información se encuentra cargando.
 */
const loading = ref(false);

/**
 * Almacena el mensaje de error en caso de fallo.
 */
const error = ref(null);

/**
 * Controla la visualización del modal.
 */
const showModal = ref(false);

/**
 * Beneficio seleccionado para edición.
 * Si es null, el formulario funcionará en modo creación.
 */
const editingBenefit = ref(null);

/**
 * Obtiene los beneficios del empleado desde el backend.
 */
const fetchBenefits = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await benefitService.getBenefitsByEmployee(props.employeeId);

    benefits.value = response.data;
  } catch (err) {
    error.value = "Error al cargar los beneficios";

    toast.error(extractErrorMessage(err, "No se pudieron cargar los beneficios"));

    console.error(err);
  } finally {
    loading.value = false;
  }
};

/**
 * Abre el modal para registrar un nuevo beneficio.
 */
const openAddModal = () => {
  editingBenefit.value = null;
  showModal.value = true;
};

/**
 * Abre el modal cargando la información del beneficio
 * seleccionado para editar.
 *
 * @param {Object} benefit Beneficio a editar.
 */
const openEditModal = (benefit) => {
  editingBenefit.value = benefit;
  showModal.value = true;
};

/**
 * Cierra el modal y limpia el beneficio seleccionado.
 */
const closeModal = () => {
  showModal.value = false;
  editingBenefit.value = null;
};

/**
 * Guarda un beneficio.
 *
 * Si existe un beneficio seleccionado se realiza una actualización,
 * de lo contrario se crea un nuevo registro.
 *
 * @param {Object} formData Información enviada desde el formulario.
 */
const handleSave = async (formData) => {
  try {
    if (editingBenefit.value) {
      await benefitService.updateBenefit(editingBenefit.value.id, {
        ...formData,
        employeeId: props.employeeId,
      });

      toast.success("Beneficio actualizado correctamente");
    } else {
      await benefitService.createBenefit({
        ...formData,
        employeeId: props.employeeId,
      });

      toast.success("Beneficio agregado correctamente");
    }

    closeModal();
    await fetchBenefits();
  } catch (err) {
    toast.error(extractErrorMessage(err, "No se pudo guardar el beneficio"));

    console.error(err);
  }
};

/**
 * Elimina un beneficio y actualiza la lista.
 *
 * @param {number|string} id Identificador del beneficio.
 */
const handleDelete = async (id) => {
  try {
    await benefitService.deleteBenefit(id);

    toast.success("Beneficio eliminado correctamente");

    await fetchBenefits();
  } catch (err) {
    toast.error(extractErrorMessage(err, "No se pudo eliminar el beneficio"));

    console.error(err);
  }
};

/**
 * Carga los beneficios cuando el componente es montado.
 */
onMounted(fetchBenefits);
</script>

<style scoped>
/* Tarjeta principal */
.benefits-card {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 20px 24px;
}

/* Encabezado de la tarjeta */
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.card-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 700;
  color: #111827;
}

/* Botón para agregar un beneficio */
.add-btn {
  background: #4f46e5;
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 8px 14px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
}

.add-btn:hover {
  background: #4338ca;
}

/* Tabla de beneficios */
.benefits-table {
  width: 100%;
  border-collapse: collapse;
}

.benefits-table thead th {
  text-align: left;
  font-size: 12px;
  color: #9ca3af;
  padding: 10px 0;
  border-bottom: 1px solid #f3f4f6;
}

.benefits-table tbody td {
  padding: 12px 0;
  font-size: 14px;
  color: #374151;
  border-bottom: 1px solid #f9fafb;
}

/* Columna de acciones */
.acciones-col {
  text-align: right;
  width: 90px;
}

/* Botones de acción */
.icon-btn {
  border: 1px solid #e5e7eb;
  background: #fff;
  border-radius: 6px;
  width: 30px;
  height: 30px;
  cursor: pointer;
  margin-left: 6px;
}

/* Botón editar */
.icon-btn.edit {
  color: #2563eb;
}

/* Botón eliminar */
.icon-btn.delete {
  color: #dc2626;
}

/* Efecto hover */
.icon-btn:hover {
  background: #f9fafb;
}

/* Estados de carga, error y lista vacía */
.empty-state,
.loading-state,
.error-state {
  text-align: center;
  color: #9ca3af;
  padding: 20px 0;
}

/* Color para mensajes de error */
.error-state {
  color: #dc2626;
}
</style>
