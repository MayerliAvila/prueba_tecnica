import { ref } from "vue";

/**
 * Estado global compartido de notificaciones toast.
 * Al ser definido fuera del composable, es compartido entre todas las instancias
 * que llamen a `useToast()` (patrón singleton de Vue 3).
 */
const toasts = ref([]);
let idCounter = 0;

/** Duración por defecto de los toasts en milisegundos. */
const DEFAULT_DURATION = 4000;

/**
 * Agrega un nuevo mensaje toast al estado global y lo elimina automáticamente
 * después de `duration` milisegundos.
 *
 * @param {string} message - Texto del mensaje a mostrar
 * @param {'success'|'error'|'info'} [type='success'] - Tipo de notificación
 * @param {number} [duration=4000] - Duración en milisegundos antes de auto-cerrar
 */
function addToast(message, type = "success", duration = DEFAULT_DURATION) {
  const id = ++idCounter;
  toasts.value.push({ id, message, type });

  setTimeout(() => {
    removeToast(id);
  }, duration);
}

/**
 * Elimina un toast del estado global por su ID.
 *
 * @param {number} id - Identificador del toast a eliminar
 */
function removeToast(id) {
  toasts.value = toasts.value.filter((t) => t.id !== id);
}

/**
 * Composable que provee acceso al sistema de notificaciones toast.
 *
 * @returns {{ toasts: import('vue').Ref, success: Function, error: Function, info: Function, removeToast: Function }}
 */
export function useToast() {
  return {
    toasts,
    success: (message, duration) => addToast(message, "success", duration),
    error: (message, duration) => addToast(message, "error", duration),
    info: (message, duration) => addToast(message, "info", duration),
    removeToast,
  };
}
