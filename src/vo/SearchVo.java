package vo;

import javax.servlet.http.HttpServletRequest;

public class SearchVo
{
	// 페이징을 위한 필드
	private String type;		// 게시판 종류
	private int	   curPage;		// 현재 페이지 번호
	// 검색을 위한 필드
	private String kind;		// 검색조건 :: 제목:T(title::pTitle) / 내용:C(content::pContent) / 제목+내용:A(all)
	private String keyword;
	// 정렬을 위한 필드
	private String sortCol;		// 정렬 대상 컬럼 이름
	private String sortOrder;	// 정렬 방식 :: 오름차순 / 내림차순 
	
	private String link = "";
	
	// 생성자 : 기본값 셋팅
	public SearchVo()
	{
		this.setType(null);
		this.setCurPage(null);
		this.setKind(null);
		this.setKeyword(null);
		this.setSortCol(null);
		this.setSortOrder(null);
	}
	
	// request를 직접 받아서 필드를 셋팅하는 생성자
	public SearchVo( HttpServletRequest request )
	{
		this();	// 기본 생성자 호출

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
		case "IN": result = "숙소"; break;
		case "RE": result = "맛집"; break;
		case "HP": result = "핫플"; break;
		case "FR": result = "자유"; break;
		default :  result = "자유"; break;
		}
		return result;
	}
	
	public void setType(String type)
	{	// 게시판 종류
		if( type == null || type.equals("") ) { this.type = "IN"; }
		else { this.type = type; }
	}
	public void setCurPage(int curPage)
	{	// 현제 페이지 번호 셋팅
		if( curPage < 1 ) { this.curPage = 1; }
		else { this.curPage = curPage; }
	}
	public void setCurPage(String curPage)
	{	// 페이지 번호가 문자열로 들어올 경우
		try { this.setCurPage(Integer.parseInt(curPage)); }
		catch(Exception e) { this.curPage = 1; }
	}
	public void setKind(String kind)
	{	
		if( kind == null || kind.contentEquals("") ) { this.kind = "T"; }		// 기본 검색 조건은 제목
		else { this.kind = kind; }
	}
	public void setKeyword(String keyword)
	{	// 검색 단어
		if( keyword == null ) { this.keyword = ""; }
		else { this.keyword = keyword; }
	}
	public void setSortCol(String sortCol)
	{	// 정렬 대상 컬럼 이름  pDate:작성일 / pCnt:조회수
		if( sortCol == null || sortCol.equals("") ) { this.sortCol = "pDate"; }
		else { this.sortCol = sortCol; }
	}
	public void setSortOrder(String sortOrder)
	{	// 정렬 방식 :: 내림차순 오름차순
		if( sortOrder == null || sortOrder.contentEquals("") ) { this.sortOrder = "desc"; }
		else { this.sortOrder = sortOrder; }
	}

	// link를 제공하는 메소드 / 3종류 :: View / Page 정수/문자
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
	
	// 정렬용 sql 구문의 'order by ...'을 생성해주는 메소드
	public String getOrder() 
	{
		return " order by  " + this.getSortCol() + " " + this.getSortOrder() + " ";
	}
	public String getOrder(String tableName) 
	{
		return " order by " + tableName + "." + this.getSortCol() + " " + this.getSortOrder() + " ";
	}
	
	// 정렬용 sql 구문의 'order by ...'을 생성해주는 메소드
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
