package rds3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Rds {

	private static final int ROOT_LEVEL = -1;

	private int level;

	private List<Rds> items;

	//
	// constructor
	//

	public Rds() {
		this(ROOT_LEVEL);
	}

	public Rds(int level) {
		this.level = level;
		this.items = new ArrayList<>();
	}

	//
	// isRoot
	//

	public boolean isRoot() {
		return this.level == ROOT_LEVEL;
	}

	//
	// add
	//

	public void add(Rds rdsNew) {
		if (this.level + 1 == rdsNew.level) {
			this.items.add(rdsNew);
		} else if (this.level < rdsNew.level) {
			if (this.items.size() != 0) {
				Rds rds = this.items.get(this.items.size() - 1);
				if (!rds.isLeaf()) {
					rds.add(rdsNew);
				}
			}
		}
	}

	//
	// getAccessible
	//

	public Rds getAccessible(IAccessibleLeaf accessibleLeaf) {
		if (this.isRoot()) {
			return this.collectAccessible(accessibleLeaf, this.getInstance());
		} else if (this.isLeaf()) {
			return this.isAccessible(accessibleLeaf) ? this : null;
		} else {
			return this.collectAccessible(accessibleLeaf, null);
		}
	}

	private Rds collectAccessible(IAccessibleLeaf accessibleLeaf, Rds valueDefault) {
		Rds rdsRoot = this.getInstance(this.level);
		for (Rds crds : this.items) {
			Rds acsRds = crds.getAccessible(accessibleLeaf);
			if (acsRds != null) {
				rdsRoot.items.add(acsRds);
			}
		}
		return (rdsRoot.items.size() == 0) ? valueDefault : rdsRoot;
	}

	//
	// toContents
	//

	public String toContents() {
		if (this.isRoot()) {
			return this.collectContents();
		} else {
			Rds rds = this;
			String childContents = (this.isLeaf()) ? "" : rds.collectContents();

			return rds.getContent(rds.level, childContents);
		}
	}

	private String collectContents() {
		StringBuffer contents = new StringBuffer();

		for (Rds item : this.items) {
			contents.append(item.toContents());
		}

		return contents.toString();
	}

	//
	// toString
	//

	@Override
	public String toString() {
		return this.toString(0);
	}

	private String toString(int indent) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s(\n", this.tab(indent)));
		sb.append(String.format("%s<level:%d>", this.tab(indent+1), this.level));
		sb.append(String.format("<%s>", (this.level == ROOT_LEVEL) ? "" : this.toStringValue()));
		if (this.items.size() != 0) {
			sb.append(String.format("\n%s[\n", this.tab(indent+1)));
			for (Rds item : this.items) {
				sb.append(item.toString(indent+2));
			}
			sb.append(String.format("%s]", this.tab(indent+1)));
		}
		sb.append(String.format("\n%s)\n", this.tab(indent)));

		return sb.toString();
	}

	private String tab(int tab) {
		return IntStream.range(0, tab).mapToObj(i -> "\t").collect(Collectors.joining());
	}

	//
	// getInstance
	//

	protected abstract Rds getInstance();
	protected abstract Rds getInstance(int level);

	//
	// isLeaf
	//

	protected abstract boolean isLeaf();

	//
	// isAccessible
	//
	protected abstract boolean isAccessible(IAccessibleLeaf accessibleLeaf);

	//
	// getContent
	//

	protected abstract String getContent(int level, String childContents);


	//
	// toStringValue
	//

	protected abstract String toStringValue();
}
