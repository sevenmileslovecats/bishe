package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.JuanzengzhengshuDao;
import com.entity.JuanzengzhengshuEntity;
import com.service.JuanzengzhengshuService;
import com.entity.vo.JuanzengzhengshuVO;
import com.entity.view.JuanzengzhengshuView;

@Service("juanzengzhengshuService")
public class JuanzengzhengshuServiceImpl extends ServiceImpl<JuanzengzhengshuDao, JuanzengzhengshuEntity> implements JuanzengzhengshuService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JuanzengzhengshuEntity> page = this.selectPage(
                new Query<JuanzengzhengshuEntity>(params).getPage(),
                new EntityWrapper<JuanzengzhengshuEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JuanzengzhengshuEntity> wrapper) {
		  Page<JuanzengzhengshuView> page =new Query<JuanzengzhengshuView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<JuanzengzhengshuVO> selectListVO(Wrapper<JuanzengzhengshuEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JuanzengzhengshuVO selectVO(Wrapper<JuanzengzhengshuEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JuanzengzhengshuView> selectListView(Wrapper<JuanzengzhengshuEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JuanzengzhengshuView selectView(Wrapper<JuanzengzhengshuEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
