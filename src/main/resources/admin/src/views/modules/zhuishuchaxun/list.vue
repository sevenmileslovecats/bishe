<template>
	<div class="main-content trace-page">
		<el-form class="center-form-pv query-panel" :inline="true" :model="searchForm" @submit.native.prevent>
			<div class="query-line">
				<label class="item-label">捐赠编号</label>
				<el-input
					v-model.trim="searchForm.juanzengbianhao"
					placeholder="请输入捐赠编号"
					@keydown.enter.native="search"
					clearable
				></el-input>
				<el-button class="search" type="success" :loading="loading" @click="search">
					<span class="icon iconfont icon-fangdajing01"></span>
					查询
				</el-button>
				<el-button class="reset" type="default" @click="reset">重置</el-button>
			</div>
		</el-form>

		<div v-if="data" class="trace-shell">
			<div v-if="allWarnings.length" class="warning-list">
				<el-alert
					v-for="(warning, index) in allWarnings"
					:key="index"
					:title="warning"
					type="warning"
					:closable="false"
					show-icon
				></el-alert>
			</div>

			<div class="summary-band">
				<div class="summary-title">
					<span>{{ field(data.juanzengwuzi, 'wuzimingcheng') }}</span>
					<el-tag :type="tagType(data.juanzengwuzi && data.juanzengwuzi.yanshouzhuangtai)" size="small">
						{{ field(data.juanzengwuzi, 'yanshouzhuangtai') }}
					</el-tag>
				</div>
				<div class="summary-grid">
					<div>
						<p>捐赠编号</p>
						<strong>{{ field(data.juanzengwuzi, 'juanzengbianhao') }}</strong>
					</div>
					<div>
						<p>物资种类</p>
						<strong>{{ field(data.juanzengwuzi, 'wuzizhonglei') }}</strong>
					</div>
					<div>
						<p>物资数量</p>
						<strong>{{ field(data.juanzengwuzi, 'wuzishuliang') }}</strong>
					</div>
					<div>
						<p>捐赠时间</p>
						<strong>{{ field(data.juanzengwuzi, 'juanzengshijian') }}</strong>
					</div>
				</div>
			</div>

			<section class="flow-section">
				<div class="section-head">
					<h3>主流程</h3>
					<span>捐赠记录到验收记录</span>
				</div>
				<div class="flow-row main-flow">
					<div
						v-for="(node, index) in mainNodes"
						:key="node.key"
						class="flow-node"
						:class="['state-' + node.state, { 'has-next': index < mainNodes.length - 1 }]"
					>
						<div class="node-top">
							<span class="node-index">{{ index + 1 }}</span>
							<el-tag :type="node.tagType" size="mini">{{ node.status }}</el-tag>
						</div>
						<h4>{{ node.title }}</h4>
						<div class="node-fields">
							<p v-for="item in node.fields" :key="item.label">
								<span>{{ item.label }}</span>
								<strong>{{ item.value }}</strong>
							</p>
						</div>
					</div>
				</div>
			</section>

			<section class="flow-section">
				<div class="section-head">
					<h3>申领分支</h3>
					<span>每条申领记录生成一条生命流程分支</span>
				</div>
				<el-empty v-if="!branchNodes.length" description="暂无申领分支"></el-empty>
				<div v-else class="branch-list">
					<div v-for="(branch, index) in branchNodes" :key="branch.key" class="branch-panel">
						<div class="branch-head">
							<div>
								<span class="branch-number">分支 {{ index + 1 }}</span>
								<strong>{{ branch.title }}</strong>
							</div>
							<div class="branch-meta">
								<el-tag size="small" :type="tagType(branch.claim.sfsh)">{{ field(branch.claim, 'sfsh') }}</el-tag>
								<span>{{ field(branch.claim, 'jigoumingcheng') }}</span>
								<span>{{ field(branch.claim, 'quyu') }}</span>
							</div>
						</div>

						<div v-if="branch.warnings.length" class="branch-warnings">
							<el-alert
								v-for="(warning, warningIndex) in branch.warnings"
								:key="warningIndex"
								:title="warning"
								type="info"
								:closable="false"
							></el-alert>
						</div>

						<div class="branch-flow">
							<div
								v-for="(node, nodeIndex) in branch.beforeFeedback"
								:key="node.key"
								class="flow-node compact"
								:class="['state-' + node.state, { 'has-next': nodeIndex < branch.beforeFeedback.length - 1 }]"
							>
								<div class="node-top">
									<span class="node-index">{{ nodeIndex + 1 }}</span>
									<el-tag :type="node.tagType" size="mini">{{ node.status }}</el-tag>
								</div>
								<h4>{{ node.title }}</h4>
								<div class="node-fields">
									<p v-for="item in node.fields" :key="item.label">
										<span>{{ item.label }}</span>
										<strong>{{ item.value }}</strong>
									</p>
								</div>
							</div>

							<div class="feedback-split">
								<div class="split-line"></div>
								<div
									v-for="node in branch.feedbackNodes"
									:key="node.key"
									class="flow-node compact feedback-node"
									:class="'state-' + node.state"
								>
									<div class="node-top">
										<span class="node-index">{{ node.short }}</span>
										<el-tag :type="node.tagType" size="mini">{{ node.status }}</el-tag>
									</div>
									<h4>{{ node.title }}</h4>
									<div class="node-fields">
										<p v-for="item in node.fields" :key="item.label">
											<span>{{ item.label }}</span>
											<strong>{{ item.value }}</strong>
										</p>
									</div>
								</div>
							</div>
						</div>

						<el-collapse class="detail-collapse">
							<el-collapse-item title="查看分支明细" :name="branch.key">
								<el-tabs type="border-card">
									<el-tab-pane label="出库记录">
										<el-table :data="branch.raw.chukufenboList" border>
											<el-table-column type="index" label="序号" width="60"></el-table-column>
											<el-table-column prop="chukushijian" label="出库时间" width="180"></el-table-column>
											<el-table-column prop="wuzishuliang" label="出库数量" width="100"></el-table-column>
											<el-table-column prop="wuliuzhuangtai" label="物流状态" width="120"></el-table-column>
											<el-table-column prop="chukudan" label="出库单"></el-table-column>
										</el-table>
									</el-tab-pane>
									<el-tab-pane label="接收记录">
										<el-table :data="branch.raw.jieshouxinxiList" border>
											<el-table-column type="index" label="序号" width="60"></el-table-column>
											<el-table-column prop="qianshoushijian" label="签收时间" width="180"></el-table-column>
											<el-table-column prop="wuzishuliang" label="签收数量" width="100"></el-table-column>
											<el-table-column prop="chukudan" label="出库单"></el-table-column>
										</el-table>
									</el-tab-pane>
									<el-tab-pane label="使用反馈">
										<el-table :data="branch.raw.shiyongfankuiList" border>
											<el-table-column type="index" label="序号" width="60"></el-table-column>
											<el-table-column prop="fankuishijian" label="反馈时间" width="180"></el-table-column>
											<el-table-column prop="shiyongrenshu" label="使用人数" width="100"></el-table-column>
											<el-table-column prop="shiyongxiaoguo" label="使用效果"></el-table-column>
											<el-table-column prop="jutiyongtu" label="具体用途"></el-table-column>
										</el-table>
									</el-tab-pane>
									<el-tab-pane label="异议反馈">
										<el-table :data="branch.raw.yiyifankuiList" border>
											<el-table-column type="index" label="序号" width="60"></el-table-column>
											<el-table-column prop="tijiaoshijian" label="提交时间" width="180"></el-table-column>
											<el-table-column prop="yiyifankui" label="异议内容"></el-table-column>
											<el-table-column prop="sfsh" label="审核状态" width="100"></el-table-column>
											<el-table-column prop="shhf" label="审核回复"></el-table-column>
										</el-table>
									</el-tab-pane>
								</el-tabs>
							</el-collapse-item>
						</el-collapse>
					</div>
				</div>
			</section>
		</div>

		<el-empty v-else description="请输入捐赠编号查询物资生命流程"></el-empty>
	</div>
</template>

<script>
export default {
	data() {
		return {
			searchForm: {
				juanzengbianhao: ''
			},
			loading: false,
			data: null
		}
	},
	computed: {
		allWarnings() {
			return (this.data && this.data.warnings) ? this.data.warnings : []
		},
		mainNodes() {
			if (!this.data) {
				return []
			}
			const donation = this.data.juanzengwuzi || {}
			const acceptList = this.asList(this.data.yanshoujiluList)
			const latestAccept = acceptList[acceptList.length - 1] || {}
			return [
				{
					key: 'donation',
					title: '捐赠记录',
					status: this.resolveStatus(donation, '捐赠已记录'),
					state: this.resolveState(donation, ['sfsh', 'yanshouzhuangtai']),
					tagType: this.tagType(this.resolveStatus(donation, '捐赠已记录')),
					fields: [
						{ label: '捐赠人', value: this.field(donation, 'xingming') },
						{ label: '数量', value: this.field(donation, 'wuzishuliang') },
						{ label: '审核', value: this.field(donation, 'sfsh') }
					]
				},
				{
					key: 'acceptance',
					title: '验收记录',
					status: acceptList.length ? this.resolveStatus(latestAccept, '已验收') : '暂无记录',
					state: acceptList.length ? this.resolveState(latestAccept, ['yanshoujieguo']) : 'missing',
					tagType: acceptList.length ? this.tagType(this.resolveStatus(latestAccept, '已验收')) : 'info',
					fields: [
						{ label: '记录数', value: acceptList.length },
						{ label: '验收人', value: this.field(latestAccept, 'yanshouren') },
						{ label: '验收时间', value: this.field(latestAccept, 'yanshoushijian') }
					]
				}
			]
		},
		branchNodes() {
			if (!this.data) {
				return []
			}
			return this.asList(this.data.fenZhiList).map((item, index) => {
				const claim = item.wuzishenling || {}
				const outList = this.asList(item.chukufenboList)
				const receiveList = this.asList(item.jieshouxinxiList)
				const useList = this.asList(item.shiyongfankuiList)
				const objectionList = this.asList(item.yiyifankuiList)
				const latestOut = outList[outList.length - 1] || {}
				const latestReceive = receiveList[receiveList.length - 1] || {}
				const latestUse = useList[useList.length - 1] || {}
				const latestObjection = objectionList[objectionList.length - 1] || {}
				const claimState = this.resolveState(claim, ['sfsh', 'chukuzhuangtai'])
				const outState = outList.length ? this.resolveState(latestOut, ['wuliuzhuangtai']) : 'missing'
				const receiveState = receiveList.length ? 'done' : 'missing'
				const useState = useList.length ? 'done' : 'missing'
				const objectionState = objectionList.length ? this.resolveObjectionState(latestObjection) : 'missing'

				return {
					key: 'branch-' + index,
					title: this.field(claim, 'shenlingbianhao'),
					claim,
					warnings: this.asList(item.warnings),
					raw: {
						chukufenboList: outList,
						jieshouxinxiList: receiveList,
						shiyongfankuiList: useList,
						yiyifankuiList: objectionList
					},
					beforeFeedback: [
						{
							key: 'claim-' + index,
							title: '物资申领',
							status: this.resolveStatus(claim, '已申领'),
							state: claimState,
							tagType: this.tagType(this.resolveStatus(claim, '已申领')),
							fields: [
								{ label: '申领数量', value: this.field(claim, 'shenlingshuliang') },
								{ label: '申领时间', value: this.field(claim, 'shenlingshijian') },
								{ label: '出库状态', value: this.field(claim, 'chukuzhuangtai') }
							]
						},
						{
							key: 'out-' + index,
							title: '出库记录',
							status: outList.length ? this.resolveStatus(latestOut, '已出库') : '暂无记录',
							state: outState,
							tagType: outList.length ? this.tagType(this.resolveStatus(latestOut, '已出库')) : 'info',
							fields: [
								{ label: '记录数', value: outList.length },
								{ label: '出库数量', value: this.field(latestOut, 'wuzishuliang') },
								{ label: '物流状态', value: this.field(latestOut, 'wuliuzhuangtai') }
							]
						},
						{
							key: 'receive-' + index,
							title: '接收记录',
							status: receiveList.length ? '已接收' : '暂无记录',
							state: receiveState,
							tagType: receiveList.length ? 'success' : 'info',
							fields: [
								{ label: '记录数', value: receiveList.length },
								{ label: '签收数量', value: this.field(latestReceive, 'wuzishuliang') },
								{ label: '签收时间', value: this.field(latestReceive, 'qianshoushijian') }
							]
						}
					],
					feedbackNodes: [
						{
							key: 'use-' + index,
							short: '用',
							title: '使用反馈',
							status: useList.length ? '已反馈' : '暂无反馈',
							state: useState,
							tagType: useList.length ? 'success' : 'info',
							fields: [
								{ label: '反馈数', value: useList.length },
								{ label: '使用人数', value: this.field(latestUse, 'shiyongrenshu') },
								{ label: '反馈时间', value: this.field(latestUse, 'fankuishijian') }
							]
						},
						{
							key: 'objection-' + index,
							short: '异',
							title: '异议反馈',
							status: objectionList.length ? this.resolveStatus(latestObjection, '存在异议') : '暂无异议',
							state: objectionState,
							tagType: objectionList.length ? (objectionState === 'pending' ? 'warning' : 'danger') : 'info',
							fields: [
								{ label: '异议数', value: objectionList.length },
								{ label: '审核状态', value: this.field(latestObjection, 'sfsh') },
								{ label: '提交时间', value: this.field(latestObjection, 'tijiaoshijian') }
							]
						}
					]
				}
			})
		}
	},
	methods: {
		reset() {
			this.searchForm.juanzengbianhao = ''
			this.data = null
		},
		search() {
			if (!this.searchForm.juanzengbianhao) {
				this.$message.error('请输入捐赠编号')
				return
			}
			this.loading = true
			this.$http({
				url: 'zhuishuchaxun/trace',
				method: 'get',
				params: {
					juanzengbianhao: this.searchForm.juanzengbianhao
				}
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.data = data.data
				} else {
					this.data = null
					this.$message.error(data && data.msg ? data.msg : '查询失败')
				}
			}).finally(() => {
				this.loading = false
			})
		},
		asList(value) {
			return Array.isArray(value) ? value : []
		},
		field(row, key) {
			if (!row || row[key] === undefined || row[key] === null || row[key] === '') {
				return '无'
			}
			return row[key]
		},
		resolveStatus(row, fallback) {
			if (!row) {
				return fallback
			}
			const keys = ['sfsh', 'yanshouzhuangtai', 'yanshoujieguo', 'chukuzhuangtai', 'wuliuzhuangtai']
			for (let i = 0; i < keys.length; i++) {
				if (row[keys[i]]) {
					return row[keys[i]]
				}
			}
			return fallback
		},
		resolveState(row, keys) {
			if (!row) {
				return 'missing'
			}
			const text = keys.map(key => row[key]).filter(Boolean).join(',')
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
		},
		resolveObjectionState(row) {
			if (!row) {
				return 'missing'
			}
			if (row.sfsh === '是') {
				return 'danger'
			}
			if (row.sfsh === '否') {
				return 'pending'
			}
			return 'danger'
		},
		tagType(status) {
			const text = status || ''
			if (/异议|异常|失败|驳回|拒绝|不通过|否/.test(text)) {
				return 'danger'
			}
			if (/待|未|暂无/.test(text)) {
				return 'warning'
			}
			return 'success'
		}
	}
}
</script>

<style lang="scss" scoped>
.trace-page {
	padding: 50px 30px 30px;
}

.query-panel {
	width: 100%;
	padding: 30px 20px 10px;
	margin: 0 0 20px;
	border: solid #ff9164;
	border-width: 4px 0 0;
	border-radius: 8px;
	background: #fff;
}

.query-line {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	gap: 14px;
	margin-bottom: 10px;
}

.item-label {
	white-space: nowrap;
	color: #666;
	font-size: 16px;
	font-weight: 500;
}

.center-form-pv .el-input {
	width: 280px;
}

.center-form-pv .el-input /deep/ .el-input__inner {
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 0 12px;
	color: #333;
	font-size: 16px;
	height: 36px;
}

.center-form-pv .search {
	border: 0;
	border-radius: 4px;
	padding: 0 18px 0 15px;
	color: #fff;
	background: #ff7d55;
	font-size: 16px;
	height: 36px;
}

.center-form-pv .reset {
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 0 18px;
	color: #666;
	background: #fff;
	font-size: 16px;
	height: 36px;
}

.trace-shell {
	padding: 20px;
	border: solid #ff9164;
	border-width: 4px 0 0;
	border-radius: 8px;
	background: #fff;
}

.warning-list,
.branch-warnings {
	display: grid;
	gap: 10px;
	margin-bottom: 16px;
}

.summary-band {
	padding: 18px 20px;
	margin-bottom: 18px;
	border: 1px solid #eee;
	border-radius: 8px;
	background: #fbfcff;
}

.summary-title {
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 12px;
	margin-bottom: 16px;
	color: #27364a;
	font-size: 20px;
	font-weight: 700;
}

.summary-grid {
	display: grid;
	grid-template-columns: repeat(4, minmax(130px, 1fr));
	gap: 12px;
}

.summary-grid div {
	padding: 12px;
	border-radius: 6px;
	background: #fff;
}

.summary-grid p,
.node-fields p {
	margin: 0;
}

.summary-grid p,
.node-fields span,
.section-head span,
.branch-meta {
	color: #7a8391;
	font-size: 13px;
}

.summary-grid strong,
.node-fields strong {
	display: block;
	margin-top: 6px;
	color: #2f3a4a;
	font-size: 15px;
	font-weight: 600;
	word-break: break-all;
}

.flow-section {
	margin-top: 18px;
}

.section-head {
	display: flex;
	align-items: baseline;
	gap: 12px;
	margin-bottom: 14px;
}

.section-head h3 {
	margin: 0;
	color: #27364a;
	font-size: 18px;
}

.flow-row,
.branch-flow {
	display: flex;
	align-items: stretch;
	gap: 28px;
	overflow-x: auto;
	padding: 6px 4px 16px;
}

.flow-node {
	position: relative;
	flex: 0 0 260px;
	min-height: 160px;
	padding: 16px;
	border: 1px solid #d7dee8;
	border-radius: 8px;
	background: #fff;
	box-shadow: 0 8px 22px rgba(39, 54, 74, 0.06);
}

.flow-node.compact {
	flex-basis: 230px;
	min-height: 170px;
}

.flow-node.has-next::after {
	content: "";
	position: absolute;
	top: 50%;
	right: -28px;
	width: 28px;
	height: 2px;
	background: #cbd5e1;
}

.flow-node.has-next::before {
	content: "";
	position: absolute;
	top: calc(50% - 5px);
	right: -30px;
	border: 6px solid transparent;
	border-left-color: #cbd5e1;
}

.node-top {
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 10px;
}

.node-index {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	width: 28px;
	height: 28px;
	border-radius: 50%;
	background: #edf2f7;
	color: #4a5568;
	font-weight: 700;
}

.flow-node h4 {
	margin: 14px 0 12px;
	color: #27364a;
	font-size: 17px;
}

.node-fields {
	display: grid;
	gap: 8px;
}

.state-done {
	border-color: #67c23a;
	background: #fbfff8;
}

.state-done .node-index {
	background: #e6f7df;
	color: #2f8f1f;
}

.state-pending {
	border-color: #e6a23c;
	background: #fffaf0;
}

.state-pending .node-index {
	background: #fdf0d4;
	color: #b56a00;
}

.state-danger {
	border-color: #f56c6c;
	background: #fff7f7;
}

.state-danger .node-index {
	background: #fde2e2;
	color: #c73939;
}

.state-missing {
	border-color: #cbd5e1;
	background: #f8fafc;
}

.state-missing .node-index {
	background: #e2e8f0;
	color: #64748b;
}

.branch-list {
	display: grid;
	gap: 18px;
}

.branch-panel {
	padding: 18px;
	border: 1px solid #eee;
	border-radius: 8px;
	background: #fff;
}

.branch-head {
	display: flex;
	align-items: center;
	justify-content: space-between;
	gap: 16px;
	margin-bottom: 16px;
}

.branch-head strong {
	color: #27364a;
	font-size: 17px;
}

.branch-number {
	display: inline-block;
	margin-right: 10px;
	padding: 4px 8px;
	border-radius: 4px;
	background: #eaf4ff;
	color: #3179c3;
	font-weight: 600;
}

.branch-meta {
	display: flex;
	align-items: center;
	gap: 10px;
	flex-wrap: wrap;
	justify-content: flex-end;
}

.feedback-split {
	position: relative;
	display: grid;
	grid-template-columns: repeat(2, 230px);
	gap: 14px;
	padding-left: 8px;
}

.split-line {
	position: absolute;
	top: 50%;
	left: -28px;
	width: 28px;
	height: 2px;
	background: #cbd5e1;
}

.feedback-node {
	min-height: 170px;
}

.detail-collapse {
	margin-top: 6px;
}

.detail-collapse /deep/ .el-collapse-item__header {
	color: #3179c3;
	font-weight: 600;
}

.detail-collapse /deep/ .el-tabs--border-card {
	box-shadow: none;
}

.el-table /deep/ .el-table__header-wrapper thead tr th {
	background: #fef6f0;
	color: #7a6a5a;
}

@media (max-width: 900px) {
	.trace-page {
		padding: 24px 12px;
	}

	.summary-grid {
		grid-template-columns: repeat(2, minmax(130px, 1fr));
	}

	.branch-head {
		align-items: flex-start;
		flex-direction: column;
	}

	.branch-meta {
		justify-content: flex-start;
	}
}

@media (max-width: 560px) {
	.center-form-pv .el-input {
		width: 100%;
	}

	.query-line {
		align-items: stretch;
		flex-direction: column;
	}

	.summary-grid {
		grid-template-columns: 1fr;
	}

	.feedback-split {
		grid-template-columns: 230px;
	}
}
</style>
