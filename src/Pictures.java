import java.util.ArrayList;

public class Pictures {
	ArrayList<Picture> pictures = new ArrayList<Picture>();

	public void addPic(Picture p) {
		pictures.add(p);
	}

	public void removePic(Picture p) {
		pictures.remove(p);
	}

	public ArrayList<Picture> getPictures() {
		return pictures;
	}
	public int getLength() {
		return pictures.size();
	}
	public Picture getPicture(int index) {
		return pictures.get(index);
	}
}
