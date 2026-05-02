import VueRouter from 'vue-router'
//引入组件
import Index from '../pages'
import Home from '../pages/home/home'
import Login from '../pages/login/login'
import Register from '../pages/register/register'
import Center from '../pages/center/center'
import Storeup from '../pages/storeup/list'
import payList from '../pages/pay'

import juanzengrenList from '../pages/juanzengren/list'
import juanzengrenDetail from '../pages/juanzengren/detail'
import juanzengrenAdd from '../pages/juanzengren/add'
import jieshoujigouList from '../pages/jieshoujigou/list'
import jieshoujigouDetail from '../pages/jieshoujigou/detail'
import jieshoujigouAdd from '../pages/jieshoujigou/add'
import wuzizhongleiList from '../pages/wuzizhonglei/list'
import wuzizhongleiDetail from '../pages/wuzizhonglei/detail'
import wuzizhongleiAdd from '../pages/wuzizhonglei/add'
import wuzixinxiList from '../pages/wuzixinxi/list'
import wuzixinxiDetail from '../pages/wuzixinxi/detail'
import wuzixinxiAdd from '../pages/wuzixinxi/add'
import juanzengwuziList from '../pages/juanzengwuzi/list'
import juanzengwuziDetail from '../pages/juanzengwuzi/detail'
import juanzengwuziAdd from '../pages/juanzengwuzi/add'
import juanzengzhengshuList from '../pages/juanzengzhengshu/list'
import juanzengzhengshuDetail from '../pages/juanzengzhengshu/detail'
import juanzengzhengshuAdd from '../pages/juanzengzhengshu/add'
import yanshoujiluList from '../pages/yanshoujilu/list'
import yanshoujiluDetail from '../pages/yanshoujilu/detail'
import yanshoujiluAdd from '../pages/yanshoujilu/add'
import wuzishenlingList from '../pages/wuzishenling/list'
import wuzishenlingDetail from '../pages/wuzishenling/detail'
import wuzishenlingAdd from '../pages/wuzishenling/add'
import chukufenboList from '../pages/chukufenbo/list'
import chukufenboDetail from '../pages/chukufenbo/detail'
import chukufenboAdd from '../pages/chukufenbo/add'
import jieshouxinxiList from '../pages/jieshouxinxi/list'
import jieshouxinxiDetail from '../pages/jieshouxinxi/detail'
import jieshouxinxiAdd from '../pages/jieshouxinxi/add'
import yiyifankuiList from '../pages/yiyifankui/list'
import yiyifankuiDetail from '../pages/yiyifankui/detail'
import yiyifankuiAdd from '../pages/yiyifankui/add'
import shiyongfankuiList from '../pages/shiyongfankui/list'
import shiyongfankuiDetail from '../pages/shiyongfankui/detail'
import shiyongfankuiAdd from '../pages/shiyongfankui/add'
import News from '../pages/news/news-list'
import NewsDetail from '../pages/news/news-detail'
import discussshiyongfankuiList from '../pages/discussshiyongfankui/list'
import discussshiyongfankuiDetail from '../pages/discussshiyongfankui/detail'
import discussshiyongfankuiAdd from '../pages/discussshiyongfankui/add'

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}

//配置路由
export default new VueRouter({
	routes:[
		{
      path: '/',
      redirect: '/index/home'
    },
		{
			path: '/index',
			component: Index,
			children:[
				{
					path: 'home',
					component: Home
				},
				{
					path: 'center',
					component: Center,
				},
				{
					path: 'pay',
					component: payList,
				},
				{
					path: 'storeup',
					component: Storeup
				},
				{
					path: 'juanzengren',
					component: juanzengrenList
				},
				{
					path: 'juanzengrenDetail',
					component: juanzengrenDetail
				},
				{
					path: 'juanzengrenAdd',
					component: juanzengrenAdd
				},
				{
					path: 'jieshoujigou',
					component: jieshoujigouList
				},
				{
					path: 'jieshoujigouDetail',
					component: jieshoujigouDetail
				},
				{
					path: 'jieshoujigouAdd',
					component: jieshoujigouAdd
				},
				{
					path: 'wuzizhonglei',
					component: wuzizhongleiList
				},
				{
					path: 'wuzizhongleiDetail',
					component: wuzizhongleiDetail
				},
				{
					path: 'wuzizhongleiAdd',
					component: wuzizhongleiAdd
				},
				{
					path: 'wuzixinxi',
					component: wuzixinxiList
				},
				{
					path: 'wuzixinxiDetail',
					component: wuzixinxiDetail
				},
				{
					path: 'wuzixinxiAdd',
					component: wuzixinxiAdd
				},
				{
					path: 'juanzengwuzi',
					component: juanzengwuziList
				},
				{
					path: 'juanzengwuziDetail',
					component: juanzengwuziDetail
				},
				{
					path: 'juanzengwuziAdd',
					component: juanzengwuziAdd
				},
				{
					path: 'juanzengzhengshu',
					component: juanzengzhengshuList
				},
				{
					path: 'juanzengzhengshuDetail',
					component: juanzengzhengshuDetail
				},
				{
					path: 'juanzengzhengshuAdd',
					component: juanzengzhengshuAdd
				},
				{
					path: 'yanshoujilu',
					component: yanshoujiluList
				},
				{
					path: 'yanshoujiluDetail',
					component: yanshoujiluDetail
				},
				{
					path: 'yanshoujiluAdd',
					component: yanshoujiluAdd
				},
				{
					path: 'wuzishenling',
					component: wuzishenlingList
				},
				{
					path: 'wuzishenlingDetail',
					component: wuzishenlingDetail
				},
				{
					path: 'wuzishenlingAdd',
					component: wuzishenlingAdd
				},
				{
					path: 'chukufenbo',
					component: chukufenboList
				},
				{
					path: 'chukufenboDetail',
					component: chukufenboDetail
				},
				{
					path: 'chukufenboAdd',
					component: chukufenboAdd
				},
				{
					path: 'jieshouxinxi',
					component: jieshouxinxiList
				},
				{
					path: 'jieshouxinxiDetail',
					component: jieshouxinxiDetail
				},
				{
					path: 'jieshouxinxiAdd',
					component: jieshouxinxiAdd
				},
				{
					path: 'yiyifankui',
					component: yiyifankuiList
				},
				{
					path: 'yiyifankuiDetail',
					component: yiyifankuiDetail
				},
				{
					path: 'yiyifankuiAdd',
					component: yiyifankuiAdd
				},
				{
					path: 'shiyongfankui',
					component: shiyongfankuiList
				},
				{
					path: 'shiyongfankuiDetail',
					component: shiyongfankuiDetail
				},
				{
					path: 'shiyongfankuiAdd',
					component: shiyongfankuiAdd
				},
				{
					path: 'news',
					component: News
				},
				{
					path: 'newsDetail',
					component: NewsDetail
				},
				{
					path: 'discussshiyongfankui',
					component: discussshiyongfankuiList
				},
				{
					path: 'discussshiyongfankuiDetail',
					component: discussshiyongfankuiDetail
				},
				{
					path: 'discussshiyongfankuiAdd',
					component: discussshiyongfankuiAdd
				},
			]
		},
		{
			path: '/login',
			component: Login
		},
		{
			path: '/register',
			component: Register
		},
	]
})
