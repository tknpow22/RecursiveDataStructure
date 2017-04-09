package test3;

import java.util.ArrayList;
import java.util.HashSet;

import rds3.Rds;
import test3.items.MyFiles.File;
import test3.items.MyMenus.AccessibleLink;
import test3.items.MyMenus.Link;
import test3.items.MyMenus.Menu;
import test3.items.MyFiles.AccessibleFile;
import test3.items.MyFiles.Directory;

public class Main {

	private void printContents(String contents) {
		System.out.println(contents);
	}

	private void test1() {

		System.out.println("test1");

		// assemble tree
		Menu root = new Menu();
		{
			root.add(new Menu(0, "rootmenu1"));
			{
				root.add(new Link(1, "rootmenu1-link1", "http://rootmenu1-link1"));
				root.add(new Link(1, "rootmenu1-link2", "http://rootmenu1-link2"));
			}

			root.add(new Menu(0, "rootmenu2"));
			{
				root.add(new Link(1, "rootmenu2-link1", "http://rootmenu2-link1"));
				root.add(new Menu(1, "rootmenu2-menu1"));
				{
					root.add(new Menu(2, "rootmenu2-menu1-menu1"));
					{
						root.add(new Link(3, "rootmenu2-menu1-menu1-link1", "http://rootmenu2-menu1-menu1-link1"));
						root.add(new Link(3, "rootmenu2-menu1-menu1-link2", "http://rootmenu2-menu1-menu1-link2"));
					}
				}
			}

			root.add(new Link(0, "rootlink1", "http://rootlink1"));

			root.add(new Menu(0, "rootmenu3"));
			{
				root.add(new Menu(1, "rootmenu3-menu1"));
				{
					root.add(new Menu(2, "rootmenu3-menu1-menu1"));
					{
						root.add(new Link(3, "rootmenu3-menu1-menu1-link1", "http://rootmenu3-menu1-menu1-link1"));
					}
				}
			}

			root.add(new Link(0, "rootlink2", "http://rootlink2"));
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
		Rds accessibleRoot = root.getAccessible(accessibleLink);

		// print accessible objects and contents
		System.out.println(accessibleRoot.toString());
		printContents(accessibleRoot.toContents());
	}

	private void test2() {

		System.out.println("test2");

		// assemble tree
		Directory root = new Directory();
		{
			root.add(new Directory(0, "rootdir1"));
			{
				root.add(new File(1, "rootdir1-file1", 100));
				root.add(new File(1, "rootdir1-file2", 120));
			}

			root.add(new Directory(0, "rootdir2"));
			{
				root.add(new File(1, "rootdir2-file1", 200));
				root.add(new Directory(1, "rootdir2-dir1"));
				{
					root.add(new Directory(2, "rootdir2-dir1-dir1"));
					{
						root.add(new File(3, "rootdir2-dir1-dir1-file1", 290));
						root.add(new File(3, "rootdir2-dir1-dir1-file2", 400));
					}
				}
			}

			root.add(new File(0, "rootfile1", 400));

			root.add(new Directory(0, "rootdir3"));
			{
				root.add(new Directory(1, "rootdir3-dir1"));
				{
					root.add(new Directory(2, "rootdir3-dir1-dir1"));
					{
						root.add(new File(3, "rootdir3-dir1-dir1-file1", 120));
					}
				}
			}

			root.add(new File(0, "rootfile2", 500));
		}

		// print objects and contents
		System.out.println(root.toString());
		printContents(root.toContents());

		// collect accessible
		AccessibleFile accessibleFile = new AccessibleFile(new HashSet<String>() {
			{ add("rootdir1-file1"); }
			{ add("rootdir1-file2"); }
			{ add("rootfile1"); }
			{ add("rootdir3-dir1-dir1-file1"); }
		}, 300);
		Rds accessibleRoot = root.getAccessible(accessibleFile);

		// print accessible objects and contents
		System.out.println(accessibleRoot.toString());
		printContents(accessibleRoot.toContents());
	}

	public static void main(String[] args) {

		try {

			Main self = new Main();
			System.out.println(self.getClass().getName());

			self.test1();
			self.test2();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
