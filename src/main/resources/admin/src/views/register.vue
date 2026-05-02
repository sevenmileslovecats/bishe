<template>
	<div>
		<div class="register-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form v-if="pageFlag=='register'" ref="ruleForm" class="rgs-form animate__animated animate__" :model="ruleForm" :rules="rules">
				<div class="rgs-form2">
					<div class="title">公益捐赠物资管理系统</div>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('zhanghao')?'required':''">账号：</div>
						<el-input  v-model="ruleForm.zhanghao" :readonly="ro.zhanghao" autocomplete="off" placeholder="账号"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('xingming')?'required':''">姓名：</div>
						<el-input  v-model="ruleForm.xingming" :readonly="ro.xingming" autocomplete="off" placeholder="姓名"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input  v-model="ruleForm.mima" :readonly="ro.mima" autocomplete="off" placeholder="密码"  type="password"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input  v-model="ruleForm.mima2" autocomplete="off" placeholder="确认密码" type="password" :readonly="ro.mima" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('xingbie')?'required':''">性别：</div>
						<el-select filterable v-model="ruleForm.xingbie" placeholder="请选择性别" :disabled="ro.xingbie">
							<el-option
								v-for="(item,index) in juanzengrenxingbieOptions"
								v-bind:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('lianxifangshi')?'required':''">联系方式：</div>
						<el-input  v-model="ruleForm.lianxifangshi" :readonly="ro.lianxifangshi" autocomplete="off" placeholder="联系方式"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='juanzengren'">
						<div class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
						<file-upload
							tip="点击上传头像"
							action="file/upload"
							:limit="3"
							:multiple="true"
							:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
							@change="juanzengrentouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('jigouzhanghao')?'required':''">机构账号：</div>
						<el-input  v-model="ruleForm.jigouzhanghao" :readonly="ro.jigouzhanghao" autocomplete="off" placeholder="机构账号"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('jigoumingcheng')?'required':''">机构名称：</div>
						<el-input  v-model="ruleForm.jigoumingcheng" :readonly="ro.jigoumingcheng" autocomplete="off" placeholder="机构名称"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input  v-model="ruleForm.mima" :readonly="ro.mima" autocomplete="off" placeholder="密码"  type="password"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input  v-model="ruleForm.mima2" autocomplete="off" placeholder="确认密码" type="password" :readonly="ro.mima" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('quyu')?'required':''">区域：</div>
						<el-input  v-model="ruleForm.quyu" :readonly="ro.quyu" autocomplete="off" placeholder="区域"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('jigoujianjie')?'required':''">机构简介：</div>
						<el-input  v-model="ruleForm.jigoujianjie" :readonly="ro.jigoujianjie" autocomplete="off" placeholder="机构简介"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('jigoudizhi')?'required':''">机构地址：</div>
						<el-input  v-model="ruleForm.jigoudizhi" :readonly="ro.jigoudizhi" autocomplete="off" placeholder="机构地址"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('yingyezhizhao')?'required':''">营业执照：</div>
						<file-upload
							tip="点击上传营业执照"
							action="file/upload"
							:limit="3"
							:type="3"
							:multiple="true"
							:fileUrls="ruleForm.yingyezhizhao?ruleForm.yingyezhizhao:''"
							@change="jieshoujigouyingyezhizhaoUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('jigoudengjizhengshu')?'required':''">机构登记证书：</div>
						<file-upload
							tip="点击上传机构登记证书"
							action="file/upload"
							:limit="3"
							:type="3"
							:multiple="true"
							:fileUrls="ruleForm.jigoudengjizhengshu?ruleForm.jigoudengjizhengshu:''"
							@change="jieshoujigoujigoudengjizhengshuUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('lianxifangshi')?'required':''">联系方式：</div>
						<el-input  v-model="ruleForm.lianxifangshi" :readonly="ro.lianxifangshi" autocomplete="off" placeholder="联系方式"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='jieshoujigou'">
						<div class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
						<file-upload
							tip="点击上传头像"
							action="file/upload"
							:limit="3"
							:multiple="true"
							:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
							@change="jieshoujigoutouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<div class="register-btn">
						<div class="register-btn1">
							<button type="button" class="r-btn" @click="login()">注册</button>
						</div>
						<div class="register-btn2">
							<div class="r-login" @click="close()">已有账号，直接登录</div>
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css'
export default {
	data() {
		return {
			ruleForm: {
			},
			forgetForm: {},
            pageFlag : '',
			tableName:"",
			rules: {},
			ro: {},
            juanzengrenxingbieOptions: [],
			indexBgUrl: '',
		};
	},
	mounted(){
		this.pageFlag = this.$route.query.pageFlag
		if(this.$route.query.pageFlag=='register'){
			
			let table = this.$storage.get("loginTable");
			this.tableName = table;
			if(this.tableName=='juanzengren'){
				this.ruleForm = {
					zhanghao: this.getUUID(),
					xingming: '',
					mima: '',
					xingbie: '',
					lianxifangshi: '',
					touxiang: '',
					status: '',
				}
				this.ro = {
					zhanghao: false,
					xingming: false,
					mima: false,
					xingbie: false,
					lianxifangshi: false,
					touxiang: false,
					status: false,
				}
			}
			if(this.tableName=='jieshoujigou'){
				this.ruleForm = {
					jigouzhanghao: this.getUUID(),
					jigoumingcheng: '',
					mima: '',
					quyu: '',
					jigoujianjie: '',
					jigoudizhi: '',
					yingyezhizhao: '',
					jigoudengjizhengshu: '',
					lianxifangshi: '',
					touxiang: '',
					sfsh: '',
					shhf: '',
					status: '',
				}
				this.ro = {
					jigouzhanghao: false,
					jigoumingcheng: false,
					mima: false,
					quyu: false,
					jigoujianjie: false,
					jigoudizhi: false,
					yingyezhizhao: false,
					jigoudengjizhengshu: false,
					lianxifangshi: false,
					touxiang: false,
					sfsh: false,
					shhf: false,
					status: false,
				}
			}
			if ('juanzengren' == this.tableName) {
				this.rules.zhanghao = [{ required: true, message: '请输入账号', trigger: 'blur' }]
			}
			if ('juanzengren' == this.tableName) {
				this.rules.xingming = [{ required: true, message: '请输入姓名', trigger: 'blur' }]
			}
			if ('juanzengren' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('jieshoujigou' == this.tableName) {
				this.rules.jigouzhanghao = [{ required: true, message: '请输入机构账号', trigger: 'blur' }]
			}
			if ('jieshoujigou' == this.tableName) {
				this.rules.jigoumingcheng = [{ required: true, message: '请输入机构名称', trigger: 'blur' }]
			}
			if ('jieshoujigou' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			this.juanzengrenxingbieOptions = "男,女".split(',')
		}
	},
	created() {
		this.$http.get('config/info?name=bRegisterBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
	},
	destroyed() {
		  	},
	methods: {
		changeRules(name){
			if(this.rules[name]){
				return true
			}
			return false
		},
		// 获取uuid
		getUUID () {
			return new Date().getTime();
		},
		close(){
			this.$router.push({ path: "/login" });
		},
        juanzengrentouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },
        jieshoujigouyingyezhizhaoUploadChange(fileUrls) {
            this.ruleForm.yingyezhizhao = fileUrls;
        },
        jieshoujigoujigoudengjizhengshuUploadChange(fileUrls) {
            this.ruleForm.jigoudengjizhengshu = fileUrls;
        },
        jieshoujigoutouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },

        // 多级联动参数


		// 注册
		login() {
			var url=this.tableName+"/register";
			if(this.tableName=='juanzengren' && this.ruleForm.zhanghao){
				this.ruleForm.zhanghao = String(this.ruleForm.zhanghao)
			}
			if((!this.ruleForm.zhanghao) && `juanzengren` == this.tableName){
				this.$message.error(`账号不能为空`);
				return
			}
			if((!this.ruleForm.xingming) && `juanzengren` == this.tableName){
				this.$message.error(`姓名不能为空`);
				return
			}
			if((!this.ruleForm.mima) && `juanzengren` == this.tableName){
				this.$message.error(`密码不能为空`);
				return
			}
			if((this.ruleForm.mima!=this.ruleForm.mima2) && `juanzengren` == this.tableName){
				this.$message.error(`两次密码输入不一致`);
				return
			}
			if(`juanzengren` == this.tableName && this.ruleForm.lianxifangshi &&(!this.$validate.isMobile(this.ruleForm.lianxifangshi))){
				this.$message.error(`联系方式应输入手机格式`);
				return
			}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
			  if(this.tableName=='jieshoujigou'){
				  this.ruleForm.sfsh = '待审核'
			  }
			if(this.tableName=='jieshoujigou' && this.ruleForm.jigouzhanghao){
				this.ruleForm.jigouzhanghao = String(this.ruleForm.jigouzhanghao)
			}
			if((!this.ruleForm.jigouzhanghao) && `jieshoujigou` == this.tableName){
				this.$message.error(`机构账号不能为空`);
				return
			}
			if((!this.ruleForm.jigoumingcheng) && `jieshoujigou` == this.tableName){
				this.$message.error(`机构名称不能为空`);
				return
			}
			if((!this.ruleForm.mima) && `jieshoujigou` == this.tableName){
				this.$message.error(`密码不能为空`);
				return
			}
			if((this.ruleForm.mima!=this.ruleForm.mima2) && `jieshoujigou` == this.tableName){
				this.$message.error(`两次密码输入不一致`);
				return
			}
            if(this.ruleForm.yingyezhizhao!=null) {
                this.ruleForm.yingyezhizhao = this.ruleForm.yingyezhizhao.replace(new RegExp(this.$base.url,"g"),"");
            }
            if(this.ruleForm.jigoudengjizhengshu!=null) {
                this.ruleForm.jigoudengjizhengshu = this.ruleForm.jigoudengjizhengshu.replace(new RegExp(this.$base.url,"g"),"");
            }
			if(`jieshoujigou` == this.tableName && this.ruleForm.lianxifangshi &&(!this.$validate.isMobile(this.ruleForm.lianxifangshi))){
				this.$message.error(`联系方式应输入手机格式`);
				return
			}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
			this.$http({
				url: url,
				method: "post",
				data:this.ruleForm
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message({
						message: "注册成功",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.$router.replace({ path: "/login" });
						}
					});
				} else {
					this.$message.error(data.msg);
				}
			});
		},
	}
};
</script>

<style lang="scss" scoped>
.register-container {
	position: relative;
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
	.rgs-form {
		.rgs-form2 {
		border-radius: 20px;
		box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1),0 4px 6px -4px rgba(0,0,0,0.1);
		padding: 10px 50px;
		backdrop-filter: blur(10px);
		margin: 20px auto;
		background: rgba(255,255,255,1);
		width: 100%;
		}
		border-radius: 10px;
		padding: 40px 20px 20px;
		margin: 0 auto;
		z-index: 1;
		background: none;
		display: flex;
		width: 720px;
		flex-wrap: wrap;
		height: auto;
		.title {
			margin: 10px 0 10px 0;
			text-shadow: none;
			color: #ff9164;
			font-weight: 600;
			width: 100%;
			font-size: 18px;
			line-height: 44px;
			text-align: center;
		}
		.list-item {
			border: 1px solid #ff916450;
			border-radius: 10px;
			padding: 0 0 0 130px;
			margin: 0 auto 15px;
			background: none;
			width: 100%;
			position: relative;
			height: auto;
			/deep/ .el-form-item__content {
				display: block;
			}
			.lable {
				padding: 0 10px 0 0;
				color: #333;
				left: -130px;
				width: 130px;
				font-size: 16px;
				line-height: 44px;
				position: absolute !important;
				text-align: right;
			}
			.el-input {
				width: 100%;
			}
			.el-input /deep/ .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 30px;
				padding: 0 10px;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-input /deep/ .el-input__inner:focus {
				border: 0px solid #ff7a00;
				border-radius:  30px;
				padding: 0 10px;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-input-number {
				width: 100%;
			}
			.el-input-number /deep/ .el-input__inner {
				text-align: center;
				border: 0px solid #ddd;
				border-radius: 30px;
				padding: 0 10px;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-input-number /deep/ .el-input__inner:focus {
				border: 0px solid #ff7a00;
				border-radius:  30px;
				padding: 0 10px;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-input-number /deep/ .el-input-number__decrease {
				display: none;
			}
			.el-input-number /deep/ .el-input-number__increase {
				display: none;
			}
			.el-select {
				width: 100%;
			}
			.el-select /deep/ .el-input__inner {
				border: 0px solid #ddd;
				border-radius:  30px;
				padding: 0 10px;
				color: #666;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-select /deep/ .el-input__inner:focus {
				border: 0px solid #ff7a00;
				border-radius:  30px;
				padding: 0 10px;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-date-editor {
				width: 100%;
			}
			.el-date-editor /deep/ .el-input__inner {
				border: 0px solid #ddd;
				border-radius:  30px;
				padding: 0 10px 0 30px;
				color: #666;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-date-editor /deep/ .el-input__inner:focus {
				border: 0px solid #ff7a00;
				border-radius:  30px;
				padding: 0 10px 0 30px;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 44px;
			}
			.el-date-editor.el-input {
				width: 100%;
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
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0;
				color: #999;
				width: 90px;
				font-size: 32px;
				line-height: 90px;
				text-align: center;
				height: 90px;
			}
			/deep/ .el-upload-list .el-upload-list__item {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0;
				color: #999;
				width: 90px;
				font-size: 32px;
				line-height: 90px;
				text-align: center;
				height: 90px;
			}
			/deep/ .el-upload .el-icon-plus {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0;
				color: #999;
				width: 90px;
				font-size: 32px;
				line-height: 90px;
				text-align: center;
				height: 90px;
			}
			/deep/ .el-upload__tip {
				color: #838fa1;
				font-size: 14px;
			}
			/deep/ .el-input__inner::placeholder {
				color: #666;
				font-size: 16px;
			}
			.required {
				position: relative;
			}
			.required::after{
				color: red;
				left: 120px;
				position: absolute;
				content: "*";
			}
			.editor {
				width: 100%;
				height: auto;
			}
			.editor>.avatar-uploader {
				line-height: 0;
				height: 0;
			}
		}
		.list-item.email {
			input {
				border: 0px solid #ddd;
				border-radius: 30px 0 0 30px;
				padding: 0 10px;
				box-shadow: none;
				margin: 0;
				outline: none;
				color: #333;
				background: #fff;
				flex: 1;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			input:focus {
				border: 0px solid #ff7a00;
				border-radius: 30px 0 0 30px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				flex: 1;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			input::placeholder {
				color: #666;
				font-size: 16px;
			}
			button {
				border: 0;
				cursor: pointer;
				border-radius: 0 10px 10px 0;
				padding: 0 0px;
				box-shadow: 0 0 0px rgba(64, 158, 255, .5);
				margin: 0;
				outline: none;
				color: #fff;
				background: #ff9164;
				width: 130px;
				font-size: 15px;
				height: 44px;
			}
			button:hover {
				opacity: 0.5;
			}
		}
		.register-btn {
			width: 100%;
		}
		.register-btn1 {
			width: 100%;
		}
		.register-btn2 {
			margin: 10px 0;
			width: 100%;
			text-align: center;
		}
		.r-btn {
			border: 0;
			cursor: pointer;
			padding: 0 10px;
			margin: 20px auto 5px;
			color: #fff;
			display: block;
			font-size: 22px;
			border-radius: 10px;
			box-shadow: none;
			outline: none;
			background: #ff9164;
			width: 100%;
			height: 50px;
		}
		.r-btn:hover {
			opacity: 0.5;
		}
		.r-login {
			cursor: pointer;
			padding: 0 10%;
			color: #666;
			display: inline-block;
			font-size: 16px;
			line-height: 2;
		}
		.r-login:hover {
			opacity: 0.5;
		}
	}
}
	
	::-webkit-scrollbar {
	  display: none;
	}
</style>
