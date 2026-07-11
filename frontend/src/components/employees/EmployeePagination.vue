<template>
  <!--
    Componente de paginación.
    Permite navegar entre páginas y muestra el rango
    de registros visualizados actualmente.
  -->
  <div class="pagination">
    <!-- Página anterior -->
    <button
      class="page-arrow"
      :disabled="currentPage === 1"
      @click="$emit('update:page', currentPage - 1)"
    >
      ‹
    </button>

    <!-- Botones de cada página -->
    <button
      v-for="page in totalPages"
      :key="page"
      class="page-number"
      :class="{ active: page === currentPage }"
      @click="$emit('update:page', page)"
    >
      {{ page }}
    </button>

    <!-- Página siguiente -->
    <button
      class="page-arrow"
      :disabled="currentPage === totalPages"
      @click="$emit('update:page', currentPage + 1)"
    >
      ›
    </button>

    <!-- Información de resultados -->
    <span class="results-text">
      Mostrando {{ startItem }} - {{ endItem }} de {{ totalItems }}
    </span>
  </div>
</template>

<script setup>
import { computed } from "vue";

/**
 * Propiedades recibidas desde el componente padre.
 *
 * currentPage -> Página actual.
 * totalItems  -> Cantidad total de registros.
 * itemsPerPage -> Cantidad de registros por página.
 */
const props = defineProps({
  currentPage: {
    type: Number,
    required: true,
  },
  totalItems: {
    type: Number,
    required: true,
  },
  itemsPerPage: {
    type: Number,
    default: 5,
  },
});

/**
 * Evento emitido hacia el componente padre.
 *
 * update:page -> Solicita cambiar la página actual.
 */
defineEmits(["update:page"]);

/**
 * Calcula el número total de páginas.
 *
 * @returns {number}
 */
const totalPages = computed(() => Math.ceil(props.totalItems / props.itemsPerPage));

/**
 * Calcula el índice del primer registro mostrado
 * en la página actual.
 *
 * @returns {number}
 */
const startItem = computed(() =>
  props.totalItems === 0 ? 0 : (props.currentPage - 1) * props.itemsPerPage + 1,
);

/**
 * Calcula el índice del último registro mostrado
 * en la página actual.
 *
 * @returns {number}
 */
const endItem = computed(() => Math.min(props.currentPage * props.itemsPerPage, props.totalItems));
</script>

<style scoped>
/* Contenedor principal de la paginación */
.pagination {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 16px 20px;
  justify-content: center;
  position: relative;
}

/* Botones de navegación y números */
.page-arrow,
.page-number {
  border: none;
  background: transparent;
  border-radius: 8px;
  min-width: 32px;
  height: 32px;
  cursor: pointer;
  font-size: 14px;
  color: #374151;
}

/* Estado deshabilitado */
.page-arrow:disabled {
  color: #d1d5db;
  cursor: not-allowed;
}

/* Página actualmente seleccionada */
.page-number.active {
  background: #4f46e5;
  color: #fff;
  font-weight: 600;
}

/* Efecto hover para páginas inactivas */
.page-number:not(.active):hover {
  background: #f3f4f6;
}

/* Texto informativo del rango de resultados */
.results-text {
  position: absolute;
  right: 20px;
  font-size: 13px;
  color: #6b7280;
}
</style>
