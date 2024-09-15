package blog.model.formater.gallery;

import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import blog.generator.Configuration;

public class GalleryThumbnail {

	private int targetW;
	private int targetH;

	public GalleryThumbnail(int w, int h) {
		this.targetW = w;
		this.targetH = h;

	}

	public String create(String url) {
		String targetUrl = filename(url) + "-" + targetW + "-" + targetH + extenssion(url);
		String base = Configuration.get().getTargetFolder() + "/";
		File file = new File(base + targetUrl);
		if (file.exists() && Configuration.get().isGalleryForceThumbnail()) {
			file.delete();
		}
		if (!file.exists()) {
			IMOperation op = new IMOperation();
			op.addImage(base + url);
			op.resize(targetW, targetW, "^").gravity("center").repage(targetW, targetH);
			op.addImage(base + targetUrl);
			try {
				new ConvertCmd().run(op);
			} catch (IOException | InterruptedException | IM4JavaException e) {
				// peut etre qu'il manque imagemagic
				System.out.println("can not generate " + targetUrl);
				System.out.println("manque imagemagic ?");
				System.err.println(e);
				targetUrl = url;
			}
		}
		return targetUrl;
	}

	private String filename(String url) {
		return url.substring(0, url.lastIndexOf('.'));
	}

	private String extenssion(String url) {
		return url.substring( url.lastIndexOf('.'));
	}

}
