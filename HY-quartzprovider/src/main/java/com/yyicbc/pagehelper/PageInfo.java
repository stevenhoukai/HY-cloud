package com.yyicbc.pagehelper;

import java.util.List;

public class PageInfo {
	private int TotlePage;
	private int PgaeSize;
	private int PageNow;
	private boolean isFristPage;
	private boolean isLastPage;
	private boolean havePerPage;
	private boolean haveNexPage;
	private List<?> list;
	
	public PageInfo() {
		// TODO 自动生成的构造函数存根
	}
 
	public int getTotlePage() {
		return TotlePage;
	}
 
	public void setTotlePage(int totlePage) {
		TotlePage = totlePage;
	}
 
	public int getPgaeSize() {
		return PgaeSize;
	}
 
	public void setPgaeSize(int pgaeSize) {
		PgaeSize = pgaeSize;
	}
 
	public int getPageNow() {
		return PageNow;
	}
 
	public void setPageNow(int pageNow) {
		PageNow = pageNow;
	}
 
	public boolean isFristPage() {
		return isFristPage;
	}
 
	public void setFristPage(boolean isFristPage) {
		this.isFristPage = isFristPage;
	}
 
	public boolean isLastPage() {
		return isLastPage;
	}
 
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
 
	public boolean isHavePerPage() {
		return havePerPage;
	}
 
	public void setHavePerPage(boolean havePerPage) {
		this.havePerPage = havePerPage;
	}
 
	public boolean isHaveNexPage() {
		return haveNexPage;
	}
 
	public void setHaveNexPage(boolean haveNexPage) {
		this.haveNexPage = haveNexPage;
	}
 
	public List<?> getList() {
		return list;
	}
 
	public void setList(List<?> list) {
		this.list = list;
	}
	
}
