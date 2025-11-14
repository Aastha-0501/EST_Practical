package pblj;
import java.sql.*;
import java.util.*;

public class ProductCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb"; 
        String user = "root"; 
        String password = "Aastha@12345"; 
        
        try (Connection con = DriverManager.getConnection(url, user, password);
             Scanner sc = new Scanner(System.in)) {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con.setAutoCommit(false);
            
            while (true) {
                System.out.println("\n===== PRODUCT MENU =====");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {
                    case 1:
                        System.out.print("Enter Product ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Product Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        PreparedStatement ps1 = con.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?)");
                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setDouble(3, price);
                        ps1.setInt(4, qty);
                        ps1.executeUpdate();
                        con.commit();
                        System.out.println("Product Added Successfully!");
                        break;

                    case 2:
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                        System.out.println("\nProductID | ProductName | Price | Quantity");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getDouble(3) + " | " + rs.getInt(4));
                        }
                        break;

                    case 3:
                        System.out.print("Enter Product ID to Update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Price: ");
                        double newPrice = sc.nextDouble();
                        System.out.print("Enter New Quantity: ");
                        int newQty = sc.nextInt();

                        PreparedStatement ps2 = con.prepareStatement("UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?");
                        ps2.setDouble(1, newPrice);
                        ps2.setInt(2, newQty);
                        ps2.setInt(3, uid);
                        int rowsUpdated = ps2.executeUpdate();
                        if (rowsUpdated > 0) {
                            con.commit();
                            System.out.println("Product Updated Successfully!");
                        } else {
                            con.rollback();
                            System.out.println("Product ID Not Found!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Product ID to Delete: ");
                        int did = sc.nextInt();

                        PreparedStatement ps3 = con.prepareStatement("DELETE FROM Product WHERE ProductID=?");
                        ps3.setInt(1, did);
                        int rowsDeleted = ps3.executeUpdate();
                        if (rowsDeleted > 0) {
                            con.commit();
                            System.out.println("Product Deleted Successfully!");
                        } else {
                            con.rollback();
                            System.out.println("Product ID Not Found!");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting Program...");
                        con.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
