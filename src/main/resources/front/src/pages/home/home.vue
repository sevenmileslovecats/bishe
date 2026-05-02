<template>
	<div class="home-preview">




		<!-- 新闻资讯 -->
		<div id="animate_newsnews" class="news animate__animated">
			<div class="news_title_box">
				<span class="news_title">公告信息</span>
				<span class="news_subhead">{{'news'.toUpperCase()}}</span>
			</div>
			<div class="list list32 index-pv1">
				<div class="list-body-left">
					<div class="list-item" @click="toDetail('newsDetail', newsList[0])">
						<div class="img">
							<img :src="baseUrl + (newsList[0].picture?newsList[0].picture.split(',')[0]:'')">
						</div>
						<div class="infoBox">
							<div class="name">{{newsList[0].title}}</div>
							<div class="desc">{{newsList[0].introduction}}</div>
							<div class="time-item">
								<span class="text">{{newsList[0].addtime.split(' ')[0].split('-')[0]}}
								/
								{{newsList[0].addtime.split(' ')[0].split('-')[1]}}
								/
								{{newsList[0].addtime.split(' ')[0].split('-')[2]}}</span>
							</div>
						</div>
					</div>
				</div>
				<div class="list-body-right">
					<div class="list-body">
						<div class="list-item" v-for="(item,index) in newsList" :key="index" v-if="index>0&&index<=Number(6)" @click="toDetail('newsDetail', item)">
							<div class="time-item">
								<span class="day">{{item.addtime.split(' ')[0].split('-')[2]}}</span>
								/
								<span calss="month">{{item.addtime.split(' ')[0].split('-')[1]}}</span>
							</div>
							<div class="name">{{item.title}}</div>
							<span class="list-item-arrow"></span>
						</div>
					</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('news')">
				<span class="text">查看更多</span>
				<i class="icon iconfont icon-jiantou25"></i>
			</div>
		</div>
		<!-- 新闻资讯 -->
	</div>
</template>

<script>
import 'animate.css'
import Swiper from "swiper";

	export default {
		//数据集合
		data() {
			return {
				baseUrl: '',
				newsList: [],





			}
		},
		created() {
			this.baseUrl = this.$config.baseUrl;
			this.getNewsList();
			this.getList();
		},
		mounted() {
			window.addEventListener('scroll', this.handleScroll)
			setTimeout(()=>{
				this.handleScroll()
			},100)
			
			this.swiperChanges()
		},
		beforeDestroy() {
			window.removeEventListener('scroll', this.handleScroll)
		},
		computed: {
			username() {
				return localStorage.getItem('username')
			}
		},
		//方法集合
		methods: {
			swiperChanges() {
				setTimeout(()=>{
				},750)
			},

			listIndexClick12(index, name) {
				this['listIndex12' + name] = index
				this.getList()
				
				document.querySelectorAll('.lists .list12' + name + ' .list .item').forEach(el => {
				  el.classList.remove("active")
				})
				setTimeout(() => {
				  document.querySelectorAll('.lists .list12' + name + ' .list .item').forEach(el => {
					el.classList.add("active")
				  })
				}, 1);
			},

			handleScroll() {
				let arr = [
					{id:'about',css:'animate__'},
					{id:'system',css:'animate__'},
					{id:'animate_newsnews',css:'animate__'},
				]
			
				for (let i in arr) {
					let doc = document.getElementById(arr[i].id)
					if (doc) {
						let top = doc.offsetTop
						let win_top = window.innerHeight + window.pageYOffset
						// console.log(top,win_top)
						if (win_top > top && doc.classList.value.indexOf(arr[i].css) < 0) {
							// console.log(doc)
							doc.classList.add(arr[i].css)
						}
					}
				}
			},
			preHttp(str) {
				return str && str.substr(0,4)=='http';
			},
			preHttp2(str) {
				return str && str.split(',w').length>1;
			},
			getNewsList() {
				let data = {
					page: 1,
					limit: 7,
					sort: 'addtime',
					order: 'desc'
				}
				this.$http.get('news/list', {params: data}).then(res => {
					if (res.data.code == 0) {
						this.newsList = res.data.data.list;
					
					}
				});
			},
			getList() {
				let autoSortUrl = "";
				let data = {}
			
			},
			toDetail(path, item) {
				this.$router.push({path: '/index/' + path, query: {id: item.id, storeupType: 1}});
			},
			moreBtn(path) {
				this.$router.push({path: '/index/' + path});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.home-preview {
		margin: 0 auto;
		display: flex;
		width: 100%;
		justify-content: space-between;
		flex-wrap: wrap;
		.news {
			padding: 40px 7%;
			margin: 0 auto;
			background: #fff;
			width: 100%;
			position: relative;
			order: 5;
			.news_title_box {
				margin: 0px auto;
				background: none;
				width: 100%;
				line-height: 54px;
				.news_title {
					padding: 0 0 0 54px;
					color: #333;
					background: url(http://codegen.caihongy.cn/20250906/81ff48f2474a415a9aee733a5b2c90a8.png)  no-repeat left center / 44px 100%;
					font-weight: 600;
					display: block;
					width: 100%;
					font-size: 32px;
					line-height: 44px;
					text-align: left;
				}
				.news_subhead {
					margin: 0 0 10px;
					color: #ddd;
					background: none;
					display: none;
					width: 100%;
					font-size: 30px;
					line-height: 1;
				}
			}
			.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, -4px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
			.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
			.list32 {
				margin: 30px auto 0;
				overflow: hidden;
				display: flex;
				width: 100%;
				justify-content: space-between;
				.list-body-left {
					background: #fff;
					width: 35%;
					float: left;
					.list-item {
						cursor: pointer;
						border: 1px solid #eee;
						border-radius: 10px;
						padding: 20px;
						background: #fff;
						display: block;
						width: 100%;
						.img {
							overflow: hidden;
							width: 100%;
							img {
								object-fit: cover;
								-webkit-transition: all 0.7s ease;
								width: 100%;
								transition: all 0.7s ease;
								height: 305px;
							}
						}
						.infoBox {
							margin: 35px 0 0;
							.name {
								overflow: hidden;
								color: #000000;
								white-space: nowrap;
								font-size: 18px;
								text-overflow: ellipsis;
							}
							.desc {
								margin: 15px 0 0;
								overflow: hidden;
								color: #666666;
								display: -webkit-box;
								font-size: 14px;
								line-height: 2;
								-webkit-box-orient: vertical;
								-webkit-line-clamp: 2;
							}
							.time-item {
								margin: 30px 0 0;
								position: relative;
								text-align: right;
								.text {
									padding: 0 0 0 50px;
									z-index: 1;
									overflow: hidden;
									color: #19aeae;
									max-width: 100%;
									background: #fff;
									display: inline-block;
									font-size: 14px;
									position: relative;
									box-sizing: border-box;
								}
							}
							.time-item::before {
								border: 1px solid #bae7e7;
								margin: auto;
								top: 0;
								left: 0;
								bottom: 0;
								width: 100%;
								border-width: 1px 0 0;
								position: absolute;
								content: "";
								height: 1px;
							}
						}
					}
					.list-item:hover {
						.img {
							img {
								transform: scale(1.1);
								-webkit-transform: scale(1.1);
							}
						}
						.infoBox {
							.name {
								color: #6aac5a;
							}
						}
					}
				}
				.list-body-right {
					background: #fff;
					width: 62%;
					float: right;
					.list-body {
						border: 1px solid #eee;
						border-radius: 10px;
						padding: 0 30px 20px;
						background: #fff;
						.list-item {
							border: 1px solid #eee;
							cursor: pointer;
							padding: 25px 0px;
							overflow: hidden;
							display: block;
							width: 100%;
							border-width: 0 0 1px;
							position: relative;
							.time-item {
								color: #ccc;
								width: 13.5%;
								font-size: 16px;
								float: left;
								.day {
									color: #6aac5a;
									font-size: 24px;
								}
								.month {
									color: inherit;
								}
							}
							.name {
								overflow: hidden;
								color: #000000;
								white-space: nowrap;
								width: 73%;
								font-size: 18px;
								line-height: 32px;
								text-overflow: ellipsis;
								float: left;
							}
							.list-item-arrow {
								background-size: 19px 12px;
								top: 0;
								background: url(http://codegen.caihongy.cn/20250906/dd0aecee2daf44e29e89067be9d79d7e.png) left center no-repeat;
								-webkit-transition: all 0.5s ease;
								width: 13.5%;
								position: absolute;
								right: 0;
								opacity: 0;
								transition: all 0.5s ease;
								height: 100%;
							}
						}
						.list-item:hover {
							.name {
								color: #6aac5a;
							}
							.list-item-arrow {
								background-size: 19px 12px;
								background: url(http://codegen.caihongy.cn/20250906/dd0aecee2daf44e29e89067be9d79d7e.png) center no-repeat;
								opacity: 1;
							}
						}
						.list-item::before {
							border: 1px solid #6aac5a;
							left: 0;
							bottom: 0;
							-webkit-transition: all 0.5s ease;
							width: 0;
							border-width: 0 0 1px;
							position: absolute;
							content: "";
							transition: all 0.5s ease;
							height: 1px;
						}
						.list-item:hover::before {
							width: 100%;
						}
					}
				}
			}
			.moreBtn {
				border: 0px solid #ddd;
				cursor: pointer;
				margin: 30px auto 0;
				display: flex;
				line-height: 40px;
				right: 7%;
				border-radius: 0px;
				top: 10px;
				background: #6aac5a;
				width: 120px;
				justify-content: center;
				position: absolute;
				text-align: center;
				.text {
					margin: 0 5px 0 0;
					color: #fff;
					font-size: 16px;
					order: 2;
				}
				.icon {
					color: #999;
					background: none;
					display: none;
					width: 23px;
					font-size: 16px;
					height: 7px;
				}
			}
		}
	}
</style>
