package blog.template.formater;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Attributes {

	private Map<String, String> attrs = new HashMap<>();

	public Attributes href(String link) {
		return add("href", link);
	}

	public Attributes fa(String icon) {
		if (StringUtils.isNotBlank(icon)) {
			return clazz("fa fa-" + icon);
		}
		return this;
	}

	public Attributes target(String target) {
		return add("target", target);
	}

	private Attributes clazz(String value) {
		return add("class", value);
	}

	private Attributes add(String attr, String value) {
		attrs.put(attr, value);
		return this;
	}

	public Map<String, String> build() {
		return attrs;
	}

}
