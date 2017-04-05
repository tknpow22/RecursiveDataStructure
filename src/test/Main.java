package test;

import java.util.ArrayList;
import java.util.List;

import rds.Rds;
import test.Files.AccessibleFile;
import test.Files.Directory;
import test.Files.DiskItem;
import test.Files.File;
import test.Menus.AccessibleLink;
import test.Menus.Link;
import test.Menus.Menu;
import test.Menus.MenuItem;

public class Main {

	private void printList(List<String> list) {
		list.forEach(System.out::print);
	}

	private void test1() {

		System.out.println("test1");

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
		printList(root.toContents());

		AccessibleLink accessibleLink = new AccessibleLink(new ArrayList<String>() {
			{ add("rootmenu1-link1"); }
			{ add("rootmenu1-link2"); }
			{ add("rootlink1"); }
			{ add("rootmenu3-menu1-menu1-link1"); }
		});

		Rds<MenuItem> accessibleRoot = root.getAccessible(accessibleLink);

		System.out.println(accessibleRoot.toString());
		printList(accessibleRoot.toContents());
	}

	private void test2() {

		System.out.println("test2");

		Rds<DiskItem> root = new Rds<>();

		root.add(new Rds<DiskItem>(0, new Directory("rootdir1")));
		{
			root.add(new Rds<DiskItem>(1, new File("rootdir1-file1")));
			root.add(new Rds<DiskItem>(1, new File("rootdir1-file2")));
		}

		root.add(new Rds<DiskItem>(0, new Directory("rootdir2")));
		{
			root.add(new Rds<DiskItem>(1, new File("rootdir2-file1")));
			root.add(new Rds<DiskItem>(1, new Directory("rootdir2-dir1")));
			{
				root.add(new Rds<DiskItem>(2, new Directory("rootdir2-dir1-dir1")));
				{
					root.add(new Rds<DiskItem>(3, new File("rootdir2-dir1-dir1-file1")));
					root.add(new Rds<DiskItem>(3, new File("rootdir2-dir1-dir1-file2")));
				}
			}
		}

		root.add(new Rds<DiskItem>(0, new File("rootfile1")));

		root.add(new Rds<DiskItem>(0, new Directory("rootdir3")));
		{
			root.add(new Rds<DiskItem>(1, new Directory("rootdir3-dir1")));
			{
				root.add(new Rds<DiskItem>(2, new Directory("rootdir3-dir1-dir1")));
				{
					root.add(new Rds<DiskItem>(3, new File("rootdir3-dir1-dir1-file1")));
				}
			}
		}

		root.add(new Rds<DiskItem>(0, new File("rootfile2")));

		System.out.println(root.toString());
		printList(root.toContents());

		AccessibleFile accessibleFile = new AccessibleFile(new ArrayList<String>() {
			{ add("rootdir1-file1"); }
			{ add("rootdir1-file2"); }
			{ add("rootfile1"); }
			{ add("rootdir3-dir1-dir1-file1"); }
		});

		Rds<DiskItem> accessibleRoot = root.getAccessible(accessibleFile);

		System.out.println(accessibleRoot.toString());
		printList(accessibleRoot.toContents());
	}

	public static void main(String[] args) {

		try {
			Main self = new Main();

			self.test1();
			self.test2();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
