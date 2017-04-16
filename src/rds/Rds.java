package rds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rds<T extends IRdsContent> {

	private T item;
	private List<Rds<T>> children;

	//
	// constructor
	//

	public Rds() {
		this(null);
	}

	public Rds(T item) {
		this.item = item;
		this.children = new ArrayList<>();
	}

	//
	// item and children operation
	//

	public T getItem() {
		return this.item;
	}

	public List<Rds<T>> getChildren() {
		return this.children;
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
		sb.append(String.format("%s<%s>", this.tab(indent+1), (this.item == null) ? "" : this.item.toString()));
		if (this.children.size() != 0) {
			sb.append(String.format("\n%s[\n", this.tab(indent+1)));
			for (Rds<T> item : this.children) {
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
