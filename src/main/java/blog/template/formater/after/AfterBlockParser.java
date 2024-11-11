package blog.template.formater.after;

import java.util.Date;
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

public class AfterBlockParser implements BlockParser {

	private static final String TAG = "after";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
//				System.out.println("Start AFTER on : " + state.getLine().getContent());

				Date date = new ParseStateReader(state).getAttrDate("date");
				return BlockStart.of(new AfterBlockParser(date))
						.atColumn(state.getLine().getContent().length());
			}
			return BlockStart.none();
		}
	}

	private AfterBlock block;

	public AfterBlockParser(Date date) {
		this.block = new AfterBlock(date);
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
	public BlockContinue tryContinue(ParserState state) {
//		System.out.println("Try Continue on : " + state.getLine().getContent());
		if (new ParseStateReader(state).endTag(TAG)) {
//			System.out.println("End AFTER on : " + state.getLine().getContent());
			return BlockContinue.finished();
		}
		return BlockContinue.atIndex(state.getIndex());
	}

	@Override
	public void addLine(SourceLine line) {
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
		getBlock().addSourceSpan(sourceSpan);
	}

	@Override
	public List<DefinitionMap<?>> getDefinitions() {
		return List.of();
	}

}
