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

	public Rds() {
		this(ROOT_LEVEL, null);
	}

	public Rds(int level, T item) {
		this.level = level;
		this.item = item;
		this.items = new ArrayList<>();
	}

	public Rds<T> getAccessible(AccessibleLeaf<T> accessibleLeaf) {

		if (this.isRoot() || !this.item.isLeaf()) {
			Rds<T> rdsRoot = new Rds<>(this.level, this.item);
			for (Rds<T> crds : this.items) {
				Rds<T> acsRds = crds.getAccessible(accessibleLeaf);
				if (acsRds != null) {
					rdsRoot.items.add(acsRds);
				}
			}

			if (this.isRoot()) {
				return (rdsRoot.items.size() == 0) ? new Rds<T>() : rdsRoot;
			} else {
				return (rdsRoot.items.size() == 0) ? null : rdsRoot;
			}

		} else {
			return accessibleLeaf.isAccessible(this.item) ? this : null;
		}
	}

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

	public boolean isRoot() {
		return this.level == ROOT_LEVEL;
	}

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
