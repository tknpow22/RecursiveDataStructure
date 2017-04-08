package test2.items;

import java.util.List;

import rds2.IAccessibleLeaf;
import rds2.Rds;

public class MyMenus {

	public static class AccessibleLink implements IAccessibleLeaf {

		private List<String> accessibleLinks;

		public AccessibleLink(List<String> accessibleLinks) {
			this.accessibleLinks = accessibleLinks;
		}

		@Override
		public boolean isAccessible(Rds rds) {
			MenuItem menuItem = (MenuItem) rds;
			return this.accessibleLinks.contains(menuItem.getName());
		}
	}

	public static abstract class MenuItem extends Rds {

		protected String name;
		protected boolean page;

		protected MenuItem() {
			super();
		}

		protected MenuItem(int level, String name, boolean page) {
			super(level);
			this.name = name;
			this.page = page;
		}

		public String getName() {
			return this.name;
		}

		@Override
		protected boolean isLeaf() {
			return this.page;
		}

		@Override
		protected String toStringValue() {
			return String.format("name:%s|page:%s", this.name, this.page);
		}
	}

	public static class Menu extends MenuItem {

		public Menu() {
			super();
		}

		public Menu(int level, String name) {
			super(level, name, false);
		}

		@Override
		protected Rds getInstance() {
			return new Menu();
		}

		@Override
		protected Rds getInstance(int level) {
			return new Menu(level, this.name);
		}

		private static String[] templates = new String[] {
				"<p class='m0'>%d:%s</p><div>%s</div>",
				"<p class='m1'>%d:%s</p><div>%s</div>",
				"<p class='m2'>%d:%s</p><div>%s</div>",
			};

		@Override
		protected String getContent(int level, String childContents) {
			return String.format(templates[level], level, this.name, childContents);
		}
	}

	public static class Link extends MenuItem {

		private String link;

		public Link() {
			super();
		}

		public Link(int level, String name, String link) {
			super(level, name, true);
			this.link = link;
		}

		@Override
		protected Rds getInstance() {
			return new Link();
		}

		@Override
		protected Rds getInstance(int level) {
			return new Link(level, this.name, this.link);
		}

		private static String[] templates = new String[] {
			"<a class='l0' href='%s'>%d:%s</a>",
			"<a class='l1' href='%s'>%d:%s</a>",
			"<a class='l2' href='%s'>%d:%s</a>",
			"<a class='l3' href='%s'>%d:%s</a>",
		};

		@Override
		protected String getContent(int level, String childContents) {
			return String.format(templates[level], this.link, level, this.name);
		}
	}
}
