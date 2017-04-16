package test.items;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import rds.CollectAccessible;
import rds.IRdsContent;
import rds.Rds;

public class MyFiles {

	public static class CollectAccessibleFile extends CollectAccessible<DiskItem> {

		private Set<String> accessibleFiles;
		private int size;

		public CollectAccessibleFile(Rds<DiskItem> rdsRoot, Set<String> accessibleFiles, int size) {
			super(rdsRoot);
			this.accessibleFiles = accessibleFiles;
			this.size = size;
		}

		@Override
		protected boolean isAccessible(DiskItem item) {
			return this.accessibleFiles.contains(item.getName())
				 || this.size < item.getSize();
		}
	}

	public static abstract class DiskItem implements IRdsContent {

		protected String name;
		protected boolean file;
		protected int size;

		protected DiskItem(String name, boolean file, int size) {
			this.name = name;
			this.file = file;
			this.size = size;
		}

		public String getName() {
			return this.name;
		}

		public int getSize() {
			return this.size;
		}

		@Override
		public boolean isLeaf() {
			return this.file;
		}

		@Override
		public String toString() {
			return String.format("name:%s|file:%s|size:%d", this.name, this.file, this.size);
		}

		protected String tab(int tab) {
			return IntStream.range(0, tab).mapToObj(i -> "\t").collect(Collectors.joining());
		}
	}

	public static class Directory extends DiskItem {

		public Directory(String name) {
			super(name, false, 0);
		}

		@Override
		public String getContent(int level, String childContents) {
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%s<DIR:%s>\n", this.tab(level), this.name));
			sb.append(childContents);

			return sb.toString();
		}
	}

	public static class File extends DiskItem {

		public File(String name, int size) {
			super(name, true, size);
		}

		@Override
		public String getContent(int level, String childContents) {
			return String.format("%s<FILE:%s:%d>\n", this.tab(level), this.name, this.size);
		}
	}
}
