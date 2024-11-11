package blog.template.formater.image;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer.Builder;
import org.commonmark.renderer.html.HtmlRenderer.HtmlRendererExtension;

public class ImageExtension implements HtmlRendererExtension {

	@Override
	public void extend(Builder builder) {
		builder.attributeProviderFactory(new AttributeProviderFactory() {
			@Override
			public AttributeProvider create(AttributeProviderContext context) {
				return new AttributeProvider() {
					@Override
					public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
						if (node instanceof Image) {
							String src = ((Image) node).getDestination();
							if (src.startsWith("/")) {
								src = StringUtils.prependIfMissing(src, "/img");
								attributes.put("src", src);
							}
						}
					}
				};
			}
		});
	}

}
