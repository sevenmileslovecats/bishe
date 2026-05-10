  
<template>
	<div class="main-content" :style='{"padding":"50px 30px 30px"}'>
		<!-- 列表页 -->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 20px"}' :inline="true" :model="searchForm">
				<el-row :style='{"padding":"30px 20px 10px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","flexWrap":"wrap","background":"#fff","borderWidth":"4px 0 0 0","display":"flex","width":"100%","position":"relative","borderStyle":"solid"}' >
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物资名称</label>
						<el-input v-model="searchForm.wuzimingcheng" placeholder="物资名称" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物资种类</label>
						<el-input v-model="searchForm.wuzizhonglei" placeholder="物资种类" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"margin":"0 1% 20px 0","display":"flex"}' class="select" label="物流状态" prop="wuliuzhuangtai">
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"#666","display":"inline-block","lineHeight":"40px","fontSize":"16px","fontWeight":"500","height":"40px"}' class="item-label">物流状态</label>
						<el-select clearable v-model="searchForm.wuliuzhuangtai" placeholder="请选择物流状态" >
							<el-option v-for="(item,index) in wuliuzhuangtaiOptions" v-bind:key="index" :label="item" :value="item"></el-option>
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
					<el-button class="add" v-if="isAuth('chukufenbo','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia16" :style='{"padding":"10px","margin":"0 2px","color":"#ff9365","borderRadius":"100%","background":"#ff936510","fontSize":"18px","fontWeight":"500","height":"40px"}'></span>
						添加
					</el-button>
					<el-button class="del" v-if="isAuth('chukufenbo','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"padding":"10px","margin":"0 2px","color":"#ea6464","borderRadius":"100%","background":"#ea646410","fontSize":"18px","fontWeight":"500","height":"40px"}'></span>
						批量删除
					</el-button>


				</el-row>
			</el-form>
			<div :style='{"padding":"20px","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","background":"#fff","borderWidth":"4px 0 0","width":"100%","borderStyle":"solid"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#eee","borderRadius":"10px","borderWidth":"1px 0 0 0px","background":"#fff","width":"100%","borderStyle":"solid"}' 
					:border='false'
					v-if="isAuth('chukufenbo','查看')"
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="序号" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'
												prop="shenlingbianhao"
						label="申领编号">
						<template slot-scope="scope">
							{{scope.row.shenlingbianhao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="wuzimingcheng"
						label="物资名称">
						<template slot-scope="scope">
							{{scope.row.wuzimingcheng}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="wuzizhonglei"
						label="物资种类">
						<template slot-scope="scope">
							{{scope.row.wuzizhonglei}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="jigouzhanghao"
						label="机构账号">
						<template slot-scope="scope">
							{{scope.row.jigouzhanghao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="jigoumingcheng"
						label="机构名称">
						<template slot-scope="scope">
							{{scope.row.jigoumingcheng}}
						</template>
					</el-table-column>
					<el-table-column  :resizable='true' prop="wuzitupian" width="200" label="物资图片">
						<template slot-scope="scope">
							<div v-if="scope.row.wuzitupian">
								<img v-if="scope.row.wuzitupian.substring(0,4)=='http'&&scope.row.wuzitupian.split(',w').length>1" :src="scope.row.wuzitupian" width="100" height="100" style="object-fit: cover" @error="$event.target.style.display='none'" @click="imgPreView(scope.row.wuzitupian)">
								<img v-else-if="scope.row.wuzitupian.substring(0,4)=='http'" :src="scope.row.wuzitupian.split(',')[0]" width="100" height="100" style="object-fit: cover" @error="$event.target.style.display='none'" @click="imgPreView(scope.row.wuzitupian.split(',')[0])">
								<img v-else :src="$base.url+scope.row.wuzitupian.split(',')[0]" width="100" height="100" style="object-fit: cover" @error="$event.target.style.display='none'" @click="imgPreView($base.url+scope.row.wuzitupian.split(',')[0])">
							</div>
							<div v-else>无图片</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="wuzishuliang"
						label="出库数量">
						<template slot-scope="scope">
							{{scope.row.wuzishuliang}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' prop="chukudan" label="出库单">
						<template slot-scope="scope">
							<el-button v-if="scope.row.chukudan" type="text" size="small" @click="download(scope.row.chukudan )">下载</el-button>
							<span v-else >无</span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="chukushijian"
						label="出库时间">
						<template slot-scope="scope">
							{{scope.row.chukushijian}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="wuliuzhuangtai"
						label="物流状态">
						<template slot-scope="scope">
							{{scope.row.wuliuzhuangtai}}
						</template>
					</el-table-column>
					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="view" v-if=" isAuth('chukufenbo','查看')" type="success" @click="addOrUpdateHandler(scope.row.id,'info')">
								<span class="icon iconfont icon-chakan14" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","height":"40px"}'></span>
								详情
							</el-button>
							<el-button class="btn8" v-if="isAuth('chukufenbo','接收')" @click="jieshouxinxiCrossAddOrUpdateHandler(scope.row,'cross','','','wuliuzhuangtai','已签收','已签收,配送中'.split(',')[0])" type="success">
								<span class="icon iconfont icon-xihuan" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","height":"40px"}'></span>
								接收
							</el-button>
							<el-button class="btn8" v-if="isAuth('chukufenbo','异议反馈')" @click="yiyifankuiCrossAddOrUpdateHandler(scope.row,'cross','','','','')" type="success">
								<span class="icon iconfont icon-xihuan" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","height":"40px"}'></span>
								异议反馈
							</el-button>
							<el-button class="edit" v-if=" isAuth('chukufenbo','修改') " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","height":"40px"}'></span>
								修改
							</el-button>




							<el-button class="del" v-if="isAuth('chukufenbo','删除')" type="primary" @click="deleteHandler(scope.row.id)">
								<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","height":"40px"}'></span>
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
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

		<jieshouxinxi-cross-add-or-update v-if="jieshouxinxiCrossAddOrUpdateFlag" :parent="this" ref="jieshouxinxiCrossaddOrUpdate"></jieshouxinxi-cross-add-or-update>
		<yiyifankui-cross-add-or-update v-if="yiyifankuiCrossAddOrUpdateFlag" :parent="this" ref="yiyifankuiCrossaddOrUpdate"></yiyifankui-cross-add-or-update>




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
	import jieshouxinxiCrossAddOrUpdate from "../jieshouxinxi/add-or-update";
	import yiyifankuiCrossAddOrUpdate from "../yiyifankui/add-or-update";
	import {
		Loading
	} from 'element-ui';
	export default {
		data() {
			return {
				indexQueryCondition: '',
				wuliuzhuangtaiOptions: [],
				searchForm: {
					key: ""
				},
				form:{},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				addOrUpdateFlag:false,
				jieshouxinxiCrossAddOrUpdateFlag: false,
				yiyifankuiCrossAddOrUpdateFlag: false,
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
			jieshouxinxiCrossAddOrUpdate,
			yiyifankuiCrossAddOrUpdate,
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
			jieshouxinxiCrossAddOrUpdateHandler(row,type,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue){
				this.showFlag = false;
				this.addOrUpdateFlag = false;
				this.jieshouxinxiCrossAddOrUpdateFlag = true;
				this.$storage.set('crossObj',row);
				this.$storage.set('crossTable','chukufenbo');
				this.$storage.set('statusColumnName',statusColumnName);
				this.$storage.set('statusColumnValue',statusColumnValue);
				this.$storage.set('tips',tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = this.$storage.getObj('crossObj');
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$message({
								message: tips,
								type: "warning",
								duration: 1500,
								onClose: () => {
									this.getDataList();
								}
							});
							this.showFlag = true;
							this.jieshouxinxiCrossAddOrUpdateFlag = false;
							return;
						}
					}
				}
				this.$nextTick(() => {
					this.$refs.jieshouxinxiCrossaddOrUpdate.init(row.id,type);
				});
			},
			yiyifankuiCrossAddOrUpdateHandler(row,type,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue){
				this.showFlag = false;
				this.addOrUpdateFlag = false;
				this.yiyifankuiCrossAddOrUpdateFlag = true;
				this.$storage.set('crossObj',row);
				this.$storage.set('crossTable','chukufenbo');
				this.$storage.set('statusColumnName',statusColumnName);
				this.$storage.set('statusColumnValue',statusColumnValue);
				this.$storage.set('tips',tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = this.$storage.getObj('crossObj');
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$message({
								message: tips,
								type: "warning",
								duration: 1500,
								onClose: () => {
									this.getDataList();
								}
							});
							this.showFlag = true;
							this.yiyifankuiCrossAddOrUpdateFlag = false;
							return;
						}
					}
				}
				this.$nextTick(() => {
					this.$refs.yiyifankuiCrossaddOrUpdate.init(row.id,type);
				});
			},
			init () {
				this.wuliuzhuangtaiOptions = "已签收,配送中".split(',')
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
				if(this.searchForm.wuzimingcheng!='' && this.searchForm.wuzimingcheng!=undefined){
					params['wuzimingcheng'] = '%' + this.searchForm.wuzimingcheng + '%'
				}
				if(this.searchForm.wuzizhonglei!='' && this.searchForm.wuzizhonglei!=undefined){
					params['wuzizhonglei'] = '%' + this.searchForm.wuzizhonglei + '%'
				}
				params['sort'] = 'chukushijian';
				params['order'] = 'desc';
				if(this.searchForm.wuliuzhuangtai!='' && this.searchForm.wuliuzhuangtai!=undefined){
					params['wuliuzhuangtai'] = this.searchForm.wuliuzhuangtai
				}
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "chukufenbo/page",
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
						url: "chukufenbo/delete",
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
</style>
