package blog.model.formater.after;

import java.util.HashSet;
import java.util.Set;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;

public class AfterRenderer implements NodeRenderer {

	private HtmlNodeRendererContext context;

	public AfterRenderer(HtmlNodeRendererContext context) {
		this.context = context;
	}

	@Override
	public Set<Class<? extends Node>> getNodeTypes() {
		Set<Class<? extends Node>> nodes = new HashSet<>();
		nodes.add(AfterBlock.class);
		return nodes;
	}

	@Override
	public void render(Node node) {
		context.getWriter().text("render");
//		context.render(node.getLastChild());
//		context.render(node.getFirstChild());
	}

}
