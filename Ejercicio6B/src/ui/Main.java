package ui;

import java.time.LocalDateTime;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import entities.*;
import Data.*;

public class Main {
	
	
	public static void listProduct(DbJavaMarket market) {
		JOptionPane.showMessageDialog(null, "Option 1\nProducts list ");
		LinkedList<Product> products = new LinkedList<>();
		products=market.recoverAllDB();
		
		
		for(int i=0;i<products.size();i++) {
			System.out.println("Product n°:"+i);
			System.out.println("ID:"+products.get(i).getId()+" Name:"+products.get(i).getName()+" Description: "+products.get(i).getDescription()+"\n");
		}
	}
	
	public static void searchProduct(DbJavaMarket market) {
		JOptionPane.showMessageDialog(null, "Option 2\nProduct info");
		Product tProduct=new Product();
		String sIncluded;
		
		String stId=JOptionPane.showInputDialog("Enter product ID");
		tProduct.setId(Integer.parseInt(stId));
		tProduct=market.recoverByParameter(tProduct);
		
		if(tProduct.isShippingIncluded()) {sIncluded="Yes";} else {sIncluded="No";}
		
		JOptionPane.showMessageDialog(null, "Product:\nID= "+tProduct.getId()+"\nName: "+tProduct.getName()+
				"\nDescription: "+tProduct.getDescription()+"\nPrice: "+tProduct.getPrice()+"\nStock: "+
				tProduct.getStock()+"\nShippingIncluded: "+sIncluded+"\nEnabled on: "+tProduct.getEnabledOnDate());
				
		
	}
	
	public static void newProduct(DbJavaMarket market) {
		JOptionPane.showMessageDialog(null, "Option 3\nRegister new product");
		boolean flag=true;
		Product pTemp=new Product();
		
		while(flag) {
			pTemp.setName(JOptionPane.showInputDialog("Enter new product name: "));
			pTemp.setDescription(JOptionPane.showInputDialog("Enter new product description: "));
			String price=JOptionPane.showInputDialog("Enter new product price: ");
			pTemp.setPrice(Double.parseDouble(price));
			String stock=JOptionPane.showInputDialog("Enter new product current stock: ");
			pTemp.setStock(Integer.parseInt(stock));
			int result=JOptionPane.showConfirmDialog(null, "Is shipping included?", "New product!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result==JOptionPane.YES_OPTION) {pTemp.setShippingIncluded(true);} else if (result==JOptionPane.NO_OPTION) {pTemp.setShippingIncluded(false);}
			pTemp.setEnabledOnDate(LocalDateTime.now());
			pTemp=market.intoDB(pTemp);
			JOptionPane.showMessageDialog(null, "ID for the new generated product: "+pTemp.getId());
			
			result=JOptionPane.showConfirmDialog(null, "Do yo want to register a new product? ", "JavaMarket", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result==JOptionPane.YES_OPTION) {flag=true;} else if (result==JOptionPane.NO_OPTION) {flag=false;}
			
			
		}
	}
	
	public static void deleteProduct(DbJavaMarket market) {
		JOptionPane.showMessageDialog(null, "Option 4\nDelete product");
		Product pTemp=new Product();
		String sId=JOptionPane.showInputDialog("Enter the ID of the product to be deleted from the DataBase: ");
		pTemp.setId(Integer.parseInt(sId));
		market.deletefDB(pTemp);
		
	}
	
	public static void updateProduct(DbJavaMarket market) {
		JOptionPane.showMessageDialog(null, "Option 5\nUpdate product info");
		String sIncluded;
		Product pTemp=new Product();
		
		String id=JOptionPane.showInputDialog("Enter the ID of the product to be updated: ");
		pTemp.setId(Integer.parseInt(id));
		pTemp=market.recoverByParameter(pTemp);
		
		if(pTemp.isShippingIncluded()) {sIncluded="Yes";} else {sIncluded="No";}
		JOptionPane.showMessageDialog(null, "Product:\nID= "+pTemp.getId()+"\nName= "+pTemp.getName()+
				"\nDescription: "+pTemp.getDescription()+"\nPrice= "+pTemp.getPrice()+"\nStock= "+
				pTemp.getStock()+"\nShippingIncluded= "+sIncluded);
		
		pTemp.setName(JOptionPane.showInputDialog("Enter product new name: "));
		pTemp.setDescription(JOptionPane.showInputDialog("Enter product new description: "));
		pTemp.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Enter product new price: ")));
		pTemp.setStock(Integer.parseInt(JOptionPane.showInputDialog("Enter product new current stock: ")));
		
		int result=JOptionPane.showConfirmDialog(null, "Is shipping included now?", "Updating product!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result==JOptionPane.YES_OPTION) {pTemp.setShippingIncluded(true);} else if (result==JOptionPane.NO_OPTION) {pTemp.setShippingIncluded(false);}
		
		market.updatefDB(pTemp);
	}
	
	public static void menu(DbJavaMarket market) {
		String sOp; 
		int op=3;
		
		
		while(op!=0) {
			sOp=JOptionPane.showInputDialog("Hi\nWhat do you want to do? \n 1-Products list\n2-Product info\n3-Register new product\n4-Delete product\n5-Update product info\n0-Exit menu");
			op=Integer.parseInt(sOp);
			if(op==1) 
			{
				listProduct(market);
			}
			else if(op==2) 
			{
				searchProduct(market);
			}
			else if(op==3) 
			{
				newProduct(market);
			}
			else if(op==4) 
			{
				deleteProduct(market);
			}
			else if(op==5) 
			{
				updateProduct(market);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		DbJavaMarket market=new DbJavaMarket();
		market.registerConector();
		menu(market);
		JOptionPane.showMessageDialog(null, "Closing application...", "EXIT", JOptionPane.INFORMATION_MESSAGE);

	}

}
