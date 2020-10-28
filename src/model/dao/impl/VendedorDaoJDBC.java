package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {
	
	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Vendedor obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into seller " + 
					"(Name, Email, BirthDate, BaseSalary, DepartmentId) " + 
					"values " + 
					"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDatanasc().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);					
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado, nenhuma linha foi afetada.");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);	
		}
		
	}

	@Override
	public void update(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update seller " + 
					"set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + 
					"where id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDatanasc().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);	
		}
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
					"FROM seller inner join department " +
					"on seller.DepartmentId = department.Id " +
					"where seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Departamento dept = instanciaDepartamento(rs);
				Vendedor obj = instanciaVendedor(rs,dept);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Vendedor instanciaVendedor(ResultSet rs, Departamento dept) throws SQLException {
		 Vendedor obj = new Vendedor();
			obj.setId(rs.getInt("Id"));
			obj.setNome(rs.getString("Name"));
			obj.setEmail(rs.getString("Email"));
			obj.setSalarioBase(rs.getDouble("BaseSalary"));
			obj.setDatanasc(rs.getDate("BirthDate"));
			obj.setDepartamento(dept);
		return obj;
	}

	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {
		Departamento dept = new Departamento();
		dept.setId(rs.getInt("DepartmentId"));
		dept.setNome(rs.getString("DepName"));
		
		return dept;
	}

	@Override
	public List<Vendedor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
					"FROM seller inner join department " +
					"on seller.DepartmentId = department.Id " +
					"order by Name ");
			
			rs = st.executeQuery();
			
			List<Vendedor> list = new ArrayList<>();
			Map<Integer,Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instanciaDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor obj = instanciaVendedor(rs,dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
		
	}
	@Override
	public List<Vendedor> findByDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
					"FROM seller inner join department " +
					"on seller.DepartmentId = department.Id " +
					"where DepartmentId = ? " +
					"order by Name ");
			
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			List<Vendedor> list = new ArrayList<>();
			Map<Integer,Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instanciaDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor obj = instanciaVendedor(rs,dep);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}

}
