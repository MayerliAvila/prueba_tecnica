<template>
  <!--
    Modal para crear o editar un empleado.
    Se cierra al hacer clic fuera del contenido o mediante el botón de cierre.
  -->
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-box">
      <div class="modal-header">
        <h3>{{ isEditing ? "Editar Empleado" : "Nuevo Empleado" }}</h3>

        <!-- Botón para cerrar el modal -->
        <button class="close-btn" type="button" @click="$emit('close')">✕</button>
      </div>

      <div class="modal-body">
        <!-- Nombre -->
        <div class="field">
          <label class="field-label">Nombre</label>

          <input v-model="form.name" type="text" placeholder="Ej: Juan Pérez" class="form-input" />

          <span v-if="errors.name" class="field-error">
            {{ errors.name }}
          </span>
        </div>

        <!-- Correo -->
        <div class="field">
          <label class="field-label">Correo</label>

          <input
            v-model="form.email"
            type="email"
            placeholder="Ej: juan.perez@gmail.com"
            class="form-input"
          />

          <span v-if="errors.email" class="field-error">
            {{ errors.email }}
          </span>
        </div>

        <!-- Ciudad y Cargo -->
        <div class="field-row">
          <div class="field">
            <label class="field-label">Ciudad</label>

            <input v-model="form.city" type="text" placeholder="Ej: Bogotá" class="form-input" />

            <span v-if="errors.city" class="field-error">
              {{ errors.city }}
            </span>
          </div>

          <div class="field">
            <label class="field-label">Cargo</label>

            <input
              v-model="form.position"
              type="text"
              placeholder="Ej: Backend Developer"
              class="form-input"
            />

            <span v-if="errors.position" class="field-error">
              {{ errors.position }}
            </span>
          </div>
        </div>

        <!-- Teléfono y Departamento -->
        <div class="field-row">
          <div class="field">
            <label class="field-label">Teléfono</label>

            <input
              v-model="form.phone"
              type="text"
              placeholder="Ej: 300 123 4567"
              class="form-input"
            />

            <span v-if="errors.phone" class="field-error">
              {{ errors.phone }}
            </span>
          </div>

          <div class="field">
            <label class="field-label">Departamento</label>

            <input
              v-model="form.department"
              type="text"
              placeholder="Ej: Tecnología"
              class="form-input"
            />

            <span v-if="errors.department" class="field-error">
              {{ errors.department }}
            </span>
          </div>
        </div>

        <!-- Fecha de ingreso y Salario -->
        <div class="field-row">
          <div class="field">
            <label class="field-label">Fecha de ingreso</label>

            <input v-model="form.hireDate" type="date" class="form-input" />

            <span v-if="errors.hireDate" class="field-error">
              {{ errors.hireDate }}
            </span>
          </div>

          <div class="field">
            <label class="field-label">Salario Base</label>

            <input
              v-model.number="form.baseSalary"
              type="number"
              placeholder="Ej: 2800000"
              class="form-input"
            />

            <span v-if="errors.baseSalary" class="field-error">
              {{ errors.baseSalary }}
            </span>
          </div>
        </div>

        <!-- Estado -->
        <div class="field">
          <label class="field-label">Estado</label>

          <select v-model="form.status" class="form-input">
            <option value="ACTIVO">Activo</option>
            <option value="INACTIVO">Inactivo</option>
            <option value="VACACIONES">Vacaciones</option>
            <option value="LICENCIA">Licencia</option>
          </select>
        </div>
      </div>

      <div class="modal-footer">
        <!-- Cancela la operación -->
        <button class="cancel-btn" type="button" @click="$emit('close')">Cancelar</button>

        <!-- Guarda la información del empleado -->
        <button class="save-btn" type="button" :disabled="saving" @click="handleSave">
          {{ saving ? "Guardando..." : "Guardar" }}
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
 * employee:
 * - Si contiene información, el formulario funcionará en modo edición.
 * - Si es null, se utilizará para crear un nuevo empleado.
 */
const props = defineProps({
  employee: {
    type: Object,
    default: null,
  },
});

/**
 * Eventos emitidos hacia el componente padre.
 *
 * save  -> Envía la información del formulario.
 * close -> Solicita cerrar el modal.
 */
const emit = defineEmits(["save", "close"]);

/**
 * Indica si el formulario está en modo edición.
 *
 * @returns {boolean}
 */
const isEditing = computed(() => !!props.employee);

/**
 * Estado que controla si el formulario se encuentra guardando.
 */
const saving = ref(false);

/**
 * Retorna la estructura inicial del formulario.
 *
 * @returns {Object}
 */
const defaultForm = () => ({
  name: "",
  email: "",
  city: "",
  position: "",
  phone: "",
  department: "",
  hireDate: "",
  baseSalary: null,
  status: "ACTIVO",
});

/**
 * Datos reactivos del formulario.
 */
const form = ref(defaultForm());

/**
 * Almacena los errores de validación.
 */
const errors = ref({});

/**
 * Observa los cambios del empleado recibido.
 *
 * Si existe un empleado:
 * - Carga la información para editar.
 *
 * Si no existe:
 * - Restablece el formulario para un nuevo registro.
 */
watch(
  () => props.employee,
  (newEmployee) => {
    if (newEmployee) {
      form.value = {
        name: newEmployee.name || "",
        email: newEmployee.email || "",
        city: newEmployee.city || "",
        position: newEmployee.position || "",
        phone: newEmployee.phone || "",
        department: newEmployee.department || "",
        hireDate: newEmployee.hireDate || "",
        baseSalary: newEmployee.baseSalary ?? null,
        status: newEmployee.status || "ACTIVO",
      };
    } else {
      form.value = defaultForm();
    }

    // Limpia los errores al abrir el formulario.
    errors.value = {};
  },
  { immediate: true },
);

/**
 * Valida la información ingresada por el usuario.
 *
 * Reglas:
 * - Todos los campos son obligatorios.
 * - El correo debe tener un formato válido.
 * - La fecha de ingreso no puede ser futura.
 * - El salario debe ser mayor que cero.
 *
 * @returns {boolean} true si el formulario es válido.
 */
const validate = () => {
  const newErrors = {};

  if (!form.value.name?.trim()) newErrors.name = "El nombre es obligatorio";

  if (!form.value.email?.trim()) newErrors.email = "El correo es obligatorio";
  else if (!/^\S+@\S+\.\S+$/.test(form.value.email)) newErrors.email = "Correo inválido";

  if (!form.value.city?.trim()) newErrors.city = "La ciudad es obligatoria";

  if (!form.value.position?.trim()) newErrors.position = "El cargo es obligatorio";

  if (!form.value.phone?.trim()) newErrors.phone = "El teléfono es obligatorio";

  if (!form.value.department?.trim()) newErrors.department = "El departamento es obligatorio";

  if (!form.value.hireDate) newErrors.hireDate = "La fecha de ingreso es obligatoria";
  else if (new Date(form.value.hireDate) > new Date())
    newErrors.hireDate = "La fecha no puede ser futura";

  if (!form.value.baseSalary || form.value.baseSalary <= 0)
    newErrors.baseSalary = "El salario debe ser mayor a cero";

  errors.value = newErrors;

  return Object.keys(newErrors).length === 0;
};

/**
 * Ejecuta la validación y, si es correcta,
 * envía la información al componente padre.
 */
const handleSave = () => {
  if (!validate()) return;

  emit("save", { ...form.value });
};
</script>

<style scoped>
/* Fondo semitransparente del modal */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
}

/* Contenedor principal del modal */
.modal-box {
  background: #fff;
  border-radius: 12px;
  width: 480px;
  max-width: 100%;
  max-height: 90vh;
  overflow-y: auto;
  padding: 20px;
}

/* Encabezado */
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

/* Botón de cierre */
.close-btn {
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 16px;
  color: #9ca3af;
}

/* Contenedor del formulario */
.modal-body {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

/* Distribución de campos en dos columnas */
.field-row {
  display: flex;
  gap: 12px;
}

.field-row .field {
  flex: 1;
}

/* Grupo de cada campo */
.field {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

/* Etiquetas */
.field-label {
  font-size: 12px;
  color: #6b7280;
}

/* Campos de entrada */
.form-input {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 10px 12px;
  font-size: 14px;
  outline: none;
  width: 100%;
  box-sizing: border-box;
  background: #fff;
}

/* Campo enfocado */
.form-input:focus {
  border-color: #4f46e5;
}

/* Mensajes de validación */
.field-error {
  font-size: 12px;
  color: #dc2626;
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

/* Efecto hover */
.save-btn:hover:not(:disabled) {
  background: #4338ca;
}
</style>
