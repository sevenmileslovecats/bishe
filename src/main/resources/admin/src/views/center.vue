<template>
	<div :style='{"padding":"50px 30px 30px"}'>
		<el-form
			:style='{"padding":"50px 20% 50px 10%","boxShadow":"none","borderColor":"#ff9164","borderRadius":"10px","flexWrap":"wrap","background":"#fff8f0","borderWidth":"4px 0 0 0","display":"flex","borderStyle":"solid"}'
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			label-width="180px"
		>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='juanzengren'"  label="账号" prop="zhanghao">
					<el-input v-model="ruleForm.zhanghao" :readonly="ro.zhanghao" placeholder="账号" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='juanzengren'"  label="姓名" prop="xingming">
					<el-input v-model="ruleForm.xingming" :readonly="ro.xingming" placeholder="姓名" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='juanzengren'"  label="性别" prop="xingbie">
					<el-select filterable v-model="ruleForm.xingbie" :disabled="ro.xingbie" placeholder="请选择性别">
						<el-option
							v-for="(item,index) in juanzengrenxingbieOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='juanzengren'"  label="联系方式" prop="lianxifangshi">
					<el-input v-model="ruleForm.lianxifangshi" :readonly="ro.lianxifangshi" placeholder="联系方式" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='juanzengren'" label="头像" prop="touxiang">
					<file-upload
						tip="点击上传头像"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
						@change="juanzengrentouxiangUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='jieshoujigou'"  label="机构账号" prop="jigouzhanghao">
					<el-input v-model="ruleForm.jigouzhanghao" :readonly="ro.jigouzhanghao" placeholder="机构账号" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='jieshoujigou'"  label="机构名称" prop="jigoumingcheng">
					<el-input v-model="ruleForm.jigoumingcheng" :readonly="ro.jigoumingcheng" placeholder="机构名称" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='jieshoujigou'"  label="区域" prop="quyu">
					<el-input v-model="ruleForm.quyu" :readonly="ro.quyu" placeholder="区域" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='jieshoujigou'"  label="机构简介" prop="jigoujianjie">
					<el-input v-model="ruleForm.jigoujianjie" :readonly="ro.jigoujianjie" placeholder="机构简介" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='jieshoujigou'"  label="机构地址" prop="jigoudizhi">
					<el-input v-model="ruleForm.jigoudizhi" :readonly="ro.jigoudizhi" placeholder="机构地址" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='jieshoujigou'" label="营业执照" prop="yingyezhizhao">
					<file-upload
						tip="点击上传营业执照"
						action="file/upload"
						:limit="1"
						:type="3"
						:multiple="false"
						:fileUrls="ruleForm.yingyezhizhao?ruleForm.yingyezhizhao:''"
						@change="jieshoujigouyingyezhizhaoUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='jieshoujigou'" label="机构登记证书" prop="jigoudengjizhengshu">
					<file-upload
						tip="点击上传机构登记证书"
						action="file/upload"
						:limit="1"
						:type="3"
						:multiple="false"
						:fileUrls="ruleForm.jigoudengjizhengshu?ruleForm.jigoudengjizhengshu:''"
						@change="jieshoujigoujigoudengjizhengshuUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}'   v-if="flag=='jieshoujigou'"  label="联系方式" prop="lianxifangshi">
					<el-input v-model="ruleForm.lianxifangshi" :readonly="ro.lianxifangshi" placeholder="联系方式" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='jieshoujigou'" label="头像" prop="touxiang">
					<file-upload
						tip="点击上传头像"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
						@change="jieshoujigoutouxiangUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='users'" label="用户名" prop="username">
					<el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"100%","margin":"0 0 20px 0"}' v-if="flag=='users'" label="头像" prop="image">
					<file-upload
						tip="点击上传头像"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.image?ruleForm.image:''"
						@change="usersimageUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","padding":"0","margin":"10px auto 0","justifyContent":"center","display":"flex"}'>
					<el-button class="btn3" type="primary" @click="onUpdateHandler">
						<span class="icon iconfont icon-queren15"></span>
						保存信息
					</el-button>
				</el-form-item>
		</el-form>
	</div>
</template>
<script>
// 校验引入
	import { 
		isIntNumer,
		isMobile,
	} from "@/utils/validate";

	export default {
		data() {
			return {
				ruleForm: {},
				ro: {},
				flag: '',
				usersFlag: false,
				juanzengrenxingbieOptions: [],
			};
		},
		mounted() {
			var table = this.$storage.get("sessionTable");
			this.flag = table;
			this.$http({
				url: `${this.$storage.get("sessionTable")}/session`,
				method: "get"
			}).then(({ data }) => {
				if (data && data.code === 0) {
					if(table == 'juanzengren') {
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
					if(table == 'jieshoujigou') {
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

					this.ruleForm = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
			this.juanzengrenxingbieOptions = "男,女".split(',')
		},
		methods: {
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
			usersimageUploadChange(fileUrls) {
				this.ruleForm.image = fileUrls;
			},
			onUpdateHandler() {
				if((!this.ruleForm.zhanghao)&& 'juanzengren'==this.flag){
					this.$message.error('账号不能为空');
					return
				}
				if((!this.ruleForm.xingming)&& 'juanzengren'==this.flag){
					this.$message.error('姓名不能为空');
					return
				}
				if((!this.ruleForm.mima)&& 'juanzengren'==this.flag){
					this.$message.error('密码不能为空');
					return
				}
				if('juanzengren' ==this.flag && this.ruleForm.lianxifangshi&&(!isMobile(this.ruleForm.lianxifangshi))){
					this.$message.error(`联系方式应输入手机格式`);
					return
				}
				if(this.ruleForm.touxiang!=null) {
					this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('juanzengren' ==this.flag && this.ruleForm.status&&(!isIntNumer(this.ruleForm.status))){
					this.$message.error(`状态应输入整数`);
					return
				}
				if((!this.ruleForm.jigouzhanghao)&& 'jieshoujigou'==this.flag){
					this.$message.error('机构账号不能为空');
					return
				}
				if((!this.ruleForm.jigoumingcheng)&& 'jieshoujigou'==this.flag){
					this.$message.error('机构名称不能为空');
					return
				}
				if((!this.ruleForm.mima)&& 'jieshoujigou'==this.flag){
					this.$message.error('密码不能为空');
					return
				}
				if(this.ruleForm.yingyezhizhao!=null) {
					this.ruleForm.yingyezhizhao = this.ruleForm.yingyezhizhao.replace(new RegExp(this.$base.url,"g"),"");
				}
				if(this.ruleForm.jigoudengjizhengshu!=null) {
					this.ruleForm.jigoudengjizhengshu = this.ruleForm.jigoudengjizhengshu.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('jieshoujigou' ==this.flag && this.ruleForm.lianxifangshi&&(!isMobile(this.ruleForm.lianxifangshi))){
					this.$message.error(`联系方式应输入手机格式`);
					return
				}
				if(this.ruleForm.touxiang!=null) {
					this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('jieshoujigou' ==this.flag && this.ruleForm.status&&(!isIntNumer(this.ruleForm.status))){
					this.$message.error(`状态应输入整数`);
					return
				}
				if('users'==this.flag && this.ruleForm.username.trim().length<1) {
					this.$message.error(`用户名不能为空`);
					return	
				}
				if(this.flag=='users'){
					this.ruleForm.image = this.ruleForm.image.replace(new RegExp(this.$base.url,"g"),"")
				}
				this.$http({
					url: `${this.$storage.get("sessionTable")}/update`,
					method: "post",
					data: this.ruleForm
				}).then(({ data }) => {
					if (data && data.code === 0) {
						if(this.flag=='users'){
							this.$storage.set('headportrait',this.ruleForm.image)
						}else {
							if(this.flag == 'juanzengren') {
								this.$storage.set('headportrait',this.ruleForm.touxiang)
							}
							if(this.flag == 'jieshoujigou') {
								this.$storage.set('headportrait',this.ruleForm.touxiang)
							}
						}
						this.$message({
							message: "修改信息成功",
							type: "success",
							duration: 1500,
							onClose: () => {
								window.location.reload();
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
	.el-date-editor.el-input {
		width: auto;
	}
	
	.add-update-preview .el-form-item /deep/ .el-form-item__label {
				padding: 0 10px 0 0;
				color: #666;
				white-space: nowrap;
				font-weight: 500;
				width: 180px;
				font-size: 16px;
				line-height: 40px;
				text-align: right;
			}
	
	.add-update-preview .el-form-item /deep/ .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	
	.add-update-preview .el-input /deep/ .el-input__inner {
				border: 1px solid #e7e8fc;
				border-radius: 4px;
				padding: 0 12px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 40px;
			}
	
	.add-update-preview .el-select /deep/ .el-input__inner {
				border: 1px solid #e7e8fc;
				border-radius: 4px;
				padding: 0 10px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 40px;
			}
	
	.add-update-preview .el-date-editor /deep/ .el-input__inner {
				border: 1px solid #e7e8fc;
				border-radius: 4px;
				padding: 0 10px 0 30px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 40px;
			}
	
	.add-update-preview /deep/ .el-upload--picture-card {
		background: transparent;
		border: 0;
		border-radius: 0;
		width: auto;
		height: auto;
		line-height: initial;
		vertical-align: middle;
	}
	
	.add-update-preview /deep/ .el-upload-list .el-upload-list__item {
				border: 1px solid #e7e8fc;
				cursor: pointer;
				border-radius: 6px;
				color: #999;
				background: #fff;
				width: 80px;
				font-size: 26px;
				line-height: 80px;
				text-align: center;
				height: 80px;
			}
	
	.add-update-preview /deep/ .el-upload .el-icon-plus {
				border: 1px solid #e7e8fc;
				cursor: pointer;
				border-radius: 6px;
				color: #999;
				background: #fff;
				width: 80px;
				font-size: 26px;
				line-height: 80px;
				text-align: center;
				height: 80px;
			}
	
	.add-update-preview .el-textarea /deep/ .el-textarea__inner {
				border: 1px solid #e7e8fc;
				border-radius: 4px;
				padding: 12px;
				box-shadow: none;
				outline: none;
				color: #666;
				width: 100%;
				font-size: 16px;
				height: auto;
			}
	
	.add-update-preview .btn3 {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 24px;
				margin: 0 20px 0 0;
				outline: none;
				color: #fff;
				background: #2f6a38;
				width: auto;
				font-size: 16px;
				height: 40px;
				.iconfont {
						margin: 0 2px;
						color: #fff;
						display: none;
						font-size: 16px;
						height: 40px;
					}
	}
	
	.add-update-preview .btn3:hover {
				opacity: 0.8;
			}
	.editor>.avatar-uploader {
		line-height: 0;
		height: 0;
	}
</style>
