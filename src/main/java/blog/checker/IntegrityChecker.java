package blog.checker;

import java.util.Arrays;

import blog.model.Article;
import blog.model.Menu;
import blog.model.Site;

public class IntegrityChecker {

	public void check(Site site) {
		checkMenuLinkExist(site, site.getMenu());
		checkArticleIsInMenu(site);
	}

	private void checkArticleIsInMenu(Site site) {
		for (Article article : site.getArticles()) {
			if (article.isPublished() && article.isToCheck() && !isInMenu(article, site.getMenu())) {
				System.err.println("Does not exist : " + article);
			}
		}
	}

	private boolean isInMenu(Article article, Menu menu) {
		boolean in = menu.getPath().equals(article.getUrl());
		if (!in)
			for (Menu child : menu.getChildrens()) {
				in |= isInMenu(article, child);
			}
		return in;
	}

	private void checkMenuLinkExist(Site site, Menu menu) {
		if (!menu.isOutsite()) {
			if (!exist(site, menu)) {
				System.err.println("Does not exist : " + menu);
			}
		}
		for (Menu child : menu.getChildrens()) {
			checkMenuLinkExist(site, child);
		}
	}

	private boolean exist(Site site, Menu menu) {
		return groupExist(site, menu) || articleExist(site, menu)
				|| Arrays.asList("index.html").contains(menu.getPath());
	}

	private boolean articleExist(Site site, Menu menu) {
		return site.getArticles().stream().filter(a -> a.getUrl().equals(menu.getPath())).findAny().isPresent();
	}

	private boolean groupExist(Site site, Menu menu) {
		return site
				.getGroups()
				.stream()
				.filter(g -> g.getUrl().equals(menu.getPath()))
				.findAny()
				.isPresent();
	}

}
