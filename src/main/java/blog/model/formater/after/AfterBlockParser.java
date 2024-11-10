package blog.model.formater.after;

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

import blog.model.formater.helper.ParseStateReader;

public class AfterBlockParser implements BlockParser {

	private static final String TAG = "after";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
				String article = new ParseStateReader(state).getAttr("article");
				return BlockStart.of(new AfterBlockParser(article)).atIndex(state.getIndent());
			}
			return BlockStart.none();
		}

	}

	private AfterBlock block;

	public AfterBlockParser(String article) {
		this.block = new AfterBlock(article);
	}

	@Override
	public boolean isContainer() {
		return true;
	}

	@Override
	public boolean canContain(Block block) {
		return true;
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
//			block.addImage(line.toString());
		}
	}

	@Override
	public void closeBlock() {
	}

	@Override
	public void parseInlines(InlineParser inlineParser) {
	}

	@Override
	public boolean canHaveLazyContinuationLines() {
		return false;
	}

	@Override
	public void addSourceSpan(SourceSpan sourceSpan) {

	}

	@Override
	public List<DefinitionMap<?>> getDefinitions() {
		return List.of();
	}

}
