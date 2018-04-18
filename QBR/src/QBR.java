import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QBR {
	public static void readFromWeb(String webURL) throws IOException {
        
		URL url = new URL(webURL);
        InputStream is =  url.openStream();
        
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String res = "";
            while ((line = br.readLine()) != null) {
                res = res + line;
            }
            
            Scanner search = new Scanner(res).useDelimiter("<|>");
            ArrayList<String> nflData = new ArrayList<String>();
            ArrayList<String> qbNames = new ArrayList<String>();
            List<Integer> Completions = new ArrayList<Integer>();
            List<Integer> Attempts = new ArrayList<Integer>();
            List<Integer> Yards = new ArrayList<Integer>();
            List<Integer> TDs = new ArrayList<Integer>();
            List<Integer> Ints = new ArrayList<Integer>();
            List<Integer> Fumbles = new ArrayList<Integer>();
            
            int arrLength = 0;
            while (search.hasNext()) {
            	String current = search.next();
            	nflData.add(current);
            	//System.out.println(current);
            	arrLength++;
            }
            search.close();
             
            int aLength = 0;
            for (int i = 0; i < arrLength; i++) {
            	
            	if (nflData.get(i).equals("td class=\"nameCell\"")) {
            		aLength++;
            		qbNames.add(nflData.get(i+3));
            		
            		int temp = Integer.parseInt(nflData.get(i+37));
            		Completions.add(temp);
            		
            		temp = Integer.parseInt(nflData.get(i+41));
            		Attempts.add(temp);
            		
            		temp = Integer.parseInt(nflData.get(i+45));
            		Yards.add(temp);
            		
            		temp = Integer.parseInt(nflData.get(i+49));
            		TDs.add(temp);
            		
            		temp = Integer.parseInt(nflData.get(i+53));
            		Ints.add(temp);
            		
            		temp = Integer.parseInt(nflData.get(i+61));
            		Fumbles.add(temp);
            		
               	}
            }
            
            
            /*
            System.out.println(qbNames);
            System.out.println(Completions);
            System.out.println(Attempts);
            System.out.println(Yards);
            System.out.println(TDs);
            System.out.println(Ints);
            System.out.println(Fumbles);
            */
            
            List<Integer> QBRS = new ArrayList<Integer>();
            
            for(int i = 0; i < aLength; i++) {
            	int tdPoints = TDs.get(i) * 30;
            	int intPoints = Ints.get(i) * 10;
            	int yardPoints = Yards.get(i) * 100 / 400;
            	int fumPoints = Fumbles.get(i) * 10;
            	int completionPoints = 0;
            if(Completions.get(i) != 0) {	
            	int x = Completions.get(i) * 100 / Attempts.get(i);
            	if(Attempts.get(i) < 5) {
            		completionPoints = Completions.get(i);
            	}else if(Attempts.get(i) < 10) {
            		completionPoints = x / 6;
            	}else if(Attempts.get(i) < 15) {
            		completionPoints = x / 5;
            	}else if(Attempts.get(i) < 20) {
            		completionPoints = x / 4;
            	}else if(Attempts.get(i) < 25) {
            		completionPoints = x / 3;
            	}else if(Attempts.get(i) < 30) {
            		completionPoints = x / 2;
            	}else if(Attempts.get(i) < 35) {
            		completionPoints = x / 3 * 2;
            	}else if(Attempts.get(i) < 40) {
            		completionPoints = x;
            	}else{
            		completionPoints = x * 3 / 2;
            	}
            }	
         		int qbr = tdPoints + yardPoints + completionPoints;
         		qbr -= intPoints;
         		qbr -= fumPoints; 
            	QBRS.add(qbr);
            }
           
           bubbleSort(QBRS, qbNames);
            
           for(int i = 0; i < aLength; i++) {
        	   System.out.print(qbNames.get(i));
        	   System.out.print(": ");
        	   System.out.println(QBRS.get(i));
           }
           
            
        
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }

    }
    public static void main(String[] args) throws IOException {
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.println("What week?");
    	String week = sc.nextLine();
    	
        String url = "http://www.nfl.com/stats/weeklyleaders?week=" + week + "&season=2017&showCategory=Passing";
        readFromWeb(url);
    }
    
    public static void bubbleSort(List<Integer> qBRS, List<String> qbNames) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < qBRS.size() - j; i++) {
                if (qBRS.get(i) < qBRS.get(i+1)) {
                    tmp = qBRS.get(i);
                    int temp = qBRS.get(i+1);
                    qBRS.set(i, temp);
                    qBRS.set(i+1, tmp);
                    swapped = true;
                    
                    String temporary = qbNames.get(i);
                    String ha = qbNames.get(i+1);
                    qbNames.set(i, ha);
                    qbNames.set(i+1, temporary);
                }
            }
        }
    }
    
    
    
}