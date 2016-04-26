package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CPVQueries implements SQLQueries<CPVModel> {
	
	private List<CPVModel> cpvList;
	private final String id = "cpv_code";
	private final String table = "cpv";
	
	public CPVQueries() {
		cpvList = new LinkedList<>();
	}

	@Override
	public void create(CPVModel object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
		
	}
	
	public void retrieve(String request, boolean sameLvlShow) throws SQLException {
		cpvList.clear();
		
		PreparedStatement prepStmt = getCPVRequest(request, sameLvlShow);
		ResultSet results = prepStmt.executeQuery();

		while (results.next()) {
			String cpvId = results.getString("cpv_code");
			String cpvUkr = results.getString("cpv_ukr");
			String cpvEng = results.getString("cpv_eng");
			int cpvLevel = results.getInt("cpv_level");
			boolean cpvTerminal = results.getBoolean("terminal");

			CPVModel cpvModel = new CPVModel(cpvId, cpvUkr, cpvEng, cpvLevel, cpvTerminal);
			cpvList.add(cpvModel);
		}
		results.close();
		prepStmt.close();
		
	}
	

	@Override
	public void update(CPVModel object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CPVModel object) {
		// TODO Auto-generated method stub
		
	}

	public List<CPVModel> getList() {
		return Collections.unmodifiableList(cpvList);
	}

	private static PreparedStatement getCPVRequest(String requestedCpv, boolean sameLvlShow) throws SQLException{
		int level = 0;
		Connection con = DBConnector.INSTANCE.getConnection();
		String query;
		
		//case: empty request
		if (requestedCpv.isEmpty()) {
			// selecting general Divisions
			requestedCpv = "__000000-_";

			query = "select cpv_code, cpv_ukr, cpv_eng, cpv_level, terminal from cpv where cpv_code ilike ? and cpv_level > 0 and active = true";
			PreparedStatement prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, requestedCpv);
			return prepStmt;
			
		//case: first char is digit
		} else if (Character.isDigit(requestedCpv.charAt(0))) {
			if (requestedCpv.length() > 8) {
				// if more then 8 char, trim to 7 char and till non 0 value found
				requestedCpv = requestedCpv.substring(0, 8);
				while (requestedCpv.length()>2 && requestedCpv.endsWith("0")) {
					requestedCpv = requestedCpv.substring(0, requestedCpv.length() - 1);
				}
			}

			if (requestedCpv.length() < 2) {
				// selecting ParentGroups
				requestedCpv = "0" + requestedCpv + "_00000-_";
				level = 1;
			} else if (requestedCpv.length() < 3) {
				// selecting ParentGroups
				requestedCpv = requestedCpv + "_00000-_";
				level = 1;
			} else if (requestedCpv.length() < 4) {
				// selecting ParentClasses
				requestedCpv = requestedCpv + "_0000-_";
				level = 2;
			} else if (requestedCpv.length() < 5) {
				// selecting ParentCategories
				requestedCpv = requestedCpv + "_000-_";
				level = 3;
			} else if (requestedCpv.length() < 6) {
				// selecting exact Category
				requestedCpv = requestedCpv + "_00-_";
				level = 4;
			} else if (requestedCpv.length() < 7) {
				// selecting exact Category
				requestedCpv = requestedCpv + "_0-_";
				level = 5;
			} else if (requestedCpv.length() < 8) {
				// selecting exact Category
				requestedCpv = requestedCpv + "_-_";
				level = 6;
			} else if (requestedCpv.length() < 9) {
				// selecting exact Category
				requestedCpv = requestedCpv + "-_";
				level = 7;
			}
			if(sameLvlShow){
				level--;
			}
			
			query = "select cpv_code, cpv_ukr, cpv_eng, cpv_level, terminal from cpv where cpv_code ilike ? and cpv_level > ? and active = true";
			PreparedStatement prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, requestedCpv);
			prepStmt.setInt(2, level);
			return prepStmt;
		
		//case: first char is literal
		} else {
			requestedCpv = "%" + requestedCpv + "%";
			if (Character.UnicodeBlock.CYRILLIC.equals(Character.UnicodeBlock.of(requestedCpv.charAt(1)))) {
				query = "select cpv_code, cpv_ukr, cpv_eng, cpv_level, terminal from cpv where cpv_ukr ilike ? and active = true";
			} else {
				query = "select cpv_code, cpv_ukr, cpv_eng, cpv_level, terminal from cpv where cpv_eng ilike ? and active = true";
			}
			PreparedStatement prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, requestedCpv);
			return prepStmt;
		}
	}
	
	public boolean isChanged(LastChangesModel cacheModel) throws SQLException {
		return checkChanges(cacheModel, table, id);
	}

	public LastChangesModel getChangedModel() throws SQLException {
		return getChanged(table, id);
	}
}
