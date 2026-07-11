<template>
  <!--
    Tarjeta que muestra la ubicación del empleado.
    Presenta la ciudad, las coordenadas geográficas y un mapa
    utilizando OpenStreetMap cuando existen coordenadas válidas.
  -->
  <div class="location-card">
    <h3>🌐 Información de ubicación (OpenStreetMap)</h3>

    <!-- Información de ubicación -->
    <div class="location-details">
      <div class="detail-item">
        <p class="label">Ciudad</p>
        <p class="value">{{ employee.city || "—" }}</p>
      </div>

      <div class="detail-item">
        <p class="label">Latitud</p>
        <p class="value">{{ employee.latitude ?? "—" }}</p>
      </div>

      <div class="detail-item">
        <p class="label">Longitud</p>
        <p class="value">{{ employee.longitude ?? "—" }}</p>
      </div>
    </div>

    <!-- Mapa cuando existen coordenadas -->
    <div v-if="hasCoords" ref="mapContainer" class="map-container"></div>

    <!-- Mensaje cuando no existen coordenadas -->
    <div v-else class="map-placeholder">Sin coordenadas registradas para este empleado</div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch, nextTick } from "vue";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

/**
 * Corrección para cargar correctamente los iconos
 * predeterminados de Leaflet cuando la aplicación
 * utiliza Vite como herramienta de construcción.
 */
import markerIcon2x from "leaflet/dist/images/marker-icon-2x.png";
import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl: markerIcon2x,
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
});

/**
 * Propiedades recibidas desde el componente padre.
 *
 * employee:
 * Contiene la información del empleado, incluyendo
 * ciudad y coordenadas geográficas.
 */
const props = defineProps({
  employee: {
    type: Object,
    required: true,
  },
});

/**
 * Referencia al contenedor HTML donde se renderizará el mapa.
 */
const mapContainer = ref(null);

/**
 * Instancia del mapa de Leaflet.
 */
let mapInstance = null;

/**
 * Indica si el empleado posee coordenadas válidas.
 *
 * @returns {boolean}
 */
const hasCoords = computed(
  () => props.employee.latitude != null && props.employee.longitude != null,
);

/**
 * Inicializa o actualiza el mapa.
 *
 * Si ya existe una instancia previa, esta se destruye
 * antes de crear una nueva para evitar duplicados.
 */
const initMap = () => {
  if (!hasCoords.value || !mapContainer.value) return;

  const lat = props.employee.latitude;
  const lng = props.employee.longitude;

  // Elimina el mapa anterior si existe.
  if (mapInstance) {
    mapInstance.remove();
  }

  // Crea el mapa centrado en las coordenadas del empleado.
  mapInstance = L.map(mapContainer.value).setView([lat, lng], 14);

  // Agrega la capa de OpenStreetMap.
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "&copy; OpenStreetMap contributors",
  }).addTo(mapInstance);

  // Agrega un marcador en la ubicación del empleado.
  L.marker([lat, lng]).addTo(mapInstance);
};

/**
 * Inicializa el mapa cuando el componente
 * termina de renderizarse.
 */
onMounted(() => {
  nextTick(initMap);
});

/**
 * Observa cambios en las coordenadas del empleado.
 *
 * Si cambian la latitud o longitud,
 * el mapa se vuelve a generar automáticamente.
 */
watch(
  () => [props.employee.latitude, props.employee.longitude],
  () => {
    nextTick(initMap);
  },
);
</script>

<style scoped>
/* Tarjeta principal */
.location-card {
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 20px 24px;
}

/* Título */
.location-card h3 {
  margin: 0 0 16px;
  font-size: 16px;
  font-weight: 700;
  color: #111827;
}

/* Información de ubicación */
.location-details {
  display: flex;
  gap: 32px;
  margin-bottom: 16px;
}

/* Etiquetas */
.label {
  margin: 0;
  font-size: 12px;
  color: #9ca3af;
}

/* Valores */
.value {
  margin: 2px 0 0;
  font-size: 14px;
  font-weight: 600;
  color: #111827;
}

/* Contenedor del mapa */
.map-container {
  height: 220px;
  border-radius: 10px;
  overflow: hidden;
  z-index: 0;
}

/* Mensaje cuando no existen coordenadas */
.map-placeholder {
  height: 220px;
  border-radius: 10px;
  background: #f9fafb;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #9ca3af;
  font-size: 14px;
}
</style>
