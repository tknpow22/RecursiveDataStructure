package rds;

public interface IRdsContent {
	boolean isLeaf();
	String toContent(int level, String child);
}
