/**
 * 
 */
package net.mingsoft.mall.action.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mingsoft.util.StringUtil;

import net.mingsoft.mall.action.BaseAction;
import net.mingsoft.mall.biz.IProductSpecificationBiz;
import net.mingsoft.mall.constant.ModelCode;
import net.mingsoft.mall.entity.ProductEntity;
import net.mingsoft.mall.entity.ProductSpecificationEntity;

/**
 * 
 * <p>
 * <b>铭飞科技-商城</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 *
 * @author 成卫雄
 *                QQ:330216230
 *
 * <p>
 * Comments:商品规格外部请求JSON
 * </p>
 *
 * <p>
 * Create Date:2014-12-11
 * </p>
 *
 * <p>
 * Modification history:
 * </p>
 */
@Controller("webProductSpecification")
@RequestMapping("/mall/productSpecification")
public class ProductSpecificationAction extends BaseAction{

	/**
	 * 产品规格关联业务层
	 */
	@Autowired
	private IProductSpecificationBiz specBiz;
	
	/**
	 * 当前商品规格集合
	 */
	@RequestMapping("/{productId}/list")
	@ResponseBody
	public void list(@PathVariable("productId")int productId, HttpServletRequest request, HttpServletResponse response){
		
		if(!StringUtil.isInteger(productId)){
			this.outJson(response, ModelCode.MALL_SPECIFICATIONS, false, null);
			return;
		}
		
		//根据商品Id查询当前商品的规格数据
		String str = specBiz.getDataStrByProductId(productId);
		this.outString(response, str);
	}
	
	/**
	 * 根据规格值查询对应的商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryBySpecifications")
	@ResponseBody
	public void queryBySpecifications(String jsonStr, HttpServletRequest request, HttpServletResponse response){
		
		// 传进来的产品规格列表 为查询条件
		List<ProductSpecificationEntity> list = JSONArray.parseArray(jsonStr, ProductSpecificationEntity.class);
		// 根据条件获得产品实体
		List<ProductEntity> productList = specBiz.queryBySpecValues(list);
		
		String resultStr = JSON.toJSONString(productList);
		
		if (StringUtil.isBlank(resultStr)){
			outJson(response, ModelCode.MALL_SPECIFICATIONS, false);
		}
		else{
			outJson(response, ModelCode.MALL_SPECIFICATIONS, true, resultStr);
		}
	}
	
	/**
	 * 根据商品规格获取商品规格数据
	 */
	@RequestMapping("/{psId}/prodcutSpecList")
	@ResponseBody
	@Deprecated
	public void queryByProductSpecificationsId(@PathVariable("psId")int psId, HttpServletRequest request, HttpServletResponse response){

		if(!StringUtil.isInteger(psId)){
			this.outJson(response, ModelCode.MALL_SPECIFICATIONS, false, getResString("mall.err.data.type", getResString("ps_id")));//产品规格id数据类型错误
			return ;
		}
		//根据商品Id查询当前商品的规格数据
//		List<ProductSpecificationsEntity> psList = this.productSpecificationBiz.queryListByProductSpecificationsId(productSpecificationsId);
//		this.outJson(response, JSONObject.toJSONString(psList));
	}
	

}


