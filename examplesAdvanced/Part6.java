package ua.nure.swirchkov.Task3.part6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part6 {

	private static class Info {

		private Boolean ignore;
		private String login;
		private String mail;
		private String group;

		public Info(Boolean ignore, String login, String mail, String group) {
			this.ignore = ignore;
			this.group = group;
			this.login = login;
			this.mail = mail;
		}
		
		public Boolean getIgnore() {
			return ignore;
		}
		public void setIgnore(Boolean value) {
			ignore = value;
		}
		
		public String getLogin() {
			return login;
		}
		
		public String getMail() {
			return mail;
		}
		
		public String getGroup() {
			return group;
		}
		public void setGroup(String value) {
			group = value;
		}
		
	}

	private static final String MAILS = "part6_mails.txt";

	private static final String GROUPS = "part6_groups.txt";

	private static final String USERS = "part6_users.txt";

	private static final String ENCODING = "Cp1251";

	private static String readLine(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		char c = (char) reader.read();
		while (c != '\n' && c != 65535) {
			sb.append(c);
			c = (char) reader.read();
		}

		return sb.toString();
	}

	private static List<Info> parseMailFile(Reader mailsReader) throws IOException {
		ArrayList<Info> list = new ArrayList<>();

		String line = "";

		while (!(line = readLine(mailsReader)).equals("")) {
			Pattern pat = Pattern.compile("#*[a-zA-Z0-9à-ÿÀ-ß]+;[a-zA-Z0-9à-ÿÀ-ß]+@[a-zA-Z0-9]+\\.[a-zA-Z]+");
			Pattern loginPat = Pattern.compile("#*[a-zA-Z0-9à-ÿÀ-ß]+;");
			Pattern mailPattern = Pattern.compile("[a-zA-Z0-9à-ÿÀ-ß]+@[a-zA-Z0-9]+\\.[a-zA-Z]+");

			Matcher expressionMatcher = pat.matcher(line);

			while (expressionMatcher.find()) {
				String expression = expressionMatcher.group();

				Matcher loginMatcher = loginPat.matcher(expression);
				Matcher mailMatcher = mailPattern.matcher(expression);

				if (!loginMatcher.find()) {
					System.err.println("login matching error in mail reader");
				}

				String login = loginMatcher.group();
				login = login.substring(0, login.length() - 1);

				if (!mailMatcher.find()) {
					System.err.println("mail matching error in mail reader");
				}

				String mail = mailMatcher.group();

				Info info = new Info(login.charAt(0) == '#', login.charAt(0) != '#' ? login : login.substring(1), mail,
						"");

				list.add(info);
			}
		}
		return list;
	}

	private static List<Info> parseGroupFile(Reader groupsReader, List<Info> list) throws IOException {

		String line = "";

		while (!(line = readLine(groupsReader)).equals("")) {
			Pattern pat = Pattern.compile("#*[a-zA-Z0-9à-ÿÀ-ß]+;[a-zA-Zà-ÿÀ-ß]+[0-9]+");
			Pattern loginPat = Pattern.compile("#*[a-zA-Z0-9à-ÿÀ-ß]+");
			Pattern groupPattern = Pattern.compile("[a-zA-Z0-9à-ÿÀ-ß]+[0-9]+");

			Matcher expressionMatcher = pat.matcher(line);

			while (expressionMatcher.find()) {
				String expression = expressionMatcher.group();

				Matcher loginMatcher = loginPat.matcher(expression);
				Matcher groupMatcher = groupPattern.matcher(expression);

				if (!loginMatcher.find()) {
					System.err.println("login matching error in mail reader");
				}

				String login = loginMatcher.group();
				String group = "";

				if (!groupMatcher.find()) {
					System.err.println("mail matching error in mail reader");
				} else {
					while (groupMatcher.find()) {
						group = groupMatcher.group();
					}
				}

				Boolean ignore = login.charAt(0) == '#';

				if (ignore) {
					login = login.substring(1);
				}

				Info info = new Info(ignore, login, "", group);

				Boolean found = false;

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getLogin().equals(login)) {
						list.get(i).setGroup(group);

						if (ignore) {
							list.get(i).setIgnore(ignore);
						}

						found = true;
						break;
					}
				}

				if (!found) {
					list.add(info);
				}
			}
		}

		return list;
	}

	private static void writeUserFile(Writer writer, List<Info> list) throws IOException {
		writer.write("Login;Email;Group;\n");

		for (Info info : list) {
			if (info.getIgnore()) {
				continue;
			}
			writer.write(info.getLogin() + ";" + info.getMail() + ";" + info.getGroup() + ";\n");
			System.out.print(info.getLogin() + ";" + info.getMail() + ";" + info.getGroup() + ";\n");
		}

	}

	public static void filter(Reader mailsReader, Reader groupsReader, Writer usersWriter) throws IOException {
		List<Info> infos = parseMailFile(mailsReader);
		infos = parseGroupFile(groupsReader, infos);
		writeUserFile(usersWriter, infos);
	}

	public static void main(String[] args) throws IOException {

		InputStreamReader mailsReader = new InputStreamReader(new FileInputStream(MAILS), ENCODING);
		InputStreamReader groupsReader = new InputStreamReader(new FileInputStream(GROUPS), ENCODING);
		OutputStreamWriter usersWriter = new OutputStreamWriter(new FileOutputStream(USERS), ENCODING);

		filter(mailsReader, groupsReader, usersWriter);

		mailsReader.close();
		groupsReader.close();
		usersWriter.close();
	}

}
