package rds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rds<T extends IRdsContent> {

	private static final int ROOT_LEVEL = -1;

	private int level;

	private T item;
	private List<Rds<T>> items;

	//
	// constructor
	//

	public Rds() {
		this(ROOT_LEVEL, null);
	}

	public Rds(int level, T item) {
		this.level = level;
		this.item = item;
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

	public void add(Rds<T> rdsNew) {
		if (this.level + 1 == rdsNew.level) {
			this.items.add(rdsNew);
		} else if (this.level < rdsNew.level) {
			if (this.items.size() != 0) {
				Rds<T> rds = this.items.get(this.items.size() - 1);
				if (!rds.item.isLeaf()) {
					rds.add(rdsNew);
				}
			}
		}
	}

	//
	// getAccessible
	//

	public Rds<T> getAccessible(IAccessibleLeaf<T> accessibleLeaf) {
		if (this.isRoot()) {
			return this.collectAccessible(accessibleLeaf, new Rds<T>());
		} else if (this.item.isLeaf()) {
			return accessibleLeaf.isAccessible(this.item) ? this : null;
		} else {
			return this.collectAccessible(accessibleLeaf, null);
		}
	}

	private Rds<T> collectAccessible(IAccessibleLeaf<T> accessibleLeaf, Rds<T> valueDefault) {
		Rds<T> rdsRoot = new Rds<>(this.level, this.item);
		for (Rds<T> crds : this.items) {
			Rds<T> acsRds = crds.getAccessible(accessibleLeaf);
			if (acsRds != null) {
				rdsRoot.items.add(acsRds);
			}
		}
		return (rdsRoot.items.size() == 0) ? valueDefault : rdsRoot;
	}

	//
	// toContents
	//

	public List<String> toContents() {
		if (this.isRoot()) {
			return this.collectContents();
		} else {
			Rds<T> rds = this;
			List<String> childContents = (this.item.isLeaf()) ? new ArrayList<String>() : rds.collectContents();

			return new ArrayList<String>() {
				{ add(rds.item.getContent(rds.level, childContents)); }
			};
		}
	}

	private List<String> collectContents() {
		List<String> contents = new ArrayList<>();

		for (Rds<T> item : this.items) {
			contents.addAll(item.toContents());
		}

		return contents;
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
		sb.append(String.format("<%s>", (this.item == null) ? "" : this.item.toString()));
		if (this.items.size() != 0) {
			sb.append(String.format("\n%s[\n", this.tab(indent+1)));
			for (Rds<T> item : this.items) {
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
}
