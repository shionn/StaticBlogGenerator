package blog.model.formater;

import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;

import blog.model.formater.after.AfterBlockParser;
import blog.model.formater.after.AfterRenderer;

public class After implements FormaterDescription {

	@Override
	public BlockParserFactory blockParserFactory() {
		return new AfterBlockParser.Factory();
	}

	@Override
	public NodeRenderer createNodeRenderer(HtmlNodeRendererContext context) {
		return new AfterRenderer(context);
	}

}
