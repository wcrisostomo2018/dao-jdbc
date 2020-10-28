package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		DepartamentoDao departamentoDao = DaoFactory.createDepartamentoDao();
		
		System.out.println("\n=== Teste 3: Departamento findById ===");
		Departamento depta = departamentoDao.findById(2);
		System.out.println(depta);
		
		System.out.println("\n=== Teste 4: Departamento findAll ===");
		List<Departamento> list = departamentoDao.findAll();
		for (Departamento deptoe : list) {
			System.out.println(deptoe);
		}
		
		System.out.println("=== Teste 1: Departamento Insert ===");
		Departamento dept = new Departamento(null,"Informatica");
		departamentoDao.insert(dept);
		System.out.println("Vendedor Inserido! Id: " + dept.getId());
	
		System.out.println("\n=== Teste 2: Departamento Update ===");
		 Departamento depto = departamentoDao.findById(9);
		 dept.setNome("Tintas");
		 departamentoDao.update(depto);
		System.out.println("Departamento alterado!");
		
		System.out.println("\n=== Teste 5: Departamento delete ===");
		System.out.println("Informe o codigo do departamento a ser excluido: ");
		int id = sc.nextInt();
		departamentoDao.deleteById(id);
		System.out.println("Departamento excluido");
		
		sc.close();

	}

}
