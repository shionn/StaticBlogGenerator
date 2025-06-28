package blog.generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import blog.model.Article;
import blog.model.Group;
import blog.model.Site;

public class SitemapXmlGenerator {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public void generate(Site site) throws IOException {
		try (FileWriter w = new FileWriter(Configuration.get().getTargetFolder() + "/sitemap.xml");
				BufferedWriter bw = new BufferedWriter(w)) {
			bw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.newLine();
			bw.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
			bw.newLine();
			append(bw, site, "index.html", site.getHomePageArticles(0).get(0).getDate());
			for (int page = 1; page <= site.getHomePageCount(); page++) {
				append(bw, site, "index-" + page + ".html", site.getHomePageArticles(0).get(0).getDate());
			}
			for (Article article : site.getArticles()) {
				if (article.isPublished()) {
					append(bw, site, article.getUrl(),
							article.getUpdateDate() == null ? article.getDate() : article.getUpdateDate());
				}
			}
			for (Group group : site.getGroups()) {
				if (!group.getArticles().isEmpty()) {
					append(bw, site, group.getUrl(), group.getArticles().get(0).getDate());
				}
			}

			bw.append("</urlset>");
		}
		
	}

	private void append(BufferedWriter bw, Site site, String url, Date date) throws IOException {

		bw.append("\t<url>");
		bw.newLine();
		bw.append("\t\t<loc>").append(site.getBase()).append("/" + url).append("</loc>");
		bw.newLine();
		bw.append("\t\t<lastmod>").append(dateFormat.format(date)).append("</lastmod>");
		bw.newLine();
		bw.append("\t</url>");
		bw.newLine();
		
		
	}

}
