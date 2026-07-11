<template>
  <!--
    Pie de información del empleado.
    Muestra datos adicionales como fecha de ingreso, teléfono,
    departamento y salario base.
  -->
  <div class="info-footer">
    <!-- Fecha de ingreso -->
    <div class="info-item">
      <span class="icon">📅</span>

      <div>
        <p class="label">Fecha de ingreso</p>
        <p class="value">{{ formattedDate }}</p>
      </div>
    </div>

    <!-- Teléfono -->
    <div class="info-item">
      <span class="icon">📞</span>

      <div>
        <p class="label">Teléfono</p>
        <p class="value">
          {{ employee.phone || "—" }}
        </p>
      </div>
    </div>

    <!-- Departamento -->
    <div class="info-item">
      <span class="icon">🏢</span>

      <div>
        <p class="label">Departamento</p>
        <p class="value">
          {{ employee.department || "—" }}
        </p>
      </div>
    </div>

    <!-- Salario base -->
    <div class="info-item">
      <span class="icon">💲</span>

      <div>
        <p class="label">Salario Base</p>
        <p class="value">{{ formattedSalary }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";

/**
 * Propiedades recibidas desde el componente padre.
 *
 * employee:
 * Contiene la información del empleado que será mostrada.
 */
const props = defineProps({
  employee: {
    type: Object,
    required: true,
  },
});

/**
 * Formatea la fecha de ingreso del empleado
 * utilizando el formato colombiano (dd/mm/yyyy).
 *
 * Si la fecha no existe o es inválida,
 * retorna un guion como valor por defecto.
 *
 * @returns {string}
 */
const formattedDate = computed(() => {
  if (!props.employee.hireDate) return "—";

  const date = new Date(props.employee.hireDate);

  if (isNaN(date)) return "—";

  return date.toLocaleDateString("es-CO", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
});

/**
 * Formatea el salario base utilizando
 * separadores de miles del formato colombiano.
 *
 * Si el salario no existe,
 * retorna un guion.
 *
 * @returns {string}
 */
const formattedSalary = computed(() => {
  if (props.employee.baseSalary == null) return "—";

  return `$${props.employee.baseSalary.toLocaleString("es-CO")}`;
});
</script>

<style scoped>
/* Contenedor principal de la información adicional */
.info-footer {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 20px 24px;
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-top: 20px;
  flex-wrap: wrap;
}

/* Cada bloque de información */
.info-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

/* Icono representativo */
.icon {
  font-size: 18px;
}

/* Etiqueta descriptiva */
.label {
  margin: 0;
  font-size: 12px;
  color: #9ca3af;
}

/* Valor mostrado */
.value {
  margin: 0;
  font-size: 14px;
  font-weight: 600;
  color: #111827;
}
</style>
