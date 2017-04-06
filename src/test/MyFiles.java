package test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import rds.IAccessibleLeaf;
import rds.IRdsContent;

public class MyFiles {

	static class AccessibleFile implements IAccessibleLeaf<DiskItem> {

		private Set<String> accessibleFiles;
		private int size;

		public AccessibleFile(Set<String> accessibleFiles, int size) {
			this.accessibleFiles = accessibleFiles;
			this.size = size;
		}

		@Override
		public boolean isAccessible(DiskItem rdsContent) {
			return this.accessibleFiles.contains(rdsContent.getName())
				 || this.size < rdsContent.getSize();
		}
	}

	static abstract class DiskItem implements IRdsContent {

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
		public String toString() {
			return String.format("name:%s|file:%s|size:%d", this.name, this.file, this.size);
		}

		@Override
		public boolean isLeaf() {
			return this.file;
		}

		protected String tab(int tab) {
			return IntStream.range(0, tab).mapToObj(i -> "\t").collect(Collectors.joining());
		}
	}

	static class Directory extends DiskItem {

		protected Directory(String name) {
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

	static class File extends DiskItem {

		protected File(String name, int size) {
			super(name, true, size);
		}

		@Override
		public String getContent(int level, String childContents) {
			return String.format("%s<FILE:%s:%d>\n", this.tab(level), this.name, this.size);
		}
	}
}
