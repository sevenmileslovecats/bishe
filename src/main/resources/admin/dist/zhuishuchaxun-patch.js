(function () {
	function getVueFromEl(el) {
		return el && el.__vue__ ? el.__vue__ : null
	}
	function findAsideVm() {
		var el = document.querySelector('.menu-preview')
		var vm = getVueFromEl(el)
		if (vm && vm.menuList && vm.menuList.backMenu) {
			return vm
		}
		var app = document.querySelector('#app')
		var root = getVueFromEl(app)
		if (root) {
			var stack = [root]
			var guard = 0
			while (stack.length && guard < 5000) {
				guard++
				var cur = stack.shift()
				if (cur && cur.menuList && cur.menuList.backMenu) {
					return cur
				}
				var kids = cur && cur.$children ? cur.$children : []
				for (var i = 0; i < kids.length; i++) {
					stack.push(kids[i])
				}
			}
		}
		var nodes = document.querySelectorAll('*')
		for (var j = 0; j < nodes.length && j < 8000; j++) {
			var v = getVueFromEl(nodes[j])
			if (v && v.menuList && v.menuList.backMenu) {
				return v
			}
		}
		return null
	}
	function ensureMenu() {
		var vm = findAsideVm()
		if (!vm || !vm.menuList || !vm.menuList.backMenu) {
			return false
		}
		if (vm.menuList.roleName !== '管理员') {
			return true
		}
		var backMenu = vm.menuList.backMenu
		for (var i = 0; i < backMenu.length; i++) {
			var child = backMenu[i].child || []
			for (var k = 0; k < child.length; k++) {
				if (child[k].tableName === 'zhuishuchaxun') {
					return true
				}
			}
		}
		backMenu.push({
			menu: '追溯查询管理',
			child: [{
				allButtons: ['查看'],
				appFrontIcon: 'cuIcon-search',
				buttons: ['查看'],
				menu: '追溯查询',
				menuJump: '列表',
				tableName: 'zhuishuchaxun'
			}]
		})
		if (vm.$forceUpdate) {
			vm.$forceUpdate()
		}
		return true
	}
	function ensureRoute() {
		var vm = findAsideVm()
		if (!vm || !vm.$router || !vm.$router.addRoutes) {
			return false
		}
		var router = vm.$router
		var routes = (router.options && router.options.routes) ? router.options.routes : []
		for (var i = 0; i < routes.length; i++) {
			if (routes[i].path === '/zhuishuchaxun') {
				return true
			}
		}
		var Comp = {
			data: function () {
				return { juanzengbianhao: '', loading: false, data: null, err: '' }
			},
			methods: {
				search: function () {
					var self = this
					if (!self.juanzengbianhao) {
						self.err = '请输入捐赠编号'
						return
					}
					self.err = ''
					self.loading = true
					self.$http({
						url: 'zhuishuchaxun/trace',
						method: 'get',
						params: { juanzengbianhao: self.juanzengbianhao }
					}).then(function (resp) {
						var d = resp && resp.data ? resp.data : null
						if (d && d.code === 0) {
							self.data = d.data
						} else {
							self.data = null
							self.err = d && d.msg ? d.msg : '查询失败'
						}
					}).finally(function () {
						self.loading = false
					})
				}
			},
			render: function (h) {
				var self = this
				return h('div', { class: 'main-content', style: { padding: '50px 30px 30px' } }, [
					h('div', { style: { background: '#fff', borderRadius: '10px', borderStyle: 'solid', borderWidth: '4px 0 0 0', borderColor: '#ff9164', padding: '20px', marginBottom: '20px' } }, [
						h('div', { style: { display: 'flex', alignItems: 'center', gap: '12px', flexWrap: 'wrap' } }, [
							h('span', { style: { fontWeight: '500', color: '#666' } }, '捐赠编号'),
							h('input', {
								style: { height: '36px', lineHeight: '36px', padding: '0 10px', border: '1px solid #dcdfe6', borderRadius: '4px', minWidth: '240px' },
								domProps: { value: self.juanzengbianhao },
								on: {
									input: function (e) { self.juanzengbianhao = e.target.value },
									keydown: function (e) { if (e.keyCode === 13) { self.search() } }
								}
							}),
							h('button', {
								style: { height: '36px', padding: '0 16px', borderRadius: '4px', border: '0', background: '#67c23a', color: '#fff', cursor: 'pointer' },
								attrs: { disabled: self.loading ? 'disabled' : null },
								on: { click: function () { self.search() } }
							}, self.loading ? '查询中...' : '查询')
						]),
						self.err ? h('div', { style: { color: '#f56c6c', marginTop: '12px' } }, self.err) : null
					]),
					self.data ? h('div', { style: { background: '#fff', borderRadius: '10px', borderStyle: 'solid', borderWidth: '4px 0 0 0', borderColor: '#ff9164', padding: '20px' } }, [
						h('pre', { style: { whiteSpace: 'pre-wrap', wordBreak: 'break-all', margin: 0 } }, JSON.stringify(self.data, null, 2))
					]) : null
				])
			}
		}
		try {
			router.addRoutes([{ path: '/zhuishuchaxun', component: Comp }])
			if (router.options && router.options.routes) {
				router.options.routes.push({ path: '/zhuishuchaxun', component: Comp })
			}
			if (vm.$forceUpdate) {
				vm.$forceUpdate()
			}
			return true
		} catch (e) {
			return false
		}
	}
	function ensureDomMenu() {
		var menuEl = document.querySelector('.menu-preview .el-menu-vertical-2')
		if (!menuEl) {
			return false
		}
		var existed = menuEl.querySelector('[data-zhuishuchaxun="1"]')
		if (existed) {
			return true
		}
		var li = document.createElement('li')
		li.className = 'el-menu-item other'
		li.setAttribute('data-zhuishuchaxun', '1')
		li.setAttribute('role', 'menuitem')
		li.style.cssText = 'cursor:pointer;'
		li.addEventListener('click', function () {
			if (location.hash.indexOf('#/zhuishuchaxun') === -1) {
				location.hash = '#/zhuishuchaxun'
			}
		})
		li.textContent = '追溯查询'
		menuEl.appendChild(li)
		return true
	}
	var tries = 0
	var timer = setInterval(function () {
		tries++
		ensureMenu()
		ensureRoute()
		ensureDomMenu()
		if (tries > 60) {
			clearInterval(timer)
		}
	}, 500)
})()
