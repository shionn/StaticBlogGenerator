package blog.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import blog.model.Article;
import blog.model.Site;

public class CalendarDayMissing {

	public void generate(Site site) throws ParseException {
		if (Configuration.get().isDraftCalendarEnable()) {
			List<Date> dates = new ArrayList<Date>();
			Date date = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE)
					.parse(Configuration.get().getDraftCalendarStartDate());
			for (int i = 0; i < 15; i++) {
				if (date.after(new Date()) && draftNotExists(site, date)) {
					dates.add(date);
				}
				Calendar next = Calendar.getInstance();
				next.setTime(date);
				next.add(Calendar.DAY_OF_YEAR, Configuration.get().getDraftCalendarDay());
				date = next.getTime();
			}
			site.setDraftMissingDates(dates);
		}

	}

	private boolean draftNotExists(Site site, Date date) {
		List<Article> drafts = site.getAutoPublishedDrafts();
		for (Article a : drafts) {
			if (Math.abs(a.getDate().getTime() - date.getTime()) <= 1000 * 60 * 60 * 3) {
				return false;
			}
		}
		return true;
	}

}
