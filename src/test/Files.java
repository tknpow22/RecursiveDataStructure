package test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import rds.IAccessibleLeaf;
import rds.IRdsContent;

public class Files {

	static class AccessibleFile implements IAccessibleLeaf<DiskItem> {

		private List<String> accessibleFileList;

		public AccessibleFile(List<String> accessibleFileList) {
			this.accessibleFileList = accessibleFileList;
		}

		@Override
		public boolean isAccessible(DiskItem rdsContent) {
			return this.accessibleFileList.contains(rdsContent.getName());
		}
	}

	static abstract class DiskItem implements IRdsContent {

		protected String name;
		protected boolean file;

		protected DiskItem(String name, boolean file) {
			this.name = name;
			this.file = file;
		}

		public String getName() {
			return this.name;
		}

		@Override
		public String toString() {
			return String.format("name: %s, file: %s", this.name, this.file);
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
			super(name, false);
		}

		@Override
		public String getContent(int level, List<String> children) {
			StringBuffer sb = new StringBuffer();
			sb.append(String.format("\n%s<DIR:%s>", this.tab(level), this.name));
			for (String child : children) {
				sb.append(String.format("%s", child));
			}

			return sb.toString();
		}
	}

	static class File extends DiskItem {

		protected File(String name) {
			super(name, true);
		}

		@Override
		public String getContent(int level, List<String> children) {
			return String.format("\n%s<FILE:%s>", this.tab(level), this.name);
		}
	}
}
