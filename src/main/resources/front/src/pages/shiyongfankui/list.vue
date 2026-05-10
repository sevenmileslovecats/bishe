<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'>>'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div v-if="centerType " class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui12"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="list-preview">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="list-item" >
					<div class="lable">申领编号：</div>
					<el-input v-model="formSearch.shenlingbianhao" placeholder="申领编号" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">物资名称：</div>
					<el-input v-model="formSearch.wuzimingcheng" placeholder="物资名称" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">物资种类：</div>
					<el-input v-model="formSearch.wuzizhonglei" placeholder="物资种类" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" true " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						搜索
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('shiyongfankui','新增')" type="primary" @click="add('/index/shiyongfankuiAdd')">
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
			<div class="sort_view">
				<el-button class="collect-sort-btn" @click="sortClick('storeupnum')">
					<span class="icon iconfont icon-shoucang10" v-if="sortType!='storeupnum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='storeupnum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='storeupnum'&&sortOrder=='asc'"></span>
					<span class="text">收藏数</span>
				</el-button>
			</div>
			<div class="list">
				<div v-if="dataList.length" class="feedback-waterfall">
					<div
						class="feedback-card"
						v-for="item in dataList"
						:key="item.id"
						@click="toDetail(item)"
					>
						<div class="feedback-cover" @click.stop="getImageUrl(item) && imgPreView(getImageUrl(item))">
							<img v-if="getImageUrl(item)" :src="getImageUrl(item)" @error="$event.target.style.display='none'">
							<span v-else>暂无图片</span>
						</div>
						<div class="feedback-card-body">
							<h3>{{formatCardText(item.wuzimingcheng, '未命名物资')}}</h3>
							<p>{{formatCardText(item.jutiyongtu, '暂无用途说明')}}</p>
							<div class="feedback-time">{{formatCardText(item.fankuishijian, '暂无反馈时间')}}</div>
						</div>
					</div>
				</div>
				<div v-else class="feedback-empty">暂无使用反馈数据</div>
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
						name: '使用反馈'
					}
				],
				formSearch: {
					shenlingbianhao: '',
					wuzimingcheng: '',
					wuzizhonglei: '',
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
				sortType: 'fankuishijian',
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
				if (this.formSearch.shenlingbianhao != ''&&this.formSearch.shenlingbianhao != undefined) searchWhere.shenlingbianhao = '%' + this.formSearch.shenlingbianhao + '%';
				if (this.formSearch.wuzimingcheng != ''&&this.formSearch.wuzimingcheng != undefined) searchWhere.wuzimingcheng = '%' + this.formSearch.wuzimingcheng + '%';
				if (this.formSearch.wuzizhonglei != ''&&this.formSearch.wuzizhonglei != undefined) searchWhere.wuzizhonglei = '%' + this.formSearch.wuzizhonglei + '%';
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`shiyongfankui/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(async res => {
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
			getImageUrl(row) {
				if (!row || !row.changjingzhaopian) {
					return ''
				}
				if (row.changjingzhaopian.substring(0, 4) === 'http' && row.changjingzhaopian.split(',w').length > 1) {
					return row.changjingzhaopian
				}
				if (row.changjingzhaopian.substring(0, 4) === 'http') {
					return row.changjingzhaopian.split(',')[0]
				}
				return this.baseUrl + row.changjingzhaopian.split(',')[0]
			},
			formatCardText(value, fallback) {
				return value !== undefined && value !== null && value !== '' ? value : fallback
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
				this.$router.push({path: '/index/shiyongfankuiDetail', query: params});
			},
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			backClick() {
				this.$router.push({path: '/index/center'});
			},

			discussClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discussshiyongfankui', query: params});
			},
			chapterClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chaptershiyongfankui', query: params});
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
			.collect-sort-btn {
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
			.el-table /deep/ .table-btn5 {
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
			.el-table /deep/ .table-btn5:hover {
				opacity: 0.8;
			}
		}
	}
.breadcrumb-preview {
	max-width: 1760px;
	margin: 0 auto;
	padding: 18px 24px 0;
	box-sizing: border-box;
}

.back_box {
	max-width: 1760px;
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
	max-width: 1760px;
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
		margin: 0 !important;
		padding: 18px 18px 8px !important;
		border: 1px solid #e8efe8;
		border-radius: 8px;
		background: #fff;
		box-shadow: 0 8px 24px rgba(34, 62, 43, .06);
		display: flex;
		align-items: flex-end;
		flex-wrap: wrap;
		gap: 12px 16px;
		order: 1;
		.list-item {
			margin: 0 0 10px !important;
			.lable {
				margin: 0 0 6px !important;
				color: #4d5d53;
				font-size: 13px !important;
				line-height: 18px !important;
				font-weight: 600;
			}
			.el-input {
				width: 190px !important;
			}
			/deep/ .el-input__inner {
				height: 36px;
				line-height: 36px;
				border-color: #dfe9df;
				border-radius: 6px;
			}
		}
		.list-btn-box {
			margin: 0 0 10px !important;
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
		.list-search-btn {
			background: #4f9f45;
		}
		.list-add-btn {
			background: #278f7f;
		}
		.list-static-btn {
			color: #35633b;
			background: #edf7ed;
			border: 1px solid #cfe6cf;
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
	.sort_view {
		width: 100% !important;
		margin: 0 !important;
		padding: 0 !important;
		order: 3;
		.collect-sort-btn {
			height: 34px;
			padding: 0 14px;
			border: 1px solid #dbe9db;
			border-radius: 17px;
			color: #47604f;
			background: #fff;
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
		overflow-x: auto;
		.el-table {
			border: 0 !important;
			min-width: 1120px;
		}
		.el-table /deep/ .el-table__header-wrapper thead tr,
		.el-table /deep/ .el-table__header-wrapper thead tr th {
			background: #f3f8f4 !important;
		}
		.el-table /deep/ .el-table__header-wrapper thead tr th {
			padding: 10px 0 !important;
			border-color: #e6eee7 !important;
			color: #26342b;
			font-weight: 700;
		}
		.el-table /deep/ .el-table__header-wrapper thead tr th .cell,
		.el-table /deep/ .el-table__body-wrapper tbody tr td .cell {
			padding: 0 12px !important;
			line-height: 22px !important;
		}
		.el-table /deep/ .el-table__body-wrapper tbody tr td {
			padding: 10px 0 !important;
			border-color: #eef2ef !important;
			color: #3e4d45 !important;
			background: #fff !important;
		}
		.el-table /deep/ .el-table__body-wrapper tbody tr:hover td {
			background: #f7fbf6 !important;
		}
		.el-table /deep/ img {
			width: 64px !important;
			height: 64px !important;
			border-radius: 6px;
			object-fit: cover;
			display: block;
			background: #f0f4f1;
			box-shadow: inset 0 0 0 1px #e5ebe6;
			cursor: pointer;
		}
		.el-table /deep/ .el-button {
			height: 32px;
			padding: 0 12px;
			border: 0;
			border-radius: 6px;
			font-size: 13px;
		}
		.el-table /deep/ .table-view {
			background: #4f9f45 !important;
		}
		.el-table /deep/ .table-btn1,
		.el-table /deep/ .table-btn5 {
			background: #e89232 !important;
		}
		.el-table /deep/ .el-tag {
			border-radius: 14px;
			padding: 0 12px;
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
	.list-preview .list-form-pv .list-item,
	.list-preview .list-form-pv .list-item .el-input {
		width: 100% !important;
	}
	.list-preview .list-form-pv .list-btn-box {
		width: 100%;
	}
}

/* business-list-polish */
.list-preview {
	max-width: 1760px;
	margin: 0 auto;
	padding: 22px 24px 28px !important;
	background: #f5f7fb !important;
	box-sizing: border-box;
}
.list-form-pv {
	padding: 16px 18px !important;
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #fff !important;
	box-shadow: 0 8px 22px rgba(32, 45, 64, .06) !important;
}
.list-form-pv .list-item,
.list-form-pv .el-form-item {
	margin: 0 14px 12px 0 !important;
}
.list-form-pv .lable,
.select2 .label {
	color: #44505c !important;
	font-size: 14px !important;
	font-weight: 700 !important;
}
.list-form-pv /deep/ .el-input__inner {
	height: 36px !important;
	line-height: 36px !important;
	border-color: #dfe7ef !important;
	border-radius: 6px !important;
	color: #334155 !important;
}
.list-btn-box .el-button,
.list-search-btn,
.list-add-btn,
.list-static-btn,
.list-recommend-btn {
	height: 36px !important;
	line-height: 36px !important;
	padding: 0 14px !important;
	margin: 0 8px 8px 0 !important;
	border: 0 !important;
	border-radius: 6px !important;
	font-size: 14px !important;
	font-weight: 700;
}
.select2 {
	padding: 12px 16px !important;
	margin: 14px 0 !important;
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #fff !important;
}
.select2 .item-body .item {
	padding: 6px 12px !important;
	border-radius: 999px !important;
	color: #49604f !important;
	background: #f2f7f2 !important;
}
.select2 .item-body .item.active {
	color: #fff !important;
	background: #4f9f45 !important;
}
.list-preview .list {
	padding: 14px !important;
	border: 1px solid #e6edf5 !important;
	border-radius: 8px !important;
	background: #f8fafc !important;
	box-shadow: none !important;
	overflow: visible;
}
.feedback-waterfall {
	column-count: 4;
	column-gap: 14px;
	width: 100%;
}
.feedback-card {
	display: inline-block;
	width: 100%;
	margin: 0 0 14px;
	border: 1px solid #e3eaf2;
	border-radius: 8px;
	background: #fff;
	box-shadow: 0 6px 16px rgba(32, 45, 64, .05);
	overflow: hidden;
	cursor: pointer;
	transition: transform .18s ease, box-shadow .18s ease, border-color .18s ease;
	break-inside: avoid;
}
.feedback-card:hover {
	transform: translateY(-2px);
	border-color: #cfddea;
	box-shadow: 0 10px 22px rgba(32, 45, 64, .1);
}
.feedback-cover {
	width: 100%;
	min-height: 136px;
	background: #edf3f7;
	cursor: zoom-in;
}
.feedback-cover img {
	display: block;
	width: 100%;
	height: auto;
	min-height: 136px;
	max-height: 220px;
	object-fit: cover;
}
.feedback-cover span {
	display: flex;
	align-items: center;
	justify-content: center;
	min-height: 136px;
	color: #9aa6b2;
	font-size: 13px;
}
.feedback-card-body {
	padding: 10px 12px 12px;
}
.feedback-card-body h3 {
	margin: 0 0 6px;
	color: #22302a;
	font-size: 15px;
	font-weight: 800;
	line-height: 22px;
}
.feedback-card-body p {
	display: -webkit-box;
	margin: 0 0 10px;
	overflow: hidden;
	color: #526170;
	font-size: 13px;
	line-height: 20px;
	text-overflow: ellipsis;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
}
.feedback-time {
	color: #8a96a3;
	font-size: 12px;
	line-height: 18px;
}
.feedback-empty {
	display: flex;
	align-items: center;
	justify-content: center;
	min-height: 180px;
	color: #8a96a3;
	font-size: 14px;
}
.tables {
	min-width: 1280px;
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
.tables /deep/ .table-view,
.tables /deep/ .view {
	background: #2f80c9 !important;
	color: #fff !important;
}
.tables /deep/ .table-edit,
.tables /deep/ .edit {
	background: #4f9f45 !important;
	color: #fff !important;
}
.tables /deep/ .table-del,
.tables /deep/ .del {
	background: #d9534f !important;
	color: #fff !important;
}
.tables /deep/ .table-trace,
.tables /deep/ .table-btn5,
.tables /deep/ .btn8,
.tables /deep/ .btn18 {
	background: #278f7f !important;
	color: #fff !important;
}
.pagination {
	padding: 14px 0 0 !important;
	margin: 16px 0 0 !important;
	justify-content: flex-end !important;
}
@media (max-width: 1280px) {
	.feedback-waterfall {
		column-count: 3;
	}
}
@media (max-width: 960px) {
	.list-preview {
		padding: 14px !important;
	}
	.list-form-pv .list-item,
	.list-form-pv .el-form-item,
	.list-form-pv /deep/ .el-input {
		width: 100% !important;
	}
	.feedback-waterfall {
		column-count: 2;
	}
}
@media (max-width: 640px) {
	.feedback-waterfall {
		column-count: 1;
	}
	.feedback-cover,
	.feedback-cover img,
	.feedback-cover span {
		min-height: 140px;
	}
}
</style>
