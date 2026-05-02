






























<template>
	<div class="addEdit-block">
		<el-form
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="180px"
		>
			<template >
				<el-form-item class="input" v-if="type!='info'"  label="捐赠编号" prop="juanzengbianhao" >
					<el-input v-model="ruleForm.juanzengbianhao" placeholder="捐赠编号" clearable  :readonly="ro.juanzengbianhao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="捐赠编号" prop="juanzengbianhao" >
					<el-input v-model="ruleForm.juanzengbianhao" placeholder="捐赠编号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="物资名称" prop="wuzimingcheng" >
					<el-input v-model="ruleForm.wuzimingcheng" placeholder="物资名称" clearable  :readonly="ro.wuzimingcheng"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="物资名称" prop="wuzimingcheng" >
					<el-input v-model="ruleForm.wuzimingcheng" placeholder="物资名称" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="物资种类" prop="wuzizhonglei" >
					<el-input v-model="ruleForm.wuzizhonglei" placeholder="物资种类" clearable  :readonly="ro.wuzizhonglei"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="物资种类" prop="wuzizhonglei" >
					<el-input v-model="ruleForm.wuzizhonglei" placeholder="物资种类" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="物资说明" prop="wuzishuoming" >
					<el-input v-model="ruleForm.wuzishuoming" placeholder="物资说明" clearable  :readonly="ro.wuzishuoming"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="物资说明" prop="wuzishuoming" >
					<el-input v-model="ruleForm.wuzishuoming" placeholder="物资说明" readonly></el-input>
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.wuzitupian" label="物资图片" prop="wuzitupian" >
					<file-upload
						tip="点击上传物资图片"
						action="file/upload"
						:limit="3"
						:disabled="ro.wuzitupian"
						:multiple="true"
						:fileUrls="ruleForm.wuzitupian?ruleForm.wuzitupian:''"
						@change="wuzitupianUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.wuzitupian" label="物资图片" prop="wuzitupian" >
					<img v-if="ruleForm.wuzitupian.substring(0,4)=='http'&&ruleForm.wuzitupian.split(',w').length>1" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.wuzitupian" width="100" height="100" @click="imgPreView(ruleForm.tupian)">
					<img v-else-if="ruleForm.wuzitupian.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.wuzitupian.split(',')[0]" width="100" height="100" @click="imgPreView(ruleForm.tupian.split(',')[0])">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.wuzitupian.split(',')" :src="$base.url+item" width="100" height="100" @click="imgPreView($base.url+item)">
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="新旧程度" prop="xinjiuchengdu" >
					<el-input v-model="ruleForm.xinjiuchengdu" placeholder="新旧程度" clearable  :readonly="ro.xinjiuchengdu"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="新旧程度" prop="xinjiuchengdu" >
					<el-input v-model="ruleForm.xinjiuchengdu" placeholder="新旧程度" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="物资数量" prop="wuzishuliang" >
					<el-input v-model.number="ruleForm.wuzishuliang" placeholder="物资数量" clearable  :readonly="ro.wuzishuliang"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="物资数量" prop="wuzishuliang" >
					<el-input v-model="ruleForm.wuzishuliang" placeholder="物资数量" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="物资重量" prop="wuzizhongliang" >
					<el-input-number v-model="ruleForm.wuzizhongliang" placeholder="物资重量" :disabled="ro.wuzizhongliang"></el-input-number>
				</el-form-item>
				<el-form-item v-else class="input" label="物资重量" prop="wuzizhongliang" >
					<el-input v-model="ruleForm.wuzizhongliang" placeholder="物资重量" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="有效期" prop="youxiaoqi" >
					<el-input v-model="ruleForm.youxiaoqi" placeholder="有效期" clearable  :readonly="ro.youxiaoqi"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="有效期" prop="youxiaoqi" >
					<el-input v-model="ruleForm.youxiaoqi" placeholder="有效期" readonly></el-input>
				</el-form-item>
				<el-form-item class="fileupload" v-if="type!='info'&& !ro.juanzengzhengshu" label="捐赠证书" prop="juanzengzhengshu" >
					<file-upload
						tip="点击上传捐赠证书"
						action="file/upload"
						:limit="1"
						:type="2"
						:multiple="true"
						:disabled="ro.juanzengzhengshu"
						:fileUrls="ruleForm.juanzengzhengshu?ruleForm.juanzengzhengshu:''"
						@change="juanzengzhengshuUploadChange"
					></file-upload>
				</el-form-item>  
				<el-form-item v-else-if="ruleForm.juanzengzhengshu" label="捐赠证书" prop="juanzengzhengshu" >
					<el-button class="downBtn" type="text" size="small" @click="download($base.url+ruleForm.juanzengzhengshu)">
						<span class="icon iconfont icon-xiazai7"></span>
						下载
					</el-button>
				</el-form-item>
				<el-form-item v-else-if="!ruleForm.juanzengzhengshu" label="捐赠证书" prop="juanzengzhengshu" >
					<el-button class="unBtn" type="text" size="small">
						<span class="icon iconfont icon-jubao"></span>
						暂无
					</el-button>
				</el-form-item>
				<el-form-item class="date" v-if="type!='info'" label="颁发时间" prop="banfashijian" >
					<el-date-picker
						value-format="yyyy-MM-dd HH:mm:ss"
						v-model="ruleForm.banfashijian" 
						type="datetime"
						:readonly="ro.banfashijian"
						placeholder="颁发时间"
					></el-date-picker>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.banfashijian" label="颁发时间" prop="banfashijian" >
					<el-input v-model="ruleForm.banfashijian" placeholder="颁发时间" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="账号" prop="zhanghao" >
					<el-input v-model="ruleForm.zhanghao" placeholder="账号" clearable  :readonly="ro.zhanghao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="账号" prop="zhanghao" >
					<el-input v-model="ruleForm.zhanghao" placeholder="账号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="姓名" prop="xingming" >
					<el-input v-model="ruleForm.xingming" placeholder="姓名" clearable  :readonly="ro.xingming"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="姓名" prop="xingming" >
					<el-input v-model="ruleForm.xingming" placeholder="姓名" readonly></el-input>
				</el-form-item>
			</template>
			<el-form-item class="btn">
				<el-button class="btn3"  v-if="type!='info'" type="success" @click="onSubmit">
					<span class="icon iconfont icon-queren15"></span>
					保存信息
				</el-button>
				<el-button class="btn4" v-if="type!='info'" type="success" @click="back()">
					<span class="icon iconfont icon-guanbi2"></span>
					取消
				</el-button>
				<el-button class="btn5" v-if="type=='info'" type="success" @click="back()">
					<span class="icon iconfont icon-fanhui13"></span>
					返回
				</el-button>
			</el-form-item>
		</el-form>
    

	</div>
</template>
<script>
	import { 
		isNumber,
		isIntNumer,
	} from "@/utils/validate";
	export default {
		data() {
			var validateNumber = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isNumber(value)) {
					callback(new Error("请输入数字"));
				} else {
					callback();
				}
			};
			var validateIntNumber = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isIntNumer(value)) {
					callback(new Error("请输入整数"));
				} else {
					callback();
				}
			};
			return {
				id: '',
				type: '',
			
			
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
					juanzengzhengshu : false,
					banfashijian : false,
					zhanghao : false,
					xingming : false,
					crossuserid : false,
					crossrefid : false,
				},
			
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
					juanzengzhengshu: '',
					banfashijian: '',
					zhanghao: '',
					xingming: '',
					crossuserid: '',
					crossrefid: '',
				},

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
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					wuzizhongliang: [
						{ validator: validateNumber, trigger: 'blur' },
					],
					youxiaoqi: [
					],
					juanzengzhengshu: [
					],
					banfashijian: [
					],
					zhanghao: [
					],
					xingming: [
					],
					crossuserid: [
					],
					crossrefid: [
					],
				},
			};
		},
		props: ["parent"],
		computed: {
			sessionForm() {
				return JSON.parse(this.$storage.getObj('userForm'))
			},
			sessionTable() {
				return this.$storage.get('sessionTable')
			},



		},
		components: {
		},
		created() {
			this.ruleForm.banfashijian = this.getCurDateTime()
		},
		methods: {
			imgPreView(url){
				this.$parent.imgPreView(url)
			},
			// 下载
			download(file){
				window.open(`${file}`)
			},
			// 初始化
			init(id,type ) {
				if (id) {
					this.id = id;
					this.type = type;
				}
				if(this.type=='info'||this.type=='else'||this.type=='msg'){
					this.info(id);
				}else if(this.type=='logistics'){
					for(let x in this.ro) {
						this.ro[x] = true
					}
					this.logistics=false;
					this.info(id);
				}else if(this.type=='cross'){
					var obj = this.$storage.getObj('crossObj');
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
							this.ruleForm.wuzitupian = obj[o];
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
						if(o=='juanzengzhengshu'){
							this.ruleForm.juanzengzhengshu = obj[o];
							this.ro.juanzengzhengshu = true;
							continue;
						}
						if(o=='banfashijian'){
							this.ruleForm.banfashijian = obj[o];
							this.ro.banfashijian = true;
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
						if(o=='crossuserid'){
							this.ruleForm.crossuserid = obj[o];
							this.ro.crossuserid = true;
							continue;
						}
						if(o=='crossrefid'){
							this.ruleForm.crossrefid = obj[o];
							this.ro.crossrefid = true;
							continue;
						}
					}
				}

				// 获取用户信息
				this.$http({
					url: `${this.sessionTable}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						var json = data.data;
						if(((json.zhanghao!=''&&json.zhanghao) || json.zhanghao==0) && this.sessionTable!="users"){
							this.ruleForm.zhanghao = json.zhanghao
							this.ro.zhanghao = true;
						}
						if(((json.xingming!=''&&json.xingming) || json.xingming==0) && this.sessionTable!="users"){
							this.ruleForm.xingming = json.xingming
							this.ro.xingming = true;
						}
					} else {
						this.$message.error(data.msg);
					}
				});
			
			},
			// 多级联动参数

			async info(id) {
				await this.$http({
					url: `juanzengzhengshu/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						//解决前台上传图片后台不显示的问题
						let reg=new RegExp('../../../upload','g')//g代表全部
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			// 提交
			async onSubmit() {
					await this.$refs["ruleForm"].validate(async valid => {
						if (valid) {
							if(this.ruleForm.wuzitupian!=null) {
								this.ruleForm.wuzitupian = this.ruleForm.wuzitupian.replace(new RegExp(this.$base.url,"g"),"");
							}
							if(this.ruleForm.juanzengzhengshu!=null) {
								this.ruleForm.juanzengzhengshu = this.ruleForm.juanzengzhengshu.replace(new RegExp(this.$base.url,"g"),"");
							}
							var objcross = this.$storage.getObj('crossObj');
							//更新跨表属性
							var crossuserid;
							var crossrefid;
							var crossoptnum;
							var finishNum = 0;
							if(!this.ruleForm.id) {
								delete this.ruleForm.userid
							}
							if(this.type=='cross'){
								var statusColumnName = this.$storage.get('statusColumnName');
								var statusColumnValue = this.$storage.get('statusColumnValue');
								if(statusColumnName!='') {
									var obj = this.$storage.getObj('crossObj');
									if(statusColumnName && !statusColumnName.startsWith("[")) {
										for (var o in obj){
											if(o==statusColumnName){
												obj[o] = statusColumnValue;
											}
										}
										var table = this.$storage.get('crossTable');
										await this.$http({
											url: `${table}/update`,
											method: "post",
											data: obj
										}).then(({ data }) => {});
									}
									else {
										crossuserid=this.$storage.get('userid');
										crossrefid=obj['id'];
										crossoptnum=this.$storage.get('statusColumnName');
										crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
									}
								}
								if(crossrefid && crossuserid) {
									this.ruleForm.crossuserid = crossuserid;
									this.ruleForm.crossrefid = crossrefid;
									let params = { 
										page: 1, 
										limit: 10, 
										crossuserid:this.ruleForm.crossuserid,
										crossrefid:this.ruleForm.crossrefid,
									} 
									await this.$http({ 
										url: "juanzengzhengshu/page", 
										method: "get", 
										params: params 
									}).then(({ 
										data 
									}) => { 
										if (data && data.code === 0) {
											finishNum = data.data.total
										}
									})
								}
								if(finishNum>=crossoptnum) {
									this.$message.error(this.$storage.get('tips'));
									return false;
								}
							}
							await this.$http({
								url: `juanzengzhengshu/${!this.ruleForm.id ? "save" : "update"}`,
								method: "post",
								data: this.ruleForm
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									this.$message({
										message: "操作成功",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.parent.showFlag = true;
											this.parent.addOrUpdateFlag = false;
											this.parent.juanzengzhengshuCrossAddOrUpdateFlag = false;
											this.parent.search();
										}
									});
								} else {
									this.$message.error(data.msg);
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
				this.parent.showFlag = true;
				this.parent.addOrUpdateFlag = false;
				this.parent.juanzengzhengshuCrossAddOrUpdateFlag = false;
			},
			wuzitupianUploadChange(fileUrls) {
				this.ruleForm.wuzitupian = fileUrls;
			},
			juanzengzhengshuUploadChange(fileUrls) {
				this.ruleForm.juanzengzhengshu = fileUrls;
			},
		}
	};
</script>
<style lang="scss" scoped>
	.addEdit-block {
		padding: 30px;
	}
	.add-update-preview {
		border-radius: 10px;
		padding: 50px 20% 50px 10%;
		box-shadow: none;
		background: #fff8f0;
		display: flex;
		border-color: #ff9164;
		border-width: 4px 0 0 0;
		border-style: solid;
		flex-wrap: wrap;
	}
	.amap-wrapper {
		width: 100%;
		height: 500px;
	}
	
	.search-box {
		position: absolute;
	}
	
	.el-date-editor.el-input {
		width: auto;
	}
	.add-update-preview /deep/ .el-form-item {
		margin: 0 0 20px 0;
		width: 100%;
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
	.add-update-preview /deep/ .el-form-item.editorBox {
		margin: 0 0 20px 0;
		width: 100%;
	}
	.add-update-preview .el-form-item.editorBox /deep/ .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		font-weight: 500;
		width: 180px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.editorBox /deep/ .el-form-item__content {
		margin: 0;
		display: flex;
		align-items: center;
	}
	.add-update-preview /deep/.el-form-item.editorBox .editor {
		box-shadow: none;
		max-width: 100% !important;
		width: 100%;
		height: auto;
	}
	.add-update-preview /deep/.el-form-item.editorBox .editor .ql-toolbar {
		border: 1px solid #e7e8fc;
		background: #fff;
		border-width: 1px 1px 0;
	}
	.add-update-preview /deep/.el-form-item.editorBox .editor .ql-container {
		border: 1px solid #e7e8fc;
		background: #fff;
		min-height: 200px;
	}
	.add-update-preview /deep/.el-form-item.editorBox .editor .ql-container .ql-blank::before {
		color: #000;
	}
	
	.add-update-preview /deep/ .el-form-item.textBox {
		margin: 0 0 20px 0;
	}
	.add-update-preview .el-form-item.textBox /deep/ .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.textBox /deep/ .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	.add-update-preview /deep/.el-form-item.textBox span.text {
		padding: 0;
		color: #666;
		font-weight: 500;
		display: inline-block;
		font-size: 16px;
		line-height: 40px;
	}
	
	.add-update-preview .el-input {
		width: 100%;
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
	.add-update-preview .el-input /deep/ .el-input__inner[readonly="readonly"] {
		border: 0px solid #ddd;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: none;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number {
		text-align: left;
		width: 100%;
	}
	.add-update-preview .el-input-number /deep/ .el-input__inner {
		text-align: left;
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
	.add-update-preview .el-input-number /deep/ .is-disabled .el-input__inner {
		text-align: left;
		border: 0px solid #ddd;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: none;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number /deep/ .el-input-number__decrease {
		display: none;
	}
	.add-update-preview .el-input-number /deep/ .el-input-number__increase {
		display: none;
	}
	.add-update-preview .el-select {
		width: 100%;
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
	.add-update-preview .el-select /deep/ .is-disabled .el-input__inner {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 10px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: none;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-date-editor {
		width: 100%;
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
	.add-update-preview .el-date-editor /deep/ .el-input__inner[readonly="readonly"] {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 10px 0 30px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: none;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .viewBtn {
		border: 1px solid #eee;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #ff7f00;
		background: #fff;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			display: none;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .viewBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .downBtn {
		border: 1px solid #eee;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #ff7f00;
		background: #fff;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			display: none;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .downBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .unBtn {
		border: 1px solid #ddd;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #999;
		background: #f6f6f6;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			display: none;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .unBtn:hover {
		opacity: 0.8;
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
	
	.add-update-preview /deep/ .upload .upload-img {
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
	.add-update-preview /deep/ .el-upload__tip {
		color: #838fa1;
	}
	.add-update-preview /deep/ .el-form-item.fileupload {
		margin: 0 0 20px 0;
	}
	.add-update-preview .el-form-item.fileupload /deep/ .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.fileupload /deep/ .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	.add-update-preview .el-form-item.fileupload /deep/ .el-upload-dragger {
		border: 1px solid #eee;
		cursor: pointer;
		background-color: #fff;
		border-radius: 6px;
		padding: 5px 20px 20px;
		overflow: hidden;
		width: auto;
		box-sizing: border-box;
		text-align: center;
		height: auto;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger /deep/ .el-icon-upload {
		margin: 0;
		color: #ff7f00;
		font-size: 60px;
		line-height: 1;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger /deep/ .el-upload__text {
		color: #606266;
		textalign: center;
		fontsize: 14px;
		line-height: 1;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger /deep/ .el-upload__text em {
		fontstyle: normal;
		color: #ff7f00;
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
	.add-update-preview .el-textarea /deep/ .el-textarea__inner[readonly="readonly"] {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 12px;
		box-shadow: none;
		outline: none;
		color: #666;
		background: none;
		width: 100%;
		font-size: 16px;
		height: auto;
	}
	.add-update-preview /deep/ .el-form-item.btn {
		padding: 0;
		margin: 10px auto 0;
		display: flex;
		width: 100%;
		justify-content: center;
		.btn1 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 0 20px 0 0;
			outline: none;
			color: #fff;
			background: rgba(255, 128, 0, 1);
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn1:hover {
			opacity: 0.8;
		}
		.btn2 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 0 20px 0 0;
			outline: none;
			color: #fff;
			background: #65bda0;
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
		.btn2:hover {
			opacity: 0.8;
		}
		.btn3 {
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
		.btn3:hover {
			opacity: 0.8;
		}
		.btn4 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 0 20px 0 0;
			outline: none;
			color: #7a6a5a;
			background: #e9cfbb;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn4:hover {
			opacity: 0.8;
		}
		.btn5 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 0 20px 0 0;
			outline: none;
			color: #fff;
			background: #f5bda0;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				display: none;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn5:hover {
			opacity: 0.8;
		}
	}
	.add-update-preview .el-form-item.btn /deep/ .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.btn /deep/ .el-form-item__content {
	}
</style>
