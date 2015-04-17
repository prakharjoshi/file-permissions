import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
public class Client {
	public static void main (String [] args ) throws IOException, InterruptedException {
		Socket socket = new Socket("localhost",15123);
		Connection c = null;
		Statement stmt=null;
		/*try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
			.getConnection("jdbc:postgresql://localhost:5432/OS",
				"postgres", "postgres");
			stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,0);
			System.out.println("Database connected");
		*/
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			Thread.sleep(3);
			String temp = in.readLine();
			System.out.println(temp);
			if(temp=="IP not authorized."){
				socket.close();
			}
			else{
				@SuppressWarnings("resource")
				Scanner s = new Scanner(System.in);
				String username = s.nextLine();
				out.println(username);
				temp = in.readLine();
				System.out.println(temp);
				String password = s.nextLine();
				out.println(password);
				if(temp == "Username and password don't match"){
					System.out.println("Invalid credentials.");
					socket.close();
				}
				else{
					temp = in.readLine();
					System.out.println(temp);
					if(temp.startsWith("GetTheFile ")){
						//System.out.println("2222222222222222222");
						File dirs = new File(".");
						String destPath = dirs.getCanonicalPath() + File.separator + "download";
			    		String[] words = temp.split(" ",2);
			    		FileTransfer.Receive(destPath, words[1]);
					}
					File file = new File("/home/valayism/workspace/Firewall/src/download.txt");
					@SuppressWarnings("resource")
					Scanner fileScanner = new Scanner(file);
					List<String> list=new ArrayList<String>();
					while(fileScanner.hasNextLine()){
						list.add(fileScanner.nextLine());
					}
					String findError = list.toString();
					System.out.println(findError);
					//ResultSet rs1 = stmt.executeQuery(" Select * from Virus");
					//System.out.println(rs1.toString());
					if(findError.contains("pppppppp")){
						file.setExecutable(false);
						file.setReadable(true);
						file.setWritable(false);
					}
				}
				socket.close();
			}}
		 
		}
		
		
        //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
