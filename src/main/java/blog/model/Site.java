package blog.model;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import blog.model.Metadata.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Site {
	private Properties configuration;
	private List<Article> articles;
	private Collection<Group> groups;
	private Menu menu;

	public List<Article> getLastArticles() {
		return articles.stream()
				.filter(a -> a.is(Type.post))
				.sorted((a, b) -> -a.getDate().compareTo(b.getDate()))
				.filter(Article::isPublished)
				.limit(5)
				.collect(Collectors.toList());
	}

	public List<Article> getDrafts() {
		return articles.stream()
				.sorted((a, b) -> -a.getDate().compareTo(b.getDate()))
				.filter(a -> !a.isPublished())
				.collect(Collectors.toList());
	}

	public List<Group> getTags() {
		return groups.stream()
				.filter(c -> c.getType() == Group.Type.Tag)
				.filter(t -> t.isGenerated())
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.collect(Collectors.toList());
	}

	public Collection<Group> getCategories() {
		return groups.stream()
				.filter(c -> c.getType() == Group.Type.Category)
				.filter(t -> t.isGenerated())
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.collect(Collectors.toList());
	}

	public Collection<Article> getPosts() {
		return articles.stream()
				.filter(a -> a.is(Type.post))
				.sorted((a, b) -> -a.getDate().compareTo(b.getDate()))
				.filter(Article::isPublished)
				.collect(Collectors.toList());
	}

	// j'aime pas trop cette methode je vais la revoir
	public List<ArticleTree> getTreePost() {
		Comparator<? super Integer> reverse = (a, b) -> Integer.compare(b, a);
		Predicate<Article> filter = article -> article.is(Type.post) && article.isPublished();
		return articles.stream().filter(filter).map(Article::getYear).distinct().sorted(reverse).map(year -> {
			List<ArticleTree> subGroup = articles.stream()
					.filter(filter)
					.filter(article -> article.getYear() == year)
					.map(Article::getMonth)
					.distinct()
					.sorted(reverse)
					.map(month -> {
						List<Article> subArticles = articles.stream()
								.filter(filter)
								.filter(a -> a.getYear() == year && a.getMonth() == month)
								.toList();
						return ArticleTree.builder()
								.name(new SimpleDateFormat("MMMM", Locale.FRANCE).format(subArticles.get(0).getDate()))
								.subArticles(subArticles)
								.build();
					})
					.toList();
			return ArticleTree.builder().name(Integer.toString(year)).subGroups(subGroup).build();
		}).toList();
	}

	public List<String> getJs() {
		return articles.stream().flatMap(a -> a.getJs().stream()).distinct().collect(Collectors.toList());
	}

	public String getBase() {
		return configuration.getProperty("base");
	}

	public String getTargetFolder() {
		return configuration.getProperty("target");
	}

	public boolean isCategopryEnable() {
		return Boolean.parseBoolean(configuration.getProperty("category.enable"));
	}

	public String getCategoryFolder() {
		return getTargetFolder() + "/" + configuration.getProperty("category.subfolder");
	}

	public boolean isTagEnable() {
		return Boolean.parseBoolean(configuration.getProperty("tag.enable"));
	}

	public String getTagFolder() {
		return getTargetFolder() + "/" + configuration.getProperty("tag.subfolder");
	}

	public boolean isDraftEnable() {
		return Boolean.parseBoolean(configuration.getProperty("draft.enable"));
	}

	public String getDraftFolder() {
		return getTargetFolder() + "/" + configuration.getProperty("draft.subfolder");
	}

}
