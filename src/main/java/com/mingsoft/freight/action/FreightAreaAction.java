/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.freight.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.freight.biz.IFreightAreaBiz;
import com.mingsoft.freight.dao.IFreightAreaDao;
import com.mingsoft.freight.entity.FreightAreaEntity;

import net.mingsoft.basic.util.BasicUtil;

@Controller
@RequestMapping("/${managerPath}/freightArea")
public class FreightAreaAction extends BaseAction {

	@Autowired
	private IFreightAreaBiz freightAreaBiz;
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 加载页面显示所有区域信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	private String index(HttpServletRequest request){
		//左侧列表
		List<FreightAreaEntity> listArea = freightAreaBiz.queryAllArea();
		request.setAttribute("listArea", listArea);
		//树形部分
		int modelId =  BasicUtil.getModelCodeId(ModelCode.CITY);
		CategoryEntity category = new CategoryEntity();
		category.setCategoryModelId(modelId);
		List<CategoryEntity> list = categoryBiz.queryChilds(category);
		String categoryJson = JSONArray.toJSONString(list);
		request.setAttribute("categoryJson", categoryJson);
		//区域名称部分和区域管理部分
		String faTitle = request.getParameter("faTitle");
		request.setAttribute("faTitle", faTitle);
		return view("/freight/area/index");
	}
	
	/**
	 * 更新区域信息
	 * @return
	 */
	@RequestMapping("/update")
	private void update(){
		
	}
	
	/**
	 * 进入编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	private String edit(@ModelAttribute CategoryEntity categoryEntity, HttpServletRequest request, HttpServletResponse response){
		//树形部分
		int modelId =  BasicUtil.getModelCodeId(ModelCode.CITY);
		CategoryEntity category = new CategoryEntity();
		category.setCategoryModelId(modelId);
		List<CategoryEntity> list = categoryBiz.queryChilds(category);
		String categoryJson = JSONArray.toJSONString(list);
		request.setAttribute("categoryJson", categoryJson);
		//区域名称部分和区域管理部分
		String faTitle = request.getParameter("faTitle");
		request.setAttribute("faTitle", faTitle);
		return view("/freight/area/area_form");
	}
	
	/**
	 * 加载添加页面
	 * @param categoryEntity
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/add")
	private String add(@ModelAttribute CategoryEntity categoryEntity, HttpServletRequest request, HttpServletResponse response){
		int modelId =  BasicUtil.getModelCodeId(ModelCode.CITY);
		// 传入一个实体，提供查询条件
		CategoryEntity category = new CategoryEntity();
		category.setCategoryModelId(modelId);
		//获取数据
		List<CategoryEntity> list = categoryBiz.queryChilds(category);
		String categoryJson = JSONArray.toJSONString(list);
		request.setAttribute("categoryJson", categoryJson);
		return view("/freight/area/area_form");
	}
	
	/**
	 * 保存添加的区域信息
	 * @return
	 */
	@RequestMapping("/save")
	private void save(@ModelAttribute FreightAreaEntity area, HttpServletResponse response, HttpServletRequest request){
		String faTitle = request.getParameter("faTitle");
		FreightAreaEntity newEntity = new FreightAreaEntity();
		newEntity.setFaTitle(faTitle);
		FreightAreaEntity areaEntity = freightAreaBiz.getAreaEntity(newEntity);
		boolean op = false;
		if(areaEntity == null){
			freightAreaBiz.saveAreaEntity(area);
			op = true;
			this.outJson(response,op);
		}else{
			op = false;
			this.outJson(response,op);
		}
	}
	
	/**
	 * 删除区域功能
	 * @param area
	 * @param response
	 * @param request
	 */
	@RequestMapping("/delete")
	private void delete(@ModelAttribute FreightAreaEntity area, HttpServletResponse response, HttpServletRequest request){
		String faIds = request.getParameter("faIds");
		freightAreaBiz.delete(faIds);
	}
}