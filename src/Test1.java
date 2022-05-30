import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Test1 {

	

	

	public static String[] getPropertiesFromConfigFile(String configFileName) {
		String[] props = new String[3];
		Properties prop = new Properties();
		String fileName = "config.cfg";
		// String fileName = configFileName;
		try (FileInputStream fis = new FileInputStream(fileName)) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		props[0] = prop.getProperty("url");
		props[1] = prop.getProperty("user");
		props[2] = prop.getProperty("pass");

		return props;
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/CaPiture";
		String user = "root";
		String pass = "";

//		GUI income
		String tableName = "T1";
		String path = "C:\\Users\\Simon\\Desktop\\MUL\\Duell";
//		GUI income
//		String[] props = getPropertiesFromConfigFile(args[0]);

		PictureFileTree.getAllPicturesFrom(path);
		Pictures ps = PictureFileTree.createAndStorePictureObjects();

		SQL.init(url, user, pass);
		SQL.createPictureTable(tableName);
		SQL.insertIntoDB(ps, tableName);
		SQL.updateName(1, "Hallo1234", "jpg");
		System.out.println("Here we go");

	}

}
