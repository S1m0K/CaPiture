import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;


public class PictureFileTree {
	
	public static ArrayList<File> pics = new ArrayList<File>();

	private static class DirectoryFilter implements FileFilter {
		@Override
		public boolean accept(File f) {
			return f.isDirectory();
		}
	}

	private static class DirectoryFilter2 implements FileFilter {
		@Override
		public boolean accept(File f) {
			return !f.isDirectory();
		}
	}

	public static void getAllPicturesFrom(String dir) {
		File f = new File(dir);
		tree(0, f);
	}

	private static void tree(int level, File f) {

		File[] ordner = f.listFiles(new DirectoryFilter());
		File[] files = f.listFiles(new DirectoryFilter2());

		for (File file : ordner) {
			tree(level + 1, file);
		}
		for (File file : files) {
			if (file.getName().endsWith("jpg") || file.getName().endsWith("png") || file.getName().endsWith("jfif")
					|| file.getName().endsWith("bmp")) {
				pics.add(file);
			}
		}

	}
	public static Pictures createAndStorePictureObjects() {
		Pictures ps = new Pictures();
		for (int i = 0; i < pics.size(); i++) {
			File f = pics.get(i);
			Picture p = new Picture(f);
//			System.out.println(p.getFullPath());
			ps.addPic(p);
		}
		return ps;
	}
}
