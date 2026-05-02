<template>
	<div class="center-preview">
		<div class="center-title">{{ title }}</div>
		<div class="center-info">
			<div class="center-info-title">个人信息</div>

			<div class="img-box" v-if="userTableName=='juanzengren'">
				<img :src="sessionForm.touxiang?baseUrl + sessionForm.touxiang:require('@/assets/avator.png')">
			</div>
			<div class="img-box" v-if="userTableName=='jieshoujigou'">
				<img :src="sessionForm.touxiang?baseUrl + sessionForm.touxiang:require('@/assets/avator.png')">
			</div>
			<div class="info-item1" v-if="userTableName=='juanzengren'">
				<span class="icon iconfont icon-geren14"></span>
				<div class="label">账号：</div>
				<div class="text">{{sessionForm.zhanghao}}</div>
			</div>
			<div class="info-item2" v-if="userTableName=='juanzengren'">
				<span class="icon iconfont icon-fenxiang"></span>
				<div class="label">姓名：</div>
				<div class="text">{{sessionForm.xingming}}</div>
			</div>
			<div class="info-item3" v-if="userTableName=='juanzengren'">
				<span class="icon iconfont icon-shijian16"></span>
				<div class="label">性别：</div>
				<div class="text">{{sessionForm.xingbie}}</div>
			</div>
			<div class="info-item4" v-if="userTableName=='juanzengren'">
				<span class="icon iconfont icon-qianshuxieyi"></span>
				<div class="label">联系方式：</div>
				<div class="text">{{sessionForm.lianxifangshi}}</div>
			</div>
		
		</div>
	
		<div class="center-box">
			<div class="center-tab-view">
				<div class="center-tab" :class="activeName==title2?'is-active':''" @click="handleClick(title2)">{{title2}}</div>
				<div class="center-tab" :class="activeName=='修改密码'?'is-active':''" @click="handleClick('修改密码')">修改密码</div>
				<div class="center-tab" v-if="hasBack(item.menu,item.child[0].tableName)" v-for="(item,index) in menuList" :key="index" @mouseenter="centerTabEnter(item.child[0].tableName)" @mouseleave="centerTabEnter('')" @click="menuClick(item.child[0],item.child.length)">
					<template v-if="item.child.length==1">
						{{item.child[0].menu}}
					</template>
					<template v-else>
						{{item.menu}}
						<transition name="el-fade-in-linear">
							<div class="center-second-tab-view" v-if="showActive=='show' + item.child[0].tableName">
								<div class="center-second-tab" v-for="(items,indexs) in item.child" :key="indexs" @click="menuClick(items)">
									{{items.menu}}
								</div>
							</div>
						</transition>
					</template>
				</div>


			</div>
			<div class="center-content-box">
				<div class="center-content-view" v-show="activeName=='个人中心'">
					<el-form class="center-preview-pv" ref="sessionForm" :model="sessionForm" :rules="rules" label-width="180px">
						<el-form-item class="center-item" v-if="userTableName=='juanzengren'" label="账号" prop="zhanghao">
							<el-input v-model="sessionForm.zhanghao" placeholder="账号" :disabled="ro.zhanghao"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='juanzengren'" label="姓名" prop="xingming">
							<el-input v-model="sessionForm.xingming" placeholder="姓名" :disabled="ro.xingming"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='juanzengren'" label="性别" prop="xingbie">
							<el-select filterable v-model="sessionForm.xingbie" placeholder="请选择性别" :disabled="ro.xingbie">
								<el-option v-for="(item, index) in dynamicProp.xingbie" :key="index" :label="item" :value="item"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='juanzengren'" label="联系方式" prop="lianxifangshi">
							<el-input v-model="sessionForm.lianxifangshi" placeholder="联系方式" :disabled="ro.lianxifangshi"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='juanzengren'" label="头像" prop="touxiang">
							<file-upload
								tip="点击上传头像"
								action="file/upload"
								:limit="1"
								:multiple="true"
								:fileUrls="sessionForm.touxiang?sessionForm.touxiang:''"
								@change="juanzengrentouxiangHandleAvatarSuccess"
								></file-upload>
						</el-form-item>
						<el-form-item class="center-btn-item">
							<div class="updateBtn" type="primary" @click="onSubmit('sessionForm')">
								<span class="icon iconfont icon-xiugai13"></span>
								<span class="text">保存信息</span>
							</div>
							<div class="closeBtn" type="danger" @click="logout">
								<span class="icon iconfont icon-shanchu7"></span>
								<span class="text">退出登录</span>
							</div>
						</el-form-item>
					</el-form>
				</div>
				<div class="center-content-view" v-show="activeName=='修改密码'">
					<el-form v-if="psdType==1" class="center-preview-pv" ref="passwordForm" :model="passwordForm" :rules="passwordRules" label-width="180px">
						<el-form-item class="center-item" label="原密码" prop="password">
							<el-input type="password" v-model="passwordForm.password" placeholder="原密码"></el-input>
						</el-form-item>
						<el-form-item class="center-item" label="新密码" prop="newpassword">
							<el-input type="password" v-model="passwordForm.newpassword" placeholder="新密码"></el-input>
						</el-form-item>
						<el-form-item class="center-item" label="确认密码" prop="repassword">
							<el-input type="password" v-model="passwordForm.repassword" placeholder="确认密码"></el-input>
						</el-form-item>
						<el-form-item class="center-btn-item">
							<div class="updateBtn" type="primary" @click="updatePassword">
								<span class="icon iconfont icon-xiugai13"></span>
								<span class="text">修改密码</span>
							</div>
						</el-form-item>
					</el-form>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import config from '@/config/config';
	import menu from '@/config/menu';
	import Vue from 'vue';
	export default {
		//数据集合
		data() {
			return {
				title: '个人中心',
				title2: '个人中心',
				showActive: '',
				activeName: '个人中心',
				baseUrl: config.baseUrl,
				sessionForm: {},
				ro: {},
				passwordForm: {},
				psdType: '1',
				passwordRules: {
					password: [
						{
							required: true,
							message: "密码不能为空",
							trigger: "blur"
						}
					],
					newpassword: [
						{
							required: true,
							message: "新密码不能为空",
							trigger: "blur"
						}
					],
					repassword: [
						{
							required: true,
							message: "确认密码不能为空",
							trigger: "blur"
						}
					]
				},
				rules: {},
				menuList: [],
				disabled: false,
				uploadUrl: config.baseUrl + 'file/upload',
				imageUrl: '',
				headers: {Token: localStorage.getItem('frontToken')},
				userTableName: localStorage.getItem('UserTableName'),
				dynamicProp: {},
			}
		},
		async created() {
			let menus = menu.list()
			for(let x in menus){
				if(menus[x].tableName == this.userTableName){
					for(let i in menus[x].backMenu){
						if(menus[x].backMenu[i].child&&menus[x].backMenu[i].child.length&&menus[x].backMenu[i].child[0].tableName.indexOf('exam')!=-1){
							menus[x].backMenu.splice(i,1)
						}
					}
					this.menuList = menus[x].backMenu
				}
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'zhanghao', null);
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'xingming', null);
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'mima', null);
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'xingbie', null);
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'lianxifangshi', null);
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'touxiang', null);
			}
			if ('juanzengren' == this.userTableName) {
				this.$set(this.sessionForm, 'status', null);
			}

			if ('juanzengren' == this.userTableName) {
				if (this.rules['zhanghao']){
					this.rules['zhanghao'].push({ required: true, message: '请输入账号', trigger: 'blur' })
				}else if(!this.rules['zhanghao']) {
					this.$set(this.rules, 'zhanghao', [{ required: true, message: '请输入账号', trigger: 'blur' }]);
				}
				if (this.rules['xingming']){
					this.rules['xingming'].push({ required: true, message: '请输入姓名', trigger: 'blur' })
				}else if(!this.rules['xingming']) {
					this.$set(this.rules, 'xingming', [{ required: true, message: '请输入姓名', trigger: 'blur' }]);
				}
				if (this.rules['mima']){
					this.rules['mima'].push({ required: true, message: '请输入密码', trigger: 'blur' })
				}else if(!this.rules['mima']) {
					this.$set(this.rules, 'mima', [{ required: true, message: '请输入密码', trigger: 'blur' }]);
				}
				this.$set(this.rules, 'lianxifangshi', [{ required: false, validator: this.$validate.isMobile, trigger: 'blur' }]);
				this.$set(this.rules, 'status', [{ required: false, validator: this.$validate.isIntNumer, trigger: 'blur' }]);
				this.ro = {
					zhanghao: true,
					xingming: false,
					mima: false,
					xingbie: false,
					lianxifangshi: false,
					touxiang: false,
					status: false,
				}
			}

			this.init();
			await this.$http.get(`${localStorage.getItem('UserTableName')}/session`, {emulateJSON: true}).then(async res => {
				if (res.data.code == 0) {
					this.sessionForm = res.data.data
				}
			});
		},
		//方法集合
		methods: {
			init() {
				if ('juanzengren' == this.userTableName) {
					this.dynamicProp.xingbie = '男,女'.split(',');
				}
			},
			setSession(){
				localStorage.setItem('sessionForm',JSON.stringify(this.sessionForm))
			},
			onSubmit(formName) {
				if(`juanzengren` == this.userTableName && this.sessionForm.touxiang!=null){
					this.sessionForm.touxiang = this.sessionForm.touxiang.replace(new RegExp(this.$config.baseUrl,"g"),"");
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
							if (res.data.code == 0) {
								this.setSession()
								this.$message({
									message: '更新成功',
									type: 'success',
									duration: 1500
								});
							}
						});
					} else {
						return false;
					}
				});
			},
			juanzengrentouxiangHandleAvatarSuccess(fileUrls) {
				this.sessionForm.touxiang = fileUrls;
			},
			handleClick(name) {
				switch(name) {
					case '个人中心':
						this.activeName = name
						this.$router.push('/index/center');
						break;
					case '修改密码':
						this.activeName = name
						this.passwordForm = {
							password: '',
							newpassword: '',
							repassword: '',
						}
						this.psdType = '1'
						this.$forceUpdate()
						break;
					case '我的收藏':
						localStorage.setItem('storeupType', 1);
						this.$router.push('/index/storeup');
						break;
					case '我的评论':
						localStorage.setItem('storeupType', 81);
						this.$router.push('/index/storeup');
						break;
				}

				this.title = event.target.outerText;
			},
			async updatePassword(){
				this.$refs["passwordForm"].validate(async valid => {
					if (valid) {
						var password = "";
						this.passwordForm.newpassword = this.passwordForm.newpassword.trim()
						this.passwordForm.repassword = this.passwordForm.repassword.trim()
						if (this.sessionForm.mima) {
							password = this.sessionForm.mima;
						} else if (this.sessionForm.password) {
							password = this.sessionForm.password;
						}
						if (this.userTableName == 'juanzengren') {
						}
						if (this.passwordForm.password != password) {
							this.$message.error("原密码错误");
							return;
						}
						if (this.passwordForm.newpassword != this.passwordForm.repassword) {
							this.$message.error("两次密码输入不一致");
							return;
						}
						if (this.passwordForm.newpassword == this.passwordForm.password) {
							this.$message.error("新密码与原密码相同！");
							return;
						}
						this.sessionForm.password = this.passwordForm.newpassword;
						this.sessionForm.mima = this.passwordForm.newpassword;
						this.$http.post(`${this.userTableName}/update`,this.sessionForm).then(({data})=>{
							if (data && data.code === 0) {
								this.$message({
									message: "修改密码成功,下次登录系统生效",
									type: "success",
									duration: 1500,
									onClose: () => {
									}
								});
								this.setSession()
							} else {
								this.$message.error(data.msg);
							}
						});
					}
				})
			},
			logout() {
				localStorage.clear();
				Vue.http.headers.common['Token'] = "";
				this.$router.push('/index/home');
				this.activeIndex = '0'
				localStorage.setItem('keyPath', this.activeIndex)
				this.$forceUpdate()
				this.$message({
					message: '登出成功',
					type: 'success',
					duration: 1500,
				});
			},
			hasBack(name,tablename){
				if(name.indexOf('章节')!=-1&&tablename.substring(0,7)=='chapter') {
					return false
				}
				return true
			},
			menuClick(row,length=1) {
				if(length==1) {
					if(row.tableName=='storeup') {
						localStorage.setItem('storeupType', row.menuJump);
						this.$router.push('/index/storeup');
						return false
					}
					this.$router.push(`/index/${row.tableName}?centerType=1`);
				}
			},
			centerTabEnter(name){
				this.showActive = name?('show' + name):''
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.center-preview {
		padding: 0 7%;
		margin: 10px auto 30px;
		background: none;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		position: relative;
		flex-wrap: wrap;
		.center-title {
			padding: 0 0 0 50px;
			margin: 10px auto;
			color: #333;
			font-weight: 600;
			display: block;
			font-size: 32px;
			border-color: #e61f4d;
			line-height: 40px;
			background: url(http://codegen.caihongy.cn/20250906/f30f666cf13e4b9eba21c610c210dcd4.png) no-repeat left center / 40px 100%;
			width: 100%;
			border-width: 0 0 0px;
			border-style: solid;
			text-align: left;
		}
		.center-info {
			border: 2px dashed #eee;
			border-radius: 0px;
			padding: 10px 10px 30px;
			box-shadow: none;
			margin: 20px 0 0 50px;
			color: #555;
			background: #ffffff;
			width: 300px;
			font-size: 16px;
			float: right;
			height: auto;
			order: 3;
			.center-info-title {
				color: #333;
				font-weight: 500;
				width: 100%;
				font-size: 22px;
				border-color: #efefef;
				border-width: 0px 0;
				line-height: 40px;
				border-style: solid;
				text-align: center;
				height: 40px;
			}
			.img-box {
				width: 100%;
				font-size: 0;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				border-style: solid;
				height: auto;
				img {
					border-radius: 0;
					margin: 10px auto;
					object-fit: contain;
					display: block;
					width: 100%;
					border-color: #efefef;
					border-width: 0 0 0px 0;
					border-style: solid;
					height: 150px;
				}
			}
			.info-item1 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 1px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item2 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 1px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item3 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 1px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item4 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 1px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item5 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 1px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item6 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 1px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
		}
		.center-box {
			border-radius: 10px;
			padding: 0;
			margin: 20px 0 0;
			background: none;
			flex: 1;
			width: calc(100% - 350px);
			.center-tab-view {
				padding: 10px 20px;
				background: rgba(255,255,255,.1);
				display: flex;
				width: 100%;
				min-height: 66px;
				border-color: #e6e6e6;
				border-width: 2px;
				line-height: 1.5;
				border-style: dashed;
				flex-wrap: wrap;
				text-align: center;
			}
			.center-tab-view .center-tab {
				border: 0;
				padding: 0 0px;
				margin: 0 10px 0 0;
				color: #333;
				font-weight: 500;
				display: inline-block;
				font-size: 16px;
				line-height: 40px;
				background: none;
				position: relative;
				list-style: none;
				min-width: 100px;
				height: 40px;
				.center-second-tab-view {
					padding: 0 10px;
					z-index: 999;
					background: #fff;
					width: 100%;
					position: relative;
					.center-second-tab {
						color: #666;
						width: 100%;
						font-size: 15px;
						border-color: #eee;
						border-width: 0 0 1px;
						border-style: solid;
					}
					.center-second-tab:hover {
						cursor: pointer;
						color: #009899;
					}
				}
			}
			.center-tab-view .center-tab:hover {
				cursor: pointer;
				padding: 0 0px;
				color: #fff;
				background: #6aac5a;
				font-weight: 500;
				font-size: 16px;
				line-height: 40px;
				position: relative;
				text-align: center;
				height: 40px;
			}
			.center-tab-view .center-tab.is-active {
				cursor: pointer;
				padding: 0 0px;
				margin: 0 10px 0 0;
				color: #fff;
				background: #6aac5a;
				font-weight: 500;
				font-size: 16px;
				line-height: 40px;
				position: relative;
				text-align: center;
				min-width: 100px;
				height: 40px;
			}
			.center-content-box {
				padding: 30px 15% 30px 3%;
				overflow: hidden;
				background: #fff;
				width: 100%;
				clear: both;
			}
			.center-content-view {
				width: 100%;
			}
			.center-preview-pv {
				.center-item.el-form-item {
					padding: 0px;
					margin: 0 0 24px;
					background: none;
					/deep/ .el-form-item__label {
						padding: 0 10px 0 0;
						color: #666;
						white-space: nowrap;
						font-weight: 500;
						width: 180px;
						font-size: 16px;
						line-height: 40px;
						text-align: right;
					}
					.el-form-item__content {
						margin-left: 180px;
					}
					.el-input {
						width: 100%;
					}
					.el-input /deep/ .el-input__inner {
						border: 0px dashed #ddd;
						border-radius: 0px;
						padding: 0 12px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						border-width: 0 0 1px;
						height: 40px;
					}
					.el-input /deep/ .el-input__inner[readonly="readonly"] {
						border: 0px solid #ddd;
						cursor: not-allowed;
						border-radius: 4px;
						padding: 0 12px;
						box-shadow: none;
						outline: none;
						color: rgba(85, 85, 127, 1.0);
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-select {
						width: 100%;
					}
					.el-select /deep/ .el-input__inner {
						border: 0px dashed #ddd;
						border-radius: 0px;
						padding: 0 10px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						border-width: 0 0 1px;
						height: 40px;
					}
					.el-select /deep/ .is-disabled .el-input__inner {
						border: 0px solid #ddd;
						cursor: not-allowed;
						border-radius: 4px;
						padding: 0 10px;
						box-shadow: none;
						outline: none;
						color: #333;
						background: #eee;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-date-editor {
						width: 100%;
					}
					.el-date-editor /deep/ .el-input__inner {
						border: 0px dashed #ddd;
						border-radius: 0px;
						padding: 0 10px 0 30px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						border-width: 0 0 1px;
						height: 40px;
					}
					.el-date-editor /deep/ .el-input__inner[readonly="readonly"] {
						border: 0;
						cursor: not-allowed;
						border-radius: 4px;
						padding: 0 10px 0 30px;
						box-shadow: none;
						outline: none;
						color: #333;
						background: #eee;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					/deep/ .el-upload--picture-card {
						background: transparent;
						border: 0;
						border-radius: 0;
						width: auto;
						height: auto;
						line-height: initial;
						vertical-align: middle;
					}
					/deep/ .upload .upload-img {
						border: 1px dashed #ddd;
						cursor: pointer;
						border-radius: 0px;
						color: #999;
						width: 80px;
						font-size: 26px;
						line-height: 80px;
						text-align: center;
						height: 80px;
					}
					/deep/ .el-upload-list .el-upload-list__item {
						border: 1px dashed #ddd;
						cursor: pointer;
						border-radius: 0px;
						color: #999;
						width: 80px;
						font-size: 26px;
						line-height: 80px;
						text-align: center;
						height: 80px;
						font-size: 14px;
						line-height: 1.8;
					}
					/deep/ .el-upload .el-icon-plus {
						border: 1px dashed #ddd;
						cursor: pointer;
						border-radius: 0px;
						color: #999;
						width: 80px;
						font-size: 26px;
						line-height: 80px;
						text-align: center;
						height: 80px;
					}
					/deep/ .el-upload__tip {
						color: #666;
						font-size: 15px;
					}
					/deep/ .el-input__inner::placeholder {
						color: #123;
						font-size: 16px;
					}
					.editor {
						border: 0px solid #ddd;
						border-radius: 4px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 14px;
						line-height: 32px;
					}
					.editor /deep/.ql-toolbar {
						border: 0px dashed #ddd;
						background: none;
						border-width: 1px 1px 0;
					}
					.editor /deep/.ql-container {
						border: 1px dashed #ddd;
						background: none;
						min-height: 180px;
					}
					.editor /deep/.ql-container .ql-blank::before {
						color: #000;
					}
				}
				.center-btn-item {
					padding: 0;
					margin: 40px auto 0;
					background: none;
					width: 100%;
					.updateBtn {
						border: 0;
						cursor: pointer;
						border-radius: 0px;
						padding: 0 15px;
						margin: 0 20px 0 0;
						outline: none;
						background: #6aac5a;
						display: inline-block;
						width: auto;
						font-size: 16px;
						line-height: 40px;
						height: 40px;
						.icon {
							color: rgba(255, 255, 255, 1);
						}
						.text {
							color: rgba(255, 255, 255, 1);
						}
					}
					.updateBtn:hover {
						opacity: 0.7;
						.icon {
							color: #fff;
						}
						.text {
							color: #fff;
						}
					}
					.closeBtn {
						border: 0;
						cursor: pointer;
						border-radius: 0px;
						padding: 0 15px;
						margin: 0 20px 0 0;
						outline: none;
						background: #ddd;
						display: inline-block;
						width: auto;
						font-size: 16px;
						line-height: 40px;
						height: 40px;
						.icon {
							color: #333;
						}
						.text {
							color: #333;
						}
					}
					.closeBtn:hover {
						opacity: 0.7;
						.icon {
						}
						.text {
						}
					}
				}
				.el-date-editor.el-input {
					width: auto;
				}
			}
		}
	}
</style>
