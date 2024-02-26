package blog.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleTree {

	private final String name;
	private List<ArticleTree> subGroups;
	private List<Article> subArticles;

	public int getSize() {
		int size = 0;
		if (subGroups != null) {
			for (ArticleTree g : subGroups) {
				size += g.getSize();
			}
		}
		if (subArticles !=null) {
			size += subArticles.size();
		}
		return size;
	}
}
