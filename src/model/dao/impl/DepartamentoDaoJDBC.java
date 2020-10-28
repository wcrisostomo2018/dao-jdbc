package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {
	
	private Connection conn; 
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	

	@Override
	public void insert(Departamento obj) {
	PreparedStatement st = null;
	
	try {
		st = conn.prepareStatement("insert into department " + 
				"( Name) " + 
				"values " + 
				"(?)",Statement.RETURN_GENERATED_KEYS);
		
		
		st.setString(1, obj.getNome());
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
			throw new DbException("Erro inesperado! Nenhuma linha foi afetada!");
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
	public void update(Departamento obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update department " + 
					"set Name = ? " + 
					"where Id = ?");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			 
			int registro = st.executeUpdate();
			
			if (registro == 0) {
				throw new DbException("Registro nao existente!");
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("delete from department where Id = ?");
			st.setInt(1, id);
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
	public Departamento findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select department.Id, department.Name " + 
					"from department " + 
					"where department.Id = ? ");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Departamento dept = new Departamento();
				dept.setId(rs.getInt("Id"));
				dept.setNome(rs.getString("Name"));
				return dept;
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

	@Override
	public List<Departamento> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select department.Id, department.Name " + 
					"from department " + 
					"order by Name ");
			rs = st.executeQuery();
			List<Departamento> list = new ArrayList<>();
			
			while (rs.next()) {
				Departamento dept = instanciaDepartamento(rs);
				list.add(dept);
				
				}
			return list;
			
			}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			
		}
	}


	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNome(rs.getString("Name"));
		return dep;
	}
	

}
