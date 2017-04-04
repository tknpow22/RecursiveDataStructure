package rds;

public interface AccessibleLeaf<T extends IRdsContent> {
	boolean isAccessible(T rdsContent);
}
