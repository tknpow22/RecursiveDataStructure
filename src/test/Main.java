package test;

import java.util.ArrayList;

import rds.Rds;
import test.Menus.AccessibleLink;
import test.Menus.Link;
import test.Menus.Menu;
import test.Menus.MenuItem;

public class Main {

	public static void main(String[] args) {

		Rds<MenuItem> root = new Rds<>();

		root.add(new Rds<MenuItem>(0, new Menu("rootmenu1")));
		{
			root.add(new Rds<MenuItem>(1, new Link("rootmenu1-link1", "http://rootmenu1-link1")));
			root.add(new Rds<MenuItem>(1, new Link("rootmenu1-link2", "http://rootmenu1-link2")));
		}

		root.add(new Rds<MenuItem>(0, new Menu("rootmenu2")));
		{
			root.add(new Rds<MenuItem>(1, new Link("rootmenu2-link1", "http://rootmenu2-link1")));
			root.add(new Rds<MenuItem>(1, new Menu("rootmenu2-menu1")));
			{
				root.add(new Rds<MenuItem>(2, new Menu("rootmenu2-menu1-menu1")));
				{
					root.add(new Rds<MenuItem>(3, new Link("rootmenu2-menu1-menu1-link1", "http://rootmenu2-menu1-menu1-link1")));
					root.add(new Rds<MenuItem>(3, new Link("rootmenu2-menu1-menu1-link2", "http://rootmenu2-menu1-menu1-link2")));
				}
			}
		}

		root.add(new Rds<MenuItem>(0, new Link("rootlink1", "http://rootlink1")));

		root.add(new Rds<MenuItem>(0, new Menu("rootmenu3")));
		{
			root.add(new Rds<MenuItem>(1, new Menu("rootmenu3-menu1")));
			{
				root.add(new Rds<MenuItem>(2, new Menu("rootmenu3-menu1-menu1")));
				{
					root.add(new Rds<MenuItem>(3, new Link("rootmenu3-menu1-menu1-link1", "http://rootmenu3-menu1-menu1-link1")));
				}
			}
		}

		root.add(new Rds<MenuItem>(0, new Link("rootlink2", "http://rootlink2")));

		System.out.println(root.toString());
		System.out.println(root.toContents());

		AccessibleLink accessibleLink = new AccessibleLink(new ArrayList<String>() {
			{ add("rootmenu1-link1"); }
			{ add("rootmenu1-link2"); }
			{ add("rootlink1"); }
			{ add("rootmenu3-menu1-menu1-link1"); }
		});

		Rds<MenuItem> accessibleRoot = root.getAccessible(accessibleLink);

		System.out.println(accessibleRoot.toString());
		System.out.println(accessibleRoot.toContents());
	}
}
