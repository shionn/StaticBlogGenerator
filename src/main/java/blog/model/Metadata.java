package blog.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.ToString;

@Getter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@ToString
public class Metadata {
	public enum Type {
		page,
		post
	}

	private Type type;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date date;
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private List<Date> updated;
	private String title;
	private String category;
	private List<String> tags;
	private List<String> js = new ArrayList<String>();
	private boolean published = true;
	private boolean toCheck = true;
	private String logo;
	private String author;

	public int getYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

}