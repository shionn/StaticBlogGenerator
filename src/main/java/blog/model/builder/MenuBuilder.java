package blog.model.builder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import blog.model.Menu;

public class MenuBuilder {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

	public Menu build() throws IOException {
		Menu root = new Menu("root", "index", null, null);
		Menu parent = root;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream("content/menu.txt")))) {
			String line = nextLine(reader);
			while (line != null) {
				if (deep(line) > deep(parent)) {
					parent = parent.getChildrens().get(parent.getChildrens().size() - 1);
				}
				while (deep(line) < deep(parent)) {
					parent = parent.getParent();
				}
				line = line.strip();
				Menu menuLine = buildMenuLine(parent, line);
				if (menuLine.isVisible())
					parent.add(menuLine);
				line = nextLine(reader);
			}
		}
		return root;
	}

	private Menu buildMenuLine(Menu parent, String line) {
		String[] cols = line.split("\\t");
		String name = cols[0];
		String url = cols[1];
		Date date;
		try {
			date = cols.length > 2 ? dateFormat.parse(cols[2]) : null;
		} catch (ParseException e) {
			e.printStackTrace();
			date = null;
		}
		return new Menu(url, name, parent, date);
	}

	private String nextLine(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null && line.startsWith("//")) {
			line = reader.readLine();
		}
		return line;
	}

	private int deep(Menu menu) {
		if (menu.getParent() == null) {
			return 0;
		}
		return 1 + deep(menu.getParent());
	}

	private int deep(String line) {
		if (line.charAt(0) == '\t') {
			return deep(line.substring(1)) + 1;
		}
		return 0;
	}

}
