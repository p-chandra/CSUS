package net.codejava.servlet;
 
import java.io.IOException;
import java.util.Random;
import java.io.PrintWriter;
import SheetPackageTest.SheetsQuickstart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.sheets.v4.Sheets;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    


public class QuickServlet extends HttpServlet {
    /**
     * this life-cycle method is invoked when this servlet is first accessed
     * by the client
     */

    public void init(ServletConfig config) {
        System.out.println("Servlet is being initialized");
    }
 
    /**
     * handles HTTP GET request
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

       PrintWriter writer = response.getWriter();
       // writer.println("<html>Welcome to Attendance Tracker</html>");
    //	writer.flush();  		
    }
    
    /**
     * handles HTTP POST request
     * @throws ServletException 
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException, NullPointerException {
    	
    	PrintWriter writer = response.getWriter();
    	String formName = request.getParameter("FormName");
    	RequestDispatcher rd;
 
    	if (formName.equals("StudentEntry")) {	
    		String paramID = request.getParameter("Student ID");
    	    String paramKey = request.getParameter("Key");
    	    String paramComment = request.getParameter("Comment"); 
    	    if (SheetsQuickstart.checkStuID(paramID) && SheetsQuickstart.checkKey(paramKey)) {
    	    	if(paramComment.equals("") || paramComment == null) {       	
    	        	if(!(SheetsQuickstart.inputComment(" X ",paramID))) {
    	        		rd = request.getRequestDispatcher("AttendanceError.html");
                        rd.forward(request, response);
    	        	}else {
    	        	request.setAttribute("stuID", paramID);
        	        request.setAttribute("key", paramKey);
        	        rd = request.getRequestDispatcher("Receipt.jsp");
                    rd.forward(request, response);
    	        	}
    	    	}else {
    	            if(!(SheetsQuickstart.inputComment(paramComment,paramID))) {
    	            	rd = request.getRequestDispatcher("AttendanceError.html");
                        rd.forward(request, response);
        	        }else {
    	            request.setAttribute("stuID", paramID);
        	        request.setAttribute("key", paramKey);
        	        rd = request.getRequestDispatcher("Receipt.jsp");
                    rd.forward(request, response);
        	        }
    	        }   
    	    }else {
    	    	rd = request.getRequestDispatcher("Invalid.html");
                rd.forward(request, response);
    	    }
    	
    	}else if(formName.equals("TeacherLogin")){//Teacher.jsp 
    	    String teacher = request.getParameter("userTeacher");
    	    String password = request.getParameter("passTeacher");
            
    	    if(SheetsQuickstart.checkLogin(teacher, password)) { 	
                rd = request.getRequestDispatcher("Course.jsp");
                rd.forward(request, response); 
            }else {
            	rd = request.getRequestDispatcher("Invalid.html");
                rd.forward(request, response);
	    	} 
    	}else if(formName.equals("CourseSelect")) { //Course.jsp   
    		String course = request.getParameter("myList");    	    	

    		if(course.equals("CffvgbSC20"))
    			SheetsQuickstart.setSheetID("CSC20",1996570317);
    		else if(course.equals("CSC130"))
    			SheetsQuickstart.setSheetID("CSC130",1472870202);
    		else if(course.equals("CSC131"))	
    			SheetsQuickstart.setSheetID("CSC131",0);
    		else if(course.equals("CSC133"))	
    			SheetsQuickstart.setSheetID("CSC133",756897706);
    		else if(course.equals("CSC135")) 
    			SheetsQuickstart.setSheetID("CSC135",543682871);   			

    		String genKey = (String) SheetsQuickstart.randomKey();    	
    	    String randKey = SheetsQuickstart.inputKeyDate(genKey);
    	    	   	    	
    	    request.setAttribute("randomKey", randKey);
    	    request.getRequestDispatcher("RandKey.jsp").forward(request, response); 
    	}else {
    		writer.println("Key has not been created");
        	writer.flush();
    	}
    }

    /**
     * this life-cycle method is invoked when the application or the server
     * is shutting down
     */
    public void destroy() {
        System.out.println("Servlet is being destroyed");
    }
}