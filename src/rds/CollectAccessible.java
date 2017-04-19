package rds;

public abstract class CollectAccessible<T extends IRdsContent> {

	private Rds<T> rdsRoot;

	public CollectAccessible(Rds<T> rdsRoot) {
		this.rdsRoot = rdsRoot;
	}

	public Rds<T> collect() {
		return this.collectInner(this.rdsRoot, new Rds<T>());
	}

	private Rds<T> collectChild(Rds<T> rds) {
		if (rds.getItem().isLeaf()) {
			return this.isAccessible(rds.getItem()) ? rds : null;
		} else {
			Rds<T> rdsNew = null;
			if (this.isAddEmptyInternalNode() && this.isAccessible(rds.getItem())) {
				rdsNew = new Rds<>(rds.getItem());
			}
			return this.collectInner(rds,  rdsNew);
		}
	}

	private Rds<T> collectInner(Rds<T> rdsParent, Rds<T> valueDefault) {
		Rds<T> rdsParentNew = new Rds<>(rdsParent.getItem());
		for (Rds<T> crds : rdsParent.getChildren()) {
			Rds<T> acsRds = this.collectChild(crds);
			if (acsRds != null) {
				rdsParentNew.getChildren().add(acsRds);
			}
		}

		return (rdsParentNew.getChildren().size() == 0) ? valueDefault : rdsParentNew;
	}

	protected boolean isAddEmptyInternalNode() {
		return false;
	}

	protected abstract boolean isAccessible(T item);
}
