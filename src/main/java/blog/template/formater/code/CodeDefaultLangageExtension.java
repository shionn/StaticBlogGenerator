package blog.template.formater.code;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer.Builder;
import org.commonmark.renderer.html.HtmlRenderer.HtmlRendererExtension;

public class CodeDefaultLangageExtension implements HtmlRendererExtension {

	@Override
	public void extend(Builder builder) {
		builder.attributeProviderFactory(new AttributeProviderFactory() {
			@Override
			public AttributeProvider create(AttributeProviderContext context) {
				return new AttributeProvider() {
					@Override
					public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
						if (node instanceof FencedCodeBlock) {
							String type = ((FencedCodeBlock) node).getInfo();
							if (StringUtils.isBlank(type)) {
								type = "java";
							}
							attributes.put("class", type);
						}
					}
				};
			}
		});
	}

}
