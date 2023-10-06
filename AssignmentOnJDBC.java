package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

public class AssignmentOnJDBC {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Practice","root","password");
			while(true) {
				System.out.println("1.Add Employee record");
				System.out.println("2.Display the employee record");
				System.out.println("3.Update the salary");
				System.out.println("4.Delete the Employee record");
				System.out.println("5.Exit");
				System.out.println("Enter choice 1-5");
				int choice=sc.nextInt();
				switch(choice) {
				case 1:
					System.out.println("Employee ID:");
					long ID=sc.nextLong();
					System.out.println("First Name:");
					String fname=sc.next();
					System.out.println("Last Name:");
					String lname=sc.next();
					System.out.println("Email:");
					String email=sc.next();
					System.out.println("Salary:");
					double salary=sc.nextDouble();
					PreparedStatement psmt=con.prepareStatement("insert into Employee values(?,?,?,?,?)");
					psmt.setLong(1,ID);
					psmt.setString(2,fname);
					psmt.setString(3,lname);
					psmt.setString(4, email);
					psmt.setDouble(5, salary);
					try {
						boolean res=psmt.execute();	
					} 
					catch(SQLIntegrityConstraintViolationException e) {
						System.out.println("Employee ID already exists");
						e.printStackTrace();
					}
					break;
				case 2:
					System.out.println("Employee ID:");
					long id=sc.nextLong();
					PreparedStatement smt=con.prepareStatement("select * from Employee where id=?");
					smt.setLong(1,id);
					ResultSet rst=smt.executeQuery();
					if(rst.next()) {
						System.out.println(rst.getLong(1)+" "+rst.getString(2)+" "+rst.getString(3)+" "+rst.getString(4)+
								" "+rst.getDouble(5));
					}
					else System.out.println("Employee not found");
					break;
				case 3:
					System.out.println("Employee ID");
					long eid=sc.nextLong();
					PreparedStatement pst=con.prepareStatement("Update Employee set salary=? where id=?");
					pst.setLong(2,eid);
					System.out.println("Enter salary:");
					double sal=sc.nextDouble();
					pst.setDouble(1,sal);
					int res1=pst.executeUpdate();
					if(res1==1) System.out.println("Salary Updated Successfully");
					else System.out.println("Employee not found");
					break;
				case 4:
					System.out.println("Employee ID");
					long empid=sc.nextLong();
					PreparedStatement pst1=con.prepareStatement("delete from employee where id=?");
					pst1.setLong(1,empid);
					int r=pst1.executeUpdate();
					if(r==1) System.out.println("Employee details are deleted");
					else System.out.println("Employee not found");

					break;
				case 5:
					System.exit(0);
					break;
				default:
					System.out.println("Invalid option");
					break;
				}
			}
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		

	}

}
