package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entidades.Vendedor;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VendedorDao vendedorDao = DaoFactory.createVendedorDao();
		Vendedor vendedor = vendedorDao.findById(3);
		
		
		
		
		System.out.println(vendedor);

	}

}
