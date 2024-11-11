package blog.template.formater.after;

import org.commonmark.node.Block;
import org.commonmark.node.Visitor;

public class AfterBlock extends Block {

	private String article;

	public AfterBlock(String article) {
		this.article = article;
	}

	@Override
	public void accept(Visitor visitor) {

		System.out.println("accept");
	}

}
