package blog.template.formater.paint;

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

public class PaintGuideParser implements BlockParser {
	private static final String TAG = "paints";

	public static class Factory implements BlockParserFactory {

		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
				return BlockStart.of(new PaintGuideParser(
						new ParseStateReader(state).getAttr("title"),
						new ParseStateReader(state).getAttr("icon"),
						new ParseStateReader(state).getAttr("link")
						))
						.atIndex(state.getIndent());

			}
			return null;
		}


	}

	private PaintGuideBlock block;

	public PaintGuideParser(String title, String icon, String link) {
		this.block = new PaintGuideBlock(title, icon, link);
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
			block.addLine(line.getContent().toString());
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
