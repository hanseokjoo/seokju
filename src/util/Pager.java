package util;

import vo.*;

public class Pager {

	public static final int perPage  = 15; // 페이지 당 표시할 게시물 개수
	public static final int maxBlock = 10; // 페이지 블럭 표시 개수
	
	private int total;
	private int maxPage;
	private int curPage;
	
	SearchVo search = null;

	public Pager(int total)
	{
		this.total = total;
		this.maxPage = ( total - 1 ) / Pager.perPage + 1;	
	}
	
	public void setCurPage(int curPage)
	{
		this.curPage = curPage;
	}

	public int getCurPage()
	{
		return(this.curPage);
	}
	
	public String getPage( int page )
	{
		String retval = this.getPageLink( page );
		
		System.out.printf("Pager: getPage(%d) = %s\r\n", page, retval);
		return retval;
	}

	public int getStartBlock(int curPage)
	{
		return (( curPage -1 ) / Pager.maxBlock ) * Pager.maxBlock + 1;
	}
	
	public int getEndBlock()
	{
		return (getStartBlock(this.curPage) + Pager.maxBlock - 1);
	}
	
	public int getMaxPage()
	{
		return this.maxPage;
	}
	
	public int getListIndex(int curPage)
	{
		int retval;
		
		retval = (curPage - 1) * Pager.perPage;

		return(retval);
	}
	
	public int getPageSize(int curPage)
	{
		int pageSize;
		
		if (curPage == this.maxPage) {
			pageSize = total - (this.maxPage - 1) * Pager.perPage;
		}
		else {
			pageSize = Pager.perPage;
		}
		
		return pageSize;
	}
	
	public int getFrameSize(int curPage)
	{
		int lastFullFramePage = (int) (this.maxPage / Pager.maxBlock) * Pager.maxBlock;
		int result;
		
		if (curPage <= lastFullFramePage) {
			result = Pager.maxBlock;
		}
		else {
			result = (this.maxPage - lastFullFramePage);
		}
		
		//System.out.printf("getFrameSize: curPage=%d, lastFullFramePage=%d, result=%d\r\n", curPage, lastFullFramePage, result);
		
		return result;
	}
	
	public int getPrevBlock(int curPage)
	{
		int retval;
		
		retval = getStartBlock(curPage) - 1;
		if (retval < 1) retval = 1;
//		this.curPage = retval;
		return retval;
	}
	
	public int getNextBlock()
	{
		int retval;
		retval = getEndBlock() + 1;
		if (retval > this.maxPage) retval = this.maxPage;
	//	this.curPage = retval;
		return retval;
	}
	
	
	public String getPageLink(int curPage)
	{
		String pageLink = "";
		
		pageLink += "type=";
		pageLink += "&page=" + curPage;
		pageLink += "&kind=" + "T";
		pageLink += "&key="	 + "";
		pageLink += "&sc="	 + "pDate";
		pageLink += "&so="	 + "asc";
		
		return pageLink;
	}
	
	public String getViewLink(int curPage, int line)
	{
		String viewLink = "";

		viewLink += "type=";
		viewLink += "&page=" + curPage;
		viewLink += "&kind=" + "T";
		viewLink += "&key="	 + "";
		viewLink += "&sc="	 + "pDate";
		viewLink += "&so="	 + "asc";
		viewLink += "&no="	 + Integer.toString(getListIndex(curPage) - line); // 첫번째 행에 들어갈 list의 index
		
		return viewLink;
	}
	
}
