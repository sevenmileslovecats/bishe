<template>
	<div>
		<div class="login-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="title-container">公益捐赠物资管理系统登录</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							<span class="icon iconfont icon-touxiang09"></span>
						</div>
						<input placeholder="请输入账号：" name="username" type="text" v-model="rulesForm.username">
					</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							<span class="icon iconfont icon-chakan13"></span>
						</div>
						<div class="password-box">
							<input placeholder="请输入密码：" name="password" :type="showPassword?'text':'password'" v-model="rulesForm.password">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>
					<div v-if="loginType==1" class="list-item captcha-item">
						<div class="lable">
							<span class="icon iconfont icon-chakan13"></span>
						</div>
						<input placeholder="请输入验证码：" name="captcha" type="text" maxlength="4" v-model="rulesForm.captcha" @keyup.enter="login()">
						<img class="captcha-img" :src="captchaUrl" @click="refreshCaptcha" title="点击刷新验证码">
					</div>

					<div class="list-item select" v-if="roles.length>1&&loginType<=2">
						<div class="lable">
							<span class="icon iconfont icon-touxiang15"></span>
						</div>
						<el-select v-model="rulesForm.role" placeholder="请选择角色：">
							<el-option v-if="loginType==1||(loginType==2&&item.role!='users')" v-for="item in roles" :key="item.roleName" :label="item.roleName" :value="item.roleName" />
						</el-select>
					</div>

		
					<div class="login-btn">
						<div class="login-btn1">
							<el-button v-if="loginType==1||loginType==3||loginType==4" type="primary" @click="login()" class="loginInBt">登录</el-button>
						</div>
						<div class="login-btn2">
							<el-button type="primary" @click="register('jieshoujigou')" class="register">
								注册接收机构							</el-button>
						</div>
						<div class="login-btn3">
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>
<script>
	import 'animate.css'
	import menu from "@/utils/menu";
	export default {
		data() {
			return {
				verifyCheck2: false,
				flag: false,
				baseUrl:this.$base.url,
				loginType: 1,
				rulesForm: {
					username: "",
					password: "",
					role: "",
					captcha: "",
				},
				menus: [],
				roles: [],
				tableName: "",
				showPassword: false,
				indexBgUrl: '',
				captchaUrl: '',
			};
		},
		mounted() {
			let menus = menu.list();
			this.menus = menus;

			for (let i = 0; i < this.menus.length; i++) {
				if (this.menus[i].hasBackLogin=='是') {
					this.roles.push(this.menus[i])
				}
			}
			this.refreshCaptcha();

		},
		created() {
			this.$http.get('config/info?name=bLoginBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		},
		destroyed() {
		},
		components: {
		},
		methods: {

			//注册
			register(tableName){
				this.$storage.set("loginTable", tableName);
				this.$router.push({path:'/register',query:{pageFlag:'register'}})
			},
			// 登陆
			login() {
				if(this.loginType==1) {

					if (!this.rulesForm.username) {
						this.$message.error("请输入用户名");
						return;
					}
					if (!this.rulesForm.password) {
						this.$message.error("请输入密码");
						return;
					}
					if (!this.rulesForm.captcha) {
						this.$message.error("请输入验证码");
						return;
					}
					if(this.roles.length>1) {
						if (!this.rulesForm.role) {
							this.$message.error("请选择角色");
							return;
						}
					
						for (let i = 0; i < this.roles.length; i++) {
							if (this.roles[i].roleName == this.rulesForm.role) {
								this.tableName = this.roles[i].tableName;
							}
						}
					} else {
						this.tableName = this.roles[0].tableName;
						this.rulesForm.role = this.roles[0].roleName;
					}
				}
		
				this.loginPost()
			},
			refreshCaptcha() {
				this.captchaUrl = `/springboot02b8755d/captcha?time=${new Date().getTime()}`;
			},
			loginPost() {
				this.$http({
					url: `${this.tableName}/login`,
					method: "post",
					params: {
						username: this.rulesForm.username,
						password: this.rulesForm.password,
						captcha: this.rulesForm.captcha
					}
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.$storage.set("Token", data.token);
						this.$storage.set("role", this.rulesForm.role);
						this.$storage.set("sessionTable", this.tableName);
						this.$storage.set("adminName", this.rulesForm.username);
						this.$nextTick(()=>{
							this.$http({
								url: this.tableName + '/session',
								method: "get"
							}).then(({
								data
							}) => {
								if (data && data.code === 0) {
									if(this.tableName == 'juanzengren') {
										this.$storage.set('headportrait',data.data.touxiang)
									}
									if(this.tableName == 'jieshoujigou') {
										this.$storage.set('headportrait',data.data.touxiang)
									}
									if(this.tableName == 'users') {
										this.$storage.set('headportrait',data.data.image)
									}
									this.$storage.set('userForm',JSON.stringify(data.data))
									this.$storage.set('userid',data.data.id);
								} else {
									let message = this.$message
									message.error(data.msg);
								}
								this.$router.replace({ path: "/" });
							});
						})
					}
					else {
						this.$message.error(data.msg);
						this.rulesForm.captcha = "";
						this.refreshCaptcha();
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
.login-container {
	min-height: 100vh;
	position: relative;
	background-repeat: no-repeat;
	background-position: center center;
	background-size: cover;
	background: url(http://codegen.caihongy.cn/20251229/c227c5bc6b554689be9fb8d2fd070c1f.jpg);
	background-repeat: no-repeat !important;
	background-size: 100% 100% !important;
	background: url(http://codegen.caihongy.cn/20251229/c227c5bc6b554689be9fb8d2fd070c1f.jpg);
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: center;
	background-position: center bottom;

	.login_form {
		border-radius: 10px;
		padding: 40px 0px 20px;
		margin: 0 auto;
		z-index: 1;
		background: none;
		display: flex;
		width: 600px;
		flex-wrap: wrap;
		height: auto;
		.login_form2 {
			border-radius: 20px;
			box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1),0 4px 6px -4px rgba(0,0,0,0.1);
			padding: 10px 50px;
			backdrop-filter: blur(10px);
			margin: 20px auto;
			background: rgba(255,255,255,1);
			width: 100%;
		}
		.title-container {
			margin: 10px 0 10px 0;
			text-shadow: none;
			color: #ff7b54;
			font-weight: 600;
			width: 100%;
			font-size: 18px;
			line-height: 44px;
			text-align: center;
		}
		.list-item {
			border: 1px solid #ff916450;
			border-radius: 10px;
			padding: 0 20px;
			margin: 0 auto 25px;
			background: none;
			display: flex;
			width: 100%;
			.lable {
				color: #333;
				width: auto;
				font-size: 16px;
				line-height: 44px;
				.icon {
					color: #ff9063;
					width: 64px;
					font-size: 18px;
					line-height: 44px;
				}
			}
			input {
				border: 0px solid #ddd;
				border-radius: 8px;
				padding: 0 10px;
				color: #333;
				flex: 1;
				background: none;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			input:focus {
				border: 0px solid #ff7a00;
				border-radius: 8px;
				padding: 0 10px;
				color: #ff7a00;
				flex: 1;
				background: none;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.password-box {
				flex: 1;
				display: flex;
				width: 100%;
				position: relative;
				align-items: center;
				input {
					border: 0px solid #ddd;
					border-radius: 8px;
					padding: 0 10px;
					color: #333;
					background: none;
					width: 100%;
					font-size: 16px;
					height: 44px;
				}
				input:focus {
					border: 0px solid #ff7a00;
					border-radius: 8px;
					padding: 0 10px;
					color: #ff7a00;
					background: none;
					width: 100%;
					font-size: 16px;
					height: 44px;
				}
				.iconfont {
					cursor: pointer;
					z-index: 1;
					color: #000;
					top: 0;
					font-size: 16px;
					line-height: 44px;
					position: absolute;
					right: 5px;
				}
			}
			.captcha-img {
				border: 1px solid #ff916450;
				border-radius: 6px;
				cursor: pointer;
				margin: 4px 0 4px 10px;
				width: 120px;
				height: 36px;
				object-fit: cover;
				flex: 0 0 120px;
			}
			input::placeholder {
				color: #666;
				font-size: 16px;
			}
			/deep/ .el-select {
				flex: 1;
				width: 100%;
			}
			/deep/ .el-select .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 8px;
				padding: 0 10px;
				color: #333;
				background: none;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			/deep/ .el-select .is-focus .el-input__inner {
				border: 0px solid #ff7a00;
				border-radius: 8px;
				padding: 0 10px;
				color: #ff7a00;
				background: none;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			/deep/ .el-select .el-input__inner::placeholder{
				color: #666;
				font-size: 16px;
			}
		}
		.login-btn {
			margin: 20px auto;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			flex-wrap: wrap;
			.login-btn1 {
				width: 100%;
			}
			.login-btn2 {
				width: 100%;
				text-align: center;
			}
			.login-btn3 {
				width: 100%;
				text-align: right;
			}
			.loginInBt {
				border: 0;
				cursor: pointer;
				border-radius: 10px;
				padding: 0 24px;
				margin: 0 0 10px;
				outline: none;
				color: #fff;
				background: #ff9164;
				width: 100%;
				font-size: 22px;
				height: 50px;
			}
			.loginInBt:hover {
				opacity: 0.5;
			}
			.register {
				border: 0px solid #333;
				cursor: pointer;
				border-radius: 20px;
				padding: 0 10px;
				margin: 0 5px 10px 0;
				outline: none;
				color: #666;
				background: none;
				width: auto;
				font-size: 16px;
				height: 44px;
			}
			.register:hover {
				opacity: 0.5;
			}
			.forget {
				border: 0;
				cursor: pointer;
				border-radius: 20px;
				padding: 0 10px;
				margin: 0 0px 5px 0;
				outline: none;
				color: #666;
				background: none;
				width: auto;
				font-size: 15px;
				height: 44px;
			}
			.forget:hover {
				opacity: 0.5;
			}
		}
	}
}

</style>
