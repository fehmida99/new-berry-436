package com.crime_IMS.dao;

import java.sql.Connection;
//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crime_IMS.Exception.CrimeException;
import com.crime_IMS.Exception.CriminalException;
import com.crime_IMS.Exception.PoliceException;
import com.crime_IMS.Utility.DButil;
import com.crime_IMS.bean.AdministratorBean;
import com.crime_IMS.bean.CrimesBean;
import com.crime_IMS.bean.CriminalBean;
import com.crime_IMS.bean.PoliceBean;

public class AdministrativePoliceDaoImpl implements AdministrativePoliceDao {

	@Override
	public AdministratorBean loginPolice(String name, String password) throws PoliceException {
		
		AdministratorBean admin = null;
		
		try(Connection conn = DButil.provideConnection()) {
			
			 PreparedStatement ps= conn.prepareStatement("select * from administrator_police where Administrator_name=? AND Administrator_password = ?");
				
				ps.setString(1, name);
				ps.setString(2, password);
				
				ResultSet rs= ps.executeQuery();
				
				if(rs.next()) {
					admin = new AdministratorBean();
					
					admin.setAdministrator_name(rs.getString("Administrator_name"));
					admin.setAdministrator_id(rs.getInt("Administrator_id"));
					admin.setAdministrator_password(rs.getString("Administrator_password"));
					admin.setAdministrator_rank(rs.getString("Administrator_rank"));
					
				}else {
					throw new PoliceException("Invalid Username or password..... :( Register yourself first");
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
		return admin;

	}

	@Override
	public List<CrimesBean> SolvedCases() throws CrimeException {
		
		List<CrimesBean> solvedCrimes = new ArrayList<>(); 
		
		try(Connection conn = DButil.provideConnection()) {
			
			PreparedStatement ps= conn.prepareStatement("select * from crimes where solved = 1");
			
			ResultSet rs = ps.executeQuery();
			
			
			while(rs.next()) {
				
				int crime_id = rs.getInt("crime_id");
				String crime_area = rs.getString("crime_area");
				String c_police_stn = rs.getString("c_police_stn");
				boolean solved = rs.getBoolean("solved");
				String crime_date = rs.getString("crime_date");
				String crime_place = rs.getString("crime_place");
				String crime_desc = rs.getString("crime_desc");
				int victim_numbers = rs.getInt("victim_numbers");
				String crime_detail_desc = rs.getString("crime_detail_desc");
				String crime_main_suspect = rs.getString("crime_main_suspect");
				
				CrimesBean sc = new CrimesBean(crime_id,crime_area,c_police_stn,solved,crime_date,crime_place,crime_desc,victim_numbers,crime_detail_desc,crime_main_suspect);
				solvedCrimes.add(sc);
				
			}
			
			if(solvedCrimes.size() == 0) {
				throw new CrimeException("There are no solved crimes currently present");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CrimeException(e.getMessage());
		}
		
		
		
		return solvedCrimes;
	}

	@Override
	public List<CrimesBean> UnSolvedCases() throws CrimeException {
		 List<CrimesBean> UnsolvedCrimes = new ArrayList<>(); 
			
			try(Connection conn = DButil.provideConnection()) {
				
				PreparedStatement ps= conn.prepareStatement("select * from crimes where solved = 0");
				
				ResultSet rs = ps.executeQuery();
				
				
				while(rs.next()) {
					
					int crime_id = rs.getInt("crime_id");
					String crime_area = rs.getString("crime_area");
					String c_police_stn = rs.getString("c_police_stn");
					boolean solved = rs.getBoolean("solved");
					String crime_date = rs.getString("crime_date");
					String crime_place = rs.getString("crime_place");
					String crime_desc = rs.getString("crime_desc");
					int victim_numbers = rs.getInt("victim_numbers");
					String crime_detail_desc = rs.getString("crime_detail_desc");
					String crime_main_suspect = rs.getString("crime_main_suspect");
					
					CrimesBean usc = new CrimesBean(crime_id,crime_area,c_police_stn,solved,crime_date,crime_place,crime_desc,victim_numbers,crime_detail_desc,crime_main_suspect);
					UnsolvedCrimes.add(usc);
					
				}
				
				if(UnsolvedCrimes.size() == 0) {
					throw new CrimeException("There are no solved crimes currently present");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CrimeException(e.getMessage());
			}
			
			
			
			return UnsolvedCrimes;
	}

	@Override
	public String UpdateUnsolveToSolve(CrimesBean crimes) throws CrimeException {
		String message = "Sorry no change occurs";
		
		
		try(Connection conn = DButil.provideConnection()) {

			PreparedStatement ps= conn.prepareStatement
					("update crimes set solved = true,crime_main_suspect = ? where crime_id = ? ;");
			
			ps.setString(1, crimes.getCrime_main_suspect());
			ps.setInt(2, crimes.getCrime_id());
			
			int x= ps.executeUpdate();
			
			 if(x > 0)
				message = "Crime Status has been changed successfully............:)";
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			
		}
		
		
		
		
		return message;
	}

	@Override
	public List<CrimesBean> getAllTheCrimesCases() throws CrimeException {
		 List<CrimesBean> Crimes = new ArrayList<>(); 
			
			try(Connection conn = DButil.provideConnection()) {
				
				PreparedStatement ps= conn.prepareStatement("select * from crimes");
				
				ResultSet rs = ps.executeQuery();
				
				
				while(rs.next()) {
					
					int crime_id = rs.getInt("crime_id");
					String crime_area = rs.getString("crime_area");
					String c_police_stn = rs.getString("c_police_stn");
					boolean solved = rs.getBoolean("solved");
					String crime_date = rs.getString("crime_date");
					String crime_place = rs.getString("crime_place");
					String crime_desc = rs.getString("crime_desc");
					int victim_numbers = rs.getInt("victim_numbers");
					String crime_detail_desc = rs.getString("crime_detail_desc");
					String crime_main_suspect = rs.getString("crime_main_suspect");
					
					CrimesBean c = new CrimesBean(crime_id,crime_area,c_police_stn,solved,crime_date,crime_place,crime_desc,victim_numbers,crime_detail_desc,crime_main_suspect);
					Crimes.add(c);
					
				}
				
				if(Crimes.size() == 0) {
					throw new CrimeException("There are no solved crimes currently present");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new CrimeException(e.getMessage());
			}
			
			
			
			return Crimes;
	}

	@Override
	public String RegisterNewCrime(CrimesBean crimes) throws CrimeException {
       String message = "Not Registered";
		
		try(Connection conn = DButil.provideConnection()) {
			
			PreparedStatement ps= conn.prepareStatement
					("insert into crimes values (?,?,?,?,?,?,?,?,?,?)");
			
			ps.setInt(1, crimes.getCrime_id());
			ps.setString(2, crimes.getCrime_area());
			ps.setString(3, crimes.getC_police_stn());
			ps.setBoolean(4, crimes.isSolved());
			ps.setString(5, crimes.getCrime_date());
			ps.setString(6, crimes.getCrime_place());
			ps.setString(7 ,crimes.getCrime_desc());
			ps.setInt(8, crimes.getVictim_numbers());
			ps.setString(9, crimes.getCrime_detail_desc());
			ps.setString(10 ,crimes.getCrime_main_suspect());
			
			int x= ps.executeUpdate();
			
			 if(x > 0)
				message = "Crime has Registered Sucessfully in the database..  ..........:)";
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CrimeException(e.getMessage());
		}
		
		
		return message;
	}

	@Override
	public String RegisterNewCriminal(CriminalBean criminal) throws CriminalException {
        String message = "Not Registered";
		
		try(Connection conn = DButil.provideConnection()) {
			
			PreparedStatement ps= conn.prepareStatement
					("insert into criminal values (?,?,?,?,?,?,?,?)");
			
			ps.setInt(1, criminal.getCriminal_id());
			ps.setString(2, criminal.getCriminal_name());
			ps.setInt(3, criminal.getCriminal_age());
			ps.setString(4, criminal.getCriminal_gender());
			ps.setString(5, criminal.getCriminal_address());
			ps.setString(6, criminal.getCriminal_identify_mark());
			ps.setString(7, criminal.getCriminal_area_of_arrest());
			ps.setInt(8, criminal.getCriminal_crime_id());
			
			int x= ps.executeUpdate();
			
			 if(x > 0)
				message = "Criminal has Registered Sucessfully in the database..  ..........:)";
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CriminalException(e.getMessage());
		}
		
		
		
		return message;
	}

	@Override
	public String RegisterNewPolice(PoliceBean police) throws PoliceException {
		String message = "Police Not Registered";
		
		
		try(Connection conn = DButil.provideConnection()) {
			
			PreparedStatement ps= conn.prepareStatement
					("insert into police values (?,?,?,?,?,?)");
			
			ps.setInt(1, police.getPolice_id());
			ps.setString(2, police.getPolice_name());
			ps.setString(3, police.getPolice_gender());
			ps.setInt(4, police.getPolice_age());
			ps.setString(5, police.getPolice_curr_police_stn());
			ps.setString(6, police.getPolice_id_password());
			
			int x= ps.executeUpdate();
			
			 if(x > 0)
				message = "Police has Registered Sucessfully in the database..  ..........:)";
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
		
		return message;
	}

	@Override
	public List<CrimesBean> getAllTheCrimesCasesinCertaInTimeInterval(String startdate, String enddate) throws CrimeException {
		List<CrimesBean> Crimes = new ArrayList<>(); 
		
        try(Connection conn = DButil.provideConnection()) {
			
			PreparedStatement ps= conn.prepareStatement("select * from crimes where crime_date between ? and ?");
			
			ps.setString(1, startdate);
			ps.setString(2, enddate);
			
			ResultSet rs = ps.executeQuery();
			
			
			while(rs.next()) {
				
				int crime_id = rs.getInt("crime_id");
				String crime_area = rs.getString("crime_area");
				String c_police_stn = rs.getString("c_police_stn");
				boolean solved = rs.getBoolean("solved");
				String crime_date = rs.getString("crime_date");
				String crime_place = rs.getString("crime_place");
				String crime_desc = rs.getString("crime_desc");
				int victim_numbers = rs.getInt("victim_numbers");
				String crime_detail_desc = rs.getString("crime_detail_desc");
				String crime_main_suspect = rs.getString("crime_main_suspect");
				
				CrimesBean c = new CrimesBean(crime_id,crime_area,c_police_stn,solved,crime_date,crime_place,crime_desc,victim_numbers,crime_detail_desc,crime_main_suspect);
				Crimes.add(c);
				
			}
			
			if(Crimes.size() == 0) {
				throw new CrimeException("There are no cases present in this time interval");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CrimeException(e.getMessage());
		}
		
		
		
		
		return Crimes;
	}

}
