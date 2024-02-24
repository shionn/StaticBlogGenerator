package blog.model.builder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import blog.model.Menu;

public class MenuBuilder {

	public Menu build() throws IOException {
		Menu root = new Menu("root", "index", null);
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
				parent.add(new Menu(line.split("\\t")[1], line.split("\\t")[0], parent));
				line = nextLine(reader);
			}
		}
		return root;
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
