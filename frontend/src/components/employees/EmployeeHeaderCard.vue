<template>
  <!--
    Encabezado con la información principal del empleado.
    Muestra datos generales y permite abrir el formulario de edición.
  -->
  <div class="header-card">
    <div class="left-info">
      <!-- Avatar generado con las iniciales del empleado -->
      <div class="avatar" :style="{ backgroundColor: avatarColor }">
        {{ initials }}
      </div>

      <!-- Información básica del empleado -->
      <div class="info">
        <h2>{{ employee.name }}</h2>

        <p class="position">
          {{ employee.position || "—" }}
        </p>

        <p class="email">
          {{ employee.email || "—" }}
        </p>

        <!-- Ciudad y estado -->
        <div class="badges">
          <span class="badge city-badge"> 📍 {{ employee.city || "—" }} </span>

          <span class="badge status-badge" :class="isActive ? 'active' : 'inactive'">
            ● {{ statusLabel }}
          </span>
        </div>
      </div>
    </div>

    <!-- Solicita editar la información del empleado -->
    <button class="edit-btn" type="button" @click="$emit('edit')">✎ Editar Empleado</button>
  </div>
</template>

<script setup>
import { computed } from "vue";

/**
 * Propiedades recibidas desde el componente padre.
 *
 * employee:
 * Contiene toda la información del empleado que será mostrada.
 */
const props = defineProps({
  employee: {
    type: Object,
    required: true,
  },
});

/**
 * Evento emitido hacia el componente padre.
 *
 * edit -> Solicita abrir el formulario de edición.
 */
defineEmits(["edit"]);

/**
 * Obtiene las iniciales del empleado para mostrarlas
 * dentro del avatar.
 *
 * Si el nombre no existe, se muestran "??".
 *
 * @returns {string}
 */
const initials = computed(() => {
  if (!props.employee.name) return "??";

  return props.employee.name
    .split(" ")
    .map((n) => n[0])
    .slice(0, 2)
    .join("")
    .toUpperCase();
});

/**
 * Paleta de colores utilizada para el avatar.
 */
const colors = ["#c7d2fe", "#bbf7d0", "#fecdd3", "#fde68a", "#a5f3fc"];

/**
 * Selecciona un color para el avatar
 * según la longitud del nombre del empleado.
 *
 * @returns {string}
 */
const avatarColor = computed(() => {
  const index = (props.employee.name?.length || 0) % colors.length;
  return colors[index];
});

/**
 * Indica si el empleado se encuentra activo.
 *
 * @returns {boolean}
 */
const isActive = computed(() => props.employee.status === "ACTIVO");

/**
 * Retorna la etiqueta del estado del empleado.
 *
 * @returns {string}
 */
const statusLabel = computed(() => (isActive.value ? "Activo" : "Inactivo"));
</script>

<style scoped>
/* Tarjeta principal del encabezado */
.header-card {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
}

/* Contenedor de avatar e información */
.left-info {
  display: flex;
  gap: 16px;
}

/* Avatar con iniciales */
.avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 18px;
  color: #4338ca;
  flex-shrink: 0;
}

/* Nombre del empleado */
.info h2 {
  margin: 0 0 2px;
  font-size: 20px;
  font-weight: 700;
  color: #111827;
}

/* Cargo */
.position {
  margin: 0 0 2px;
  color: #6b7280;
  font-size: 14px;
}

/* Correo electrónico */
.email {
  margin: 0 0 10px;
  color: #6b7280;
  font-size: 14px;
}

/* Contenedor de insignias */
.badges {
  display: flex;
  gap: 8px;
}

/* Estilo base de las insignias */
.badge {
  font-size: 12px;
  padding: 4px 10px;
  border-radius: 999px;
  font-weight: 500;
}

/* Insignia de ciudad */
.city-badge {
  background: #f3f4f6;
  color: #374151;
}

/* Estado activo */
.status-badge.active {
  background: #dcfce7;
  color: #16a34a;
}

/* Estado inactivo */
.status-badge.inactive {
  background: #fee2e2;
  color: #dc2626;
}

/* Botón para editar */
.edit-btn {
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  background: #fff;
  padding: 10px 16px;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  white-space: nowrap;
}

/* Efecto hover */
.edit-btn:hover {
  background: #f9fafb;
}
</style>
