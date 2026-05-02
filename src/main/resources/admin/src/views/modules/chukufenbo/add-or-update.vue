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
				<el-form-item class="select" v-if="type!='info'" label="申领编号" prop="shenlingbianhao" >
					<el-select :disabled="ro.shenlingbianhao" @change="shenlingbianhaoChange" v-model="ruleForm.shenlingbianhao" placeholder="请选择申领编号" filterable>
						<el-option
							v-for="(item,index) in shenlingbianhaoOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.shenlingbianhao" label="申领编号" prop="shenlingbianhao" >
					<el-input v-model="ruleForm.shenlingbianhao" placeholder="申领编号" readonly></el-input>
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
				<el-form-item class="input" v-if="type!='info'"  label="机构账号" prop="jigouzhanghao" >
					<el-input v-model="ruleForm.jigouzhanghao" placeholder="机构账号" clearable  :readonly="ro.jigouzhanghao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="机构账号" prop="jigouzhanghao" >
					<el-input v-model="ruleForm.jigouzhanghao" placeholder="机构账号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="机构名称" prop="jigoumingcheng" >
					<el-input v-model="ruleForm.jigoumingcheng" placeholder="机构名称" clearable  :readonly="ro.jigoumingcheng"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="机构名称" prop="jigoumingcheng" >
					<el-input v-model="ruleForm.jigoumingcheng" placeholder="机构名称" readonly></el-input>
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
				<el-form-item class="input" v-if="type!='info'"  label="出库数量" prop="wuzishuliang" >
					<el-input v-model.number="ruleForm.wuzishuliang" placeholder="出库数量" clearable  :readonly="ro.wuzishuliang"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="出库数量" prop="wuzishuliang" >
					<el-input v-model="ruleForm.wuzishuliang" placeholder="出库数量" readonly></el-input>
				</el-form-item>
				<el-form-item class="fileupload" v-if="type!='info'&& !ro.chukudan" label="出库单" prop="chukudan" >
					<file-upload
						tip="点击上传出库单"
						action="file/upload"
						:limit="1"
						:type="2"
						:multiple="true"
						:disabled="ro.chukudan"
						:fileUrls="ruleForm.chukudan?ruleForm.chukudan:''"
						@change="chukudanUploadChange"
					></file-upload>
				</el-form-item>  
				<el-form-item v-else-if="ruleForm.chukudan" label="出库单" prop="chukudan" >
					<el-button class="downBtn" type="text" size="small" @click="download($base.url+ruleForm.chukudan)">
						<span class="icon iconfont icon-xiazai7"></span>
						下载
					</el-button>
				</el-form-item>
				<el-form-item v-else-if="!ruleForm.chukudan" label="出库单" prop="chukudan" >
					<el-button class="unBtn" type="text" size="small">
						<span class="icon iconfont icon-jubao"></span>
						暂无
					</el-button>
				</el-form-item>
				<el-form-item class="date" v-if="type!='info'" label="出库时间" prop="chukushijian" >
					<el-date-picker
						value-format="yyyy-MM-dd HH:mm:ss"
						v-model="ruleForm.chukushijian" 
						type="datetime"
						:readonly="ro.chukushijian"
						placeholder="出库时间"
					></el-date-picker>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.chukushijian" label="出库时间" prop="chukushijian" >
					<el-input v-model="ruleForm.chukushijian" placeholder="出库时间" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="物流状态" prop="wuliuzhuangtai" >
					<el-select :disabled="ro.wuliuzhuangtai" v-model="ruleForm.wuliuzhuangtai" placeholder="请选择物流状态"  filterable>
						<el-option
							v-for="(item,index) in wuliuzhuangtaiOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="物流状态" prop="wuliuzhuangtai" >
					<el-input v-model="ruleForm.wuliuzhuangtai"
						placeholder="物流状态" readonly></el-input>
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
		isIntNumer,
	} from "@/utils/validate";
	export default {
		data() {
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
					shenlingbianhao : false,
					wuzimingcheng : false,
					wuzizhonglei : false,
					jigouzhanghao : false,
					jigoumingcheng : false,
					wuzitupian : false,
					wuzishuliang : false,
					chukudan : false,
					chukushijian : false,
					wuliuzhuangtai : false,
				},
			
				ruleForm: {
					shenlingbianhao: '',
					wuzimingcheng: '',
					wuzizhonglei: '',
					jigouzhanghao: '',
					jigoumingcheng: '',
					wuzitupian: '',
					wuzishuliang: '',
					chukudan: '',
					chukushijian: '',
					wuliuzhuangtai: '配送中',
				},
				shenlingbianhaoOptions: [],
				wuliuzhuangtaiOptions: [],

				rules: {
					shenlingbianhao: [
					],
					wuzimingcheng: [
					],
					wuzizhonglei: [
					],
					jigouzhanghao: [
					],
					jigoumingcheng: [
					],
					wuzitupian: [
					],
					wuzishuliang: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					chukudan: [
					],
					chukushijian: [
					],
					wuliuzhuangtai: [
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
			this.ruleForm.chukushijian = this.getCurDateTime()
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
						if(o=='shenlingbianhao'){
							this.ruleForm.shenlingbianhao = obj[o];
							this.ro.shenlingbianhao = true;
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
						if(o=='jigouzhanghao'){
							this.ruleForm.jigouzhanghao = obj[o];
							this.ro.jigouzhanghao = true;
							continue;
						}
						if(o=='jigoumingcheng'){
							this.ruleForm.jigoumingcheng = obj[o];
							this.ro.jigoumingcheng = true;
							continue;
						}
						if(o=='wuzitupian'){
							this.ruleForm.wuzitupian = obj[o];
							this.ro.wuzitupian = true;
							continue;
						}
						if(o=='shenlingshuliang'){
							this.ruleForm.wuzishuliang = obj[o];
							this.ro.wuzishuliang = true;
							continue;
						}
						if(o=='wuzishuliang'){
							this.ruleForm.wuzishuliang = obj[o];
							this.ro.wuzishuliang = true;
							continue;
						}
						if(o=='chukudan'){
							this.ruleForm.chukudan = obj[o];
							this.ro.chukudan = true;
							continue;
						}
						if(o=='chukushijian'){
							this.ruleForm.chukushijian = obj[o];
							this.ro.chukushijian = true;
							continue;
						}
						if(o=='wuliuzhuangtai'){
							this.ruleForm.wuliuzhuangtai = obj[o];
							this.ro.wuliuzhuangtai = true;
							continue;
						}
					}
					// this.ruleForm.wuzishuliang = 0
					// this.ro.wuzishuliang = false
					this.ruleForm.wuliuzhuangtai = '配送中'; 				}

				// 获取用户信息
				this.$http({
					url: `${this.sessionTable}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						var json = data.data;
						if(((json.jigouzhanghao!=''&&json.jigouzhanghao) || json.jigouzhanghao==0) && this.sessionTable!="users"){
							this.ruleForm.jigouzhanghao = json.jigouzhanghao
							this.ro.jigouzhanghao = true;
						}
						if(((json.jigoumingcheng!=''&&json.jigoumingcheng) || json.jigoumingcheng==0) && this.sessionTable!="users"){
							this.ruleForm.jigoumingcheng = json.jigoumingcheng
							this.ro.jigoumingcheng = true;
						}
						if(this.sessionTable!="users") {
							this.ro.wuliuzhuangtai = true;
						}
					} else {
						this.$message.error(data.msg);
					}
				});
				this.$http({
					url: `option/wuzishenling/shenlingbianhao`,
					method: "get",
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.shenlingbianhaoOptions = data.data;
					} else {
						this.$message.error(data.msg);
					}
				});
				this.wuliuzhuangtaiOptions = "已签收,配送中".split(',')
			
			},
			// 下二随
			shenlingbianhaoChange () {
				this.$http({
					url: `follow/wuzishenling/shenlingbianhao?columnValue=`+ this.ruleForm.shenlingbianhao,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						if(data.data.wuzimingcheng){
							this.ruleForm.wuzimingcheng = data.data.wuzimingcheng
						}
						if(data.data.wuzizhonglei){
							this.ruleForm.wuzizhonglei = data.data.wuzizhonglei
						}
						if(data.data.jigouzhanghao){
							this.ruleForm.jigouzhanghao = data.data.jigouzhanghao
						}
						if(data.data.jigoumingcheng){
							this.ruleForm.jigoumingcheng = data.data.jigoumingcheng
						}
						if(data.data.wuzitupian){
							this.ruleForm.wuzitupian = data.data.wuzitupian
						}
						// 物资申领表中字段为 shenlingshuliang，出库分拨表中字段为 wuzishuliang。
						if(data.data.shenlingshuliang !== undefined && data.data.shenlingshuliang !== null && data.data.shenlingshuliang !== ''){
							this.ruleForm.wuzishuliang = data.data.shenlingshuliang
						}else if(data.data.wuzishuliang !== undefined && data.data.wuzishuliang !== null && data.data.wuzishuliang !== ''){
							this.ruleForm.wuzishuliang = data.data.wuzishuliang
						}
					} else {
						this.$message.error(data.msg);
					}
				});
			},
			// 多级联动参数

			async info(id) {
				await this.$http({
					url: `chukufenbo/info/${id}`,
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
							if(this.ruleForm.chukudan!=null) {
								this.ruleForm.chukudan = this.ruleForm.chukudan.replace(new RegExp(this.$base.url,"g"),"");
							}
							var objcross = this.$storage.getObj('crossObj');
							var table = this.$storage.getObj('crossTable');
							if(objcross!=null) {
								if(!this.ruleForm.wuzishuliang&&this.ruleForm.wuzishuliang!=0){
									this.$message.error("出库数量不能为空");
									return
								}
								if(objcross.wuzishuliang<this.ruleForm.wuzishuliang){
									this.$message.error("出库数量不足");
									return
								}
							}
							if(!this.ruleForm.id) {
								delete this.ruleForm.userid
							}
							if(this.type=='cross'){
								var statusColumnName = this.$storage.get('statusColumnName');
								var statusColumnValue = this.$storage.get('statusColumnValue');
								if(statusColumnName!='') {
									var obj = this.$storage.getObj('crossObj');
									if(statusColumnName && !statusColumnName.startsWith("[")) {
										var notUpdateColumn = 'chukuzhuangtai'.split(',')
										if(changeInList(statusColumnName,notUpdateColumn)) {
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
										function changeInList(name,arr){
											for(let x in arr) {
												if(arr[x] == name) {
													return false
												}
											}
											return true
										}
									}
								}
								// 跨表计算
								var objcross = this.$storage.getObj('crossObj');
								var table = this.$storage.get('crossTable');
								objcross.wuzishuliang = Number((parseFloat(objcross.wuzishuliang) - parseFloat(this.ruleForm.wuzishuliang)).toFixed(2))
								await this.$http({
									url: `${table}/update`,
									method: "post",
									data: objcross
								}).then(({ data }) => {});
							}
							let changeType = true
							let errMsg = ''
							if(this.type=='cross') {
								await this.$http.post('updateColumn/wuzishenling/1',{
									csuUpdateColumn: 'chukuzhuangtai',
									csuUpdateColumnValue: '已出库',
									csuConditionColumn: 'shenlingbianhao',
									csuConditionColumnValue: this.ruleForm.shenlingbianhao
								}).then(rs=>{
									if(rs.data.code!=0) {
										changeType = false
										errMsg = '已出库'
									}
								},err=>{
									changeType = false
									errMsg = '已出库'
								})
								if(!changeType) {
									this.$message.error(errMsg);
									return false
								}
							}
							await this.$http({
								url: `chukufenbo/${!this.ruleForm.id ? "save" : "update"}`,
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
											this.parent.chukufenboCrossAddOrUpdateFlag = false;
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
				this.parent.chukufenboCrossAddOrUpdateFlag = false;
			},
			wuzitupianUploadChange(fileUrls) {
				this.ruleForm.wuzitupian = fileUrls;
			},
			chukudanUploadChange(fileUrls) {
				this.ruleForm.chukudan = fileUrls;
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
