<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'>>'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div v-if="centerType ||true " class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui12"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="list-preview">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" false " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						搜索
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('discussshiyongfankui','新增')" type="primary" @click="add('/index/discussshiyongfankuiAdd')">
						<span class="icon iconfont icon-tianjia7"></span>
						新增
					</el-button>
  
				</div>
			</el-form>
			<div class="select2">
				<div class="select2-list" v-for="(item,index) in selectOptionsList" :key="index">
					<div class="label">{{item.name}}：</div>
					<div class="item-body">
						<div class="item" @click="selectClick2(item,-1)" :class="item.check ==-1 ? 'active' : ''">全部</div>
						<div class="item" @click="selectClick2(item,index1)" :class="item.check == index1 ? 'active' : ''" v-for="item1,index1 in item.list" :key="index1">{{item1}}</div>
					</div>
				</div>
			</div>
			<div class="list">
				<el-table class="tables" :stripe='false'
					:style='{"width":"100%","padding":"0","borderColor":"#eee","borderStyle":"solid","borderWidth":"1px 0 0 1px","background":"#fff"}' 
					:border='true' 
					:data="dataList">
					<el-table-column :resizable='true' :sortable='false'
						prop="nickname"
						label="用户名" show-overflow-tooltip>
						<template slot-scope="scope">
							<span class="ql-snow ql-editor" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" v-html="scope.row.nickname"></span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false'
						prop="content"
						label="评论内容" show-overflow-tooltip>
						<template slot-scope="scope">
							<span class="ql-snow ql-editor" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" v-html="scope.row.content"></span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false'
						prop="reply"
						label="回复内容" show-overflow-tooltip>
						<template slot-scope="scope">
							<span class="ql-snow ql-editor" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" v-html="scope.row.reply"></span>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false' prop="istop" label="是否置顶">
						<template slot-scope="scope">
							<el-switch
								v-model="scope.row.istop"
								:width="60"
								active-icon-class="el-icon-edit"
								inactive-icon-class="el-icon-share"
								active-text="开"
								inactive-text="关"
								:active-value="1"
								:inactive-value="0"
								active-color="#51b1f0"
								inactive-color="#999"
								@change="(e)=>discussistopChange(e,scope.row)">
							</el-switch>
						</template>
					</el-table-column>
					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="table-view" type="success" @click.native="toDetail(scope.row)">
								<span class="icon iconfont icon-fangdajing02"></span>
								查看
							</el-button>
							<el-button class="table-edit" type="success" @click.native="editClick(scope.row)">
								<span class="icon iconfont icon-xiugai17"></span>
								回复
							</el-button>
							<el-button class="table-del" type="success" @click.native="delClick(scope.row)" v-if="btnAuth('shiyongfankui','删除评论')">
								<span class="icon iconfont icon-shanchu6"></span>
								删除
							</el-button>
							<el-button class="table-btn1" type="success" @click.native="discussClick(scope.row)" v-if="btnAuth('discussshiyongfankui','查看评论')">
								<span class="icon iconfont icon-xiaoxi-zhihui"></span>
								查看评论
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>

	
			<el-pagination
				background
				id="pagination"
				class="pagination"
				:pager-count="7"
				:page-size="pageSize"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="true"
				:layout='["total","prev","pager","next","sizes","jumper"].join()'
				:total="total"
				:page-sizes="pageSizes"
				@current-change="curChange"
				@size-change="sizeChange"
				@prev-click="prevClick"
				@next-click="nextClick"
				></el-pagination>
		</div>
		<el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>
<script>
	import axios from 'axios';
	export default {
		//数据集合
		data() {
			return {
				selectIndex2: 0,
				selectOptionsList: [],
				layouts: '',
				swiperIndex: -1,
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '使用反馈评论'
					}
				],
				formSearch: {
				},
				fenlei: [],
				dataList: [],
				total: 1,
				pageSize: 12,
				pageSizes: [],
				totalPage: 1,
				curFenlei: '全部',
				isPlain: false,
				indexQueryCondition: '',
				timeRange: [],
				centerType:false,
				previewImg: '',
				previewVisible: false,
				sortType: 'id',
				sortOrder: 'desc',
			}
		},
		async created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0){
				this.centerType = true
			}
			this.baseUrl = this.$config.baseUrl;
			if(this.centerType) {
			}
			await this.getFenlei();
			let fenlei = '全部'
			if(this.$route.query.homeFenlei){
				fenlei = this.$route.query.homeFenlei
			}
			this.getList(1, fenlei);
		},
		watch:{
			$route(newValue){
				this.getList(1, newValue.query.homeFenlei);
			}
		},
		computed: {
			role(){
				return localStorage.getItem('frontRole');
			},
			username() {
				return localStorage.getItem('username')
			}
		},
		//方法集合
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			selectClick2(row,index) {
				row.check = index
				if(index == -1){
					this.formSearch[row.tableName] = ''
				}else {
					this.formSearch[row.tableName] = row.list[index]
				}
				this.getList()
			},
			add(path) {
				let query = {}
				if(this.centerType){
					query.centerType = 1
				}
				this.$router.push({path: path,query:query});
			},
			async getFenlei() {
			},
			getList(page, fenlei, ref = '') {
				let params = {
					page,
					limit: this.pageSize,
				};
				let searchWhere = {};
				params.refid = this.$route.query.refid
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`discussshiyongfankui/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(async res => {
					if (res.data.code == 0) {
						this.dataList = res.data.data.list;
						this.total = Number(res.data.data.total);
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			sortClick(type){
				if(this.sortType==type){
					if(this.sortOrder == 'desc'){
						this.sortOrder = 'asc'
					}else{
						this.sortOrder = ''
						this.sortType = ''
					}
				}else{
					this.sortType = type
					this.sortOrder = 'desc'
				}
				this.getList(1, '全部')
			},
			curChange(page) {
				this.getList(page);
			},
			prevClick(page) {
				this.getList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getList(1);
			},
			nextClick(page) {
				this.getList(page);
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
			},
			toDetail(item) {
				let params = {
					id: item.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discussshiyongfankuiDetail', query: params});
			},
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			backClick() {
				history.back()
			},

			discussClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discussdiscussshiyongfankui', query: params});
			},
			editClick (row){
				this.$router.push(`/index/discussshiyongfankuiAdd?type=edit&&id=${row.id}`);
			},
			async delClick (row){
				await this.$confirm('是否删除此评论？') .then(_ => {
					this.$http.post('discussshiyongfankui/delete', [row.id]).then(async res => {
						if (res.data.code == 0) {
							await this.$http.get('shiyongfankui/info/' + row.refid).then(async rs=>{
								rs.data.data.discussnum--
								await this.$http.post('shiyongfankui/update',rs.data.data).then(rs2=>{})
							})
							this.$message({
								message: "操作成功",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.getList(1, '全部');
								}
							});
						}
					});
				}).catch(_ => {});
			},
			discussistopChange(e,row){
				this.$http.post('discussshiyongfankui/update',row).then(res=>{})
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.list-preview {
		padding: 0 7%;
		margin: 0px auto;
		align-content: flex-start;
		background: none;
		display: flex;
		width: 100%;
		align-items: flex-start;
		position: relative;
		flex-wrap: wrap;
		.list-form-pv {
			padding: 0 0 20px;
			margin: 10px auto;
			background: none;
			display: flex;
			width: 100%;
			border-color: #ddd;
			border-width: 0 0 1px;
			justify-content: center;
			align-items: center;
			border-style: solid;
			flex-wrap: wrap;
			height: auto;
			.list-item {
				margin: 0 5px 0 0;
				/deep/.el-form-item__content {
					display: flex;
				}
				.lable {
					padding: 0;
					color: #333;
					white-space: nowrap;
					display: inline-block;
					width: auto;
					font-size: 16px;
					line-height: 42px;
				}
				.el-input {
					width: 100%;
				}
				.datetimerange {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 3px 0px;
					outline: none;
					background: #fff;
					width: auto;
					justify-content: center;
				}
				.el-input /deep/ .el-input__inner {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 0 10px;
					margin: 0;
					outline: none;
					color: #333;
					width: 140px;
					font-size: 14px;
					line-height: 42px;
					height: 42px;
				}
				.el-select {
					width: 100%;
				}
				.el-select /deep/ .el-input__inner {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 0 10px;
					margin: 0;
					outline: none;
					color: #333;
					width: 140px;
					font-size: 14px;
					line-height: 42px;
					height: 42px;
				}
				.el-date-editor {
					width: 100%;
				}
				.el-date-editor /deep/ .el-input__inner {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 0 30px;
					margin: 0;
					outline: none;
					color: #333;
					width: 140px;
					font-size: 14px;
					line-height: 42px;
					height: 42px;
				}
			}
			.list-btn-box {
				display: flex;
			}
			.list-search-btn {
				cursor: pointer;
				border: 0;
				border-radius: 4px;
				padding: 0px 10px;
				margin: 0 10px 0 0;
				outline: none;
				color: #fff;
				background: #6aac5a;
				width: auto;
				font-size: 16px;
				line-height: 42px;
				height: 42px;
				.icon {
					margin: 0 3px 0 0;
					color: #fff;
					font-size: 14px;
				}
			}
			.list-add-btn {
				cursor: pointer;
				border: 0;
				border-radius: 4px;
				padding: 0px 10px;
				margin: 0 10px 0 0;
				outline: none;
				color: #fff;
				background: #53cab2;
				width: auto;
				font-size: 14px;
				line-height: 42px;
				height: 42px;
				.icon {
					margin: 0 3px 0 0;
					color: #fff;
					font-size: 14px;
				}
			}
		}
		.select2 {
			padding: 10px 0;
			background: #fff;
			width: 100%;
			height: auto;
			.select2-list {
				padding: 0;
				margin: 0 0 15px 0;
				background: none;
				display: inline-flex;
				width: 100%;
				position: relative;
				height: auto;
				.label {
					padding: 0 5px;
					color: #333;
					white-space: nowrap;
					display: inline-block;
					font-size: 16px;
					line-height: 32px;
				}
				.item-body {
					display: inline-block;
					width: 100%;
					flex-wrap: wrap;
					height: auto;
					.item {
						cursor: pointer;
						border-radius: 4px;
						padding: 0 5px;
						color: #333;
						background: none;
						display: inline-block;
						font-size: 16px;
						line-height: 32px;
					}
					.item:hover {
						color: #fff;
						background: #6aac5a;
					}
					.item.active {
						color: #fff;
						background: #6aac5a;
						display: inline-block;
					}
				}
			}
		}
		.sort_view {
			border: 0px solid #ddd;
			padding: 0;
			margin: 10px auto;
			color: #333;
			background: #fff;
			width: 100%;
			font-size: 16px;
			border-width: 0 0 1px;
			text-align: center;
			order: 2;
			.click-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
		}
		.list {
			margin: 20px 0 0;
			background: none;
			flex: 1;
			width: calc(100% - 350px);
			order: 4;
			.index-pv1 .animation-box {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				z-index: initial;
			}
				
			.index-pv1 .animation-box:hover {
				transform: rotate(0) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
				
			.index-pv1 .animation-box img {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
			}
			
			.index-pv1 .animation-box img:hover {
				transform: rotate(0) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
			.el-table /deep/ .el-table__header-wrapper thead {
				color: #333;
				font-weight: 500;
				width: 100%;
			}
			
			.el-table /deep/ .el-table__header-wrapper thead tr {
				background: #fff;
			}
			
			.el-table /deep/ .el-table__header-wrapper thead tr th {
				padding: 12px 0;
				border-color: #eee;
				border-width: 0 1px 1px 0;
				border-style: solid;
				text-align: left;
			}
			
			.el-table /deep/ .el-table__header-wrapper thead tr th .cell {
				padding: 0 10px;
				word-wrap: normal;
				word-break: break-all;
				white-space: normal;
				font-weight: bold;
				display: inline-block;
				vertical-align: middle;
				width: 100%;
				line-height: 24px;
				position: relative;
				text-overflow: ellipsis;
			}
			
			
			.el-table /deep/ .el-table__body-wrapper tbody {
				width: 100%;
			}
			
			.el-table /deep/ .el-table__body-wrapper tbody tr {
				background: #fff;
			}
			
			.el-table /deep/ .el-table__body-wrapper tbody tr td {
				padding: 12px 0;
				color: #666;
				background: #fff;
				border-color: #eee;
				border-width: 0 1px 1px 0;
				border-style: solid;
				text-align: left;
			}
			
			
			.el-table /deep/ .el-table__body-wrapper tbody tr:hover td {
				padding: 12px 0;
				color: #333;
				background: rgba(64, 158, 255, .1);
				border-color: #eee;
				border-width: 0 1px 1px 0;
				border-style: solid;
				text-align: left;
			}
			
			.el-table /deep/ .el-table__body-wrapper tbody tr td {
				padding: 12px 0;
				color: #666;
				background: #fff;
				border-color: #eee;
				border-width: 0 1px 1px 0;
				border-style: solid;
				text-align: left;
			}
			
			.el-table /deep/ .el-table__body-wrapper tbody tr td .cell {
				padding: 0 10px;
				overflow: hidden;
				word-break: break-all;
				white-space: normal;
				line-height: 24px;
				text-overflow: ellipsis;
			}
			.el-table /deep/ .table-view {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: #67C23A;
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table /deep/ .table-view:hover {
				opacity: 0.8;
			}
			.el-table /deep/ .table-edit {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: rgba(64, 158, 255, 1);
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table /deep/ .table-edit:hover {
				opacity: 0.8;
			}
			.el-table /deep/ .table-del {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: rgba(255, 0, 0, 1);
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table /deep/ .table-del:hover {
				opacity: 0.8;
			}
			.el-table /deep/ .table-btn1 {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: rgba(255, 128, 0, 1);
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table /deep/ .table-btn1:hover {
				opacity: 0.8;
			}
			.el-table .el-switch {
				display: inline-flex;
				vertical-align: middle;
				line-height: 30px;
				position: relative;
				align-items: center;
				height: 30px;
			}
			.el-table .el-switch /deep/ .el-switch__label--left {
				cursor: pointer;
				margin: 0 10px 0 0;
				color: #333;
				font-weight: 500;
				display: inline-block;
				vertical-align: middle;
				font-size: 16px;
				transition: .2s;
				height: 30px;
			}
			.el-table .el-switch /deep/ .el-switch__label--right {
				cursor: pointer;
				margin: 0 0 0 10px;
				color: #333;
				font-weight: 500;
				display: inline-block;
				vertical-align: middle;
				font-size: 16px;
				transition: .2s;
				height: 30px;
			}
			.el-table .el-switch /deep/ .el-switch__core {
				border: 1px solid #0000ff;
				cursor: pointer;
				border-radius: 15px;
				margin: 0;
				outline: 0;
				background: #0000ff;
				display: inline-block;
				width: 60px;
				box-sizing: border-box;
				transition: border-color .3s,background-color .3s;
				height: 30px;
			}
			.el-table .el-switch /deep/ .el-switch__core::after {
				border-radius: 100%;
				top: 1px;
				left: 1px;
				background: #FFF;
				width: 26px;
				position: absolute;
				transition: all .3s;
				height: 26px;
			}
			.el-table .el-switch.is-checked /deep/ .el-switch__core::after {
				margin: 0 0 0 -27px;
				left: 100%;
			}
			
			.el-table .el-rate /deep/ .el-rate__item {
				cursor: pointer;
				display: inline-block;
				vertical-align: middle;
				font-size: 0;
				position: relative;
			}
			.el-table .el-rate /deep/ .el-rate__item .el-rate__icon {
				margin: 0 3px;
				display: inline-block;
				font-size: 18px;
				position: relative;
				transition: .3s;
			}
		}
	}
.breadcrumb-preview {
	max-width: 1280px;
	margin: 0 auto;
	padding: 18px 24px 0;
	box-sizing: border-box;
}

.back_box {
	max-width: 1280px;
	margin: 12px auto 0;
	padding: 0 24px;
	text-align: right;
	box-sizing: border-box;
	.backBtn {
		border: 0;
		border-radius: 6px;
		color: #fff;
		background: #5ba84d;
		height: 34px;
		padding: 0 16px;
	}
}

.list-preview {
	max-width: 1280px;
	width: 100% !important;
	margin: 0 auto !important;
	padding: 20px 24px 44px !important;
	box-sizing: border-box;
	display: flex;
	flex-wrap: wrap;
	gap: 16px;
	background: #f7faf8;
	.list-form-pv {
		width: 100% !important;
		min-height: 0;
		margin: 0 !important;
		padding: 14px 18px !important;
		border: 1px solid #e8efe8;
		border-radius: 8px;
		background: #fff;
		box-shadow: 0 8px 24px rgba(34, 62, 43, .06);
		display: flex;
		align-items: center;
		justify-content: flex-end;
		order: 1;
		.list-btn-box {
			margin: 0 !important;
			display: flex;
			flex-wrap: wrap;
			gap: 8px;
			align-items: center;
		}
		.el-button {
			height: 36px;
			padding: 0 16px;
			border: 0;
			border-radius: 6px;
			font-size: 14px;
		}
		.list-add-btn {
			background: #278f7f;
		}
	}
	.select2 {
		width: 100% !important;
		margin: 0 !important;
		padding: 12px 16px !important;
		border: 1px solid #e8efe8;
		border-radius: 8px;
		background: #fff;
		box-shadow: 0 8px 24px rgba(34, 62, 43, .05);
		order: 2;
		.select2-list {
			margin: 0 !important;
			display: flex;
			align-items: flex-start;
			gap: 12px;
			.label {
				min-width: 86px;
				color: #56645b;
				font-weight: 600;
				line-height: 32px;
			}
			.item-body {
				display: flex;
				flex-wrap: wrap;
				gap: 8px;
				.item {
					margin: 0 !important;
					padding: 0 14px !important;
					border-radius: 16px !important;
					color: #52645a !important;
					background: #f4f7f4 !important;
					font-size: 14px !important;
					line-height: 30px !important;
				}
				.item:hover,
				.item.active {
					color: #fff !important;
					background: #5ba84d !important;
				}
			}
		}
	}
	.list {
		width: 100% !important;
		margin: 0 !important;
		flex: none !important;
		order: 4;
		border: 1px solid #e8efe8;
		border-radius: 8px;
		background: #fff;
		box-shadow: 0 12px 30px rgba(34, 62, 43, .08);
		overflow: hidden;
		.el-table {
			border: 0 !important;
		}
		.el-table /deep/ .el-table__header-wrapper thead tr,
		.el-table /deep/ .el-table__header-wrapper thead tr th {
			background: #f3f8f4 !important;
		}
		.el-table /deep/ .el-table__header-wrapper thead tr th {
			padding: 12px 0 !important;
			border-color: #e6eee7 !important;
			color: #26342b;
			font-weight: 700;
		}
		.el-table /deep/ .el-table__header-wrapper thead tr th .cell,
		.el-table /deep/ .el-table__body-wrapper tbody tr td .cell {
			padding: 0 14px !important;
			line-height: 22px !important;
		}
		.el-table /deep/ .el-table__body-wrapper tbody tr td {
			padding: 14px 0 !important;
			border-color: #eef2ef !important;
			color: #3e4d45 !important;
			background: #fff !important;
		}
		.el-table /deep/ .el-table__body-wrapper tbody tr.current-row td,
		.el-table /deep/ .el-table__body-wrapper tbody tr.hover-row td,
		.el-table /deep/ .el-table__body-wrapper tbody tr:hover td {
			background: #f7fbf6 !important;
		}
		.el-table /deep/ .ql-editor {
			padding: 0 !important;
			white-space: normal !important;
			overflow: hidden !important;
			text-overflow: ellipsis !important;
			line-height: 22px !important;
			color: #33443a;
		}
		.el-table /deep/ .table-view,
		.el-table /deep/ .table-edit,
		.el-table /deep/ .table-del,
		.el-table /deep/ .table-btn1 {
			height: 32px;
			padding: 0 12px;
			border: 0;
			border-radius: 6px;
			font-size: 13px;
			margin: 0 6px 4px 0;
		}
		.el-table /deep/ .table-view {
			background: #4f9f45 !important;
		}
		.el-table /deep/ .table-edit {
			background: #278f7f !important;
		}
		.el-table /deep/ .table-del {
			background: #d9534f !important;
		}
		.el-table /deep/ .table-btn1 {
			background: #e89232 !important;
		}
		.el-table .el-switch /deep/ .el-switch__core {
			border-color: #5ba84d !important;
			background: #5ba84d !important;
		}
	}
	.pagination {
		width: 100%;
		margin: 8px 0 0 !important;
		padding: 14px 0 0;
		text-align: center;
		order: 5;
	}
}

@media (max-width: 900px) {
	.breadcrumb-preview,
	.back_box,
	.list-preview {
		padding-left: 12px !important;
		padding-right: 12px !important;
	}
	.list-preview .list {
		overflow-x: auto;
	}
	.list-preview .list .el-table {
		min-width: 900px;
	}
}
</style>
