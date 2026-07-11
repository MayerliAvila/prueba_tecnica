<template>
  <!--
    Modal para crear o editar un beneficio.
    Se cierra al hacer clic fuera del contenido o al presionar el botón "✕".
  -->
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-box">
      <div class="modal-header">
        <h3>{{ isEditing ? "Editar Beneficio" : "Agregar Beneficio" }}</h3>

        <!-- Botón para cerrar el modal -->
        <button class="close-btn" type="button" @click="$emit('close')">✕</button>
      </div>

      <div class="modal-body">
        <!-- Campo para el nombre del beneficio -->
        <label class="field-label">Nombre del beneficio</label>
        <input
          v-model="form.benefitName"
          type="text"
          placeholder="Ej: Auxilio de Transporte"
          class="form-input"
        />

        <!-- Campo para el monto del beneficio -->
        <label class="field-label">Monto</label>
        <input
          v-model.number="form.amount"
          type="number"
          placeholder="Ej: 200000"
          class="form-input"
        />
      </div>

      <div class="modal-footer">
        <!-- Cierra el modal sin guardar -->
        <button class="cancel-btn" type="button" @click="$emit('close')">Cancelar</button>

        <!-- Guarda la información del beneficio -->
        <button class="save-btn" type="button" :disabled="!isValid" @click="handleSave">
          Guardar
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";

/**
 * Propiedades recibidas desde el componente padre.
 *
 * benefit:
 * - Si tiene información, el formulario funcionará en modo edición.
 * - Si es null, el formulario se utilizará para crear un nuevo beneficio.
 */
const props = defineProps({
  benefit: {
    type: Object,
    default: null,
  },
});

/**
 * Eventos emitidos hacia el componente padre.
 *
 * save  -> Envía la información del beneficio.
 * close -> Solicita cerrar el modal.
 */
const emit = defineEmits(["save", "close"]);

/**
 * Estado reactivo del formulario.
 */
const form = ref({
  benefitName: "",
  amount: null,
});

/**
 * Indica si el formulario está en modo edición.
 *
 * @returns {boolean}
 */
const isEditing = computed(() => !!props.benefit);

/**
 * Valida que los campos obligatorios tengan información.
 *
 * Se requiere:
 * - Nombre del beneficio.
 * - Monto.
 *
 * @returns {boolean}
 */
const isValid = computed(() => form.value.benefitName?.trim() && form.value.amount);

/**
 * Observa los cambios en la propiedad benefit.
 *
 * Cuando existe un beneficio:
 * - Carga sus datos en el formulario para editar.
 *
 * Cuando no existe:
 * - Limpia el formulario para crear un nuevo registro.
 */
watch(
  () => props.benefit,
  (newBenefit) => {
    if (newBenefit) {
      form.value = {
        benefitName: newBenefit.benefitName,
        amount: newBenefit.amount,
      };
    } else {
      form.value = {
        benefitName: "",
        amount: null,
      };
    }
  },
  {
    immediate: true,
  },
);

/**
 * Valida el formulario y emite el evento de guardado.
 */
const handleSave = () => {
  if (!isValid.value) return;

  emit("save", { ...form.value });
};
</script>

<style scoped>
/* Fondo semitransparente que cubre toda la pantalla */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

/* Contenedor principal del modal */
.modal-box {
  background: #fff;
  border-radius: 12px;
  width: 380px;
  max-width: 90vw;
  padding: 20px;
}

/* Encabezado del modal */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.modal-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 700;
  color: #111827;
}

/* Botón para cerrar el modal */
.close-btn {
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 16px;
  color: #9ca3af;
}

/* Contenedor de los campos del formulario */
.modal-body {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

/* Etiquetas de los campos */
.field-label {
  font-size: 12px;
  color: #6b7280;
  margin-top: 10px;
}

/* Campos de entrada */
.form-input {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 14px;
  outline: none;
}

/* Resalta el campo activo */
.form-input:focus {
  border-color: #4f46e5;
}

/* Pie del modal */
.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 20px;
}

/* Botón cancelar */
.cancel-btn {
  background: #f3f4f6;
  color: #374151;
  border: none;
  border-radius: 8px;
  padding: 9px 16px;
  font-size: 14px;
  cursor: pointer;
}

/* Botón guardar */
.save-btn {
  background: #4f46e5;
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 9px 16px;
  font-size: 14px;
  cursor: pointer;
}

/* Estado deshabilitado */
.save-btn:disabled {
  background: #c7d2fe;
  cursor: not-allowed;
}

/* Estado hover */
.save-btn:hover:not(:disabled) {
  background: #4338ca;
}
</style>
