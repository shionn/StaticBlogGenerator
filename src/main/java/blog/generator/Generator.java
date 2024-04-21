package blog.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import blog.model.Article;
import blog.model.Group;
import blog.model.Site;
import blog.model.builder.SiteBuilder;
import blog.template.TemplateEngineBuilder;

public class Generator {


	public static void main(String[] args) throws IOException {
		new Generator().generate();
	}

	public void generate() throws IOException {
		Site site = new SiteBuilder().build();
		prepareDestFolder(site);
		TemplateEngine engine = new TemplateEngineBuilder().build();
		engine.process("template", buildIndexContext(site),
				new FileWriter(Configuration.get().getTargetFolder() + "/index.html"));
		engine.process("template", build404Context(site),
				new FileWriter(Configuration.get().getTargetFolder() + "/404.html"));
		if (Configuration.get().isDraftEnable()) {
			engine.process("template", buildDraftContext(site),
					new FileWriter(Configuration.get().getTargetFolder() + "/draft.html"));
		}
		for (Article article : site.getArticles()) {
			new File(Configuration.get().getTargetFolder() + "/" + article.getFolder()).mkdirs();
			System.out.println("generate " + article.getTitle() + " into " + article.getUrl());
			engine.process("template", buildArticleContext(site, article),
					new FileWriter(Configuration.get().getTargetFolder() + "/" + article.getUrl()));
		}
		for (Group group : site.getGroups()) {
			engine.process("template",
					buildGroupContext(site, group),
					new FileWriter(Configuration.get().getTargetFolder() + "/" + group.getUrl()));
		}
		System.out.println("Genaration done for " + site.getBase());
	}

	private Context buildIndexContext(Site site) {
		Map<String, Object> params = buildParam(site, "index");
		params.put("scripts", site.getJs());
		return new Context(Locale.FRANCE, params);
	}

	private Context build404Context(Site site) {
		return new Context(Locale.FRANCE, buildParam(site, "404"));
	}

	private Context buildDraftContext(Site site) {
		Map<String, Object> params = buildParam(site, "draft");
		params.put("scripts", site.getJs());
		return new Context(Locale.FRANCE, params);
	}

	private Context buildGroupContext(Site site, Group group) {
		Map<String, Object> params = buildParam(site, "group");
		params.put("group", group);
		params.put("scripts", group.getJs());
		return new Context(Locale.FRANCE, params);
	}

	private Context buildArticleContext(Site site, Article article) {
		Map<String, Object> params = buildParam(site, "article");
		params.put("article", article);
		params.put("scripts", article.getJs());
		return new Context(Locale.FRANCE, params);
	}

	private Map<String, Object> buildParam(Site site, String mode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("site", site);
		param.put("mode", mode);
		return param;
	}

	private void prepareDestFolder(Site site) throws IOException {

		new File(Configuration.get().getTargetFolder()).mkdir();
		if (Configuration.get().isCategopryEnable()) {
			new File(Configuration.get().getCategoryFolder()).mkdir();
		}
		if (Configuration.get().isTagEnable()) {
			new File(Configuration.get().getTagFolder()).mkdir();
		}
		if (Configuration.get().isDraftEnable()) {
			File folder = new File(Configuration.get().getDraftFolder());
			folder.mkdir();
			Arrays.stream(folder.listFiles()).forEach(f -> f.delete());
		}
	}


}
