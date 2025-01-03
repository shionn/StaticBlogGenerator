package blog.template.formater.table;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

public class TableRenderer implements NodeRenderer {

	private HtmlWriter writer;

	public TableRenderer(HtmlNodeRendererContext context) {
		this.writer = context.getWriter();
	}

	@Override
	public Set<Class<? extends Node>> getNodeTypes() {
		Set<Class<? extends Node>> nodes = new HashSet<>();
		nodes.add(TableBlock.class);
		return nodes;
	}

	@Override
	public void render(Node node) {
		writer.tag("table", buildTableAttr(collection(node)));

		thead(collection(node));
		tbody(collection(node));

		writer.tag("/table");
	}

	private void tbody(TableBlock node) {
		boolean body = false;
		for (String[] datas : node.getDatas()) {
			if (datas.length == 1) {
				if (body) {
					writer.tag("/tbody");
					writer.tag("thead");
					body = false;
				}
				writer.tag("tr");
				writer.tag("th", Collections.singletonMap("colspan", Integer.toString(node.getCols().length)));
				writer.text(datas[0]);
				writer.tag("/th");
				writer.tag("/tr");
			} else {
				if (!body) {
					writer.tag("/thead");
					writer.tag("tbody");
					body = true;
				}
				writer.tag("tr");
				Arrays.stream(datas).forEach(data -> {
					writer.tag("td");
					writer.text(data);
					writer.tag("/td");
				});
				writer.tag("/tr");
			}
		}
		if (body) {
			writer.tag("/tbody");
		} else {
			writer.tag("/thead");
		}
	}

	private void thead(TableBlock node) {
		writer.tag("thead");
		writer.tag("tr");
		writer.tag("th", Collections.singletonMap("colspan", Integer.toString(node.getCols().length)));
		writer.text(node.getTitle());
		writer.tag("/th");
		writer.tag("/tr");
		writer.tag("tr");
		Arrays.stream(node.getCols()).forEach(col -> {
			writer.tag("th");
			writer.text(col);
			writer.tag("/th");
		});
		writer.tag("/tr");
	}

	private Map<String, String> buildTableAttr(TableBlock node) {
		Map<String, String> attr = new HashMap<>();
		attr.put("class", node.getType());
		return attr;
	}

	private TableBlock collection(Node node) {
		return (TableBlock) node;
	}


}
