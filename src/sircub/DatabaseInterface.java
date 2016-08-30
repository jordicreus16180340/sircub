/**
 * 
 */
package sircub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sircub.datamodel.Annotation;
import sircub.datamodel.AnnotationDataset;
import sircub.datamodel.Descriptor;
import sircub.datamodel.DescriptorDataset;
import sircub.datamodel.DescriptorLocation;
import sircub.datamodel.Label;

/**
 * @author jordi
 *
 */
public class DatabaseInterface {

	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement preStmt = null;
	
	public static void connect() {
		loadDriver();
		obtainConnection();
	}
	
	static void connectImprovedOld() throws SQLException {
		loadDriver();
		obtainConnection();
		
		String insertParams = "INSERT INTO exp4_params_raw VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		preStmt = conn.prepareStatement(insertParams);
	}
	
	static void loadDriver() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
    }
	
	static void obtainConnection() {
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://" + Sircub.hostname + "/" + Sircub.database + "?" +
		                                   "user=" + Sircub.user + "&password=" + Sircub.password);
		    // Do something with the Connection
		   //...
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	static void executeQuery() {
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM exp1_params_raw");

		    // or alternatively, if you don't know ahead of time that
		    // the query will be a SELECT...
		    /*if (stmt.execute("SELECT foo FROM bar")) {
		        rs = stmt.getResultSet();
		    }*/

		    // Now do something with the ResultSet ....
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static int insertDescriptorDataset(DescriptorDataset descriptorDataset) {
		int id = -1;
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO descriptor_datasets(name, nb_years, nb_values) VALUES (" + descriptorDataset.toInsertString() + ")", Statement.RETURN_GENERATED_KEYS);

		    rs = stmt.getGeneratedKeys();
		    rs.next();
		    id = rs.getInt(1);
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return id;
	}
	
	static int insertAnnotationDataset(AnnotationDataset annotationDataset) {
		int id = -1;
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO annotation_datasets(name) VALUES (" + annotationDataset.toInsertString() + ")", Statement.RETURN_GENERATED_KEYS);

		    rs = stmt.getGeneratedKeys();
		    rs.next();
		    id = rs.getInt(1);
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return id;
	}
	
	static void insertDescriptorLocation(DescriptorLocation descriptorLocation) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO descriptor_locations VALUES (" + descriptorLocation.toInsertString() + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertDescriptor(Descriptor descriptor) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO descriptors VALUES (" + descriptor.toInsertString() + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertLabel(Label label) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO annotation_labels VALUES (" + label.toInsertString() + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertAnnotation(Annotation annotation) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO annotations VALUES (" + annotation.toInsertString() + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	public static Map<String, DescriptorLocation> getDescriptorLocationsAsMap(int descriptorDatasetId) {
		Map<String, DescriptorLocation> descriptorLocationMap = new HashMap<String, DescriptorLocation>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM descriptors AS d, descriptor_locations AS l " +
		    		"WHERE d.descriptor_dataset_id = l.descriptor_dataset_id AND d.descriptor_location_id = l.id AND d.descriptor_dataset_id = " + descriptorDatasetId);

		    while(rs.next()) {
		    	Descriptor descriptor = new Descriptor();
		    	descriptor.descriptorDatasetId = rs.getInt(1);
		    	descriptor.descriptorLocationId = rs.getInt(2);
		    	descriptor.row = rs.getInt(3);
		    	descriptor.col = rs.getInt(4);
		    	descriptor.seas = rs.getInt(5);
		    	descriptor.beg = rs.getFloat(6);
		    	descriptor.end = rs.getFloat(7);
		    	descriptor.begRelative = rs.getFloat(8);
		    	descriptor.endRelative = rs.getFloat(9);
		    	descriptor.length = rs.getFloat(10);
		    	descriptor.base = rs.getFloat(11);
		    	descriptor.midX = rs.getFloat(12);
		    	descriptor.max = rs.getFloat(13);
		    	descriptor.amp = rs.getFloat(14);
		    	descriptor.lDer = rs.getFloat(15);
		    	descriptor.rDer = rs.getFloat(16);
		    	descriptor.lInteg = rs.getFloat(17);
		    	descriptor.sInteg = rs.getFloat(18);
		    	
		    	double latitude = rs.getDouble(21);
		    	double longitude = rs.getDouble(22);
		    	String locationString = latitude + "," + longitude;
		    	
		    	DescriptorLocation descriptorLocation = null;
		    	if(descriptorLocationMap.containsKey(locationString)) {
		    		descriptorLocation = descriptorLocationMap.get(locationString);
		    	} else {
			    	descriptorLocation = new DescriptorLocation(rs.getInt(19), rs.getInt(20), latitude, longitude);
		    		descriptorLocationMap.put(locationString, descriptorLocation);
		    	}
		    	descriptorLocation.descriptors.add(descriptor);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return descriptorLocationMap;
	}
	
	public static Map<String, List<Annotation>> getAnnotationsAsMap(int annotationDatasetId) {
		Map<String, List<Annotation>> annotationMap = new HashMap<String, List<Annotation>>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM annotations " +
		    		"WHERE annotation_dataset_id = " + annotationDatasetId);

		    while(rs.next()) {
		    	Annotation annotation = new Annotation();
		    	annotation.annotationDatasetId = rs.getInt(1);
		    	annotation.locationId = rs.getInt(2);
		    	annotation.latitude = rs.getDouble(3);
		    	annotation.longitude = rs.getDouble(4);
		    	annotation.yearId = rs.getInt(5);
		    	annotation.year = rs.getString(6);
		    	annotation.firstSeasonId = rs.getInt(7);
		    	annotation.secondSeasonId = rs.getInt(8);
		    	annotation.firstSeason = rs.getString(9);
		    	annotation.secondSeason = rs.getString(10);
		    	
		    	String locationString = annotation.latitude + "," + annotation.longitude;
		    	List<Annotation> annotations = null;
		    	if(annotationMap.containsKey(locationString)) {
		    		annotations = annotationMap.get(locationString);
		    	} else {
		    		annotations = new ArrayList<Annotation>();
		    		annotationMap.put(locationString, annotations);
		    	}
		    	annotations.add(annotation);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return annotationMap;
	}
	
	public static DescriptorDataset getDescriptorDataset(int id) {
		DescriptorDataset descriptorDataset = null;
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM descriptor_datasets WHERE id = " + id);

		    rs.next();
		    descriptorDataset = new DescriptorDataset(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return descriptorDataset;
	}
	
	public static List<DescriptorDataset> getDescriptorDatasets() {
		List<DescriptorDataset> descriptorDatasets = new ArrayList<DescriptorDataset>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM descriptor_datasets ORDER BY name");

		    while(rs.next()) {
		    	DescriptorDataset descriptorDataset = new DescriptorDataset(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
		    	descriptorDatasets.add(descriptorDataset);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return descriptorDatasets;
	}
	
	public static List<AnnotationDataset> getAnnotationDatasets() {
		List<AnnotationDataset> annotationDatasets = new ArrayList<AnnotationDataset>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM annotation_datasets ORDER BY name");

		    while(rs.next()) {
		    	AnnotationDataset annotationDataset = new AnnotationDataset(rs.getInt(1), rs.getString(2));
		    	annotationDatasets.add(annotationDataset);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return annotationDatasets;
	}
	
	public static Map<Integer, Label> getLabelsAsMap(int annotationDatasetId) {
		Map<Integer, Label> labelMap = new HashMap<Integer, Label>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM annotation_labels WHERE annotation_dataset_id = " + annotationDatasetId);

		    while(rs.next()) {
		    	int id = rs.getInt(2);
		    	Label label = new Label(rs.getInt(1), id, rs.getString(3));
		    	labelMap.put(id, label);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return labelMap;
	}
	
	static void insertParams(int id, int row, int col, int seas, float beg, float end, float length, float base, float midX, float max, float amp, float lDer, float rDer, float lInteg, float sInteg) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO exp4_params_raw VALUES (" + id + ", " + row + ", " + col + ", " + seas + ", " +
		    	beg + ", " + end + ", " + length + ", " + base + ", " + midX + ", " +
		    	max + ", " + amp + ", " + lDer + ", " + rDer + ", " + lInteg + ", " + sInteg + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertParamsImprovedOld(int id, int row, int col, int seas, float beg, float end, float length, float base, float midX, float max, float amp, float lDer, float rDer, float lInteg, float sInteg) {
		try {
		    //stmt = conn.createStatement();
		    //stmt.executeUpdate("INSERT INTO exp4_params_raw VALUES (" + id + ", " + row + ", " + col + ", " + seas + ", " +
		    //	beg + ", " + end + ", " + length + ", " + base + ", " + midX + ", " +
		    //	max + ", " + amp + ", " + lDer + ", " + rDer + ", " + lInteg + ", " + sInteg + ")");
			preStmt.setInt(1, id);
			preStmt.setInt(2, row);
			preStmt.setInt(3, col);
			preStmt.setInt(4, seas);
			preStmt.setFloat(5, beg);
			preStmt.setFloat(6, end);
			preStmt.setFloat(7, length);
			preStmt.setFloat(8, base);
			preStmt.setFloat(9, midX);
			preStmt.setFloat(10, max);
			preStmt.setFloat(11, amp);
			preStmt.setFloat(12, lDer);
			preStmt.setFloat(13, rDer);
			preStmt.setFloat(14, lInteg);
			preStmt.setFloat(15, sInteg);
			preStmt.executeUpdate();
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    /*if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }*/
		}
	}
	
	static void insertParamsImproved(String values) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO dataset4_settings2_params_raw VALUES " + values);
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertPointsImproved(String values) {
		try {
		    stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO dataset3_points VALUES " + values);
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    /*if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }*/
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertDates(int id, java.util.Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO dataset3_dates VALUES (" + id + ", '" + df.format(date) + "')");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertPoints(int id, double latitude, double longitude) {
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO dataset3_points VALUES (" + id + ", " + latitude + ", " + longitude + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertNdvis(int pointId, double latitude, double longitude, int dateId, java.util.Date date, int ndvi) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO dataset3_ndvis VALUES (" + pointId + ", " + latitude + ", " + longitude + ", " + dateId + ", '" + df.format(date) + "', " + ndvi + ")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static void insertAnnotations(int pointId, double latitude, double longitude, String season, String safra, String safrinha) {
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate("INSERT INTO dataset3_annotations VALUES (" + pointId + ", " + latitude + ", " + longitude + ", \"" + season + "\", \"" + safra + "\", \"" + safrinha + "\")");
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	
	static HashMap<Integer, HashMap<Integer, List<Integer>>> getAnnotationsOrderedByAssociationYear() {
		HashMap<Integer, HashMap<Integer, List<Integer>>> annotationsOrderedByAssociationYear = new HashMap<Integer, HashMap<Integer, List<Integer>>>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT association_id, year_id, point_id FROM dataset4_annotations ORDER BY association_id, year_id, point_id");

		    int prevAssociationId = 0;
		    int curAssociationId;
		    int prevYearId = 0;
		    int curYearId;
		    int pointId;
		    HashMap<Integer, List<Integer>> annotationsOrderedByYear = null;
		    List<Integer> pointIds = null;
		    
		    while(rs.next()) {
		    	curAssociationId = rs.getInt(1);
		    	curYearId = rs.getInt(2);
	    		pointId = rs.getInt(3);
		    	
		    	if(curAssociationId != prevAssociationId) {
		    		annotationsOrderedByYear = new HashMap<Integer, List<Integer>>();
		    		annotationsOrderedByAssociationYear.put(curAssociationId, annotationsOrderedByYear);
		    		prevAssociationId = curAssociationId;
	    			
	    			pointIds = new ArrayList<Integer>();
		    		annotationsOrderedByYear.put(curYearId, pointIds);
		    		prevYearId = curYearId;
		    	} else if(curYearId != prevYearId) {
	    			pointIds = new ArrayList<Integer>();
		    		annotationsOrderedByYear.put(curYearId, pointIds);
		    		prevYearId = curYearId;
	    		}
	    		
	    		pointIds.add(pointId);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return annotationsOrderedByAssociationYear;
	}
	
	static HashMap<Integer, HashMap<Integer, List<Integer>>> getAnnotationsOrderedBySafraYear() {
		HashMap<Integer, HashMap<Integer, List<Integer>>> annotationsOrderedBySafraYear = new HashMap<Integer, HashMap<Integer, List<Integer>>>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT safra_id, year_id, point_id FROM dataset4_annotations ORDER BY safra_id, year_id, point_id");

		    int prevSafraId = 0;
		    int curSafraId;
		    int prevYearId = 0;
		    int curYearId;
		    int pointId;
		    HashMap<Integer, List<Integer>> annotationsOrderedByYear = null;
		    List<Integer> pointIds = null;
		    
		    while(rs.next()) {
		    	curSafraId = rs.getInt(1);
		    	curYearId = rs.getInt(2);
	    		pointId = rs.getInt(3);
		    	
		    	if(curSafraId != prevSafraId) {
		    		annotationsOrderedByYear = new HashMap<Integer, List<Integer>>();
		    		annotationsOrderedBySafraYear.put(curSafraId, annotationsOrderedByYear);
		    		prevSafraId = curSafraId;
	    			
	    			pointIds = new ArrayList<Integer>();
		    		annotationsOrderedByYear.put(curYearId, pointIds);
		    		prevYearId = curYearId;
		    	} else if(curYearId != prevYearId) {
	    			pointIds = new ArrayList<Integer>();
		    		annotationsOrderedByYear.put(curYearId, pointIds);
		    		prevYearId = curYearId;
	    		}
	    		
	    		pointIds.add(pointId);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return annotationsOrderedBySafraYear;
	}
	
	static HashMap<Integer, HashMap<Integer, List<Integer>>> getAnnotationsOrderedBySafrinhaYear() {
		HashMap<Integer, HashMap<Integer, List<Integer>>> annotationsOrderedBySafrinhaYear = new HashMap<Integer, HashMap<Integer, List<Integer>>>();
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT safrinha_id, year_id, point_id FROM dataset4_annotations ORDER BY safrinha_id, year_id, point_id");

		    int prevSafrinhaId = 0;
		    int curSafrinhaId;
		    int prevYearId = 0;
		    int curYearId;
		    int pointId;
		    HashMap<Integer, List<Integer>> annotationsOrderedByYear = null;
		    List<Integer> pointIds = null;
		    
		    while(rs.next()) {
		    	curSafrinhaId = rs.getInt(1);
		    	curYearId = rs.getInt(2);
	    		pointId = rs.getInt(3);
		    	
		    	if(curSafrinhaId != prevSafrinhaId) {
		    		annotationsOrderedByYear = new HashMap<Integer, List<Integer>>();
		    		annotationsOrderedBySafrinhaYear.put(curSafrinhaId, annotationsOrderedByYear);
		    		prevSafrinhaId = curSafrinhaId;
	    			
	    			pointIds = new ArrayList<Integer>();
		    		annotationsOrderedByYear.put(curYearId, pointIds);
		    		prevYearId = curYearId;
		    	} else if(curYearId != prevYearId) {
	    			pointIds = new ArrayList<Integer>();
		    		annotationsOrderedByYear.put(curYearId, pointIds);
		    		prevYearId = curYearId;
	    		}
	    		
	    		pointIds.add(pointId);
		    }
		} catch (SQLException ex){ // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally { // it is a good idea to release resources in a finally{} block in reverse-order of their creation if they are no-longer needed
		    if (rs != null) {
		        try { rs.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); }
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return annotationsOrderedBySafrinhaYear;
	}
}



























