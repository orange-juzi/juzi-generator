export default [
  {
    path: '/user',
    layout: false,
    routes: [
      { name: '登录', path: '/user/login', component: './User/Login' },
      { name: '注册', path: '/user/register', component: './User/Register' },
    ],
  },
  { path: '/', icon: 'home', component: './Index', name: "主页" },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/user' },
      { icon: 'user', path: '/admin/user', name: '用户管理', component: './Admin/User' },
      {
        icon: 'tools',
        path: '/admin/generator',
        name: '生成器管理',
        component: './Admin/Generator',
      },
    ],
  },
  { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
