package com.kim.sep151.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/Output")
public class Output extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//기존 방식으로 전송된 파라미터 한글처리
//		request.setCharacterEncoding("EUC-KR");
		//기존 방식으로 전송된 파라미터 읽기
//		String title = request.getParameter("title");
		
		response.setCharacterEncoding("EUC-KR");
		
		//cos.jar 파일로 파일 업로드를 해보자
		//파일이 업로드 될 서버상의 폴더의 절대경로
		String path = request.getServletContext().getRealPath("img");
		System.out.println(path);//절대 경로의 위치
		
		//파일 업로드 Multipartrequest 는 cos.jar 에들어있는기능 
		//괄호안의 request 는 위 post, path는 경로, 허용할최대파일크기, 인코딩방식, 중복시에 파일명뒤에 숫자붙이게처리
		MultipartRequest mr = new MultipartRequest(request, path,10*1024*1024,"EUC-KR",new DefaultFileRenamePolicy());
		
		String title = mr.getParameter("title");
		
		//중복처리된 업로드 한 파일명
		String fileName = mr.getFilesystemName("photo");
		//DB에 파일명 저장할때 컬럼의 용량을 상당히 여유있게 만들어야한다. ex)f_name varchar2(200 char)
		fileName= URLEncoder.encode(fileName,"EUC-KR");
		
		//한번더
		String fileName2 = mr.getFilesystemName("etc");
		fileName2= URLEncoder.encode(fileName2,"EUC-KR");
		fileName2= fileName2.replace("+"," ");
		
		PrintWriter out = response.getWriter();
		out.print("<html>");
		out.print("<head><meta charset='EUC-KR'></head>");
		out.print("<body>");
		
		out.printf("<h1>제목 : %s</h1>",title);
		out.printf("<h1>파일명 : %s</h1>",fileName);
		out.printf("<img src='img/%s'>",fileName);
		out.printf("<h1>%s</h1>",fileName2);
		out.printf("<a href='img/%s'>다운</a>",fileName2);
				
				
		out.print("</body>");
		out.print("</html>");
		
	}

}
