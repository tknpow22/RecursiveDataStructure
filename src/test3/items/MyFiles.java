package test3.items;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import rds3.IAccessibleLeaf;
import rds3.Rds;

public class MyFiles {

	public static class AccessibleFile implements IAccessibleLeaf {

		private Set<String> accessibleFiles;
		private int size;

		public AccessibleFile(Set<String> accessibleFiles, int size) {
			this.accessibleFiles = accessibleFiles;
			this.size = size;
		}

		public Set<String> getAccessibleFiles() {
			return accessibleFiles;
		}

		public int getSize() {
			return size;
		}
	}

	public static abstract class DiskItem extends Rds {

		protected String name;
		protected boolean file;
		protected int size;

		protected DiskItem() {
			super();
		}

		protected DiskItem(int level, String name, boolean file, int size) {
			super(level);
			this.name = name;
			this.file = file;
			this.size = size;
		}

		@Override
		protected boolean isLeaf() {
			return this.file;
		}

		@Override
		protected boolean isAccessible(IAccessibleLeaf accessibleLeaf) {
			AccessibleFile accessibleFile = (AccessibleFile) accessibleLeaf;
			return accessibleFile.getAccessibleFiles().contains(this.name)
				 || accessibleFile.getSize() < this.size;
		}

		@Override
		protected String toStringValue() {
			return String.format("name:%s|file:%s|size:%d", this.name, this.file, this.size);
		}

		protected String tab(int tab) {
			return IntStream.range(0, tab).mapToObj(i -> "\t").collect(Collectors.joining());
		}
	}

	public static class Directory extends DiskItem {

		public Directory() {
			super();
		}

		public Directory(int level, String name) {
			super(level, name, false, 0);
		}

		@Override
		protected Rds getInstance() {
			return new Directory();
		}

		@Override
		protected Rds getInstance(int level) {
			return new Directory(level, this.name);
		}

		@Override
		protected String getContent(int level, String childContents) {
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("%s<DIR:%s>\n", this.tab(level), this.name));
			sb.append(childContents);

			return sb.toString();
		}
	}

	public static class File extends DiskItem {

		public File() {
			super();
		}

		public File(int level, String name, int size) {
			super(level, name, true, size);
		}

		@Override
		protected Rds getInstance() {
			return new File();
		}

		@Override
		protected Rds getInstance(int level) {
			return new File(level, this.name, this.size);
		}

		@Override
		protected String getContent(int level, String childContents) {
			return String.format("%s<FILE:%s:%d>\n", this.tab(level), this.name, this.size);
		}
	}
}
