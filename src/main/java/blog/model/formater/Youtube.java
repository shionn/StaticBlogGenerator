package blog.model.formater;

import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;

import blog.model.formater.youtube.YoutubeBlockParser;
import blog.model.formater.youtube.YoutubeRenderer;

public class Youtube implements FormaterDescription {

	@Override
	public BlockParserFactory blockParserFactory() {
		return new YoutubeBlockParser.Factory();
	}

	@Override
	public NodeRenderer createNodeRenderer(HtmlNodeRendererContext context) {
		return new YoutubeRenderer(context);
	}

}
