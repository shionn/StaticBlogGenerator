package blog.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import blog.model.Article;
import blog.model.Group;
import blog.model.Site;

public class SitemapTxtGenerator {

	public void generate(Site site) throws IOException {
		try (FileWriter w = new FileWriter(Configuration.get().getTargetFolder() + "/sitemap.txt");
				BufferedWriter bw = new BufferedWriter(w)) {
			append(bw, site, "index.html");
			for (int page = 1; page <= site.getHomePageCount(); page++) {
				append(bw, site, "index-" + page + ".html");
			}
			for (Article article : site
					.getArticles()
					.stream()
					.filter(Article::isPublished)
					.sorted((a, b) -> a.getUrl().compareTo(b.getUrl()))
					.toList()) {
				append(bw, site, article.getUrl());
			}
			for (Group group : site
					.getGroups()
					.stream()
					.filter(Group::isGenerated)
					.sorted((a, b) -> a.getUrl().compareTo(b.getUrl()))
					.toList()) {
				append(bw, site, group.getUrl());
			}
		}
	}

	private void append(BufferedWriter bw, Site site, String url) throws IOException {
		bw.append(site.getBase()).append("/" + url);
		bw.newLine();
	}
}
