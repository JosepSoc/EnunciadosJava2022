package entities;

import java.sql.*;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class DbJavaMarket {
	
	public static void registerConector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//recuperar todo
	public static LinkedList<Product> recoverAllDB() {
		Connection conn= null;
		LinkedList<Product> products= new LinkedList<>();
		
		try {
			//creacion conexion
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javaMarket", "root", "josu0207");
			
			//ejecutar la query
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("select * from product");
			
			// resultset a objeto
			while(rs.next()) {
				Product p=new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getDouble("price"));
				p.setStock(rs.getInt("stock"));
				p.setShippingIncluded(rs.getBoolean("shippingIncluded"));
				
				products.add(p);
			}
			
			//cerrar recursos
			if(rs!=null) {rs.close();}
			if(stmt!=null) {stmt.close();}
			conn.close();
			
		
		
		
		
		
		
		
		} catch (SQLException ex) {
			//Manejo de errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
		return products;

		
	}
	
	

	//recuperar por parametro
	public static Product recoverByParameter(int id) {
		Connection conn= null;
		Product p=new Product();
		try {
			//conection
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javaMarket", "root", "josu0207");
			//query
			PreparedStatement stmt = conn.prepareStatement("select * from product where id=?");
			//setear parametros
			stmt.setInt(1, id);
			//ejecutar query
			ResultSet rs= stmt.executeQuery();
			
			//Rs a objeto
			if(rs.next()) {
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getDouble("price"));
				p.setStock(rs.getInt("stock"));
				p.setShippingIncluded(rs.getBoolean("shippingIncluded"));
			}
			
			//cerrar recursos
			if(rs!=null) {rs.close();}
			if(stmt!=null) {stmt.close();}
			
			
			
			
		} catch (SQLException ex) 
		{
			//Manejo de errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
		return p;
	}	
	
	public static void intoDB(Product productemp) {
		Connection conn =null;
		Product pIns=new Product();
		
		pIns=productemp;
		
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost/javaMarket", "root", "josu0207");
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into product(name, description, price, stock, shippingIncluded) values (?,?,?,?,?)"
					,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, pIns.getName());
			pstmt.setString(2, pIns.getDescription());
			pstmt.setDouble(3, pIns.getPrice());
			pstmt.setInt(4, pIns.getStock());
			pstmt.setBoolean(5, pIns.isShippingIncluded());
			
			pstmt.executeUpdate();
			
			//Pedir las claves generadas para recuperar el id autoincremental
			ResultSet keyResultSet=pstmt.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {
				int id=keyResultSet.getInt(1);
				JOptionPane.showMessageDialog(null, "ID for the new generated product: "+id);
				}

			if(keyResultSet!=null) {keyResultSet.close();}
			if(pstmt!=null) {pstmt.close();}
			conn.close();
			
			
					
			
		} catch (SQLException ex) 
		{
			//Manejo de errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
		
		
	}
	
	public static void deletefDB(int id) {
		Connection conn=null;
		int tId=id;
		
		
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost/javaMarket", "root", "josu0207");
			PreparedStatement stmt = conn.prepareStatement("delete from product where id=?");
			stmt.setInt(1, tId);
			stmt.executeUpdate();
			
			if(stmt!=null) {stmt.close();}
			conn.close();
			
			
			
			
		} catch (SQLException ex) 
		{
			//Manejo de errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
	}
	
	public static void updatefDB(Product pTemp) {
		Connection conn=null;
		
		
		try {
			conn= DriverManager.getConnection("jdbc:mysql://localhost/javaMarket", "root", "josu0207");
			
			
			PreparedStatement pStmt=conn.prepareStatement("UPDATE product SET name=?, description=?, price=?, stock=?, shippingIncluded=? WHERE id=?");
			
			pStmt.setString(1,(JOptionPane.showInputDialog("Enter product new name: ")));
			pStmt.setString(2, (JOptionPane.showInputDialog("Enter product new description: ")));
			pStmt.setDouble(3, Double.parseDouble(JOptionPane.showInputDialog("Enter product new price: ")));
			pStmt.setInt(4, Integer.parseInt(JOptionPane.showInputDialog("Enter product new current stock: ")));
			
			int result=JOptionPane.showConfirmDialog(null, "Is shipping included now?", "Updating product!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result==JOptionPane.YES_OPTION) {pStmt.setBoolean(5, true);} else if (result==JOptionPane.NO_OPTION) {pStmt.setBoolean(5, false);}
			pStmt.setInt(6, pTemp.getId());
			
			pStmt.executeUpdate();
			
			
			
			
			
			if(pStmt!=null) {pStmt.close();}
			conn.close();
			
			
			
			
		} catch (SQLException ex) 
		{
			//Manejo de errores
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
	}
	
	
	
}
