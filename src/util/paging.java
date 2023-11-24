package util;

import vo.*;

public class paging
{
	// ����¡�� ���� ���� �ʵ�
	int total		= 0;	// ����¡�� �ؾ� �� �Խù��� ��ü ���� -> �Խ����� ������ �Ǿ������� ����
	int maxPage		= 0;	// ��ü ������ ���� �������� �ִ� ����
	
	int startBlock	= 0; // ���� �� ��ȣ
	int endBlock	= 0; // �� �� ��ȣ
	
	int curPage		= 1 ; // ���� ������ ��ȣ
	
	public static final int perPage  = 15; // ������ �� ǥ���� �Խù� ����
	public static final int maxBlock = 10; // ������ �� ǥ�� ����
	
	SearchVo search = null;
	
	// ������
	public paging( SearchVo search ) { this.search = search; }
	
	// getters & -setters-
	public int getTotal() { return total; }
	public int getMaxPage() { return maxPage; }
	public int getStartBlock() { return startBlock; }
	public int getEndBlock() { return endBlock; }
	public int getCurPage() { return curPage; }
	
	// �պ� ��ũ ����
	public String getPrevBlock()
	{	// ������ �� ������������ �־�� ��ũ�� ��ȯ
		if( this.startBlock > paging.maxBlock )
		{ return search.getPageLink( this.startBlock - 1 ); }
		else{ return null; }
	}
	
	// �޺� ��ũ ����
	public String getNextBlock()
	{
		if( this.endBlock < this.maxPage ) 
		{ return search.getPageLink( this.endBlock + 1 ); }
		else{ return null; }
	}
	
	// ������ �� ��ȣ�� ��ũ ��� �޼ҵ�
	public String getPage( int page )
	{
		return search.getPageLink( page );
	}
	
	// �� Ŭ������ �����ϴ� �޼ҵ�
	public void setTotal( int total, int curPage)
	{
		this.total = total;
		this.curPage = curPage;
		// ��ü �Խù� ������ �������� �ִ� ������ ��ȣ�� ����Ѵ�
		this.maxPage = ( total - 1 ) / paging.perPage + 1;
		if( this.curPage > this.maxPage )
		{	// �Է¹��� ������ ��ȣ�� �ִ� ������ ��ȣ���� ũ�� �����Ѵ�
			this.curPage = this.maxPage;
		}
		
		// ���� ����ȣ��, �� �� ��ȣ�� ����Ѵ�
		this.startBlock = (( this.curPage -1 ) / paging.maxBlock ) * paging.maxBlock + 1;
		this.endBlock = this.startBlock + paging.maxBlock - 1;
		if( this.endBlock > this.maxPage )
		{	// ���� �� �� ��ȣ�� �ִ� ������ ��ȣ ���� ũ�� �����Ѵ�
			this.endBlock = this.maxPage;
		}
	}
}
