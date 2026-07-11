<template>
  <!--
    Contenedor global de notificaciones.
    Se renderiza directamente en el <body> mediante Teleport para evitar
    problemas de posicionamiento dentro de otros componentes.
  -->
  <Teleport to="body">
    <div class="app-toast-container">
      <!-- Animación de entrada y salida para las notificaciones -->
      <TransitionGroup name="app-toast">
        <div
          v-for="toast in toasts"
          :key="toast.id"
          class="app-toast"
          :class="`app-toast-${toast.type}`"
        >
          <!-- Icono según el tipo de notificación -->
          <span class="app-toast-icon">
            {{ iconFor(toast.type) }}
          </span>

          <!-- Mensaje de la notificación -->
          <p class="app-toast-message">
            {{ toast.message }}
          </p>

          <!-- Botón para cerrar manualmente la notificación -->
          <button class="app-toast-close" type="button" @click="removeToast(toast.id)">✕</button>
        </div>
      </TransitionGroup>
    </div>
  </Teleport>
</template>

<script setup>
import { useToast } from "@/composables/useToast";

/**
 * Composable encargado de administrar las notificaciones
 * de la aplicación.
 *
 * toasts      -> Lista reactiva de notificaciones activas.
 * removeToast -> Elimina una notificación por su identificador.
 */
const { toasts, removeToast } = useToast();

/**
 * Retorna el icono correspondiente al tipo de notificación.
 *
 * Tipos soportados:
 * - success
 * - error
 * - info
 *
 * @param {string} type Tipo de notificación.
 * @returns {string} Icono representativo.
 */
const iconFor = (type) => {
  if (type === "success") return "✓";
  if (type === "error") return "⚠";
  return "ℹ";
};
</script>

<style scoped>
/* Contenedor principal de las notificaciones */
.app-toast-container {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 2000;
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-width: 360px;
}

/* Tarjeta de cada notificación */
.app-toast {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  background: #fff;
  border-radius: 10px;
  padding: 14px 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  border-left: 4px solid transparent;
}

/* Colores según el tipo de notificación */
.app-toast-success {
  border-left-color: #16a34a;
}

.app-toast-error {
  border-left-color: #dc2626;
}

.app-toast-info {
  border-left-color: #4f46e5;
}

/* Icono de la notificación */
.app-toast-icon {
  font-size: 16px;
  line-height: 1.4;
}

/* Colores del icono según el tipo */
.app-toast-success .app-toast-icon {
  color: #16a34a;
}

.app-toast-error .app-toast-icon {
  color: #dc2626;
}

.app-toast-info .app-toast-icon {
  color: #4f46e5;
}

/* Texto del mensaje */
.app-toast-message {
  flex: 1;
  margin: 0;
  font-size: 14px;
  color: #374151;
  line-height: 1.4;
}

/* Botón para cerrar la notificación */
.app-toast-close {
  border: none;
  background: transparent;
  cursor: pointer;
  color: #9ca3af;
  font-size: 12px;
  padding: 0;
}

/* Efecto hover del botón de cierre */
.app-toast-close:hover {
  color: #374151;
}

/* Animaciones de entrada y salida */
.app-toast-enter-active,
.app-toast-leave-active {
  transition: all 0.25s ease;
}

/* Estado inicial al aparecer */
.app-toast-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

/* Estado final al desaparecer */
.app-toast-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>
