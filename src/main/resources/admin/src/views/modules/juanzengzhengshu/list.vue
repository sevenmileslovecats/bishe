  
<template>
	<div class="main-content" :style='{"padding":"50px 30px 30px"}'>
		<!-- 列表页 -->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 20px"}' :inline="true" :model="searchForm">
				<el-row :style='{"padding":"30px 20px 10px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","flexWrap":"wrap","background":"#fff","borderWidth":"4px 0 0 0","display":"flex","width":"100%","position":"relative","borderStyle":"solid"}' >
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">捐赠编号</label>
						<el-input v-model="searchForm.juanzengbianhao" placeholder="捐赠编号" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物资名称</label>
						<el-input v-model="searchForm.wuzimingcheng" placeholder="物资名称" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物资种类</label>
						<el-input v-model="searchForm.wuzizhonglei" placeholder="物资种类" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing01" :style='{"margin":"0 0px","fontSize":"16px","color":"#fff","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>

				<el-row class="actions" :style='{"width":"calc(100% + 8px)","margin":"20px 0 20px -4px","flexWrap":"wrap","display":"flex"}'>
					<el-button class="btn18" type="success" @click="refreshClick()">
						刷新
					</el-button>
					<el-button class="add" v-if="isAuth('juanzengzhengshu','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia16" :style='{"padding":"10px","margin":"0 2px","color":"#ff9365","borderRadius":"100%","background":"#ff936510","fontSize":"18px","fontWeight":"500","height":"40px"}'></span>
						添加
					</el-button>
					<el-button class="del" v-if="isAuth('juanzengzhengshu','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"padding":"10px","margin":"0 2px","color":"#ea6464","borderRadius":"100%","background":"#ea646410","fontSize":"18px","fontWeight":"500","height":"40px"}'></span>
						批量删除
					</el-button>


				</el-row>
			</el-form>
			<div :style='{"padding":"20px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","background":"#fff","borderWidth":"4px 0 0","width":"100%","borderStyle":"solid"}'>
				<div
					v-if="isAuth('juanzengzhengshu','查看')"
					class="certificate-split"
					v-loading="dataListLoading"
				>
					<div class="certificate-list-pane">
						<div v-if="!dataListLoading && dataList.length === 0" class="certificate-empty">暂无捐赠证书数据</div>
						<div
							v-for="(item,index) in dataList"
							:key="item.id"
							class="certificate-list-item"
							:class="{ active: currentRow && currentRow.id === item.id }"
							@click="selectRow(item)"
						>
							<el-checkbox
								class="certificate-check"
								:value="isSelected(item)"
								@click.native.stop
								@change="toggleSelection(item, $event)"
							></el-checkbox>
							<div class="certificate-index">{{(pageIndex - 1) * pageSize + index + 1}}</div>
							<div class="certificate-thumb" @click.stop="getImageUrl(item) && imgPreView(getImageUrl(item))">
								<img v-if="getImageUrl(item)" :src="getImageUrl(item)" @error="$event.target.style.display='none'">
								<span v-else>暂无图片</span>
							</div>
							<div class="certificate-item-main">
								<div class="certificate-item-title">{{formatValue(item.wuzimingcheng, '未命名物资')}}</div>
								<div class="certificate-item-meta">
									<span>编号：{{formatValue(item.juanzengbianhao)}}</span>
									<span>姓名：{{formatValue(item.xingming)}}</span>
								</div>
								<div class="certificate-item-footer">
									<span>{{formatValue(item.banfashijian, '暂无颁发时间')}}</span>
									<el-tag size="mini" :type="item.juanzengzhengshu ? 'success' : 'info'">{{item.juanzengzhengshu ? '已生成证书' : '暂无证书'}}</el-tag>
								</div>
							</div>
						</div>
					</div>
					<div class="certificate-detail-pane">
						<div v-if="currentRow" class="certificate-detail">
							<div class="certificate-detail-head">
								<div class="certificate-detail-img" @click="getImageUrl(currentRow) && imgPreView(getImageUrl(currentRow))">
									<img v-if="getImageUrl(currentRow)" :src="getImageUrl(currentRow)" @error="$event.target.style.display='none'">
									<span v-else>暂无图片</span>
								</div>
								<div class="certificate-detail-title">
									<h3>{{formatValue(currentRow.wuzimingcheng, '未命名物资')}}</h3>
									<p>{{formatValue(currentRow.juanzengbianhao, '暂无捐赠编号')}}</p>
									<el-tag :type="currentRow.juanzengzhengshu ? 'success' : 'info'">{{currentRow.juanzengzhengshu ? '已生成证书' : '暂无证书'}}</el-tag>
								</div>
							</div>
							<div class="certificate-detail-grid">
								<div class="detail-cell"><label>物资种类</label><span>{{formatValue(currentRow.wuzizhonglei)}}</span></div>
								<div class="detail-cell"><label>新旧程度</label><span>{{formatValue(currentRow.xinjiuchengdu)}}</span></div>
								<div class="detail-cell"><label>物资数量</label><span>{{formatValue(currentRow.wuzishuliang, 0)}}</span></div>
								<div class="detail-cell"><label>物资重量</label><span>{{formatValue(currentRow.wuzizhongliang)}}</span></div>
								<div class="detail-cell"><label>有效期</label><span>{{formatValue(currentRow.youxiaoqi)}}</span></div>
								<div class="detail-cell"><label>颁发时间</label><span>{{formatValue(currentRow.banfashijian)}}</span></div>
								<div class="detail-cell"><label>账号</label><span>{{formatValue(currentRow.zhanghao)}}</span></div>
								<div class="detail-cell"><label>姓名</label><span>{{formatValue(currentRow.xingming)}}</span></div>
								<div class="detail-cell detail-cell-full"><label>物资说明</label><span>{{formatValue(currentRow.wuzishuoming, '暂无说明')}}</span></div>
								<div class="detail-cell detail-cell-full"><label>捐赠证书</label>
									<el-button v-if="currentRow.juanzengzhengshu" class="certificate-download" type="text" size="small" @click="download(currentRow.juanzengzhengshu)">下载证书</el-button>
									<span v-else>暂无证书</span>
								</div>
							</div>
							<div class="certificate-detail-actions">
								<el-button class="view" v-if="isAuth('juanzengzhengshu','查看')" type="success" @click="addOrUpdateHandler(currentRow.id,'info')">
									<span class="icon iconfont icon-chakan14"></span>
									详情
								</el-button>
								<el-button class="edit" v-if="isAuth('juanzengzhengshu','修改')" type="success" @click="addOrUpdateHandler(currentRow.id)">
									<span class="icon iconfont icon-xiugai13"></span>
									修改
								</el-button>
								<el-button class="del" v-if="isAuth('juanzengzhengshu','删除')" type="primary" @click="deleteHandler(currentRow.id)">
									<span class="icon iconfont icon-shanchu6"></span>
									删除
								</el-button>
							</div>
						</div>
						<div v-else class="certificate-detail-empty">请选择左侧证书记录查看详情</div>
					</div>
				</div>
			</div>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				background
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:layout="layouts.join()"
				:total="totalPage"
				prev-text="< "
				next-text="> "
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","display":"flex","width":"100%","fontWeight":"500","justifyContent":"flex-end"}'
			></el-pagination>
		</template>
		<!-- 添加/修改页面  将父组件的search方法传递给子组件-->
		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>





		<el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>

<script>
	import * as echarts from 'echarts'
	import chinaJson from "@/components/echarts/china.json";
	import axios from 'axios';
	import AddOrUpdate from "./add-or-update";
	import {
		Loading
	} from 'element-ui';
	export default {
		data() {
			return {
				indexQueryCondition: '',
				searchForm: {
					key: ""
				},
				form:{},
				dataList: [],
				currentRow: null,
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				addOrUpdateFlag:false,
				layouts: ["total","prev","pager","next","sizes","jumper"],
				previewImg: '',
				previewVisible: false,
			};
		},
		created() {
			if(this.statType) {
				return false
			}
			this.init();
			this.getDataList();
		},
		mounted() {
		},
		watch: {
		},
		filters: {
			htmlfilter: function (val) {
				return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
			}
		},
		computed: {
			tablename(){
				return this.$storage.get('sessionTable')
			},
			role(){
				return this.$storage.get('role')
			},
		},
		components: {
			AddOrUpdate,
		},
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
				
			},
			getImageUrl(row) {
				if (!row || !row.wuzitupian) {
					return ''
				}
				if (row.wuzitupian.substring(0, 4) === 'http' && row.wuzitupian.split(',w').length > 1) {
					return row.wuzitupian
				}
				if (row.wuzitupian.substring(0, 4) === 'http') {
					return row.wuzitupian.split(',')[0]
				}
				return this.$base.url + row.wuzitupian.split(',')[0]
			},
			selectRow(row) {
				this.currentRow = row
			},
			toggleSelection(row, checked) {
				if (!row) {
					return
				}
				if (checked) {
					if (!this.isSelected(row)) {
						this.dataListSelections.push(row)
					}
				} else {
					this.dataListSelections = this.dataListSelections.filter(item => item.id !== row.id)
				}
			},
			isSelected(row) {
				return !!row && this.dataListSelections.some(item => item.id === row.id)
			},
			formatValue(value, fallback = '-') {
				return value !== undefined && value !== null && value !== '' ? value : fallback
			},
			init () {
			},
			search() {
				this.pageIndex = 1;
				this.getDataList();
			},

			// 获取数据列表
			getDataList() {
				this.dataListLoading = true;
				let params = {
					page: this.pageIndex,
					limit: this.pageSize,
					sort: 'id',
					order: 'desc',
				}
				if(this.searchForm.juanzengbianhao!='' && this.searchForm.juanzengbianhao!=undefined){
					params['juanzengbianhao'] = '%' + this.searchForm.juanzengbianhao + '%'
				}
				if(this.searchForm.wuzimingcheng!='' && this.searchForm.wuzimingcheng!=undefined){
					params['wuzimingcheng'] = '%' + this.searchForm.wuzimingcheng + '%'
				}
				if(this.searchForm.wuzizhonglei!='' && this.searchForm.wuzizhonglei!=undefined){
					params['wuzizhonglei'] = '%' + this.searchForm.wuzizhonglei + '%'
				}
				params['sort'] = 'banfashijian';
				params['order'] = 'desc';
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "juanzengzhengshu/page",
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.dataList = data.data.list;
						this.totalPage = data.data.total;
					} else {
						this.dataList = [];
						this.totalPage = 0;
					}
					this.dataListSelections = [];
					this.currentRow = this.dataList.length ? this.dataList[0] : null;
					this.dataListLoading = false;
				});
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.getDataList();
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val;
				this.getDataList();
			},
			// 多选
			selectionChangeHandler(val) {
				this.dataListSelections = val;
			},
			// 添加/修改
			addOrUpdateHandler(id,type) {
				this.showFlag = false;
				this.addOrUpdateFlag = true;
				this.crossAddOrUpdateFlag = false;
				if(type!='info'&&type!='msg'){
					type = 'else';
				}
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,type );
				});
			},
			// 下载
			download(file,type=1){
				let arr = file.replace(new RegExp('upload/', "g"), "")
				axios.get(this.$base.url + 'file/download?fileName=' + arr, {
					headers: {
						token: this.$storage.get('Token')
					},
					responseType: "blob"
				}).then(({
					data
				}) => {
					const binaryData = [];
					binaryData.push(data);
					const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
						type: 'application/pdf;chartset=UTF-8'
					}))
					const a = document.createElement('a')
					a.href = objectUrl
					a.download = arr
					// a.click()
					// 下面这个写法兼容火狐
					a.dispatchEvent(new MouseEvent('click', {
						bubbles: true,
						cancelable: true,
						view: window
					}))
					window.URL.revokeObjectURL(data)
				},err=>{
					axios.get((location.href.split(this.$base.name).length>1 ? location.href.split(this.$base.name)[0] :'') + this.$base.name + 'file/download?fileName=' + arr, {
						headers: {
							token: this.$storage.get('Token')
						},
						responseType: "blob"
					}).then(({
						data
					}) => {
						const binaryData = [];
						binaryData.push(data);
						const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
							type: 'application/pdf;chartset=UTF-8'
						}))
						const a = document.createElement('a')
						a.href = objectUrl
						a.download = arr
						// a.click()
						// 下面这个写法兼容火狐
						a.dispatchEvent(new MouseEvent('click', {
							bubbles: true,
							cancelable: true,
							view: window
						}))
						window.URL.revokeObjectURL(data)
					})
				})
			},
			// 删除
			async deleteHandler(id ) {
				var ids = id? [Number(id)]: this.dataListSelections.map(item => {
					return Number(item.id);
				});
				await this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(async () => {
					await this.$http({
						url: "juanzengzhengshu/delete",
						method: "post",
						data: ids
					}).then(async ({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "操作成功",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
			
						} else {
							this.$message.error(data.msg);
						}
					});
				});
			},


		}

	};
</script>
<style lang="scss" scoped>
	
	.center-form-pv {
		.el-date-editor.el-input {
			width: auto;
		}
	}
	
	.el-input {
		width: auto;
	}
	
	// form
	.center-form-pv .el-input {
		width: 100%;
	}
	.center-form-pv .el-input /deep/ .el-input__inner {
		border: 1px solid #ddd;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 36px;
	}
	.center-form-pv .el-select {
		width: 100%;
	}
	.center-form-pv .el-select /deep/ .el-input__inner {
		border: 1px solid #ddd;
		border-radius: 4px;
		padding: 0 10px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 36px;
	}
	.center-form-pv .el-date-editor {
		width: 100%;
	}
	
	.center-form-pv .el-date-editor /deep/ .el-input__inner {
		border: 1px solid #ddd;
		border-radius: 4px;
		padding: 0 10px 0 30px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 36px;
	}
	
	.center-form-pv .search {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 18px 0 15px;
		outline: none;
		color: #fff;
		background: #ff7d55;
		width: auto;
		font-size: 16px;
		height: 36px;
	}
	
	.center-form-pv .search:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .add {
		border: 0;
		cursor: pointer;
		border-radius: 8px;
		padding: 0 10px;
		margin: 4px;
		outline: none;
		color: #666;
		background: #fff;
		width: auto;
		font-size: 14px;
		height: 48px;
	}
	
	.center-form-pv .actions .add:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .del {
		border: 0;
		cursor: pointer;
		border-radius: 8px;
		padding: 0 10px;
		margin: 4px;
		outline: none;
		color: #666;
		background: #fff;
		width: auto;
		font-size: 14px;
		height: 48px;
	}
	
	.center-form-pv .actions .del:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .statis {
		border: 0;
		cursor: pointer;
		border-radius: 8px;
		padding: 0 10px;
		margin: 4px;
		outline: none;
		color: #666;
		background: #fff;
		width: auto;
		font-size: 14px;
		height: 48px;
	}
	
	.center-form-pv .actions .statis:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .btn18 {
		border: 0;
		cursor: pointer;
		border-radius: 8px;
		padding: 0 10px;
		margin: 4px;
		outline: none;
		color: #666;
		background: #fff;
		width: auto;
		font-size: 14px;
		height: 48px;
	}
	
	.center-form-pv .actions .btn18:hover {
		opacity: 0.8;
	}
	
	// table
	.el-table /deep/ .el-table__header-wrapper thead {
		color: #999;
		font-weight: 500;
		width: 100%;
	}
	
	.el-table /deep/ .el-table__header-wrapper thead tr {
		background: #fff;
	}
	
	.el-table /deep/ .el-table__header-wrapper thead tr th {
		padding: 12px 0;
		background: #fef6f0;
		border-color: #eee;
		border-width: 0 0px 1px 0;
		border-style: solid;
		text-align: center;
	}

	.el-table /deep/ .el-table__header-wrapper thead tr th .cell {
		padding: 0 0 0 5px;
		word-wrap: normal;
		color: #7a6a5a;
		white-space: normal;
		font-weight: bold;
		display: flex;
		vertical-align: middle;
		font-size: 14px;
		line-height: 24px;
		text-overflow: ellipsis;
		word-break: break-all;
		width: 100%;
		justify-content: flex-start;
		align-items: center;
		position: relative;
		min-width: 110px;
	}

	.el-table /deep/ .el-table__body-wrapper {
		position: relative;
	}
	.el-table /deep/ .el-table__body-wrapper tbody {
		width: 100%;
	}

	.el-table /deep/ .el-table__body-wrapper tbody tr {
		background: #fff;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 0px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
		
	.el-table /deep/ .el-table__body-wrapper tbody tr:hover td {
		padding: 4px 0;
		color: #000;
		background: #f9f7f3;
		border-color: #eee;
		border-width: 0 0px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 0px 1px 0;
		border-style: solid;
		text-align: left;
	}

	.el-table /deep/ .el-table__body-wrapper tbody tr td .cell {
		padding: 0 0 0 5px;
		overflow: hidden;
		word-break: break-all;
		white-space: normal;
		font-size: inherit;
		line-height: 24px;
		text-overflow: ellipsis;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .view {
		border: 0;
		cursor: pointer;
		border-radius: 8px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #3179c3;
		width: auto;
		font-size: 14px;
		height: 32px;
		order: 3;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .view:hover {
		opacity: 0.8;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .add {
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .add:hover {
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .edit {
		border: 0;
		cursor: pointer;
		border-radius: 8px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2f6937;
		width: auto;
		font-size: 14px;
		height: 32px;
		order: -1;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .edit:hover {
		opacity: 0.8;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #ef6767;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .del:hover {
		opacity: 0.8;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .btn8 {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #ff955c;
		width: auto;
		font-size: 14px;
		height: 32px;
		order: 5;
	}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .btn8:hover {
		opacity: 0.8;
	}
	
	// pagination
	.main-content .el-pagination /deep/ .el-pagination__total {
		margin: 0 auto 0 0;
		color: #666;
		font-weight: 400;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .btn-prev {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .btn-next {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .btn-prev:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .btn-next:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}

	.main-content .el-pagination /deep/ .el-pager {
		padding: 0;
		margin: 0;
		display: inline-block;
		vertical-align: top;
	}

	.main-content .el-pagination /deep/ .el-pager .number {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #666;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #f4f4f5;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pager .number:hover {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #ff8b00;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pager .number.active {
		cursor: default;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #ff8b00;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes {
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input {
		margin: 0 5px;
		width: 100px;
		position: relative;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 25px 0 8px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input span.el-input__suffix {
		top: 0;
		position: absolute;
		right: 0;
		height: 100%;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
		cursor: pointer;
		color: #C0C4CC;
		width: 25px;
		font-size: 15px;
		line-height: 28px;
		text-align: center;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__jump {
		margin: 0 0 0 24px;
		color: #606266;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__jump .el-input {
		border-radius: 3px;
		padding: 0 2px;
		margin: 0 2px;
		display: inline-block;
		width: 50px;
		font-size: 15px;
		line-height: 18px;
		position: relative;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination /deep/ .el-pagination__jump .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 3px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	// list one
	.one .list1-view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-view:hover {
		opacity: 0.8;
	}
	
	.one .list1-edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f60;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-edit:hover {
		opacity: 0.8;
	}
	
	.one .list1-del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f00;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-del:hover {
		opacity: 0.8;
	}
	
	.one .list1-btn8 {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-btn8:hover {
		opacity: 0.8;
	}
	
	.main-content .el-table .el-switch {
		display: inline-flex;
		vertical-align: middle;
		line-height: 30px;
		position: relative;
		align-items: center;
		height: 30px;
	}
	.main-content .el-table .el-switch /deep/ .el-switch__label--left {
		cursor: pointer;
		margin: 0 10px 0 0;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch /deep/ .el-switch__label--right {
		cursor: pointer;
		margin: 0 0 0 10px;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch /deep/ .el-switch__core {
		border: 1px solid #ff8b00;
		cursor: pointer;
		border-radius: 15px;
		margin: 0;
		outline: 0;
		background: #ff8b00;
		display: inline-block;
		width: 36px;
		box-sizing: border-box;
		transition: border-color .3s,background-color .3s;
		height: 18px;
	}
	.main-content .el-table .el-switch /deep/ .el-switch__core::after {
		border-radius: 100%;
		top: 1px;
		left: 2px;
		background: #FFF;
		width: 14px;
		position: absolute;
		transition: all .3s;
		height: 14px;
	}
	.main-content .el-table .el-switch.is-checked /deep/ .el-switch__core::after {
		margin: 0 0 0 -16px;
		left: 100%;
	}
	
	.main-content .el-table .el-rate /deep/ .el-rate__item {
		cursor: pointer;
		display: inline-block;
		vertical-align: middle;
		font-size: 0;
		position: relative;
	}
	.main-content .el-table .el-rate /deep/ .el-rate__item .el-rate__icon {
		margin: 0 3px;
		display: inline-block;
		font-size: 18px;
		position: relative;
		transition: .3s;
	}

	.chartDialog /deep/ .el-dialog {
		background: #fff;
	}

.certificate-split {
	display: grid;
	grid-template-columns: minmax(360px, 38%) minmax(0, 1fr);
	gap: 18px;
	min-height: 420px;
}

.certificate-list-pane,
.certificate-detail-pane {
	border: 1px solid #e6edf5;
	border-radius: 8px;
	background: #f8fafc;
}

.certificate-list-pane {
	max-height: 680px;
	padding: 12px;
	overflow-y: auto;
}

.certificate-list-item {
	position: relative;
	display: flex;
	align-items: center;
	gap: 12px;
	margin: 0 0 10px;
	padding: 12px;
	border: 1px solid #e4ebf2;
	border-radius: 8px;
	background: #fff;
	cursor: pointer;
	transition: border-color .18s ease, box-shadow .18s ease, background .18s ease;
}

.certificate-list-item:hover {
	border-color: #cfddea;
	box-shadow: 0 8px 18px rgba(32, 45, 64, .08);
}

.certificate-list-item.active {
	border-color: #b7d8b1;
	background: #f5fbf4;
	box-shadow: 0 10px 22px rgba(64, 120, 75, .1);
}

.certificate-list-item.active::before {
	position: absolute;
	top: 10px;
	bottom: 10px;
	left: 0;
	width: 4px;
	border-radius: 0 4px 4px 0;
	background: #4f9f45;
	content: "";
}

.certificate-check {
	flex: 0 0 auto;
	line-height: 1;
}

.certificate-index {
	flex: 0 0 32px;
	width: 32px;
	height: 32px;
	border-radius: 8px;
	background: #eef4f8;
	color: #607083;
	font-size: 13px;
	font-weight: 800;
	line-height: 32px;
	text-align: center;
}

.certificate-thumb {
	flex: 0 0 58px;
	width: 58px;
	height: 58px;
	border-radius: 8px;
	background: #edf3f7;
	box-shadow: inset 0 0 0 1px #e1e8ef;
	overflow: hidden;
	cursor: zoom-in;
}

.certificate-thumb img,
.certificate-detail-img img {
	display: block;
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.certificate-thumb span,
.certificate-detail-img span {
	display: flex;
	align-items: center;
	justify-content: center;
	width: 100%;
	height: 100%;
	color: #94a3b8;
	font-size: 12px;
}

.certificate-item-main {
	flex: 1;
	min-width: 0;
}

.certificate-item-title {
	margin: 0 0 7px;
	overflow: hidden;
	color: #22302a;
	font-size: 15px;
	font-weight: 800;
	line-height: 22px;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.certificate-item-meta,
.certificate-item-footer {
	display: flex;
	flex-wrap: wrap;
	gap: 6px 12px;
	color: #64748b;
	font-size: 12px;
	line-height: 18px;
}

.certificate-item-meta span,
.certificate-item-footer span {
	max-width: 170px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.certificate-detail-pane {
	padding: 18px;
}

.certificate-detail {
	min-height: 100%;
}

.certificate-detail-head {
	display: flex;
	gap: 18px;
	margin: 0 0 18px;
	padding: 0 0 18px;
	border-bottom: 1px solid #e6edf5;
}

.certificate-detail-img {
	flex: 0 0 150px;
	width: 150px;
	height: 150px;
	border-radius: 8px;
	background: #edf3f7;
	box-shadow: inset 0 0 0 1px #e1e8ef;
	overflow: hidden;
	cursor: zoom-in;
}

.certificate-detail-title {
	display: flex;
	flex: 1;
	flex-direction: column;
	justify-content: center;
	min-width: 0;
}

.certificate-detail-title h3 {
	margin: 0 0 8px;
	color: #22302a;
	font-size: 22px;
	font-weight: 800;
	line-height: 30px;
}

.certificate-detail-title p {
	margin: 0 0 12px;
	color: #64748b;
	font-size: 14px;
	line-height: 22px;
}

.certificate-detail-grid {
	display: grid;
	grid-template-columns: repeat(3, minmax(0, 1fr));
	gap: 12px;
}

.detail-cell {
	min-height: 62px;
	padding: 10px 12px;
	border: 1px solid #e6edf5;
	border-radius: 8px;
	background: #fff;
}

.detail-cell label {
	display: block;
	margin: 0 0 6px;
	color: #7b8794;
	font-size: 12px;
	font-weight: 700;
}

.detail-cell span {
	color: #2f3b46;
	font-size: 14px;
	line-height: 20px;
	word-break: break-word;
}

.detail-cell-full {
	grid-column: 1 / -1;
}

.certificate-download {
	color: #2474d4 !important;
	font-weight: 700;
}

.certificate-detail-actions {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin: 18px 0 0;
}

.certificate-detail-actions /deep/ .el-button {
	height: 34px !important;
	padding: 0 14px !important;
	border: 0 !important;
	border-radius: 6px !important;
	font-size: 13px !important;
	font-weight: 700;
	line-height: 34px !important;
}

.certificate-detail-actions /deep/ .view {
	background: #2f80c9 !important;
	color: #fff !important;
}

.certificate-detail-actions /deep/ .edit {
	background: #4f9f45 !important;
	color: #fff !important;
}

.certificate-detail-actions /deep/ .del {
	background: #d9534f !important;
	color: #fff !important;
}

.certificate-empty,
.certificate-detail-empty {
	display: flex;
	align-items: center;
	justify-content: center;
	min-height: 220px;
	color: #8a96a3;
	font-size: 14px;
}

@media (max-width: 1100px) {
	.certificate-split {
		grid-template-columns: 1fr;
	}
	.certificate-list-pane {
		max-height: none;
	}
}

@media (max-width: 640px) {
	.certificate-list-item,
	.certificate-detail-head {
		flex-direction: column;
		align-items: stretch;
	}
	.certificate-thumb,
	.certificate-detail-img {
		width: 100%;
		max-width: 220px;
	}
	.certificate-detail-grid {
		grid-template-columns: 1fr;
	}
	.certificate-detail-title h3 {
		font-size: 18px;
		line-height: 26px;
	}
}
</style>
