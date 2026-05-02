<template>
	<div class="main-content" :style='{"padding":"50px 30px 30px"}'>
		<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 20px"}' :inline="true" :model="searchForm">
			<el-row :style='{"padding":"30px 20px 10px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","flexWrap":"wrap","background":"#fff","borderWidth":"4px 0 0 0","display":"flex","width":"100%","position":"relative","borderStyle":"solid"}' >
				<div :style='{"margin":"0 1% 20px 0","display":"flex"}'>
					<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}'>捐赠编号</label>
					<el-input v-model="searchForm.juanzengbianhao" placeholder="捐赠编号" @keydown.enter.native="search" clearable></el-input>
				</div>
				<el-button class="search" type="success" @click="search" :loading="loading">
					查询
				</el-button>
				<el-button class="btn18" type="success" @click="reset">
					重置
				</el-button>
			</el-row>
		</el-form>

		<div v-if="data" :style='{"padding":"20px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","background":"#fff","borderWidth":"4px 0 0","width":"100%","borderStyle":"solid"}'>
			<el-alert v-for="(w,idx) in (data.warnings||[])" :key="idx" :title="w" type="warning" :closable="false" :style='{"margin":"0 0 12px 0"}'></el-alert>

			<el-card shadow="never" :style='{"margin":"0 0 20px 0"}'>
				<div slot="header">
					<span>捐赠信息</span>
				</div>
				<el-row :gutter="20">
					<el-col :span="8">捐赠编号：{{ data.juanzengwuzi && data.juanzengwuzi.juanzengbianhao }}</el-col>
					<el-col :span="8">物资名称：{{ data.juanzengwuzi && data.juanzengwuzi.wuzimingcheng }}</el-col>
					<el-col :span="8">物资种类：{{ data.juanzengwuzi && data.juanzengwuzi.wuzizhonglei }}</el-col>
				</el-row>
				<el-row :gutter="20" :style='{"marginTop":"10px"}'>
					<el-col :span="8">数量：{{ data.juanzengwuzi && data.juanzengwuzi.wuzishuliang }}</el-col>
					<el-col :span="8">验收状态：{{ data.juanzengwuzi && data.juanzengwuzi.yanshouzhuangtai }}</el-col>
					<el-col :span="8">捐赠时间：{{ data.juanzengwuzi && data.juanzengwuzi.juanzengshijian }}</el-col>
				</el-row>
			</el-card>

			<el-card shadow="never" :style='{"margin":"0 0 20px 0"}'>
				<div slot="header">
					<span>验收记录</span>
				</div>
				<el-table :data="data.yanshoujiluList || []" border style="width:100%">
					<el-table-column type="index" label="序号" width="60"></el-table-column>
					<el-table-column prop="yanshoujieguo" label="验收结果"></el-table-column>
					<el-table-column prop="yanshouren" label="验收人" width="120"></el-table-column>
					<el-table-column prop="yanshoushijian" label="验收时间" width="180"></el-table-column>
					<el-table-column prop="yanshoujilu" label="验收记录"></el-table-column>
				</el-table>
			</el-card>

			<el-card shadow="never">
				<div slot="header">
					<span>申领分支</span>
				</div>
				<el-empty v-if="!(data.fenZhiList && data.fenZhiList.length)" description="暂无申领记录"></el-empty>
				<el-collapse v-else v-model="activeNames">
					<el-collapse-item v-for="(fz,idx) in data.fenZhiList" :key="idx" :name="String(idx)">
						<template slot="title">
							<span>申领编号：{{ fz.wuzishenling && fz.wuzishenling.shenlingbianhao }}</span>
							<span :style='{"marginLeft":"16px","color":"#999"}'>机构：{{ fz.wuzishenling && fz.wuzishenling.jigoumingcheng }}</span>
						</template>

						<el-alert v-for="(w,i2) in (fz.warnings||[])" :key="idx+'-'+i2" :title="w" type="info" :closable="false" :style='{\"margin\":\"0 0 12px 0\"}'></el-alert>

						<el-card shadow="never" :style='{"margin":"0 0 16px 0"}'>
							<div slot="header">
								<span>申领</span>
							</div>
							<el-row :gutter="20">
								<el-col :span="8">物资名称：{{ fz.wuzishenling && fz.wuzishenling.wuzimingcheng }}</el-col>
								<el-col :span="8">申领数量：{{ fz.wuzishenling && fz.wuzishenling.shenlingshuliang }}</el-col>
								<el-col :span="8">申领时间：{{ fz.wuzishenling && fz.wuzishenling.shenlingshijian }}</el-col>
							</el-row>
							<el-row :gutter="20" :style='{"marginTop":"10px"}'>
								<el-col :span="8">出库状态：{{ fz.wuzishenling && fz.wuzishenling.chukuzhuangtai }}</el-col>
								<el-col :span="8">区域：{{ fz.wuzishenling && fz.wuzishenling.quyu }}</el-col>
								<el-col :span="8">捐赠编号：{{ fz.wuzishenling && fz.wuzishenling.juanzengbianhao }}</el-col>
							</el-row>
						</el-card>

						<el-card shadow="never" :style='{"margin":"0 0 16px 0"}'>
							<div slot="header">
								<span>出库</span>
							</div>
							<el-table :data="fz.chukufenboList || []" border style="width:100%">
								<el-table-column type="index" label="序号" width="60"></el-table-column>
								<el-table-column prop="chukushijian" label="出库时间" width="180"></el-table-column>
								<el-table-column prop="wuzishuliang" label="出库数量" width="100"></el-table-column>
								<el-table-column prop="wuliuzhuangtai" label="物流状态" width="120"></el-table-column>
								<el-table-column prop="chukudan" label="出库单"></el-table-column>
							</el-table>
						</el-card>

						<el-card shadow="never" :style='{"margin":"0 0 16px 0"}'>
							<div slot="header">
								<span>接收</span>
							</div>
							<el-table :data="fz.jieshouxinxiList || []" border style="width:100%">
								<el-table-column type="index" label="序号" width="60"></el-table-column>
								<el-table-column prop="qianshoushijian" label="签收时间" width="180"></el-table-column>
								<el-table-column prop="wuzishuliang" label="签收数量" width="100"></el-table-column>
								<el-table-column prop="chukudan" label="出库单"></el-table-column>
							</el-table>
						</el-card>

						<el-card shadow="never" :style='{"margin":"0 0 16px 0"}'>
							<div slot="header">
								<span>使用反馈</span>
							</div>
							<el-table :data="fz.shiyongfankuiList || []" border style="width:100%">
								<el-table-column type="index" label="序号" width="60"></el-table-column>
								<el-table-column prop="fankuishijian" label="反馈时间" width="180"></el-table-column>
								<el-table-column prop="shiyongrenshu" label="使用人数" width="100"></el-table-column>
								<el-table-column prop="shiyongxiaoguo" label="使用效果"></el-table-column>
								<el-table-column prop="jutiyongtu" label="具体用途"></el-table-column>
							</el-table>
						</el-card>

						<el-card shadow="never">
							<div slot="header">
								<span>异议反馈</span>
							</div>
							<el-table :data="fz.yiyifankuiList || []" border style="width:100%">
								<el-table-column type="index" label="序号" width="60"></el-table-column>
								<el-table-column prop="tijiaoshijian" label="提交时间" width="180"></el-table-column>
								<el-table-column prop="yiyifankui" label="异议内容"></el-table-column>
								<el-table-column prop="sfsh" label="审核状态" width="100"></el-table-column>
								<el-table-column prop="shhf" label="审核回复"></el-table-column>
							</el-table>
						</el-card>
					</el-collapse-item>
				</el-collapse>
			</el-card>
		</div>
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
			data: null,
			activeNames: []
		}
	},
	methods: {
		reset() {
			this.searchForm.juanzengbianhao = ''
			this.data = null
			this.activeNames = []
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
					if (this.data && this.data.fenZhiList && this.data.fenZhiList.length) {
						this.activeNames = ['0']
					}
				} else {
					this.data = null
					this.$message.error(data.msg)
				}
			}).finally(() => {
				this.loading = false
			})
		}
	}
}
</script>

