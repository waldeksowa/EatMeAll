
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Login.vue') },
      { path: '/plan', component: () => import('pages/Meals.vue') },
      { path: '/dodajPosilek', component: () => import('pages/AddMeal.vue') },
      { path: '/list', component: () => import('pages/List.vue') },
      { path: '/rejestracja', component: () => import('pages/Register.vue') },
      { path: '/kato', component: () => import('pages/Member.vue') },
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
