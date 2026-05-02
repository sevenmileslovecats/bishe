<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'>>'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
	
		<div class="news-preview-pv">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="search-item">
					<el-input v-model="title" placeholder="标题"></el-input>
				</el-form-item>
				<el-button class="search-btn" type="primary" @click="getNewsList(1)">
					<span class="icon iconfont icon-chakan14"></span>
					搜索
				</el-button>
			</el-form>
			
			<!-- category -->
			<!-- 样式五 -->
			<div class="list5 index-pv1">
				<div v-for="item in newsList" :key="item.id" @click="toNewsDetail(item)" class="list-item animation-box">
					<img :src="baseUrl + (item.picture?item.picture.split(',')[0]:'')" >
					<div class="infoBox">
						<div class="name">{{item.title}}</div>
						<div class="desc">{{item.introduction}}</div>
						<div class="time_item">
							<span class="icon iconfont icon-shijian21"></span>
							<span class="label">发布时间：</span>
							<span class="text">{{item.addtime.split(' ')[0]}}</span>
						</div>
						<div class="publisher_item">
							<span class="icon iconfont icon-geren16"></span>
							<span class="label">发布人：</span>
							<span class="text">{{item.name}}</span>
						</div>
						<div class="tags">查看更多</div>
					</div>
				</div>
			</div>
		
			<el-pagination
				background
				id="pagination" class="pagination"
				:pager-count="7"
				:page-size="pageSize"
				:page-sizes="pageSizes"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="true"
				:layout='["total","prev","pager","next","sizes","jumper"].join()'
				:total="total"
				@current-change="curChange"
				@size-change="sizeChange"
				@prev-click="prevClick"
				@next-click="nextClick"
				></el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		//数据集合
		data() {
			return {
				baseUrl: this.$config.baseUrl,
				breadcrumbItem: [
				  {
					name: '公告信息'
				  }
				],
				newsList: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				layouts: '',
				title: '',
			}
		},
		created() {
			this.getNewsList(1);
		},
		watch:{
			$route(newValue){
				this.getNewsList(1);
			}
		},
		//方法集合
		methods: {
			getNewsList(page) {
				let params = {page, limit: this.pageSize,sort:'addtime',order:'desc'};
				let searchWhere = {};
				if(this.title != '') searchWhere.title = '%' + this.title + '%';
				this.$http.get('news/list', {params: Object.assign(params, searchWhere)}).then(res => {
					if (res.data.code == 0) {
						this.newsList = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			curChange(page) {
				this.getNewsList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getNewsList(1);
			},
			prevClick(page) {
				this.getNewsList(page);
			},
			nextClick(page) {
				this.getNewsList(page);
			},
			toNewsDetail(item) {
				this.$router.push({path: '/index/newsDetail', query: {id: item.id}});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.news-preview-pv {
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
						padding: 10px;
						background: none;
						display: flex;
						width: 100%;
						justify-content: center;
						align-items: center;
						flex-wrap: wrap;
						height: auto;
						.search-item {
								margin: 0 10px;
								.el-input {
										width: 100%;
									}
				.el-input /deep/ .el-input__inner {
										border: 1px solid #ddd;
										border-radius: 4px;
										padding: 0 10px;
										margin: 0;
										outline: none;
										color: #333;
										width: 500px;
										font-size: 16px;
										line-height: 42px;
										height: 42px;
									}
			}
			.search-btn {
								cursor: pointer;
								border: 0;
								border-radius: 4px;
								padding: 0px 15px;
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
										font-size: 16px;
									}
			}
		}
		.category-list {
						border: 0px solid #eee;
						padding: 130px 0px 0px;
						margin: 20px 30px 0 0;
						background: url(http://codegen.caihongy.cn/20250906/93261a8cfd7540c78e60e4a46f93d1e8.png) no-repeat center top,#193b38;
						display: flex;
						width: 260px;
						justify-content: center;
						flex-wrap: wrap;
						height: auto;
						order: 3;
						.item {
								cursor: pointer;
								border: 0;
								padding: 0 20px;
								margin: 0;
								color: #fff;
								font-weight: 600;
								display: flex;
								font-size: 16px;
								border-color: #1f4542;
								line-height: 60px;
								border-radius: 0;
								background: none;
								width: 100%;
								border-width: 0 0 1px;
								align-items: center;
								border-style: solid;
							}
			
			.item:hover {
								padding: 0 20px;
								color: #6aac5a;
								font-weight: 600;
								width: 100%;
								font-size: 16px;
								line-height: 60px;
							}
			
			.item.active {
								padding: 0 20px;
								color: #6aac5a;
								font-weight: 600;
								width: 100%;
								font-size: 16px;
								border-color: #1f4542;
								border-width: 0 0 1px;
								line-height: 60px;
								border-style: solid;
							}
		}
		.list5 {
						padding: 0;
						margin: 20px 0 0;
						color: #999;
						background: none;
						flex: 1;
						display: flex;
						width: calc(100% - 350px);
						font-size: 14px;
						flex-wrap: wrap;
						height: auto;
						order: 4;
						.list-item {
								cursor: pointer;
								padding: 15px;
								margin: 0 0px 10px;
								background: #f7f7f7;
								display: flex;
								width: calc(100% - 0px);
								border-color: #e5e5e5;
								border-width: 0 0 0px;
								border-style: solid;
								height: auto;
								img {
										object-fit: cover;
										display: block;
										width: 200px;
										height: 120px;
									}
				.infoBox {
										padding: 0 0 0 20px;
										align-content: center;
										flex: 1;
										display: flex;
										width: 100%;
										align-items: center;
										position: relative;
										flex-wrap: wrap;
										height: 100%;
										.name {
												overflow: hidden;
												color: #333;
												white-space: nowrap;
												font-weight: 600;
												width: 100%;
												font-size: 16px;
												line-height: 30px;
												text-overflow: ellipsis;
											}
					.desc {
												margin: 0;
												overflow: hidden;
												color: #666;
												width: 100%;
												font-size: 14px;
												line-height: 24px;
												height: 48px;
											}
					.time_item {
												padding: 0 10px 0 0;
												top: 0;
												position: absolute;
												right: 0;
												.icon {
														margin: 0 2px 0 0;
														display: none;
														line-height: 1.5;
													}
						.label {
														display: none;
														line-height: 1.5;
													}
						.text {
														font-size: 18px;
														line-height: 1.5;
													}
					}
					.publisher_item {
												padding: 0 10px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.like_item {
												padding: 0 10px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.collect_item {
												padding: 0 10px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.view_item {
												padding: 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.tags {
												border: 1px solid #6aac5a;
												border-radius: 20px;
												padding: 0 10px;
												margin: 5px 10px 0 auto;
												color: #6aac5a;
												background: none;
												display: inline-block;
												width: auto;
												font-size: 15px;
												line-height: 32px;
												text-align: right;
											}
				}
			}
		}
	}
	
	.index-pv1 .animation-box {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
		z-index: initial;
	}
	
	.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 6px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
	}
	
	.index-pv1 .animation-box img {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
	}
	
	.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
</style>
