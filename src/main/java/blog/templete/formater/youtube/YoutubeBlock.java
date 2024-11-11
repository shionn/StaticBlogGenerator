package blog.templete.formater.youtube;

import org.commonmark.node.Block;
import org.commonmark.node.Visitor;

public class YoutubeBlock extends Block {

	private String video;

	public YoutubeBlock(String video) {
		this.video = video;
	}

	@Override
	public void accept(Visitor visitor) {
	}

	public String getVideo() {
		return video;
	}

}
