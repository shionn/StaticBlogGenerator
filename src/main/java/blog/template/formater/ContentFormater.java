package blog.template.formater;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.parser.PostProcessor;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import blog.template.formater.gallery.GalleryExtension;
import blog.template.formater.paint.PaintExtension;
import blog.template.formater.table.TableExtension;
import blog.template.formater.youtube.YoutubeExtension;

public class ContentFormater {

	private static final List<Extension> EXTENSION = Arrays.asList( //
			new PaintExtension(),
			new GalleryExtension(),
			new TableExtension(),
			new YoutubeExtension());

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
				.postProcessor(new PostProcessor() {
					/**
					 * permet de limiter à X paragraph pour les articles de la page d'acceuil
					 */
					@Override
					public Node process(Node root) {
						Node current = root.getFirstChild();
						int count = 0;
						while (current != null) {
							Node next = current.getNext();
							if (++count > limit) {
								current.unlink();
							}
							current = next;
						}
						return root;
					}
				})
				.build();
	}

	public Parser fullParser() {
		return Parser.builder().extensions(EXTENSION).build();
	}

	private HtmlRenderer renderer() {
		HtmlRenderer.Builder builder = HtmlRenderer.builder().extensions(EXTENSION);
		return builder.attributeProviderFactory(new AttributeProviderFactory() {
			@Override
			public AttributeProvider create(AttributeProviderContext context) {
				return new AttributeProvider() {
					@Override
					public void setAttributes(Node node, String machin, Map<String, String> attributes) {
						if (node instanceof FencedCodeBlock) {
							String type = ((FencedCodeBlock) node).getInfo();
							if (StringUtils.isBlank(type)) {
								type = "java";
							}
							attributes.put("class", type);
						} else if (node instanceof Image) {
							String src = ((Image) node).getDestination();
							if (src.startsWith("/")) {
								src = StringUtils.prependIfMissing(src, "/img");
								attributes.put("src", src);
							}
						} else if (node instanceof Link) {
							String src = ((Link) node).getDestination();
							if (src.startsWith("http")) {
								attributes.put("target", "_blank");
							}
						}
					}
				};
			}
		}).build();
	}

}
