package rds;

public interface IRdsContent {
	boolean isLeaf();
	String getContent(int level, String child);
}
