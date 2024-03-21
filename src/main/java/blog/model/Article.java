package blog.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import blog.model.Metadata.Type;
import blog.model.formater.ContentFormater;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Article {

	private final Metadata metadata;
	@Getter
	private final Group category;
	@Getter
	private final List<Group> tags;
	private final File file;

	private final Properties configuration;

	public String getLogo() {
		if (metadata.getLogo() != null) {
			return metadata.getLogo();
		}
		return null;
	}

	public boolean is(Type type) {
		return metadata.getType() == type;
	}

	public boolean isPublished() {
		return metadata.isPublished();
	}

	public String getTitle() {
		return metadata.getTitle();
	}

	public String getFolder() {
		if (metadata.isPublished()) {
			return metadata.getYear() + "/";
		}
		return "draft/";
	}

	public String getUrl() {
		return getFolder() + getName() + ".html";
	}

	private String getName() {
		return file.getName().substring(0, file.getName().lastIndexOf('.'));
	}

	public String getRawContent() throws IOException {
		return FileUtils.readFileToString(new File(file.getPath().replaceAll("json", "md")), StandardCharsets.UTF_8);
	}

	public String getShortContent() throws IOException {
		return new ContentFormater().shortPost(getRawContent(),
				Integer.parseInt(configuration.getProperty("article.shortsize")));
	}

	public String getFullContent() throws IOException {
		return new ContentFormater().fullPost(getRawContent());
	}

	public Date getDate() {
		return metadata.getDate();
	}

	public Date getUpdateDate() {
		return metadata.getUpdated();
	}

	public String getFormatedDate() {
		return new SimpleDateFormat(configuration.getProperty("date.format"), Locale.FRANCE).format(getDate());
	}

	public String getFormatedUpdateDate() {
		return new SimpleDateFormat(configuration.getProperty("date.format"), Locale.FRANCE).format(getUpdateDate());
	}

	public int getYear() {
		return metadata.getYear();
	}

	public int getMonth() {
		return metadata.getMonth();
	}

	public String getAuthor() {
		return metadata.getAuthor();
	}

	public List<String> getJs() {
		return metadata.getJs();
	}

}
