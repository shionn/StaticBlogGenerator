package blog.template.formater;

import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import blog.template.formater.after.AfterExtension;
import blog.template.formater.code.CodeDefaultLangageExtension;
import blog.template.formater.gallery.GalleryExtension;
import blog.template.formater.homepage.NodeLimiter;
import blog.template.formater.image.ImageExtension;
import blog.template.formater.link.LinkExtension;
import blog.template.formater.paint.PaintExtension;
import blog.template.formater.table.TableExtension;
import blog.template.formater.youtube.YoutubeExtension;

public class ContentFormater {

	private static final List<Extension> EXTENSION = Arrays.asList( //
			new AfterExtension(),
			new CodeDefaultLangageExtension(),
			new GalleryExtension(),
			new ImageExtension(),
			new LinkExtension(),
			new TableExtension(),
			new PaintExtension(),
			new YoutubeExtension() 
			);

	public String shortPost(String content, int limit) {
		Node nodes = homeParser(limit).parse(content);
		return renderer().render(nodes);
	}

	public String fullPost(String content) {
		Node nodes = fullParser().parse(content);
		return renderer().render(nodes);
	}

	private Parser homeParser(int limit) {
		return Parser.builder().extensions(EXTENSION)
				.extensions(Arrays.asList(new NodeLimiter(limit)))
				.build();
	}

	public Parser fullParser() {
		return Parser.builder().extensions(EXTENSION).build();
	}

	private HtmlRenderer renderer() {
		return HtmlRenderer.builder().extensions(EXTENSION).build();
	}

}
