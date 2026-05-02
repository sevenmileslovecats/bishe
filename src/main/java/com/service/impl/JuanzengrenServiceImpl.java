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


import com.dao.JuanzengrenDao;
import com.entity.JuanzengrenEntity;
import com.service.JuanzengrenService;
import com.entity.vo.JuanzengrenVO;
import com.entity.view.JuanzengrenView;

@Service("juanzengrenService")
public class JuanzengrenServiceImpl extends ServiceImpl<JuanzengrenDao, JuanzengrenEntity> implements JuanzengrenService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JuanzengrenEntity> page = this.selectPage(
                new Query<JuanzengrenEntity>(params).getPage(),
                new EntityWrapper<JuanzengrenEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JuanzengrenEntity> wrapper) {
		  Page<JuanzengrenView> page =new Query<JuanzengrenView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<JuanzengrenVO> selectListVO(Wrapper<JuanzengrenEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JuanzengrenVO selectVO(Wrapper<JuanzengrenEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JuanzengrenView> selectListView(Wrapper<JuanzengrenEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JuanzengrenView selectView(Wrapper<JuanzengrenEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
