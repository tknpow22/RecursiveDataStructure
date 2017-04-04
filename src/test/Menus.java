package test;

import java.util.List;

import rds.AccessibleLeaf;
import rds.IRdsContent;

public class Menus {

	static class AccessibleLink implements AccessibleLeaf<MenuItem> {

		private List<String> accessibleLinkList;

		public AccessibleLink(List<String> accessibleLinkList) {
			this.accessibleLinkList = accessibleLinkList;
		}

		@Override
		public boolean isAccessible(MenuItem rdsContent) {
			return this.accessibleLinkList.contains(rdsContent.getName());
		}
	}

	static class MenuItem implements IRdsContent {

		private String name;
		private boolean page;

		public MenuItem(String name, boolean page) {
			this.name = name;
			this.page = page;
		}

		@Override
		public String toString() {
			return String.format("name: %s, page: %s", this.name, this.page);
		}

		@Override
		public boolean isLeaf() {
			return this.page;
		}

		public String getName() {
			return this.name;
		}
	}

	static class Menu extends MenuItem {

		public Menu(String name) {
			super(name, false);
		}
	}

	static class Link extends MenuItem {

		public Link(String name) {
			super(name, true);
		}
	}
}
