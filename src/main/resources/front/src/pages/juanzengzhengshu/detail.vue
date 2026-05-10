<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'>>'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/juanzengzhengshu?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item3"><a href="javascript:void(0);">详情</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui12"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="detail-preview">
			<div class="attr">
				<div class="info">
					<div class="title-item">
						<div class="detail-title">
							{{detail.wuzimingcheng}}
						</div>
					</div>
					<div class="item">
						<div class="lable">捐赠编号</div>
						<div class="text "  >{{detail.juanzengbianhao}}</div>
					</div>
					<div class="item">
						<div class="lable">物资种类</div>
						<div class="text "  >{{detail.wuzizhonglei}}</div>
					</div>
					<div class="item">
						<div class="lable">物资说明</div>
						<div class="text "  >{{detail.wuzishuoming}}</div>
					</div>
					<div class="item">
						<div class="lable">新旧程度</div>
						<div class="text "  >{{detail.xinjiuchengdu}}</div>
					</div>
					<div class="item">
						<div class="lable">物资数量</div>
						<div class="text "  >{{detail.wuzishuliang}}</div>
					</div>
					<div class="item">
						<div class="lable">物资重量</div>
						<div class="text "  >{{detail.wuzizhongliang}}</div>
					</div>
					<div class="item">
						<div class="lable">有效期</div>
						<div class="text "  >{{detail.youxiaoqi}}</div>
					</div>
					<div class="item">
						<div class="lable">颁发时间</div>
						<div class="text "  >{{detail.banfashijian}}</div>
					</div>
					<div class="item">
						<div class="lable">账号</div>
						<div class="text "  >{{detail.zhanghao}}</div>
					</div>
					<div class="item">
						<div class="lable">姓名</div>
						<div class="text "  >{{detail.xingming}}</div>
					</div>
					<div class="item certificate-download">
						<div class="lable">捐赠证书</div>
						<el-button class="uploadBtn" @click="download(detail.juanzengzhengshu )">点击下载</el-button>
					</div>
					<div class="btn_box">
						<el-button class="editBtn" v-if="btnAuth('juanzengzhengshu','修改')" @click="editClick">修改</el-button>
						<el-button class="delBtn" v-if="btnAuth('juanzengzhengshu','删除')" @click="delClick">删除</el-button>
						<el-button class="editBtn" @click="allchapterClick()" type="warning" v-if="btnAuth('juanzengzhengshu','章节管理')">章节管理</el-button>
					</div>
				</div>
			</div>
		
			<div class="detail-swpier2" v-if="detailBanner.length">
				<div class="swiper21">
					<div class="swiper-container mySwiper21">
						<div class="swiper-wrapper">
							<div class="swiper-slide" v-for="item in detailBanner" :key="item.id">
								<div class="swiper-item">
									<img v-if="item.substr(0,4)=='http'" :src="item" class="image" @error="$event.target.style.display='none'">
									<img v-else :src="baseUrl + item" class="image" @error="$event.target.style.display='none'">
								</div>
							</div>
						</div>
						<div class="swiper-button-prev">
							<span class="icon iconfont icon-jiantou39"></span>
						</div>
						<div class="swiper-button-next">
							<span class="icon iconfont icon-jiantou18"></span>
						</div>
					</div>
				</div>
				<div class="swiper22">
					<div class="swiper-container mySwiper22">
						<div class="swiper-wrapper">
							<div class="swiper-slide" v-for="item in detailBanner" :key="item.id">
								<div class="swiper-item">
									<img v-if="item.substr(0,4)=='http'" :src="item" class="image" @error="$event.target.style.display='none'">
									<img v-else :src="baseUrl + item" class="image" @error="$event.target.style.display='none'">
								</div>
							</div>
						</div>
						<div class="swiper-button-prev">
							<span class="icon iconfont icon-jiantou39"></span>
						</div>
						<div class="swiper-button-next">
							<span class="icon iconfont icon-jiantou18"></span>
						</div>
					</div>
				</div>
			</div>



			<el-tabs id="tabsBox" class="detail-tabs" v-model="activeName" type="border-card" v-if="tabsNum>0" >
			</el-tabs>

		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import Swiper from "swiper";
	export default {
		//数据集合
		data() {
			return {
				tablename: 'juanzengzhengshu',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '捐赠证书'
					}
				],
				title: '',
				detailBanner: [],
				userid: Number(localStorage.getItem('frontUserid')),
				id: 0,
				detail: {},
				tabsNum: 0,
				activeName: '1',
				buynumber: 1,
				centerType: false,
				storeupType: false,
			}
		},
		created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0) {
				this.centerType = true
			}
			if(this.$route.query.storeupType&&this.$route.query.storeupType!=0) {
				this.storeupType = true
			}
			this.init();
		},
		mounted() {
			setTimeout(()=>{
				let mySwiper22 = new Swiper(".mySwiper22", {"navigation":{"nextEl":".swiper-button-next","prevEl":".swiper-button-prev"},"freeMode":true,"watchSlidesVisibility":true,"watchSlidesProgress":true,"loopedSlides":6,"slidesPerView":3,"spaceBetween":10})
				let option21 = {...{"navigation":{"nextEl":".swiper-button-next","prevEl":".swiper-button-prev"},"loopedSlides":5,"spaceBetween":10}}
				option21.thumbs = {
					swiper: mySwiper22
				}
				new Swiper(".mySwiper21", option21)
			},100)
		},
		computed: {
			username() {
				return localStorage.getItem('username')
			}
		},
		//方法集合
		methods: {
			init() {
				this.id = this.$route.query.id
				this.baseUrl = this.$config.baseUrl;
				this.$http.get(this.tablename + '/detail/'  + this.id, {}).then(async res => {
					if (res.data.code == 0) {
						this.detail = res.data.data;
						this.title = this.detail.wuzimingcheng;
						if(this.detail.wuzitupian) {
							this.detailBanner = this.detail.wuzitupian.split(",w").length>1?[this.detail.wuzitupian]:this.detail.wuzitupian.split(',');
						}
						this.$forceUpdate();
						if(localStorage.getItem('frontToken')){
						}

					}
				});
			},
			curChange(page) {
				this.getDiscussList(page);
			},
			prevClick(page) {
				this.getDiscussList(page);
			},
			nextClick(page) {
				this.getDiscussList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getDiscussList(1);
			},
			// 返回按钮
			backClick(){
				if(this.storeupType){
					history.back()
				}else{
					let params = {}
					if(this.centerType){
						params.centerType = 1
					}
					this.$router.push({path: '/index/juanzengzhengshu', query: params});
				}
			},
			// 下载
			download(file ){
				if(!file) {
					this.$message({
						type: 'error',
						message: '文件不存在',
						duration: 1500,
					});
					return;
				}
				let arr = file.replace(new RegExp('upload/', "g"), "")
				axios.get(this.baseUrl + 'file/download?fileName=' + arr, {
					headers: {
						token: localStorage.getItem("frontToken")
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
					axios.get((location.href.split(this.$config.name).length>1 ? location.href.split(this.$config.name)[0] :'') + this.$config.name + 'file/download?fileName=' + arr, {
						headers: {
							token: localStorage.getItem("frontToken")
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


			// 权限判断
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			// 修改
			editClick(){
				this.$router.push(`/index/juanzengzhengshuAdd?type=edit&&id=${this.detail.id}`);
			},
			// 删除
			async delClick(){
				await this.$confirm('是否删除此捐赠证书？') .then(_ => {
					this.$http.post('juanzengzhengshu/delete', [this.detail.id]).then(async res => {
						if (res.data.code == 0) {
							this.$message({
								type: 'success',
								message: '删除成功!',
								duration: 1500,
								onClose: () => {
									history.back()
								}
							});
						}
					});
				}).catch(_ => {});
			},
			allchapterClick (){
				let params = {
					refid: this.detail.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chapterjuanzengzhengshu', query: params});
			},
			// 获取uuid
			getUUID() {
				return new Date().getTime();
			},
		},
		components: {
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.detail-preview {
		padding: 0 7%;
		margin: 10px auto;
		display: flex;
		width: 100%;
		position: relative;
		flex-wrap: wrap;
		.attr {
			padding: 0px;
			margin: 0 0 20px;
			background: none;
			flex: 1;
			display: flex;
			position: relative;
			order: 2;
			.info {
				border-radius: 8px;
				padding: 10px;
				margin: 0;
				background: #fff;
				flex: 1;
				.title-item {
					padding: 10px;
					margin: 0 0 0px 0;
					background: none;
					display: flex;
					border-color: #ddd;
					border-width: 0 0 1px;
					justify-content: space-between;
					align-items: center;
					border-style: solid;
					.detail-title {
						padding: 0;
						color: #333;
						font-weight: 600;
						font-size: 18px;
						border-color: #009899;
						border-width: 0 0 0 0px;
						line-height: 1;
						border-style: solid;
					}
				}
				.item {
					padding: 0 10px;
					margin: 0 0 0px 0;
					background: none;
					display: flex;
					justify-content: spaceBetween;
					.lable {
						padding: 0 10px 0 0;
						color: #333;
						white-space: nowrap;
						font-weight: 500;
						width: auto;
						font-size: 16px;
						line-height: 24px;
						text-align: right;
						height: auto;
					}
					.count-down {
						padding: 0 10px;
						color: #666;
						word-break: break-all;
						flex: 1;
						font-size: 14px;
						line-height: 24px;
						height: auto;
					}
					.text {
						padding: 0 10px;
						color: #666;
						word-break: break-all;
						flex: 1;
						font-size: 14px;
						line-height: 24px;
						height: auto;
					}
					.bold {
						font-weight: bold;
					}
					.link {
						cursor: pointer;
						text-decoration: underline;
					}
					.uploadBtn {
						border: 1px solid #ddd;
						border-radius: 4px;
						padding: 0px 10px;
						margin: 0px;
						color: #333;
						background: none;
						text-decoration: none;
						width: 100px;
						font-size: 14px;
						line-height: 32px;
						text-align: center;
						height: 32px;
					}
					.uploadBtn:hover {
						background: rgba(75, 223, 201,.5);
					}
				}
				.item-price {
					.lable {
					}
					.text {
					}
				}
				.btn_box {
					padding: 10px 0;
					display: flex;
					flex-wrap: wrap;
				}
				.editBtn {
					border: 0;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 10px;
					margin: 0 5px 0 0;
					outline: none;
					color: #fff;
					background: rgba(64, 158, 255, 1);
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.editBtn:hover {
					opacity: 0.7;
				}
				.delBtn {
					border: 0;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 10px;
					margin: 0 5px 0 0;
					outline: none;
					color: #fff;
					background: rgba(255, 0, 0, 1.0);
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.delBtn:hover {
					opacity: 0.7;
				}
			}
		}
		.detail-swpier2 {
			margin: 0 20px 20px 0;
			width: 480px;
			height: 480px;
			order: 1;
			.swiper21 {
				width: 100%;
				height: auto;
				.swiper-button-prev:after {
					display:none;
				}
				.swiper-button-next:after {
					display:none;
				}
				.swiper-item {
					width: 100%;
					height: auto;
					img {
						border: 1px solid #eee;
						background: #fff;
						object-fit: contain;
						width: calc(100% - 2px);
						height: 400px;
					}
				}
				.swiper-button-prev {
					margin: -12px 0 0;
					top: 50%;
					width: 24px;
					height: 24px;
					.icon {
						color: #fff;
						width: 24px;
						font-size: 24px;
						height: 24px;
					}
				}
				.swiper-button-next {
					margin: -12px 0 0;
					top: 50%;
					width: 24px;
					height: 24px;
					.icon {
						color: #fff;
						width: 24px;
						font-size: 24px;
						height: 24px;
					}
				}
			}
			.swiper22 {
				display: none;
				width: 100%;
				height: auto;
				
				.swiper-button-prev:after {
					display:none;
				}
				.swiper-button-next:after {
					display:none;
				}
				
				.swiper-item {
					width: 100%;
					opacity: 0.4;
					height: auto;
					img {
						object-fit: cover;
						width: 100%;
						height: 100px;
					}
				}
				.swiper-slide.swiper-slide-thumb-active div {
					opacity: 1;
				}
				.swiper-button-prev {
					margin: -7px 0 0;
					top: 50%;
					width: 14px;
					height: 14px;
					.icon {
						color: #fff;
						width: 14px;
						font-size: 14px;
						height: 14px;
					}
				}
				.swiper-button-next {
					margin: -7px 0 0;
					top: 50%;
					width: 14px;
					height: 14px;
					.icon {
						color: #fff;
						width: 14px;
						font-size: 14px;
						height: 14px;
					}
				}
			}
		}
		.detail-tabs {
			border: 0px solid #DCDFE6;
			box-shadow: none;
			padding: 0;
			margin: 20px auto;
			background: none;
			width: 100%;
			order: 5;
			& /deep/ .el-tabs__header .el-tabs__nav-wrap {
				margin-bottom: 0;
			}
			/deep/ .el-tabs__header {
				padding: 8px 0;
				background: #fff;
				display: block;
				width: 100%;
				border-color: #ddd;
				border-width: 0 0 1px;
				line-height: 1.5;
				border-style: solid;
				text-align: center;
			}
			
			/deep/ .el-tabs__header .el-tabs__item {
				border: 0;
				padding: 0 20px;
				margin: 0 10px 0 0;
				color: #333;
				background: none;
				font-weight: 500;
				display: inline-block;
				font-size: 18px;
				line-height: 40px;
				position: relative;
				list-style: none;
				height: 40px;
			}
			
			/deep/ .el-tabs__header .el-tabs__item:hover {
				color: #fff;
				background: #6aac5a;
				border-color: #6aac5a;
			}
			
			/deep/ .el-tabs__header .el-tabs__item.is-active {
				margin: 0 10px 0 0;
				color: #fff;
				background: #6aac5a;
				font-size: 18px;
				border-color: #6aac5a;
			}
			
			/deep/ .el-tabs__content {
				padding: 15px 0;
				background: #fff;
				width: 100%;
				font-size: 16px;
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

.detail-preview {
	max-width: 1280px;
	width: 100% !important;
	margin: 0 auto !important;
	padding: 26px 24px 48px !important;
	box-sizing: border-box;
	display: grid !important;
	grid-template-columns: minmax(360px, 520px) minmax(420px, 1fr);
	gap: 28px;
	align-items: start;
	background: #f7faf8;
	.detail-swpier2 {
		width: 100% !important;
		height: auto !important;
		margin: 0 !important;
		order: 1;
		border: 1px solid #e6eee7;
		border-radius: 8px;
		background: #fff;
		box-shadow: 0 12px 30px rgba(34, 62, 43, .07);
		overflow: hidden;
		position: relative;
	}
	.detail-swpier2:before {
		content: "暂无图片";
		position: absolute;
		inset: 0;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #9aa7a0;
		font-size: 15px;
		background: #f4f7f5;
	}
	.detail-swpier2 .swiper21,
	.detail-swpier2 .swiper-container,
	.detail-swpier2 .swiper-wrapper,
	.detail-swpier2 .swiper-slide,
	.detail-swpier2 .swiper-item {
		position: relative;
		z-index: 1;
	}
	.detail-swpier2 .swiper21 .swiper-item img {
		width: 100% !important;
		height: 430px !important;
		border: 0 !important;
		object-fit: contain !important;
		background: #fbfcfb;
	}
	.attr {
		width: 100% !important;
		margin: 0 !important;
		order: 2;
	}
	.attr .info {
		width: 100% !important;
		margin: 0 !important;
		padding: 26px 28px !important;
		border: 1px solid #e6eee7;
		border-radius: 8px;
		background: #fff;
		box-shadow: 0 12px 30px rgba(34, 62, 43, .08);
		box-sizing: border-box;
	}
	.title-item {
		margin: 0 0 18px !important;
		padding: 0 0 14px;
		border-bottom: 1px solid #edf2ee;
	}
	.detail-title {
		color: #23362b !important;
		font-size: 24px !important;
		line-height: 32px !important;
		font-weight: 700;
	}
	.item {
		display: grid !important;
		grid-template-columns: 96px 1fr;
		gap: 12px;
		align-items: start;
		min-height: 32px;
		margin: 0 !important;
		padding: 8px 0 !important;
		border-bottom: 1px dashed #edf2ee;
	}
	.item .lable {
		width: auto !important;
		padding: 0 !important;
		color: #5e6d63 !important;
		font-size: 14px !important;
		line-height: 24px !important;
		font-weight: 600;
		text-align: left !important;
	}
	.item .text {
		padding: 0 !important;
		color: #2f3f35 !important;
		font-size: 14px !important;
		line-height: 24px !important;
		word-break: break-word;
	}
	.certificate-download {
		margin-top: 14px !important;
		padding: 18px !important;
		border: 1px solid #d8ecd8;
		border-radius: 8px;
		background: #f7fcf6;
		grid-template-columns: 96px 1fr;
	}
	.uploadBtn {
		width: auto !important;
		min-width: 132px;
		height: 38px !important;
		line-height: 38px !important;
		padding: 0 20px !important;
		border: 0 !important;
		border-radius: 6px !important;
		color: #fff !important;
		background: #4f9f45 !important;
		font-size: 14px !important;
		font-weight: 600;
	}
	.uploadBtn:hover {
		background: #438b3b !important;
	}
	.btn_box {
		margin-top: 16px;
		padding: 0 !important;
		gap: 8px;
	}
	.editBtn,
	.delBtn {
		height: 36px !important;
		line-height: 36px !important;
		border-radius: 6px !important;
		padding: 0 16px !important;
	}
	.delBtn {
		background: #d9534f !important;
	}
}

@media (max-width: 960px) {
	.detail-preview {
		grid-template-columns: 1fr;
		padding-left: 12px !important;
		padding-right: 12px !important;
	}
	.detail-preview .detail-swpier2 .swiper21 .swiper-item img {
		height: 300px !important;
	}
	.detail-preview .item {
		grid-template-columns: 86px 1fr;
	}
}
</style>
