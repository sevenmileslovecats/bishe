	import Vue from 'vue';
//配置路由
	import VueRouter from 'vue-router'
	Vue.use(VueRouter);
//1.创建组件
	import Index from '@/views/index'
	import Home from '@/views/home'
	import Login from '@/views/login'
	import NotFound from '@/views/404'
	import UpdatePassword from '@/views/update-password'
	import pay from '@/views/pay'
	import register from '@/views/register'
	import center from '@/views/center'
	import juanzengren from '@/views/modules/juanzengren/list'
	import jieshoujigou from '@/views/modules/jieshoujigou/list'
	import wuzizhonglei from '@/views/modules/wuzizhonglei/list'
	import wuzixinxi from '@/views/modules/wuzixinxi/list'
	import juanzengwuzi from '@/views/modules/juanzengwuzi/list'
	import juanzengzhengshu from '@/views/modules/juanzengzhengshu/list'
	import yanshoujilu from '@/views/modules/yanshoujilu/list'
	import wuzishenling from '@/views/modules/wuzishenling/list'
	import chukufenbo from '@/views/modules/chukufenbo/list'
	import jieshouxinxi from '@/views/modules/jieshouxinxi/list'
	import yiyifankui from '@/views/modules/yiyifankui/list'
	import shiyongfankui from '@/views/modules/shiyongfankui/list'
	import zhuishuchaxun from '@/views/modules/zhuishuchaxun/list'
	import news from '@/views/modules/news/list'
	import syslog from '@/views/modules/syslog/list'
	import users from '@/views/modules/users/list'
	import discussshiyongfankui from '@/views/modules/discussshiyongfankui/list'
import config from '@/views/modules/config/list'

//2.配置路由   注意：名字
export const routes = [{
	path: '/',
	name: '系统首页',
	component: Index,
	children: [{
		// 这里不设置值，是把main作为默认页面
		path: '/',
		name: '系统首页',
		component: Home,
		meta: {icon:'', title:'center', affix: true}
	}, {
		path: '/updatePassword',
		name: '修改密码',
		component: UpdatePassword,
		meta: {icon:'', title:'updatePassword'}
	}, {
		path: '/pay',
		name: '支付',
		component: pay,
		meta: {icon:'', title:'pay'}
	}, {
		path: '/center',
		name: '个人信息',
		component: center,
		meta: {icon:'', title:'center'}
	}
	,{
		path: '/juanzengren',
		name: '捐赠人',
		component: juanzengren
	}
	,{
		path: '/jieshoujigou',
		name: '接收机构',
		component: jieshoujigou
	}
	,{
		path: '/wuzizhonglei',
		name: '物资种类',
		component: wuzizhonglei
	}
	,{
		path: '/wuzixinxi',
		name: '物资信息',
		component: wuzixinxi
	}
	,{
		path: '/juanzengwuzi',
		name: '捐赠物资',
		component: juanzengwuzi
	}
	,{
		path: '/juanzengzhengshu',
		name: '捐赠证书',
		component: juanzengzhengshu
	}
	,{
		path: '/yanshoujilu',
		name: '验收记录',
		component: yanshoujilu
	}
	,{
		path: '/wuzishenling',
		name: '物资申领',
		component: wuzishenling
	}
	,{
		path: '/chukufenbo',
		name: '出库分拨',
		component: chukufenbo
	}
	,{
		path: '/jieshouxinxi',
		name: '接收信息',
		component: jieshouxinxi
	}
	,{
		path: '/yiyifankui',
		name: '异议反馈',
		component: yiyifankui
	}
	,{
		path: '/shiyongfankui',
		name: '使用反馈',
		component: shiyongfankui
	}
	,{
		path: '/news',
		name: '公告信息',
		component: news
	}
	,{
		path: '/zhuishuchaxun',
		alias: '/zhuisuchaxun',
		name: '追溯查询',
		component: zhuishuchaxun
	}
	,{
		path: '/syslog',
		name: '系统日志',
		component: syslog
	}
	,{
		path: '/users',
		name: '管理员',
		component: users
	}
	,{
		path: '/discussshiyongfankui',
		name: '使用反馈评论',
		component: discussshiyongfankui
	}
	,{
		path: '/config/:type',
		name: '配置管理',
		component: config
	}
	]
	},
	{
		path: '/login',
		name: 'login',
		component: Login,
		meta: {icon:'', title:'login'}
	},
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {icon:'', title:'register'}
	},
	{
		path: '*',
		component: NotFound
	}
]
//3.实例化VueRouter  注意：名字
const router = new VueRouter({
	mode: 'hash',
	/*hash模式改为history*/
	routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
export default router;
