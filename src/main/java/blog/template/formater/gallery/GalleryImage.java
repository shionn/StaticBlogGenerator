package blog.template.formater.gallery;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GalleryImage {
	private String url;
	private String position;
	private String title;

}
