package application;


import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		VendedorDao vendedorDao = DaoFactory.createVendedorDao();
		
		System.out.println("=== Teste 1: Vendedor findById ===");		
		Vendedor vendedor = vendedorDao.findById(2);		
		System.out.println(vendedor);
		
		System.out.println("\n=== Teste 2: Vendedor findByDepartment ===");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> list = vendedorDao.findByDepartamento(departamento);
		for(Vendedor obj:list) {
			System.out.println(obj);
		}
		
		
		System.out.println("\n=== Teste 3: Vendedor findAll ===");
		list = vendedorDao.findAll();
		for(Vendedor obj:list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== Teste 4: Vendedor Insert ===");
		Vendedor novoVendedor = new Vendedor(null,"Willian","wbento2010@gmail.com",new Date(),4000.00,departamento);
		vendedorDao.insert(novoVendedor);
		System.out.println("Vendedor Inserido! Id: " + novoVendedor.getId());
		
		
		System.out.println("\n=== Teste 5: Vendedor Update ===");
		
		vendedor = vendedorDao.findById(1);
		vendedor.setNome("Simone");
		vendedorDao.update(vendedor);
		System.out.println("Vendedor alterado!");
		
		System.out.println("\n=== Teste 6: Vendedor Delete ===");
		System.out.println("Informe o codigo do vendedor a ser excluido: ");
		int id = sc.nextInt();
		vendedorDao.deleteById(id);
		System.out.println("Vendedor Excluido");
		
		sc.close();
		

	}

}
