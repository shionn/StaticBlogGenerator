package blog.model.formater.youtube;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.commonmark.node.Block;
import org.commonmark.parser.InlineParser;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.BlockParser;
import org.commonmark.parser.block.BlockParserFactory;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

public class YoutubeBlockParser implements BlockParser {

	private static final String TAG = "youtube";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (state.getLine().toString().startsWith("[" + TAG)) {
				String video = getAttr("video", state);
				return BlockStart.of(new YoutubeBlockParser(video)).atIndex(state.getIndent());
			}
			return BlockStart.none();
		}

		private String getAttr(String attr, ParserState state) {
			Matcher m = Pattern.compile(attr + "=\"([^\"]+)\"").matcher(state.getLine());
			if (m.find()) {
				return m.group(1);
			}
			return "";
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
	public void addLine(CharSequence line) {
	}

	public void closeBlock() {
	}

	@Override
	public void parseInlines(InlineParser inlineParser) {
	}

}
