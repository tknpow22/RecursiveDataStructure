package test;

import java.util.ArrayList;
import java.util.List;

import rds.Rds;
import test.MyFiles.AccessibleFile;
import test.MyFiles.Directory;
import test.MyFiles.DiskItem;
import test.MyFiles.File;
import test.MyMenus.AccessibleLink;
import test.MyMenus.Link;
import test.MyMenus.Menu;
import test.MyMenus.MenuItem;

public class Main {

	private void printContents(List<String> contents) {
		System.out.println("\n--");
		contents.forEach(System.out::print);
		System.out.println("\n--");
	}

	private void test1() {

		System.out.println("test1");

		// assemble tree
		Rds<MenuItem> root = new Rds<>();
		{
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
		}

		// print objects and contents
		System.out.println(root.toString());
		printContents(root.toContents());

		// collect accessible
		AccessibleLink accessibleLink = new AccessibleLink(new ArrayList<String>() {
			{ add("rootmenu1-link1"); }
			{ add("rootmenu1-link2"); }
			{ add("rootlink1"); }
			{ add("rootmenu3-menu1-menu1-link1"); }
		});
		Rds<MenuItem> accessibleRoot = root.getAccessible(accessibleLink);

		// print accessible objects and contents
		System.out.println(accessibleRoot.toString());
		printContents(accessibleRoot.toContents());
	}

	private void test2() {

		System.out.println("test2");

		// assemble tree
		Rds<DiskItem> root = new Rds<>();
		{
			root.add(new Rds<DiskItem>(0, new Directory("rootdir1")));
			{
				root.add(new Rds<DiskItem>(1, new File("rootdir1-file1", 100)));
				root.add(new Rds<DiskItem>(1, new File("rootdir1-file2", 120)));
			}

			root.add(new Rds<DiskItem>(0, new Directory("rootdir2")));
			{
				root.add(new Rds<DiskItem>(1, new File("rootdir2-file1", 200)));
				root.add(new Rds<DiskItem>(1, new Directory("rootdir2-dir1")));
				{
					root.add(new Rds<DiskItem>(2, new Directory("rootdir2-dir1-dir1")));
					{
						root.add(new Rds<DiskItem>(3, new File("rootdir2-dir1-dir1-file1", 290)));
						root.add(new Rds<DiskItem>(3, new File("rootdir2-dir1-dir1-file2", 400)));
					}
				}
			}

			root.add(new Rds<DiskItem>(0, new File("rootfile1", 400)));

			root.add(new Rds<DiskItem>(0, new Directory("rootdir3")));
			{
				root.add(new Rds<DiskItem>(1, new Directory("rootdir3-dir1")));
				{
					root.add(new Rds<DiskItem>(2, new Directory("rootdir3-dir1-dir1")));
					{
						root.add(new Rds<DiskItem>(3, new File("rootdir3-dir1-dir1-file1", 120)));
					}
				}
			}

			root.add(new Rds<DiskItem>(0, new File("rootfile2", 500)));
		}

		// print objects and contents
		System.out.println(root.toString());
		printContents(root.toContents());

		// collect accessible
		AccessibleFile accessibleFile = new AccessibleFile(new ArrayList<String>() {
			{ add("rootdir1-file1"); }
			{ add("rootdir1-file2"); }
			{ add("rootfile1"); }
			{ add("rootdir3-dir1-dir1-file1"); }
		}, 300);
		Rds<DiskItem> accessibleRoot = root.getAccessible(accessibleFile);

		// print accessible objects and contents
		System.out.println(accessibleRoot.toString());
		printContents(accessibleRoot.toContents());
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
