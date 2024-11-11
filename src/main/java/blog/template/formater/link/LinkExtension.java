package blog.template.formater.link;

import java.util.Map;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer.Builder;
import org.commonmark.renderer.html.HtmlRenderer.HtmlRendererExtension;

public class LinkExtension implements HtmlRendererExtension {

	@Override
	public void extend(Builder builder) {
		builder.attributeProviderFactory(new AttributeProviderFactory() {
			@Override
			public AttributeProvider create(AttributeProviderContext context) {
				return new AttributeProvider() {
					@Override
					public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
						if (node instanceof Link) {
							String src = ((Link) node).getDestination();
							if (src.startsWith("http")) {
								attributes.put("target", "_blank");
							}
						}
					}
				};
			}
		});
	}

}
