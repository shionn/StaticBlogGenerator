package blog.template.formater.gallery;

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

public class GalleryBlockParser implements BlockParser {

	private static final String TAG = "gallery";

	public static class Factory implements BlockParserFactory {
		@Override
		public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
			if (new ParseStateReader(state).startTag(TAG)) {
				int w = new ParseStateReader(state).getAttrInt("w");
				int h = new ParseStateReader(state).getAttrInt("h");
				return BlockStart.of(new GalleryBlockParser(w, h)).atIndex(state.getIndent());
			}
			return BlockStart.none();
		}

	}

	private GalleryBlock block;

	public GalleryBlockParser(int w, int h) {
		this.block = new GalleryBlock(w, h);
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
	public void addLine(SourceLine line) {
		if (!line.getContent().toString().contains(TAG)) {
			block.addImage(line.getContent().toString());
		}
	}

	@Override
	public void addSourceSpan(SourceSpan sourceSpan) {

	}

	@Override
	public List<DefinitionMap<?>> getDefinitions() {
		return List.of();
	}

}
