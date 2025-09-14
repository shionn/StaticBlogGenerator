package blog.template;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import blog.generator.Configuration;

public class TemplateEngineBuilder {

	public TemplateEngine build() {
		TemplateEngine engine = new TemplateEngine();
		engine.setTemplateResolver(initResolver());
		return engine;
	}

	private FileTemplateResolver initResolver() {
		FileTemplateResolver resolver = new FileTemplateResolver();
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setPrefix(Configuration.get().getTemplateFolder());
		resolver.setSuffix(".html");
		return resolver;
	}

}
