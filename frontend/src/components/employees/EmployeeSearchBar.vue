<template>
  <!--
    Barra de búsqueda de empleados.
    Permite filtrar la lista enviando el texto ingresado
    al componente padre.
  -->
  <div class="search-bar">
    <!-- Campo de búsqueda -->
    <div class="search-input-wrapper">
      <span class="search-icon">🔍</span>

      <input
        v-model="searchText"
        type="text"
        placeholder="Buscar empleado..."
        class="search-input"
        @input="$emit('search', searchText)"
      />
    </div>

    <!-- Botón de filtros (preparado para futuras funcionalidades) -->
    <button class="filter-btn" type="button">
      <span>⚙</span>
    </button>
  </div>
</template>

<script setup>
import { ref } from "vue";

/**
 * Evento emitido hacia el componente padre.
 *
 * search -> Envía el texto ingresado para realizar
 * el filtrado de empleados.
 */
defineEmits(["search"]);

/**
 * Texto ingresado en el campo de búsqueda.
 */
const searchText = ref("");
</script>

<style scoped>
/* Contenedor principal de la barra de búsqueda */
.search-bar {
  display: flex;
  align-items: center;
  gap: 12px;
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 12px 16px;
  margin-bottom: 20px;
}

/* Contenedor del icono y el campo de búsqueda */
.search-input-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
  flex: 1;
}

/* Icono de búsqueda */
.search-icon {
  color: #9ca3af;
  font-size: 14px;
}

/* Campo de texto */
.search-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 14px;
  color: #374151;
}

/* Texto de ayuda */
.search-input::placeholder {
  color: #9ca3af;
}

/* Botón de filtros */
.filter-btn {
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background: #fff;
  padding: 8px 12px;
  cursor: pointer;
  color: #6b7280;
}

/* Efecto hover */
.filter-btn:hover {
  background: #f9fafb;
}
</style>
