package net.mingsoft.mall.biz;

import java.util.List;

import com.mingsoft.base.biz.IBaseBiz;

import net.mingsoft.attention.constant.e.AttentionTypeEnum;
import net.mingsoft.attention.entity.BasicAttentionEntity;
import net.mingsoft.mall.entity.ProductEntity;
import net.mingsoft.mall.entity.CartEntity;

/**
 * 铭飞商城－购物车
 * @author 铭飞开发团队
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014年7月12日<br/>
 * 历史修订：<br/>
 */
public interface ICartBiz extends IBaseBiz {

	/**
	 * 购物车查询
	 * @param cartIds 购物车编号
	 * @param cartProductDetailIds 规格编号
	 * @param peopleId 用户编号
	 * @return
	 */
	List<CartEntity> query(int[] cartIds,int[]cartProductDetailIds,int peopleId);
	
	/**
	 * 购物车查询
	 * @param cartIds 商品编号
	 * @param cartProductDetailId 规格编号
	 * @param peopleId 用户编号
	 * @return
	 */
	List<CartEntity> query(int productId,int cartProductDetailId,int peopleId);
	
	/**
	 * 通过加入购物车或立即购买方式保存
	 * @param cart 购物车
	 * @return
	 */
	int saveEntity(CartEntity cart);
	
}
