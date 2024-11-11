package blog.template.formater.youtube;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

public class YoutubeRenderer implements NodeRenderer {

	private HtmlWriter writer;

	public YoutubeRenderer(HtmlNodeRendererContext context) {
		this.writer = context.getWriter();
	}

	@Override
	public Set<Class<? extends Node>> getNodeTypes() {
		Set<Class<? extends Node>> nodes = new HashSet<>();
		nodes.add(YoutubeBlock.class);
		return nodes;
	}

	@Override
	public void render(Node node) {
		writer.tag("iframe", buildAttr(youtube(node)));
		writer.tag("/iframe");
	}

	private Map<String, String> buildAttr(YoutubeBlock node) {
		Map<String, String> attr = new HashMap<>();
		attr.put("style", "margin: 0 auto; display: block");
		attr.put("width", "560");
		attr.put("height", "315");
		attr.put("src", "https://www.youtube-nocookie.com/embed/" + node.getVideo());
		attr.put("title", "YouTube video player");
		attr.put("frameborder", "0");
		attr.put("allow",
				"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share");
		attr.put("referrerpolicy", "strict-origin-when-cross-origin");
		attr.put("allowfullscreen", "true");
		return attr;
	}

	private YoutubeBlock youtube(Node node) {
		return (YoutubeBlock) node;
	}


}
