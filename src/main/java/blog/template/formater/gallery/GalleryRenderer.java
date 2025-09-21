package blog.template.formater.gallery;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

public class GalleryRenderer implements NodeRenderer {

	private HtmlNodeRendererContext context;
	private HtmlWriter writer;

	public GalleryRenderer(HtmlNodeRendererContext context) {
		this.context = context;
		this.writer = context.getWriter();
	}

	@Override
	public Set<Class<? extends Node>> getNodeTypes() {
		Set<Class<? extends Node>> nodes = new HashSet<>();
		nodes.add(GalleryBlock.class);
		return nodes;
	}

	@Override
	public void render(Node node) {
		writer.tag("div", context.extendAttributes(node, "truc ? ", Collections.singletonMap("class", "gallery")));
		for (GalleryImage img : gallery(node).getImgs()) {

			writer.tag("a", Collections.singletonMap("href", img.getUrl()));
			writer.tag("img", buildImgAttrs(img, gallery(node)));
			writer.tag("/a");
		}
		writer.tag("/div");
	}

	private Map<String, String> buildImgAttrs(GalleryImage img, GalleryBlock gallery) {
		Map<String, String> attrs = new HashMap<String, String>();
		String url = img.getUrl();
		if (gallery.getW() > 0 && gallery.getH() > 0) {
			attrs.put("width", Integer.toString(gallery.getW()));
			attrs.put("height", Integer.toString(gallery.getH()));
			url = new GalleryThumbnail(gallery.getW(), gallery.getH()).create(url);
		}
		if (img.getPosition() != null) {
			attrs.put("style", "object-position: " + img.getPosition());
		}
		if (img.getTitle() != null) {
			attrs.put("title", img.getTitle());
		}
		attrs.put("src", url);
		return attrs;
	}

	private GalleryBlock gallery(Node node) {
		return (GalleryBlock) node;
	}

}
