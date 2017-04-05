package test;

import java.util.List;
import java.util.stream.Collectors;

import rds.IAccessibleLeaf;
import rds.IRdsContent;

public class MyMenus {

	static class AccessibleLink implements IAccessibleLeaf<MenuItem> {

		private List<String> accessibleLinkList;

		public AccessibleLink(List<String> accessibleLinkList) {
			this.accessibleLinkList = accessibleLinkList;
		}

		@Override
		public boolean isAccessible(MenuItem rdsContent) {
			return this.accessibleLinkList.contains(rdsContent.getName());
		}
	}

	static abstract class MenuItem implements IRdsContent {

		protected String name;
		protected boolean page;

		protected MenuItem(String name, boolean page) {
			this.name = name;
			this.page = page;
		}

		public String getName() {
			return this.name;
		}

		@Override
		public String toString() {
			return String.format("name:%s|page:%s", this.name, this.page);
		}

		@Override
		public boolean isLeaf() {
			return this.page;
		}
	}

	static class Menu extends MenuItem {

		public Menu(String name) {
			super(name, false);
		}

		private static String[] templates = new String[] {
				"<p class='m0'>%d:%s</p><div>%s</div>",
				"<p class='m1'>%d:%s</p><div>%s</div>",
				"<p class='m2'>%d:%s</p><div>%s</div>",
			};

		@Override
		public String getContent(int level, List<String> childContents) {
			return String.format(templates[level], level, this.name, childContents.stream().collect(Collectors.joining()));
		}
	}

	static class Link extends MenuItem {

		private String link;

		public Link(String name, String link) {
			super(name, true);
			this.link = link;
		}

		private static String[] templates = new String[] {
			"<a class='l0' href='%s'>%d:%s</a>",
			"<a class='l1' href='%s'>%d:%s</a>",
			"<a class='l2' href='%s'>%d:%s</a>",
			"<a class='l3' href='%s'>%d:%s</a>",
		};

		@Override
		public String getContent(int level, List<String> childContents) {
			return String.format(templates[level], this.link, level, this.name);
		}
	}
}
