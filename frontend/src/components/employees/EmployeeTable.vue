<template>
  <!--
    Tabla de empleados.
    Muestra la información básica de cada empleado y permite
    visualizar el detalle mediante el botón de acciones.
  -->
  <div class="table-wrapper">
    <table class="employee-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Ciudad</th>
          <th>Cargo</th>
          <th>Correo</th>
          <th class="acciones-col">Acciones</th>
        </tr>
      </thead>

      <tbody>
        <!-- Lista de empleados -->
        <tr v-for="employee in employees" :key="employee.id">
          <td class="nombre-cell">
            {{ employee.name }}
          </td>

          <td>{{ employee.city || "—" }}</td>

          <td>{{ employee.position || "—" }}</td>

          <td>{{ employee.email || "—" }}</td>

          <td class="acciones-col">
            <!-- Abre el detalle del empleado -->
            <button class="view-btn" type="button" @click="$emit('view-employee', employee.id)">
              👁
            </button>
          </td>
        </tr>

        <!-- Estado cuando no existen empleados -->
        <tr v-if="employees.length === 0">
          <td colspan="5" class="empty-state">No hay empleados para mostrar</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
/**
 * Propiedades recibidas desde el componente padre.
 *
 * employees:
 * Lista de empleados que será mostrada en la tabla.
 */
defineProps({
  employees: {
    type: Array,
    required: true,
  },
});

/**
 * Evento emitido hacia el componente padre.
 *
 * view-employee -> Solicita visualizar el detalle
 * de un empleado específico.
 */
defineEmits(["view-employee"]);
</script>

<style scoped>
/* Contenedor de la tabla */
.table-wrapper {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  overflow: hidden;
}

/* Tabla principal */
.employee-table {
  width: 100%;
  border-collapse: collapse;
}

/* Encabezados */
.employee-table thead th {
  text-align: left;
  font-size: 13px;
  font-weight: 600;
  color: #6b7280;
  padding: 14px 20px;
  border-bottom: 1px solid #e5e7eb;
}

/* Celdas */
.employee-table tbody td {
  padding: 16px 20px;
  font-size: 14px;
  color: #374151;
  border-bottom: 1px solid #f3f4f6;
}

/* Elimina el borde de la última fila */
.employee-table tbody tr:last-child td {
  border-bottom: none;
}

/* Resalta el nombre del empleado */
.nombre-cell {
  font-weight: 600;
  color: #111827;
}

/* Columna de acciones */
.acciones-col {
  text-align: center;
  width: 100px;
}

/* Botón para visualizar el detalle */
.view-btn {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
  padding: 6px 10px;
  cursor: pointer;
}

/* Efecto hover */
.view-btn:hover {
  background: #f3f4f6;
}

/* Mensaje cuando no existen registros */
.empty-state {
  text-align: center;
  color: #9ca3af;
  padding: 30px;
}
</style>
