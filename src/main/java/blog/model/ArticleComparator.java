package blog.model;

import java.util.Comparator;

public class ArticleComparator implements Comparator<Article> {

	@Override
	public int compare(Article o1, Article o2) {
		int res = o2.getDate().compareTo(o1.getDate());
		if (res == 0) {
			res = o1.getTitle().compareTo(o2.getTitle());
		}
		return res;
	}

}
