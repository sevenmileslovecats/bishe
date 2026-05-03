<template>
	<div>
		<div class="login-container" :style="{'backgroundImage': indexBgUrl?`url(${$config.baseUrl + indexBgUrl})`:''}">
			<el-form ref="loginForm" :model="loginForm" :rules="rules" class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="login-title">公益捐赠物资管理系统</div>
					<div v-if="loginType==1" class="list-item" prop="username">
						<div class="lable">
							账号：
						</div>
						<input v-model="loginForm.username" placeholder="请输入账号：">
					</div>
					<div v-if="loginType==1" class="list-item" prop="password">
						<div class="lable">
							密码：
						</div>
						<div class="password-box">
							<input v-model="loginForm.password" placeholder="请输入密码：" :type="showPassword?'text':'password'">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>
					<div v-if="loginType==1" class="list-item captcha-item" prop="captcha">
						<div class="lable">
							验证码：
						</div>
						<input v-model="loginForm.captcha" placeholder="请输入验证码：" maxlength="4" @keyup.enter="submitForm('loginForm')">
						<img class="captcha-img" :src="captchaUrl" @click="refreshCaptcha" title="点击刷新验证码">
					</div>

					<div class="list-item" v-if="roles.length>1&&loginType<=2">
						<div class="lable">
							角色：
						</div>
						<div class="list-type" prop="role">
							<el-radio v-model="loginForm.tableName" :label="item.tableName" v-for="(item, index) in roles" :key="index" @change.native="getCurrentRow(item)">{{item.roleName}}</el-radio>
						</div>
					</div>

			
					<div class="list-btn">
						<el-button class="login_btn" v-if="loginType==1||loginType==3||loginType==4" @click="submitForm('loginForm')">登录</el-button>

						<div class="list-btn2">
							<router-link class="register_btn" :to="{path: '/register', query: {role: item.tableName,pageFlag:'register'}}" v-if="item.hasFrontRegister=='是'" v-for="(item, index) in roles2" :key="index">注册{{item.roleName.replace('注册','')}}</router-link>
						</div>
					</div>
				</div>
				<div class="idea1"></div>
				<div class="idea2"></div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css';
import menu from '@/config/menu'
export default {
	//数据集合
	data() {
		return {
            baseUrl: this.$config.baseUrl,
            loginType: 1,
			roleMenus: [],
			loginForm: {
				username: '',
				password: '',
				tableName: '',
				captcha: '',
			},
			role: '',
			roles: [],
			roles2: [],
			rules: {
				username: [
					{ required: true, message: '请输入账号', trigger: 'blur' }
				],
				password: [
					{ required: true, message: '请输入密码', trigger: 'blur' }
				],
				captcha: [
					{ required: true, message: '请输入验证码', trigger: 'blur' }
				]
			},
			flag: false,
			verifyCheck2: false,
			showPassword: false,
			indexBgUrl: '',
			captchaUrl: ''
		}
	},
	components: {
	},
	created() {
		this.$http.get('config/info?name=fLoginBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		this.roleMenus = menu.list()
		for(let item in this.roleMenus) {
			if(this.roleMenus[item].hasFrontLogin=='是') {
				this.roles.push(this.roleMenus[item]);
			}
			if(this.roleMenus[item].hasFrontRegister=='是') {
				this.roles2.push(this.roleMenus[item]);
			}
		}
		this.refreshCaptcha();
		
	},
	mounted() {
	},
	//方法集合
	methods: {
		getCurrentRow(row) {
			this.role = row.roleName;
		},
		refreshCaptcha() {
			this.captchaUrl = `/springboot02b8755d/captcha?time=${new Date().getTime()}`;
		},
		submitForm(formName) {
			if(this.loginType==1) {
				if (this.roles.length!=1) {
					if (!this.role) {
						this.$message.error("请选择登录用户类型");
						return false;
					}
				} else {
					this.role = this.roles[0].roleName;
					this.loginForm.tableName = this.roles[0].tableName;
				}
				if (!this.loginForm.username) {
					this.$message.error("请输入用户名");
					return;
				}
				if (!this.loginForm.password) {
					this.$message.error("请输入密码");
					return;
				}
				if (!this.loginForm.captcha) {
					this.$message.error("请输入验证码");
					return;
				}
			}

			this.loginPost(formName)
		},
		loginPost(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.$http.get(`${this.loginForm.tableName}/login`, {params: this.loginForm}).then(res => {
						if (res.data.code === 0) {
							localStorage.setItem('frontToken', res.data.token);
							localStorage.setItem('UserTableName', this.loginForm.tableName);
							localStorage.setItem('username', this.loginForm.username);
							localStorage.setItem('frontSessionTable', this.loginForm.tableName);
							localStorage.setItem('frontRole', this.role);
							localStorage.setItem('keyPath', 0);
							this.$router.push('/');
							this.$message({
								message: '登录成功',
								type: 'success',
								duration: 1500,
							});
						} 
						else {
							this.$message.error(res.data.msg);
							this.loginForm.captcha = '';
							this.refreshCaptcha();
						}
					});
				} else {
					return false;
				}
			});
		},
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.login-container {
		background-repeat: no-repeat;
		background-size: 100% 100% !important;
		background: url(http://codegen.caihongy.cn/20250906/cbc81fd7aac24eeda3ab25df0431839b.jpg);
		display: flex;
		width: 100%;
		min-height: 100vh;
		justify-content: center;
		align-items: center;
		background-position: center center;
		position: relative;
		background: url(http://codegen.caihongy.cn/20250906/cbc81fd7aac24eeda3ab25df0431839b.jpg);
		.login_form {
			border-radius: 0;
			padding: 40px 40px;
			box-shadow: 0 0px 0px rgba(0, 0, 0, .5);
			margin: 0 auto 0 15%;
			z-index: 10;
			background: rgba(255,255,255,.5);
			width: 680px;
			height: auto;
			.login_form2 {
				width: 100%;
				.login-title {
					padding: 0 0 40px;
					margin: 0 0 30px 0;
					text-shadow: none;
					color: #000;
					background: url(http://codegen.caihongy.cn/20250906/c5990d1952674958937e5dc9d1face72.png) no-repeat left bottom;
					width: 100%;
					font-size: 20px;
					line-height: 1.5;
					text-align: center;
				}
				.list-item {
					margin: 0 auto 20px;
					display: flex;
					width: 100%;
					border-color: #ccc;
					border-width: 0 0 1px;
					align-items: center;
					border-style: dashed;
					.lable {
						color: #000;
						width: 100px;
						font-size: 16px;
						line-height: 40px;
						text-align: right;
					}
					input {
						border: 0px solid #ddd;
						border-radius: 4px;
						padding: 0 10px;
						box-shadow: none;
						color: #666;
						flex: 1;
						background: none;
						width: calc(100% - 100px);
						font-size: 16px;
						height: 44px;
					}
					input:focus {
						border: 0px solid #ddd;
						border-radius: 4px;
						padding: 0 10px;
						box-shadow: none;
						color: #666;
						flex: 1;
						background: none;
						width: calc(100% - 100px);
						font-size: 16px;
						height: 44px;
					}
					.password-box {
						flex: 1;
						display: flex;
						width: calc(100% - 100px);
						position: relative;
						align-items: center;
						input {
							border: 0px solid #ddd;
							border-radius: 4px;
							padding: 0 10px;
							box-shadow: none;
							color: #666;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 44px;
						}
						input:focus {
							border: 0px solid #ddd;
							border-radius: 4px;
							padding: 0 10px;
							box-shadow: none;
							color: #666;
							flex: 1;
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
							right: 15px;
						}
					}
					.captcha-img {
						border: 1px solid #ccc;
						border-radius: 4px;
						cursor: pointer;
						margin-left: 10px;
						width: 120px;
						height: 36px;
						object-fit: cover;
						flex: 0 0 120px;
					}
					input::placeholder {
						color: #123;
						font-size: 16px;
					}
				}
				.list-type {
					flex: 1;
					width: 100%;
					/deep/ .el-radio__input .el-radio__inner {
						background: rgba(53, 53, 53, 0);
						border-color: #333;
					}
					/deep/ .el-radio__input.is-checked .el-radio__inner {
						background: #6aac5a;
						border-color: #6aac5a;
					}
					/deep/ .el-radio__label {
						color: #333;
						font-size: 16px;
					}
					/deep/ .el-radio__input.is-checked+.el-radio__label {
						color: #6aac5a;
						font-size: 16px;
					}
				}
				.list-btn {
					padding: 0;
					margin: 30px auto;
					width: 100%;
					text-align: center;
					.login_btn {
						border: 0;
						cursor: pointer;
						padding: 0 24px;
						margin: 0 5px;
						color: #fff;
						font-size: 20px;
						transition: all 0s;
						border-radius: 0px;
						outline: none;
						background: #6aac5a;
						width: auto;
						min-width: 150px;
						height: 44px;
					}
					.login_btn:hover {
						opacity: 0.8;
					}
					.list-btn2 {
						padding: 0;
						margin: 20px auto;
						width: 100%;
						text-align: center;
						.register_btn {
							cursor: pointer;
							margin: 0 10px 10px 5px;
							color: #222;
							background: none;
							text-decoration: none;
							font-size: 18px;
							float: left;
						}
						.register_btn:hover {
							opacity: 0.5;
						}
						.resetpwd_btn {
							cursor: pointer;
							margin: 0 0 10px 5px;
							color: #333;
							background: none;
							text-decoration: none;
							font-size: 18px;
							float: right;
						}
						.resetpwd_btn:hover {
							opacity: 0.5;
						}
					}
				}
			}
			.idea1 {
				background: red;
				display: none;
				width: 100%;
				height: 40px;
			}
			.idea2 {
				background: blue;
				display: none;
				width: 100%;
				height: 40px;
			}
		}
	}
</style>
