package blog.template.formater.after;

import java.util.Date;

import org.commonmark.node.Block;
import org.commonmark.node.Visitor;

public class AfterBlock extends Block {

	private Date date;

	public AfterBlock(Date date) {
		this.date = date;
	}

	@Override
	public void accept(Visitor visitor) {
	}

	public boolean isDisplay() {
		return new Date().after(date);
	}

}
