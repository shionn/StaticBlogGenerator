package blog.template.formater.gallery;

import java.util.ArrayList;
import java.util.List;

import org.commonmark.node.Block;
import org.commonmark.node.Visitor;

public class GalleryBlock extends Block {

	private List<GalleryImage> imgs = new ArrayList<>();
	private int w;
	private int h;

	public GalleryBlock(int w, int h) {
		this.w = w;
		this.h = h;
	}

	@Override
	public void accept(Visitor visitor) {
		System.out.println("accept");
	}

	public void addImage(GalleryImage img) {
		this.imgs.add(img);
	}

	public List<GalleryImage> getImgs() {
		return imgs;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
