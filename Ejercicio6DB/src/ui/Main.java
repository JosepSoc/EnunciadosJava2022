package ui;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import entities.*;

public class Main {
	
	
	public static void listProduct() {
		JOptionPane.showMessageDialog(null, "Option 1\nProducts list ");
		LinkedList<Product> products = new LinkedList<>();
		products=DbJavaMarket.recoverAllDB();
		
		
		for(int i=0;i<products.size();i++) {
			System.out.println("Product n°:"+i);
			System.out.println("ID:"+products.get(i).getId()+" Name:"+products.get(i).getName()+" Description: "+products.get(i).getDescription()+"\n");
		}
	}
	
	public static void searchProduct() {
		JOptionPane.showMessageDialog(null, "Option 2\nProduct info");
		Product tProduct=new Product();
		String sIncluded;
		
		String stId=JOptionPane.showInputDialog("Enter product ID");
		int tId=Integer.parseInt(stId);
		tProduct=DbJavaMarket.recoverByParameter(tId);
		
		if(tProduct.isShippingIncluded()) {sIncluded="Yes";} else {sIncluded="No";}
		
		JOptionPane.showMessageDialog(null, "Product:\nID= "+tProduct.getId()+"\nName= "+tProduct.getName()+
				"\nDescription: "+tProduct.getDescription()+"\nPrice: "+tProduct.getPrice()+"\nStock: "+
				tProduct.getStock()+"\n ShippingIncluded: "+sIncluded);
				
		
	}
	
	public static void newProduct() {
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
			DbJavaMarket.intoDB(pTemp);

			
			result=JOptionPane.showConfirmDialog(null, "Do yo want to register a new product? ", "JavaMarket", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result==JOptionPane.YES_OPTION) {flag=true;} else if (result==JOptionPane.NO_OPTION) {flag=false;}
			
			
		}
	}
	
	public static void deleteProduct() {
		JOptionPane.showMessageDialog(null, "Option 4\nDelete product");
		String sId=JOptionPane.showInputDialog("Enter the ID of the product to be deleted from the DataBase: ");
		int id=Integer.parseInt(sId);
		DbJavaMarket.deletefDB(id);
		
	}
	
	public static void updateProduct() {
		JOptionPane.showMessageDialog(null, "Option 5\nUpdate product info");
		String sIncluded;
		
		String id=JOptionPane.showInputDialog("Enter the ID of the product to be updated: ");
		Product pTemp=DbJavaMarket.recoverByParameter(Integer.parseInt(id));
		
		if(pTemp.isShippingIncluded()) {sIncluded="Yes";} else {sIncluded="No";}
		JOptionPane.showMessageDialog(null, "Product:\nID= "+pTemp.getId()+"\nName= "+pTemp.getName()+
				"\nDescription: "+pTemp.getDescription()+"\nPrice= "+pTemp.getPrice()+"\nStock= "+
				pTemp.getStock()+"\nShippingIncluded= "+sIncluded);
		
		DbJavaMarket.updatefDB(pTemp);
	}
	
	public static void menu() {
		String sOp; 
		int op=3;
		
		
		while(op!=0) {
			sOp=JOptionPane.showInputDialog("Hi\nWhat do you want to do? \n 1-Products list\n2-Product info\n3-Register new product\n4-Delete product\n5-Update product info\n0-Exit menu");
			op=Integer.parseInt(sOp);
			if(op==1) 
			{
				listProduct();
			}
			else if(op==2) 
			{
				searchProduct();
			}
			else if(op==3) 
			{
				newProduct();
			}
			else if(op==4) 
			{
				deleteProduct();
			}
			else if(op==5) 
			{
				updateProduct();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		DbJavaMarket.registerConector();
		menu();
		JOptionPane.showMessageDialog(null, "Closing application...", "EXIT", JOptionPane.INFORMATION_MESSAGE);

	}

}
