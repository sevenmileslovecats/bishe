  
<template>
	<div class="main-content" :style='{"padding":"50px 30px 30px"}'>
		<!-- 列表页 -->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 20px"}' :inline="true" :model="searchForm">
				<el-row :style='{"padding":"30px 20px 10px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","flexWrap":"wrap","background":"#fff","borderWidth":"4px 0 0 0","display":"flex","width":"100%","position":"relative","borderStyle":"solid"}' >
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">申领编号</label>
						<el-input v-model="searchForm.shenlingbianhao" placeholder="申领编号" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物资名称</label>
						<el-input v-model="searchForm.wuzimingcheng" placeholder="物资名称" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物资种类</label>
						<el-input v-model="searchForm.wuzizhonglei" placeholder="物资种类" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div :style='{"margin":"0 1% 20px 0","display":"flex"}' class="select">
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">是否通过</label>
						<el-select clearable v-model="searchForm.sfsh" placeholder="是否通过">
							<el-option v-for="(item,index) in sfshOptions" v-bind:key="index" :label="item" :value="item"></el-option>
						</el-select>
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
					<el-button class="add" v-if="isAuth('yiyifankui','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia16" :style='{"padding":"10px","margin":"0 2px","color":"#ff9365","borderRadius":"100%","background":"#ff936510","fontSize":"18px","fontWeight":"500","height":"40px"}'></span>
						添加
					</el-button>
					<el-button class="del" v-if="isAuth('yiyifankui','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"padding":"10px","margin":"0 2px","color":"#ea6464","borderRadius":"100%","background":"#ea646410","fontSize":"18px","fontWeight":"500","height":"40px"}'></span>
						批量删除
					</el-button>


					<el-button class="btn18" v-if="isAuth('yiyifankui','审核')" :disabled="dataListSelections.length?false:true" type="success" @click="shBatchDialog()">
						<span class="icon iconfont icon-shenhe9" :style='{"padding":"10px","margin":"0 2px","color":"#af7d59","borderRadius":"100%","background":"#e9cfbc20","fontSize":"20px","fontWeight":"500","height":"40px"}'></span>
						审核
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"padding":"20px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","background":"#fff","borderWidth":"4px 0 0","width":"100%","borderStyle":"solid"}'>
				<div v-if="isAuth('yiyifankui','查看')" class="split-list-layout" v-loading="dataListLoading">
					<div class="split-list-left">
						<div v-if="!dataListLoading && dataList.length === 0" class="split-empty">暂无异议反馈数据</div>
						<div
							v-for="(item,index) in dataList"
							:key="item.id"
							class="split-list-item"
							:class="{ active: currentRow && currentRow.id === item.id }"
							@click="selectRow(item)"
						>
							<el-checkbox
								class="split-check"
								:value="isSelected(item)"
								@click.native.stop
								@change="toggleSelection(item, $event)"
							></el-checkbox>
							<div class="split-index">{{(pageIndex - 1) * pageSize + index + 1}}</div>
							<div class="split-thumb" @click.stop="getImageUrl(item) && imgPreView(getImageUrl(item))">
								<img v-if="getImageUrl(item)" :src="getImageUrl(item)" @error="$event.target.style.display='none'">
								<span v-else>暂无图片</span>
							</div>
							<div class="split-item-main">
								<div class="split-item-title">{{formatValue(item.wuzimingcheng, '未命名物资')}}</div>
								<div class="split-item-meta">
									<span>申领：{{formatValue(item.shenlingbianhao)}}</span>
									<span>{{formatValue(item.tijiaoshijian, '暂无提交时间')}}</span>
								</div>
								<el-tag size="mini" :type="getSfshTagType(item.sfsh)">{{getSfshText(item.sfsh)}}</el-tag>
							</div>
						</div>
					</div>
					<div class="split-detail-right">
						<div v-if="currentRow" class="split-detail">
							<div class="split-detail-head">
								<div class="split-detail-img" @click="getImageUrl(currentRow) && imgPreView(getImageUrl(currentRow))">
									<img v-if="getImageUrl(currentRow)" :src="getImageUrl(currentRow)" @error="$event.target.style.display='none'">
									<span v-else>暂无图片</span>
								</div>
								<div class="split-detail-title">
									<h3>{{formatValue(currentRow.wuzimingcheng, '未命名物资')}}</h3>
									<p>{{formatValue(currentRow.shenlingbianhao, '暂无申领编号')}}</p>
									<el-tag :type="getSfshTagType(currentRow.sfsh)">{{getSfshText(currentRow.sfsh)}}</el-tag>
								</div>
							</div>
							<div class="split-detail-grid">
								<div class="split-detail-cell full">
									<div class="split-label">异议反馈</div>
									<div class="split-value">{{formatValue(currentRow.yiyifankui, '暂无反馈内容')}}</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">物资种类</div>
									<div class="split-value">{{formatValue(currentRow.wuzizhonglei)}}</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">出库数量</div>
									<div class="split-value">{{formatValue(currentRow.wuzishuliang, 0)}}</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">提交时间</div>
									<div class="split-value">{{formatValue(currentRow.tijiaoshijian)}}</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">机构账号</div>
									<div class="split-value">{{formatValue(currentRow.jigouzhanghao)}}</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">机构名称</div>
									<div class="split-value">{{formatValue(currentRow.jigoumingcheng)}}</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">佐证材料</div>
									<div class="split-value">
									<el-button v-if="currentRow.zuozhengcailiao" type="text" size="small" @click="download(currentRow.zuozhengcailiao)">下载</el-button>
									<span v-else>暂无</span>
									</div>
								</div>
								<div class="split-detail-cell">
									<div class="split-label">出库单</div>
									<div class="split-value">
									<el-button v-if="currentRow.chukudan" type="text" size="small" @click="download(currentRow.chukudan)">下载</el-button>
									<span v-else>暂无</span>
									</div>
								</div>
								<div class="split-detail-cell full">
									<div class="split-label">审核回复</div>
									<div class="split-value">{{formatValue(currentRow.shhf, '暂无审核回复')}}</div>
								</div>
							</div>
							<div class="split-actions">
								<el-button class="view" v-if="isAuth('yiyifankui','查看')" type="success" @click="addOrUpdateHandler(currentRow.id,'info')">
									<span class="icon iconfont icon-chakan14"></span>
									详情
								</el-button>
								<el-button class="edit" v-if="isAuth('yiyifankui','修改') && currentRow.sfsh=='待审核'" type="success" @click="addOrUpdateHandler(currentRow.id)">
									<span class="icon iconfont icon-xiugai13"></span>
									修改
								</el-button>
								<el-button class="del" v-if="isAuth('yiyifankui','删除')" type="primary" @click="deleteHandler(currentRow.id)">
									<span class="icon iconfont icon-shanchu6"></span>
									删除
								</el-button>
							</div>
						</div>
						<div v-else class="split-empty">请选择左侧异议反馈查看详情</div>
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


		
		<el-dialog :title="this.batchIds.length>1?'批量审核':'审核'" :visible.sync="sfshBatchVisiable" width="50%">
			<el-form ref="shBatchForm" :model="shBatchForm" :rules="shRules" label-width="80px">
				<el-form-item label="审核状态" prop="sfsh">
					<el-select v-model="shBatchForm.sfsh" placeholder="审核状态">
						<el-option label="通过" value="是"></el-option>
						<el-option label="不通过" value="否"></el-option>
						<el-option label="待审核" value="待审核"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="内容" prop="shhf">
					<el-input type="textarea" :rows="8" v-model="shBatchForm.shhf"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="sfshBatchVisiable=false">取 消</el-button>
				<el-button type="primary" @click="shBatchHandler">确 定</el-button>
			</span>
		</el-dialog>



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
				sfshOptions: [],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				currentRow: null,
				showFlag: true,
				sfshVisiable: false,
				shForm: {},
				sfshBatchVisiable: false,
				shBatchForm: {
					sfsh:'',
					shhf:''
				},
				shRules: {
					sfsh:[{ required: true, message: '审核状态不能为空', trigger: 'blur' },],
				},
				batchIds:[],
				shList: [],
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
				if(!row || !row.zuozhengtupian) {
					return ''
				}
				const image = String(row.zuozhengtupian)
				if(image.substring(0,4)=='http'&&image.split(',w').length>1) {
					return encodeURI(image)
				}
				if(image.substring(0,4)=='http') {
					return encodeURI(image.split(',')[0])
				}
				const firstImage = image.split(',')[0].replace(/^\/+/, '')
				return encodeURI(this.$base.url + firstImage)
			},
			selectRow(row) {
				this.currentRow = row
			},
			toggleSelection(row, checked) {
				if(checked) {
					if(!this.isSelected(row)) {
						this.dataListSelections.push(row)
					}
				} else {
					this.dataListSelections = this.dataListSelections.filter(item => item.id !== row.id)
				}
			},
			isSelected(row) {
				return this.dataListSelections.some(item => item.id === row.id)
			},
			formatValue(value, fallback = '-') {
				if(value === undefined || value === null || value === '') {
					return fallback
				}
				return value
			},
			getSfshTagType(sfsh) {
				if(sfsh === '是') {
					return 'success'
				}
				if(sfsh === '否') {
					return 'danger'
				}
				return 'warning'
			},
			getSfshText(sfsh) {
				if(sfsh === '是') {
					return '通过'
				}
				if(sfsh === '否') {
					return '未通过'
				}
				return '待审核'
			},
			init () {
				this.sfshOptions = "是,否,待审核".split(',');
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
				params['sort'] = 'tijiaoshijian';
				params['order'] = 'desc';
				if(this.searchForm.shenlingbianhao!='' && this.searchForm.shenlingbianhao!=undefined){
					params['shenlingbianhao'] = '%' + this.searchForm.shenlingbianhao + '%'
				}
				if(this.searchForm.wuzimingcheng!='' && this.searchForm.wuzimingcheng!=undefined){
					params['wuzimingcheng'] = '%' + this.searchForm.wuzimingcheng + '%'
				}
				if(this.searchForm.wuzizhonglei!='' && this.searchForm.wuzizhonglei!=undefined){
					params['wuzizhonglei'] = '%' + this.searchForm.wuzizhonglei + '%'
				}
				if(this.searchForm.sfsh!='' && this.searchForm.sfsh!=undefined){
					params['sfsh'] = this.searchForm.sfsh
				}
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "yiyifankui/page",
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
			//批量审核窗口
			shBatchDialog(){
				this.batchIds = []
				for(let x in this.dataListSelections){
					if(this.dataListSelections[x].sfsh&&this.dataListSelections[x].sfsh!='待审核'){
						this.$message.error('存在已审核数据，不能继续操作');
						this.batchIds = []
						return false
					}
					this.batchIds.push(this.dataListSelections[x].id)
				}
				this.shList = this.dataListSelections
				this.sfshBatchVisiable = true
			},
			//批量审核
			shBatchHandler(){
				this.$refs["shBatchForm"].validate(valid => {
					if(valid){
						this.$confirm(`是否${this.batchIds.length>1?'一键审核':'审核'}选中数据?`, "提示", {
							confirmButtonText: "确定",
							cancelButtonText: "取消",
							type: "warning"
						}).then(async() => {
							this.$http({
								url: "yiyifankui/shBatch?sfsh="+this.shBatchForm.sfsh+"&shhf="+this.shBatchForm.shhf,
								method: "post",
								data: this.batchIds
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									this.$message({
										message: "操作成功",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.getDataList();
											this.sfshBatchVisiable = false
											this.batchIds = []
										}
									});
								} else {
									this.$message.error(data.msg);
								}
							});
						});
					}
				})
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
						url: "yiyifankui/delete",
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

/* admin-list-polish */
.main-content {
	padding: 24px !important;
	background: #f5f7fb !important;
	min-height: calc(100vh - 48px);
	box-sizing: border-box;
}

.center-form-pv {
	margin: 0 0 16px !important;
}

.center-form-pv > .el-row:first-child {
	padding: 18px 20px 4px !important;
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #fff !important;
	box-shadow: 0 8px 22px rgba(32, 45, 64, .06) !important;
	display: flex !important;
	align-items: flex-end;
	gap: 12px 16px;
}

.center-form-pv > .el-row:first-child > div {
	margin: 0 0 14px !important;
	display: flex !important;
	align-items: center;
}

.center-form-pv .item-label {
	height: 36px !important;
	line-height: 36px !important;
	margin: 0 8px 0 0 !important;
	color: #44505c !important;
	font-size: 14px !important;
	font-weight: 600 !important;
}

.center-form-pv /deep/ .el-input,
.center-form-pv /deep/ .el-select {
	width: 190px;
}

.center-form-pv /deep/ .el-input__inner {
	height: 36px;
	line-height: 36px;
	border-color: #dfe7ef;
	border-radius: 6px;
	color: #334155;
}

.center-form-pv /deep/ .el-input__inner:focus {
	border-color: #6aac5a;
}

.center-form-pv .search,
.center-form-pv .add,
.center-form-pv .btn18,
.center-form-pv .del {
	height: 36px !important;
	line-height: 36px !important;
	padding: 0 15px !important;
	margin: 0 8px 12px 0 !important;
	border: 0 !important;
	border-radius: 6px !important;
	font-size: 14px !important;
	font-weight: 600;
	box-shadow: none !important;
}

.center-form-pv .search,
.center-form-pv .add {
	background: #4f9f45 !important;
	color: #fff !important;
}

.center-form-pv .btn18 {
	background: #eef7ee !important;
	color: #35663b !important;
	border: 1px solid #cfe6cf !important;
}

.center-form-pv .del {
	background: #fff1f1 !important;
	color: #c94b4b !important;
	border: 1px solid #f0caca !important;
}

.center-form-pv .actions {
	margin: 12px 0 16px !important;
	width: 100% !important;
	gap: 8px;
}

.center-form-pv .actions .icon,
.center-form-pv .actions .iconfont,
.center-form-pv .search .icon,
.center-form-pv .search .iconfont {
	padding: 0 !important;
	margin: 0 4px 0 0 !important;
	background: transparent !important;
	color: inherit !important;
	font-size: 14px !important;
	height: auto !important;
}

.center-form-pv + div {
	padding: 16px !important;
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #fff !important;
	box-shadow: 0 10px 28px rgba(32, 45, 64, .07) !important;
	overflow-x: auto;
}

.tables {
	min-width: 1180px;
	border: 0 !important;
	border-radius: 8px !important;
	overflow: hidden;
}

.tables /deep/ .el-table__header-wrapper thead tr,
.tables /deep/ .el-table__header-wrapper thead tr th {
	background: #f6faf7 !important;
}

.tables /deep/ .el-table__header-wrapper thead tr th {
	padding: 10px 0 !important;
	border-color: #e7edf0 !important;
	color: #22302a !important;
	font-weight: 700 !important;
}

.tables /deep/ .el-table__header-wrapper thead tr th .cell,
.tables /deep/ .el-table__body-wrapper tbody tr td .cell {
	padding: 0 12px !important;
	line-height: 22px !important;
}

.tables /deep/ .el-table__body-wrapper tbody tr td {
	padding: 10px 0 !important;
	border-color: #eef2f3 !important;
	color: #3d4b43 !important;
	background: #fff !important;
}

.tables /deep/ .el-table__body-wrapper tbody tr:hover td,
.tables /deep/ .el-table__body-wrapper tbody tr.current-row td,
.tables /deep/ .el-table__body-wrapper tbody tr.hover-row td {
	background: #f7fbf6 !important;
}

.tables /deep/ img {
	width: 64px !important;
	height: 64px !important;
	border-radius: 6px;
	object-fit: cover;
	display: block;
	background: #f0f4f1;
	box-shadow: inset 0 0 0 1px #e5ebe6;
	cursor: pointer;
}

.tables /deep/ .el-tag {
	border-radius: 14px;
	padding: 0 12px;
}

.tables /deep/ .el-button {
	height: 32px !important;
	line-height: 32px !important;
	padding: 0 12px !important;
	margin: 0 6px 4px 0 !important;
	border: 0 !important;
	border-radius: 6px !important;
	font-size: 13px !important;
	box-shadow: none !important;
}

.tables /deep/ .view {
	background: #4f9f45 !important;
	color: #fff !important;
}

.tables /deep/ .edit,
.tables /deep/ .btn8 {
	background: #278f7f !important;
	color: #fff !important;
}

.tables /deep/ .del {
	background: #d9534f !important;
	color: #fff !important;
}

.tables /deep/ .btn18,
.tables /deep/ .btn3,
.tables /deep/ .btn4,
.tables /deep/ .btn5 {
	background: #e89232 !important;
	color: #fff !important;
}

.tables /deep/ .el-button--text {
	color: #2474d4 !important;
	background: transparent !important;
	padding: 0 !important;
}

.el-pagination {
	padding: 14px 0 0 !important;
	margin: 16px 0 0 !important;
	justify-content: flex-end !important;
}

.el-pagination /deep/ .btn-prev,
.el-pagination /deep/ .btn-next,
.el-pagination /deep/ .el-pager li {
	border-radius: 6px !important;
}

@media (max-width: 960px) {
	.main-content {
		padding: 14px !important;
	}
	.center-form-pv > .el-row:first-child > div,
	.center-form-pv /deep/ .el-input,
	.center-form-pv /deep/ .el-select {
		width: 100%;
	}
}

/* business-list-polish */
.main-content {
	background: #f5f7fb !important;
}
.center-form-pv > .el-row:first-child {
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #fff !important;
	box-shadow: 0 8px 22px rgba(32, 45, 64, .06) !important;
}
.center-form-pv .item-label {
	color: #44505c !important;
	font-size: 14px !important;
	font-weight: 700 !important;
}
.center-form-pv /deep/ .el-input__inner,
.center-form-pv /deep/ .el-select .el-input__inner {
	height: 36px !important;
	line-height: 36px !important;
	border-color: #dfe7ef !important;
	border-radius: 6px !important;
	color: #334155 !important;
}
.center-form-pv + div,
.data-table-card {
	padding: 16px !important;
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #fff !important;
	box-shadow: 0 10px 28px rgba(32, 45, 64, .07) !important;
	overflow-x: auto;
}
.tables {
	min-width: 1180px;
	border: 0 !important;
	border-radius: 8px !important;
	overflow: hidden;
}
.tables /deep/ .el-table__header-wrapper thead tr th {
	padding: 10px 0 !important;
	background: #f6faf7 !important;
	border-color: #e7edf0 !important;
	color: #22302a !important;
	font-weight: 800 !important;
}
.tables /deep/ .el-table__body-wrapper tbody tr td {
	padding: 10px 0 !important;
	border-color: #eef2f3 !important;
	color: #3d4b43 !important;
	background: #fff !important;
}
.tables /deep/ .el-table__body-wrapper tbody tr:hover td,
.tables /deep/ .el-table__body-wrapper tbody tr.hover-row td,
.tables /deep/ .el-table__body-wrapper tbody tr.current-row td {
	background: #f7fbf6 !important;
}
.tables /deep/ .cell {
	line-height: 22px !important;
	word-break: break-word !important;
}
.tables /deep/ img {
	display: block;
	width: 64px !important;
	height: 64px !important;
	border-radius: 6px;
	object-fit: cover;
	background: #f0f4f1;
	box-shadow: inset 0 0 0 1px #e5ebe6;
	cursor: pointer;
}
.tables /deep/ .el-tag {
	border-radius: 999px !important;
	padding: 0 12px !important;
	font-weight: 700;
}
.tables /deep/ .el-button {
	height: 32px !important;
	line-height: 32px !important;
	padding: 0 12px !important;
	margin: 0 6px 4px 0 !important;
	border: 0 !important;
	border-radius: 6px !important;
	font-size: 13px !important;
	font-weight: 700;
	box-shadow: none !important;
}
.tables /deep/ .view,
.tables /deep/ .table-view {
	background: #2f80c9 !important;
	color: #fff !important;
}
.tables /deep/ .edit,
.tables /deep/ .table-edit {
	background: #4f9f45 !important;
	color: #fff !important;
}
.tables /deep/ .del,
.tables /deep/ .table-del {
	background: #d9534f !important;
	color: #fff !important;
}
.tables /deep/ .btn8,
.tables /deep/ .btn18,
.tables /deep/ .btn3,
.tables /deep/ .btn4,
.tables /deep/ .btn5,
.tables /deep/ .table-btn5,
.tables /deep/ .table-trace {
	background: #278f7f !important;
	color: #fff !important;
}
.el-pagination {
	padding: 14px 0 0 !important;
	margin: 16px 0 0 !important;
	justify-content: flex-end !important;
}
@media (max-width: 960px) {
	.center-form-pv > .el-row:first-child > div,
	.center-form-pv /deep/ .el-input,
	.center-form-pv /deep/ .el-select {
		width: 100% !important;
	}
}

.split-list-layout {
	display: grid;
	grid-template-columns: minmax(360px, 38%) minmax(0, 1fr);
	gap: 18px;
	align-items: stretch;
	height: calc(100vh - 320px);
	min-height: 520px;
}

.split-list-left,
.split-detail-right {
	border: 1px solid #e6edf5;
	border-radius: 8px;
	background: #f8fafc;
	height: 100%;
	overflow-y: auto;
	box-sizing: border-box;
}

.split-list-left {
	padding: 12px;
}

.split-list-item {
	display: grid;
	grid-template-columns: 24px 40px 64px minmax(0, 1fr);
	align-items: center;
	gap: 12px;
	margin-bottom: 12px;
	padding: 12px;
	border: 1px solid #e1e8f0;
	border-radius: 8px;
	background: #fff;
	cursor: pointer;
	transition: border-color .2s ease, box-shadow .2s ease, background .2s ease;
	position: relative;
}

.split-list-item:last-child {
	margin-bottom: 0;
}

.split-list-item:hover {
	border-color: #bcd9c0;
	box-shadow: 0 8px 20px rgba(45, 91, 63, .08);
}

.split-list-item.active {
	border-color: #9fd3a7;
	background: #f4fbf5;
	box-shadow: inset 4px 0 0 #67c23a;
}

.split-check {
	display: flex;
	align-items: center;
	justify-content: center;
}

.split-index {
	display: flex;
	align-items: center;
	justify-content: center;
	width: 32px;
	height: 32px;
	border-radius: 8px;
	background: #eef4f8;
	color: #5c7a95;
	font-weight: 700;
}

.split-thumb,
.split-detail-img {
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;
	border-radius: 8px;
	background: #eef3f7;
	color: #8a9bae;
	font-size: 12px;
	text-align: center;
}

.split-thumb {
	width: 64px;
	height: 64px;
}

.split-thumb img,
.split-detail-img img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.split-item-main {
	min-width: 0;
}

.split-item-title {
	margin-bottom: 7px;
	color: #1f2d3d;
	font-size: 16px;
	font-weight: 700;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.split-item-meta {
	display: flex;
	flex-wrap: wrap;
	gap: 4px 12px;
	margin-bottom: 8px;
	color: #59718a;
	font-size: 13px;
	line-height: 1.5;
}

.split-item-status {
	display: flex;
}

.split-empty {
	padding: 56px 20px;
	color: #8a9bae;
	text-align: center;
}

.split-detail-right {
	padding: 20px;
}

.split-detail-head {
	display: flex;
	gap: 16px;
	align-items: center;
	padding-bottom: 18px;
	margin-bottom: 18px;
	border-bottom: 1px solid #e6edf5;
}

.split-detail-img {
	flex: 0 0 116px;
	width: 116px;
	height: 116px;
	cursor: pointer;
}

.split-detail-title {
	min-width: 0;
}

.split-detail-title h3 {
	margin: 0 0 8px;
	color: #1f2d3d;
	font-size: 22px;
	line-height: 1.3;
	word-break: break-word;
}

.split-detail-title p {
	margin: 0 0 10px;
	color: #66788a;
}

.split-detail-grid {
	display: grid;
	grid-template-columns: repeat(2, minmax(0, 1fr));
	gap: 12px;
}

.split-detail-cell {
	padding: 12px 14px;
	border: 1px solid #e6edf5;
	border-radius: 8px;
	background: #fff;
	min-width: 0;
}

.split-detail-cell.full {
	grid-column: 1 / -1;
}

.split-label {
	margin-bottom: 6px;
	color: #8a9bae;
	font-size: 13px;
}

.split-value {
	color: #2f3f50;
	font-size: 14px;
	line-height: 1.6;
	word-break: break-word;
}

.split-actions {
	display: flex;
	flex-wrap: wrap;
	justify-content: flex-end;
	gap: 10px;
	margin-top: 18px;
}

@media (max-width: 1100px) {
	.split-list-layout {
		grid-template-columns: 1fr;
		height: auto;
	}
	.split-list-left,
	.split-detail-right {
		height: auto;
		overflow-y: visible;
	}
}

@media (max-width: 640px) {
	.split-list-item {
		grid-template-columns: 22px 34px 54px minmax(0, 1fr);
		gap: 8px;
		padding: 10px;
	}
	.split-thumb {
		width: 54px;
		height: 54px;
	}
	.split-detail-head {
		align-items: flex-start;
		flex-direction: column;
	}
	.split-detail-grid {
		grid-template-columns: 1fr;
	}
	.split-actions {
		justify-content: flex-start;
	}
}
</style>
