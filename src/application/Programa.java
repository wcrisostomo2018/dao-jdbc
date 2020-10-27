package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Departamento obj = new Departamento(1,"Livros");
		
		Vendedor vend = new Vendedor(21,"Bob","bob@gmail.com",new Date(),3000.00,obj);
		
		VendedorDao vendedorDao = DaoFactory.createVendedorDao();
		
		System.out.println(vend);

	}

}
