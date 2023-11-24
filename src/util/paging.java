package util;

import vo.*;

public class paging
{
	// 페이징을 위한 변수 필드
	int total		= 0;	// 페이징을 해야 할 게시물의 전체 개수 -> 게시판이 선택이 되어있을수 있음
	int maxPage		= 0;	// 전체 개수로 계산된 페이지의 최대 숫자
	
	int startBlock	= 0; // 시작 블럭 번호
	int endBlock	= 0; // 끝 블럭 번호
	
	int curPage		= 1 ; // 현재 페이지 번호
	
	public static final int perPage  = 15; // 페이지 당 표시할 게시물 개수
	public static final int maxBlock = 10; // 페이지 블럭 표시 개수
	
	SearchVo search = null;
	
	// 생성자
	public paging( SearchVo search ) { this.search = search; }
	
	// getters & -setters-
	public int getTotal() { return total; }
	public int getMaxPage() { return maxPage; }
	public int getStartBlock() { return startBlock; }
	public int getEndBlock() { return endBlock; }
	public int getCurPage() { return curPage; }
	
	// 앞블럭 링크 생성
	public String getPrevBlock()
	{	// 앞으로 갈 페이지블럭들이 있어야 링크를 반환
		if( this.startBlock > paging.maxBlock )
		{ return search.getPageLink( this.startBlock - 1 ); }
		else{ return null; }
	}
	
	// 뒷블럭 링크 생성
	public String getNextBlock()
	{
		if( this.endBlock < this.maxPage ) 
		{ return search.getPageLink( this.endBlock + 1 ); }
		else{ return null; }
	}
	
	// 페이지 블럭 번호로 링크 얻는 메소드
	public String getPage( int page )
	{
		return search.getPageLink( page );
	}
	
	// 이 클래스를 셋팅하는 메소드
	public void setTotal( int total, int curPage)
	{
		this.total = total;
		this.curPage = curPage;
		// 전체 게시물 개수를 바탕으로 최대 페이지 번호를 계산한다
		this.maxPage = ( total - 1 ) / paging.perPage + 1;
		if( this.curPage > this.maxPage )
		{	// 입력받은 페이지 번호가 최대 페이지 번호보다 크면 수정한다
			this.curPage = this.maxPage;
		}
		
		// 시작 블럭번호와, 끝 블럭 번호를 계산한다
		this.startBlock = (( this.curPage -1 ) / paging.maxBlock ) * paging.maxBlock + 1;
		this.endBlock = this.startBlock + paging.maxBlock - 1;
		if( this.endBlock > this.maxPage )
		{	// 계산된 끝 블럭 번호가 최대 페이지 번호 보다 크면 수정한다
			this.endBlock = this.maxPage;
		}
	}
}
