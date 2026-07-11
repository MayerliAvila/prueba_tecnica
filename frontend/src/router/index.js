import { createRouter, createWebHistory } from "vue-router";
import EmployeeListView from "../views/EmployeeListView.vue";
import EmployeeDetailView from "../views/EmployeeDetailView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "employees",
      component: EmployeeListView,
    },
    {
      path: "/empleados/:id",
      name: "employee-detail",
      component: EmployeeDetailView,
    },
  ],
});

export default router;
