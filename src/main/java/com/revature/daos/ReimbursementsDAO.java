package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursements;
import com.revature.utils.ConnectionUtil;

public class ReimbursementsDAO implements ReimbursementsDAOInterface {

	@Override
	public void addReimbursement(int reimb_type_id, String reimb_description, int reimb_amount) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT into \"ers\".reimbursements (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)"
					+ "values (?, current_timestamp, null, ?, 5, 6, 1, ?);";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, reimb_amount);
			ps.setString(2, reimb_description);
			ps.setInt(3, reimb_type_id);
			
			ps.executeUpdate();
		} catch(SQLException e) {
			System.out.println("ERROR!!! Add Reimbursement Failed!");
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Reimbursements> viewPendingTickets(){
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ResultSet rs = null;
			
			String sql = "SELECT * FROM \"ers\".reimbursements WHERE reimb_status_id = 1;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			List<Reimbursements> list = new ArrayList<>();
			
			while (rs.next()) {
				Reimbursements r = new Reimbursements(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id")
						);
				list.add(r);
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public List<Reimbursements> viewPreviousTickets(){
		try (Connection conn = ConnectionUtil.getConnection()){
			
			ResultSet rs = null;
			
			String sql = "SELECT * FROM \"ers\".reimbursements WHERE reimb_status_id != 1;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			List<Reimbursements> list = new ArrayList<>();
			
			while(rs.next()) {
				Reimbursements r = new Reimbursements(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id")
						);
				list.add(r);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reimbursements> viewAllTickets(){
		try(Connection conn = ConnectionUtil.getConnection()){
			
			ResultSet rs = null;
			
			String sql = "SELECT * FROM \"ers\".reimbursements;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			List<Reimbursements> list = new ArrayList<>();
			
			while(rs.next()) {
				Reimbursements r = new Reimbursements(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id")
						);
				list.add(r);
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override public List<Reimbursements> filterByStatus(int reimb_status_id){
		try (Connection conn = ConnectionUtil.getConnection()){
			
			ResultSet rs = null;
			
			String sql = "SELECT * FROM \"ers\".reimbursements WHERE reimb_status_id = ?;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, reimb_status_id);
			
			rs = ps.executeQuery();
			
			List<Reimbursements> list = new ArrayList<>();
			
			while(rs.next()) {
				Reimbursements r = new Reimbursements(
						rs.getInt("reimb_id"),
						rs.getInt("reimb_amount"),
						rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"),
						rs.getString("reimb_description"),
						rs.getInt("reimb_author"),
						rs.getInt("reimb_resolver"),
						rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id")
						);
				list.add(r);
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void resolveTicket(int reimb_id, int reimb_status_id) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE \"ers\".reimbursements SET reimb_status_id = ?, reimb_resolver = 2, reimb_resolved = current_timestamp WHERE reimb_id = ?;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, reimb_status_id);
			ps.setInt(2, reimb_id);
			ps.executeUpdate();
		} catch(SQLException e) {
			System.out.println("ERROR: Resolution failed!");
			e.printStackTrace();
		}
	}
}
