package vo;

import javax.servlet.http.HttpServletRequest;

public class SearchVo
{
	// ����¡�� ���� �ʵ�
	private String type;		// �Խ��� ����
	private int	   curPage;		// ���� ������ ��ȣ
	// �˻��� ���� �ʵ�
	private String kind;		// �˻����� :: ����:T(title::pTitle) / ����:C(content::pContent) / ����+����:A(all)
	private String keyword;
	// ������ ���� �ʵ�
	private String sortCol;		// ���� ��� �÷� �̸�
	private String sortOrder;	// ���� ��� :: �������� / �������� 
	
	private String link = "";
	
	// ������ : �⺻�� ����
	public SearchVo()
	{
		this.setType(null);
		this.setCurPage(null);
		this.setKind(null);
		this.setKeyword(null);
		this.setSortCol(null);
		this.setSortOrder(null);
	}
	
	// request�� ���� �޾Ƽ� �ʵ带 �����ϴ� ������
	public SearchVo( HttpServletRequest request )
	{
		this();	// �⺻ ������ ȣ��

		this.setType(request.getParameter("type"));
		this.setCurPage(request.getParameter("page"));
		this.setKind(request.getParameter("kind"));
		this.setKeyword(request.getParameter("key"));
		this.setSortCol(request.getParameter("sc"));
		this.setSortOrder(request.getParameter("so"));
	}
	
	// setters & getters
	public String getType()		{ return type;		}
	public int getCurPage()		{ return curPage;	}
	public String getKind()		{ return kind;		}
	public String getKeyword()	{ return keyword;	}
	public String getSortCol()	{ return sortCol;	}
	public String getSortOrder(){ return sortOrder;	}
	public String getTypeName()
	{
		String result = "";
		switch(this.type)
		{
		case "IN": result = "����"; break;
		case "RE": result = "����"; break;
		case "HP": result = "����"; break;
		case "FR": result = "����"; break;
		default :  result = "����"; break;
		}
		return result;
	}
	
	public void setType(String type)
	{	// �Խ��� ����
		if( type == null || type.equals("") ) { this.type = "IN"; }
		else { this.type = type; }
	}
	public void setCurPage(int curPage)
	{	// ���� ������ ��ȣ ����
		if( curPage < 1 ) { this.curPage = 1; }
		else { this.curPage = curPage; }
	}
	public void setCurPage(String curPage)
	{	// ������ ��ȣ�� ���ڿ��� ���� ���
		try { this.setCurPage(Integer.parseInt(curPage)); }
		catch(Exception e) { this.curPage = 1; }
	}
	public void setKind(String kind)
	{	
		if( kind == null || kind.contentEquals("") ) { this.kind = "T"; }		// �⺻ �˻� ������ ����
		else { this.kind = kind; }
	}
	public void setKeyword(String keyword)
	{	// �˻� �ܾ�
		if( keyword == null ) { this.keyword = ""; }
		else { this.keyword = keyword; }
	}
	public void setSortCol(String sortCol)
	{	// ���� ��� �÷� �̸�  pDate:�ۼ��� / pCnt:��ȸ��
		if( sortCol == null || sortCol.equals("") ) { this.sortCol = "pDate"; }
		else { this.sortCol = sortCol; }
	}
	public void setSortOrder(String sortOrder)
	{	// ���� ��� :: �������� ��������
		if( sortOrder == null || sortOrder.contentEquals("") ) { this.sortOrder = "desc"; }
		else { this.sortOrder = sortOrder; }
	}

	// link�� �����ϴ� �޼ҵ� / 3���� :: View / Page ����/����
	public String getViewLink( String no )
	{
/*		link  = "type="	 + this.getType();
		link += "&page=" + this.getCurPage();
		link += "&kind=" + this.getKind();
		link += "&key="	 + this.getKeyword() ;
		link += "&sc="	 + this.getSortCol();
		link += "&so="	 + this.getSortOrder();
		link += "&no="	 + no;
		return link; */
		link  = this.getPageLink(this.getCurPage());
		link += "&no="	 + no;
		return link;
	}
	public String getPageLink( int pageNo )
	{
		link  = "type="	 + this.getType();
		link += "&page=" + pageNo;
		link += "&kind=" + this.getKind();
		link += "&key="	 + this.getKeyword();
		link += "&sc="	 + this.getSortCol();
		link += "&so="	 + this.getSortOrder();
		return link;
	}
	public String getPageLink( String pageNo )
	{
		try { return this.getPageLink(Integer.parseInt(pageNo)); }
		catch(Exception e) { return null; }
	}
	
	// ���Ŀ� sql ������ 'order by ...'�� �������ִ� �޼ҵ�
	public String getOrder() 
	{
		return " order by  " + this.getSortCol() + " " + this.getSortOrder() + " ";
	}
	public String getOrder(String tableName) 
	{
		return " order by " + tableName + "." + this.getSortCol() + " " + this.getSortOrder() + " ";
	}
	
	// ���Ŀ� sql ������ 'order by ...'�� �������ִ� �޼ҵ�
	public String getmOrder() 
	{
		return " order by joinDate " ;
	}
	
	public void Print_SearchVo(String msg, int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; ++i) {
			prefix += " ";
		}
		
		System.out.printf("\r\n%s--------------- %s begin ---------------------\r\n", prefix, msg);
		System.out.printf("%sSearchVo.java: type, curPage : [%s] [%s]\r\n", prefix, type, curPage);
		System.out.printf("%sSearchVo.java: kind, keyword : [%s] [%s]\r\n", prefix, kind, keyword);
		System.out.printf("%sSearchVo.java: sortCol, sortOrder : [%s] [%s]\r\n", prefix, sortCol, sortOrder);
		System.out.printf("%sSearchVo.java: link : [%s]\r\n", prefix, link);
		System.out.printf("%s--------------- %s end ---------------------\r\n\r\n", prefix, msg);
	}
}
