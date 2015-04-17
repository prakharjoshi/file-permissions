import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Server {
	public static void main (String [] args ) throws IOException {
		System.out.println("Waiting for someone to connect..");
		Connection c = null;
		Statement stmt=null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
			.getConnection("jdbc:postgresql://localhost:5432/postgres",
				"postgres", "postgres");
			stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,0);
			System.out.println("Database connected");
			@SuppressWarnings("resource")
			Socket socket = new ServerSocket(15123).accept();
			String ipAddress = socket.getRemoteSocketAddress().toString();
			String[] ip=ipAddress.split(":");
			String[] ip1= ip[0].split("/");
			ipAddress=ip1[1];
			System.out.println(ipAddress);
			//String ipaddr = "127.0.0.1";
			ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE ipaddress='"+ipAddress+"';");
			if(rs.first()){
				System.out.println("Accepted connection : " + socket);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Enter username:");
				File transferFile = new File ("/home/valayism/workspace/Firewall/src/document.txt");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String username = in.readLine();
				System.out.println(username);
				out.println("Enter password:");
                                //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String password = in.readLine();
				System.out.println(password);
				String usrnm = "valayism";
				String psswd = "destiny07";
				ResultSet rs1 = stmt.executeQuery(" Select * from login where username='"+username+"' AND password='"+password+"'");
				if(rs1.first()){
					String temp = FileTransfer.Send("/home/valayism/workspace/Firewall/src/document.txt");
					System.out.println(temp);
		    		temp = "GetTheFile " + temp;
		            out.println(temp);
				}
				else{
					out.println("Username and password don't match");
					System.out.println("Invalid username and password");
				}
			}
			else{
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("IP not authorised.");
				System.out.println("ip not authorized");
				socket.close();
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		
	}
}