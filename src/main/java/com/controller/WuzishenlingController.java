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

import com.entity.WuzishenlingEntity;
import com.entity.view.WuzishenlingView;

import com.service.WuzishenlingService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 物资申领
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@RestController
@RequestMapping("/wuzishenling")
public class WuzishenlingController {
    @Autowired
    private WuzishenlingService wuzishenlingService;

    @Autowired
    private WuzixinxiService wuzixinxiService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,WuzishenlingEntity wuzishenling,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jieshoujigou")) {
			wuzishenling.setJigouzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();


        //查询结果
		PageUtils page = wuzishenlingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wuzishenling), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,WuzishenlingEntity wuzishenling,
                @RequestParam(required = false) Double shenlingshuliangstart,
                @RequestParam(required = false) Double shenlingshuliangend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date shenlingshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date shenlingshijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
        if(shenlingshuliangstart!=null) ew.ge("shenlingshuliang", shenlingshuliangstart);
        if(shenlingshuliangend!=null) ew.le("shenlingshuliang", shenlingshuliangend);
        if(shenlingshijianstart!=null) ew.ge("shenlingshijian", shenlingshijianstart);
        if(shenlingshijianend!=null) ew.le("shenlingshijian", shenlingshijianend);

        //查询结果
		PageUtils page = wuzishenlingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wuzishenling), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( WuzishenlingEntity wuzishenling){
       	EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( wuzishenling, "wuzishenling"));
        return R.ok().put("data", wuzishenlingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(WuzishenlingEntity wuzishenling){
        EntityWrapper< WuzishenlingEntity> ew = new EntityWrapper< WuzishenlingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( wuzishenling, "wuzishenling"));
		WuzishenlingView wuzishenlingView =  wuzishenlingService.selectView(ew);
		return R.ok("查询物资申领成功").put("data", wuzishenlingView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        WuzishenlingEntity wuzishenling = wuzishenlingService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(wuzishenling,deSens);
        return R.ok().put("data", wuzishenling);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        WuzishenlingEntity wuzishenling = wuzishenlingService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(wuzishenling,deSens);
        return R.ok().put("data", wuzishenling);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增物资申领")
    public R save(@RequestBody WuzishenlingEntity wuzishenling, HttpServletRequest request){
        //ValidatorUtils.validateEntity(wuzishenling);
        R check = prepareByDonationNo(wuzishenling);
        if(check != null) {
            return check;
        }
        wuzishenlingService.insert(wuzishenling);
        return R.ok().put("data",wuzishenling.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增物资申领")
    @RequestMapping("/add")
    public R add(@RequestBody WuzishenlingEntity wuzishenling, HttpServletRequest request){
        //ValidatorUtils.validateEntity(wuzishenling);
        R check = prepareByDonationNo(wuzishenling);
        if(check != null) {
            return check;
        }
        wuzishenlingService.insert(wuzishenling);
        return R.ok().put("data",wuzishenling.getId());
    }

    private R prepareByDonationNo(WuzishenlingEntity wuzishenling) {
        if(wuzishenling == null) {
            return R.error("申领信息不能为空");
        }
        Integer applyCount = wuzishenling.getShenlingshuliang() == null ? 0 : wuzishenling.getShenlingshuliang();
        if(applyCount <= 0) {
            return R.error("申领数量必须大于0");
        }

        WuzixinxiEntity wuzixinxi = findWuzixinxi(wuzishenling);
        if(wuzixinxi == null) {
            return R.error("请选择有效的物资信息");
        }
        Integer stock = wuzixinxi.getWuzishuliang() == null ? 0 : wuzixinxi.getWuzishuliang();
        if(stock < applyCount) {
            return R.error("库存不足");
        }
        wuzishenling.setJuanzengbianhao(wuzixinxi.getJuanzengbianhao());
        wuzishenling.setWuzimingcheng(wuzixinxi.getWuzimingcheng());
        wuzishenling.setWuzizhonglei(wuzixinxi.getWuzizhonglei());
        wuzishenling.setWuziguige(wuzixinxi.getWuziguige());
        wuzishenling.setWuzitupian(wuzixinxi.getWuzitupian());
        return null;
    }

    private WuzixinxiEntity findWuzixinxi(WuzishenlingEntity wuzishenling) {
        if(wuzishenling.getWuzixinxiid() != null) {
            WuzixinxiEntity wuzixinxi = wuzixinxiService.selectById(wuzishenling.getWuzixinxiid());
            if(wuzixinxi != null) {
                return wuzixinxi;
            }
        }

        WuzixinxiEntity wuzixinxi = findWuzixinxiByDonationNo(wuzishenling.getJuanzengbianhao());
        if(wuzixinxi != null) {
            return wuzixinxi;
        }

        // Some old records used shenlingbianhao to carry the donation number.
        wuzixinxi = findWuzixinxiByDonationNo(wuzishenling.getShenlingbianhao());
        if(wuzixinxi != null) {
            return wuzixinxi;
        }

        EntityWrapper<WuzixinxiEntity> ew = new EntityWrapper<WuzixinxiEntity>();
        boolean hasCondition = false;
        if(StringUtils.isNotBlank(wuzishenling.getWuzimingcheng())) {
            ew.eq("wuzimingcheng", wuzishenling.getWuzimingcheng());
            hasCondition = true;
        }
        if(StringUtils.isNotBlank(wuzishenling.getWuzizhonglei())) {
            ew.eq("wuzizhonglei", wuzishenling.getWuzizhonglei());
            hasCondition = true;
        }
        if(StringUtils.isNotBlank(wuzishenling.getWuziguige())) {
            ew.eq("wuziguige", wuzishenling.getWuziguige());
            hasCondition = true;
        }
        if(!hasCondition) {
            return null;
        }

        List<WuzixinxiEntity> list = wuzixinxiService.selectList(ew);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    private WuzixinxiEntity findWuzixinxiByDonationNo(String juanzengbianhao) {
        if(StringUtils.isBlank(juanzengbianhao)) {
            return null;
        }
        return wuzixinxiService.selectOne(
            new EntityWrapper<WuzixinxiEntity>().eq("juanzengbianhao", juanzengbianhao)
        );
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改物资申领")
    public R update(@RequestBody WuzishenlingEntity wuzishenling, HttpServletRequest request){
        //ValidatorUtils.validateEntity(wuzishenling);
        //全部更新
        R check = prepareByDonationNo(wuzishenling);
        if(check != null) {
            return check;
        }
        wuzishenlingService.updateById(wuzishenling);
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    @SysLog("审核物资申领")
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<WuzishenlingEntity> list = new ArrayList<WuzishenlingEntity>();
        for(Long id : ids) {
            WuzishenlingEntity wuzishenling = wuzishenlingService.selectById(id);
            wuzishenling.setSfsh(sfsh);
            wuzishenling.setShhf(shhf);
            list.add(wuzishenling);
        }
        wuzishenlingService.updateBatchById(list);
        return R.ok();
    }




    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除物资申领")
    public R delete(@RequestBody Long[] ids){
        wuzishenlingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }







    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, @RequestParam(required = false, defaultValue = "总和") String func, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_wuzishenling_" + xColumnName + "_" + yColumnName + "_timeType.json");
        if(java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        //构建查询统计条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("method", func);
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
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
        if(tableName.equals("jieshoujigou")) {
            ew.eq("jigouzhanghao", (String)request.getSession().getAttribute("username"));
        }
        //获取结果
        List<Map<String, Object>> result = wuzishenlingService.selectValue(params, ew);
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
     * （按值统计(多)）
     */
    @RequestMapping("/valueMul/{xColumnName}")
    public R valueMul(@PathVariable("xColumnName") String xColumnName,@RequestParam String yColumnNameMul, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, HttpServletRequest request)  throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_wuzishenling_" + xColumnName + "_" + String.join("_", yColumnNameMul.split(",")) + "_timeType.json");
        if(java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        //构建查询统计条件
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
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
        if(tableName.equals("jieshoujigou")) {
            ew.eq("jigouzhanghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = wuzishenlingService.selectValue(params, ew);
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
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, @RequestParam(required = false, defaultValue = "总和") String func, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_wuzishenling_" + xColumnName + "_" + yColumnName + "_"+timeStatType+".json");
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
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
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
        if(tableName.equals("jieshoujigou")) {
            ew.eq("jigouzhanghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = wuzishenlingService.selectTimeStatValue(params, ew);
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
     * （按值统计）时间统计类型(多)
     */
    @RequestMapping("/valueMul/{xColumnName}/{timeStatType}")
    public R valueMulDay(@PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType, @RequestParam String yColumnNameMul, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("value_wuzishenling_" + xColumnName + "_" + String.join("_", yColumnNameMul.split(",")) + ".json");
        if (java.nio.file.Files.exists(path)) {
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        String[] yColumnNames = yColumnNameMul.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("timeStatType", timeStatType);
        //构建查询统计条件
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
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
        if(tableName.equals("jieshoujigou")) {
            ew.eq("jigouzhanghao", (String)request.getSession().getAttribute("username"));
        }
        for(int i=0;i<yColumnNames.length;i++) {
            params.put("yColumn", yColumnNames[i]);
            List<Map<String, Object>> result = wuzishenlingService.selectTimeStatValue(params, ew);
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
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName, @RequestParam(required = false) String conditionColumn, @RequestParam(required = false) String conditionValue, HttpServletRequest request) throws IOException {
        //读取文件，如果文件存在，则优先返回文件内容
        java.nio.file.Path path = java.nio.file.Paths.get("group_wuzishenling_" + columnName + "_timeType.json");
        if(java.nio.file.Files.exists(path)){
            String content = new String(java.nio.file.Files.readAllBytes(path), java.nio.charset.StandardCharsets.UTF_8);
            return R.ok().put("data", (new org.json.JSONArray(content)).toList());
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        //构建查询统计条件
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
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
        if(tableName.equals("jieshoujigou")) {
            ew.eq("jigouzhanghao", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = wuzishenlingService.selectGroup(params, ew);
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
     * 总数量
     */
    @RequestMapping("/count")
    public R count(@RequestParam Map<String, Object> params,WuzishenlingEntity wuzishenling, HttpServletRequest request){
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("jieshoujigou")) {
            wuzishenling.setJigouzhanghao((String)request.getSession().getAttribute("username"));
        }
        EntityWrapper<WuzishenlingEntity> ew = new EntityWrapper<WuzishenlingEntity>();
        int count = wuzishenlingService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wuzishenling), params), params));
        return R.ok().put("data", count);
    }


}
