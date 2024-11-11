package blog.template.formater.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.commonmark.parser.block.ParserState;

public class ParseStateReader {

	private ParserState state;

	public ParseStateReader(ParserState state) {
		this.state = state;
	}

	public boolean startTag(String tag) {
		return state.getLine().getContent().toString().startsWith("[" + tag);
	}

	public boolean endTag(String tag) {
		return state.getLine().getContent().toString().equals("[/" + tag + "]");
	}

	public String getAttr(String attr) {
		Matcher m = Pattern.compile(attr + "=\"([^\"]+)\"").matcher(state.getLine().getContent().toString());
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public int getAttrInt(String attr) {
		Matcher m = Pattern.compile(attr + "=(\\d+)").matcher(state.getLine().getContent().toString());
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		}
		return 0;
	}

	public Date getAttrDate(String attr) {
		try {
			return new SimpleDateFormat("yyyy/MM/dd").parse(getAttr(attr));
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
	}

}
