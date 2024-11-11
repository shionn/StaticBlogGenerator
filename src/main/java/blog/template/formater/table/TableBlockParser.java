package blog.template.formater.table;

import java.util.List;

import org.commonmark.node.Block;
import org.commonmark.node.DefinitionMap;
import org.commonmark.node.SourceSpan;
import org.commonmark.parser.InlineParser;
import org.commonmark.parser.SourceLine;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockParser;
import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

import blog.template.formater.helper.ParseStateReader;

public class TableBlockParser implements BlockParser {

	private static final String TAG = "table";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
				String type = new ParseStateReader(state).getAttr("class");
				String title = new ParseStateReader(state).getAttr("title");
				String[] cols = new ParseStateReader(state).getAttr("cols").split(",");
				return BlockStart.of(new TableBlockParser(type, title, cols)).atIndex(state.getIndent());
			}
			return BlockStart.none();
		}

	}

	private TableBlock block;

	public TableBlockParser(String type, String title, String[] cols) {
		this.block = new TableBlock(type, title, cols);
	}

	@Override
	public boolean isContainer() {
		return false;
	}

	@Override
	public boolean canContain(Block block) {
		return false;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public BlockContinue tryContinue(ParserState parserState) {
		if (new ParseStateReader(parserState).endTag(TAG)) {
			return BlockContinue.finished();
		}
		return BlockContinue.atIndex(parserState.getIndex());
	}

	@Override
	public void addLine(SourceLine line) {
		if (!line.getContent().toString().contains(TAG)) {
			block.addData(line.getContent().toString().split("\t"));
		}
	}

	public void closeBlock() {
	}

	@Override
	public void parseInlines(InlineParser inlineParser) {
	}

	@Override
	public boolean canHaveLazyContinuationLines() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSourceSpan(SourceSpan sourceSpan) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DefinitionMap<?>> getDefinitions() {
		return List.of();
	}

}
