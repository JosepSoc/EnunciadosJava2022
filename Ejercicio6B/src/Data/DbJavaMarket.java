package Data;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import entities.*;

public class DbJavaMarket {
	
	Connection conn=null;
	public void registerConector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void openConn() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/javaMarket", "root", "josu0207");
		}
		catch(SQLException ex){
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}
	}
	
	public void closeConn(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if(rs!=null) {rs.close();}
			if(stmt!=null) {stmt.close();}
			conn.close();
		} catch(SQLException ex){
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}

	}
	

	@SuppressWarnings("finally")
	public LinkedList<Product> recoverAllDB() {
		LinkedList<Product> products= new LinkedList<>();
		Statement stmt= null;
		ResultSet rs= null;
		
		try {
			this.openConn();
			
			stmt = conn.createStatement();
			rs= stmt.executeQuery("select * from product");
			
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

		
		
		} catch (SQLException ex) {
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			closeConn(rs, stmt, conn);
			return products;
		}

		
	}
	
	

	@SuppressWarnings("finally")
	public Product recoverByParameter(Product tProduct) {
		Product p=new Product();
		ResultSet rs=null;
		PreparedStatement stmt=null;
		
		try {
			this.openConn();
			
			stmt = conn.prepareStatement("select * from product where id=?");
			stmt.setInt(1, tProduct.getId());
			rs= stmt.executeQuery();
			
			if(rs.next()) {
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getDouble("price"));
				p.setStock(rs.getInt("stock"));
				p.setShippingIncluded(rs.getBoolean("shippingIncluded"));
				p.setEnabledOnDate(rs.getObject("enabledOnDate", LocalDateTime.class));
			}
			
			
			
			
			
		} catch (SQLException ex) 
		{
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			closeConn(rs, stmt, conn);
			return p;
		}
		
	}	
	
	@SuppressWarnings("finally")
	public Product intoDB(Product productemp) {
		Product pIns=new Product();
		ResultSet keyResultSet=null;
		PreparedStatement pstmt=null;
		
		pIns=productemp;
		
		try {
			this.openConn();
			pstmt = conn.prepareStatement(
					"insert into product(name, description, price, stock, shippingIncluded, enabledOnDate) values (?,?,?,?,?,?)"
					,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, pIns.getName());
			pstmt.setString(2, pIns.getDescription());
			pstmt.setDouble(3, pIns.getPrice());
			pstmt.setInt(4, pIns.getStock());
			pstmt.setBoolean(5, pIns.isShippingIncluded());
			pstmt.setObject(6, pIns.getEnabledOnDate());
			
			pstmt.executeUpdate();
			
			//Pedir las claves generadas para recuperar el id autoincremental
			keyResultSet=pstmt.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()) {productemp.setId(keyResultSet.getInt(1));}

			
		} catch (SQLException ex) 
		{
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			closeConn(keyResultSet, pstmt, conn);
			return productemp;
			
		}
		
		
	}
	
	public void deletefDB(Product pTemp) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		
		try {
			this.openConn();
			stmt = conn.prepareStatement("delete from product where id=?");
			stmt.setInt(1, pTemp.getId());
			stmt.executeUpdate();
			
			
		} catch (SQLException ex) 
		{
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
			
		} finally {
			closeConn(rs, stmt, conn);
		}
	}
	
	public void updatefDB(Product pTemp) {
		ResultSet rs=null;
		PreparedStatement pStmt=null;
		
		
		try {
			this.openConn();
			
			
			pStmt=conn.prepareStatement("UPDATE product SET name=?, description=?, price=?, stock=?, shippingIncluded=? WHERE id=?");
			
			pStmt.setString(1,pTemp.getName());
			pStmt.setString(2, pTemp.getDescription());
			pStmt.setDouble(3, pTemp.getPrice());
			pStmt.setInt(4, pTemp.getStock());
			pStmt.setBoolean(5, pTemp.isShippingIncluded());
			pStmt.setInt(6, pTemp.getId());
			
			pStmt.executeUpdate();
	
			
			
		} catch (SQLException ex) 
		{
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		} finally {
			closeConn(rs, pStmt, conn);
		}
	}
	
	
	
}
 