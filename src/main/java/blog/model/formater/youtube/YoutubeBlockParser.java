package blog.model.formater.youtube;

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

public class YoutubeBlockParser implements BlockParser {

	private static final String TAG = "youtube";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
				String video = new ParseStateReader(state).getAttr("video");
				return BlockStart.of(new YoutubeBlockParser(video)).atIndex(state.getIndent());
			}
			return BlockStart.none();
		}

	}

	private YoutubeBlock block;

	public YoutubeBlockParser(String video) {
		this.block = new YoutubeBlock(video);
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
		return BlockContinue.finished();
	}

	@Override
	public void addLine(SourceLine line) {
	}

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
