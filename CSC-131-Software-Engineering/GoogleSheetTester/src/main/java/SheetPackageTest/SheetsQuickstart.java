package SheetPackageTest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
 
public class SheetsQuickstart {
    
	private static final String APPLICATION_NAME = "CSC131";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials//sheets.googleapis.com-java-quickstart.json");
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList( SheetsScopes.SPREADSHEETS );
    private static String spreadsheetId = "1HEkPX-wEowUAOSH3rAzwLOndnAMZ_WsCkxR_aonbyu8";
   
 static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    

    public static Credential authorize() throws IOException {
        InputStream in = new FileInputStream("C:\\Users\\p-chandra\\Documents\\Workspace\\GoogleSheetTester\\src\\main\\resources\\client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        //System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    } 

    //Date
    public static String getDate() throws IOException{
    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
        Date date = new Date();  
        return formatter.format(date);
    }
    
    //Random
    public static String randomKey() throws IOException{
    	Random rand = new Random();
    	int randomKey = rand.nextInt(10000-1000) + 1000;
    	return "" + randomKey;
    }
    
    //Student Comment
    public static boolean inputComment(String Comment, String stuID)throws IOException{
    	Sheets service = getSheetsService();
    	final String range = "'"+getSheetName()+"'!C2:C26";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        int cellRow = 0;
        for (List row : element) {
        	cellRow++;
        	if(stuID.equals(row.get(0))){
        		String addRange = "'"+getSheetName()+"'!F"+Integer.toString(cellRow+1) +":P"+Integer.toString(cellRow+1);
        		System.out.println("Your ID is in row: " +(cellRow) + " col: " + (getCol()+5));
        		if(!checkCommentcell(addRange)) {
        			printInfo(cellRow,getCol()-1,Comment);
        			return true;
        		}
        		break;
        	}			
        } 
        return false;
    }

    //checks if comment cell is empty
    public static boolean checkCommentcell(String range) throws IOException {
    	Sheets service = getSheetsService();
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();    
        if(element == null || element.isEmpty()) {
        	System.out.println("The whole row is empty");
        }else {
        	for (List row : element) {
        		System.out.println(row.get(0));
        		if(row.size() == getCol()) {
        			return true;
        		}
        	}
        }
        return false;
    }
       
    
    //check student ID exists
    public static boolean checkStuID(String ID)throws IOException{
    	Sheets service = getSheetsService();
    	final String range = "'"+getSheetName()+"'!C2:C26";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        if(element == null || element.isEmpty())
            System.out.println("Flag CHECK ID.");
        else{
        	for (List row : element)
        		if (ID.equals(row.get(0)))
        			return true;
        }
        return false;
    }
    
    
    //check if key matches student input
    public static boolean checkKey(String Key)throws IOException{
    	Sheets service = getSheetsService();
    	final String range = "'"+getSheetName()+"'!F32:P32";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        System.out.println(getCol());
        if(element == null || element.isEmpty())
            System.out.println("Flag CHECK KEY.");
        else{
        	for (List row : element)
        		if (Key.equals(row.get(getCol()-1)))
        			return true;
        }
        return false;
    }
    
    
    //get the column number of date
    public static int getCol() throws IOException{
    	Sheets service = getSheetsService();
    	final String range = "'"+getSheetName()+"'!F1:P1";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        if(element == null)
        	return 0;
        else
        	for(List row : element)
        		return row.size();     
        return 0;
    }
    
    
    //enter key and date to the sheet
    //Min's code without timer
    public static String inputKeyDate(String randKey)throws IOException, NullPointerException{
    	Sheets service = getSheetsService();
    	final String range = "'"+getSheetName()+"'!F:P";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        if(element == null) {
        	printInfo(31, 0,randKey);
        	printInfo(0, 0,getDate());
        	return randKey;
        }else {
        	int i = element.get(0).size();
        	
        	if(element.get(0).get(i-1).equals(getDate())) {
        		System.out.println("The dates are same"); 
        		return (String) element.get(31).get(i-1);		
        	}else { 
        		printInfo(31, getCol(),randKey); 
        		printInfo(0, getCol(),getDate());
	        	return randKey;
        	}
        }		
    }
     
    
    //prints data to the sheet (comment date key)
    public static void printInfo(int row, int col, String info)  throws IOException{
    	Sheets service = getSheetsService();
    	List<Request> requests = new ArrayList<>(); 
    	List<CellData> values = new ArrayList<>();
 		values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue((info))));
    	requests.add(new Request()
    		.setUpdateCells(new UpdateCellsRequest()
            .setStart(new GridCoordinate().setSheetId(Integer.parseInt(getSheetID())).setRowIndex(row).setColumnIndex(col+5))
            .setRows(Arrays.asList(new RowData().setValues(values)))
            .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));     
    	BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
    	service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
    }
    
    
    //prints data to the SHA tab (sheetName sheetID)
    public static void printSHA(int id, int row, int col, String info)  throws IOException{
    	Sheets service = getSheetsService();
    	List<Request> requests = new ArrayList<>(); 
    	List<CellData> values = new ArrayList<>();
 		values.add(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue((info))));
    	requests.add(new Request()
    		.setUpdateCells(new UpdateCellsRequest()
            .setStart(new GridCoordinate().setSheetId(id).setRowIndex(row).setColumnIndex(col))
            .setRows(Arrays.asList(new RowData().setValues(values)))
            .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));     
    	BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
    	service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
    }  

    
    //get sheedID and sheetName from teacher (course.jsp)
    public static void setSheetID(String sheetName,int sheetID) throws IOException {
    	printSHA(681712685,4,1,Integer.toString(sheetID));
    	printSHA(681712685,5,1,sheetName);
    }
    
    
    //get sheetID from the SHA tab
    public static String getSheetID() throws IOException{
    	Sheets service = getSheetsService();
    	final String range = "'SHA'!B5";
    	String temp = "";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        if(element == null) {
            System.out.println("Empty Cell");
        }else{
        	for (List row : element) {
        		//System.out.println(row.get(0));
        		temp = (String) row.get(0);
        	}
        	return temp;
        }
    	return null;	
    }
    
    
    //gets sheetName from SHA tab
    public static String getSheetName() throws IOException{
    	Sheets service = getSheetsService();
    	final String range = "'SHA'!B6";
    	String temp = "";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> element = response.getValues();
        if(element == null) {
            System.out.println("Empty Cell");
        }else{
        	for (List row : element) {
        		//System.out.println(row.get(0));
        		temp = (String) row.get(0);
        	}
        	return temp;
        }
    	return range;
    }
    
    // Min's checkLogin for the teacher
    public static boolean checkLogin(String uname, String pword)throws IOException{
    	String dbName = "";
    	String dbWord = "";
    	String sheetName = "SHA";
    	Sheets service = getSheetsService();
    	final String range = "'" + sheetName + "'!A3:B3";
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        //2d list
        List<List<Object>> element = response.getValues();
    	try {
    		dbName = (String)element.get(0).get(0);
    		dbWord = (String)element.get(0).get(1);
    	}
    	catch(Exception e) {
    		System.out.println("missing username or password, register first");
    		return false;
    	}
    	
    	if(uname.equals(dbName) && makeHash256(pword).equals(dbWord)){
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }
    
    //Min's make hash
    public static String makeHash256(String str) {
    	//Hash the string to bytes
    	MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
    	
    	//Byte to hex converter to get the hashed value in hexadecimal
    	StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    //Min's Time Limit
    public static String timeLimit(int x) throws IOException{
    	//returns a time m minutes in the future
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
    	Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, x);
        return formatter.format(calendar.getTime());
    }
    
    public static void main(String[] args) throws IOException {
    	inputKeyDate("1234");
    }
}

