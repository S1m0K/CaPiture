import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	private static Connection c;
	private static String tableName;

	public static void init(String url, String user, String pass) {
		SQL.c = getConnection(url, user, pass);
		SQL.setAutoCommit(c, true);
	}

	private static Connection getConnection(String url, String user, String pass) {
		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void setAutoCommit(Connection c, boolean ToF) {
		try {
			c.setAutoCommit(ToF);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Set Auto Commit didnt work!");
		}

	}

	public static void createPictureTable(String tableName) {
		SQL.tableName = tableName;
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists " + tableName + ";";
			stmt.executeUpdate(sql);
			sql = "create table " + tableName
					+ "(PID int primary key auto_increment, parentPath varchar(255), name varchar(255),datum date, ext varchar(10), fileSize bigint);";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void createCustomTableTable() {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists CustomTable(TID int primary key auto_increment, name varchar(255));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Date getSQLDate(long time) {
		Date d = new Date(time);
		return d;
	}

	public static void insertIntoDB(Pictures ps, String tableName) {
		for (int i = 0; i < ps.getLength(); i++) {
			Picture p = ps.getPicture(i);
			try {
				String sql = "insert into " + tableName + "(parentPath, name,datum,ext,fileSize) values(?,?,?,?,?);";
				PreparedStatement stmt = c.prepareStatement(sql);
				stmt.setString(1, p.getParentPath());
				stmt.setString(2, p.getName());
				stmt.setDate(3, SQL.getSQLDate(p.getLastModTime()));
				stmt.setString(4, p.getExtension());
				stmt.setLong(5, p.getFileSize());
				stmt.executeUpdate();
				stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void updateParentPath(int PID, String newParentPath) {
		try {
			String sql = "update " + SQL.tableName + " set parentpath = ? where PID = ?;";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, newParentPath);
			stmt.setInt(2, PID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateName(int PID, String newName, String ext) {
		try {
			newName = newName + "." + ext;
			String sql = "update " + SQL.tableName + " set name = ? where PID = ?;";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, newName);
			stmt.setInt(2, PID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deletePicture(int PID) {
		try {
			String sql = "delete from " + SQL.tableName + " where PID = ?;";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setInt(1, PID);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Pictures selectAll() {
		Pictures ps = new Pictures();
		try {
			String sql = "select pid,parentPath, name,datum,ext,fileSize from " + SQL.tableName + " ;";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int pid = rs.getInt("pid");
				String parentPath = rs.getString("parentPath");
				String name = rs.getString("name");
				Date datum = rs.getDate("datum");
				String ext = rs.getString("ext");
				long fileSize = rs.getLong("fileSize");
								
				Picture p = new Picture(pid, parentPath, name, ext, fileSize, datum.getTime());
				ps.addPic(p);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
}
