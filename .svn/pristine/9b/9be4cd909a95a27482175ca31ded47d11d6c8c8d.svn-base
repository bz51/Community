package com.community.core;

import java.io.Serializable;
import java.util.List;



/**
 * project：互联网内容管理系统
 * Company: 娄伟�?
*/



/**
 * <p>标题: 分页</p>
 * <p>描述: 用于前台列表页的显示分页</p>
 * <p>模块: 通用平台</p>
 * <p>版权: Copyright (c) 2009 娄伟�?
 * @author 娄伟�?
 * @version 1.0
 * @since 2009-2-25 上午10:34:42
 * @history（历次修订内容�?修订人�?修订时间等） 
 */
public class Pagination implements Serializable {
	
	private static final long serialVersionUID = 475323711973090750L;

	/** 当前是第几页 */
	public long currPage = 1;
	
	/** �?��有多少页 */
	public long maxPage;
	
	/** �?��有多少行*/
	public long maxRowCount;
	
	/** 每页多少�?*/
	public int rowsPerPage = 3;
	
	/** 查询关键�?*/
	public String queryKey;
	
	/** 模糊查询关键�?*/
	public String queryKeyWord;
	
	/** 本页要显示的数据 */
	@SuppressWarnings("unchecked")
	private List data;
	
	/**
	 * 根据总行数计算�?页数
	 */
//	public void countMaxPage() {
//		this.maxPage = ((this.maxRowCount-1) / this.rowsPerPage) + 1;
//	}

	/**
	 * @return the maxPage
	 */
	public long getMaxPage() {
		return maxPage;
	}

	/**
	 * @param maxPage the maxPage to set
	 */
	public void setMaxPage(long maxPage) {
		this.maxPage = maxPage;
	}

	/**
	 * 设置分页数据
	 */
	public void setData(List data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public List getData() {
		return data;
	}

	/**
	 * 设置�?��记录�?并计算最大页
	 */
	public void setMaxRowCount(long maxRowCount) {
		this.maxRowCount = maxRowCount;
//		this.countMaxPage();
	}

	/**
	 * @return the maxRowCount
	 */
	public long getMaxRowCount() {
		return maxRowCount;
	}

	/**
	 * 获取每页记录数量
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * @return the currPage
	 */
	public long getCurrPage() {
		return currPage;
	}

	/**
	 * @param currPage the currPage to set
	 */
	public void setCurrPage(long currPage) {
		this.currPage = currPage;
	}

	/**
	 * 设置每页记录数量 
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
}
