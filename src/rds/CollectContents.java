package rds;

public class CollectContents<T extends IRdsContent> {

	private Rds<T> rdsRoot;

	public CollectContents(Rds<T> rdsRoot) {
		this.rdsRoot = rdsRoot;
	}

	public String collect() {
		return this.collectInnter(-1, this.rdsRoot);
	}

	private String collectChild(int level, Rds<T> rds) {
		String childContents = (rds.getItem().isLeaf()) ? "" : this.collectInnter(level, rds);
		return rds.getItem().getContent(level, childContents);
	}

	private String collectInnter(int level, Rds<T> rds) {
		StringBuffer contents = new StringBuffer();

		for (Rds<T> crds : rds.getChildren()) {
			contents.append(this.collectChild(level + 1, crds));
		}

		return contents.toString();
	}
}
