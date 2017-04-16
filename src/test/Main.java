package test;

import java.util.ArrayList;
import java.util.HashSet;

import rds.CollectContents;
import rds.Rds;
import rds.RdsBuilder;
import test.items.MyFiles.CollectAccessibleFile;
import test.items.MyFiles.Directory;
import test.items.MyFiles.DiskItem;
import test.items.MyFiles.File;
import test.items.MyMenus.CollectAccessibleLink;
import test.items.MyMenus.Link;
import test.items.MyMenus.Menu;
import test.items.MyMenus.MenuItem;

public class Main {

	private void printContents(String contents) {
		System.out.println(contents);
	}

	private void test1() {

		System.out.println("test1");

		// assemble tree
		Rds<MenuItem> root = new Rds<>();
		RdsBuilder<MenuItem> rdsBuilder = new RdsBuilder<>(root);
		{
			rdsBuilder.add(0, new Menu("rootmenu1"));
			{
				rdsBuilder.add(1, new Link("rootmenu1-link1", "http://rootmenu1-link1"));
				rdsBuilder.add(1, new Link("rootmenu1-link2", "http://rootmenu1-link2"));
			}

			rdsBuilder.add(0, new Menu("rootmenu2"));
			{
				rdsBuilder.add(1, new Link("rootmenu2-link1", "http://rootmenu2-link1"));
				rdsBuilder.add(1, new Menu("rootmenu2-menu1"));
				{
					rdsBuilder.add(2, new Menu("rootmenu2-menu1-menu1"));
					{
						rdsBuilder.add(3, new Link("rootmenu2-menu1-menu1-link1", "http://rootmenu2-menu1-menu1-link1"));
						rdsBuilder.add(3, new Link("rootmenu2-menu1-menu1-link2", "http://rootmenu2-menu1-menu1-link2"));
					}
				}
			}

			rdsBuilder.add(0, new Link("rootlink1", "http://rootlink1"));

			rdsBuilder.add(0, new Menu("rootmenu3"));
			{
				rdsBuilder.add(1, new Menu("rootmenu3-menu1"));
				{
					rdsBuilder.add(2, new Menu("rootmenu3-menu1-menu1"));
					{
						rdsBuilder.add(3, new Link("rootmenu3-menu1-menu1-link1", "http://rootmenu3-menu1-menu1-link1"));
					}
				}
			}

			rdsBuilder.add(0, new Link("rootlink2", "http://rootlink2"));
		}

		// print objects and contents
		System.out.println(root.toString());
		{
			CollectContents<MenuItem> collectContents = new CollectContents<>(root);
			printContents(collectContents.collect());
		}

		// collect accessible
		CollectAccessibleLink collectAccessibleLink = new CollectAccessibleLink(root, new ArrayList<String>() {
			{ add("rootmenu1-link1"); }
			{ add("rootmenu1-link2"); }
			{ add("rootlink1"); }
			{ add("rootmenu3-menu1-menu1-link1"); }
		});
		Rds<MenuItem> accessibleRoot = collectAccessibleLink.collect();

		// print accessible objects and contents
		System.out.println(accessibleRoot.toString());
		{
			CollectContents<MenuItem> collectContents = new CollectContents<>(accessibleRoot);
			printContents(collectContents.collect());
		}
	}

	private void test2() {

		System.out.println("test2");

		// assemble tree
		Rds<DiskItem> root = new Rds<>();
		RdsBuilder<DiskItem> rdsBuilder = new RdsBuilder<>(root);
		{
			rdsBuilder.add(0, new Directory("rootdir1"));
			{
				rdsBuilder.add(1, new File("rootdir1-file1", 100));
				rdsBuilder.add(1, new File("rootdir1-file2", 120));
			}

			rdsBuilder.add(0, new Directory("rootdir2"));
			{
				rdsBuilder.add(1, new File("rootdir2-file1", 200));
				rdsBuilder.add(1, new Directory("rootdir2-dir1"));
				{
					rdsBuilder.add(2, new Directory("rootdir2-dir1-dir1"));
					{
						rdsBuilder.add(3, new File("rootdir2-dir1-dir1-file1", 290));
						rdsBuilder.add(3, new File("rootdir2-dir1-dir1-file2", 400));
					}
				}
			}

			rdsBuilder.add(0, new File("rootfile1", 400));

			rdsBuilder.add(0, new Directory("rootdir3"));
			{
				rdsBuilder.add(1, new Directory("rootdir3-dir1"));
				{
					rdsBuilder.add(2, new Directory("rootdir3-dir1-dir1"));
					{
						rdsBuilder.add(3, new File("rootdir3-dir1-dir1-file1", 120));
					}
				}
			}

			rdsBuilder.add(0, new File("rootfile2", 500));
		}

		// print objects and contents
		System.out.println(root.toString());
		{
			CollectContents<DiskItem> collectContents = new CollectContents<>(root);
			printContents(collectContents.collect());
		}

		// collect accessible
		CollectAccessibleFile collectAccessibleFile = new CollectAccessibleFile(root, new HashSet<String>() {
			{ add("rootdir1-file1"); }
			{ add("rootdir1-file2"); }
			{ add("rootfile1"); }
			{ add("rootdir3-dir1-dir1-file1"); }
		}, 300);

		Rds<DiskItem> accessibleRoot = collectAccessibleFile.collect();

		// print accessible objects and contents
		System.out.println(accessibleRoot.toString());
		{
			CollectContents<DiskItem> collectContents = new CollectContents<>(accessibleRoot);
			printContents(collectContents.collect());
		}
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
