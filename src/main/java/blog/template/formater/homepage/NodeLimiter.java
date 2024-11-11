package blog.template.formater.homepage;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.parser.Parser.ParserExtension;
import org.commonmark.parser.PostProcessor;

public class NodeLimiter implements PostProcessor, ParserExtension {

	private int limit;

	public NodeLimiter(int limit) {
		this.limit = limit;
	}

	@Override
	public Node process(Node root) {
		Node current = root.getFirstChild();
		int count = 0;
		while (current != null) {
			Node next = current.getNext();
			if (++count > limit) {
				current.unlink();
			}
			current = next;
		}
		return root;
	}

	@Override
	public void extend(Parser.Builder builder) {
		builder.postProcessor(this);
	}

}
