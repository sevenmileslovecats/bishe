<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'>>'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/chukufenbo?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
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
						<div class="lable">申领编号</div>
						<div class="text "  >{{detail.shenlingbianhao}}</div>
					</div>
					<div class="item">
						<div class="lable">物资种类</div>
						<div class="text "  >{{detail.wuzizhonglei}}</div>
					</div>
					<div class="item">
						<div class="lable">机构账号</div>
						<div class="text "  >{{detail.jigouzhanghao}}</div>
					</div>
					<div class="item">
						<div class="lable">机构名称</div>
						<div class="text "  >{{detail.jigoumingcheng}}</div>
					</div>
					<div class="item">
						<div class="lable">出库数量</div>
						<div class="text "  >{{detail.wuzishuliang}}</div>
					</div>
					<div class="item">
						<div class="lable">出库时间</div>
						<div class="text "  >{{detail.chukushijian}}</div>
					</div>
					<div class="item">
						<div class="lable">物流状态</div>
						<div class="text "  >{{detail.wuliuzhuangtai}}</div>
					</div>
					<div class="item">
						<div class="lable">出库单</div>
						<el-button class="uploadBtn" @click="download(detail.chukudan )">点击下载</el-button>
					</div>
					<div class="btn_box">
						<el-button class="editBtn" v-if="btnAuth('chukufenbo','修改')" @click="editClick">修改</el-button>
						<el-button class="delBtn" v-if="btnAuth('chukufenbo','删除')" @click="delClick">删除</el-button>
						<el-button class="reserveBtn" 
							v-if="btnAuth('chukufenbo','接收')" 
							@click="onAcross('jieshouxinxi','','','wuliuzhuangtai','已签收','已签收,配送中'.split(',')[0])" type="warning">
							接收
						</el-button>
						<el-button class="reserveBtn" 
							v-if="btnAuth('chukufenbo','异议反馈')" 
							@click="onAcross('yiyifankui','','','','')" type="warning">
							异议反馈
						</el-button>
						<el-button class="editBtn" @click="allchapterClick()" type="warning" v-if="btnAuth('chukufenbo','章节管理')">章节管理</el-button>
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
				tablename: 'chukufenbo',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '出库分拨'
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
			async onAcross(acrossTable,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue,type=1){
				localStorage.setItem('crossTable',`chukufenbo`);
				localStorage.setItem('crossObj', JSON.stringify(this.detail));
				localStorage.setItem('statusColumnName',statusColumnName);
				localStorage.setItem('statusColumnValue',statusColumnValue);
				localStorage.setItem('tips',tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = JSON.parse(localStorage.getItem('crossObj'));
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$message({
								type: 'error',
								message: tips,
								duration: 1500
							});
							return
						}
					}
				}
				this.$router.push({path: '/index/' + acrossTable + 'Add', query: {type: 'cross'}});
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
					this.$router.push({path: '/index/chukufenbo', query: params});
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
				this.$router.push(`/index/chukufenboAdd?type=edit&&id=${this.detail.id}`);
			},
			// 删除
			async delClick(){
				await this.$confirm('是否删除此出库分拨？') .then(_ => {
					this.$http.post('chukufenbo/delete', [this.detail.id]).then(async res => {
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
				this.$router.push({path: '/index/chapterchukufenbo', query: params});
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
		padding: 18px 24px 6px;
	}
	.back_box {
		max-width: 1280px;
		margin: 0 auto;
		padding: 0 24px;
		display: flex;
		justify-content: flex-end;
	}
	.back_box .backBtn {
		border: 0;
		border-radius: 6px;
		background: #5fa85a;
		color: #fff;
		height: 36px;
		padding: 0 18px;
		font-weight: 600;
	}
	.detail-preview {
		max-width: 1280px;
		padding: 24px;
		margin: 0 auto 40px;
		gap: 28px;
		align-items: flex-start;
		.attr {
			margin: 0;
			min-width: 420px;
			order: 2;
			.info {
				border: 1px solid #edf0f3;
				border-radius: 8px;
				padding: 24px;
				box-shadow: 0 10px 28px rgba(32, 45, 64, 0.08);
				background: #fff;
				.title-item {
					padding: 0 0 16px;
					margin: 0 0 16px;
					border-color: #edf0f3;
					.detail-title {
						font-size: 24px;
						line-height: 32px;
						color: #1f2d3d;
					}
				}
				.item {
					display: grid;
					grid-template-columns: 112px minmax(0, 1fr);
					align-items: center;
					gap: 10px;
					padding: 10px 0;
					border-bottom: 1px dashed #edf0f3;
					.lable {
						padding: 0;
						color: #53616f;
						font-size: 14px;
						font-weight: 600;
						line-height: 22px;
						text-align: left;
					}
					.text {
						padding: 0;
						color: #1f2d3d;
						font-size: 15px;
						line-height: 22px;
					}
					.uploadBtn {
						border: 1px solid #5fa85a;
						border-radius: 6px;
						background: #f5fbf5;
						color: #357d3a;
						height: 34px;
						line-height: 32px;
						font-weight: 600;
					}
				}
				.btn_box {
					padding: 20px 0 0;
					gap: 10px;
				}
				.editBtn,
				.delBtn,
				.reserveBtn {
					border: 0;
					border-radius: 6px;
					padding: 0 18px;
					margin: 0;
					height: 38px;
					line-height: 38px;
					font-weight: 600;
				}
				.editBtn,
				.reserveBtn {
					background: #5fa85a;
				}
				.delBtn {
					background: #d95f54;
				}
			}
		}
		.detail-swpier2 {
			width: 420px;
			height: auto;
			margin: 0;
			order: 1;
			.swiper21 .swiper-item {
				position: relative;
				border: 1px solid #edf0f3;
				border-radius: 8px;
				background: linear-gradient(135deg, #f7faf8, #eef5f2);
				overflow: hidden;
				min-height: 420px;
				&::before {
					content: "暂无图片";
					position: absolute;
					inset: 0;
					display: flex;
					align-items: center;
					justify-content: center;
					color: #8a98a8;
					font-size: 15px;
				}
				img {
					position: relative;
					z-index: 1;
					border: 0;
					width: 100%;
					height: 420px;
					background: #fff;
				}
			}
		}
	}
	@media (max-width: 900px) {
		.detail-preview {
			padding: 16px;
			.attr {
				min-width: 0;
				width: 100%;
				.info .item {
					grid-template-columns: 96px minmax(0, 1fr);
				}
			}
			.detail-swpier2 {
				width: 100%;
				.swiper21 .swiper-item {
					min-height: 280px;
					img {
						height: 280px;
					}
				}
			}
		}
	}
</style>
