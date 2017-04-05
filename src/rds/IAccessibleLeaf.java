package rds;

public interface IAccessibleLeaf<T extends IRdsContent> {
	boolean isAccessible(T rdsContent);
}
