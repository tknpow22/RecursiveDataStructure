package rds;

import java.util.List;

public interface IRdsContent {
	boolean isLeaf();
	String getContent(int level, List<String> childContents);
}
