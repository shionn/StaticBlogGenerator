package blog.template.formater.after;

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
		if (((AfterBlock) node).isDisplay()) {
			context.render(node.getFirstChild());
			Node next = node.getFirstChild().getNext();
			while (next != null) {
				context.render(next);
				next = next.getNext();
			}
		}
	}

}
