package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import com.utils.*;
import com.service.*;
import com.entity.*;
import com.entity.view.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.JuanzengwuziEntity;
import com.entity.view.JuanzengwuziView;

import com.service.JuanzengwuziService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 捐赠物资 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/juanzengwuzi")
public class JuanzengwuziController {
    @Autowired
    private JuanzengwuziService juanzengwuziService;










    /**
     * 功能：分页查询捐赠物资数据。
     * 使用端：管理端捐赠物资管理列表页。
     * 前端触发：admin/src/views/modules/juanzengwuzi/list.vue 通过 $http.get('juanzengwuzi/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JuanzengwuziEntity juanzengwuzi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("juanzengren")) {
			juanzengwuzi.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();


        //查询结果
		PageUtils page = juanzengwuziService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengwuzi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询捐赠物资前台列表数据。
     * 使用端：前台捐赠物资列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/juanzengwuzi/list.vue 通过 $http.get('juanzengwuzi/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JuanzengwuziEntity juanzengwuzi,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
                @RequestParam(required = false) Double wuzizhongliangstart,
                @RequestParam(required = false) Double wuzizhongliangend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date youxiaoqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date youxiaoqiend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date juanzengshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date juanzengshijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);
        if(wuzizhongliangstart!=null) ew.ge("wuzizhongliang", wuzizhongliangstart);
        if(wuzizhongliangend!=null) ew.le("wuzizhongliang", wuzizhongliangend);
        if(youxiaoqistart!=null) ew.ge("youxiaoqi", youxiaoqistart);
        if(youxiaoqiend!=null) ew.le("youxiaoqi", youxiaoqiend);
        if(juanzengshijianstart!=null) ew.ge("juanzengshijian", juanzengshijianstart);
        if(juanzengshijianend!=null) ew.le("juanzengshijian", juanzengshijianend);

        //查询结果
		PageUtils page = juanzengwuziService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengwuzi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询捐赠物资不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('juanzengwuzi/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( JuanzengwuziEntity juanzengwuzi){
       	EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
      	ew.allEq(MPUtil.allEQMapPre( juanzengwuzi, "juanzengwuzi"));
        return R.ok().put("data", juanzengwuziService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条捐赠物资视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('juanzengwuzi/query') 触发。
     */
    @RequestMapping("/query")
    public R query(JuanzengwuziEntity juanzengwuzi){
        EntityWrapper< JuanzengwuziEntity> ew = new EntityWrapper< JuanzengwuziEntity>();
 		ew.allEq(MPUtil.allEQMapPre( juanzengwuzi, "juanzengwuzi"));
		JuanzengwuziView juanzengwuziView =  juanzengwuziService.selectView(ew);
		return R.ok("查询捐赠物资成功").put("data", juanzengwuziView);
    }

    /**
     * 功能：查询捐赠物资管理端详情。
     * 使用端：管理端捐赠物资列表页、编辑页。
     * 前端触发：管理端通过 $http.get('juanzengwuzi/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JuanzengwuziEntity juanzengwuzi = juanzengwuziService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(juanzengwuzi,deSens);
        return R.ok().put("data", juanzengwuzi);
    }

    /**
     * 功能：查询捐赠物资前台详情。
     * 使用端：前台捐赠物资详情页或编辑回显页。
     * 前端触发：front/src/pages/juanzengwuzi/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JuanzengwuziEntity juanzengwuzi = juanzengwuziService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(juanzengwuzi,deSens);
        return R.ok().put("data", juanzengwuzi);
    }




    /**
     * 功能：管理端新增捐赠物资记录。
     * 使用端：管理端捐赠物资新增表单。
     * 前端触发：管理端表单通过 $http.post('juanzengwuzi/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增捐赠物资")
    public R save(@RequestBody JuanzengwuziEntity juanzengwuzi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengwuzi);
        juanzengwuziService.insert(juanzengwuzi);
        return R.ok().put("data",juanzengwuzi.getId());
    }

    /**
     * 功能：前台新增捐赠物资记录。
     * 使用端：前台捐赠物资新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('juanzengwuzi/add') 触发。
     */
    @SysLog("新增捐赠物资")
    @RequestMapping("/add")
    public R add(@RequestBody JuanzengwuziEntity juanzengwuzi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengwuzi);
        juanzengwuziService.insert(juanzengwuzi);
        return R.ok().put("data",juanzengwuzi.getId());
    }





    /**
     * 功能：修改捐赠物资记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('juanzengwuzi/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改捐赠物资")
    public R update(@RequestBody JuanzengwuziEntity juanzengwuzi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengwuzi);
        //全部更新
        juanzengwuziService.updateById(juanzengwuzi);
        return R.ok();
    }

    /**
     * 功能：批量审核捐赠物资记录。
     * 使用端：管理端捐赠物资审核按钮。
     * 前端触发：列表页审核操作通过 $http.post('juanzengwuzi/shBatch') 触发。
     */
    @RequestMapping("/shBatch")
    @Transactional
    @SysLog("审核捐赠物资")
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<JuanzengwuziEntity> list = new ArrayList<JuanzengwuziEntity>();
        for(Long id : ids) {
            JuanzengwuziEntity juanzengwuzi = juanzengwuziService.selectById(id);
            juanzengwuzi.setSfsh(sfsh);
            juanzengwuzi.setShhf(shhf);
            list.add(juanzengwuzi);
        }
        juanzengwuziService.updateBatchById(list);
        return R.ok();
    }




    /**
     * 功能：删除捐赠物资记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('juanzengwuzi/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除捐赠物资")
    public R delete(@RequestBody Long[] ids){
        juanzengwuziService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }







    /**
     * 功能：统计捐赠物资图表数值。
     * 使用端：管理端首页统计、模块统计图表。
     * 前端触发：统计图组件通过 value 接口触发。
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, @RequestParam(required = false, defaultValue = "总和") String func, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_juanzengwuzi_" + xColumnName + "_" + yColumnName + "_timeType.json");
        if(java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        //构建查询统计条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("method", func);
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        String order = request.getParameter("order");
        if (StringUtils.isNotBlank(order)) {
            String orderType = request.getParameter("orderType");
            if (null != orderType) {
                ew.orderBy(true, orderType.equals("x") ? xColumnName : "total", order.equals("desc"));
            }
        }
        if(StringUtils.isNotBlank(conditionColumn)&&StringUtils.isNotBlank(conditionValue))
        {
            String[] conditionColumns = conditionColumn.split(";");
            String[] conditionValues = conditionValue.split(";");

            for (int i = 0; i < conditionColumns.length; i++) {
                String column = conditionColumns[i];
                String value = conditionValues[i];

                // 处理范围查询：如果列名包含逗号，表示是范围查询
                if (column.contains(",")) {
                    String[] rangeColumns = column.split(",");
                    String[] rangeValues = value.split(",");

                    if (rangeColumns.length == 2 && rangeValues.length == 2) {
                        // 第一个列名使用 >= 条件
                        ew.ge(rangeColumns[0], rangeValues[0]);
                        // 第二个列名使用 <= 条件
                        ew.le(rangeColumns[1], rangeValues[1]);
                    }
                } else {
                    // 普通等值查询
                    ew.eq(column, value);
                }
            }
        }
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("juanzengren")) {
            ew.eq("zhanghao", (String)request.getSession().getAttribute("username"));
        }
        //获取结果
        List<Map<String, Object>> result = juanzengwuziService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * 功能：统计捐赠物资图表数值。
     * 使用端：管理端首页统计、模块统计图表。
     * 前端触发：统计图组件通过 value 接口触发。
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, HttpServletRequest request)  throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_juanzengwuzi_" + xColumnName + "_" + String.join("_", yColumnNameMul.split(",")) + "_timeType.json");
        if(java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        //构建查询统计条件
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        String order = request.getParameter("order");
        if (StringUtils.isNotBlank(order)) {
            String orderType = request.getParameter("orderType");
            if (null != orderType) {
                ew.orderBy(true, orderType.equals("x") ? Arrays.asList(xColumnName) : Arrays.asList("total"), order.equals("desc"));
            }
        }
        if(StringUtils.isNotBlank(conditionColumn)&&StringUtils.isNotBlank(conditionValue))
        {
            String[] conditionColumns = conditionColumn.split(";");
            String[] conditionValues = conditionValue.split(";");

            for (int i = 0; i < conditionColumns.length; i++) {
                String column = conditionColumns[i];
                String value = conditionValues[i];

                // 处理范围查询：如果列名包含逗号，表示是范围查询
                if (column.contains(",")) {
                    String[] rangeColumns = column.split(",");
                    String[] rangeValues = value.split(",");

                    if (rangeColumns.length == 2 && rangeValues.length == 2) {
                        // 第一个列名使用 >= 条件
                        ew.ge(rangeColumns[0], rangeValues[0]);
                        // 第二个列名使用 <= 条件
                        ew.le(rangeColumns[1], rangeValues[1]);
                    }
                } else {
                    // 普通等值查询
                    ew.eq(column, value);
                }
            }
        }
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("juanzengren")) {
            ew.eq("zhanghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = juanzengwuziService.selectValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 功能：统计捐赠物资图表数值。
     * 使用端：管理端首页统计、模块统计图表。
     * 前端触发：统计图组件通过 value 接口触发。
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, @RequestParam(required = false, defaultValue = "总和") String func, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_juanzengwuzi_" + xColumnName + "_" + yColumnName + "_"+timeStatType+".json");
        if(java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        params.put("method", func);
        //构建查询统计条件
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        String order = request.getParameter("order");
        if (StringUtils.isNotBlank(order)) {
            String orderType = request.getParameter("orderType");
            if (null != orderType) {
                ew.orderBy(true, orderType.equals("x") ? xColumnName : yColumnName, order.equals("desc"));
            }
        }
        if(StringUtils.isNotBlank(conditionColumn)&&StringUtils.isNotBlank(conditionValue))
        {
            String[] conditionColumns = conditionColumn.split(";");
            String[] conditionValues = conditionValue.split(";");

            for (int i = 0; i < conditionColumns.length; i++) {
                String column = conditionColumns[i];
                String value = conditionValues[i];

                // 处理范围查询：如果列名包含逗号，表示是范围查询
                if (column.contains(",")) {
                    String[] rangeColumns = column.split(",");
                    String[] rangeValues = value.split(",");

                    if (rangeColumns.length == 2 && rangeValues.length == 2) {
                        // 第一个列名使用 >= 条件
                        ew.ge(rangeColumns[0], rangeValues[0]);
                        // 第二个列名使用 <= 条件
                        ew.le(rangeColumns[1], rangeValues[1]);
                    }
                } else {
                    // 普通等值查询
                    ew.eq(column, value);
                }
            }
        }
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("juanzengren")) {
            ew.eq("zhanghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = juanzengwuziService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * 功能：统计捐赠物资图表数值。
     * 使用端：管理端首页统计、模块统计图表。
     * 前端触发：统计图组件通过 value 接口触发。
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType, @RequestParam String yColumnNameMul, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_juanzengwuzi_" + xColumnName + "_" + String.join("_", yColumnNameMul.split(",")) + ".json");
        if (java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        //构建查询统计条件
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        String order = request.getParameter("order");
        if (StringUtils.isNotBlank(order)) {
            String orderType = request.getParameter("orderType");
            if (null != orderType) {
                ew.orderBy(true, orderType.equals("x") ? Arrays.asList(xColumnName) : Arrays.asList(yColumnNames), order.equals("desc"));
            }
        }
        if(StringUtils.isNotBlank(conditionColumn)&&StringUtils.isNotBlank(conditionValue))
        {
            String[] conditionColumns = conditionColumn.split(";");
            String[] conditionValues = conditionValue.split(";");

            for (int i = 0; i < conditionColumns.length; i++) {
                String column = conditionColumns[i];
                String value = conditionValues[i];

                // 处理范围查询：如果列名包含逗号，表示是范围查询
                if (column.contains(",")) {
                    String[] rangeColumns = column.split(",");
                    String[] rangeValues = value.split(",");

                    if (rangeColumns.length == 2 && rangeValues.length == 2) {
                        // 第一个列名使用 >= 条件
                        ew.ge(rangeColumns[0], rangeValues[0]);
                        // 第二个列名使用 <= 条件
                        ew.le(rangeColumns[1], rangeValues[1]);
                    }
                } else {
                    // 普通等值查询
                    ew.eq(column, value);
                }
            }
        }
        List<List<Map<String, Object>>> result2 = new ArrayList<List<Map<String,Object>>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("juanzengren")) {
            ew.eq("zhanghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = juanzengwuziService.selectTimeStatValue(params, ew);
            for(Map<String, Object> m : result) {
                for(String k : m.keySet()) {
                    if(m.get(k) instanceof Date) {
                        m.put(k, sdf.format((Date)m.get(k)));
                    }
                }
            }
            result2.add(result);
        }
        return R.ok().put("data", result2);
    }

    /**
     * 功能：按字段分组统计捐赠物资数据。
     * 使用端：管理端首页统计、前台统计图表。
     * 前端触发：图表组件通过 group 接口触发。
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("group_juanzengwuzi_" + columnName + "_timeType.json");
        if(java.nio.file.Files.exists(path)){
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        //构建查询统计条件
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        if(StringUtils.isNotBlank(conditionColumn)&&StringUtils.isNotBlank(conditionValue))
        {
            String[] conditionColumns = conditionColumn.split(";");
            String[] conditionValues = conditionValue.split(";");

            for (int i = 0; i < conditionColumns.length; i++) {
                String column = conditionColumns[i];
                String value = conditionValues[i];

                // 处理范围查询：如果列名包含逗号，表示是范围查询
                if (column.contains(",")) {
                    String[] rangeColumns = column.split(",");
                    String[] rangeValues = value.split(",");

                    if (rangeColumns.length == 2 && rangeValues.length == 2) {
                        // 第一个列名使用 >= 条件
                        ew.ge(rangeColumns[0], rangeValues[0]);
                        // 第二个列名使用 <= 条件
                        ew.le(rangeColumns[1], rangeValues[1]);
                    }
                } else {
                    // 普通等值查询
                    ew.eq(column, value);
                }
            }
        }
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("juanzengren")) {
            ew.eq("zhanghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = juanzengwuziService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }




    /**
     * 功能：统计捐赠物资总数。
     * 使用端：首页总数卡片或模块统计区域。
     * 前端触发：首页统计组件通过 $http.get('juanzengwuzi/count') 触发。
     */
    @RequestMapping("/count")
    public R count(@RequestParam Map<String, Object> params,JuanzengwuziEntity juanzengwuzi, HttpServletRequest request){
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("juanzengren")) {
            juanzengwuzi.setZhanghao((String)request.getSession().getAttribute("username"));
        }
        EntityWrapper<JuanzengwuziEntity> ew = new EntityWrapper<JuanzengwuziEntity>();
        int count = juanzengwuziService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengwuzi), params), params));
        return R.ok().put("data", count);
    }


}
