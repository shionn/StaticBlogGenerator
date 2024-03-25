package blog.generator;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private Properties props;

	private Configuration(Properties props) {
		this.props = props;
	}

	public static Configuration get() {
		return LazyLoader.instance;
	}

	private static final class LazyLoader {
		private static Configuration instance = build();

		private static Configuration build() {
			try {
				Properties props = new Properties();
				props.load(new FileReader("configuration.properties"));
				return new Configuration(props);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getBase() {
		return props.getProperty("base");
	}

	public String getTargetFolder() {
		return props.getProperty("target");
	}

	public boolean isCategopryEnable() {
		return Boolean.parseBoolean(props.getProperty("category.enable"));
	}

	public String getCategoryFolder() {
		return getTargetFolder() + "/" + props.getProperty("category.subfolder");
	}

	public boolean isTagEnable() {
		return Boolean.parseBoolean(props.getProperty("tag.enable"));
	}

	public String getTagFolder() {
		return getTargetFolder() + "/" + props.getProperty("tag.subfolder");
	}

	public boolean isDraftEnable() {
		return Boolean.parseBoolean(props.getProperty("draft.enable"));
	}

	public String getDraftFolder() {
		return getTargetFolder() + "/" + props.getProperty("draft.subfolder");
	}

	public boolean isGalleryForceThumbnail() {
		return Boolean.parseBoolean(props.getProperty("gallery.forcethumbnail"));
	}

}
