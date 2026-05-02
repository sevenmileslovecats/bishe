package com.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ChukufenboEntity;
import com.entity.JieshouxinxiEntity;
import com.entity.JuanzengwuziEntity;
import com.entity.ShiyongfankuiEntity;
import com.entity.WuzishenlingEntity;
import com.entity.YanshoujiluEntity;
import com.entity.YiyifankuiEntity;
import com.entity.vo.ZhuishuchaxunFenZhiVO;
import com.entity.vo.ZhuishuchaxunVO;
import com.service.ChukufenboService;
import com.service.JieshouxinxiService;
import com.service.JuanzengwuziService;
import com.service.ShiyongfankuiService;
import com.service.WuzishenlingService;
import com.service.YanshoujiluService;
import com.service.YiyifankuiService;
import com.utils.R;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhuishuchaxun")
public class ZhuishuchaxunController {
	@Autowired
	private JuanzengwuziService juanzengwuziService;
	@Autowired
	private YanshoujiluService yanshoujiluService;
	@Autowired
	private WuzishenlingService wuzishenlingService;
	@Autowired
	private ChukufenboService chukufenboService;
	@Autowired
	private JieshouxinxiService jieshouxinxiService;
	@Autowired
	private ShiyongfankuiService shiyongfankuiService;
	@Autowired
	private YiyifankuiService yiyifankuiService;

	@RequestMapping("/trace")
	public R trace(@RequestParam String juanzengbianhao, HttpServletRequest request) {
		Object roleObj = request.getSession().getAttribute("role");
		if (roleObj == null || !"管理员".equals(roleObj.toString())) {
			return R.error(403, "无权限");
		}
		if (StringUtils.isBlank(juanzengbianhao)) {
			return R.error("捐赠编号不能为空");
		}

		JuanzengwuziEntity juanzengwuzi = juanzengwuziService
				.selectOne(new EntityWrapper<JuanzengwuziEntity>().eq("juanzengbianhao", juanzengbianhao));
		if (juanzengwuzi == null) {
			return R.error("未找到捐赠记录");
		}

		List<String> warnings = new ArrayList<>();

		List<YanshoujiluEntity> yanshoujiluList = yanshoujiluService.selectList(
				new EntityWrapper<YanshoujiluEntity>().eq("juanzengbianhao", juanzengbianhao).orderBy("yanshoushijian", true));
		if (yanshoujiluList == null || yanshoujiluList.isEmpty()) {
			warnings.add("该捐赠编号暂无验收记录");
		}

		List<WuzishenlingEntity> wuzishenlingList = wuzishenlingService.selectList(
				new EntityWrapper<WuzishenlingEntity>().eq("juanzengbianhao", juanzengbianhao).orderBy("shenlingshijian", true));
		if (wuzishenlingList == null || wuzishenlingList.isEmpty()) {
			warnings.add("该捐赠编号暂无申领记录");
		}

		List<ZhuishuchaxunFenZhiVO> fenZhiList = new ArrayList<>();
		if (wuzishenlingList != null) {
			for (WuzishenlingEntity wuzishenling : wuzishenlingList) {
				ZhuishuchaxunFenZhiVO fenZhi = new ZhuishuchaxunFenZhiVO();
				fenZhi.setWuzishenling(wuzishenling);

				List<String> fenZhiWarnings = new ArrayList<>();
				String shenlingbianhao = wuzishenling.getShenlingbianhao();
				if (StringUtils.isBlank(shenlingbianhao)) {
					fenZhiWarnings.add("申领记录缺少申领编号");
					fenZhi.setWarnings(fenZhiWarnings);
					fenZhiList.add(fenZhi);
					continue;
				}

				List<ChukufenboEntity> chukufenboList = chukufenboService.selectList(
						new EntityWrapper<ChukufenboEntity>().eq("shenlingbianhao", shenlingbianhao).orderBy("chukushijian", true));
				if (chukufenboList == null || chukufenboList.isEmpty()) {
					fenZhiWarnings.add("暂无出库记录");
				}

				List<JieshouxinxiEntity> jieshouxinxiList = jieshouxinxiService.selectList(
						new EntityWrapper<JieshouxinxiEntity>().eq("shenlingbianhao", shenlingbianhao).orderBy("qianshoushijian", true));
				if (jieshouxinxiList == null || jieshouxinxiList.isEmpty()) {
					fenZhiWarnings.add("暂无接收记录");
				}

				List<ShiyongfankuiEntity> shiyongfankuiList = shiyongfankuiService.selectList(
						new EntityWrapper<ShiyongfankuiEntity>().eq("shenlingbianhao", shenlingbianhao).orderBy("fankuishijian", true));
				if (shiyongfankuiList == null || shiyongfankuiList.isEmpty()) {
					fenZhiWarnings.add("暂无使用反馈");
				}

				List<YiyifankuiEntity> yiyifankuiList = yiyifankuiService.selectList(
						new EntityWrapper<YiyifankuiEntity>().eq("shenlingbianhao", shenlingbianhao).orderBy("tijiaoshijian", true));
				if (yiyifankuiList == null || yiyifankuiList.isEmpty()) {
					fenZhiWarnings.add("暂无异议反馈");
				}

				fenZhi.setChukufenboList((List) chukufenboList);
				fenZhi.setJieshouxinxiList((List)jieshouxinxiList);
				fenZhi.setShiyongfankuiList((List)shiyongfankuiList);
				fenZhi.setYiyifankuiList((List)yiyifankuiList);
				fenZhi.setWarnings(fenZhiWarnings);
				fenZhiList.add(fenZhi);
			}
		}

		ZhuishuchaxunVO data = new ZhuishuchaxunVO();
		data.setJuanzengwuzi(juanzengwuzi);
		data.setYanshoujiluList((List)yanshoujiluList);
		data.setFenZhiList(fenZhiList);
		data.setWarnings(warnings);
		return R.ok().put("data", data);
	}
}

