package blog.model.formater.after;

import org.commonmark.internal.DocumentBlockParser;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

import blog.model.formater.helper.ParseStateReader;

public class AfterBlockParser extends DocumentBlockParser {

	private static final String TAG = "after";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
				String article = new ParseStateReader(state).getAttr("article");
				return BlockStart.of(new DocumentBlockParser())
						.atIndex(state.getLine().getContent().toString().length());
//				return BlockStart.of(new AfterBlockParser(article, matchedBlockParser))
//////						.atIndex(state.getIndex() + state.getLine().getContent().toString().length())
//						.atIndex(state.getLine().getContent().toString().length());
			}
			return BlockStart.none();
		}

	}

//	private AfterBlock block;
	private MatchedBlockParser parser;

	public AfterBlockParser(String article, MatchedBlockParser parser) {
		this.parser = parser;
//		this.block = new AfterBlock(article);
	}

//	@Override
//	public boolean isContainer() {
//		return true;
//	}
//
//	@Override
//	public boolean canContain(Block block) {
//		return true;
//	}

//	@Override
//	public Block getBlock() {
//		return block;
//	}

	@Override
	public BlockContinue tryContinue(ParserState parserState) {
		if (new ParseStateReader(parserState).endTag(TAG)) {
			return BlockContinue.finished();
		}
		return BlockContinue.atIndex(parserState.getIndex());
	}

//	@Override
//	public void addLine(SourceLine line) {
//		if (!line.getContent().toString().contains(TAG)) {
//			super
//		}
//	}

//	@Override
//	public void closeBlock() {
//	}
//
//	@Override
//	public void parseInlines(InlineParser inlineParser) {
//	}
//
//	@Override
//	public boolean canHaveLazyContinuationLines() {
//		return true;
//	}

//	@Override
//	public void addSourceSpan(SourceSpan sourceSpan) {
//		if (sourceSpan.)
//		sourceSpan.
//		block.addSourceSpan(sourceSpan);
//	}

//	@Override
//	public List<DefinitionMap<?>> getDefinitions() {
//		return List.of();
//	}

}
