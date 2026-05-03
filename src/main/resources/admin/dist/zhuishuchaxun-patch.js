(function () {
	var state = {
		juanzengbianhao: '',
		loading: false,
		data: null,
		error: '',
		renderedHash: ''
	}

	function getVueFromEl(el) {
		return el && el.__vue__ ? el.__vue__ : null
	}

	function findVm() {
		var app = document.querySelector('#app')
		var root = getVueFromEl(app)
		if (!root) {
			return null
		}
		var stack = [root]
		var guard = 0
		while (stack.length && guard < 5000) {
			guard++
			var cur = stack.shift()
			if (cur && cur.$http && cur.$router) {
				return cur
			}
			var kids = cur && cur.$children ? cur.$children : []
			for (var i = 0; i < kids.length; i++) {
				stack.push(kids[i])
			}
		}
		return root
	}

	function findAsideVm() {
		var el = document.querySelector('.menu-preview')
		var vm = getVueFromEl(el)
		if (vm && vm.menuList && vm.menuList.backMenu) {
			return vm
		}
		return findVm()
	}

	function traceHash() {
		var hash = decodeURIComponent(location.hash || '')
		return hash.indexOf('/zhuishuchaxun') > -1
			|| hash.indexOf('/zhuisuchaxun') > -1
			|| hash.indexOf('trace=zhuishuchaxun') > -1
			|| hash.indexOf('trace=zhuisuchaxun') > -1
	}

	function traceRouteHash() {
		var hash = decodeURIComponent(location.hash || '')
		return hash.indexOf('#/zhuishuchaxun') === 0 || hash.indexOf('#/zhuisuchaxun') === 0
	}

	function ensureTraceLayout() {
		if (!traceHash()) {
			return false
		}
		if (traceRouteHash() && !document.querySelector('.menu-preview')) {
			state.renderedHash = ''
			location.hash = '#/?trace=zhuishuchaxun'
			return false
		}
		return true
	}

	function ensureStyle() {
		if (document.getElementById('trace-query-style')) {
			return
		}
		var style = document.createElement('style')
		style.id = 'trace-query-style'
		style.textContent = ''
			+ '.trace-page{padding:50px 30px 30px}.trace-query{padding:30px 20px 20px;margin:0 0 20px;border:solid #ff9164;border-width:4px 0 0;border-radius:8px;background:#fff}.trace-query-line{display:flex;align-items:center;gap:14px;flex-wrap:wrap}.trace-label{white-space:nowrap;color:#666;font-size:16px;font-weight:500}.trace-input{width:280px;height:36px;border:1px solid #ddd;border-radius:4px;padding:0 12px;color:#333;font-size:16px;box-sizing:border-box}.trace-btn{height:36px;border:0;border-radius:4px;padding:0 18px;background:#ff7d55;color:#fff;font-size:16px;cursor:pointer}.trace-btn.secondary{border:1px solid #ddd;background:#fff;color:#666}.trace-shell{padding:20px;border:solid #ff9164;border-width:4px 0 0;border-radius:8px;background:#fff}.trace-empty{padding:42px 20px;color:#909399;text-align:center;background:#fff;border-radius:8px}.trace-warning{margin:0 0 10px;padding:10px 12px;border-radius:4px;background:#fdf6ec;color:#ad6d18}.summary-band{padding:18px 20px;margin-bottom:18px;border:1px solid #eee;border-radius:8px;background:#fbfcff}.summary-title{display:flex;align-items:center;justify-content:space-between;gap:12px;margin-bottom:16px;color:#27364a;font-size:20px;font-weight:700}.summary-grid{display:grid;grid-template-columns:repeat(4,minmax(130px,1fr));gap:12px}.summary-grid div{padding:12px;border-radius:6px;background:#fff}.summary-grid p,.node-fields p{margin:0}.summary-grid p,.node-fields span,.section-head span,.branch-meta{color:#7a8391;font-size:13px}.summary-grid strong,.node-fields strong{display:block;margin-top:6px;color:#2f3a4a;font-size:15px;font-weight:600;word-break:break-all}.tag{display:inline-flex;align-items:center;height:24px;padding:0 8px;border-radius:4px;font-size:12px}.tag.success{background:#f0f9eb;color:#67c23a}.tag.warning{background:#fdf6ec;color:#e6a23c}.tag.danger{background:#fef0f0;color:#f56c6c}.tag.info{background:#f4f4f5;color:#909399}.flow-section{margin-top:18px}.section-head{display:flex;align-items:baseline;gap:12px;margin-bottom:14px}.section-head h3{margin:0;color:#27364a;font-size:18px}.flow-row,.branch-flow{display:flex;align-items:stretch;gap:28px;overflow-x:auto;padding:6px 4px 16px}.flow-node{position:relative;flex:0 0 260px;min-height:160px;padding:16px;border:1px solid #d7dee8;border-radius:8px;background:#fff;box-shadow:0 8px 22px rgba(39,54,74,.06);box-sizing:border-box}.flow-node.compact{flex-basis:230px;min-height:170px}.flow-node.has-next:after{content:"";position:absolute;top:50%;right:-28px;width:28px;height:2px;background:#cbd5e1}.flow-node.has-next:before{content:"";position:absolute;top:calc(50% - 5px);right:-30px;border:6px solid transparent;border-left-color:#cbd5e1}.node-top{display:flex;align-items:center;justify-content:space-between;gap:10px}.node-index{display:inline-flex;align-items:center;justify-content:center;width:28px;height:28px;border-radius:50%;background:#edf2f7;color:#4a5568;font-weight:700}.flow-node h4{margin:14px 0 12px;color:#27364a;font-size:17px}.node-fields{display:grid;gap:8px}.state-done{border-color:#67c23a;background:#fbfff8}.state-done .node-index{background:#e6f7df;color:#2f8f1f}.state-pending{border-color:#e6a23c;background:#fffaf0}.state-pending .node-index{background:#fdf0d4;color:#b56a00}.state-danger{border-color:#f56c6c;background:#fff7f7}.state-danger .node-index{background:#fde2e2;color:#c73939}.state-missing{border-color:#cbd5e1;background:#f8fafc}.state-missing .node-index{background:#e2e8f0;color:#64748b}.branch-list{display:grid;gap:18px}.branch-panel{padding:18px;border:1px solid #eee;border-radius:8px;background:#fff}.branch-head{display:flex;align-items:center;justify-content:space-between;gap:16px;margin-bottom:16px}.branch-head strong{color:#27364a;font-size:17px}.branch-number{display:inline-block;margin-right:10px;padding:4px 8px;border-radius:4px;background:#eaf4ff;color:#3179c3;font-weight:600}.branch-meta{display:flex;align-items:center;gap:10px;flex-wrap:wrap;justify-content:flex-end}.feedback-split{position:relative;display:grid;grid-template-columns:repeat(2,230px);gap:14px;padding-left:8px}.split-line{position:absolute;top:50%;left:-28px;width:28px;height:2px;background:#cbd5e1}.detail-toggle{margin-top:6px;border-top:1px solid #ebeef5}.detail-toggle summary{cursor:pointer;padding:12px 0;color:#3179c3;font-weight:600}.detail-grid{display:grid;grid-template-columns:repeat(2,minmax(220px,1fr));gap:12px}.detail-box{padding:12px;border:1px solid #ebeef5;border-radius:6px}.detail-box h5{margin:0 0 8px;color:#27364a;font-size:15px}.detail-box pre{margin:0;white-space:pre-wrap;word-break:break-all;color:#555;font-size:13px}.trace-error{margin-top:12px;color:#f56c6c}@media(max-width:900px){.trace-page{padding:24px 12px}.summary-grid{grid-template-columns:repeat(2,minmax(130px,1fr))}.branch-head{align-items:flex-start;flex-direction:column}.branch-meta{justify-content:flex-start}}@media(max-width:560px){.trace-input{width:100%}.trace-query-line{align-items:stretch;flex-direction:column}.summary-grid{grid-template-columns:1fr}.feedback-split{grid-template-columns:230px}}'
		document.head.appendChild(style)
	}

	function escapeHtml(value) {
		if (value === undefined || value === null || value === '') {
			return '无'
		}
		return String(value)
			.replace(/&/g, '&amp;')
			.replace(/</g, '&lt;')
			.replace(/>/g, '&gt;')
			.replace(/"/g, '&quot;')
			.replace(/'/g, '&#39;')
	}

	function escapeAttr(value) {
		if (value === undefined || value === null) {
			return ''
		}
		return String(value)
			.replace(/&/g, '&amp;')
			.replace(/</g, '&lt;')
			.replace(/>/g, '&gt;')
			.replace(/"/g, '&quot;')
			.replace(/'/g, '&#39;')
	}

	function asList(value) {
		return Array.isArray(value) ? value : []
	}

	function field(row, key) {
		return escapeHtml(row && row[key])
	}

	function rawField(row, key) {
		return row && row[key] ? row[key] : ''
	}

	function statusOf(row, fallback) {
		var keys = ['sfsh', 'yanshouzhuangtai', 'yanshoujieguo', 'chukuzhuangtai', 'wuliuzhuangtai']
		for (var i = 0; i < keys.length; i++) {
			if (row && row[keys[i]]) {
				return row[keys[i]]
			}
		}
		return fallback
	}

	function stateOf(row, keys) {
		if (!row) {
			return 'missing'
		}
		var text = ''
		for (var i = 0; i < keys.length; i++) {
			if (row[keys[i]]) {
				text += row[keys[i]] + ','
			}
		}
		if (!text) {
			return 'done'
		}
		if (/异议|异常|失败|驳回|拒绝|不通过|否/.test(text)) {
			return 'danger'
		}
		if (/待|未/.test(text)) {
			return 'pending'
		}
		return 'done'
	}

	function objectionState(row) {
		if (!row) {
			return 'missing'
		}
		return row.sfsh === '否' ? 'pending' : 'danger'
	}

	function tagType(status) {
		var text = status || ''
		if (/异议|异常|失败|驳回|拒绝|不通过|否/.test(text)) {
			return 'danger'
		}
		if (/待|未|暂无/.test(text)) {
			return 'warning'
		}
		return 'success'
	}

	function tag(status, type) {
		return '<span class="tag ' + (type || tagType(status)) + '">' + escapeHtml(status) + '</span>'
	}

	function nodeHtml(node, index, hasNext) {
		var fields = ''
		for (var i = 0; i < node.fields.length; i++) {
			fields += '<p><span>' + escapeHtml(node.fields[i].label) + '</span><strong>' + escapeHtml(node.fields[i].value) + '</strong></p>'
		}
		return '<div class="flow-node ' + (node.compact ? 'compact ' : '') + 'state-' + node.state + (hasNext ? ' has-next' : '') + '">'
			+ '<div class="node-top"><span class="node-index">' + escapeHtml(node.short || index + 1) + '</span>' + tag(node.status, node.tagType) + '</div>'
			+ '<h4>' + escapeHtml(node.title) + '</h4><div class="node-fields">' + fields + '</div></div>'
	}

	function jsonBox(title, data) {
		return '<div class="detail-box"><h5>' + escapeHtml(title) + '</h5><pre>' + escapeHtml(JSON.stringify(data, null, 2)) + '</pre></div>'
	}

	function buildMainNodes(data) {
		var donation = data.juanzengwuzi || {}
		var acceptList = asList(data.yanshoujiluList)
		var latestAccept = acceptList[acceptList.length - 1] || {}
		return [
			{
				title: '捐赠记录',
				status: statusOf(donation, '捐赠已记录'),
				state: stateOf(donation, ['sfsh', 'yanshouzhuangtai']),
				tagType: tagType(statusOf(donation, '捐赠已记录')),
				fields: [
					{ label: '捐赠人', value: rawField(donation, 'xingming') || '无' },
					{ label: '数量', value: rawField(donation, 'wuzishuliang') || '无' },
					{ label: '审核', value: rawField(donation, 'sfsh') || '无' }
				]
			},
			{
				title: '验收记录',
				status: acceptList.length ? statusOf(latestAccept, '已验收') : '暂无记录',
				state: acceptList.length ? stateOf(latestAccept, ['yanshoujieguo']) : 'missing',
				tagType: acceptList.length ? tagType(statusOf(latestAccept, '已验收')) : 'info',
				fields: [
					{ label: '记录数', value: acceptList.length },
					{ label: '验收人', value: rawField(latestAccept, 'yanshouren') || '无' },
					{ label: '验收时间', value: rawField(latestAccept, 'yanshoushijian') || '无' }
				]
			}
		]
	}

	function branchHtml(item, index) {
		var claim = item.wuzishenling || {}
		var outList = asList(item.chukufenboList)
		var receiveList = asList(item.jieshouxinxiList)
		var useList = asList(item.shiyongfankuiList)
		var objectionList = asList(item.yiyifankuiList)
		var latestOut = outList[outList.length - 1] || {}
		var latestReceive = receiveList[receiveList.length - 1] || {}
		var latestUse = useList[useList.length - 1] || {}
		var latestObjection = objectionList[objectionList.length - 1] || {}
		var warnings = ''
		asList(item.warnings).forEach(function (warning) {
			warnings += '<div class="trace-warning">' + escapeHtml(warning) + '</div>'
		})
		var before = [
			{
				title: '物资申领',
				status: statusOf(claim, '已申领'),
				state: stateOf(claim, ['sfsh', 'chukuzhuangtai']),
				tagType: tagType(statusOf(claim, '已申领')),
				compact: true,
				fields: [
					{ label: '申领数量', value: rawField(claim, 'shenlingshuliang') || '无' },
					{ label: '申领时间', value: rawField(claim, 'shenlingshijian') || '无' },
					{ label: '出库状态', value: rawField(claim, 'chukuzhuangtai') || '无' }
				]
			},
			{
				title: '出库记录',
				status: outList.length ? statusOf(latestOut, '已出库') : '暂无记录',
				state: outList.length ? stateOf(latestOut, ['wuliuzhuangtai']) : 'missing',
				tagType: outList.length ? tagType(statusOf(latestOut, '已出库')) : 'info',
				compact: true,
				fields: [
					{ label: '记录数', value: outList.length },
					{ label: '出库数量', value: rawField(latestOut, 'wuzishuliang') || '无' },
					{ label: '物流状态', value: rawField(latestOut, 'wuliuzhuangtai') || '无' }
				]
			},
			{
				title: '接收记录',
				status: receiveList.length ? '已接收' : '暂无记录',
				state: receiveList.length ? 'done' : 'missing',
				tagType: receiveList.length ? 'success' : 'info',
				compact: true,
				fields: [
					{ label: '记录数', value: receiveList.length },
					{ label: '签收数量', value: rawField(latestReceive, 'wuzishuliang') || '无' },
					{ label: '签收时间', value: rawField(latestReceive, 'qianshoushijian') || '无' }
				]
			}
		]
		var feedback = [
			{
				title: '使用反馈',
				short: '用',
				status: useList.length ? '已反馈' : '暂无反馈',
				state: useList.length ? 'done' : 'missing',
				tagType: useList.length ? 'success' : 'info',
				compact: true,
				fields: [
					{ label: '反馈数', value: useList.length },
					{ label: '使用人数', value: rawField(latestUse, 'shiyongrenshu') || '无' },
					{ label: '反馈时间', value: rawField(latestUse, 'fankuishijian') || '无' }
				]
			},
			{
				title: '异议反馈',
				short: '异',
				status: objectionList.length ? statusOf(latestObjection, '存在异议') : '暂无异议',
				state: objectionList.length ? objectionState(latestObjection) : 'missing',
				tagType: objectionList.length ? (objectionState(latestObjection) === 'pending' ? 'warning' : 'danger') : 'info',
				compact: true,
				fields: [
					{ label: '异议数', value: objectionList.length },
					{ label: '审核状态', value: rawField(latestObjection, 'sfsh') || '无' },
					{ label: '提交时间', value: rawField(latestObjection, 'tijiaoshijian') || '无' }
				]
			}
		]
		var beforeHtml = ''
		for (var i = 0; i < before.length; i++) {
			beforeHtml += nodeHtml(before[i], i, i < before.length - 1)
		}
		var feedbackHtml = '<div class="feedback-split"><div class="split-line"></div>'
		for (var j = 0; j < feedback.length; j++) {
			feedbackHtml += nodeHtml(feedback[j], j, false)
		}
		feedbackHtml += '</div>'
		return '<div class="branch-panel">'
			+ '<div class="branch-head"><div><span class="branch-number">分支 ' + (index + 1) + '</span><strong>' + field(claim, 'shenlingbianhao') + '</strong></div>'
			+ '<div class="branch-meta">' + tag(rawField(claim, 'sfsh') || '无', tagType(rawField(claim, 'sfsh'))) + '<span>' + field(claim, 'jigoumingcheng') + '</span><span>' + field(claim, 'quyu') + '</span></div></div>'
			+ warnings
			+ '<div class="branch-flow">' + beforeHtml + feedbackHtml + '</div>'
			+ '<details class="detail-toggle"><summary>查看分支明细</summary><div class="detail-grid">'
			+ jsonBox('出库记录', outList)
			+ jsonBox('接收记录', receiveList)
			+ jsonBox('使用反馈', useList)
			+ jsonBox('异议反馈', objectionList)
			+ '</div></details></div>'
	}

	function renderData() {
		var data = state.data || {}
		var donation = data.juanzengwuzi || {}
		var warnings = ''
		asList(data.warnings).forEach(function (warning) {
			warnings += '<div class="trace-warning">' + escapeHtml(warning) + '</div>'
		})
		var mainHtml = ''
		var mainNodes = buildMainNodes(data)
		for (var i = 0; i < mainNodes.length; i++) {
			mainHtml += nodeHtml(mainNodes[i], i, i < mainNodes.length - 1)
		}
		var branches = asList(data.fenZhiList)
		var branchList = ''
		for (var j = 0; j < branches.length; j++) {
			branchList += branchHtml(branches[j], j)
		}
		if (!branchList) {
			branchList = '<div class="trace-empty">暂无申领分支</div>'
		}
		return '<div class="trace-shell">' + warnings
			+ '<div class="summary-band"><div class="summary-title"><span>' + field(donation, 'wuzimingcheng') + '</span>' + tag(rawField(donation, 'yanshouzhuangtai') || '无', tagType(rawField(donation, 'yanshouzhuangtai'))) + '</div>'
			+ '<div class="summary-grid"><div><p>捐赠编号</p><strong>' + field(donation, 'juanzengbianhao') + '</strong></div><div><p>物资种类</p><strong>' + field(donation, 'wuzizhonglei') + '</strong></div><div><p>物资数量</p><strong>' + field(donation, 'wuzishuliang') + '</strong></div><div><p>捐赠时间</p><strong>' + field(donation, 'juanzengshijian') + '</strong></div></div></div>'
			+ '<section class="flow-section"><div class="section-head"><h3>主流程</h3><span>捐赠记录到验收记录</span></div><div class="flow-row">' + mainHtml + '</div></section>'
			+ '<section class="flow-section"><div class="section-head"><h3>申领分支</h3><span>每条申领记录生成一条生命流程分支</span></div><div class="branch-list">' + branchList + '</div></section></div>'
	}

	function ensureRoute() {
		return ensureTraceLayout()
	}

	function traceTarget() {
		return document.querySelector('[data-trace-page="1"]')
			|| document.querySelector('.router-view.main-content')
			|| document.querySelector('.router-view .main-content')
			|| document.querySelector('.router-view')
	}

	function renderPage(force) {
		if (!ensureTraceLayout()) {
			return false
		}
		ensureStyle()
		var target = traceTarget()
		if (!target) {
			return false
		}
		var hash = location.hash
		var inputActive = document.activeElement && document.activeElement.id === 'traceInput'
		if (!force && state.renderedHash === hash && document.getElementById('traceInput')) {
			return true
		}
		if (!force && inputActive) {
			return true
		}
		target.setAttribute('data-trace-page', '1')
		target.className = target.className.indexOf('router-view') > -1 ? 'router-view main-content trace-page' : 'main-content trace-page'
		var html = '<div class="trace-query"><div class="trace-query-line"><label class="trace-label">捐赠编号</label><input class="trace-input" id="traceInput" value="' + escapeAttr(state.juanzengbianhao) + '" placeholder="请输入捐赠编号"><button class="trace-btn" id="traceSearch">' + (state.loading ? '查询中...' : '查询') + '</button><button class="trace-btn secondary" id="traceReset">重置</button></div>'
		if (state.error) {
			html += '<div class="trace-error">' + escapeHtml(state.error) + '</div>'
		}
		html += '</div>'
		html += state.data ? renderData() : '<div class="trace-empty">请输入捐赠编号查询物资生命流程</div>'
		target.innerHTML = html
		var input = document.getElementById('traceInput')
		var search = document.getElementById('traceSearch')
		var reset = document.getElementById('traceReset')
		if (input) {
			input.oninput = function () { state.juanzengbianhao = input.value }
			input.onkeydown = function (event) {
				if (event.keyCode === 13) {
					doSearch()
				}
			}
		}
		if (search) {
			search.onclick = doSearch
			search.disabled = state.loading
		}
		if (reset) {
			reset.onclick = function () {
				state.juanzengbianhao = ''
				state.data = null
				state.error = ''
				renderPage(true)
			}
		}
		state.renderedHash = hash
		return true
	}

	function doSearch() {
		var vm = findVm()
		if (!state.juanzengbianhao) {
			state.error = '请输入捐赠编号'
			renderPage(true)
			return
		}
		state.loading = true
		state.error = ''
		renderPage(true)
		if (vm && vm.$http) {
			vm.$http({
				url: 'zhuishuchaxun/trace',
				method: 'get',
				params: { juanzengbianhao: state.juanzengbianhao }
			}).then(function (resp) {
				var data = resp && resp.data ? resp.data : null
				if (data && data.code === 0) {
					state.data = data.data
				} else {
					state.data = null
					state.error = data && data.msg ? data.msg : '查询失败'
				}
			}).catch(function () {
				state.data = null
				state.error = '查询失败'
			}).finally(function () {
				state.loading = false
				renderPage(true)
			})
			return
		}
		var base = location.pathname.indexOf('/admin/') > -1 ? location.pathname.split('/admin/')[0] : '/springboot02b8755d'
		var token = localStorage.getItem('Token') || ''
		token = token.replace(/^"/, '').replace(/"$/, '')
		fetch(base + '/zhuishuchaxun/trace?juanzengbianhao=' + encodeURIComponent(state.juanzengbianhao), {
			headers: { Token: token },
			credentials: 'same-origin'
		}).then(function (resp) {
			return resp.json()
		}).then(function (data) {
			if (data && data.code === 0) {
				state.data = data.data
			} else {
				state.data = null
				state.error = data && data.msg ? data.msg : '查询失败'
			}
		}).catch(function () {
			state.data = null
			state.error = '查询失败'
		}).finally(function () {
			state.loading = false
			renderPage(true)
		})
	}

	function ensureMenu() {
		var vm = findAsideVm()
		if (!vm || !vm.menuList || !vm.menuList.backMenu) {
			return false
		}
		if (vm.menuList.roleName && vm.menuList.roleName !== '管理员') {
			return true
		}
		var backMenu = vm.menuList.backMenu
		for (var i = 0; i < backMenu.length; i++) {
			var child = backMenu[i].child || []
			for (var k = 0; k < child.length; k++) {
				if (child[k].tableName === 'zhuishuchaxun' || child[k].tableName === 'zhuisuchaxun') {
					child[k].tableName = '?trace=zhuishuchaxun'
					if (vm.$forceUpdate) {
						vm.$forceUpdate()
					}
					return true
				}
				if (child[k].tableName === '?trace=zhuishuchaxun') {
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
				tableName: '?trace=zhuishuchaxun'
			}]
		})
		if (vm.$forceUpdate) {
			vm.$forceUpdate()
		}
		return true
	}

	function ensureDomMenu() {
		var menuEl = document.querySelector('.menu-preview .el-menu-vertical-2')
		if (!menuEl || menuEl.querySelector('[data-zhuishuchaxun="1"]')) {
			return !!menuEl
		}
		var li = document.createElement('li')
		li.className = 'el-menu-item other'
		li.setAttribute('data-zhuishuchaxun', '1')
		li.setAttribute('role', 'menuitem')
		li.style.cssText = 'cursor:pointer;'
		li.onclick = function () {
			if (location.hash.indexOf('trace=zhuishuchaxun') === -1) {
				location.hash = '#/?trace=zhuishuchaxun'
			}
			setTimeout(function () { renderPage(true) }, 80)
		}
		li.textContent = '追溯查询'
		menuEl.appendChild(li)
		return true
	}

	function tick() {
		ensureRoute()
		ensureMenu()
		ensureDomMenu()
		renderPage(false)
	}

	window.addEventListener('hashchange', function () {
		state.renderedHash = ''
		setTimeout(function () { renderPage(true) }, 120)
	})

	var tries = 0
	var timer = setInterval(function () {
		tries++
		tick()
		if (tries > 900) {
			clearInterval(timer)
		}
	}, 200)
	setTimeout(tick, 100)
})()
