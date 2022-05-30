import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class Picture {
	private int pid;
	private File f;
	private String parentPath;
	private String name;
	private String extension;
	private long lastModTime;
	private long fileSize;

	public Picture(File f) {
		this.f = f;
		this.parentPath = f.getParent();
		this.name = f.getName();
		this.lastModTime = f.lastModified();

		if (f.getPath().lastIndexOf('.') > 0) {
			this.extension = f.getPath().substring(f.getPath().lastIndexOf('.') + 1);
		}

		try {
			this.fileSize = Files.size(Paths.get(f.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Picture(int pid, String parentPath, String name, String ext, long fileSize, long lastModTime) {
		this.pid = pid;
		this.parentPath = parentPath;
		this.name = name;
		this.extension = ext;
		this.fileSize = fileSize;
		this.lastModTime = lastModTime;

	}

	public void deletePic() {
		f.delete();
		SQL.deletePicture(pid);
	}

	public void renamePic(String newName, int PID, String ext) {
		File nf = new File(parentPath + File.separator + newName + "." + extension);
		f.renameTo(nf);
		SQL.updateName(PID, newName, ext);
	}

	public String getFullPath() {
		return parentPath + File.separator + name;
	}

	public void movePicTo(String newPath) {
		Path copiedToPath = Paths.get(newPath);
		Path originPath = Paths.get(getFullPath());
		try {
			Files.move(originPath, copiedToPath, StandardCopyOption.REPLACE_EXISTING);
			SQL.updateParentPath(pid, newPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getParentPath() {
		return parentPath;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public long getLastModTime() {
		return lastModTime;
	}

	public long getFileSize() {
		return fileSize;
	}

	public Date getRealDate() {
		Date d = new Date(lastModTime);
		return d;
	}
}
