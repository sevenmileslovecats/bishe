






























<template>
	<div class="add-update-preview">
		<el-form
			class="add-update-form"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="200px"
			>
			<el-form-item class="add-item" label="捐赠编号" prop="juanzengbianhao">
				<el-input v-model="ruleForm.juanzengbianhao" 
					placeholder="捐赠编号" clearable :readonly="ro.juanzengbianhao"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="物资名称" prop="wuzimingcheng">
				<el-input v-model="ruleForm.wuzimingcheng" 
					placeholder="物资名称" clearable :readonly="ro.wuzimingcheng"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="物资种类" prop="wuzizhonglei">
				<el-input v-model="ruleForm.wuzizhonglei" 
					placeholder="物资种类" clearable :readonly="ro.wuzizhonglei"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="物资说明" prop="wuzishuoming">
				<el-input v-model="ruleForm.wuzishuoming" 
					placeholder="物资说明" clearable :readonly="ro.wuzishuoming"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="物资图片" v-if="type!='cross' || (type=='cross' && !ro.wuzitupian)" prop="wuzitupian">
				<file-upload
					tip="点击上传物资图片"
					action="file/upload"
					:limit="3"
					:multiple="true"
					:disabled="ro.wuzitupian"
					:fileUrls="ruleForm.wuzitupian?ruleForm.wuzitupian:''"
					@change="wuzitupianUploadChange"
					></file-upload>
			</el-form-item>
			<el-form-item class="add-item" v-else label="物资图片" prop="wuzitupian">
				<img v-if="ruleForm.wuzitupian.substring(0,4)=='http'" class="upload-img" v-bind:key="index" :src="ruleForm.wuzitupian.split(',')[0]">
				<img v-else class="upload-img" v-bind:key="index" v-for="(item,index) in ruleForm.wuzitupian.split(',')" :src="baseUrl+item">
			</el-form-item>
			<el-form-item class="add-item" label="新旧程度" prop="xinjiuchengdu">
				<el-input v-model="ruleForm.xinjiuchengdu" 
					placeholder="新旧程度" clearable :readonly="ro.xinjiuchengdu"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="物资数量" prop="wuzishuliang">
				<el-input v-model.number="ruleForm.wuzishuliang" 
					placeholder="物资数量" clearable :readonly="ro.wuzishuliang"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="物资重量" prop="wuzizhongliang">
				<el-input-number v-model="ruleForm.wuzizhongliang" placeholder="物资重量" :disabled="ro.wuzizhongliang"></el-input-number>
			</el-form-item>
			<el-form-item class="add-item" label="有效期" prop="youxiaoqi">
				<el-input v-model="ruleForm.youxiaoqi" 
					placeholder="有效期" clearable :readonly="ro.youxiaoqi"></el-input>
			</el-form-item>
			<el-form-item class="add-item"  label="验收结果" prop="yanshoujieguo">
				<el-select v-model="ruleForm.yanshoujieguo" placeholder="请选择验收结果" :disabled="ro.yanshoujieguo"  filterable>
					<el-option
						v-for="(item,index) in yanshoujieguoOptions"
						:key="index"
						:label="item"
						:value="item">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item class="add-item" label="验收记录" prop="yanshoujilu">
				<el-input v-model="ruleForm.yanshoujilu" 
					placeholder="验收记录" clearable :readonly="ro.yanshoujilu"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="验收人" prop="yanshouren">
				<el-input v-model="ruleForm.yanshouren" 
					placeholder="验收人" clearable :readonly="ro.yanshouren"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="验收时间" prop="yanshoushijian">
				<el-date-picker
					:disabled="ro.yanshoushijian"
					value-format="yyyy-MM-dd HH:mm:ss"
					v-model="ruleForm.yanshoushijian" 
					type="datetime"
					placeholder="验收时间">
				</el-date-picker>
			</el-form-item>
			<el-form-item class="add-item" label="账号" prop="zhanghao">
				<el-input v-model="ruleForm.zhanghao" 
					placeholder="账号" clearable :readonly="ro.zhanghao"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="姓名" prop="xingming">
				<el-input v-model="ruleForm.xingming" 
					placeholder="姓名" clearable :readonly="ro.xingming"></el-input>
			</el-form-item>

			<el-form-item class="add-btn-item">
				<el-button class="submitBtn"  type="primary" @click="onSubmit(null)">
					<span class="icon iconfont icon-xiugai13"></span>
					<span class="text">确认</span>
				</el-button>
				<el-button class="closeBtn" @click="back()">
					<span class="icon iconfont icon-shanchu8"></span>
					<span class="text">取消</span>
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				baseUrl: '',
				ro:{
					juanzengbianhao : false,
					wuzimingcheng : false,
					wuzizhonglei : false,
					wuzishuoming : false,
					wuzitupian : false,
					xinjiuchengdu : false,
					wuzishuliang : false,
					wuzizhongliang : false,
					youxiaoqi : false,
					yanshoujieguo : false,
					yanshoujilu : false,
					yanshouren : false,
					yanshoushijian : false,
					zhanghao : false,
					xingming : false,
				},
				type: '',
				userTableName: localStorage.getItem('UserTableName'),
				ruleForm: {
					juanzengbianhao: '',
					wuzimingcheng: '',
					wuzizhonglei: '',
					wuzishuoming: '',
					wuzitupian: '',
					xinjiuchengdu: '',
					wuzishuliang: '',
					wuzizhongliang: '',
					youxiaoqi: '',
					yanshoujieguo: '',
					yanshoujilu: '',
					yanshouren: '',
					yanshoushijian: '',
					zhanghao: '',
					xingming: '',
				},
				yanshoujieguoOptions: [],

				rules: {
					juanzengbianhao: [
					],
					wuzimingcheng: [
					],
					wuzizhonglei: [
					],
					wuzishuoming: [
					],
					wuzitupian: [
					],
					xinjiuchengdu: [
					],
					wuzishuliang: [
						{ validator: this.$validate.isIntNumer, trigger: 'blur' },
					],
					wuzizhongliang: [
						{ validator: this.$validate.isNumber, trigger: 'blur' },
					],
					youxiaoqi: [
					],
					yanshoujieguo: [
					],
					yanshoujilu: [
					],
					yanshouren: [
					],
					yanshoushijian: [
					],
					zhanghao: [
					],
					xingming: [
					],
				},
				centerType: false,
			};
		},
		computed: {
			sessionForm() {
				return JSON.parse(localStorage.getItem('sessionForm'))
			},



		},
		components: {
		},
		created() {
			if(this.$route.query.centerType){
				this.centerType = true
			}
			//this.bg();
			let type = this.$route.query.type ? this.$route.query.type : '';
			this.init(type);
			this.baseUrl = this.$config.baseUrl;
			this.ruleForm.yanshoushijian = this.getCurDateTime()
		},
		methods: {
			getMakeZero(s) {
				return s < 10 ? '0' + s : s;
			},
			// 下载
			download(file ){
				window.open(`${file}`)
			},
			// 初始化
			init(type) {
				this.type = type;
				if(type=='cross'){
					var obj = JSON.parse(localStorage.getItem('crossObj'));
					for (var o in obj){
						if(o=='juanzengbianhao'){
							this.ruleForm.juanzengbianhao = obj[o];
							this.ro.juanzengbianhao = true;
							continue;
						}
						if(o=='wuzimingcheng'){
							this.ruleForm.wuzimingcheng = obj[o];
							this.ro.wuzimingcheng = true;
							continue;
						}
						if(o=='wuzizhonglei'){
							this.ruleForm.wuzizhonglei = obj[o];
							this.ro.wuzizhonglei = true;
							continue;
						}
						if(o=='wuzishuoming'){
							this.ruleForm.wuzishuoming = obj[o];
							this.ro.wuzishuoming = true;
							continue;
						}
						if(o=='wuzitupian'){
							this.ruleForm.wuzitupian = obj[o]?obj[o].split(",")[0]:'';
							this.ro.wuzitupian = true;
							continue;
						}
						if(o=='xinjiuchengdu'){
							this.ruleForm.xinjiuchengdu = obj[o];
							this.ro.xinjiuchengdu = true;
							continue;
						}
						if(o=='wuzishuliang'){
							this.ruleForm.wuzishuliang = obj[o];
							this.ro.wuzishuliang = true;
							continue;
						}
						if(o=='wuzizhongliang'){
							this.ruleForm.wuzizhongliang = obj[o];
							this.ro.wuzizhongliang = true;
							continue;
						}
						if(o=='youxiaoqi'){
							this.ruleForm.youxiaoqi = obj[o];
							this.ro.youxiaoqi = true;
							continue;
						}
						if(o=='yanshoujieguo'){
							this.ruleForm.yanshoujieguo = obj[o];
							this.ro.yanshoujieguo = true;
							continue;
						}
						if(o=='yanshoujilu'){
							this.ruleForm.yanshoujilu = obj[o];
							this.ro.yanshoujilu = true;
							continue;
						}
						if(o=='yanshouren'){
							this.ruleForm.yanshouren = obj[o];
							this.ro.yanshouren = true;
							continue;
						}
						if(o=='yanshoushijian'){
							this.ruleForm.yanshoushijian = obj[o];
							this.ro.yanshoushijian = true;
							continue;
						}
						if(o=='zhanghao'){
							this.ruleForm.zhanghao = obj[o];
							this.ro.zhanghao = true;
							continue;
						}
						if(o=='xingming'){
							this.ruleForm.xingming = obj[o];
							this.ro.xingming = true;
							continue;
						}
					}
				}else if(type=='edit'){
					this.info()
				}
				// 获取用户信息
				this.$http.get(this.userTableName + '/session', {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						var json = res.data.data;
					}
				});
				this.yanshoujieguoOptions = "合格,不合格".split(',')

				if (localStorage.getItem('raffleType') && localStorage.getItem('raffleType') != null) {
					localStorage.removeItem('raffleType')
					setTimeout(() => {
						this.onSubmit(null)
					}, 300)
				}
			},

			// 多级联动参数
			// 多级联动参数
			async info() {
				await this.$http.get(`yanshoujilu/detail/${this.$route.query.id}`, {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						this.ruleForm = res.data.data;
					}
				});
			},
			// 提交
			async onSubmit(subMitType=null) {
				await this.$refs["ruleForm"].validate(async valid => {
					if(valid) {
						if(!this.ruleForm.id) {
							delete this.ruleForm.userid
						}
						if(this.type=='cross' && !this.ruleForm.id) {
							let obj = {}
							try {
								obj = JSON.parse(localStorage.getItem('crossObj')) || {}
							} catch (e) {
								obj = {}
							}
							await this.$http.post('yanshoujilu/accept',{
								juanzengwuziId: obj.id,
								yanshoujilu: this.ruleForm
							}).then(async res => {
								if (res.data.code == 0) {
									await this.$message({
										message: '操作成功',
										type: 'success',
										duration: 1500,
										onClose: () => {
											this.$router.go(-1);
										}
									});
								} else {
									this.$message({
										message: res.data.msg,
										type: 'error',
										duration: 1500
									});
								}
							},err=>{
								this.$message({
									message: '操作失败',
									type: 'error',
									duration: 1500
								});
							})
							return false
						}
						await this.$http.post(`yanshoujilu/${this.ruleForm.id?'update':this.centerType?'save':'add'}`, this.ruleForm).then(async res => {
							if (res.data.code == 0) {
								await this.$message({
									message: '操作成功',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.$router.go(-1);
										
									}
								});
							} else {
								this.$message({
									message: res.data.msg,
									type: 'error',
									duration: 1500
								});
							}
						});
					}
				});
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 返回
			back() {
				this.$router.go(-1);
			},
			wuzitupianUploadChange(fileUrls) {
				this.ruleForm.wuzitupian = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
			},
		}
	};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.add-update-preview {
		padding: 20px 7% 40px;
		margin: 10px auto;
		background: none;
		width: 100%;
		position: relative;
		.add-update-form {
			border: 2px dashed #eee;
			border-radius: 0;
			padding: 40px 20% 20px 10%;
			background: #fff;
			width: 100%;
			position: relative;
			.add-item.el-form-item {
				padding: 0;
				margin: 0 0 24px;
				background: none;
				/deep/ .el-form-item__label {
					padding: 0 10px 0 0;
					color: #333;
					white-space: nowrap;
					font-weight: 500;
					width: 200px;
					font-size: 16px;
					line-height: 40px;
					text-align: right;
				}
				/deep/ .el-form-item__content {
					margin-left: 200px;
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
					color: #666;
					width: 100%;
					font-size: 16px;
					border-width: 0 0 1px;
					height: 40px;
				}
				.el-input /deep/ .el-input__inner[readonly="readonly"] {
					border: 0;
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
				.el-input-number /deep/ .el-input__inner {
					text-align: left;
					border: 0px dashed #ddd;
					border-radius: 0px;
					padding: 0 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					border-width: 0 0 1px;
					height: 40px;
				}
				.el-input-number /deep/ .is-disabled .el-input__inner {
					text-align: left;
					border: 0;
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
					border: 0px dashed #ddd;
					border-radius: 0px;
					padding: 0 10px;
					box-shadow: none;
					outline: none;
					color: rgba(64, 158, 255, 1);
					width: 100%;
					font-size: 14px;
					border-width: 0 0 1px;
					height: 40px;
				}
				.el-select /deep/ .is-disabled .el-input__inner {
					border: 0;
					cursor: not-allowed;
					border-radius: 4px;
					padding: 0 10px;
					box-shadow: none;
					outline: none;
					color: rgba(85, 85, 127, 1.0);
					background: #eee;
					width: 100%;
					font-size: 14px;
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
					color: #666;
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
					color: #666;
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
					font-size: 16px;
				}
				.el-textarea /deep/ .el-textarea__inner {
					border: 1px dashed #ddd;
					border-radius: 0px;
					padding: 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: auto;
				}
				.el-textarea /deep/ .el-textarea__inner[readonly="readonly"] {
					border: 0;
					cursor: not-allowed;
					border-radius: 4px;
					padding: 12px;
					box-shadow: none;
					outline: none;
					color: rgba(85, 85, 127, 1.0);
					width: 100%;
					font-size: 16px;
					height: auto;
				}
				/deep/ .el-input__inner::placeholder {
					color: #123;
					font-size: 16px;
				}
				/deep/ textarea::placeholder {
					color: #123;
					font-size: 16px;
				}
				.editor {
					background-color: #fff;
					border-radius: 0;
					padding: 0;
					box-shadow: none;
					margin: 0;
					width: 100%;
					border-color: #ccc;
					border-width: 0;
					border-style: solid;
					height: auto;
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
					color: #999;
				}
				.upload-img {
					object-fit: cover;
					width: 120px;
					height: 120px;
				}
				.viewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 20px;
					margin: 0;
					color: #fff;
					display: inline-block;
					font-size: 14px;
					line-height: 34px;
					border-radius: 4px;
					outline: none;
					background: #6aac5a;
					width: auto;
					height: 34px;
				}
				.viewBtn:hover {
					opacity: 0.7;
				}
				.unviewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 20px;
					margin: 0;
					color: #fff;
					display: inline-block;
					font-size: 14px;
					line-height: 34px;
					border-radius: 4px;
					outline: none;
					background: #999;
					width: auto;
					height: 34px;
				}
				.unviewBtn:hover {
					opacity: 0.8;
				}
			}
			.add-btn-item {
				padding: 0;
				margin: 20px auto;
				width: 100%;
				text-align: center;
				.submitBtn {
					border: 0;
					cursor: pointer;
					border-radius: 0px;
					padding: 0 25px;
					margin: 0 20px 0 0;
					outline: none;
					background: #6aac5a;
					display: inline-block;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
					.icon {
						color: rgba(255, 255, 255, 1);
					}
					.text {
						color: rgba(255, 255, 255, 1);
						font-size: 16px;
					}
				}
				.submitBtn:hover {
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
					padding: 0 15px;
					margin: 0 20px 0 0;
					display: inline-block;
					font-size: 16px;
					line-height: 40px;
					border-radius: 0px;
					outline: none;
					background: #cb515120;
					width: auto;
					min-width: 100px;
					height: 40px;
					.icon {
						color: #cb5151;
						font-size: 18px;
					}
					.text {
						color: #cb5151;
						font-size: 16px;
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
		}
	}
	.el-date-editor.el-input {
		width: auto;
	}
</style>
